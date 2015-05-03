package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
@NamedQuery(
		name = "getAcntByEmail",
		query = "from USER_ACNT where UA_EMAIL like :email")
public class UserAccount {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "Id")
	private Long id;
	
	
	@Column(name = "UA_ACNT_ID", unique = true,	nullable = false, length = 12)
	private String acntId;
	
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
	
	@Column(name = "UA_ADDRESS",nullable = true, length = 13)
	private String Address;
	
	@Column(name = "UA_ATM_PIN")
	private String atmPin;
	
	@Column(name = "UA_ONLN_PIN")
	private String onlnpin;
	
	@Column(name = "UA_BALANCE")
	private double balance;
	
	@Column(name = "UA_ENABLED")
	private String enabled;
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
	public String getAddress() {
		return Address;
	}
	public void setAddress(String address) {
		Address = address;
	}
	
	public String getAcntId() {
		return acntId;
	}
	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}
	public String getAtmPin() {
		return atmPin;
	}
	public void setAtmPin(String atmPin) {
		this.atmPin = atmPin;
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
