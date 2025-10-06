package com.dev.spring_security_basics.dto.response;

public record RegisterUserResponse(
        String name,
        String email
) {
}
