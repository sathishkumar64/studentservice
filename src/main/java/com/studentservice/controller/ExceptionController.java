package com.studentservice.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class ExceptionController {
	
	
	Logger logger = LoggerFactory.getLogger(this.getClass());	

	@ExceptionHandler
	void handleException(HttpServletRequest request,Exception e, HttpServletResponse response) throws IOException {		
		 logger.error("Request: {} raised " , request.getRequestURL(), HttpStatus.BAD_REQUEST.value(),e.getLocalizedMessage());		
		 response.sendError(HttpStatus.BAD_REQUEST.value(),e.getLocalizedMessage());
	}
}
