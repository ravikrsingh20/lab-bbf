package org.rwth.bbf4.dao;

import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.UserAccount;

public interface CashDetailsDao extends Dao<CashDetails>{

	public abstract CashDetails getCashDetailsAtm(CashDetails cashDetails);

}