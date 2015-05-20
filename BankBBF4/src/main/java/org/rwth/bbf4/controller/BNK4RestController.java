package org.rwth.bbf4.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.rwth.bbf4.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class BNK4RestController {
	private static final Logger logger = LoggerFactory.getLogger(BNK4RestController.class);

	@Autowired
	private RestService restService;
	public BNK4RestController()
	{
		System.out.println("BNK4RestController initiated");
	}
	@RequestMapping(value="/validate",method = RequestMethod.GET,produces = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> getvalidate() {
		JsonUser user = new JsonUser();
		user.setCardNumber("BNK400000003");
		user.setAmount(100);
		user.setPin("0532");
		return new ResponseEntity<JsonUser>(user,HttpStatus.OK);

	}
	@RequestMapping(value="/validate",method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> validate(@RequestBody  JsonUser user) {
		  //JsonUser user = new JsonUser();
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.validate(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND);

	}

	@RequestMapping(value = "/validate/validateAccountId", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> validateAccountId(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.validateAccountId(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}

	@RequestMapping(value = "/validate/cashWithdraw", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> cashWithdraw( @RequestBody JsonUser user) {
		
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.cashWithdraw(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}	
	
	@RequestMapping(value = "/validate/viewBal", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> viewBal(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.viewBal(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}

	@RequestMapping(value = "/validate/readTxn", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<List<JsonTxnDtls>> readTxn(@RequestBody JsonUser user) {
		List<JsonTxnDtls> jsonTxnDtlsList= new ArrayList<JsonTxnDtls>();
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.readTxn(user);
		}
		else
			return new ResponseEntity<List<JsonTxnDtls>> (jsonTxnDtlsList,HttpStatus.NOT_FOUND); //404 status code

	}
	@RequestMapping(value = "/validate/plcwrtrns", method = RequestMethod.POST,produces = "application/json",consumes = "application/json")
	@ResponseBody
	public ResponseEntity<JsonUser> placeWireTransfer(@RequestBody JsonUser user) {
		List<JsonTxnDtls> jsonTxnDtlsList= new ArrayList<JsonTxnDtls>();
		if(user.getDestAcntId().substring(0, 4).equals("BNK4")){
			if(user.getAmount()>0){
				return restService.plcwrtrnsfr(user);				
			}
			else{
				user.setMsg("Please Give Some amount to credit");
				return new ResponseEntity<JsonUser> (user,HttpStatus.NOT_FOUND); //404 status code				
			}
			
		}
		else{
			user.setMsg("Account is not of our Bank");
			return new ResponseEntity<JsonUser> (user,HttpStatus.NOT_FOUND); //404 status code
		}
			

	}


}
