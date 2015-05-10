package org.rwth.bbf4.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import org.rwth.bbf4.dao.CashDetailsDaoImpl;
import org.rwth.bbf4.dao.TxnDtlsDaoImpl;
import org.rwth.bbf4.dao.UserAccountDao;
import org.rwth.bbf4.model.CashDetails;
import org.rwth.bbf4.model.TxnDtls;
import org.rwth.bbf4.model.UserAccount;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


public interface AtmService {
	public UserAccount withdrawCash(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsOnln(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsB2B(UserAccount useraccount);
	public List<TxnDtls> getTxnDtlsAtm(UserAccount useraccount);
}
