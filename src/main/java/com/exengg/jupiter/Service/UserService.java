package com.exengg.jupiter.Service;

import com.exengg.jupiter.Dto.Requests.PasswordUpdateReq;
import com.exengg.jupiter.Dto.Requests.UserRequest;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Repo.UserRepo;
import com.exengg.jupiter.Utils.Converter.ObjConverter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Slf4j
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

    public User getUserByEmailId(String emailId) {
        return userRepo.findByEmailId(emailId);
    }

    public void createUser(UserRequest userRequest) {
        User user = ObjConverter.getUserObjFromUserRequest(userRequest);
        if (user == null) {
            log.error("When transformed user comes as null");
            return;
        }
        String password = user.getPassword();
        String newPassword = passwordEncoder.encode(password);
        user.setPassword(newPassword);
        user.getRoles().add("USER");
        userRepo.save(user);
    }

    public void updateUser(UserRequest updatedUserRequest, String userId) {
        Optional<User> opt = userRepo.findById(userId);
        User updatedUser = ObjConverter.getUserObjFromUserRequest(updatedUserRequest);
        if (opt.isEmpty() || updatedUser == null) {
            log.error("Either update user is null or existing user");
            return;
        }

        User existingUser = opt.get();
        updatedUser.setId(existingUser.getId());
        updatedUser.setPassword(existingUser.getPassword());
        userRepo.save(updatedUser);

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
