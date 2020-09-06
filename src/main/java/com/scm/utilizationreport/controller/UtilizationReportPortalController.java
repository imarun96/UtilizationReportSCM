package com.scm.utilizationreport.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Comparator;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import com.google.gson.Gson;
import com.scm.utilizationreport.dao.DailyDetailsDao;
import com.scm.utilizationreport.dao.WeeklyStatusDao;
import com.scm.utilizationreport.model.DailyStatusReference;
import com.scm.utilizationreport.model.EmployeeGroupDetails;
import com.scm.utilizationreport.model.FullNameClass;
import com.scm.utilizationreport.model.SubmissionDetails;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;
import com.scm.utilizationreport.model.WeeklyStatusReference;
import com.scm.utilizationreport.service.DailyDetailsService;

import util.GetAllGroupNames;
import util.WriteDataToCSV;

@RestController
@CrossOrigin(origins = "http://localhost:3000")
public class UtilizationReportPortalController {
	Logger log = LoggerFactory.getLogger(UtilizationReportPortalController.class);

	@Autowired
	DailyDetailsService dailyDetailsService;

	@Autowired
	WeeklyStatusDao weeklyStatusDao;

	@Autowired
	DailyDetailsDao dailyDetailsDao;

	public static final String CACHE_CONTROL = "Cache-Control";
	public static final String NO_CACHE = "no-cache";
	public static final String PRAGMA = "Pragma";
	public static final String OPEN_TABLE_HEADER = "<tr>";
	public static final String CLOSE_TABLE_HEADER = "</tr>";
	public static final String CLOSE_TABLE_DATA = "</td>";
	public static final String OPEN_TABLE_DATA = "<td>";
	public static final String CLOSE_TABLE_HEADER_STR = "</th>";
	public static final String APPLICATION_JSON = "application/json";
	public static final String UTF_8 = "utf-8";

	private Gson gson = new Gson();
	String empId = StringUtils.EMPTY;

