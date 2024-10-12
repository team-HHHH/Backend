package com.hhhh.dodream.domain.calender.controller;

import com.hhhh.dodream.domain.calender.dto.CalenderRequestDto;
import com.hhhh.dodream.domain.calender.dto.CalenderResponseDto;
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

    @GetMapping
    public ResponseDto getCalender(@RequestParam("year") Integer year,
                                   @RequestParam("month") Integer month,
                                   @AuthenticationPrincipal CustomUserDetails userDetails) {
        List<CalenderResponseDto> calenderResponse = calenderService.get(
                userDetails.getUserId(), year, month);
        return BodyResponseDto.onSuccess("캘린더 조회 성공", calenderResponse);
    }

    @PostMapping
    public ResponseDto saveCalender(@RequestBody CalenderRequestDto calenderRequest,
                                    @AuthenticationPrincipal CustomUserDetails userDetails) {
        Long calenderId = calenderService.save(userDetails.getUserId(), calenderRequest);
        return BodyResponseDto.onSuccess("캘린더 저장 성공", calenderId);
    }

    @PatchMapping("/{calenderId}")
    public ResponseDto updateCalender(@PathVariable("calenderId") Long calenderId,
                                      @RequestBody CalenderRequestDto calenderRequest,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calenderService.update(userDetails.getUserId(), calenderId, calenderRequest);
        return ResponseDto.onSuccess("캘린더 수정 성공");
    }

    @DeleteMapping("/{calenderId}")
    public ResponseDto deleteCalender(@PathVariable("calenderId") Long calenderId,
                                      @AuthenticationPrincipal CustomUserDetails userDetails) {
        calenderService.delete(userDetails.getUserId(), calenderId);
        return ResponseDto.onSuccess("캘린더 삭제 성공");
    }
}
