package com.swp.project.controller;

import com.swp.project.entity.Role;
import com.swp.project.entity.User;
import com.swp.project.repository.RoleRepository;
import com.swp.project.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/api/admin")
public class DoctorController {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @PatchMapping("/users/{userId}/role-doctor")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<User> changeRoleToDoctor(@PathVariable int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not available"));
        Role doctorRole = roleRepository.findByName("ROLE_DOCTOR")
                .orElseThrow(() -> new RuntimeException("ROLE_DOCTOR available"));
        user.setRole(doctorRole);
        userRepository.save(user);
        return ResponseEntity.ok(user);
    }
}
