package com.example.login.Config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.LogoutConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(csrf -> csrf.disable()) // Disable CSRF
                .headers(headers -> headers
                        .frameOptions(frameOptions -> frameOptions.disable()) // Proper way to disable frame options
                )
                .authorizeHttpRequests((authorize) ->
                        authorize.requestMatchers(
                                "/api/auth/register",
                                "/api/auth/verify-email",
                                "/api/auth/login",
                                "/api/auth/reset-password",
                                "/api/auth/reset-password/confirm",
                                "/h2-console/**"
                        ).permitAll()  // Allow access without authentication
                        .anyRequest().authenticated() // All other requests require authentication
                )
                .formLogin(form -> form
                        .loginPage("/api/auth/login") // Custom login page
                        .loginProcessingUrl("/process-login") // Endpoint where credentials are processed
                        .defaultSuccessUrl("/dashboard", true) // Redirect after successful login
                        .failureUrl("/api/auth/login?error=true") // Redirect back to login page on failure
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/api/auth/login?logout=true") // Redirect to login page after logout
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                );


        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}


