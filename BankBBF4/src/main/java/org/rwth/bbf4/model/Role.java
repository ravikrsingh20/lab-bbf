package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model class for various roles in db
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name="ROLE")
public class Role {
	@Id
	@GeneratedValue
	@Column(name = "RL_ID", nullable = false)
	private Integer roleId;

	@Column(name = "RL_NAME", nullable = false,length = 12)
	private String roleName;
	public Integer getRoleId() {
		return roleId;
	}
	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}
	public String getRoleName() {
		return roleName;
	}
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	

}
