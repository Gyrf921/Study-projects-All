package org.oladushek.service;

import org.oladushek.entity.LabelEntity;

import java.util.List;

public interface LabelService extends GenericService<LabelEntity, Long>{
    List<LabelEntity> getSelectedLabelByIdForPost(List<Long> labelsIdForNewPost);
}
