var ventanaOpciones = null;

function marcarTodosConsultaTramite(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksConsultaTramite) {
		if (!document.formData.listaChecksConsultaTramite.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksConsultaTramite.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksConsultaTramite.length; i++) {
			document.formData.listaChecksConsultaTramite[i].checked = marcar;
		}
	}
}
function abrirEvolucionConsTram(numExpediente,destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvoConsultaTramTraf.action?numExpediente=" + numExpediente;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950,height: 500,
				buttons : {
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

function cambiarElementosPorPaginaConsultaTramiteTraf(){
	var $dest = $("#displayTagDivConsultaTramTraf");
	$.ajax({
		url:"navegarConsTramTraf.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaTramiteTraf").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los resultados.');
	    }
	});
}

function cambiarElementosPorPaginaEvoConsTramTraf(){
	var $dest = $("#displayTagEvoConsTramTraf");
	$.ajax({
		url:"navegarEvoConsultaTramTraf.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolConsTramTraf").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los resultados.');
	    }
	});
}

function limpiarConsultaIntern(){
	$("#idEstadoTramite").val("");
	$("#idTipoTramite").val("");
	$("#idContratoConsultaTram").val("");
	$("#idRefPropia").val("");
	$("#idNumExpediente").val("");
	$("#idDniTitular").val("");
	$("#idNifFacturacion").val("");
	$("#idPresentadoJPT").val("");
	$("#idNumBastidor").val("");
	$("#idMatricula").val("");
	$("#idNive").val("");
	$("#idTipoTasa").val("");
	$("#idEsLiberableEEFF").val("");
	$("#idConNive").val("");
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
	$("#diaFechaPresIni").val("");
	$("#mesFechaPresIni").val("");
	$("#anioFechaPresIni").val("");
	$("#diaFechaPresFin").val("");
	$("#mesFechaPresFin").val("");
	$("#anioFechaPresFin").val("");
}

