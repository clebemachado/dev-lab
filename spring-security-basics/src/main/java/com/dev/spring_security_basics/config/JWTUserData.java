package com.dev.spring_security_basics.config;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String email
) {
}
