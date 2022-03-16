package com.emesall.recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

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
	public String handleNotFound(Exception exception,Model model) {
		String message = "404 NOT FOUND";
		String response = exception.getMessage();
		model.addAttribute("message", message);
		model.addAttribute("response", response);
		return "error";
	}

	@ResponseStatus(HttpStatus.BAD_REQUEST)
	@ExceptionHandler(NumberFormatException.class)
	public String handleBadRequest(Exception exception,Model model) {
		String message = "400 Wrong Format ";
		String response = exception.getMessage();
		model.addAttribute("message", message);
		model.addAttribute("response", response);
		return "error";
	}
	

}
