package com.technova.shopping_cart.TechNova.Cart.service;

import com.technova.shopping_cart.TechNova.Cart.dto.UserRequest;
import com.technova.shopping_cart.TechNova.Cart.model.User;
import com.technova.shopping_cart.TechNova.Cart.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {


    public List<User> getAllUser;
    @Autowired
    private UserRepository userRepository;

//      public UserService(UserRepository userRepository) {
//      this.userRepository =userRepository;
//      }
    public User createUser(UserRequest userRequest) {
        User newUser = new User();
        newUser.setEmail(userRequest.getEmail());
        newUser.setFirstName(userRequest.getFirstName());
        newUser.setLastname(userRequest.getLastName());
        return userRepository.save(newUser);
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
}
