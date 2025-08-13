package org.oladushek.repository.impl;

import com.mysql.cj.jdbc.ConnectionImpl;
import org.oladushek.config.DbConnectConfig;
import org.oladushek.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;

import java.sql.*;
import java.util.List;

import static org.oladushek.config.DbConnectConfig.*;

public class LabelRepositoryImpl implements LabelRepository {


    @Override
    public LabelEntity findById(Long aLong) {
        Connection connection = null;
        Statement statement = null;

        try {
            Class.forName(JDBC_DRIVER);
            connection = DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
            statement = connection.createStatement();

            ResultSet resultSet = statement.executeQuery("SELECT * FROM labels");

        }
        catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        }


        return null;
    }

    @Override
    public List<LabelEntity> findAll() {
        return List.of();
    }

    @Override
    public LabelEntity save(LabelEntity labelEntity) {
        return null;
    }

    @Override
    public LabelEntity update(LabelEntity labelEntity) {
        return null;
    }

    @Override
    public void deleteById(Long aLong) {

    }
}