function mostrarLoadingConsultaTramTraf(boton){
	bloqueaBotonesConsultaTramTraf();
	document.getElementById("bloqueLoadingConsultaTramTraf").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaTramTraf(boton, value) {
	document.getElementById("bloqueLoadingConsultaTramTraf").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesConsultaTramTraf();
}
function bloqueaBotonesConsultaTramTraf(){
	$('#idBuscar').prop('disabled', true);
	$('#idBuscar').css('color', '#6E6E6E');
	$('#idLimpiar').prop('disabled', true);
	$('#idLimpiar').css('color', '#6E6E6E');
	$('#idValidar').prop('disabled', true);
	$('#idValidar').css('color', '#6E6E6E');
	$('#idComprobar').prop('disabled', true);
	$('#idComprobar').css('color', '#6E6E6E');
	$('#idDuplicar').prop('disabled', true);
	$('#idDuplicar').css('color', '#6E6E6E');
	$('#idEliminar').prop('disabled', true);
	$('#idEliminar').css('color', '#6E6E6E');
	$('#idTramitar').prop('disabled', true);
	$('#idTramitar').css('color', '#6E6E6E');
	$('#idGenerarJustificante').prop('disabled', true);
	$('#idGenerarJustificante').css('color', '#6E6E6E');
	$('#idImprimirJustificante').prop('disabled', true);
	$('#idImprimirJustificante').css('color', '#6E6E6E');
	$('#idImpresionDocumentos').prop('disabled', true);
	$('#idImpresionDocumentos').css('color', '#6E6E6E');
	$('#idGenerarCertificado').prop('disabled', true);
	$('#idGenerarCertificado').css('color', '#6E6E6E');
	$('#idPdteExcel').prop('disabled', true);
	$('#idPdteExcel').css('color', '#6E6E6E');
	$('#idGenerarXML').prop('disabled', true);
	$('#idGenerarXML').css('color', '#6E6E6E');
	$('#idSolicitarPlacas').prop('disabled', true);
	$('#idSolicitarPlacas').css('color', '#6E6E6E');
	$('#idGenDocBase').prop('disabled', true);
	$('#idGenDocBase').css('color', '#6E6E6E');
	$('#idAsignarTasasMasiva').prop('disabled', true);
	$('#idAsignarTasasMasiva').css('color', '#6E6E6E');
	$('#idObtenerXML').prop('disabled', true);
	$('#idObtenerXML').css('color', '#6E6E6E');
	$('#idGenerarJP').prop('disabled', true);
	$('#idGenerarJP').css('color', '#6E6E6E');
	$('#idCambiarEstado').prop('disabled', true);
	$('#idCambiarEstado').css('color', '#6E6E6E');
	$('#id576').prop('disabled', true);
	$('#id576').css('color', '#6E6E6E');
	$('#idConsultaTarjetaEitv').prop('disabled', true);
	$('#idConsultaTarjetaEitv').css('color', '#6E6E6E');
	$('#idLiberarEEFFMasivo').prop('disabled', true);
	$('#idLiberarEEFFMasivo').css('color', '#6E6E6E');
	$('#idConsultaEEFF').prop('disabled', true);
	$('#idConsultaEEFF').css('color', '#6E6E6E');
	$('#idFicheroAEAT').prop('disabled', true);
	$('#idFicheroAEAT').css('color', '#6E6E6E');
	$('#idGenerarNRE06').prop('disabled', true);
	$('#idGenerarNRE06').css('color', '#6E6E6E');
	$('#idClonarTramite').prop('disabled', true);
	$('#idClonarTramite').css('color', '#6E6E6E');
	$('#idGenerarSolicitud05').prop('disabled', true);
	$('#idGenerarSolicitud05').css('color', '#6E6E6E');
	$('#idLiquidacionNRE06').prop('disabled', true);
	$('#idLiquidacionNRE06').css('color', '#6E6E6E');
	$('#idLiberarDocBaseNive').prop('disabled', true);
	$('#idLiberarDocBaseNive').css('color', '#6E6E6E');
	$('#idCambiaEstadoIni').prop('disabled', true);
	$('#idCambiaEstadoIni').css('color', '#6E6E6E');
	$('#idCambiaMatricula').prop('disabled', true);
	$('#idCambiaMatricula').css('color', '#6E6E6E');
	$('#idGenerarDistintivo').prop('disabled', true);
	$('#idGenerarDistintivo').css('color', '#6E6E6E');
	$('#idDescargarP576').prop('disabled', true);
	$('#idDescargarP576').css('color', '#6E6E6E');
	$('#idOkImprInformaBaja').prop('disabled', true);
	$('#idOkImprInformaBaja').css('color', '#6E6E6E');
	$('#idTramiteRevisado').prop('disabled', true);
	$('#idTramiteRevisado').css('color', '#6E6E6E');
	$('#idImprimirDocumentos').prop('disabled', true);
	$('#idImprimirDocumentos').css('color', '#6E6E6E');
}

function desbloqueaBotonesConsultaTramTraf(){
	$('#idBuscar').prop('disabled', false);
	$('#idBuscar').css('color', '#000000');	
	$('#idLimpiar').prop('disabled', false);
	$('#idLimpiar').css('color', '#000000');
	$('#idValidar').prop('disabled', false);
	$('#idValidar').css('color', '#000000');
	$('#idComprobar').prop('disabled', false);
	$('#idComprobar').css('color', '#000000');
	$('#idDuplicar').prop('disabled', false);
	$('#idDuplicar').css('color', '#000000');
	$('#idEliminar').prop('disabled', false);
	$('#idEliminar').css('color', '#000000');
	$('#idTramitar').prop('disabled', false);
	$('#idTramitar').css('color', '#000000');
	$('#idGenerarJustificante').prop('disabled', false);
	$('#idGenerarJustificante').css('color', '#000000');
	$('#idImprimirJustificante').prop('disabled', false);
	$('#idImprimirJustificante').css('color', '#000000');
	$('#idImpresionDocumentos').prop('disabled', false);
	$('#idImpresionDocumentos').css('color', '#000000');
	$('#idGenerarCertificado').prop('disabled', false);
	$('#idGenerarCertificado').css('color', '#000000');
	$('#idPdteExcel').prop('disabled', false);
	$('#idPdteExcel').css('color', '#000000');
	$('#idGenerarXML').prop('disabled', false);
	$('#idGenerarXML').css('color', '#000000');
	$('#idSolicitarPlacas').prop('disabled', false);
	$('#idSolicitarPlacas').css('color', '#000000');
	$('#idGenDocBase').prop('disabled', false);
	$('#idGenDocBase').css('color', '#000000');
	$('#idAsignarTasasMasiva').prop('disabled', false);
	$('#idAsignarTasasMasiva').css('color', '#000000');
	$('#idObtenerXML').prop('disabled', false);
	$('#idObtenerXML').css('color', '#000000');
	$('#idGenerarJP').prop('disabled', false);
	$('#idGenerarJP').css('color', '#000000');
	$('#idCambiarEstado').prop('disabled', false);
	$('#idCambiarEstado').css('color', '#000000');
	$('#id576').prop('disabled', false);
	$('#id576').css('color', '#000000');
	$('#idConsultaTarjetaEitv').prop('disabled', false);
	$('#idConsultaTarjetaEitv').css('color', '#000000');
	$('#idLiberarEEFFMasivo').prop('disabled', false);
	$('#idLiberarEEFFMasivo').css('color', '#000000');
	$('#idConsultaEEFF').prop('disabled', false);
	$('#idConsultaEEFF').css('color', '#000000');
	$('#idFicheroAEAT').prop('disabled', false);
	$('#idFicheroAEAT').css('color', '#000000');
	$('#idGenerarNRE06').prop('disabled', false);
	$('#idGenerarNRE06').css('color', '#000000');
	$('#idClonarTramite').prop('disabled', false);
	$('#idClonarTramite').css('color', '#000000');
	$('#idGenerarSolicitud05').prop('disabled', false);
	$('#idGenerarSolicitud05').css('color', '#000000');
	$('#idLiquidacionNRE06').prop('disabled', false);
	$('#idLiquidacionNRE06').css('color', '#000000');
	$('#idLiberarDocBaseNive').prop('disabled', false);
	$('#idLiberarDocBaseNive').css('color', '#000000');
	$('#idCambiaEstadoIni').prop('disabled', false);
	$('#idCambiaEstadoIni').css('color', '#000000');
	$('#idCambiaMatricula').prop('disabled', false);
	$('#idCambiaMatricula').css('color', '#000000');
	$('#idGenerarDistintivo').prop('disabled', false);
	$('#idGenerarDistintivo').css('color', '#000000');
	$('#idDescargarP576').prop('disabled', false);
	$('#idDescargarP576').css('color', '#000000');
	$('#idOkImprInformaBaja').prop('disabled', false);
	$('#idOkImprInformaBaja').css('color', '#000000');
	$('#idTramiteRevisado').prop('disabled', false);
	$('#idTramiteRevisado').css('color', '#000000');
	$('#idImprimirDocumentos').prop('disabled', false);
	$('#idImprimirDocumentos').css('color', '#000000');
}

function imprimirVariosTramites(){
	var valueBoton = $("#idImprimirDocumentos").val();
	mostrarLoadingConsultaTramTraf('idImprimirDocumentos');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idImprimirDocumentos',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para imprimir.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea imprimir los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idImprimirDocumentos',valueBoton);
	} 
}
function validarTramite(){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var codigos = "";
	var i = 0;
	var check = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
			check++;
		}
		i++;
	}
	// Por la generacion de xml de custodia, que puede demorar la validacion, se
	// aniaden nuevos mensajes
	if (check >= 10 && check < 50) {
		if (confirm("Va a validar "
				+ check
				+ " tr\u00E1mites. Se puede producir una demora entre 2 y 5 minutos. Gracias por su espera")) {
			if (confirm("Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
				if (confirm(document.getElementById("textoLegal").value)) {
					mostrarLoadingConsultaTramTraf('idValidar');
					$('#formData').attr("action","validarConsTramTraf.action");
					$('#formData').submit();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	} else if (check >= 50) {
		if (confirm("Va a validar "
				+ check
				+ " tr\u00E1mites. Se puede producir una demora mayor de 5 minutos. Gracias por su espera")) {
			if (confirm("Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
				if (confirm(document.getElementById("textoLegal").value)) {
					mostrarLoadingConsultaTramTraf('idValidar');
					$('#formData').attr("action","validarConsTramTraf.action");
					$('#formData').submit();
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	} else {
		if (confirm("Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
			if (confirm(document.getElementById("textoLegal").value)) {
				mostrarLoadingConsultaTramTraf('idValidar');
				$('#formData').attr("action","validarConsTramTraf.action");
				$('#formData').submit();
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}
}

function comprobarBloque(){
	var valueBoton = $("#idComprobar").val();
	mostrarLoadingConsultaTramTraf('idComprobar');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idComprobar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para comprobar.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea comprobar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "comprobarBloqueConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idComprobar',valueBoton);
	} 
}

function duplicarTramite(){
	var valueBoton = $("#idDuplicar").val();
	mostrarLoadingConsultaTramTraf('idDuplicar');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(numCheckedImprimir() > 1){
		ocultarLoadingConsultaTramTraf('idDuplicar',valueBoton);
		return alert("No puede seleccionar m\u00E1s de un tr\u00E1mite para ser duplicado.");
	}
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idDuplicar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para duplicar.");
	} else {
		
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		
		url = "abrirDuplicarDesasignarTasaConsTramTraf.action"
		var $dest = $("#divEmergenteConsultaTramiteTrafico");
		var $idForm = $("#formData");
		$.post(url, $idForm.serialize(), function(data) {

			if (data.toLowerCase().indexOf("<html")<0) {
				$dest.empty().append(data).dialog({
					modal : true,
					position : { my: 'right-400', at: 'right' },
					overflow: 'hidden',
					resizable: false,
					appendTo: $idForm,
					show : {
						effect : "blind",
						duration : 300
					},
					dialogClass : 'no-close',
					width: 370,
					height: 150,
					buttons : {
						SI : function() {
							$("#formData").attr("action", "duplicarConsTramTraf.action?desasignarTasaAlDuplicar=SI").trigger("submit");
						},
						NO : function() {
							$("#formData").attr("action", "duplicarConsTramTraf.action?desasignarTasaAlDuplicar=NO").trigger("submit");
						},
						Cancelar : function() {
							$(this).dialog("close");
							ocultarLoadingConsultaTramTraf('idDuplicar',valueBoton);
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

function eliminarTramite(){
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaTramTraf('idEliminar');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para eliminar.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea eliminar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idEliminar',valueBoton);
	} 
}

function tramitarTelematicoTramite(){
	var valueBoton = $("#idTramitar").val();
	mostrarLoadingConsultaTramTraf('idTramitar');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	var check =  document.getElementsByName("listaChecksConsultaTramite");
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idTramitar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para tramitar.");
	} else {
		var i = 0;
		var numSelected = 0;
		var transElectronica = false;
		var matrElectronica = false;
		while (check[i] != null) {
			if (check[i].checked) {
				var tipoTramite = $('#tipo_'+check[i].value).val();
				if (tipoTramite == "T7") {
					transElectronica = true;
				}
				if (tipoTramite == "T1") {
					matrElectronica = true;
				}
				numSelected++;
			}
			i++;
		}
	
		var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite tramitado telem\u00e1ticamente. Estimados: "
				+ numSelected + " cr\u00e9ditos";
	
		if (transElectronica) {
			textoAlert += "\n\n\nEl Colegio de Gestores verifica que:"
					+ "\n\n"
					+ "  1. El Gestor Administrativo "
					+ "ha comprobado,  en un momento previo al env\u00EDo de esta solicitud, "
					+ "el cumplimiento de los requisitos establecidos en el anexo XIV de RD 2822/1998 de 23 de diciembre "
					+ "por el que se aprueba el Reglamento General de Veh\u00EDculos ( acreditaci\u00F3n de la identidad, titularidad, "
					+ " domicilio,  y liquidaci\u00F3n de las obligaciones fiscales)."
					+ "\n\n"
					+ "  2. Que obra en su poder y conserva mandato suficiente para actuar en nombre del/ de los solicitantes y "
					+ "custodia toda la documentaci\u00F3n que integra este expediente de cambio de titularidad conforme a la normativa "
					+ "archiv\u00EDstica del Ministerio del Interior." + "";
		} else if (matrElectronica) {
			textoAlert += "\n\n\nEl tr\u00e1mite ser\u00e1 enviado a DGT con los datos con los que fue validado.\n\n\nEsta usted procediendo a realizar tr\u00E1mites de matriculaci\u00F3n de vehiculo/s, para la realizacion de tal/es tr\u00E1mites es obligatorio que el Gestor Administrativo verifique de forma correcta y tenga en su poder la ficha t\u00E9cnica del veh\u00EDculo, as\u00ED como pagada y/o sellada la tasa de tr\u00E1fico,  el modelo 576 del impuesto sobre veh\u00EDculos de transporte y el impuesto de veh\u00EDculos de tracci\u00F3n mec\u00E1nica de la localidad correspondiente.  La realizaci\u00F3n de la operaci\u00F3n de matriculaci\u00F3n sin cumplir todos los requisitos de la encomienda de gesti\u00F3n suponen una infracci\u00F3n muy grave que puede ser sancionada con la inhabilitaci\u00F3n profesional del gestor administrativo, ademas de no estar cubierta tal contingencia por la p\u00F3liza de seguros de responsabilidad civil, en cuyo caso el infractor sera responsable de las contingencias econ\u00F3micas que surjan. Todo ello sin perjuicio de las responsabilidades administrativas y jur\u00EDdico penales en que puedan incurrir de forma directa los part\u00EDcipes de dicho tr\u00E1mite."
				+ "\n\n"
				+ "En el caso de cumplir tales requisitos pulse continuar y ultime el proceso de matriculaci\u00F3n, en el caso de no cumplir los requisitos alguno de los veh\u00EDculos que en este proceso se matriculan, se debera cancelar el proceso telem\u00E1tico de matriculaci\u00F3n referido a dichos veh\u00EDculos hasta el correcto cumplimiento de los mismos.";
	
		} else {
			// Aqui texto responsabilidad para el resto
			textoAlert += "\n\n" + document.getElementById("textoLegal").value;
		}
		if (confirm(textoAlert)) {
			$("#formData").attr("action", "tramitarBloqueTelematicamenteConsTramTraf.action").trigger("submit");
		}else{
			ocultarLoadingConsultaTramTraf('idTramitar',valueBoton);
		} 
	} 
}

function generarJustificantesProfesionales(){
	var valueBoton = $("#idGenerarJustificante").val();
	mostrarLoadingConsultaTramTraf('idGenerarJustificante');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarJustificante',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar justificante profesional.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar justificante profesional de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarJustificanteEnBloqueConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarJustificante',valueBoton);
	} 
}

function imprimirJustificantesProfesionales(){
	var valueBoton = $("#idImprimirJustificante").val();
	mostrarLoadingConsultaTramTraf('idImprimirJustificante');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idImprimirJustificante',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para imprimir justificante profesional.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea imprimir el justificante profesional de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirJustificanteConsTramTraf.action").trigger("submit");
		ocultarLoadingConsultaTramTraf('idImprimirJustificante',valueBoton);
	} else{
		ocultarLoadingConsultaTramTraf('idImprimirJustificante',valueBoton);
	} 
}

function impresionVariosTramites(){
	var valueBoton = $("#idImpresionDocumentos").val();
	mostrarLoadingConsultaTramTraf('idImpresionDocumentos');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idImpresionDocumentos',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para imprimir.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea imprimir los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "impresionConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idImpresionDocumentos',valueBoton);
	} 
}

function generarCertificados(){
	var valueBoton = $("#idGenerarCertificado").val();
	mostrarLoadingConsultaTramTraf('idGenerarCertificado');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarCertificado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar un certificado.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar certificado los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarCertificadosConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarCertificado',valueBoton);
	} 
}

function pendientesEnvioExcel(){
	var valueBoton = $("#idPdteExcel").val();
	mostrarLoadingConsultaTramTraf('idPdteExcel');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idPdteExcel',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar un certificado.");
	} else{
		var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: "
			+ numCheckedImprimir() + " cr\u00e9ditos";
	} 
	if (confirm(textoAlert)) {
		$("#formData").attr("action", "pendientesEnvioExcelConsTramTraf.action").trigger("submit");
	}else{
		ocultarLoadingConsultaTramTraf('idPdteExcel',valueBoton);
	}
}

function generarXML620Tramites(){
	var valueBoton = $("#idGenerarXML").val();
	mostrarLoadingConsultaTramTraf('idGenerarXML');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarXML',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar el xml 620.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el xml 620 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarXML620ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarXML',valueBoton);
	} 
}
/*function solicitarPlacasMatricula(){
	
}*/

function generarDocBase(){
	var valueBoton = $("#idGenDocBase").val();
	mostrarLoadingConsultaTramTraf('idGenDocBase');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenDocBase',valueBoton);
		return alert("Debe seleccionar alg\u00FAn trámite para generar el documento base.");
	} else if(confirm("Ha solicitado generar un documento base para los expedientes seleccionados.\n\rPara confirmar esta acción pulse aceptar.")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDocBaseConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenDocBase',valueBoton);
	} 
}

function asignacionMasivaTasas(){
	var valueBoton = $("#idAsignarTasasMasiva").val();
	mostrarLoadingConsultaTramTraf('idAsignarTasasMasiva');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idAsignarTasasMasiva',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para asignar tasas masivas.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea asignar tasas masivas de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "asignacionMasivaTasasConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idAsignarTasasMasiva',valueBoton);
	}
}	
function obtenerXMLenviadoaDGT(){
	var valueBoton = $("#idObtenerXML").val();
	mostrarLoadingConsultaTramTraf('idObtenerXML');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para obtener el XML de DGT.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el XML de DGT de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargaXMLConsTramTraf.action").trigger("submit");
	} 
	ocultarLoadingConsultaTramTraf('idObtenerXML',valueBoton);
}

function generarJP(){
	var valueBoton = $("#idGenerarJP").val();
	mostrarLoadingConsultaTramTraf('idGenerarJP');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarJP',valueBoton);
		return alert("Debe seleccionar alg\u00FAn trámite para generar justificante profesional.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el justificante profesional de los trámites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarJustificanteProfesionalForzadoConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarJP',valueBoton);
	} 
}

function cambiarEstados() {
	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingConsultaTramTraf("idCambiarEstado");
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idCambiarEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaTramiteTrafico");
	$.post("cargarPopUpCambioEstadoConsTramTraf.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 350,
				buttons : {
					CambiarEstado : function() {
						var estadoNuevo = $("#idEstadoNuevo").val();
						if(estadoNuevo == ""){
							alert("Seleccione un estado");
							return false;
						}
						$("#divEmergenteConsultaTramiteTrafico").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsTramTraf.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaTramTraf('idCambiarEstado',valueBoton);
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

function presentacion576(){
	var valueBoton = $("#id576").val();
	mostrarLoadingConsultaTramTraf('id576');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('id576',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para la presentación del 576.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea presentar el 576 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "presentacion576ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('id576',valueBoton);
	}
} 

function consultaTarjetaEitv(){
	var valueBoton = $("#idConsultaTarjetaEitv").val();
	mostrarLoadingConsultaTramTraf('idConsultaTarjetaEitv');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idConsultaTarjetaEitv',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para la consulta EITV.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea realizar la consulta EITV de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "consultaTarjetaEitvConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idConsultaTarjetaEitv',valueBoton);
	} 
} 

function liberarEEFFMasivo(){
	var valueBoton = $("#idLiberarEEFFMasivo").val();
	mostrarLoadingConsultaTramTraf('idLiberarEEFFMasivo');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idLiberarEEFFMasivo',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para liberar EEFF.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea realizar la liberacion de EEFF de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "liberarMasivoEeffConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idLiberarEEFFMasivo',valueBoton);
	} 
}

function consultarEEFF(){
	var valueBoton = $("#idConsultaEEFF").val();
	mostrarLoadingConsultaTramTraf('idConsultaEEFF');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idConsultaEEFF',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para la consulta EEFF.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea realizar la consulta EEFF de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "consultaLiberacionConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idConsultaEEFF',valueBoton);
	} 
}

