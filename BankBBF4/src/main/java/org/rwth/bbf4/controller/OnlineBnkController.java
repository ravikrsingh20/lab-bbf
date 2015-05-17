package org.rwth.bbf4.controller;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.rwth.bbf4.service.OnlineService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * Handles requests for the application Online Banking.
 * @author Ravi Kumar Singh
 *
 */
@Controller
public class OnlineBnkController {
	private static final Logger logger = LoggerFactory.getLogger(OnlineBnkController.class);
	@Autowired
	private OnlineService onlnService;
	public OnlineBnkController()
	{
		System.out.println("OnlineBnkController initiated");
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String showLogin(@ModelAttribute("UserAccount") UserAccount useraccount, Model model) {
				
		return "login";
	}
	
	// handling for customer
	@RequestMapping(value = "/onln/", method = RequestMethod.GET)
	public String showCustOnlnBank(@ModelAttribute("UserAccount") UserAccount useraccount, Model model) {
				
		return "onlinecust";
	} 
	@RequestMapping(value = "/onln/plcwrtrns", method = RequestMethod.GET)
	public String getPlaceWireTransfer(@ModelAttribute("TxnDtls") TxnDtls txndtls , Model model) {
				
		return "placewirerequestpage";
	}
	
	@RequestMapping(value = "/onln/plcwrtrns", method = RequestMethod.POST)
	public String postPlaceWireTransfer(@ModelAttribute("TxnDtls") TxnDtls txndtls , Model model, Principal principal) {
		TxnDtls txndtlret = new TxnDtls();
		txndtls.setTxnacntid(principal.getName());
		if(txndtls.getTxnamt() > 0)
		txndtlret = onlnService.placeTransferRequest(txndtls);
		else
			txndtlret.setMsg("Please enter some amount to tranfer");
		model.addAttribute("TxnDtls", txndtlret );
		return "plcwirereqscs";
	}
	
	@RequestMapping(value = "/onln/viewtrns", method = RequestMethod.GET)
	public String viewTransaction(Model model, Principal principal) {
		List<TxnDtls> txndtlslist = new ArrayList<TxnDtls>();	
		UserAccount ua = new UserAccount();
		ua.setAcntid(principal.getName());
		txndtlslist = onlnService.getTxnDtlsOnln(ua);
		// checks msg if there is some error msg like pin doesnot match
				
		if (ua.getMsg().equals("OK")) {
			model.addAttribute("TxnDtlsList", txndtlslist );
			model.addAttribute("UserAccount", ua );
			return "showonlntxnlogpage";
		}			
		else{
			model.addAttribute("UserAccount", ua );
			return "onlinecust"; 
		}
	}
	@RequestMapping(value = "/onln/viewbal", method = RequestMethod.GET)
	public String viewBalance( Model model,Principal principal) {
		UserAccount ua = new UserAccount();
		ua.setAcntid(principal.getName());
		ua = onlnService.viewBalance(ua);
		model.addAttribute("UserAccount", ua);
				
		return "showblnconln";
	}
	
	
	// handling for employee
	@RequestMapping(value = "/emp/", method = RequestMethod.GET)
	public String showEmpBank( Model model) {
				
		return "onlineemp";
	}
	// transfer money hold status from place wire transfers
	@RequestMapping(value = "/emp/trnsmnyb2b", method = RequestMethod.GET)
	public String transferMoneyB2B( Model model) {
				
		return "onlineemp";
	}
	// allow only admin to do so
	@RequestMapping(value = "/emp/lndmny", method = RequestMethod.GET)
	public String lendMoney2BankGet(Model model,Principal principal,@ModelAttribute("TxnDtls") TxnDtls txndtls) {
		
		return "lendmoney2bankscs";
	}
	@RequestMapping(value = "/emp/lndmny", method = RequestMethod.POST)
	public String lendMoney2BankPost(Model model,Principal principal,@ModelAttribute("UserAccount") UserAccount useraccount) {
		TxnDtls txndtls = new TxnDtls();
		useraccount.setAcntid(principal.getName());		
		if(useraccount.getAmt() > 0)
			txndtls = onlnService.lendMoney2Bank(useraccount);
		else
			txndtls.setMsg("Please enter some amount to tranfer");
		model.addAttribute("TxnDtls", txndtls );
		
		return "lendmoney2bankscs";
	}
	@RequestMapping(value = "/emp/viewatmlog", method = RequestMethod.GET)
	public String viewAtmLogGet( Model model,Principal principal) {
				
		return "showempatmlog";
	}
	@RequestMapping(value = "/emp/viewatmlog", method = RequestMethod.POST)
	public String viewAtmLogPost( Model model,Principal principal,@ModelAttribute("UserAccount") UserAccount useraccount) {
		List<TxnDtls> txndtlslist = new ArrayList<TxnDtls>();	
		
		useraccount.setAcntid(principal.getName());
		txndtlslist = onlnService.getTxnDtlsB2B(useraccount);
		// checks msg if there is some error msg like pin doesnot match
				
		if (useraccount.getMsg().equals("OK")) {
			model.addAttribute("TxnDtlsList", txndtlslist );
			model.addAttribute("UserAccount", useraccount );
			return "empatmlog";
		}			
		else{
			model.addAttribute("UserAccount", useraccount );
			return "onlinecust"; 
		}
	}
	//admin
	@RequestMapping(value = "/emp/viewbnkmoney", method = RequestMethod.GET)
	public String viewBnkMoney( Model model,Principal principal,@ModelAttribute("UserAccount") UserAccount useraccount) {
		useraccount.setAcntid(principal.getName());
		List<CashDetails> cdlist = new ArrayList<CashDetails>();
		cdlist = onlnService.getCashDetails(useraccount);
		if(useraccount.getMsg().equals("OK")){
			model.addAttribute("CashDetailsList", cdlist );
			return "showCashDetails.jsp";
		}
				
		return "onlineemp";
	}
}
