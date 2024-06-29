package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.views.LoginRequest;
import com.arquiproject.svc_artisans.views.LoginResponse;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();
    User getUserById(int id);
    User findByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(int id);
//    List<Product> getAllUserProducts(int userId);
    LoginResponse findByMailAndPassword(LoginRequest loginRequest);
    List<Order> getAllUserOrders(int userId);
    List<Review> getAllUserReviews(int userId);

}
