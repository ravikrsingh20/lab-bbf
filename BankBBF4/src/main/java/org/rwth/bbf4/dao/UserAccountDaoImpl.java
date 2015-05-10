package org.rwth.bbf4.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.UserAccount;
import org.springframework.stereotype.Repository;

@Transactional
@Repository("userAccountDao")
public class UserAccountDaoImpl extends AbstractDao<UserAccount> implements UserAccountDao{

	@Override
	public List<UserAccount> getUserByAcntId(String acntid) {
		// TODO Auto-generated method stub
		return (List<UserAccount>) getSession()
				.getNamedQuery("getUserByAcntId")
				.setString("acntid", acntid ).list();
	}

	
	
     
}