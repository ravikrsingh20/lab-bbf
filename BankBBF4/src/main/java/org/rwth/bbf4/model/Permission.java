package org.rwth.bbf4.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * model to store PERMISSION table
 * @author Ravi Kumar Singh
 *
 */
@Entity
@Table(name="PERMISSION")
public class Permission {
	@Id
	@GeneratedValue
	@Column(name = "PR_ID", nullable = false)
	private Integer permissionId;

	@Column(name = "PR_NAME", nullable = false,length = 12)
	private String permissionName;

}
