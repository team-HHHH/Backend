package com.hhhh.dodream.domain.poster.dto.response;

import com.hhhh.dodream.domain.poster.entity.PosterInfo;
import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.geo.Point;

@Getter
@AllArgsConstructor
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
