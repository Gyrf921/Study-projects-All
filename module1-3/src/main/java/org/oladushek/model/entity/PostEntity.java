package org.oladushek.model.entity;

import lombok.*;
import org.oladushek.model.Status;
import org.oladushek.model.StatusEntity;

import java.util.List;


@Data
@NoArgsConstructor
@Builder(toBuilder = true)
public class PostEntity extends StatusEntity {
    private String title;
    private String content;
    private List<LabelEntity> postLabelEntities;

    public PostEntity(Long id, @NonNull String title, String content, List<LabelEntity> postLabelEntities) {
        this.setId(id);
        this.title = title;
        this.content = content;
        this.postLabelEntities = postLabelEntities;
        this.setStatus(Status.ACTIVE);
    }

    public PostEntity(@NonNull String title, String content, List<LabelEntity> postLabelEntities) {
        this.setId(-1L);
        this.title = title;
        this.content = content;
        this.postLabelEntities = postLabelEntities;
        this.setStatus(Status.ACTIVE);
    }
}
