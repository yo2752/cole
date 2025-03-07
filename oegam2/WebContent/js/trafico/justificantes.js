	/**
	 * *************************** JUSTIFICANTES PROFESIONALES ***********************
	 */
	
	/** Obtiene un interviniente por NIF, en Transmisiones** */
	function buscarIntervinienteJustificante(tipoInterviniente, pestania) {
		// hidden en el action con el campo tipoIntervinienteBuscar
		document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.formData.action = "buscarIntervinienteAltasJustificantesProfesionales.action#" + pestania;
		document.formData.submit();
	}
	
	function limpiarFormConsultaJustificantesProfesionales() {
		
		if (document.getElementById("numExpediente")) {
			document.getElementById("numExpediente").value = "";
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
		
		// Fecha de inicio
		if (document.getElementById("diaInicio")) {
			document.getElementById("diaInicio").value = "";
		}
		
		if (document.getElementById("mesInicio")) {
			document.getElementById("mesInicio").value = "";
		}
		
		if (document.getElementById("anioInicio")) {
			document.getElementById("anioInicio").value = "";
		}
		
		// Fecha de fin
		if (document.getElementById("diaFin")) {
			document.getElementById("diaFin").value = "";
		}
		
		if (document.getElementById("mesFin")) {
			document.getElementById("mesFin").value = "";
		}
		
		if (document.getElementById("anioFin")) {
			document.getElementById("anioFin").value = "";
		}
		
		if (document.getElementById("estadoId")) {
			document.getElementById("estadoId").value = "-1";
		}
		
		if (document.getElementById("idContratoJustif")) {
			document.getElementById("idContratoJustif").value = "";
		}
		
		if (document.getElementById("idNumColegiadoJust")) {
			document.getElementById("idNumColegiadoJust").value = "";
		}
		
	}
	
	function validarYGenerarJustificanteProAltaDuplicados(pestania) {
		document.formData.action = "validarYGenerarJustificanteProAltasTramiteDuplicado.action#" + pestania;
		document.formData.submit();
		return true;
	}
	
	function altaJustificante(boton){
		mostrarLoadingConsultar(boton);
		var url = (document.URL);
		var pestania = '';
		if(url.indexOf('#')!=-1){
			pestania = (document.URL).substr((document.URL).indexOf('#') + 1);
		}
		generarJustificanteAltasJustificanteProfesional(pestania);
	}
	
	function generarJustificanteAltasJustificanteProfesional(pestania) {
	
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
	
		document.formData.action = "generarJustificanteAltasJustificantesProfesionales.action#" + pestania;
		document.formData.submit();
		return true;
	}
	
	function validarYGenerarJustificanteProAltaTransmision(pestania) {
		loadingGenerarJustificante();
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "validarYGenerarJustificanteProAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		return true;
	}
	
	function generarJustificanteProAltaTransmision(pestania) {
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "generarJustificanteProAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		return true;
	}
	
	function validarYGenerarJustificanteProAltaTransmisionActual(pestania) {
		loadingGenerarJustificanteActual();
		habilitarCamposDeshabilitadosTransmisionActual();
		document.formData.action = "validarYGenerarJustificanteProAltasTramiteTransmisionActual.action#"
				+ pestania;
		document.formData.submit();
		return true;
	}
	
	function confirmJustificanteRepetidoTransmisionActual(pestania) {
		if (confirm("El Colegio le recuerda que solo se debera de solicitar repetidos JP para la tramitacion de un expediente de transferencia"))
			return generarJustificanteProAltaTransmisionActual(pestania);
	}
	
	function confirmJustificanteRepetidoTransmision() {
		if (confirm("El Colegio le recuerda que solo se debera de solicitar repetidos JP para la tramitacion de un expediente de transferencia")) {
			numeroExpediente = document.getElementById("idHiddenNumeroExpediente");
			habilitarCamposDeshabilitadosTransmision();
			document.formData.action = "generarJustificanteProAltasTramiteTransmision.action?numExpediente="
					+ numeroExpediente;
			document.formData.submit();
			return true;
	
		} else
			return false;
	}
	
	function verificarJustificante(boton) {
	
		if (document.getElementById('csv') == null
				|| document.getElementById('csv').value == "") {
			alert("Debe indicar un Codigo Seguro de Verificacion para poder realizar la consulta");
			return false;
		}
	
		document.formData.action = "verificarJustificantesProfesionales.action";
		loadingBusquedaSolicitud(boton);
		document.formData.submit();
	}
	
	function buscarVerificacionJustificante(boton) {
	
		if (document.getElementById('csv') == null
				|| document.getElementById('csv').value == "") {
			alert("Debe indicar un Codigo Seguro de Verificacion para poder realizar la consulta");
			return false;
		}
	
		document.formData.action = "buscarVerificarJustificantesProfesionales.action";
		loadingBusquedaSolicitud(boton);
		document.formData.submit();
	}
	
	function loadingGenerarJustificante() {
		if (document.getElementById("bGuardar10")) {
			document.getElementById("bGuardar10").disabled = "true";
			document.getElementById("bGuardar10").style.color = "#6E6E6E";
		}
		if (document.getElementById("bFinalizar")) {
			document.getElementById("bFinalizar").disabled = "true";
			document.getElementById("bFinalizar").style.color = "#6E6E6E";
		}
		if (document.getElementById("bTramitacionTelematica")) {
			document.getElementById("bTramitacionTelematica").disabled = "true";
			document.getElementById("bTramitacionTelematica").style.color = "#6E6E6E";
		}
		if (document.getElementById("bImprimir")) {
			document.getElementById("bImprimir").disabled = "true";
			document.getElementById("bImprimir").style.color = "#6E6E6E";
		}
		document.getElementById("loadingImage16").style.display = "block";
		document.getElementById("idBotonGenerarJustificantesPro").value = "Procesando";
		document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
		return;
	}
	
	function checkFA(){
		var check = document.getElementById('idCheckFa').checked && 
			!document.getElementById('idCheckFa').disabled;
		 if (check) {
			 document.getElementById('idFa').disabled =false;
			 document.getElementById("idLabelFa").style.color = "black";
		 } else  {
			 document.getElementById('idFa').disabled =true;
			 document.getElementById('idFa').value ="";
			 document.getElementById("idLabelFa").style.color = "gray";
		 }
		return;
	}
	
	function habilitarAutonomo(idAutonomo, idIAE){
		var check = document.getElementById(idAutonomo).checked && 
					!document.getElementById(idAutonomo).disabled;
		if (check) {
			document.getElementById(idIAE).disabled = false;
		} else {
			document.getElementById(idIAE).disabled = true;
			document.getElementById(idIAE).value = '-1';
		}
	}
	
	function loadingGenerarJustificanteActual() {
		if (document.getElementById("bGuardar10")) {
			document.getElementById("bGuardar10").disabled = "true";
			document.getElementById("bGuardar10").style.color = "#6E6E6E";
		}
		if (document.getElementById("bFinalizar")) {
			document.getElementById("bFinalizar").disabled = "true";
			document.getElementById("bFinalizar").style.color = "#6E6E6E";
		}
		if (document.getElementById("bImprimir")) {
			document.getElementById("bImprimir").disabled = "true";
			document.getElementById("bImprimir").style.color = "#6E6E6E";
		}
		document.getElementById("loadingImage16").style.display = "block";
		document.getElementById("idBotonGenerarJustificantesPro").value = "Procesando";
		document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
		return;
	}
	
	function generarJP(boton){
		if(numCheckedImprimir() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","generarJustificanteProfesionalForzadoConsultaTramiteTrafico.action");
		$('#formData').submit();
		return true;
	}
	
	function habilitarCamposDeshabilitadosTransmision() {
		document.getElementById("acreditacionHerenciaId").disabled = false;
		// <!-- ***************** ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccion 16 Abril ************-->
		document.getElementById("codigoTasaCambioServicioId").disabled = false;
		// <!-- ***************** fin ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccion 16 Abril ************-->
		document.getElementById("tipoPersonaAdquiriente").disabled = false;
		document.getElementById("motivoRepresentanteAdquiriente").disabled = false;
		document.getElementById("codigoIAEAdquiriente").disabled = false;
		document.getElementById("tipoPersonaTransmitente").disabled = false;
		document.getElementById("motivoRepresentanteTransmitente").disabled = false;
		document.getElementById("tipoPersonaPrimerCotitularTransmitente").disabled = false;
		document.getElementById("tipoPersonaSegundoCotitularTransmitente").disabled = false;
		document.getElementById("tipoPersonaPresentador").disabled = false;
		document.getElementById("tipoPersonaPoseedor").disabled = false;
		document.getElementById("motivoRepresentantePoseedor").disabled = false;
		document.getElementById("tipoPersonaArrendatario").disabled = false;
		document.getElementById("motivoRepresentanteArrendatario").disabled = false;
		document.getElementById("idEstado620").disabled = false;
		document.getElementById("oficinaLiquidadora").disabled = false;
		document.getElementById("fundamentoExencion620").disabled = false;
		document.getElementById("fundamentoNoSujeccion620").disabled = false;
		document.getElementById("direccionDistintaAdquiriente620").disabled = false;
	}

	function habilitarCamposDeshabilitadosTransmisionActual() {
		document.getElementById("estadoId").disabled = false;
		document.getElementById("tipoPersonaAdquiriente").disabled = false;
		document.getElementById("motivoRepresentanteAdquiriente").disabled = false;
		document.getElementById("codigoIAEAdquiriente").disabled = false;
		document.getElementById("tipoPersonaTransmitente").disabled = false;
		document.getElementById("motivoRepresentanteTransmitente").disabled = false;
		document.getElementById("tipoPersonaPresentador").disabled = false;
		document.getElementById("tipoPersonaPoseedor").disabled = false;
		document.getElementById("motivoRepresentantePoseedor").disabled = false;
		document.getElementById("idEstado620").disabled = false;
		document.getElementById("oficinaLiquidadora").disabled = false;
		document.getElementById("fundamentoExencion620").disabled = false;
		document.getElementById("fundamentoNoSujeccion620").disabled = false;
		document.getElementById("direccionDistintaAdquiriente620").disabled = false;
	}

	function deshabilitarCamposDeshabilitadosTransmision() {
		document.getElementById("estadoId").disabled = true;
		if (document.getElementById("modoAdjudicacionId").value != "3") {
			document.getElementById("acreditacionHerenciaId").disabled = true;
		}
		// <!-- ***************** ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccion 16 Abril ************-->
		if (!document.getElementById("cambioServicioId").checked) {
			document.getElementById("codigoTasaCambioServicioId").disabled = true;
		}
		// <!-- ***************** fin ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccion 16 Abril ************-->
		document.getElementById("tipoPersonaAdquiriente").disabled = true;
		if (document.getElementById("conceptoRepresentanteAdquiriente").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentanteAdquiriente").disabled = true;
		}
		if (!document.getElementById("autonomoIdAdquiriente").checked) {
			document.getElementById("codigoIAEAdquiriente").disabled = true;
		}
		document.getElementById("tipoPersonaTransmitente").disabled = true;
		if (document.getElementById("conceptoRepresentanteTransmitente").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentanteTransmitente").disabled = true;
		}
		document.getElementById("tipoPersonaPrimerCotitularTransmitente").disabled = true;
		document.getElementById("tipoPersonaSegundoCotitularTransmitente").disabled = true;
		document.getElementById("tipoPersonaPresentador").disabled = true;
		document.getElementById("tipoPersonaPoseedor").disabled = true;
		if (document.getElementById("conceptoRepresentantePoseedor").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentantePoseedor").disabled = true;
		}
		document.getElementById("tipoPersonaArrendatario").disabled = true;
		if (document.getElementById("conceptoRepresentanteArrendatario").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentanteArrendatario").disabled = true;
		}
		document.getElementById("idEstado620").disabled = true;
		document.getElementById("oficinaLiquidadora").disabled = true;
		if (!document.getElementById("exento620").checked) {
			document.getElementById("fundamentoExencion620").disabled = true;
		}
		if (!document.getElementById("noSujeto620").checked) {
			document.getElementById("fundamentoNoSujeccion620").disabled = true;
		}
		document.getElementById("direccionDistintaAdquiriente620").disabled = true;
	}

	function deshabilitarCamposDeshabilitadosTransmisionActual() {
		document.getElementById("estadoId").disabled = true;
		document.getElementById("tipoPersonaAdquiriente").disabled = true;
		if (document.getElementById("conceptoRepresentanteAdquiriente").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentanteAdquiriente").disabled = true;
		}
		if (!document.getElementById("autonomoIdAdquiriente").checked) {
			document.getElementById("codigoIAEAdquiriente").disabled = true;
		}
		document.getElementById("tipoPersonaTransmitente").disabled = true;
		if (document.getElementById("conceptoRepresentanteTransmitente").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentanteTransmitente").disabled = true;
		}
		document.getElementById("tipoPersonaPresentador").disabled = true;
		document.getElementById("tipoPersonaPoseedor").disabled = true;
		if (document.getElementById("conceptoRepresentantePoseedor").value != "TUTELA O PATRIA POTESTAD") {
			document.getElementById("motivoRepresentantePoseedor").disabled = true;
		}
		document.getElementById("idEstado620").disabled = true;
		document.getElementById("oficinaLiquidadora").disabled = true;
		if (!document.getElementById("exento620").checked) {
			document.getElementById("fundamentoExencion620").disabled = true;
		}
		if (!document.getElementById("noSujeto620").checked) {
			document.getElementById("fundamentoNoSujeccion620").disabled = true;
		}
		document.getElementById("direccionDistintaAdquiriente620").disabled = true;
	}

	function abrirDetalle(idNumJustificanteInterno){
		aniadirHidden("justificanteProfDto.idJustificanteInterno", idNumJustificanteInterno, "formData");
		aniadirHidden("vieneDeResumen", true, "formData");
		$("#formData").attr("action", "detalleAltaJustificanteProfesional.action").trigger("submit");
	}

	function faConValor() {
		if (document.getElementById("idFa").value != null
				&& document.getElementById("idFa").value != "") {
			document.getElementById("idCheckFa").checked = true;
			document.getElementById("idFa").disabled = false;
			document.getElementById("idLabelFa").style.color = "black";
		}else {
			document.getElementById("idCheckFa").checked = false;
			document.getElementById("idFa").disabled = true;
		}
	}