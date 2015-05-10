package org.rwth.bbf4.service;

import org.rwth.bbf4.dao.CashDetailsDao;
import org.rwth.bbf4.dao.TxnDtlsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OnlineServiceImpl implements OnlineService{
	@Autowired
	CashDetailsDao cashDetailsDao;
	@Autowired
	TxnDtlsDao txnDtlsDao;

}
