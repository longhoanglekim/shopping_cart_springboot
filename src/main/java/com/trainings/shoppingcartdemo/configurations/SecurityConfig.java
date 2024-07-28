package com.trainings.shoppingcartdemo.configurations;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.jaas.memory.InMemoryConfiguration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@EnableWebSecurity
@Configuration
public class SecurityConfig {
    @Bean
    public InMemoryUserDetailsManager createUserDetails() {
        return new InMemoryUserDetailsManager(
                org.springframework.security.core.userdetails.User.withDefaultPasswordEncoder()
                        .username("hlklonga5")
                        .password("12345678")
                        .roles("USER")
                        .build()
        );
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)// Disable CSRF protection
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/login","/welcome", "/logout-confirm").permitAll() // Allow access to login and welcome-user pages without authentication
                        .requestMatchers("/addProduct", "/updateProduct").authenticated() // Secure specific servlets
                        .anyRequest().permitAll() // Allow access to all other URLs
                )
                .formLogin(form -> form
                        .loginPage("/login")
                        .defaultSuccessUrl("/welcome", true) // Redirect to welcome-user page after successful login
                        .permitAll()
                )
                .logout(logout ->
                        logout
                                .logoutUrl("/perform-logout") // URL để xử lý yêu cầu đăng xuất
                                .logoutSuccessUrl("/logout-success") // URL để chuyển hướng sau khi đăng xuất thành công
                                .invalidateHttpSession(true)
                                .deleteCookies("JSESSIONID")
                                .permitAll()
                )
                .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}