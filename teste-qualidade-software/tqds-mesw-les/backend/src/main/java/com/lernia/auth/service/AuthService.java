package com.lernia.auth.service;

import com.lernia.auth.dto.request.*;
import com.lernia.auth.dto.response.LoginResponse;
import com.lernia.auth.dto.response.PasswordResetTokenResponse;
import com.lernia.auth.dto.response.RegisterResponse;
import com.lernia.auth.dto.response.UserProfileResponse;
import com.lernia.auth.entity.PasswordResetTokenEntity;
import com.lernia.auth.entity.UserEntity;
import com.lernia.auth.entity.enums.AuthProvider;
import com.lernia.auth.entity.enums.Gender;
import com.lernia.auth.entity.enums.UserRole;
import com.lernia.auth.repository.UserRepository;
import com.lernia.auth.service.PasswordResetTokenService.GeneratedToken;
import jakarta.persistence.EntityNotFoundException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.context.SecurityContextRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final SecurityContextRepository securityContextRepository;
    private final PasswordResetTokenService passwordResetTokenService;
    private final EmailService emailService;

    @Value("${app.frontend.url}")
    private String frontendUrl;

    @Transactional
    public RegisterResponse register(RegisterRequest req) {
        if (userRepository.existsByUsername(req.getUsername())) {
            return new RegisterResponse("Username already taken", "error");
        }
        if (req.getEmail() != null && userRepository.existsByEmail(req.getEmail())) {
            return new RegisterResponse("Email already registered", "error");
        }

        UserEntity user = new UserEntity();
        user.setUsername(req.getUsername());
        user.setName(req.getName());
        user.setEmail(req.getEmail());
        user.setPassword(passwordEncoder.encode(req.getPassword()));
        user.setProvider(AuthProvider.LOCAL);
        user.setGender(Gender.OTHER);
        user.setUserRole(UserRole.REGULAR);
        user.setCreationDate(LocalDate.now());

        UserEntity savedUser = userRepository.save(user);
        return new RegisterResponse("User registered", "success", savedUser.getId());
    }

    public LoginResponse login(LoginRequest req, HttpServletRequest request, HttpServletResponse response) {
        String identifier = req.getText();
        UserEntity user = userRepository.findByUsernameOrEmail(identifier, identifier)
                .orElse(null);

        if (user == null || user.getPassword() == null || !passwordEncoder.matches(req.getPassword(), user.getPassword())) {
            return new LoginResponse("Invalid credentials", "error");
        }

        SecurityContext context = SecurityContextHolder.createEmptyContext();
        UserRole role = user.getUserRole() != null ? user.getUserRole() : UserRole.REGULAR;

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                null,
                List.of(new SimpleGrantedAuthority("ROLE_" + role.name())));

        context.setAuthentication(authToken);
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);

        LoginResponse res = new LoginResponse("Login successful", "success");
        res.setUser(mapToProfile(user));
        return res;
    }

    public void logout(HttpServletRequest request, HttpServletResponse response) {
        SecurityContext context = SecurityContextHolder.createEmptyContext();
        SecurityContextHolder.setContext(context);
        securityContextRepository.saveContext(context, request, response);
    }

    @Transactional
    public void deleteAccount(Long id) {
        if (!userRepository.existsById(id)) {
            throw new EntityNotFoundException("User not found with id: " + id);
        }
        userRepository.deleteById(id);
    }

    @Transactional
    public void changePassword(Long userId, ChangePasswordRequest req) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (req.getCurrentPassword() == null || req.getCurrentPassword().isBlank() ||
                req.getNewPassword() == null || req.getNewPassword().isBlank()) {
            throw new IllegalArgumentException("Passwords cannot be empty");
        }

        if (req.getCurrentPassword().equals(req.getNewPassword())) {
            throw new IllegalArgumentException("New password cannot be the same as current password");
        }

        if (user.getPassword() == null || !passwordEncoder.matches(req.getCurrentPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Incorrect current password");
        }

        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
    }

    public PasswordResetTokenResponse requestPasswordReset(ForgotPasswordRequest req) {
        String message = "If an account exists for that email, we have sent reset instructions.";

        userRepository.findByEmail(req.getEmail()).ifPresent(user -> {
            GeneratedToken generated = passwordResetTokenService.createToken(user);
            String resetLink = frontendUrl + "/reset-password?token=" + generated.token();
            emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
        });

        return new PasswordResetTokenResponse(message);
    }

    @Transactional
    public void resetPassword(ResetPasswordRequest req) {
        PasswordResetTokenEntity token = passwordResetTokenService.validate(req.getToken())
                .orElseThrow(() -> new IllegalArgumentException("Invalid or expired token"));
        UserEntity user = token.getUser();
        user.setPassword(passwordEncoder.encode(req.getNewPassword()));
        userRepository.save(user);
        passwordResetTokenService.consume(token);
    }

    public void adminResetPassword(Long userId) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found with id: " + userId));

        if (user.getEmail() == null || user.getEmail().isBlank()) {
            throw new IllegalStateException("User does not have an email address");
        }

        GeneratedToken generated = passwordResetTokenService.createToken(user);
        String resetLink = frontendUrl + "/reset-password?token=" + generated.token();
        emailService.sendPasswordResetEmail(user.getEmail(), resetLink);
    }

    private UserProfileResponse mapToProfile(UserEntity u) {
        UserProfileResponse r = new UserProfileResponse();
        r.setId(u.getId());
        r.setUsername(u.getUsername());
        r.setName(u.getName());
        r.setEmail(u.getEmail());
        r.setAge(u.getAge());
        r.setGender(u.getGender());
        r.setLocation(u.getLocation());
        r.setJobTitle(u.getJobTitle());
        r.setUserRole(u.getUserRole() != null ? u.getUserRole().name() : null);
        r.setProvider(u.getProvider() != null ? u.getProvider().name() : null);
        r.setPremium(u.getUserRole() == UserRole.PREMIUM);
        return r;
    }
}