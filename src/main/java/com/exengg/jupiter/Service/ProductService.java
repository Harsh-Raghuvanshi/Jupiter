package com.exengg.jupiter.Service;

import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Enums.ProductType;
import com.exengg.jupiter.Repo.ProductRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepo productRepo;

    public void createProduct(Product product) {
        productRepo.save(product);
    }

    public List<Product> getProductCategoryWise(String productType) {
        return productRepo.findByProductType(productType);
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
        productRepo.deleteById(productId);
    }
}
