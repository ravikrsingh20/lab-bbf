package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * Stores User Account Details
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name = "CASH_DTLS")
public class CashDetails {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CD_ID")
	private int id;
	@Column(name = "CD_ACNT_ID", length = 12)
	private String acntId;
	@Column(name = "CD_BNK_NAME", length = 20)
	private String bankNm;
	@Column(name = "CD_CR_DR", length = 2)
	private String crdrflg;
	@Column(name = "CDA_AMT")
	private double amount;
	@Transient
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getAcntId() {
		return acntId;
	}
	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}
	public String getBankNm() {
		return bankNm;
	}
	public void setBankNm(String bankNm) {
		this.bankNm = bankNm;
	}
	public String getCrdrflg() {
		return crdrflg;
	}
	public void setCrdrflg(String crdrflg) {
		this.crdrflg = crdrflg;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	
}
