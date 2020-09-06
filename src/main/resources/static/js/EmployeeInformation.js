function navigateToDetailURL(emp_mst_id) {
	window.open("/loadEmployeeDetail?employee_mst_id=" + emp_mst_id
			+ "&path=direct", "_self");
}
function backToDashboard() {
	window.open("/dashboard", "_self");
}
function openInputPage() {
	window.open("/addEmployee", "_self");
}