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
    private Long calenderId;
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    public static CalendarInquiryResponseDto from(CalendarEntity calender) {
        return CalendarInquiryResponseDto.builder()
                .calenderId(calender.getId())
                .dateInfo(calender.getDateInfo())
                .title(calender.getTitle())
                .content(calender.getContent())
                .startDay(calender.getStartDay())
                .endDay(calender.getEndDay())
                .build();
    }
}
