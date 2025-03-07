function generarExcel() {
	$("#formData").attr("action", "generarExcelResumenEstadisticaSolNRE06").trigger("submit");
}

function limpiarConsulta(){
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
	
}