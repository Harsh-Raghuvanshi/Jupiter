package com.exengg.jupiter.Service;

import com.exengg.jupiter.Entity.Product;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Repo.ProductRepo;
import com.exengg.jupiter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserProdService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private ProductRepo productRepo;

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

}
