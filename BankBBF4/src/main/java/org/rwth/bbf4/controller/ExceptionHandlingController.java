package org.rwth.bbf4.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

public class ExceptionHandlingController {
	@ResponseStatus(value=HttpStatus.INTERNAL_SERVER_ERROR)	
	@ExceptionHandler(Exception.class)
	public ModelAndView handleError(HttpServletRequest req, Exception exception) {

		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", exception);
		mav.addObject("url", req.getRequestURL());
		mav.setViewName("error");
		return mav;
	}
	@ResponseStatus(value=HttpStatus.BAD_REQUEST)
	@ExceptionHandler(Throwable.class)
	public String handleThrowable(Throwable t) {
		return "redirect:/error.jsp";
	}

	@ExceptionHandler(Exception.class)
	public String handleException(Throwable t) {
		return "redirect:/error.jsp";
	}
}
