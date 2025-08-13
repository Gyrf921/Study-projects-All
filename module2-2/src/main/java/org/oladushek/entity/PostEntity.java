package org.oladushek.entity;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.oladushek.controller.dto.PostDTO;
import org.oladushek.entity.base.BaseEntity;
import org.oladushek.entity.base.UpdatedEntity;
import org.oladushek.entity.enums.PostStatus;

import java.util.List;


@Data
@NoArgsConstructor
public class PostEntity extends UpdatedEntity {
    private String content;
    private List<LabelEntity> postLabelEntities;
    private PostStatus status;

    public PostEntity(Long id, String content, List<LabelEntity> postLabelEntities, PostStatus status) {
        this.setId(id);
        this.content = content;
        this.postLabelEntities = postLabelEntities;
        this.status = status;
    }

    public PostEntity(Long id, String content, List<LabelEntity> postLabelEntities) {
        this.setId(id);
        this.content = content;
        this.postLabelEntities = postLabelEntities;
        this.status = PostStatus.UNDER_REVIEW;
    }

    public PostEntity(String content, List<LabelEntity> postLabelEntities) {
        this.setId(-1L);
        this.content = content;
        this.postLabelEntities = postLabelEntities;
        this.status = PostStatus.UNDER_REVIEW;
    }
}
