package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * Stores User Account Details
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name = "CASH_DTLS_B2B")
public class CashDetailsB2B {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "CDB_ID")
	private int id;
	@Column(name = "CDA_BNK_NAME", length = 20)
	private String bankname;
	@Column(name = "CDB_BNK_AMT")
	private double amount;
	@Column(name = "CDB_CR_DR_FLG")
	private String crdrflg;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getBankname() {
		return bankname;
	}
	public void setBankname(String bankname) {
		this.bankname = bankname;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public String getCrdrflg() {
		return crdrflg;
	}
	public void setCrdrflg(String crdrflg) {
		this.crdrflg = crdrflg;
	}
	
	

}
