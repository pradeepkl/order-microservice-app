package com.classpathio.orders.exception;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(exception = IllegalArgumentException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Error handleInvalidOrderIdExceptions(IllegalArgumentException exception) {
		return new Error(100L, exception.getMessage());
		
	}
	
	@ExceptionHandler(exception = MethodArgumentNotValidException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<Error, Set<String>> handleInvalidOrded(MethodArgumentNotValidException exception) {
		
		Map<Error, Set<String>> errorMap = new LinkedHashMap<>();
		
		List<ObjectError> allErrors = exception.getAllErrors();
		Set<String> errors = allErrors.stream().map(error -> error.getDefaultMessage()).collect(Collectors.toSet());
		errorMap.put(new Error(200L, "validation error"), errors);
		return errorMap;
	}
}

class Error {
	private Long code;
	private String message;
	
	public Error(Long code, String message) {
		this.code = code;
		this.message = message;
	}

	public Long getCode() {
		return code;
	}

	public String getMessage() {
		return message;
	}

	@Override
	public String toString() {
		return "Error [code=" + code + ", message=" + message + "]";
	}
}
