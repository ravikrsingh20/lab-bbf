package org.rwth.bbf4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Handles requests for the ATM banking.
 * @author Ravi Kumar Singh
 *
 */
@Controller
public class AtmBnkController {
	private static final Logger logger = LoggerFactory.getLogger(AtmBnkController.class);
	public AtmBnkController()
	{
		System.out.println("AtmBnkController initiated");
	}

}
