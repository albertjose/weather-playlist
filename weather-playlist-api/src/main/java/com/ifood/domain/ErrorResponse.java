package com.ifood.domain;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {

	private int statusCode;
	private String message;
	private String error;

	public ErrorResponse() {
		super();
	}

	public ErrorResponse(int statusCode, String message, String error) {
		super();
		this.statusCode = statusCode;
		this.message = message;
		this.error = error;
	}

	public int getStatusCode() {
		return statusCode;
	}

	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
