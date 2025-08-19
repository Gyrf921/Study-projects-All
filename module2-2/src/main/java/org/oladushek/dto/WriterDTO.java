package org.oladushek.dto;

import org.oladushek.entity.PostEntity;

import java.util.List;

public record WriterDTO(Long id, String firstName, String lastName, List<PostEntity> postEntities) {

}
