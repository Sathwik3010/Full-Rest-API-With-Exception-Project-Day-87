package com.codegnan.exceptions;

public class MethodArgumentNotValidException extends RuntimeException{

	public MethodArgumentNotValidException(String msg) {
		super(msg);
	}

	public Object getBindingResult() {
		// TODO Auto-generated method stub
		return null;
	}
	
}
