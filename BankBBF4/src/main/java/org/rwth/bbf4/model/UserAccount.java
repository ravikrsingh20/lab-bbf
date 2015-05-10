package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.NotNull;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Stores User Account Details
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name = "USER_ACNT")
@NamedQueries({ @NamedQuery(name = "getUserByAcntId", query = "from UserAccount ua where ua.acntid = :acntid" )})
public class UserAccount {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;
	
	
	@Column(name = "UA_ACNT_ID", unique = true, length = 12)
	private String acntid;
	
	@Column(name = "UA_FNAME", nullable = false, length = 20)
	@NotEmpty
	@NotNull
	private String fname;
	
	@Column(name = "UA_LNAME", nullable = false, length = 20)
	@NotEmpty
	@NotNull
	private String lname;
	
	@Column(name = "UA_PASSWD")
	private String passwd;
	
	@Column(name = "UA_EMAIL",nullable = false, length = 30, unique = true)
	@NotEmpty
	@NotNull
	@Email
	private String email;
	
	@Column(name = "UA_PHN_NO",nullable = true, length = 13)
	private String phoneno;
	
	@Column(name = "UA_ADDRESS",nullable = true, length = 30)
	private String address;
	
	@Column(name = "UA_ATM_PIN")
	private String atmpin;
	
	@Column(name = "UA_ONLN_PIN")
	private String onlnpin;
	
	@Column(name = "UA_BALANCE")
	private double balance;
	
	@Column(name = "UA_ENABLED")
	private String enabled;
	@Transient
	private double amt;
	@Transient
	private String msg;
	@Transient
	private String bnkname;
	@Transient
	private String atmname;
	public String getBnkname() {
		return bnkname;
	}
	public void setBnkname(String bnkname) {
		this.bnkname = bnkname;
	}
	public String getAtmname() {
		return atmname;
	}
	public void setAtmname(String atmname) {
		this.atmname = atmname;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public double getAmt() {
		return amt;
	}
	public void setAmt(double amt) {
		this.amt = amt;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getFname() {
		return fname;
	}
	public void setFname(String fname) {
		this.fname = fname;
	}
	
	public String getLname() {
		return lname;
	}
	public void setLname(String lname) {
		this.lname = lname;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhoneno() {
		return phoneno;
	}
	public void setPhoneno(String phoneno) {
		this.phoneno = phoneno;
	}

	public String getAcntid() {
		return acntid;
	}
	public void setAcntid(String acntid) {
		this.acntid = acntid;
	}
	public String getAddress() {
		return address;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public String getAtmpin() {
		return atmpin;
	}
	public void setAtmpin(String atmpin) {
		this.atmpin = atmpin;
	}
	public String getOnlnpin() {
		return onlnpin;
	}
	public void setOnlnpin(String onlnpin) {
		this.onlnpin = onlnpin;
	}
	public double getBalance() {
		return balance;
	}
	public void setBalance(double balance) {
		this.balance = balance;
	}
	public String getEnabled() {
		return enabled;
	}
	public void setEnabled(String enabled) {
		this.enabled = enabled;
	}
	

}
