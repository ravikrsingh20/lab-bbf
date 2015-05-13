package org.rwth.bbf4.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.stereotype.Repository;
@Transactional
@Repository("txnDtlsDao")
public class TxnDtlsDaoImpl extends AbstractDao<TxnDtls> implements TxnDtlsDao {

	@Override
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount) {
		// TODO Auto-generated method stub
		return (List<TxnDtls>) getSession()
				.getNamedQuery("getTxnDtlsByAcntId")
				.setString("acntid", useraccount.getAcntid() ).list();	
	}

	@Override
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount) {
		// TODO Auto-generated method stub
		return (List<TxnDtls>) getSession()
				.getNamedQuery("getTxnDtlsByAcntId")
				.setString("acntid", useraccount.getAcntid() ).list();	
		
	}
	

}