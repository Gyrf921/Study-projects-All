package org.oladushek.config;

import org.flywaydb.core.Flyway;

import java.sql.Connection;

public class FlywayConfig {

    private static final String JDBC_DRIVER = "hibernate.connection.driver_class";
    private static final String DATABASE_URL= "hibernate.connection.url";
    private static final String USER= "hibernate.connection.username";
    private static final String PASSWORD = "hibernate.connection.password";


    public static void applyMigrations() {
        try{
            Flyway flyway = Flyway.configure().dataSource(
                            PropertiesConfig.getProperty(DATABASE_URL),
                            PropertiesConfig.getProperty(USER),
                            PropertiesConfig.getProperty(PASSWORD)).load();
            flyway.migrate();
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }
}
