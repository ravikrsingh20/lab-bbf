package org.rwth.bbf4.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;

/**
 * Handles requests for the application Online Banking.
 * @author Ravi Kumar Singh
 *
 */
@Controller
public class OnlineBnkController {
	private static final Logger logger = LoggerFactory.getLogger(OnlineBnkController.class);
	public OnlineBnkController()
	{
		System.out.println("OnlineBnkController initiated");
	}

}