function generarFicheroAEATTramites(){
	var valueBoton = $("#idFicheroAEAT").val();
	mostrarLoadingConsultaTramTraf('idFicheroAEAT');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idFicheroAEAT',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar el fichero AEAT.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el fichero AEAT de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarFicheroAEATConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idFicheroAEAT',valueBoton);
	} 
}

function generarFicheroMOVE(){
	var valueBoton = $("#idFicheroMOVE").val();
	mostrarLoadingConsultaTramTraf('idFicheroMOVE');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idFicheroMOVE',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar el fichero MOVE.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el fichero MOVE de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarFicheroMOVEConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idFicheroMOVE',valueBoton);
	} 
}

function generarSolicitudNRE06(){
	var valueBoton = $("#idGenerarNRE06").val();
	mostrarLoadingConsultaTramTraf('idGenerarNRE06');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(numCheckedImprimir() > 1){
		ocultarLoadingConsultaTramTraf('idGenerarNRE06',valueBoton);
		return alert("No puede seleccionar m\u00E1s de un tr\u00E1mite para generar la solicitud NRE06.");
	}
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarNRE06',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar la solicitud NRE06.");
	}else if(confirm("\u00BFEst\u00E1 seguro de que desea  generar la solicitud NRE06 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarSolicitudNRE06ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarNRE06',valueBoton);
	} 
}

