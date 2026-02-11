package com.codegnan.exceptions;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorAPI {
	private LocalDateTime localDateTime;
	private int status;
	private String error;
	private String message;
	private String path;

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime localDateTime) {
		this.localDateTime = localDateTime;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getPath() {
		return path;
	}

	public void setPath(String path) {
		this.path = path;
	}

	public ErrorAPI(LocalDateTime localDateTime, int status, String error, String message, String path) {
		super();
		this.localDateTime = localDateTime;
		this.status = status;
		this.error = error;
		this.message = message;
		this.path = path;
	}

	public ErrorAPI() {
		super();
	}

}
