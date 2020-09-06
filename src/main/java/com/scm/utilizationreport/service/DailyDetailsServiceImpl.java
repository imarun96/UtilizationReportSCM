package com.scm.utilizationreport.service;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.scm.utilizationreport.dao.DailyDetailsDao;
import com.scm.utilizationreport.model.EmployeeGroupDetails;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;

@Service
public class DailyDetailsServiceImpl implements DailyDetailsService {

	@Autowired
	DailyDetailsDao dailyDetailsDao;

	@Override
	public void insertDailyDetails(UtilizationReportDailyDetails details) {
		dailyDetailsDao.insertDetails(details);
	}

	@Override
	public List<UtilizationReportDailyDetails> callToDao() {
		return dailyDetailsDao.getAll();
	}

	@Override
	public void insertEmployeeDailyDetails(EmployeeGroupDetails employeeGroupDetails) {
		dailyDetailsDao.insertEmployeeDetails(employeeGroupDetails);
	}
}