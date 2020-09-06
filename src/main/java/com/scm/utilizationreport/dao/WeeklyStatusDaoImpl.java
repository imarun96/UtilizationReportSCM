package com.scm.utilizationreport.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import com.scm.utilizationreport.model.DailyStatusReference;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;
import com.scm.utilizationreport.model.WeeklyStatusReference;

@Repository
public class WeeklyStatusDaoImpl implements WeeklyStatusDao {

	private static final String SELECT_QUERY = "select count(e.groupName) FROM utilization_report_daily_details_mst e where e.groupName='";

	private static final String SUBMITTED_COND = "' and e.submittedDate = '";

	private static final String SUBMITTED_DATE_QUERY = "' and e.submittedDate in (";

	Logger log = LoggerFactory.getLogger(WeeklyStatusDaoImpl.class);

	@Override
	public List<WeeklyStatusReference> getWeeklyReference(List<String> groupNameList, int weekNumber) {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, weekNumber);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String endDate1;
		String endDate2;
		String endDate3;
		String endDate4;
		String startDate;
		startDate = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate1 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate2 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate3 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate4 = df.format(c.getTime());
		List<WeeklyStatusReference> weeklyStatusReferenceList = new ArrayList<>();
		for (int i = 0; i < groupNameList.size(); i++) {
			WeeklyStatusReference weeklyStatusReference = new WeeklyStatusReference();
			String catalogHql = "select count(e.categoryDiv) FROM utilization_report_daily_details_mst e where e.categoryDiv=1 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query1 = session.createQuery(catalogHql);
			long catalogSum = (long) query1.uniqueResult();
			String devopsHql = "select count(e.categoryDiv) FROM utilization_report_daily_details_mst e where e.categoryDiv=2 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query2 = session.createQuery(devopsHql);
			long devopsSum = (long) query2.uniqueResult();
			String qeHql = "select count(e.categoryDiv) FROM utilization_report_daily_details_mst e where e.categoryDiv=3 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query6 = session.createQuery(qeHql);
			long qeSum = (long) query6.uniqueResult();
			String freeResourceHql = "select count(e.freeResourceDiv) FROM utilization_report_daily_details_mst e where e.freeResourceDiv=1 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query3 = session.createQuery(freeResourceHql);
			long freeResourceSum = (long) query3.uniqueResult();
			String fullDayHql = "select count(e.ifLeaveDiv) FROM utilization_report_daily_details_mst e where e.ifLeaveDiv=1 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query4 = session.createQuery(fullDayHql);
			long fullDaySum = (long) query4.uniqueResult();
			String halfDayHql = "select count(e.ifLeaveDiv) FROM utilization_report_daily_details_mst e where e.ifLeaveDiv=2 and e.groupName='"
					+ groupNameList.get(i) + SUBMITTED_DATE_QUERY + "'" + startDate + "'," + "'" + endDate1 + "'," + "'"
					+ endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query query5 = session.createQuery(halfDayHql);
			long halfDaySum = (long) query5.uniqueResult();
			float floatHalfDayLeaveSum = (float) halfDaySum / 2;
			float totalLeaveDays = fullDaySum + floatHalfDayLeaveSum;
			weeklyStatusReference.setGroupName(groupNameList.get(i));
			weeklyStatusReference.setCatalog((int) catalogSum);
			weeklyStatusReference.setDevops((int) devopsSum);
			weeklyStatusReference.setFreeResource((int) freeResourceSum);
			weeklyStatusReference.setLeaveDays(totalLeaveDays);
			weeklyStatusReference.setSystemQe((int) qeSum);
			weeklyStatusReferenceList.add(weeklyStatusReference);
		}
		session.getTransaction().commit();
		session.close();
		return weeklyStatusReferenceList;
	}

	@Override
	public List<DailyStatusReference> getDailyReference(List<String> dailyGroupNameList, int weekNumber) {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, weekNumber);
		c.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
		String endDate1;
		String endDate2;
		String endDate3;
		String endDate4;
		String startDate;
		startDate = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate1 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate2 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate3 = df.format(c.getTime());
		c.add(Calendar.DATE, 1);
		endDate4 = df.format(c.getTime());
		List<DailyStatusReference> dailyStatusReferenceList = new ArrayList<>();
		for (int i = 0; i < dailyGroupNameList.size(); i++) {
			DailyStatusReference dailyStatusReference = new DailyStatusReference();
			String mondayCountHql = SELECT_QUERY + dailyGroupNameList.get(i) + SUBMITTED_COND + startDate + "'";
			Query query1 = session.createQuery(mondayCountHql);
			long mondayCount = (long) query1.uniqueResult();
			String tuesdayCountHql = SELECT_QUERY + dailyGroupNameList.get(i) + SUBMITTED_COND + endDate1 + "'";
			Query query2 = session.createQuery(tuesdayCountHql);
			long tuesdayCount = (long) query2.uniqueResult();
			String wednesdayCountHql = SELECT_QUERY + dailyGroupNameList.get(i) + SUBMITTED_COND + endDate2 + "'";
			Query query3 = session.createQuery(wednesdayCountHql);
			long wednesdayCount = (long) query3.uniqueResult();
			String thursdayCountHql = SELECT_QUERY + dailyGroupNameList.get(i) + SUBMITTED_COND + endDate3 + "'";
			Query query4 = session.createQuery(thursdayCountHql);
			long thursdayCount = (long) query4.uniqueResult();
			String fridayCountHql = SELECT_QUERY + dailyGroupNameList.get(i) + SUBMITTED_COND + endDate4 + "'";
			Query query5 = session.createQuery(fridayCountHql);
			long fridayCount = (long) query5.uniqueResult();
			dailyStatusReference.setGroupName(dailyGroupNameList.get(i));
			dailyStatusReference.setMondayCount((int) mondayCount);
			dailyStatusReference.setTuesdayCount((int) tuesdayCount);
			dailyStatusReference.setWednesdayCount((int) wednesdayCount);
			dailyStatusReference.setThursdayCount((int) thursdayCount);
			dailyStatusReference.setFridayCount((int) fridayCount);
			dailyStatusReferenceList.add(dailyStatusReference);
		}
		session.getTransaction().commit();
		session.close();
		return dailyStatusReferenceList;
	}
}