package org.rwth.bbf4.controller;

import java.text.DateFormat;
import java.util.Date;
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
import org.springframework.web.context.request.WebRequest;

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
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
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
		    }		
			model.addAttribute("userAccount", ua);
		return "regsuccess";
	}


}
