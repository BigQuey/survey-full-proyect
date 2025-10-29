package com.surservice.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.surservice.dto.QuestionDTO;
import com.surservice.dto.SurveyWithQuestionsDTO;
import com.surservice.feign.QuestionClient;
import com.surservice.models.Survey;
import com.surservice.repository.SurveyRepository;
import com.surservice.utils.ApiResponse;

@Service
public class SurveyService extends ICRUDImpl<Survey, Long> {
	@Autowired
	private SurveyRepository repo;
	@Autowired
	private QuestionClient questionClient;

	@Override
	public JpaRepository<Survey, Long> getRepository() {
		// TODO Auto-generated method stub
		return repo;
	}

	public List<QuestionDTO> getAllQuestions() {
        ApiResponse<List<QuestionDTO>> response = questionClient.getAllQuestions();
        return response.getData(); 
    }
	public SurveyWithQuestionsDTO getSurveyWithQuestions(Long surveyId) {
        Survey survey = repo.findById(surveyId).orElseThrow(() -> new RuntimeException("Survey not found"));

        // Traemos las preguntas desde question-service
        ApiResponse<List<QuestionDTO>> response = questionClient.getQuestionsBySurvey(surveyId);
        List<QuestionDTO> questions = response.getData();

        // Unimos datos
        return new SurveyWithQuestionsDTO(survey.getId(), survey.getTitle(), survey.getDescription(), questions);
    }
	public long count() {
		return repo.count();
	}
}
