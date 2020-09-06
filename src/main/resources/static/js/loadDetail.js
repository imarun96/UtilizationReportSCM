function ajaxAsyncRequest() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var utilizationReportDailyDetailsMstId = url.searchParams
			.get("utilizationReportDailyDetailsMstId");
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var reqURL = "/load-utilization-detail/"
			+ utilizationReportDailyDetailsMstId;
	xmlhttp.open("GET", reqURL, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var object = JSON.parse(xmlhttp.responseText);
				document.getElementById("emp_id").value = object.empId;
				document.getElementById("emp_name").value = object.empName;
				document.getElementById("submitted_date").value = object.submittedDate;
				document.getElementById("group_name").value = object.groupName;
				document.getElementById("category_div").value = categoryValueDecider(object.categoryDiv);
				document.getElementById("work_div").value = workValueDecider(object.workDiv);
				document.getElementById("free_resource_div").value = freeResourceValueDecider(object.freeResourceDiv);
				document.getElementById("leave_div").value = leaveValueDecider(object.leaveDiv);
				document.getElementById("if_leave_div").value = ifLeaveValueDecider(object.ifLeaveDiv);
			} else {
				document.getElementById("emp_id").innerHTML = "Something is wrong !!";
			}
		}
	};
	xmlhttp.send(utilizationReportDailyDetailsMstId);
}
function backToList() {
	window.open("/submissionDetails", "_self");
}
function categoryValueDecider(categoryDiv) {
	switch (categoryDiv) {
	case 1:
		return "Catalog";
		break;
	case 2:
		return "DevOps";
		break;
	case 3:
		return "System QE";
		break;
	default:
		return "---";
		break;
	}
}
function workValueDecider(workDiv) {
	switch (workDiv) {
	case 1:
		return "YES";
		break;
	case 2:
		return "NO";
		break;
	default:
		return "NO";
		break;
	}
}
function freeResourceValueDecider(freeResourceDiv) {
	switch (freeResourceDiv) {
	case 1:
		return "YES";
		break;
	case 2:
		return "NO";
		break;
	default:
		return "NO";
		break;
	}
}
function leaveValueDecider(leaveDiv) {
	switch (leaveDiv) {
	case 1:
		return "YES";
		break;
	case 2:
		return "NO";
		break;
	default:
		return "---";
		break;
	}
}
function ifLeaveValueDecider(ifLeaveDiv) {
	switch (ifLeaveDiv) {
	case 1:
		return "FULL DAY";
		break;
	case 2:
		return "HALF DAY";
		break;
	default:
		return "---";
		break;
	}
}