/**
 *  Funciones JS de Duplicados
 */

// GUARDAR ALTAS NUEVAS DE TRAMITES DE DUPLICADO
function validacionesGuardarAltaTramiteDuplicado() {

	if (!validaNIF(document.getElementById("dniTitular"))) {
		alert("DNI de Titular no valido.");
		return false;
	}

	if (!validaNIF(document.getElementById("nifRepresentanteTitular"))) {
		alert("Dni de Representante no valido.");
		return false;
	}

	if (document.getElementById("diaMatriculacionVehiculo").value != ""
		|| document.getElementById("mesMatriculacionVehiculo").value != ""
		|| document.getElementById("anioMatriculacionVehiculo").value != "") {

		if (!validaInputFecha(document
			.getElementById("diaMatriculacionVehiculo"), document
				.getElementById("mesMatriculacionVehiculo"), document
					.getElementById("anioMatriculacionVehiculo"), false)) {
			alert("Fecha de Primera Matriculaci\u00f3n no valida.");
			return false;
		}
	}

	if ((document.getElementById("diaPresentacion").value != "")
		|| document.getElementById("mesPresentacion").value != ""
		|| document.getElementById("anioPresentacion").value != "") {
		if (!validaInputFecha(document.getElementById("diaPresentacion"),
			document.getElementById("mesPresentacion"), document
				.getElementById("anioPresentacion"), false)) {
			alert("Fecha de presentacion no valida.");
			return false;
		}
	}
	return true;
}

function validacionesFinalizarAltaTramiteDuplicado() {
	/*
	 * Antes de guardar se comprueba que los datos obligatorios se hayan
	 * introducido en el formulario y que se han seleccionado o rellenado los
	 * campos necesarios. Ademas se validan que NIF's y los campos de Fecha.
	 */

	// Pestania Baja
	// Se valida que se seleccione un motivo en la baja.
	if (document.getElementById("motivoDuplicado").value == "-1") {
		alert("Debe seleccionar un motivo para el duplicado.");
		return false;
	}

	// Pestaniaa Vehiculo
	// Se valida la introduccion de una matricula en el formulario.
	if (document.getElementById("matriculaVehiculo").value == "") {
		alert("Debe introducir una matricula para el vehiculo.");
		return false;
	}
	// Se valida la fecha de Matriculacion del Vehiculo si existe
	if (document.getElementById("diaMatriculacionVehiculo").value != ""
		|| document.getElementById("mesMatriculacionVehiculo").value != ""
		|| document.getElementById("anioMatriculacionVehiculo").value != "") {
		if (!validaInputFecha(document
			.getElementById("diaMatriculacionVehiculo"), document
				.getElementById("mesMatriculacionVehiculo"), document
					.getElementById("anioMatriculacionVehiculo"), false)) {
			alert("Fecha de Primera Matriculaci\u00f3n no valida.");
			return false;
		}
	} else {
		alert("Debe introducir la Fecha de Matriculaci\u00f3n del veh\u00edculo.");
		return false;
	}

	// Pestania Titular
	// Se valida que haya un NIF y que sea correcto
	if (document.getElementById("dniTitular").value == "") {
		alert("Debe introducir un NIF para el Titular.");
		return false;
	} else {
		if (!validaNIF(document.getElementById("dniTitular"))) {
			alert("NIF de Titular no valido.");
			return false;
		}
			// Se valida que se ha introducido un Primer Apellido o Razon Social si
			// hay un nif.
			/*
			 * if (document.getElementById("primerApellidoTitular").value==""){
			 * alert("Debe introducir un Primer Apellido o Raz\u00f3n Social.");
			 * return false; }
			 */}
	/*
	 * if (document.getElementById("idProvinciaTitular").value=="-1"){
	 * alert("Debe introducir la Provincia del Titular."); return false; } if
	 * (document.getElementById("idMunicipioTitular").value=="-1"){ alert("Debe
	 * introducir la Provincia del Titular."); return false; } if
	 * (document.getElementById("cpTitular").value==""){ alert("Debe introducir
	 * el C\u00f3digo Postal del Titular."); return false; }
	 */
	// Pestania Representante
	// Se valida el DNI del Representante
	if (!validaNIF(document.getElementById("nifRepresentanteTitular"))) {
		alert("NIF de Representante no valido.");
		return false;
	}

	return true;
}

