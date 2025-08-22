package org.oladushek.repository;

import org.oladushek.entity.LabelEntity;
import org.oladushek.entity.PostEntity;

import java.util.Collection;
import java.util.List;

public interface PostRepository extends GenericRepository<PostEntity, Long>{

    List<PostEntity> findXNew(int count);

    void savePostLabelsLink(Long postIdForSave, List<LabelEntity> labels);

    List<PostEntity> findAllWithoutWriter();

    void updateWriterLink(Long postIdForSave, Long writerIdForSave);
}
