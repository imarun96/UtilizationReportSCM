package com.scm.utilizationreport.service;

import java.util.List;

import org.springframework.stereotype.Service;
import com.scm.utilizationreport.model.EmployeeGroupDetails;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;

@Service
public interface DailyDetailsService {
	public void insertDailyDetails(UtilizationReportDailyDetails details);

	public List<UtilizationReportDailyDetails> callToDao();

	public void insertEmployeeDailyDetails(EmployeeGroupDetails employeeGroupDetails);
}