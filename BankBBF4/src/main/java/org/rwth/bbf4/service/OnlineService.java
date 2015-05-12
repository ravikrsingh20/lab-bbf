package org.rwth.bbf4.service;

import java.util.List;

import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;

public interface OnlineService {
	public TxnDtls placeTransferRequest(TxnDtls txndtls);
	public UserAccount authorizeTransferRequest(UserAccount useraccount);
	public UserAccount viewBalance(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount);
	

}
