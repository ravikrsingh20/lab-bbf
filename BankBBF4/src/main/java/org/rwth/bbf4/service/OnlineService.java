package org.rwth.bbf4.service;

import org.rwth.bbf4.model.CashDetails;
import org.springframework.beans.factory.annotation.Autowired;

public class OnlineService {
	@Autowired
	CashDetails CashDetailsDao;
	@Autowired
	CashDetails TxnDtlsDao;

}
