package com.smartcity.config;

import com.smartcity.utils.EncryptionUtil;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class EncryptionConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder() {
        return new EncryptionUtil();
    }
}