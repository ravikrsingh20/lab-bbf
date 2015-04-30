package org.rwth.bbf4.dao;

import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.rwth.bbf4.model.User;
import org.springframework.stereotype.Repository;

 
@Repository("userDao")
public class UserDaoImpl extends AbstractDao implements UserDao{
      
	@Override
	public void saveUser(User user) {
		// TODO Auto-generated method stub
		 persist(user);
		
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<User> findAllUser() {
		// TODO Auto-generated method stub
		Criteria criteria = getSession().createCriteria(User.class);
        return (List<User>) criteria.list();
	}

	@Override
	public void deleteUserByAcntID(String AcntId) {
		// TODO Auto-generated method stub
		Query query = getSession().createSQLQuery("delete from User where AcntId = :AcntId");
        query.setString("AcntId", AcntId);
        query.executeUpdate();
		
	}
     
}