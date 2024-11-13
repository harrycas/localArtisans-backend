package com.arquiproject.svc_artisans.service;

import com.arquiproject.svc_artisans.client.CatalogClientRest;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.repository.OrderRepository;
import com.arquiproject.svc_artisans.views.LoginRequest;
import com.arquiproject.svc_artisans.views.LoginResponse;
import org.springframework.stereotype.Service;
import com.arquiproject.svc_artisans.repository.UserRepository;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    final private UserRepository userRepository;
    private final CatalogClientRest clientRest;
    private static final String UPLOAD_DIR = "svc-artisans/uploads/";
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, CatalogClientRest clientRest, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.clientRest = clientRest;
        this.orderRepository = orderRepository;
    }

    public Optional<User> getUserById(Long id) { return userRepository.findById(id); }

    @Override
    public Optional<User> getUserByEmail(String email) { return userRepository.findByEmail(email);}

    @Override
    public User createUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        User userFound = userRepository.findById(user.getId()).orElse(null);
        if (userFound != null) return userRepository.save(user);
        else return null;
    }

    @Override
    public boolean deleteUser(Long id) {
        boolean deleted = false;
        try {
            userRepository.deleteById(id);
            clientRest.updateProductsToInactive(id);
            deleted = true;
        } catch (Exception e) {
            System.out.println("Error when deleting user: " + id + ": " + e.getMessage());
        }
        return deleted;
    }

    public LoginResponse login(LoginRequest loginRequest) {
        User user = userRepository.findByEmailAndPassword(loginRequest.getEmail(), loginRequest.getPassword());
        if(user != null){
            return new LoginResponse(
                user.getId(),
                user.getEmail(),
                user.getFullName(),
                user.getUserType()
            );
        } else {
            return null;
        }
    }

    public void uploadProfileImage(Long userId, MultipartFile file) throws IOException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        // Verify if there is an existing profile Image for the user
        if (user.getProfileImage() != null) {
            File existingImage = new File(UPLOAD_DIR + File.separator + user.getProfileImage());
            if (existingImage.exists()) {
                boolean deleted = existingImage.delete();
                if (!deleted) {
                    System.err.println("Cannot delete previous image: " + existingImage.getPath());
                } else {
                    System.out.println("Previous image deleted: " + existingImage.getPath());
                }
            }
        }

        // Save Image file in the directory uploads
        String fileName = userId + "_" + file.getOriginalFilename();
        Path filePath = Paths.get(UPLOAD_DIR + File.separator + fileName);
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Update profile image
        user.setProfileImage(fileName);
        userRepository.save(user);
    }

    public byte[] getProfileImage(Long userId) throws IOException {
        User user = userRepository.findById(userId)
            .orElseThrow(() -> new IllegalArgumentException("User not found"));

        String profileImage = user.getProfileImage();
        if (profileImage == null) {
            throw new IllegalArgumentException("No profile image found");
        }

        Path filePath = Paths.get(UPLOAD_DIR + profileImage);
        return Files.readAllBytes(filePath);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<Order> getAllUserOrders(Long userId) {return userRepository.findAllUserOrders(userId);}

    @Override
    public List<Review> getAllUserReviews(Long userId) {return userRepository.findAllUserReviews(userId);}

}
