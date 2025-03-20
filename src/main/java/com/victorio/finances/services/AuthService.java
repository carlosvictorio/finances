package com.victorio.finances.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

import com.victorio.finances.dto.UserDto;
import com.victorio.finances.exceptions.UserAlreadyExistsException;
import com.victorio.finances.models.UserModel;
import com.victorio.finances.repositories.UserRepository;
import com.victorio.finances.security.SecurityConfiguration;

@Service
public class AuthService {
	
	@Autowired
	private UserRepository repository;
	@Autowired
	private AuthenticationManager authenticationManager;
	@Autowired
	private JwtTokenService tokenService;
	@Autowired
	private SecurityConfiguration securityConfiguration ;
	
	public String login(UserDto user) {
		try {
			var usernamePassword = new UsernamePasswordAuthenticationToken(user.username(), user.password());
			var auth = authenticationManager.authenticate(usernamePassword);
			var token = tokenService.generateToken((UserModel)auth.getPrincipal());
			return token;
		}catch(BadCredentialsException e) {
			throw new BadCredentialsException("Credenciais inválidas: ", e);
		}catch(Exception e) {
			throw new RuntimeException("Erro inesperado: " + e.getMessage());
		}
	}		
	
	public void register(UserDto user) {
		if(repository.findByUsername(user.username()) != null) {
			throw new UserAlreadyExistsException();
		}
		String encryptedPassword = securityConfiguration.passwordEncoder().encode(user.password());
		repository.save(new UserModel(user.username(), encryptedPassword));
	}
	
}
