package com.hhhh.dodream.domain.calender.dto.request;

import com.hhhh.dodream.domain.calender.entity.embedded.DateInfo;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class CalenderUpdateRequestDto {
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
