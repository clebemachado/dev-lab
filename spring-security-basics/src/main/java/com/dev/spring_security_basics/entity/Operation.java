package com.dev.spring_security_basics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

@Entity
@Getter@Setter
public class Operation implements GrantedAuthority {

    @Id
    private String id;

    @Override
    public String getAuthority() {
        return id;
    }
}
