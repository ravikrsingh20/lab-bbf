package org.rwth.bbf4.controller;

import java.util.Locale;

import javax.validation.Valid;

import org.rwth.bbf4.model.UserAccount;
import org.rwth.bbf4.service.UserAccountService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class AccountController {
	
	private static final Logger logger = LoggerFactory.getLogger(AccountController.class);
	@Autowired
    private UserAccountService service;
	public AccountController()
	{
		System.out.println("AccountController initiated");
	}
	
	/**
	 * returns registration page for bank customer
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.GET)
	public String showRegisterForm(Locale locale, Model model) {			
		model.addAttribute("UserAccount", new UserAccount() );		
		return "register";
	}
	
	/**
	 * performs user registration
	 * @param locale
	 * @param model
	 * @return
	 */
	@RequestMapping(value = "/register", method = RequestMethod.POST)
	public String doRegistration(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {
		
		UserAccount ua = new UserAccount();
		 if (!result.hasErrors()) {
			 ua = service.createUserAccount(useraccount);
			 if (ua.getMsg().contains("Sorry!!")){
				 model.addAttribute("userAccount", useraccount);
				 return "register";
			 }
		    }	else {
		    	model.addAttribute("userAccount", useraccount);
		    	return "register";	
		    }
			model.addAttribute("userAccount", ua);
		return "regsuccess";
	}
	@RequestMapping(value = "/403", method = RequestMethod.GET)
	public String accessdenied(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		return "403";
	}
	@RequestMapping(value = "/error", method = RequestMethod.GET)
	public String error() {
		return "error";
	}

}
