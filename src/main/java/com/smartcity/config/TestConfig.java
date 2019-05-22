package com.smartcity.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

@Configuration
@Profile("test")
@PropertySource(value = "classpath:application-test.properties")
public class TestConfig implements ProfileConfig {

}
