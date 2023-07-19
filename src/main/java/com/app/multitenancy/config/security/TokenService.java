package com.app.multitenancy.config.security;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.app.multitenancy.domain.User;
import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class TokenService {
	
	@Value("${api.security.jwt.secret}")
	private String secretJwt;

	public String generateToken(User user) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretJwt);
			String token = JWT.create()
					.withIssuer("auth-api-tenant")
					.withSubject(user.getUsername())
					.withExpiresAt(generateExpirationDate())
					.sign(algorithm);
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error while generating token", e);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secretJwt);
			return JWT.require(algorithm)
					.withIssuer("auth-api-tenant")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
	}
	
	private Instant generateExpirationDate() {
		return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
	}
}
