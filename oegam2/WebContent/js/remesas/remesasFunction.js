
var opciones_ventana = 'width=850,height=400,top=150,left=280';
var opciones_ventana_bienes = 'width=850,height=600,top=150,left=280';
var input;

function seleccionarSexoPorTipoPersona(idTipoPersona,idSexo){
	if($("#"+idTipoPersona).val() == "JURIDICA" || $("#"+idTipoPersona).val() == "JURIDICA_PUBLICA"){
		$("#"+idSexo).val("X");
		$("#"+idSexo).prop("disabled",true);
	}else{
		$("#"+idSexo).val("-1");
		$("#"+idSexo).prop("disabled",false);
	}
}

function habilitarTipoBien(){
	var $divUrbano = $("#divBienUrbano");
	var $divRustico = $("#divBienRustico");
	var $divOtroBien = $("#divOtroBien");

	if($('#idTipoBien option:selected').val() == "URBANO"){
		$divRustico.hide();
		$divOtroBien.hide();
		$divUrbano.show();
		if($("#idEstadoRemesa").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		$divRustico.show();
		$divUrbano.hide();
		$divOtroBien.hide();
		if($("#idEstadoRemesa").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}else if($('#idTipoBien option:selected').val() == "OTRO"){
		$divRustico.hide();
		$divUrbano.hide();
		$divOtroBien.show();
		if($("#idEstadoRemesa").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}
	else{
		$divRustico.hide();
		$divUrbano.hide();
		$divOtroBien.hide();
		if($("#idEstadoModelos").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}
	if($("#idEstadoRemesa").val() != "3"){
		var $tablaBienesRustico = $("#tablaBienesRusticos");
		if($tablaBienesRustico.length > 0){
			$("#idEliminarBienesRustico").show();
			$("#idModificarBienesRustico").show();
		}else{
			$("#idEliminarBienesRustico").hide();
			$("#idModificarBienesRustico").hide();
		}
		var $tablaBienesUrbano = $("#tablaBienesUrbanos");
		if($tablaBienesUrbano.length > 0){
			$("#idEliminarBienesUrbano").show();
			$("#idModificarBienesUrbano").show();
		}else{
			$("#idEliminarBienesUrbano").hide();
			$("#idModificarBienesUrbano").hide();
		}
		var $tablaOtrosBienes = $("#tablaOtrosBienes");
		if($tablaOtrosBienes.length > 0){
			$("#idEliminarOtrosBienes").show();
			$("#idModificarOtrosBienes").show();
		}else{
			$("#idEliminarOtrosBienes").hide();
			$("#idModificarOtrosBienes").hide();
		}

	}
}

/**
 * Borra el contenido de los inputs de tipo texto contenidos en el div con id pasado por parametro.
 * Tambien limpia los combos (asegurate de que los combos tienen un ID unico)
 * @param idDiv
 * 			Identificador de la capa que contiene los campos a borrar
 */
function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " input[type=hidden]").attr("value", "");
	$("#" + idDiv + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
}

function limpiarBien(){
	//deshabilitarBotonesBienes();
	if($('#idTipoBien option:selected').val() == "URBANO"){
		limpiarCamposBienUrbano();
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		limpiarCamposBienRustico();
	} else {
		limpiarCamposOtroBien();
	}
}

function limpiarCamposBienUrbano(){
	limpiarDiv('bienesUrbanos');
}

function limpiarCamposBienRustico(){
	limpiarDiv('bienesRusticos');
}

function limpiarCamposOtroBien(){
	limpiarDiv('otrosBienes');
}

function limpiarFormularioSujetoPasivo(){
	$('#idNifSujPasivo').val("");
	$('#idDireccionSujPasivo').val("");
	$('#trDireccionSujPasivo').hide();
	$('#idTipoPersonaSujPasivo').val("-1");
	$('#idSexoSujPasivo').val("-1");
	$('#idPrimerApeRazonSocialSujPasivo').val("");
	$('#idSegundoApeSujPasivo').val("");
	$('#idNombreSujPasivo').val("");
	$('#idDiaNacSujPasivo').val("");
	$('#idMesNacSujPasivo').val("");
	$('#idAnioNacSujPasivo').val("");
	$('#idProvinciaSujPasivo').val("-1");
	$('#idMunicipioSujPasivo').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioSujPasivo').val("-1");
	$("#municipioSeleccionadoSujPasivo").val("");
	$('#idTipoViaSujetoPasivo').find('option').remove().end().append('<option value="-1">Seleccione Tipo de Via</option>').val('whatever');
	$('#idTipoViaSujetoPasivo').val("-1");
	$('#idPuebloSujPasivo').find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#idPuebloSujPasivo').val("-1");
	$('#idNombreViaSujPasivo').val("");
	$('#idCodPostalSujPasivo').val("");
	$('#idNumeroDireccionSujPasivo').val("");
	$('#idLetraDireccionSujPasivo').val("");
	$('#idEscaleraDireccionSujPasivo').val("");
	$('#idPisoDireccionSujPasivo').val("");
	$('#idPuertaDireccionSujPasivo').val("");
	$('#idBloqueDireccionSujPasivo').val("");
	$('#idKmDireccionSujPasivo').val("");
	$('#idNifSujPasivo').prop("disabled", false);
	$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
	$("#idLimpiarSujPasivo").hide();
}

function limpiarFormularioTransmitente(){
	$('#idNifTransmitente').val("");
	$('#idDireccionTransmitente').val("");
	$('#trDireccionTransmitente').hide();
	$('#idTipoPersonaTransmitente').val("-1");
	$('#idSexoTransmitente').val("-1");
	$('#idPrimerApeRazonSocialTransmitente').val("");
	$('#idSegundoApeTransmitente').val("");
	$('#idNombreTransmitente').val("");
	$('#idDiaNacTransmitente').val("");
	$('#idMesNacTransmitente').val("");
	$('#idAnioNacTransmitente').val("");
	$('#idProvinciaTransmitente').val("-1");
	$('#idMunicipioTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioTransmitente').val("-1");
	$('#idPuebloTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idPuebloTransmitente').val("-1");
	$("#municipioSeleccionadoTransmitente").val("");
	$('#idTipoViaTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Tipo de Via</option>').val('whatever');
	$('#idTipoViaTransmitente').val("-1");
	$('#tipoViaSeleccionadaTransmitente').val("");
	$('#idNombreViaTransmitente').val("");
	$('#idCodPostalTransmitente').val("");
	$('#idNumeroDireccionTransmitente').val("");
	$('#idLetraDireccionTransmitente').val("");
	$('#idEscaleraDireccionTransmitente').val("");
	$('#idPisoDireccionTransmitente').val("");
	$('#idPuertaDireccionTransmitente').val("");
	$('#idBloqueDireccionTransmitente').val("");
	$('#idKmDireccionTransmitente').val("");
	$('#idNifTransmitente').prop("disabled", false);
	$("#idBotonBuscarNifTransmitente").prop("disabled", false);
	$("#idLimpiarTransmitente").hide();
}

function seleccionarUsoRustico(idHiddenTipoUsoRustico, valor){
	var $tipoUsoRustico = $("#" + idHiddenTipoUsoRustico);
	$tipoUsoRustico.val(valor);
	deshabilitarCamposUsoBienRustico();
}

function controlNumeros(e){
	var charCode = event.keyCode;
	if ((charCode >= 48 && charCode <= 57) || charCode == 9){
		return true;
	}else{
		return false;
	}	
}

function deshabilitarNotarioProtocolo(idForm){	
	var $checkTipoDocPRI = $("#tipoDocPRI");
	var $checkTipoDocADM = $("#tipoDocADM");
	var $checkTipoDocJUD = $("#tipoDocJUD");
	var $checkTipoDocPUB = $("#tipoDocPUB");
	var $protocoloBis = $("#idProtocoloBis");
	var $numProtocolo = $("#idNumProtocolo");
	var $botonNotario = $("#botonNotario");
	
	var $nifNotario = $("#idNotarioNif");
	var $apellido1 = $("#idNotarioApe1");
	var $apellido2 = $("#idNotarioApe2");
	var $nombre = $("#idNotarioApe1");
	if($("#idEstadoRemesa").val() != "3"){
		if($checkTipoDocPRI.is(':checked') || $checkTipoDocADM.is(':checked') || $checkTipoDocJUD.is(':checked')){
			$protocoloBis.prop("disabled", true);
			$protocoloBis.prop("checked", false);
			$numProtocolo.prop("readOnly", true);
			$botonNotario.hide();
			$nifNotario.val("");
			$apellido1.val("");
			$apellido2.val("");
			$nombre.val("");
		}else if($checkTipoDocPUB.is(':checked')){
			$protocoloBis.prop("disabled", false);
			$numProtocolo.prop("readOnly", false);
			$botonNotario.show();
		}else{		
			$protocoloBis.prop("disabled", true);
			$numProtocolo.prop("readOnly", true);
			$botonNotario.hide();
			$nifNotario.val("");
			$apellido1.val("");
			$apellido2.val("");
			$nombre.val("");
		}
	}
}

function habilitarNotario(){
	var $checkTipoDocPRI = $("#tipoDocPRI");
	var $checkTipoDocADM = $("#tipoDocADM");
	var $checkTipoDocJUD = $("#tipoDocJUD");
	var $checkTipoDocPUB = $("#tipoDocPUB");
	var $botonNotario = $("#botonNotario");
	if($checkTipoDocPRI.is(':checked') || $checkTipoDocADM.is(':checked') || $checkTipoDocJUD.is(':checked')){
		$botonNotario.hide();
	}else if($checkTipoDocPUB.is(':checked')){
		$botonNotario.show();
	}else{		
		$botonNotario.hide();
	}
}
var ventanaNotario;
var ventanaBienRustico;
var ventanaBienUrbano;
var ventanaBienOtro;

function abrirVentanaNotarioPopUp(){
	ventanaNotario = window.open('inicioConsultaNotarioRM.action', 'popup', opciones_ventana + ',resizable=NO,status=NO,menubar=NO');
	document.getElementById("divEmergenteDocumento").style.display = "block";
	ventanaNotario.focus();
}

function abrirVentanaBienRusticoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposBienRustico();
	ventanaBienRustico = window.open('inicioConsultaBienRustico.action', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienRustico.focus();
}

function abriVentanaBienUrbanoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposBienUrbano();
	ventanaBienUrbano = window.open('inicioConsultaBienUrbano.action', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienUrbano.focus();
}

function invokeSeleccionNotario(codigo){
	document.getElementById("divEmergenteDocumento").style.display = "none";
	var $dest = $("#divNotario");
	$.ajax({
		url:"notarioNuevoAjaxRMS.action",
		data:"codigo="+ codigo + "&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	    }
	});
	ventanaNotario.close();
}

function deshabilitarCamposNotario(){
	$("#idCodNotario").prop("disabled", false);
	$("#idNotarioNombre").prop("disabled", false);
	$("#idNotarioApellidos").prop("disabled", false);
	$("#idExprAbreviada").prop("disabled", false);
}

function guardarBien(){
	if($('#idTipoBien option:selected').val() == "URBANO"){
		if($("#idTipoInmuebleUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		if($("#idProvinciaBienUrbano",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienUrbano",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
		
		if($("#idTipoVia",parent.document).val() == "-1" || $("#tipoViaSeleccionadaUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de vía para la dirección del bien.");
		}
		if($("#idNombreViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el nombre de la vía del bien.");
		}
		if($("#idNumeroViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el número de la vía del bien.");
		}
		$("#idBienUrbanoDto",parent.document).prop("disabled", false);
		$("#idVHabitual", parent.document).prop("disabled", false);
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		if($("#idTipoInmuebleRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		
		if($("#idUsoRusticoGanaderia",parent.document).val() == "" && $("#idUsoRusticoCultivo",parent.document).val() == "" && $("#idUsoRusticoOtros",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de uso para poder guardar el bien.");
		}
		if($("#idSuperficieRustico",parent.document).val() == ""){
			return alert("Debe rellenar la superficie del bien.");
		}
		if( $("#idUsoRusticoCultivo",parent.document).val() != ""){
			if($("#idUnidadMetricaRustico",parent.document).val() == ""){
				return alert("Debe seleccionar alguna Unidad M\u00E9trica para poder guardar el bien.");
			}
		}
		if($("#idProvinciaBienRustico",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienRustico",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
		$("#idBienRusticoDto",parent.document).prop("disabled", false);
	}
	deshabilitarBotonesBienes();
	deshabilitarCamposComunes();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action","guardarBienRms.action#"+pestania).trigger("submit");
}
function deshabilitarCamposComunes(){
	deshabilitarCamposNotario();
	deshabilitarCamposPresentador();
	habilitarCamposSujetoPasivo();
	habilitarCamposTransmitente();
	$("#idTipoBien").prop("disabled", false);
	$("#idProvinciaOficinaLiquid").prop("disabled", false);
	$("#idNumExpediente").prop("disabled", false);
	$("#idBonificacionMonto").prop("disabled", false);
	$("#idEstadoRemesa").prop("disabled", false);
	$("#idNumBienes").prop("disabled", false);
	$("#idNifSujPasivo",parent.document).prop("disabled", false);
	$("#idNifTransmitente",parent.document).prop("disabled", false);
	$("#idBienRusticoDto",parent.document).prop("disabled", false);
	$("#idBienUrbanoDto",parent.document).prop("disabled", false);
	$("#idDireccionRustico").prop("disabled", false);
	$("#idDireccionUrbano").prop("disabled", false);
	$("#idFundamentoNoSujeto").prop("disabled", false);
	$("#idNoSujeto").prop("disabled", false);
	$("#idExento").prop("disabled", false);
	$("#idFundamentoExento").prop("disabled", false);
	$("#idEstadoRemesa").prop("disabled", false);
}


function deshabilitarCamposPresentador(){
	$("#idNifPresentador").prop("disabled", false);
	$("#idTipoPersonaPresentador").prop("disabled", false);
	$("#idSexoPresentador").prop("disabled", false);
	$("#idPrimerApeRazonSocialPresentador").prop("disabled", false);
	$("#idSegundoApePresentador").prop("disabled", false);
	$("#idNombrePresentador").prop("disabled", false);
	$("#idDiaNacPresentador").prop("disabled", false);
	$("#idMesNacPresentador").prop("disabled", false);
	$("#idAnioNacPresentador").prop("disabled", false);
	$("#idProvinciaPresentador").prop("disabled", false);
	$("#idNombreViaPresentador").prop("disabled", false);
	$("#idCodPostalPresentador").prop("disabled", false);
	$("#idNumeroDireccionPresentador").prop("disabled", false);
	$("#idLetraDireccionPresentador").prop("disabled", false);
	$("#idEscaleraDireccionPresentador").prop("disabled", false);
	$("#idPisoDireccionPresentador").prop("disabled", false);
	$("#idPuertaDireccionPresentador").prop("disabled", false);
	$("#idBloqueDireccionPresentador").prop("disabled", false);
	$("#idKmDireccionPresentador").prop("disabled", false);
	$("#idMunicipioPresentador").prop("disabled", false);
	$("#idPuebloPresentador").prop("disabled", false);
	$("#idTipoViaPresentador").prop("disabled", false);
	$("#hmDireccionPresentador").prop("disabled", false);
}


function invokeSeleccionBienRustico(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divBienRustico");
	$.ajax({
		url:"bienNuevoAjaxRMS.action",
		data:"codigo="+ codigo + "&tipoBien=RUSTICO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				limpiarCamposBienUrbano();
				$("#divBienUrbano").hide();
				$("#divOtroBien").hide();
				$("#divBienRustico").show();
				$('#idTipoBien').val("RUSTICO"); 
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos del bien Rustico.');
	    }
	});
	ventanaBienRustico.close();
}

function invokeSeleccionBienUrbano(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divBienUrbano");
	$.ajax({
		url:"bienNuevoAjaxRMS.action",
		data:"codigo="+ codigo + "&tipoBien=URBANO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				$("#divBienUrbano").show();
				limpiarCamposBienRustico();
				$("#divBienRustico").hide();
				$("#divOtroBien").hide();
				$('#idTipoBien').val("URBANO"); 
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos del bienUrbano.');
	    }
	});
	ventanaBienUrbano.close();
}

function invokeSeleccionBienOtro(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divOtroBien");
	$.ajax({
		url:"bienNuevoAjaxRMS.action",
		data:"codigo="+ codigo + "&tipoBien=OTRO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				$dest.show();
				$("#idTipoBien").val("OTRO");
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	    }
	});
	ventanaBienOtro.close();
}


function modificarBienes(dest,tipo){
	var $dest;
	if(tipo == 'OTRO') {
		var $dest = $("#divOtroBien");
	} else {
		$dest = $("#" + dest, parent.document);
	}
	var codigos = "";
	var checks = null;
	var i = 0;
	var numChecks = 0;
	if(tipo == 'URBANO'){
		checks = document.getElementsByName("listaChecksBienUrbano");
	}else if(tipo == 'RUSTICO'){
		checks = document.getElementsByName("listaChecksBienRustico");
	} else {
		checks = document.getElementsByName("listaChecksOtroBien");
	}
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn Bien para modificar.");
	}else if(numChecks > 1){
		return alert("Los Bienes no se pueden modificar en bloque, por favor seleccione solo uno.");
	}
	deshabilitarBotonesBienes();
	$.ajax({
		url:"modificarBienAjaxRMS.action#Bienes",
		data:"codigo="+ codigos +  "&tipoModelo=" + $("#tipoModelo").val() + "&idRemesa=" + $("#idRemesa").val(),
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			if('RUSTICO' == tipo){
				deshabilitarCamposUsoBienRustico();
				$("#idTipoBien").val("RUSTICO");
				$("#divBienRustico").show();
				$("#divBienUrbano").hide();
				$("#divOtroBien").hide();
			}else if('URBANO' == tipo){
				$("#idTipoBien").val("URBANO");
				$("#divBienUrbano").show();
				$("#divBienRustico").hide();
				$("#divOtroBien").hide();
			} else {
				$("#idTipoBien").val("OTRO");
				$("#divOtroBien").show();
				$("#divBienUrbano").hide();
				$("#divBienRustico").hide();
			}
			$("#bloqueBotonesListBienes").hide();
			habilitarBotonesBienes();
			if($("#idEstadoRemesa").val() != "3"){
				$('#idLimpiarBien').show();
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el bien.');
	        $("#bloqueBotonesListBienes").hide();
	    	habilitarBotonesBienes();
	    }
	});

}

function eliminarBien(tipoBien){
	var codigos = "";
	var checksEliminarBien = null;
	if(tipoBien == 'URBANO'){
		checksEliminarBien = document.getElementsByName("listaChecksBienUrbano");
	}else if(tipoBien == 'RUSTICO'){
		checksEliminarBien = document.getElementsByName("listaChecksBienRustico");
	}else{
		checksEliminarBien = document.getElementsByName("listaChecksOtroBien");
	}
	var i = 0;
	while(checksEliminarBien[i] != null) {
		if(checksEliminarBien[i].checked) {
			if(codigos==""){
				codigos += checksEliminarBien[i].value;
			}else{
				codigos += "-" + checksEliminarBien[i].value;
			}
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los Bienes seleccionados de la lista?")){
		$("#bloqueBotonesListBienes").show();
		deshabilitarBotones();
		if("RUSTICO" == tipoBien){
			limpiarCamposBienRustico();
		}else if("URBANO" == tipoBien){
			limpiarCamposBienUrbano();
		}
		deshabilitarCamposComunes();
		limpiarHiddenInput("codigo");
		input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
		$('#formData').append($(input));
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "eliminarBienRms.action#"+pestania).trigger("submit");
	}
}

function marcarTodosOtrosBienes(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksOtroBien) {
		if (!document.formData.listaChecksOtroBien.length) { 
			document.formData.listaChecksOtroBien.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksOtroBien.length; i++) {
			document.formData.listaChecksOtroBien[i].checked = marcar;
		}
	}
}

function marcarTodosBienesUrbanos(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksBienUrbano) {
		if (!document.formData.listaChecksBienUrbano.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksBienUrbano.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksBienUrbano.length; i++) {
			document.formData.listaChecksBienUrbano[i].checked = marcar;
		}
	}
}

function marcarTodosBienesRusticos(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksBienRustico) {
		if (!document.formData.listaChecksBienRustico.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksBienRustico.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksBienRustico.length; i++) {
			document.formData.listaChecksBienRustico[i].checked = marcar;
		}
	}
}

function deshabilitarCamposUsoBienRustico(){
	var $usoGanaderia = $("#idUsoRusticoGanaderia", parent.document);
	var $usoCultivo = $("#idUsoRusticoCultivo", parent.document);
	var $usoOtros = $("#idUsoRusticoOtros", parent.document);
	if($usoGanaderia.val() != ""){
		$usoCultivo.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoCultivo.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoOtros.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoCultivo.prop("disabled", true);
	}else{
		$usoGanaderia.prop("disabled", false);
		$usoCultivo.prop("disabled", false);
		$usoOtros.prop("disabled", false);
	}
}



function buscarBienes(tipoBien){
	if("RUSTICO" == tipoBien){
		abrirVentanaBienRusticoPopUp();
	}else if("URBANO" == tipoBien){
		abriVentanaBienUrbanoPopUp();
	} else if("OTRO" == tipoBien){
		abrirVentanaBienOtroPopUp();
	}
}

function abrirVentanaBienOtroPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposOtroBien();
	ventanaBienOtro = window.open('inicioConsultaBnS.action?esPopup=S', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienOtro.focus();
}

function buscarIntervinienteSujetoPasivo(){
	var nif = $('#idNifSujPasivo').val();
	if(nif != ""){
		limpiarFormularioSujetoPasivo();
		var $dest = $("#divSujetoPasivo", parent.document);
		deshabilitarBotonesSujetoPasivo();
		$.ajax({
			url:"buscarPersonaAjaxRMS.action#Bienes",
			data:"nif="+ nif + "&tipoInterviniente=001" + "&idContrato=" + $("#idContrato").val() +  "&idRemesa=" + $("#idRemesa").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idPrimerApeRazonSocialSujPasivo').val() != ""){
					$("#idLimpiarSujPasivo").show();
					$("#idBotonBuscarNifSujPasivo").prop("disabled", true);
					$('#idNifSujPasivo').prop("disabled", true);
				}else{
					$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
					$('#idNifSujPasivo').prop("disabled", false);
				}
				habilitarCamposSujetoPasivo();
				habilitarBotonesSujetoPasivo();
				$('#idTipoPersonaSujPasivo').prop("disabled", true);
				$('#idSexoSujPasivo').prop("disabled", true);
				$('#idDireccionSujPasivo').prop("disabled", true);
				inicializarVentanasConcepto($("#idConceptoDocumento").val(),$("#tipoModelo").val());
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el sujeto pasivo.');
		        habilitarBotonesSujetoPasivo();
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del sujeto pasivo debe rellenar el NIF/CIF.");
	}
}

function buscarIntervinienteTransmitente(){
	var nif = $('#idNifTransmitente').val();
	if(nif != ""){
		limpiarFormularioTransmitente();
		var $dest = $("#divTransmitente", parent.document);
		deshabilitarBotonesTransmitente();
		$.ajax({
			url:"buscarPersonaAjaxRMS.action#Bienes",
			data:"nif="+ nif + "&tipoInterviniente=003" + "&idContrato=" + $("#idContrato").val() +  "&idRemesa=" + $("#idRemesa").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idPrimerApeRazonSocialTransmitente').val() != "" ){
					$("#idLimpiarTransmitente").show();
					$("#idBotonBuscarNifTransmitente").prop("disabled", true);
					$('#idNifTransmitente').prop("disabled", true);
				}else{
					$("#idBotonBuscarNifTransmitente").prop("disabled", false);
					$('#idNifTransmitente').prop("disabled", false);
				}
				habilitarCamposTransmitente();
				habilitarBotonesTransmitente();
				$('#idTipoPersonaTransmitente').prop("disabled", true);
				$('#idSexoTransmitente').prop("disabled", true);
				$('#idDireccionTransmitente').prop("disabled", true);
				inicializarVentanasConcepto($("#idConceptoDocumento").val(),$("#tipoModelo").val());
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

function habilitarCamposSujetoPasivo(){
	$('#idTipoPersonaSujPasivo').prop("disabled", false);
	$('#idSexoSujPasivo').prop("disabled", false);
	$('#idPrimerApeRazonSocialSujPasivo').prop("disabled", false);
	$('#idSegundoApeSujPasivo').prop("disabled", false);
	$('#idNombreSujPasivo').prop("disabled", false);
	$('#idDiaNacSujPasivo').prop("disabled", false);
	$('#idMesNacSujPasivo').prop("disabled", false);
	$('#idAnioNacSujPasivo').prop("disabled", false);
	$('#idProvinciaSujPasivo').prop("disabled", false);
	$('#idNombreViaSujPasivo').prop("disabled", false);
	$('#idCodPostalSujPasivo').prop("disabled", false);
	$('#idNumeroDireccionSujPasivo').prop("disabled", false);
	$('#idLetraDireccionSujPasivo').prop("disabled", false);
	$('#idEscaleraDireccionSujPasivo').prop("disabled", false);
	$('#idPisoDireccionSujPasivo').prop("disabled", false);
	$('#idPuertaDireccionSujPasivo').prop("disabled", false);
	$('#idBloqueDireccionSujPasivo').prop("disabled", false);
	$('#idKmDireccionSujPasivo').prop("disabled", false);
	$('#idPorcentajeSujPasivo').prop("disabled", false);
	$("#idDireccionSujPasivo").prop("disabled", false);
}
function habilitarCamposTransmitente(){
	$('#idTipoPersonaTransmitente').prop("disabled", false);
	$('#idSexoTransmitente').prop("disabled", false);
	$('#idPrimerApeRazonSocialTransmitente').prop("disabled", false);
	$('#idSegundoApeTransmitente').prop("disabled", false);
	$('#idNombreTransmitente').prop("disabled", false);
	$('#idDiaNacTransmitente').prop("disabled", false);
	$('#idMesNacTransmitente').prop("disabled", false);
	$('#idAnioNacTransmitente').prop("disabled", false);
	$('#idProvinciaTransmitente').prop("disabled", false);
	$('#idNombreViaTransmitente').prop("disabled", false);
	$('#idCodPostalTransmitente').prop("disabled", false);
	$('#idNumeroDireccionTransmitente').prop("disabled", false);
	$('#idLetraDireccionTransmitente').prop("disabled", false);
	$('#idEscaleraDireccionTransmitente').prop("disabled", false);
	$('#idPisoDireccionTransmitente').prop("disabled", false);
	$('#idPuertaDireccionTransmitente').prop("disabled", false);
	$('#idBloqueDireccionTransmitente').prop("disabled", false);
	$('#idKmDireccionTransmitente').prop("disabled", false);
	$('#idPorcentajeTransmitente').prop("disabled", false);
	$("#idDireccionTransmitente").prop("disabled", false);
}

function limpiarTransmitente(){
	$("#bloqueBotonesTransmitente").show();
	deshabilitarBotonesTransmitente();
	limpiarFormularioTransmitente();
	$("#idLimpiarTransmitente").hide();
	$("#idNifTrasnmitente",parent.document).prop("disabled", false);
	$("#idBotonBuscarNifTransmitente").prop("disabled", false);
	$("#bloqueBotonesTransmitente").hide();
	habilitarBotonesTransmitente();
}

function limpiarSujetoPasivo(){
	$("#bloqueBotonesSujeto").show();
	deshabilitarBotonesSujetoPasivo();
	limpiarFormularioSujetoPasivo();
	$("#idLimpiarSujPasivo").hide();
	$("#idNifSujPasivo",parent.document).prop("disabled", false);
	$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
	$("#bloqueBotonesSujeto").hide();
	habilitarBotonesSujetoPasivo();
}


function deshabilitarBotonesSujetoPasivo(){
	if (!$('#idAnadirSujPasivo').is(':hidden')){
		$("#idAnadirSujPasivo").css("color","#BDBDBD");
		$("#idAnadirSujPasivo").prop("disabled",true);
	}
	
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#BDBDBD");
		$("#idGuardarRemesa").prop("disabled",true);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#BDBDBD");
		$("#idValidarRemesa").prop("disabled",true);
	}
}

function habilitarBotonesSujetoPasivo(){
	if (!$('#idAnadirSujPasivo').is(':hidden')){
		$("#idAnadirSujPasivo").css("color","#000000");
		$("#idAnadirSujPasivo").prop("disabled",false);
	}
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#000000");
		$("#idLimpiarSujPasivo").prop("disabled",false);
	}
	if (!$('#idModificarSujPasivo').is(':hidden')){
		$("#idModificarSujPasivo").css("color","#000000");
		$("#idModificarSujPasivo").prop("disabled",false);
	}
	if (!$('#idEliminarSujPasivo').is(':hidden')){
		$("#idEliminarSujPasivo").css("color","#000000");
		$("#idEliminarSujPasivo").prop("disabled",false);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#000000");
		$("#idGuardarRemesa").prop("disabled",false);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#000000");
		$("#idValidarRemesa").prop("disabled",false);
	}
}

function deshabilitarBotonesTransmitente(){
	if (!$('#idAnadirTransmitente').is(':hidden')){
		$("#idAnadirTransmitente").css("color","#BDBDBD");
		$("#idAnadirTransmitente").prop("disabled",true);
	}
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#BDBDBD");
		$("#idLimpiarTransmitente").prop("disabled",true);
	}
	if (!$('#idModificarTransmitente').is(':hidden')){
		$("#idModificarTransmitente").css("color","#BDBDBD");
		$("#idModificarTransmitente").prop("disabled",true);
	}
	if (!$('#idEliminarTransmitente').is(':hidden')){
		$("#idEliminarTransmitente").css("color","#BDBDBD");
		$("#idEliminarTransmitente").prop("disabled",true);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#BDBDBD");
		$("#idGuardarRemesa").prop("disabled",true);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#BDBDBD");
		$("#idValidarRemesa").prop("disabled",true);
	}
}

function habilitarBotonesTransmitente(){
	if (!$('#idAnadirTransmitente').is(':hidden')){
		$("#idAnadirTransmitente").css("color","#000000");
		$("#idAnadirTransmitente").prop("disabled",false);
	}
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#000000");
		$("#idLimpiarTransmitente").prop("disabled",false);
	}
	if (!$('#idModificarTransmitente').is(':hidden')){
		$("#idModificarTransmitente").css("color","#000000");
		$("#idModificarTransmitente").prop("disabled",false);
	}
	if (!$('#idEliminarTransmitente').is(':hidden')){
		$("#idEliminarTransmitente").css("color","#000000");
		$("#idEliminarTransmitente").prop("disabled",false);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#000000");
		$("#idGuardarRemesa").prop("disabled",false);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#000000");
		$("#idValidarRemesa").prop("disabled",false);
	}
}

function deshabilitarBotonesBienes(){
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#BDBDBD");
		$("#idConsultarBienesRustico").prop("disabled",true);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#BDBDBD");
		$("#idEliminarBienesRustico").prop("disabled",true);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#BDBDBD");
		$("#idModificarBienesRustico").prop("disabled",true);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#BDBDBD");
		$("#idConsultarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#BDBDBD");
		$("#idEliminarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#BDBDBD");
		$("#idModificarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#BDBDBD");
		$("#idLimpiarBien").prop("disabled",true);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#BDBDBD");
		$("#idGuardarRemesa").prop("disabled",true);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#BDBDBD");
		$("#idValidarRemesa").prop("disabled",true);
	}
}

function habilitarBotonesBienes(){
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#000000");
		$("#idConsultarBienesRustico").prop("disabled",false);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#000000");
		$("#idEliminarBienesRustico").prop("disabled",false);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#000000");
		$("#idModificarBienesRustico").prop("disabled",false);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#000000");
		$("#idConsultarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#000000");
		$("#idEliminarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#000000");
		$("#idModificarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#000000");
		$("#idLimpiarBien").prop("disabled",false);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#000000");
		$("#idGuardarRemesa").prop("disabled",false);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#000000");
		$("#idValidarRemesa").prop("disabled",false);
	}
}

function aniadirTransmitente(){
	if($("#idNifTransmitente",parent.document).val() == ""){
		return alert("Debe rellenar el NIF/CIF.");
	}
	if($("#idTipoPersonaTransmitente",parent.document).val() == "-1"){
		return alert("Debe seleccionar el tipo de persona.");
	}else{
		if($("#idTipoPersonaTransmitente",parent.document).val() == "FISICA"){
			if($("#idPrimerApeRazonSocialTransmitente",parent.document).val() == ""){
				return alert("Debe rellenar el 1er Apellido.");
			}
			if($("#idSegundoApeTransmitente",parent.document).val() == "-1"){
				return alert("Debe rellenar el 2do Apellido.");
			}
			if($("#idNombreTransmitente",parent.document).val() == "-1"){
				return alert("Debe rellenar el nombre.");
			}
		}else if($("#idTipoPersonaTransmitente",parent.document).val() == "JURIDICA"){
			if($("#idPrimerApeRazonSocialTransmitente",parent.document).val() == ""){
				return alert("Debe rellenar la raz\u00F3n Social.");
			}
		}
	}
	if($("#idProvinciaTransmitente",parent.document).val() == "-1"){
		return alert("Debe seleccionar alguna provincia.");
	}
	if($("#idMunicipioTransmitente",parent.document).val() == "-1" || $("#idMunicipioTransmitente",parent.document).val() == ""){
		return alert("Debe seleccionar alg\u00FAn municipio.");
	}
	
	if($("#idTipoVia",parent.document).val() == "-1" || $("#tipoViaSeleccionadaTransmitente",parent.document).val() == ""){
		return alert("Debe seleccionar alg\u00FAn tipo de via.");
	}
	if($("#idNombreViaTransmitente",parent.document).val() == ""){
		return alert("Debe rellenar el nombre de la via.");
	}
	if($("#idNumeroDireccionTransmitente",parent.document).val() == ""){
		return alert("Debe rellenar el numero de la via.");
	}
	$("#bloqueBotonesTransmitente").show();
	deshabilitarBotonesTransmitente();
	var pestania = obtenerPestaniaSeleccionada();
	deshabilitarCamposComunes();
	limpiarHiddenInput("tipoInterviniente");
	input = $("<input>").attr("type", "hidden").attr("name", "tipoInterviniente").val("003");
	$('#formData').append($(input));
	$("#formData").attr("action","anadirPersonaListaRms.action#"+pestania).trigger("submit");
	limpiarFormularioSujetoPasivo();
}

function datosInicialesSujpasivo(){
	var $botonLimpiar = $("#idLimpiarSujPasivo");
	var $botonBuscarNif = $("#idBotonBuscarNifSujPasivo");
	var $botonModif = $("#idModificarSujPasivo");
	var $botonEliminar = $("#idEliminarSujPasivo");
	var $tablaSujPasivo = $("#tablaSujetosPasivos");
	var $divSujPasivo = $("#divListSujetoPasivo");
	if($("#idNifSujPasivo",parent.document).val() != ""){
		habilitarCamposSujetoPasivo();
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
		$("#idNifSujPasivo",parent.document).prop("disabled", true);
	}else{
		$botonLimpiar.hide();
	}
	if($tablaSujPasivo.length > 0){
		$botonEliminar.show();
		$botonModif.show();
		$divSujPasivo.show();
	}else{
		$divSujPasivo.hide();
		$botonEliminar.hide();
		$botonModif.hide();
	}
	if($('#idTipoPersonaSujPasivo').val() != "-1"){
		$('#idSexoSujPasivo').prop("disabled",false);
	}
}

function datosInicialesTransmitente(){
	var $botonLimpiar = $("#idLimpiarTransmitente");
	var $botonBuscarNif = $("#idBotonBuscarNifTransmitente");
	var $botonModif = $("#idModificarTransmitente");
	var $botonEliminar = $("#idEliminarTransmitente");
	var $tablaTransmitente = $("#tablaTransmitentes");
	var $divTransmitente = $("#divListTransmitente");
	if($("#idNifTransmitente",parent.document).val() != ""){
		$("#idNifTransmitente",parent.document).prop("disabled", true);
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
	}else{
		$botonLimpiar.hide();
	}
	if($tablaTransmitente.length > 0){
		$botonEliminar.show();
		$botonModif.show();
		$divTransmitente.show();
	}else{
		$divTransmitente.hide();
		$botonEliminar.hide();
		$botonModif.hide();
	}
	if($('#idTipoPersonaTransmitente').val() != "-1"){
		$('#idSexoTransmitente').prop("disabled",false);
	}
}

function modificarSujPasivo(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecksSujPasivo");
	var i = 0;
	var numChecks = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn Sujeto Pasivo para modificar.");
	}else if(numChecks > 1){
		return alert("Los Sujeto Pasivo no se pueden modificar en bloque, por favor seleccione solo uno.");
	}
		
	var $dest = $("#divSujetoPasivo", parent.document);
	$("#bloqueBotonesListSujeto").show();
	deshabilitarBotonesSujetoPasivo();
	$.ajax({
		url:"modificarPersonaAjaxRMS.action#Bienes",
		data:"codigo="+ codigos + "&tipoInterviniente=001" + "&idSession=" + $("#idSession").val(),
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			if($("#idEstadoRemesa").val() != "3"){
				if($('#idPrimerApeRazonSocialSujPasivo').val() != "" || $('#idPrimerApeRazonSocialSujPasivo').val() != null){
					$("#idLimpiarSujPasivo").show();
					$("#idBotonBuscarNifSujPasivo").prop("disabled", true);
					$('#idNifSujPasivo').prop("disabled", true);
				}else{
					$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
					$('#idNifSujPasivo').prop("disabled", false);
				}
				habilitarCamposSujetoPasivo();
			}else{
				$("#idBotonBuscarNifSujPasivo").hide();
			}
			$("#bloqueBotonesListSujeto").hide();
			habilitarBotonesSujetoPasivo();
			inicializarVentanasConcepto($("#idConceptoDocumento").val(),$("#tipoModelo").val());
			$("#idDireccionSujPasivo").prop("disabled",true);
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el sujeto pasivo.');
	        $("#bloqueBotonesListSujeto").hide();
			habilitarBotonesSujetoPasivo();
	    }
	});
}

