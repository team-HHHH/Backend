package com.hhhh.dodream.domain.calendar.repository;

import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.hhhh.dodream.domain.calendar.entity.QCalendarEntity.calendarEntity;


@RequiredArgsConstructor
public class CustomCalendarRepositoryImpl implements CustomCalendarRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CalendarEntity> findByIdWithUser(Long calendarId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(calendarEntity)
                        .where(calendarEntity.id.eq(calendarId))
                        .join(calendarEntity.user).fetchJoin()
                        .fetchOne()
        );
    }
}
