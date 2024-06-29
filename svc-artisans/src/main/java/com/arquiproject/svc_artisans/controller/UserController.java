package com.arquiproject.svc_artisans.controller;

import com.arquiproject.svc_artisans.model.User;
import com.arquiproject.svc_artisans.model.Order;
import com.arquiproject.svc_artisans.model.Review;
import com.arquiproject.svc_artisans.views.LoginRequest;
import com.arquiproject.svc_artisans.views.LoginResponse;
import com.arquiproject.svc_artisans.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    // Attributes

    private final UserService userService;

    // Constructor

    public UserController(UserService userService) {
        this.userService = userService;
    }

    // Methods

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/one/{userId}")
    public ResponseEntity<User> getUserById(@PathVariable("userId") int userId) {
        User user = userService.getUserById(userId);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/one/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable("email") String userEmail) {
        User user = userService.findByEmail(userEmail);
        if (user != null) return new ResponseEntity<>(user, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User createdUser = userService.createUser(user);
        if (createdUser != null) return new ResponseEntity<>(createdUser, HttpStatus.CREATED);
        else return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User userDetails) {
        User updatedUser = userService.updateUser(userDetails);
        if (updatedUser != null) return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        else return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable("userId") int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest loginRequest) {
        LoginResponse loginResponse = userService.findByMailAndPassword(loginRequest);
        if(loginResponse != null) {
            return ResponseEntity.ok(loginResponse);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/allOrder/{userId}")
    public ResponseEntity<List<Order>> getOrdersById(@PathVariable int userId){
        List<Order> orders = userService.getAllUserOrders(userId);
        return new ResponseEntity<>(orders, HttpStatus.OK);
    }

    @GetMapping("/allReview/{userId}")
    public ResponseEntity<List<Review>> getReviewsById(@PathVariable int userId){
        List<Review> reviews = userService.getAllUserReviews(userId);
        return new ResponseEntity<>(reviews, HttpStatus.OK);
    }

}
