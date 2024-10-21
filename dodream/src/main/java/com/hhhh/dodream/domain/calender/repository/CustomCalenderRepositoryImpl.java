package com.hhhh.dodream.domain.calender.repository;

import com.hhhh.dodream.domain.calender.entity.CalenderEntity;
import com.hhhh.dodream.domain.calender.entity.QCalenderEntity;
import com.querydsl.jpa.impl.JPAQueryFactory;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

import static com.hhhh.dodream.domain.calender.entity.QCalenderEntity.calenderEntity;

@RequiredArgsConstructor
public class CustomCalenderRepositoryImpl implements CustomCalenderRepository {
    private final JPAQueryFactory jpaQueryFactory;

    @Override
    public Optional<CalenderEntity> findByIdWithUser(Long calenderId) {
        return Optional.ofNullable(
                jpaQueryFactory.selectFrom(calenderEntity)
                        .where(calenderEntity.id.eq(calenderId))
                        .join(calenderEntity.user).fetchJoin()
                        .fetchOne()
        );
    }
}
