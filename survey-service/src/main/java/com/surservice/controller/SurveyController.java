package com.surservice.controller;

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

import com.surservice.dto.SurveyDTO;
import com.surservice.models.Survey;
import com.surservice.services.SurveyService;
import com.surservice.utils.ApiResponse;
import com.surservice.utils.ModeloNotFoundException;

@RestController
@RequestMapping("/surv")
public class SurveyController {
	@Autowired
	private SurveyService servicio;
	@Autowired
	private ModelMapper mapper;
	
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

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<?>> update(@RequestBody SurveyDTO bean) throws Exception {
		Survey med = servicio.buscarPorId(bean.getId());
		if (med == null)
			throw new ModeloNotFoundException("Código : " + bean.getId() + " no existe");

		Survey m = servicio.actualizar(med);
		SurveyDTO dtoResponse = mapper.map(m, SurveyDTO.class);
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
}
