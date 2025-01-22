package com.exengg.jupiter.Service;

import com.exengg.jupiter.Dto.Requests.ProductRequest;
import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Enums.ProductStatus;
import com.exengg.jupiter.Repo.ProductRepo;
import com.exengg.jupiter.Repo.UserRepo;
import com.exengg.jupiter.Utils.Converter.ObjConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    public void createProduct(ProductRequest productRequest) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        Product product = ObjConverter.getProductObjFromProductRequest(productRequest);
        User user = userRepo.findById(userId).orElse(null);
        if (user == null || product == null) {
            log.error("Either user or product is null user : {} product : {}", user, product);
            return;
        }
        user.getMyProducts().add(product.getId());
        userRepo.save(user);

        product.setOwnerId(userId);
        product.setProductStatus(ProductStatus.IN_MARKET);
        productRepo.save(product);
    }

    public List<Product> getProductCategoryWise(String productType) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        List<Product> products = productRepo.findByProductType(productType);
        if (user != null) {
            Set<String> myProducts = new HashSet<>(user.getMyProducts());
            products = products.stream().filter(x -> !myProducts.contains(x.getId())).collect(Collectors.toList());
        }
        return products;
    }

    public Product getProductById(String productId) {
        return productRepo.findById(productId).orElse(null);
    }

    public void updateProduct(ProductRequest productRequest, String productId) {
        Optional<Product> opt = productRepo.findById(productId);
        Product updateProduct = ObjConverter.getProductObjFromProductRequest(productRequest);
        if (opt.isEmpty() || updateProduct == null) {
            log.error("Either existing product is null or coming product is null");
            return;
        }

        Product existingProduct = opt.get();
        updateProduct.setId(existingProduct.getId());
        updateProduct.setOwnerId(existingProduct.getOwnerId());
        productRepo.save(updateProduct);

    }

    public void deleteProduct(String productId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            user.setMyProducts(user.getMyProducts().stream().filter(x -> !x.equals(productId)).collect(Collectors.toList()));
            userRepo.save(user);
        }
        productRepo.deleteById(productId);
    }

}
