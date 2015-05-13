package org.rwth.bbf4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.dao.TxnDtlsDao;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

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
		if (txndtls.getTxncrdracntid().substring(0, 4).equalsIgnoreCase("BNK4") && txndtls.getTxncrdrbnknm().equals("BANK4")){
			//within bank transaction
			
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(txndtls.getTxnacntid());
			uasrc = ualist.get(0);
			if (uasrc.getBalance()>= txndtls.getTxnamt()){
				ualist = userAccountDao.getUserByAcntId(txndtls.getTxncrdracntid());
				if (ualist != null){
					// destination account exists
					uadest = ualist.get(0);
					if(uadest.getAcntid().equals(uasrc.getAcntid())){
						// create 2 entries in txn table for cr and dr
						uasrc.setBalance(uasrc.getBalance() - txndtls.getTxnamt());
						userAccountDao.update(uasrc);
						uadest.setBalance(uadest.getBalance() + txndtls.getTxnamt());
						userAccountDao.update(uadest);

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
						txndtls.setMsg("Wire Transfer Request completed successfully.");
						
					}
					else
						txndtls.setMsg("Error!! Creditor and Debitor Account are same");
										
				}else {
					txndtls.setMsg("Error!! Destination Account Doesnot exist");
				}		
				
			}
			else{
				txndtls.setMsg("Error!! Not Enough Balance in account");
			}
		}else {
			// outside bank case
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
						msg += " Euro Debited from Your Account from ATM : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
				}

				if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("B2B")){
					msg += " Bank 2 Bank Transaction.";
					msg += " " + txnDtlsTmp.getTxnamt();
				}	
				msg += " Status : "+txnDtlsTmp.getTxnstat();
				txnDtlsret.setMsg(msg);
				txndtlsListret.add(txnDtlsret);

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
		//write code to check acnt id. also note that useraccount.fname has bankname and lname has atmname
		// amount to be withdrawn will be in amt field
		if(useraccount.getAcntid().substring(0, 4).equals("BNK4")){
			List <UserAccount> ualist = userAccountDao.getUserByAcntId(useraccount.getAcntid());
			ua = ualist.get(0);
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
						msg += " Euro Debited from Your Account from ATM : "+txnDtlsTmp.getTxncrdracntid() +" Bank Name : "+txnDtlsTmp.getTxncrdrbnknm();
					}
				}

				if (txnDtlsTmp.getTxntyp().equalsIgnoreCase("B2B")){
					msg += " Bank 2 Bank Transaction.";
					msg += " Amount :" + txnDtlsTmp.getTxnamt();
				}				
				txnDtlsret.setMsg(msg);
				txndtlsListret.add(txnDtlsret);

			}
			useraccount.setMsg("OK");
			return txndtlsListret;				


		}else {
			// call web service of other bank to facilitate transaction from other banks
		}
		return txndtlsListret;


	}

}
