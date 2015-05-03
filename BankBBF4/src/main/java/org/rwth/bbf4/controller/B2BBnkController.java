package org.rwth.bbf4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

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

}
