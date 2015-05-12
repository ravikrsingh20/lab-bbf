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
	@Column(name = "TXN_CR_DR_BNK_NM")
	private String txncrdrbnknm;
	@Column(name = "TXN_CR_DR_ACNT_ID")
	private String txncrdracntid;
	@Column(name = "TXN_FLG")
	private String txnflg;
	@Column(name = "TXN_TYP")
	private String txntyp;
	@Column(name = "TXN_ORD_DT")
	private Timestamp orddt;
	@Column(name = "TXN_STAT")
	private String txnstat;
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
	public String getTxnstat() {
		return txnstat;
	}
	public void setTxnstat(String txnstat) {
		this.txnstat = txnstat;
	}
	public void setTxnamt(double txnamt) {
		this.txnamt = txnamt;
	}
	
	public String getTxntyp() {
		return txntyp;
	}
	public void setTxntyp(String txntyp) {
		this.txntyp = txntyp;
	}
	
	public String getTxncrdrbnknm() {
		return txncrdrbnknm;
	}
	public void setTxncrdrbnknm(String txncrdrbnknm) {
		this.txncrdrbnknm = txncrdrbnknm;
	}
	public String getTxncrdracntid() {
		return txncrdracntid;
	}
	public void setTxncrdracntid(String txncrdracntid) {
		this.txncrdracntid = txncrdracntid;
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
