package com.hhhh.dodream.domain.poster.dto.response;

import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PosterSummaryResponse {
    private String title;
    private String participants;
    private String summary;
    private LocalDate endDate;
    private LocalDate startDate;
    private String image;
}