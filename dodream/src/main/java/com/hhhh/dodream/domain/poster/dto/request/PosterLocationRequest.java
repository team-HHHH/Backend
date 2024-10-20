package com.hhhh.dodream.domain.poster.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@Getter
@Setter
@ToString
public class PosterLocationRequest {
    private double longitude;
    private double latitude;
    private double distance;
    private int page;
}
