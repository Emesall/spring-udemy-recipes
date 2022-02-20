package com.emesall.recipes.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.emesall.recipes.exceptions.NotFoundException;

@ControllerAdvice
public class ExceptionHandlingController {

	
	@ExceptionHandler({NotFoundException.class,NumberFormatException.class})
	public ModelAndView handleNotFound(Exception exception) {
		String message="";
		HttpStatus status=null;
		
		if(exception instanceof NotFoundException) {
			message="404 NOT FOUND";
			status=HttpStatus.NOT_FOUND;
		}
		if(exception instanceof NumberFormatException) {
			message="400 BAD REQUEST";
			status=HttpStatus.BAD_REQUEST;
		}
	
		return genericExceptionMethod(exception, message, status);
	}

	
	private ModelAndView genericExceptionMethod(Exception exception, String response,HttpStatus status) {
		ModelAndView mav = new ModelAndView();
		mav.addObject("response", response);
		mav.addObject("message", exception.getMessage());
		mav.setViewName("error");
		mav.setStatus(status);
		return mav;
	}

}
