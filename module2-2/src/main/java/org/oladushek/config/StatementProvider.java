package org.oladushek.config;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

public class StatementProvider {
    private StatementProvider() {}

    public static Statement getStatement() throws SQLException {
        return JDBCConfig.getConnection().createStatement();
    }

    public static PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return JDBCConfig.getConnection().prepareStatement(sql);
    }

}
