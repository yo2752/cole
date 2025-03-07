
function validarCampos() {
	var tipoCredito = document.getElementById("idTipoCredito");
	var num=document.getElementById("idPrecioCredito");
	if (!num.value || isNaN(num.value)) {
		alert("Debe introducir un valor num\u00e9rico");
		return false;
		}
	if (tipoCredito.value=="-1") {
		alert("Debe seleccionar un tipo de cr\u00e9dito");
		return false;		}
	else {
		document.formData.action = "exportarResultadosMesResumenCreditos.action";
		document.formData.submit();
		return true;
		}	
}

function limpiarFormConsultaColegiados() {
	document.getElementById("nif").value = "";
	document.getElementById("apellidos_Nombre").value = "";
	document.getElementById("num_Colegiado").value = "";
}

function limpiarFormConsultaColegiadosNuevo() {
	document.getElementById("nif").value = "";
	document.getElementById("apellidosNombre").value = "";
	document.getElementById("numColegiado").value = "";
}

function buscarConsultaColegiados() {
	document.formData.action = "buscarConsultaColegiado.action";
	document.formData.submit();
}

function limpiarFormHistoricoCargaCreditos() {
	document.getElementById("contratoColegiado").value = "";
	document.getElementById("tipoCredito").value = "";
	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
}

function limpiarFormResumenCargaCreditos() {
	if(document.getElementById('esAdmin').value=="true"){
		$('#contratoColegiado').val("");
	}
	document.getElementById("tipoCredito").value = "";
	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
}

function limpiarFormResumenMensualCreditos() {
	document.getElementById("idcontratoColegiado").value = "-1";
	document.getElementById("idTipoCredito").value = "-1";
	document.getElementById("idPrecioCredito").value = "";
}


function limpiarFormConsultaTipoCredito() {
	document.getElementById("tipo_Credito").value = "";
	document.getElementById("descripcion").value = "";
}

function limpiarFormConsultaCreditosDisponibles() {
	document.getElementById("idcontratoColegiado").selectedIndex = 0;
}

function buscarConsultaHistoricoCreditos() {
	document.formData.action = "buscarConsultaHst.action";
	document.formData.submit();
}
