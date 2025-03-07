function limpiarFormulario(){
	if (document.getElementById("idTipoDocumento")) {
		document.getElementById("idTipoDocumento").value = "";
	}
	
	if (document.getElementById("diaFechaFin")) {
		document.getElementById("diaFechaFin").value = "";
	}
	if (document.getElementById("mesFechaFin")) {
		document.getElementById("mesFechaFin").value = "";
	}
	if (document.getElementById("anioFechaFin")) {
		document.getElementById("anioFechaFin").value = "";
	}
	if (document.getElementById("diaFechaIni")) {
		document.getElementById("diaFechaIni").value = "";
	}
	if (document.getElementById("mesFechaIni")) {
		document.getElementById("mesFechaIni").value = "";
	}
	if (document.getElementById("anioFechaIni")) {
		document.getElementById("anioFechaIni").value = "";
	}

}



function generar(){
	
	$('#formData').attr("action","generarExcelEstadisticaImpresion.action");
	$('#formData').submit();			
	
}
