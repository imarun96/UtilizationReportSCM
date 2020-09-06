package com.scm.utilizationreport.dao;

import java.util.List;
import com.scm.utilizationreport.model.DailyStatusReference;
import com.scm.utilizationreport.model.WeeklyStatusReference;

public interface WeeklyStatusDao {
	public List<WeeklyStatusReference> getWeeklyReference(List<String> groupNameList, int weekNumber);

	public List<DailyStatusReference> getDailyReference(List<String> dailyGroupNameList, int weekNumber);
}