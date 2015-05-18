package org.rwth.bbf4.dao;

import java.util.List;

import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;

public interface TxnDtlsDao extends Dao<TxnDtls>{
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsbytxntyp(String acntid,String txntyp);
	public List<TxnDtls> getTxnDtlsbyAtmNm(String atmName,String txntyp);

}