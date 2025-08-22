package org.oladushek.repository.impl;

import org.oladushek.config.StatementProviderUtils;
import org.oladushek.entity.LabelEntity;
import org.oladushek.repository.LabelRepository;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class LabelRepositoryImpl implements LabelRepository {

    @Override
    public LabelEntity findById(Long aLong) {
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatement("SELECT * FROM labels where id = ?")) {
            ps.setLong(1, aLong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LabelEntity labelEntity = new LabelEntity();
                labelEntity.setId(rs.getLong("id"));
                labelEntity.setName(rs.getString("name"));
                return labelEntity;
            }
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
        return null;
    }

    @Override
    public LabelEntity findByName(String name) {
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatement("SELECT * FROM labels where name = ?")) {
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LabelEntity labelEntity = new LabelEntity();
                labelEntity.setId(rs.getLong("id"));
                labelEntity.setName(rs.getString("name"));
                return labelEntity;
            }
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<LabelEntity> findAll() {
        List<LabelEntity> labelEntities = new ArrayList<>();
        try(Statement st = StatementProviderUtils.getStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM labels");
            while (rs.next()) {
                LabelEntity entity = new LabelEntity();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                labelEntities.add(entity);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return labelEntities;
    }


    @Override
    public LabelEntity save(LabelEntity labelEntity) {
        LabelEntity savedLabelEntity = new LabelEntity();
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatementWithKey("INSERT INTO labels (name) VALUES (?)")) {
            ps.setString(1, labelEntity.getName());
            ps.executeUpdate();

            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    savedLabelEntity = findById(keys.getLong(1));
                    savedLabelEntity.setName(labelEntity.getName());
                }
            }
            return savedLabelEntity;
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
        return null;
    }



    @Override
    public LabelEntity update(LabelEntity labelEntity) {
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatement("UPDATE labels SET name ? where id = ?")) {
            ps.setString(1, labelEntity.getName());
            ps.setLong(2, labelEntity.getId());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                LabelEntity entity = new LabelEntity();
                entity.setId(rs.getLong("id"));
                entity.setName(rs.getString("name"));
                return entity;
            }
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long aLong) {
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatement("DELETE FROM labels WHERE id = ?")) {
            ps.setLong(1, aLong);
            ps.execute();
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
    }
}
