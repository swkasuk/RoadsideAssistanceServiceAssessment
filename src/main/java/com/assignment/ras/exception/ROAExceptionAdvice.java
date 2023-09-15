package com.assignment.ras.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class ROAExceptionAdvice {

	@ResponseBody
	@ExceptionHandler(RoadsideServiceException.class)
	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)

	String ROAServiceExceptionHandler(RoadsideServiceException ex) {
		ex.getMessage();
		return ex.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(AssistantNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)

	String AssistantNotFoundExceptionHandler(AssistantNotFoundException e) {
		return e.getMessage();
	}

	@ResponseBody
	@ExceptionHandler(RequestNotValidException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)

	String RequestNotValidExceptionHandler(RequestNotValidException e) {
		return e.getMessage();
	}

}
