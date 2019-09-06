package com.studentservice.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.studentservice.domain.ErrorResponse;

@RestControllerAdvice
public class RestExceptionHandler {

	Logger logger = LoggerFactory.getLogger(this.getClass());

	@ExceptionHandler(value = { IllegalArgumentException.class })
	@ResponseStatus(HttpStatus.BAD_REQUEST)		
	public ErrorResponse badRequest(Exception ex, HttpServletRequest request) {
		logger.error("Request raised: {}, Status: {}, Error Message: {}", request.getRequestURL(),HttpStatus.BAD_REQUEST.value(), ex.getLocalizedMessage());
		return new ErrorResponse(400, "Bad Request", ex.getLocalizedMessage());
	}

	

	@ExceptionHandler(value = { Exception.class })
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
	public ErrorResponse unknownException(Exception ex, HttpServletRequest request) {
		logger.error("Request raised: {}, Status: {}, Error Message: {}", request.getRequestURL(),
				HttpStatus.INTERNAL_SERVER_ERROR.value(), ex.getCause().toString());
		return new ErrorResponse(500, "Internal Server Error",ex.getLocalizedMessage());
	}
}
