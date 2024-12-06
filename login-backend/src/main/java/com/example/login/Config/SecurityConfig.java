package com.example.login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF for APIs (consider enabling for non-API endpoints)
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Allow H2 console in dev mode
                )
                .authorizeHttpRequests(authorize -> authorize
                        // Publicly accessible endpoints
                        .requestMatchers(
                                "/register",
                                "/login",
                                "/forgot-password",
                                "/reset-password",
                                "/api/auth/register",
                                "/api/auth/verify-email",
                                "/api/auth/login",
                                "/api/auth/reset-password",
                                "/api/auth/reset-password/confirm",
                                "/h2-console/**"
                        ).permitAll()

                        // Restricted endpoints for authenticated users only
                        .requestMatchers(
                                "/expenses/**",  // All expense-related operations
                                "/categories/**" // All category-related operations
                        ).hasRole("USER") // Allow only 'USER' role

                        .anyRequest().authenticated() // All other endpoints require authentication
                )
                .formLogin(form -> form
                        .loginPage("/login") // Custom login page
                        .loginProcessingUrl("/process-login") // Endpoint where login form is submitted
                        .defaultSuccessUrl("/dashboard", true) // Redirect on successful login
                        .failureUrl("/login?error=true") // Redirect on login failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout=true") // Redirect to login page after logout
                        .invalidateHttpSession(true) // Clear session
                        .deleteCookies("JSESSIONID") // Remove authentication cookies
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Securely encrypt passwords
    }
}
