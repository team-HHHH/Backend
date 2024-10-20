package com.hhhh.dodream.domain.poster.dto.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@NoArgsConstructor
@Getter
@Setter
public class PosterUploadRequest {
    private MultipartFile file;
}
