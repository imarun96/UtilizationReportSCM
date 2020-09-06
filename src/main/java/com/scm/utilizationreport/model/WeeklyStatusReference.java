package com.scm.utilizationreport.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class WeeklyStatusReference {
	private String groupName;
	private int devops;
	private int catalog;
	private int systemQe;
	private int freeResource;
	private float leaveDays;
}