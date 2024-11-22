package com.project.shopapp.configurations;

import com.project.shopapp.models.Role;
import com.project.shopapp.models.User;
import com.project.shopapp.repositories.RoleRepository;
import com.project.shopapp.repositories.UserRepository;
import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Transactional
@RequiredArgsConstructor
public class AdminAccountInitializer {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;

    @PostConstruct
    public void initAdminAccount() {
        if(!userRepository.existsByPhoneNumber("0123456")){
            Role adminRole = new Role();
            adminRole.setName("ADMIN");

            var role = roleRepository.findByName("ADMIN");
            if (role.isEmpty()) {
                roleRepository.save(adminRole); // Lưu vai trò admin nếu chưa có
            } else {
                adminRole = role.get(); // Lấy vai trò admin nếu đã tồn tại
            }
            User user = User.builder()
                    .fullName("admin")
                    .phoneNumber("0123456")
                    .password(passwordEncoder.encode("0123456"))
                    .active(true)
                    .role(adminRole)
                    .build();
            userRepository.save(user);
            System.out.println("Admin Account created");
        }
    }
}