	@GetMapping(value = { "/dashboard" })
	public ModelAndView dashboardPage() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/dashboard");
		List<String> employeeList = dailyDetailsDao.getEmployeeList();
		List<String> employeeNotSubmittedList = dailyDetailsDao.getNotSubmittedEmployeeNumbers(employeeList);
		List<FullNameClass> fullNameList = dailyDetailsDao.getEmployeeFullName(employeeNotSubmittedList);
		model.addObject("fullNameList", fullNameList);
		return model;
	}

	@GetMapping(value = { "/submissionDetails" })
	public ModelAndView submissionDetailsPage() {
		ModelAndView model = new ModelAndView();
		List<SubmissionDetails> utilizationReportDailyDetails = dailyDetailsDao.loadSubmissionDetail();
		model.addObject("utilizationReportDailyDetails", utilizationReportDailyDetails);
		model.setViewName("/submissionDetails");
		return model;
	}

	@GetMapping(value = { "/weeklyStatus" })
	public ModelAndView weeklyStatusPage(@RequestParam(name = "weekNumber", required = true) int weekNumber) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/weeklyStatus");
		return model;
	}

	@GetMapping("/get-weekly-man-days/{weekNumber}")
	public void eGet(@PathVariable("weekNumber") String weekNumberString, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		GetAllGroupNames gp = new GetAllGroupNames();
		List<WeeklyStatusReference> weeklyStatusReferenceList = weeklyStatusDao
				.getWeeklyReference(gp.getAllGroupNames(), Integer.valueOf(weekNumberString));
		StringBuilder buf = new StringBuilder();
		buf.append("<table id=\"myTable\" style=\"table-layout: fixed; width: 1500px\">");
		buf.append(OPEN_TABLE_HEADER + "<th>Group Name</th>" + "<th>Catalog Man Days</th>" + "<th>DevOps Man Days</th>"
				+ "<th>DevOps Man Days</th>" + "<th>System QE Man Days</th>" + "<th>Leave Days</th>"
				+ CLOSE_TABLE_HEADER);
		weeklyStatusReferenceList.stream().forEach(week -> {
			buf.append(OPEN_TABLE_HEADER);
			buf.append("<td><a href= \"www.google.com\">" + week.getGroupName() + "</a></td>");
			buf.append(OPEN_TABLE_DATA + week.getCatalog() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + week.getDevops() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + week.getSystemQe() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + week.getFreeResource() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + week.getLeaveDays() + CLOSE_TABLE_DATA);
			buf.append(CLOSE_TABLE_HEADER);
		});
		buf.append("</table>");
		out.print(buf.toString());
	}

	@GetMapping(value = { "/dailyStatus" })
	public ModelAndView dailyStatusPage(@RequestParam(name = "weekNumber", required = true) int weekNumber) {
		ModelAndView model = new ModelAndView();
		model.setViewName("/dailyStatus");
		return model;
	}

	@GetMapping(value = { "/employeeInformation" })
	public ModelAndView employeeInformation() {
		ModelAndView model = new ModelAndView();
		model.setViewName("/EmployeeInformation");
		List<EmployeeGroupDetails> employeeList = dailyDetailsDao.getAllEmployees();
		model.addObject("employeeList", employeeList.stream()
				.sorted(Comparator.comparing(EmployeeGroupDetails::getEmpId).reversed()).collect(Collectors.toList()));
		return model;
	}

	@GetMapping("/get-emp-groupname/{empid}")
	public void doGet(@PathVariable("empid") String empid, HttpServletResponse response) throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		PrintWriter out = response.getWriter();
		String[] groupName = dailyDetailsDao.getEmployeeGroupName(empid);
		String message = Objects.nonNull(groupName[1])
				? "Your Main Group Name is : " + "<b>" + groupName[1] + "</b>"
						+ "<a href= \"loadEmployeeDetail?employee_mst_id=" + groupName[0] + "&path=indirect"
						+ "\" target=\"_blank\">" + " See Employee Detail" + "</a>"
				: "Not found ? No worries " + "<a href= \"addEmployee\" target=\"_blank\">" + "Register here" + "</a>";
		out.print(message);
	}

	@GetMapping("/is-emp-exists/{empid}")
	public void empIdExistenceCheck(@PathVariable("empid") String empid, HttpServletResponse response)
			throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setHeader(PRAGMA, NO_CACHE);
		PrintWriter out = response.getWriter();
		if (dailyDetailsDao.isEmployeePresent(empid)) {
			out.print("present");
		} else {
			out.print("no");
		}
	}

	@GetMapping("/get-week-days/{weekNumber}")
	public void dGet(@PathVariable("weekNumber") String weekNumberString, HttpServletResponse response)
			throws IOException {
		PrintWriter out = response.getWriter();
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setHeader(PRAGMA, NO_CACHE);
		Calendar c = GregorianCalendar.getInstance();
		c.set(Calendar.WEEK_OF_YEAR, Integer.valueOf(weekNumberString));
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
		ModelAndView model = new ModelAndView();
		GetAllGroupNames gp = new GetAllGroupNames();
		List<DailyStatusReference> dailyStatusReferenceList = weeklyStatusDao.getDailyReference(gp.getAllGroupNames(),
				Integer.valueOf(weekNumberString));
		StringBuilder buf = new StringBuilder();
		buf.append("<table id=\"myTable\" style=\"table-layout: fixed; width: 1500px\">");
		buf.append(OPEN_TABLE_HEADER + "<th>Group Name</th>" + "<th>" + startDate + CLOSE_TABLE_HEADER_STR + "<th>"
				+ endDate1 + CLOSE_TABLE_HEADER_STR + "<th>" + endDate2 + CLOSE_TABLE_HEADER_STR + "<th>" + endDate3
				+ CLOSE_TABLE_HEADER_STR + "<th>" + endDate4 + CLOSE_TABLE_HEADER_STR + CLOSE_TABLE_HEADER);
		dailyStatusReferenceList.stream().forEach(dailyStatusReference -> {
			buf.append(OPEN_TABLE_HEADER);
			buf.append(OPEN_TABLE_DATA + "<a href=" + "www.google.com/" + dailyStatusReference.getGroupName() + ">"
					+ dailyStatusReference.getGroupName() + "</a>" + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + dailyStatusReference.getMondayCount() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + dailyStatusReference.getTuesdayCount() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + dailyStatusReference.getWednesdayCount() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + dailyStatusReference.getThursdayCount() + CLOSE_TABLE_DATA);
			buf.append(OPEN_TABLE_DATA + dailyStatusReference.getFridayCount() + CLOSE_TABLE_DATA);
			buf.append(CLOSE_TABLE_HEADER);
		});
		buf.append("</table>");
		out.print(buf.toString());
		model.setViewName("/dailyStatus");
	}

	@GetMapping(value = { "/delete-emp/{empid}" })
	public void funGet(@PathVariable("empid") String empid, HttpServletResponse response) throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setHeader(PRAGMA, NO_CACHE);
		response.setCharacterEncoding(UTF_8);
		PrintWriter out = response.getWriter();
		dailyDetailsDao.deleteEmployee(empid);
		out.print("deleted");
	}

	@GetMapping(value = { "/addEmployee" })
	public ModelAndView addEmployee() {
		ModelAndView model = new ModelAndView();
		EmployeeGroupDetails employeeGroupDetails = new EmployeeGroupDetails();
		model.addObject("employeeGroupDetails", employeeGroupDetails);
		model.setViewName("/addEmployee");
		return model;
	}

	@PostMapping(value = { "/saveEmployeeDetails" })
	public ModelAndView createEmployee(@Valid EmployeeGroupDetails employeeGroupDetails) {
		dailyDetailsService.insertEmployeeDailyDetails(employeeGroupDetails);
		return employeeInformation();
	}

	@GetMapping(value = "/update-employee/{employee_mst_id}", produces = "application/json")
	public void updateEmp(@PathVariable("employee_mst_id") String employeeMstId, HttpServletResponse response)
			throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setContentType(APPLICATION_JSON);
		response.setCharacterEncoding(UTF_8);
		PrintWriter out = response.getWriter();
		List<EmployeeGroupDetails> employeeList = dailyDetailsDao.getDetails(employeeMstId);
		EmployeeGroupDetails employeeGroupDetails = new EmployeeGroupDetails();
		employeeList.stream().forEach(emp -> {
			employeeGroupDetails.setEmployeeDetailsMstId(emp.getEmployeeDetailsMstId());
			employeeGroupDetails.setEmpId(emp.getEmpId());
			employeeGroupDetails.setEmpName(emp.getEmpName());
			employeeGroupDetails.setEmpGroupName(emp.getEmpGroupName());
			empId = emp.getEmpId();
		});
		out.print(this.gson.toJson(employeeGroupDetails));
	}

	@GetMapping("/load-utilization-detail/{utilizationReportDailyDetailsMstId}")
	public void loadDetail(
			@PathVariable("utilizationReportDailyDetailsMstId") String utilizationReportDailyDetailsMstId,
			HttpServletResponse response) throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setContentType(APPLICATION_JSON);
		response.setCharacterEncoding(UTF_8);
		PrintWriter out = response.getWriter();
		SubmissionDetails utilizationDetails = dailyDetailsDao.loadDetail(utilizationReportDailyDetailsMstId);
		out.print(this.gson.toJson(utilizationDetails));
	}

	@GetMapping("/load-employee-detail/{employee_mst_id}")
	public void loadEmpDetail(@PathVariable("employee_mst_id") String employeeMstId, HttpServletResponse response)
			throws IOException {
		response.setHeader(CACHE_CONTROL, NO_CACHE);
		response.setContentType(APPLICATION_JSON);
		response.setCharacterEncoding(UTF_8);
		PrintWriter out = response.getWriter();
		EmployeeGroupDetails employeeGroupDetail = new EmployeeGroupDetails();
		employeeGroupDetail.setMessage("No Data found for selected parameters");
		if (dailyDetailsDao.getCountNumber(employeeMstId) != 0) {
			List<EmployeeGroupDetails> employeeGroupDetails = dailyDetailsDao.getDetails(employeeMstId);
			employeeGroupDetails.stream().forEach(emp -> {
				employeeGroupDetail.setEmployeeDetailsMstId(emp.getEmployeeDetailsMstId());
				employeeGroupDetail.setEmpId(emp.getEmpId());
				employeeGroupDetail.setEmpName(emp.getEmpName());
				employeeGroupDetail.setEmpGroupName(emp.getEmpGroupName());
				employeeGroupDetail.setMessage("Success");
			});
		}
		out.print(this.gson.toJson(employeeGroupDetail));
	}

	@GetMapping(value = { "/loadDetail" })
	public ModelAndView loadUtilizationDetails(
			@RequestParam(name = "utilizationReportDailyDetailsMstId", required = true) String utilizationReportDailyDetailsMstId) {
		ModelAndView model = new ModelAndView();
		UtilizationReportDailyDetails utilizationDetails = new UtilizationReportDailyDetails();
		model.addObject("utilizationDetails", utilizationDetails);
		model.setViewName("/loadDetail");
		return model;
	}

	@GetMapping(value = { "/loadEmployeeDetail" })
	public ModelAndView loadEmpDetails(@RequestParam(name = "employee_mst_id", required = true) String employeeMstId,
			@RequestParam(name = "path", required = true) String path) {
		ModelAndView model = new ModelAndView();
		if (employeeMstId.matches("[0-9]+") && (path.equals("direct") || path.equals("indirect"))) {
			model.setViewName("/loadEmployeeDetail");
			return model;
		} else {
			throw new IllegalArgumentException("Enter a proper parameter for employee_mst_id and path.");
		}
	}

	@GetMapping(value = { "/updateEmployeeInformation" })
	public ModelAndView updateEmployeeInformation(
			@RequestParam(name = "employee_mst_id", required = true) long employeeMstId) {
		ModelAndView model = new ModelAndView();
		EmployeeGroupDetails employeeGroupDetails = new EmployeeGroupDetails();
		model.addObject("employeeGroupDetails", employeeGroupDetails);
		model.setViewName("/UpdateEmployeeInformation");
		return model;
	}

	@GetMapping(value = { "/updateEmployeeDetails" })
	public ModelAndView update(@Valid EmployeeGroupDetails employeeGroupDetails) {
		employeeGroupDetails.setEmpId(empId);
		dailyDetailsDao.updateEmployeeDetails(employeeGroupDetails);
		return employeeInformation();
	}

	@PostMapping(value = { "/saveDetails" })
	public ModelAndView show(@Valid UtilizationReportDailyDetails utilizationReportDailyDetails) {
		dailyDetailsService.insertDailyDetails(utilizationReportDailyDetails);
		return submissionDetailsPage();
	}

	@GetMapping(value = { "/submitForm" })
	public ModelAndView submitForm() {
		ModelAndView model = new ModelAndView();
		UtilizationReportDailyDetails utilizationReportDailyDetails = new UtilizationReportDailyDetails();
		model.addObject("utilizationReportDailyDetails", utilizationReportDailyDetails);
		model.setViewName("/submitForm");
		return model;
	}

	@GetMapping("/download")
	public void downloadCSV(HttpServletResponse response) throws IOException {
		Calendar cal = Calendar.getInstance();
		HttpHeaders headers = new HttpHeaders();
		ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Unauthorized user");
		headers.setContentType(MediaType.parseMediaType("application/pdf"));
		String filename = String.format("attachment; filename=\"SCM-Weekly Utilization Report Week - " + ""
				+ cal.get(Calendar.WEEK_OF_YEAR) + "" + ".csv\"");
		headers.setCacheControl("must-revalidate, post-check=0, pre-check=0");
		headers.add("content-disposition", "inline;filename=" + filename);
		response.setHeader("Content-Disposition",
				String.format("attachment; filename=\"SCM-Weekly Utilization Report Week - " + ""
						+ cal.get(Calendar.WEEK_OF_YEAR) + "" + ".csv\""));
		List<UtilizationReportDailyDetails> utilizationReportDailyDetails = dailyDetailsService.callToDao();
		WriteDataToCSV.writeDataToCsvUsingStringArray(response.getWriter(), utilizationReportDailyDetails);
	}
}