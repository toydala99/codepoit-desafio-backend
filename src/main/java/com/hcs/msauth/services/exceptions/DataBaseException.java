package com.hcs.msauth.services.exceptions;



public class DataBaseException extends RuntimeException{
	private static final Long serialVersionUID = 1L;
	
	public DataBaseException(String msg) {
		super(msg);
	}
}
