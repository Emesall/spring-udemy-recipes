package com.emesall.recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.emesall.recipes.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {
	//extends ResponseEntityExceptionHandler// 
	//another way to handle errors with responseentity (http response)
	/*
	 * @ExceptionHandler(NotFoundException.class) protected ResponseEntity<Object>
	 * handleConflict(RuntimeException ex, WebRequest request) { String
	 * bodyOfResponse = "Not found exception";
	 * 
	 * return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(),
	 * HttpStatus.CONFLICT, request); }
	 */

	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ModelAndView handleNotFound(Exception exception) {
		String NOT_FOUND = "404 NOT FOUND";
		return genericExceptionMethod(exception, NOT_FOUND);
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public ModelAndView handleBadRequest(Exception exception) {
		String BAD_REQUEST = "400 BAD REQUEST";
		return genericExceptionMethod(exception, BAD_REQUEST);
	}

	private ModelAndView genericExceptionMethod(Exception exception, String response) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("response", response);
		mav.addObject("message", "Provided input is not a number");
		mav.setViewName("error");
		return mav;
	}

}
