package com.scm.utilizationreport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity(name = "employee_details_mst")
@Getter
@Setter
@ToString
public class EmployeeGroupDetails {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "employee_details_mst_id")
	private long employeeDetailsMstId;
	@Column(name = "emp_id")
	private String empId;
	@Column(name = "emp_group_name")
	private String empGroupName;
	@Column(name = "emp_name")
	private String empName;
	private String message;
}