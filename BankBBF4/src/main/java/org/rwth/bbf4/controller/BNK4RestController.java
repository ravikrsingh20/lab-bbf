

package org.rwth.bbf4.controller;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.rwth.bbf4.service.RestService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping(value="/validate")
public class BNK4RestController {
	private static final Logger logger = LoggerFactory.getLogger(BNK4RestController.class);

	@Autowired
	private RestService restService;
	public BNK4RestController()
	{
		System.out.println("BNK4RestController initiated");
	}
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ResponseEntity<JsonUser> getvalidate() {
		JsonUser user = new JsonUser();
		user.setCardNumber("BNK400000003");
		user.setAmount(100);
		user.setPin("0532");
		return new ResponseEntity<JsonUser>(user,HttpStatus.OK);

	}
	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> validate(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.validate(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.OK);

	}
	@RequestMapping(value = "/validateAccountId", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> validateAccountId(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.validateAccountId(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}

	@RequestMapping(value = "/cashWithdraw", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> cashWithdraw(String str) {
		JsonUser user = new JsonUser();
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.cashWithdraw(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}	
	
	@RequestMapping(value = "/validate/viewBal", method = RequestMethod.POST)
	public ResponseEntity<JsonUser> viewBal(@RequestBody  JsonUser user) {
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.viewBal(user);
		}
		else
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 status code

	}

	@RequestMapping(value = "/validate/readTxn", method = RequestMethod.POST)
	public ResponseEntity<List<JsonTxnDtls>> readTxn(@RequestBody JsonUser user) {
		List<JsonTxnDtls> jsonTxnDtlsList= new ArrayList<JsonTxnDtls>();
		if(user.getCardNumber().substring(0, 4).equals("BNK4")){
			return restService.readTxn(user);
		}
		else
			return new ResponseEntity<List<JsonTxnDtls>> (jsonTxnDtlsList,HttpStatus.NOT_FOUND); //404 status code

	}


}
