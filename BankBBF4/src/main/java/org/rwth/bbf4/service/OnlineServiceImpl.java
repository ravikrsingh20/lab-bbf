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
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

@Service("onlineService")
@Transactional
public class OnlineServiceImpl implements OnlineService{
	@Autowired
	private CashDetailsDao cashDetailsDao;
	@Autowired
	private UserAccountDao userAccountDao;
	@Autowired
	private TxnDtlsDao txnDtlsDao;
	@Override
	public TxnDtls placeTransferRequest(TxnDtls txndtls) {
		// TODO Auto-generated method stub
		UserAccount uasrc = new UserAccount();
		UserAccount uadest = new UserAccount();
		java.util.Date date= new java.util.Date();
		TxnDtls txnDtlstmpsrc = new TxnDtls();
		TxnDtls txnDtlstmpdest = new TxnDtls();
		CashDetails cdSrc = new CashDetails();
		CashDetails cdDest = new CashDetails();
		if (txndtls.getTxncrdracntid().substring(0, 4).equalsIgnoreCase("BNK4") ){
			if(txndtls.getTxncrdrbnknm().equals("BANK4")){
				//within bank transfer
				List <UserAccount> ualist = userAccountDao.getUserByAcntId(txndtls.getTxnacntid());
				uasrc = ualist.get(0);
				if (uasrc.getBalance()>= txndtls.getTxnamt()){
					ualist = userAccountDao.getUserByAcntId(txndtls.getTxncrdracntid());
					if (ualist != null){
						// destination account exists
						uadest = ualist.get(0);
						if(uadest.getAcntid().equals(uasrc.getAcntid())){
							txndtls.setMsg("Error!! Creditor and Debitor Account are same");

						}
						else {
							// create 2 entries in txn table for cr and dr
							
							// also create an entry in txn table
							txnDtlstmpsrc.setExecdt(new Timestamp(date.getTime()));
							txnDtlstmpsrc.setOrddt(new Timestamp(date.getTime()));
							txnDtlstmpsrc.setTxnamt(txndtls.getTxnamt());
							txnDtlstmpsrc.setTxncrdracntid(uadest.getAcntid());
							txnDtlstmpsrc.setTxncrdrbnknm(txndtls.getTxncrdrbnknm());
							txnDtlstmpsrc.setTxnacntid(uasrc.getAcntid());
							txnDtlstmpsrc.setTxnflg("DR");
							txnDtlstmpsrc.setTxntyp("ONLN");
							txnDtlstmpsrc.setTxnstat("Processed");
							txnDtlsDao.create(txnDtlstmpsrc);

							// create and entry for CR leg
							txnDtlstmpdest.setExecdt(new Timestamp(date.getTime()));
							txnDtlstmpdest.setOrddt(new Timestamp(date.getTime()));
							txnDtlstmpdest.setTxnamt(txndtls.getTxnamt());
							txnDtlstmpdest.setTxncrdracntid(uasrc.getAcntid());
							txnDtlstmpdest.setTxncrdrbnknm(txndtls.getTxncrdrbnknm());
							txnDtlstmpdest.setTxnacntid(uadest.getAcntid());
							txnDtlstmpdest.setTxnflg("CR");
							txnDtlstmpdest.setTxntyp("ONLN");
							txnDtlstmpdest.setTxnstat("Processed");
							txnDtlsDao.create(txnDtlstmpdest);
							
							//update balance of src and dest account
							uasrc.setBalance(uasrc.getBalance() - txndtls.getTxnamt());
							userAccountDao.update(uasrc);
							uadest.setBalance(uadest.getBalance() + txndtls.getTxnamt());
							userAccountDao.update(uadest);

							txndtls.setMsg("Wire Transfer Request completed successfully.");
						}


					}else {
						txndtls.setMsg("Error!! Destination Account Doesnot exist");
					}		

				}
				else{
					txndtls.setMsg("Error!! Not Enough Balance in account");
				}
			}

		}else {
			// outside bank cases
			// create one entry in txn dtls with type b2b for cr leg and add the amount to cash details
			//also call web service with account detail to send money to particular customer in foreign bank
			if(txndtls.getTxncrdrbnknm().equals("BANK1")){
				txndtls.setMsg("Error!! Bank1 Not Supported");

			}else if (txndtls.getTxncrdrbnknm().equals("BANK2")){
				txndtls.setMsg("Error!! Bank2 Not Supported");

			}else if (txndtls.getTxncrdrbnknm().equals("BANK3")){

				//outside bank transfer bank3
				List <UserAccount> ualist = userAccountDao.getUserByAcntId(txndtls.getTxnacntid());
				uasrc = ualist.get(0);
				if (uasrc.getBalance()>= txndtls.getTxnamt()){
					// create 2 entries in txn table for cr and dr
					
					//JsonUser userReturn = new JsonUser();
					try{
						RestTemplate restTemplate = new RestTemplate();
						JsonUser user = new JsonUser();
						user.setAmount(txndtls.getTxnamt());
						user.setSrcAcntId(txndtls.getTxnacntid());
						user.setDestAcntId(txndtls.getTxncrdracntid());
						user.setSrcBnkNm("BANK4");
						ResponseEntity<JsonUser> userReturn;
						userReturn= restTemplate.postForEntity("http://137.226.112.106:80/bbf3/rest_api/transfer/format/json", user, JsonUser.class);
						if(userReturn.getStatusCode() == HttpStatus.OK){
							// also create an entry in txn table
							txnDtlstmpsrc.setExecdt(new Timestamp(date.getTime()));
							txnDtlstmpsrc.setOrddt(new Timestamp(date.getTime()));
							txnDtlstmpsrc.setTxnamt(txndtls.getTxnamt());
							txnDtlstmpsrc.setTxncrdracntid(txndtls.getTxncrdracntid());
							txnDtlstmpsrc.setTxncrdrbnknm(txndtls.getTxncrdrbnknm());
							txnDtlstmpsrc.setTxnacntid(uasrc.getAcntid());
							txnDtlstmpsrc.setTxnflg("DR");
							txnDtlstmpsrc.setTxntyp("ONLN");
							txnDtlstmpsrc.setTxnstat("Processed");
							txnDtlsDao.create(txnDtlstmpsrc);

							// create and entry for CR leg
							txnDtlstmpdest.setExecdt(new Timestamp(date.getTime()));
							txnDtlstmpdest.setOrddt(new Timestamp(date.getTime()));
							txnDtlstmpdest.setTxnamt(txndtls.getTxnamt());
							txnDtlstmpdest.setTxncrdracntid("BNK493000000");
							txnDtlstmpdest.setTxncrdrbnknm(txndtls.getTxncrdrbnknm());
							txnDtlstmpdest.setTxnacntid("BNK494000000");
							txnDtlstmpdest.setTxnflg("DR");
							txnDtlstmpdest.setTxntyp("B2B");
							txnDtlstmpdest.setTxnstat("Processed");
							txnDtlsDao.create(txnDtlstmpdest);
							
							//update bank 3 and bank4 cash details
							cdSrc = cashDetailsDao.get(104);
							cdDest= cashDetailsDao.get(103);
							//add amount to our bank account and debit balance from customers foreign bank account
							cdSrc.setAmount(cdSrc.getAmount()+txndtls.getTxnamt());
							cdDest.setAmount(cdDest.getAmount()-txndtls.getTxnamt());
							cashDetailsDao.update(cdDest);
							cashDetailsDao.update(cdDest);
							uasrc.setBalance(uasrc.getBalance() - txndtls.getTxnamt());
							userAccountDao.update(uasrc);
							txndtls.setMsg("Wire Transfer Request completed successfully.");
							
						}else if (userReturn.getStatusCode() == HttpStatus.NOT_FOUND){ //404 destination account doesnot exist
							txndtls.setMsg("Error!!!! Destination account doesnot exist");
						}

					}catch (final HttpClientErrorException e) {
						System.out.println(e.getStatusCode());
						System.out.println(e.getResponseBodyAsString());
					}
					
				}
			else{
				txndtls.setMsg("Error!! Not Enough Balance in account");
			}



		}else if (txndtls.getTxncrdrbnknm().equals("BANK5")){
			txndtls.setMsg("Error!! Destination Bank 5 Not Supported");

		}else if (txndtls.getTxncrdrbnknm().equals("BANK6")){
			txndtls.setMsg("Error!! Destination Bank 6 Not Supported");

		}else if (txndtls.getTxncrdrbnknm().equals("BANK7")){
			txndtls.setMsg("Error!! Destination Bank 7 Not Supported");

		}else if (txndtls.getTxncrdrbnknm().equals("BANK8")){
			txndtls.setMsg("Error!! Destination Bank 8 Not Supported");

		}else if (txndtls.getTxncrdrbnknm().equals("BANK9")){
			txndtls.setMsg("Error!! Destination Bank 9 Not Supported");

		}

	}


	return txndtls;
}
@Override
public UserAccount authorizeTransferRequest(UserAccount useraccount) {
	// TODO Auto-generated method stub

	return null;
}
@Override
public UserAccount viewBalance(UserAccount useraccount) {
	// TODO Auto-generated method stub

	UserAccount ua = new UserAccount();
	List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
	ua = ualist.get(0);
	useraccount.setMsg("Balance for Account No. "+useraccount.getAcntid()+" is "+ua.getBalance());

	return useraccount;

}
@Override
public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount) {
	// TODO Auto-generated method stub

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
	if(useraccount.getAcntid().substring(0, 4).equals("BNK4")){
		List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
		ua = ualist.get(0);
		//get all txn details from db
		txndtlsList = txnDtlsDao.getTxnDtlsOnln(useraccount);
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
	}else {
		// call web service of other bank to facilitate transaction from other banks
	}
	return txndtlsListret;

}
@Override
public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount) {
	// TODO Auto-generated method stub

	List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
	List<TxnDtls>  txndtlsListret = new ArrayList<TxnDtls>();
	TxnDtls txnDtlsret;
	String msg="";
	//get below details only when the user is valid
	// need to write a query to get txn dtls for particular bank acnt id and 

	UserAccount ua  ;
	TxnDtls txnDtls = new TxnDtls();
	CashDetails cashDetails = new CashDetails();
	java.util.Date date= new java.util.Date();
	//	check if the user has sufficient privilege
	String role = userAccountDao.getAccountRoleByAcntId(useraccount.getAcntid());
	if(role.equalsIgnoreCase("EMPL") || role.equalsIgnoreCase("ADMN")){

		if(useraccount.getAcntid().substring(0, 4).equals("BNK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
			txndtlsList = txnDtlsDao.getTxnDtlsbytxntyp(useraccount.getAcntid(), "B2B");
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
					msg += " Amount :" + txnDtlsTmp.getTxnamt();
					if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("CR")){
						msg += " Euro Credited to Bank Account from  : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
					if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("DR")){
						msg += " Euro Debited from Bank Account To : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
				}				
				txnDtlsret.setMsg(msg);
				txndtlsListret.add(txnDtlsret);

			}
			useraccount.setMsg("OK");
			return txndtlsListret;	
		}
	}		
	return txndtlsListret;
}

