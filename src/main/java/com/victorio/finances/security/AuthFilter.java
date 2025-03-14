package com.victorio.finances.security;

import java.io.IOException;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import com.victorio.finances.models.UserModel;
import com.victorio.finances.repositories.UserRepository;
import com.victorio.finances.services.JwtTokenService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class AuthFilter extends OncePerRequestFilter{
	
	@Autowired
	private JwtTokenService service;
	@Autowired
	private UserRepository repository;

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		
		 // Ignora requisições OPTIONS (preflight requests)
		if("OPTIONS".equalsIgnoreCase(request.getMethod())){
			response.setStatus(HttpServletResponse.SC_OK);
			return;
		}
		
		String token = this.recoverToken(request);
		if(token != null) {
			String subject = service.validateToken(token);
			Optional<UserModel> user = repository.findByUsername(subject);
			UserDetails userModel = user.get();
		
			Authentication auth = new UsernamePasswordAuthenticationToken(userModel.getUsername(), null, userModel.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(auth);
		}
		
		filterChain.doFilter(request, response);
	}
	
	private String recoverToken(HttpServletRequest request) {
		String header = request.getHeader("Authorization");
		if(header == null) return null;
		String token = header.replace("Bearer ", "");
		return token;
	}

}
