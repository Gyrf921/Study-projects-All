package org.oladushek.repository.impl;

import org.oladushek.config.StatementProvider;
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
        try(PreparedStatement ps = StatementProvider.getPreparedStatement("SELECT * FROM labels where id = ?")) {
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
    public List<LabelEntity> findAll() {
        List<LabelEntity> labelEntities = new ArrayList<>();
        try(Statement st = StatementProvider.getStatement()) {
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
        try(PreparedStatement ps = StatementProvider.getPreparedStatement("INSERT INTO labels (name) VALUES (?)")) {
            ps.setString(1, labelEntity.getName());
            ps.executeUpdate();

            //FIXME костыль тот ещё
            return new LabelEntity((long) findAll().size(), labelEntity.getName());
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
        return null;
    }

    @Override
    public LabelEntity update(LabelEntity labelEntity) {
        try(PreparedStatement ps = StatementProvider.getPreparedStatement("UPDATE labels SET name ? where id = ?")) {
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
        try(PreparedStatement ps = StatementProvider.getPreparedStatement("DELETE FROM labels WHERE id = ?")) {
            ps.setLong(1, aLong);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
        }
    }
}
