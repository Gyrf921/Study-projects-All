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

import static org.oladushek.config.JDBCConfig.getConnection;

public class LiqubaseConfig {

    public static void applyMigrations(Connection connection) {
        try{
            Database database = DatabaseFactory.getInstance()
                    .findCorrectDatabaseImplementation(new JdbcConnection(connection));
            Liquibase liquibase = new Liquibase(
                    "db/changelog/db.changelog-master.xml",
                    new ClassLoaderResourceAccessor(),
                    database
            );
            liquibase.update(new Contexts(), new LabelExpression());
        } catch (Throwable e) {
            System.out.println(e.getMessage());
            System.exit(-1);
        }

    }


}
