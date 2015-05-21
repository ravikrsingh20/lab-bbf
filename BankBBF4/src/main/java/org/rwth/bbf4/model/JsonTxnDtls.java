package org.rwth.bbf4.model;

import java.sql.Timestamp;

public class JsonTxnDtls  {
	
	private String execDate;
	
	private String amount;
	
	private String message;

	public String getExecDate() {
		return execDate;
	}

	public void setExecDate(String execDate) {
		this.execDate = execDate;
	}

	public String getAmount() {
		return amount;
	}

	public void setAmount(String amount) {
		this.amount = amount;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}
