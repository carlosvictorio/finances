package com.victorio.finances.services;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.victorio.finances.models.UserModel;

@Service
public class JwtTokenService {

	@Value("${secret.key}")
	private String secret;
	
	private final String issuer = "finance-api";
		
	public String generateToken(UserModel user) {
		Algorithm algorthm = Algorithm.HMAC256(secret);
		
		try {
			String token = JWT
					.create()
					.withIssuer(issuer)
					.withSubject(user.getUsername())
					.withExpiresAt(generateInstant())
					.sign(algorthm);
			
			return token;
		} catch (JWTCreationException e) {
			throw new JWTCreationException("Erro ao gerar token.", e);
		}
		
	}
	
	public String validateToken(String token) {	
		Algorithm algorthm = Algorithm.HMAC256(secret);
		
		try {
			return JWT.require(algorthm)
					.withIssuer(issuer)
					.build()
					.verify(token)
					.getSubject();
		} catch(JWTVerificationException e) {
			throw new JWTVerificationException("ERROR: ", e);
		}
		
	}
	
	private Instant generateInstant() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
	
}
