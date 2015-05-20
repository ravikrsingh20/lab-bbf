package org.rwth.bbf4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.rwth.bbf4.model.TxnDtls;
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
	private AtmService atmService;
	public AtmBnkController()
	{
		System.out.println("AtmBnkController initiated");
	}

	@RequestMapping(value = "/atmbank", method = RequestMethod.GET)
	public String showAtmPage(@ModelAttribute("UserAccount") UserAccount useraccount, Model model) {

		return "atmpage";
	}
	
	@RequestMapping(value = "/atmbank/cashwithdrawl", method = RequestMethod.GET)
	public String getWithdrawCash(Model model) {
		model.addAttribute("UserAccount",new UserAccount());
		return "cashwithdrawlpage";				
	}

	@RequestMapping(value = "/atmbank/cashwithdrawl", method = RequestMethod.POST)
	public String postWithdrawCash(@ModelAttribute("UserAccount") @Valid UserAccount useraccount,BindingResult result, Errors errors,Model model) {
		//useraccount is not null and has bankname and atm name
		UserAccount ua = new UserAccount();
		// check if balance is not 0
		if(useraccount.getBnkname().equals("BANK4")){
			if (useraccount.getAtmpin().length() != 4){
				ua.setMsg("Enter 4 digit pin");
				model.addAttribute("UserAccount",ua);	
				return "cashwithdrawlpage";

			}

			if (useraccount.getAcntid().length() != 12){
				ua.setMsg("Enter 12 digit iban number");
				model.addAttribute("UserAccount",ua);	
				return "cashwithdrawlpage";
			}

			if(useraccount.getAmt() == 0){	
				ua.setMsg("Enter some amount to withdraw");
				model.addAttribute("UserAccount",ua);
				return "cashwithdrawlpage";

			} 	
			
		}						

		ua = atmService.withdrawCash(useraccount);				
		model.addAttribute("userAccount", ua);	
		return "withdrawlsuccess";	
	}

	@RequestMapping(value = "/atmbank/readtxnlog", method = RequestMethod.GET)
	public String getReadTxnLog(Model model) {
		model.addAttribute("UserAccount", new UserAccount() );
		return "readtxnlogpage";

	}

	@RequestMapping(value = "/atmbank/readtxnlog", method = RequestMethod.POST)
	public String postReadTxnLog(@ModelAttribute("UserAccount") @Valid UserAccount useraccount, BindingResult result, Errors errors,Model model) {

		List<TxnDtls> txndtlslist = new ArrayList<TxnDtls>();	
		UserAccount ua = new UserAccount();		
		// checks msg if there is some error msg like pin doesnot match
		if(useraccount.getBnkname().equals("BANK4")){
			if (useraccount.getAtmpin().length() != 4){
				ua.setMsg("Enter 4 digit atm pin");
				model.addAttribute("UserAccount", ua );
				return "readtxnlogpage";

			}

			if (useraccount.getAcntid().length() != 12){
				ua.setMsg("Enter 12 digit iban no.");
				model.addAttribute("UserAccount", ua );
				return "readtxnlogpage";
			}
			
		}		
		
		//get log
		txndtlslist = atmService.getTxnDtlsAtm(useraccount);
		if (useraccount.getMsg().equals("OK")) {
			model.addAttribute("TxnDtlsList", txndtlslist );
			model.addAttribute("UserAccount", useraccount );
			return "showtxnlogpage";
		}			
		else{
			ua.setMsg(useraccount.getMsg());
			model.addAttribute("UserAccount", ua );
			return "readtxnlogpage"; 
		}

	}

	@RequestMapping(value = "/atmbank/viewbal", method = RequestMethod.GET)
	public String getViewBal(Model model) {
		model.addAttribute("UserAccount",new UserAccount());
		return "viewbalance";				
	}

	@RequestMapping(value = "/atmbank/viewbal", method = RequestMethod.POST)
	public String postViewBal(@ModelAttribute("UserAccount") @Valid UserAccount useraccount,BindingResult result, Errors errors,Model model) {
		//useraccount is not null and has bankname and atm name
		UserAccount ua = new UserAccount();
		// check if balance is not 0
		if(useraccount.getBnkname().equals("BANK4")){
			if (useraccount.getAtmpin().length() != 4){
				ua.setMsg("Enter 4 digit pin");
				model.addAttribute("UserAccount",ua);	
				return "viewbalance";

			}

			if (useraccount.getAcntid().length() != 12){
				ua.setMsg("Enter 12 digit iban number");
				model.addAttribute("UserAccount",ua);	
				return "viewbalance";
			}				

			
		}
		
		ua = atmService.viewBalance(useraccount);				
		model.addAttribute("userAccount", ua);	
		return "showbalance";	
	}

}
