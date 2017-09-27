package io.spring.wso2.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.client.HttpClientErrorException;

/**
 * The Class ExceptionHandlerController.
 * 
 * @author izuito
 */
@ControllerAdvice
public class ExceptionHandlerController {

	/**
	 * Classe de tratamento e retorno de erros, ocorridos no servico REST.
	 *
	 * @param hsr
	 *            the hsr
	 * @param ex
	 *            the ex
	 * @return the response entity
	 */
	@ExceptionHandler(value = { HttpClientErrorException.class })
	public @ResponseBody ResponseEntity<?> error(HttpServletRequest hsr, HttpClientErrorException ex) {
		return new ResponseEntity<>(ex.getResponseBodyAsString(), ex.getStatusCode());
	}

}
