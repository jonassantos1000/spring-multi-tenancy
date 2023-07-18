package com.app.multitenancy.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.multitenancy.domain.User;
import com.app.multitenancy.domain.request.AuthenticationRequest;
import com.app.multitenancy.domain.request.RegisterRequest;
import com.app.multitenancy.domain.response.AuthenticationResponse;
import com.app.multitenancy.repository.UserRepository;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {
	
	@Autowired
	private AuthenticationManager authenticationManager;
	
	@Autowired
	private UserRepository repository;

	@PostMapping("/login")
	public ResponseEntity<AuthenticationResponse> login(@RequestBody @Valid AuthenticationRequest authRequest) {
		UsernamePasswordAuthenticationToken usernamePassword = new UsernamePasswordAuthenticationToken(
				authRequest.username(), authRequest.password());
		
		var auth = this.authenticationManager.authenticate(usernamePassword);
		return ResponseEntity.ok().build();
	}
	
	@PostMapping("/register")
	public ResponseEntity<Void> register(@RequestBody @Valid RegisterRequest data){
		if(this.repository.findByUsername(data.username()) != null) return ResponseEntity.badRequest().build();
		
		String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
		User newUser = new User(null, data.username(), encryptedPassword, data.name(),data.email(), data.tenant(), data.role());
		this.repository.save(newUser);
		return ResponseEntity.ok().build();
	}
}
