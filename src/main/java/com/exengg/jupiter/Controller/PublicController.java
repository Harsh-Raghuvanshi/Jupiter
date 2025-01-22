package com.exengg.jupiter.Controller;

import com.exengg.jupiter.Dto.Requests.UserRequest;
import com.exengg.jupiter.Service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    private UserService userService;


    private static final String SUFFIX=".dtu.ac.in";

    @GetMapping("/ping")
    public String ping(){
        return "Pong";
    }

    @PostMapping("/create")
    public ResponseEntity<?> createUser(@RequestBody UserRequest userRequest) {
        try {
            if(!userRequest.getEmailId().endsWith(SUFFIX)){
                return new ResponseEntity<>("User NOT Created as mail do not belongs to DTU", HttpStatus.BAD_REQUEST);
            }
            userService.createUser(userRequest);
            return new ResponseEntity<>("User Created Successfully", HttpStatus.CREATED);
        } catch (Exception e) {
            log.error("Error occurred while creating user ", e);
            return new ResponseEntity<>("User NOT Created", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
