package org.oladushek.model;

import lombok.Builder;
import lombok.Data;
import lombok.NonNull;

import java.util.List;


@Data
@Builder(toBuilder = true)
public class Post extends BaseEntity{
    private String title;
    private String content;
    private List<Label> postLabels;

    public Post(Long id, @NonNull String title, String content, List<Label> postLabels) {
        this.setId(id);
        this.title = title;
        this.content = content;
        this.postLabels = postLabels;
        this.setStatus(Status.ACTIVE);
    }

    public Post(@NonNull String title, String content, List<Label> postLabels) {
        this.setId(-1L);
        this.title = title;
        this.content = content;
        this.postLabels = postLabels;
        this.setStatus(Status.ACTIVE);
    }
}
