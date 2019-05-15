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
        dataSource.setUrl("jdbc:mysql://mysql-2305-0.cloudclusters.net:10007/smartcity");
        dataSource.setUsername("smartcity");
        dataSource.setPassword("smartcity");
        template = new JdbcTemplate(dataSource);
        setupQueries();
    }

    protected static void setupQueries() {
        template.update("INSERT INTO Users() VALUES (1,'santa@sasa','1234','Saaanta','Saatat','09898965456', true,'2019-05-05','2019-05-05');");
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
        template.batchUpdate("set foreign_key_checks=0",
            "TRUNCATE TABLE Users",
            "TRUNCATE TABLE Organizations",
            "TRUNCATE TABLE Users_organizations",
            "TRUNCATE TABLE Tasks",
            "TRUNCATE TABLE Transactions",
            "TRUNCATE TABLE Comments",
            "TRUNCATE TABLE Roles",
            "TRUNCATE TABLE Users_roles",
            "TRUNCATE TABLE Budget",
            "set foreign_key_checks=1");
    }

    public void clearTables(String... tableNames) {
        final String[] cleanupQueries = new String[tableNames.length + 2];
        cleanupQueries[0] = "SET foreign_key_checks=0";
        cleanupQueries[cleanupQueries.length - 1] = "SET foreign_key_checks=1";
        for (int index = 0; index < tableNames.length; index++) {
            cleanupQueries[index + 1] = "TRUNCATE TABLE " + tableNames[index];
        }
        template.batchUpdate(cleanupQueries);
    }
}