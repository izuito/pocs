package io.spring.wso2am.controller;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;

/**
 * The Class ExceptionHandlerController.
 * 
 * @author izuito
 */
@ControllerAdvice
public class ExceptionHandlerController {

	private static final Logger LOGGER = LoggerFactory.getLogger(ExceptionHandlerController.class);

	@ExceptionHandler(value = { HttpClientErrorException.class, HttpServerErrorException.class })
	public @ResponseBody ResponseEntity<?> error(HttpServletRequest hsr, HttpClientErrorException ex) {
		LOGGER.info("* Fail Response: {}", ex);
		return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
	}

}
