package com.hhhh.dodream.domain.poster.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.hhhh.dodream.domain.poster.entity.PosterInfo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.geo.Point;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@Setter
@JsonInclude(Include.NON_NULL)
public class PosterInfoResponse {
    private String id;
    private String images;
    private Point uPos;
    private Point pPos;
    private String url;
    private String title;
    private String content;
    private String participant;
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    public PosterInfoResponse(String title, String participants, String summary, LocalDateTime endDate,
                              LocalDateTime startDate) {
        this.title = title;
        this.participant = participants;
        this.content = summary;
        this.endDay = endDate;
        this.startDay = startDate;
    }

    public static PosterInfoResponse fromEntity(PosterInfo posterInfo) {
        return new PosterInfoResponse(
                posterInfo.getId(),
                posterInfo.getImages(),
                posterInfo.getUPos(),
                posterInfo.getPPos(),
                posterInfo.getUrl(),
                posterInfo.getTitle(),
                posterInfo.getContent(),
                posterInfo.getParticipant(),
                posterInfo.getStartDay(),
                posterInfo.getEndDay()
        );
    }
}
