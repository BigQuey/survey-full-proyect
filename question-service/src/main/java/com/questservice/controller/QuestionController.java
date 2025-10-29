package com.questservice.controller;

import java.util.List;
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

import com.questservice.dto.QuestionDTO;
import com.questservice.models.Question;
import com.questservice.services.QuestionService;
import com.questservice.utils.ApiResponse;
import com.questservice.utils.ModeloNotFoundException;


@RestController
@RequestMapping("/api/questions")
public class QuestionController {
	@Autowired
	private QuestionService servicio;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/hola")
	public String hola() {
		return "Funcionando";
	}
	
	
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<?>> findAll() throws Exception {
		List<QuestionDTO> data = servicio.listarTodos().stream().map(p -> mapper.map(p, QuestionDTO.class))
				.collect(Collectors.toList());
		ApiResponse<List<QuestionDTO>> response = new ApiResponse<>(true, "listado correcto", data);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/find/{codigo}")
	public ResponseEntity<ApiResponse<?>> findById(@PathVariable("codigo") Long cod) throws Exception {
		Question bean = servicio.buscarPorId(cod);
		// validar bean
		if (bean == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		QuestionDTO dtoResponse = mapper.map(bean, QuestionDTO.class);
		ApiResponse<QuestionDTO> response = new ApiResponse<>(true, "Question existe", dtoResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	@GetMapping("/find")
	public ResponseEntity<String> findQuestion() {
	    return ResponseEntity.badRequest().body("El parámetro ID es necesario");
	}
	
	
	@PostMapping("/create")
	public ResponseEntity<ApiResponse<?>> save(@RequestBody QuestionDTO bean) throws Exception {
		// convertir de DTO a entidad
		Question med = mapper.map(bean, Question.class);
		Question m = servicio.registrar(med);
		QuestionDTO dtoResponse = mapper.map(m, QuestionDTO.class);
		ApiResponse<QuestionDTO> response = new ApiResponse<>(true, "Question registrado", dtoResponse);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<?>> update(@RequestBody QuestionDTO bean) throws Exception {
		Question med = servicio.buscarPorId(bean.getId());
		if (med == null)
			throw new ModeloNotFoundException("Código : " + bean.getId() + " no existe");

		Question m = servicio.actualizar(med);
		QuestionDTO dtoResponse = mapper.map(m, QuestionDTO.class);
		ApiResponse<QuestionDTO> response = new ApiResponse<>(true, "Question actualizado", dtoResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{codigo}")
	public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable("codigo") Long cod) throws Exception {
		Question med = servicio.buscarPorId(cod);
		if (med == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		servicio.eliminarPorId(cod);

		ApiResponse<Void> response = new ApiResponse<>(true, "Question eliminado", null);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
	
	@GetMapping("/survey/{surveyId}")
	public ResponseEntity<ApiResponse<List<QuestionDTO>>> getQuestionsBySurvey(@PathVariable Long surveyId) {
	    List<QuestionDTO> questions = servicio.findBySurveyId(surveyId);
	    ApiResponse<List<QuestionDTO>> response = new ApiResponse<>(true, "Preguntas obtenidas", questions);
	    return new ResponseEntity<>(response, HttpStatus.OK);
	}
	
}
