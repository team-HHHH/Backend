package com.hhhh.dodream.domain.poster.controller;

import com.hhhh.dodream.domain.poster.dto.request.PosterLocationRequest;
import com.hhhh.dodream.domain.poster.dto.request.PosterRegisterRequest;
import com.hhhh.dodream.domain.poster.dto.request.PosterUploadRequest;
import com.hhhh.dodream.domain.poster.dto.response.PosterInfoResponse;
import com.hhhh.dodream.domain.poster.service.PosterInfoService;
import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.security.custom.CustomUserDetails;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/posters")
public class PosterInfoController {
    private final PosterInfoService posterInfoService;

    @PostMapping("/upload")
    public ResponseDto uploadPoster(@ModelAttribute PosterUploadRequest request) {
        PosterInfoResponse posterInfoResponse = posterInfoService.uploadAndProcessImage(request);
        return BodyResponseDto.onSuccess("포스터 업로드 성공", posterInfoResponse);
    }


    @GetMapping("/location/user")
    public ResponseDto getPostersByLocationByUpos(@ModelAttribute PosterLocationRequest request) {
        List<PosterInfoResponse> posts = posterInfoService.getNearPostByUPos(request);
        return BodyResponseDto.onSuccess("주변 유저 위치 기반 포스터 가져오기 성공", posts);
    }

    @GetMapping("/location/poster")
    public ResponseDto getPostersByLocationByPpos(@ModelAttribute PosterLocationRequest request) {
        List<PosterInfoResponse> posts = posterInfoService.getNearPostByPPos(request);
        return BodyResponseDto.onSuccess("주변 포스터 위치 기반 포스터 가져오기 성공", posts);
    }

    @PostMapping
    public ResponseDto postPoster(@RequestBody PosterRegisterRequest request,
                                  @AuthenticationPrincipal CustomUserDetails userDetails) {
        posterInfoService.savePoster(userDetails.getUserId(), request);
        return ResponseDto.onSuccess("포스터 저장 성공");
    }
}
