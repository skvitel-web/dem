package com.river.training.config;

import com.river.training.entity.User;
import com.river.training.repository.UserRepository;
import java.time.LocalDate;
import java.time.OffsetDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * Создаёт или обновляет администратора Admin26 / Demo20 при старте приложения.
 */
@Component
public class AdminBootstrap implements ApplicationRunner {

    private static final Logger log = LoggerFactory.getLogger(AdminBootstrap.class);
    private static final String USERNAME = "Admin26";
    private static final String PASSWORD = "Demo20";

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public AdminBootstrap(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void run(ApplicationArguments args) {
        User admin = userRepository.findByUsernameIgnoreCase(USERNAME)
                .or(() -> userRepository.findByEmailIgnoreCase("admin26@training.local"))
                .orElseGet(this::newAdmin);

        admin.setUsername(USERNAME);
        admin.setPassword(passwordEncoder.encode(PASSWORD));
        admin.setRole("ADMIN");
        admin.setStaff(true);
        admin.setSuperuser(true);
        admin.setActive(true);
        admin.setFullName("Администратор системы");
        admin.setFirstName("Администратор");
        admin.setLastName("системы");
        if (admin.getEmail() == null || admin.getEmail().isBlank()) {
            admin.setEmail("admin26@training.local");
        }
        if (admin.getPhone() == null || admin.getPhone().isBlank()) {
            admin.setPhone("+79000000026");
        }
        if (admin.getBirthDate() == null) {
            admin.setBirthDate(LocalDate.of(1980, 1, 26));
        }
        if (admin.getDateJoined() == null) {
            admin.setDateJoined(OffsetDateTime.now());
        }

        userRepository.save(admin);
        log.info("Администратор {} готов (роль ADMIN, пароль Demo20)", USERNAME);
    }

    private User newAdmin() {
        User user = new User();
        user.setEmail("admin26@training.local");
        user.setPhone("+79000000026");
        user.setBirthDate(LocalDate.of(1980, 1, 26));
        user.setDateJoined(OffsetDateTime.now());
        return user;
    }
}
