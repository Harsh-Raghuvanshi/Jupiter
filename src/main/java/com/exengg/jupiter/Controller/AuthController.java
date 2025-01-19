package com.exengg.jupiter.Controller;

import com.exengg.jupiter.Dto.AuthRequest;
import com.exengg.jupiter.Utils.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;

    public AuthController(AuthenticationManager authenticationManager, JwtUtil jwtUtil) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody AuthRequest authRequest) {
        try {
            Authentication authenticate = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmailId(), authRequest.getPassword())
            );

            User user = (User) authenticate.getPrincipal();
            return new ResponseEntity<>(jwtUtil.generateToken(user.getUsername()), HttpStatus.OK);
        } catch (AuthenticationException e) {
            log.info("Invalid credentials entered {}",authRequest);
            return new ResponseEntity<>("Invalid Credentials",HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

