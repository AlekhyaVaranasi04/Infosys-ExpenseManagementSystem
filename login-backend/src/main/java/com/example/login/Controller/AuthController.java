package com.example.login.Controller;

import com.example.login.Entity.User;
import com.example.login.Service.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@Valid @RequestBody User user) {
        try {
            userService.registerUser(user);
            return ResponseEntity.ok("User registered successfully. Please verify your email.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/login")
    public ResponseEntity<String> loginUser(@Valid @RequestBody Map<String, String> loginDetails) {
        try {
            String username = loginDetails.get("username");
            String password = loginDetails.get("password");

            boolean isAuthenticated = userService.loginUser(username, password);
            if (isAuthenticated) {
                return ResponseEntity.ok("User logged in successfully.");
            } else {
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid credentials.");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(
            @RequestParam("code") String verificationCode,
            @RequestParam("username") String username) {
        try {
            boolean verified = userService.verifyEmail(verificationCode, username);
            if (verified) {
                return ResponseEntity.ok("Email verified successfully.");
            } else {
                return ResponseEntity.badRequest().body("Invalid verification code or username.");
            }
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPassword(
            @RequestParam("email") String email,
            @RequestParam("token") String token,
            @RequestParam("newPassword") String newPassword) {
        try {
            boolean resetSuccessful = userService.resetPassword(email, token, newPassword);
            if (resetSuccessful) {
                return ResponseEntity.ok("Your password has been successfully changed.");
            }
            return ResponseEntity.badRequest().body("Invalid email, token, or password.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> sendResetPasswordToken(@RequestParam("email") String email) {
        try {
            boolean tokenSent = userService.sendResetPasswordToken(email);
            if (tokenSent) {
                return ResponseEntity.ok("Reset password token has been successfully sent to your email.");
            }
            return ResponseEntity.badRequest().body("Invalid email address.");
        } catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
