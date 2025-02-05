package com.hhhh.dodream.domain.calendar.dto.request;

import com.hhhh.dodream.domain.calendar.entity.embedded.DateInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalendarCreateRequestDto {
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
