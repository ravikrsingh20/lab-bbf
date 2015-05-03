package org.rwth.bbf4.dao;

import java.util.List;

import org.rwth.bbf4.model.UserAccount;

public interface UserAccountDao extends Dao<UserAccount>{
	public UserAccount getAcntByEmail(String email);
     
}