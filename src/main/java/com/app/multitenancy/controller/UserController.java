package com.app.multitenancy.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.app.multitenancy.domain.request.UserRequest;
import com.app.multitenancy.domain.response.UserResponse;
import com.app.multitenancy.service.UserService;

@RestController
@RequestMapping("/users")
public class UserController {
	
	@Autowired
	UserService service;

	@Autowired
	private ModelMapper modelMapper;
	
	@PostMapping("/{id}")
	public ResponseEntity<UserResponse> create(@PathVariable String id, @RequestBody UserRequest userRequest) {
		UserResponse response = modelMapper.map(service.create(userRequest), UserResponse.class);
		return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}
	
	@GetMapping
	@PreAuthorize("hasRole('MASTER')  && hasAuthority('READ')")
	public ResponseEntity<Void> list() {
		System.out.println("chamou endpoint");
		return ResponseEntity.noContent().build();
	}
}
