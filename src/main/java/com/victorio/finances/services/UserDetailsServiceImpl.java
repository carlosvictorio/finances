package com.victorio.finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.victorio.finances.exceptions.EntityNotFoundException;
import com.victorio.finances.repositories.UserRepository;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {
	
	@Autowired
	private UserRepository repository;

	@Override
	public UserDetails loadUserByUsername(String username) {
		return repository.findByUsername(username).orElseThrow(() -> new EntityNotFoundException("User"));
	}

}
