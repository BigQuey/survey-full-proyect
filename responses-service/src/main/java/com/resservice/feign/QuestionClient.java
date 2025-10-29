package com.resservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.resservice.dto.QuestionDTO;
import com.resservice.utils.ApiResponse;

@FeignClient(name = "question-service")
public interface QuestionClient {
    @GetMapping("/api/questions/find/{codigo}")
    QuestionDTO getQuestionById(@PathVariable("codigo") Long id);
    @GetMapping("/api/questions/survey/{surveyId}")
    ApiResponse<List<QuestionDTO>> getQuestionsBySurveyId(@PathVariable("surveyId") Long surveyId);
}