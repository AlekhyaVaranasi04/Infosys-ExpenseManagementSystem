package com.example.login.Service;

import com.example.login.Entity.User;
import com.example.login.Repository.UserRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private EmailService emailService;

    private static final String NUMBERS = "0123456789";
    private static final SecureRandom random = new SecureRandom();

    @Override
    @Transactional
    public String registerUser(User user) {
        if (userRepository.findByUsername(user.getUsername()).isPresent()) {
            throw new RuntimeException("Username already exists");
        }

        if (userRepository.findByEmail(user.getEmail()).isPresent()) {
            throw new RuntimeException("Email already exists");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword())); // Encrypt the password
        String verificationCode = generateOTP();
        user.setVerificationCode(verificationCode);
        userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "Email Verification",
                "Your OTP is: " + verificationCode + "\nEnter the OTP in the application to verify your email."
        );

        return "Registration successful! Please check your email to verify your account.";
    }

    @Override
    public boolean loginUser(@Valid String username, String password) {
        User existingUser = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!existingUser.isEnabled()) {
            throw new RuntimeException("Verify your email before logging in");
        }

        if (passwordEncoder.matches(password, existingUser.getPassword())) {
            return true;
        } else {
            throw new RuntimeException("Invalid password");
        }
    }

    @Override
    public boolean verifyEmail(String verificationCode, String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("Username not found"));

        if (!verificationCode.equals(user.getVerificationCode())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setEnabled(true);
        user.setVerificationCode(null);
        userRepository.save(user);
        return true;
    }

    @Override
    public boolean sendResetPasswordToken(String email) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Invalid email"));

        String resetPasswordToken = generateOTP();
        user.setResetPasswordToken(resetPasswordToken);
        userRepository.save(user);

        emailService.sendEmail(
                user.getEmail(),
                "Password Reset Request",
                "Your OTP for password reset is: " + resetPasswordToken
        );

        return true;
    }

    @Override
    public boolean resetPassword(String email, String token, String newPassword) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Email not found"));

        if (!token.equals(user.getResetPasswordToken())) {
            throw new RuntimeException("Invalid OTP");
        }

        user.setPassword(passwordEncoder.encode(newPassword));
        user.setResetPasswordToken(null); // Clear the token after successful reset
        userRepository.save(user);
        return true;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
        return new org.springframework.security.core.userdetails.User(
                user.getUsername(),
                user.getPassword(),
                new ArrayList<>()
        );
    }

    public String generateOTP() {
        StringBuilder otp = new StringBuilder(4);

        for (int i = 0; i < 4; i++) {
            otp.append(NUMBERS.charAt(random.nextInt(NUMBERS.length())));
        }

        return otp.toString();
    }
}
