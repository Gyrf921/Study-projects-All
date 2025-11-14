package org.oladushek.module25.service;

import org.oladushek.module25.entity.UserEntity;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public interface GenericService<Entity, ID> {

    Mono<Entity> getById(ID id);

    Mono<List<Entity>> getAll();

    Mono<Entity> create(Entity entity);

    Mono<Entity> update(ID id, Entity entity);

    void delete(ID id);
}
