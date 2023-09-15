package com.assignment.ras.exception;

public class RoadsideServiceException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public RoadsideServiceException(String msg) {
		super("Service Exception " + msg);
	}
}