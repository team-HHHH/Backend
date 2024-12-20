package com.hhhh.dodream.domain.calender.repository;

import com.hhhh.dodream.domain.calender.entity.CalenderEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CalenderRepository extends JpaRepository<CalenderEntity, Long>, CustomCalenderRepository {
    List<CalenderEntity> findAllByUserIdAndDateInfoYearAndDateInfoMonth(Long userId, Integer year, Integer month);

    List<CalenderEntity> findAllByUserId(Long userId);
}
