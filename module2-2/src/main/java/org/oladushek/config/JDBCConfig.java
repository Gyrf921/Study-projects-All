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
import lombok.AllArgsConstructor;
import lombok.Value;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@AllArgsConstructor
public class JDBCConfig {
    private static volatile Connection connection;

    private static final String JDBC_DRIVER = "db.JDBC_DRIVER";
    private static final String DATABASE_URL= "db.DATABASE_URL";
    private static final String USER= "db.USER";
    private static final String PASSWORD = "db.PASSWORD";

    public static Connection getConnection() {
        Connection localConnection = connection;
        if (localConnection == null) {
            synchronized (JDBCConfig.class) {
                localConnection = connection;
                if (localConnection == null) {
                    try {
                        localConnection = connection = DriverManager.getConnection(
                                PropertiesConfig.getProperty(DATABASE_URL),
                                PropertiesConfig.getProperty(USER),
                                PropertiesConfig.getProperty(PASSWORD));
                    }catch (SQLException e) {
                        System.exit(-1);
                    }
                }
            }
        }
        return localConnection;

    }


}
