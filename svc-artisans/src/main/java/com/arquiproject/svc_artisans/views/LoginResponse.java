package com.arquiproject.svc_artisans.views;

import com.arquiproject.svc_artisans.model.enums.UserType;
import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private Long userId;
    private String email;
    private String fullName;
    private UserType userType;
}
