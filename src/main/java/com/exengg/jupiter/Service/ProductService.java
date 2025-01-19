package com.exengg.jupiter.Service;

import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Enums.ProductStatus;
import com.exengg.jupiter.Repo.ProductRepo;
import com.exengg.jupiter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private UserRepo userRepo;

    public void createProduct(Product product) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        if (user != null) {
            user.getMyProducts().add(product.getId());
            userRepo.save(user);
            product.setOwnerId(userId);
        }
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

    public void updateProduct(Product product, String productId) {
        Optional<Product> opt = productRepo.findById(productId);
        if (opt.isPresent()) {
            Product existingProduct = opt.get();
            product.setId(existingProduct.getId());
            product.setOwnerId(existingProduct.getOwnerId());
        }
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
