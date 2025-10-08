package com.dev.spring_security_basics.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    @PostMapping("/roles/add")
    public ResponseEntity<Void> addRole(@RequestParam String roleName) {
        //roleService.createRole(roleName);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PostMapping("/roles/delete")
    public ResponseEntity<Void> removeRole(@RequestParam String roleName) {
        //roleService.deleteRole(roleName);
        return ResponseEntity.status(HttpStatus.ACCEPTED).build();
    }
}
