
function buscarVehiculoBaja(idMatricula) {
	$("#matriculaBusqueda").val( $("#"+ idMatricula).val());
	document.formData.action = "buscarVehiculoAltaTramiteTraficoBaja.action#Vehiculo";
	document.formData.submit();
}


function buscarIntervinienteBaja(tipoInterviniente, pestania, idNif) {
	$("#nifBusqueda").val( $("#"+ idNif).val());
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "consultarPersonaAltaTramiteTraficoBaja.action#" + pestania;
	document.formData.submit();
}


function guardarAltaTramiteTraficoBaja() {
	deshabilitarBotonesBaja();
	if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentante").value != "" && document.getElementById("primerApellidoRepresentante").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniAdquiriente").value != "" && document.getElementById("primerApellidoAdquiriente").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentanteAdquiriente").value != "" && document.getElementById("primerApellidoRepresentanteAd").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if(document.getElementById("motivoBaja").value == "-1"){
		habilitarBotonesBaja();
		alert("El motivo de la baja es obligatorio");
		return false;
	}
	
	var pestania = obtenerPestaniaSeleccionada();
	
	document.formData.action = "guardarAltaTramiteTraficoBaja.action#" + pestania;
	document.formData.submit();
}

function validarAltaTramiteTraficoBaja() {
	deshabilitarBotonesBaja();
	if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentante").value != "" && document.getElementById("primerApellidoRepresentante").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniAdquiriente").value != "" && document.getElementById("primerApellidoAdquiriente").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentanteAdquiriente").value != "" && document.getElementById("primerApellidoRepresentanteAd").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if(document.getElementById("motivoBaja").value == "-1"){
		habilitarBotonesBaja();
		alert("El motivo de la baja es obligatorio");
		return false;
	}
	
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "validarAltaTramiteTraficoBaja.action#" + pestania;
	document.formData.submit();
}


function tramTelematicamenteTramiteTraficoBaja(){
	var textoAlert = "\n\n\nEl Colegio de Gestores verifica que:"
		+ "\n\n"
		+ "  1. El Gestor Administrativo ha comprobado,  en un momento previo al env\u00EDo de esta solicitud, "
		+ "el cumplimiento de los requisitos establecidos en el anexo XIV de RD 2822/1998 de 23 de diciembre "
		+ "por el que se aprueba el Reglamento General de Veh\u00EDculos ( acreditaci\u00F3n de la identidad, titularidad, "
		+ " domicilio,  y liquidaci\u00F3n de las obligaciones fiscales)."
		+ "\n\n"
		+ "  2. Que obra en su poder y conserva mandato suficiente para actuar en nombre del/ de los solicitantes y "
		+ "custodia toda la documentaci\u00F3n que integra este expediente de cambio de titularidad conforme a la normativa "
		+ "archiv\u00EDstica del Ministerio del Interior." + "";
	if (confirm(textoAlert)) {
		var pestania = obtenerPestaniaSeleccionada();
		deshabilitarBotonesBaja();
		document.formData.action = "tramitarTelematicamenteAltaTramiteTraficoBaja.action#" + pestania;
		document.formData.submit();
	}
}

