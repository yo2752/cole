var opciones_ventanaEvJus = 'width=850,height=450,top=050,left=200';

function numChecked() {
	var checks = document.getElementsByName("idJustificanteInternos");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function tieneNumExpedienteJustificante() {
	var numExp =document.getElementsByName("tramiteTrafDto.numExpediente");
	if (numExp != null && numExp.value!=null && document.getElementById("tipoTramiteId") != null)
		document.getElementById("tipoTramiteId").disabled=true;
}

function buscarIntervinienteJustificanteProf(tipoInterviniente, pestania, idNif) {
	$("#nifBusqueda").val( $("#"+ idNif).val());
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "consultarPersonaAltaTramiteJustificanteProfesional.action#" + pestania;
	document.formData.submit();
}

function buscarVehiculoJustificante(idMatricula) {
	$("#matriculaBusqueda").val( $("#"+ idMatricula).val());
	document.formData.action = "buscarVehiculoAltaTramiteJustificanteProfesional.action#Vehiculo";
	document.formData.submit();
}

function altaJustificanteProf(boton){
	var url = (document.URL);
	var pestania = '';
	if(url.indexOf('#')!=-1){
		pestania = (document.URL).substr((document.URL).indexOf('#') + 1);
	}
	generarJustificanteAltasJustificanteProfesional(boton, pestania);
}

function generarJustificanteAltasJustificanteProfesional(boton, pestania) {
	var numExp =document.getElementsByName("tramiteTrafDto.numExpediente");
	if (numExp[0].value == null || numExp[0].value == "") {
		if (document.getElementById("tipoTramiteId").value == "-1") {
			alert("Debe seleccionar un tipo de tr\u00E1mite");
			return false;
		}
	}
	if (document.getElementById("idNifAdquiriente").value != "") {
		if (!validaNIF(document.getElementById("idNifAdquiriente"))) {
			alert("NIF de Titular no v\u00e1lido.");
			return false;
		}
	}
	if (document.getElementById("idCheckFa").checked == true
			&& document.getElementById("idFa").value.length < 6) {
		alert("El identificador de las fuerzas armadas se compone de 6 d\u00EDgitos");
		return false;
	}
	mostrarLoadingConsultar(boton);
	document.formData.action = "generarJustificanteAltaTramiteJustificanteProfesional.action#"
			+ pestania;
	document.formData.submit();
	return true;
}

function buscarConsultaJustificante() {
	
    var matricula = new String(document.getElementById("matricula").value);
	
	matricula=Limpia_Caracteres_Matricula(matricula);
	
	document.getElementById("matricula").value = matricula.toUpperCase();
	
	document.formData.action = "buscarConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
}

function consultaEvolucionJustificantes(numExpediente, idJustificanteInterno) {
	if (numExpediente == null || numExpediente == '') {
		alert('No se puede referenciar a ning\u00FAn tr\u00E1mite.');
		return false;
	}
	
	var ventana_evolucion = window.open('inicioConsultaEvJustificanteProf.action?numExpediente=' + numExpediente + '&idJustificanteInterno=' + idJustificanteInterno, 'popup', opciones_ventanaEvJus);
}

function pendienteAutorizacionColegio(){
	if(numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	document.formData.action="pendienteAutorizacionColegioConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
	return true;
}

function forzarJpb(){
	if(numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}

	document.formData.action="forzarConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
	return true;
}

function imprimirJustificante() {
	if(numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}

	document.formData.action="imprimirConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
	return true;
}

function recuperarJPb(){
	if(numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}

	document.formData.action="recuperarConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
	return true;
}

function anularJPb(){
	if(numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}

	document.formData.action="anularConsultaTramiteTraficoJustificanteProfesional.action";
	document.formData.submit();
	return true;
}

function limpiarConsultaJustificantesProfesionales() {
	
	if (document.getElementById("numExpediente")) {
		document.getElementById("numExpediente").value = "";
	}
	
	if (document.getElementById("estado")) {
		document.getElementById("estado").value = "-1";
	}
	
	if (document.getElementById("idJustificante")) {
		document.getElementById("idJustificante").value = "";
	}
	
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}
	
	if (document.getElementById("matricula")) {
		document.getElementById("matricula").value = "";
	}
	
	if (document.getElementById("diaInicio")) {
		document.getElementById("diaInicio").value = "";
	}
	
	if (document.getElementById("mesInicio")) {
		document.getElementById("mesInicio").value = "";
	}
	
	if (document.getElementById("anioInicio")) {
		document.getElementById("anioInicio").value = "";
	}
	
	if (document.getElementById("diaFin")) {
		document.getElementById("diaFin").value = "";
	}
	
	if (document.getElementById("mesFin")) {
		document.getElementById("mesFin").value = "";
	}
	
	if (document.getElementById("anioFin")) {
		document.getElementById("anioFin").value = "";
	}
	
	if (document.getElementById("idJustificanteInterno")) {
		document.getElementById("idJustificanteInterno").value = "";
	}
}

function volverTramite(){
	window.history.back();
}

function buscarVerificacionJustificanteProf(boton) {
	if (document.getElementById('csv') == null
			|| document.getElementById('csv').value == "") {
		alert("Debe indicar un Codigo Seguro de Verificacion para poder realizar la consulta");
		return false;
	}
	document.formData.action = "buscarVerificarVerificacionJustificante.action";
	loadingBusquedaSolicitud(boton);
	document.formData.submit();
}

function verificarJustificanteProf(boton) {
	if (document.getElementById('csv') == null
			|| document.getElementById('csv').value == "") {
		alert("Debe indicar un Codigo Seguro de Verificacion para poder realizar la consulta");
		return false;
	}
	document.formData.action = "verificarVerificacionJustificante.action";
	loadingBusquedaSolicitud(boton);
	document.formData.submit();
}

function imprimirVerificacionJustificanteProf() {
	document.formData.action="imprimirVerificacionJustificante.action";
	document.formData.submit();
	return true;
}