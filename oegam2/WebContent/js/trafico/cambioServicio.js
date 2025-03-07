function guardarAltaTramiteTraficoCambServ() {
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltaTramTrafCambioServicio.action#"+pestania).trigger("submit");
}

/*function buscarVehiculoCambServ(matriculaVehiculo) {
	$("#matriculaBusqueda").val( $("#"+ matriculaVehiculo).val());
	document.formData.action = "buscarVehiculoAltaTramTrafCambioServicio.action#Vehiculo";
	document.formData.submit();
}*/

function limpiarVehiculoAltaTramiteTraficoCambServ(pestania) {
	document.getElementById("matriculaVehiculo").readOnly = false;

	document.getElementById("idVehiculoText").value = "";
	document.getElementById("matriculaVehiculo").value = "";
	document.getElementById("idServicioTraficoAnterior").value = "-1";
	document.getElementById("idServicioTrafico").value = "-1";
	document.getElementById("diaMatriculacionVehiculo").value = "";
	document.getElementById("mesMatriculacionVehiculo").value = "";
	document.getElementById("anioMatriculacionVehiculo").value = "";
	document.getElementById("diaItv").value = "";
	document.getElementById("mesItv").value = "";
	document.getElementById("anioItv").value = "";
}

function buscarVehiculoCambioServ(pestania) {
	document.formData.action = "buscarVehiculoAltaTramTrafCambioServicio.action#" + pestania;
	document.formData.submit();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function buscarIntervinienteCambioServ(tipoInterviniente, pestania) {
	limpiarHiddenInput("tipoIntervinienteBuscar");
	input = $("<input>").attr("type", "hidden").attr("name", "tipoIntervinienteBuscar").val(tipoInterviniente);
	$('#formData').append($(input));
	document.formData.action = "consultarPersonaAltaTramTrafCambioServicio.action#" + pestania;
	document.formData.submit();
}
function pendientesEnvioAExcelCambServ() {
	var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: 1 cr\u00e9dito";
	if (confirm(textoAlert)) {
		var pestania = obtenerPestaniaSeleccionada();
		document.formData.action = "pendientesEnvioExcelAltaTramTrafCambioServicio.action?idHiddenNumeroExpediente="
			+ document.getElementById("idHiddenNumeroExpediente").value
			+ "&excelDesdeAlta=true#" + pestania;
		document.formData.submit();
	}
}

function validarAltaTramiteTraficoCambServ() {
	if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}

	if (document.getElementById("dniRepresentante").value != "" && document.getElementById("apellido1RepresentanteTitular").value == "") {
		alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	deshabilitarBotonesCambioServicio();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "validarAltaTramTrafCambioServicio.action#"+pestania).trigger("submit");
}

function habilitarBotonesCambioServicio() {
	if (!$('#bValidarCambServ').is(':hidden')) {
		$("#bValidarCambServ").css("color", "#000000");
		$("#bValidarCambServ").prop("disabled", false);
	}

	if (!$('#bGuardarCambServ').is(':hidden')) {
		$("#bGuardarCambServ").css("color", "#000000");
		$("#bGuardarCambServ").prop("disabled", false);
	}

	if (!$('#bPdteExcelCambServ').is(':hidden')) {
		$("#bPdteExcelCambServ").css("color", "#000000");
		$("#bPdteExcelCambServ").prop("disabled", false);
	}

}
function deshabilitarBotonesCambioServicio() {
	$("#bValidarCambServ").prop('disabled', true);
	$("#bValidarCambServ").css('color', '#6E6E6E');
	$("#bGuardarCambServ").prop('disabled', true);
	$("#bGuardarCambServ").css('color', '#6E6E6E');
	$("#bPdteExcelCambServ").prop('disabled', true);
	$("#bPdteExcelCambServ").css('color', '#6E6E6E');
}