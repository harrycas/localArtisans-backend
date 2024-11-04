package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.views.LoginRequest;
import com.arquiproject.svc_artisans.views.LoginResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

public interface IUserService {
    Optional<User> getUserByEmail(String email);
    User createUser(User user);
    User updateUser(User user);
    boolean deleteUser(Long id);
    LoginResponse login(LoginRequest loginRequest);
    void uploadProfileImage(Long userId, MultipartFile file) throws IOException;
    byte[] getProfileImage(Long userId) throws IOException;
    List<User> getAllUsers();
    List<Order> getAllUserOrders(Long userId);
    List<Review> getAllUserReviews(Long userId);

}
