package com.student.subject.jwtUtils;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException, HttpMessageNotReadableException {
		
		Map<String, String> errorResponse = new HashMap<>();
		String errorMessage = "Access Denied";
		errorResponse.put("Provide Token For Authentication", errorMessage);
        // Use ResponseHandler.generateResponse to create the response
        ResponseEntity<Object> responseEntity = new ResponseEntity<Object>(errorResponse, HttpStatus.UNAUTHORIZED);
        // Set the HTTP response status and content type
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType("application/json");

        // Write the JSON response to the HttpServletResponse
        response.getWriter().write(new ObjectMapper().writeValueAsString(responseEntity.getBody()));
        response.getWriter().flush();
		
	}

}