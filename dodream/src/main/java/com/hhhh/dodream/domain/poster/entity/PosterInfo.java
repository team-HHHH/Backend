package com.hhhh.dodream.domain.poster.entity;

import jakarta.persistence.Id;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@NoArgsConstructor
@Document(collection = "posterInfo")
public class PosterInfo {
    @Id
    private String id;
    private String images;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point uPos;
    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    private Point pPos;
    private String url;
    private String title;
    private Long uploadUserId;
    private String content;
    private String participant;
    private LocalDateTime startDay;
    private LocalDateTime endDay;

    public PosterInfo(String images, Point uPos, Point pPos, String url, Long userId, String title, String content,
                      String participant,
                      LocalDateTime startDay, LocalDateTime endDay) {
        this.images = images;
        this.uPos = uPos;
        this.pPos = pPos;
        this.url = url;
        this.uploadUserId = userId;
        this.title = title;
        this.content = content;
        this.participant = participant;
        this.startDay = startDay;
        this.endDay = endDay;
    }
}
