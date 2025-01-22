package com.exengg.jupiter.Service;

import com.exengg.jupiter.Dto.Requests.DealRequest;
import com.exengg.jupiter.Entity.Deals;
import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Enums.ProductStatus;
import com.exengg.jupiter.Repo.DealsRepo;
import com.exengg.jupiter.Repo.ProductRepo;
import com.exengg.jupiter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserProdService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

    @Autowired
    private DealsRepo dealsRepo;

    public List<Product> getMyProducts() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        List<Product> products = new ArrayList<>();
        if (user != null) {
            products = productRepo.findAllById(user.getMyProducts());
        }
        return products;
    }

    public List<Product> getMyWatchList() {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        List<Product> products = new ArrayList<>();
        List<String> newWatchList = new ArrayList<>();
        if (user != null) {
            for (String pid : user.getMyWatchlist()) {
                Optional<Product> productOpt = productRepo.findById(pid);
                if (productOpt.isPresent()) {
                    products.add(productOpt.get());
                    newWatchList.add(pid);
                }
            }
            if (user.getMyWatchlist().size() > newWatchList.size()) {
                user.setMyWatchlist(newWatchList);
                userRepo.save(user);
            }
        }
        return products;
    }

    public void bookProduct(String productId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);
        if (user != null) {
            user.getMyWatchlist().add(productId);
            userRepo.save(user);
        }
        if (product != null) {
            product.getBookers().add(userId);
            productRepo.save(product);
        }
    }

    public void unBookProduct(String productId) {
        String userId = SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        User user = userRepo.findById(userId).orElse(null);
        Product product = productRepo.findById(productId).orElse(null);
        if (user != null) {
            user.setMyWatchlist(user.getMyWatchlist().stream().filter(pid -> !pid.equals(productId)).collect(Collectors.toList()));
            userRepo.save(user);
        }
        if (product != null) {
            product.setBookers(product.getBookers().stream().filter(uid -> !uid.equals(userId)).collect(Collectors.toList()));
            productRepo.save(product);
        }
    }

    public void dealDoneProduct(DealRequest dealRequest) {
        Deals deals = new Deals();
        Product product = productRepo.findById(dealRequest.getProductId()).orElse(null);
        if (product != null) {
            product.setProductStatus(ProductStatus.DEAL_DONE);
            productRepo.save(product);
        }
        deals.setProductId(dealRequest.getProductId());
        deals.setCustomerId(dealRequest.getCustomerId());
        deals.setOwnerId(dealRequest.getOwnerId());
        deals.setLocalDateTime(LocalDateTime.now());
        dealsRepo.save(deals);
    }

}
