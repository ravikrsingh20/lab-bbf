package org.rwth.bbf4.controller;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;

import javax.validation.Valid;

import org.rwth.bbf4.model.UserAccount;
import org.rwth.bbf4.service.AtmService;
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

/**
 * Handles requests for the ATM banking.
 * @author Ravi Kumar Singh
 *
 */
@Controller
public class AtmBnkController {
	private static final Logger logger = LoggerFactory.getLogger(AtmBnkController.class);
	@Autowired
    private AtmService service;
	public AtmBnkController()
	{
		System.out.println("AtmBnkController initiated");
	}
	
	@RequestMapping(value = "/atmbank", method = RequestMethod.GET)
	public String showAtmPage(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("UserAccount", new UserAccount() );
		
		return "atmpage";
	}
	@RequestMapping(value = "/atmbank/cashwithdrawl", method = RequestMethod.GET)
	public String showWithdrawlPage(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {
				
		
		model.addAttribute("UserAccount", new UserAccount() );
		
		return "cashwithdrawlpage";
	}
	@RequestMapping(value = "/atmbank/cashwithdrawl", method = RequestMethod.POST)
	public String doCashWithdrawl(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {
					
		UserAccount ua = new UserAccount();
		 if (!result.hasErrors()) {
			 ua = service.withdrawCash(useraccount);
		    }		
			model.addAttribute("userAccount", ua);
		return "regsuccess";
		
		return "withdrawlsuccess";
	}
	@RequestMapping(value = "/atmbank/showtxnlog", method = RequestMethod.GET)
	public String getTxnLog(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {
						
		model.addAttribute("UserAccount", new UserAccount() );
		
		return "showtxnlogpage";
	}
	@RequestMapping(value = "/atmbank/readtxnlog", method = RequestMethod.GET)
	public String showTxnLogPage(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {
			
		Date date = new Date();
						
		model.addAttribute("UserAccount", new UserAccount() );
		
		return "readtxnlogpage";
	}
}
