package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

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
	@Column(name = "CD_SRC_NAME", length = 20)
	private String srcname;
	@Column(name = "CD_DEST_NAME", length = 20)
	private String destname;
	@Column(name = "CD_CR_DR", length = 2)
	private String crdrflg;
	@Column(name = "CDA_AMT")
	private double amount;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getSrcname() {
		return srcname;
	}
	public void setSrcname(String srcname) {
		this.srcname = srcname;
	}
	public String getDestname() {
		return destname;
	}
	public void setDestname(String destname) {
		this.destname = destname;
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
