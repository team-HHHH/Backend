package com.hhhh.dodream.domain.poster.service;

import com.hhhh.dodream.domain.poster.dto.response.PosterInfoResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class PosterImageProcessingService {
    public PosterInfoResponse processImage(MultipartFile file) {
        //TODO - 파이썬 OCR
        //TODO - gpt API 처리
        return new PosterInfoResponse();
    }
}
