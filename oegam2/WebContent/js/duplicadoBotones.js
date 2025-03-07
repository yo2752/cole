
function buscarVehiculoDuplicado(matriculaVehiculo) {
	
	$("#matriculaBusqueda").val( $("#"+ matriculaVehiculo).val());
	document.formData.action = "buscarVehiculoAltaTramiteTraficoDuplicado.action#Vehiculo";
	document.formData.submit();
}


function buscarIntervinienteDuplicado(tipoInterviniente, pestania, idNif) {
	$("#nifBusqueda").val( $("#"+ idNif).val());
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "consultarPersonaAltaTramiteTraficoDuplicado.action#" + pestania;
	document.formData.submit();
}
function deshabilitarBotonesTransmitente(){
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#BDBDBD");
		$("#idLimpiarTransmitente").prop("disabled",true);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#BDBDBD");
		$("#idGuardarModelo").prop("disabled",true);
	}
}

function guardarAltaTramiteTraficoDuplicado() {
	deshabilitarBotonesDuplicados();
	if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
		habilitarBotonesDuplicados();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	if (document.getElementById("dniRepresentante").value != "" && document.getElementById("apellido1RepresentanteTitular").value == "") {
		habilitarBotonesDuplicados();
		alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	if (document.getElementById("dniCotitular").value != "" && document.getElementById("primerApellidoCotitular").value == "") {
		habilitarBotonesDuplicados();
		alert("Ha a\u00f1adido NIF del Cotitular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	if(document.getElementById("motivoDuplicado").value == "-1"){
		habilitarBotonesDuplicados();
		alert("El motivo del duplicado es obligatorio");
		return false;
	}
	var pestania = obtenerPestaniaSeleccionada();
	document.getElementById('checkBoxPermisoCirculacion').disabled = false;
	
	document.formData.action = "guardarAltaTramiteTraficoDuplicado.action#" + pestania;
	document.formData.submit();
}

function validarAltaTramiteTraficoDuplicado() {
	let mostrarAdvertencia = false;
	let advertenciaAceptada = false;
	if(document.getElementById("estado_duplicado").textContent.toLowerCase().includes('no tramitable')){
		mostrarAdvertencia = true;
	}
	
	if (mostrarAdvertencia){
		advertenciaAceptada = confirm("El trámite se va a tramitar vía Excel. ¿Continuar? ");
	}

	if (advertenciaAceptada || !mostrarAdvertencia) {
		deshabilitarBotonesDuplicados();
		if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
			habilitarBotonesDuplicados();
			alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
			return false;
		}
		
		if (document.getElementById("dniRepresentante").value != "" && document.getElementById("apellido1RepresentanteTitular").value == "") {
			habilitarBotonesDuplicados();
			alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
			return false;
		}
		
		if (document.getElementById("dniCotitular").value != "" && document.getElementById("primerApellidoCotitular").value == "") {
			habilitarBotonesDuplicados();
			alert("Ha a\u00f1adido NIF del Cotitular, Primer Apellido / Raz\u00f3n Social obligatorio");
			return false;
		}
		
		if(document.getElementById("motivoDuplicado").value == "-1"){
			habilitarBotonesDuplicados();
			alert("El motivo de la baja es obligatorio");
			return false;
		}
		
		var pestania = obtenerPestaniaSeleccionada();
		document.getElementById('checkBoxPermisoCirculacion').disabled = false;
		document.formData.action = "validarAltaTramiteTraficoDuplicado.action#" + pestania;
		document.formData.submit();
	}
}

function guardarFacturacionAltaTramiteTraficoDuplicado() {
	if (document.getElementById("nifFacturacion").value != "" && document.getElementById("apellido1Facturacion").value == "") {
		alert("Ha a\u00f1adido NIF del Titular de la Facturaci\u00f3n, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	document.formData.action = "guardarTitularFacturacionAltaTramiteTraficoDuplicado.action#Facturacion";
	document.formData.submit();
}

function pendientesEnvioAExcelAltaDuplicado() {
	var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: 1 cr\u00e9dito";
	if (confirm(textoAlert)) {
		var pestania = obtenerPestaniaSeleccionada();
		document.getElementById("jefaturaProvincialPagoPresentacion").disabled = false;
		deshabilitarBotonesDuplicados();
		document.formData.action = "pendientesEnvioExcelAltaTramiteTraficoDuplicado.action?idHiddenNumeroExpediente="
			+ document.getElementById("idHiddenNumeroExpediente").value
			+ "&excelDesdeAlta=true#" + pestania;
		document.formData.submit();
	}
}

function limpiarVehiculoAltaTramiteTraficoDuplicado(pestania) {
	document.getElementById("matriculaVehiculo").readOnly = false;
	
	document.getElementById("idVehiculoText").value = "";
	document.getElementById("matriculaVehiculo").value = "";
	document.getElementById("diaMatriculacionVehiculo").value = "";
	document.getElementById("mesMatriculacionVehiculo").value = "";
	document.getElementById("anioMatriculacionVehiculo").value = "";
	document.getElementById("diaItv").value = "";
	document.getElementById("mesItv").value = "";
	document.getElementById("anioItv").value = "";
}

function limpiarFormularioTitular(){
	var dniTitular = document.getElementById("dniTitular").value;
	if (dniTitular != null && dniTitular != "") {
		document.getElementById("tipoPersonaTitular").value = "-1";
		document.getElementById("primerApellidoTitular").value = "";
		document.getElementById("segundoApellidoTitular").value = "";
		document.getElementById("nombreTitular").value = "";
		document.getElementById("sexoTitular").value = "-1";
		document.getElementById("idDireccionTitular").value = "";
		document.getElementById("idProvinciaTitular").value = "-1";
		document.getElementById("idMunicipioTitular").length = 0;
		document.getElementById("idMunicipioTitular").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoTitular").value = "";
		document.getElementById("idPuebloTitular").length = 0;
		document.getElementById("idPuebloTitular").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoTitular").value = "";
		document.getElementById("cpTitular").value = "";
		document.getElementById("tipoViaTitular").length = 0;
		document.getElementById("tipoViaTitular").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaTitular").value = "";
		document.getElementById("nombreViaTitular").value = "";
		document.getElementById("numeroDireccionTitular").value = "";
		document.getElementById("letraDireccionTitular").value = "";
		document.getElementById("escaleraDireccionTitular").value = "";
		document.getElementById("pisoDireccionTitular").value = "";
		document.getElementById("puertaDireccionTitular").value = "";
		document.getElementById("bloqueDireccionTitular").value = "";
		document.getElementById("kmDireccionTitular").value = "";
		document.getElementById("hmDireccionTitular").value = "";
	}
}

function limpiarFormularioRepresentanteTitular(){
	if(document.getElementById("dniRepresentante")) {
		var dniRepresentante = document.getElementById("dniRepresentante").value;
		if (dniRepresentante != null && dniRepresentante != "") {
			document.getElementById("tipoPersonaRepresentante").value = "-1";
			document.getElementById("apellido1RepresentanteTitular").value = "";
			document.getElementById("segundoApellidoRepresentante").value = "";
			document.getElementById("idNombreRepresentanteTitular").value = "";
			document.getElementById("sexoRepresentante").value = "-1";
			document.getElementById("diaInicioRepresentanteTitular").value = "";
			document.getElementById("mesInicioRepresentanteTitular").value = "";
			document.getElementById("anioInicioRepresentanteTitular").value = "";
			document.getElementById("diaFinRepresentanteTitular").value = "";
			document.getElementById("mesFinRepresentanteTitular").value = "";
			document.getElementById("anioFinRepresentanteTitular").value = "";
			document.getElementById("conceptoRepresentanteTitular").value = "-1";
			document.getElementById("motivoRepresentanteTitular").value = "-1";
			document.getElementById("datosAcrediteRepresentanteTitular").value = "";
		}
	}
}

function limpiarFormularioCotitular(){
	var dniCotitular = document.getElementById("dniCotitular").value;
	if (dniCotitular != null && dniCotitular != "") {
		document.getElementById("tipoPersonaCotitular").value = "-1";
		document.getElementById("primerApellidoCotitular").value = "";
		document.getElementById("segundoApellidoCotitular").value = "";
		document.getElementById("nombreCotitular").value = "";
		document.getElementById("sexoCotitular").value = "-1";
		document.getElementById("idDireccionCotitular").value = "";
		document.getElementById("idProvinciaCotitular").value = "-1";
		document.getElementById("idMunicipioCotitular").length = 0;
		document.getElementById("idMunicipioCotitular").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoCotitular").value = "";
		document.getElementById("idPuebloCotitular").length = 0;
		document.getElementById("idPuebloCotitular").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoCotitular").value = "";
		document.getElementById("cpCotitular").value = "";
		document.getElementById("tipoViaCotitular").length = 0;
		document.getElementById("tipoViaCotitular").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaCotitular").value = "";
		document.getElementById("nombreViaCotitular").value = "";
		document.getElementById("numeroDireccionCotitular").value = "";
		document.getElementById("letraDireccionCotitular").value = "";
		document.getElementById("escaleraDireccionCotitular").value = "";
		document.getElementById("pisoDireccionCotitular").value = "";
		document.getElementById("puertaDireccionCotitular").value = "";
		document.getElementById("bloqueDireccionCotitular").value = "";
		document.getElementById("kmDireccionCotitular").value = "";
		document.getElementById("hmDireccionCotitular").value = "";
	}
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

function deshabilitarBotonesDuplicados(){
	if (!$('#bValidarDuplicado').is(':hidden')){
		$("#bValidarDuplicado").css("color","#BDBDBD");
		$("#bValidarDuplicado").prop("disabled",true);
	}
	if (!$('#bGuardarDuplicado').is(':hidden')){
		$("#bGuardarDuplicado").css("color","#BDBDBD");
		$("#bGuardarDuplicado").prop("disabled",true);
	}
	if (!$('#bComprobarBaja').is(':hidden')){
		$("#bComprobarBaja").css("color","#BDBDBD");
		$("#bComprobarBaja").prop("disabled",true);
	}
	if (!$('#idBotonImprimirDuplicado').is(':hidden')){
		$("#idBotonImprimirDuplicado").css("color","#BDBDBD");
		$("#idBotonImprimirDuplicado").prop("disabled",true);
	}
	if (!$('#bPdteExcelDuplicado').is(':hidden')){
		$("#bPdteExcelDuplicado").css("color","#BDBDBD");
		$("#bPdteExcelDuplicado").prop("disabled",true);
	}
	if (!$('#bTramitarDuplicado').is(':hidden')){
		$("#bTramitarDuplicado").css("color","#BDBDBD");
		$("#bTramitarDuplicado").prop("disabled",true);
	}
	
	$("#bloqueBotones").show();
}

function habilitarBotonesDuplicados(){
	if (!$('#bValidarDuplicado').is(':hidden')){
		$("#bValidarDuplicado").css("color","#000000");
		$("#bValidarDuplicado").prop("disabled",false);
	}
	if (!$('#bGuardarDuplicado').is(':hidden')){
		$("#bGuardarDuplicado").css("color","#000000");
		$("#bGuardarDuplicado").prop("disabled",false);
	}
	if (!$('#bComprobarBaja').is(':hidden')){
		$("#bComprobarBaja").css("color","#000000");
		$("#bComprobarBaja").prop("disabled",false);
	}
	if (!$('#idBotonImprimirDuplicado').is(':hidden')){
		$("#idBotonImprimirDuplicado").css("color","#000000");
		$("#idBotonImprimirDuplicado").prop("disabled",false);
	}
	if (!$('#bPdteExcelDuplicado').is(':hidden')){
		$("#bPdteExcelDuplicado").css("color","#000000");
		$("#bPdteExcelDuplicado").prop("disabled",false);
	}
	$("#bloqueBotones").hide();
}

function cambioConceptoRepreTitular(){
	if(document.getElementById("conceptoRepresentanteTitular").value=="TUTELA O PATRIA POTESTAD"){
		document.getElementById("motivoRepresentanteTitular").disabled=false;
	} else {
		document.getElementById("motivoRepresentanteTitular").disabled=true;
		document.getElementById("motivoRepresentanteTitular").value="-1";
	}
}

function verTodasTasas(idContrato,destino){
	var $dest =  $("#"+destino);
	var contrato = $("#"+idContrato);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=4.4",
			type:'POST',
			success: function(data){
				if(data != ""){
					$dest.find('option').remove().end().append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
					var listTasas = data.split(";");
					$.each(listTasas,function(i,o){
						 $dest.append($('<option>', { 
						        value: listTasas[i],
						        text : listTasas[i]
						    }));
					 });
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar las tasas.');
		    }
		});
	}
}