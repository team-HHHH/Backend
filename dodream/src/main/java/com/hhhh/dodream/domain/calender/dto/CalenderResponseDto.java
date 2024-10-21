package com.hhhh.dodream.domain.calender.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CalenderResponseDto {
    private Long calenderId;
    private Integer year;
    private Integer month;
    private Integer day;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
