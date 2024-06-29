package com.arquiproject.svc_artisans.views;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class LoginResponse {
    private String email;
    private String password;
}
