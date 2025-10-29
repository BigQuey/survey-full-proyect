package com.resservice.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.resservice.dto.AnswerDTO;
import com.resservice.dto.ResponseDTO;
import com.resservice.dto.SurveyStatsDTO;
import com.resservice.models.Response;
import com.resservice.service.ResponseService;
import com.resservice.utils.ApiResponse;

@RestController
@RequestMapping("/api/responses")

public class ResponseController {
	@Autowired
    private  ResponseService service;
	
	@GetMapping("/hola")
	public String hola() {
		return "Funcionando";
	}
	
	
	@PostMapping("/submit")
	public ResponseEntity<ApiResponse<?>> submitResponse(@RequestBody ResponseDTO dto) {
	    List<Response> responses = new ArrayList<>();

	    for (AnswerDTO answer : dto.getAnswers()) {
	        Response response = new Response(
	            null,
	            dto.getSurveyId(),
	            answer.getQuestionId(),
	            dto.getUserEmail(),
	            answer.getResponse()
	        );
	        responses.add(service.saveResponse(response));
	    }

	    ApiResponse<List<Response>> apiResponse =
	            new ApiResponse<>(true, "Respuestas registradas", responses);

	    return ResponseEntity.ok(apiResponse);
	}


    @GetMapping("/survey/{surveyId}")
    public ResponseEntity<ApiResponse<?>> getResponsesBySurvey(@PathVariable Long surveyId) {
        List<Response> list = service.getResponsesBySurvey(surveyId);
        ApiResponse<List<Response>> apiResponse = new ApiResponse<>(true, "Listado de respuestas", list);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/stats/survey/{surveyId}")
    public ResponseEntity<ApiResponse<?>> getSurveyStatistics(@PathVariable Long surveyId) {
        SurveyStatsDTO stats = service.getSurveyStatistics(surveyId);
        ApiResponse<SurveyStatsDTO> apiResponse = new ApiResponse<>(true, "Estadísticas de la encuesta", stats);
        return ResponseEntity.ok(apiResponse);
    }
    @GetMapping("/count")
    public long countUsers() {
        return service.count();
    }
    @GetMapping("/my-completed-ids")
    public Set<Long> getMyCompletedSurveyIds(Authentication authentication) {
        String userEmail = authentication.getName(); 
        return service.getCompletedSurveyIdsByUser(userEmail);
    }
    @GetMapping("/by-user-and-survey")
    public ResponseEntity<ApiResponse<List<Response>>> getMyAnswersForSurvey(
            @RequestParam Long surveyId, Authentication authentication) {
        
        String userEmail = authentication.getName();
        List<Response> responses = service.getResponsesByUserAndSurvey(userEmail, surveyId);
        return ResponseEntity.ok(new ApiResponse<>(true, "Respuestas del usuario", responses));
    }
}