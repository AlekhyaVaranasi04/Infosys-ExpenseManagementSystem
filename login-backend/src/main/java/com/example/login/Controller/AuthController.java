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
        }
        catch (RuntimeException e) {
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

        }
        catch (UsernameNotFoundException e) {
            // Handle the case where the provided role is not valid
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/verify-email")
    public ResponseEntity<String> verifyEmail(@RequestParam("code") String verificationCode, @RequestParam("username") String username) {
        try {
            if(userService.verifyEmail(verificationCode, username)) {
                return ResponseEntity.ok("Email verified Successfully");
            }
            else {
                return ResponseEntity.badRequest().body("Enter valid credentials");
            }
        }
        catch (UsernameNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password/confirm")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email, @RequestParam("token") String token, @RequestParam("newPassword") String newPassword) {
        try {
            if(userService.resetPassword(email, token, newPassword)) {
                return ResponseEntity.ok("Your password has been successfully changes");
            }

            return ResponseEntity.badRequest().body("Enter valid credentials");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PostMapping("/reset-password")
    public ResponseEntity<String> sendResetPasswordToken(@RequestParam("email") String email) {
        try {
           if(userService.sendResetPasswordToken(email)) {
               return ResponseEntity.ok("Reset password OTP has been successfully sent to your email");
           }

           return ResponseEntity.badRequest().body("Enter valid credentials");
        }
        catch (RuntimeException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
