package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model class to link UserAccount and Role
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name="ACNT_ROLE")
public class AccountRole {
	@Id
	@GeneratedValue
	@Column(name = "AR_ID", nullable = false)
	private Integer acntRoleId;
	@Column(name = "AR_ACNT_ID", nullable = false, length = 12)
	private String acntId;
	@Column(name = "AR_RL_ID", nullable = false)
	private Integer roleId;
	public Integer getAcntRoleId() {
		return acntRoleId;
	}
	public void setAcntRoleId(Integer acntRoleId) {
		this.acntRoleId = acntRoleId;
	}
	public String getAcntId() {
		return acntId;
	}
	public void setAcntId(String acntId) {
		this.acntId = acntId;
	}
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	
	
	

}
