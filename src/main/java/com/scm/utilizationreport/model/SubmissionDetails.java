package com.scm.utilizationreport.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SubmissionDetails {
	private String submittedDate;
	private String groupName;
	private int categoryDiv;
	private int workDiv;
	private int freeResourceDiv;
	private int leaveDiv;
	private int ifLeaveDiv;
	private String empId;
	private String empName;
	private long utilizationReportDailyDetailsMstId;
}