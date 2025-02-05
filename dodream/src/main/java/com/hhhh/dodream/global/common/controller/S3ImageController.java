package com.hhhh.dodream.global.common.controller;

import com.hhhh.dodream.global.common.dto.BodyResponseDto;
import com.hhhh.dodream.global.common.dto.PreSignedUrlResponseDto;
import com.hhhh.dodream.global.common.dto.ResponseDto;
import com.hhhh.dodream.global.common.service.S3ImageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/images")
public class S3ImageController {
    private final S3ImageService s3ImageService;

    @GetMapping
    public ResponseDto getPreSignedUrl(@RequestParam("imageType") String imageType) {
        PreSignedUrlResponseDto responseDto = s3ImageService.generatePreSignedUrl(imageType);

        return BodyResponseDto.onSuccess("PreSignedUrl 생성 및 조회 성공", responseDto);
    }
}
