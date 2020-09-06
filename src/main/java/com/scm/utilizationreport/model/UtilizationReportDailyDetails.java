package com.scm.utilizationreport.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.apache.commons.lang3.StringUtils;

import lombok.ToString;

@Entity(name = "utilization_report_daily_details_mst")
@ToString
public class UtilizationReportDailyDetails {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "utilization_report_daily_details_mst_id")
	private long utilizationReportDailyDetailsMstId;
	@Column(name = "submitted_date")
	private String submittedDate;
	@Column(name = "group_name")
	private String groupName;
	@Column(name = "category_div")
	private int categoryDiv;
	@Column(name = "work_div")
	private int workDiv;
	@Column(name = "free_resource_div")
	private int freeResourceDiv;
	@Column(name = "leave_div")
	private int leaveDiv;
	@Column(name = "if_leave_div")
	private int ifLeaveDiv;
	@Column(name = "emp_id")
	private String empId;

	public String getEmpId() {
		return empId;
	}

	public void setEmpId(String empId) {
		this.empId = empId;
	}

	public String getSubmittedDate() {
		return submittedDate;
	}

	public void setSubmittedDate(String submittedDate) {
		this.submittedDate = submittedDate;
	}

	public long getUtilizationReportDailyDetailsMstId() {
		return utilizationReportDailyDetailsMstId;
	}

	public void setUtilizationReportDailyDetailsMstId(long utilizationReportDailyDetailsMstId) {
		this.utilizationReportDailyDetailsMstId = utilizationReportDailyDetailsMstId;
	}

	public String getCategoryDiv() {
		return String.valueOf(categoryDiv);
	}

	public void setCategoryDiv(String categoryDiv) {
		if (categoryDiv.equals(StringUtils.EMPTY) || categoryDiv.equals("")) {
			this.categoryDiv = 0;
		}
		if (categoryDiv.equals("1")) {
			this.categoryDiv = 1;
		}
		if (categoryDiv.equals("2")) {
			this.categoryDiv = 2;
		}
		if (categoryDiv.equals("3")) {
			this.categoryDiv = 3;
		}
	}

	public String getGroupName() {
		return groupName;
	}

	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}

	public String getWorkDiv() {
		return String.valueOf(workDiv);
	}

	public void setWorkDiv(String workDiv) {
		if (workDiv.equals(StringUtils.EMPTY) || workDiv.equals("")) {
			this.workDiv = 0;
		}
		if (workDiv.equals("1")) {
			this.workDiv = 1;
		}
		if (workDiv.equals("2")) {
			this.workDiv = 2;
		}
	}

	public String getFreeResourceDiv() {
		return String.valueOf(freeResourceDiv);
	}

	public void setFreeResourceDiv(String freeResourceDiv) {
		if (freeResourceDiv.equals(StringUtils.EMPTY) || freeResourceDiv.equals("")) {
			this.freeResourceDiv = 0;
		}
		if (freeResourceDiv.equals("1")) {
			this.freeResourceDiv = 1;
		}
		if (freeResourceDiv.equals("2")) {
			this.freeResourceDiv = 2;
		}
	}

	public String getLeaveDiv() {
		return String.valueOf(leaveDiv);
	}

	public void setLeaveDiv(String leaveDiv) {
		if (leaveDiv.equals(StringUtils.EMPTY) || leaveDiv.equals("")) {
			this.leaveDiv = 0;
		}
		if (leaveDiv.equals("1")) {
			this.leaveDiv = 1;
		}
		if (leaveDiv.equals("2")) {
			this.leaveDiv = 2;
		}
	}

	public String getIfLeaveDiv() {
		return String.valueOf(ifLeaveDiv);
	}

	public void setIfLeaveDiv(String ifLeaveDiv) {
		if (ifLeaveDiv.equals(StringUtils.EMPTY) || ifLeaveDiv.equals("")) {
			this.ifLeaveDiv = 0;
		}
		if (ifLeaveDiv.equals("1")) {
			this.ifLeaveDiv = 1;
		}
		if (ifLeaveDiv.equals("2")) {
			this.ifLeaveDiv = 2;
		}
	}
}