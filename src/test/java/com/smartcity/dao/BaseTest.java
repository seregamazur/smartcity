package com.smartcity.dao;

import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class BaseTest {
    protected DriverManagerDataSource dataSource;

    public void setup() {
        // Setting up db
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.99.100:3306/smartcity");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
    }
}