package com.hhhh.dodream.domain.calendar.repository;

import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;

import java.util.Optional;

public interface CustomCalendarRepository {
    Optional<CalendarEntity> findByIdWithUser(Long calenderId);
}
