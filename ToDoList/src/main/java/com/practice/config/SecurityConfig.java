package com.practice.config;

import com.practice.entites.Message;
import com.practice.service.CustomUserDetailsService;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

    private final CustomUserDetailsService userDetailsService;

    public SecurityConfig(CustomUserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(
                        auth -> auth
                                .requestMatchers("/", "/signUp", "/register", "/css/**", "/js/**", "/images/**",
                                        "/home", "/profiles/**")
                                .permitAll()
                                .requestMatchers("/admin/**").hasAuthority("ROLE_ADMIN")
                                .anyRequest().authenticated()
                )
                .formLogin(form -> form
                        .loginPage("/goTologInPage")
                        .loginProcessingUrl("/login")
                        .successHandler((request, response, authentication) -> {
                            authentication.getAuthorities().forEach(authority -> {
                                try {
                                    if (authority.getAuthority().equals("ROLE_ADMIN")) {
                                        response.sendRedirect("/admin/admin_dashboard");
                                    } else {
                                        response.sendRedirect("/user/user_dashboard");
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        })
                        .failureHandler((request, response, exception) -> {
                            String errorMessage;
                            String messageType = "alert";
                            
                            // Unwrap the actual exception
                            if (exception instanceof InternalAuthenticationServiceException && 
                                exception.getCause() instanceof DisabledException) {
                                errorMessage = "Your account is inactive. Please contact the admin.";
                            } else {
                                errorMessage = "Invalid email or password!";
                            }
                            
                            // Store in session
                            request.getSession().setAttribute("message", new Message(errorMessage, messageType));
                            response.sendRedirect("/goTologInPage");
                        })
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/goTologInPage?logout=true")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll()
                );

        return http.build();
    }

    // ... rest of your configuration

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration)
			throws Exception {
		return authenticationConfiguration.getAuthenticationManager();
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
}
