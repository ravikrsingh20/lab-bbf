package org.rwth.bbf4.service;

import java.util.ArrayList;
import java.util.List;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AtmService {
	/*@Autowired
	private CashDetailsDao CashDetailsDao;
	@Autowired
	private CashDetailsDao TxnDtlsDao;*/
	public UserAccount withdrawCash(UserAccount useraccount) {
		// TODO Auto-generated method stub	
		UserAccount ua  ;
		
		return useraccount;
		
	}
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount){
		List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
		
		return txndtlsList;
	}
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount){
		List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
		
		return txndtlsList;
	}
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount){
		List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
		
		return txndtlsList;
	}

}
