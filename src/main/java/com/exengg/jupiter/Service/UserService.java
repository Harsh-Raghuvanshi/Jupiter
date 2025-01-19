package com.exengg.jupiter.Service;

import com.exengg.jupiter.Dto.PasswordUpdateReq;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public static PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public List<User> getAllUsers() {
        return userRepo.findAll();
    }

    public User getUserById(String userId) {
        Optional<User> opt = userRepo.findById(userId);
        return opt.orElse(null);
    }

    public User getUserByEmailId(String emailId){
        return userRepo.findByEmailId(emailId);
    }

    public void createUser(User user) {
        String password = user.getPassword();
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);
        userRepo.save(user);
    }

    public void updateUser(User updatedUser, String userId) {
        Optional<User> opt = userRepo.findById(userId);
        if (opt.isPresent()) {
            User existingUser = opt.get();
            updatedUser.setId(existingUser.getId());
            updatedUser.setPassword(existingUser.getPassword());
            userRepo.save(updatedUser);
        }
    }

    public void deleteUser(String userId) {
        userRepo.deleteById(userId);
    }

    public void updatePassword(PasswordUpdateReq passwordUpdateReq) {
        Optional<User> opt = userRepo.findById(passwordUpdateReq.getUserId());
        if (opt.isPresent()) {
            User user = opt.get();
            String hashedPassword = passwordEncoder.encode(passwordUpdateReq.getNewPassword());
            user.setPassword(hashedPassword);
            userRepo.save(user);
        }
    }


}