function clonarTramite(){
	var valueBoton = $("#idClonarTramite").val();
	mostrarLoadingConsultaTramTraf('idClonarTramite');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	var checks =  document.getElementsByName("listaChecksConsultaTramite");
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idClonarTramite',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para clonar.");
	} else {
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "-" + checks[i].value;
				}
				var estadoTramite = document.getElementById('estado_' + checks[i].value).value;
				var tipoTramite= document.getElementById('tipo_' + checks[i].value).value; 
				var bastidor= document.getElementById('bastidor_' + checks[i].value).value; 
				
				document.getElementById("estadoTramiteSeleccionado").value = estadoTramite;
				document.getElementById("tipoTramiteSeleccionado").value = tipoTramite;
				document.getElementById("bastidorSeleccionado").value = bastidor;
			}
			i++;
		$('#formData').attr("action","clonarConsTramTraf.action");
		$('#formData').submit();
		}
	}
}


function generarFicheroSolicitud05(){
	var valueBoton = $("#idGenerarSolicitud05").val();
	mostrarLoadingConsultaTramTraf('idGenerarSolicitud05');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarSolicitud05',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar la solicitud 05.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea  generar la solicitud 05 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarFicheroSolicitud05ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarSolicitud05',valueBoton);
	} 
}

function generarliquidacionNRE06(){
	var valueBoton = $("#idLiquidacionNRE06").val();
	mostrarLoadingConsultaTramTraf('idLiquidacionNRE06');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idLiquidacionNRE06',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar la liquidacion NRE 06.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea  generar la liquidacion NRE 06 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarLiquidacionNRE06ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idLiquidacionNRE06',valueBoton);
	} 
}

