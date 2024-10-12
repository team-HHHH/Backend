package com.hhhh.dodream.domain.calender.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalenderRequestDto {
    private Integer year;
    private Integer month;
    private Integer day;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
