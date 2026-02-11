package com.codegnan.exceptions;

public class ObjectOptimisticLockingFailureException extends RuntimeException{

	public ObjectOptimisticLockingFailureException(String msg) {
		super(msg);
	}
	
}
