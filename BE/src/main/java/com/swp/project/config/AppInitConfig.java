package com.swp.project.config;

import com.swp.project.entity.Role;
import com.swp.project.entity.User;
import com.swp.project.repository.RoleRepository;
import com.swp.project.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@Slf4j
public class AppInitConfig {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Bean
    ApplicationRunner init() {
        return args -> {
            if(roleRepository.findAll().isEmpty()){
            Role roleAdmin = new Role();
            roleAdmin.setName("ROLE_ADMIN");
            roleRepository.save(roleAdmin);
            Role roleUser = new Role();
            roleUser.setName("ROLE_USER");
            roleRepository.save(roleUser);
            Role roleDoctor = new Role();
            roleDoctor.setName("ROLE_DOCTOR");
            roleRepository.save(roleDoctor);
            log.info("Roles initialized.");
            }
            if (userRepository.findByEmail("babytrackingsys@gmail.com").isEmpty()) {
                User admin = new User();
                admin.setEmail("babytrackingsys@gmail.com");
                admin.setPassword(passwordEncoder.encode("123456")); // thay đổi mật khẩu theo nhu cầu
                admin.setFirstName("Admin");
                admin.setLastName("Admin");
                admin.setActive(true);

                Role adminRole = roleRepository.findByName("ROLE_ADMIN")
                        .orElseThrow(() -> new RuntimeException("Error: Role ROLE_ADMIN not exist"));
                admin.setRole(adminRole);

                userRepository.save(admin);
                log.info("Admin account initialized.");

            }
        };
    }
}
