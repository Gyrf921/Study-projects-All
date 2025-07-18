package org.oladushek.controller.dto;

import org.oladushek.model.Post;

import java.util.List;

public record WriterDTO(Long id, String firstName, String lastName, List<Post> posts) {

}
