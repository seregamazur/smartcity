/*
 * Copyright Homeaway, Inc 2019-Present. All Rights Reserved.
 * No unauthorized use of this software.
 */
package com.smartcity.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;

@Import({DBConfig.class, WebSecurityConfig.class})
@Configuration
@PropertySource(value = "classpath:application-test.properties")
@ComponentScan(basePackages = "com.smartcity")
public class ApplicationConfig {

}
