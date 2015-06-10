package org.rwth.bbf4.dao;

import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.model.AccountRole;
import org.rwth.bbf4.model.CashDetails;
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

	@Override
	public AccountRole createAccountRole(AccountRole ar) {
		// TODO Auto-generated method stub
		getSession().save(ar);
		return ar;
	}

	@Override
	public String getAccountRoleByAcntId(String acntid) {
		// TODO Auto-generated method stub
		return (String) getSession().createQuery("SELECT rlnm from AccountRole where acntId=:acntId").setParameter("acntId", acntid).list().get(0);
		}

	@Override
	public CashDetails getCashDetailsId(int id) {
		// TODO Auto-generated method stub
		return (CashDetails) getSession().get(CashDetails.class, id);
	}
	
     
}