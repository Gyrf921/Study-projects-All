package org.oladushek.dto;

import org.oladushek.entity.PostEntity;

import java.util.List;

public record WriterDTO(Long id, String firstName, String lastName, List<PostEntity> postEntities) {

    @Override
    public String toString() {
        return "WriterDTO{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", postEntities=" + postEntities +
                '}';
    }
}
