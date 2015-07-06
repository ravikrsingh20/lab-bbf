package org.rwth.bbf4.configuration;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

@ControllerAdvice
public class GlobalDefaultExceptionHandler {
	public static final String DEFAULT_ERROR_VIEW = "error";

	@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)  // 500
	public ModelAndView defaultErrorHandler500(HttpServletRequest req, Exception e) throws Exception {		
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("reason", "Internal error");
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
	@ResponseStatus(HttpStatus.METHOD_NOT_ALLOWED)  // 500
	public ModelAndView defaultErrorHandler405(HttpServletRequest req, Exception e) throws Exception {		
		// Otherwise setup and send the user to a default error-view.
		ModelAndView mav = new ModelAndView();
		mav.addObject("exception", e);
		mav.addObject("reason", "Method not allowed");
		mav.addObject("url", req.getRequestURL());
		mav.setViewName(DEFAULT_ERROR_VIEW);
		return mav;
	}
}

