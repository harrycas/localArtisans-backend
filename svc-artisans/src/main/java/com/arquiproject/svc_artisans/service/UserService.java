package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Product;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.views.LoginRequest;
import com.arquiproject.svc_artisans.views.LoginResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.arquiproject.svc_artisans.repository.UserRepository;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    // Attributes
    final private UserRepository userRepository;

    @Autowired
    private CatalogClientRest clientRest;

    // Constructor
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    // Methods
    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUserById(int id) {
        return userRepository.findById(id).orElse(null);
    }

    @Override
    public User findByEmail(String email){ return userRepository.findByEmail(email);}

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User userFound = userRepository.findById(user.getUserId()).orElse(null);
        if (userFound != null) return userRepository.save(user);
        else return null;
    }

    @Override
    public boolean deleteUser(int id) {
        boolean deleted = false;
        try {
            userRepository.deleteById(id);
            clientRest.updateProductsToInactive(id);
            deleted = true;
        } catch (Exception e) {
            System.out.println("No se pudo eliminar al usuario: " + id + ": " + e.getMessage());
        }
        return deleted;
    }

    @Override
    public LoginResponse findByMailAndPassword(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if(user != null){
            return new LoginResponse(user.getEmail(), user.getPassword());
        } else {
            return null;
        }
    }

    @Override
    public List<Order> getAllUserOrders(int userId) {return userRepository.findAllUserOrders(userId);}

    @Override
    public List<Review> getAllUserReviews(int userId) {return userRepository.findAllUserReviews(userId);}

}
