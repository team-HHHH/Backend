package com.hhhh.dodream.domain.calender.entity;

import com.hhhh.dodream.domain.calender.dto.CalenderRequestDto;
import com.hhhh.dodream.domain.calender.dto.CalenderResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import com.hhhh.dodream.global.common.utils.LambdaUtils;
import io.jsonwebtoken.lang.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import static com.hhhh.dodream.global.common.utils.LambdaUtils.updateLambda;

@Entity
@Getter
@Builder
@AllArgsConstructor
@Table(name = "calender")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CalenderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "year")
    private Integer year;

    @Column(name = "month")
    private Integer month;

    @Column(name = "day")
    private Integer day;

    @Column(name = "title")
    private String title;

    @Column(name = "content")
    private String content;

    @Column(name = "start_day")
    private LocalDateTime startDay;

    @Column(name = "end_day")
    private LocalDateTime endDay;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private UserEntity user;

    public CalenderResponseDto toCalenderResponseDto() {
        return CalenderResponseDto.builder()
                .calenderId(this.id)
                .year(this.year).month(this.month).day(this.day)
                .title(this.title).content(this.content)
                .startDay(this.startDay).endDay(this.endDay)
                .build();
    }

    public void updateEntity(CalenderRequestDto calenderRequest) {
        updateLambda(calenderRequest.getYear(), year -> this.year = year);
        updateLambda(calenderRequest.getMonth(), month -> this.month = month);
        updateLambda(calenderRequest.getDay(), day -> this.day = day);
        updateLambda(calenderRequest.getTitle(), title -> this.title = title);
        updateLambda(calenderRequest.getContent(), content -> this.content = content);
        updateLambda(calenderRequest.getStartDay(), startDay -> this.startDay = startDay);
        updateLambda(calenderRequest.getEndDay(), endDay -> this.endDay = endDay);
    }
}