function liberarDocBaseNive(){
	var valueBoton = $("#idLiberarDocBaseNive").val();
	mostrarLoadingConsultaTramTraf('idLiberarDocBaseNive');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	var check =  document.getElementsByName("listaChecksConsultaTramite");
	if(confirm("Realizar\u00E1 la liberaci\u00F3n del Nive para este tr\u00E1mite. \n\rPara confirmar esta acci\u00F3n pulse aceptar")) {
		if(codigos == ""){
			ocultarLoadingConsultaTramTraf('idLiberarDocBaseNive',valueBoton);
			return alert("Debe seleccionar alg\u00FAn tramite para liberar el NIVE.");
		} else {
			var i = 0;
			while (check[i] != null) {
				if (check[i].checked) {
					var tipoTramite = $('#tipo_'+check[i].value).val();
					var estadoTramite = $('#estado_'+check[i].value).val();
					if (estadoTramite != "13") {
						ocultarLoadingConsultaTramTraf('idLiberarDocBaseNive',valueBoton);
						alert("El tr\u00E1mite debe de estar en Estado Finalizado PDF para poder liberar el Nive.");
						return false;	
					}
					if (tipoTramite != "T1") {
						ocultarLoadingConsultaTramTraf('idLiberarDocBaseNive',valueBoton);
						alert("El tipo de tr\u00E1mite no se puede liberar.");
						return false;
					}
				}
			i++;
			}
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "liberarDocBaseNiveConsTramTraf.action").trigger("submit");
		} 
	} else {
		ocultarLoadingConsultaTramTraf('idLiberarDocBaseNive',valueBoton);
	}
}

