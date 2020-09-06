var emp_name;
var emp_group_name;
document.addEventListener('DOMContentLoaded', function() {
	var options = {
		data : {
			"Inventory Management" : null,
			"Inventory Receipt & Payment" : null,
			"Hedge" : null,
			"Business" : null,
			"Sales" : null,
			"Project 1" : null,
			"Project 2" : null,
			"Project 3" : null,
			"PP" : null,
			"Cost" : null,
			"QE" : null,
			"Functional BT" : null
		},
		limit : 5
	}
	var elems = document.querySelectorAll('.autocomplete-input');
	M.Autocomplete.init(elems, options);
});
function checkform() {
	var f = document.forms["myForm"].elements;
	var cansubmit = true;
	for (var i = 1; i < 3; i++) {
		if (f[i].value.length == 0
				|| (f[1].value == emp_name && f[2].value == emp_group_name))
			cansubmit = false;
	}
	if (cansubmit) {
		document.getElementById('submitButton').disabled = false;
	} else {
		document.getElementById('submitButton').disabled = true;
	}
}
function emp_group_name_check() {
	var h = document.getElementById("emp_group_name_message");
	var empGroupName = document.getElementById("emp_group_name").value;
	empGroupName = empGroupName.trim();
	if (empGroupName.length == 0) {
		document.getElementById("emp_group_name").value = "";
		document.getElementById("emp_group_name").style.backgroundColor = "red";
		document.getElementById("emp_group_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_group_name_message").innerHTML = "Entry required.";
		document.getElementById("submitButton").disabled = true;
	} else {
		document.getElementById("emp_group_name").value = empGroupName;
		document.getElementById("emp_group_name").style.backgroundColor = "white";
		document.getElementById("emp_group_name").style.borderColor = "grey";
		h.style.display = "none";
	}
}
function emp_name_check() {
	var h = document.getElementById("emp_name_message");
	var empName = document.getElementById("emp_name").value;
	empName = empName.trim();
	if (empName.length == 0) {
		document.getElementById("emp_name").value = "";
		document.getElementById("emp_name").style.backgroundColor = "red";
		document.getElementById("emp_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_name_message").innerHTML = "Entry required.";
		document.getElementById("submitButton").disabled = true;
	} else if (empName.length >= 15) {
		document.getElementById("emp_name").value = empName;
		document.getElementById("emp_name").style.backgroundColor = "red";
		document.getElementById("emp_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_name_message").innerHTML = "Entered Emp Name exceeds the maximum length. Enter less than length 15.";
		document.getElementById("submitButton").disabled = true;
	} else {
		document.getElementById("emp_name").value = empName;
		document.getElementById("emp_name").style.backgroundColor = "white";
		document.getElementById("emp_name").style.borderColor = "grey";
		h.style.display = "none";
	}
}
function ajaxAsyncRequest() {
	document.getElementById('submitButton').disabled = true;
	var url_string = window.location.href;
	var url = new URL(url_string);
	var employee_mst_id = url.searchParams.get("employee_mst_id");
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
				emp_name = object.empName;
				emp_group_name = object.empGroupName;
			} else {
				document.getElementById("emp_id").innerHTML = "Something is wrong !!";
			}
		}
	};
	xmlhttp.send(employee_mst_id);
}
function backToDetail() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var employee_mst_id = url.searchParams.get("employee_mst_id");
	window.open("/loadEmployeeDetail?employee_mst_id=" + employee_mst_id
			+ "&path=direct", "_self");
}