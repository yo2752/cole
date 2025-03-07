	/**
	 * Funciones JS relacionadas con Solicitudes de Informacion
	 */
	
	/** Obtiene un interviniente por NIF, en Solicitudes** */
	function buscarIntervinienteSolicitud(tipoInterviniente, pestania) {
		// hidden en el action con el campo tipoIntervinienteBuscar
		document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.formData.action = "buscarIntervinienteAltaSolicitudDatos.action#"
				+ pestania;
		document.formData.submit();
	}

	/** ******************* GUARDAR SOLICITUD DE DATOS ************************ */
	function validacionesGuardarSolicitud() {
		if (document.getElementById("nifSolicitante").value.trim()=='') {
			alert("Es obligatorio indicar el NIF/CIF del solicitante");
			return false;
		}
		if (!validaNIF(document.getElementById("nifSolicitante"))) {
			alert("DNI del solicitante no valido.");
			return false;
		}
		if (document.getElementById("apellido1RazonSocialSolicitante").value.trim()=='') {
			alert("Es obligatorio indicar el primer apellido / raz\u00F3n social del solicitante");
			return false;
		}
		var nif = document.getElementById("nifSolicitante").value;
		if (nif.charAt(0) == 'X' || esNumerico(nif.charAt(0))) {
			if (document.getElementById("nombreSolicitante").value.trim()=='') {
				alert("Es obligatorio indicar el nombre del solicitante");
				return false;
			}
		}
		return true;
	}
	
	// / Para llamarla: loadingSolicitud(this);
	function loadingAltaSolicitud(boton) {
		if (document.getElementById("bGuardarSolicitud")) {
			document.getElementById("bGuardarSolicitud").disabled = "true";
			document.getElementById("bGuardarSolicitud").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bGuardarSolicitud1")) {
			document.getElementById("bGuardarSolicitud1").disabled = "true";
			document.getElementById("bGuardarSolicitud1").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bGuardarSolicitud2")) {
			document.getElementById("bGuardarSolicitud2").disabled = "true";
			document.getElementById("bGuardarSolicitud2").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bFinalizarSolicitud")) {
			document.getElementById("bFinalizarSolicitud").disabled = "true";
			document.getElementById("bFinalizarSolicitud").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("botonInteve")) {
			document.getElementById("botonInteve").disabled = "true";
			document.getElementById("botonInteve").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("botonAtem")) {
			document.getElementById("botonAtem").disabled = "true";
			document.getElementById("botonAtem").style.color = "#6E6E6E";
		}
		
		if (document.getElementById("bObtenerZip")) {
			document.getElementById("bObtenerZip").disabled = "true";
			document.getElementById("bObtenerZip").style.color = "#6E6E6E";
		}
		if (document.getElementById("bReiniciar")) {
			document.getElementById("bReiniciar").disabled = "true";
			document.getElementById("bReiniciar").style.color = "#6E6E6E";
		}
	
		document.getElementById("loadingImage").style.display = "block";
		document.getElementById("loadingImage1").style.display = "block";
		document.getElementById("loadingImage2").style.display = "block";
		boton.value = "Procesando";
	}
	
	function quitarLoadingAltaSolicitud(boton,textoBoton) {
		if (document.getElementById("bGuardarSolicitud")) {
			$("#bGuardarSolicitud").css("color","#000000");
			$("#bGuardarSolicitud").prop("disabled",false);
		}
	
		if (document.getElementById("bGuardarSolicitud1")) {
			$("#bGuardarSolicitud1").css("color","#000000");
			$("#bGuardarSolicitud1").prop("disabled",false);
		}
	
		if (document.getElementById("bGuardarSolicitud2")) {
			$("#bGuardarSolicitud2").css("color","#000000");
			$("#bGuardarSolicitud2").prop("disabled",false);
		}
	
		if (document.getElementById("bFinalizarSolicitud")) {
			$("#bFinalizarSolicitud").css("color","#000000");
			$("#bFinalizarSolicitud").prop("disabled",false);
		}
	
		if (document.getElementById("botonInteve")) {
			$("#botonInteve").css("color","#000000");
			$("#botonInteve").prop("disabled",false);
		}
	
		if (document.getElementById("bObtenerZip")) {
			$("#bObtenerZip").css("color","#000000");
			$("#bObtenerZip").prop("disabled",false);
		}
		if (document.getElementById("bReiniciar")) {
			$("#bReiniciar").css("color","#000000");
			$("#bReiniciar").prop("disabled",false);
		}
	
		document.getElementById("loadingImage").style.display = "none";
		document.getElementById("loadingImage1").style.display = "none";
		document.getElementById("loadingImage2").style.display = "none";
		boton.value = textoBoton;
	}
	
	function guardarSolicitud(boton, pestania) {
		// Mantis 7677 (jose.rumbo): migracion de las validaciones para el solicitante de Javascript a Java lo para ATEM
		//if (!validacionesGuardarSolicitud()) {
		//	return false;
		//}
		
		if ((document.getElementById("matricula").value.trim()!="" || document.getElementById("bastidor").value.trim()!="") && !validarCodigoTasa()) {
			return false;
		}
		document.formData.action = "guardarSolicitudAltaSolicitudDatos.action#" + pestania;
		document.formData.submit();
		loadingAltaSolicitud(boton);
		return true;
	}
	
	function borrarSolicitudVehiculo(idVehiculo) {
		document.getElementsByName("idVehiculoBorrar").value = idVehiculo;
		document.getElementById("idVehiculoBorrar").value = idVehiculo;
		document.formData.action = "borrarSolicitudAltaSolicitudDatos.action#Vehiculos";
		document.formData.submit();
		return true;
	}
	
	function duplicarSolicitud(boton) {
		document.formData.action = "duplicarSolicitudAltaSolicitudDatos.action#Resumen";
		document.formData.submit();
		loadingAltaSolicitud(boton);
		return true;
	}
	
	function avpoSolicitud(boton) {
		if (validacionesGuardarSolicitud()) {
			if (boton.id == 'botonAtem') {
				document.formData.action = "avpoSolicitud_ATEMAltaSolicitudDatos.action#Vehiculos";
			} else if (boton.id == 'botonInteve') {
				document.formData.action = "avpoSolicitud_INTEVEAltaSolicitudDatos.action#Vehiculos";
			} else if (boton.id == 'botonInteveNuevo') {
				document.formData.action = "avpoSolicitud_INTEVENuevoAltaSolicitudDatos.action#Vehiculos";
			} else {
				document.formData.action = "avpoSolicitud_AVPOAltaSolicitudDatos.action#Vehiculos";
			}
			document.formData.submit();
			loadingAltaSolicitud(boton);
		}
		return true;
	}
	
	function consultarSolicitud(boton) {
		/*
		 * if (document.getElementById("codigoTasa").value.toString().length != 12 &&
		 * document.getElementById("codigoTasa").value.toString().length != 0){
		 * alert("Por favor, compruebe que el codigo de tasa tenga todos los
		 * digitos."); return false; }
		 */
		document.formData.action = "consultarSolicitudConsultaSolicitudDatos.action";
		document.formData.submit();
		loadingBusquedaSolicitud(boton);
		return true;
	}
	
	function consultarHistorico(boton) {
		/*
		 * if (document.getElementById("codigoTasa").value.toString().length != 12 &&
		 * document.getElementById("codigoTasa").value.toString().length != 0){
		 * alert("Por favor, compruebe que el codigo de tasa tenga todos los
		 * digitos."); return false; } if
		 * (document.getElementById("codigoTasaHistorico").value.toString().length !=
		 * 12 &&
		 * document.getElementById("codigoTasaHistorico").value.toString().length !=
		 * 0){ alert("Por favor, compruebe que el codigo de tasa tenga todos los
		 * digitos."); return false; }
		 */
		// Habilitar AQUI la pestania de Historico
		document.getElementById("matriculaHistorico").value = document
				.getElementById("matricula").value;
		document.getElementById("bastidorHistorico").value = document
				.getElementById("bastidor").value;
		document.getElementById("codigoTasaHistorico").value = document
				.getElementById("codigoTasa").value;
		document.formData.action = "consultarHistoricoConsultaSolicitudDatos.action#Historico";
		document.formData.submit();
		loadingBusquedaSolicitud(boton);
		return true;
	}
	
	function buscarHistorico(boton) {
		/*
		 * if (document.getElementById("codigoTasa").value.toString().length != 12 &&
		 * document.getElementById("codigoTasa").value.toString().length != 0){
		 * alert("Por favor, compruebe que el codigo de tasa tenga todos los
		 * digitos."); return false; } if
		 * (document.getElementById("codigoTasaHistorico").value.toString().length !=
		 * 12 &&
		 * document.getElementById("codigoTasaHistorico").value.toString().length !=
		 * 0){ alert("Por favor, compruebe que el codigo de tasa tenga todos los
		 * digitos."); return false; }
		 */
		document.formData.action = "buscarHistoricoConsultaSolicitudDatos.action#Historico";
		document.formData.submit();
		loadingBusquedaSolicitud(boton);
		return true;
	}
	
	function imprimirDocumentosBusquedaSolicitud(boton) {
		document.formData.action = "imprimirBusquedaConsultaSolicitudDatos.action#Historico";
		document.formData.submit();
		loadingBusquedaSolicitud(boton);
		return true;
	}
	
	function loadingBusquedaSolicitud(boton) {
		if (document.getElementById("bConsultarSolicitudDatos")) {
			document.getElementById("bConsultarSolicitudDatos").disabled = "true";
			document.getElementById("bConsultarSolicitudDatos").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bHistoricoSolicitudDatos")) {
			document.getElementById("bHistoricoSolicitudDatos").disabled = "true";
			document.getElementById("bHistoricoSolicitudDatos").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bBuscarHistorico")) {
			document.getElementById("bBuscarHistorico").disabled = "true";
			document.getElementById("bBuscarHistorico").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bImprimirDocumentosBusquedaSolicitud")) {
			document.getElementById("bImprimirDocumentosBusquedaSolicitud").disabled = "true";
			document.getElementById("bImprimirDocumentosBusquedaSolicitud").style.color = "#6E6E6E";
		}
	
		if (document.getElementById("bBuscar")) {
			document.getElementById("bBuscar").disabled = "true";
			document.getElementById("bBuscar").style.color = "#6E6E6E";
		}
	
		document.getElementById("loadingImage").style.display = "block";
		boton.value = "Procesando";
	}
	
	function habilitarPestaniaHistorico() {
		document.getElementById('pestaniaHistorico').visible = true;
		document.getElementById('pestaniaHistorico').style.display = "inline";
	}
	
	function muestraDocumento_AVPO() {
		if (document.getElementById("puedoImprimir").value == "true") {
			if (tabsIniciadas) {
				log("hay documento para imprimir:", document
						.getElementById("puedoImprimir").value == "true");
				document.formData.action = "imprimirOKAltaSolicitudDatos.action#Vehiculos";
				document.formData.submit();
				document.getElementById("puedoImprimir").value = "false";
			} else {
				setTimeout("muestraDocumento_AVPO()", 1000);
			}
		}
	}
	
	function imprimirZipSolicitud(boton) {
		var numeroExpediente = document.getElementById("idHiddenNumeroExpediente").value;
		if (numeroExpediente != null) {
			loadingAltaSolicitud(boton);
			document.forms[0].action = "imprimirConsultaTramiteTrafico.action?listaExpedientes="
					+ numeroExpediente;
			document.forms[0].submit();
			return true;
		}
		alert("No se ha recuperado el numero de expediente.");
		return false;
	}
	
	function reiniciarEstados(boton) {
		document.formData.action = "reiniciarEstadosAltaSolicitudDatos.action#Vehiculos";
		document.formData.submit();
		loadingAltaSolicitud(boton);
		return true;
	}
	
	function getMatrSolInfo(idBoton){
		document.formData.action = "buscarMatrSolDatos.action";
		document.formData.submit();
		loadingMatrSolDatos(idBoton, "bloqueLoadingMatrSolDatos");
		return true;
	}
	
	function modificarSolInfo(boton){
		var idIdentificador = boton.id;
		var trozosIdentificador = idIdentificador.split("_");
		var identificador = trozosIdentificador[1];
		document.getElementById("identificador").value = identificador;
		document.getElementById("matriculaNew").value = document.getElementById("matricula_" + identificador).value;
		if(document.getElementById("bastidor_" + identificador)){
			document.getElementById("bastidorNew").value = document.getElementById("bastidor_" + identificador).value;
		}
		boton.disabled = "true";
		boton.style.color = "#6E6E6E";
		boton.value = "Procesando...";
		document.formData.action = "modificarMatrSolDatos.action";
		document.formData.submit();
		return true;
	}
	
	function borrarSolicitudVehiculoBloqueado(){
		alert("No se puede modificar el tr\u00e1mite mientras su estado sea 'Pendiente de respuesta de la DGT'");
	}
	
	function bloqueosSiEstadoPendiente(){
		if(document.getElementById("bGuardarSolicitud1") != null){
			document.getElementById("bGuardarSolicitud1").disabled = "true";
			document.getElementById("bGuardarSolicitud1").style.color = "#6E6E6E";
		}
		if(document.getElementById("bFinalizarSolicitud") != null){
			document.getElementById("bFinalizarSolicitud").disabled = "true";
			document.getElementById("bFinalizarSolicitud").style.color = "#6E6E6E";
		}
		if(document.getElementById("botonInteve") != null){
			document.getElementById("botonInteve").disabled = "true";
			document.getElementById("botonInteve").style.color = "#6E6E6E";
		}
		if(document.getElementById("bReiniciar") != null){
			document.getElementById("bReiniciar").disabled = "true";
			document.getElementById("bReiniciar").style.color = "#6E6E6E";
		}
		if(document.getElementById("bObtenerZip") != null){
			document.getElementById("bObtenerZip").disabled = "true";
			document.getElementById("bObtenerZip").style.color = "#6E6E6E";
		}
		if(document.getElementById("bGuardarSolicitud") != null){
			document.getElementById("bGuardarSolicitud").disabled = "true";
			document.getElementById("bGuardarSolicitud").style.color = "#6E6E6E";
		}
		if(document.getElementById("bGuardarSolicitud2") != null){
			document.getElementById("bGuardarSolicitud2").disabled = "true";
			document.getElementById("bGuardarSolicitud2").style.color = "#6E6E6E";
		}
		if(document.getElementById("botonAtem") != null){
			document.getElementById("botonAtem").disabled = "true";
			document.getElementById("botonAtem").style.color = "#6E6E6E";
		}
	}

	/*
	 * Inicio Scripts Nueva solicitud de informes (AVPO, INTEVEs...)
	 */
	function guardarSolicitudInformacion(pestania) {
		if (($('#matricula').attr('value').trim() != "" || $('#bastidor').attr('value').trim() != "") && !validarCodigoTasa()) {
			return false;
		}
		loading("loadingImage");
		doPost("formData", "guardarSolicitudInformacion.action#" + pestania, null);
	}

	function borrarSolicitudInformacion(idVehiculo) {
		if ($('#estado').attr('value') == "10") {
			alert("No se puede modificar el tr\u00e1mite mientras su estado sea 'Pendiente de respuesta de la DGT'");
			return;
		}
		loading("loadingImage");
		$('#idVehiculoBorrar').attr('value', idVehiculo);
		doPost("formData", "borrarSolicitudInformacion.action", null);
	}

	function reiniciarSolicitudInformacion() {
		if ($('#estado').attr('value') == "10") {
			alert("No se puede modificar el tr\u00e1mite mientras su estado sea 'Pendiente de respuesta de la DGT'");
			return;
		}
		loading("loadingImage");
		doPost("formData", "reiniciarSolicitudInformacion.action", null);
	}

	/** Obtiene un interviniente por NIF, en Solicitudes** */
	function buscarIntervinienteSolicitudInformacion() {
		var $nif = $('#nifSolicitante').attr('value').trim();
		if ($nif != "") {
			var $url = "buscarIntervinienteSolicitudInformacion.action?nifBusqueda="
					+ $nif
					+ "&tramite.numColegiado="
					+ $('#numColegiado').attr('value')
					+ "&tipoIntervinienteBuscar=017";
			doPostWithTarget("solicitanteDiv", $url, null);
		}
	}


	/** Obtiene un interviniente por NIF, en Solicitudes** */
	function buscarIntervinienteFacturacionSolicitudInformacion() {
		var $nif = $('#nifFacturacion').attr('value').trim();
		if ($nif != "") {
			var $url = "buscarIntervinienteSolicitudInformacion.action?nifBusqueda="
					+ $nif
					+ "&tramite.numColegiado="
					+ $('#numColegiado').attr('value');
			doPostWithTarget("facturacionDiv", $url, null);
		}
	}

	function guardarFacturacionSolicitudInformacion() {
		doPost("formData", "guardarTitularFacturacionSolicitudInformacion.action");
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
	function descargaFicheroSolicitudInformacion() {
		loading("loadingImage");
		doPost("formData", "imprimirConsultaTramiteTrafico.action?listaExpedientes=" + $('#idHiddenNumeroExpediente').attr('value'), null);
	}

	function solicitarInforme(boton) {
		var $url;
		if (boton.id == 'botonAtem') {
			$url = "solicitarInformeAtemSolicitudInformacion.action";
		} else if (boton.id == 'botonInteve') {
			$url = "solicitarInformeInteveSolicitudInformacion.action";
		}else if(boton.id == 'botonInteveNuevo'){
			$url = "solicitarInformeInteveNuevoSolicitudInformacion.action";
		} else {
			$url = "solicitarInformeAvpoSolicitudInformacion.action";
		}
		loading("loadingImage");
		doPost("formData", $url, null);
	}

	/*
	 * Fin Scripts Nueva solicitud de informes (AVPO, INTEVEs...)
	 */

	function descargarXmlApp(boton){
		var numeroExpediente = document.getElementById("idHiddenNumeroExpediente").value;
		if (numeroExpediente != null) {
			loadingAltaSolicitud(boton);
			var pestania = obtenerPestaniaSeleccionada();
			$("#formData").attr("action", "descargarXmlAppAltaSolicitudDatos.action"+pestania).trigger("submit");
		} else {
			alert("No se ha recuperado el numero de expediente.");
		}
		quitarLoadingAltaSolicitud(boton, "Descargar XML")
	}
	
	
	function limpiarHiddenInput(nombre){
		if ($("input[name='"+nombre+"']").is(':hidden')){
			$("input[name='"+nombre+"']").remove();
		}
	}
	