function cambiaEstadoIni(){
	var valueBoton = $("#idCambiaEstadoIni").val();
	mostrarLoadingConsultaTramTraf('idCambiaEstadoIni');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idCambiaEstadoIni',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para cambiar a Estado Iniciado.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea cambiar a Estado Iniciado los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "cambiaEstadoIniConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idCambiaEstadoIni',valueBoton);
	}  
}

function generarPopPupMatriculaConsulta(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}else if(numCheckedImprimir() > 1){
		alert("S\u00F3lo se puede seleccionar un tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var i = 0;
	
	while (checks[i] != null) {
		if (checks[i].checked){
			var codigos = checks[i].value;
			var estado = $('#estado_'+checks[i].value).val();
			var tipoTramite =  $('#tipo_'+checks[i].value).val();
			var matricula = $('#matricula_'+checks[i].value).val();
			if(estado !="13"){
				alert("El estado del tr\u00E1mite debe ser Finalizado PDF");
				return false;
			}else if(tipoTramite!="T1"){
				alert("El tipo de tr\u00E1mite debe ser MATRICULACION");
				return false;
			}else if(matricula != ""){
				alert("El tr\u00E1mite ya tiene matrícula");
				return false;
			}
			break;
		}
		i++;
	}

	
	var ex1 = new RegExp("[0-9]{4}[A-Z]{3}");
    var ex2 = new RegExp("[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}");

	var $dest = $("#divEmergenteConsultaTramiteTrafico");
	$.post("abrirPopMatriculacionConsTramTraf.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 500,
				height: 390,
				buttons : {
					Aceptar : function() {
						var numExpediente        = $("#numExpediente").val();
						var matricula            = $("#matriculaManual").val();
						
						if(matricula === ""){
                            return alert("Debe introducir al menos una matrícula");
                        }  
						
						var dia  = $("#diaFechaMatriculacion").val();
						var mes  = $("#mesFechaMatriculacion").val();
						var anio = $("#anioFechaMatriculacion").val();
						
						var fechaValida = dateValid(dia, mes, anio );
						if ( fechaValida === 'false' ) {
                            return alert("Debe introducir una fecha con formato valido");
						}

						var fechaMatriculacion = fechaValida;
						console.log('Fecha Matriculacion ==> ' + fechaMatriculacion);
						
                        if(!ex1.test(matricula) && !ex2.test(matricula)){
                            return alert("El formato de matrícula introducido no es correcto");
                        }
                        	
                    	limpiarHiddenInput("numExpediente");
						input = $("<input>").attr("type", "hidden").attr("name", "numExpediente").val(codigos);
						$('#formData').append($(input));
						
						limpiarHiddenInput("matriculaManual");
						input = $("<input>").attr("type", "hidden").attr("name", "matricula").val(matricula);
						$('#formData').append($(input));
						
						limpiarHiddenInput("fechaMatriculacion");
						input = $("<input>").attr("type", "hidden").attr("name", "fechaMatriculacion").val(fechaMatriculacion);
						$('#formData').append($(input));
						
                    	$("#formData").attr("action", "actualizarMatManualConsTramTraf.action").trigger("submit");
						
					},
					Cancelar : function() {
						
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

function dateValid(strDia, strMes, strAnnio) {
	
	if (strDia == '' || strMes == '' || strAnnio == '') {
		return 'false';
		
	}
	var dia  = parseInt(strDia);
	var mes  = parseInt(strMes);
	var annio = parseInt(strAnnio);
	
    if (mes < 1 || mes > 12) 
        return 'false';
    else if (dia < 1 || dia > 31) 
        return 'false';
    else if ((mes == 4 || mes == 6 || mes == 9 || mes == 11) && dia == 31) 
        return 'false';
    else if (mes == 2) 
    {
        var isleap = (annio % 4 == 0 && (annio % 100 != 0 || annio % 400 == 0));
        if (dia > 29 || (dia == 29 && !isleap)) 
                return 'false';
    }		
    
    return strDia + '/' + strMes + '/' + strAnnio;
}

function generarDistintivo(){
	var valueBoton = $("#idGenerarDistintivo").val();
	mostrarLoadingConsultaTramTraf('idGenerarDistintivo');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idGenerarDistintivo',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para generar distintivo.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea generar el distintivo de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDistintivoConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idGenerarDistintivo',valueBoton);
	} 
}

function descargarPresentacion576(){
	var valueBoton = $("#idDescargarP576").val();
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para descargar la presentacion 576.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea descargar la presentacion 576 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarPresentacion576ConsTramTraf.action").trigger("submit");
	} 
}

function autorizarImpresionBaja(){
	var valueBoton = $("#idOkImprInformaBaja").val();
	mostrarLoadingConsultaTramTraf('idOkImprInformaBaja');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idOkImprInformaBaja',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para autorizar la impresion del informe.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea  autorizar la impresion del informe de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "autorInfoBjConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idOkImprInformaBaja',valueBoton);
	} 
}

