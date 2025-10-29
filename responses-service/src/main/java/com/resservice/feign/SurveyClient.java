package com.resservice.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.resservice.dto.SurveyDTO;
import com.resservice.utils.ApiResponse;

@FeignClient(name = "survey-service")
public interface SurveyClient {
    @GetMapping("/api/surveys/find/{codigo}")
    ApiResponse<SurveyDTO> getSurveyById(@PathVariable("codigo") Long id);
}
