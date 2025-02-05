package com.hhhh.dodream.domain.calendar.service;

import com.hhhh.dodream.domain.calendar.dto.request.CalendarCreateRequestDto;
import com.hhhh.dodream.domain.calendar.dto.request.CalendarUpdateRequestDto;
import com.hhhh.dodream.domain.calendar.dto.response.CalendarInquiryResponseDto;
import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;
import com.hhhh.dodream.domain.calendar.repository.CalendarRepository;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.domain.user.repository.UserRepository;
import com.hhhh.dodream.global.exception.kind.agreed_exception.UnAuthorizedException;
import com.hhhh.dodream.global.exception.kind.error_exception.DataFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CalendarService {
    private final UserRepository userRepository;
    private final CalendarRepository calendarRepository;

    public List<CalendarInquiryResponseDto> get(Long userId, Integer year, Integer month) {
        List<CalendarEntity> calenders = calendarRepository
                .findAllByUserIdAndDateInfoYearAndDateInfoMonth(userId, year, month);

        return calenders.stream().map(CalendarInquiryResponseDto::from).toList();

    }

    public List<CalendarInquiryResponseDto> get(Long userId) {
        List<CalendarEntity> calenders = calendarRepository.findAllByUserId(userId);

        return calenders.stream().map(CalendarInquiryResponseDto::from).toList();
    }

    @Transactional
    public Long save(Long userId, CalendarCreateRequestDto calenderRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataFoundException("user db에 없습니다."));

        CalendarEntity calender = CalendarEntity.of(calenderRequest, user);
        calendarRepository.save(calender);

        return calender.getId();
    }

    @Transactional
    public void update(Long userId, Long calenderId, CalendarUpdateRequestDto updateRequestDto) {
        CalendarEntity calender = validateCalenderWithUser(calenderId, userId, "수정");

        calender.modify(updateRequestDto);
        calendarRepository.save(calender);
    }

    @Transactional
    public void delete(Long userId, Long calenderId) {
        CalendarEntity calender = validateCalenderWithUser(calenderId, userId, "삭제");

        calendarRepository.delete(calender);
    }

    private CalendarEntity validateCalenderWithUser(Long calenderId, Long userId, String action) {
        CalendarEntity calender = calendarRepository.findByIdWithUser(calenderId)
                .orElseThrow(() -> new DataFoundException("calender db에 없는 데이터입니다."));

        if (!userId.equals(calender.getUser().getId())) {
            throw new UnAuthorizedException("해당 일정을 " + action + "할 권한이 없습니다.");
        }

        return calender;
    }
}
