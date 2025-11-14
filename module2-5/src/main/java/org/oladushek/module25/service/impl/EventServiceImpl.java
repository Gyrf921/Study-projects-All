package org.oladushek.module25.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.oladushek.module25.entity.EventEntity;
import org.oladushek.module25.service.EventService;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EventServiceImpl implements EventService {

    @Override
    public Mono<EventEntity> getById(Long aLong) {
        return null;
    }

    @Override
    public Mono<List<EventEntity>> getAll() {
        return null;
    }

    @Override
    public Mono<EventEntity> create(EventEntity eventEntity) {
        return null;
    }

    @Override
    public Mono<EventEntity> update(Long aLong, EventEntity eventEntity) {
        return null;
    }

    @Override
    public void delete(Long aLong) {

    }
}
