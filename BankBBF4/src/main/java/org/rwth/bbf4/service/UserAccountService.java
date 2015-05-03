package org.rwth.bbf4.service;

import org.rwth.bbf4.model.UserAccount;

public interface UserAccountService {
	public UserAccount createUserAccount(UserAccount useraccount);
	public UserAccount authorizeUserAccount(UserAccount useraccount);
	public UserAccount updateUserAccount(UserAccount useraccount);
	public UserAccount deleteUserAccount(UserAccount useraccount);

}