function comprobarAltaTramiteTraficoBaja(){
	if (document.getElementById("dniTitular").value != "" && document.getElementById("primerApellidoTitular").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentante").value != "" && document.getElementById("primerApellidoRepresentante").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniAdquiriente").value != "" && document.getElementById("primerApellidoAdquiriente").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if (document.getElementById("dniRepresentanteAdquiriente").value != "" && document.getElementById("primerApellidoRepresentanteAd").value == "") {
		habilitarBotonesBaja();
		alert("Ha a\u00f1adido NIF del Representante Adquiriente, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	if(document.getElementById("motivoBaja").value == "-1"){
		habilitarBotonesBaja();
		alert("El motivo de la baja es obligatorio");
		return false;
	}
	
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "comprobarAltaTramiteTraficoBaja.action#" + pestania;
	document.formData.submit();
}

function guardarFacturacionAltaTramiteTraficoBaja() {
	if (document.getElementById("nifFacturacion").value != "" && document.getElementById("apellido1Facturacion").value == "") {
		alert("Ha a\u00f1adido NIF del Titular de la Facturaci\u00f3n, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	document.formData.action = "guardarTitularFacturacionAltaTramiteTraficoBaja.action#Facturacion";
	document.formData.submit();
}

function pendientesEnvioAExcelAltaBaja() {
	var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: 1 cr\u00e9dito";
	if (confirm(textoAlert)) {
		var pestania = obtenerPestaniaSeleccionada();
		deshabilitarBotonesBaja();
		document.formData.action = "pendientesEnvioExcelAltaTramiteTraficoBaja.action?idHiddenNumeroExpediente="
			+ document.getElementById("idHiddenNumeroExpediente").value
			+ "&excelDesdeAlta=true#" + pestania;
		document.formData.submit();
	}
}

function limpiarVehiculoAltaTramiteTraficoBaja(pestania) {
	document.getElementById("idMatricula").readOnly = false;
	document.getElementById("idBastidor").readOnly = false;
	
	document.getElementById("idVehiculo").value = "";
	document.getElementById("idDireccion").value = "";
	document.getElementById("idMatricula").value = "";
	document.getElementById("diaMatriculacionVehiculo").value = "";
	document.getElementById("mesMatriculacionVehiculo").value = "";
	document.getElementById("anioMatriculacionVehiculo").value = "";
	document.getElementById("idTipoVehiculo").value = "-1";
	document.getElementById("idServicioTrafico").value = "-1";
	document.getElementById("idMma").value = "";
	document.getElementById("idProvinciaVehiculo").value = "-1";
	document.getElementById("idMunicipioVehiculo").value = "-1";
	document.getElementById("municipioSeleccionadoVehiculo").value = "";
	document.getElementById("idPuebloVehiculo").value = "-1";
	document.getElementById("puebloSeleccionadoVehiculo").value = "";
	document.getElementById("codigoPostalVehiculo").value = "";
	document.getElementById("tipoViaVehiculo").value = "-1";
	document.getElementById("tipoViaSeleccionadaVehiculo").value = "";
	document.getElementById("nombreViaVehiculo").value = "";
	document.getElementById("numeroDireccionVehiculo").value = "";
	document.getElementById("letraDireccionVehiculo").value = "";
	document.getElementById("escaleraDireccionVehiculo").value = "";
	document.getElementById("pisoDireccionVehiculo").value = "";
	document.getElementById("puertaDireccionVehiculo").value = "";
	document.getElementById("bloqueDireccionVehiculo").value = "";
	document.getElementById("kmDireccionVehiculo").value = "";
	document.getElementById("hmDireccionVehiculo").value = "";
	document.getElementById("idVehiculoText").value = "";
	document.getElementById("idBastidor").value = "";
}

function limpiarFormularioTitular(){
	var dniTitular = document.getElementById("dniTitular").value;
	if (dniTitular != null && dniTitular != "") {
		document.getElementById("tipoPersonaTitular").value = "-1";
		document.getElementById("primerApellidoTitular").value = "";
		document.getElementById("segundoApellidoTitular").value = "";
		document.getElementById("nombreTitular").value = "";
		document.getElementById("sexoTitular").value = "-1";
		document.getElementById("diaNacTitular").value = "";
		document.getElementById("mesNacTitular").value = "";
		document.getElementById("anioNacTitular").value = "";
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
			document.getElementById("primerApellidoRepresentante").value = "";
			document.getElementById("segundoApellidoRepresentante").value = "";
			document.getElementById("nombreRepresentante").value = "";
			document.getElementById("sexoRepresentante").value = "-1";
			document.getElementById("diaInicioRepresentanteTitular").value = "";
			document.getElementById("mesInicioRepresentanteTitular").value = "";
			document.getElementById("anioInicioRepresentanteTitular").value = "";
			document.getElementById("diaFinRepresentanteTitular").value = "";
			document.getElementById("mesFinRepresentanteTitular").value = "";
			document.getElementById("anioFinRepresentanteTitular").value = "";
			document.getElementById("conceptoTutela").value = "-1";
		}
	}
}

function limpiarFormularioAdquiriente(){
	var dniAdquiriente = document.getElementById("dniAdquiriente").value;
	if (dniAdquiriente != null && dniAdquiriente != "") {
		document.getElementById("tipoPersonaAdquiriente").value = "-1";
		document.getElementById("primerApellidoAdquiriente").value = "";
		document.getElementById("segundoApellidoAdquiriente").value = "";
		document.getElementById("nombreAdquiriente").value = "";
		document.getElementById("sexoAdquiriente").value = "-1";
		document.getElementById("diaNacAdquiriente").value = "";
		document.getElementById("mesNacAdquiriente").value = "";
		document.getElementById("anioNacAdquiriente").value = "";
		document.getElementById("idDireccionAdquiriente").value = "";
		document.getElementById("idProvinciaAdquiriente").value = "-1";
		document.getElementById("idMunicipioAdquiriente").length = 0;
		document.getElementById("idMunicipioAdquiriente").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoAdquiriente").value = "";
		document.getElementById("idPuebloAdquiriente").length = 0;
		document.getElementById("idPuebloAdquiriente").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoAdquiriente").value = "";
		document.getElementById("cpAdquiriente").value = "";
		document.getElementById("tipoViaAdquiriente").length = 0;
		document.getElementById("tipoViaAdquiriente").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaAdquiriente").value = "";
		document.getElementById("nombreViaAdquiriente").value = "";
		document.getElementById("numeroDireccionAdquiriente").value = "";
		document.getElementById("letraDireccionAdquiriente").value = "";
		document.getElementById("escaleraDireccionAdquiriente").value = "";
		document.getElementById("pisoDireccionAdquiriente").value = "";
		document.getElementById("puertaDireccionAdquiriente").value = "";
		document.getElementById("bloqueDireccionAdquiriente").value = "";
		document.getElementById("kmDireccionAdquiriente").value = "";
		document.getElementById("hmDireccionAdquiriente").value = "";
	}
}

function limpiarFormularioRepresentanteAdquiriente(){
	if(document.getElementById("dniRepresentanteAdquiriente")) {
		var dniRepresentanteAdquiriente = document.getElementById("dniRepresentanteAdquiriente").value;
		if (dniRepresentanteAdquiriente!=null && dniRepresentanteAdquiriente!="") {
			document.getElementById("tipoPersonaRepresentanteAdquiriente").value = "-1";
			document.getElementById("primerApellidoRepresentanteAd").value = "";
			document.getElementById("segundoApellidoRepresentanteAd").value = "";
			document.getElementById("nombreRepresentanteAd").value = "";
			document.getElementById("sexoRepresentanteAdquiriente").value = "-1";
			document.getElementById("diaInicioRepresentanteAdquiriente").value = "";
			document.getElementById("mesInicioRepresentanteAdquiriente").value = "";
			document.getElementById("anioInicioRepresentanteAdquiriente").value = "";
			document.getElementById("diaFinRepresentanteAdquiriente").value = "";
			document.getElementById("mesFinRepresentanteAdquiriente").value = "";
			document.getElementById("anioFinRepresentanteAdquiriente").value = "";
			document.getElementById("conceptoTutelaAd").value = "-1";
		}
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

function comprobarBaja(){
	deshabilitarBotonesBaja();
	comprobarAltaTramiteTraficoBaja();
}

function deshabilitarBotonesBaja(){
	if (!$('#bValidarBaja').is(':hidden')){
		$("#bValidarBaja").css("color","#BDBDBD");
		$("#bValidarBaja").prop("disabled",true);
	}
	if (!$('#bGuardarBaja').is(':hidden')){
		$("#bGuardarBaja").css("color","#BDBDBD");
		$("#bGuardarBaja").prop("disabled",true);
	}
	if (!$('#bComprobarBaja').is(':hidden')){
		$("#bComprobarBaja").css("color","#BDBDBD");
		$("#bComprobarBaja").prop("disabled",true);
	}
	if (!$('#idBotonImprimir').is(':hidden')){
		$("#idBotonImprimir").css("color","#BDBDBD");
		$("#idBotonImprimir").prop("disabled",true);
	}
	if (!$('#bPdteExcel').is(':hidden')){
		$("#bPdteExcel").css("color","#BDBDBD");
		$("#bPdteExcel").prop("disabled",true);
	}
	if (!$('#bTramTelematicamenteBaja').is(':hidden')){
		$("#bTramTelematicamenteBaja").css("color","#BDBDBD");
		$("#bTramTelematicamenteBaja").prop("disabled",true);
	}
	if (!$('#bLimpiarVehiculo').is(':hidden')){
		$("#bLimpiarVehiculo").css("color","#BDBDBD");
		$("#bLimpiarVehiculo").prop("disabled",true);
	}
	if (!$('#bGuardarTitular').is(':hidden')){
		$("#bGuardarTitular").css("color","#BDBDBD");
		$("#bGuardarTitular").prop("disabled",true);
	}
	
	$("#bloqueBotones").show();
}

function habilitarBotonesBaja() {
	if (!$('#bValidarBaja').is(':hidden')){
		$("#bValidarBaja").css("color","#000000");
		$("#bValidarBaja").prop("disabled",false);
	}
	if (!$('#bGuardarBaja').is(':hidden')){
		$("#bGuardarBaja").css("color","#000000");
		$("#bGuardarBaja").prop("disabled",false);
	}
	if (!$('#bComprobarBaja').is(':hidden')){
		$("#bComprobarBaja").css("color","#000000");
		$("#bComprobarBaja").prop("disabled",false);
	}
	if (!$('#idBotonImprimir').is(':hidden')){
		$("#idBotonImprimir").css("color","#000000");
		$("#idBotonImprimir").prop("disabled",false);
	}
	if (!$('#bPdteExcel').is(':hidden')){
		$("#bPdteExcel").css("color","#000000");
		$("#bPdteExcel").prop("disabled",false);
	}
	if (!$('#bTramTelematicamenteBaja').is(':hidden')){
		$("#bTramTelematicamenteBaja").css("color","#000000");
		$("#bTramTelematicamenteBaja").prop("disabled",false);
	}
	$("#bloqueBotones").hide();
}

function mostrarComprobarValidarCheckBTV(motivo, estado, numExpediente){
	var botonComprobar = document.getElementById("bComprobarBaja");
	var botonValidar = document.getElementById("bValidarBaja");
	var tdValidar = document.getElementById("idTdValidar");
	var campo = null;
	if(motivo != null && numExpediente != null && numExpediente != ""){
		if(motivo.value == "7" || motivo.value == "8"){
			if(estado == "1"){
				botonComprobar.style.display='block'; 
				if(botonValidar != null){
					botonValidar.style.display = 'none';
				}
			}else{
				botonComprobar.style.display='none'; 
				if(botonValidar != null){
					botonValidar.style.display = 'block';
				}else{
					campo = document.createElement("INPUT");
					campo.type = 'button';
					campo.className ='boton'; 
					campo.name = 'bValidarBaja';
					campo.id = 'bValidarBaja';
					campo.value = 'Validar';
					campo.onClick = 'return validarAltaTramiteTraficoBaja();';
					campo.onKeyPress = 'this.onClick';
					tdValidar.appendChild(campo);
				}
			}
		}else{
			if(botonValidar != null){
				botonValidar.style.display = 'block';
			}else{
				campo = document.createElement("INPUT");
				campo.type = 'button';
				campo.className ='boton'; 
				campo.name = 'bValidarBaja';
				campo.id = 'bValidarBaja';
				campo.value = 'Validar';
				campo.onClick = 'return validarAltaTramiteTraficoBaja();';
				campo.onKeyPress = 'this.onClick';
				tdValidar.appendChild(campo);
			}
			botonComprobar.style.display='none'; 
		}
	}
	
	
	
}

function cargarListaPaisesBajasTelematicas(selectMotivoBaja,pais) {
	var selectPaises = document.getElementById('idPaisBaja');
	if (selectMotivoBaja.selectedIndex == null) {
		selectPaises.length = 0;
		selectPaises.options[0] = new Option("Seleccione Pais Baja", "");
		return;
	}
	
	selectedOption = selectMotivoBaja.options[selectMotivoBaja.selectedIndex].value;
	if (selectedOption == 8 || selectedOption == 7 ) {
		selectPaises.disabled = false;
		if (selectedOption == 8) {
		     tipoPaisBajaSeleccionado =1;
		}	 
		
		if (selectedOption == 7) {
		     tipoPaisBajaSeleccionado =0;
		}
		var urlPaises = "recuperarPaisesBajasTelematicasTraficoAjax.action?tipoPaisBajaSeleccionado=";
		url = urlPaises + tipoPaisBajaSeleccionado;
		
		var req_generico_pais = NuevoAjax();
		req_generico_pais.onreadystatechange = function() {
			rellenarListaPaisesBajasTelematicas(req_generico_pais,selectPaises,pais);
		};
		req_generico_pais.open("POST", url, true);
		req_generico_pais.send(null);
	}else{
		selectPaises.options[0].selected = true;
		selectPaises.disabled = true;
	}
}

function rellenarListaPaisesBajasTelematicas(req_generico_pais,select_paises,paisSeleccionado) {

	select_paises.options.length = 0;
   
	if (req_generico_pais.readyState == 4) { 
		if (req_generico_pais.status == 200) { 
			textToSplit = req_generico_pais.responseText;
			returnElements = textToSplit.split(";");
			
			select_paises.options[0] = new Option("Seleccione Pais Baja", "");
			select_paises.options[0].value= "";
			for (var i = 0; i < returnElements.length; i++) {
				value = returnElements[i].split("||");
				select_paises.options[i + 1] = new Option(value[1], value[0]);
				if (select_paises.options[i + 1].value == paisSeleccionado) {
					select_paises.options[i + 1].selected = true;
				}
			}
		}
	}
}

function imprimirPdf() {
	aniadirHidden("numExpediente", $("#idHiddenNumeroExpediente").val(), "formData");
	$("#formData").attr("action", "imprimirAltaTramiteTraficoBaja.action").trigger("submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function aniadirHidden(nombre,valor,formulario){
	limpiarHiddenInput(nombre);
	input = $("<input>").attr("type", "hidden").attr("name", nombre).val(valor);
	$('#'+formulario).append($(input));
}

function verTodasTasas(idContrato,destino){
	var $dest =  $("#"+destino);
	var contrato = $("#"+idContrato);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=4.1",
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

function cargaPestaniaAdquiriente(){
	if (document.getElementById('motivoBaja').value == 1 || document.getElementById('motivoBaja').value == 13) {
		$("#Adquiriente").hide();
	}else{
		$("#Adquiriente").show();
	}
}