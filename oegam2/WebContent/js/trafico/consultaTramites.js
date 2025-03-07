/**
 * *************************** CONSULTA TRAMITE TRAFICO ***********************
 */
function buscarTramiteTrafico() {
	var matricula = new String(document.getElementById("matricula").value);
	matricula=Limpia_Caracteres_Matricula(matricula);
	document.getElementById("matricula").value = matricula.toUpperCase();

	var bastidor = new String(document.getElementById("numBastidor").value);
	bastidor=Limpia_Caracteres_Bastidor(bastidor);
	document.getElementById("numBastidor").value = bastidor.toUpperCase();

	if (!validaNIF(document.getElementById("dniTitular"))) {
		alert("No has introducido un dni correcto");
		return false;
	}

	// Validamos el numero de expediente
	var numExpediente = document.getElementById("numExpediente").value;

	if (numExpediente != null) {
		numExpediente = numExpediente.replace(/\s/g,'');

	if (isNaN(numExpediente)) {
		alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
			return false;
		}

		document.getElementById("numExpediente").value = numExpediente;
	}
	// //Para que muestre el loading
	mostrarLoadingBuscar();
	document.formData.action = "buscarConsultaTramiteTrafico.action";
	document.formData.submit();
}

function obtenerXMLenviadoaDGT(){
	if(numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	$('#formData').attr("action","descargaXMLConsultaTramiteTrafico.action");
	$('#formData').submit();
	return true;
}

function consultaGest(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("Debe seleccionar un s\u00f3lo tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","consultaGestConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function cambiarElementosPorPaginaConsultaTrafico() {
	document.location.href = 'navegarConsultaTramiteTrafico.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaFacturaNormal() {
	document.location.href = 'navegarNormalGestionFacturar.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaFacturaRect() {
	document.location.href = 'navegarRectGestionFacturar.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaEvolucion() {
	document.location.href = 'navegarEvolucionTramiteTrafico.action?resultadosPorPagina='
			+ $('#idResultadosPorPagina').val();
}

function cambiarElementosPorPaginaAcciones() {
	document.location.href = 'navegarAcciones.action?resultadosPorPagina='
			+ $('#idResultadosPorPagina').val();
}

function cambiarElementosPorPaginaConsultaPlacasMatriculacion() {
	document.location.href = 'navegarSolicitudPlacas.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

/** *************************** BOTONES CONSULTA *********************** */

// Método que pasa al action de modificar o detalle del registro seleccionado
function validarTramite(boton) {
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
	// Por la generación de XML de custodia, que puede demorar la validación, se
	// aniaden nuevos mensajes
	if (check >= 10 && check < 50) {
		if (confirm("Va a validar "
				+ check
				+ " tr\u00E1mites. Se puede producir una demora entre 2 y 5 minutos. Gracias por su espera")) {
			if (confirm(String.fromCharCode(191)
					+ "Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
				if (confirm(document.getElementById("textoLegal").value)) {
					mostrarLoadingConsultar(boton);
					$('#formData').attr("action","validarConsultaTramiteTrafico.action");
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
			if (confirm(String.fromCharCode(191)
					+ "Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
				if (confirm(document.getElementById("textoLegal").value)) {
					mostrarLoadingConsultar(boton);
					$('#formData').attr("action","validarConsultaTramiteTrafico.action");
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
		if (confirm(String.fromCharCode(191)
				+ "Est\u00E1 seguro de que desea validar estos tr\u00E1mites?")) {
			if (confirm(document.getElementById("textoLegal").value)) {
				mostrarLoadingConsultar(boton);
				$('#formData').attr("action","validarConsultaTramiteTrafico.action");
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
// Metodo que pasa al action de eliminar los registros seleccionados.
function eliminarTramite(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar los tr\u00E1mites?")) {
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","eliminarConsultaTramiteTrafico.action");
		$('#formData').submit();
		return true;
	} else {
		return false;
	}
}

// Metodo que pasa al action de duplicar el tramite
function duplicarTramite(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con un tr\u00E1mite a la vez");
		return false;
	}

	mostrarLoadingConsultar(boton);
	url = "abrirDuplicarDesasignarTasaConsultaTramiteTrafico.action"
	var $dest = $("#divEmergenteMatricula");
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
						$('#formData').attr("action","duplicarConsultaTramiteTrafico.action?desasignarTasaAlDuplicar=SI");
						$('#formData').submit();
					},
					NO : function() {
						$('#formData').attr("action","duplicarConsultaTramiteTrafico.action?desasignarTasaAlDuplicar=NO");
						$('#formData').submit();
					},
					Cancelar : function() {
						$(this).dialog("close");
						ocultarLoadingConsultar(boton,'Duplicar');
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

// Metodo para el boton de volver
function volverConsulta(boton) {
	mostrarLoadingImprimir(boton);
	document.forms[0].action = "inicioConsultaTramiteTrafico.action";
	document.forms[0].submit();
}

// Metodo para imprimir varios tramites a la vez.
function marcarTodosConsultaTramite(boton, listaCheck){
	var obj = $('[name="'+listaCheck+'"]');
	$.each(obj, function(key, objeto) {
		$(this).attr('checked', boton.checked);
		cambiarCheck(this);
	});
}

function cambiarCheck(checkboxTramite){
	var objHidden = $('[name="listaExpedientes"][value='+checkboxTramite.value+']');
	objHidden.attr('checked', checkboxTramite.checked);
}

function imprimirVariosTramites(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","imprimirConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function impresionVariosTramites(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","impresionConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function generarSolicitudNRE06(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}else if(numCheckedImprimir() > 1){
		alert("Solo puede elegir un tr\u00E1mite para realizar esta acci\u00F3n");
		return false;
	}

	mostrarLoadingConsultar(boton);

	$('#formData').attr("action","generarSolicitudNRE06ConsultaTramiteTrafico.action");	
	$('#formData').submit();
}

function generarliquidacionNRE06(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","generarLiquidacionNRE06ConsultaTramiteTrafico.action");
	$('#formData').submit();

	ocultarLoadingConsultar(document.getElementById('bLiquidacionNRE06'), 'Exportación Datos Modelo06');
	boton.value = "Exportación Datos Modelo06";

}

function numCheckedImprimir(){
	var checks = document.getElementsByName("listaExpedientes");
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}
// Metodo para generar certificados de tasas.
function generarCertificados(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);

	$('#formData').attr("action","generarCertificadosConsultaTramiteTrafico.action");
	$('#formData').submit();
}
function generarPermiso(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","generarPermisoConsultaTramiteTrafico.action");
	$('#formData').submit();
}
function generarDistintivo(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","generarDistintivoConsultaTramiteTrafico.action");
	$('#formData').submit();
}
function generarEitv(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","generarEitvConsultaTramiteTrafico.action");
	$('#formData').submit();
}
// Funcion para asignar tasas masivamente
function asignacionMasivaTasas(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","asignacionMasivaTasasConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function mostrarDivErrorConsultaTramiteTrafico(){

	if(!document.getElementById('divErrores')) {
		var div = document.createElement("DIV");
		div.innerHTML = '<div id="divErrores"></div>';
		document.getElementById("resultado").appendChild(div);
	}

}

function descargarCertificado() {
	$('#formData').attr("action","descargarCertificadoConsultaTramiteTrafico.action");
	$('#formData').submit();
}

// Metodo para exportar tramites
function exportarTramite(boton) {
	ventanaOpciones.close();

	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	$('#formData').attr("action","exportarConsultaTramiteTrafico.action");
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
	$('#formData').attr("action","exportarConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function exportarTramiteBM(boton) {
	ventanaOpciones.close();
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","exportarBMConsultaTramiteTrafico.action");
	$('#formData').submit();
}

// Metodo para generar XML de los tramites
function generarXML620Tramites(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","generarXML620ConsultaTramiteTrafico.action");
	$('#formData').submit();
}

//Metodo para generar el fichero con los datos de los lotes AEAT
function generarFicheroAEATTramites(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var errores = document.getElementById("divError");
	if (errores != null) {
		errores.style.display = "none";
	}
	$('#formData').attr("action","generarFicheroAEATConsultaTramiteTrafico.action");
	$('#formData').submit();
}

//Metodo para generar el fichero con los datos de los lotes AEAT
function generarFicheroMOVE(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var errores = document.getElementById("divError");
	if (errores != null) {
		errores.style.display = "none";
	}
	$('#formData').attr("action","generarFicheroMOVEConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function generarFicheroSolicitud05(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	var errores = document.getElementById("divError");
	if (errores != null) {
		errores.style.display = "none";
	}
	$('#formData').attr("action","generarFicheroSolicitud05ConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function pendientesEnvioExcel(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	} else {
		var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: "
				+ numCheckedImprimir() + " cr\u00e9ditos";

	}

	if (confirm(textoAlert)) {
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","pendientesEnvioExcelConsultaTramiteTrafico.action");
		$('#formData').submit();
	}

}

function pendientesEnvioAExcel() {
	var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: 1 cr\u00e9dito";
	if (confirm(textoAlert)) {
		document.forms[0].action = "pendientesEnvioExcelConsultaTramiteTrafico.action?numsExpediente="
				+ document.getElementById("idHiddenNumeroExpediente").value;
		document.forms[0].submit();
	}
}

function pendientesEnvioAExcelAlta() {

	var textoAlert = "Va a consumir un cr\u00e9dito por cada tr\u00e1mite seleccionado. Estimados: 1 cr\u00e9dito";
	if (confirm(textoAlert)) {
		document.forms[0].action = "pendientesEnvioExcelConsultaTramiteTrafico.action?numsExpediente="
				+ document.getElementById("idHiddenNumeroExpediente").value
				+ "&excelDesdeAlta=true";
		;
		document.forms[0].submit();
	}

}

// Metodo para tramitar telematicamente los tramites de Matriculacion y
// Transmision que le pasemos.
function tramitarTelematicoTramite(boton, colegiado) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaExpedientes");
//	var codigos = "";
	var i = 0;
	var numSelected = 0;
	var transElectronica = false;
	var matrElectronica = false;
	while (checks[i] != null) {
		if (checks[i].checked) {
			var tipoTramite = $('#tipo_'+checks[i].value).val();
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
	} else if (matrElectronica) {
		textoAlert += "\n\n\nEl tr\u00e1mite ser\u00e1 enviado a DGT con los datos con los que fue validado.\n\n\nEsta usted procediendo a realizar tr\u00E1mites de matriculaci\u00F3n de vehiculo/s, para la realizacion de tal/es tr\u00E1mites es obligatorio que el Gestor Administrativo verifique de forma correcta y tenga en su poder la ficha t\u00E9cnica del veh\u00EDculo, as\u00ED como pagada y/o sellada la tasa de tr\u00E1fico,  el modelo 576 del impuesto sobre veh\u00EDculos de transporte y el impuesto de veh\u00EDculos de tracci\u00F3n mec\u00E1nica de la localidad correspondiente.  La realizaci\u00F3n de la operaci\u00F3n de matriculaci\u00F3n sin cumplir todos los requisitos de la encomienda de gesti\u00F3n suponen una infracci\u00F3n muy grave que puede ser sancionada con la inhabilitaci\u00F3n profesional del gestor administrativo, ademas de no estar cubierta tal contingencia por la p\u00F3liza de seguros de responsabilidad civil, en cuyo caso el infractor sera responsable de las contingencias econ\u00F3micas que surjan. Todo ello sin perjuicio de las responsabilidades administrativas y jur\u00EDdico penales en que puedan incurrir de forma directa los part\u00EDcipes de dicho tr\u00E1mite."
			+ "\n\n"
			+ "En el caso de cumplir tales requisitos pulse continuar y ultime el proceso de matriculaci\u00F3n, en el caso de no cumplir los requisitos alguno de los veh\u00EDculos que en este proceso se matriculan, se debera cancelar el proceso telem\u00E1tico de matriculaci\u00F3n referido a dichos veh\u00EDculos hasta el correcto cumplimiento de los mismos.";

	} else {
		// Aqui texto responsabilidad para el resto
		textoAlert += "\n\n" + document.getElementById("textoLegal").value;
	}
	if (confirm(textoAlert)) {

		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","tramitarBloqueTelematicamenteConsultaTramiteTrafico.action");
		$('#formData').submit();
	}
}

// Metodo para comprobar tramites de Transmision que le pasemos.
function comprobarBloque(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","comprobarBloqueConsultaTramiteTrafico.action");
	$('#formData').submit();
}

// Metodo para generar XML de los tramites
function obtenerMatriculasBloque(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	if (numCheckedImprimir() > 10) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con tr\u00E1mites de 10 en 10");
		return false;
	}

	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","obtenerMatriculasBloqueConsultaTramiteTrafico.action");
	$('#formData').submit();
	return true;
}

// Metodo para generar XML de los tramites
function genericoTramite(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con un tr\u00E1mite a la vez");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaTramite");
	var codigo = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			codigo = checks[i].value;
			break;
		}
		i++;
	}
	mostrarLoadingConsultar(boton);
	document.forms[0].action = "generarXMLConsultaTramiteTrafico.action?numExpediente="
			+ codigo;
	document.forms[0].submit();
	return true;
}

var ventana_evolucion;
var opciones_ventana = 'width=850,height=400,top=150,left=280';
var opciones_ventana2 = 'width=850,height=450,top=050,left=200';
var opciones_ventana3 = 'width=862,height=450,top=050,left=200';
var mas_opciones = ",toolbar=no,location=no,directories=no,status=no,menubar=no,resizable=no";
var opciones_popup = 'width=1250,height=300,top=150,left=50,scrollbars=Yes';
var opciones_popup_direccion = 'width=850,height=500,top=150,left=50,scrollbars=Yes';

// metodo que abrira un popup con la evolucion del tramite que hayamos
// seleccionado.
function abrirEvolucionTramite(numExpediente) {
	if (null == numExpediente) {
		alert('No se puede referenciar a ning\u00FAn tr\u00E1mite.');
		return false;
	}
	var ventana_evolucion = window.open(
			'actualizarPaginatedListEvolucionTramiteTrafico.action?numExpediente=' + numExpediente, 'popup',
			opciones_ventana);
}

// metodo que abrira un popup con la evolucion del tramite que hayamos
// seleccionado.
function abrirListaAcciones() {
	var numeroExpediente = document.getElementById("idHiddenNumeroExpediente").value;

	if (null == numeroExpediente) {
		alert('No se puede referenciar a ning\u00FAn tr\u00E1mite.');
		return false;
	} else {
		var ventana_evolucion = window.open(
				'inicioAcciones.action?numExpediente=' + numeroExpediente,
				'popup', opciones_ventana);
	}
}

// Método que abrirá un popup para introducir la matrícula manualmente 
//en un tramite finalizado pdf sin matricula
function abrirMatriculaManual(numExpediente){
	window.open('abrirPopMatriculacionAltasMatriculacionMatw.action?numExpediente='+numExpediente,'popup',opciones_ventana);
	//window.open('abrirIdentificacionIdentificacionCorpme.action?tipoTramiteRegistro=' + tipoTramiteRegistro + "&idContrato=" + idContratoRegistro + "&numExpediente=" + idTramiteRegistro,'popup',opciones_ventana);
}

function generarPopPupMatricula(codigos){
	var ex1 = new RegExp("[0-9]{4}[A-Z]{3}");
	var ex2 = new RegExp("[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}");

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopMatriculacionAltasMatriculacionMatw.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 500,
				height: 200,
				buttons: {
					Aceptar : function() {
						var numExpediente=$("#numExpediente").val();
						var matricula=$("#matriculaManual").val();
						if(matricula===""){
							return alert("Debe introducir al menos una matrícula");
						}
						if(ex1.test(matricula) || ex2.test(matricula)){
							limpiarHiddenInput("numExpediente");
							input = $("<input>").attr("type", "hidden").attr("name", "numExpediente").val(codigos);
							$('#formData').append($(input));
							limpiarHiddenInput("matriculaManual");
							input = $("<input>").attr("type", "hidden").attr("name", "matricula").val(matricula);
							$('#formData').append($(input));
							$("#formData").attr("action", "actualizarMatManualAltasMatriculacionMatw.action").trigger("submit");

						}else{
							return alert("El formato de matrícula introducido no es correcto");
						}
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

//Para la nueva matriculacion
function generarPopPupMatriculaNueva(codigos){
	var ex1 = new RegExp("[0-9]{4}[A-Z]{3}");
	var ex2 = new RegExp("[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}");

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopMatriculacionAltaTramiteTraficoMatriculacion.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 500,
				height: 200,
				buttons : {
					Aceptar : function() {
						var numExpediente=$("#numExpediente").val();
						var matricula=$("#matriculaManual").val();

						if(matricula===""){
							return alert("Debe introducir al menos una matrícula");
						}

						var dia  = $("#diaFechaMatriculacion").val();
						var mes  = $("#mesFechaMatriculacion").val();
						var anio = $("#anioFechaMatriculacion").val();

						var fechaValida = dateValid(dia, mes, anio );
						if (fechaValida === 'false') {
							return alert("Debe introducir una fecha con formato valido");
						}

						var fechaMatriculacion = fechaValida;

						if(ex1.test(matricula) || ex2.test(matricula)){
							limpiarHiddenInput("numExpediente");
							input = $("<input>").attr("type", "hidden").attr("name", "numExpediente").val(codigos);
							$('#formData').append($(input));

							limpiarHiddenInput("matriculaManual");
							input = $("<input>").attr("type", "hidden").attr("name", "matricula").val(matricula);
							$('#formData').append($(input));

							limpiarHiddenInput("fechaMatriculacion");
							input = $("<input>").attr("type", "hidden").attr("name", "fechaMatriculacion").val(fechaMatriculacion);
							$('#formData').append($(input));

							$("#formData").attr("action", "actualizarMatManualAltaTramiteTraficoMatriculacion.action").trigger("submit");
						} else {
							return alert("El formato de matrícula introducido no es correcto");
						}
					},
					Cancelar: function() {
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

function generarPopPupMatriculaConsulta(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}else if(numCheckedImprimir() > 1){
		alert("S\u00F3lo se puede seleccionar un tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaExpedientes");
//	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked){
			var codigos = checks[i].value;
			var estado = document.getElementById("tablaConsultaTramite").rows[i+1].cells[7].innerText;
			var tipoTramite = document.getElementById("tablaConsultaTramite").rows[i+1].cells[6].innerText;
			var matricula = document.getElementById("tablaConsultaTramite").rows[i+1].cells[6].innerText;
			if(estado.trim()!="Finalizado PDF"){
				alert("El estado del tr\u00E1mite debe ser Finalizado PDF");
				return false;
			}else if(tipoTramite!="MATRICULACION"){
				alert("El tipo de tr\u00E1mite debe ser MATRICULACION");
				return false;
			}else if(document.getElementById("tablaConsultaTramite").rows[i+1].cells[3].innerText.trim()!=""){
				alert("El tr\u00E1mite ya tiene matrícula");
				return false;
			}
			break;
		}
		i++;
	}

	var ex1 = new RegExp("[0-9]{4}[A-Z]{3}");
	var ex2 = new RegExp("[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}");

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopMatriculacionConsultaTramiteTrafico.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 500,
				height: 390,
				buttons : {
					Aceptar : function() {
						var numExpediente	= $("#numExpediente").val();
						var matricula		= $("#matriculaManual").val();

						if(matricula === ""){
							return alert("Debe introducir al menos una matrícula");
						}

						var dia  = $("#diaFechaMatriculacion").val();
						var mes  = $("#mesFechaMatriculacion").val();
						var anio = $("#anioFechaMatriculacion").val();

						var fechaValida = dateValid(dia, mes, anio );
						if (fechaValida === 'false') {
							return alert("Debe introducir una fecha con formato valido");
						}

						var fechaMatriculacion = fechaValida;
						console.log('Fecha Matriculacion ==> ' + fechaMatriculacion);

						if (!ex1.test(matricula) && !ex2.test(matricula)) {
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

						$("#formData").attr("action", "actualizarMatManualConsultaTramiteTrafico.action").trigger("submit");
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
	else if (mes == 2) {
		var isleap = (annio % 4 == 0 && (annio % 100 != 0 || annio % 400 == 0));
		if (dia > 29 || (dia == 29 && !isleap))
			return 'false';
	}

	return strDia + '/' + strMes + '/' + strAnnio;
}

function cerrarEvolucionTramite() {
	this.close();
}

function cerrarConsultaDireccion() {
	this.close();
}

function imprimirJustificantesProfesionales(boton) {

	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	// Para que muestre el loading
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","imprimirJustificanteConsultaTramiteTrafico.action");
	$('#formData').submit();
	ocultarLoadingConsultar(boton,'Imprimir Justificantes');
	return true;
}

function generarJustificantesProfesionales(boton) {
	var codigos = "";
	$("input[name='listaExpedientes']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}else if (numCheckedImprimir() > 1) {
		alert("Debe seleccionar solo un tr\u00E1mite");
		return false;
	}

	var textoBoton = $("#bGenerarJustificante").val();
	mostrarLoadingConsultar(boton);
	var $dest = $("#divEmegergenteJustificantes");

	$.post("cargarPopUpJustificantesProfConsultaTramiteTrafico.action?listaExpedientes="+codigos, function(data) {
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
						var motivo = $("#motivoJustificante").val();
						if(motivo == ""){
							alert("Seleccione un motivo");
							return false;
						}
						var documentos = $("#documentosJustificante").val();
						if (documentos == "") {
							alert("Seleccione un Documento");
							return false;
						}
						$("#divEmegergenteJustificantes").dialog("close");
						limpiarHiddenInput("motivoJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "motivoJustificantes").val(motivo);
						$('#formData').append($(input));
						limpiarHiddenInput("documentoJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "documentoJustificantes").val(documentos);
						$('#formData').append($(input));
						$("#formData").attr("action", "generarJustificanteEnBloqueConsultaTramiteTrafico.action#").trigger("submit");
					},
					Cerrar: function() {
						ocultarLoadingConsultar(boton, textoBoton);
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
	return true;
}

function imprimirJustifProf(boton){
	var textoBoton = $("#bImprimirJustifProf").val();
	mostrarLoadingConsultar(boton);
	var codigos = "";
	$("input[name='listaExpedientes']:checked").each(function() {
		if (this.checked) {
			if(codigos==""){
				codigos += this.value;
			} else {
				codigos += "-" + this.value;
			}
		}
	});
	if (codigos == "") {
		ocultarLoadingConsultar(boton, textoBoton);
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	limpiarHiddenInput("listaExpedientes");
	input = $("<input>").attr("type", "hidden").attr("name", "listaExpedientes").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "imprimirJustifProfNuevoConsultaTramiteTrafico.action#").trigger("submit");
}

function generarJustifProf(boton){
	var textoBoton = $("#bGenerarJustificante").val();
	mostrarLoadingConsultar(boton);
	var codigos = "";
	$("input[name='listaExpedientes']:checked").each(function() {
		if (this.checked) {
			if (codigos == "") {
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if (codigos == "") {
		ocultarLoadingConsultar(boton, textoBoton);
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	var $dest = $("#divEmergenteJustfProf");
	$.post("cargarPopUpJustificantesProfConsultaTramiteTrafico.action?listaExpedientes="+codigos, function(data) {
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
					Generar : function() {
						var motivo = $("#idMotivoCompletar").val();
						if (motivo == "") {
							alert("Seleccione un motivo");
							return false;
						}
						var documentos = $("#idDocJustifCompletar").val();
						if (documentos == "") {
							alert("Seleccione un Documento");
							return false;
						}
						var diasValidez = $("#idDiasValidez").val();
						if (diasValidez == "") {
							alert("Debe rellenar los días de validez");
							return false;
						}
						$("#divEmergenteJustfProf").dialog("close");
						limpiarHiddenInput("motivoJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "motivoJustificantes").val(motivo);
						$('#formData').append($(input));
						limpiarHiddenInput("documentoJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "documentoJustificantes").val(documentos);
						$('#formData').append($(input));
						limpiarHiddenInput("diasValidezJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "diasValidezJustificantes").val(diasValidez);
						$('#formData').append($(input));
						$("#formData").attr("action", "generarJustifProfNuevoConsultaTramiteTrafico.action#").trigger("submit");
					},
					Cerrar: function() {
						ocultarLoadingConsultar(boton, textoBoton);
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

// Para mostrar el loading en impresion.
function mostrarLoadingImprimir(boton) {
	document.getElementById("bloqueLoading").style.display = "block";
	boton.value = "Procesando..";
	bloqueaBotonesConsulta();
}

// Para mostrar el loading en buscar.
function mostrarLoadingBuscar(){
	//document.getElementById("bloqueLoadingBuscar").style.display = "block";
	loading("loadingImage");
//	boton.value = "Procesando..";
	bloqueaBotonesConsulta();
}

// Para mostrar el loading en los botones de consultar.
function mostrarLoadingConsultar(boton) {
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultar").style.display = "block";
	boton.value = "Procesando..";
}

function ocultarLoadingConsultar(boton, value) {
	document.getElementById("bloqueLoadingConsultar").style.display = "none";
	boton.value = value;
	desbloqueaBotonesConsulta();
}

// Desbloquea los botones de la pantalla de consulta de tramites
function desbloqueaBotonesConsulta() {
	var inputs = document.getElementsByTagName('input');
	for (var i = 0; i < inputs.length; i++) {
		var input = inputs[i];

		if (input.type == 'button') {
			input.disabled = false;
			input.style.color = '#000000';
		}
	}
}

// Llama al action que muestra el documento cargado en sesion.
function muestraDocumento() {
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
		var mostrarLink = document.getElementById("mostrarDocumentoLink");
		mostrarLink.style.display = "block";

	} else {
		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	}
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

// Llama al action que muestra el documento cargado en sesion.
function obtenerDocumentoActual620(pestania) {
	if (tabsIniciadas) {
		var actionPoint = "obtenerFichero620AltasTramiteTransmisionActual.action#"
				+ pestania;

		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	} else {
		setTimeout("obtenerDocumentoActual620()", 1000);
	}
}

// Llama al action que muestra el documento cargado en sesion.
function muestraDocumento_BS() {
	actualizaRadios_BS();

	var actionPoint = "mostrarDocumentoTrafico.action";

	var IE = '\v' == 'v';

	if (IE) {
		alert("Est\u00E1 utilizando Internet Explorer, este navegador no permite la descarga autom\u00E1tica de ficheros por "
				+ "la aplicaci\u00F3n.\n Pulse en el link que aparecer\u00E1 para imprimir el documento generado.\n\n"
				+ "Recomendamos la instalaci\u00F3n de otro navegador para un uso m\u00E1s \u00F3ptimo de la aplicaci\u00F3n.");
		var mostrarLink = document.getElementById("mostrarDocumentoLink");
		mostrarLink.style.display = "block";
	} else {
		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	}
}

// Llama al action que muestra el documento cargado en sesion.
function muestraDocumento_S() {

	var actionPoint = "mostrarDocumentoTrafico.action";
	var IE = '\v' == 'v';

	if (IE) {
		alert("Est\u00E1 utilizando Internet Explorer, este navegador no permite la descarga autom\u00E1tica de ficheros por "
				+ "la aplicaci\u00F3n.\n Pulse en el link que aparecer\u00E1 para imprimir el documento generado.\n\n"
				+ "Recomendamos la instalaci\u00F3n de otro navegador para un uso m\u00E1s \u00F3ptimo de la aplicaci\u00F3n.");
		var mostrarLink = document.getElementById("mostrarDocumentoLink");
		mostrarLink.style.display = "block";

	} else {
		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	}
}

function muestraYOculta() {
	var actionPoint = "mostrarDocumentoTrafico.action";
	var mostrarLink = document.getElementById("mostrarDocumentoLink");
	mostrarLink.style.display = "none";
}

function mostrarBloqueValidados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueValidados").is(":visible")){
		$("#bloqueValidados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	} else {
		$("#bloqueValidados").show();
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
	$("#despFallidos").attr("src","img/plus.gif");
}

function mostrarBloqueFallidosGenDocBase(){
	$("#bloqueGenDocBase").hide();
	if ($("#bloqueFallidosGenDocBase").is(":visible")) {
		$("#bloqueFallidosGenDocBase").hide();
		$("#despFallidosGenDocBase").attr("src","img/plus.gif");
		$("#despFallidosGenDocBase").attr("alt","Mostrar");
	} else {
		$("#bloqueFallidosGenDocBase").show();	
		$("#despFallidosGenDocBase").attr("src","img/minus.gif");
		$("#despFallidosGenDocBase").attr("alt","Ocultar");
	}
	$("#despGenDocBase").attr("src","img/plus.gif");
}

function mostrarBloqueGenDocBase() {
	$("#bloqueFallidosGenDocBase").hide();
	if ($("#bloqueGenDocBase").is(":visible")) {
		$("#bloqueGenDocBase").hide();
		$("#despGenDocBase").attr("src","img/plus.gif");
		$("#despGenDocBase").attr("alt","Mostrar");
	} else {
		$("#bloqueGenDocBase").show();	
		$("#despGenDocBase").attr("src","img/minus.gif");
		$("#despGenDocBase").attr("alt","Ocultar");
	}
	$("#despFallidosGenDocBase").attr("src","img/plus.gif");
}

function mostrarBloqueFallidos() {
	$("#bloqueValidados").hide();
	if ($("#bloqueFallidos").is(":visible")) {
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	} else {
		$("#bloqueFallidos").show();
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
	}
	$("#despValidado").attr("src","img/plus.gif");
}

function liberarDocBaseNive(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	if (confirm("Realizar\u00E1 la liberaci\u00F3n del Nive para este tr\u00E1mite. \n\rPara confirmar esta acci\u00F3n pulse aceptar")) {
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
				if (estadoTramite != '13') {
					alert("El tr\u00E1mite debe de estar en Estado Finalizado PDF para poder liberar el Nive.");
					return false;
				}
				if (tipoTramite != 'T1'){
					alert("El tipo de tr\u00E1mite no se puede liberar.");
					return false;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","liberarDocBaseNiveConsultaTramiteTrafico.action");
		$('#formData').submit();
	}
}

function cambiaEstadoIni(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","cambiaEstadoIniConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function descargarPresentacion576(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite para poder descargar los ficheros de presentacion del 576.");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","descargarPresentacion576ConsultaTramiteTrafico.action");
	$('#formData').submit();
	ocultarLoadingConsultar(boton, "Descargar 576");
}

function consultarEEFF(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","consultaLiberacionConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function liberarEEFFMasivo(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","liberarMasivoEeffConsultaTramiteTrafico.action");
	$('#formData').submit();
}
function descargarFichero05(){
	$('#formData').attr("action","descargarFichero05ConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function descargarFicheroPermiso(){
	$('#formData').attr("action","descargarFicheroPermisoConsultaTramiteTrafico.action");
	$('#formData').submit();
}
function descargarFicheroDistintivo(){
	$('#formData').attr("action","descargarFicheroDistintivoConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function autorizarImpresionBaja(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if(confirm("Est\u00E1 seguro de que desea autorizar los tr\u00E1mites seleccionados para la impresi\u00f3n de su informe?")){
		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","autorInfoBjConsultaTramiteTrafico.action");
		$('#formData').submit();
	}
}

function generarDocBase(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if(confirm("Ha solicitado generar un documento base para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")){
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "_" + checks[i].value;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		limpiarHiddenInput("listaExpedientes");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDocBaseConsultaTramiteTrafico.action").trigger("submit");
	}
}

function tramiteRevisado(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","tramiteRevisadoConsultaTramiteTrafico.action");
	$('#formData').submit();
}

function tramitarNRE06(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (confirm("Ha solicitado tramitar NRE06 para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		limpiarHiddenInput("listaExpedientes");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "tramitarNRE06ConsultaTramiteTrafico.action").trigger("submit");
	}
}

function cambiarFechaPresentacion(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if(confirm("Ha solicitado cambiar la fecha de presentacion para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")){
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		limpiarHiddenInput("listaExpedientes");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "cambiarFechaPresentacionConsultaTramiteTrafico.action").trigger("submit");
	}
}

function desasignarTasaCau(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (confirm("Ha solicitado desasignar tasa para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		limpiarHiddenInput("listaExpedientes");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarTasaCauConsultaTramiteTrafico.action").trigger("submit");
	}
}

function tramitarSolicitud05(boton){
	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	$("input[name='listaExpedientes']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if (codigos == "") {
		//ocultarLoadingConsultar(boton, 'btramitarSolicitud05');
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (codigos.split("-").length > '1') {
		//ocultarLoadingConsultar(boton,'btramitarSolicitud05');
		return alert("Solo se puede seleccionar un expediente.");
	}
	//mostrarLoadingConsultar(boton);
	var url = "https://"+ jQuery(location).attr('host')+"/oegam2/jnlpServlet05";

	document.formData.action = url + "?numExpediente=" + codigos;
	document.formData.submit();
}

function modificarReferenciaPropia(){
	var checks =  $("input[name='listaExpedientes']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}

	//Ingresamos un mensaje a mostrar
	var referencia = prompt("Ingrese la nueva referencia", "");
	//Detectamos si el usuario ingreso un valor
	if (referencia != null){
		var urlAction = "modificarReferenciaPropiaConsultaTramiteTrafico.action?nuevaReferenciaPropia=" + referencia;
		var $idForm = $("#formData");
		$idForm.attr("action", urlAction).trigger("submit");
	}
}

function autorizarTramitesIcogam(boton) {
		var codigos = "";
	$("input[name='listaExpedientes']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}else if (numCheckedImprimir() > 1) {
		alert("Debe seleccionar solo un tr\u00E1mite");
		return false;
	}

	var errores = document.getElementById("divError");
	if (errores != null) {
		errores.style.display = "none";
	}
		var $dest = $("#divEmergenteAutorizar");
	$.post("abrirPopUpAutorizarTramitesConsultaTramiteTrafico.action?listaExpedientes="+codigos, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 400,
				height: 290,
				buttons: {
					Aceptar : function() {
        				var valorSeleccionado = $("input[name='autorizadoSeleccionado']:checked").attr('title');
        				var valorAdicional = $("input[name='autorizadoSeleccionado']:checked").val();
						if (typeof valorAdicional !== 'undefined') {
           					 valorAdicional = valorAdicional.trim();
        				}
						if (valorSeleccionado == "IVTM" && valorAdicional == "SI") {
							alert("Ya ha autorizado el trámite para " +valorSeleccionado);
							return false;
						}else if (valorSeleccionado == "HERENCIA" && valorAdicional == "SI") {
							alert("Ya ha autorizado el trámite para " +valorSeleccionado);
							return false;
						}else if (valorSeleccionado == "DONACION" && valorAdicional == "SI") {
							alert("Ya ha autorizado el trámite para " +valorSeleccionado);
							return false;
						}
						mostrarLoadingConsultar(boton);
						limpiarHiddenInput("valorSeleccionado");
						input = $("<input>").attr("type", "hidden").attr("name", "valorSeleccionado").val(valorSeleccionado);
						$('#formData').append($(input));
						limpiarHiddenInput("valorAdicional");
						input = $("<input>").attr("type", "hidden").attr("name", "valorAdicional").val(valorAdicional);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
			
						$("#formData").attr("action", "autorizarTramitesIcogamConsultaTramiteTrafico.action").trigger("submit");
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

function borrarDatos(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con un tr\u00E1mite a la vez");
		return false;
	}
	if (confirm("Ha solicitado borrar datos para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
	}

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopUpBorrarDatosConsultaTramiteTrafico.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 400,
				height: 290,
				buttons: {
					Borrar : function() {
						var datoBorrar = $("#idDatoBorrar").val();
						if (datoBorrar == "") {
							alert("Seleccione un dato para borrar.");
							return false;
						}
						limpiarHiddenInput("datoBorrar");
						input = $("<input>").attr("type", "hidden").attr("name", "datoBorrar").val(datoBorrar);
						$('#formData').append($(input));
						mostrarLoadingConsultar(boton);
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));

						$("#formData").attr("action", "borrarDatosConsultaTramiteTrafico.action").trigger("submit");
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

function actualizarDatos(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con un tr\u00E1mite a la vez");
		return false;
	}
	if (confirm("Ha solicitado actualizar datos para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
	}

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopUpActualizarDatosConsultaTramiteTrafico.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 400,
				height: 290,
				buttons: {
					Actualizar : function() {
						var datoActualizar = $("#idDatoActualizar").val();
						if (datoActualizar == "") {
							alert("Seleccione un dato para actualizar.");
							return false;
						}
						var datoNuevo = $("#idDatoNuevo").val();
						if (datoNuevo == "") {
							alert("Rellene con el dato para actualizar.");
							return false;
						}
						limpiarHiddenInput("datoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "datoNuevo").val(datoNuevo);
						$('#formData').append($(input));
						mostrarLoadingConsultar(boton);
						limpiarHiddenInput("datoActualizar");
						input = $("<input>").attr("type", "hidden").attr("name", "datoActualizar").val(datoActualizar);
						$('#formData').append($(input));
						mostrarLoadingConsultar(boton);
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));

						$("#formData").attr("action", "actualizarDatosConsultaTramiteTrafico.action").trigger("submit");
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

function mostrarDatos() {
	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "," + checks[i].value;
			}
		}
		i++;
	}
	var combo = document.getElementById("idDatoBorrar");
	var seleccion = combo.options[combo.selectedIndex].value;

	var campo = document.getElementById("datosTexto");
		$.ajax({
		url:"buscarRespuestaConsultaTramiteTrafico.action",
		type:'POST',
		data: 'campo='+seleccion + '&numExpediente=' + codigos,
		dataType: 'text',
		success: function(data){
			campo.value = data
		},
		error: function() {
			alert('Ha sucedido un error a la hora de devolver la respuesta.');
		}
	});
}

function mostrarDatosActualizar() {
	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "," + checks[i].value;
			}
		}
		i++;
	}
	var combo = document.getElementById("idDatoActualizar");
	var seleccion = combo.options[combo.selectedIndex].value;

	var campo = document.getElementById("datosTexto");
		$.ajax({
		url:"buscarDatoActualizarConsultaTramiteTrafico.action",
		type:'POST',
		data: 'campo='+seleccion + '&numExpediente=' + codigos,
		dataType: 'text',
		success: function(data){
			campo.value = data
		},
		error: function() {
			alert('Ha sucedido un error a la hora de devolver la respuesta.');
		}
	});
}

function asignarTasaXml(boton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
	}
	if (numCheckedImprimir().length > '1'){
		//ocultarLoadingConsultar(boton,'btramitarSolicitud05');
		alert("Solo se puede seleccionar un expediente.");
		return false;
	}
	if (confirm("Ha solicitado asignar tasa del XML enviado para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
					var tipoTramite =  document.getElementById("tablaConsultaTramite").rows[i+1].cells[6].innerText;
				 if(tipoTramite!="TRANSMISION ELECTRONICA"){
					alert("El tipo de tr\u00E1mite debe ser TRANSMISION ELECTRONICA");
					return false;
				}
				break;
			
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
		limpiarHiddenInput("listaExpedientes");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "asignarTasaXmlConsultaTramiteTrafico.action").trigger("submit");
	}
}

function actualizarPuebloInterviniente(boton) {
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedImprimir() > 1) {
		alert("S\u00F3lo puede realizar esta acci\u00F3n con un tr\u00E1mite a la vez");
		return false;
	}
	if (confirm("Ha solicitado modificar el pueblo para los expedientes seleccionados.\n\rPara confirmar esta accion pulse aceptar.")) {
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "," + checks[i].value;
				}
			}
			i++;
		}
	}

	var $dest = $("#divEmergenteMatricula");
	$.post("abrirPopUpActualizarPuebloConsultaTramiteTrafico.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 400,
				height: 290,
				buttons: {
					Actualizar : function() {
						var interviniente = $("#idIntervinientePueblo").val();
						var puebloNuevo = $("#idPuebloNuevo").val();
						if (puebloNuevo == "") {
							alert("Rellene con el nombre del pueblo.");
							return false;
						}
						limpiarHiddenInput("puebloNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "puebloNuevo").val(puebloNuevo);
						$('#formData').append($(input));
						mostrarLoadingConsultar(boton);
						limpiarHiddenInput("campo");
						input = $("<input>").attr("type", "hidden").attr("name", "interviniente").val(interviniente);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));

						$("#formData").attr("action", "actualizarPuebloConsultaTramiteTrafico.action").trigger("submit");
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

function mostrarDatosInterviniente() {
	var checks = document.getElementsByName("listaExpedientes");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "," + checks[i].value;
			}
		}
		i++;
	}
	var combo = document.getElementById("idIntervinientePueblo");
	var seleccion = combo.options[combo.selectedIndex].value;

	var campo = document.getElementById("idDatosInterviniente");
		$.ajax({
		url:"buscarIntervinienteConsultaTramiteTrafico.action",
		type:'POST',
		data: 'campo='+seleccion + '&numExpediente=' + codigos,
		dataType: 'text',
		success: function(data){
			campo.value = data
		},
		error: function() {
			alert('Ha sucedido un error a la hora de devolver la respuesta.');
		}
	});
}