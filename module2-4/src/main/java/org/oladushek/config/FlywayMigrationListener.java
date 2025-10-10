package org.oladushek.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class FlywayMigrationListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        System.out.println("Starting Flyway migrations...");
        FlywayConfig.applyMigrations();
        System.out.println("Flyway migrations completed");
    }

}