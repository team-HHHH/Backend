package com.hhhh.dodream.domain.calender.repository;

import com.hhhh.dodream.domain.calender.entity.CalenderEntity;

import java.util.Optional;

public interface CustomCalenderRepository {
    Optional<CalenderEntity> findByIdWithUser(Long calenderId);
}
