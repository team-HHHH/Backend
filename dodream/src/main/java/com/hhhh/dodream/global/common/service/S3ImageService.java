package com.hhhh.dodream.global.common.service;

import com.amazonaws.HttpMethod;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.Headers;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.GeneratePresignedUrlRequest;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.hhhh.dodream.global.common.dto.PreSignedUrlResponseDto;
import com.hhhh.dodream.global.common.utils.ImageMimeTypeUtil;
import com.hhhh.dodream.global.exception.kind.error_exception.S3ContentTypeException;
import com.hhhh.dodream.global.exception.kind.error_exception.S3UploadException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class S3ImageService {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    public String uploadImageToS3(MultipartFile multipartFile, String path) {
        ObjectMetadata metadata = new ObjectMetadata();
        metadata.setContentLength(multipartFile.getSize());
        metadata.setContentType(multipartFile.getContentType());
        String originalFilename = multipartFile.getOriginalFilename();
        if (originalFilename == null) {
            throw new S3UploadException("파일의 이름이 존재하지 않습니다.");
        }
        String extension = extractExtension(originalFilename);
        String fileName = path + "." + extension;
        try {
            amazonS3.putObject(bucket, fileName, multipartFile.getInputStream(), metadata);
        } catch (IOException e) {
            throw new S3UploadException("S3 업로드에 실패하였습니다.");
        }
        String url = amazonS3.getUrl(bucket, fileName).toString();
        return url;
    }

    private String extractExtension(String originalFileName) {
        return originalFileName.substring(originalFileName.lastIndexOf(".") + 1);
    }

    public PreSignedUrlResponseDto generatePreSignedUrl(String imageType) {
        GeneratePresignedUrlRequest generatePresignedUrlRequest = getGeneratePreSignedUrlRequest(imageType);

        String preSignedUrl = amazonS3.generatePresignedUrl(generatePresignedUrlRequest).toString();
        String imageSavedUrl = amazonS3.getUrl(bucket, generatePresignedUrlRequest.getKey()).toString();

        return PreSignedUrlResponseDto.of(preSignedUrl, imageSavedUrl);
    }

    private GeneratePresignedUrlRequest getGeneratePreSignedUrlRequest(String imageType) {
        String savedFileName = UUID.randomUUID() + "." + imageType;
        String mimeType = ImageMimeTypeUtil.getImageMimeType(imageType);

        if(mimeType == null) {
            throw new S3ContentTypeException("이미지 타입이 아닙니다.");
        }

        GeneratePresignedUrlRequest generatePresignedUrlRequest = new GeneratePresignedUrlRequest(bucket, savedFileName)
                .withMethod(HttpMethod.PUT)
                .withExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 5))
                .withContentType(mimeType);

        generatePresignedUrlRequest.addRequestParameter(
                Headers.S3_CANNED_ACL,
                CannedAccessControlList.PublicRead.toString()
        );

        return generatePresignedUrlRequest;
    }
}
