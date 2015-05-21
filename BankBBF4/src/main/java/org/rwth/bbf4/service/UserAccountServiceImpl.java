package org.rwth.bbf4.service;

import javax.transaction.Transactional;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.model.AccountRole;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class UserAccountServiceImpl implements UserAccountService {
	
	@Autowired
	UserAccountDao userAccountDao;
	@Autowired
	CashDetailsDao cashDetailsDao;
	
	@Override
	@Transactional
	public UserAccount createUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub	
		UserAccount ua ;
		userAccountDao.create(useraccount);
		ua = authorizeUserAccount(useraccount);
		return ua;
	}

	@Override
	@Transactional
	public UserAccount authorizeUserAccount(UserAccount useraccount) {
		// TODO Auto-generated method stub
		UserAccount ua = new UserAccount();
		AccountRole ar = new AccountRole();
		CashDetails cashDetails = new CashDetails();	
		TxnDtls txnDtlstmpdest = new TxnDtls();
		java.util.Date date= new java.util.Date();
		
		//useraccount = userAccountDao.getAcntByEmail(useraccount.getEmail());
		int atmpin = (int)(Math.random()*10000);
		int onlnpin = (int)(Math.random()*100000);
		useraccount.setAtmpin( String.format("%04d",atmpin));
		useraccount.setOnlnpin(String.format("%05d",onlnpin));
		useraccount.setEnabled(true);
		String acntId = String.format("%08d",useraccount.getId());
		useraccount.setAcntid("BNK4" + acntId);
		
		ua.setFname(useraccount.getFname());
		ua.setLname(useraccount.getLname());
		ua.setAcntid(useraccount.getAcntid());
		ua.setAtmpin(useraccount.getAtmpin());
		ua.setOnlnpin(useraccount.getOnlnpin());
		ua.setBalance(useraccount.getBalance());
		
		PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
		useraccount.setAtmpin(passwordEncoder.encode(useraccount.getAtmpin())); 
		useraccount.setOnlnpin(passwordEncoder.encode(useraccount.getOnlnpin()));
		userAccountDao.update(useraccount);
//		System.out.println(passwordEncoder.encode("B@nk4P@ssAdm9#1"));
//		System.out.println(passwordEncoder.encode("B@nk4P@ssAdm9#2"));
//		System.out.println(passwordEncoder.encode("B@nk4P@ssAdm9#3"));
//		System.out.println(passwordEncoder.encode("B@nk4P@ssEmp9#1"));
//		System.out.println(passwordEncoder.encode("B@nk4P@ssEmp9#2"));
//		System.out.println(passwordEncoder.encode("B@nk4P@ssEmp9#3"));
		
		//update account_role table
		ar.setAcntId(useraccount.getAcntid());
		ar.setRoleId(3);// for customer
		ar.setRlnm("CUST");
		userAccountDao.createAccountRole(ar);
		cashDetails = cashDetailsDao.get(104);
		cashDetails.setAmount(cashDetails.getAmount()+useraccount.getBalance());
		cashDetailsDao.update(cashDetails);		
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
