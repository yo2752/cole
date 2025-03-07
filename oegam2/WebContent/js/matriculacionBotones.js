function guardarAltaTramiteTraficoMatriculacion() {

	deshabilitarBotonesMatriculacion();
	if (!validaNIF(document.getElementById("nifTitular"))) {
		habilitarBotonesMatriculacion();
		alert("El NIF del titular no es v\u00e1lido.");
		return false;
	}

	if (!validaNIF(document.getElementById("nifRepresentante"))) {
		habilitarBotonesMatriculacion();
		alert("El NIF del representante del titular no es v\u00e1lido.");
		return false;
	}

	if (!validaNIF(document.getElementById("nifConductorHabitual"))) {
		habilitarBotonesMatriculacion();
		alert("El NIF del conductor habitual no es v\u00e1lido.");
		return false;
	}

	if (!validaNIF(document.getElementById("idNifArrendatario"))) {
		habilitarBotonesMatriculacion();
		alert("El NIF del arrendatario no es v\u00e1lido.");
		return false;
	}

	var nifTitular = document.getElementById("nifTitular").value;
	var nifConductorHabitual = document.getElementById("nifConductorHabitual").value;
	if((nifTitular != null && nifConductorHabitual != null) && (nifTitular != "" && nifConductorHabitual != "" ) &&
			(nifTitular == nifConductorHabitual)) {
		habilitarBotonesMatriculacion();
		alert("Indique datos de conductor habitual, solo cuando sea distinto del titular del veh\u00edculo");
		return false;
	}

	if (document.getElementById("diaAltaIni") != null && document.getElementById("mesAltaIni") != null
			&& document.getElementById("anioAltaIni") != null && document.getElementById("diaAltaFin") != null
			&& document.getElementById("mesAltaFin") != null && document.getElementById("anioAltaFin") != null
			&& document.getElementById("diaAltaIni").value != "" && document.getElementById("mesAltaIni").value != ""
			&& document.getElementById("anioAltaIni").value != "" && document.getElementById("diaAltaFin").value != ""
			&& document.getElementById("mesAltaFin").value != "" && document.getElementById("anioAltaFin").value != "") {
		var fInicioConductorHabitual = new Date(document.getElementById("anioAltaIni").value, parseInt(document
		.getElementById("mesAltaIni").value) - 1, document.getElementById("diaAltaIni").value).getTime();
		var fFinConductorHabitual = new Date(document.getElementById("anioAltaFin").value, parseInt(document
		.getElementById("mesAltaFin").value) - 1, document.getElementById("diaAltaFin").value).getTime();
		var fActual = new Date().getTime();

		if (fInicioConductorHabitual > fFinConductorHabitual) {
			habilitarBotonesMatriculacion();
			alert("La fecha de Inicio del Titular no puede ser mayor que la fecha de Fin.");
			return false;
		}

		if (fInicioConductorHabitual > fActual) {
			habilitarBotonesMatriculacion();
			alert("La fecha de Inicio del Titular no puede ser mayor que la fecha actual.");
			return false;
		}
	}

	var valueConcepto = document.getElementById("conceptoRepresentanteTitular").options[document.getElementById("conceptoRepresentanteTitular").selectedIndex].value;
	var motivoTutela = document.getElementById("motivoRepresentanteTitular").options[document.getElementById("motivoRepresentanteTitular").selectedIndex].value;
	var titularEsEmpresa = document.getElementById("sexoTitular").options[document.getElementById("sexoTitular").selectedIndex].value;

	if(valueConcepto == "TUTELA O PATRIA POTESTAD" && titularEsEmpresa == "X"){
		habilitarBotonesMatriculacion();
		alert("El concepto del representante no es v\u00e1lido para una empresa");
		return false;
	}

	if(valueConcepto == "TUTELA O PATRIA POTESTAD"){
		if(motivoTutela == null || motivoTutela == "-1"){
			habilitarBotonesMatriculacion();
			alert("Requerido el motivo de la tutela o patria potestad");
			return false;
		}
	}

	if (document.getElementById("diaInicioTutela") != null && document.getElementById("diaInicioTutela").value != ""
			&& document.getElementById("mesInicioTutela") != null && document.getElementById("mesInicioTutela").value != ""
			&& document.getElementById("anioInicioTutela") != null && document.getElementById("anioInicioTutela").value != ""
			&& document.getElementById("diaFinTutela") != null && document.getElementById("diaFinTutela").value != ""
			&& document.getElementById("mesFinTutela") != null && document.getElementById("mesFinTutela").value != ""
			&& document.getElementById("anioFinTutela") != null && document.getElementById("anioFinTutela").value != "") {
		var fInicioTutela = new Date(document.getElementById("anioInicioTutela").value, parseInt(document
				.getElementById("mesInicioTutela").value) - 1, document.getElementById("diaInicioTutela").value).getTime();
		var fFinTutela = new Date(document.getElementById("anioFinTutela").value,parseInt(document.getElementById("mesFinTutela").value) - 1,
				document.getElementById("diaFinTutela").value).getTime();
		var fActualTutela = new Date().getTime();

		if (fInicioTutela > fFinTutela) {
			habilitarBotonesMatriculacion();
			alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha de Fin.");
			return false;
		}

		if (fInicioTutela > fActualTutela) {
			habilitarBotonesMatriculacion();
			alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha actual.");
			return false;
		}
	}

	if ((document.getElementById("tipoViaVehiculo").value.length + document.getElementById("nombreViaDireccionVehiculo").value.length
			+ document.getElementById("numeroDireccionVehiculo").value.length + document.getElementById("letraDireccionVehiculo").value.length
			+ document.getElementById("pisoDireccionVehiculo").value.length + document.getElementById("puertaDireccionVehiculo").value.length
			+ document.getElementById("bloqueDireccionVehiculo").value.length + document.getElementById("kmDireccionVehiculo").value.length
			+ document.getElementById("escaleraDireccionVehiculo").value.length + document.getElementById("hmDireccionVehiculo").value.length) > 26) {
		if (!confirm("El conjunto de los campos que forman el domicilio del vehiculo ("
				+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?")) {
			habilitarBotonesMatriculacion();
			return false;
		}
	} else if ((document.getElementById("tipoViaDireccionTitular").value.length + document.getElementById("nombreViaDireccionTitular").value.length
			+ document.getElementById("numeroDireccionTitular").value.length + document.getElementById("letraDireccionTitular").value.length
			+ document.getElementById("pisoDireccionTitular").value.length + document.getElementById("puertaDireccionTitular").value.length
			+ document.getElementById("bloqueDireccionTitular").value.length + document.getElementById("kmDireccionTitular").value.length
			+ document.getElementById("escaleraDireccionTitular").value.length + document.getElementById("hmDireccionTitular").value.length) > 26) {
		if (!confirm("El conjunto de los campos que forman el domicilio del titular del vehiculo ("
				+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?")) {
			habilitarBotonesMatriculacion();
			return false;
		}
	}

	if (exento05()) {
		if (document.formData.numRegistro.options[document.formData.numRegistro.selectedIndex].value == '') {
			alert("Debe seleccionar el numero de registro asociado a la Reduccion, no sujecion o exencion solicitada");
		}
	}

	if (!validarMaximos()) {
		habilitarBotonesMatriculacion();
		return false;
	}

	if (!campoVacio("idBastidor")){
		if (document.getElementById("idEcoInnovacion").selectedIndex == 1){
			if (campoVacio("idCodigoEco")){
				habilitarBotonesMatriculacion();
				alert ("El campo codigo ECO es obligatorio.");
				return false;
			}
		}
	}

	var pestania = obtenerPestaniaSeleccionada();

	document.formData.action = "guardarAltaTramiteTraficoMatriculacion.action#" + pestania;
	document.formData.submit();
	return true;
}

function buscarIntervinienteMatriculacion(tipoInterviniente, pestania, idNif) {
	$("#nifBusqueda").val($("#"+ idNif).val());
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "consultarPersonaAltaTramiteTraficoMatriculacion.action#" + pestania;
	document.formData.submit();
}

function limpiarConductorHabitual(){
	var nifConductorHabitual = document.getElementById("nifConductorHabitual").value;
	if (nifConductorHabitual!=null && nifConductorHabitual!="") {
		document.getElementById("conductorHabitualDiaCadNif").value = "";
		document.getElementById("conductorHabitualMesCadNif").value = "";
		document.getElementById("conductorHabitualAnioCadNif").value = "";
		document.getElementById("conductorHabitualOtroDocumentoIdentidad").checked = false;
		document.getElementById("conductorHabitualIndefinido").checked = false;
		document.getElementById("conductorHabitualTipoDocumentoAlternativo").selectedIndex = 0;
		document.getElementById("conductorHabitualDiaCadAlternativo").value = "";
		document.getElementById("conductorHabitualMesCadAlternativo").value = "";
		document.getElementById("conductorHabitualAnioCadAlternativo").value = "";
		document.getElementById("sexoConductorHabitual").value = "-1";
		document.getElementById("apellido1RazonSocialConductorHabitual").value = "";
		document.getElementById("apellido2ConductorHabitual").value = "";
		document.getElementById("nombreConductorHabitual").value = "";
		document.getElementById("diaNacConductorHabitual").value = "";
		document.getElementById("mesNacConductorHabitual").value = "";
		document.getElementById("anioNacConductorHabitual").value = "";
		document.getElementById("estadoCivilConductorHabitual").value = "-1";
		document.getElementById("idDireccionConductorHabitual").value = "";
		document.getElementById("idProvinciaConductorHabitual").value = "-1";
		document.getElementById("idMunicipioConductorHabitual").length = 0;
		document.getElementById("idMunicipioConductorHabitual").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoConductorHabitual").value = "";
		document.getElementById("idPuebloConductorHabitual").length = 0;
		document.getElementById("idPuebloConductorHabitual").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoConductorHabitual").value = "";
		document.getElementById("codPostalConductorHabitual").value = "";
		document.getElementById("tipoViaDireccionConductorHabitual").length = 0;
		document.getElementById("tipoViaDireccionConductorHabitual").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaDireccionConductorHabitual").value = "";
		document.getElementById("nombreViaDireccionConductorHabitual").value = "";
		document.getElementById("numeroDireccionConductorHabitual").value = "";
		document.getElementById("letraDireccionConductorHabitual").value = "";
		document.getElementById("escaleraDireccionConductorHabitual").value = "";
		document.getElementById("pisoDireccionConductorHabitual").value = "";
		document.getElementById("puertaDireccionConductorHabitual").value = "";
		document.getElementById("bloqueDireccionConductorHabitual").value = "";
		document.getElementById("kmDireccionConductorHabitual").value = "";
		document.getElementById("hmDireccionConductorHabitual").value = "";
		document.getElementById("idCambioDomicilioConductorHabitual").checked = false;
		document.getElementById("diaAltaFin").value = "";
		document.getElementById("mesAltaFin").value = "";
		document.getElementById("anioAltaFin").value = "";
		document.getElementById("horaAltaFin").value = "";
		if(document.getElementById("asignarPrincipalConductorHabitual")) {
			document.getElementById("asignarPrincipalConductorHabitual").value = "SI";
		}
	}
}

function limpiarFormularioRenting(){
	var nifArrendatario = document.getElementById("idNifArrendatario").value;
	if (nifArrendatario!=null && nifArrendatario!="") {
		document.getElementById("arrendatarioDiaCadNif").value = "";
		document.getElementById("arrendatarioMesCadNif").value = "";
		document.getElementById("arrendatarioAnioCadNif").value = "";
		document.getElementById("arrendatarioOtroDocumentoIdentidad").checked = false;
		document.getElementById("arrendatarioIndefinido").checked = false;
		document.getElementById("arrendatarioTipoDocumentoAlternativo").selectedIndex = 0;
		document.getElementById("arrendatarioDiaCadAlternativo").value = "";
		document.getElementById("arrendatarioMesCadAlternativo").value = "";
		document.getElementById("arrendatarioAnioCadAlternativo").value = "";
		document.getElementById("tipoPersonaArrendatario").value = "-1";
		document.getElementById("apellido1RazonSocialArrendatario").value = "";
		document.getElementById("apellido2Arrendatario").value = "";
		document.getElementById("nombreArrendatario").value = "";
		document.getElementById("sexoArrendatario").value = "-1";
		document.getElementById("diaNacArrendatario").value = "";
		document.getElementById("mesNacArrendatario").value = "";
		document.getElementById("anioNacArrendatario").value = "";
		document.getElementById("idDireccionAdminRenMatr").value = "";
		document.getElementById("idProvinciaRenting").value = "-1";
		document.getElementById("idMunicipioRenting").length = 0;
		document.getElementById("idMunicipioRenting").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoRenting").value = "";
		document.getElementById("idPuebloRenting").length = 0;
		document.getElementById("idPuebloRenting").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoRenting").value = "";
		document.getElementById("codPostalRenting").value = "";
		document.getElementById("tipoViaDireccionRenting").length = 0;
		document.getElementById("tipoViaDireccionRenting").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaDireccionRenting").value = "";
		document.getElementById("nombreViaDireccionRenting").value = "";
		document.getElementById("numeroDireccionRenting").value = "";
		document.getElementById("letraDireccionRenting").value = "";
		document.getElementById("escaleraDireccionRenting").value = "";
		document.getElementById("pisoDireccionRenting").value = "";
		document.getElementById("puertaDireccionRenting").value = "";
		document.getElementById("bloqueDireccionRenting").value = "";
		document.getElementById("kmDireccionRenting").value = "";
		document.getElementById("hmDireccionRenting").value = "";
		document.getElementById("diaInicioRenting").value = "";
		document.getElementById("mesInicioRenting").value = "";
		document.getElementById("anioInicioRenting").value = "";
		document.getElementById("horaInicioRenting").value = "";
		document.getElementById("diaFinRenting").value = "";
		document.getElementById("mesFinRenting").value = "";
		document.getElementById("anioFinRenting").value = "";
		document.getElementById("horaFinRenting").value = "";
		if(document.getElementById("asignarPrincipalArrendatario")) {
			document.getElementById("asignarPrincipalArrendatario").value = "SI";
		}
	}
}

function limpiarFormularioRepresentanteRenting(){
	var idNifRepresentanteArrendatario = document.getElementById("idNifRepresentanteArrendatario").value;
	if (idNifRepresentanteArrendatario!=null && idNifRepresentanteArrendatario!="") {
		document.getElementById("idApellido1RepresentanteArrendatario").value = "";
		document.getElementById("idApellido2RepresentanteArrendatario").value = "";
		document.getElementById("idNombreRepresentanteArrendatario").value = "";
	}
}

function limpiarFormTitular(){
	var nifTitular = document.getElementById("nifTitular").value;
	if (nifTitular!=null && nifTitular!="") {
		document.getElementById("titularDiaCadNif").value = "";
		document.getElementById("titularMesCadNif").value = "";
		document.getElementById("titularAnioCadNif").value = "";
		document.getElementById("titularOtroDocumentoIdentidad").checked = false;
		document.getElementById("titularIndefinido").checked = false;
		document.getElementById("titularTipoDocumentoAlternativo").selectedIndex = 0;
		document.getElementById("titularDiaCadAlternativo").value = "";
		document.getElementById("titularMesCadAlternativo").value = "";
		document.getElementById("titularAnioCadAlternativo").value = "";
		document.getElementById("tipoPersonaTitular").value = "-1";
		document.getElementById("apellido1RazonSocialTitular").value = "";
		document.getElementById("apellido2Titular").value = "";
		document.getElementById("nombreTitular").value = "";
		document.getElementById("sexoTitular").value = "-1";
		document.getElementById("anagramaTitular").value = "";
		document.getElementById("diaNacTitular").value = "";
		document.getElementById("mesNacTitular").value = "";
		document.getElementById("anioNacTitular").value = "";
		document.getElementById("estadoCivilTitular").value = "-1";
		document.getElementById("telefonoTitular").value = "";
		document.getElementById("correoElectronicoTitular").value = "";
		document.getElementById("idDireccionTitular").value = "";
		document.getElementById("idProvinciaTitular").value = "-1";
		document.getElementById("idMunicipioTitular").length = 0;
		document.getElementById("idMunicipioTitular").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoTitular").value = "";
		document.getElementById("idPuebloTitular").length = 0;
		document.getElementById("idPuebloTitular").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoTitular").value = "";
		document.getElementById("tipoViaDireccionTitular").length = 0;
		document.getElementById("tipoViaDireccionTitular").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaDireccionTitular").value = "";
		document.getElementById("nombreViaDireccionTitular").value = "";
		document.getElementById("numeroDireccionTitular").value = "";
		document.getElementById("codPostalTitular").value = "";
		document.getElementById("letraDireccionTitular").value = "";
		document.getElementById("escaleraDireccionTitular").value = "";
		document.getElementById("pisoDireccionTitular").value = "";
		document.getElementById("puertaDireccionTitular").value = "";
		document.getElementById("bloqueDireccionTitular").value = "";
		document.getElementById("kmDireccionTitular").value = "";
		document.getElementById("hmDireccionTitular").value = "";
		document.getElementById("idAutonomo").value = "";
		document.getElementById("epigrafe").value = "";
		document.getElementById("iae").value = "";
		if(document.getElementById("asignarPrincipalTitular")) {
			document.getElementById("asignarPrincipalTitular").value = "SI";
		}
	}
}

function limpiarFormularioRepresentanteTitular() {
	var nifRepresentante = document.getElementById("nifRepresentante").value;
	if (nifRepresentante!=null && nifRepresentante!="") {
		document.getElementById("representanteTitularDiaCadNif").value = "";
		document.getElementById("representanteTitularMesCadNif").value = "";
		document.getElementById("representanteTitularAnioCadNif").value = "";
		document.getElementById("representanteTitularOtroDocumentoIdentidad").checked = false;
		document.getElementById("representanteIndefinido").checked = false;
		document.getElementById("representanteTitularTipoDocumentoAlternativo").selectedIndex = 0;
		document.getElementById("representanteTitularDiaCadAlternativo").value = "";
		document.getElementById("representanteTitularMesCadAlternativo").value = "";
		document.getElementById("representanteTitularAnioCadAlternativo").value = "";
		document.getElementById("tipoPersonaRepresentante").value = "-1";
		document.getElementById("apellido1Representante").value = "";
		document.getElementById("apellido2Representante").value = "";
		document.getElementById("nombreRepresentante").value = "";
		document.getElementById("sexoRepresentante").value = "-1";
		document.getElementById("diaNacRepresentante").value = "";
		document.getElementById("mesNacRepresentante").value = "";
		document.getElementById("anioNacRepresentante").value = "";
		document.getElementById("estadoCivilRepresentante").value = "-1";
		document.getElementById("idDireccionRepresentanteTitular").value = "";
		document.getElementById("idProvinciaRepresentante").value = "-1";
		document.getElementById("idMunicipioRepresentante").length = 0;
		document.getElementById("idMunicipioRepresentante").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoRepresentante").value = "";
		document.getElementById("idPuebloRepresentante").length = 0;
		document.getElementById("idPuebloRepresentante").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoRepresentante").value = "";
		document.getElementById("tipoViaRepresentanteTitular").length = 0;
		document.getElementById("tipoViaRepresentanteTitular").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaRepresentanteTitular").value = "";
		document.getElementById("nombreViaRepresentanteTitular").value = "";
		document.getElementById("numeroDireccionRepresentanteTitular").value = "";
		document.getElementById("codPostalRepresentante").value = "";
		document.getElementById("letraDireccionRepresentanteTitular").value = "";
		document.getElementById("escaleraDireccionRepresentanteTitular").value = "";
		document.getElementById("pisoDireccionRepresentanteTitular").value = "";
		document.getElementById("puertaDireccionRepresentanteTitular").value = "";
		document.getElementById("bloqueDireccionRepresentanteTitular").value = "";
		document.getElementById("kmDireccionRepresentanteTitular").value = "";
		document.getElementById("hmDireccionRepresentanteTitular").value = "";
		if(document.getElementById("diaInicioTutela")) {
			document.getElementById("diaInicioTutela").value = "";
		}
		if(document.getElementById("mesInicioTutela")) {
			document.getElementById("mesInicioTutela").value = "";
		}
		if(document.getElementById("anioInicioTutela")) {
			document.getElementById("anioInicioTutela").value = "";
		}
		document.getElementById("conceptoRepresentanteTitular").value = "-1";
		document.getElementById("motivoRepresentanteTitular").value = "-1";	
		if(document.getElementById("asignarPrincipalRepresentanteTitular")) {
			document.getElementById("asignarPrincipalRepresentanteTitular").value = "SI";
		}
	}
}

function mostrarRenting(){
	if(document.getElementById('rentingId').checked){
		document.getElementById('Renting').style.display = "BLOCK";
	} else {
		document.getElementById('Renting').style.display = "NONE";
		limpiarFormularioArrendatarioTransmision();
		document.getElementById("idNifArrendatario").value="";
		document.getElementById("idNifRepresentanteArrendatario").value = "";
	}
}

function mostrarConductorHabitual(){
	if(document.getElementById("marcadoConductorHabitual").checked == true){
		document.getElementById("ConductorHabitual").style.display = "block";
	}else{
		document.getElementById("ConductorHabitual").style.display = "none";
		limpiarFormularioConductorHabitualMatriculacionMatw();
	}
}

function consultaDatosITV() {
	if (document.getElementById("idCodigoItv").value != ""
			&& document.getElementById("idCodigoItv").value != "SINCODIG") {
		if (document.getElementById("idCodigoItv").value.length < 8
				|| document.getElementById("idCodigoItv").value.length > 9) {
			alert("El codigo ITV debe tener 8 o 9 caracteres");
		} else {
			document.formData.action = "consultaDatosITVAltaTramiteTraficoMatriculacion.action#Vehiculo";
			document.formData.submit();
			return true;
		}
	} else {
		document.getElementById("idTipoVehiculo").value = "-1";
		document.getElementById("modelo").value = "";
		document.getElementById("idMarca").value = "-1";
		document.getElementById("idCarburanteVehiculo").value = "-1";
		document.getElementById("idPlazas").value = "";
		document.getElementById("idPotenciaFiscal").value = "";
		document.getElementById("idNcilindros").value = "";
		document.getElementById("idCo2").value = "";
		document.getElementById("idtara").value = "";
		document.getElementById("idMma").value = "";
		document.getElementById("idPotenciaNeta").value = "";
		document.getElementById("idTipo").value = "";
		document.getElementById("idPotenciaPeso").value = "";
	}
}

function guardarFacturacionAltaTramiteTraficoMatriculacion() {
	if (document.getElementById("nifFacturacion").value != "" && document.getElementById("apellido1Facturacion").value == "") {
		alert("Ha a\u00f1adido NIF del Titular de la Facturaci\u00f3n, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}

	document.formData.action = "guardarTitularFacturacionAltaTramiteTraficoMatriculacion.action#Facturacion";
	document.formData.submit();
}

function presentarAEATMatriculacion(){
	if (botonAeatPulsado){
		alert(MSG_PETICION_REALIZADA);
		return false;
	}else{
		loadingPresentarAEAT();
		document.formData.action="presentarAEATAltaTramiteTraficoMatriculacion.action#576";
		document.formData.submit();
	}
}

function descargarPresentacionAEAT(){
	$('#formData').attr("action","recuperar576AltaTramiteTraficoMatriculacion.action#576");
	$('#formData').submit();
}

function descargaRespuesta576Matw() {
	var actionPoint = "exportarRespuesta576AltaTramiteTraficoMatriculacion.action";
	var mostrarLink = document.getElementById("respuesta576Link");
	mostrarLink.style.display = "none";
	document.forms[0].action = actionPoint;
	document.forms[0].submit();
}

function validarMatw() {
	deshabilitarBotonesMatriculacion();
	if (!campoVacio("idBastidor")){
		if($("#idCategoriaElectrica").val() != null && $("#idCategoriaElectrica").val() == "PHEV"){
			if($("#idAutonomiaElectrica").val() == null || $("#idAutonomiaElectrica").val() == ""){
				if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
					return false;
				}
			} else if($("#idAutonomiaElectrica").val() == "0"){
				if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica y esta no puede ser '0' para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
					return false;
				}
			}
		}
	}
	if (confirm(document.getElementById("textoLegal").value)) {
		if (validarMatriculacionPrevia() && validarMaximos()) {
			var pestania = obtenerPestaniaSeleccionada();
			document.formData.action = "validarAltaTramiteTraficoMatriculacion.action#" + pestania;
			document.formData.submit();
			return true;
		} else
			habilitarBotonesMatriculacion();
			return false;
	} else {
		habilitarBotonesMatriculacion();
		return false;
	}
}

function validarMatriculacionPrevia() {
	if (!campoVacio("idCem")
			&& (!validaExpresionAlfanumerica(document.getElementById("idCem").value))) {
		alert("El C\u00f3digo Electr\u00f3nico de Matriculaci\u00f3n introducido no es v\u00e1lido.");
		return false;
	}

	if (!campoVacio("idCem")) {
		var cemValue = document.getElementById("idCem").value;
		if (cemValue.length != 8 || cemValue == "00000000") {
			alert("El C\u00f3digo Electr\u00f3nico de Matriculaci\u00f3n introducido no es v\u00e1lido.");
			return false;
		}
	}

	if (!campoVacio("idBastidor")){
		if (document.getElementById("idEcoInnovacion").selectedIndex == 1){
			if (campoVacio("idCodigoEco")){
				alert ("El campo codigo ECO es obligatorio.");
				return false;
			}
		}
	}
	return true;
}

function consultarTarjetaEitvMatriculacion(pestania) {
	if (document.getElementById('idBastidor').value == null || document.getElementById('idBastidor').value == "") {
		alert("Por favor, introduzca el bastidor");
		return false;
	}

	if (document.getElementById('idBastidor').value.length != 17) {
		alert("El bastidor debe tener una longitud de 17 caracteres.");
		return false;
	}

	if ((!document.getElementById('idNive').value == null || !document.getElementById('idNive').value == "") 
		&& document.getElementById('idNive').value.length != 32) {
		alert("El NIVE debe tener una longitud de 32 caracteres.");
		return false;
	}

	deshabilitarBotonesEITV();
	document.formData.action = "getDataEitvAltaTramiteTraficoMatriculacion#" + pestania;
	document.formData.submit();
	return true;
}

function deshabilitarBotonesEITV() {
	if (document.getElementById("bConsultaTarjetaEitv") != null) {
		document.getElementById("bConsultaTarjetaEitv").disabled = "true";
		document.getElementById("bConsultaTarjetaEitv").style.color = "#6E6E6E";
		document.getElementById("bConsultaTarjetaEitv").value = "Procesando...";
	}
	if (document.getElementById("idBotonGuardar") != null) {
		document.getElementById("idBotonGuardar").disabled = "true";
		document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	}
	document.getElementById("loadingImage").style.display = "block";
}

function clonarExpedienteMatriculacion(){
	habilitarBotonesMatriculacion();
	document.formData.action = "clonarAltaTramiteTraficoMatriculacion.action";
	document.formData.submit();
	return true;
}

function matricularTelematicamenteMatriculacion() {
	deshabilitarBotonesMatriculacion();
	var textoAlert = "Se le va a descontar 1 credito";
	textoAlert += "\n\n\nEl tr\u00e1mite ser\u00e1 enviado a DGT con los datos con los que fue validado.\n\n\nEsta usted procediendo a realizar tr\u00E1mites de matriculaci\u00F3n de vehiculo/s, para la realizacion de tal/es tr\u00E1mites es obligatorio que el Gestor Administrativo verifique de forma correcta y tenga en su poder la ficha t\u00E9cnica del veh\u00EDculo, as\u00ED como pagada y/o sellada la tasa de tr\u00E1fico, el modelo 576 del impuesto sobre veh\u00EDculos de transporte y el impuesto de veh\u00EDculos de tracci\u00F3n mec\u00E1nica de la localidad correspondiente. La realizaci\u00F3n de la operaci\u00F3n de matriculaci\u00F3n sin cumplir todos los requisitos de la encomienda de gesti\u00F3n suponen una infracci\u00F3n muy grave que puede ser sancionada con la inhabilitaci\u00F3n profesional del gestor administrativo, ademas de no estar cubierta tal contingencia por la p\u00F3liza de seguros de responsabilidad civil, en cuyo caso el infractor sera responsable de las contingencias econ\u00F3micas que surjan. Todo ello sin perjuicio de las responsabilidades administrativas y jur\u00EDdico penales en que puedan incurrir de forma directa los part\u00EDcipes de dicho tr\u00E1mite."
		+ "\n\n"
		+ "En el caso de cumplir tales requisitos pulse continuar y ultime el proceso de matriculaci\u00F3n, en el caso de no cumplir los requisitos alguno de los veh\u00EDculos que en este proceso se matriculan, se debera cancelar el proceso telem\u00E1tico de matriculaci\u00F3n referido a dichos veh\u00EDculos hasta el correcto cumplimiento de los mismos.";

	if (!confirm(textoAlert)) {
		habilitarBotonesMatriculacion();
		return false;
	}
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "matricularTelematicamenteAltaTramiteTraficoMatriculacion.action#" + pestania;

	document.formData.submit();
	return true;
}

function deshabilitarBotonesMatriculacion(){
	if (!$('#idBotonGuardar').is(':hidden')){
		$("#idBotonGuardar").css("color","#BDBDBD");
		$("#idBotonGuardar").prop("disabled",true);
	}
	if (!$('#idBotonLiberarEEFF').is(':hidden')){
		$("#idBotonLiberarEEFF").css("color","#BDBDBD");
		$("#idBotonLiberarEEFF").prop("disabled",true);
	}
	if (!$('#idBotonMatriculacionTelematica').is(':hidden')){
		$("#idBotonMatriculacionTelematica").css("color","#BDBDBD");
		$("#idBotonMatriculacionTelematica").prop("disabled",true);
	}
	if (!$('#idBotonValidarMatricular').is(':hidden')){
		$("#idBotonValidarMatricular").css("color","#BDBDBD");
		$("#idBotonValidarMatricular").prop("disabled",true);
	}
	if (!$('#idBotonClonar').is(':hidden')){
		$("#idBotonClonar").css("color","#BDBDBD");
		$("#idBotonClonar").prop("disabled",true);
	}
	if (!$('#idBotonImprimir').is(':hidden')){
		$("#idBotonImprimir").css("color","#BDBDBD");
		$("#idBotonImprimir").prop("disabled",true);
	}
	if (!$('#bConsultaTarjetaEitv').is(':hidden')){
		$("#bConsultaTarjetaEitv").css("color","#BDBDBD");
		$("#bConsultaTarjetaEitv").prop("disabled",true);
	}
	if (!$('#botonIdentificacion').is(':hidden')){
		$("#botonIdentificacion").css("color","#BDBDBD");
		$("#botonIdentificacion").prop("disabled",true);
	}
	$("#bloqueBotones").show();
}

function habilitarBotonesMatriculacion() {
	if (!$('#idBotonGuardar').is(':hidden')){
		$("#idBotonGuardar").css("color","#000000");
		$("#idBotonGuardar").prop("disabled",false);
	}
	if (!$('#idBotonLiberarEEFF').is(':hidden')){
		$("#idBotonLiberarEEFF").css("color","#000000");
		$("#idBotonLiberarEEFF").prop("disabled",false);
	}
	if (!$('#idBotonMatriculacionTelematica').is(':hidden')){
		$("#idBotonMatriculacionTelematica").css("color","#000000");
		$("#idBotonMatriculacionTelematica").prop("disabled",false);
	}
	if (!$('#idBotonValidarMatricular').is(':hidden')){
		$("#idBotonValidarMatricular").css("color","#000000");
		$("#idBotonValidarMatricular").prop("disabled",false);
	}
	if (!$('#idBotonClonar').is(':hidden')){
		$("#idBotonClonar").css("color","#000000");
		$("#idBotonClonar").prop("disabled",false);
	}
	if (!$('#idBotonImprimir').is(':hidden')){
		$("#idBotonImprimir").css("color","#000000");
		$("#idBotonImprimir").prop("disabled",false);
	}
	if (!$('#bConsultaTarjetaEitv').is(':hidden')){
		$("#bConsultaTarjetaEitv").css("color","#000000");
		$("#bConsultaTarjetaEitv").prop("disabled",false);
	}
	if (!$('#botonIdentificacion').is(':hidden')){
		$("#botonIdentificacion").css("color","#000000");
		$("#botonIdentificacion").prop("disabled",false);
	}
	$("#bloqueBotones").hide();
}

function limpiarFormularioFacturacion(){
	var nifFacturacion = document.getElementById("nifFacturacion").value;
	if (nifFacturacion != null && nifFacturacion != "") {
		document.getElementById("tipoPersonaFac").value = "-1";
		document.getElementById("apellido1Facturacion").value = "";
		document.getElementById("apellido2Fac").value = "";
		document.getElementById("nombreFact").value = "";
		document.getElementById("sexoFac").value = "-1";
		document.getElementById("idDireccionFact").value = "";
		document.getElementById("idProvinciaFacturacion").value = "-1";
		document.getElementById("idMunicipioFacturacion").length = 0;
		document.getElementById("idMunicipioFacturacion").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoFacturacion").value = "";
		
		document.getElementById("idPuebloFacturacion").length = 0;
		document.getElementById("idPuebloFacturacion").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoFacturacion").value = "";
		document.getElementById("cpFacturacion").value = "";
		document.getElementById("tipoViaFacturacion").length = 0;
		document.getElementById("tipoViaFacturacion").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaFacturacion").value = "";
		document.getElementById("nombreViaFacturacion").value = "";
		document.getElementById("numeroDireccionFacturacion").value = "";
		document.getElementById("letraDireccionFacturacion").value = "";
		document.getElementById("escaleraDireccionFacturacion").value = "";
		document.getElementById("pisoDireccionFacturacion").value = "";
		document.getElementById("puertaDireccionFacturacion").value = "";
		document.getElementById("bloqueDireccionFacturacion").value = "";
		document.getElementById("kmDireccionFacturacion").value = "";
		document.getElementById("hmDireccionFacturacion").value = "";
	}
}

function cambioRentingMatriculacion(){
	if(document.getElementById('rentingId').checked){
		document.getElementById('Renting').style.display = "BLOCK";
	} else {
		document.getElementById('Renting').style.display = "NONE";
		limpiarFormularioRenting();
		document.getElementById("idNifArrendatario").value="";
		document.getElementById("idNifRepresentanteArrendatario").value = "";
	}
}

function descargarDocBaseMatriculacion(pestania){
	document.formData.action = "descargarDocBaseAltaTramiteTraficoMatriculacion.action#" + pestania;
	document.formData.submit();
	return true;
}

function obtenerDetalleConsultaEEFF(numExpedienteEEFF){
	if(numExpedienteEEFF == ""){
		return alert("No se puede obtener el detalle de la consulta porque no se le indica el numero de expediente de EEFF.")
	}
	$("#divEmergenteMatriculacionConsultaEEFF").show();
	var $dest = $("#divEmergenteMatriculacionConsultaEEFF");
	$.post("cargarPopUpResultadoWSAltaConEEFF.action?consultaEEFF.numExpediente="+numExpedienteEEFF, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 720,
				buttons: {
					Cerrar: function() {
						$("#divEmergenteMatriculacionConsultaEEFF").show();
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function checkIvtm(){
	if(document.getElementById("idMunicipioIVTM").value == "MADRID"){
		visibilidadMostrar="block";
		visibilidadOcultar="none";
		read=true;
	}else{
		visibilidadMostrar="none";
		visibilidadOcultar="block";
		read=false;
	}
	bloquearCampos(read);
	cambiarVisibilidadMostrar(visibilidadMostrar);
	cambiarVisibilidadOcultar(visibilidadOcultar);
	deshabilitarBotonIvtm(visibilidadMostrar);
	deshabilitarBotonPagoIvtm(visibilidadMostrar);
	if(document.getElementById("idMunicipioIVTM").value == "MADRID"){
		cambiarColorFondo("#EEEEEE", "");
	} else {
		cambiarColorFondo("", "#EEEEEE");
	}
	activarMedioAmbienteIVTM();
}

function bloquearCampos (read){
	document.getElementById("importeIvtm").readOnly = read;
	document.getElementById("idNrcIvtm").readOnly = read;
}

function cargarCodigosTasaMatr(select_tipoTasa,id_select_codigoTasa){
	obtenerCodigosTasaPorTipoTasaMatr(select_tipoTasa,document.getElementById(id_select_codigoTasa));
}

function obtenerCodigosTasaPorTipoTasaMatr(selectTipoTasa,selectCodigosTasa,id_CodigoTasaSeleccionado){
	if(selectTipoTasa.selectedIndex==0){
		selectCodigosTasa.length = 0;
		selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
		return;
	}
	var selectedOption = selectTipoTasa.options[selectTipoTasa.selectedIndex].value;
	var idContrato = document.getElementById("idContratoTramite").value;
	var url = "recuperarCodigosTasaLibresPorTipoTasaTraficoAjax.action?tipoTasaSeleccionado="+selectedOption+"&idContrato="+idContrato;		
	var req_generico_codigosTasa = NuevoAjax();
	req_generico_codigosTasa.onreadystatechange = function () {
		rellenarListaCodigosTasaMatr(req_generico_codigosTasa,selectCodigosTasa);
	}
	req_generico_codigosTasa.open("POST", url, true);
	req_generico_codigosTasa.send(null);
}

function rellenarListaCodigosTasaMatr(req_generico_codigosTasa,selectCodigosTasa){

	selectCodigosTasa.options.length = 0;

	if (req_generico_codigosTasa.readyState == 4) {
		if (req_generico_codigosTasa.status == 200) {
			textToSplit = req_generico_codigosTasa.responseText;

			returnElements=textToSplit.split("||");

			var existe = false;
			selectCodigosTasa.options[0] = new Option("Seleccione Código de Tasa", "-1");
			for (var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				if(!isNaN(value[0])){
					 selectCodigosTasa.options[i+1] = new Option(value[0], value[1]);
				}
			}
		}
	}
}

function incluirFichero(){
	$("#formData").attr("action", "subirJustificanteIVTMAltaTramiteTraficoMatriculacion.action#PagosEImpuestos").trigger("submit");
}

function descargarJustificanteIVTM(){
	$("#formData").attr("action", "verJustificanteIVTMAltaTramiteTraficoMatriculacion.action#PagosEImpuestos").trigger("submit");
}

function cambiarVisibilidadMostrar (visibilidad){
	if (document.getElementById("spanLabelIdImporteAnual") != null) {
		cambiarVisibilidad("spanLabelIdImporteAnual",visibilidad);
	}

	if (document.getElementById("spanIdImporteAnual") != null) {
		cambiarVisibilidad("spanIdImporteAnual",visibilidad);
	}

	cambiarVisibilidad("spanLabelBonMedAmbIvtm",visibilidad);
	cambiarVisibilidad("spanBonMedAmbIvtm",visibilidad);
	cambiarVisibilidad("spanLabelPorcentajeBonMedAmbIvtm",visibilidad);
	cambiarVisibilidad("spanPorcentajeBonMedAmbIvtm",visibilidad);
	cambiarVisibilidad("spanLabelIdCCCIvtm",visibilidad);
	cambiarVisibilidad("spanIdCCCIvtm",visibilidad);
	cambiarVisibilidad("spanIdBotonCargarIban",visibilidad);
	cambiarVisibilidad("spanLabelIdCodGestIvtm",visibilidad);
	cambiarVisibilidad("spanIdCodGestIvtm",visibilidad);
	cambiarVisibilidad("spanLabelEmisorIvtm",visibilidad);
	cambiarVisibilidad("spanEmisorIvtm",visibilidad);
	cambiarVisibilidad("spanIdDigControlIvtm",visibilidad);
	cambiarVisibilidad("spanLabelIdDigControlIvtm",visibilidad);
	cambiarVisibilidad("spanLabelCodigoRespuestaPago",visibilidad);
	cambiarVisibilidad("spanCodigoRespuestaPago",visibilidad);
}

function cambiarVisibilidadOcultar(visibilidad){
	cambiarVisibilidad("spanLabelIdExentoIvtm",visibilidad);
	cambiarVisibilidad("spanIdExentoIvtm",visibilidad);
}

function cambiarVisibilidad(campo, visibilidad){
	document.getElementById(campo).style.display=visibilidad;
}

function deshabilitarBotonIvtm(visibilidad){
	document.getElementById("spanBotonIvtm").style.display = visibilidad;
}

function deshabilitarBotonPagoIvtm(visibilidad){
	document.getElementById("spanBotonPagoIvtm").style.display = visibilidad;
}