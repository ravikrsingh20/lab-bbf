package org.rwth.bbf4.dao;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.CashDetails;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
@Transactional
@Repository("cashDetailsDao")
public class CashDetailsDaoImpl extends AbstractDao<CashDetails> implements CashDetailsDao {
	
	@Override
	public CashDetails getCashDetailsAtm(CashDetails cashDetails){
		CashDetails cashdetails = new CashDetails();
		
		return cashdetails;
	}

}
