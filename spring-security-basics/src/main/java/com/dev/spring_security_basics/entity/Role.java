package com.dev.spring_security_basics.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Entity
@Getter@Setter
public class Role implements GrantedAuthority {
    @Id
    private String id;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "role_operations",
            joinColumns = @JoinColumn(name = "role_id"),
            inverseJoinColumns = @JoinColumn(name = "operation_id")
    )
    private final List<Operation> allowedOperations = new ArrayList<>();

    @Override
    public String getAuthority() {
        return id;
    }

    public Collection<? extends GrantedAuthority> getAllowedOperations () {
        return allowedOperations;
    }
}
