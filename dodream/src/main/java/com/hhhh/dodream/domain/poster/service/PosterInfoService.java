package com.hhhh.dodream.domain.poster.service;

import com.hhhh.dodream.domain.poster.dto.request.PosterLocationRequest;
import com.hhhh.dodream.domain.poster.dto.request.PosterRegisterRequest;
import com.hhhh.dodream.domain.poster.dto.response.PosterInfoResponse;
import com.hhhh.dodream.domain.poster.entity.PosterInfo;
import com.hhhh.dodream.domain.poster.repository.PostInfoRepository;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.geo.Distance;
import org.springframework.data.geo.Metrics;
import org.springframework.data.geo.Point;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PosterInfoService {
    private static final int PAGE_SIZE = 10;
    private final PostInfoRepository postInfoRepository;

    /**
     * 유저 거리에 따라 포스터 가져오기
     *
     * @param request 포스터 쿼리 요청 객체
     * @return 포스터 응답
     */
    public List<PosterInfoResponse> getNearPostByUPos(PosterLocationRequest request) {
        List<PosterInfo> byUPosNear = postInfoRepository.findByuPosNear(
                new Point(request.getLatitude(), request.getLongitude()),
                new Distance(request.getDistance(), Metrics.KILOMETERS),
                PageRequest.of(request.getPage(), PAGE_SIZE));
        return byUPosNear.stream().map(PosterInfoResponse::fromEntity).collect(Collectors.toList());
    }

    /**
     * 포스터 거리에 따라 포스터 가져오기
     *
     * @param request
     * @return
     */
    public List<PosterInfoResponse> getNearPostByPPos(PosterLocationRequest request) {
        List<PosterInfo> bypPosNear = postInfoRepository.findBypPosNear(
                new Point(request.getLatitude(), request.getLongitude()),
                new Distance(request.getDistance(), Metrics.KILOMETERS),
                PageRequest.of(request.getPage(), PAGE_SIZE));
        return bypPosNear.stream().map(PosterInfoResponse::fromEntity).collect(Collectors.toList());
    }

    /**
     * 포스터 저장
     *
     * @param request 포스터 저장 요청 객체
     */
    public void savePoster(Long userId, PosterRegisterRequest request) {
        postInfoRepository.save(request.toEntity(userId));
    }
}
