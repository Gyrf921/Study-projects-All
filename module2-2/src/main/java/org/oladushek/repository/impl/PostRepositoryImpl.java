package org.oladushek.repository.impl;

import org.oladushek.config.StatementProviderUtils;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.mapper.PostMapper;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {
    private final LabelRepository labelRepository;

    public PostRepositoryImpl(LabelRepository labelRepository) {
        this.labelRepository = labelRepository;
    }
    public PostRepositoryImpl() {
        this.labelRepository = new LabelRepositoryImpl();
    }

    @Override
    public PostEntity findById(Long id) {
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement(
                "select p.*, l.id as label_id, l.name as label_name from posts p " +
                        "left join post_label pl on p.id = pl.post_id " +
                        "left join labels l on l.id = pl.label_id" +
                        " WHERE p.id = ?;")) {
            ps.setLong(1, id);

            return PostMapper.mapResultSetToPostEntity(ps.executeQuery());
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findById): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<PostEntity> findXNew(int count) {
        List<PostEntity> posts = new ArrayList<>();
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement(
                "select p.*, l.id as label_id, l.name as label_name from posts p " +
                        "left join post_label pl on p.id = pl.post_id " +
                        "left join labels l on l.id = pl.label_id" +
                        "ORDER BY created DESC LIMIT ?;")) {
            ps.setInt(1, count);
            posts = PostMapper.mapResultSetToList(ps.executeQuery());
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findXNew): " + e.getMessage());
        }
        return posts;
    }


    @Override
    public List<PostEntity> findAll() {
        List<PostEntity> posts = new ArrayList<>();
        try (Statement st = StatementProviderUtils.getStatement()) {
            ResultSet rs = st.executeQuery(
                    "select p.*, l.id as label_id, l.name as label_name from posts p " +
                            "left join post_label pl on p.id = pl.post_id " +
                            "left join labels l on l.id = pl.label_id; ");
            posts = PostMapper.mapResultSetToList(rs);
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findAll): " + e.getMessage());
        }
        return posts;
    }

    @Override
    public List<PostEntity> findAllWithoutWriter() {
        List<PostEntity> posts = new ArrayList<>();
        try (Statement st = StatementProviderUtils.getStatement()) {
            ResultSet rs = st.executeQuery(
                    "select p.*, l.id as label_id, l.name as label_name from posts p " +
                            "left join post_label pl on p.id = pl.post_id " +
                            "left join labels l on l.id = pl.label_id " +
                            "where p.writer_id is null ; ");
            posts = PostMapper.mapResultSetToList(rs);
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findAll): " + e.getMessage());
        }
        return posts;
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        //у нас один connection, спокойно можем использовать labelRepository и не громоздить кучу кода
        Connection connection;
        try {
            connection = StatementProviderUtils.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepointOne = connection.setSavepoint("SavepointOne");

            try (PreparedStatement ps = StatementProviderUtils.getPreparedStatementWithKey("INSERT INTO posts (content, status, writer_id) VALUES (?, ?, null)")) {
                //Сохраняем новый пост
                ps.setString(1, postEntity.getContent());
                ps.setString(2, postEntity.getStatus().name());
                ps.executeUpdate();
                Long savedId = getIdFromKey(ps);

                //Сохраняем лейблы и привязываем их к постам
                savePostLabelsLink(savedId, postEntity.getPostLabelEntities());

                connection.commit();
                connection.setAutoCommit(true);
                return findById(savedId);
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("SQLException. Executing rollback to savepoint...");
                connection.rollback(savepointOne);
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (save): " + e.getMessage());
        }

        return null;
    }



    @Override
    public void savePostLabelsLink(Long postIdForSave, List<LabelEntity> labels) {
        if (postIdForSave != null) {
            if (!labels.isEmpty()) {
                for (LabelEntity label : labels){
                    savePostLabelLink(postIdForSave, label.getId());
                }
            }
        }
    }


    @Override
    public void updateWriterLink(Long postIdForSave, Long writerIdForSave) {
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement("UPDATE posts SET writer_id = ? WHERE id = ?")) {
            ps.setLong(1, writerIdForSave);
            ps.setLong(2, postIdForSave);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (update): " + e.getMessage());
        }
    }

    @Override
    public PostEntity update(PostEntity postForUpdate) {
        //у нас один connection, спокойно можем использовать labelRepository и не громоздить кучу кода
        Connection connection;
        try {
            connection = StatementProviderUtils.getConnection();
            connection.setAutoCommit(false);
            Savepoint savepointOne = connection.setSavepoint("SavepointOne");

            try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement("UPDATE posts SET content = ?, status = ? WHERE id = ?")) {
                //Сохраняем новый пост
                ps.setString(1, postForUpdate.getContent());
                ps.setString(2, postForUpdate.getStatus().name());
                ps.setLong(3, postForUpdate.getId());
                ps.executeUpdate();

                //отправить на сохранение только те, каких ещё нет
                PostEntity savedPostEntity = findById(postForUpdate.getId());

                List<LabelEntity> labelToSave = postForUpdate.getPostLabelEntities().stream()
                        .filter(labelForUpdate -> !savedPostEntity.getPostLabelEntities()
                                .contains(labelForUpdate)).toList();

                savePostLabelsLink(postForUpdate.getId(), labelToSave);

                connection.commit();
                connection.setAutoCommit(true);
                return findById(postForUpdate.getId());
            } catch (SQLException e) {
                System.out.println("SQLException. Executing rollback to savepoint...");
                connection.rollback(savepointOne);
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (update): " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long postId) {
        try (PreparedStatement ps = StatementProviderUtils.getPreparedStatement(
                "DELETE FROM posts WHERE id = ?")) {
            ps.setLong(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (deleteById): " + e.getMessage());
        }
    }

    private void savePostLabelLink(Long postId, Long labelId) {
        try(PreparedStatement ps = StatementProviderUtils.getPreparedStatement("INSERT INTO post_label (post_id, label_id) VALUES (?, ?)")) {
            ps.setLong(1, postId);
            ps.setLong(2, labelId);
            ps.executeUpdate();
        }
        catch (SQLException e) {
            System.out.println("Problem with SQL query: " + e.getMessage());
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
