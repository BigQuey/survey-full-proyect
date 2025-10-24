package com.example.demo.controller;

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

import com.example.demo.dto.UserDTO;
import com.example.demo.entity.User;
import com.example.demo.services.UserService;
import com.example.demo.utils.ApiResponse;
import com.example.demo.utils.ModeloNotFoundException;



@RestController
@RequestMapping("/users")
public class UserController {
	@GetMapping("/hello")
	public String hello() {
		return "Servicio funcionando correctamente";
	}
	@Autowired
	private UserService servicio;
	@Autowired
	private ModelMapper mapper;
	
	@GetMapping("/list")
	public ResponseEntity<ApiResponse<?>> findAll() throws Exception {
		List<UserDTO> data = servicio.listarTodos().stream().map(p -> mapper.map(p, UserDTO.class))
				.collect(Collectors.toList());
		ApiResponse<List<UserDTO>> response = new ApiResponse<>(true, "listado correcto", data);
		return new ResponseEntity<>(response, HttpStatus.OK);

	}

	@GetMapping("/find/{codigo}")
	public ResponseEntity<ApiResponse<?>> findById(@PathVariable("codigo") Long cod) throws Exception {
		User bean = servicio.buscarPorId(cod);
		// validar bean
		if (bean == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		UserDTO dtoResponse = mapper.map(bean, UserDTO.class);
		ApiResponse<UserDTO> response = new ApiResponse<>(true, "User existe", dtoResponse);
		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@PostMapping("/create")
	public ResponseEntity<ApiResponse<?>> save(@RequestBody UserDTO bean) throws Exception {
		// convertir de DTO a entidad
		User med = mapper.map(bean, User.class);
		User m = servicio.registrar(med);
		UserDTO dtoResponse = mapper.map(m, UserDTO.class);
		ApiResponse<UserDTO> response = new ApiResponse<>(true, "User registrado", dtoResponse);

		return new ResponseEntity<>(response, HttpStatus.CREATED);
	}

	@PutMapping("/update")
	public ResponseEntity<ApiResponse<?>> update(@RequestBody UserDTO bean) throws Exception {
		User med = servicio.buscarPorId(bean.getId());
		if (med == null)
			throw new ModeloNotFoundException("Código : " + bean.getId() + " no existe");

		User m = servicio.actualizar(med);
		UserDTO dtoResponse = mapper.map(m, UserDTO.class);
		ApiResponse<UserDTO> response = new ApiResponse<>(true, "User actualizado", dtoResponse);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}

	@DeleteMapping("/delete/{codigo}")
	public ResponseEntity<ApiResponse<?>> deleteById(@PathVariable("codigo") Long cod) throws Exception {
		User med = servicio.buscarPorId(cod);
		if (med == null)
			throw new ModeloNotFoundException("Código : " + cod + " no existe");

		servicio.eliminarPorId(cod);

		ApiResponse<Void> response = new ApiResponse<>(true, "User eliminado", null);

		return new ResponseEntity<>(response, HttpStatus.OK);
	}
}
