package org.rwth.bbf4.model;

import java.io.Serializable;
import java.sql.Timestamp;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class JsonTxnDtls implements Serializable {
	private static final long serialVersionUID = -8039686696076337052L;
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
