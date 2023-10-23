package com.playdata.config;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@RequiredArgsConstructor
public class ApplicationConfig {

    private int strength = 10;
    // 이것도 환경설정으로 보내버려야함.

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public AuthenticationProvider authenticationProvider(){
        DaoAuthenticationProvider authenticationProvider
                = new DaoAuthenticationProvider();

        authenticationProvider.setPasswordEncoder(passwordEncoder());

        return authenticationProvider;
    }
}
