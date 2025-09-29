package com.example.bookweb_management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private JwtFilter jwtFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        return http
                .csrf(customizer -> customizer.disable())
                .authorizeHttpRequests(request -> request
                        .requestMatchers("/api/users/all",
                                "/api/users/get/*",
                                "/api/users/search").hasAnyRole("ADMIN", "MANAGER")
                        .requestMatchers(
                                "/api/users/register",
                                "/api/users/login",
                                "/api/*/all",
                                "/api/*/get/*",
                                "/api/*/search").permitAll()
                        //hasAuthority() để check chính xác chuỗi được đặt trong UserPrincipal vd: ROLE_USER, còn hasRole() là chỉ check sau prefix là ROLE_
                        .requestMatchers("/api/**").hasRole("ADMIN")
                        .requestMatchers("/api/books/**",
                                "/api/categories/**",
                                "/api/comments/**",
                                "/api/posts/**",
                                "/api/users/**").hasRole("MANAGER")
                        .requestMatchers("/api/books/booktest").hasRole("USER")
                        .requestMatchers("/api/**").permitAll()
                        .anyRequest().authenticated())
                //.formLogin(Customizer.withDefaults()) // Dùng cho Test web app
                .httpBasic(Customizer.withDefaults()) // Dùng cho test REST API
                .sessionManagement(session
                        -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public AuthenticationProvider authenticationProvider()
    {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(new BCryptPasswordEncoder(12));
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
        return config.getAuthenticationManager();
    }
}
