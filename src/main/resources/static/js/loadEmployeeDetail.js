function ajaxAsyncRequest() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var employee_mst_id = url.searchParams.get("employee_mst_id");
	var screenPath = url.searchParams.get("path");
	var z = document.getElementById("close-button");
	var e = document.getElementById("show-button");
	if (screenPath === "direct") {
		z.style.display = "none";
		e.style.display = "block";
	}
	if (screenPath === "indirect") {
		z.style.display = "block";
		e.style.display = "none";
	}
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var reqURL = "/load-employee-detail/" + employee_mst_id;
	xmlhttp.open("GET", reqURL, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var object = JSON.parse(xmlhttp.responseText);
				document.getElementById("emp_id").value = object.empId;
				document.getElementById("emp_name").value = object.empName;
				document.getElementById("group_name").value = object.empGroupName;
			} else {
				document.getElementById("emp_id").value = "Something is wrong !!";
			}
		}
	};
	xmlhttp.send(employee_mst_id);
}
function updateURL() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var employee_mst_id = url.searchParams.get("employee_mst_id");
	window.open(
			"/updateEmployeeInformation?employee_mst_id=" + employee_mst_id,
			"_self");
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var reqURL = "/update-employee/" + employee_mst_id;
	xmlhttp.open("GET", reqURL, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				var object = JSON.parse(xmlhttp.responseText);
				document.getElementById("emp_id").value = object.empId;
				document.getElementById("emp_name").value = object.empName;
				document.getElementById("emp_group_name").value = object.empGroupName;
			} else {
				document.getElementById("emp_id").innerHTML = "Something is wrong !!";
			}
		}
	};
	xmlhttp.send(employee_mst_id);
}
function deleteURL() {
	var empId = document.getElementById("emp_id").value;
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	var reqURL = "/delete-emp/" + empId;
	xmlhttp.open("GET", reqURL, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				if (xmlhttp.responseText == "deleted") {
					window.open("/employeeInformation", "_self");
				}
			}
		}
	};
	xmlhttp.send(empId);
}
function backToList() {
	location.reload();
	window.open("/employeeInformation", "_self");
}