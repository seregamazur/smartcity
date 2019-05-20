package com.smartcity.profile;

import com.smartcity.config.DBConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@Profile("dev")
@PropertySource(value = "classpath:application-dev.properties")
public class ProfileDev extends DBConfig {

    @Bean
    public DataSource sourceDev() throws PropertyVetoException {
        return dataSource();
    }
}