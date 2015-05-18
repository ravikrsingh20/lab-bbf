package org.rwth.bbf4.model;
import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown=true)
public class JsonUser implements Serializable{
	private static final long serialVersionUID = -8039686696076337053L;
	
	private String cardNumber;
	
	private String pin;

	private double amount;
	private String srcAcntId;
	private String srcBnkNm;
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getSrcAcntId() {
		return srcAcntId;
	}
	public void setSrcAcntId(String srcAcntId) {
		this.srcAcntId = srcAcntId;
	}
	public String getSrcBnkNm() {
		return srcBnkNm;
	}
	public void setSrcBnkNm(String srcBnkNm) {
		this.srcBnkNm = srcBnkNm;
	}
	public String getDestAcntId() {
		return destAcntId;
	}
	public void setDestAcntId(String destAcntId) {
		this.destAcntId = destAcntId;
	}
	private String destAcntId;
	public String getCardNumber() {
		return cardNumber;
	}
	public void setCardNumber(String cardNumber) {
		this.cardNumber = cardNumber;
	}
	public String getPin() {
		return pin;
	}
	public void setPin(String pin) {
		this.pin = pin;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