function modificarTransmitente(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecksTransmitente");
	var i = 0;
	var numChecks = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn Transmitente para modificar.");
	}else if(numChecks > 1){
		return alert("Los Transmitentes no se pueden modificar en bloque, por favor seleccione solo uno.");
	}
		
	var $dest = $("#divTransmitente", parent.document);
	$("#bloqueBotonesListTransmitente").show();
	deshabilitarBotonesTransmitente();
	$.ajax({
		url:"modificarPersonaAjaxRMS.action#Bienes",
		data:"codigo="+ codigos + "&tipoInterviniente=003" + "&idSession=" + $("#idSession").val(),
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			if($("#idEstadoRemesa").val() != "3"){
				if($('#idPrimerApeRazonSocialTransmitente').val() != "" || $('#idPrimerApeRazonSocialTransmitente').val() != null){
					$("#idLimpiarTransmitente").show();
					$("#idBotonBuscarNifTransmitente").prop("disabled", true);
				}else{
					$("#idBotonBuscarNifTransmitente").prop("disabled", false);
				}
				habilitarCamposTransmitente();
			}else{
				$("#idBotonBuscarNifTransmitente").hide();
			}
			$("#bloqueBotonesListTransmitente").hide();
			habilitarBotonesTransmitente();
			inicializarVentanasConcepto($("#idConceptoDocumento").val(),$("#tipoModelo").val());
			$("#idDireccionTransmitente").prop("disabled",true);
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el transmitente.');
			$("#bloqueBotonesListTransmitente").hide();
			habilitarBotonesTransmitente();
	    }
	});
}

