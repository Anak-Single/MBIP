package com.internetprogramming.mbip.Security;

import java.util.Collection;
import java.io.IOException;
import java.util.Collection;
import java.util.Set;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.web.SecurityFilterChain;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests(authz -> authz
                .requestMatchers("/Admin/**").hasAuthority("ADMIN")
                .requestMatchers("/","/registerform", "/register", "/images/**", "/css/**").permitAll()
                .anyRequest().authenticated()
            )
            .formLogin(form -> form
                .loginPage("/")
                .loginProcessingUrl("/process-login")
                .failureUrl("/?error=true")
                .permitAll()
                .successHandler((request, response, authentication) -> {
                    // Get authorities (roles) of the authenticated user
                    Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                    
                    // Check if the user has the "ADMIN" role
                    if (authorities.stream().anyMatch(a -> a.getAuthority().equals("ADMIN"))) {
                        response.sendRedirect("/Admin/dashboard");
                    } else {
                        response.sendRedirect("/utama");
                    }
                })
            )
            .logout(logout -> logout
                    .logoutSuccessUrl("/?logout=true")
                    .invalidateHttpSession(true)
                    .permitAll()
            )
            .exceptionHandling(exceptionHandling -> exceptionHandling
                .accessDeniedPage("/access-denied")
            );
        
            return http.build();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/ignore1", "/ignore2");
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private void handleSuccessfulLogin(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        Set<String> roles = AuthorityUtils.authorityListToSet(authorities);

        if (roles.contains("ADMIN")) {
            response.sendRedirect("/Admin/dashboard"); 
        } else {
            response.sendRedirect("/utama"); 
        }
    }

    
}

