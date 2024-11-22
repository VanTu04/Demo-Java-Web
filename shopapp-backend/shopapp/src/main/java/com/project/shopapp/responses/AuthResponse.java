package com.project.shopapp.responses;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class AuthResponse {
    private Long id;
    private String fullName;
    private String jwt;
    private String roles;
}
