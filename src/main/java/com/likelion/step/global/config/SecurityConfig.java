package com.likelion.step.global.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  public PasswordEncoder passwordEncoder() {
    return new BCryptPasswordEncoder();
  }

  @Bean
  public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
    http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
            .requestMatchers(
                "/api/auth/**",
                "/swagger-ui.html",
                "/swagger-ui/**",
                "/v3/api-docs/**"
            ).permitAll()
            // TODO: JWT 필터 완성 후 인증 필요한 API만 authenticated()로 변경
            .anyRequest().permitAll()
        );
    return http.build();
  }
}
