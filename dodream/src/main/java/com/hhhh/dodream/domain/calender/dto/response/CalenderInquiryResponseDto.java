package com.hhhh.dodream.domain.calender.dto.response;

import com.hhhh.dodream.domain.calender.entity.embedded.DateInfo;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CalenderInquiryResponseDto {
    private Long calenderId;
    private DateInfo dateInfo;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
}
