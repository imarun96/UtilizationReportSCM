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
function initialFunction() {
	document.getElementById("submitButton").disabled = true;
}
String.prototype.isNumber = function() {
	return /^\d+$/.test(this);
}
function checkform() {
	var f = document.forms["myForm"].elements;
	var cansubmit = true;
	for (var i = 0; i < 3; i++) {
		if (f[i].value.length == 0)
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
	var emp_group_name = document.getElementById("emp_group_name").value;
	emp_group_name = emp_group_name.trim();
	if (emp_group_name.length == 0) {
		document.getElementById("emp_group_name").value = "";
		document.getElementById("emp_group_name").style.backgroundColor = "red";
		document.getElementById("emp_group_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_group_name_message").innerHTML = "Entry required.";
		document.getElementById("submitButton").disabled = true;
	} else {
		document.getElementById("emp_group_name").value = emp_group_name;
		document.getElementById("emp_group_name").style.backgroundColor = "white";
		document.getElementById("emp_group_name").style.borderColor = "grey";
		h.style.display = "none";
	}
}
function emp_name_check() {
	var h = document.getElementById("emp_name_message");
	var emp_name = document.getElementById("emp_name").value;
	emp_name = emp_name.trim();
	if (emp_name.length == 0) {
		document.getElementById("emp_name").value = "";
		document.getElementById("emp_name").style.backgroundColor = "red";
		document.getElementById("emp_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_name_message").innerHTML = "Entry required.";
		document.getElementById("submitButton").disabled = true;
	} else if (emp_name.length >= 15) {
		document.getElementById("emp_name").value = emp_name;
		document.getElementById("emp_name").style.backgroundColor = "red";
		document.getElementById("emp_name").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("emp_name_message").innerHTML = "Entered Emp Name exceeds the maximum length. Enter less than length 15.";
		document.getElementById("submitButton").disabled = true;
	} else {
		document.getElementById("emp_name").value = emp_name;
		document.getElementById("emp_name").style.backgroundColor = "white";
		document.getElementById("emp_name").style.borderColor = "grey";
		h.style.display = "none";
	}
}
function emp_id_existence_check(reqURL) {
	var h = document.getElementById("message");
	var empId = document.getElementById("emp_id").value;
	empId = empId.trim();
	if (empId.isNumber() && empId.length < 5) {
		document.getElementById("emp_id").value = empId;
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}
		xmlhttp.open("GET", reqURL + empId, true);
		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4) {
				if (xmlhttp.status == 200) {
					if (xmlhttp.responseText == "present") {
						document.getElementById("emp_id").style.backgroundColor = "red";
						document.getElementById("emp_id").style.borderColor = "red";
						h.style.display = "block";
						document.getElementById("message").innerHTML = "A record with the entered Emp Id already exists. Please enter a new Emp Id.";
						document.getElementById("submitButton").disabled = true;
					} else {
						document.getElementById("emp_id").style.backgroundColor = "white";
						document.getElementById("emp_id").style.borderColor = "grey";
						h.style.display = "none";
					}
				}
			}
		};
		xmlhttp.send(empId);
	} else if (!empId.isNumber() && empId.length != 0) {
		document.getElementById("emp_id").style.backgroundColor = "red";
		document.getElementById("emp_id").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("message").innerHTML = "Only Numbers can be entered.";
		document.getElementById("submitButton").disabled = true;
	} else if (empId.length >= 5) {
		document.getElementById("emp_id").style.backgroundColor = "red";
		document.getElementById("emp_id").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("message").innerHTML = "Entered Emp Id exceeds the maximum length. Enter less than length 5.";
		document.getElementById("submitButton").disabled = true;
	} else if (empId.length == 0) {
		document.getElementById("emp_id").value = "";
		document.getElementById("emp_id").style.backgroundColor = "red";
		document.getElementById("emp_id").style.borderColor = "red";
		h.style.display = "block";
		document.getElementById("message").innerHTML = "Entry required.";
		document.getElementById("submitButton").disabled = true;
	}
}
function backToDashboard() {
	window.open("/employeeInformation", "_self");
}