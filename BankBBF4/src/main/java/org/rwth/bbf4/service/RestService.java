package org.rwth.bbf4.service;

import java.util.List;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.springframework.http.ResponseEntity;

public interface RestService {
	public ResponseEntity<JsonUser> cashWithdraw(JsonUser user);
	public ResponseEntity<JsonUser> viewBal(JsonUser user);
	public ResponseEntity<JsonUser> plcwrtrnsfr(JsonUser user);
	public ResponseEntity<List<JsonTxnDtls>> readTxn(JsonUser user);
	public ResponseEntity<JsonUser> validate(JsonUser user) ;
	public ResponseEntity<JsonUser> validateAccountId(JsonUser user);

}