function eliminarSujetosPasivos(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecksSujPasivo");
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn Sujeto Pasivo para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los Sujetos Pasivos seleccionados de la lista?")){
		var pestania = obtenerPestaniaSeleccionada();
		$("#bloqueBotonesListSujeto").show();
		deshabilitarBotonesSujetoPasivo();
		deshabilitarCamposComunes();
		limpiarHiddenInput("tipoInterviniente");
		input = $("<input>").attr("type", "hidden").attr("name", "tipoInterviniente").val("001");
		$('#formData').append($(input));
		limpiarHiddenInput("codigo");
		input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarIntervinienteRms.action#"+pestania).trigger("submit");
	}
}

function guardarCoefPart(){
	var codigosSjP = "";
	var porcentajesSjP = "";
	var codigosTr = "";
	var porcentajesTr = "";
	var i = 0;
	$("input[name='porctjaSujetoPasivo']:text").each(function(){
		if($(this).val() != ""){
			if(i==0){
				codigosSjP = ""+i;
				porcentajesSjP = $(this).val();
			}else{
				codigosSjP += "-" + i;
				porcentajesSjP += "-" +  $(this).val();
			}
		}
		i++;
	});
	var j = 0;
	$("input[name='partTransmitente']:text").each(function(){
		if($(this).val() != ""){
			if(j==0){
				codigosTr = ""+j;
				porcentajesTr = $(this).val();
			}else{
				codigosTr += "-" + j;
				porcentajesTr += "-" +  $(this).val();
			}
		}
		j++;
	});

	if(porcentajesSjP == "" && porcentajesTr == ""){
		return alert("Debe rellenar alg\u00FAn porcentaje para modificar.");
	}
	$("#bloqueBotonesCoefPart").show();
	deshabilitarBotonesCoefPart();
	deshabilitarCamposComunes();
	limpiarHiddenInput("codigoSjP");
	input = $("<input>").attr("type", "hidden").attr("name", "codigoSjP").val(codigosSjP);
	$('#formData').append($(input));
	limpiarHiddenInput("codigoTr");
	input = $("<input>").attr("type", "hidden").attr("name", "codigoTr").val(codigosTr);
	$('#formData').append($(input));
	limpiarHiddenInput("porcentajeSjP");
	input = $("<input>").attr("type", "hidden").attr("name", "porcentajeSjP").val(porcentajesSjP);
	$('#formData').append($(input));
	limpiarHiddenInput("porcentajeTr");
	input = $("<input>").attr("type", "hidden").attr("name", "porcentajeTr").val(porcentajesTr);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarCoefPartRms.action#"+pestania).trigger("submit");
}

