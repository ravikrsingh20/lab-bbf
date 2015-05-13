package org.rwth.bbf4.model;

import java.sql.Timestamp;

import org.codehaus.jackson.annotate.JsonIgnoreProperties;
@JsonIgnoreProperties
public class JsonTxnDtls {
	private Timestamp execDate;
	private double amount;
	private String message;
	public Timestamp getExecDate() {
		return execDate;
	}
	public void setExecDate(Timestamp execDate) {
		this.execDate = execDate;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

}
