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
@RequestMapping("/calenders")
public class CalendarController {
    private final CalendarService calendarService;

    @GetMapping("/{year}/{month}")
    public ResponseDto getCalenders(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        List<CalendarInquiryResponseDto> inquiryResponseDtoList = calendarService.get(userDetails.getUserId(), year, month);
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @GetMapping
    public ResponseDto getCalenders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CalendarInquiryResponseDto> inquiryResponseDtoList = calendarService.get(userDetails.getUserId());
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @PostMapping
    public ResponseDto saveCalender(@RequestBody CalendarCreateRequestDto createRequestDto,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long calenderId = calendarService.save(userDetails.getUserId(), createRequestDto);
        return BodyResponseDto.onSuccess("캘린더 저장 성공", calenderId);
    }

    //TODO 여기부터 해야 됨
    @PatchMapping("/{calenderId}")
    public ResponseDto updateCalender(@PathVariable("calenderId") Long calenderId,
                                      @RequestBody CalendarUpdateRequestDto updateRequestDto,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calendarService.update(userDetails.getUserId(), calenderId, updateRequestDto);
        return ResponseDto.onSuccess("캘린더 수정 성공");
    }

    @DeleteMapping("/{calenderId}")
    public ResponseDto deleteCalender(@PathVariable("calenderId") Long calenderId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calendarService.delete(userDetails.getUserId(), calenderId);
        return ResponseDto.onSuccess("캘린더 삭제 성공");
    }
}
