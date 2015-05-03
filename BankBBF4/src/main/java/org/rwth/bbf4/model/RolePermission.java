package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model to map ROLE and PERMISSION tables
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name="ROLE_PERMISSION")
public class RolePermission {
	@Id
	@GeneratedValue
	@Column(name = "RP_ID", nullable = false)
	private Integer rlPermissionId;

	@Column(name = "RP_RL_ID", nullable = false)
	private Integer roleId;

	@Column(name = "RP_PR_ID", nullable = false)
	private Integer permissionId;

	public Integer getRlPermissionId() {
		return rlPermissionId;
	}

	public void setRlPermissionId(Integer rlPermissionId) {
		this.rlPermissionId = rlPermissionId;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getPermissionId() {
		return permissionId;
	}

	public void setPermissionId(Integer permissionId) {
		this.permissionId = permissionId;
	}

}
