package com.hhhh.dodream.global.cloud.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploadService {
    public String uploadImageToS3(MultipartFile image){
        //TODO : S3에 업로드하기
        return "temp::image";
    }
}
