package com.dev.spring_security_basics.config;

import lombok.Builder;

import java.util.List;

@Builder
public record JWTUserData(
        Long userId,
        String email,
        List<String> roles,
        List<String> operations
) {
}
