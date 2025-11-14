package org.oladushek.module25.rest;

import lombok.RequiredArgsConstructor;
import org.oladushek.module25.entity.EventEntity;
import org.oladushek.module25.rest.dto.EventRequestDto;
import org.oladushek.module25.service.EventService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@RequestMapping("/api/v1/files")
@RequiredArgsConstructor
public class EventControllerV1 {

    private final EventService eventService;

    @GetMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<EventEntity> getEventById(@PathVariable Long eventId) {
        return eventService.getById(eventId);
    }

    @GetMapping
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<List<EventEntity>> getAllEvents() {
        return eventService.getAll();
    }


    @PutMapping("/{eventId}/")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<EventEntity> updateEvent(@PathVariable Long eventId, @RequestBody EventRequestDto event) {
        return eventService.update(eventId, new EventEntity(event));
    }

    @DeleteMapping("/{eventId}")
    @PreAuthorize("hasAnyAuthority('MODERATOR', 'ADMIN')")
    public Mono<Void> deleteEvent(@PathVariable Long eventId) {
        eventService.delete(eventId);
        return Mono.empty();
    }
}
