package org.rwth.bbf4.service;

import java.util.List;

import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;


public interface AtmService {
	public UserAccount withdrawCash(UserAccount useraccount);
	public UserAccount viewBalance(UserAccount useraccount);
	public boolean validateAccount(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount);
	public List<JsonTxnDtls> getTxnDtlsAtmOB(UserAccount useraccount);
	
}
