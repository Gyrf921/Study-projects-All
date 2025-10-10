package org.oladushek.service;

import org.oladushek.dto.EventDto;

import java.util.List;

public interface EventService {
    List<EventDto> getByAll();

    void delete(Long eventId);
}
