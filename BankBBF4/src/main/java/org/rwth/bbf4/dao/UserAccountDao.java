package org.rwth.bbf4.dao;

import java.util.List;

import org.rwth.bbf4.model.AccountRole;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.UserAccount;

public interface UserAccountDao extends Dao<UserAccount>{
	public List<UserAccount> getUserByAcntId(String acntid);
	public AccountRole createAccountRole(AccountRole ar);
	public String getAccountRoleByAcntId(String acntid);
	public CashDetails getCashDetailsId(int id);
     
}