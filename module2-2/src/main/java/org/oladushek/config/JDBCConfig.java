package org.oladushek.config;

import liquibase.Contexts;
import liquibase.LabelExpression;
import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.exception.DatabaseException;
import liquibase.exception.LiquibaseException;
import liquibase.resource.ClassLoaderResourceAccessor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class JDBCConfig {
    private static volatile Connection connection;
    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String DATABASE_URL = "jdbc:mysql://localhost:3306/module22db";
    private static final String USER = "user";
    private static final String PASSWORD = "user";
    
    public static Connection getConnection() {
        Connection localConnection = connection;
        if (localConnection == null) {
            synchronized (JDBCConfig.class) {
                localConnection = connection;
                if (localConnection == null) {
                    try {
                        localConnection = connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);

                        Database database = DatabaseFactory.getInstance()
                                .findCorrectDatabaseImplementation(new JdbcConnection(localConnection));
                        Liquibase liquibase = new Liquibase(
                                "db/changelog/db.changelog-master.xml",
                                new ClassLoaderResourceAccessor(),
                                database
                        );
                        liquibase.update(new Contexts(), new LabelExpression());
                    }catch (SQLException e) {
                        System.out.println("Problem with JDBC connection: " + e.getMessage());
                    } catch (DatabaseException e) {
                        System.out.println("Problem with Database: " + e.getMessage());
                    } catch (LiquibaseException e) {
                        System.out.println("Problem with Liquibase: " + e.getMessage());
                    }
                }
            }
        }
        return localConnection;

    }


}