function eliminarTransmitente(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecksTransmitente");
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn Transmitente para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los Transmitente seleccionados de la lista?")){
		$("#bloqueBotonesListTransmitente").show();
		deshabilitarBotonesTransmitente();
		deshabilitarCamposComunes();
		limpiarHiddenInput("tipoInterviniente");
		input = $("<input>").attr("type", "hidden").attr("name", "tipoInterviniente").val("003");
		$('#formData').append($(input));
		limpiarHiddenInput("codigo");
		input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
		$('#formData').append($(input));
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "eliminarIntervinienteRms.action#"+pestania).trigger("submit");
	}
}

function marcarTodosSujetoPasivo(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksSujPasivo) {
		if (!document.formData.listaChecksSujPasivo.length) { // Controlamos
																// el caso en
																// que solo hay
																// un
																// elemento...
			document.formData.listaChecksSujPasivo.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksSujPasivo.length; i++) {
			document.formData.listaChecksSujPasivo[i].checked = marcar;
		}
	}
}

function marcarTodosTransmitente(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksTransmitente) {
		if (!document.formData.listaChecksTransmitente.length) { // Controlamos
																	// el caso
																	// en que
																	// solo hay
																	// un
																	// elemento...
			document.formData.listaChecksTransmitente.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksTransmitente.length; i++) {
			document.formData.listaChecksTransmitente[i].checked = marcar;
		}
	}
}

