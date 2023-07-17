package com.app.multitenancy.service;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.multitenancy.domain.User;
import com.app.multitenancy.domain.request.UserRequest;
import com.app.multitenancy.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	UserRepository repository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	public User create(UserRequest userRequest) {
		User user = modelMapper.map(userRequest, User.class);
		return repository.save(user);
	}

}
