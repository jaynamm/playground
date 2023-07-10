package com.encore.playground.domain.recommend.controller;

import com.encore.playground.domain.recommend.dto.RecommendedListDto;
import com.encore.playground.global.api.DefaultResponse;
import com.encore.playground.global.api.ResponseMessage;
import com.encore.playground.global.api.StatusCode;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/recommend")
@Tag(name="recommend", description = "추천시스템 관련 API")
public class RecommendAPIController {

    @PostMapping("/list")
    public ResponseEntity<?> recommendedList(@RequestBody HashMap<String, RecommendedListDto> inputData) {
        System.out.println(inputData);
        System.out.println(inputData.get("inputData"));
        System.out.println(inputData.get("inputData").getJobName());

        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setContentType(MediaType.APPLICATION_JSON);

        HashMap<String, Object> body = new HashMap<>();
        body.put("job_name", inputData.get("inputData").getJobName());
        body.put("location", inputData.get("inputData").getJobLocation());
        body.put("skills", inputData.get("inputData").getJobSkills());

        System.out.println(body);

        RestTemplate restTemplate = new RestTemplate();

        String url = "https://port-0-playground-recommended-das6e2dli8i6fze.sel4.cloudtype.app/recommend";
        HttpEntity<?> requestMessage = new HttpEntity<>(body, httpHeaders);
        HttpEntity<String> response = restTemplate.postForEntity(url, requestMessage, String.class);

        System.out.println(response.getBody());

        return new ResponseEntity<>(
                DefaultResponse.res(
                        StatusCode.OK,
                        ResponseMessage.RECOMMENDED_SUCCESS,
                        response.getBody()
                ),
                HttpStatus.OK
        );
    }
}
