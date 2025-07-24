package org.oladushek.controller.dto;

import org.oladushek.model.entity.LabelEntity;

import java.util.List;

public record PostDTO (Long id, String title, String content, List<LabelEntity> postLabelEntities) {
}
