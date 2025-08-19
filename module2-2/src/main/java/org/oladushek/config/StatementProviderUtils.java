package org.oladushek.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementProviderUtils {
    private StatementProviderUtils() {}

    public static Statement getStatement() throws SQLException {
        return JDBCConfig.getConnection().createStatement();
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return JDBCConfig.getConnection().prepareStatement(sql);
    }

    public static PreparedStatement getPreparedStatementWithKey(String sql) throws SQLException {
        return JDBCConfig.getConnection().prepareStatement(sql);
    }

}
