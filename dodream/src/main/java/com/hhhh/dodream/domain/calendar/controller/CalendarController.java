package com.hhhh.dodream.domain.calendar.controller;

import com.hhhh.dodream.domain.calendar.dto.request.CalendarCreateRequestDto;
import com.hhhh.dodream.domain.calendar.dto.request.CalendarUpdateRequestDto;
import com.hhhh.dodream.domain.calendar.dto.response.CalendarInquiryResponseDto;
import com.hhhh.dodream.domain.calendar.service.CalendarService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.security.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/calendars")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/{year}/{month}")
    public ResponseDto getCalendars(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        List<CalendarInquiryResponseDto> inquiryResponseDtoList = calendarService.get(userDetails.getUserId(), year, month);
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @GetMapping
    public ResponseDto getCalendars(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CalendarInquiryResponseDto> inquiryResponseDtoList = calendarService.get(userDetails.getUserId());
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @PostMapping
    public ResponseDto saveCalendar(@RequestBody CalendarCreateRequestDto createRequestDto,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long calendarId = calendarService.save(userDetails.getUserId(), createRequestDto);
        return BodyResponseDto.onSuccess("캘린더 저장 성공", calendarId);
    }

    @PatchMapping("/{calendarId}")
    public ResponseDto updateCalendar(@PathVariable("calendarId") Long calendarId,
                                      @RequestBody CalendarUpdateRequestDto updateRequestDto,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calendarService.update(userDetails.getUserId(), calendarId, updateRequestDto);
        return ResponseDto.onSuccess("캘린더 수정 성공");
    }

    @DeleteMapping("/{calendarId}")
    public ResponseDto deleteCalendar(@PathVariable("calendarId") Long calendarId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calendarService.delete(userDetails.getUserId(), calendarId);
        return ResponseDto.onSuccess("캘린더 삭제 성공");
    }
}
