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
		if(ualist!=null && ualist.size()>0){

			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(!ua.isAtmenabled()){
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account doesnot exist
			}else if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
				// password matches
				if (ua.getBalance() >= user.getAmount() ){			
					// dispense cash and update the balance in account
					ua.setBalance(ua.getBalance() - user.getAmount());
					userAccountDao.update(ua);

					// also create an entry in txn table
					txnDtls.setAtmname("OtherBankAtm");
					txnDtls.setExecdt(new Timestamp(date.getTime()));
					txnDtls.setOrddt(new Timestamp(date.getTime()));
					txnDtls.setTxnamt(user.getAmount());
					txnDtls.setTxncrdracntid("OtherBankAtm");
					txnDtls.setTxncrdrbnknm("OtherBankAtm");
					txnDtls.setTxnacntid(ua.getAcntid());
					txnDtls.setTxnflg("DR");
					txnDtls.setTxntyp("ATM");
					txnDtls.setTxnstat("Processed");

					txnDtlsDao.create(txnDtls);
					// update balace of the bank which is dispensing cash for our customer
					//cashDetails.setAmount(cashDetails.getAmount()-user.getAmount());
					//cashDetailsDao.update(cashDetails);		
					user.setAmount(ua.getBalance());							
					return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200

				}
				else {
					return new ResponseEntity<JsonUser>(user,HttpStatus.UNPROCESSABLE_ENTITY); //422 not enough balance in account of customer
				}
			}
			else {
				atmService.updateWrongAttempt(ua);
				return new ResponseEntity<JsonUser>(user,HttpStatus.UNAUTHORIZED); // 401 invalid pin

			}




		} else {
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account doesnot exist		
		}



	}
	@Override
	public ResponseEntity<JsonUser> viewBal(JsonUser user) {
		// TODO Auto-generated method stub
		UserAccount ua  ;
		List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
		if(ualist!=null && ualist.size()>0){
			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(!ua.isAtmenabled()){
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account doesnot exist
			}else if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
				user.setAmount(ua.getBalance());
				return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200
			}
			else{
				atmService.updateWrongAttempt(ua);
				return new ResponseEntity<JsonUser>(user,HttpStatus.UNAUTHORIZED); // 401
			}				
		}
		else {
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404	
		}

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
		if(ualist!=null && ualist.size()>0){
			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(!ua.isAtmenabled()){
				return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.NOT_FOUND);//404
			}else if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
				//get all txn details from db
				txndtlsList = txnDtlsDao.getTxnDtlsAtm(useraccount);
				// send only amount message (credit/debit from etc) exec date,
				for (TxnDtls txnDtlsTmp : txndtlsList){
					jsontxnDtls = new JsonTxnDtls();
					msg="";
					jsontxnDtls.setAmount(Double.toString(txnDtlsTmp.getTxnamt()));
					jsontxnDtls.setExecDate(txnDtlsTmp.getExecdt().toString());

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
							msg += " Euro Debited from Your Account from ATM : "+txnDtlsTmp.getAtmname() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
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

				return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.OK);//204 No content as asked by anton not 200 OK			

			}
			else {
				atmService.updateWrongAttempt(ua);
				return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.UNAUTHORIZED);//401

			}

		}

		return new ResponseEntity<List<JsonTxnDtls>>(jsonTxnDtlsList,HttpStatus.NOT_FOUND);//404
	}
	@Override
	public ResponseEntity<JsonUser> validate(JsonUser user) {
		UserAccount ua  ;
		List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
		if(ualist!=null && ualist.size()>0){

			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if(!ua.isAtmenabled()){
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account doesnot exist
			}else if (passwordEncoder.matches(user.getPin(), ua.getAtmpin())){
				return new ResponseEntity<JsonUser>(user,HttpStatus.NO_CONTENT); //200
			}
			else{
				atmService.updateWrongAttempt(ua);
				return new ResponseEntity<JsonUser>(user,HttpStatus.UNAUTHORIZED); // 401 invalid pin
			}
				
		}
		else {
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account not found
		}

	}
	@Override
	public ResponseEntity<JsonUser> validateAccountId(JsonUser user) {
		List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getCardNumber());
		if(ualist!=null && ualist.size()>0){
			if(!ualist.get(0).isAtmenabled()){
				return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account doesnot exist
			}else
				return new ResponseEntity<JsonUser>(user,HttpStatus.OK); // 200 valid acnt id
		}
		else 
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND); //404 account not found


	}
	@Override
	public ResponseEntity<JsonUser> plcwrtrnsfr(JsonUser user){

		// TODO Auto-generated method stub
		UserAccount uasrc = new UserAccount();
		UserAccount uadest = new UserAccount();
		java.util.Date date= new java.util.Date();
		TxnDtls txnDtlstmpsrc = new TxnDtls();
		TxnDtls txnDtlstmpdest = new TxnDtls();
		CashDetails cashDetails = new CashDetails();

		List <UserAccount> ualist = userAccountDao.getUserByAcntId(user.getDestAcntId());
		if(ualist!=null && ualist.size()>0){
			uasrc = ualist.get(0);
			// create 2 entries in txn table for cr and dr
			uasrc.setBalance(uasrc.getBalance() + user.getAmount());
			userAccountDao.update(uasrc);			

			// also create an entry in txn table
			txnDtlstmpsrc.setExecdt(new Timestamp(date.getTime()));
			txnDtlstmpsrc.setOrddt(new Timestamp(date.getTime()));
			txnDtlstmpsrc.setTxnamt(user.getAmount());
			txnDtlstmpsrc.setTxncrdracntid(user.getSrcAcntId());
			txnDtlstmpsrc.setTxncrdrbnknm(user.getSrcBnkNm());
			txnDtlstmpsrc.setTxnacntid(uasrc.getAcntid());
			txnDtlstmpsrc.setTxnflg("CR");
			txnDtlstmpsrc.setTxntyp("ONLN");
			txnDtlstmpsrc.setTxnstat("Processed");
			txnDtlsDao.create(txnDtlstmpsrc);

			// create one transaction from bank x to our bank. need to handle mor functionality over here debit the bank account from where we got request
			//to be specific
			txnDtlstmpdest.setExecdt(new Timestamp(date.getTime()));
			txnDtlstmpdest.setOrddt(new Timestamp(date.getTime()));
			txnDtlstmpdest.setTxnamt(user.getAmount());
			txnDtlstmpdest.setTxncrdracntid(user.getSrcAcntId());
			txnDtlstmpdest.setTxncrdrbnknm(user.getSrcBnkNm());
			txnDtlstmpdest.setTxnacntid("BNK494000000");
			txnDtlstmpdest.setTxnflg("CR");
			txnDtlstmpdest.setTxntyp("B2B");
			txnDtlstmpdest.setTxnstat("Processed");
			txnDtlsDao.create(txnDtlstmpdest);
			//update cash details for Bank as amount is recieved in bank account
			cashDetails = cashDetailsDao.get(104);
			cashDetails.setAmount(cashDetails.getAmount() + user.getAmount());
			cashDetailsDao.update(cashDetails);

			user.setMsg("Wire Transfer Request completed successfully.");
			return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200 




		}else{
			user.setMsg("Account is not of our bank");
			return new ResponseEntity<JsonUser>(user,HttpStatus.NOT_FOUND);// account not found
		}

	}
	@Override
	public ResponseEntity<JsonUser> lendMoney(JsonUser user) {

		// TODO Auto-generated method stub
		java.util.Date date= new java.util.Date();
		TxnDtls txnDtlstmpsrc = new TxnDtls();
		TxnDtls txnDtlstmpdest = new TxnDtls();
		CashDetails cashDetails = new CashDetails();
		CashDetails cdDest = new CashDetails();
		CashDetails cdSrc = new CashDetails();
		//get our bank account

		cdSrc = cashDetailsDao.get(104);
		if(user.getSrcBnkNm().equals("BANK1"))
			cdDest= cashDetailsDao.get(101);				
		else if(user.getSrcBnkNm().equals("BANK2"))
			cdDest= cashDetailsDao.get(102);
		else if(user.getSrcBnkNm().equals("BANK3"))
			cdDest= cashDetailsDao.get(103);
		else if(user.getSrcBnkNm().equals("BANK5"))
			cdDest= cashDetailsDao.get(105);
		else if(user.getSrcBnkNm().equals("BANK6"))
			cdDest= cashDetailsDao.get(106);
		else if(user.getSrcBnkNm().equals("BANK7"))
			cdDest= cashDetailsDao.get(107);
		else if(user.getSrcBnkNm().equals("BANK8"))
			cdDest= cashDetailsDao.get(108);
		else if(user.getSrcBnkNm().equals("BANK9"))
			cdDest= cashDetailsDao.get(109);
		// create and entry for CR leg where other bank account is debited				
		txnDtlstmpsrc.setExecdt(new Timestamp(date.getTime()));
		txnDtlstmpsrc.setOrddt(new Timestamp(date.getTime()));
		txnDtlstmpsrc.setTxnamt(user.getAmount());
		txnDtlstmpsrc.setTxncrdracntid(cdDest.getAcntId());
		txnDtlstmpsrc.setTxncrdrbnknm(cdDest.getBankNm());
		txnDtlstmpsrc.setTxnacntid(cdSrc.getAcntId());
		txnDtlstmpsrc.setTxnflg("DR");
		txnDtlstmpsrc.setTxntyp("B2B");
		txnDtlstmpsrc.setTxnstat("Processed");

		// our bank is credited
		txnDtlstmpdest.setExecdt(new Timestamp(date.getTime()));
		txnDtlstmpdest.setOrddt(new Timestamp(date.getTime()));
		txnDtlstmpdest.setTxnamt(user.getAmount());
		txnDtlstmpdest.setTxncrdracntid(cdSrc.getAcntId());
		txnDtlstmpdest.setTxncrdrbnknm(cdSrc.getBankNm());
		txnDtlstmpdest.setTxnacntid(cdDest.getAcntId());
		txnDtlstmpdest.setTxnflg("CR");
		txnDtlstmpdest.setTxntyp("B2B");
		txnDtlstmpdest.setTxnstat("Processed");
		
		//amount added to dest accounts  to indicate whether we need to give(+) or take money from bank(-)	to dest account
		cdSrc.setAmount(cdSrc.getAmount()+user.getAmount());
		cdDest.setAmount(cdDest.getAmount()+user.getAmount());

		cashDetailsDao.update(cdSrc);
		cashDetailsDao.update(cdDest);
		txnDtlsDao.create(txnDtlstmpsrc);
		txnDtlsDao.create(txnDtlstmpdest);
		
		return new ResponseEntity<JsonUser>(user,HttpStatus.OK); //200 processed

	}

}