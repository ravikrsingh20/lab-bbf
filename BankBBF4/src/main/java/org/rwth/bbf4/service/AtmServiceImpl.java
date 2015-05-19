package org.rwth.bbf4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.dao.TxnDtlsDao;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.JsonUser;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service("atmService")
@Transactional
public class AtmServiceImpl implements AtmService {

	@Autowired
	private CashDetailsDao cashDetailsDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private TxnDtlsDao txnDtlsDao;
	@Override
	public UserAccount withdrawCash(UserAccount useraccount) {
		// TODO Auto-generated method stub	
		UserAccount ua  ;
		CashDetails cdDest = new CashDetails();
		TxnDtls txnDtls = new TxnDtls();
		TxnDtls txnDtlstmpsrc = new TxnDtls();
		TxnDtls txnDtlstmpdest = new TxnDtls();
		CashDetails cdSrc = new CashDetails();
		CashDetails cashDetails = new CashDetails();
		java.util.Date date= new java.util.Date();
		//write code to check acnt id. also note that useraccount.fname has bankname and lname has atmname
		// amount to be withdrawn will be in amt field
		//fetch atm balance
		if (useraccount.getAtmname().equals("ATM1"))
			cashDetails = cashDetailsDao.get(1);
		else if (useraccount.getAtmname().equals("ATM2"))
			cashDetails	= cashDetailsDao.get(2);
		else if (useraccount.getAtmname().equals("ATM3"))
			cashDetails = cashDetailsDao.get(3);
		if(useraccount.getBnkname().equals("BANK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(useraccount.getAtmpin(), ua.getAtmpin())){
				// password matches
				if (ua.getBalance() >= useraccount.getAmt() ){						
					int id=0 ;

					if(cashDetails.getAmount() >= useraccount.getAmt()){
						// dispense cash and update the balance in account
						ua.setBalance(ua.getBalance() - useraccount.getAmt());
						userAccountDao.update(ua);

						// also create an entry in txn table
						txnDtls.setAtmname(useraccount.getAtmname());
						txnDtls.setExecdt(new Timestamp(date.getTime()));
						txnDtls.setOrddt(new Timestamp(date.getTime()));
						txnDtls.setTxnamt(useraccount.getAmt());
						txnDtls.setTxncrdracntid(cashDetails.getAcntId());
						txnDtls.setTxncrdrbnknm(useraccount.getBnkname());
						txnDtls.setTxnacntid(ua.getAcntid());
						txnDtls.setTxnflg("DR");
						txnDtls.setTxntyp("ATM");
						txnDtls.setTxnstat("Processed");

						txnDtlsDao.create(txnDtls);
						// update atm balance
						cashDetails.setAmount(cashDetails.getAmount()-useraccount.getAmt());
						cashDetailsDao.update(cashDetails);		
						useraccount.setBalance(ua.getBalance());
						useraccount.setMsg("Dispensing Cash!!\n Amount"
								+ useraccount.getAmt()
								+ " successfully withdrawn"
								+" \n Remaining Balance is "
								+ua.getBalance());

					}
					else{
						useraccount.setMsg("Sorry!! ATM doesnot have sufficient cash try other atms");
					}

				}
				else {
					useraccount.setMsg("Sorry!! Not enough balance. Your Balance is "
							+ ua.getBalance()
							+" and Amount entered to withdraw" 
							+useraccount.getAmt());

				}
			}
			else {
				useraccount.setMsg("Sorry!! ATM Pin Doesnot match");

			}

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK1")){

		} else if (useraccount.getBnkname().equals("BANK2")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK3")){

			// call web service of other bank to facilitate cash withdrawl of foreign bank atms			
			RestTemplate restTemplate = new RestTemplate();
			JsonUser user = new JsonUser();
			user.setCardNumber(useraccount.getAcntid());
			user.setPin(useraccount.getAtmpin());
			user.setAmount(useraccount.getAmt());
			//JsonUser userReturn = new JsonUser();
			try{
				ResponseEntity<JsonUser> userReturn;
				userReturn= restTemplate.postForEntity("http://137.226.112.106:80/bbf3/validate/cashWithdraw", user, JsonUser.class);
				if(userReturn.getStatusCode() == HttpStatus.OK){
					// everything is ok
					// also create an entry in txn table					
					cdSrc = cashDetailsDao.get(104);
					cdDest= cashDetailsDao.get(103);
					txnDtls.setAtmname(useraccount.getAtmname());
					txnDtls.setExecdt(new Timestamp(date.getTime()));
					txnDtls.setOrddt(new Timestamp(date.getTime()));
					txnDtls.setTxnamt(useraccount.getAmt());
					txnDtls.setTxncrdracntid("BNK493000000");
					txnDtls.setTxncrdrbnknm(useraccount.getBnkname());
					txnDtls.setTxnacntid(useraccount.getAcntid());
					txnDtls.setTxnflg("DR");
					txnDtls.setTxntyp("ATM");
					txnDtls.setTxnstat("Processed");

					txnDtlsDao.create(txnDtls);
					useraccount.setMsg("Dispensing Cash!!\n Amount"
							+ useraccount.getAmt()
							+ " successfully withdrawn");
					//add amount to our bank account and debit balance from customers foreign bank account
					cdSrc.setAmount(cdSrc.getAmount()+useraccount.getAmt());
					cdDest.setAmount(cdDest.getAmount()-useraccount.getAmt());

					cashDetailsDao.update(cdDest);
					cashDetailsDao.update(cdDest);
					// update atm balance
					cashDetails.setAmount(cashDetails.getAmount()-useraccount.getAmt());
					cashDetailsDao.update(cashDetails);	


				}else if (userReturn.getStatusCode() == HttpStatus.UNPROCESSABLE_ENTITY){ //422 not enough balance
					useraccount.setMsg("Sorry!! Not enough balance in your account. "
							+"  Amount entered to withdraw" 
							+useraccount.getAmt());


				}else if (userReturn.getStatusCode() == HttpStatus.UNAUTHORIZED){ //401 invalid pin
					useraccount.setMsg("Sorry!! ATM Pin Doesnot match");

				}

			}catch (final HttpClientErrorException e) {
				System.out.println(e.getStatusCode());
				System.out.println(e.getResponseBodyAsString());
			}

			//	Update cash details for that particular bank and create one entry in transaction with type B2B

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK5")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK6")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK7")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK8")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK9")){

		}



		return useraccount;

	}
	@Override
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount){
		List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
		List<TxnDtls>  txndtlsListret = new ArrayList<TxnDtls>();
		TxnDtls txnDtlsret;
		String msg="";
		//get below details only when the user is valid
		// need to write a query to get txn dtls for particular bank acnt id and 

		// TODO Auto-generated method stub	
		UserAccount ua  ;
		TxnDtls txnDtls = new TxnDtls();
		CashDetails cashDetails = new CashDetails();
		java.util.Date date= new java.util.Date();
		//write code to check acnt id. also note that useraccount.fname has bankname and lname has atmname
		// amount to be withdrawn will be in amt field
		if(useraccount.getBnkname().equals("BANK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(useraccount.getAtmpin(), ua.getAtmpin())){
				//get all txn details from db
				txndtlsList = txnDtlsDao.getTxnDtlsAtm(useraccount);
				// send only amount message (credit/debit from etc) exec date,
				for (TxnDtls txnDtlsTmp : txndtlsList){
					txnDtlsret = new  TxnDtls();
					msg="";
					txnDtlsret.setTxnid(txnDtlsTmp.getTxnid());
					txnDtlsret.setTxnamt(txnDtlsTmp.getTxnamt());
					txnDtlsret.setTxnacntid(txnDtlsTmp.getTxnacntid());
					txnDtlsret.setExecdt(txnDtlsTmp.getExecdt());
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
					txnDtlsret.setMsg(msg);
					txndtlsListret.add(txnDtlsret);
					useraccount.setBalance(ua.getBalance());

				}
				useraccount.setMsg("OK");
				return txndtlsListret;				

			}
			else {
				useraccount.setMsg("Sorry!! ATM Pin Doesnot match");

			}

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK1")){

		} else if (useraccount.getBnkname().equals("BANK2")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK3")){

			// call web service of other bank to facilitate cash withdrawl of foreign bank atms			
			RestTemplate restTemplate = new RestTemplate();
			JsonUser user = new JsonUser();
			user.setCardNumber(useraccount.getAcntid());
			user.setPin(useraccount.getAtmpin());
			try{
				ResponseEntity<JsonUser> userReturn;
				userReturn= restTemplate.postForEntity("http://137.226.112.106:80/bbf3/validate/readTxn", user, JsonUser.class);
				if(userReturn.getStatusCode() == HttpStatus.OK){

				}else if (userReturn.getStatusCode() == HttpStatus.UNAUTHORIZED){ //401 invalid pin
					useraccount.setMsg("Sorry!! ATM Pin Doesnot match");

				}

			}catch (final HttpClientErrorException e) {
				System.out.println(e.getStatusCode());
				System.out.println(e.getResponseBodyAsString());
			}

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK5")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK6")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK7")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK8")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK9")){

		}

		return txndtlsListret;
	}

	@Override
	public UserAccount viewBalance(UserAccount useraccount) {
		// TODO Auto-generated method stub
		UserAccount ua  ;
		if(useraccount.getBnkname().equals("BANK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
			PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
			if (passwordEncoder.matches(useraccount.getAtmpin(), ua.getAtmpin())){
				useraccount.setMsg("Balance for Account No. "+useraccount.getAcntid()+" is "+ua.getBalance());

			}
			else
				useraccount.setMsg("ATM Pin and Account no. doesnot match");
		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK1")){

		} else if (useraccount.getBnkname().equals("BANK2")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK3")){
			// call web service of other bank to facilitate cash withdrawl of foreign bank atms			
			RestTemplate restTemplate = new RestTemplate();
			JsonUser user = new JsonUser();
			user.setCardNumber(useraccount.getAcntid());
			user.setPin(useraccount.getAtmpin());
			try{
				ResponseEntity<JsonUser> userReturn;
				userReturn= restTemplate.postForEntity("http://137.226.112.106:80/bbf3/validate/viewBal", user, JsonUser.class);
				if(userReturn.getStatusCode() == HttpStatus.OK){
					useraccount.setMsg("Balance for Account No. "+useraccount.getAcntid()+" is "+userReturn.getBody().getAmount());
				}else if (userReturn.getStatusCode() == HttpStatus.UNAUTHORIZED){ //401 invalid pin
					useraccount.setMsg("Sorry!! ATM Pin Doesnot match");

				}

			}catch (final HttpClientErrorException e) {
				System.out.println(e.getStatusCode());
				System.out.println(e.getResponseBodyAsString());
			}

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK5")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK6")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK7")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK8")){

		}else if (useraccount.getBnkname().equalsIgnoreCase("BANK9")){

		}
		return useraccount;
	}

}
