package com.assignment.ras.exception;

public class RequestNotValidException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RequestNotValidException(String msg) {
		super("Request Not valid  Exception :  " + msg);
	}
}
