package com.technova.shopping_cart.TechNova.Cart.controller;

import com.technova.shopping_cart.TechNova.Cart.dto.LoginRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.exception.UserException;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.service.UserService;
import com.technova.shopping_cart.TechNova.Cart.utils.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/str")
    public String name() {
        return "Hi Guys";
    }

    @GetMapping("/users")
    public ResponseEntity<Object> getAllUsers(@RequestParam(name = "email", required = false) String email) {
        if (email != null) {
            Optional<User> user = userService.getUserByEmail(email);
            if (user.isPresent()) {
                log.info("user with email{} fetched",user.get().getEmail());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), "User with email:" + email + "fetched successfully", user, null);
            }
            log.info("user with email{} not found",user.get().getEmail());
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "User with email" + email + "not found in our database", null, "User not found");

        } else {
            List<User> users = userService.getAllUsers();
            if (users.size() > 0) {
                log.info("{} users available", users.size());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), users.size() + "users available", users, null);
            }
            log.info("{} users available", users.size());
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "No user available", null, "User not found");


        }
    }
        @GetMapping("/users/{id}")
        public ResponseEntity<Object> getUserById (@PathVariable Long id) {
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                log.info("user with {} id fetched",existingUser.get().getId());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), "User fetch successfully", existingUser, null);
            }
         //   return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "User with id" + id + "not found in our database", null, "User not found");
            throw new UserException("User with id: " + id +" not found in our database.");
        }

/*
    @GetMapping("/users/byId/{id}")
    public User getUser(@PathVariable Long id){
        return userService.getUsers(id).get();
    }

 */

        @PostMapping("/users")
        public ResponseEntity<Object> createUser (@RequestBody @Valid UserRequest userRequest){
            log.info("this is test");
            Optional<User> user = userService.getUserByEmail(userRequest.getEmail());
//            log.info("user is {}", user.get());
            if (user.isPresent()){
                log.info("user with email{} already exits",user.get().getEmail());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), "User already exist with same email "+userRequest.getEmail(),null,null);
            }


            User savedUser = userService.createUser(userRequest);
            log.info("user with email{} created successfully",savedUser.getEmail());
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "User created successfully", savedUser, null);
        }

        /*
        @PutMapping("/users/{id}")
        public User updateUser (@PathVariable Long id, @RequestBody UserRequest userRequest){
            Optional<User> existingUser = userService.getUserById(id);
            if (!existingUser.isPresent()) {

            }
            return userService.updateUser(existingUser.get(), userRequest);
        }

         */



    @PutMapping("/users/{id}")
    public ResponseEntity<Object> updateUser (@PathVariable Long id, @RequestBody @Valid UserRequest userRequest){
        Optional<User> existingUser = userService.getUserById(id);
        if (existingUser.isPresent()) {
            userService.updateUser(existingUser.get(), userRequest);
            log.info("user with id {} updated",existingUser.get().getId());
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "User updated", existingUser.get(), null);
        }
        log.info("user with id{} not found",existingUser.get().getId());
        return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "Sorry User with id " + id + " not found",null, "user not found");
    }


        @DeleteMapping("/users/{id}")
        public ResponseEntity<Object> deleteUser (@PathVariable Long id){
            Optional<User> existingUser = userService.getUserById(id);
            if (existingUser.isPresent()) {
                userService.deleteUser(id);
                log.info("user with id{} deleted",existingUser.get().getId());
                return ApiResponse.generateResponse(HttpStatus.OK.value(), "User deleted successfully with id" + id, null, null);
            }
            log.info("user with id{} doesn't exist",existingUser.get().getId());
            return ApiResponse.generateResponse(HttpStatus.NOT_FOUND.value(), "User with id" + id + "not found in our database", null, "User not found");

        }
        @PostMapping("/users/login")
        public ResponseEntity<Object> userLogin(@RequestBody LoginRequest loginRequest){
            Map<String,Object> res= (Map<String, Object>) userService.userLogin(loginRequest);
            return ApiResponse.generateResponse(HttpStatus.OK.value(), "Login successfull",res,null);
        }

    }







