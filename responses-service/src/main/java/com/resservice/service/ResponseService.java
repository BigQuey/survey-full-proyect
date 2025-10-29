package com.resservice.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import com.resservice.dto.QuestionDTO;
import com.resservice.dto.QuestionStatsDTO;
import com.resservice.dto.SurveyDTO;
import com.resservice.dto.SurveyStatsDTO;
import com.resservice.feign.QuestionClient;
import com.resservice.feign.SurveyClient;
import com.resservice.models.Response;
import com.resservice.repository.ResponseRepository;
import com.resservice.utils.ApiResponse;

@Service
public class ResponseService extends ICRUDImpl<Response, Long> {
	@Autowired
    private SurveyClient surveyClient;

    @Autowired
    private QuestionClient questionClient;

    @Autowired
    private ResponseRepository repository;


	@Override
	public JpaRepository<Response, Long> getRepository() {
		// TODO Auto-generated method stub
		return repository;
	}
	
	public Response saveResponse(Response response) {
	    // 1. Llama al Feign Client y captura el objeto ApiResponse completo
	    ApiResponse<SurveyDTO> surveyResponse = surveyClient.getSurveyById(response.getSurveyId());
	    QuestionDTO questionResponse = questionClient.getQuestionById(response.getQuestionId());

	    // 2. Extrae el DTO real del campo 'data' de la respuesta
	    SurveyDTO survey = surveyResponse.getData();
	    

	    // 3. El resto de tu lógica de validación funciona igual
	    if (survey == null || questionResponse == null) {
	        // Puedes hacer la validación más específica
	        if (survey == null) throw new RuntimeException("La encuesta con ID " + response.getSurveyId() + " no existe.");
	        if (questionResponse == null) throw new RuntimeException("La pregunta con ID " + response.getQuestionId() + " no existe.");
	    }

	    return repository.save(response);
	}

    
    public List<Response> getResponsesBySurvey(Long surveyId) {
        return repository.findBySurveyId(surveyId);
    }
    
    public SurveyStatsDTO getSurveyStatistics(Long surveyId) {
        // obtener cantidad de preguntas con Feign
        ApiResponse<List<QuestionDTO>> questionsResponse = questionClient.getQuestionsBySurveyId(surveyId);
        List<QuestionDTO> questions = questionsResponse.getData();

        // obtener todas las respuestas para esa encuesta de la BD local
        List<Response> responses = this.getResponsesBySurvey(surveyId);

        // Agrupar respuestas por ID de pregunta para un acceso eficiente
        Map<Long, List<Response>> responsesByQuestionId = responses.stream()
                .collect(Collectors.groupingBy(Response::getQuestionId));

        // Procesar cada pregunta para generar sus estadísticas
        List<QuestionStatsDTO> questionStatsList = questions.stream().map(question -> {
            List<Response> questionResponses = responsesByQuestionId.getOrDefault(question.getId(), new ArrayList<>());
            
            Map<String, Long> answerCounts = new HashMap<>();
            List<String> textResponses = new ArrayList<>();

            if ("select".equalsIgnoreCase(question.getType()) || "radio".equalsIgnoreCase(question.getType())) {
                // Contar la frecuencia de cada respuesta
                answerCounts = questionResponses.stream()
                        .collect(Collectors.groupingBy(Response::getAnswer, Collectors.counting()));
            } else { // Asumimos que es 'text'
                // Simplemente listar las respuestas de texto
                textResponses = questionResponses.stream()
                        .map(Response::getAnswer)
                        .collect(Collectors.toList());
            }

            return new QuestionStatsDTO(
                question.getId(),
                question.getText(),
                question.getType(),
                answerCounts,
                textResponses
            );
        }).collect(Collectors.toList());

        // 5. Obtener los detalles de la encuesta (título) usando Feign
        ApiResponse<SurveyDTO> surveyResponse = surveyClient.getSurveyById(surveyId);
        String surveyTitle = surveyResponse.getData().getTitle();

        // 6. Ensamblar y devolver el DTO final
        return new SurveyStatsDTO(surveyId, surveyTitle, questionStatsList);
    }
    public long count() {
		return repository.count();
	}
    public Set<Long> getCompletedSurveyIdsByUser(String email) {
        // Busca todas las respuestas del usuario y extrae los surveyId únicos
        return repository.findByUserEmail(email)
                         .stream()
                         .map(Response::getSurveyId)
                         .collect(Collectors.toSet());
    }
    public List<Response> getResponsesByUserAndSurvey(String email, Long surveyId) {
        return repository.findByUserEmailAndSurveyId(email, surveyId);
    }
}
