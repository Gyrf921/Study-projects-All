package org.oladushek.dto;

import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.enums.PostStatus;

import java.time.LocalDateTime;
import java.util.List;

public record PostDTO (Long id, String content, LocalDateTime created, LocalDateTime updated, List<LabelEntity> postLabelEntities, PostStatus status) {
}
