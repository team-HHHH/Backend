package com.hhhh.dodream.domain.calendar.dto.response;

import com.hhhh.dodream.domain.calendar.dto.DateInfoDto;
import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;
import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class CalendarInquiryResponseDto {
    private Long calendarId;
    private DateInfoDto dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    public static CalendarInquiryResponseDto from(CalendarEntity calendar) {
        return CalendarInquiryResponseDto.builder()
                .calendarId(calendar.getId())
                .dateInfo(DateInfoDto.from(calendar.getDateInfo()))
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .startDay(calendar.getStartDay())
                .endDay(calendar.getEndDay())
                .build();
    }
}
