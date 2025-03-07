function cambiarElementosPorPaginaConsultaDocImpr(){
	var $dest = $("#displayTagDivConsultaDocImpr");
	$.ajax({
		url:"navegarGestDocImpr.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsDocImpr").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los expedientes.');
	    }
	});
}

function buscarDocImpr(){
	bloqueaBotonesDocImpr();
	$("#formData").attr("action", "buscarGestDocImpr").trigger("submit");
}


function limpiarConsultaDocImpr(){
	$("#idDocIdImpr").val("");
	$("#idMatriculaImpr").val("");
	$("#idCarpetaImpr").val("");
	$("#idNumExpedienteImpr").val("");
	$("#idTipoTramiteImpr").val("");
	$("#idTipoImpr").val("");
	$("#idEstado").val("");
	$("#idJefatura").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#diaFechaImprIni").val("");
	$("#mesFechaImprIni").val("");
	$("#anioFechaImprIni").val("");
	$("#diaFechaImprFin").val("");
	$("#mesFechaImprFin").val("");
	$("#anioFechaImprFin").val("");
	
	
}

function bloqueaBotonesDocImpr() {	
	$('#idBConsultaImpr').prop('disabled', true);
	$('#idBConsultaImpr').css('color', '#6E6E6E');	
	$('#idBLimpiarImpr').prop('disabled', true);
	$('#idBLimpiarImpr').css('color', '#6E6E6E');
	$('#idBImpManual').prop('disabled', true);
	$('#idBImpManual').css('color', '#6E6E6E');
	$('#idBDescgManual').prop('disabled', true);
	$('#idBDescgManual').css('color', '#6E6E6E');
	$('#idBCBEstado').prop('disabled', true);
	$('#idBCBEstado').css('color', '#6E6E6E');
	$('#idBReactivar').prop('disabled', true);
	$('#idBReactivar').css('color', '#6E6E6E');
	$('#idBImprimir').prop('disabled', true);
	$('#idBImprimir').css('color', '#6E6E6E');
	$('#idAnular').prop('disabled', true);
	$('#idAnular').css('color', '#6E6E6E');
	$('#idBReiniciar').prop('disabled', true);
	$('#idBReiniciar').css('color', '#6E6E6E');
	$('#idBErrorMail').prop('disabled', true);
	$('#idBErrorMail').css('color', '#6E6E6E');
}

function desbloqueaBotonesDocImpr() {	
	$('#idBConsultaImpr').prop('disabled', false);
	$('#idBConsultaImpr').css('color', '#000000');
	$('#idBLimpiarImpr').prop('disabled', false);
	$('#idBLimpiarImpr').css('color', '#000000');
	$('#idBImpManual').prop('disabled', false);
	$('#idBImpManual').css('color', '#000000');	
	$('#idBDescgManual').prop('disabled', false);
	$('#idBDescgManual').css('color', '#000000');	
	$('#idBCBEstado').prop('disabled', false);
	$('#idBCBEstado').css('color', '#000000');	
	$('#idBReactivar').prop('disabled', false);
	$('#idBReactivar').css('color', '#000000');	
	$('#idBImprimir').prop('disabled', false);
	$('#idBImprimir').css('color', '#000000');
	$('#idAnular').prop('disabled', false);
	$('#idAnular').css('color', '#000000');
	$('#idBReiniciar').prop('disabled', false);
	$('#idBReiniciar').css('color', '#000000');
	$('#idBErrorMail').prop('disabled', false);
	$('#idBErrorMail').css('color', '#000000');
	
}

function mostrarLoading(boton){
	bloqueaBotonesDocImpr();
	document.getElementById("bloqueLoadingConsultaDocImpr").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoading(boton, value) {
	document.getElementById("bloqueLoadingConsultaDocImpr").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesDocImpr();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
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

function marcarTodosImpr(objCheck) {
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

function impresionManual(){
	var valueBoton = $("#idBImpManual").val();
	mostrarLoading('idBImpManual');
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
	
	if(codigos == ""){
		ocultarLoading('idBImpManual',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder solicitar el IMPR manual.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el IMPR manual para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "impresionManualGestDocImpr.action").trigger("submit");
	}else{
		ocultarLoading('idBImpManual',valueBoton);
	}
}

function cambiarEstado(){
	var valueBoton = $("#idBCBEstado").val();
	mostrarLoading('idBCBEstado');
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
	
	if(codigos == ""){
		ocultarLoading('idBCBEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaDocImpr");
	$.post("cargarPopUpCambioEstadoGestDocImpr.action", function(data) {
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
						$("#divEmergenteConsultaImpr").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoGestDocImpr.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idBCBEstado',valueBoton);
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

function descargaManual() {
	var valueBoton = $("#idBDescgManual").val();
	mostrarLoading('idBDescgManual');
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
	
	if(codigos == ""){
		ocultarLoading('idBDescgManual',valueBoton);
		return alert("Debe seleccionar algun documento para imprimir y ser descargado manualmente.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea descargar el IMPR manual para los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargaManualGestDocImpr.action").trigger("submit");
	}else{
		ocultarLoading('idBDescgManual',valueBoton);
	}
}

function reactivar(){
	var valueBoton = $("#idBReactivar").val();
	mostrarLoading('idBReactivar');
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
	
	if(codigos == ""){
		ocultarLoading('idBReactivar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder solicitar el IMPR manual.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea reactivar el documento para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reactivarGestDocImpr.action").trigger("submit");
	}else{
		ocultarLoading('idBReactivar',valueBoton);
	}
}

function imprimir(){
	var valueBoton = $("#idBImprimir").val();
	mostrarLoading('idBImprimir');
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
	
	if(codigos == ""){
		ocultarLoading('idBImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder imprimir el documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea imprimir el documento para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirAutoGestDocImpr.action").trigger("submit");
	}else{
		ocultarLoading('idBImprimir',valueBoton);
	}
}