function marcarTodosRemesa(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { // Controlamos el caso
														// en que solo hay un
														// elemento...
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}

function limpiarCoefPart(){
	$("#bloqueBotonesCoefPart").show();
	deshabilitarBotonesCoefPart();
	for(var i = 0; i < document.formData.porctjaSujetoPasivo.length; i++){
		document.formData.porctjaSujetoPasivo[i].value = "";
	}
	for(var i = 0; i < document.formData.partTransmitente.length; i++){
		document.formData.partTransmitente[i].value = "";
	}
	habilitarBotonesCoefPart();
	$("#bloqueBotonesCoefPart").hide();
}

function deshabilitarBotonesCoefPart(){
	if (!$('#idGuardarCoefPart').is(':hidden')){
		$("#idGuardarCoefPart").css("color","#BDBDBD");
		$("#idGuardarCoefPart").prop("disabled",true);
	}
	if (!$('#idLimpiarCoefPart').is(':hidden')){
		$("#idLimpiarCoefPart").css("color","#BDBDBD");
		$("#idLimpiarCoefPart").prop("disabled",true);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#BDBDBD");
		$("#idGuardarRemesa").prop("disabled",true);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#BDBDBD");
		$("#idValidarRemesa").prop("disabled",true);
	}
}

function habilitarBotonesCoefPart(){
	if (!$('#idGuardarCoefPart').is(':hidden')){
		$("#idGuardarCoefPart").css("color","#000000");
		$("#idGuardarCoefPart").prop("disabled",false);
	}
	if (!$('#idLimpiarCoefPart').is(':hidden')){
		$("#idLimpiarCoefPart").css("color","#000000");
		$("#idLimpiarCoefPart").prop("disabled",false);
	}
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#000000");
		$("#idGuardarRemesa").prop("disabled",false);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#000000");
		$("#idValidarRemesa").prop("disabled",false);
	}
}

function guardarCoefPartIndvSujPasv(coefPart,numFila){
	if(coefPart.value == ""){
		return alert("Debe rellenar el coeficiente de participaci\u00F3n para poder guardarlo.");
	}
	$("#bloqueBotonesCoefPart").show();
	deshabilitarBotonesCoefPart();
	deshabilitarCamposComunes();
	limpiarHiddenInput("codigoSjP");
	input = $("<input>").attr("type", "hidden").attr("name", "codigoSjP").val(numFila);
	$('#formData').append($(input));
	limpiarHiddenInput("porcentajeSjP");
	input = $("<input>").attr("type", "hidden").attr("name", "porcentajeSjP").val(coefPart.value);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarLimpiarCoefPartRms.action#"+pestania).trigger("submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function guardarCoefPartIndvTr(coefPart,numFila){
	if(coefPart.value == ""){
		return alert("Debe rellenar el coeficiente de participaci\u00F3n para poder guardarlo.");
	}
	$("#bloqueBotonesCoefPart").show();
	deshabilitarBotonesCoefPart();
	deshabilitarCamposComunes();
	limpiarHiddenInput("codigoTr");
	input = $("<input>").attr("type", "hidden").attr("name", "codigoTr").val(numFila);
	$('#formData').append($(input));
	limpiarHiddenInput("porcentajeTr");
	input = $("<input>").attr("type", "hidden").attr("name", "porcentajeTr").val(coefPart.value);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarLimpiarCoefPartRms.action#"+pestania).trigger("submit");
}


function cargarExpAbreviadaConcepto(concepto,expAbreviada,modelo){
	$("#"+expAbreviada).val(concepto.value);
	if(concepto.value == ""){
		 $("#Bienes").hide();
		 $("#CoefParticipacion").hide();
	}else{
		inicializarVentanasConcepto(concepto.value,$("#"+modelo).val());
	}
	cargarExentoNoSujeto(concepto.value);
}

function cargarExentoNoSujeto(concepto){
	$.ajax({
		url:"getExentoNoSujetoAjaxMd.action#Autoliquidacion",
		data:"codigo="+ concepto,
		type:'POST',
		success: function(data, xhr, status){
			habilitarExentoNoSujeto(data);
		},
		error : function(xhr, status) {
	    }
	});
}

function habilitarExentoNoSujeto(descripcion){
	if (!descripcion=="" && descripcion == "S"){
		$("#idNoSujeto").prop("checked", true);
		$("#idNoSujeto").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idExento").prop("checked", false);
		$("#idFundamentoExento").hide();
		$("#idFundamentoNoSujeto").show();
		$("#idFundamentoNoSujeto").prop("disabled", false);
		$("#idFundamentoNoSujeto").val("OP. NO SUJETAS");
	}else if (!descripcion=="" && descripcion != "S"){
		$("#idNoSujeto").prop("checked", false);
		$("#idNoSujeto").prop("disabled", true);
		$("#idExento").prop("checked", true);
		$("#idExento").prop("disabled", true);
		$("#idFundamentoNoSujeto").hide();
		$("#idFundamentoExento").show();
		$("#idFundamentoExento").prop("disabled", false);
		$("#idFundamentoExento").val(descripcion);
	}else{
		$("#idExento").prop("checked", false);
		$("#idNoSujeto").prop("checked", false);
		$("#idNoSujeto").prop("disabled", false);
		$("#idExento").prop("disabled", false);
		$("#idFundamentoExento").show();
		$("#idFundamentoExento").prop("disabled", false);
		$("#idFundamentoNoSujeto").hide();
		$("#idFundamentoExento").val("");
		$("#idFundamentoNoSujeto").val("");
	}
}

function inicializarVentanas(concepto,modelo){
	if($("#"+concepto).val() != ""){
		inicializarVentanasConcepto($("#"+concepto).val(),$("#"+modelo).val());
	}
	if($("#idNumExpediente").val() == ""){
		$("#Resumen").hide();
	}
	if($("#idEstadoRemesa").val() == "3"){
		$("#idConceptoDocumento").prop("disabled", true);
		$("#botonNotario").hide();
		$("#idEliminarSujPasivo").hide();
		$("#idBotonBuscarNifSujPasivo").hide();
		$("#idEliminarTransmitente").hide();
		$("#idBotonBuscarNifTransmitente").hide();
		$("#idConsultarBienesUrbano").hide();
		$("#idEliminarBienesUrbano").hide();
		$("#idConsultarBienesRustico").hide();
		$("#idEliminarBienesRustico").hide();
		$("#idLimpiarCoefPart").hide();
		$("#idBonificaciones").prop("disabled", true);
		$("#idFundamentoExento").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idNoSujeto").prop("disabled", true);
		$("#idLiquiComp").prop("disabled", true);
	}
}

function inicializarVentanasConcepto(concepto,modelo){
	$.ajax({
		url:"getGrupoValidacionAjaxRMS.action#Documento",
		data:"codigo="+ concepto,
		type:'POST',
		dataType: 'json',
		success: function(data){
			habilitarVentanasPorConcepto(data,modelo);
		},
		error : function(xhr, status) {
	    }
	});
}

function recargarExpAbreviadaConcepto(concepto,expAbreviada){
	$("#"+expAbreviada).val($("#"+concepto).val());
	if($("#"+concepto).val() == ""){
		 $("#Bienes").hide();
		 $("#CoefParticipacion").hide();
	}
}

function cargarMonto(comboBonificacion,inputMonto){
	$dest = $("#"+inputMonto);
	$.ajax({
		url:"getMontoBonificacionAjaxRMS.action#Autoliquidacion",
		data:"codigo="+ comboBonificacion.value,
		type:'POST',
		dataType: 'json',
		success: function(data){
			$dest.val(data);
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la bonificacion.');
	    }
	});
}

function inicializarCamposAutoLiq(checkExento,checkNoSujeto,fundamentoExento,fundamentoNoSujeto,dia,mes, anio,calendar,liqComplementaria,numJustiPrimLiq,numPrimLiq){
	if($("#"+checkNoSujeto).is(':checked')){
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoExento).val("-1");
		$("#"+fundamentoExento).hide();
	 }else if($("#"+checkExento).is(':checked')){
		$("#"+fundamentoExento).show();
		$("#"+fundamentoNoSujeto).val("");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoNoSujeto).hide();
	}else{
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoExento).val("-1");
		$("#"+fundamentoExento).hide();
	}
	 
	if($("#tipoImpuestoExento").val() == "true" || $("#tipoImpuestoNoSujeto").val()  == "true"){
		$("#"+checkNoSujeto).prop("disabled", true);
		$("#"+checkExento).prop("disabled", true);
		$("#"+fundamentoExento).prop("disabled", true);
	}
	 
	if($("#"+liqComplementaria).is(':checked')){
		$("#"+calendar).show();
		$("#"+dia).prop("disabled", false);
		$("#"+mes).prop("disabled", false);
		$("#"+anio).prop("disabled", false);
		$("#"+numJustiPrimLiq).prop("disabled", false);
		$("#"+numPrimLiq).prop("disabled", false);
	}else{
		$("#"+calendar).hide();
		$("#"+dia).val("");
		$("#"+dia).prop("disabled", true);
		$("#"+mes).val("");
		$("#"+mes).prop("disabled", true);
		$("#"+anio).val("");
		$("#"+anio).prop("disabled", true);
		$("#"+numJustiPrimLiq).val("");
		$("#"+numJustiPrimLiq).prop("disabled", true);
		$("#"+numPrimLiq).val("");
		$("#"+numPrimLiq).prop("disabled", true);
	}
}

function cambiarExento(checkExento,checkNoSujeto, fundamentoExento,fundamentoNoSujeto){
	if($("#"+checkExento).is(':checked')){
		$("#"+fundamentoExento).show();
		$("#"+fundamentoExento).prop("disabled", false);
		$("#"+fundamentoNoSujeto).hide();
	}else{
		$("#"+fundamentoExento).hide();
		$("#"+fundamentoNoSujeto).show();
	}
	$("#"+fundamentoNoSujeto).val("");
	$("#"+fundamentoNoSujeto).prop("disabled", true);
	$("#"+checkNoSujeto).prop("checked", false);
}

