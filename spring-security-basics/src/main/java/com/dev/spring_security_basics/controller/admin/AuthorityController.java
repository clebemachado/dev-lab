package com.dev.spring_security_basics.controller.admin;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AuthorityController {

    // ---------- ROLES ----------

    @PostMapping("/roles")
    public ResponseEntity<Void> addRole(@RequestParam String roleName) {
        // roleService.createRole(roleName);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @DeleteMapping("/roles/{roleName}")
    public ResponseEntity<Void> removeRole(@PathVariable String roleName) {
        // roleService.deleteRole(roleName);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ---------- OPERATIONS ----------

    @PostMapping("/operations")
    public ResponseEntity<Void> addOperation(@RequestParam String operationName) {
        // roleService.createOperation(operationName);
        return ResponseEntity.status(201).build(); // 201 Created
    }

    @DeleteMapping("/operations/{operationName}")
    public ResponseEntity<Void> removeOperation(@PathVariable String operationName) {
        // roleService.deleteOperation(operationName);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ---------- ASSOCIAR ROLES COM OPERATIONS ----------

    @PatchMapping("/roles/{roleName}/operations/{operationName}")
    public ResponseEntity<Void> associateRoleWithOperation(
            @PathVariable String roleName,
            @PathVariable String operationName
    ) {
        // roleService.associateRoleWithOperation(roleName, operationName);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    // ---------- ASSOCIAR / DISSOCIAR ROLES COM USERS ----------

    @PatchMapping("/roles/{roleId}/users/{userId}")
    public ResponseEntity<Void> associateRoleWithUser(
            @PathVariable("roleId") String roleId,
            @PathVariable("userId") Integer userId
    ) {
        // roleService.associateRoleWithUser(roleId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }

    @DeleteMapping("/roles/{roleId}/users/{userId}")
    public ResponseEntity<Void> dissociateRoleWithUser(
            @PathVariable("roleId") String roleId,
            @PathVariable("userId") Integer userId
    ) {
        // roleService.dissociateRoleWithUser(roleId, userId);
        return ResponseEntity.noContent().build(); // 204 No Content
    }
}
