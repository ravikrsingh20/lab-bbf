package org.rwth.bbf4.service;

import javax.transaction.Transactional;

import org.rwth.bbf4.dao.Dao;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.dao.UserAccountDaoImpl;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountDao userAccountDao;
	
	@Override
	@Transactional
	public UserAccount createUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub		
		userAccountDao.create(useraccount);
		 authorizeUserAccount(useraccount);
		return useraccount;
	}

	@Override
	public UserAccount authorizeUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub

		Dao dao = new UserAccountDaoImpl();
		UserAccount ua = new UserAccount();
		ua.setAddress(useraccount.getAddress());
		ua.setEmail(useraccount.getEmail());
		ua.setFname(useraccount.getFname());
		ua.setLname(useraccount.getLname());
		ua.setPhoneno(useraccount.getPhoneno());
		int atmpin = (int)(Math.random()*10000);
		int onlnpin = (int)(Math.random()*100000);
		ua.setAtmPin( String.format("%04d",atmpin));
		ua.setOnlnpin(String.format("%05d",onlnpin));
		ua.setEnabled("ToBAuth");
		
		useraccount.setAtmPin(ua.getAtmPin());
		useraccount.setOnlnpin(ua.getOnlnpin());
		dao.update(useraccount);
		return ua;
	}

	@Override
	public UserAccount updateUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public UserAccount deleteUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub
		return null;
	}

	

}
