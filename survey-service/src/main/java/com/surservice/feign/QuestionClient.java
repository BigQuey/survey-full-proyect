package com.surservice.feign;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.surservice.dto.QuestionDTO;
import com.surservice.utils.ApiResponse;

@FeignClient(name = "question-service") // Debe coincidir con el spring.application.name
public interface QuestionClient {

	@GetMapping("/api/questions/list")
    ApiResponse<List<QuestionDTO>> getAllQuestions(); 

    @GetMapping("/api/questions/find/{codigo}")
    QuestionDTO getQuestionById(@PathVariable Long id);
    
    @GetMapping("/api/questions/survey/{surveyId}")
    ApiResponse<List<QuestionDTO>> getQuestionsBySurvey(@PathVariable("surveyId") Long surveyId);
}