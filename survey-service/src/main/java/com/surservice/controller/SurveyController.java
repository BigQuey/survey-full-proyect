package com.surservice.controller;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.surservice.dto.QuestionDTO;
import com.surservice.dto.SurveyDTO;
import com.surservice.dto.SurveyStatusDTO;
import com.surservice.dto.SurveyWithQuestionsDTO;
import com.surservice.feign.ResponseClient;
import com.surservice.models.Survey;
import com.surservice.services.SurveyService;
import com.surservice.utils.ApiResponse;
import com.surservice.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/api/surveys")
public class SurveyController {
	@Autowired
	private SurveyService servicio;
	@Autowired
	private ModelMapper mapper;
	@Autowired
	private ResponseClient responseClient;
	
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<?>> findAll() throws Exception {
		List<SurveyDTO> data = servicio.listarTodos().stream().map(p -> mapper.map(p, SurveyDTO.class))
				.collect(Collectors.toList());
		ApiResponse<List<SurveyDTO>> response = new ApiResponse<>(true, "listado correcto", data);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/find/{codigo}")
	public ResponseEntity<ApiResponse<?>> findById(@PathVariable("codigo") Long cod) throws Exception {
		Survey bean = servicio.buscarPorId(cod);
		// validar bean
		if (bean == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		SurveyDTO dtoResponse = mapper.map(bean, SurveyDTO.class);
		ApiResponse<SurveyDTO> response = new ApiResponse<>(true, "Survey existe", dtoResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<?>> save(@RequestBody SurveyDTO bean) throws Exception {
		// convertir de DTO a entidad
		Survey med = mapper.map(bean, Survey.class);
		Survey m = servicio.registrar(med);
		SurveyDTO dtoResponse = mapper.map(m, SurveyDTO.class);
		ApiResponse<SurveyDTO> response = new ApiResponse<>(true, "Survey registrado", dtoResponse);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update/{id}")
	public ResponseEntity<ApiResponse<?>> update(@PathVariable Long id, @RequestBody SurveyDTO bean) throws Exception {
		Survey surveyToUpdate = servicio.buscarPorId(id);
	    if (surveyToUpdate == null)
	        throw new ModeloNotFoundException("Código de encuesta: " + id + " no existe");

	    surveyToUpdate.setTitle(bean.getTitle());
	    surveyToUpdate.setDescription(bean.getDescription());

	    // 4. Guarda la entidad ya actualizada
	    Survey updatedSurvey = servicio.actualizar(surveyToUpdate);

	    SurveyDTO dtoResponse = mapper.map(updatedSurvey, SurveyDTO.class);
	    ApiResponse<SurveyDTO> response = new ApiResponse<>(true, "Survey actualizado", dtoResponse);

	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{codigo}")
	public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable("codigo") Long cod) throws Exception {
		Survey med = servicio.buscarPorId(cod);
		if (med == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		servicio.eliminarPorId(cod);

		ApiResponse<Void> response = new ApiResponse<>(true, "Survey eliminado", null);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	@GetMapping("/questions")
	public ResponseEntity<ApiResponse<?>> getQuestionsFromQuestionService() {
		List<QuestionDTO> data = servicio.getAllQuestions();
	    ApiResponse<List<QuestionDTO>> response = new ApiResponse<>(true, "Listado de preguntas correcto", data);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@GetMapping("/{surveyId}/details")
	public ResponseEntity<ApiResponse<SurveyWithQuestionsDTO>> getSurveyDetails(@PathVariable Long surveyId) {
	    SurveyWithQuestionsDTO data = servicio.getSurveyWithQuestions(surveyId);
	    ApiResponse<SurveyWithQuestionsDTO> response = new ApiResponse<>(true, "Encuesta con preguntas obtenida", data);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/count")
    public long countSurveys() {
        return servicio.count(); 
    }
	@GetMapping("/my-list") // Nuevo endpoint para usuarios logueados
	public ResponseEntity<ApiResponse<?>> getMySurveyList() throws Exception {
	    // 1. Obtiene los IDs de las encuestas completadas por el usuario actual
	    Set<Long> completedIds = responseClient.getMyCompletedSurveyIds();

	    // 2. Obtiene todas las encuestas
	    List<Survey> allSurveys = servicio.listarTodos(); 

	    // 3. Mapea al nuevo DTO, añadiendo el estado 'completedByUser'
	    List<SurveyStatusDTO> dtoList = allSurveys.stream().map(survey -> 
	        new SurveyStatusDTO(
	            survey.getId(),
	            survey.getTitle(),
	            survey.getDescription(),
	            completedIds.contains(survey.getId()) // true si el ID está en el Set
	        )
	    ).collect(Collectors.toList());

	    return ResponseEntity.ok(new ApiResponse<>(true, "Lista de encuestas de usuario", dtoList));
	}
}
