package com.example.demo.services;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.dto.LoginRequest;
import com.example.demo.dto.RegisterRequest;
import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;



@Service
public class AuthService {
	private final UserRepository userRepository;
	private final JwtService jwtService;
	private final  PasswordEncoder passwordEncoder;
	private final UserDetailsService userDetailsService;
	
	public AuthService(UserRepository userRepository, JwtService jwtService, PasswordEncoder passwordEncoder, UserDetailsService userDetailsService ) {
	    this.userRepository = userRepository;
	    this.jwtService = jwtService;
	    this.passwordEncoder = passwordEncoder;
		this.userDetailsService = userDetailsService;
	}
	
	public String register(RegisterRequest request) {
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new RuntimeException("El usuario ya existe");
        }

        User user = new User();
        user.setName(request.getName());
        user.setEmail(request.getEmail());
        user.setPassword(passwordEncoder.encode(request.getPassword()));

        String role = request.getRole() != null ? request.getRole() : "ROLE_USUARIO";
        user.setRole(role);
        userRepository.save(user);

        return jwtService.generateToken(user);
    }

    public String login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new RuntimeException("Contraseña incorrecta");
        }
        return jwtService.generateToken(user);
    }
}
