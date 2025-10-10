package org.oladushek.service.impl;

import org.oladushek.config.HibernateConfig;
import org.oladushek.dto.EventDto;
import org.oladushek.dto.FileDto;
import org.oladushek.dto.UserDto;
import org.oladushek.entity.EventEntity;
import org.oladushek.service.EventService;

import java.util.List;

public class EventServiceImpl implements EventService {

    @Override
    public List<EventDto> getByAll() {
        return HibernateConfig.getSessionFactory()
                .fromTransaction(session ->
                        session.createSelectionQuery(
                                        "SELECT e FROM EventEntity e " +
                                                "JOIN FETCH e.user " +
                                                "JOIN FETCH e.file", EventEntity.class)
                                .list()
                                .stream()
                                .map(entity -> new EventDto(new UserDto(entity.getUser()), new FileDto(entity.getFile())))
                                .toList()
                );
    }

    @Override
    public void delete(Long eventId) {
        HibernateConfig.getSessionFactory().inTransaction(
                session -> session.remove(session.getReference(EventDto.class, eventId)));
    }
}
