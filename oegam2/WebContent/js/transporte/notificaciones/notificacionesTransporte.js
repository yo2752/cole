function limpiarConsultaNotTrans(){
	$("#idCifRepreNotTrans").val("");
	$("#idMunicipioNotRepre").val("");
	$("#idNifEmpresaNotTrans").val("");
	$("#idNombreEmpresaNotTrans").val("");
	$("#idEstadoNotTrans").val("");
	$("#idContratoNotTrans").val("");
	$("#diaFechaAltaIniNotTrans").val("");
	$("#mesFechaAltaIniNotTrans").val("");
	$("#anioFechaAltaIniNotTrans").val("");
	$("#diaFechaAltaFinNotTrans").val("");
	$("#mesFechaAltaFinNotTrans").val("");
	$("#anioFechaAltaFinNotTrans").val("");
}

function buscarNotTrans(){
	$('#formData').attr("action","buscarNotifTransporte.action");
	$('#formData').submit();
}

function mostrarLoadingConsultaNotTrans(boton) {
	deshabilitarBotonesConsultaNotTrans();
	$('#bloqueLoadingConsultaNotTrans').show();
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaNotTrans(boton, value) {
	$('#bloqueLoadingConsultaNotTrans').hide();
	$('#'+boton).val(value);
	habilitarBotonesConsultaNotTrans();
}

function deshabilitarBotonesConsultaNotTrans(){
	$("#idImprimirPdf").prop('disabled', true);
	$("#idImprimirPdf").css('color', '#6E6E6E');	
	$("#idRechazarNotTrans").prop('disabled', true);
	$("#idRechazarNotTrans").css('color', '#6E6E6E');	
	$("#idBuscarNotTrans").prop('disabled', true);
	$("#idBuscarNotTrans").css('color', '#6E6E6E');	
	$("#idLimpiarNotTrans").prop('disabled', true);
	$("#idLimpiarNotTrans").css('color', '#6E6E6E');
	$("#idDescargarNotTrans").prop('disabled', true);
	$("#idDescargarNotTrans").css('color', '#6E6E6E');
}

function habilitarBotonesConsultaNotTrans(){
	$("#idImprimirPdf").prop('disabled', false);
	$("#idImprimirPdf").css('color', '#000000');	
	$("#idRechazarNotTrans").prop('disabled', false);
	$("#idRechazarNotTrans").css('color', '#000000');
	$("#idBuscarNotTrans").prop('disabled', false);
	$("#idBuscarNotTrans").css('color', '#000000');
	$("#idLimpiarNotTrans").prop('disabled', false);
	$("#idLimpiarNotTrans").css('color', '#000000');
	$("#idDescargarNotTrans").prop('disabled', false);
	$("#idDescargarNotTrans").css('color', '#000000');
}

function imprimirNotTransBloque(){
	var valueBoton = $("#idImprimirPdf").val();
	mostrarLoadingConsultaNotTrans("idImprimirPdf");
	var codigos = getChecksConsultaNotTransMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaNotTrans('idImprimirPdf',valueBoton);
		return alert("Debe seleccionar alguna notificacion para realizar su impresión.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "imprimirNotificacionesNotifTransporte.action").trigger("submit");
}

function descargarNotTransBloque(){
	var valueBoton = $("#idDescargarNotTrans").val();
	mostrarLoadingConsultaNotTrans("idDescargarNotTrans");
	var codigos = getChecksConsultaNotTransMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaNotTrans('idDescargarNotTrans',valueBoton);
		return alert("Debe seleccionar alguna notificación para realizar su descarga.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarPdfBloqueNotifTransporte.action").trigger("submit");
	ocultarLoadingConsultaNotTrans('idDescargarNotTrans',valueBoton);
}

function getChecksConsultaNotTransMarcados(){
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	return codigos;
}

function rechazarNotTransBloque(){
	var valueBoton = $("#idRechazarNotTrans").val();
	mostrarLoadingConsultaNotTrans("idRechazarNotTrans");
	var codigos = getChecksConsultaNotTransMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaNotTrans('idConsultarEEFF',valueBoton);
		return alert("Debe seleccionar alguna notificación para poder rechazarla.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "rechazarNotificacionesNotifTransporte.action").trigger("submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function cambiarElementosPorPaginaConsultaNotTrans(){
	var $dest = $("#displayTagDivConsultaNotTrans");
	$.ajax({
		url:"navegarNotifTransporte.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaNotTrans").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las notificaciones de transporte.');
	    }
	});
}

function marcarTodosConsultaNotTrans(objCheck){
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}

function mostrarBloqueActualizados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueActualizados").is(":visible")){
		$("#bloqueActualizados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueActualizados").hide();
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

function descargarFichero(){
	$("#formData").attr("action", "obtenerFicheroNotifTransporte.action").trigger("submit");
}