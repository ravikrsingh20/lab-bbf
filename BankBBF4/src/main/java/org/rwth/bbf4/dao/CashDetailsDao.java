package org.rwth.bbf4.dao;

import java.util.List;

import org.rwth.bbf4.model.CashDetails;

public interface CashDetailsDao extends Dao<CashDetails>{

	public abstract CashDetails getCashDetailsAtm(CashDetails cashDetails);
	public List<CashDetails> findAll();

}