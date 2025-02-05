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
        List<CalendarEntity> calendars = calendarRepository
                .findAllByUserIdAndDateInfoYearAndDateInfoMonth(userId, year, month);

        return calendars.stream().map(CalendarInquiryResponseDto::from).toList();

    }

    public List<CalendarInquiryResponseDto> get(Long userId) {
        List<CalendarEntity> calendars = calendarRepository.findAllByUserId(userId);

        return calendars.stream().map(CalendarInquiryResponseDto::from).toList();
    }

    @Transactional
    public Long save(Long userId, CalendarCreateRequestDto calendarRequest) {
        UserEntity user = userRepository.findById(userId)
                .orElseThrow(() -> new DataFoundException("user db에 없습니다."));

        CalendarEntity calendar = CalendarEntity.of(calendarRequest, user);
        calendarRepository.save(calendar);

        return calendar.getId();
    }

    @Transactional
    public void update(Long userId, Long calendarId, CalendarUpdateRequestDto updateRequestDto) {
        CalendarEntity calendar = validatecalendarWithUser(calendarId, userId, "수정");

        calendar.modify(updateRequestDto);
        calendarRepository.save(calendar);
    }

    @Transactional
    public void delete(Long userId, Long calendarId) {
        CalendarEntity calendar = validatecalendarWithUser(calendarId, userId, "삭제");

        calendarRepository.delete(calendar);
    }

    private CalendarEntity validatecalendarWithUser(Long calendarId, Long userId, String action) {
        CalendarEntity calendar = calendarRepository.findByIdWithUser(calendarId)
                .orElseThrow(() -> new DataFoundException("calendar db에 없는 데이터입니다."));

        if (!userId.equals(calendar.getUser().getId())) {
            throw new UnAuthorizedException("해당 일정을 " + action + "할 권한이 없습니다.");
        }

        return calendar;
    }
}
