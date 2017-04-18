package com.ermes.api.controllers;

import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class ExceptionController
{
	/*@ExceptionHandler(value = {Exception.class, RuntimeException.class})
	@ResponseBody
	public ResponseEntity<?> handleException(Exception e)
	{
		e.printStackTrace();
		ApiError apiError = new ApiError(e.getClass().getSimpleName(), e.toString());
		return new ResponseEntity<>(apiError, HttpStatus.INTERNAL_SERVER_ERROR);
	}*/
}
