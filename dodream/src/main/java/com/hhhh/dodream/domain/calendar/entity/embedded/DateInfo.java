package com.hhhh.dodream.domain.calendar.entity.embedded;

import com.hhhh.dodream.domain.calendar.dto.DateInfoDto;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

@Getter
@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateInfo {
    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    public static DateInfo from(DateInfoDto dateInfo) {
        return DateInfo.builder()
                .year(dateInfo.getYear())
                .month(dateInfo.getMonth())
                .day(dateInfo.getDay())
                .build();
    }
}
