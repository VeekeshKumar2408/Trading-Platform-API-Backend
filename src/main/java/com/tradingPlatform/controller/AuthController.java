package com.tradingPlatform.controller;

import com.tradingPlatform.model.User;
import com.tradingPlatform.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/sign-up")
    public ResponseEntity<User> register(@RequestBody User user){

        log.info("Inside register method");
        try {
            User newUser = new User();
            newUser.setEmail(user.getEmail());
            newUser.setPassword(user.getPassword());
            newUser.setFullName(user.getFullName());
            newUser.setMobile(user.getMobile());

            User savedUser = userRepository.save(newUser);

            return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
       }
//        catch (DataIntegrityViolationException e){
//            log.error("Email {} already exists", user.getEmail(), e);
//            return ResponseEntity.status(HttpStatus.CONFLICT).body(null);
//        }
        catch (Exception e){
            log.error("Error occurred while creating new User", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }
}
