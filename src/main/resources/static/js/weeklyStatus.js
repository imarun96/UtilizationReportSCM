function myFunction() {
	var input, filter, table, tr, td, i, txtValue;
	input = document.getElementById("myInput");
	filter = input.value.toUpperCase();
	table = document.getElementById("myTable");
	tr = table.getElementsByTagName("tr");
	for (i = 0; i < tr.length; i++) {
		td = tr[i].getElementsByTagName("td")[0];
		if (td) {
			txtValue = td.textContent || td.innerText;
			if (txtValue.toUpperCase().indexOf(filter) > -1) {
				tr[i].style.display = "";
			} else {
				tr[i].style.display = "none";
			}
		}
	}
}
function getWeekNumberFun(dt) {
	var tdt = new Date(dt.valueOf());
	var dayn = (dt.getDay() + 6) % 7;
	tdt.setDate(tdt.getDate() - dayn + 3);
	var firstThursday = tdt.valueOf();
	tdt.setMonth(0, 1);
	if (tdt.getDay() !== 4) {
		tdt.setMonth(0, 1 + ((4 - tdt.getDay() + 7) % 7));
	}
	return 1 + Math.ceil((firstThursday - tdt) / 604800000);
}
function addOption() {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var weekNumber1 = url.searchParams.get("weekNumber");
	var select = document.getElementById("week_number_select_div");
	var date = new Date();
	var weekNumber = getWeekNumberFun(date);
	var i;
	for (i = weekNumber; i > 0; i--) {
		select.options[select.options.length] = new Option(i, i);
	}
	var temp = weekNumber1;
	for (var k, j = 0; k = select.options[j]; j++) {
		if (k.value == temp) {
			select.selectedIndex = j;
			break;
		}
	}
	changeTableHeaderFunction("get-weekly-man-days/");
}
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
function backToDashboard() {
	window.open("/dashboard", "_self");
}
function changeTableHeaderFunction(reqURL) {
	var url_string = window.location.href;
	var url = new URL(url_string);
	var weekNumber = url.searchParams.get("weekNumber");
	document.getElementById("label_id").innerHTML = "<b>Week Number : "
			+ weekNumber + "</b>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;";
	var xmlhttp;
	if (window.XMLHttpRequest) {
		xmlhttp = new XMLHttpRequest();
	} else {
		xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	}
	xmlhttp.open("GET", reqURL + weekNumber, true);
	xmlhttp.onreadystatechange = function() {
		if (xmlhttp.readyState == 4) {
			if (xmlhttp.status == 200) {
				document.getElementById("dynamic-table").innerHTML = xmlhttp.responseText;
			}
		}
	};
	xmlhttp.send(weekNumber);
}
function onChangeTableHeaderChange() {
	var weekNumber = document.getElementById("week_number_select_div").value;
	window.open("/weeklyStatus?weekNumber=" + weekNumber, "_self");
}