function navigateToDetailURL(utilization_report_daily_details_mst_id) {
	window.open("/loadDetail?utilizationReportDailyDetailsMstId="
			+ utilization_report_daily_details_mst_id, "_self");
}
function backToDashboard() {
	window.open("/dashboard", "_self");
}