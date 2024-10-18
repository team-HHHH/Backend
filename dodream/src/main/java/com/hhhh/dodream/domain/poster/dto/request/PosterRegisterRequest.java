package com.hhhh.dodream.domain.poster.dto.request;

import com.hhhh.dodream.domain.poster.entity.PosterInfo;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.geo.Point;

@NoArgsConstructor
@ToString
@Getter
@Setter
public class PosterRegisterRequest {
    private String images;
    private double uPosX;
    private double uPosY;
    private double pPosX;
    private double pPosY;
    private String url;
    private String title;
    private String content;
    private LocalDateTime startDay;
    private LocalDateTime endDay;
    private String participant;

    public void setuPosX(double uPosX) {
        this.uPosX = uPosX;
    }

    public void setuPosY(double uPosY) {
        this.uPosY = uPosY;
    }

    public void setpPosX(double pPosX) {
        this.pPosX = pPosX;
    }

    public void setpPosY(double pPosY) {
        this.pPosY = pPosY;
    }

    public PosterInfo toEntity(Long userId) {
        return new PosterInfo(
                images,
                new Point(uPosX, uPosY),
                new Point(pPosX, pPosY),
                url,
                userId,
                title,
                content,
                participant,
                startDay,
                endDay
        );
    }
}
