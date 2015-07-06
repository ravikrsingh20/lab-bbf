package org.rwth.bbf4.controller;

import org.rwth.bbf4.model.UserAccount;
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
	public B2BBnkController()
	{
		System.out.println("B2BBnkController initiated");
	}
	@RequestMapping(value = "/b2b/login", method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("UserAccount") UserAccount useraccount, Model model) {
				
		return "loginpage";
	}

}