function guardarAltaTramiteDuplicado(pestania) {
	if (!validacionesGuardarAltaTramiteDuplicado())
		return false;
	document.formData.action = "guardarTramiteDuplicadoAltasTramiteDuplicado.action#"
		+ pestania;
	document.getElementById('diaMatriculacionVehiculo').disabled = false;
	document.getElementById('mesMatriculacionVehiculo').disabled = false;
	document.getElementById('anioMatriculacionVehiculo').disabled = false;
	document.getElementById('checkBoxPermisoCirculacion').disabled = false;
	document.getElementById('codigoTasaPermiso').disabled = false;
	document.getElementById('tasaFichaTecnica').disabled = false;

	document.formData.submit();
	loadingGuardarDuplicado();
	return true;
}

function finalizarAltaTramiteDuplicado(pestania) {
	if (!validacionesFinalizarAltaTramiteDuplicado())
		return false;
	document.formData.action = "finalizarTramiteDuplicadoAltasTramiteDuplicado.action#"
		+ pestania;
	document.formData.submit();
	loadingGuardarDuplicado();
	return true;
}

/** Obtiene un interviniente por NIF, en Duplicados** */
function buscarIntervinienteDuplicado(tipoInterviniente, pestania) {
	// hidden en el action con el campo tipoIntervinienteBuscar
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "buscarIntervinienteAltasTramiteDuplicado.action#"
		+ pestania;
	document.formData.submit();
}
/*function buscarIntervinienteDuplicado(tipoInterviniente, pestania){
	var nif = $('#dniTitular').val();
	if(nif != ""){
		limpiarFormularioTitular();
		var $dest = $("#divTitularDuplicado", parent.document);
		deshabilitarBotonesDuplicados();
		$.ajax({
			url:"buscarIntervinienteAltasTramiteDuplicado.action#Titular",
			data:"nif="+ nif + "&tipoInterviniente=004" + "&idContrato=" + $("#idContrato").val() +  "&idModelo=" + $("#idModelo").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#primerApellidoTitular').val() != "" || $('#primerApellidoTitular').val() != null){
					$("#idLimpiarTransmitente").show();
					$("#botonBuscarTitular").prop("disabled", true);
					$('#dniTitular').prop("disabled", true);
				}else{
					$("#botonBuscarTitular").prop("disabled", false);
					$('#dniTitular').prop("disabled", false);
				}
				habilitarBotonesTransmitente();
				habilitarCamposTransmitente();
				$('#tipoPersonaTitular').prop("disabled", true);
				$('#sexoTitular').prop("disabled", true);
				$('#nombreViaTitular').prop("disabled", true);
			},
			error : function(xhr, status) {
				alert('Ha sucedido un error a la hora de cargar el transmitente.');
				habilitarBotonesTransmitente();
			}
		});
	}else{
		alert("Para poder realizar la busqueda del transmitente debe rellenar el NIF/CIF.");
	}
}
*/

function loadingDuplicar() {
	document.getElementById("idBotonGuardar").disabled = "true";
	document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	document.getElementById("idBotonDuplicar").disabled = "true";
	document.getElementById("idBotonDuplicar").style.color = "#6E6E6E";
	document.getElementById("idBotonVolver").disabled = "true";
	document.getElementById("idBotonVolver").style.color = "#6E6E6E";
	document.getElementById("idBotonDuplicar").value = "Duplicando";
	document.getElementById("loadingImage").style.display = "block";
	document.getElementById("idBotonValidarMatricular").disabled = "true";
	document.getElementById("idBotonValidarMatricular").style.color = "#6E6E6E";
}

function loadingGuardarDuplicado() {
	document.getElementById("bGuardar1").disabled = "true";
	document.getElementById("bGuardar1").style.color = "#6E6E6E";
	document.getElementById("bGuardar1").value = "Procesando";
	document.getElementById("bGuardar2").disabled = "true";
	document.getElementById("bGuardar2").style.color = "#6E6E6E";
	document.getElementById("bGuardar2").value = "Procesando";
	document.getElementById("bGuardar3").disabled = "true";
	document.getElementById("bGuardar3").style.color = "#6E6E6E";
	document.getElementById("bGuardar3").value = "Procesando";
	if (document.getElementById("bGuardar4") != null) {
		document.getElementById("bGuardar4").disabled = "true";
		document.getElementById("bGuardar4").style.color = "#6E6E6E";
		document.getElementById("bGuardar4").value = "Procesando";
		document.getElementById("loadingImage4").style.display = "block";
	}

	if (document.getElementById("bFinalizar") != null) {
		document.getElementById("bFinalizar").disabled = "true";
		document.getElementById("bFinalizar").style.color = "#6E6E6E";
	}
	document.getElementById("loadingImage1").style.display = "block";
	document.getElementById("loadingImage2").style.display = "block";
	document.getElementById("loadingImage3").style.display = "block";
}

