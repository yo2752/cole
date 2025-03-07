/**
	 *  Funciones JS de Transmision
	 */

	/** Obtiene un interviniente por NIF, en Transmisiones** */
	function buscarIntervinienteTransmision(tipoInterviniente, pestania) {
		// hidden en el action con el campo tipoIntervinienteBuscar
		document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.formData.action = "buscarIntervinienteAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
	}

	/** Obtiene un interviniente por NIF, en Transmisiones Actual** */
	function buscarIntervinienteTransmisionActual(tipoInterviniente, pestania) {
		// hidden en el action con el campo tipoIntervinienteBuscar
		document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
		document.formData.action = "buscarIntervinienteAltasTramiteTransmisionActual.action#"
				+ pestania;
		document.formData.submit();
	}

	function validarPermiso(){
		var seleccionTipo = 0;
		if (document.getElementById('tipoTransferenciaId') != null){
			seleccionTipo = document.getElementById('tipoTransferenciaId').value;
		}

		if(seleccionTipo != "5" && seleccionTipo != "4") {
			if(!document.getElementsByName("tramiteTraficoTransmisionBean.impresionPermiso")[0].checked){
				if(!confirm("Recuerde que no tiene marcado el check de impresión de permiso de circulación y sin esto no se va a imprimir dicho permiso.")){
					return false;
				}
			}
		}
		return true;
	}

	/** *********** VALIDA EL TRAMITE *********** */
	function validarAltaTramiteTransmision(pestania) {
		if (!validarPermiso()) {
			return false;
		}
		var seleccionTipo = 0;
		if (document.getElementById('tipoTransferenciaId') != null) {
			seleccionTipo = document.getElementById('tipoTransferenciaId').value;
		}

		if((seleccionTipo == "4" || seleccionTipo == "5") && document.getElementsByName("tramiteTraficoTransmisionBean.impresionPermiso")[0].checked) {
			alert("Debe desmarcar el check de Impresión de Permisos.");
			return false;
		}

		if (confirm(document.getElementById("textoLegal").value)) {
			habilitarCamposDeshabilitadosTransmision();
			document.formData.action = "validarTramiteTransmisionAltasTramiteTransmision.action#"
					+ pestania;
			document.formData.submit();
			loadingValidarTransmision();
			return true;
		} else {
			return false;
		}
	}

	/** *********** VALIDA EL TRAMITE *********** */
	function validarTramiteTransmisionEnServidor(pestania) {
		if (confirm(document.getElementById("textoLegal").value)) {
			habilitarCamposDeshabilitadosTransmision();
			document.formData.action = "validarTramiteTransmisionEnServidorAltasTramiteTransmision.action#"
					+ pestania;
			document.formData.submit();
			loadingValidarTransmision();
			return true;
		} else {
			return false;
		}
	}

	/** *********** VALIDA EL TRAMITE ACTUAL, SIN CTIT *********** */
	function validarAltaTramiteTransmisionActual(pestania) {
		if (confirm(document.getElementById("textoLegal").value)) {
			habilitarCamposDeshabilitadosTransmisionActual();
			document.formData.action = "validarTramiteTransmisionAltasTramiteTransmisionActual.action#"
					+ pestania;
			document.formData.submit();
			loadingValidarTransmision();
			return true;
		} else {
			return false;
		}
	}

	/** *********** REALIZA LA TRANSMISION TELEMATICA *********** */
	function tramitacionTelematicaAltaTramiteTransmision(pestania) {
		if (!confirm("Se le va a descontar 1 credito"))
			return false;

		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "transmisionTelematicaAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		loadingTramitacionTelematica();
		return true;
	}

	/** *********** IMPRIME EL PDF *********** */
	function imprimirPdfAltaTramiteTransmision() {
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "impresionConsultaTramiteTrafico.action?listaExpedientes="
				+ document.getElementById("idHiddenNumeroExpediente").value;
		document.formData.submit();
		loadingImprimirPdf();
		return true;
	}

	function imprimirPdfAltaTramiteTransmisionActual() {
		habilitarCamposDeshabilitadosTransmisionActual();
		document.formData.action = "imprimirConsultaTramiteTrafico.action?listaExpedientes="
				+ document.getElementById("idHiddenNumeroExpediente").value;
		document.formData.submit();
		loadingImprimirPdf();
		return true;
	}

	function validaFechaCaducidadNif(){
		if (document.getElementById('idNifAdquiriente')!=''){
			if (document.getElementById('otroDocumentoIdentidad').checked==false){
				if (document.getElementById('diaCadNif').value=='' || document.getElementById('mesCadNif').value=='' || document.getElementById('anioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el adquiriente");
					return false;
				}
			}else{
				if (document.getElementById('diaCadAlternativo').value=='' || document.getElementById('mesCadAlternativo').value=='' || document.getElementById('anioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el adquiriente");
					return false;
				}
			}
		}

		if (document.getElementById('nifConductorHabitualAdquiriente').value!=''){
			if (document.getElementById('conductorHabitualOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('conductorHabitualDiaCadNif').value=='' || document.getElementById('conductorHabitualMesCadNif').value=='' 
					|| document.getElementById('conductorHabitualAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el conductor habitual");
					return false;
				}
			}else{
				if (document.getElementById('conductorHabitualDiaCadAlternativo').value=='' || document.getElementById('conductorHabitualMesCadAlternativo').value=='' 
					|| document.getElementById('conductorHabitualAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el conductor habitual");
					return false;
				}
			}
		}

		if (document.getElementById('nifRepresentanteAdquiriente').value!=''){
			if (document.getElementById('representanteAdquirienteOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('representanteAdquirienteDiaCadNif').value=='' || document.getElementById('representanteAdquirienteMesCadNif').value=='' 
					|| document.getElementById('representanteAdquirienteAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el representante del adquiriente");
					return false;
				}
			}else{
				if (document.getElementById('representanteAdquirienteDiaCadAlternativo').value=='' || document.getElementById('representanteAdquirienteMesCadAlternativo').value=='' 
					|| document.getElementById('representanteAdquirienteAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el representante del adquiriente");
					return false;
				}
			}
		}

		if (document.getElementById('idNifTransmitente').value!=''){
			if (document.getElementById('transOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('transDiaCadNif').value=='' || document.getElementById('transMesCadNif').value=='' 
					|| document.getElementById('transAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el transmitente");
					return false;
				}
			}else{
				if (document.getElementById('transDiaCadAlternativo').value=='' || document.getElementById('transMesCadAlternativo').value=='' 
					|| document.getElementById('transAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el transmitente");
					return false;
				}
			}
		}

		if (document.getElementById('nifRepresentanteTransmitente').value!=''){
			if (document.getElementById('represOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('represDiaCadNif').value=='' || document.getElementById('represMesCadNif').value=='' 
					|| document.getElementById('represAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el representante transmitente");
					return false;
				}
			}else{
				if (document.getElementById('represDiaCadAlternativo').value=='' || document.getElementById('represAnioCadAlternativo').value=='' 
					|| document.getElementById('represMesCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el representante transmitente");
					return false;
				}
			}
		}

		if (document.getElementById('idNifPrimerCotitularTransmitente').value!=''){
			if (document.getElementById('primerCotOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('primerCotDiaCadNif').value=='' || document.getElementById('primerCotMesCadNif').value=='' 
					|| document.getElementById('primerCotAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el primer cotitular");
					return false;
				}
			}else{
				if (document.getElementById('primerCotDiaCadAlternativo').value=='' || document.getElementById('primerCotMesCadAlternativo').value=='' 
					|| document.getElementById('primerCotAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el primer cotitular");
					return false;
				}
			}
		}

		if (document.getElementById('idNifSegundoCotitularTransmitente').value!=''){
			if (document.getElementById('segundoCotitularOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('segundoCotDiaCadNif').value=='' || document.getElementById('segundoCotMesCadNif').value=='' 
					|| document.getElementById('segundoCotAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el segundo cotitular");
					return false;
				}
			}else{
				if (document.getElementById('segundoCotDiaCadAlternativo').value=='' || document.getElementById('segundoCotAnioCadAlternativo').value=='' 
					|| document.getElementById('segundoCotMesCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el segundo cotitular");
					return false;
				}
			}
		}

		if (document.getElementById('idNifPoseedor').value!=''){
			if (document.getElementById('poseedorOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('poseedorDiaCadNif').value=='' || document.getElementById('poseedorMesCadNif').value=='' 
					|| document.getElementById('poseedorAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el poseedor");
					return false;
				}
			}else{
				if (document.getElementById('poseedorDiaCadAlternativo').value=='' || document.getElementById('poseedorMesCadAlternativo').value=='' 
					|| document.getElementById('poseedorAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el poseedor");
					return false;
				}
			}
		}

		if (document.getElementById('nifRepresentantePoseedor').value!=''){
			if (document.getElementById('representantePoseedorOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('representantePoseedorDiaCadNif').value=='' || document.getElementById('representantePoseedorMesCadNif').value=='' 
					|| document.getElementById('representantePoseedorAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el representate del poseedor");
					return false;
				}
			}else{
				if (document.getElementById('representantePoseedorDiaCadAlternativo').value=='' || document.getElementById('representantePoseedorMesCadAlternativo').value=='' 
					|| document.getElementById('representantePoseedorAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el representate del poseedor");
					return false;
				}
			}
		}

		if (document.getElementById('idNifArrendatario').value!=''){
			if (document.getElementById('arrendatarioOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('arrendatarioDiaCadNif').value=='' || document.getElementById('arrendatarioMesCadNif').value=='' 
					|| document.getElementById('arrendatarioAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el arrendatario");
					return false;
				}
			}else{
				if (document.getElementById('arrendatarioDiaCadAlternativo').value=='' || document.getElementById('arrendatarioMesCadAlternativo').value=='' 
					|| document.getElementById('arrendatarioAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el arrendatario");
					return false;
				}
			}
		}

		if (document.getElementById('nifRepresentanteArrendatario').value!=''){
			if (document.getElementById('representanteArrendatarioOtroDocumentoIdentidad').checked==false){
				if (document.getElementById('representanteArrendatarioDiaCadNif').value=='' || document.getElementById('representanteArrendatarioMesCadNif').value=='' 
					|| document.getElementById('representanteArrendatarioAnioCadNif').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del nif para el representante del arrendatario");
					return false;
				}
			}else{
				if (document.getElementById('representanteArrendatarioDiaCadAlternativo').value=='' || document.getElementById('representanteArrendatarioMesCadAlternativo').value=='' 
					|| document.getElementById('representanteArrendatarioAnioCadAlternativo').value==''){
					alert("Es obligatorio insertar la fecha de caducidad del documento alternativo para el representante del arrendatario");
					return false;
				}
			}
		}
		return true;
	}
	/** *********** GUARDAR ALTA TRAMITE TRANSMISION *********** */
	// GUARDAR ALTAS NUEVAS DE TRAMITES DE TRANSMISION DE TRAFICO
	function guardarAltaTramiteTransmision(pestania) {
		/*
		 * Antes de guardar se comprueban validaciones de que se han seleccionado o
		 * rellenado los campos necesarios. Ademas se validan que el NIF y la fecha
		 * sean validos.

		if (validaFechaCaducidadNif() == false){
			return false;
		}
		*/
		//Mantis 25415
		/*if(!validarDosDecimalesValorReal(document.getElementById('valorReal').value) && 
			document.getElementById('nFactura').value== ""){
			alert("El valor real de la transmisi\u00f3n debe ser superior a cero y con un m\u00E1ximo de 6 enteros y 2 decimales");
			return false;
		}*/
		//Mantis 25415
		if(document.getElementById('valorReal').value!= "") {
			var coma = document.getElementById('valorReal').value.indexOf(".");
			if(coma==-1){coma = document.getElementById('valorReal').value.indexOf(",");};
			if(coma!=-1){
			document.getElementById('valorReal').value = 
				document.getElementById('valorReal').value.substr(0,coma+3);
			}
		}

		// Pestania Tramite
		if ((document.getElementById("modoAdjudicacionId").value != "-1")
				&& (document.getElementById("tipoTransferenciaId").value == "-1")) {
			alert("Ha seleccionado un modo de adjudicaci\u00f3n. Debe seleccionar el modo de transferencia.");
			return false;
		}

		if (document.getElementById("modoAdjudicacionId").value == "3"
				&& (document.getElementById("acreditacionHerenciaId").value == "-1" && document
						.getElementById("acreditacionHerenciaId").value == "")) {
			alert("Ha seleccionado el modo de adjudicaci\u00f3n: Fallecimiento o donaci\u00f3n del titular. Debe seleccionar la acreditaci\u00f3n.");
			return false;
		}

		// <!-- ***************** ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccionn 16 Abril ************-->
		if ((document.getElementById("codigoTasaTransmisionId").value == document
				.getElementById("codigoTasaInformeId").value && document
				.getElementById("codigoTasaInformeId").value != -1)
				|| (document.getElementById("codigoTasaTransmisionId").value == document
						.getElementById("codigoTasaCambioServicioId").value && document
						.getElementById("codigoTasaCambioServicioId").value != -1)
				|| (document.getElementById("codigoTasaInformeId").value == document
						.getElementById("codigoTasaCambioServicioId").value && document
						.getElementById("codigoTasaInformeId").value != -1)) {
			alert("Los c\u00f3digos de tasas seleccionados deben ser distintos.");
			return false;
		}
		// <!-- ***************** fin ESTABA COMENTADO POR -- mantis: 0001432: CTIT:
		// Nueva version - Produccion 16 Abril ************-->

		// ****************** ESTABA ESTE CODIGO PARA PASE A PRODUCCION 16 de abril.
		/*-- mantis: 0001432: CTIT: Nueva version - Produccion 16 Abril ************-->
		/*if(document.getElementById("codigoTasaTransmisionId").value == document.getElementById("codigoTasaInformeId").value && document.getElementById("codigoTasaInformeId").value!=-1){
			alert("Los c\u00f3digos de tasas seleccionados deben ser distintos.");
			return false;
		}*/
		// ****************** FIN ESTABA ESTE CODIGO -- mantis: 0001432: CTIT: Nueva
		// version - Produccion 16 Abril ************-->
		// Pestania Adquiriente
		if (document.getElementById("idNifAdquiriente").value != "") {
			if (!validaNIF(document.getElementById("idNifAdquiriente"))) {
				alert("NIF/CIF de Adquiriente no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("viaAdquiriente").value != ""
				&& document.getElementById("numeroAdquiriente").value == "") {
			document.getElementById("numeroAdquiriente").value = "sn";
		}

		if (document.getElementById("sexoAdquiriente").value == "X"
				&& document.getElementById("tipoTransferenciaId").value != "4") {
			if (document.getElementById("nifRepresentanteAdquiriente").value == null
					|| document.getElementById("nifRepresentanteAdquiriente").value == "") {
				alert("Debe informar el representante del adquiriente, ya que se trata de una empresa");
				return false;
			}
		}

		if (document.getElementById("diaNacAdquiriente").value != ""
				|| document.getElementById("mesNacAdquiriente").value != ""
				|| document.getElementById("anioNacAdquiriente").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacAdquiriente"),
					document.getElementById("mesNacAdquiriente"), document
							.getElementById("anioNacAdquiriente"), false)) {
				alert("Fecha de Nacimiento del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("autonomoIdAdquiriente").checked
				&& document.getElementById("codigoIAEAdquiriente").value == "") {
			alert("Debe introducir el C\u00f3digo IAE del Adquiriente si este es aut\u00f3nomo.");
			return false;
		}

		if (document.getElementById("nifConductorHabitualAdquiriente").value != "") {
			if (!validaNIF(document
					.getElementById("nifConductorHabitualAdquiriente"))) {
				alert("NIF/CIF de Conductor Habitual no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("diaNacConductorHabitualAdquiriente").value != ""
				|| document.getElementById("mesNacConductorHabitualAdquiriente").value != ""
				|| document.getElementById("anioNacConductorHabitualAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaNacConductorHabitualAdquiriente"), document
					.getElementById("mesNacConductorHabitualAdquiriente"), document
					.getElementById("anioNacConductorHabitualAdquiriente"), false)) {
				alert("Fecha de Nacimiento del Conductor Habitual no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioConductorHabitualAdquiriente").value != ""
				|| document.getElementById("mesInicioConductorHabitualAdquiriente").value != ""
				|| document
						.getElementById("anioInicioConductorHabitualAdquiriente").value != "") {
			if (!validaInputFecha(
					document
							.getElementById("diaInicioConductorHabitualAdquiriente"),
					document
							.getElementById("mesInicioConductorHabitualAdquiriente"),
					document
							.getElementById("anioInicioConductorHabitualAdquiriente"),
					false)) {
				alert("Fecha de Inicio del Conductor Habitual no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinConductorHabitualAdquiriente").value != ""
				|| document.getElementById("mesFinConductorHabitualAdquiriente").value != ""
				|| document.getElementById("anioFinConductorHabitualAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinConductorHabitualAdquiriente"), document
					.getElementById("mesFinConductorHabitualAdquiriente"), document
					.getElementById("anioFinConductorHabitualAdquiriente"), false)) {
				alert("Fecha de Fin del Conductor Habitual no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioConductorHabitualAdquiriente").value != ""
				&& document.getElementById("mesInicioConductorHabitualAdquiriente").value != ""
				&& document
						.getElementById("anioInicioConductorHabitualAdquiriente").value != ""
				&& document.getElementById("diaFinConductorHabitualAdquiriente").value != ""
				&& document.getElementById("mesFinConductorHabitualAdquiriente").value != ""
				&& document.getElementById("anioFinConductorHabitualAdquiriente").value != "") {
			var fInicioConductorHabitual = new Date(
					document
							.getElementById("anioInicioConductorHabitualAdquiriente").value,
					parseInt(document
							.getElementById("mesInicioConductorHabitualAdquiriente").value) - 1,
					document
							.getElementById("diaInicioConductorHabitualAdquiriente").value)
					.getTime();
			var fFinConductorHabitual = new Date(
					document.getElementById("anioFinConductorHabitualAdquiriente").value,
					parseInt(document
							.getElementById("mesFinConductorHabitualAdquiriente").value) - 1,
					document.getElementById("diaFinConductorHabitualAdquiriente").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioConductorHabitual > fFinConductorHabitual) {
				alert("La fecha de Inicio del Conductor Habitual no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioConductorHabitual > fActual) {
				alert("La fecha de Inicio del Conductor Habitual no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteAdquiriente").value != ""
				|| document.getElementById("mesInicioRepresentanteAdquiriente").value != ""
				|| document.getElementById("anioInicioRepresentanteAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteAdquiriente"), document
					.getElementById("mesInicioRepresentanteAdquiriente"), document
					.getElementById("anioInicioRepresentanteAdquiriente"), false)) {
				alert("Fecha de Inicio del Representante del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteAdquiriente").value != ""
				|| document.getElementById("mesFinRepresentanteAdquiriente").value != ""
				|| document.getElementById("anioFinRepresentanteAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteAdquiriente"), document
					.getElementById("mesFinRepresentanteAdquiriente"), document
					.getElementById("anioFinRepresentanteAdquiriente"), false)) {
				alert("Fecha de Fin del Representante del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("mesInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("anioInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("diaFinRepresentanteAdquiriente").value != ""
				&& document.getElementById("mesFinRepresentanteAdquiriente").value != ""
				&& document.getElementById("anioFinRepresentanteAdquiriente").value != "") {
			var fInicioRepresentanteAdquiriente = new Date(
					document.getElementById("anioInicioRepresentanteAdquiriente").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteAdquiriente").value) - 1,
					document.getElementById("diaInicioRepresentanteAdquiriente").value)
					.getTime();
			var fFinRepresentanteAdquiriente = new Date(
					document.getElementById("anioFinRepresentanteAdquiriente").value,
					parseInt(document
							.getElementById("mesFinRepresentanteAdquiriente").value) - 1,
					document.getElementById("diaFinRepresentanteAdquiriente").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteAdquiriente > fFinRepresentanteAdquiriente) {
				alert("La fecha de Inicio del Representante del Adquiriente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteAdquiriente > fActual) {
				alert("La fecha de Inicio del Representante del Adquiriente no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		// Pestania Transmitente
		if (document.getElementById("idNifTransmitente").value != "") {
			if (!validaNIF(document.getElementById("idNifTransmitente"))) {
				alert("NIF/CIF de Transmitente no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("sexoTransmitente").value == "X") {
			if (document.getElementById("nifRepresentanteTransmitente").value == null
					|| document.getElementById("nifRepresentanteTransmitente").value == "") {
				alert("Debe informar el representante del transmitente, ya que se trata de una empresa");
				return false;
			}
		}

		if (document.getElementById("diaNacTransmitente").value != ""
				|| document.getElementById("mesNacTransmitente").value != ""
				|| document.getElementById("anioNacTransmitente").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacTransmitente"),
					document.getElementById("mesNacTransmitente"), document
							.getElementById("anioNacTransmitente"), false)) {
				alert("Fecha de Nacimiento del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteTransmitente").value != ""
				|| document.getElementById("mesInicioRepresentanteTransmitente").value != ""
				|| document.getElementById("anioInicioRepresentanteTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteTransmitente"), document
					.getElementById("mesInicioRepresentanteTransmitente"), document
					.getElementById("anioInicioRepresentanteTransmitente"), false)) {
				alert("Fecha de Inicio del Representante del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteTransmitente").value != ""
				|| document.getElementById("mesFinRepresentanteTransmitente").value != ""
				|| document.getElementById("anioFinRepresentanteTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteTransmitente"), document
					.getElementById("mesFinRepresentanteTransmitente"), document
					.getElementById("anioFinRepresentanteTransmitente"), false)) {
				alert("Fecha de Fin del Representante del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("mesInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("anioInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("diaFinRepresentanteTransmitente").value != ""
				&& document.getElementById("mesFinRepresentanteTransmitente").value != ""
				&& document.getElementById("anioFinRepresentanteTransmitente").value != "") {
			var fInicioRepresentanteTransmitente = new Date(
					document.getElementById("anioInicioRepresentanteTransmitente").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteTransmitente").value) - 1,
					document.getElementById("diaInicioRepresentanteTransmitente").value)
					.getTime();
			var fFinRepresentanteTransmitente = new Date(
					document.getElementById("anioFinRepresentanteTransmitente").value,
					parseInt(document
							.getElementById("mesFinRepresentanteTransmitente").value) - 1,
					document.getElementById("diaFinRepresentanteTransmitente").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteTransmitente > fFinRepresentanteTransmitente) {
				alert("La fecha de Inicio del Representante del Transmitente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteTransmitente > fActual) {
				alert("La fecha de Inicio del Representante del Transmitente no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		if (document.getElementById("idNifPrimerCotitularTransmitente").value !="" &&
				document.getElementById("viaPrimerCotitularTransmitente").value != "" &&
				document.getElementById("numeroPrimerCotitularTransmitente").value == "" ){
					alert("Si se introduce direcci\u00f3n del primer cotitular es obligatorio n\u00famero de via. En caso de no tener poner sn");
					return false;
		}

		// REPRESENTANTE DEL PRIMER COTITULAR
		if (document.getElementById("diaInicioRepresentantePrimerCotitularTransmitente").value != ""
			|| document.getElementById("mesInicioRepresentantePrimerCotitularTransmitente").value != ""
				|| document.getElementById("anioInicioRepresentantePrimerCotitularTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentantePrimerCotitularTransmitente"), document
					.getElementById("mesInicioRepresentantePrimerCotitularTransmitente"), document
					.getElementById("anioInicioRepresentantePrimerCotitularTransmitente"), false)) {
				alert("Fecha de Inicio del Representante del primer cotitular del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentantePrimerCotitularTransmitente").value != ""
			|| document.getElementById("mesFinRepresentantePrimerCotitularTransmitente").value != ""
				|| document.getElementById("anioFinRepresentantePrimerCotitularTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentantePrimerCotitularTransmitente"), document
					.getElementById("mesFinRepresentantePrimerCotitularTransmitente"), document
					.getElementById("anioFinRepresentantePrimerCotitularTransmitente"), false)) {
				alert("Fecha de Fin del Representante del primer cotitular del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentantePrimerCotitularTransmitente").value != ""
			&& document.getElementById("mesInicioRepresentantePrimerCotitularTransmitente").value != ""
				&& document.getElementById("anioInicioRepresentantePrimerCotitularTransmitente").value != ""
					&& document.getElementById("diaFinRepresentantePrimerCotitularTransmitente").value != ""
						&& document.getElementById("mesFinRepresentantePrimerCotitularTransmitente").value != ""
							&& document.getElementById("anioFinRepresentantePrimerCotitularTransmitente").value != "") {
			var fInicioRepresentantePrimerCotitularTransmitente = new Date(
					document.getElementById("anioInicioRepresentantePrimerCotitularTransmitente").value,
					parseInt(document
							.getElementById("mesInicioRepresentantePrimerCotitularTransmitente").value) - 1,
							document.getElementById("diaInicioRepresentantePrimerCotitularTransmitente").value)
			.getTime();
			var fFinRepresentantePrimerCotitularTransmitente = new Date(
					document.getElementById("anioFinRepresentantePrimerCotitularTransmitente").value,
					parseInt(document
							.getElementById("mesFinRepresentantePrimerCotitularTransmitente").value) - 1,
							document.getElementById("diaFinRepresentantePrimerCotitularTransmitente").value)
			.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentantePrimerCotitularTransmitente > fFinRepresentantePrimerCotitularTransmitente) {
				alert("La fecha de Inicio del Representante del primer cotitular del Transmitente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentantePrimerCotitularTransmitente > fActual) {
				alert("La fecha de Inicio del Representante del primer cotitular del Transmitente no puede ser mayor que la fecha actual.");
				return false;
			}
		}
		// FIN DE REPRESENTANTE DEL PRIMER COTITULAR

		if (document.getElementById("idNifSegundoCotitularTransmitente").value !="" &&
				document.getElementById("viaSegundoCotitularTransmitente").value != "" &&
				document.getElementById("numeroSegundoCotitularTransmitente").value == "" ){
			alert("Si se introduce direcci\u00f3n del segundo cotitular es obligatorio n\u00famero de via. En caso de no tener poner sn");
			return false;
		}

		// REPRESENTANTE DEL SEGUNDO COTITULAR
		if (document.getElementById("diaInicioRepresentanteSegundoCotitularTransmitente").value != ""
			|| document.getElementById("mesInicioRepresentanteSegundoCotitularTransmitente").value != ""
				|| document.getElementById("anioInicioRepresentanteSegundoCotitularTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteSegundoCotitularTransmitente"), document
					.getElementById("mesInicioRepresentanteSegundoCotitularTransmitente"), document
					.getElementById("anioInicioRepresentanteSegundoCotitularTransmitente"), false)) {
				alert("Fecha de Inicio del Representante del segundo cotitular del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteSegundoCotitularTransmitente").value != ""
			|| document.getElementById("mesFinRepresentanteSegundoCotitularTransmitente").value != ""
				|| document.getElementById("anioFinRepresentanteSegundoCotitularTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteSegundoCotitularTransmitente"), document
					.getElementById("mesFinRepresentanteSegundoCotitularTransmitente"), document
					.getElementById("anioFinRepresentanteSegundoCotitularTransmitente"), false)) {
				alert("Fecha de Fin del Representante del segundo cotitular del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteSegundoCotitularTransmitente").value != ""
			&& document.getElementById("mesInicioRepresentanteSegundoCotitularTransmitente").value != ""
				&& document.getElementById("anioInicioRepresentanteSegundoCotitularTransmitente").value != ""
					&& document.getElementById("diaFinRepresentanteSegundoCotitularTransmitente").value != ""
						&& document.getElementById("mesFinRepresentanteSegundoCotitularTransmitente").value != ""
							&& document.getElementById("anioFinRepresentanteSegundoCotitularTransmitente").value != "") {
			var fInicioRepresentanteSegundoCotitularTransmitente = new Date(
					document.getElementById("anioInicioRepresentanteSegundoCotitularTransmitente").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteSegundoCotitularTransmitente").value) - 1,
							document.getElementById("diaInicioRepresentanteSegundoCotitularTransmitente").value)
			.getTime();
			var fFinRepresentanteSegundoCotitularTransmitente = new Date(
					document.getElementById("anioFinRepresentanteSegundoCotitularTransmitente").value,
					parseInt(document
							.getElementById("mesFinRepresentanteSegundoCotitularTransmitente").value) - 1,
							document.getElementById("diaFinRepresentanteSegundoCotitularTransmitente").value)
			.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteSegundoCotitularTransmitente > fFinRepresentanteSegundoCotitularTransmitente) {
				alert("La fecha de Inicio del Representante del segundo cotitular del Transmitente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteSegundoCotitularTransmitente > fActual) {
				alert("La fecha de Inicio del Representante del segundo cotitular del Transmitente no puede ser mayor que la fecha actual.");
				return false;
			}
		}
		// FIN DE REPRESENTANTE DEL SEGUNDO COTITULAR

		// Pestania Presentador
		if (document.getElementById("idNifPresentador").value != "") {
			if (!validaNIF(document.getElementById("idNifPresentador"))) {
				alert("NIF/CIF de Presentador no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("diaNacPresentador").value != ""
				|| document.getElementById("mesNacPresentador").value != ""
				|| document.getElementById("anioNacPresentador").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacPresentador"),
					document.getElementById("mesNacPresentador"), document
							.getElementById("anioNacPresentador"), false)) {
				alert("Fecha de Nacimiento del Presentador no v\u00e1lida.");
				return false;
			}
		}

		// Pestania Compraventa
		if (document.getElementById("idNifPoseedor").value != "") {
			if (!validaNIF(document.getElementById("idNifPoseedor"))) {
				alert("NIF/CIF de Poseedor no v\u00e1lido.");
				return false;
			}
			if (document.getElementById("viaPoseedor").value != ""
					&& document.getElementById("numeroPoseedor").value == "") {
				document.getElementById("numeroPoseedor").value = "sn";
			}
			// Si el tipo de transferencia es ENT - ENTREGA COMPRAVENTA:
		} else if (document.getElementById("tipoTransferenciaId").value == 5) { // Mantis
																				// 1551:
																				// Cambios
																				// CTIT
																				// (se
																				// cambia
																				// 3
																				// por
																				// 5)
			alert("Ha seleccionado el tipo de transferencia Interviene Compraventa en los datos del tr\u00e1mite. Debe introducir los datos del Poseedor.");
		}

		if (document.getElementById("diaNacPoseedor").value != ""
				|| document.getElementById("mesNacPoseedor").value != ""
				|| document.getElementById("anioNacPoseedor").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacPoseedor"),
					document.getElementById("mesNacPoseedor"), document
							.getElementById("anioNacPoseedor"), false)) {
				alert("Fecha de Nacimiento del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentantePoseedor").value != ""
				|| document.getElementById("mesInicioRepresentantePoseedor").value != ""
				|| document.getElementById("anioInicioRepresentantePoseedor").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentantePoseedor"), document
					.getElementById("mesInicioRepresentantePoseedor"), document
					.getElementById("anioInicioRepresentantePoseedor"), false)) {
				alert("Fecha de Inicio del Representante del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentantePoseedor").value != ""
				|| document.getElementById("mesFinRepresentantePoseedor").value != ""
				|| document.getElementById("anioFinRepresentantePoseedor").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentantePoseedor"), document
					.getElementById("mesFinRepresentantePoseedor"), document
					.getElementById("anioFinRepresentantePoseedor"), false)) {
				alert("Fecha de Fin del Representante del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentantePoseedor").value != ""
				&& document.getElementById("mesInicioRepresentantePoseedor").value != ""
				&& document.getElementById("anioInicioRepresentantePoseedor").value != ""
				&& document.getElementById("diaFinRepresentantePoseedor").value != ""
				&& document.getElementById("mesFinRepresentantePoseedor").value != ""
				&& document.getElementById("anioFinRepresentantePoseedor").value != "") {
			var fInicioRepresentantePoseedor = new Date(
					document.getElementById("anioInicioRepresentantePoseedor").value,
					parseInt(document
							.getElementById("mesInicioRepresentantePoseedor").value) - 1,
					document.getElementById("diaInicioRepresentantePoseedor").value)
					.getTime();
			var fFinRepresentantePoseedor = new Date(
					document.getElementById("anioFinRepresentantePoseedor").value,
					parseInt(document.getElementById("mesFinRepresentantePoseedor").value) - 1,
					document.getElementById("diaFinRepresentantePoseedor").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentantePoseedor > fFinRepresentantePoseedor) {
				alert("La fecha de Inicio del Representante del Poseedor no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentantePoseedor > fActual) {
				alert("La fecha de Inicio del Representante del Poseedor no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		// Pestania Veh\u00edculo
		if (document.getElementById("diaMatriculacion").value != ""
				|| document.getElementById("mesMatriculacion").value != ""
				|| document.getElementById("anioMatriculacion").value != "") {
			if (!validaInputFecha(document.getElementById("diaMatriculacion"),
					document.getElementById("mesMatriculacion"), document
							.getElementById("anioMatriculacion"), false)) {
				alert("Fecha de Matriculaci\u00f3n del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInspeccion").value != ""
				|| document.getElementById("mesInspeccion").value != ""
				|| document.getElementById("anioInspeccion").value != "") {
			if (!validaInputFecha(document.getElementById("diaInspeccion"),
					document.getElementById("mesInspeccion"), document
							.getElementById("anioInspeccion"), false)) {
				alert("Fecha de ITV del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("viaVehiculo").value != ""
				&& document.getElementById("numeroVehiculo").value == "") {
			document.getElementById("numeroVehiculo").value = "sn";
		}

		// Validaciones Servicio.

		if (document.getElementById("idTipoVehiculo").value != "0A"
				&& document.getElementById("idTipoVehiculo").value != "7E"
				&& document.getElementById("idServicioTraficoAnterior").value == "A05") {
			alert("Este tipo de veh\u00edculo no puede tener asignado ese servicio anterior");
			return false;

		} else if (document.getElementById("idTipoVehiculo").value != "0A"
				&& document.getElementById("idTipoVehiculo").value != "7E"
				&& document.getElementById("idServicioTrafico").value == "A05") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Tipo 02/04
		if ((document.getElementById("idTipoVehiculo").value == "90"
				|| document.getElementById("idTipoVehiculo").value == "91" || document
				.getElementById("idTipoVehiculo").value == "92")
				&& (document.getElementById("idServicioTrafico").value == "A02" || document
						.getElementById("idServicioTrafico").value == "A04")) {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// JRG En la ultima actualizacion de CTIT del 06/02/2013 se infroma que los
		// siguientes tipos de vehiculos no pueden modificar su servicio.
		if (document.getElementById("idServicioTraficoAnterior").value == "A03"
				|| document.getElementById("idServicioTraficoAnterior").value == "A05"
				|| document.getElementById("idServicioTraficoAnterior").value == "A10"
				|| document.getElementById("idServicioTraficoAnterior").value == "A11"
				|| document.getElementById("idServicioTraficoAnterior").value == "A12"
				|| document.getElementById("idServicioTraficoAnterior").value == "A13"
				|| document.getElementById("idServicioTraficoAnterior").value == "A14"
				|| document.getElementById("idServicioTraficoAnterior").value == "A15"
				|| document.getElementById("idServicioTraficoAnterior").value == "A16"
				|| document.getElementById("idServicioTraficoAnterior").value == "A08"
				|| document.getElementById("idServicioTraficoAnterior").value == "B17") {
			alert("No esta permitido cambiar el servicio desde el servicio "
					+ document.getElementById("idServicioTraficoAnterior").value);
			return false;
		}

		// Tipo Ambulancia
		if (document.getElementById("idTipoVehiculo").value != "22"
				&& document.getElementById("idServicioTrafico").value == "A07") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Tipo Funerario
		if (document.getElementById("idTipoVehiculo").value != "23"
				&& document.getElementById("idServicioTrafico").value == "A08") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Acreditar actividad economica
		// Mantis 41789 (recuperado tras petición del CAU en enero del 2023)
		//if (document.getElementById("idServicioTrafico").value == "A18") {
		//	alert("Deber\u00e1 informar el IAE");
		//	return false;
		//}

		// Vehiculo Agricola
		// Mantis 0002862. 12/12/2012. Gilberto.
		// Si esta activado el check de Vehiculo Agricola se debe rellenar el Cema.
		if (document.getElementById("vehiculoAgricola").checked == true
				&& document.getElementById("idCema").value == "") {
			alert("Al tratarse de un veh\u00edculo agr\u00edcola deber\u00e1 informar el CEMA");
			return false;
		}

		if (document.getElementById("diaITV").value != ""
				|| document.getElementById("mesITV").value != ""
				|| document.getElementById("anioITV").value != "") {
			if (!validaInputFecha(document.getElementById("diaITV"), document
					.getElementById("mesITV"), document.getElementById("anioITV"),
					false)) {
				alert("Fecha de caducidad de ITV del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioExencion").value != ""
				|| document.getElementById("mesInicioExencion").value != ""
				|| document.getElementById("anioInicioExencion").value != "") {
			if (!validaInputFecha(document.getElementById("diaInicioExencion"),
					document.getElementById("mesInicioExencion"), document
							.getElementById("anioInicioExencion"), false)) {
				alert("Fecha de Financiera de Disposici\u00f3n del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		// Pestania Renting
		if (document.getElementById("idNifArrendatario").value != "") {
			if (!validaNIF(document.getElementById("idNifArrendatario"))) {
				alert("NIF/CIF de Arrendatario no v\u00e1lido.");
				return false;
			}
		} else if (document.getElementById("rentingId").checked) {
			alert("Ha seleccionado Renting en los datos del tr\u00e1mite. Debe introducir los datos del Arrendatario.");
			return false;
		}

		if (document.getElementById("rentingId").checked
				&& document.getElementById("viaArrendatario").value != ""
				&& document.getElementById("numeroArrendatario").value == "") {
			document.getElementById("numeroArrendatario").value = "sn";
		}

		if (document.getElementById("diaNacArrendatario").value != ""
				|| document.getElementById("mesNacArrendatario").value != ""
				|| document.getElementById("anioNacArrendatario").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacArrendatario"),
					document.getElementById("mesNacArrendatario"), document
							.getElementById("anioNacArrendatario"), false)) {
				alert("Fecha de Nacimiento del Arrendatario no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioArrendatario").value != ""
				|| document.getElementById("mesInicioArrendatario").value != ""
				|| document.getElementById("anioInicioArrendatario").value != "") {
			if (!validaInputFecha(document.getElementById("diaInicioArrendatario"),
					document.getElementById("mesInicioArrendatario"), document
							.getElementById("anioInicioArrendatario"), false)) {
				alert("Fecha de Inicio del Arrendatario no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinArrendatario").value != ""
				|| document.getElementById("mesFinArrendatario").value != ""
				|| document.getElementById("anioFinArrendatario").value != "") {
			if (!validaInputFecha(document.getElementById("diaFinArrendatario"),
					document.getElementById("mesFinArrendatario"), document
							.getElementById("anioFinArrendatario"), false)) {
				alert("Fecha de Fin del Arrendatario no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioArrendatario").value != ""
				&& document.getElementById("mesInicioArrendatario").value != ""
				&& document.getElementById("anioInicioArrendatario").value != ""
				&& document.getElementById("diaFinArrendatario").value != ""
				&& document.getElementById("mesFinArrendatario").value != ""
				&& document.getElementById("anioFinArrendatario").value != "") {
			var fInicioArrendatario = new Date(
					document.getElementById("anioInicioArrendatario").value,
					parseInt(document.getElementById("mesInicioArrendatario").value) - 1,
					document.getElementById("diaInicioArrendatario").value)
					.getTime();
			var fFinArrendatario = new Date(document
					.getElementById("anioFinArrendatario").value, parseInt(document
					.getElementById("mesFinArrendatario").value) - 1, document
					.getElementById("diaFinArrendatario").value).getTime();
			var fActual = new Date().getTime();

			if (fInicioArrendatario > fFinArrendatario) {
				alert("La fecha de Inicio del Arrendatario no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioArrendatario > fActual) {
				alert("La fecha de Inicio del Arrendatario no puede ser mayor que la fecha actual.");
				return false;
			}

			if (fInicioArrendatario == fFinArrendatario) {
				if (document.getElementById("idHoraInicioArrendatario").value != ""
						&& document.getElementById("idHoraFinArrendatario").value == "") {
					alert("Si introduce las fechas de inicio y fin del Arrendatario y la hora de inicio, debe introducir tambien la hora de fin.");
					return false;
				} else if (document.getElementById("idHoraFinArrendatario").value != ""
						&& document.getElementById("idHoraInicioArrendatario").value == "") {
					alert("Si introduce las fechas de inicio y fin del Arrendatario y la hora de fin, debe introducir tambien la hora de inicio.");
					return false;
				}
			}
		}

		if (document.getElementById("idHoraInicioArrendatario").value != "") {
			if (document.getElementById("idHoraInicioArrendatario").value.length < 5
					|| document.getElementById("idHoraInicioArrendatario").value
							.indexOf(":") == -1) {
				alert("La hora de Inicio del Arrendatario debe cumplir el formato HH:MM.");
				return false;
			} else {
				var horaMinuto = document
						.getElementById("idHoraInicioArrendatario").value
						.split(":");
				var hora = horaMinuto[0];
				var minuto = horaMinuto[1];
				if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
					alert("La hora de Inicio del Arrendatario debe estar entre las 00:00 y las 23:59");
					return false;
				}
			}
		}

		if (document.getElementById("idHoraFinArrendatario").value != "") {
			if (document.getElementById("idHoraFinArrendatario").value.length < 5
					|| document.getElementById("idHoraFinArrendatario").value
							.indexOf(":") == -1) {
				alert("La hora de Fin del Arrendatario debe cumplir el formato HH:MM.");
				return false;
			} else {
				var horaMinuto = document.getElementById("idHoraFinArrendatario").value
						.split(":");
				var hora = horaMinuto[0];
				var minuto = horaMinuto[1];
				if (hora < 0 || hora > 23 || minuto < 0 || minuto > 59) {
					alert("La hora de Fin del Arrendatario debe estar entre las 00:00 y las 23:59");
					return false;
				}
			}
		}

		if (document.getElementById("diaInicioArrendatario").value != ""
				&& document.getElementById("mesInicioArrendatario").value != ""
				&& document.getElementById("anioInicioArrendatario").value != ""
				&& document.getElementById("diaFinArrendatario").value != ""
				&& document.getElementById("mesFinArrendatario").value != ""
				&& document.getElementById("anioFinArrendatario").value != ""
				&& document.getElementById("idHoraInicioArrendatario").value != ""
				&& document.getElementById("idHoraFinArrendatario").value != "") {
			var fInicioArrendatario = new Date(
					document.getElementById("anioInicioArrendatario").value,
					parseInt(document.getElementById("mesInicioArrendatario").value) - 1,
					document.getElementById("diaInicioArrendatario").value)
					.getTime();
			var fFinArrendatario = new Date(document
					.getElementById("anioFinArrendatario").value, parseInt(document
					.getElementById("mesFinArrendatario").value) - 1, document
					.getElementById("diaFinArrendatario").value).getTime();
			var horaMinutoInicio = document
					.getElementById("idHoraInicioArrendatario").value.split(":");
			var horaInicio = horaMinutoInicio[0];
			var minutoInicio = horaMinutoInicio[1];
			var horaMinutoFin = document.getElementById("idHoraFinArrendatario").value
					.split(":");
			var horaFin = horaMinutoFin[0];
			var minutoFin = horaMinutoFin[1];
			if (fInicioArrendatario == fFinArrendatario) {
				if (horaInicio > horaFin
						|| (horaInicio == horaFin && minutoInicio > minutoFin)) {
					alert("Si las fechas de inicio y fin del Arrendatario son iguales, la hora de inicio debe ser menor que la hora de fin");
					return false;
				}
			}
		}

		if (document.getElementById("diaInicioRepresentanteArrendatario").value != ""
				|| document.getElementById("mesInicioRepresentanteArrendatario").value != ""
				|| document.getElementById("anioInicioRepresentanteArrendatario").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteArrendatario"), document
					.getElementById("mesInicioRepresentanteArrendatario"), document
					.getElementById("anioInicioRepresentanteArrendatario"), false)) {
				alert("Fecha de Inicio del Representante del Arrendatario no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteArrendatario").value != ""
				|| document.getElementById("mesFinRepresentanteArrendatario").value != ""
				|| document.getElementById("anioFinRepresentanteArrendatario").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteArrendatario"), document
					.getElementById("mesFinRepresentanteArrendatario"), document
					.getElementById("anioFinRepresentanteArrendatario"), false)) {
				alert("Fecha de Fin del Representante del Arrendatario no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteArrendatario").value != ""
				&& document.getElementById("mesInicioRepresentanteArrendatario").value != ""
				&& document.getElementById("anioInicioRepresentanteArrendatario").value != ""
				&& document.getElementById("diaFinRepresentanteArrendatario").value != ""
				&& document.getElementById("mesFinRepresentanteArrendatario").value != ""
				&& document.getElementById("anioFinRepresentanteArrendatario").value != "") {
			var fInicioRepresentanteArrendatario = new Date(
					document.getElementById("anioInicioRepresentanteArrendatario").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteArrendatario").value) - 1,
					document.getElementById("diaInicioRepresentanteArrendatario").value)
					.getTime();
			var fFinRepresentanteArrendatario = new Date(
					document.getElementById("anioFinRepresentanteArrendatario").value,
					parseInt(document
							.getElementById("mesFinRepresentanteArrendatario").value) - 1,
					document.getElementById("diaFinRepresentanteArrendatario").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteArrendatario > fFinRepresentanteArrendatario) {
				alert("La fecha de Inicio del Representante del Arrendatario no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteArrendatario > fActual) {
				alert("La fecha de Inicio del Representante del Arrendatario no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		/*
		 * Mantis 0002977: Notifificaciones - NOTIFICATION CTIT - Gilberto Pedroso
		 */
		/*
		 * Mantis 0003789: Modo Adjudicacion : ENTREGA COMPRAVENTA
		 */
		if ((document.getElementById("tipoTransferenciaId").value != "4")
				&& (document.getElementById("tipoTransferenciaId").value != "5")) {
			if (document.getElementById("cetItp").value == "") {
				if (!confirm("CET introducido no v\u00e1lido. Se rellenar\u00e1 con 00000000.\n\u00bfDesea continuar?")) {
					return false;
				}
			}
		}

		if (document.getElementById('exentoIedtm').checked) {
			if (document.getElementById('idReduccionNoSujeccion05').value == "-1") {
				alert("Si marca Exento Iedtm, debe indicar un motivo de Exenci\u00f3n 05");
				return false;
			}
		}

		if (document.getElementById('noSujetoIedtm').checked) {
			if (document.getElementById('idNoSujeccionOExencion06').value == "-1") {
				alert("Si marca No Sujeto Iedtm, debe indicar un motivo de No Sujecci\u00f3n 06");
				return false;
			}
		}

		// Se habilitan los combos deshabilitados para recoger su valor
		habilitarCamposDeshabilitadosTransmision();

		// Despues de validar esos campos llamamos al metodo guardar del action y se
		// hace el submit.
		// documen.formData.action="<metodoEnElAction><nombreAction-"Action">.action
		// Mantis 25415
		document.getElementById('valorReal').value = document.getElementById('valorReal').value.replace(",", ".");
		document.formData.action = "guardarTramiteTransmisionAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		loadingGuardarTransmision();
		return true;
	}

	/** *********** GUARDAR ALTA TRAMITE TRANSMISION ACTUAL *********** */
	// GUARDAR ALTAS NUEVAS DE TRAMITES DE TRANSMISION DE TRAFICO SIN CTIT
	function guardarAltaTramiteTransmisionActual(pestania) {
		/*
		 * Antes de guardar se comprueban validaciones de que se han seleccionado o
		 * rellenado los campos necesarios. Ademas se validan que el NIF y la fecha
		 * sean validos.
		 */
		// Pestania Tramite

		if ((document.getElementById("modoAdjudicacionId").value != "-1")
				&& (document.getElementById("tipoTransferenciaId").value == "-1")) {
			alert("Ha seleccionado un modo de adjudicaci\u00f3n. Debe seleccionar el modo de transferencia.");
			return false;
		}

		// Pestania Adquiriente
		if (document.getElementById("idNifAdquiriente").value != "") {
			if (!validaNIF(document.getElementById("idNifAdquiriente"))) {
				alert("NIF/CIF de Adquiriente no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("diaNacAdquiriente").value != ""
				|| document.getElementById("mesNacAdquiriente").value != ""
				|| document.getElementById("anioNacAdquiriente").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacAdquiriente"),
					document.getElementById("mesNacAdquiriente"), document
							.getElementById("anioNacAdquiriente"), false)) {
				alert("Fecha de Nacimiento del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("autonomoIdAdquiriente").checked
				&& document.getElementById("codigoIAEAdquiriente").value == "") {
			alert("Debe introducir el C\u00f3digo IAE del Adquiriente si este es aut\u00f3nomo.");
			return false;
		}

		if (document.getElementById("diaInicioRepresentanteAdquiriente").value != ""
				|| document.getElementById("mesInicioRepresentanteAdquiriente").value != ""
				|| document.getElementById("anioInicioRepresentanteAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteAdquiriente"), document
					.getElementById("mesInicioRepresentanteAdquiriente"), document
					.getElementById("anioInicioRepresentanteAdquiriente"), false)) {
				alert("Fecha de Inicio del Representante del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteAdquiriente").value != ""
				|| document.getElementById("mesFinRepresentanteAdquiriente").value != ""
				|| document.getElementById("anioFinRepresentanteAdquiriente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteAdquiriente"), document
					.getElementById("mesFinRepresentanteAdquiriente"), document
					.getElementById("anioFinRepresentanteAdquiriente"), false)) {
				alert("Fecha de Fin del Representante del Adquiriente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("mesInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("anioInicioRepresentanteAdquiriente").value != ""
				&& document.getElementById("diaFinRepresentanteAdquiriente").value != ""
				&& document.getElementById("mesFinRepresentanteAdquiriente").value != ""
				&& document.getElementById("anioFinRepresentanteAdquiriente").value != "") {
			var fInicioRepresentanteAdquiriente = new Date(
					document.getElementById("anioInicioRepresentanteAdquiriente").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteAdquiriente").value) - 1,
					document.getElementById("diaInicioRepresentanteAdquiriente").value)
					.getTime();
			var fFinRepresentanteAdquiriente = new Date(
					document.getElementById("anioFinRepresentanteAdquiriente").value,
					parseInt(document
							.getElementById("mesFinRepresentanteAdquiriente").value) - 1,
					document.getElementById("diaFinRepresentanteAdquiriente").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteAdquiriente > fFinRepresentanteAdquiriente) {
				alert("La fecha de Inicio del Representante del Adquiriente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteAdquiriente > fActual) {
				alert("La fecha de Inicio del Representante del Adquiriente no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		// Pestania Transmitente
		if (document.getElementById("idNifTransmitente").value != "") {
			if (!validaNIF(document.getElementById("idNifTransmitente"))) {
				alert("NIF/CIF de Transmitente no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("diaNacTransmitente").value != ""
				|| document.getElementById("mesNacTransmitente").value != ""
				|| document.getElementById("anioNacTransmitente").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacTransmitente"),
					document.getElementById("mesNacTransmitente"), document
							.getElementById("anioNacTransmitente"), false)) {
				alert("Fecha de Nacimiento del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteTransmitente").value != ""
				|| document.getElementById("mesInicioRepresentanteTransmitente").value != ""
				|| document.getElementById("anioInicioRepresentanteTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentanteTransmitente"), document
					.getElementById("mesInicioRepresentanteTransmitente"), document
					.getElementById("anioInicioRepresentanteTransmitente"), false)) {
				alert("Fecha de Inicio del Representante del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentanteTransmitente").value != ""
				|| document.getElementById("mesFinRepresentanteTransmitente").value != ""
				|| document.getElementById("anioFinRepresentanteTransmitente").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentanteTransmitente"), document
					.getElementById("mesFinRepresentanteTransmitente"), document
					.getElementById("anioFinRepresentanteTransmitente"), false)) {
				alert("Fecha de Fin del Representante del Transmitente no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("mesInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("anioInicioRepresentanteTransmitente").value != ""
				&& document.getElementById("diaFinRepresentanteTransmitente").value != ""
				&& document.getElementById("mesFinRepresentanteTransmitente").value != ""
				&& document.getElementById("anioFinRepresentanteTransmitente").value != "") {
			var fInicioRepresentanteTransmitente = new Date(
					document.getElementById("anioInicioRepresentanteTransmitente").value,
					parseInt(document
							.getElementById("mesInicioRepresentanteTransmitente").value) - 1,
					document.getElementById("diaInicioRepresentanteTransmitente").value)
					.getTime();
			var fFinRepresentanteTransmitente = new Date(
					document.getElementById("anioFinRepresentanteTransmitente").value,
					parseInt(document
							.getElementById("mesFinRepresentanteTransmitente").value) - 1,
					document.getElementById("diaFinRepresentanteTransmitente").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentanteTransmitente > fFinRepresentanteTransmitente) {
				alert("La fecha de Inicio del Representante del Transmitente no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentanteTransmitente > fActual) {
				alert("La fecha de Inicio del Representante del Transmitente no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		// Pestania Presentador
		if (document.getElementById("idNifPresentador").value != "") {
			if (!validaNIF(document.getElementById("idNifPresentador"))) {
				alert("NIF/CIF de Presentador no v\u00e1lido.");
				return false;
			}
		}

		if (document.getElementById("diaNacPresentador").value != ""
				|| document.getElementById("mesNacPresentador").value != ""
				|| document.getElementById("anioNacPresentador").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacPresentador"),
					document.getElementById("mesNacPresentador"), document
							.getElementById("anioNacPresentador"), false)) {
				alert("Fecha de Nacimiento del Presentador no v\u00e1lida.");
				return false;
			}
		}

		// Pestania Compraventa
		if (document.getElementById("idNifPoseedor").value != "") {
			if (!validaNIF(document.getElementById("idNifPoseedor"))) {
				alert("NIF/CIF de Poseedor no v\u00e1lido.");
				return false;
			}
			// Si el tipo de transferencia es ENT - ENTREGA COMPRAVENTA:
		} else if (document.getElementById("tipoTransferenciaId").value == 5) { // Mantis
																				// 1551:
																				// Cambios
																				// CTIT
																				// (se
																				// cambia
																				// 3
																				// por
																				// 5)
			alert("Ha seleccionado el tipo de transferencia Interviene Compraventa en los datos del tr\u00e1mite. Debe introducir los datos del Poseedor.");
		}

		if (document.getElementById("diaNacPoseedor").value != ""
				|| document.getElementById("mesNacPoseedor").value != ""
				|| document.getElementById("anioNacPoseedor").value != "") {
			if (!validaInputFecha(document.getElementById("diaNacPoseedor"),
					document.getElementById("mesNacPoseedor"), document
							.getElementById("anioNacPoseedor"), false)) {
				alert("Fecha de Nacimiento del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentantePoseedor").value != ""
				|| document.getElementById("mesInicioRepresentantePoseedor").value != ""
				|| document.getElementById("anioInicioRepresentantePoseedor").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaInicioRepresentantePoseedor"), document
					.getElementById("mesInicioRepresentantePoseedor"), document
					.getElementById("anioInicioRepresentantePoseedor"), false)) {
				alert("Fecha de Inicio del Representante del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaFinRepresentantePoseedor").value != ""
				|| document.getElementById("mesFinRepresentantePoseedor").value != ""
				|| document.getElementById("anioFinRepresentantePoseedor").value != "") {
			if (!validaInputFecha(document
					.getElementById("diaFinRepresentantePoseedor"), document
					.getElementById("mesFinRepresentantePoseedor"), document
					.getElementById("anioFinRepresentantePoseedor"), false)) {
				alert("Fecha de Fin del Representante del Poseedor no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRepresentantePoseedor").value != ""
				&& document.getElementById("mesInicioRepresentantePoseedor").value != ""
				&& document.getElementById("anioInicioRepresentantePoseedor").value != ""
				&& document.getElementById("diaFinRepresentantePoseedor").value != ""
				&& document.getElementById("mesFinRepresentantePoseedor").value != ""
				&& document.getElementById("anioFinRepresentantePoseedor").value != "") {
			var fInicioRepresentantePoseedor = new Date(
					document.getElementById("anioInicioRepresentantePoseedor").value,
					parseInt(document
							.getElementById("mesInicioRepresentantePoseedor").value) - 1,
					document.getElementById("diaInicioRepresentantePoseedor").value)
					.getTime();
			var fFinRepresentantePoseedor = new Date(
					document.getElementById("anioFinRepresentantePoseedor").value,
					parseInt(document.getElementById("mesFinRepresentantePoseedor").value) - 1,
					document.getElementById("diaFinRepresentantePoseedor").value)
					.getTime();
			var fActual = new Date().getTime();

			if (fInicioRepresentantePoseedor > fFinRepresentantePoseedor) {
				alert("La fecha de Inicio del Representante del Poseedor no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioRepresentantePoseedor > fActual) {
				alert("La fecha de Inicio del Representante del Poseedor no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		// Pestania Veh\u00edculo
		if (document.getElementById("diaMatriculacion").value != ""
				|| document.getElementById("mesMatriculacion").value != ""
				|| document.getElementById("anioMatriculacion").value != "") {
			if (!validaInputFecha(document.getElementById("diaMatriculacion"),
					document.getElementById("mesMatriculacion"), document
							.getElementById("anioMatriculacion"), false)) {
				alert("Fecha de Matriculaci\u00f3n del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		// Validaciones Servicio.

		if (document.getElementById("idTipoVehiculo").value != "0A"
				&& document.getElementById("idTipoVehiculo").value != "7E"
				&& document.getElementById("idServicioTrafico").value == "A05") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}
		// Tipo 02/04
		if ((document.getElementById("idTipoVehiculo").value == "90"
				|| document.getElementById("idTipoVehiculo").value == "91" || document
				.getElementById("idTipoVehiculo").value == "92")
				&& (document.getElementById("idServicioTrafico").value == "A02" || document
						.getElementById("idServicioTrafico").value == "A04")) {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Tipo Ambulancia
		if (document.getElementById("idTipoVehiculo").value != "22"
				&& document.getElementById("idServicioTrafico").value == "A07") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Tipo Funerario
		if (document.getElementById("idTipoVehiculo").value != "23"
				&& document.getElementById("idServicioTrafico").value == "A08") {
			alert("A este tipo de veh\u00edculo no se le puede asignar este nuevo servicio");
			return false;
		}

		// Acreditar actividad economica
		// Mantis 41789 (recuperado tras petición del CAU en enero del 2023)
		//if (document.getElementById("idServicioTrafico").value == "A18") {
		//	alert("Deber\u00e1 informar el IAE");
		//	return false;
		//}

		if (document.getElementById("diaInspeccion").value != ""
				|| document.getElementById("mesInspeccion").value != ""
				|| document.getElementById("anioInspeccion").value != "") {
			if (!validaInputFecha(document.getElementById("diaInspeccion"),
					document.getElementById("mesInspeccion"), document
							.getElementById("anioInspeccion"), false)) {
				alert("Fecha de ITV del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaITV").value != ""
				|| document.getElementById("mesITV").value != ""
				|| document.getElementById("anioITV").value != "") {
			if (!validaInputFecha(document.getElementById("diaITV"), document
					.getElementById("mesITV"), document.getElementById("anioITV"),
					false)) {
				alert("Fecha de caducidad de ITV del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("diaIEDTM").value != ""
				|| document.getElementById("mesIEDTM").value != ""
				|| document.getElementById("anioIEDTM").value != "") {
			if (!validaInputFecha(document.getElementById("diaIEDTM"), document
					.getElementById("mesIEDTM"), document
					.getElementById("anioIEDTM"), false)) {
				alert("Fecha de Financiera de Disposici\u00f3n del Veh\u00edculo no v\u00e1lida.");
				return false;
			}
		}

		if (document.getElementById("cetItp").value == "") {
			if (!confirm("CET introducido no v\u00e1lido. Se rellenar\u00e1 con 00000000.\n\u00bfDesea continuar?")) {
				return false;
			}
		}

		// Se habilitan los combos deshabilitados para recoger su valor
		habilitarCamposDeshabilitadosTransmisionActual();

		// Despues de validar esos campos llamamos al metodo guardar del action y se
		// hace el submit.
		// documen.formData.action="<metodoEnElAction><nombreAction-"Action">.action
		document.formData.action = "guardarTramiteTransmisionAltasTramiteTransmisionActual.action#"
				+ pestania;
		document.formData.submit();
		loadingGuardarTransmision();
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

		document.getElementById('valorDeclarado620').disabled=false
		document.getElementById('baseImponible620').disabled=false
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

	function descargarDocBaseTransmision(pestania){
		document.formData.action = "descargarDocBaseAltasTramiteTransmision.action#"
			+ pestania;
		document.formData.submit();
		return true;
	}

	function loadingTransmision(idBoton){
		document.getElementById("bGuardar1").disabled = "true";
		document.getElementById("bGuardar1").style.color = "#6E6E6E";
		document.getElementById("bGuardar2").disabled = "true";
		document.getElementById("bGuardar2").style.color = "#6E6E6E";
		document.getElementById("bGuardar3").disabled = "true";
		document.getElementById("bGuardar3").style.color = "#6E6E6E";
		document.getElementById("bGuardar4").disabled = "true";
		document.getElementById("bGuardar4").style.color = "#6E6E6E";
		document.getElementById("bGuardar5").disabled = "true";
		document.getElementById("bGuardar5").style.color = "#6E6E6E";
		if (document.getElementById("bGuardar6")) {
			document.getElementById("bGuardar6").disabled = "true";
			document.getElementById("bGuardar6").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGuardar7")) {
			document.getElementById("bGuardar7").disabled = "true";
			document.getElementById("bGuardar7").style.color = "#6E6E6E";
		}
		document.getElementById("bGuardar8").disabled = "true";
		document.getElementById("bGuardar8").style.color = "#6E6E6E";
		document.getElementById("bGuardar9").disabled = "true";
		document.getElementById("bGuardar9").style.color = "#6E6E6E";
		document.getElementById("bGuardar10").disabled = "true";
		document.getElementById("bGuardar10").style.color = "#6E6E6E";
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
		if (document.getElementById("bValidar620")) {
			document.getElementById("bValidar620").disabled = "true";
			document.getElementById("bValidar620").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGenerarXML")) {
			document.getElementById("bGenerarXML").disabled = "true";
			document.getElementById("bGenerarXML").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonGenerarJustificantesPro")) {
			document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
			document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		}
		$("#"+idBoton).val("Procesando");
		document.getElementById("loadingImage1").style.display = "block";
		document.getElementById("loadingImage2").style.display = "block";
		document.getElementById("loadingImage3").style.display = "block";
		document.getElementById("loadingImage4").style.display = "block";
		document.getElementById("loadingImage5").style.display = "block";
		if (document.getElementById("loadingImage6")) {
			document.getElementById("loadingImage6").style.display = "block";
		}
		if (document.getElementById("loadingImage7")) {
			document.getElementById("loadingImage7").style.display = "block";
		}
		document.getElementById("loadingImage8").style.display = "block";
		document.getElementById("loadingImage9").style.display = "block";
		document.getElementById("loadingImage10").style.display = "block";
	}

	function loadingGuardarTransmision() {
		document.getElementById("bGuardar1").disabled = "true";
		document.getElementById("bGuardar1").style.color = "#6E6E6E";
		document.getElementById("bGuardar2").disabled = "true";
		document.getElementById("bGuardar2").style.color = "#6E6E6E";
		document.getElementById("bGuardar3").disabled = "true";
		document.getElementById("bGuardar3").style.color = "#6E6E6E";
		document.getElementById("bGuardar4").disabled = "true";
		document.getElementById("bGuardar4").style.color = "#6E6E6E";
		document.getElementById("bGuardar5").disabled = "true";
		document.getElementById("bGuardar5").style.color = "#6E6E6E";
		if (document.getElementById("bGuardar6")) {
			document.getElementById("bGuardar6").disabled = "true";
			document.getElementById("bGuardar6").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGuardar7")) {
			document.getElementById("bGuardar7").disabled = "true";
			document.getElementById("bGuardar7").style.color = "#6E6E6E";
		}
		document.getElementById("bGuardar8").disabled = "true";
		document.getElementById("bGuardar8").style.color = "#6E6E6E";
		document.getElementById("bGuardar9").disabled = "true";
		document.getElementById("bGuardar9").style.color = "#6E6E6E";
		document.getElementById("bGuardar10").disabled = "true";
		document.getElementById("bGuardar10").style.color = "#6E6E6E";
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
		if (document.getElementById("bValidar620")) {
			document.getElementById("bValidar620").disabled = "true";
			document.getElementById("bValidar620").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGenerarXML")) {
			document.getElementById("bGenerarXML").disabled = "true";
			document.getElementById("bGenerarXML").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonGenerarJustificantesPro")) {
			document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
			document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		}
		document.getElementById("bGuardar1").value = "Procesando";
		document.getElementById("bGuardar2").value = "Procesando";
		document.getElementById("bGuardar3").value = "Procesando";
		document.getElementById("bGuardar4").value = "Procesando";
		document.getElementById("bGuardar5").value = "Procesando";
		if (document.getElementById("bGuardar6")) {
			document.getElementById("bGuardar6").value = "Procesando";
		}
		if (document.getElementById("bGuardar7")) {
			document.getElementById("bGuardar7").value = "Procesando";
		}
		document.getElementById("bGuardar8").value = "Procesando";
		document.getElementById("bGuardar9").value = "Procesando";
		document.getElementById("bGuardar10").value = "Procesando";
		document.getElementById("loadingImage1").style.display = "block";
		document.getElementById("loadingImage2").style.display = "block";
		document.getElementById("loadingImage3").style.display = "block";
		document.getElementById("loadingImage4").style.display = "block";
		document.getElementById("loadingImage5").style.display = "block";
		if (document.getElementById("loadingImage6")) {
			document.getElementById("loadingImage6").style.display = "block";
		}
		if (document.getElementById("loadingImage7")) {
			document.getElementById("loadingImage7").style.display = "block";
		}
		document.getElementById("loadingImage8").style.display = "block";
		document.getElementById("loadingImage9").style.display = "block";
		document.getElementById("loadingImage10").style.display = "block";
	}

	function loadingValidarTransmision() {
		document.getElementById("bGuardar1").disabled = "true";
		document.getElementById("bGuardar1").style.color = "#6E6E6E";
		document.getElementById("bGuardar2").disabled = "true";
		document.getElementById("bGuardar2").style.color = "#6E6E6E";
		document.getElementById("bGuardar3").disabled = "true";
		document.getElementById("bGuardar3").style.color = "#6E6E6E";
		document.getElementById("bGuardar4").disabled = "true";
		document.getElementById("bGuardar4").style.color = "#6E6E6E";
		document.getElementById("bGuardar5").disabled = "true";
		document.getElementById("bGuardar5").style.color = "#6E6E6E";
		if (document.getElementById("bGuardar6")) {
			document.getElementById("bGuardar6").disabled = "true";
			document.getElementById("bGuardar6").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGuardar7")) {
			document.getElementById("bGuardar7").disabled = "true";
			document.getElementById("bGuardar7").style.color = "#6E6E6E";
		}
		document.getElementById("bGuardar8").disabled = "true";
		document.getElementById("bGuardar8").style.color = "#6E6E6E";
		document.getElementById("bGuardar9").disabled = "true";
		document.getElementById("bGuardar9").style.color = "#6E6E6E";
		document.getElementById("bGuardar10").disabled = "true";
		document.getElementById("bGuardar10").style.color = "#6E6E6E";
		document.getElementById("bFinalizar").disabled = "true";
		document.getElementById("bFinalizar").style.color = "#6E6E6E";
		if (document.getElementById("bTramitacionTelematica")) {
			document.getElementById("bTramitacionTelematica").disabled = "true";
			document.getElementById("bTramitacionTelematica").style.color = "#6E6E6E";
		}
		if (document.getElementById("bValidar620")) {
			document.getElementById("bValidar620").disabled = "true";
			document.getElementById("bValidar620").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGenerarXML")) {
			document.getElementById("bGenerarXML").disabled = "true";
			document.getElementById("bGenerarXML").style.color = "#6E6E6E";
		}
		if (document.getElementById("bImprimir")) {
			document.getElementById("bImprimir").disabled = "true";
			document.getElementById("bImprimir").style.color = "#6E6E6E";
		}
		document.getElementById("bFinalizar").value = "Procesando";
		if (document.getElementById("bTramitacionTelematica")) {
			document.getElementById("bTramitacionTelematica").value = "Procesando";
		}
		document.getElementById("loadingImage12").style.display = "block";
		if (document.getElementById("idBotonGenerarJustificantesPro")) {
			document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
			document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		}
	}

	function loadingTramitacionTelematica() {
		document.getElementById("bGuardar1").disabled = "true";
		document.getElementById("bGuardar1").style.color = "#6E6E6E";
		document.getElementById("bGuardar2").disabled = "true";
		document.getElementById("bGuardar2").style.color = "#6E6E6E";
		document.getElementById("bGuardar3").disabled = "true";
		document.getElementById("bGuardar3").style.color = "#6E6E6E";
		document.getElementById("bGuardar4").disabled = "true";
		document.getElementById("bGuardar4").style.color = "#6E6E6E";
		document.getElementById("bGuardar5").disabled = "true";
		document.getElementById("bGuardar5").style.color = "#6E6E6E";
		if (document.getElementById("bGuardar6")) {
			document.getElementById("bGuardar6").disabled = "true";
			document.getElementById("bGuardar6").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGuardar7")) {
			document.getElementById("bGuardar7").disabled = "true";
			document.getElementById("bGuardar7").style.color = "#6E6E6E";
		}
		document.getElementById("bGuardar8").disabled = "true";
		document.getElementById("bGuardar8").style.color = "#6E6E6E";
		document.getElementById("bGuardar9").disabled = "true";
		document.getElementById("bGuardar9").style.color = "#6E6E6E";
		document.getElementById("bGuardar10").disabled = "true";
		document.getElementById("bGuardar10").style.color = "#6E6E6E";
		if (document.getElementById("bFinalizar")) {
			document.getElementById("bFinalizar").disabled = "true";
			document.getElementById("bFinalizar").style.color = "#6E6E6E";
		}
		document.getElementById("bTramitacionTelematica").disabled = "true";
		document.getElementById("bTramitacionTelematica").style.color = "#6E6E6E";
		if (document.getElementById("bValidar620")) {
			document.getElementById("bValidar620").disabled = "true";
			document.getElementById("bValidar620").style.color = "#6E6E6E";
		}
		if (document.getElementById("bGenerarXML")) {
			document.getElementById("bGenerarXML").disabled = "true";
			document.getElementById("bGenerarXML").style.color = "#6E6E6E";
		}
		if (document.getElementById("bImprimir")) {
			document.getElementById("bImprimir").disabled = "true";
			document.getElementById("bImprimir").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonGenerarJustificantesPro")) {
			document.getElementById("idBotonGenerarJustificantesPro").disabled = "true";
			document.getElementById("idBotonGenerarJustificantesPro").style.color = "#6E6E6E";
		}
		document.getElementById("bTramitacionTelematica").value = "Procesando";
		document.getElementById("loadingImage14").style.display = "block";
	}

	function tramitarTelematicoTramiteN(colegiado) {
		if (!validarPermiso()) {
			return false;
		}
		var textoAlert = "\n\n\nEl Colegio de Gestores verifica que:"
				+ "\n\n"
				+ "  1. El Gestor Administrativo \""
				+ colegiado
				+ "\" ha comprobado,  en un momento previo al env\u00EDo de esta solicitud, "
				+ "el cumplimiento de los requisitos establecidos en el anexo XIV de RD 2822/1998 de 23 de diciembre "
				+ "por el que se aprueba el Reglamento General de Veh\u00EDculos ( acreditaci\u00F3n de la identidad, titularidad, "
				+ " domicilio,  y liquidaci\u00F3n de las obligaciones fiscales)."
				+ "\n\n"
				+ "  2. Que obra en su poder y conserva mandato suficiente para actuar en nombre del/ de los solicitantes y "
				+ "custodia toda la documentaci\u00F3n que integra este expediente de cambio de titularidad conforme a la normativa "
				+ "archiv\u00EDstica del Ministerio del Interior." + "";
		if (confirm(textoAlert)) {
			tramitacionTelematicaAltaTramiteTransmision('Resumen');
		}
	}

	//Carga la oficina liquidadora en la pestaña del modelo 620 cuando se selecciona el municipio del adquiriente
	function cargarOficinaLiquidadora620(){
		var idMunicipioSeleccionado = document.getElementById("municipioSeleccionadoAdquiriente").value;
		var oficinaLiquidadora = document.getElementById("oficinaLiquidadora");
		//Sin elementos
		if(idMunicipioSeleccionado==""){
			oficinaLiquidadora.length = 0;
			oficinaLiquidadora.options[0] = new Option("-", "-1");
			return;
		}
		url="recuperarOficinaLiquidadoraPorMunicipioTraficoAjax.action?municipioSeleccionado="+idMunicipioSeleccionado;
		var req_generico_oficinaLiquidadora = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_oficinaLiquidadora.onreadystatechange = function() {
			rellenarListaOficinasLiquidadoras(req_generico_oficinaLiquidadora,oficinaLiquidadora,"oficinaLiquidadora");
		}
		req_generico_oficinaLiquidadora.open("POST", url, true);
		req_generico_oficinaLiquidadora.send(null);
	}

	function limpiarDatosVehiculo620(){
		$("#potenciaFiscal620").val("");
		$("#cilindrada620").val("");
		$("#nCilindros620").val("");
		$("#caracteristicas620").val("");
		$("#carburante620").val("-1");

		$("#porcentajeReduccionAnual").val("");
		$("#valorDeclarado620").val("");
		$("#reduccionImporte620").val("")
		$("#baseImponible620").val("");
		$("#cuotaTributaria620").val("");

		if ($("#tipoVehiculo620").val() == "A"|| $("#tipoVehiculo620").val() == "B") {
			//Seteamos los valores por defecto
			$("#marcaCamSeleccionada").val("-1");
			$("#modeloCamSeleccionado").val("-1");
			$("#idMarcaCam").prop("selectedIndex", 0);
			$("#idModeloCam").prop("selectedIndex", 0);
		} else {
			//Seteamos los valores por defecto
			$("#marcaCamSeleccionada").val("");
			$("#modeloCamSeleccionado").val("");
			$("#idMarcaCamText").val("");
			$("#idModeloCamText").val("");

			habilitarCampos620Automaticos();
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

	/**
	 * Método para habilitar/deshabilitar los campos del documento alternativo;
	 * */
	function habilitarDocumentoAlternativo(idCheckDocAlternativo, idDocAlternativo, diaCadAlternativo, mesCadAlternativo, anioCadAlternativo, idFecha, idIndefinido){

		var check = document.getElementById(idCheckDocAlternativo).checked &&
					!document.getElementById(idCheckDocAlternativo).disabled;
		var checkIndefinido = document.getElementById(idIndefinido).checked;
		if (check) {
			if(!checkIndefinido){
				document.getElementById(idDocAlternativo).disabled = false;
				document.getElementById(diaCadAlternativo).disabled = false;
				document.getElementById(mesCadAlternativo).disabled = false;
				document.getElementById(anioCadAlternativo).disabled = false;
				document.getElementById(idFecha).style.display="block";
			}else{
				document.getElementById(idDocAlternativo).disabled = false;
			}
		} else {
			document.getElementById(idDocAlternativo).disabled = true;
			document.getElementById(idDocAlternativo).value = '-1';
			document.getElementById(diaCadAlternativo).value ="";
			document.getElementById(mesCadAlternativo).value ="";
			document.getElementById(anioCadAlternativo).value ="";
			document.getElementById(diaCadAlternativo).disabled = true;
			document.getElementById(mesCadAlternativo).disabled = true;
			document.getElementById(anioCadAlternativo).disabled = true;
			document.getElementById(idFecha).style.display="none";
		}
	}

	/**
	 * Método para comprobar que la fecha del documento es indefinida y deshabilitar los demás.
	 * */
	function documentoIndefinido(idCheckIndefinido, idCheckAlternativo, idDocAlternativo, idDiaDni, idMesDni, idAnioDni, idDiaAlter, idMesAlter, idAnioAlter, idFecha, idFechaAlter){
		var check = document.getElementById(idCheckIndefinido).checked;
		var checkAlter = document.getElementById(idCheckAlternativo).checked;
		if(check){
			document.getElementById(idDiaAlter).disabled=true;
			document.getElementById(idMesAlter).disabled=true;
			document.getElementById(idAnioAlter).disabled=true;
			document.getElementById(idDiaDni).disabled=true;
			document.getElementById(idMesDni).disabled=true;
			document.getElementById(idAnioDni).disabled=true;
			document.getElementById(idFecha).style.display="none";
			if(checkAlter){
				document.getElementById(idDocAlternativo).disabled=false;
				document.getElementById(idFechaAlter).style.display="none";
			}
		}else{
			document.getElementById(idDiaDni).disabled=false;
			document.getElementById(idMesDni).disabled=false;
			document.getElementById(idAnioDni).disabled=false;
			document.getElementById(idFecha).style.display="block";
			if(checkAlter){
				document.getElementById(idDiaAlter).disabled = false;
				document.getElementById(idMesAlter).disabled = false;
				document.getElementById(idAnioAlter).disabled = false;
				document.getElementById(idDocAlternativo).disabled=false;
				document.getElementById(idFechaAlter).style.display="block";
			}
		}
	}

	function habilitarCampos620Automaticos(){
		document.getElementById("potenciaFiscal620").readOnly = true;
		document.getElementById("cilindrada620").readOnly = false;
		document.getElementById("nCilindros620").readOnly = false;
		document.getElementById("caracteristicas620").readOnly = false;
		document.getElementById("valorDeclarado620").readOnly = false;
		document.getElementById("cuotaTributaria620").readOnly = true;
	}
	// Mantis 11712. David Sierra: Se agrega para volver al estado inicial de readonly en que se encuentran
	// estos campos en funcion del valor seleccionado en el combo Tipo de vehículo (A=Turismo, B=Todoterreno)
	function desahabilitarCampos620Automaticos() {
		if (document.getElementById('tipoVehiculo620').value == "A" || document.getElementById('tipoVehiculo620').value == "B") {
			document.getElementById("tipoGravamen620").readOnly = true;
			document.getElementById("porcentajeReduccionAnual").readOnly = true;
			document.getElementById("cilindrada620").readOnly = true;
		}
	}

	function cargarDatosVehiculo620(tipoVehiculo620, anioDevengo, marcaCamSeleccionada, modeloCamSeleccionado){
		//Sin elementos
		if(tipoVehiculo620=="-1" || anioDevengo=="" || marcaCamSeleccionada == "-1" || modeloCamSeleccionado == "-1"){
			document.getElementById("potenciaFiscal620").value = "";
			document.getElementById("cilindrada620").value = "";
			document.getElementById("nCilindros620").value = "";
			document.getElementById("caracteristicas620").value = "";
			document.getElementById("carburante620").value = "-1";
			return;
		}

		var tipVehi = document.getElementById(tipoVehiculo620).value;
		var anio = document.getElementById(anioDevengo).value;
		var marca = document.getElementById(marcaCamSeleccionada).value;
		var modelo = document.getElementById(modeloCamSeleccionado).value;

		url="recuperarDatosVehiculo620TraficoAjax.action?idTipoVehiculo="+tipVehi+"&anioDevengo="+anio+"&marcaCamSeleccionada="+marca+"&modeloCamSeleccionado="+modelo;
		var req_generico_datosVehiculo = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_datosVehiculo.onreadystatechange = function() {
			rellenarDatosVehiculo620(req_generico_datosVehiculo);
		};
		req_generico_datosVehiculo.open("POST", url, true);
		req_generico_datosVehiculo.send(null);
	}

	function rellenarListaOficinasLiquidadoras(req_generico_oficinaLiquidadora,selectOficinaLiquidadora,id_oficinaLiquidadoraSeleccionada){
		selectOficinaLiquidadora.options.length = 0;

		if (req_generico_oficinaLiquidadora.readyState == 4) { // Complete
			if (req_generico_oficinaLiquidadora.status == 200) { // OK response
				textToSplit = req_generico_oficinaLiquidadora.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				//Process each of the elements
				var oficinaLiquidadoraSeleccionada = document.getElementById(id_oficinaLiquidadoraSeleccionada).value;
				for ( var i=0; i<returnElements.length; i++ ){
					value = returnElements[i].split(";");
					selectOficinaLiquidadora.options[i] = new Option(value[0], value[1]);
					if(selectOficinaLiquidadora.options[i].value == oficinaLiquidadoraSeleccionada){
						selectOficinaLiquidadora.options[i].selected = true;
					}
				}

			}
		}
	}

	function rellenarDatosVehiculo620(req_generico_datosVehiculo){

		if (req_generico_datosVehiculo.readyState == 4) { // Complete
			if (req_generico_datosVehiculo.status == 200) { // OK response
				textToSplit = req_generico_datosVehiculo.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				//Process each of the elements
				for (var i=0; i<returnElements.length; i++){
					value = returnElements[i].split(";;");
					document.getElementById("potenciaFiscal620").value = value[0]!=undefined?value[0]:"";
					document.getElementById("cilindrada620").value = value[1]!=undefined?value[1]:"";
					document.getElementById("nCilindros620").value = value[2]!=undefined?value[2]:"";
					document.getElementById("caracteristicas620").value = value[3]!=undefined?value[3]:"";
					if(value[3].substr(0,1)=="G"){
						document.getElementById("carburante620").value = "G";
					} else if(value[3].substr(0,1)=="D"){
						document.getElementById("carburante620").value = "D";
					}
					document.getElementById("valorDeclarado620").value = value[4]!=undefined?value[4]:"";
				}

				//Si el tipo de vehiculo es MOTOCICLETA hay que calcular su valor declarado según su cilindrada
				if(document.getElementById("tipoVehiculo620").value=="C"){
					calcularValorDeclaradoMoto();
				}
				calcularPorcentajeReduccionAnual();
				//Calculamos la Base Imponible multiplicando (valor declarado * porcentaje de reducción anual) - importe de reducción
				calcularReduccion620();
			}
		}
	}

	function calcularValorDeclaradoMoto(){
		var cilindradaMoto = $("#cilindrada620").val();

		if($("#cilindrada620").val() != ""){
			if(cilindradaMoto >= 0 && cilindradaMoto <= 50){
				$("#valorDeclarado620").val("800");
			} else if(cilindradaMoto > 50 && cilindradaMoto <= 75){
				$("#valorDeclarado620").val("1000");
			} else if(cilindradaMoto > 75 && cilindradaMoto <= 125){
				$("#valorDeclarado620").val("1400");
			} else if(cilindradaMoto > 125 && cilindradaMoto <= 150){
				$("#valorDeclarado620").val("1500");
			} else if(cilindradaMoto > 150 && cilindradaMoto <= 200){
				$("#valorDeclarado620").val("1700");
			} else if(cilindradaMoto > 200 && cilindradaMoto <= 250){
				$("#valorDeclarado620").val("2000");
			} else if(cilindradaMoto > 250 && cilindradaMoto <= 350){
				$("#valorDeclarado620").val("2800");
			} else if(cilindradaMoto > 350 && cilindradaMoto <= 450){
				$("#valorDeclarado620").val("3500");
			} else if(cilindradaMoto > 450 && cilindradaMoto <= 550){
				$("#valorDeclarado620").val("3900");
			} else if(cilindradaMoto > 550 && cilindradaMoto <= 750){
				$("#valorDeclarado620").val("6400");
			} else if(cilindradaMoto > 750 && cilindradaMoto <= 1000){
				$("#valorDeclarado620").val("9600");
			} else if(cilindradaMoto > 1000 && cilindradaMoto <= 1200){
				$("#valorDeclarado620").val("12100");
			} else if(cilindradaMoto > 1200){
				$("#valorDeclarado620").val("15300");
			}
		}
	}

	function calcularBaseImponible(){
		calcularPorcentajeReduccionAnual();
		if(document.getElementById("porcentajeReduccionAnual")!="" && document.getElementById("valorDeclarado620")!=""){
			var baseImponible = $("#valorDeclarado620").val() * ($("#porcentajeReduccionAnual").val() / 100);
			
			if ($("#reduccionImporte620").val() != "") {
				if($("#valorReal").val() != "" && Math.fround(parseFloat($("#valorReal").val())) > Math.fround(parseFloat($("#valorDeclarado620").val() * $("#porcentajeReduccionAnual").val() / 100))){
					baseImponible = $("#valorReal").val()
				}
				baseImponible = baseImponible -$("#reduccionImporte620").val();
			}

			$("#baseImponible620").val(parseFloat(baseImponible).toFixed(2));
			calcularCuotaTributaria();
		}
	}

	function calcularCuotaTributaria(){
		if(document.getElementById("baseImponible620")!="" && document.getElementById("tipoGravamen620")!=""){
			var cuotaTributaria = document.getElementById("baseImponible620").value * (document.getElementById("tipoGravamen620").value / 100);
			$("#cuotaTributaria620").val(parseFloat(cuotaTributaria).toFixed(2));
		}
	}

	function calcularReduccion620() {
		var valorDeclarado = parseFloat($("#valorDeclarado620").val() * $("#porcentajeReduccionAnual").val() / 100).toFixed(2);
		if(valorDeclarado==""){
			$("#valorDeclarado620").val("0");
		}
		if($("#baseImponible620").val()==""){
			$("#baseImponible620").val("0");
		}
		if($("#reduccionImporte620").val() == ""){
			$("#reduccionImporte620").val("0");
		}
		$("#reduccionPorcentaje620").show();
		$('#reduccionImporte620').show();
		$(".datoReduccion").show();
		var reduccion620 = $("#reduccionCodigo620").val();
		if (reduccion620 != "-1") {
			var porcentaje = $("#reduccionPorcentaje620").val();
			if($("#valorReal").val() != "" && Math.fround(parseFloat($("#valorReal").val())) > Math.fround(parseFloat($("#valorDeclarado620").val() * $("#porcentajeReduccionAnual").val() / 100))){
				valorDeclarado = parseFloat($("#valorReal").val() * porcentaje / 100).toFixed(2);
				$('#reduccionImporte620').val(valorDeclarado);
				$("#baseImponible620").val(valorDeclarado - $('#reduccionImporte620').val());
			}else{
				$('#reduccionImporte620').val(parseFloat(valorDeclarado * porcentaje / 100).toFixed(2));
				$("#baseImponible620").val(valorDeclarado - $('#reduccionImporte620').val());
			}
			
		} else {
			$("#reduccionPorcentaje620").hide();
			$("#reduccionPorcentaje620").val(0.00);
			$('#reduccionImporte620').hide();
			$('#reduccionImporte620').val(0);
			$(".datoReduccion").hide();
			$("#baseImponible620").val(valorDeclarado);
		}
		calcularBaseImponible();
	}

	function rellenarListaModelos(req_generico_modelo,selectModelo, id_modeloSeleccionado){
		selectModelo.options.length = 0;

		if (req_generico_modelo.readyState == 4) { // Complete
			if (req_generico_modelo.status == 200) { // OK response
				textToSplit = req_generico_modelo.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				selectModelo.options[0] = new Option("Seleccione modelo", "");
				//Process each of the elements
				var modeloSeleccionado = document.getElementById(id_modeloSeleccionado).value;
				for (var i=0; i<returnElements.length; i++){
					value = returnElements[i].split(";");
					selectModelo.options[i+1] = new Option(value[0], value[1]);
					if(selectModelo.options[i+1].value == modeloSeleccionado){
						selectModelo.options[i+1].selected = true;
					}
				}
			}
		}
	}

	// Recarga el combo cuando se recarga la página
	function recargarComboPueblos(id_select_provincia, id_hidden_municipio,id_select_pueblo,id_puebloSeleccionado){
		var selectProvincia = document.getElementById(id_select_provincia);
		var hiddenMunicipio= document.getElementById(id_hidden_municipio);
		var selectPueblo = document.getElementById(id_select_pueblo);
		if(selectProvincia != null && selectProvincia.selectedIndex >0){
			if(hiddenMunicipio != null && hiddenMunicipio.value > 0){
				obtenerPueblosPorMunicipio(selectProvincia,hiddenMunicipio,selectPueblo,id_puebloSeleccionado);
			}
		}
	}

	//Coloca los combos/textarea dependiendo del tipo de vehículo 620
	function cargarMarcaModelo(){
		if ($('#tipoVehiculo620').val()=="A" || $('#tipoVehiculo620').val()=="B") {
			//Mostramos el select de las marcas y modelos
			$('#idMarcaCam').css("display", "block");
			$('#idModeloCam').css("display", "block");
		} else {
			//Mostramos el textfield de las marcas y modelos
			$('#idMarcaCamText').css("display", "block");
			$('#idModeloCamText').css("display", "block");
		}
	}

	//Recarga del combo Modo de adjudicación dependiendo de lo seleccionado en el combo Tipo de transferencia
	//También oculta o muestra la pestaña Compraventa
	function seleccionaModoAdjudicacion() {
		var seleccionTipo = 0;
		if (document.getElementById('tipoTransferenciaId') != null)
			seleccionTipo = document.getElementById('tipoTransferenciaId').value;

		// Con Cambio Titularidad Completo no se muestra Compraventa
		if(seleccionTipo == "1") {
			document.getElementById("pestaniaCompraventaMO").style.display = "NONE";
		}

		var seleccionModo = 0;
		if (document.getElementById('modoAdjudicacionId') != null)
			seleccionModo = document.getElementById('modoAdjudicacionId').value;

		var tam = 0;

		if(seleccionTipo == "1" || seleccionTipo == "2" || seleccionTipo == "3") {
			document.getElementById('modoAdjudicacionId').options.length = 4;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Fallecimiento o donaci\u00f3n del titular","3");
			document.getElementById('modoAdjudicacionId').options[4] = new Option("Otros","4");

			//Para el adquiriente
			if (document.getElementById("pestaniaAdquirienteTransmision") != null)
				document.getElementById("pestaniaAdquirienteTransmision").style.display = "BLOCK";

			if (document.getElementById("pestaniaCompraventaMO") != null && seleccionTipo != "1")
				document.getElementById("pestaniaCompraventaMO").style.display = "BLOCK";

			if (document.getElementById("idNifPoseedor") != null)
				document.getElementById("idNifPoseedor").value="";
		} else if(seleccionTipo == "4"){
			document.getElementById('modoAdjudicacionId').options.length = 3;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Otros","4");

			//Para el adquiriente
			if (document.getElementById("pestaniaAdquirienteTransmision") != null && seleccionTipo != "1")
				document.getElementById("pestaniaAdquirienteTransmision").style.display = "BLOCK";

			/**
			 * Mantis 0004049: 2918. NOTIFICACIÓN DE VEHÍCULO EN BATE.
			 * Se añade el compraventa al trámite de Notificación.
			 * Gilberto Pedroso
			 */
			if (document.getElementById("pestaniaCompraventaMO") != null && seleccionTipo != "1")
				document.getElementById("pestaniaCompraventaMO").style.display = "BLOCK";

		} else if(seleccionTipo == "5"){
			document.getElementById('modoAdjudicacionId').options.length = 2;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Otros","4");

			//No tiene adquiriente
			if (document.getElementById("pestaniaAdquirienteTransmision") != null)
				document.getElementById("pestaniaAdquirienteTransmision").style.display = "NONE";

			if (document.getElementById("idNifAdquiriente") != null)
				document.getElementById("idNifAdquiriente").value="";

			if (document.getElementById("pestaniaCompraventaMO") != null && seleccionTipo != "1")
				document.getElementById("pestaniaCompraventaMO").style.display = "BLOCK";
		} else {
			document.getElementById('modoAdjudicacionId').options.length = 4;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Fallecimiento o donaci\u00f3n del titular","3");
			document.getElementById('modoAdjudicacionId').options[4] = new Option("Otros","4");

			//Para el adquiriente
			if (document.getElementById("pestaniaAdquirienteTransmision") != null)
				document.getElementById("pestaniaAdquirienteTransmision").style.display = "BLOCK";

			if (document.getElementById("pestaniaCompraventaMO") != null)
				document.getElementById("pestaniaCompraventaMO").style.display = "NONE";

			limpiarFormularioPoseedorTransmision();
			if (document.getElementById("idNifPoseedor") != null)
				document.getElementById("idNifPoseedor").value="";
		}

		// DRC@10-10-2012 Incidencia: 2549
		for (var a = 0; a < tam+1; a++) {
			var test = document.getElementById('modoAdjudicacionId').options[a];
			if (seleccionModo == test.value) {
				document.getElementById('modoAdjudicacionId').options[a].selected = true;
			}
		}

		// DRC@10-10-2012 Incidencia: 2658 y 2662
		if(document.getElementById('acreditacionHerenciaId').value == null || document.getElementById('acreditacionHerenciaId').value == ""){
			document.getElementById('acreditacionHerenciaId').value = "-1";
			document.getElementById('acreditacionHerenciaId').disabled=true;
		}
	}

	//Recarga del combo Modo de adjudicación dependiendo de lo seleccionado en el combo Tipo de transferencia
	//También oculta o muestra la pestaña Compraventa
	//Este es para la pantalla de transmisiones actuales.
	function seleccionaModoAdjudicacionActual(){
		var seleccionTipo = 0;
		if (document.getElementById('tipoTransferenciaId') != null)
			seleccionTipo = document.getElementById('tipoTransferenciaId').value;

		var seleccionModo = 0;
		if (document.getElementById('modoAdjudicacionId') != null) {
			seleccionModo = document.getElementById('modoAdjudicacionId').value;
		}

		if(seleccionTipo == "1") {
			document.getElementById("pestaniaCompraventaMO").style.display = "NONE";
		}

		var tam = 0;

		if(seleccionTipo == "1") {
			document.getElementById('modoAdjudicacionId').options.length = 4;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Fallecimiento o donaci\u00f3n del titular","3");
			document.getElementById('modoAdjudicacionId').options[4] = new Option("Otros","4");

			if (document.getElementById("pestaniaCompraventaMO") != null)
				document.getElementById("pestaniaCompraventaMO").style.display = "NONE";

			limpiarFormularioPoseedorTransmision();

			if (document.getElementById("idNifPoseedor") != null)
				document.getElementById("idNifPoseedor").value="";
		} else if(seleccionTipo == "2") {
			document.getElementById('modoAdjudicacionId').options.length = 3;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Otros","4");

			if (document.getElementById("pestaniaCompraventaMO") != null)
				document.getElementById("pestaniaCompraventaMO").style.display = "NONE";

			limpiarFormularioPoseedorTransmision();

			if (document.getElementById("idNifPoseedor") != null)
				document.getElementById("idNifPoseedor").value="";
		} else if(seleccionTipo == "3") {
			document.getElementById('modoAdjudicacionId').options.length = 2;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Otros","4");

			if (document.getElementById("pestaniaCompraventaMO") != null && seleccionTipo != "1")
				document.getElementById("pestaniaCompraventaMO").style.display = "BLOCK";
		} else {
			document.getElementById('modoAdjudicacionId').options.length = 4;
			tam = document.getElementById('modoAdjudicacionId').options.length;
			document.getElementById('modoAdjudicacionId').options[0] = new Option("Seleccione modo de adjudicaci\u00f3n","-1");
			document.getElementById('modoAdjudicacionId').options[1] = new Option("Transmisi\u00f3n","1");
			document.getElementById('modoAdjudicacionId').options[2] = new Option("Adjudicaci\u00f3n Judicial o subasta","2");
			document.getElementById('modoAdjudicacionId').options[3] = new Option("Fallecimiento o donaci\u00f3n del titular","3");
			document.getElementById('modoAdjudicacionId').options[4] = new Option("Otros","4");

			if (document.getElementById("pestaniaCompraventaMO") != null)
				document.getElementById("pestaniaCompraventaMO").style.display = "NONE";

			limpiarFormularioPoseedorTransmision();

			if (document.getElementById("idNifPoseedor") != null)
				document.getElementById("idNifPoseedor").value="";
		}
		// DRC@10-10-2012 Incidencia: 2549
		for (var a = 0; a < tam+1; a++) {
			var test = document.getElementById('modoAdjudicacionId').options[a];
			if (seleccionModo == test.value) {
				document.getElementById('modoAdjudicacionId').options[a].selected = true;
			}
		}
	}

	function cambioAutonomoAdquiriente(){
		if(document.getElementById('autonomoIdAdquiriente').checked){
			document.getElementById('codigoIAEAdquiriente').disabled = false;
		} else {
			document.getElementById('codigoIAEAdquiriente').disabled = true;
			document.getElementById('codigoIAEAdquiriente').value = "";
		}
	}

	function cambioAutonomoPoseedor(){
		if(document.getElementById('autonomoIdPoseedor').checked){
			document.getElementById('codigoIAEPoseedor').disabled = false;
		} else {
			document.getElementById('codigoIAEPoseedor').disabled = true;
			document.getElementById('codigoIAEPoseedor').value = "";
		}
	}

	function cambioAutonomoTransmitente(){
		if(document.getElementById('autonomoIdTransmitente').checked){
			document.getElementById('codigoIAETransmitente').disabled = false;
		} else {
			document.getElementById('codigoIAETransmitente').disabled = true;
			document.getElementById('codigoIAETransmitente').value = "";
		}
	}

	function cambioExento(){
		if(document.getElementById('exento620').checked){
			document.getElementById('noSujeto620').checked = false;
			if(document.getElementById('noSujetoItp') && document.getElementById('exentoItp')){
				document.getElementById('noSujetoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
				document.getElementById('exentoItp').checked = true; //Se copia el valor a la pestaña Pago de impuestos
			}
			document.getElementById('fundamentoExencion620').disabled = false;
			document.getElementById('fundamentoNoSujeccion620').disabled = true;
			document.getElementById('fundamentoNoSujeccion620').value = "";
		} else {
			if(document.getElementById('noSujetoItp') && document.getElementById('exentoItp')){
				document.getElementById('exentoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
				document.getElementById('noSujetoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
			}
			document.getElementById('fundamentoExencion620').disabled = true;
			document.getElementById('fundamentoExencion620').value = "-1";
			document.getElementById('fundamentoNoSujeccion620').disabled = true;
			document.getElementById('fundamentoNoSujeccion620').value = "";
		}
	}

	function cambioValorManual(){
		if(document.getElementById('checkValorManual').checked){
			document.getElementById('valorDeclarado620').disabled = false;
			document.getElementById('valorDeclarado620').value = "";
			document.getElementById('baseImponible620').value = "";
			document.getElementById('tipoGravamen620').value = "4.0";
			document.getElementById('idPorcentajeAdquisicion620').value = "100";
		} else {
			if(document.getElementById('tipoVehiculo620').value!='A' && document.getElementById('tipoVehiculo620').value!='B'){
				calcularValorDeclaradoMoto();
				calcularBaseImponible();
			}else{
				cargarDatosVehiculo620('tipoVehiculo620','anioDevengo','marcaCamSeleccionada', 'modeloCamSeleccionado');
			}
			document.getElementById('valorDeclarado620').disabled = true;
		}
	}

	function cambioNoSujeto(){
		if(document.getElementById('noSujeto620').checked){
			document.getElementById('exento620').checked = false;
			if(document.getElementById('noSujetoItp') && document.getElementById('exentoItp')){
				document.getElementById('exentoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
				document.getElementById('noSujetoItp').checked = true; //Se copia el valor a la pestaña Pago de impuestos
			}
			document.getElementById('fundamentoExencion620').disabled = true;
			document.getElementById('fundamentoExencion620').value = "-1";
			document.getElementById('fundamentoNoSujeccion620').disabled = false;
		} else {
			if(document.getElementById('noSujetoItp') && document.getElementById('exentoItp')){
				document.getElementById('exentoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
				document.getElementById('noSujetoItp').checked = false; //Se copia el valor a la pestaña Pago de impuestos
			}
			document.getElementById('fundamentoExencion620').disabled = true;
			document.getElementById('fundamentoExencion620').value = "-1";
			document.getElementById('fundamentoNoSujeccion620').disabled = true;
			document.getElementById('fundamentoNoSujeccion620').value = "";
		}
	}

	function cambioExentoItp(){
		if(document.getElementById('exentoItp').checked){
			document.getElementById('noSujetoItp').checked = false;
			document.getElementById('noSujeto620').checked = false;
			document.getElementById('exento620').checked = true;
		} else {
			document.getElementById('noSujeto620').checked = false;
			document.getElementById('exento620').checked = false;
		}
		cambioExento(); //Actualizamos los checks en la pestaña del modelo 620
	}

	function cambioNoSujetoItp(){
		if(document.getElementById('noSujetoItp').checked){
			document.getElementById('exentoItp').checked = false;
			document.getElementById('noSujeto620').checked = true;
		} else {
			document.getElementById('noSujeto620').checked = false;
		}
		document.getElementById('exento620').checked = false;
		cambioNoSujeto(); //Actualizamos los checks en la pestaña del modelo 620
	}

	function cambioExentoIsd(){
		if(document.getElementById('exentoIsd').checked){
			document.getElementById('noSujetoIsd').checked = false;
		}
	}

	function cambioNoSujetoIsd(){
		if(document.getElementById('noSujetoIsd').checked){
			document.getElementById('exentoIsd').checked = false;
		}
	}

	//Poner los combos a -1 en el caso de tenerlos deshabilitadas
	function cambioExentoIedtm(){
		if(document.getElementById('exentoIedtm').checked){
			document.getElementById('noSujetoIedtm').checked = false;
			document.getElementById('idReduccionNoSujeccion05').disabled=false;
			document.getElementById('idNoSujeccionOExencion06').value="-1";
			document.getElementById('idNoSujeccionOExencion06').disabled=true;
		} else{
			document.getElementById('idReduccionNoSujeccion05').value="-1";
			document.getElementById('idReduccionNoSujeccion05').disabled=true;
		}
	}

	function cambioNoSujetoIedtm(){
		if(document.getElementById('noSujetoIedtm').checked){
			document.getElementById('exentoIedtm').checked = false;
			document.getElementById('idReduccionNoSujeccion05').value="-1";
			document.getElementById('idReduccionNoSujeccion05').disabled=true;
			document.getElementById('idNoSujeccionOExencion06').disabled=false;
		} else{
			document.getElementById('idNoSujeccionOExencion06').value="-1";
			document.getElementById('idNoSujeccionOExencion06').disabled=true;
		}
	}

	function cambioCambioServicio(){
		if(document.getElementById('cambioServicioId').checked){
			document.getElementById('codigoTasaCambioServicioId').disabled = false;
		} else {
			document.getElementById('codigoTasaCambioServicioId').disabled = true;
			document.getElementById('codigoTasaCambioServicioId').value="-1";
		}
	}

	function cambioDireccionVehiculo(){
		if((document.getElementById("tipoViaVehiculo").value!="-1" ||
			document.getElementById("idProvinciaVehiculo").value!="-1" ||
			document.getElementById("idMunicipioVehiculo").value!="-1" ||
			document.getElementById("codPostalVehiculo").value!="" ||
			document.getElementById("viaVehiculo").value!="" ||
			document.getElementById("numeroVehiculo").value!="") && (document.getElementById('idResponsabilidadLocalizacion').checked))
			{
			document.getElementById("direccionDistintaAdquiriente620").checked=true;
		} else {
			document.getElementById("direccionDistintaAdquiriente620").checked=false;
		}
	}

	function exentoIEDTM_CTIT(){
		var $numRegistro = $("#numRegistro");
		if ($numRegistro.length && $numRegistro.val()!='-1' && $numRegistro.val()!='') {
			$("#idNombreFinanciera").attr("value", "EXENTO PR IEDMT");
		}
		if ($numRegistro.length && ($numRegistro.val()=='-1' || $numRegistro.val()=='')) {
			$("#idNombreFinanciera").attr("value", "");
		}
	}

	function habilitarDireccionVehiculo(checked, pestaniaVehiculo) {
		var bloqueado = true;
		var marcar620 = false;
		var color = "#EEEEEE";

		if (checked == true) {
			bloqueado = false;
			marcar620 = true;
			color = "white";
		}

		if (bloqueado) {
			document.getElementById("idProvinciaVehiculo").selectedIndex = 0;
			document.getElementById("idMunicipioVehiculo").selectedIndex = 0;
			document.getElementById("municipioSeleccionadoVehiculo").value = "-1";
			document.getElementById("codPostalVehiculo").value = "";
			document.getElementById("idPuebloVehiculo").selectedIndex = 0;
			document.getElementById("puebloSeleccionadoVehiculo").value = "-1";
			document.getElementById("tipoViaVehiculo").selectedIndex = 0;
			document.getElementById("tipoViaSeleccionadaVehiculo").value = "-1";
			document.getElementById("viaVehiculo").value = "";
			document.getElementById("numeroVehiculo").value = "";
			document.getElementById("escaleraVehiculo").value = "";
			document.getElementById("letraVehiculo").value = "";
			document.getElementById("pisoVehiculo").value = "";
			document.getElementById("puertaVehiculo").value = "";
			document.getElementById("bloqueVehiculo").value = "";
			document.getElementById("kmVehiculo").value = "";
			document.getElementById("hmVehiculo").value = "";
		}

		document.getElementById("idProvinciaVehiculo").disabled = bloqueado;
		document.getElementById("idProvinciaVehiculo").style.backgroundColor = color;

		document.getElementById("idMunicipioVehiculo").disabled = bloqueado;
		document.getElementById("idMunicipioVehiculo").style.backgroundColor = color;

		document.getElementById("codPostalVehiculo").disabled = bloqueado;
		document.getElementById("codPostalVehiculo").style.backgroundColor = color;

		document.getElementById("idPuebloVehiculo").disabled = bloqueado;
		document.getElementById("idPuebloVehiculo").style.backgroundColor = color;

		document.getElementById("tipoViaVehiculo").disabled = bloqueado;
		document.getElementById("tipoViaVehiculo").style.backgroundColor = color;

		document.getElementById("viaVehiculo").disabled = bloqueado;
		document.getElementById("viaVehiculo").style.backgroundColor = color;

		document.getElementById("numeroVehiculo").disabled = bloqueado;
		document.getElementById("numeroVehiculo").style.backgroundColor = color;

		document.getElementById("escaleraVehiculo").disabled = bloqueado;
		document.getElementById("escaleraVehiculo").style.backgroundColor = color;

		document.getElementById("letraVehiculo").disabled = bloqueado;
		document.getElementById("letraVehiculo").style.backgroundColor = color;

		document.getElementById("pisoVehiculo").disabled = bloqueado;
		document.getElementById("pisoVehiculo").style.backgroundColor = color;

		document.getElementById("puertaVehiculo").disabled = bloqueado;
		document.getElementById("puertaVehiculo").style.backgroundColor = color;

		document.getElementById("bloqueVehiculo").disabled = bloqueado;
		document.getElementById("bloqueVehiculo").style.backgroundColor = color;

		document.getElementById("kmVehiculo").disabled = bloqueado;
		document.getElementById("kmVehiculo").style.backgroundColor = color;

		document.getElementById("hmVehiculo").disabled = bloqueado;
		document.getElementById("hmVehiculo").style.backgroundColor = color;

		if (pestaniaVehiculo) {
			document.getElementById("direccionDistintaAdquiriente620").checked = marcar620;
		}

		if (checked == true && document.getElementById("idVehiculo"!=null)) {
			var idVehiculo = document.getElementById("idVehiculo").value;
			if (idVehiculo != null && idVehiculo != "") {
				var url = "consultaDireccionVehiculoAltasTramiteTransmision.action?idVehiculo=" + idVehiculo;
				if (pestaniaVehiculo) {
					url = url + "#Vehiculo";
				}
				document.formData.action = url;
				document.formData.submit();
			}
		}
	}

	function cambioConceptoRepreAdquiriente(){
		if(document.getElementById("conceptoRepresentanteAdquiriente").value=="TUTELA O PATRIA POTESTAD"){
			document.getElementById("motivoRepresentanteAdquiriente").disabled=false;
		} else {
			document.getElementById("motivoRepresentanteAdquiriente").disabled=true;
			document.getElementById("motivoRepresentanteAdquiriente").value="-1";
		}
	}

	function cambioConceptoRepreTransmitente(idConcepto, idMotivo){
		if(document.getElementById(idConcepto).value=="TUTELA O PATRIA POTESTAD"){
			document.getElementById(idMotivo).disabled=false;
		} else {
			document.getElementById(idMotivo).disabled=true;
			document.getElementById(idMotivo).value="-1";
		}
	}

	function cambioConceptoReprePoseedor(){
		if(document.getElementById("conceptoRepresentantePoseedor").value=="TUTELA O PATRIA POTESTAD"){
			document.getElementById("motivoRepresentantePoseedor").disabled=false;
		} else {
			document.getElementById("motivoRepresentantePoseedor").disabled=true;
			document.getElementById("motivoRepresentantePoseedor").value="-1";
		}
	}

	function cambioConceptoRepreArrendatario(){
		if(document.getElementById("conceptoRepresentanteArrendatario").value=="TUTELA O PATRIA POTESTAD"){
			document.getElementById("motivoRepresentanteArrendatario").disabled=false;
		} else {
			document.getElementById("motivoRepresentanteArrendatario").disabled=true;
			document.getElementById("motivoRepresentanteArrendatario").value="-1";
		}
	}

	function cambioModoAdjudicacionSinImpuestos(){
		if(document.getElementById("modoAdjudicacionId").value=="3"){
			document.getElementById("acreditacionHerenciaId").disabled=false;
		} else {
			document.getElementById("acreditacionHerenciaId").disabled=true;
			document.getElementById("acreditacionHerenciaId").value="-1";
		}
	}
	function mostrarAnexo(){
		  var seleccionado = document.getElementById("acreditacionHerenciaId").value;
		  var enlaceDescarga = document.getElementById('herenciaAnexo');
		  
		  if (seleccionado === "HERENCIA") {
		    // Habilitar el enlace de descarga y establecer su href
		    enlaceDescarga.href = "descargarAnexoHerenciaAltasTramiteTransmision.action";
		    enlaceDescarga.style.display = 'inline'; // Mostrar el enlace
		  } else {
		    // Si se selecciona otra opción, deshabilitar el enlace de descarga
		    enlaceDescarga.removeAttribute('href');
		    enlaceDescarga.style.display = 'none'; // Ocultar el enlace
		  }
	}

	function cambioModoAdjudicacion(){
		if(document.getElementById("modoAdjudicacionId").value=="3"){
			document.getElementById("acreditacionHerenciaId").disabled=false;
		} else {
			document.getElementById("acreditacionHerenciaId").disabled=true;
			document.getElementById("acreditacionHerenciaId").value="-1";
		}
		//Seleccionamos el impuesto
		habilitaImpuestos();
	}

	function habilitaImpuestos(){
		if(document.getElementById("modoAdjudicacionId").value=="3"){
			//Deshabilitamos el ITP
			document.getElementById("cetItp").disabled=true;
			document.getElementById("cetItp").value="";
			document.getElementById("modeloItp").disabled=true;
			document.getElementById("modeloItp").value="-1";
			document.getElementById("exentoItp").disabled=true;
			document.getElementById("exentoItp").checked=false;
			document.getElementById("numAutoItp").disabled=true;
			document.getElementById("numAutoItp").value="";
			document.getElementById("noSujetoItp").disabled=true;
			document.getElementById("noSujetoItp").checked=false;
			//También en la pestaña Modelo620
			document.getElementById("exento620").disabled=true;
			document.getElementById("exento620").checked=false;
			document.getElementById("fundamentoExencion620").disabled=true;
			document.getElementById("fundamentoExencion620").value="-1";
			document.getElementById("noSujeto620").disabled=true;
			document.getElementById("noSujeto620").checked=false;
			document.getElementById("fundamentoNoSujeccion620").disabled=true;
			document.getElementById("fundamentoNoSujeccion620").value="";
			//Habilitamos el ISD
			document.getElementById("modeloIsd").disabled=false;
			document.getElementById("exentoIsd").disabled=false;
			document.getElementById("numAutoIsd").disabled=false;
			document.getElementById("noSujetoIsd").disabled=false;
			//Deshabilitamos también la factura y el check de contrato de compraventa
			document.getElementById("nFactura").value="";
			document.getElementById("nFactura").disabled=true;
			document.getElementById("contratoCompraventaId").disabled=true;
			document.getElementById("contratoCompraventaId").checked=false;
		} else {
			//Deshabilitamos el ISD
			document.getElementById("modeloIsd").disabled=true;
			document.getElementById("modeloIsd").value="-1";
			document.getElementById("exentoIsd").disabled=true;
			document.getElementById("exentoIsd").checked=false;
			document.getElementById("numAutoIsd").disabled=true;
			document.getElementById("numAutoIsd").value="";
			document.getElementById("noSujetoIsd").disabled=true;
			document.getElementById("noSujetoIsd").checked=false;
			//Habilitamos el ITP
			document.getElementById("cetItp").disabled=false;
			document.getElementById("modeloItp").disabled=false;
			document.getElementById("exentoItp").disabled=false;
			document.getElementById("numAutoItp").disabled=false;
			document.getElementById("noSujetoItp").disabled=false;
			//También en la pestaña Modelo620
			document.getElementById("exento620").disabled=false;
			document.getElementById("fundamentoExencion620").disabled=false;
			document.getElementById("noSujeto620").disabled=false;
			document.getElementById("fundamentoNoSujeccion620").disabled=false;
			//Habilitamos el check de contrato de compraventa y la factura, dado el caso
			document.getElementById("contratoCompraventaId").disabled=false;
			if(document.getElementById("contratoCompraventaId").checked){
				document.getElementById("nFactura").disabled=true;
			} else {
				document.getElementById("nFactura").disabled=false;
				if(document.getElementById("nFactura").value!=""){
					//Deshabilitamos el ITP
					document.getElementById("cetItp").disabled=true;
					document.getElementById("cetItp").value="";
					document.getElementById("modeloItp").disabled=true;
					document.getElementById("modeloItp").value="-1";
					document.getElementById("exentoItp").disabled=true;
					document.getElementById("exentoItp").checked=false;
					document.getElementById("numAutoItp").disabled=true;
					document.getElementById("numAutoItp").value="";
					document.getElementById("noSujetoItp").disabled=true;
					document.getElementById("noSujetoItp").checked=false;
					//También en la pestaña Modelo620
					document.getElementById("exento620").disabled=true;
					document.getElementById("exento620").checked=false;
					document.getElementById("fundamentoExencion620").disabled=true;
					document.getElementById("fundamentoExencion620").value="-1";
					document.getElementById("noSujeto620").disabled=true;
					document.getElementById("noSujeto620").checked=false;
					document.getElementById("fundamentoNoSujeccion620").disabled=true;
					document.getElementById("fundamentoNoSujeccion620").value="";
				}
			}
		}
	}

	function cambioFactura(){
		if(document.getElementById("nFactura").value!=""){
			//Deshabilitamos el ITP
			document.getElementById("cetItp").disabled=true;
			document.getElementById("cetItp").value="";
			document.getElementById("modeloItp").disabled=true;
			document.getElementById("modeloItp").value="-1";
			document.getElementById("exentoItp").disabled=true;
			document.getElementById("exentoItp").checked=false;
			document.getElementById("numAutoItp").disabled=true;
			document.getElementById("numAutoItp").value="";
			document.getElementById("noSujetoItp").disabled=true;
			document.getElementById("noSujetoItp").checked=false;
			//También en la pestaña Modelo620
			document.getElementById("exento620").disabled=true;
			document.getElementById("exento620").checked=false;
			document.getElementById("fundamentoExencion620").disabled=true;
			document.getElementById("fundamentoExencion620").value="-1";
			document.getElementById("noSujeto620").disabled=true;
			document.getElementById("noSujeto620").checked=false;
			document.getElementById("fundamentoNoSujeccion620").disabled=true;
			document.getElementById("fundamentoNoSujeccion620").value="";
			//Deshabilitamos el campo del valor real
			document.getElementById("valorReal").disabled=true;
			document.getElementById("valorReal").value="0.00";
		} else {
			//Habilitamos el ITP
			document.getElementById("cetItp").disabled=false;
			document.getElementById("modeloItp").disabled=false;
			document.getElementById("exentoItp").disabled=false;
			document.getElementById("numAutoItp").disabled=false;
			document.getElementById("noSujetoItp").disabled=false;
			//También en la pestaña Modelo620
			document.getElementById("exento620").disabled=false;
			document.getElementById("fundamentoExencion620").disabled=false;
			document.getElementById("noSujeto620").disabled=false;
			document.getElementById("fundamentoNoSujeccion620").disabled=false;
			//Deshabilitamos el campo del valor real
			document.getElementById("valorReal").disabled=false;
		}
	}

	function cambioFacturaActual(){
		if(document.getElementById("nFactura").value!=""){
			document.getElementById("cetItp").disabled = true;
			document.getElementById("cetItp").value = "";
		} else {
			document.getElementById("cetItp").disabled = false;
		}
	}

	function cambioContratoFactura(){
		if(document.getElementById("contratoCompraventaId").checked){
			document.getElementById("nFactura").disabled=true;
			document.getElementById("nFactura").value="";
		} else {
			document.getElementById("nFactura").disabled=false;
		}
	}

	function cargarTipoTasaTipoTransferencia(){
		var tipoTasaSeleccionado = document.getElementById("tipoTasaTransmisionId").value;
		if(document.getElementById("tipoTransferenciaId").value == "4" ||
			document.getElementById("tipoTransferenciaId").value == "5"){
			document.getElementById('tipoTasaTransmisionId').options.length=2;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("-","-1",true,true);
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("4.1","4.1");
		} else {
			document.getElementById("tipoTasaTransmisionId").disabled=false;
			document.getElementById('tipoTasaTransmisionId').options.length=3;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("-","-1");
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
			document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
		}
	}

	function cargarTipoTasaTipoTransferenciaActual(){
		var tipoTasaSeleccionado = document.getElementById("tipoTasaTransmisionId").value;
		if(document.getElementById("tipoTransferenciaId").value == "2" ||
			document.getElementById("tipoTransferenciaId").value == "3"){
			document.getElementById('tipoTasaTransmisionId').options.length=2;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("","-1",true,true);
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("4.1","4.1");
		} else {
			document.getElementById("tipoTasaTransmisionId").disabled=false;
			document.getElementById('tipoTasaTransmisionId').options.length=3;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("","-1");
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
			document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
		}
	}

	function cargarTipoTasaModoAdjudicacion(){
		var tipoTasaSeleccionado = document.getElementById("tipoTasaTransmisionId").value;
		if(document.getElementById("tipoTransferenciaId").value == "4" || document.getElementById("tipoTransferenciaId").value == "5"){
			document.getElementById('tipoTasaTransmisionId').options.length=2;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("-","-1");
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("4.1","4.1",tipoTasaSeleccionado=="4.1"?true:false,tipoTasaSeleccionado=="4.1"?true:false);
		} else {
			if(document.getElementById("modoAdjudicacionId").value == "4"){
				document.getElementById("tipoTasaTransmisionId").disabled=false;
				document.getElementById('tipoTasaTransmisionId').options.length=4;
				document.getElementById('tipoTasaTransmisionId').options[0] = new Option("-","-1");
				document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[3] = new Option("1.6","1.6",tipoTasaSeleccionado!="1.2" && tipoTasaSeleccionado!="1.5"?true:false,tipoTasaSeleccionado!="1.2" && tipoTasaSeleccionado!="1.5"?true:false);
			} else {
				document.getElementById("tipoTasaTransmisionId").disabled=false;
				document.getElementById('tipoTasaTransmisionId').options.length=3;
				document.getElementById('tipoTasaTransmisionId').options[0] = new Option("-","-1");
				document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
			}
		}
		document.getElementById("tipoTasaTransmisionId").value = tipoTasaSeleccionado;
	}

	function cargarTipoTasaModoAdjudicacionActual(){
		var tipoTasaSeleccionado = document.getElementById("tipoTasaTransmisionId").value;
		if(document.getElementById("tipoTransferenciaId").value == "2" ){
			document.getElementById('tipoTasaTransmisionId').options.length=2;
			document.getElementById('tipoTasaTransmisionId').options[0] = new Option("","-1");
			document.getElementById('tipoTasaTransmisionId').options[1] = new Option("4.1","4.1",tipoTasaSeleccionado=="4.1"?true:false,tipoTasaSeleccionado=="4.1"?true:false);
		} else {
			if(document.getElementById("modoAdjudicacionId").value == "4"){
				document.getElementById("tipoTasaTransmisionId").disabled=false;
				document.getElementById('tipoTasaTransmisionId').options.length=4;
				document.getElementById('tipoTasaTransmisionId').options[0] = new Option("","-1");
				document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[3] = new Option("1.6","1.6",tipoTasaSeleccionado!="1.2" && tipoTasaSeleccionado!="1.5"?true:false,tipoTasaSeleccionado!="1.2" && tipoTasaSeleccionado!="1.5"?true:false);
			} else {
				document.getElementById("tipoTasaTransmisionId").disabled=false;
				document.getElementById('tipoTasaTransmisionId').options.length=3;
				document.getElementById('tipoTasaTransmisionId').options[0] = new Option("","-1");
				document.getElementById('tipoTasaTransmisionId').options[1] = new Option("1.2","1.2",tipoTasaSeleccionado=="1.2"?true:false,tipoTasaSeleccionado=="1.2"?true:false);
				document.getElementById('tipoTasaTransmisionId').options[2] = new Option("1.5","1.5",tipoTasaSeleccionado=="1.5"?true:false,tipoTasaSeleccionado=="1.5"?true:false);
			}
		}
		document.getElementById("tipoTasaTransmisionId").value = tipoTasaSeleccionado;
	}

	function habilitaCodigoTasaSiTipoTasaTransmision(){
		if(document.getElementById("tipoTasaTransmisionId").value != "" && document.getElementById("tipoTasaTransmisionId").value != "-1")
			document.getElementById("codigoTasaTransmisionId").disabled = false;
		else
			document.getElementById("codigoTasaTransmisionId").disabled = true;
	}

	function trasCambioTipoTransferenciaOModoAdjudicacion(){
		document.getElementById('codigoTasaTransmisionId').options.length = 0;
		document.getElementById('codigoTasaTransmisionId').options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
		document.getElementById('codigoTasaTransmisionSeleccionadoId').value = "-1";
		recargarComboTasas('tipoTasaTransmisionId', 'codigoTasaTransmisionId', 'codigoTasaTransmisionSeleccionadoId');
	}

	function calcularPorcentajeReduccionAnual(){
		var fDev = new Date(document.getElementById("anioDevengo").value, document.getElementById("mesDevengo").value, document.getElementById("diaDevengo").value).getTime();
		var fPM = new Date(document.getElementById("anioPrimeraMatriculacion").value, document.getElementById("mesPrimeraMatriculacion").value, document.getElementById("diaPrimeraMatriculacion").value).getTime();

		var dif = fDev - fPM;

		var aniosDif = dif/(1000.0*60.0*60.0*24.0*365.2425);

		if(aniosDif>12) 
			aniosDif = 12;
		else
			aniosDif = Math.floor(aniosDif);

		switch (aniosDif) {
		case 0: document.getElementById("porcentajeReduccionAnual").value = "100"; break;
		case 1: document.getElementById("porcentajeReduccionAnual").value = "84"; break;
		case 2: document.getElementById("porcentajeReduccionAnual").value = "67"; break;
		case 3: document.getElementById("porcentajeReduccionAnual").value = "56"; break;
		case 4: document.getElementById("porcentajeReduccionAnual").value = "47"; break;
		case 5: document.getElementById("porcentajeReduccionAnual").value = "39"; break;
		case 6: document.getElementById("porcentajeReduccionAnual").value = "34"; break;
		case 7: document.getElementById("porcentajeReduccionAnual").value = "28"; break;
		case 8: document.getElementById("porcentajeReduccionAnual").value = "24"; break;
		case 9: document.getElementById("porcentajeReduccionAnual").value = "19"; break;
		case 10: document.getElementById("porcentajeReduccionAnual").value = "17"; break;
		case 11: document.getElementById("porcentajeReduccionAnual").value = "13"; break;
		case 12: document.getElementById("porcentajeReduccionAnual").value = "10"; break;
		}
	}

	/////////////////////////////////////////////////
	//////////LIMPIEZA DE INTERVINIENTES/////////////
	/////////////////////////////////////////////////

	function limpiarFormularioAdquirienteTransmision(){
		var nifAdquiriente = document.getElementById("nifAdquiriente").value;
		if (nifAdquiriente!=null && nifAdquiriente!="") {
			//document.getElementById("rowIdPersonaAdquiriente").value = "";
			document.getElementById("idDireccionAdquiriente").value = "";
			document.getElementById("tipoPersonaAdquiriente").value = "-1";
			document.getElementById("sexoAdquiriente").value = "-1";
			document.getElementById("idApellidoRazonSocialAdquiriente").value = "";
			document.getElementById("idApellido2Adquiriente").value = "";
			document.getElementById("idNombreAdquiriente").value = "";
			document.getElementById("idAnagramaAdquiriente").value = "";
			document.getElementById("diaNacAdquiriente").value = "";
			document.getElementById("mesNacAdquiriente").value = "";
			document.getElementById("anioNacAdquiriente").value = "";
			document.getElementById("idTelefonoAdquiriente").value = "";
			document.getElementById("tipoViaAdquiriente").value = "-1";
			document.getElementById("idProvinciaAdquiriente").value = "-1";
			document.getElementById("idMunicipioAdquiriente").length = 0;
			document.getElementById("idMunicipioAdquiriente").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoAdquiriente").value = "";
			document.getElementById("idPuebloAdquiriente").length = 0;
			document.getElementById("idPuebloAdquiriente").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoAdquiriente").value = "";
			document.getElementById("codPostalAdquiriente").value = "";
			document.getElementById("viaAdquiriente").value = "";
			document.getElementById("numeroAdquiriente").value = "";
			document.getElementById("letraAdquiriente").value = "";
			document.getElementById("escaleraAdquiriente").value = "";
			document.getElementById("pisoAdquiriente").value = "";
			document.getElementById("puertaAdquiriente").value = "";
			document.getElementById("bloqueAdquiriente").value = "";
			document.getElementById("kmAdquiriente").value = "";
			document.getElementById("hmAdquiriente").value = "";
			document.getElementById("actualizaDomicilioIdAdquiriente").checked = false;
			document.getElementById("autonomoIdAdquiriente").checked = false;
			document.getElementById("codigoIAEAdquiriente").value = "";
			document.getElementById("codigoIAEAdquiriente").disabled = true;
		}
	}

	function limpiarFormularioRepresentanteAdquirienteTransmision(){
		var nifRepresentanteAdquiriente = document.getElementById("nifRepresentanteAdquiriente").value;
		if (nifRepresentanteAdquiriente!=null && nifRepresentanteAdquiriente!="") {
			document.getElementById("idDireccionRepresentanteAdquiriente").value = "";
			document.getElementById("apellido1RepresentanteAdquiriente").value = "";
			document.getElementById("apellido2RepresentanteAdquiriente").value = "";
			document.getElementById("idNombreRepresentanteAdquiriente").value = "";
			document.getElementById("conceptoRepresentanteAdquiriente").value = "-1";
			document.getElementById("motivoRepresentanteAdquiriente").value = "-1";
			document.getElementById("motivoRepresentanteAdquiriente").disabled = true;
			document.getElementById("datosAcrediteRepresentanteAdquiriente").value = "";
			document.getElementById("diaInicioRepresentanteAdquiriente").value = "";
			document.getElementById("mesInicioRepresentanteAdquiriente").value = "";
			document.getElementById("anioInicioRepresentanteAdquiriente").value = "";
			document.getElementById("diaFinRepresentanteAdquiriente").value = "";
			document.getElementById("mesFinRepresentanteAdquiriente").value = "";
			document.getElementById("anioFinRepresentanteAdquiriente").value = "";
		}
	}

	function limpiarFormularioConductorHabitualTransmision(){
		var nifConductorHabitual = document.getElementById("nifConductorHabitual").value;
		if (nifConductorHabitual!=null && nifConductorHabitual!="") {
			document.getElementById("idDireccionConductorHabitual").value = "";
			document.getElementById("idApellido1ConductorHabitualAdquiriente").value = "";
			document.getElementById("idApellido2ConductorHabitualAdquiriente").value = "";
			document.getElementById("idNombreConductorHabitualAdquiriente").value = "";
			document.getElementById("diaNacConductorHabitualAdquiriente").value = "";
			document.getElementById("mesNacConductorHabitualAdquiriente").value = "";
			document.getElementById("anioNacConductorHabitualAdquiriente").value = "";
			document.getElementById("diaInicioConductorHabitualAdquiriente").value = "";
			document.getElementById("mesInicioConductorHabitualAdquiriente").value = "";
			document.getElementById("anioInicioConductorHabitualAdquiriente").value = "";
			document.getElementById("diaFinConductorHabitualAdquiriente").value = "";
			document.getElementById("mesFinConductorHabitualAdquiriente").value = "";
			document.getElementById("anioFinConductorHabitualAdquiriente").value = "";
		}
	}

	function limpiarFormularioTransmitenteTransmision(){
		var nifTransmitente = document.getElementById("nifTransmitente").value;
		if (nifTransmitente!=null && nifTransmitente!="") {		
			document.getElementById("idDireccionTransmitente").value = "";
			document.getElementById("tipoPersonaTransmitente").value = "-1";
			document.getElementById("sexoTransmitente").value = "-1";
			document.getElementById("idApellidoRazonSocialTransmitente").value = "";
			document.getElementById("idApellido2Transmitente").value = "";
			document.getElementById("idNombreTransmitente").value = "";
			document.getElementById("idAnagramaTransmitente").value = "";
			document.getElementById("diaNacTransmitente").value = "";
			document.getElementById("mesNacTransmitente").value = "";
			document.getElementById("anioNacTransmitente").value = "";
			document.getElementById("idTelefonoTransmitente").value = "";
			document.getElementById("tipoViaTransmitente").value = "-1";
			document.getElementById("idProvinciaTransmitente").value = "-1";
			document.getElementById("idMunicipioTransmitente").length = 0;
			document.getElementById("idMunicipioTransmitente").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoTransmitente").value = "";
			document.getElementById("idPuebloTransmitente").length = 0;
			document.getElementById("idPuebloTransmitente").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoTransmitente").value = "";
			document.getElementById("codPostalTransmitente").value = "";
			document.getElementById("viaTransmitente").value = "";
			document.getElementById("numeroTransmitente").value = "";
			document.getElementById("letraTransmitente").value = "";
			document.getElementById("escaleraTransmitente").value = "";
			document.getElementById("pisoTransmitente").value = "";
			document.getElementById("puertaTransmitente").value = "";
			document.getElementById("bloqueTransmitente").value = "";
			document.getElementById("kmTransmitente").value = "";
			document.getElementById("hmTransmitente").value = "";
		}
	}

	// Tipos de representante : 'Representante', 'RepresentantePrimerCotitular', 'RepresentanteSegundoCotitular'
	function limpiarFormularioRepresentanteTransmitenteTransmision(tipoRepresentante){
		var nifRepresentanteTransmitente = document.getElementById("nif" + tipoRepresentante + "Transmitente").value;
		if (nifRepresentanteTransmitente!=null && nifRepresentanteTransmitente!="") {
			document.getElementById("idDireccion" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("apellido1" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("apellido2" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("idNombre" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("concepto" + tipoRepresentante + "Transmitente").value = "-1";
			document.getElementById("motivo" + tipoRepresentante + "Transmitente").value = "-1";
			document.getElementById("motivo" + tipoRepresentante + "Transmitente").disabled = true;
			document.getElementById("datosAcredite" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("diaInicio" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("mesInicio" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("anioInicio" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("diaFin" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("mesFin" + tipoRepresentante + "Transmitente").value = "";
			document.getElementById("anioFin" + tipoRepresentante + "Transmitente").value = "";
		}
	}

	function limpiarFormularioPrimerCotitularTransmitenteTransmision(){
		var nifPrimerCotitularTransmitente = document.getElementById("nifPrimerCotitularTransmitente").value;
		if (nifPrimerCotitularTransmitente!=null && nifPrimerCotitularTransmitente!="") {
			document.getElementById("idDireccionPrimerCotitularTransmitente").value = "";
			document.getElementById("tipoPersonaPrimerCotitularTransmitente").value = "-1";
			document.getElementById("sexoPrimerCotitularTransmitente").value = "-1";
			document.getElementById("idApellidoRazonSocialPrimerCotitularTransmitente").value = "";
			document.getElementById("idApellido2PrimerCotitularTransmitente").value = "";
			document.getElementById("idNombrePrimerCotitularTransmitente").value = "";
			document.getElementById("diaNacPrimerCotitularTransmitente").value = "";
			document.getElementById("mesNacPrimerCotitularTransmitente").value = "";
			document.getElementById("anioNacPrimerCotitularTransmitente").value = "";
			document.getElementById("idTelefonoPrimerCotitularTransmitente").value = "";
			document.getElementById("tipoViaPrimerCotitularTransmitente").value = "-1";
			document.getElementById("idProvinciaPrimerCotitularTransmitente").value = "-1";
			document.getElementById("idMunicipioPrimerCotitularTransmitente").length = 0;
			document.getElementById("idMunicipioPrimerCotitularTransmitente").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoPrimerCotitularTransmitente").value = "";
			document.getElementById("idPuebloPrimerCotitularTransmitente").length = 0;
			document.getElementById("idPuebloPrimerCotitularTransmitente").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoPrimerCotitularTransmitente").value = "";
			document.getElementById("codPostalPrimerCotitularTransmitente").value = "";
			document.getElementById("viaPrimerCotitularTransmitente").value = "";
			document.getElementById("numeroPrimerCotitularTransmitente").value = "";
			document.getElementById("letraPrimerCotitularTransmitente").value = "";
			document.getElementById("escaleraPrimerCotitularTransmitente").value = "";
			document.getElementById("pisoPrimerCotitularTransmitente").value = "";
			document.getElementById("puertaPrimerCotitularTransmitente").value = "";
			document.getElementById("bloquePrimerCotitularTransmitente").value = "";
			document.getElementById("kmPrimerCotitularTransmitente").value = "";
			document.getElementById("hmPrimerCotitularTransmitente").value = "";
		}
	}

	function limpiarFormularioSegundoCotitularTransmitenteTransmision(){
		var nifSegundoCotitularTransmitente = document.getElementById("nifSegundoCotitularTransmitente").value;
		if (nifSegundoCotitularTransmitente!=null && nifSegundoCotitularTransmitente!="") {
			document.getElementById("idDireccionSegundoCotitularTransmitente").value = "";
			document.getElementById("tipoPersonaSegundoCotitularTransmitente").value = "-1";
			document.getElementById("sexoSegundoCotitularTransmitente").value = "-1";
			document.getElementById("idApellidoRazonSocialSegundoCotitularTransmitente").value = "";
			document.getElementById("idApellido2SegundoCotitularTransmitente").value = "";
			document.getElementById("idNombreSegundoCotitularTransmitente").value = "";
			document.getElementById("diaNacSegundoCotitularTransmitente").value = "";
			document.getElementById("mesNacSegundoCotitularTransmitente").value = "";
			document.getElementById("anioNacSegundoCotitularTransmitente").value = "";
			document.getElementById("idTelefonoSegundoCotitularTransmitente").value = "";
			document.getElementById("tipoViaSegundoCotitularTransmitente").value = "-1";
			document.getElementById("idProvinciaSegundoCotitularTransmitente").value = "-1";
			document.getElementById("idMunicipioSegundoCotitularTransmitente").length = 0;
			document.getElementById("idMunicipioSegundoCotitularTransmitente").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoSegundoCotitularTransmitente").value = "";
			document.getElementById("idPuebloSegundoCotitularTransmitente").length = 0;
			document.getElementById("idPuebloSegundoCotitularTransmitente").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoSegundoCotitularTransmitente").value = "";
			document.getElementById("codPostalSegundoCotitularTransmitente").value = "";
			document.getElementById("viaSegundoCotitularTransmitente").value = "";
			document.getElementById("numeroSegundoCotitularTransmitente").value = "";
			document.getElementById("letraSegundoCotitularTransmitente").value = "";
			document.getElementById("escaleraSegundoCotitularTransmitente").value = "";
			document.getElementById("pisoSegundoCotitularTransmitente").value = "";
			document.getElementById("puertaSegundoCotitularTransmitente").value = "";
			document.getElementById("bloqueSegundoCotitularTransmitente").value = "";
			document.getElementById("kmSegundoCotitularTransmitente").value = "";
			document.getElementById("hmSegundoCotitularTransmitente").value = "";
		}
	}

	function limpiarFormularioPresentadorTransmision(){
		var nifPresentador = document.getElementById("nifPresentador").value;
		if (nifPresentador!=null && nifPresentador!="") {
			document.getElementById("idDireccionPresentador").value = "";
			document.getElementById("tipoPersonaPresentador").value = "-1";
			document.getElementById("sexoPresentador").value = "-1";
			document.getElementById("idApellidoRazonSocialPresentador").value = "";
			document.getElementById("idApellido2Presentador").value = "";
			document.getElementById("idNombrePresentador").value = "";
			document.getElementById("idAnagramaPresentador").value = "";
			document.getElementById("diaNacPresentador").value = "";
			document.getElementById("mesNacPresentador").value = "";
			document.getElementById("anioNacPresentador").value = "";
			document.getElementById("idTelefonoPresentador").value = "";
			document.getElementById("tipoViaPresentador").value = "-1";
			document.getElementById("idProvinciaPresentador").value = "-1";
			document.getElementById("idMunicipioPresentador").length = 0;
			document.getElementById("idMunicipioPresentador").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoPresentador").value = "";
			document.getElementById("idPuebloPresentador").length = 0;
			document.getElementById("idPuebloPresentador").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoPresentador").value = "";
			document.getElementById("codPostalPresentador").value = "";
			document.getElementById("viaPresentador").value = "";
			document.getElementById("numeroPresentador").value = "";
			document.getElementById("letraPresentador").value = "";
			document.getElementById("escaleraPresentador").value = "";
			document.getElementById("pisoPresentador").value = "";
			document.getElementById("puertaPresentador").value = "";
			document.getElementById("bloquePresentador").value = "";
			document.getElementById("kmPresentador").value = "";
			document.getElementById("hmPresentador").value = "";
		}
	}

	function limpiarFormularioAdquirienteTransmision(){
		var nifPresentador = document.getElementById("nifAdquiriente").value;
		if (nifPresentador!=null && nifPresentador!="") {
			document.getElementById("idDireccionAdquiriente").value = "";
			document.getElementById("tipoPersonaAdquiriente").value = "-1";
			document.getElementById("sexoAdquiriente").value = "-1";
			document.getElementById("idApellidoRazonSocialAdquiriente").value = "";
			document.getElementById("idApellido2Adquiriente").value = "";
			document.getElementById("idNombreAdquiriente").value = "";
			document.getElementById("idAnagramaAdquiriente").value = "";
			document.getElementById("diaNacAdquiriente").value = "";
			document.getElementById("mesNacAdquiriente").value = "";
			document.getElementById("anioNacAdquiriente").value = "";
			document.getElementById("idTelefonoAdquiriente").value = "";
			document.getElementById("tipoViaAdquiriente").value = "-1";
			document.getElementById("idProvinciaAdquiriente").value = "-1";
			document.getElementById("idMunicipioAdquiriente").length = 0;
			document.getElementById("idMunicipioAdquiriente").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoAdquiriente").value = "";
			document.getElementById("idPuebloAdquiriente").length = 0;
			document.getElementById("idPuebloAdquiriente").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoAdquiriente").value = "";
			document.getElementById("codPostalAdquiriente").value = "";
			document.getElementById("viaAdquiriente").value = "";
			document.getElementById("numeroAdquiriente").value = "";
			document.getElementById("letraAdquiriente").value = "";
			document.getElementById("escaleraAdquiriente").value = "";
			document.getElementById("pisoAdquiriente").value = "";
			document.getElementById("puertaAdquiriente").value = "";
			document.getElementById("bloqueAdquiriente").value = "";
			document.getElementById("kmAdquiriente").value = "";
			document.getElementById("hmAdquiriente").value = "";
		}

		//Si tiene conductor habitual también lo limpia
		var nifPresentador = document.getElementById("nifConductorHabitual").value;
		if (nifPresentador!=null && nifPresentador!="") {
			document.getElementById("nifConductorHabitualAdquiriente").value = "";
			document.getElementById("idApellido1ConductorHabitualAdquiriente").value = "";
			document.getElementById("idApellido2ConductorHabitualAdquiriente").value = "";
			document.getElementById("idNombreConductorHabitualAdquiriente").value = "";
			document.getElementById("diaNacConductorHabitualAdquiriente").value = "";
			document.getElementById("mesNacConductorHabitualAdquiriente").value = "";
			document.getElementById("anioNacConductorHabitualAdquiriente").value = "";

			document.getElementById("diaInicioConductorHabitualAdquiriente").value = "";
			document.getElementById("mesInicioConductorHabitualAdquiriente").value = "";
			document.getElementById("anioInicioConductorHabitualAdquiriente").value = "";

			document.getElementById("diaFinConductorHabitualAdquiriente").value = "";
			document.getElementById("mesFinConductorHabitualAdquiriente").value = "";
			document.getElementById("anioFinConductorHabitualAdquiriente").value = "";
		}

		//Si tiene representante del adquiriente también lo limpia
		var nifPresentador = document.getElementById("nifRepresentanteAdquiriente").value;
		if (nifPresentador!=null && nifPresentador!="") {
			document.getElementById("nifRepresentanteAdquiriente").value = "";
			document.getElementById("apellido1RepresentanteAdquiriente").value = "";
			document.getElementById("apellido2RepresentanteAdquiriente").value = "";
			document.getElementById("idNombreRepresentanteAdquiriente").value = "";

			document.getElementById("conceptoRepresentanteAdquiriente").value = "-1";
			document.getElementById("motivoRepresentanteAdquiriente").value = "-1";
			document.getElementById("datosAcrediteRepresentanteAdquiriente").value = "";

			document.getElementById("diaInicioRepresentanteAdquiriente").value = "";
			document.getElementById("mesInicioRepresentanteAdquiriente").value = "";
			document.getElementById("anioInicioRepresentanteAdquiriente").value = "";

			document.getElementById("diaFinRepresentanteAdquiriente").value = "";
			document.getElementById("mesFinRepresentanteAdquiriente").value = "";
			document.getElementById("anioInicioRepresentanteAdquiriente").value = "";
		}

	}

	function limpiarFormularioPoseedorTransmision(){
		if(document.getElementById("nifPoseedor") != null){
			var nifPoseedor = document.getElementById("nifPoseedor").value;
			if (nifPoseedor!=null && nifPoseedor!="") {
				document.getElementById("idDireccionPoseedor").value = "";
				document.getElementById("tipoPersonaPoseedor").value = "-1";
				document.getElementById("sexoPoseedor").value = "-1";
				document.getElementById("idApellidoRazonSocialPoseedor").value = "";
				document.getElementById("idApellido2Poseedor").value = "";
				document.getElementById("idNombrePoseedor").value = "";
				document.getElementById("idAnagramaPoseedor").value = "";
				document.getElementById("diaNacPoseedor").value = "";
				document.getElementById("mesNacPoseedor").value = "";
				document.getElementById("anioNacPoseedor").value = "";
				document.getElementById("idTelefonoPoseedor").value = "";
				document.getElementById("tipoViaPoseedor").value = "-1";
				document.getElementById("idProvinciaPoseedor").value = "-1";
				document.getElementById("idMunicipioPoseedor").length = 0;
				document.getElementById("idMunicipioPoseedor").options[0] = new Option("Seleccione Municipio", "", true, true);
				document.getElementById("municipioSeleccionadoPoseedor").value = "";
				document.getElementById("idPuebloPoseedor").length = 0;
				document.getElementById("idPuebloPoseedor").options[0] = new Option("Seleccione Pueblo", "", true, true);
				document.getElementById("puebloSeleccionadoPoseedor").value = "";
				document.getElementById("codPostalPoseedor").value = "";
				document.getElementById("viaPoseedor").value = "";
				document.getElementById("numeroPoseedor").value = "";
				document.getElementById("letraPoseedor").value = "";
				document.getElementById("escaleraPoseedor").value = "";
				document.getElementById("pisoPoseedor").value = "";
				document.getElementById("puertaPoseedor").value = "";
				document.getElementById("bloquePoseedor").value = "";
				document.getElementById("kmPoseedor").value = "";
				document.getElementById("hmPoseedor").value = "";
			}
		}
	}

	function limpiarFormularioRepresentantePoseedorTransmision(){
		var nifPoseedor = document.getElementById("nifRepresentantePoseedor").value;
		if (nifPoseedor!=null && nifPoseedor!="") {
			document.getElementById("idDireccionRepresentantePoseedor").value = "";
			document.getElementById("apellido1RepresentantePoseedor").value = "";
			document.getElementById("apellido2RepresentantePoseedor").value = "";
			document.getElementById("idNombreRepresentantePoseedor").value = "";
			document.getElementById("conceptoRepresentantePoseedor").value = "-1";
			document.getElementById("motivoRepresentantePoseedor").value = "-1";
			document.getElementById("motivoRepresentantePoseedor").disabled = true;
			document.getElementById("datosAcrediteRepresentantePoseedor").value = "";
			document.getElementById("diaInicioRepresentantePoseedor").value = "";
			document.getElementById("mesInicioRepresentantePoseedor").value = "";
			document.getElementById("anioInicioRepresentantePoseedor").value = "";
			document.getElementById("diaFinRepresentantePoseedor").value = "";
			document.getElementById("mesFinRepresentantePoseedor").value = "";
			document.getElementById("anioFinRepresentantePoseedor").value = "";
		}
	}

	function limpiarFormularioArrendatarioTransmision(){
		var nifArrendatario = document.getElementById("nifArrendatario").value;
		if (nifArrendatario!=null && nifArrendatario!="") {
			document.getElementById("idDireccionArrendatario").value = "";
			document.getElementById("tipoPersonaArrendatario").value = "-1";
			document.getElementById("sexoArrendatario").value = "-1";
			document.getElementById("idApellidoRazonSocialArrendatario").value = "";
			document.getElementById("idApellido2Arrendatario").value = "";
			document.getElementById("idNombreArrendatario").value = "";
			document.getElementById("diaNacArrendatario").value = "";
			document.getElementById("mesNacArrendatario").value = "";
			document.getElementById("anioNacArrendatario").value = "";
			document.getElementById("idTelefonoArrendatario").value = "";
			document.getElementById("tipoViaArrendatario").value = "-1";
			document.getElementById("idProvinciaArrendatario").value = "-1";
			document.getElementById("idMunicipioArrendatario").length = 0;
			document.getElementById("idMunicipioArrendatario").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoArrendatario").value = "";
			document.getElementById("idPuebloArrendatario").length = 0;
			document.getElementById("idPuebloArrendatario").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoArrendatario").value = "";
			document.getElementById("codPostalArrendatario").value = "";
			document.getElementById("viaArrendatario").value = "";
			document.getElementById("numeroArrendatario").value = "";
			document.getElementById("letraArrendatario").value = "";
			document.getElementById("escaleraArrendatario").value = "";
			document.getElementById("pisoArrendatario").value = "";
			document.getElementById("puertaArrendatario").value = "";
			document.getElementById("bloqueArrendatario").value = "";
			document.getElementById("kmArrendatario").value = "";
			document.getElementById("hmArrendatario").value = "";
			document.getElementById("diaInicioArrendatario").value = "";
			document.getElementById("mesInicioArrendatario").value = "";
			document.getElementById("anioInicioArrendatario").value = "";
			document.getElementById("idHoraInicioArrendatario").value = "";
			document.getElementById("diaFinArrendatario").value = "";
			document.getElementById("mesFinArrendatario").value = "";
			document.getElementById("anioFinArrendatario").value = "";
			document.getElementById("idHoraFinArrendatario").value = "";
		}
	}

	function limpiarFormularioRepresentanteArrendatarioTransmision(){
		var nifRepresentanteArrendatario = document.getElementById("nifRepresentanteArrendatario").value;
		if (nifRepresentanteArrendatario!=null && nifRepresentanteArrendatario!="") {
			document.getElementById("idDireccionRepresentanteArrendatario").value = "";
			document.getElementById("apellido1RepresentanteArrendatario").value = "";
			document.getElementById("apellido2RepresentanteArrendatario").value = "";
			document.getElementById("idNombreRepresentanteArrendatario").value = "";
			document.getElementById("conceptoRepresentanteArrendatario").value = "-1";
			document.getElementById("motivoRepresentanteArrendatario").value = "-1";
			document.getElementById("motivoRepresentanteArrendatario").disabled = true;
			document.getElementById("datosAcrediteRepresentanteArrendatario").value = "";
			document.getElementById("diaInicioRepresentanteArrendatario").value = "";
			document.getElementById("mesInicioRepresentanteArrendatario").value = "";
			document.getElementById("anioInicioRepresentanteArrendatario").value = "";
			document.getElementById("diaFinRepresentanteArrendatario").value = "";
			document.getElementById("mesFinRepresentanteArrendatario").value = "";
			document.getElementById("anioFinRepresentanteArrendatario").value = "";
		}
	}

	//////////////////////////////////////
	////LIMPIEZA DE DATOS DEL VEHÍCULO////
	//////////////////////////////////////

	function limpiarFormularioVehiculoTransmision(){
		var id = document.getElementById("idVehiculo").value;
		if (id!=null && id!="") {
			document.getElementById("idDireccionVehiculo").value = "";
			document.getElementById("idBastidor").value = "";
			document.getElementById("idTipoVehiculo").value = "-1";
			document.getElementById("idMarcaVehiculo").value = "-1";
			document.getElementById("idModeloVehiculo").value = "";
			document.getElementById("diaMatriculacion").value = "";
			document.getElementById("mesMatriculacion").value = "";
			document.getElementById("anioMatriculacion").value = "";
			if(document.getElementById("idServicioTraficoAnterior")){
				document.getElementById("idServicioTraficoAnterior").value = "-1";
			}
			document.getElementById("idServicioTrafico").value = "-1";
			if(document.getElementById("vehiculoAgricola")){
				document.getElementById("vehiculoAgricola").checked = false;
			}
			document.getElementById("motivosITV").value = "-1";
			document.getElementById("conceptoITV").value = "-1";
			document.getElementById("diaInspeccion").value = "";
			document.getElementById("mesInspeccion").value = "";
			document.getElementById("anioInspeccion").value = "";
			document.getElementById("diaITV").value = "";
			document.getElementById("mesITV").value = "";
			document.getElementById("anioITV").value = "";
			document.getElementById("estacionITV").value = "-1";
			document.getElementById("idDeclaracionResponsabilidad").checked = false;

			if (document.getElementById("idResponsabilidadLocalizacion")){
				document.getElementById("idResponsabilidadLocalizacion").checked = false;
			}

			document.getElementById("idProvinciaVehiculo").value = "-1";
			document.getElementById("idMunicipioVehiculo").length = 0;
			document.getElementById("idMunicipioVehiculo").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoVehiculo").value = "";
			document.getElementById("idPuebloVehiculo").length = 0;
			document.getElementById("idPuebloVehiculo").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoVehiculo").value = "";
			document.getElementById("codPostalVehiculo").value = "";
			document.getElementById("tipoViaVehiculo").value = "-1";
			document.getElementById("viaVehiculo").value = "";
			document.getElementById("numeroVehiculo").value = "";
			document.getElementById("letraVehiculo").value = "";
			document.getElementById("escaleraVehiculo").value = "";
			document.getElementById("pisoVehiculo").value = "";
			document.getElementById("puertaVehiculo").value = "";
			document.getElementById("bloqueVehiculo").value = "";
			document.getElementById("kmVehiculo").value = "";
			document.getElementById("hmVehiculo").value = "";
			document.getElementById("tipoVehiculo620").value = "-1";
			document.getElementById("diaPrimeraMatriculacion").value = "";
			document.getElementById("mesPrimeraMatriculacion").value = "";
			document.getElementById("anioPrimeraMatriculacion").value = "";
			document.getElementById("diaDevengo").value = "";
			document.getElementById("mesDevengo").value = "";
			document.getElementById("anioDevengo").value = "";
			document.getElementById("idMarcaCam").length = 0;
			document.getElementById("idMarcaCam").options[0] = new Option("Seleccione Marca", "", true, true);
			document.getElementById("marcaCamSeleccionada").value = "";
			document.getElementById("idModeloCam").length = 0;
			document.getElementById("idModeloCam").options[0] = new Option("Seleccione Modelo", "", true, true);
			document.getElementById("modeloCamSeleccionado").value = "";
			document.getElementById("potenciaFiscal620").value = "";
			document.getElementById("cilindrada620").value = "";
			document.getElementById("nCilindros620").value = "";
			document.getElementById("caracteristicas620").value = "";
			document.getElementById("carburante620").value = "-1";
			document.getElementById("porcentajeReduccionAnual").value = "";
			document.getElementById("valorDeclarado620").value = "";
			document.getElementById("idPorcentajeAdquisicion620").value = "";
			document.getElementById("baseImponible620").value = "";
			document.getElementById("tipoGravamen620").value = "";
			document.getElementById("cuotaTributaria620").value = "";
			document.getElementById("direccionDistintaAdquiriente620").checked = false;
			if(document.getElementById("idFechaCaducidadITV")){
				document.getElementById("idFechaCaducidadITV").checked = false;
			}
		}
	}

	function iniciarPestaniaTramiteTransmision(){
		//JMC: Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
		if (document.getElementById("permisoEspecial").value == "false") {
			cambioModoAdjudicacionSinImpuestos(); //Si el modo de adjudicacion es fallecimiento o donacion del titular, el combo de acreditación debe estar habilitado
			cargarTipoTasaModoAdjudicacion();
		}
		anyadeTasaSiNoExiste('codigoTasaTransmisionId', 'codigoTasaTransmisionSeleccionadoId');

			//JMC: Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
		if (document.getElementById("permisoEspecial").value == "false") {
			cambioCambioServicio();
			habilitaCodigoTasaSiTipoTasaTransmision();

			// DRC@08-10-2012 Incidencia:2549 
			seleccionaModoAdjudicacion();
		}
		initTasaInforme();
	}

	// Recarga el combo cuando se recarga la página
	function recargarNombreVias(id_select_provincia, id_select_municipio_seleccionado,id_select_tipoVia, id_select_nombre_via,via){
		var selectProvincia = document.getElementById(id_select_provincia);
		var selectTipoVia = document.getElementById(id_select_tipoVia);

		var selectNombreVia = document.getElementById(id_select_nombre_via);
		if(selectProvincia != null && selectProvincia.selectedIndex >0){
			if(document.getElementById(id_select_municipio_seleccionado).value > 0){
				if (selectTipoVia!=null && selectTipoVia.value != ''){
					obtenerNombreViasPorProvinciaMunicipioYTipoVia(selectProvincia,id_select_municipio_seleccionado,selectTipoVia, selectNombreVia,via); 
				}
			}
		}
	}

	function iniciarPestaniaAdquirienteTransmision(){
		habilitarAutonomo('autonomoIdAdquiriente','codigoIAEAdquiriente');
		recargarComboMunicipios('idProvinciaAdquiriente','idMunicipioAdquiriente','municipioSeleccionadoAdquiriente');
		recargarComboTipoVia('idProvinciaAdquiriente','tipoViaAdquiriente','tipoViaSeleccionadaAdquiriente');
		recargarComboPueblos('idProvinciaAdquiriente','municipioSeleccionadoAdquiriente','idPuebloAdquiriente', 'puebloSeleccionadoAdquiriente');
		cambioAutonomoAdquiriente(); //Si el check de autónomo está marcado, el campo Codigo IAE debe estar habilitado
		cambioConceptoRepreAdquiriente(); //Si el combo de concepto es TUTELA O PATRIA POTESTAD, el combo de motivo debe estar habilitado
		recargarNombreVias('idProvinciaAdquiriente', 'municipioSeleccionadoAdquiriente', 'tipoViaSeleccionadaAdquiriente','viaAdquiriente',viaAdquirienteTransmision);
	}

	function iniciarPestaniaTransmitenteTransmision(){
		habilitarAutonomo('autonomoIdTransmitente','codigoIAETransmitente');
		recargarComboMunicipios('idProvinciaTransmitente','idMunicipioTransmitente','municipioSeleccionadoTransmitente');
		recargarComboTipoVia('idProvinciaTransmitente','tipoViaTransmitente','tipoViaSeleccionadaTransmitente');
		recargarComboPueblos('idProvinciaTransmitente','municipioSeleccionadoTransmitente','idPuebloTransmitente', 'puebloSeleccionadoTransmitente');
		cambioConceptoRepreTransmitente('conceptoRepresentanteTransmitente','motivoRepresentanteTransmitente');
		cambioConceptoRepreTransmitente('conceptoRepresentantePrimerCotitularTransmitente','motivoRepresentantePrimerCotitularTransmitente');
		cambioConceptoRepreTransmitente('conceptoRepresentanteSegundoCotitularTransmitente','motivoRepresentanteSegundoCotitularTransmitente');

		recargarNombreVias('idProvinciaTransmitente', 'municipioSeleccionadoTransmitente', 'tipoViaSeleccionadaSeleccionadaTransmitente','viaTransmitente',viaTransmitenteTransmision);
		cambioAutonomoTransmitente(); //Si el check de autónomo está marcado, el campo Codigo IAE debe estar habilitado

		recargarComboMunicipios('idProvinciaPrimerCotitularTransmitente','idMunicipioPrimerCotitularTransmitente','municipioSeleccionadoPrimerCotitularTransmitente');
		recargarComboTipoVia('idProvinciaPrimerCotitularTransmitente','tipoViaPrimerCotitularTransmitente','tipoViaSeleccionadaPrimerCotitularTransmitente');
		recargarComboPueblos('idProvinciaPrimerCotitularTransmitente','municipioSeleccionadoPrimerCotitularTransmitente','idPuebloPrimerCotitularTransmitente', 'puebloSeleccionadoPrimerCotitularTransmitente');
		recargarNombreVias('idProvinciaPrimerCotitularTransmitente', 'municipioSeleccionadoPrimerCotitularTransmitente', 'tipoViaSeleccionadaSeleccionadaPrimerCotitularTransmitente','viaPrimerCotitularTransmitente', viaPrimerCotitularTransmitenteTransmision);
		//cambioConceptoReprePrimerCotitularTransmitente(); //Si el combo de concepto es TUTELA O PATRIA POTESTAD, el combo de motivo debe estar habilitado

		recargarComboMunicipios('idProvinciaSegundoCotitularTransmitente','idMunicipioSegundoCotitularTransmitente','municipioSeleccionadoSegundoCotitularTransmitente');
		recargarComboTipoVia('idProvinciaSegundoCotitularTransmitente','tipoViaSegundoCotitularTransmitente','tipoViaSeleccionadaSegundoCotitularTransmitente');
		recargarComboPueblos('idProvinciaSegundoCotitularTransmitente','municipioSeleccionadoSegundoCotitularTransmitente','idPuebloSegundoCotitularTransmitente', 'puebloSeleccionadoSegundoCotitularTransmitente');
		recargarNombreVias('idProvinciaSegundoCotitularTransmitente', 'municipioSeleccionadoSegundoCotitularTransmitente', 'tipoViaSeleccionadaSeleccionadaSegundoCotitularTransmitente','viaSegundoCotitularTransmitente',viaSegundoCotitularTransmitenteTransmision);
		//cambioConceptoRepreSegundoCotitularTransmitente(); //Si el combo de concepto es TUTELA O PATRIA POTESTAD, el combo de motivo debe estar habilitado
	}

	function iniciarPestaniaPresentadorTransmision(){
		recargarComboMunicipios('idProvinciaPresentador','idMunicipioPresentador','municipioSeleccionadoPresentador');
		recargarComboTipoVia('idProvinciaPresentador','tipoViaPresentador','tipoViaSeleccionadaPresentador');
		recargarComboPueblos('idProvinciaPresentador','municipioSeleccionadoPresentador','idPuebloPresentador', 'puebloSeleccionadoPresentador');
		recargarNombreVias('idProvinciaPresentador', 'municipioSeleccionadoPresentador', 'tipoViaSeleccionadaPresentador','viaPresentador', viaPresentadorTransmision);
	}

	function iniciarPestaniaCompraventaTransmision(){
		recargarComboMunicipios('idProvinciaPoseedor','idMunicipioPoseedor','municipioSeleccionadoPoseedor');
		recargarComboTipoVia('idProvinciaPoseedor','tipoViaPoseedor','tipoViaSeleccionadaPoseedor');
		recargarComboPueblos('idProvinciaPoseedor','municipioSeleccionadoPoseedor','idPuebloPoseedor', 'puebloSeleccionadoPoseedor');
		recargarNombreVias('idProvinciaPoseedor', 'municipioSeleccionadoPoseedor', 'tipoViaSeleccionadaPoseedor','viaPoseedor',viaPoseedorTransmision);
	}

	function iniciarPestaniaPagoImpuestosTransmision(){
		//JMC : Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
		if (document.getElementById("permisoEspecial").value == "false") {
			habilitarCEMA();
			habilitaImpuestos();
			cambioExentoIedtm();
			cambioNoSujetoIedtm();
		}

		//JMC: Incidencia Mantis 3020 3187 Usuario Especial necesita tener deshabilitado todo
		if (document.getElementById("permisoEspecial").value == "false") {
			if(document.getElementById("exentoItp").checked){
				document.getElementById("exento620").checked="true";
				cambioExento();
			}
			if(document.getElementById("noSujetoItp").checked){
				document.getElementById("noSujeto620").checked="true";
				cambioNoSujeto();
			}
		}
		//Mantis 25415
		deshabilitarValorReal();
	}

	/** *********** VALIDA EL MODELO 620 *********** */
	function validar620(pestania) {
		var regExp = /^\d{0,5}(?:\.\d{1,2})?$/
		habilitarCamposDeshabilitadosTransmision();
		if(!regExp.test(document.getElementById("cilindrada620").value)){
			alert("Introduzca un formato correcto de cilindrada")
			return false;
		}	
		document.formData.action = "validar620AltasTramiteTransmision.action#"
			+ pestania;
		document.formData.submit();
		// TODO: Crear función
		//loadingValidar620();
		return true;
	}

	/** *********** VALIDA EL MODELO 620 ACTUAL, SIN CTIT *********** */
	function validar620Actual(pestania) {
		habilitarCamposDeshabilitadosTransmisionActual();
		document.formData.action = "validar620AltasTramiteTransmisionActual.action#"
				+ pestania;
		document.formData.submit();
		// TODO: Crear función
		//loadingValidar620();
		return true;
	}

	/** *********** VALIDA EL MODELO 620 *********** */
	function generarXML(pestania) {
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "generarXMLAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		loadingGenerarXML();
		setTimeout("unLoadingGenerarXML()", 2000);
		setTimeout("deshabilitarCamposDeshabilitadosTransmision()", 1000);
		return true;
	}

	/** *********** VALIDA EL MODELO 620 ACTUAL, SIN CTIT *********** */
	function generarXMLActual(pestania) {
		habilitarCamposDeshabilitadosTransmisionActual();
		document.formData.action = "generarXMLAltasTramiteTransmisionActual.action#"
				+ pestania;
		document.formData.submit();
		loadingGenerarXML();

		return true;
	}

	// Llama al action que muestra el documento cargado en sesion.
	function obtenerDocumento620(pestania) {
		if (tabsIniciadas) {
			var actionPoint = "obtenerFichero620AltasTramiteTransmision.action#"
					+ pestania;

			document.forms[0].action = actionPoint;
			document.forms[0].submit();
		} else {
			setTimeout("obtenerDocumento620()", 1000);
		}
	}

	function validarYGenerarJustificanteProAltaTransmision(pestania) {
		ocultarLoadingConsultarTransmision();
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "validarYGenerarJustificanteProAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		return true;
	}

	function ocultarLoadingConsultarTransmision() {
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

	function generarJustificanteProAltaTransmision(pestania) {
		habilitarCamposDeshabilitadosTransmision();
		document.formData.action = "generarJustificanteProAltasTramiteTransmision.action#"
				+ pestania;
		document.formData.submit();
		return true;
	}

	function validarYGenerarJustificanteProAltaTransmisionActual(pestania) {
		ocultarLoadingConsultarTransmision();
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

	// Recarga el combo cuando se recarga la página
	function recargarComboMarcas(id_select_tipoVehiculo,id_anioDevengo,id_select_marca,id_marcaSeleccionada){
		if(document.getElementById(id_anioDevengo).value!="" &&
				(document.getElementById(id_select_tipoVehiculo).value=="A" ||
				document.getElementById(id_select_tipoVehiculo).value=="B")){
			obtenerMarcasPorTipoVehiculoYFechaDevengo(document.getElementById(id_select_tipoVehiculo), id_anioDevengo, document.getElementById(id_select_marca), id_marcaSeleccionada);
			var marcaSelec = document.getElementById(id_marcaSeleccionada).value;
			if (marcaSelec != null && marcaSelec != "") {
				recargarComboModelos('idMarcaCam','marcaCamSeleccionada','tipoVehiculo620','anioDevengo','idModeloCam','modeloCamSeleccionado');
			}
		} else {
			$("#idMarcaCamText").val($("#marcaCamSeleccionada").val());
			$("#idModeloCamText").val($("#modeloCamSeleccionado").val());
		}
	}

	function cargarListaMarcas(id_select_tipoVehiculo, id_anioDevengo, id_select_marca,id_marcaSeleccionada){
		if((document.getElementById(id_select_tipoVehiculo).value == "A" ||
			document.getElementById(id_select_tipoVehiculo).value == "B")) {
			//Mostramos los combos
			document.getElementById('idMarcaCam').style.display="block";
			document.getElementById('idModeloCam').style.display="block";
			//Ocultamos el textfield de las marcas y modelos
			document.getElementById('idMarcaCamText').style.display="none";
			document.getElementById('idModeloCamText').style.display="none";
			//Seteamos los valores por defecto
			document.getElementById(id_marcaSeleccionada).value = "-1";
			document.getElementById("modeloCamSeleccionado").value = "-1";
		} else {
			//Ocultamos los combos
			document.getElementById('idMarcaCam').style.display="none";
			document.getElementById('idModeloCam').style.display="none";
			//Mostramos el textfield de las marcas y modelos
			document.getElementById('idMarcaCamText').style.display="block";
			document.getElementById('idModeloCamText').style.display="block";
			//Seteamos los valores por defecto
			document.getElementById(id_marcaSeleccionada).value = "";
			document.getElementById("modeloCamSeleccionado").value = "";
		}
		//Hacemos la llamada AJAX en caso de Turismo o Todoterreno y Fecha de devengo
		if((document.getElementById(id_select_tipoVehiculo).value == "A" ||
			document.getElementById(id_select_tipoVehiculo).value == "B") &&
			document.getElementById(id_anioDevengo).value != ""){ //Si el tipo de vehículo no es Turismo o Todoterreno o el año de devengo no están relleno, no se realiza la llamada AJAX
			obtenerMarcasPorTipoVehiculoYFechaDevengo(document.getElementById(id_select_tipoVehiculo),id_anioDevengo,document.getElementById(id_select_marca),id_marcaSeleccionada);
		}
	}

	function obtenerMarcasPorTipoVehiculoYFechaDevengo(select_tipoVehiculo,id_anioDevengo,selectMarca,id_marcaSeleccionada){
		//Sin elementos
		if(select_tipoVehiculo.selectedIndex==0){
			selectMarca.length = 0;
			selectMarca.options[0] = new Option("Seleccione Marca", "");
			return;
		}
		selectedOption = select_tipoVehiculo.options[select_tipoVehiculo.selectedIndex].value;
		url="recuperarMarcasCamPorTipoVehiculoYFechaDevengoTraficoAjax.action?tipoVehiculoSeleccionado="+selectedOption+"&anioDevengo="+document.getElementById("anioDevengo").value;
		var req_generico_marca = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_marca.onreadystatechange = function() { 
					rellenarListaMarcas(req_generico_marca,selectMarca,id_marcaSeleccionada);
				};
		req_generico_marca.open("POST", url, true);
		req_generico_marca.send(null);
	}

	function rellenarListaMarcas(req_generico_marca,selectMarca,id_marcaSeleccionada){
		selectMarca.options.length = 0;

		if (req_generico_marca.readyState == 4) { // Complete
			if (req_generico_marca.status == 200) { // OK response
				textToSplit = req_generico_marca.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				selectMarca.options[0] = new Option("Seleccione Marca", "");
				//Process each of the elements
				var marcaSeleccionada = document.getElementById(id_marcaSeleccionada).value;
				for ( var i=0; i<returnElements.length; i++ ){
					value = returnElements[i].split(";");
					if (value[1]==undefined) value[1]=" ";
					selectMarca.options[i+1] = new Option(value[0], value[1]);
					if(selectMarca.options[i+1].value == marcaSeleccionada){
						selectMarca.options[i+1].selected = true;
					}
				}
			}
		}
	}

function cargarPorcentajeReduccion(idSelectReduccion, idPorcentajeReduccion){
	obtenerPorcentajeReduccion(document.getElementById(idSelectReduccion), document.getElementById(idPorcentajeReduccion));
}

function obtenerPorcentajeReduccion(selectCodigoReduccion, inputPorcentaje) {
	//Sin elementos
	if(selectCodigoReduccion.selectedIndex == 0){
		inputPorcentaje.value = 0;
		calcularReduccion620();
		return;
	}
	selectedOption = selectCodigoReduccion.options[selectCodigoReduccion.selectedIndex].value;
	url = "recuperarPorcentajeReduccionTraficoAjax.action?tipoReduccionSeleccionado="+selectedOption;
	var reqReduccion = NuevoAjax();
	//Hace la llamada AJAX
	reqReduccion.onreadystatechange = function() {
		rellenarPorcentajeReduccion(reqReduccion, inputPorcentaje);
	};
	reqReduccion.open("POST", url, true);
	reqReduccion.send(null);
}

function rellenarPorcentajeReduccion(reqReduccion, inputPorcentaje){
	inputPorcentaje.value = 0;
	if (reqReduccion.readyState == 4 && reqReduccion.status == 200) { // Complete (4) - OK response (200)
		reduccion = reqReduccion.responseText;
		reduccionValues = reduccion.split(";");
		inputPorcentaje.value = reduccionValues[1];
	}
	calcularReduccion620();
}

function checkImpresoPermiso() {
	var seleccionTipo = 0;
	if (document.getElementById('tipoTransferenciaId') != null){
		seleccionTipo = $('#tipoTransferenciaId').val();
	}

	if(seleccionTipo == "5") {
		document.getElementById('impresionPermisoId').checked = false;
		document.getElementById('impresionPermisoId').disabled = true;
	}else if(seleccionTipo == "4") {
		document.getElementById('impresionPermisoId').checked = false;
		document.getElementById('impresionPermisoId').disabled = true;
	} else {
		document.getElementById('impresionPermisoId').disabled = false;
	}
}

	function noEmpezarPorComas(){
		if(document.getElementById('valorReal').value == '.' ||
				document.getElementById('valorReal').value == ','){
			document.getElementById('valorReal').value= "";
		}
	}

	//Mantis 25415
	function ponerComasCamposBigDecimal(){
		document.getElementById('valorReal').value = document.getElementById('valorReal').value.replace(",", "."); 
	}
	//Mantis 25415
	function llamarValidarDosDecimalesValorReal(cadena){
		/*if(!validarDosDecimalesValorReal(document.getElementById('valorReal').value) && 
					document.getElementById('nFactura').value == ""){
				alert("El valor real de la transmisi\u00f3n debe ser superior a cero y con un m\u00E1ximo de 6 enteros y 2 decimales");
				document.getElementById('valorReal').value = "";
			}
			document.getElementById('valorReal').value = document.getElementById('valorReal').value.replace(",", ".");
		}

		if(document.getElementByID('valorReal').value=="" &&
				document.getElementById('nFactura').value != "" &&
				!validarDosDecimalesValorReal(document.getElementById('valorReal').value)){
				alert("El valor real de la transmisi\u00f3n debe ser superior a cero y con un m\u00E1ximo de 6 enteros y 2 decimales");
				document.getElementById('valorReal').value = "";
		}*/
		//Si no hubiera factura, se valida el valor real	
		if(document.getElementById('nFactura').value == ""){
			if(!validarDosDecimalesValorReal(document.getElementById('valorReal').value)){
				alert("El valor real de la transmisi\u00f3n debe ser superior a cero y con un m\u00E1ximo de 6 enteros y 2 decimales");
				document.getElementById('valorReal').value = "";
			}
		}else{
		//Si hubiera factura, se valida el valor real sólo si el campo no estuviera vacío.
			if(document.getElementById('valorReal').value!="" &&
					!validarDosDecimalesValorReal(document.getElementById('valorReal').value)){
				alert("El valor real de la transmisi\u00f3n debe ser superior a cero y con un m\u00E1ximo de 6 enteros y 2 decimales");
				document.getElementById('valorReal').value = "";
			}
		}
		document.getElementById('valorReal').value = document.getElementById('valorReal').value.replace(",", ".");
	}
	//Mantis 25415
	function validarDosDecimalesValorReal(cadena){
		var prueba1 = /^\d{0,6}(\.\d{1})?\d{0,1}$/;
		var prueba2 = /^\d{0,6}(\,\d{1})?\d{0,1}$/;
		var valorCero1 = "0.00";
		var valorCero2 = "0.0";
		var valorCero3 = "0";

		return ((prueba1).test(cadena)
		|| (prueba2).test(cadena)) &&
		valorCero1 != cadena &&
		valorCero2 != cadena &&
		valorCero3 != cadena &&
		cadena!="";
	}

	function deshabilitarValorReal(){
		if(document.getElementById("nFactura").value!=""){
			document.getElementById("valorReal").disabled=true;
			document.getElementById("valorReal").value="0.00";
		}else{
			document.getElementById("valorReal").disabled=false;
		}
	}

	function generarJustificanteTransmision(pestaña){
		habilitarCamposDeshabilitadosTransmision();
		var textoBoton = $("#idBotonGenerarJustificantesPro").val();
		if (confirm("\u00BFEst\u00E1 seguro de que desea generar el justificante profesional?")){

			var $dest = $("#divEmergenteJustfProfTrans");
			$.post("cargarPopUpJustificantesProfAltasTramiteTransmision.action", function(data) {
				if (data.toLowerCase().indexOf("<html")<0) {
					$dest.empty().append(data).dialog({
						modal: true,
						show: {
							effect: "blind",
							duration: 300
						},
						dialogClass: 'no-close',
						width: 600,
						buttons: {
							Generar: function() {
								var motivo = $("#idMotivoCompletar").val();
								if(motivo == ""){
									alert("Seleccione un motivo");
									return false;
								}
								var documentos = $("#idDocJustifCompletar").val();
								if(documentos == ""){
									alert("Seleccione un Documento");
									return false;
								}
								var diasValidez = $("#idDiasValidez").val();
								if(diasValidez == ""){
									alert("Debe rellenar los días de validez");
									return false;
								}
								$("#divEmergenteJustfProfTrans").dialog("close");
								loadingTransmision("idBotonGenerarJustificantesPro");
								limpiarHiddenInput("motivoJustificantes");
								input = $("<input>").attr("type", "hidden").attr("name", "motivoJustificantes").val(motivo);
								$('#formData').append($(input));
								limpiarHiddenInput("documentoJustificantes");
								input = $("<input>").attr("type", "hidden").attr("name", "documentoJustificantes").val(documentos);
								$('#formData').append($(input));
								limpiarHiddenInput("diasValidezJustificantes");
								input = $("<input>").attr("type", "hidden").attr("name", "diasValidezJustificantes").val(diasValidez);
								$('#formData').append($(input));
								$("#formData").attr("action", "validarYGenerarJustificanteProAltasTramiteTransmision.action#"+pestaña).trigger("submit");
							},
							Cerrar : function() {
								$(this).dialog("close");
							}
						}
					});
				} else {
					// Viene <html>, así que no es un modal
					var newDoc = document.open("text/html", "replace");
					newDoc.write(data);
					newDoc.close();
				}
				$(".ui-dialog-titlebar").hide();
			}).always(function() {
				unloading("loadingImage");
			});
		}
	}

	function generarJustificanteTransmisionActual(pestaña){
		habilitarCamposDeshabilitadosTransmisionActual();
		var textoBoton = $("#idBotonGenerarJustificantesPro").val();
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el justificante profesional?")){

		var $dest = $("#divEmergenteJustfProfTransActual");
		$.post("cargarPopUpJustificantesProfAltasTramiteTransmisionActual.action", function(data) {
			if (data.toLowerCase().indexOf("<html")<0) {
				$dest.empty().append(data).dialog({
					modal : true,
					show : {
						effect : "blind",
						duration : 300
					},
					dialogClass : 'no-close',
					width: 600,
					buttons : {
						Generar : function() {
							var motivo = $("#idMotivoCompletar").val();
							if(motivo == ""){
								alert("Seleccione un motivo");
								return false;
							}
							var documentos = $("#idDocJustifCompletar").val();
							if(documentos == ""){
								alert("Seleccione un Documento");
								return false;
							}
							var diasValidez = $("#idDiasValidez").val();
							if(diasValidez == ""){
								alert("Debe rellenar los días de validez");
								return false;
							}
							$("#divEmergenteJustfProfTrans").dialog("close");
							loadingTransmision("idBotonGenerarJustificantesPro");
							limpiarHiddenInput("motivoJustificantes");
							input = $("<input>").attr("type", "hidden").attr("name", "motivoJustificantes").val(motivo);
							$('#formData').append($(input));
							limpiarHiddenInput("documentoJustificantes");
							input = $("<input>").attr("type", "hidden").attr("name", "documentoJustificantes").val(documentos);
							$('#formData').append($(input));
							limpiarHiddenInput("diasValidezJustificantes");
							input = $("<input>").attr("type", "hidden").attr("name", "diasValidezJustificantes").val(diasValidez);
							$('#formData').append($(input));
							$("#formData").attr("action", "validarYGenerarJustificanteProAltasTramiteTransmisionActual.action#"+pestaña).trigger("submit");
						},
						Cerrar : function() {
							$(this).dialog("close");
						}
					}
				});
			} else {
				// Viene <html>, así que no es un modal
				var newDoc = document.open("text/html", "replace");
				newDoc.write(data);
				newDoc.close();
			}
			$(".ui-dialog-titlebar").hide();
		}).always(function() {
			unloading("loadingImage");
		});
		}
	}
	
	function incluirFichero() {
		if (document.getElementById("fichero") && document.getElementById("fichero").files.length == 0) {
			alert("Debe seleccionar un fichero para subir.");
		} else if (!esListaDeFicherosVacia("filesList")) {
			let confirmacion = window.confirm("Va a sobreescribir el fichero subido. ¿Quiere continuar?")
			if (confirmacion) {
				iniciarProcesando("botonSubirFichero", "loading3Image");
				doPost('formData', 'incluirFicheroAltasTramiteTransmision.action\u0023Documentacion', '', '');
			}
		} else {
			iniciarProcesando("botonSubirFichero", "loading3Image");
			doPost('formData', 'incluirFicheroAltasTramiteTransmision.action\u0023Documentacion', '', '');
		}
	}
	
	function esListaDeFicherosVacia(id) {
		// Obtener la referencia a la tabla
		var tabla = document.getElementById(id);
		// Iterar sobre las filas de la tabla
		for (var i = 0; i < tabla.rows.length; i++) {
			// Iterar sobre las celdas de cada fila
			for (var j = 0; j < tabla.rows[i].cells.length; j++) {
				// Obtener el contenido de la celda
				var contenidoCelda = tabla.rows[i].cells[j].innerHTML;
	
				// Comprobar si la palabra buscada está presente
				if (contenidoCelda.includes("No se han encontrado resultados")) {
					return true;
				}
			}
		}
		return false;
	}
	
	function iniciarProcesando(idBoton,idImagen) {
		document.getElementById(idBoton).disabled = "true";
		document.getElementById(idBoton).style.color = "#6E6E6E";
		document.getElementById(idBoton).value = "Procesando...";
		document.getElementById(idImagen).style.display = "block";
		$('input[type=button]').attr('disabled', true);
	}
	
	function descargarDocumentacion(nombre) {
		limpiarHiddenInput("nombreDoc");
		let input = $("<input>").attr("type", "hidden").attr("name", "nombreDoc").val(nombre);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarDocumentacion" + "AltasTramiteTransmision.action#Documentacion").trigger("submit");
	}
	
	function eliminarFichero(idFichero) {
	if (confirm("¿Está seguro de que desea borrar este fichero?")) {
		document.formData.idFicheroEliminar.value = idFichero;
		document.formData.action = "eliminarFichero" + "AltasTramiteTransmision" + ".action#Documentacion";
		document.formData.submit();
		}
	}