function cambiarNoSujeto(checkNoSujeto,checkExento,fundamentoNoSujeto,fundamentoExento){
	if($("#"+checkNoSujeto).is(':checked')){
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).val("OP. NO SUJETAS");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
	}else{
		$("#"+fundamentoNoSujeto).val("");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
	}
	$("#"+fundamentoExento).val("-1");
	$("#"+fundamentoExento).hide();
	$("#"+checkExento).prop("checked", false);
}

function habilitarCamposLiq(dia,mes, anio,calendar,liqComplementaria,numJustiPrimLiq,numPrimLiq){
	if($("#"+liqComplementaria).is(':checked')){
		$("#"+calendar).show();
		$("#"+dia).prop("disabled", false);
		$("#"+mes).prop("disabled", false);
		$("#"+anio).prop("disabled", false);
		$("#"+numJustiPrimLiq).prop("disabled", false);
		$("#"+numPrimLiq).prop("disabled", false);
	}else{
		$("#"+calendar).hide();
		$("#"+dia).val("");
		$("#"+dia).prop("disabled", true);
		$("#"+mes).val("");
		$("#"+mes).prop("disabled", true);
		$("#"+anio).val("");
		$("#"+anio).prop("disabled", true);
		$("#"+numJustiPrimLiq).val("");
		$("#"+numJustiPrimLiq).prop("disabled", true);
		$("#"+numPrimLiq).val("");
		$("#"+numPrimLiq).prop("disabled", true);
	}
}


function habilitarVentanasPorConcepto(grupoValidacion,modelo){
	switch (grupoValidacion) {
	case 1:
		/*
		 * La base imponible y el valor declarado deben de estar rellenos con
		 * los cual los habilitamos
		 */
		$("#Bienes").hide();
		$("#CoefParticipacion").hide();
		$("#idLabelPorctjSujPasivo").show();
		$("#idPorcentajeSujPasivo").show();
		$("#idLabelPorctjTransmitente").show();
		$("#idPorcentajeTransmitente").show();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 2:
		/* Habilitamos la renta */
		$("#Bienes").hide();
		$("#CoefParticipacion").hide();
		$("#idLabelPorctjSujPasivo").show();
		$("#idPorcentajeSujPasivo").show();
		$("#idLabelPorctjTransmitente").show();
		$("#idPorcentajeTransmitente").show();
		if(modelo=="600"){
			$("#divRenta").show();
			deshabilitarPeriodoRenta();
			$("#divDerechosReales").hide();
		}
		break;
	case 3:
		/*
		 * Debe de haber bienes rústicos. Habilitamos la pestaña de bienes
		 * rusticos
		 */
		$("#idTipoBien").val("RUSTICO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').hide();
		$('#divListadoBienesRusticos').show();
		$('#divListadoOtrosBienes').hide();
		$('#divBienRustico').show();
		$('#divBienUrbano').hide();
		$('#divOtroBien').hide();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 4:
		/*
		 * Debe de haber bienes urbanos. Habilitamos la pestaña de bienes
		 * urbanos
		 */
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').show();
		$('#divListadoBienesRusticos').hide();
		$('#divListadoOtrosBienes').hide();
		$('#divBienRustico').hide();
		$('#divBienUrbano').show();
		$('#divOtroBien').hide();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 5:
		/* Habilitamos los derechos reales */
		$("#Bienes").hide();
		$('#divListadoBienesUrbanos').show();
		$('#divListadoBienesRusticos').show();
		$("#CoefParticipacion").hide();
		$("#idLabelPorctjSujPasivo").show();
		$("#idPorcentajeSujPasivo").show();
		$("#idLabelPorctjTransmitente").show();
		$("#idPorcentajeTransmitente").show();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			habilitarCamposDerReal();
		}
		break;
	case 6:
		/* Debe de haber bienes rústicos y derechos reales */
		$("#idTipoBien").val("RUSTICO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').hide();
		$('#divListadoBienesRusticos').show();
		$('#divListadoOtrosBienes').hide();
		$('#divBienRustico').show();
		$('#divBienUrbano').hide();
		$('#divOtroBien').hide();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			habilitarCamposDerReal();
		}
		break;
	case 7:
		/* Debe de haber bienes urbanos y derechos reales */
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').show();
		$('#divListadoBienesRusticos').hide();
		$('#divListadoOtrosBienes').hide();
		$('#divBienRustico').hide();
		$('#divBienUrbano').show();
		$('#divOtroBien').hide();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			habilitarCamposDerReal();
		}
		break;
	case 8:
		/*
		 * Debe de haber bienes urbanos y la base imponible debe de estar
		 * habilitada por si quieren introducir un valor
		 */
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').show();
		$('#divListadoBienesRusticos').hide();
		$('#divBienRustico').hide();
		$('#divBienUrbano').show();
		$('#divListadoOtrosBienes').hide();
		$('#divOtroBien').hide();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 9:
		/*
		 * Debe de haber bienes (cualquiera de los dos tipos) y la base
		 * imponible y valor declarado debe estar habilitado para introducir un
		 * valor
		 */
		$("#Bienes").show();
		$('#divListadoOtrosBienes').hide();
		$('#divOtroBien').hide();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 10:
		/* Debe de haber bienes (cualquiera de los dos tipos) */
		$("#Bienes").show();
		$('#divListadoOtrosBienes').hide();
		$('#divOtroBien').hide();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		$("#idTipoBien option[value='OTRO']").remove();
		break;
	case 11:
		/* Modelo 601 - se habilitan otros bienes */
		/*
		 * Debe de haber otros bienes y la base imponible y valor declarado debe
		 * estar habilitado para introducir un valor
		 */
		$("#idTipoBien").val("OTRO");
		$("#idTipoBien").prop("disabled", true);
		$('#divListadoBienesUrbanos').hide();
		$('#divListadoBienesRusticos').hide();
		$('#divListadoOtrosBienes').show();
		$('#divBienRustico').hide();
		$('#divBienUrbano').hide();
		$('#divOtroBien').show();
		$("#Bienes").show();
		$("#CoefParticipacion").show();
		$("#idLabelPorctjSujPasivo").hide();
		$("#idPorcentajeSujPasivo").hide();
		$("#idLabelPorctjTransmitente").hide();
		$("#idPorcentajeTransmitente").hide();
		if(modelo=="601"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
		
	default:
		break;
	}
}

function deshabilitarBotones(){
	if (!$('#idGuardarRemesa').is(':hidden')){
		$("#idGuardarRemesa").css("color","#BDBDBD");
		$("#idGuardarRemesa").prop("disabled",true);
	}
	if (!$('#idGenerarRemesa').is(':hidden')){
		$("#idGenerarRemesa").css("color","#BDBDBD");
		$("#idGenerarRemesa").prop("disabled",true);
	}
	$("#bloqueBotonesSujeto").show();
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#BDBDBD");
		$("#idLimpiarSujPasivo").prop("disabled",true);
	}
	if (!$('#idModificarSujPasivo').is(':hidden')){
		$("#idModificarSujPasivo").css("color","#BDBDBD");
		$("#idModificarSujPasivo").prop("disabled",true);
	}
	if (!$('#idEliminarSujPasivo').is(':hidden')){
		$("#idEliminarSujPasivo").css("color","#BDBDBD");
		$("#idEliminarSujPasivo").prop("disabled",true);
	}
	$("#bloqueBotonesTransmitente").show();
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#BDBDBD");
		$("#idLimpiarTransmitente").prop("disabled",true);
	}
	if (!$('#idModificarTransmitente').is(':hidden')){
		$("#idModificarTransmitente").css("color","#BDBDBD");
		$("#idModificarTransmitente").prop("disabled",true);
	}
	if (!$('#idEliminarTransmitente').is(':hidden')){
		$("#idEliminarTransmitente").css("color","#BDBDBD");
		$("#idEliminarTransmitente").prop("disabled",true);
	}
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#BDBDBD");
		$("#idConsultarBienesRustico").prop("disabled",true);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#BDBDBD");
		$("#idEliminarBienesRustico").prop("disabled",true);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#BDBDBD");
		$("#idModificarBienesRustico").prop("disabled",true);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#BDBDBD");
		$("#idConsultarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#BDBDBD");
		$("#idEliminarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#BDBDBD");
		$("#idModificarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#BDBDBD");
		$("#idLimpiarBien").prop("disabled",true);
	}
	if (!$('#idValidarRemesa').is(':hidden')){
		$("#idValidarRemesa").css("color","#BDBDBD");
		$("#idValidarRemesa").prop("disabled",true);
	}
	if (!$('#idModificarBienes').is(':hidden')){
		$("#idModificarBienes").css("color","#BDBDBD");
		$("#idModificarBienes").prop("disabled",true);
	}
	$("#bloqueBotonesCoefPart").show();
	if (!$('#idLimpiarCoefPart').is(':hidden')){
		$("#idLimpiarCoefPart").css("color","#BDBDBD");
		$("#idLimpiarCoefPart").prop("disabled",true);
	}
	if (!$('#idPresentarModelo').is(':hidden')){
		$("#idPresentarModelo").css("color","#BDBDBD");
		$("#idPresentarModelo").prop("disabled",true);
	}
	if (!$('#idModificarModelo').is(':hidden')){
		$("#idModificarModelo").css("color","#BDBDBD");
		$("#idModificarModelo").prop("disabled",true);
	}
}


function guardarRemesa(){
	guardar();
	var pestania = obtenerPestaniaSeleccionada();
	$("#idVHabitual", parent.document).prop("disabled", false);
	$("#formData").attr("action", "guardarRms.action#"+pestania).trigger("submit");
}

function guardar(){
	var codigo = "";
	var porcentaje = "";
	
	$("input[name='porctjaSujetoPasivo']:text").each(function(){
		if($(this).val() != ""){
			if(codigo == ""){
				codigo = "" + $(this).attr("id");
				porcentaje = $(this).val();
			}else{
				codigo += "-" + $(this).attr("id");
				porcentaje += "-" +  $(this).val();
			}
		}
	});
	$("input[name='partTransmitente']:text").each(function(){
		if($(this).val() != ""){
			if(codigo == ""){
				codigo = ""+ $(this).attr("id");
				porcentaje = $(this).val();
			}else{
				codigo += "-"+$(this).attr("id");
				porcentaje += "-" +  $(this).val();
			}
		}
	});
	deshabilitarCamposComunes();
	deshabilitarBotones();
	input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigo);
	$('#formData').append($(input));
	input = $("<input>").attr("type", "hidden").attr("name", "porcentaje").val(porcentaje);
	$('#formData').append($(input));
}

function validarRemesa(){
	guardar();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "validarRms.action#"+pestania).trigger("submit");
}