function cargaPestaniasDuplicCambDomicConduc() {
	if (document.getElementById('motivoDuplicado').value == 1 || document.getElementById('motivoDuplicado').value == 2) {
		$("#checkBoxPermisoCirculacion").hide();
		$("#checkBoxPermisoCirculacionLabel").hide();
		if (document.getElementById('motivoDuplicado').value == 2){
			$("#Vehiculo").hide();
			$("#Cotitular").hide();
			$("#importacion").prop('checked', false);
			document.getElementById("tasaImportacion").disabled=true;
			$("#importacion").hide();
			$("#importacionLabel").hide();
			$("#tasaImportacion").hide();
			$("#importacionLabeltext").hide();
			$("#labelTasaImportacion").hide();
		}else{
			$("#Vehiculo").show();
			$("#Cotitular").show();
			$("#importacion").show();
			$("#importacionLabel").show();
			$("#tasaImportacion").show();
			$("#importacionLabeltext").show();
			$("#labelTasaImportacion").show();
		}
		$("#tasaImportacion").show();
		/*$("#idbarraCotitular").hide();*/
		$("#codigoTasaPagoPresentacion").prop('disabled', 'disabled');
		$("#codigoTasaPermiso").val('-1');
		$("#codigoTasaPermiso").prop('disabled', 'disabled');
		$("#tasaFichaTecnica").val('-1');
		$("#tasaFichaTecnica").prop('disabled', 'disabled');
		$("#idTdComprobar").hide();
		$("#tipoDocumentoId").prop('disabled', 'disabled');
	} else {
		$("#Vehiculo").show();
		$("#Cotitular").show();
		$("#checkBoxPermisoCirculacion").hide();
		$("#checkBoxPermisoCirculacionLabel").hide();
		$("#importacion").show();
		$("#importacionLabel").show();
		$("#tasaImportacion").show();
		$("#labelTasaImportacion").show();
		/*$("#idbarraCotitular").show();*/
		$("#importacionLabeltext").show();
		$("#codigoTasaPagoPresentacion").show();
		$("#labelcodigoTasaPagoPresentacion").show();
		$("#codigoTasaPagoPresentacion").prop('disabled', false);
		$("#codigoTasaPermiso").prop('disabled', false);
		$("#tasaFichaTecnica").prop('disabled', false);
		$("#idTdComprobar").show();
		$("#tipoDocumentoId").prop('disabled', false);
	}
}
function checkImpresoPermiso() {
	var seleccionTipo = 0;
	if (document.getElementById('motivoDuplicado') != null) {
		seleccionTipo = document.getElementById('motivoDuplicado').value;
	}

	if (seleccionTipo == "3") {
		document.getElementById('checkBoxPermisoCirculacion').checked = true;
		document.getElementById('checkBoxPermisoCirculacion').disabled = true;
	} else if (seleccionTipo == "4") {
		document.getElementById('checkBoxPermisoCirculacion').checked = true;
		document.getElementById('checkBoxPermisoCirculacion').disabled = true;
	} else if (seleccionTipo == "5") {
		document.getElementById('checkBoxPermisoCirculacion').checked = true;
		document.getElementById('checkBoxPermisoCirculacion').disabled = true;
	} else if (seleccionTipo == "1") {
		if (document.getElementById("jefaturaProvincialPagoPresentacion").value == 'M') {
			document.getElementById('checkBoxPermisoCirculacion').checked = false;
			document.getElementById('checkBoxPermisoCirculacion').disabled = true;
		} else {
			document.getElementById('checkBoxPermisoCirculacion').disabled = false;
			if (document.getElementById('checkBoxPermisoCirculacion').checked) {
				document.getElementById('checkBoxPermisoCirculacion').checked = true;
			} else {
				document.getElementById('checkBoxPermisoCirculacion').checked = false;
			}
		}
	} else {
		document.getElementById('checkBoxPermisoCirculacion').checked = false;
		document.getElementById('checkBoxPermisoCirculacion').disabled = false;
	}
}

