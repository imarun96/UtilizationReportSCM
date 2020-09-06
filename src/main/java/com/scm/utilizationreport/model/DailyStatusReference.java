package com.scm.utilizationreport.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DailyStatusReference {
	private String groupName;
	private int mondayCount;
	private int tuesdayCount;
	private int wednesdayCount;
	private int thursdayCount;
	private int fridayCount;
}