package org.rwth.bbf4.controller;

import java.util.ArrayList;
import java.util.List;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.rwth.bbf4.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/validate")
public class BNK4RestController {
	private static final Logger logger = LoggerFactory.getLogger(BNK4RestController.class);
	
	@Autowired
	private RestService restService;
	public BNK4RestController()
	{
		System.out.println("BNK4RestController initiated");
	}
//	@RequestMapping(value = "/", method = RequestMethod.POST)
//	public ResponseEntity validate(@RequestBody  JsonUser user) {
//		if(user.getMethodName().equals("cashWithdraw")){
//			return (ResponseEntity) cashWithdraw(user);				
//		}else if (user.getMethodName().equals("viewBal")){
//			return (ResponseEntity) viewBal(user);
//		} else if (user.getMethodName().equals("readTxn")){
//			return (ResponseEntity) readTxn(user);
//		}					    
//	}
	@RequestMapping(value = "/cashWithdraw", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> cashWithdraw(@RequestBody  JsonUser user) {
		HttpStatus httpstatus;
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.cashWithdraw(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND);

	}
	@RequestMapping(value = "/viewBal", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> viewBal(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.viewBal(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND);

	}
	@RequestMapping(value = "/readTxn", method = RequestMethod.POST)
	public ResponseEntity<List<JsonTxnDtls>> readTxn(@RequestBody JsonUser user) {
		List<JsonTxnDtls> jsonTxnDtlsList= new ArrayList<JsonTxnDtls>();
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.readTxn(user);
		}
		else
			return new ResponseEntity<List<JsonTxnDtls>> (jsonTxnDtlsList,HttpStatus.NOT_FOUND);
		
	}


}
