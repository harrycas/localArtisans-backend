package com.arquiproject.svc_artisans.model;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private int userId;

    @Column(name = "full_name")
    private String fullName;

    private String dni;

    @Column(unique = true)
    private String email;

    private String phone;

    // Not actually a code, should be a certificate (sth like that)
    private String artisanCode;

    @Column(name = "user_type")
    private String userType;

    @Column(name = "profile_image")
    private String profileImage;

    private String password;

}
