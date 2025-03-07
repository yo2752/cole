function generarExcel() {
	document.formData.action = "generarExcelLiquidacion620.action";
	document.formData.submit();
}

function limpiarConsulta(){
	$("#idContrato").val("");
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
	
}