package com.hhhh.dodream.domain.calendar.dto.request;

import com.hhhh.dodream.domain.calendar.dto.DateInfoDto;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CalendarUpsertRequestDto {
    @Valid
    @NotNull
    private DateInfoDto dateInfo;

    @NotBlank
    private String title;

    @NotBlank
    private String content;

    @NotNull
    private LocalDateTime startDay;

    @NotNull
    private LocalDateTime endDay;
}
