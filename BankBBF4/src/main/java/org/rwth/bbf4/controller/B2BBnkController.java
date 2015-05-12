package org.rwth.bbf4.controller;

import org.rwth.bbf4.model.UserAccount;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the bank to bank transfer.
 * @author Ravi Kumar Singh
 *
 */
@Controller
public class B2BBnkController {
	private static final Logger logger = LoggerFactory.getLogger(B2BBnkController.class);
	public B2BBnkController()
	{
		System.out.println("B2BBnkController initiated");
	}
	@RequestMapping(value = "/b2b/login", method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("UserAccount") UserAccount useraccount, Model model) {
				
		return "loginpage";
	}

}
