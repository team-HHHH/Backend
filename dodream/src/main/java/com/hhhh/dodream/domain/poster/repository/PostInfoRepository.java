package com.hhhh.dodream.domain.poster.repository;

import com.hhhh.dodream.domain.poster.entity.PosterInfo;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Point;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PostInfoRepository extends MongoRepository<PosterInfo, String> {
    List<PosterInfo> findByuPosNear(Point point, Distance distance, Pageable pageable);

    List<PosterInfo> findBypPosNear(Point point, Distance distance, Pageable pageable);

}
