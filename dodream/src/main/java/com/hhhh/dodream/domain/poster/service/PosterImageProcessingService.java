package com.hhhh.dodream.domain.poster.service;

import com.hhhh.dodream.domain.poster.dto.response.PosterInfoResponse;
import com.hhhh.dodream.domain.poster.dto.response.PosterSummaryResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class PosterImageProcessingService {
    @Value("${ocr.server.url}")
    private String url;

    public PosterInfoResponse processImage(MultipartFile file) {
        RestTemplate restTemplate = new RestTemplate();
        MultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
        body.add("image", file.getResource());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);

        HttpEntity<MultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);

        ResponseEntity<PosterSummaryResponse> response = restTemplate.exchange(
                url,
                HttpMethod.POST,
                requestEntity,
                PosterSummaryResponse.class
        );

        PosterSummaryResponse summary = response.getBody();
        return new PosterInfoResponse(summary.getTitle(), summary.getParticipants(), summary.getSummary(),
                summary.getEndDate(), summary.getStartDate());
    }
}
