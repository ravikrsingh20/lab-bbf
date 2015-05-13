package org.rwth.bbf4.service;

import java.util.List;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.springframework.http.ResponseEntity;

public interface RestService {
	ResponseEntity<JsonUser> cashWithdraw(JsonUser user);
	ResponseEntity<JsonUser> viewBal(JsonUser user);
	ResponseEntity<List<JsonTxnDtls>> readTxn(JsonUser user);

}
