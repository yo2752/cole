function consultarDatosBancarios(){
	mostrarLoadingConsulta('bConsulta','bloqueLoadingConsultaDtBancarios');
	$("#idContrato").prop("disabled",false);
	$("#formData").attr("action","buscarConsultaDtB.action").trigger("submit");
}

function limpiarConsultaDatosBancarios(){
	$('#idFormaPago').val("");
	$('#idNIf').val("");
	$('#idEntidad').val("");
	$('#idOficina').val("");
	$('#idDC').val("");
	$('#idNumeroCuentaPago').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
	$('#idEstado').val("");
	
	if($("#esAdmin").val()){
		$('#idContrato').val("");
	}
}

function cambiarElementosPorPaginaConsulta(){
	document.location.href = 'navegarConsultaDtB.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
}

function mostrarLoadingAlta(boton,divLoading){
	bloqueaBotonesAlta();
	document.getElementById(divLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function mostrarLoadingConsulta(boton, divLoading) {
	bloqueaBotonesConsulta();
	document.getElementById(divLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function guardarDatoBancario(){
	mostrarLoadingAlta('bGuardar','bloqueLoadingAltaDtBancarios');
	habilitarCampos();
	$("#formData").attr("action","guardarDTB.action").trigger("submit");
}

function habilitarCampos(){
	habilitarDeshabilitarCamposTarjeta(false);
	habilitarDeshabilitarCamposCuentaBancaria(false);
	habilitarDeshabilitarCampoIban(false);
	$('#idFormaPago').prop("disabled",false);
	$('#idDatosBancario').prop("disabled",false);
	$('#idNifPagadorDatBancarios').prop("disabled",false);
	$('#idDescripcionDatBancarios').prop("disabled",false);
	$('#idContrato').prop("disabled",false);
	$('#idEstado').prop("disabled",false);
}

function eliminarDatoBancario(){
	mostrarLoadingAlta('bEliminar','bloqueLoadingAltaDtBancarios');
	habilitarDeshabilitarCamposTarjeta(false);
	habilitarDeshabilitarCamposCuentaBancaria(false);
	habilitarDeshabilitarCampoIban(false);
	$('#idFormaPago').prop("disabled",false);
	$('#idDatosBancario').prop("disabled",false);
	$("#formData").attr("action","eliminarDTB.action").trigger("submit");
}

function bloqueaBotonesAlta() {	
	$('#bGuardar').prop('disabled', true);
	$('#bGuardar').css('color', '#6E6E6E');	
	$('#bLimpiar').prop('disabled', true);
	$('#bLimpiar').css('color', '#6E6E6E');	
	$('#bEliminar').prop('disabled', true);
	$('#bEliminar').css('color', '#6E6E6E');	
}

function desbloqueaBotonesAlta() {	
	$('#bGuardar').prop('disabled', false);
	$('#bGuardar').css('color', '#000000');			
	$('#bLimpiar').prop('disabled', false);
	$('#bLimpiar').css('color', '#000000');			
}

function bloqueaBotonesConsulta() {	
	$('#bConsulta').prop('disabled', true);
	$('#bConsulta').css('color', '#6E6E6E');	
	$('#bLimpiar').prop('disabled', true);
	$('#bLimpiar').css('color', '#6E6E6E');	
	$('#bEliminar').prop('disabled', true);
	$('#bEliminar').css('color', '#6E6E6E');
}

function desbloqueaBotonesConsulta() {	
	$('#bConsulta').prop('disabled', false);
	$('#bConsulta').css('color', '#000000');			
	$('#bLimpiar').prop('disabled', false);
	$('#bLimpiar').css('color', '#000000');			
}

function marcarTodosDtBancarios(){
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { 
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}


function verFormaPago(){
	var formaPago = $('#idFormaPago').val();
	if (formaPago == '1') {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
		$('[id^=divContenidoTarjeta]').show("slow");
	} else if (formaPago == '0') {
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
		$('[id^=divContenidoCuenta]').show("slow");
	} else if (formaPago == '2') {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').show("slow");
	} else {
		$('[id^=divContenidoCuenta]').hide("slow");
		$('[id^=divContenidoTarjeta]').hide("slow");
		$('[id^=divContenidoIban]').hide("slow");
	}
}

function iniciarVentanaDatosBancarios(){
	verFormaPago();
	if($("#idDatosBancario").val() == null || $("#idDatosBancario").val() == ""){
		$('#idEstado').prop("disabled",true);
	}
	var formaPago = $('#idFormaPago').val();
	if (formaPago == '1') {
		$('#idFormaPago').prop("disabled",true);
		habilitarDeshabilitarCamposTarjeta(true);
		if($('#idEstado').val() == "1" || $('#idEstado').val() == "2"){
			$('#idEliminarTarjeta').hide();
		}
	} else if (formaPago == '0') {
		$('#idFormaPago').prop("disabled",true);
		habilitarDeshabilitarCamposCuentaBancaria(true);
		if($('#idEstado').val() == "1" || $('#idEstado').val() == "2"){
			$('#idEliminarNumCuenta').hide();
		}
	} else if (formaPago == '2') {
		$('#idFormaPago').prop("disabled",true);
		habilitarDeshabilitarCampoIban(true);
		if($('#idEstado').val() == "1" || $('#idEstado').val() == "2"){
			$('#idEliminarIban').hide();
		}
	}
}

function gestionarCamposFormaPago(){
	var formaPago = $('#idFormaPago').val();
	if (formaPago == '1') {
		limpiarCampoIban();
		habilitarDeshabilitarCampoIban(true);
		limpiarCamposTarjeta();
		habilitarDeshabilitarCamposTarjeta(false);
		limpiarCamposCuentaBancaria();
		habilitarDeshabilitarCamposCuentaBancaria(true);
	} else if (formaPago == '0') {
		limpiarCampoIban();
		habilitarDeshabilitarCampoIban(true);
		limpiarCamposTarjeta();
		habilitarDeshabilitarCamposTarjeta(true);
		limpiarCamposCuentaBancaria();
		habilitarDeshabilitarCamposCuentaBancaria(false);
	} else if (formaPago == '2') {
		limpiarCampoIban();
		habilitarDeshabilitarCampoIban(false);
		limpiarCamposTarjeta();
		habilitarDeshabilitarCamposTarjeta(true);
		limpiarCamposCuentaBancaria();
		habilitarDeshabilitarCamposCuentaBancaria(true);
	} else {
		limpiarCampoIban();
		habilitarDeshabilitarCampoIban(true);
		limpiarCamposTarjeta();
		habilitarDeshabilitarCamposTarjeta(true);
		limpiarCamposCuentaBancaria();
		habilitarDeshabilitarCamposCuentaBancaria(true);
	}
}

function gestionarEstadoDatoBancario(){
	
	if($('#idEstado').val() == "0" ){
		habilitarCamposEstado();
	}else if($('#idEstado').val() == "1"){
		deshabilitarCamposEstado();
	}else if($('#idEstado').val() == "2"){
		deshabilitarCamposEstado();
	}
	if($('#idEstado').val() == "0" ){
		if ($('#idFormaPago').val() == '1') {
			$('#idEliminarTarjeta').show();
		} else if ($('#idFormaPago').val() == '0') {
			$('#idEliminarNumCuenta').show();
		} else if ($('#idFormaPago').val() == '2') {
			$('#idEliminarIban').show();
		}
	}else{
		if ($('#idFormaPago').val() == '1') {
			$('#idEliminarTarjeta').hide();
		} else if ($('#idFormaPago').val() == '0') {
			$('#idEliminarNumCuenta').hide();
		} else if ($('#idFormaPago').val() == '2') {
			$('#idEliminarIban').hide();
		}
	}
}

function deshabilitarCamposEstado(){
	$('#idEliminarIban').prop("disabled",true);
	$('#idEliminarTarjeta').prop("disabled",true);
	$('#idEliminarNumCuenta').prop("disabled",true);
	$('#idNifPagadorDatBancarios').prop("disabled",true);
	$('#idDescripcionDatBancarios').prop("disabled",true);
	$('#idContrato').prop("disabled",true);
	$('#idFormaPago').prop("disabled",true);
	habilitarDeshabilitarCampoIban(true);
	habilitarDeshabilitarCamposCuentaBancaria(true);
	habilitarDeshabilitarCamposTarjeta(true);
}

function habilitarCamposEstado(){
	$('#idEliminarIban').prop("disabled",false);
	$('#idEliminarTarjeta').prop("disabled",false);
	$('#idEliminarNumCuenta').prop("disabled",false);
	$('#idNifPagadorDatBancarios').prop("disabled",false);
	$('#idDescripcionDatBancarios').prop("disabled",false);
	$('#idContrato').prop("disabled",false);
	if($('#idDatosBancario').val() == null){
		$('#idFormaPago').prop("disabled",false);
		habilitarDeshabilitarCampoIban(false);
		habilitarDeshabilitarCamposCuentaBancaria(false);
		habilitarDeshabilitarCamposTarjeta(false);
	}
}

function limpiarCampoIban(){
	$('#idcuentaIban').val("");
}

function habilitarDeshabilitarCampoIban(disabled){
	$('#idcuentaIban').prop("disabled",disabled);
}

function limpiarCamposTarjeta(){
	$('#idTarjetaEntidad').val("");
	$('#idTarjeta1').val("");
	$('#idTarjeta2').val("");
	$('#idTarjeta3').val("");
	$('#idTarjeta4').val("");
	$('#idCCV').val("");
	$('#idTarjetaMes').val("");
	$('#idTarjetaAnio').val("");
}

function habilitarDeshabilitarCamposTarjeta(disabled){
	$('#idTarjetaEntidad').prop("disabled",disabled);
	$('#idTarjeta1').prop("disabled",disabled);
	$('#idTarjeta2').prop("disabled",disabled);
	$('#idTarjeta3').prop("disabled",disabled);
	$('#idTarjeta4').prop("disabled",disabled);
	$('#idCCV').prop("disabled",disabled);
	$('#idTarjetaMes').prop("disabled",disabled);
	$('#idTarjetaAnio').prop("disabled",disabled);
}

function limpiarCamposCuentaBancaria() {
	$('#idEntidad').val("");
	$('#idOficina').val("");
	$('#idDC').val("");
	$('#idNumeroCuentaPago').val("");
}

function habilitarDeshabilitarCamposCuentaBancaria(disabled){
	$('#idEntidad').prop("disabled",disabled);
	$('#idOficina').prop("disabled",disabled);
	$('#idDC').prop("disabled",disabled);
	$('#idNumeroCuentaPago').prop("disabled",disabled);
}

function limpiarNumCuenta(){
	habilitarDeshabilitarCamposCuentaBancaria(false);
	limpiarCamposCuentaBancaria();
	$('#idFormaPago').prop("disabled",false);
	if($('#idDatosBancario').val() != null){
		$('#esModificado').val(true);
	}
}

function limpiarTarjeta(){
	habilitarDeshabilitarCamposTarjeta(false);
	limpiarCamposTarjeta();
	$('#idFormaPago').prop("disabled",false);
	if($('#idDatosBancario').val() != null){
		$('#esModificado').val(true);
	}
}

function limpiarIban(){
	habilitarDeshabilitarCampoIban(false);
	limpiarCampoIban();
	$('#idFormaPago').prop("disabled",false);
	if($('#idDatosBancario').val() != null){
		$('#esModificado').val(true);
	}
}

function eliminarConsultaDatosBancarios(){
	mostrarLoadingConsulta('bEliminar','bloqueLoadingAccionesDtBancarios');
}

function abrirDetalleDtBancario(idDatoBancario){
	if(idDatoBancario == null || idDatoBancario == ""){
		return alert("No se puede obtener el detalle del dato bancario seleccionado.");
	}
	if ($("input[name='idDatoBancario']").is(':hidden')){
		$("input[name='idDatoBancario']").remove();
	}
	input = $("<input>").attr("type", "hidden").attr("name", "idDatoBancario").val(idDatoBancario);
	$('#formData').append($(input));
	$("#formData").attr("action","altaDTB.action").trigger("submit");
}

function consultaEvolucionDatosBancarios(idDatoBancario){
	if(idDatoBancario == null || idDatoBancario == ""){
		return alert("No se puede obtener la evolución del dato bancario.");
	}
	var $dest = $("#divEmergenteEvolucionDatosBancarios");
	var url = "consultarEvolucionDtb.action?idDatoBancario=" + idDatoBancario;
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
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}