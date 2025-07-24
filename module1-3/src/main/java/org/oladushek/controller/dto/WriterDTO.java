package org.oladushek.controller.dto;

import org.oladushek.model.entity.PostEntity;

import java.util.List;

public record WriterDTO(Long id, String firstName, String lastName, List<PostEntity> postEntities) {

}
