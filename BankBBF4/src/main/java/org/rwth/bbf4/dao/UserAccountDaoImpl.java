package org.rwth.bbf4.dao;



import org.rwth.bbf4.model.UserAccount;
import org.springframework.stereotype.Repository;

 
@Repository("userAccountDao")
public class UserAccountDaoImpl  extends AbstractDao<UserAccount> implements UserAccountDao{

	@Override
	@SuppressWarnings("unchecked")
	public UserAccount getAcntByEmail(String email) {
		// TODO Auto-generated method stub
		return (UserAccount)getSession().getNamedQuery("UserAccount").setString("email", "%" + email + "%");		
		
	}	  
	
	
     
}