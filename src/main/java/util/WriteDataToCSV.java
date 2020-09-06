package util;

import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import com.opencsv.CSVWriter;
import com.scm.utilizationreport.model.UtilizationReportDailyDetails;

public class WriteDataToCSV {
	private WriteDataToCSV() {
	}

	public static void writeDataToCsvUsingStringArray(PrintWriter writer,
			List<UtilizationReportDailyDetails> customers) {
		String[] csvHader = { "EMP ID", "Group Name", "Category", "Work", "Free Resource ?", "Leave ?",
				"Full Day (or) Half Day", "Submitted Date" };
		try (CSVWriter csvWriter = new CSVWriter(writer, CSVWriter.DEFAULT_SEPARATOR, CSVWriter.NO_QUOTE_CHARACTER,
				CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);) {
			csvWriter.writeNext(csvHader);
			for (UtilizationReportDailyDetails customer : customers) {
				String[] data = { customer.getEmpId(), customer.getGroupName(),
						categoryDivisionValue(customer.getCategoryDiv()), workDivisionValue(customer.getWorkDiv()),
						freeResourceDivisionValue(customer.getWorkDiv(), customer.getFreeResourceDiv()),
						yesOrNoDivisionValue(customer.getLeaveDiv()),
						leaveDivisionValue(customer.getIfLeaveDiv(), customer.getLeaveDiv()),
						customer.getSubmittedDate() };
				csvWriter.writeNext(data);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String categoryDivisionValue(String value) {
		if (value.equals("2")) {
			return "DevOps";
		}
		if (value.equals("1")) {
			return "Catalog";
		}
		return "---";
	}

	public static String yesOrNoDivisionValue(String value) {
		if (value.equals("1")) {
			return "Yes";
		} else if (value.equals("2") || value.equals("0")) {
			return "No";
		}
		return "";
	}

	public static String workDivisionValue(String value) {
		if (value.equals("1")) {
			return "Yes";
		}
		if (value.equals("2") || value.equals("0")) {
			return "No";
		}
		return "";
	}

	public static String freeResourceDivisionValue(String value, String value1) {
		if (value.equalsIgnoreCase("1")) {
			if (value1.equals("1")) {
				return "Yes";
			} else if (value1.equals("2") || value1.equals("0")) {
				return "No";
			}
		}
		return "";
	}

	public static String leaveDivisionValue(String value, String value2) {
		if (value2.equalsIgnoreCase("1")) {
			if (value.equals("1")) {
				return "Full Day";
			} else if (value.equals("2")) {
				return "Half Day";
			}
		} else {
			return "No";
		}
		return "";
	}

	public static String dateValue(Date a) {
		SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		return dateFormat.format(a);
	}
}