package org.oladushek.repository.impl;

import org.oladushek.config.StatementProvider;
import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;
import org.oladushek.entity.enums.PostStatus;
import org.oladushek.repository.LabelRepository;
import org.oladushek.repository.PostRepository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PostRepositoryImpl implements PostRepository {

    @Override
    public PostEntity findById(Long id) {
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "SELECT * FROM posts WHERE id = ?")) {
            ps.setLong(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return mapPost(rs);
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findById): " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<PostEntity> findXNew(int count) {
        List<PostEntity> posts = new ArrayList<>();
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "SELECT * FROM posts ORDER BY created DESC LIMIT ?")) {
            ps.setInt(1, count);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                posts.add(mapPost(rs));
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findXNew): " + e.getMessage());
        }
        return posts;
    }

    @Override
    public List<PostEntity> findAll() {
        List<PostEntity> posts = new ArrayList<>();
        try (Statement st = StatementProvider.getStatement()) {
            ResultSet rs = st.executeQuery("SELECT * FROM posts");
            while (rs.next()) {
                posts.add(mapPost(rs));
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findAll): " + e.getMessage());
        }
        return posts;
    }

    @Override
    public PostEntity save(PostEntity postEntity) {
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "INSERT INTO posts (content, status) VALUES (?, ?)")) {

            ps.setString(1, postEntity.getContent());
            ps.setString(2, postEntity.getStatus().name());

            ps.executeUpdate();

            // Получим id новой записи
            try (Statement st = StatementProvider.getStatement();
                 ResultSet rs = st.executeQuery("SELECT LAST_INSERT_ID()")) {
                if (rs.next()) {
                    postEntity.setId(rs.getLong(1));
                }
            }

            //return new LabelEntity((long) findAll().size(), labelEntity.getName());
            saveLabelsForPost(postEntity);
            return postEntity;
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (save): " + e.getMessage());
        }
        return null;
    }

    @Override
    public PostEntity update(PostEntity postEntity) {
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "UPDATE posts SET content = ?, status = ? WHERE id = ?")) {

            ps.setString(1, postEntity.getContent());
            ps.setString(2, postEntity.getStatus().name());
            ps.setLong(3, postEntity.getId());

            ps.executeUpdate();

            deleteLabelsForPost(postEntity.getId());
            saveLabelsForPost(postEntity);

            return postEntity;
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (update): " + e.getMessage());
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "DELETE FROM posts WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (deleteById): " + e.getMessage());
        }
    }

    private PostEntity mapPost(ResultSet rs) throws SQLException {
        PostEntity post = new PostEntity();
        post.setId(rs.getLong("id"));
        post.setContent(rs.getString("content"));
        post.setStatus(PostStatus.valueOf(rs.getString("status")));
        post.setCreated(rs.getTimestamp("created").toLocalDateTime());
        post.setUpdated(rs.getTimestamp("updated").toLocalDateTime());
        post.setPostLabelEntities(findLabelsByPostId(post.getId()));
        return post;
    }

    private List<LabelEntity> findLabelsByPostId(Long postId) {
        List<LabelEntity> labels = new ArrayList<>();
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "SELECT l.id, l.name FROM labels l " +
                        "JOIN post_label pl ON l.id = pl.label_id " +
                        "WHERE pl.post_id = ?")) {
            ps.setLong(1, postId);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                labels.add(new LabelEntity(rs.getLong("id"), rs.getString("name")));
            }
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (findLabelsByPostId): " + e.getMessage());
        }
        return labels;
    }

    private void saveLabelsForPost(PostEntity postEntity) {
        if (postEntity.getPostLabelEntities() == null) return;
        for (LabelEntity label : postEntity.getPostLabelEntities()) {
            try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                    "INSERT INTO post_label (post_id, label_id) VALUES (?, ?)")) {
                ps.setLong(1, postEntity.getId());
                ps.setLong(2, label.getId());
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println("Problem with SQL query (saveLabelsForPost): " + e.getMessage());
            }
        }
    }

    private void deleteLabelsForPost(Long postId) {
        try (PreparedStatement ps = StatementProvider.getPreparedStatement(
                "DELETE FROM post_label WHERE post_id = ?")) {
            ps.setLong(1, postId);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.out.println("Problem with SQL query (deleteLabelsForPost): " + e.getMessage());
        }
    }
}
