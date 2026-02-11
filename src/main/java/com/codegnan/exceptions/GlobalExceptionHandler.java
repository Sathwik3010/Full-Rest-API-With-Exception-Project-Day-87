package com.codegnan.exceptions;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.orm.ObjectOptimisticLockingFailureException;


@RestControllerAdvice
public class GlobalExceptionHandler {
	private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

//Helper method
	private ResponseEntity<ErrorAPI> buildError(HttpStatus status, String error, String message, String path) {
		ErrorAPI apiError = new ErrorAPI();
		apiError.setLocalDateTime(LocalDateTime.now());
		apiError.setStatus(status.value());
		apiError.setError(error);
		apiError.setMessage(message);
		apiError.setPath(path);
		return new ResponseEntity<>(apiError, status);

	}

//Business Exceptions
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<ErrorAPI> handleUserNotFound(UserNotFoundException ex, HttpServletRequest request) {
		log.warn("User not found:{}", ex.getMessage());
		return buildError(HttpStatus.NOT_FOUND, "User Not Found", ex.getMessage(), request.getRequestURI());
	}

	@ExceptionHandler(RecordAlreadyExistsException.class)
	public ResponseEntity<ErrorAPI> handleDuplicateRecord(RecordAlreadyExistsException ex, HttpServletRequest request) {
		log.warn("Duplicate record: {}", ex.getMessage());
		return buildError(HttpStatus.CONFLICT, "Duplicate Record", ex.getMessage(), request.getRequestURI());
	}

	/*
	 * ============================ VALIDATION EXCEPTIONS
	 * ============================
	 */
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ErrorAPI> handleValidationErrors(MethodArgumentNotValidException ex,
			HttpServletRequest request) {
		String errors = ex.getBindingResult().getFieldErrors().stream()
				.map(err -> err.getField() + ": " + err.getDefaultMessage()).collect(Collectors.joining(", "));
		log.warn("Validation failed: {}", errors);
		return buildError(HttpStatus.BAD_REQUEST, "Validation Failed", errors, request.getRequestURI());
	}

	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ErrorAPI> handleMalformedJson(HttpMessageNotReadableException ex,
			HttpServletRequest request) {
		log.warn("Malformed JSON request", ex);
		return buildError(HttpStatus.BAD_REQUEST, "Invalid Input", "Malformed JSON request", request.getRequestURI());
	}

	/*
	 * ============================ CONCURRENCY (OPTIMISTIC LOCK)
	 * ============================
	 */
	@ExceptionHandler(ObjectOptimisticLockingFailureException.class)
	public ResponseEntity<ErrorAPI> handleOptimisticLocking(ObjectOptimisticLockingFailureException ex,
			HttpServletRequest request) {
		log.warn("Optimistic locking failure", ex);
		return buildError(HttpStatus.CONFLICT, "Concurrent Update",
				"Record was updated by another user. Please refresh and retry.", request.getRequestURI());
	}

	/*
	 * ============================ GENERIC EXCEPTION (LAST)
	 * ============================
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorAPI> handleGenericException(Exception ex, HttpServletRequest request) {
		log.error("Unhandled exception occurred", ex);
		return buildError(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error",
				"An unexpected error occurred. Please contact support.", request.getRequestURI());
	}

}
