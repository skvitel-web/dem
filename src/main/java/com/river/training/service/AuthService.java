package com.river.training.service;

import com.river.training.dto.LoginRequest;
import com.river.training.dto.RegisterRequest;
import com.river.training.dto.UserResponse;
import com.river.training.entity.User;
import com.river.training.exception.ApiException;
import com.river.training.repository.UserRepository;
import com.river.training.security.UserPrincipal;
import com.river.training.util.DateParser;
import java.time.OffsetDateTime;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.HttpSessionSecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthService(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            AuthenticationManager authenticationManager) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.authenticationManager = authenticationManager;
    }

    @Transactional
    public UserResponse register(RegisterRequest request) {
        String email = request.email().trim().toLowerCase();
        if (userRepository.existsByEmailIgnoreCase(email)) {
            throw new ApiException(HttpStatus.CONFLICT, "Пользователь с таким e-mail уже зарегистрирован");
        }

        String username = deriveUsername(email);
        while (userRepository.existsByUsernameIgnoreCase(username)) {
            username = username + "_" + System.currentTimeMillis() % 10000;
        }

        String[] nameParts = splitFullName(request.fullName().trim());

        User user = new User();
        user.setFullName(request.fullName().trim());
        user.setFirstName(nameParts[0]);
        user.setLastName(nameParts[1]);
        user.setUsername(username);
        user.setEmail(email);
        user.setPhone(request.phone().trim());
        user.setBirthDate(DateParser.parseDdMmYyyy(request.birthDate()));
        user.setPassword(passwordEncoder.encode(request.password()));
        user.setRole("USER");
        user.setActive(true);
        user.setStaff(false);
        user.setSuperuser(false);
        user.setDateJoined(OffsetDateTime.now());

        return UserResponse.from(userRepository.save(user));
    }

    public UserResponse login(LoginRequest request, HttpServletRequest httpRequest) {
        String login = request.email().trim();
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(login, request.password()));

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        context.setAuthentication(authentication);
        SecurityContextHolder.setContext(context);

        HttpSession session = httpRequest.getSession(true);
        session.setAttribute(
                HttpSessionSecurityContextRepository.SPRING_SECURITY_CONTEXT_KEY,
                context);

        UserPrincipal principal = (UserPrincipal) authentication.getPrincipal();
        User user = principal.getUser();
        user.setLastLogin(OffsetDateTime.now());
        userRepository.save(user);

        return UserResponse.from(user);
    }

    public void logout(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session != null) {
            session.invalidate();
        }
        SecurityContextHolder.clearContext();
    }

    public UserResponse currentUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || !auth.isAuthenticated() || !(auth.getPrincipal() instanceof UserPrincipal principal)) {
            throw new ApiException(HttpStatus.UNAUTHORIZED, "Требуется авторизация");
        }
        return UserResponse.from(principal.getUser());
    }

    private static String deriveUsername(String email) {
        int at = email.indexOf('@');
        String base = at > 0 ? email.substring(0, at) : email;
        return base.replaceAll("[^a-zA-Z0-9._-]", "_");
    }

    private static String[] splitFullName(String fullName) {
        String[] parts = fullName.trim().split("\\s+", 2);
        if (parts.length == 1) {
            return new String[] { parts[0], "" };
        }
        return new String[] { parts[0], parts[1] };
    }
}
