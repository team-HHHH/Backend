package com.hhhh.dodream.domain.calendar.dto;

import com.hhhh.dodream.domain.calendar.entity.embedded.DateInfo;
import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Builder
@Embeddable
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class DateInfoDto {
    @Min(1)
    @NotNull
    private Integer year;

    @Min(1)
    @Max(12)
    @NotNull
    private Integer month;

    @Min(1)
    @Max(31)
    @NotNull
    private Integer day;

    public static DateInfoDto from(DateInfo dateInfo) {
        return DateInfoDto.builder()
                .year(dateInfo.getYear())
                .month(dateInfo.getMonth())
                .day(dateInfo.getDay())
                .build();
    }
}
