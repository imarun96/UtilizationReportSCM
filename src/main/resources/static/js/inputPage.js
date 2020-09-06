function initialFunction() {
	var d = new Date();
	d.setDate(d.getDate());
	document.getElementById("submitted_date").valueAsDate = d;
	var x = document.getElementById("category-hide-id");
	x.style.display = "none";
	var y = document.getElementById("hide_id");
	y.style.display = "none";
	var z = document.getElementById("hide_work_div_id");
	z.style.display = "none";
	var h = document.getElementById("free_resource_div_id");
	h.style.display = "none";
	document.getElementById("emp_id").focus();
	document.getElementById("emp_id").style.backgroundColor = "red";
	document.getElementById("emp_id").style.borderColor = "red";
}
function hideAndDisplayFunction() {
	var x = document.getElementById("hide_id");
	var h = document.getElementById("free_resource_div_id");
	var z = document.getElementById("hide_work_div_id");
	if (document.getElementById("leave_select_div").value == "2") {
		x.style.display = "none";
		z.style.display = "block";
		document.getElementById("work_div_id").required = true;
	} else {
		x.style.display = "block";
		document.getElementById("if-leave-id").required = true;
		h.style.display = "none";
		z.style.display = "none";
	}
}
function hideDivElementFunction() {
	var x = document.getElementById("free_resource_div_id");
	var z = document.getElementById("category-hide-id");
	if (document.getElementById("work_div_id").value == "1") {
		x.style.display = "none";
		z.style.display = "block";
		document.getElementById("category-required-hide-id").required = true;
	} else {
		x.style.display = "block";
		z.style.display = "none";
		document.getElementById("free-resource-required-id").required = true;
	}
}
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
document.addEventListener("DOMContentLoaded", function() {
	const box = document.querySelectorAll(".modal");
	M.Modal.init(box, {
		opacity : 0.6,
		inDuration : 300,
		outDuration : 375,
		preventScrolling : true,
		startingTop : '4%',
		endingTop : '10%',
		dismissible : true
	});
});
function topFunction() {
	document.body.scrollTop = 0;
	document.documentElement.scrollTop = 0;
}
window.onscroll = function() {
	scrollFunction()
};

function scrollFunction() {
	var mybutton = document.getElementById("myBtn");
	if (document.body.scrollTop > 20 || document.documentElement.scrollTop > 20) {
		mybutton.style.display = "block";
	} else {
		mybutton.style.display = "none";
	}
}
function ajaxAsyncRequest(reqURL) {
	var empId = document.getElementById("emp_id").value;
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
				document.getElementById("message").innerHTML = xmlhttp.responseText;
			} else {
				document.getElementById("message").innerHTML = "Something is wrong !!";
			}
		}
	};
	xmlhttp.send(empId);
}
function backToDashboard() {
	window.open('/dashboard', "_self");
}