package com.technova.shopping_cart.TechNova.Cart.service;

import com.technova.shopping_cart.TechNova.Cart.dto.LoginRequest;
import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.repository.UserRepository;
import com.technova.shopping_cart.TechNova.Cart.utils.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
public class UserService {
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<User> getAllUser;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private CartUserDetailsService cartUserDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

//      public UserService(UserRepository userRepository) {
//      this.userRepository =userRepository;
//      }
    public User createUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastname(userRequest.getLastName());
        newUser.setPassword(encodePassword(userRequest.getPassword()));
        return userRepository.save(newUser);
    }
    public String encodePassword(String Password){
        return passwordEncoder.encode(Password);
    }
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

  //  public Optional<User> getUsers(Long id){
  //      return userRepository.findById(id);
   // }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public User updateUser(User user, UserRequest userRequest) {

        user.setLastname(userRequest.getLastName());
        user.setFirstName(userRequest.getFirstName());
        user.setEmail(userRequest.getEmail());
        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Map<String,Object> userLogin(LoginRequest loginRequest) {
        Map<String,Object> tokenMap= new HashMap<>();
        try{
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getEmail(),loginRequest.getPassword()));
        }catch (AuthenticationException e) {

            throw new BadCredentialsException("Invalid credentials");
        }
        UserDetails userDetails= cartUserDetailsService.loadUserByUsername(loginRequest.getEmail());
        String  token= jwtUtils.generateToken(userDetails);
        tokenMap.put("jwt",token);
        tokenMap.put("user",userDetails.getUsername());
        tokenMap.put("roles",userDetails.getAuthorities());

        log.info("Generated token is: {}",token);
        return tokenMap;
    }
}
