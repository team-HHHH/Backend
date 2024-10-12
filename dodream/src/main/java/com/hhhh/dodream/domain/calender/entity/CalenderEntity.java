package com.hhhh.dodream.domain.calender.entity;

import com.hhhh.dodream.domain.calender.dto.CalenderRequestDto;
import com.hhhh.dodream.domain.calender.dto.CalenderResponseDto;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import io.jsonwebtoken.lang.Objects;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

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
        Integer year = calenderRequest.getYear();
        Integer month = calenderRequest.getMonth();
        Integer day = calenderRequest.getDay();
        String title = calenderRequest.getTitle();
        String content = calenderRequest.getContent();
        LocalDateTime startDay = calenderRequest.getStartDay();
        LocalDateTime endDay = calenderRequest.getEndDay();
        if (!Objects.isEmpty(year)) this.year = year;
        if (!Objects.isEmpty(month)) this.month = month;
        if (!Objects.isEmpty(day)) this.day = day;
        if (!Objects.isEmpty(title)) this.title = title;
        if (!Objects.isEmpty(content)) this.content = content;
        if (!Objects.isEmpty(startDay)) this.startDay = startDay;
        if (!Objects.isEmpty(endDay)) this.endDay = endDay;
    }
}
