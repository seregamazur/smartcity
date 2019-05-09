package com.smartcity.dao;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

public class BaseTest {
  
    protected static DriverManagerDataSource dataSource;
    protected static JdbcTemplate template;

    public static void setup() {
        // Setting up db
        dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://192.168.99.100:3306/smartcity");
        dataSource.setUsername("root");
        dataSource.setPassword("root");
        template = new JdbcTemplate(dataSource);
        setupQueries();
    }

    protected static void setupQueries() {
        template.update("INSERT INTO Users() VALUES (1,'santa@sasa','1234','Saaanta','Saatat','09898965456','2019-05-05','2019-05-05');");
        template.update("INSERT INTO Organizations() VALUES (1,'santa','ssassa','2019-05-05','2019-05-05');");
        template.update("INSERT INTO Users_organizations() VALUES (1,1,1,'2019-05-05','2019-05-05');");
        template.update("Insert into Tasks() values (1,'Title','Desc','2019-05-05','Santa',1000,1000,'2019-05-05','2019-05-05',1);");
        template.update("Insert into Transactions() values (1,1,12000,6000,'2019-05-05','2019-05-05');");
        template.update("Insert into Budget() values (120000);");
        template.update("Insert into Roles() values (1,'Admin','2019-05-05','2019-05-05');");
        template.update("Insert into Users_roles() values (1,1,1,'2019-05-05','2019-05-05');");
        template.update("Insert into Comments() values (1,'Comment desc','2019-05-05','2019-05-05',1,1);");
    }

    public static void tearDown() {
        template.update("set global foreign_key_checks=0");
        template.update("TRUNCATE TABLE Users");
        template.update("TRUNCATE TABLE Organizations");
        template.update("TRUNCATE TABLE Users_organizations");
        template.update("TRUNCATE TABLE Tasks");
        template.update("TRUNCATE Table Transactions");
        template.update("TRUNCATE TABLE Comments");
        template.update("TRUNCATE TABLE Roles");
        template.update("TRUNCATE TABLE Users_roles");
        template.update("TRUNCATE TABLE Budget");
        template.update("set global foreign_key_checks=1");
    }

}