package com.exengg.jupiter.Controller;

import com.exengg.jupiter.Dto.Requests.PasswordUpdateReq;
import com.exengg.jupiter.Dto.Requests.UserRequest;
import com.exengg.jupiter.Entity.User;
import com.exengg.jupiter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;


    @GetMapping("/get/{userId}")
    public ResponseEntity<?> getUser(@PathVariable String userId) {
        try {
            User user = userService.getUserById(userId);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (Exception e) {
            log.error("Error while fetching single user ",e);
            return new ResponseEntity<>("User NOT fetched", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable String userId,@RequestBody UserRequest userRequest){
        try{
            userService.updateUser(userRequest,userId);
            return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while updating user : ",e);
            return new ResponseEntity<>("User NOT updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/update/password")
    public ResponseEntity<?> updatePassword(@RequestBody PasswordUpdateReq passwordUpdateReq){
        try{
            userService.updatePassword(passwordUpdateReq);
            return new ResponseEntity<>("User updated successfully",HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while updating user : ",e);
            return new ResponseEntity<>("User NOT updated",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable String userId){
        try{
            userService.deleteUser(userId);
            return new ResponseEntity<>("User deleted successfully",HttpStatus.OK);
        }catch(Exception e){
            log.error("Error while deleting user : ",e);
            return new ResponseEntity<>("User NOT Deleted",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/print")
    public String print(){
        log.info(SecurityContextHolder.getContext().getAuthentication().toString());
        String userId=SecurityContextHolder.getContext().getAuthentication().getCredentials().toString();
        log.info("Fetched user through context is userId : {}",userId);
        return "Printed in logs";
    }


}
