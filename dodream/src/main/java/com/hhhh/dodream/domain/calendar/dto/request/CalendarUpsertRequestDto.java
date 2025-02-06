package com.hhhh.dodream.domain.calendar.dto.request;

import com.hhhh.dodream.domain.calendar.entity.embedded.DateInfo;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarUpsertRequestDto {
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
