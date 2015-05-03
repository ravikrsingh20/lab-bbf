package org.rwth.bbf4.dao;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.UserAccount;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("userAccountDao")
public class UserAccountDaoImpl extends AbstractDao<UserAccount> implements UserAccountDao{

	@Override
	public UserAccount getAcntByEmail(String email) {
		// TODO Auto-generated method stub
		return (UserAccount) getSession()
				.getNamedQuery("getAcntByEmail")
				.setString("email", "%" + email + "%");
	}

	
	
     
}