package org.rwth.bbf4.model;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;

/**
 * model class to store transaction details of every credit and debit made to the atm
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name = "TXN_DTLS")
@NamedQueries({ @NamedQuery(name = "getTxnDtlsByAcntId", query = "from TxnDtls txn where txn.txnacntid = :acntid ORDER BY txn.execdt desc" )})
public class TxnDtls {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "TXN_ID")
	private long txnid;
	@Column(name = "TXN_ATM_NM")
	private String atmname;
	@Column(name = "TXN_AMT")
	private double txnamt;
	@Column(name = "TXN_ACNT_ID")
	private String txnacntid;
	@Column(name = "TXN_CR_BNK_NM")
	private String txncrbnknm;
	@Column(name = "TXN_CR_ACNT_ID")
	private String txncracntid;
	@Column(name = "TXN_DR_BNK_NM")
	private String txndrbnknm;
	@Column(name = "TXN_DR_ACNT_ID")
	private String txndracntid;
	@Column(name = "TXN_FLG")
	private String txnflg;
	@Column(name = "TXN_TYP")
	private String txntyp;
	@Column(name = "TXN_ORD_DT")
	private Timestamp orddt;
	
	@Column(name = "TXN_EXEC_DT")
	private Timestamp execdt;
	@Transient
	private String msg;
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public long getTxnid() {
		return txnid;
	}
	public void setTxnid(long txnid) {
		this.txnid = txnid;
	}
	public String getAtmname() {
		return atmname;
	}
	public void setAtmname(String atmname) {
		this.atmname = atmname;
	}
	public double getTxnamt() {
		return txnamt;
	}
	public void setTxnamt(double txnamt) {
		this.txnamt = txnamt;
	}
	public String getTxncrbnknm() {
		return txncrbnknm;
	}
	public void setTxncrbnknm(String txncrbnknm) {
		this.txncrbnknm = txncrbnknm;
	}
	public String getTxncracntid() {
		return txncracntid;
	}
	public void setTxncracntid(String txncracntid) {
		this.txncracntid = txncracntid;
	}
	public String getTxndrbnknm() {
		return txndrbnknm;
	}
	public void setTxndrbnknm(String txndrbnknm) {
		this.txndrbnknm = txndrbnknm;
	}
	public String getTxntyp() {
		return txntyp;
	}
	public void setTxntyp(String txntyp) {
		this.txntyp = txntyp;
	}
	public String getTxndracntid() {
		return txndracntid;
	}
	public void setTxndracntid(String txndracntid) {
		this.txndracntid = txndracntid;
	}
	public String getTxnflg() {
		return txnflg;
	}
	public void setTxnflg(String txnflg) {
		this.txnflg = txnflg;
	}
	public Timestamp getOrddt() {
		return orddt;
	}
	public void setOrddt(Timestamp orddt) {
		this.orddt = orddt;
	}
	public Timestamp getExecdt() {
		return execdt;
	}
	public void setExecdt(Timestamp execdt) {
		this.execdt = execdt;
	}
	public String getTxnacntid() {
		return txnacntid;
	}
	public void setTxnacntid(String txnacntid) {
		this.txnacntid = txnacntid;
	}
	
	
	

}