@Override
public TxnDtls lendMoney2Bank(UserAccount useraccount) {
	// TODO Auto-generated method stub
	TxnDtls txndtls= new TxnDtls();
	UserAccount uasrc = new UserAccount();
	UserAccount uadest = new UserAccount();
	java.util.Date date= new java.util.Date();
	TxnDtls txnDtlstmpsrc = new TxnDtls();
	TxnDtls txnDtlstmpdest = new TxnDtls();
	CashDetails cdDest = new CashDetails();
	CashDetails cdSrc = new CashDetails();
	String srcBnkAcntId;
	String srcBnkNm;
	String destBnkId;
	String destBnkNm;
	if (useraccount.getAcntid().substring(0, 4).equalsIgnoreCase("BNK4") && !(useraccount.getBnkname().equals("BANK4"))){
		//Outside bank transaction
		srcBnkNm = "Bank4";
		srcBnkAcntId="BNK494000000" ;

		String role = userAccountDao.getAccountRoleByAcntId(useraccount.getAcntid());
		if(role.equalsIgnoreCase("EMPL") || role.equalsIgnoreCase("ADMN")){
			cdSrc = cashDetailsDao.get(104);
			if(useraccount.getBnkname().equals("BANK1"))
				cdDest= cashDetailsDao.get(101);				
			else if(useraccount.getBnkname().equals("BANK2"))
				cdDest= cashDetailsDao.get(102);
			else if(useraccount.getBnkname().equals("BANK3"))
				cdDest= cashDetailsDao.get(103);
			else if(useraccount.getBnkname().equals("BANK5"))
				cdDest= cashDetailsDao.get(105);
			else if(useraccount.getBnkname().equals("BANK6"))
				cdDest= cashDetailsDao.get(106);
			else if(useraccount.getBnkname().equals("BANK7"))
				cdDest= cashDetailsDao.get(107);
			else if(useraccount.getBnkname().equals("BANK8"))
				cdDest= cashDetailsDao.get(108);
			else if(useraccount.getBnkname().equals("BANK9"))
				cdDest= cashDetailsDao.get(109);
			else {
				txndtls.setMsg("Error!! Cash Details for the bank not found");
				return txndtls;
			}
			if(cdSrc.getAmount()> useraccount.getAmt())	{
				cdSrc.setAmount(cdSrc.getAmount()-useraccount.getAmt());
				cdDest.setAmount(cdDest.getAmount()-useraccount.getAmt());

				// create and entry for CR leg where other bank account is credited					
				txnDtlstmpsrc.setExecdt(new Timestamp(date.getTime()));
				txnDtlstmpsrc.setOrddt(new Timestamp(date.getTime()));
				txnDtlstmpsrc.setTxnamt(useraccount.getAmt());
				txnDtlstmpsrc.setTxncrdracntid(cdDest.getAcntId());
				txnDtlstmpsrc.setTxncrdrbnknm(cdDest.getBankNm());
				txnDtlstmpsrc.setTxnacntid(cdSrc.getAcntId());
				txnDtlstmpsrc.setTxnflg("CR");
				txnDtlstmpsrc.setTxntyp("B2B");
				txnDtlstmpsrc.setTxnstat("Processed");

				// our bank is debited
				txnDtlstmpdest.setExecdt(new Timestamp(date.getTime()));
				txnDtlstmpdest.setOrddt(new Timestamp(date.getTime()));
				txnDtlstmpdest.setTxnamt(useraccount.getAmt());
				txnDtlstmpdest.setTxncrdracntid(cdSrc.getAcntId());
				txnDtlstmpdest.setTxncrdrbnknm(cdSrc.getBankNm());
				txnDtlstmpdest.setTxnacntid(cdDest.getAcntId());
				txnDtlstmpdest.setTxnflg("DR");
				txnDtlstmpdest.setTxntyp("B2B");
				txnDtlstmpdest.setTxnstat("Processed");

				cashDetailsDao.update(cdSrc);
				cashDetailsDao.update(cdDest);
				txnDtlsDao.create(txnDtlstmpsrc);
				txnDtlsDao.create(txnDtlstmpdest);
				txndtls.setMsg("Wire Transfer Request completed successfully.");
				//call other bank webservice to send money

			}else {
				txndtls.setMsg("We are bankrupt.");
			}

		}else{
			txndtls.setMsg("Error!! Not Authorized to perform transaction");
		}		
	}

	return txndtls;
}
@Override
public List<CashDetails> getCashDetails(UserAccount useraccount) {
	// TODO Auto-generated method stub
	List<CashDetails> lstCashDtls = new ArrayList<CashDetails>();
	List<CashDetails> lstCashDtls_ret = new ArrayList<CashDetails>();
	if (useraccount.getAcntid().substring(0, 4).equalsIgnoreCase("BNK4")){
		//Outside bank transaction			
		String role = userAccountDao.getAccountRoleByAcntId(useraccount.getAcntid());
		if(role.equalsIgnoreCase("ADMN")){				
			useraccount.setMsg("OK");
			return cashDetailsDao.findAll();

		}else{
			useraccount.setMsg("Not logged in as admin");

		}

	} else{
		useraccount.setMsg("Not logged in as admin");
	}
	return lstCashDtls_ret;
}
@Override
public List<TxnDtls> getTxnDtlsAtmEmp(UserAccount useraccount) {
	// TODO Auto-generated method stub

	List<TxnDtls>  txndtlsList = new ArrayList<TxnDtls>();
	List<TxnDtls>  txndtlsListret = new ArrayList<TxnDtls>();
	TxnDtls txnDtlsret;
	String msg="";
	//get below details only when the user is valid
	// need to write a query to get txn dtls for particular bank acnt id and 

	UserAccount ua  ;
	TxnDtls txnDtls = new TxnDtls();
	CashDetails cashDetails = new CashDetails();
	java.util.Date date= new java.util.Date();
	//	check if the user has sufficient privilege
	String role = userAccountDao.getAccountRoleByAcntId(useraccount.getAcntid());
	if(role.equalsIgnoreCase("EMPL") || role.equalsIgnoreCase("ADMN")){

		if(useraccount.getAcntid().substring(0, 4).equals("BNK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
			txndtlsList = txnDtlsDao.getTxnDtlsbyAtmNm(useraccount.getAtmname(), "ATM");
			// send only amount message (credit/debit from etc) exec date,
			for (TxnDtls txnDtlsTmp : txndtlsList){
				txnDtlsret = new  TxnDtls();
				msg="";
				txnDtlsret.setTxnid(txnDtlsTmp.getTxnid());
				txnDtlsret.setTxnamt(txnDtlsTmp.getTxnamt());
				txnDtlsret.setTxnacntid(txnDtlsTmp.getTxnacntid());
				txnDtlsret.setExecdt(txnDtlsTmp.getExecdt());
				// set message to be displayed


				if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("ATM")){
					msg += " ATM Transaction.";
					msg += " " + txnDtlsTmp.getTxnamt();
					if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("CR")){
						msg += " Euro Credited to Account "+txnDtlsTmp.getTxnacntid()+" from ATM : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
					if (txnDtlsTmp.getTxnflg().equalsIgnoreCase("DR")){
						msg += " Euro Debited from  Account "+txnDtlsTmp.getTxnacntid()+" from ATM : "+txnDtlsTmp.getAtmname() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
				}

				txnDtlsret.setMsg(msg);
				txndtlsListret.add(txnDtlsret);
			}
			useraccount.setMsg("OK");
			return txndtlsListret;	
		}
	}	
	else {
		useraccount.setMsg("Not Sufficient Privilege");
	}
	return txndtlsListret;
}

}
