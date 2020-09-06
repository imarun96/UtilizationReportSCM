package com.scm.utilizationreport.dao;

import java.util.List;

import com.scm.utilizationreport.model.EmployeeGroupDetails;
import com.scm.utilizationreport.model.FullNameClass;
import com.scm.utilizationreport.model.SubmissionDetails;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;

public interface DailyDetailsDao {
	public void insertDetails(UtilizationReportDailyDetails details);

	public List<UtilizationReportDailyDetails> getAll();

	public List<String> getEmployeeList();

	public List<String> getNotSubmittedEmployeeNumbers(List<String> list);

	public List<FullNameClass> getEmployeeFullName(List<String> list);

	String[] getEmployeeGroupName(String empId);

	public void insertEmployeeDetails(EmployeeGroupDetails employeeGroupDetails);

	public void deleteEmployee(String empId);

	public List<EmployeeGroupDetails> getAllEmployees();

	public List<EmployeeGroupDetails> getDetails(String employeeMstId);

	public void updateEmployeeDetails(EmployeeGroupDetails employeeGroupDetails);

	SubmissionDetails loadDetail(String utilizationReportDailyDatailsMstId);

	long getCountNumber(String employeeMstId);

	List<SubmissionDetails> loadSubmissionDetail();

	String getEmployeeNameForDailySubmission(String empId);

	public String getEmployeeName(String empId);

	public boolean isEmployeePresent(String empId);
}