package org.oladushek.repository.impl;

import org.oladushek.config.StatementProviderUtils;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.WriterEntity;
import org.oladushek.mapper.WriterMapper;
import org.oladushek.repository.PostRepository;
import org.oladushek.repository.WriterRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class WriterRepositoryImpl implements WriterRepository {

    private final PostRepository postRepository;

    public WriterRepositoryImpl(PostRepository postRepository) {
        this.postRepository = postRepository;
    }
    public WriterRepositoryImpl() {
        this.postRepository = new PostRepositoryImpl();
    }

    @Override
    public WriterEntity findById(Long id) {
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement(
                "select w.*, p.id as post_id, p.* from writers w " +
                        "left join posts p on p.writer_id = w.id " +
                        "where w.id = ?;")) {
            ps.setLong(1, id);

            return WriterMapper.mapResultSetToWriterEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findById): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<WriterEntity> findAll() {
        List<WriterEntity> writers = new ArrayList<>();
        try(Statement ps = StatementProviderUtils.getStatement()){
            ResultSet rs = ps.executeQuery("select w.*, p.id as post_id, p.* from writers w " +
                    "left join posts p on p.writer_id = w.id");
            writers = WriterMapper.mapResultSetToList(rs);
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findAll): " + e.getMessage());
        }
        return writers;
    }

    @Override
    public WriterEntity save(WriterEntity writerEntity) {
        Connection connection;

        try {
            connection = StatementProviderUtils.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("savepointWS");
            try (PreparedStatement ps = StatementProviderUtils.getPreparedStatementWithKey(
                    "insert into writers(first_name, last_name) values(?, ?);")){

                ps.setString(1, writerEntity.getFirstName());
                ps.setString(2, writerEntity.getLastName());
                ps.executeUpdate();
                Long savedId = getIdFromKey(ps);

                //поставить в уже существующие посты writer_id и сохранить
                for(PostEntity post : writerEntity.getPostEntities()){
                    postRepository.updateWriterLink(post.getId(), savedId);
                }
                connection.commit();
                connection.setAutoCommit(true);

                writerEntity.setId(savedId);
                return writerEntity;
            }
            catch (SQLException e) {
                System.out.println("SQLException. Executing rollback to savepoint...");
                connection.rollback(savepoint);
            }
        }catch (SQLException e){
            System.out.println("Problem with SQL query (save): " + e.getMessage());
        }
        return null;
    }

    @Override
    public WriterEntity update(WriterEntity writerEntity) {
        Connection connection;

        try {
            connection = StatementProviderUtils.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepoint = connection.setSavepoint("savepointWS");
            try (PreparedStatement ps = StatementProviderUtils.getPreparedStatementWithKey(
                    "update writers set first_name = ?, last_name = ? where id = ?;")){

                ps.setString(1, writerEntity.getFirstName());
                ps.setString(2, writerEntity.getLastName());
                ps.setLong(3, writerEntity.getId());
                ps.executeUpdate();

                //поставить в уже существующие посты writer_id и сохранить
                for(PostEntity post : writerEntity.getPostEntities()){
                    postRepository.updateWriterLink(post.getId(), writerEntity.getId());
                }
                connection.commit();
                connection.setAutoCommit(true);

                return writerEntity;
            }
            catch (SQLException e) {
                System.out.println("SQLException. Executing rollback to savepoint...");
                connection.rollback(savepoint);
            }
        }catch (SQLException e){
            System.out.println("Problem with SQL query (save): " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement(
                "delete from writers where id = ? ;")){
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (deleteById): " + e.getMessage());
        }
    }

    private Long getIdFromKey(PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                return rs.getLong(1);
            }
        }
        return null;
    }
}
