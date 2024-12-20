package com.hhhh.dodream.domain.calender.entity;

import com.hhhh.dodream.domain.calender.dto.request.CalenderCreateRequestDto;
import com.hhhh.dodream.domain.calender.dto.request.CalenderUpdateRequestDto;
import com.hhhh.dodream.domain.calender.entity.embedded.DateInfo;
import com.hhhh.dodream.domain.user.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

import static com.hhhh.dodream.global.common.utils.LambdaUtils.updateLambda;

@Entity
@Getter
@Builder
@AllArgsConstructor(access = AccessLevel.PRIVATE)
@Table(name = "calender")
@NoArgsConstructor(access = lombok.AccessLevel.PROTECTED)
public class CalenderEntity {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Embedded
    private DateInfo dateInfo;

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

    public static CalenderEntity of(CalenderCreateRequestDto createRequestDto, UserEntity user) {
        return CalenderEntity.builder()
                .dateInfo(createRequestDto.getDateInfo())
                .title(createRequestDto.getTitle())
                .content(createRequestDto.getContent())
                .startDay(createRequestDto.getStartDay())
                .endDay(createRequestDto.getEndDay())
                .user(user)
                .build();
    }

    public void modify(CalenderUpdateRequestDto updateRequest) {
        updateLambda(updateRequest.getDateInfo(), dateInfo -> this.dateInfo = dateInfo);
        updateLambda(updateRequest.getTitle(), title -> this.title = title);
        updateLambda(updateRequest.getContent(), content -> this.content = content);
        updateLambda(updateRequest.getStartDay(), startDay -> this.startDay = startDay);
        updateLambda(updateRequest.getEndDay(), endDay -> this.endDay = endDay);
    }
}
