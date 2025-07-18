package org.oladushek.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NonNull;

import java.util.ArrayList;
import java.util.List;

@Data
public class Writer extends BaseEntity{

    private String firstName;
    private String lastName;
    private List<Post> posts;

    public Writer(Long id, @NonNull String firstName, @NonNull String lastName, List<Post> posts) {
        this.setId(id);
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.setStatus(Status.ACTIVE);
    }

    public Writer(@NonNull String firstName, @NonNull String lastName, List<Post> posts) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = posts;
        this.setStatus(Status.ACTIVE);
    }
    public Writer(@NonNull String firstName, @NonNull String lastName) {
        this.setId(-1L);
        this.firstName = firstName;
        this.lastName = lastName;
        this.posts = new ArrayList<>();
        this.setStatus(Status.ACTIVE);
    }
}
