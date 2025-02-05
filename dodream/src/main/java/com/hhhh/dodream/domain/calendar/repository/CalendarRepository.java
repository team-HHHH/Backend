package com.hhhh.dodream.domain.calendar.repository;

import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalendarRepository extends JpaRepository<CalendarEntity, Long>, CustomCalendarRepository {
    List<CalendarEntity> findAllByUserIdAndDateInfoYearAndDateInfoMonth(
            Long userId,
            Integer year,
            Integer month
    );

    List<CalendarEntity> findAllByUserId(Long userId);
}
