package com.scm.utilizationreport.dao;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.commons.collections.CollectionUtils;
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

import com.scm.utilizationreport.model.EmployeeGroupDetails;
import com.scm.utilizationreport.model.FullNameClass;
import com.scm.utilizationreport.model.SubmissionDetails;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;

@Repository
public class DailyDetailsDaoImpl implements DailyDetailsDao {
	private static final String EMP_ID = "emp_id";

	Logger log = LoggerFactory.getLogger(DailyDetailsDaoImpl.class);

	@Override
	public void insertDetails(UtilizationReportDailyDetails details) {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String startDate;
		startDate = details.getSubmittedDate();
		String empId = details.getEmpId();
		String hql = "select count(*) FROM utilization_report_daily_details_mst c where c.submittedDate =:start_date and c.empId =:emp_id";
		Query q = session.createQuery(hql);
		q.setParameter("start_date", startDate);
		q.setParameter(EMP_ID, empId);
		long count = (long) q.uniqueResult();
		if (count == 1) {
			String hql1 = "select utilizationReportDailyDetailsMstId FROM utilization_report_daily_details_mst c where c.submittedDate = '"
					+ startDate + "' and c.empId = '" + empId + "'";
			Query q2 = session.createQuery(hql1);
			long mstId = (long) q2.uniqueResult();
			Object o = session.load(UtilizationReportDailyDetails.class, mstId);
			UtilizationReportDailyDetails u = (UtilizationReportDailyDetails) o;
			u.setCategoryDiv(details.getCategoryDiv());
			u.setEmpId(details.getEmpId());
			u.setFreeResourceDiv(details.getFreeResourceDiv());
			u.setGroupName(details.getGroupName());
			u.setIfLeaveDiv(details.getIfLeaveDiv());
			u.setLeaveDiv(details.getLeaveDiv());
			u.setSubmittedDate(details.getSubmittedDate());
			u.setWorkDiv(details.getWorkDiv());
			session.getTransaction().commit();
			session.close();
			sessionFactory.close();
		} else {
			session.save(details);
			session.getTransaction().commit();
			session.close();
		}
	}

	@Override
	public List<UtilizationReportDailyDetails> getAll() {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "FROM utilization_report_daily_details_mst order by utilizationReportDailyDetailsMstId desc";
		Query q = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<UtilizationReportDailyDetails> utilizationReportDailyDetails = q.list();
		session.getTransaction().commit();
		session.close();
		return utilizationReportDailyDetails;
	}

	@Override
	public List<String> getEmployeeList() {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "select empId FROM employee_details_mst";
		Query q = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<String> employeeList = q.list();
		session.getTransaction().commit();
		session.close();
		return employeeList;
	}

	@Override
	public String[] getEmployeeGroupName(String empId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "FROM employee_details_mst c where c.empId=:newEmpid";
		Query q = session.createQuery(hql);
		q.setParameter("newEmpid", empId);
		@SuppressWarnings("unchecked")
		List<EmployeeGroupDetails> groupName = q.list();
		String[] values = new String[2];
		session.getTransaction().commit();
		session.close();
		if (CollectionUtils.isNotEmpty(groupName)) {
			groupName.stream().forEach(g -> {
				values[1] = g.getEmpGroupName();
				values[0] = String.valueOf(g.getEmployeeDetailsMstId());
			});
			return values;
		}
		return values;
	}