function tramiteRevisado(){
	var valueBoton = $("#idTramiteRevisado").val();
	mostrarLoadingConsultaTramTraf('idTramiteRevisado');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('idTramiteRevisado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para revisar un tramite.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea  revisar tramite de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "tramiteRevisadoConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('idTramiteRevisado',valueBoton);
	} 
}

function numCheckedImprimir(){
	var checks = document.getElementsByName("listaChecksConsultaTramite");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}	
	return numChecked;
}

function buscarConsultaTramiteTrafico() {
	var matricula = new String(document.getElementById("idMatricula").value);
	matricula=Limpia_Caracteres_Matricula(matricula);
	document.getElementById("idMatricula").value = matricula.toUpperCase();
	
	var bastidor = new String(document.getElementById("idNumBastidor").value);
	bastidor=Limpia_Caracteres_Bastidor(bastidor);
	document.getElementById("idNumBastidor").value = bastidor.toUpperCase();
	
	var nif = document.getElementById("idDniTitular").value;
	if(nif != null && nif != "") {
		var tipoTramite = document.getElementById("idTipoTramite").value; 
		if (tipoTramite == null || tipoTramite == "") {
			alert("Para buscar por titular debe seleccionar el tipo de tramite primero");
			return false;
		} else if(!validaNIF(document.getElementById("idDniTitular"))) {
			alert("No has introducido un dni correcto");
			return false;
		}
	}
	
	// Validamos el numero de expediente
	var numExpediente = document.getElementById("idNumExpediente").value;
	if (numExpediente != null) {		
		numExpediente = numExpediente.replace(/\s/g,'');
		if (isNaN(numExpediente)) {
			alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
			return false;
		}		
		document.getElementById("idNumExpediente").value = numExpediente;
	}
	bloqueaBotonesConsultaTramTraf();
	$("#formData").attr("action", "buscarConsTramTraf.action").trigger("submit");
}

