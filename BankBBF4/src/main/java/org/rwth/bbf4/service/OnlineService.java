package org.rwth.bbf4.service;

import java.util.List;

import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;

public interface OnlineService {
	public TxnDtls placeTransferRequest(TxnDtls txndtls);
	public TxnDtls lendMoney2Bank(UserAccount useraccount);
	public UserAccount authorizeTransferRequest(UserAccount useraccount);
	public UserAccount viewBalance(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount);
	public List<CashDetails> getCashDetails(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsAtmEmp(UserAccount useraccount);
	

}