/*
* Metodo que realiza la llamada Post del formulario del que se pasa el id
* (idForm). llamando a la url que se pasa por parametro (url). dest, opcional.
* Si dest viene informado, se carga el contenido en el elemento que tenga por
* id, dest. Si no viene informado, el contenido se carga en la pagina actual
* success, opcional, función que se ejecutará de modo callback cuando el POST
* acabe con exito. (Si hay que anidar muchos ", poner %)
*/
function doPost(idForm, url, dest, success) {
	$("#propiedadDtoCategoria").attr('disabled', false);
	var $form = $("#" + idForm);
	if (dest != null && dest.length > 0) {
		$dest = $("#" + dest);
		if (!$dest.size()) {
			$dest = $("#" + dest, parent.document);
		}
		$.post(url, $form.serialize(), function(data) {
			$dest.empty().append(data);
		}).success(function(data) {
			if (success != null && success.length > 0) {
				eval(success.replace(/%/g, "\""));
			}
		});
	} else {
		$('input[type=button]').attr('disabled', true);
		$form.attr("action", url).trigger("submit");
	}
}

function checkHabilitarPestaniaDocumento() {
	//document.getElementById('tipoDocumentoId').value = -1
	if ($('#motivoDuplicado').attr('value') == 1 && $('#importacion').attr('checked') && $('#jefaturaProvincialPagoPresentacion').attr('value') == 'M') {
		$("#Documentacion").show();
	} else {
		$("#Documentacion").hide();
	}
}

function descargarDocumentacion(nombre) {
	limpiarHiddenInput("nombreDoc");

	let input = $("<input>").attr("type", "hidden").attr("name", "nombreDoc").val(nombre);
	$('#formData').append($(input));

	$("#formData").attr("action", "descargarDocumentacion" + "AltaTramiteTraficoDuplicado.action#Documentacion").trigger("submit");
}

function limpiarHiddenInput(nombre) {
	if ($("input[name='" + nombre + "']").is(':hidden')) {
		$("input[name='" + nombre + "']").remove();
	}
}

function eliminarFichero(idFichero) {
	if (confirm("¿Está seguro de que desea borrar este fichero?")) {
		document.formData.idFicheroEliminar.value = idFichero;
		document.formData.action = "eliminarFichero" + "AltaTramiteTraficoDuplicado" + ".action#Documentacion";
		document.formData.submit();
	}
}

function enviarFichero() {
	//doPost('formData', 'incluirFicheroAltaTramiteTraficoDuplicado.action\u0023Documentacion', '', '');
	// Domicilio fiscal relleno
	if (document.getElementById('idProvinciaTitular').value == -1
		|| document.getElementById('idMunicipioTitular').value == -1
		|| document.getElementById('cpTitular').value.length == 0
		|| document.getElementById('nombreViaTitular').value.trim().length == 0
		|| document.getElementById('tipoViaTitular').value.length == 0
		|| document.getElementById('dniTitular').value.trim().length == 0) {

		alert('Es necesario introducir los datos del titular');
	} else {
		doPost('formData', 'incluirFicheroAltaTramiteTraficoDuplicado.action\u0023Documentacion', '', '');
	}
}

function comprobarAltaTramiteTraficoDuplicado() {
	deshabilitarBotonesDuplicados();
	var pestania = obtenerPestaniaSeleccionada();
	document.getElementById('checkBoxPermisoCirculacion').disabled = false;
	document.formData.action = "comprobarAltaTramiteTraficoDuplicado.action#" + pestania;
	document.formData.submit();
}

function tramitarAltaTramiteTraficoDuplicado() {
	deshabilitarBotonesDuplicados();
	var pestania = obtenerPestaniaSeleccionada();
	document.getElementById('checkBoxPermisoCirculacion').disabled = false;
	document.formData.action = "tramitarAltaTramiteTraficoDuplicado.action#" + pestania;
	document.formData.submit();
}

function tasaTipoDocumento() {
	var tipoDocumento = document.getElementById('tipoDocumentoId').value;
	if (tipoDocumento == "1") {
		$("#codigoTasaPermiso").prop('disabled', false);
		$("#tasaFichaTecnica").val('-1');
		$("#tasaFichaTecnica").prop('disabled', 'disabled');
	} else if (tipoDocumento == "2") {
		$("#codigoTasaPermiso").val('-1');
		$("#codigoTasaPermiso").prop('disabled', 'disabled');
		$("#tasaFichaTecnica").prop('disabled', false);
	} else if (tipoDocumento == "3") {
		$("#codigoTasaPermiso").prop('disabled', false);
		$("#tasaFichaTecnica").prop('disabled', false);
	}
}