function generarRemesa(){
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar los modelos de la remesa, una vez generados ya no se podra modificar los datos de la remesa introducidos?")){
		deshabilitarCamposComunes();
		deshabilitarBotones();
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "generarRms.action#"+pestania).trigger("submit");
	}
}

function cambiarElementosPorPaginaConsultaRemesa() {
	
	document.location.href = 'navegarConsultaRmS.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaEvolucionRemesas() {
	var $dest = $("#displayTagEvolRemesasDiv");
	$.ajax({
		url:"navegarEvolucionReme.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolRemesa").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			 if(filteredResponse.size() == 1){
				 $dest.html(filteredResponse);
			 }
			 $dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la bonificacion.');
	    }
	});
}

function consultarRemesa(){
	document.formData.action = "buscarConsultaRmS.action";
	document.formData.submit();
}

function limpiarConsultaRemesa(){
	$('#idEstadoRemesa').val("");
	$('#idModeloRemesa').val("");
	$('#idConceptoRemesa').val("");
	$('#idCodNotarioRemesa').val("");
	$('#idNumExpedienteRemesa').val("");
	$('#idNumColegiadoRemesa').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
	$('#diaFechaPreIni').val("");
	$('#mesFechaPreIni').val("");
	$('#anioFechaPreIni').val("");
	$('#diaFechaPreFin').val("");
	$('#mesFechaPreFin').val("");
	$('#anioFechaPreFin').val("");
	$('#idReferenciaPropia').val("");
}

function recargarConceptoPorModelo(concepto,modelo){
	var $dest = $("#"+concepto);
	$.ajax({
		url:"getConceptoPorModeloAjaxRMS.action",
		data:"modelo="+ $("#"+modelo).val(),
		type:'POST',
		success: function(data){
			$dest.find('option').remove();
			$dest.append('<option value="">Seleccione Concepto</option>').val('whatever');
			var valor = data.split("||");
			for(var i=0;i<valor.length;i++){
				var concepto = valor[i].split(";");
				$dest.append('<option value="'+concepto[0]+'">'+concepto[1]+'</option>');
			}
			
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la bonificacion.');
	    }
	});
}

function obtenerPestaniaSeleccionada() {
	var toc = document.getElementById("toc");
    if (toc) {
        var lis = toc.getElementsByTagName("li");
        for (var j = 0; j < lis.length; j++) {
            var li = lis[j];
            if (li.className == "current") {
            	return li.id;
            }
        }
    }
}

function modificarModelo(){
	var codigos = "";
	var checksModelos = document.getElementsByName("listaChecks");
	var i = 0;
	var numChecks = 0;
	while(checksModelos[i] != null) {
		if(checksModelos[i].checked) {
			if(codigos==""){
				codigos += checksModelos[i].value;
			}else{
				codigos += "-" + checksModelos[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien para eliminar.");
	}else if(numChecks > 1){
		return alert("Debe seleccionar solo un modelo a modificar.");
	}
	deshabilitarBotones();
	limpiarHiddenInput("numExpediente");
	input = $("<input>").attr("type", "hidden").attr("name", "numExpediente").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "modificarMdRms.action#").trigger("submit");
}

function deshabilitarPeriodoRenta(){
	var $checkDurRentaGlobal = $("#duracionRentaGLOBAL");
	var $checkDurRentaPeriodica = $("#duracionRentaPERIODICA");
	
	if($checkDurRentaGlobal.is(':checked')){
		$("#periodoRentaMENSUAL").prop("disabled",true);
		$("#periodoRentaANUAL").prop("disabled",true);
		$("#idNumPeriodo").val("");
		$("#idNumPeriodo").prop("disabled",true);
	}else if($checkDurRentaPeriodica.is(':checked')){
		$("#periodoRentaMENSUAL").prop("disabled",false);
		$("#periodoRentaANUAL").prop("disabled",false);
		$("#idNumPeriodo").prop("disabled",false);
	}else{
		$("#periodoRentaMENSUAL").prop("disabled",true);
		$("#periodoRentaAnual").prop("disabled",true);
		$("#idNumPeriodo").prop("disabled",true);
	}
}

function habilitarCamposDerReal(){
	var $checkDurDerRealTemporal = $("#duracionTEMPORAL");
	var $checkDurDerRealVitalicio = $("#duracionVITALICIO");
	
	if($checkDurDerRealTemporal.is(':checked')){
		$("#idNumAnio").prop("disabled",false);
		$("#idDiaNacUsufructurario").prop("disabled",true);
		$("#idMesNacUsufructurario").prop("disabled",true);
		$("#idAnioNacUsufructurario").prop("disabled",true);
		$("#idCalendarNacUsufructurario").hide();
	}else if($checkDurDerRealVitalicio.is(':checked')){
		$("#idNumAnio").prop("disabled",true);
		$("#idDiaNacUsufructurario").prop("disabled",false);
		$("#idMesNacUsufructurario").prop("disabled",false);
		$("#idAnioNacUsufructurario").prop("disabled",false);
		$("#idCalendarNacUsufructurario").show();
	}else{
		$("#idNumAnio").prop("disabled",true);
		$("#idDiaNacUsufructurario").prop("disabled",true);
		$("#idMesNacUsufructurario").prop("disabled",true);
		$("#idAnioNacUsufructurario").prop("disabled",true);
		$("#idCalendarNacUsufructurario").hide();
	}
}

// Para mostrar el loading en los botones de consultar.
function mostrarLoadingRemesa(boton) {
	bloqueaBotonesRemesa();
	deshabilitarBotonesConsultaRemesa();
	document.getElementById("bloqueLoadingRemesa").style.display = "block";
	$('#'+boton).val("Procesando..");

}

function ocultarLoadingRemesa(boton, value) {
	document.getElementById("bloqueLoadingRemesa").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesRemesa();
	habilitarBotonesConsultaRemesa();
}

function deshabilitarBotonesConsultaRemesa(){
	$("#bConsConsulta").prop('disabled', true);
	$("#bConsConsulta").css('color', '#6E6E6E');	
	$("#bLimpiar").prop('disabled', true);
	$("#bLimpiar").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaRemesa(){
	$("#bConsConsulta").prop('disabled', false);
	$("#bConsConsulta").css('color', '#000000');	
	$("#bLimpiar").prop('disabled', false);
	$("#bLimpiar").css('color', '#000000');	
}

function bloqueaBotonesRemesa() {	
	$('table.acciones input').prop('disabled', true);
	$('table.acciones input').css('color', '#6E6E6E');		
}

function desbloqueaBotonesRemesa() {	
	$('table.acciones input').prop('disabled', false);
	$('table.acciones input').css('color', '#000000');		
}

function validarBloque(){
	var valueBoton = $("#bValidar").val();
	mostrarLoadingRemesa('bValidar');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingRemesa('bValidar',valueBoton);
		return alert("Debe seleccionar alguna remesa para validar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea validar las remesas seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "validarConsultaRmS.action#").trigger("submit");
	}else{
		ocultarLoadingRemesa('bValidar',valueBoton);
	}
}

function eliminarBloque(){
	var valueBoton = $("#bEliminar").val();
	mostrarLoadingRemesa('bEliminar');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingRemesa('bEliminar',valueBoton);
		return alert("Debe seleccionar alguna remesa para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar las remesas seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaRmS.action#").trigger("submit");
	}else{
		ocultarLoadingRemesa('bEliminar',valueBoton);
	}
}

function mostrarBloqueValidados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueValidados").is(":visible")){
		$("#bloqueValidados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueValidados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueValidados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
		
	}
}

function generarBloque(){
	var valueBoton = $("#bGenerar").val();
	mostrarLoadingRemesa('bGenerar');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingRemesa('bGenerar',valueBoton);
		return alert("Debe seleccionar alguna remesa para generar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar los modelos de las remesas seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarModelosBloqueConsultaRmS.action#").trigger("submit");
	}else{
		ocultarLoadingRemesa('bGenerar',valueBoton);
	}
}

