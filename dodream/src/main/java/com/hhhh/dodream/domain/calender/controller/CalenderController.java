package com.hhhh.dodream.domain.calender.controller;

import com.hhhh.dodream.domain.calender.dto.request.CalenderCreateRequestDto;
import com.hhhh.dodream.domain.calender.dto.request.CalenderUpdateRequestDto;
import com.hhhh.dodream.domain.calender.dto.response.CalenderInquiryResponseDto;
import com.hhhh.dodream.domain.calender.service.CalenderService;
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
public class CalenderController {
    private final CalenderService calenderService;

    @GetMapping("/{year}/{month}")
    public ResponseDto getCalenders(@AuthenticationPrincipal CustomUserDetails userDetails,
                                    @PathVariable("year") Integer year, @PathVariable("month") Integer month) {
        List<CalenderInquiryResponseDto> inquiryResponseDtoList = calenderService.get(userDetails.getUserId(), year, month);
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @GetMapping
    public ResponseDto getCalenders(@AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CalenderInquiryResponseDto> inquiryResponseDtoList = calenderService.get(userDetails.getUserId());
        return BodyResponseDto.onSuccess("캘린더 조회 성공", inquiryResponseDtoList);
    }

    @PostMapping
    public ResponseDto saveCalender(@RequestBody CalenderCreateRequestDto createRequestDto,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long calenderId = calenderService.save(userDetails.getUserId(), createRequestDto);
        return BodyResponseDto.onSuccess("캘린더 저장 성공", calenderId);
    }

    //TODO 여기부터 해야 됨
    @PatchMapping("/{calenderId}")
    public ResponseDto updateCalender(@PathVariable("calenderId") Long calenderId,
                                      @RequestBody CalenderUpdateRequestDto updateRequestDto,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calenderService.update(userDetails.getUserId(), calenderId, updateRequestDto);
        return ResponseDto.onSuccess("캘린더 수정 성공");
    }

    @DeleteMapping("/{calenderId}")
    public ResponseDto deleteCalender(@PathVariable("calenderId") Long calenderId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calenderService.delete(userDetails.getUserId(), calenderId);
        return ResponseDto.onSuccess("캘린더 삭제 성공");
    }
}
