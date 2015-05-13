package org.rwth.bbf4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.dao.TxnDtlsDao;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.JsonTxnDtls;
import org.rwth.bbf4.model.JsonUser;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("restService")
@Transactional
public class RestServiceImpl implements RestService {

	@Autowired
	private CashDetailsDao cashDetailsDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private TxnDtlsDao txnDtlsDao;
	@Autowired
	private AtmService atmService;
	@Autowired
	private OnlineService onlnService;
	@Override
	public ResponseEntity<JsonUser> cashWithdraw(JsonUser user) {
		// TODO Auto-generated method stub
		UserAccount ua  ;
		TxnDtls txnDtls = new TxnDtls();
		CashDetails cashDetails = new CashDetails();
		java.util.Date date= new java.util.Date();
		//write code to check acnt id. also note that useraccount.fname has bankname and lname has atmname
		// amount to be withdrawn will be in amt field
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
			if(ualist.equals(null)){
				ua = ualist.get(0);
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
					// password matches
					if (ua.getBalance() >= user.getAmount() ){						
						int id=0 ;
							cashDetails = cashDetailsDao.get(1);						
						if(cashDetails.getAmount() >= user.getAmount()){
							// dispense cash and update the balance in account
							ua.setBalance(ua.getBalance() - user.getAmount());
							userAccountDao.update(ua);

							// also create an entry in txn table
							txnDtls.setAtmname("OtherBankAtm");
							txnDtls.setExecdt(new Timestamp(date.getTime()));
							txnDtls.setOrddt(new Timestamp(date.getTime()));
							txnDtls.setTxnamt(user.getAmount());
							//txnDtls.setTxncracntid(txncracntid); no cr 
							//txnDtls.setTxncrbnknm();txnDtls.getTxndracntid() +" Bank Name : "+txnDtls.getTxndrbnknm()
							txnDtls.setTxncrdracntid("OtherBankAtm");
							txnDtls.setTxncrdrbnknm("OtherBankAtm");
							txnDtls.setTxnacntid(ua.getAcntid());
							txnDtls.setTxnflg("DR");
							txnDtls.setTxntyp("ATM");
							txnDtls.setTxnstat("Processed");

							txnDtlsDao.create(txnDtls);
							// update atm balance
							cashDetails.setAmount(cashDetails.getAmount()-user.getAmount());
							cashDetailsDao.update(cashDetails);		
							user.setAmount(ua.getBalance());							
							return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200
						}
						else{
							return new ResponseEntity<JsonUser>(user,HttpStatus.UPGRADE_REQUIRED); //426
						}
						
					}
					else {
						return new ResponseEntity<JsonUser>(user,HttpStatus.UNPROCESSABLE_ENTITY); //422
					}
				}
				else {
					return new ResponseEntity<JsonUser>(user,HttpStatus.UNAUTHORIZED); // 401

				}
				
			} else 
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404
			

	
	}
	@Override
	public ResponseEntity<JsonUser> viewBal(JsonUser user) {
		// TODO Auto-generated method stub
		UserAccount ua  ;
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
			if(ualist.equals(null)){
				ua = ualist.get(0);
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
					user.setAmount(ua.getBalance());
					return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200
					
				}
				else
					return new ResponseEntity<JsonUser>(user,HttpStatus.UNAUTHORIZED); // 401
			}
			else 
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404
	}
	@Override
	public ResponseEntity<List<JsonTxnDtls>> readTxn(JsonUser user) {
		// TODO Auto-generated method stub
		List<JsonTxnDtls> jsonTxnDtlsList= new ArrayList<JsonTxnDtls>();
		List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
		
		String msg="";
		//get below details only when the user is valid
		// need to write a query to get txn dtls for particular bank acnt id and 

		// TODO Auto-generated method stub	
		UserAccount ua  ;
		UserAccount useraccount = new UserAccount () ;
		JsonTxnDtls jsontxnDtls = new JsonTxnDtls();
		CashDetails cashDetails = new CashDetails();
		java.util.Date date= new java.util.Date();
		useraccount.setAcntid(user.getCardNumber());
		//write code to check acnt id. also note that useraccount.fname has bankname and lname has atmname
		// amount to be withdrawn will be in amt field
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
			if(ualist == null){
				ua = ualist.get(0);
				PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
				if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
					//get all txn details from db
					txndtlsList = txnDtlsDao.getTxnDtlsAtm(useraccount);
					// send only amount message (credit/debit from etc) exec date,
					for (TxnDtls txnDtlsTmp : txndtlsList){
						jsontxnDtls = new JsonTxnDtls();
						msg="";
						jsontxnDtls.setAmount(txnDtlsTmp.getTxnamt());
						jsontxnDtls.setExecDate(txnDtlsTmp.getExecdt());
						
						// set message to be displayed
						if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("ONLN")){
							msg += " Online Transaction.";
							msg += " " + txnDtlsTmp.getTxnamt();
							if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("CR")){
								msg += " Euro Credited to Your Account from Bank Account : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
							}
							if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("DR")){
								msg += " Euro Debited from Your Account to Bank Account : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
							}
						}
							
						if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("ATM")){
							msg += " ATM Transaction.";
							msg += " " + txnDtlsTmp.getTxnamt();
							if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("CR")){
								msg += " Euro Credited to Your Account from ATM : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
							}
							if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("DR")){
								msg += " Euro Debited from Your Account from ATM : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
							}
						}
							
						if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("B2B")){
							msg += " Bank 2 Bank Transaction.";
							msg += " " + txnDtlsTmp.getTxnamt();
						}
						msg += " Status : "+txnDtlsTmp.getTxnstat();
						jsontxnDtls.setMessage(msg);
						jsonTxnDtlsList.add(jsontxnDtls);
						
					}

					return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.OK);//200			
					
				}
				else {

					return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.UNAUTHORIZED);//401

				}
				
			}
			
		return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.NOT_FOUND);//404
	}
	
	
}