function cargarVentanaCambioEstado(){
	var valueBoton = $("#bCambiarEstado").val();
	mostrarLoadingRemesa('bCambiarEstado');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingModelo('bCambiarEstado',valueBoton);
		return alert("Debe seleccionar alguna remesa para cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaRemesa");
	$.post("cargarPopUpCambioEstadoConsultaRmS.action", function(data) {
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
						$("#divEmergenteConsultaRemesa").dialog("close");
						limpiarHiddenInput("estadoRemesaNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoRemesaNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaRmS.action#").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingRemesa('bCambiarEstado',valueBoton);
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

function abrirEvolucionRemesa(numExpediente){
	if(numExpediente == null || numExpediente == ""){
		return alert("No se puede obtener la evolución de la remesa seleccionado.");
	}
	var $dest = $("#divEmergenteConsultaEvolucionRemesa");
	var url = "abrirEvolucionConsultaRmS.action?numExpediente=" + numExpediente;
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

function cargarListaMunicipiosCam(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxRMS.action#DatosBancarios",
		data:"provincia="+ $("#"+provincia).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
				var listMuni = data.split("|");
				$.each(listMuni,function(i,o){
					var municipio = listMuni[i].split("_");
					 $dest.append($('<option>', { 
					        value: municipio[0],
					        text : municipio[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
	    }
	});
}

function cargarListaPueblosCam(provincia, municipio, pueblo){
	var $dest = $("#"+pueblo);
	$.ajax({
		url:"cargarPuebloAjaxRMS.action#DatosBancarios",
		data:"provincia="+ $("#"+provincia).val() +"&municipio=" +$("#"+municipio).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
				var listPueblo = data.split("_");
				$.each(listPueblo,function(i,o){
					 $dest.append($('<option>', { 
					        value: listPueblo[i],
					        text : listPueblo[i]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los pueblos.');
	    }
	});
}

function cargarListaTipoViaCam(provincia,tipoVia){
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaCamAjaxMd.action",
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Tipo Via</option>').val('whatever');
				var listTpVias = data.split("|");
				$.each(listTpVias,function(i,o){
					var tipoVia = listTpVias[i].split("_");
					 $dest.append($('<option>', { 
					        value: tipoVia[0],
					        text : tipoVia[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los tipos de vias de la provincia.');
	    }
	});
}

function borrarComboPuebloCam(pueblo){
	$('#'+pueblo).find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
}

function presentarModelos(){
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn modelo para presentar.");
	}
	if (confirm("Se le va a descontar 1 credito por modelo presentado. Una vez confirmados los datos de la autoliquidaci\u00F3n, "+
			"el usuario acepta expresamente la presentaci\u00F3n telem\u00E1tica de la solicitud a la Oficina Virtual  "+
			"de Impuestos Auton\u00F3micos de la Comunidad de Madrid, as\u00ED como el pago correspondiente de la cantidad autoliquidada en cada modelo.\u00BFEst\u00E1 usted seguro?")){
		deshabilitarBotones();
		$.ajax({
			url:"comprobarEstadosModelosAjaxRMS.action",
			data:"codigo="+ codigos,
			type:'POST',
			success: function(data){
				if(data){
					generarPopPupPresentacion(codigos,"divEmergentePresentacionModelo",null,"alta");
				}else{
					alert("Para poder presentar en bloque los modelos deben de estar todos en estado 'Autoliquidable'.");
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de comprobar los estados de los modelos.');
		    }
		});
	}
}


function habilitarCamposDatosBancariosConsulta(){
	 $("#idNifPagadorDatBancarios").prop("disabled", false);
	 $("#idDescripcionDatBancarios").prop("disabled", false);
	 $("#idEntidad").prop("disabled", false);
	 $("#idOficina").prop("disabled", false);
	 $("#idDC").prop("disabled", false);
	 $("#idNumeroCuentaPago").prop("disabled", false);
	 $("#idFavoritoDatBancario").prop("disabled", false);
}

function deshabilitarCamposDatosBancariosConsulta(){
	 $("#idNifPagadorDatBancarios").prop("disabled", true);
	 $("#idDescripcionDatBancarios").prop("disabled", true);
	 $("#idEntidad").prop("disabled", true);
	 $("#idOficina").prop("disabled", true);
	 $("#idDC").prop("disabled", true);
	 $("#idNumeroCuentaPago").prop("disabled", true);
	 $("#idFavoritoDatBancario").prop("disabled", true);
}

function mostrarBloqueValidados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueValidados").is(":visible")){
		$("#bloqueValidados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueValidados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueValidados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
		
	}
}

function imprimirBloque(){
	var valueBoton = $("#bImprimir").val();
	mostrarLoadingRemesa('bImprimir');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingModelo('bImprimir',valueBoton);
		return alert("Debe seleccionar alguna remesa para imprimir.");
	}
	$.ajax({
		url:"comprobarContratosRemesasAjaxRMS.action",
		data:"codigo="+ codigos,
		type:'POST',
		success: function(data){
			if(data){
				limpiarHiddenInput("codSeleccionados");
				input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
				$('#formData').append($(input));
				$("#formData").attr("action", "inicioImpRmS.action").trigger("submit");
			}else{
				alert("Para poder imprimir en bloque las remesas deben de pertenecer todas al mismo contrato.");
				ocultarLoadingRemesa('bImprimir',valueBoton);
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de comprobar los contratos de las remesas.');
	        ocultarLoadingRemesa('bImprimir',valueBoton);
	    }
	});
}


function cambiarElementosPorPaginaImprimirRemesas(){
	document.location.href = 'navegarImpRmS.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
}

function imprimirRemesas(){
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		return alert("Debe seleccionar alguna remesa para imprimir.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "obtenerFicheroImpRmS.action").trigger("submit");
}

function volverRemesas(){
	$("#formData").attr("action", "inicioConsultaRmS.action").trigger("submit");
}

function generarPopPupPresentacion(codigos,dest,valueBoton,tipo){
	var $dest = $("#"+dest);
	$.post("cargarPopUpPresentarRms.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 800,
				height: 300,
				buttons : {
					Presentar : function() {
						var $checkTipoNueva = $("#idTipoDatoBancario0");
						var $checkTipoExistente = $("#idTipoDatoBancario1");
						var nifPagador = $("#idNifPagadorDatBancarios").val();
						var entidad =  $("#idEntidad").val();
						var oficina = $("#idOficina").val();
						var dc = $("#idDC").val();
						var numCuentaPago = $("#idNumeroCuentaPago").val();
						if($checkTipoNueva.is(':checked')){
							if($("#idNifPagadorDatBancarios").val() == null && $("#idNifPagadorDatBancarios").val() == ""){
								return alert("Debe de introducir el nif del pagador para realizar la presentación.");
							}else if($("#idEntidad").val() == null && $("#idEntidad").val() == ""){
								return alert("Debe de introducir la entidad del numero de cuenta para poderrealizar la presentación.");
							}else if($("#idOficina").val() == null && $("#idOficina").val() == ""){
								return alert("Debe de introducir la oficina del numero de cuenta para poder realizar la presentación.");
							}else if($("#idDC").val() == null && $("#idDC").val() == ""){
								return alert("Debe de introducir el DC del numero de cuenta para poder realizar la presentación.");
							}else if($("#idNumeroCuentaPago").val() == null && $("#idNumeroCuentaPago").val() == ""){
								return alert("Debe de introducir el numero de cuenta para poder realizar la presentación.");
							}else if($("#idFavoritoDatBancario").is(':checked')){
								 if($("#idDescripcionDatBancarios").val() == null && $("#idDescripcionDatBancarios").val() == ""){
										return alert("Debe de rellenar la descripción de la cuenta bancaria para guardarla como favorita.");
								 }
							}
							limpiarHiddenInput("datosBancarios.nifPagador");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.nifPagador").val(nifPagador);
							$('#formData').append($(input));
							limpiarHiddenInput("datosBancarios.entidad");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.entidad").val(entidad);
							$('#formData').append($(input));
							limpiarHiddenInput("datosBancarios.oficina");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.oficina").val(oficina);
							$('#formData').append($(input));
							limpiarHiddenInput("datosBancarios.dc");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.dc").val(dc);
							$('#formData').append($(input));
							limpiarHiddenInput("datosBancarios.numeroCuentaPago");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.numeroCuentaPago").val(numCuentaPago);
							$('#formData').append($(input));
							limpiarHiddenInput("datosBancarios.tipoDatoBancario");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.tipoDatoBancario").val($checkTipoNueva.val());
							$('#formData').append($(input));
						}else if( $checkTipoExistente.is(':checked')){
							if($("#idComboExistente").val() == "" || $("#idComboExistente").val() == "-1"){
								return alert("Debe de seleccionar alguna cuenta existente para poder realizar la presentación.");
							}
							var idDatosBancarios = $("#idDatosBancarios").val();
							limpiarHiddenInput("datosBancarios.idDatosBancarios");
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.idDatosBancarios").val(idDatosBancarios);
							$('#formData').append($(input));
							input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.tipoDatoBancario").val($checkTipoExistente.val());
							$('#formData').append($(input));
						}else{
							return alert("Debe de seleccionar o introducir alguna cuenta para poder realizar la presentación.");
						}
						$("#"+dest).dialog("close");
						habilitarCamposDatosBancariosConsulta();
						if(tipo == "consulta"){
							limpiarHiddenInput("codSeleccionados");
							input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
							$('#formData').append($(input));
							$("#formData").attr("action", "presentarBloqueConsultaRmS.action").trigger("submit");
						}else{
							limpiarHiddenInput("codigo");
							input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
							$('#formData').append($(input));
							deshabilitarCamposComunes();
							$("#formData").attr("action", "presentarRms.action#Liquidaciones").trigger("submit");
						}
					},
					Cerrar : function() {
						if(tipo == "consulta"){
							ocultarLoadingRemesa('bPresentar',valueBoton);
						}
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

function cargarDatosBancariosFavoritosPopPup(){
	var $dest = $("#tablaDatosBancarios");
	if($("#idComboExistente").val() == ""){
		habilitarPorTipoDatoBancarioConsulta();		
		limpiarCamposDatosBancariosConsulta();
	}else{
		$.ajax({
			url:"cargarDatosBancariosAjaxRMS.action",
			data:"codigo="+ $("#idComboExistente").val(),
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
				$dest.show();
				$("#idDatosBancarios").val($("#idComboExistente").val());
				deshabilitarCamposDatosBancariosConsulta();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
		    }
		});
	}
}

function presentarBloque(){
	var valueBoton = $("#bPresentar").val();
	mostrarLoadingRemesa('bPresentar');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingRemesa('bPresentar',valueBoton);
		return alert("Debe seleccionar alguna remesa para presentar.");
	}
	if (confirm("Se le va a descontar 1 credito por modelo presentado. Una vez confirmados los datos de la autoliquidaci\u00F3n, "+
			"el usuario acepta expresamente la presentaci\u00F3n telem\u00E1tica de la solicitud a la Oficina Virtual  "+
			"de Impuestos Auton\u00F3micos de la Comunidad de Madrid, as\u00ED como el pago correspondiente de la cantidad autoliquidada en cada modelo.\u00BFEst\u00E1 usted seguro?")){
		$.ajax({
			url:"comprobarContratosRemesasAjaxRMS.action",
			data:"codigo="+ codigos,
			type:'POST',
			success: function(data){
				if(data){
					generarPopPupPresentacion(codigos,"divEmergenteConsultaRemesa",valueBoton,"consulta");
				}else{
					alert("Para poder presentar en bloque las remesas deben de pertenecer todas al mismo contrato.");
					ocultarLoadingModelo('bPresentar',valueBoton);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de comprobar los contratos de las remesas.');
		        ocultarLoadingModelo('bPresentar',valueBoton);
		    }
		});
		
		
		generarPopPupPresentacion(codigos,"divEmergenteConsultaRemesa",valueBoton,"consulta");
	}else{
        ocultarLoadingModelo('bPresentar',valueBoton);
	}
}




function validarPorcentaje(id){
	$(id).val($(id).val().replace(',','.'));
	if($(id).val()>100){
		$(id).val($(id).val().substr(0,2));
	}
}
function habilitarVHabitual(selectVH){
	var tipoBien = $("#"+selectVH).val();
	var concepto = $("#idExprAbreviada").val();
	$("#idVHabitual").prop('checked',false);
	$("#idVHabitual", parent.document).prop("disabled", true);
	if(concepto == "TSO" || concepto == "TU1"){
		alert(concepto);
		if(tipoBien == "VI" || tipoBien == "VU" || tipoBien == "VH" || tipoBien == "PG" || tipoBien == "CT"){
			$("#idVHabitual", parent.document).prop("disabled", false);
		}
	}
}

function buscarNotario(){
	var codigo = $('#idCodNotario').val();
	if(codigo != ""){
		limpiarDatosNotario();
		$.ajax({
			url:"buscarNotarioAjaxRMS.action#Documento",
			data:"codigo="+ codigo,
			type:'POST',
			success: function(data, xhr, status){
				var datos = data.split("_");
				if(datos[0]=="Error"){
					$('#idMensajeNotario').text('No se ha encontrado el notario');
				}else{
					$('#idCodNotario').val(datos[0]);
					$('#idCodigoNotaria').val(datos[1]);
					$('#idNotarioNombre').val(datos[2]);
					$('#idNotarioApellidos').val(datos[3]);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el notario.');
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del notario debe rellenar el Codigo.");
	}
}

function limpiarDatosNotario(){
	document.getElementById("idCodNotario").value = "";
	document.getElementById("idCodigoNotaria").value = "";
	document.getElementById("idNotarioNombre").value = "";
	document.getElementById("idNotarioApellidos").value = "";
	$('#idMensajeNotario').text('');
}
