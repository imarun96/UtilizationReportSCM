function openInputPage() {
	window.open("/submitForm", "_self");
}
function showEmployees() {
	var x = document.getElementById("un-submitted-employee-list-id");
	x.style.display = "block";
}
function initialFunction() {
	var x = document.getElementById("un-submitted-employee-list-id");
	x.style.display = "none";
}
function noShowEmployees() {
	var x = document.getElementById("un-submitted-employee-list-id");
	x.style.display = "none";
}
function employeeInformation() {
	window.open("/employeeInformation", "_self");
}
function submissionDetails() {
	window.open("/submissionDetails", "_self");
}
function myFunction() {
	window.open("/download", "_self");
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
function showWeeklyStatus() {
	var date = new Date();
	var weekNumber = getWeekNumberFun(date);
	window.open("/weeklyStatus?weekNumber=" + weekNumber, "_self");
}
function showDailyStatus() {
	var date = new Date();
	var weekNumber = getWeekNumberFun(date);
	window.open("/dailyStatus?weekNumber=" + weekNumber, "_self");
}
function addOrRemoveEmployee() {
	window.open("/addOrRemoveEmployee", "_self");
}