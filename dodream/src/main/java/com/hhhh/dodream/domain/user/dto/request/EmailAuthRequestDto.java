package com.hhhh.dodream.domain.user.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class EmailAuthRequestDto {
    private String email;
    private Integer emailCode;
}
