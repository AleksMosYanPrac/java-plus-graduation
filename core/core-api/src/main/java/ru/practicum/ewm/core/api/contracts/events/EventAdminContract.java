package ru.practicum.ewm.core.api.contracts.events;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;
import ru.practicum.ewm.core.api.contracts.events.dto.EventFullDto;
import ru.practicum.ewm.core.api.contracts.events.dto.UpdateEventAdminRequest;
import ru.practicum.ewm.core.api.exceptions.DataIntegrityViolation;
import ru.practicum.ewm.core.api.exceptions.NotFoundException;

import java.time.LocalDateTime;
import java.util.List;

@RequestMapping("/admin/events")
public interface EventAdminContract {

    @GetMapping
    List<EventFullDto> search(@RequestParam(required = false) Long[] users,
                              @RequestParam(required = false) String[] states,
                              @RequestParam(required = false) Integer[] categories,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                              LocalDateTime rangeStart,
                              @RequestParam(required = false) @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
                              LocalDateTime rangeEnd,
                              @RequestParam(required = false, defaultValue = "0") int from,
                              @RequestParam(required = false, defaultValue = "10") int size);

    @PatchMapping("/{eventId}")
    EventFullDto adminVerification(@PathVariable Long eventId,
                                   @RequestBody UpdateEventAdminRequest event)
            throws NotFoundException, DataIntegrityViolation;
}
