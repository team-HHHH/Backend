package com.hhhh.dodream.domain.calender.dto.response;

import com.hhhh.dodream.domain.calender.entity.CalenderEntity;
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

    public static CalenderInquiryResponseDto from(CalenderEntity calender) {
        return CalenderInquiryResponseDto.builder()
                .calenderId(calender.getId())
                .dateInfo(calender.getDateInfo())
                .title(calender.getTitle())
                .startDay(calender.getStartDay())
                .endDay(calender.getEndDay())
                .build();
    }
}
