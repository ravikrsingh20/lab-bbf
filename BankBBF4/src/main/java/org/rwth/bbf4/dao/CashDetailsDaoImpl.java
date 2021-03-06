package org.rwth.bbf4.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.CashDetails;
import org.springframework.stereotype.Repository;
@Transactional
@Repository("cashDetailsDao")
public class CashDetailsDaoImpl extends AbstractDao<CashDetails> implements CashDetailsDao {

	@Override
	public CashDetails getCashDetailsAtm(CashDetails cashDetails){
		CashDetails cashdetails = new CashDetails();

		return cashdetails;
	}
	@SuppressWarnings("unchecked")
	@Override
	public List<CashDetails> findAll() {
		return (List<CashDetails>) getSession().createQuery("from CashDetails").list();
	}
	@Override
	public CashDetails getCashDetailsbyId(int id) {
		// TODO Auto-generated method stub
		return (CashDetails) getSession().createQuery("from Cashdetails where Id=:id").setParameter("id", id).list().get(0);
	}
}
