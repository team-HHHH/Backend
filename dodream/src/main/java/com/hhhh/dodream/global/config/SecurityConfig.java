package com.hhhh.dodream.global.config;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.hhhh.dodream.global.common.service.RedisService;
import com.hhhh.dodream.global.security.CustomAuthenticationEntryPoint;
import com.hhhh.dodream.global.security.ExceptionHandlingFilter;
import com.hhhh.dodream.global.security.JWTFilter;
import com.hhhh.dodream.global.security.JWTUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;

import java.util.Collections;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {
    private final JWTUtil jwtUtil;
    private final RedisService redisService;
    private final ObjectMapper objectMapper;

    @Bean
    SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .cors(corsCustomizer -> corsCustomizer.configurationSource(this.corsCustomizer()));

        http
                .csrf(AbstractHttpConfigurer::disable);

        http
                .formLogin(AbstractHttpConfigurer::disable);

        http
                .httpBasic(AbstractHttpConfigurer::disable);

        http
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS));

        http
                .exceptionHandling(e -> e.authenticationEntryPoint(new CustomAuthenticationEntryPoint(objectMapper)));

        http
                .authorizeHttpRequests((auth) -> auth
                        .requestMatchers("/users/login/**").permitAll()
                        .requestMatchers("/users/reissue").permitAll()
                        .requestMatchers("/users/logout").permitAll()
                        .requestMatchers(HttpMethod.POST, "/users/check/**").permitAll()
                        .requestMatchers("/users/register").permitAll()
                        .requestMatchers("/posters/location/**").permitAll()
                        .anyRequest().authenticated());

        http
                .addFilterBefore(new JWTFilter(jwtUtil, redisService), UsernamePasswordAuthenticationFilter.class)
                .addFilterBefore(new ExceptionHandlingFilter(objectMapper), JWTFilter.class);

        return http.build();
    }

    private CorsConfigurationSource corsCustomizer() {
        return request -> {
            CorsConfiguration configuration = new CorsConfiguration();
            configuration.setAllowedOrigins(Collections.singletonList("http://localhost:3000"));
            configuration.setAllowedMethods(Collections.singletonList("*"));
            configuration.setAllowedHeaders(Collections.singletonList("*"));
            configuration.setExposedHeaders(Collections.singletonList("Authorization"));
            configuration.setExposedHeaders(Collections.singletonList("Refresh"));
            configuration.setAllowCredentials(true);
            configuration.setMaxAge(3600L);

            return configuration;
        };
    }
}