	@Override
	public List<String> getNotSubmittedEmployeeNumbers(List<String> list) {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		Calendar c = Calendar.getInstance();
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
		List<String> employeeNotSubmittedList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			String hql = "select count(*) FROM utilization_report_daily_details_mst c where c.empId = '" + list.get(i)
					+ "' " + " and c.submittedDate IN (" + "'" + startDate + "'" + "," + "'" + endDate1 + "'" + ","
					+ "'" + endDate2 + "'" + "," + "'" + endDate3 + "'" + "," + "'" + endDate4 + "'" + ")";
			Query q = session.createQuery(hql);
			long count = (long) q.uniqueResult();
			if (count < 5) {
				employeeNotSubmittedList.add(list.get(i));
			}
		}
		session.getTransaction().commit();
		session.close();
		return employeeNotSubmittedList;
	}

	@Override
	public List<FullNameClass> getEmployeeFullName(List<String> list) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		List<FullNameClass> fullNameList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			FullNameClass fullNameObject = new FullNameClass();
			String hql = "select c.empName as fullName FROM employee_details_mst c where c.empId = '" + list.get(i)
					+ "'";
			Query q = session.createQuery(hql);
			String fullname = (String) q.uniqueResult();
			String hql1 = "select c.empGroupName FROM employee_details_mst c where c.empId = '" + list.get(i) + "'";
			Query q1 = session.createQuery(hql1);
			String groupName = (String) q1.uniqueResult();
			fullNameObject.setFullName(fullname);
			fullNameObject.setGroupName(groupName);
			fullNameList.add(fullNameObject);
		}
		session.getTransaction().commit();
		session.close();
		return fullNameList;
	}

	@Override
	public void insertEmployeeDetails(EmployeeGroupDetails employeeGroupDetails) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		session.save(employeeGroupDetails);
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public String getEmployeeNameForDailySubmission(String empId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "select c.empName FROM employee_details_mst c where c.empId='" + empId + "'";
		Query q = session.createQuery(hql);
		String empName = (String) q.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return empName;
	}

	@Override
	public void deleteEmployee(String empId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "delete FROM employee_details_mst c where c.empId='" + empId + "'";
		Query q = session.createQuery(hql);
		q.executeUpdate();
		session.getTransaction().commit();
		session.close();
	}

	@Override
	public String getEmployeeName(String empId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String newEmpid = empId.substring(6, empId.length());
		String hql = "select c.empName FROM employee_details_mst c where c.empId='" + newEmpid + "'";
		Query q = session.createQuery(hql);
		q.setCacheable(true);
		String groupName = (String) q.uniqueResult();
		session.getTransaction().commit();
		session.close();
		return groupName;
	}

	@Override
	public List<EmployeeGroupDetails> getAllEmployees() {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "FROM employee_details_mst";
		Query q = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<EmployeeGroupDetails> employeeList = q.list();
		session.getTransaction().commit();
		session.close();
		return employeeList;
	}

	@Override
	public List<EmployeeGroupDetails> getDetails(String employeeMstId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "FROM employee_details_mst where employeeDetailsMstId=:employee_mst_id";
		Query q = session.createQuery(hql);
		q.setParameter("employee_mst_id", Long.parseLong(employeeMstId));
		@SuppressWarnings("unchecked")
		List<EmployeeGroupDetails> employeeList = q.list();
		return employeeList;
	}

	@Override
	public long getCountNumber(String employeeMstId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "select count(*) FROM employee_details_mst where employeeDetailsMstId=:employee_mst_id";
		Query q = session.createQuery(hql);
		q.setParameter("employee_mst_id", Long.parseLong(employeeMstId));
		long employeeCount = (long) q.uniqueResult();
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		return employeeCount;
	}

	@Override
	public void updateEmployeeDetails(EmployeeGroupDetails employeeGroupDetails) {
		employeeGroupDetails.getEmpId();
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "select employeeDetailsMstId from employee_details_mst where empId=:emp_id";
		Query q = session.createQuery(hql);
		q.setParameter(EMP_ID, employeeGroupDetails.getEmpId());
		Long employeeDetailsMstId = (long) q.uniqueResult();
		employeeGroupDetails.setEmployeeDetailsMstId(employeeDetailsMstId);
		session.update(employeeGroupDetails);
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
	}

	@Override
	public List<SubmissionDetails> loadSubmissionDetail() {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "from utilization_report_daily_details_mst order by utilizationReportDailyDetailsMstId desc";
		Query q = session.createQuery(hql);
		@SuppressWarnings("unchecked")
		List<UtilizationReportDailyDetails> dailySubmissionDetails = q.list();
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		List<SubmissionDetails> dailySubmissionDetails2 = new ArrayList<>();
		dailySubmissionDetails.stream().forEach(e -> {
			SubmissionDetails details = new SubmissionDetails();
			details.setEmpId(e.getEmpId());
			details.setEmpName(getEmployeeNameForDailySubmission(e.getEmpId()));
			details.setSubmittedDate(e.getSubmittedDate());
			details.setGroupName(e.getGroupName());
			details.setCategoryDiv(Integer.valueOf(e.getCategoryDiv()));
			details.setWorkDiv(Integer.valueOf(e.getWorkDiv()));
			details.setFreeResourceDiv(Integer.valueOf(e.getFreeResourceDiv()));
			details.setLeaveDiv(Integer.valueOf(e.getLeaveDiv()));
			details.setIfLeaveDiv(Integer.valueOf(e.getIfLeaveDiv()));
			details.setUtilizationReportDailyDetailsMstId(e.getUtilizationReportDailyDetailsMstId());
			dailySubmissionDetails2.add(details);

		});
		return dailySubmissionDetails2;
	}

	@Override
	public SubmissionDetails loadDetail(String utilizationReportDailyDetailsMstId) {
		Configuration configuration = new Configuration().configure()
				.addAnnotatedClass(UtilizationReportDailyDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "from utilization_report_daily_details_mst where utilizationReportDailyDetailsMstId=:utilizationReportDailyDetailsMstId";
		Query q = session.createQuery(hql);
		q.setParameter("utilizationReportDailyDetailsMstId", Long.valueOf(utilizationReportDailyDetailsMstId));
		@SuppressWarnings("unchecked")
		List<UtilizationReportDailyDetails> dailySubmissionDetails = q.list();
		SubmissionDetails submissionDetails = new SubmissionDetails();
		dailySubmissionDetails.stream().forEach(sub -> {
			submissionDetails.setEmpId(sub.getEmpId());
			submissionDetails.setEmpName(getEmployeeNameForDailySubmission(sub.getEmpId()));
			submissionDetails.setSubmittedDate(sub.getSubmittedDate());
			submissionDetails.setGroupName(sub.getGroupName());
			submissionDetails.setCategoryDiv(Integer.valueOf(sub.getCategoryDiv()));
			submissionDetails.setWorkDiv(Integer.valueOf(sub.getWorkDiv()));
			submissionDetails.setFreeResourceDiv(Integer.valueOf(sub.getFreeResourceDiv()));
			submissionDetails.setLeaveDiv(Integer.valueOf(sub.getLeaveDiv()));
			submissionDetails.setIfLeaveDiv(Integer.valueOf(sub.getIfLeaveDiv()));
			submissionDetails.setUtilizationReportDailyDetailsMstId(sub.getUtilizationReportDailyDetailsMstId());
		});
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		return submissionDetails;
	}

	@Override
	public boolean isEmployeePresent(String empId) {
		Configuration configuration = new Configuration().configure().addAnnotatedClass(EmployeeGroupDetails.class);
		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
				.buildServiceRegistry();
		SessionFactory sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		Session session = sessionFactory.openSession();
		Transaction tx = session.getTransaction();
		tx.begin();
		String hql = "select employeeDetailsMstId from employee_details_mst where empId=:emp_id";
		Query q = session.createQuery(hql);
		q.setParameter(EMP_ID, empId);
		long mstId = 0L;
		try {
			mstId = (long) q.uniqueResult();
		} catch (NullPointerException e) {
			mstId = 0L;
		}
		session.getTransaction().commit();
		session.close();
		sessionFactory.close();
		return mstId == 0L ? Boolean.FALSE : Boolean.TRUE;
	}
}