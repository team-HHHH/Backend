package com.hhhh.dodream.domain.calendar.dto.response;

import com.hhhh.dodream.domain.calendar.entity.CalendarEntity;
import com.hhhh.dodream.domain.calendar.entity.embedded.DateInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CalendarInquiryResponseDto {
    private Long calendarId;
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    public static CalendarInquiryResponseDto from(CalendarEntity calendar) {
        return CalendarInquiryResponseDto.builder()
                .calendarId(calendar.getId())
                .dateInfo(calendar.getDateInfo())
                .title(calendar.getTitle())
                .content(calendar.getContent())
                .startDay(calendar.getStartDay())
                .endDay(calendar.getEndDay())
                .build();
    }
}
