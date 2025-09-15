package org.oladushek.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.oladushek.entity.base.BaseEntity;
import org.oladushek.entity.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@EqualsAndHashCode(callSuper=false)
@Entity
@Table(name = "posts")
public class PostEntity extends BaseEntity {

    @Column(name = "content", nullable = false)
    private String content;

    @Column(name = "created", nullable = false)
    private LocalDateTime created = LocalDateTime.now();

    @Column(name = "updated")
    private LocalDateTime updated;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "writer_id", nullable = false)
    private WriterEntity writer;

    @ManyToMany
    @JoinTable(
            name = "posts_labels",
            joinColumns = @JoinColumn(name = "post_id"),
            inverseJoinColumns = @JoinColumn(name = "label_id")
    )
    private List<LabelEntity> labels;

    @Column(name = "status", nullable = false)
    @Enumerated(EnumType.STRING)
    private PostStatus status = PostStatus.UNDER_REVIEW;

    public PostEntity(String content, WriterEntity writer, List<LabelEntity> labels, PostStatus status) {
        this.content = content;
        this.writer = writer;
        this.labels = labels;
        this.status = status;
    }

    public PostEntity(String content, WriterEntity writer, List<LabelEntity> labels) {
        this.content = content;
        this.writer = writer;
        this.labels = labels;
    }
}
