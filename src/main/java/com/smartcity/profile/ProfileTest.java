package com.smartcity.profile;

import com.smartcity.config.DBConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

@Configuration
@Profile("test")
@PropertySource(value = "classpath:application-test.properties")
public class ProfileTest extends DBConfig {

    @Bean
    public DataSource sourceTest() throws PropertyVetoException {
        return dataSource();
    }
}