//function abrirVentanaExportar() {
//	 
//	var permiso=false;
//	if (document.getElementById("bObtenerXML")!=null)
//		{
//		 permiso=true;
//		}	
//	
//	if (numCheckedImprimir() == 0) {
//		alert("Debe seleccionar algun tr\u00E1mite");
//		return false;
//	}
//	var left = (screen.width - 350) / 2;
//	var top = (screen.height - 200) / 2;
//	ventanaOpciones = window.open("", "ventana1", "width=360,height=100,top=" + top + ",left=" + left + ",scrollbars=NO,resizable=NO,toolbar=NO,status=NO,menubar=NO");
//	ventanaOpciones.document.write("<link href='css/estilos.css' rel='stylesheet' type='text/css' media='screen' />");
//	ventanaOpciones.document.write("<script>var padre; function setPadre(p) { padre=p; } "  + "</script>");
//	ventanaOpciones.setPadre(bExportarDatos);
//	ventanaOpciones.document.write("<script>function mensajeAlPadre(boton) { if (boton.value=='XML') padre.exportarTramite(padre.document.getElementById('bExportarDatos'));if (boton.value=='XMLSESION') padre.exportarTramiteXML(padre.document.getElementById('bExportarDatos'));if (boton.value=='BM') padre.exportarTramiteBM(padre.document.getElementById('bExportarDatos')); this.close;  }</script>");
//	ventanaOpciones.document.write("<body style='background:#ededed'><div>");
//	ventanaOpciones.document.write("<table class='subtitulo'  cellspacing='0'>");
//	ventanaOpciones.document.write(	"<tr><td>Seleccione el formato para exportar:</td></tr></table>");
//	ventanaOpciones.document.write("<table align='center' border='0' cellpadding='0'>");
//	ventanaOpciones.document.write("<tr><td>&nbsp;&nbsp;&nbsp;</td>");
//	ventanaOpciones.document.write("<td><input type='button' value='XML' class='boton' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td>");
//	if (permiso){
//		ventanaOpciones.document.write("<td><input type='button' class='boton' id='botonSesion' value='XMLSESION' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td>");
//	}
//	ventanaOpciones.document.write("<td><input type='button' class='boton' value='BM' onkeypress='this.onClick' onclick='mensajeAlPadre(this);'></td></tr></div></body>");
//
//}

function exportarTramite(boton) {
	ventanaOpciones.close();
	
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	$('#formData').attr("action","exportarConsTramTraf.action");
	$('#formData').submit();
}

//Metodo para exportar tramites con el usuario de sesion
function exportarTramiteXML(boton) {
	     ventanaOpciones.close();
	 if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite para la sesion");
		return false;
	}
	
	var input = $("<input>").attr("type", "hidden").attr("name","exportarXmlSesion").val(true);
	$('#formData').append($(input));
	//$('#exportarXmlSesion"').attr("value",true);
	$('#formData').attr("action","exportarConsTramTraf.action");
	$('#formData').submit();
}


function exportarTramiteBM(boton) {
	ventanaOpciones.close();
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","exportarBMConsTramTraf.action");
	$('#formData').submit();
}

//Llama al action que muestra el documento cargado en sesion.
function muestraDocumentoFicheroAEAT() {

	var actionPoint = "mostrarDocumentoTrafico.action";

	var navegador = '';
	var IE = '\v' == 'v';
	if (navigator.userAgent.toLowerCase().indexOf('safari/') != '-1')
		var navegador = 'safari';

	// if(navigator.userAgent.toLowerCase().indexOf('firefox/')) var navegador =
	// 'firefox';

	if (IE || navegador == 'safari') {
		alert("Est\u00E1 utilizando un navegador que no permite la descarga autom\u00E1tica de ficheros por "
				+ "la aplicaci\u00F3n.\n Pulse en el link que aparecer\u00E1 para imprimir el documento generado.\n\n"
				+ "Recomendamos la instalaci\u00F3n de otro navegador para un uso m\u00E1s \u00F3ptimo de la aplicaci\u00F3n.");
		var mostrarLink = document.getElementById("mostrarDocumentoFicheroLink");
		mostrarLink.style.display = "block";

	} else {
		document.forms[0].action = actionPoint;
		document.forms[0].submit();

	}
}

function muestraYOcultaFicheroAEAT() {
	var mostrarLink = document.getElementById("mostrarDocumentoFicheroLink");
	mostrarLink.style.display = "none";
}

function tramitarNRE06(){
	var valueBoton = $("#btramitarNre06").val();
	mostrarLoadingConsultaTramTraf('btramitarNre06');
	var codigos = "";
	$("input[name='listaChecksConsultaTramite']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaTramTraf('btramitarNre06',valueBoton);
		return alert("Debe seleccionar alg\u00FAn expediente para tramitar NRE");
	} else if(confirm("¿Quiere tramitar NRE06 de los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "tramitarNRE06ConsTramTraf.action").trigger("submit");
	} else{
		ocultarLoadingConsultaTramTraf('btramitarNre06',valueBoton);
	} 
}

function mostrarBloqueFallidosGenDocBase(){
	$("#bloqueGenDocBase").hide();
	if($("#bloqueFallidosGenDocBase").is(":visible")){
		$("#bloqueFallidosGenDocBase").hide();
		$("#despFallidosGenDocBase").attr("src","img/plus.gif");
		$("#despFallidosGenDocBase").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidosGenDocBase").show();	
		$("#despFallidosGenDocBase").attr("src","img/minus.gif");
		$("#despFallidosGenDocBase").attr("alt","Ocultar");
	}
	$("#despGenDocBase").attr("src","img/plus.gif");
}

function mostrarBloqueGenDocBase(){
	$("#bloqueFallidosGenDocBase").hide();
	if($("#bloqueGenDocBase").is(":visible")){
		$("#bloqueGenDocBase").hide();
		$("#despGenDocBase").attr("src","img/plus.gif");
		$("#despGenDocBase").attr("alt","Mostrar");
	}else{
		$("#bloqueGenDocBase").show();	
		$("#despGenDocBase").attr("src","img/minus.gif");
		$("#despGenDocBase").attr("alt","Ocultar");
	}
	$("#despFallidosGenDocBase").attr("src","img/plus.gif");
}