function cambiarElementosPorPaginaGestionDB(){
	var $dest = $("#displayTagDivGestionDB");
	$.ajax({
		url:"navegarGestionDB.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaGestionDB").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los documentos base.');
	    }
	});
}

function marcarTodosDB(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksDb) {
		if (!document.formData.listaChecksDb.length) { 
			document.formData.listaChecksDb.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksDb.length; i++) {
			document.formData.listaChecksDb[i].checked = marcar;
		}
	}
}

function mostrarBloqueActualizados(){
	$("#bloqueFallidos").hide();
	$("#despFallido").attr("src","img/plus.gif");
	$("#despFallido").attr("alt","Mostrar");
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
	$("#despValidado").attr("src","img/plus.gif");
	$("#despValidado").attr("alt","Mostrar");
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallido").attr("src","img/plus.gif");
		$("#despFallido").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallido").attr("src","img/minus.gif");
		$("#despFallido").attr("alt","Ocultar");
		
	}
}

function bloqueaBotonesGestionDB(){
	$('#idBBuscarDB').prop('disabled', true);
	$('#idBBuscarDB').css('color', '#6E6E6E');
	$('#idBLimpiarDB').prop('disabled', true);
	$('#idBLimpiarDB').css('color', '#6E6E6E');
	$('#idDbReimprimir').prop('disabled', true);
	$('#idDbReimprimir').css('color', '#6E6E6E');
	$('#idDbCambiarEstado').prop('disabled', true);
	$('#idDbCambiarEstado').css('color', '#6E6E6E');
	$('#idDbEliminar').prop('disabled', true);
	$('#idDbEliminar').css('color', '#6E6E6E');
	$('#idBDbDescargar').prop('disabled', true);
	$('#idBDbDescargar').css('color', '#6E6E6E');
	$('#idBDbModificar').prop('disabled', true);
	$('#idBDbModificar').css('color', '#6E6E6E');
}

function desbloqueaBotonesGestionDB(){
	$('#idBBuscarDB').prop('disabled', false);
	$('#idBBuscarDB').css('color', '#000000');	
	$('#idBLimpiarDB').prop('disabled', false);
	$('#idBLimpiarDB').css('color', '#000000');	
	$('#idDbReimprimir').prop('disabled', false);
	$('#idDbReimprimir').css('color', '#000000');
	$('#idDbCambiarEstado').prop('disabled', false);
	$('#idDbCambiarEstado').css('color', '#000000');	
	$('#idDbEliminar').prop('disabled', false);
	$('#idDbEliminar').css('color', '#000000');	
	$('#idBDbDescargar').prop('disabled', false);
	$('#idBDbDescargar').css('color', '#000000');	
	$('#idBDbModificar').prop('disabled', false);
	$('#idBDbModificar').css('color', '#000000');	
}

function buscarGestionDB(){
	bloqueaBotonesGestionDB();
	$("#formData").attr("action", "buscarGestionDB").trigger("submit");
}

function limpiarGestionDB(){
	$("#idDocIdDB").val("");
	$("#idMatriculaDB").val("");
	$("#idNumExpedienteDB").val("");
	$("#idTipoCarpetaDB").val("");
	$("#idEstadoDB").val("");
	$("#idContratoDB").val("");
	$("#diaFechaPrtIniDB").val("");
	$("#mesFechaPrtIniDB").val("");
	$("#anioFechaPrtIniDB").val("");
	$("#diaFechaPrtFinDB").val("");
	$("#mesFechaPrtFinDB").val("");
	$("#anioFechaPrtFinDB").val("");
}

function mostrarLoadingGestionDB(boton){
	bloqueaBotonesGestionDB();
	document.getElementById("bloqueLoadingGestionDB").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingGestionDB(boton, value) {
	document.getElementById("bloqueLoadingGestionDB").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesGestionDB();
}

function eliminarDB(){
	var valueBoton = $("#idDbEliminar").val();
	mostrarLoadingGestionDB('idDbEliminar');
	var codigos = "";
	$("input[name='listaChecksDb']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionDB('idDbEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento base para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los documentos base seleccionados?")){
		limpiarHiddenInput("idDocBase");
		input = $("<input>").attr("type", "hidden").attr("name", "idDocBase").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarGestionDB.action").trigger("submit");
	}else{
		ocultarLoadingGestionDB('idDbEliminar',valueBoton);
	}
}

function reimprimirDB(){
	var valueBoton = $("#idDbReimprimir").val();
	mostrarLoadingGestionDB('idDbReimprimir');
	var codigos = "";
	$("input[name='listaChecksDb']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionDB('idDbReimprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento base para reimprimir.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea reimprimir los documentos base seleccionados?")){
		limpiarHiddenInput("idDocBase");
		input = $("<input>").attr("type", "hidden").attr("name", "idDocBase").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reimprimirErroneosGestionDB.action").trigger("submit");
	}else{
		ocultarLoadingGestionDB('idDbReimprimir',valueBoton);
	}
}

function descargarDB(){
	var valueBoton = $("#idBDbDescargar").val();
	mostrarLoadingGestionDB('idBDbDescargar');
	var codigos = "";
	$("input[name='listaChecksDb']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionDB('idBDbDescargar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento base para descargar.");
	}
	limpiarHiddenInput("idDocBase");
	input = $("<input>").attr("type", "hidden").attr("name", "idDocBase").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarGestionDB.action").trigger("submit");
	ocultarLoadingGestionDB('idBDbDescargar',valueBoton);
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function abrirVentanaSeleccionEstados(){
	var valueBoton = $("#idDbCambiarEstado").val();
	mostrarLoadingGestionDB('idDbCambiarEstado');
	var codigos = "";
	$("input[name='listaChecksDb']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionDB('idDbCambiarEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento base para cambiar el estado.");
	}

	url = "abrirSeleccionEstadoGestionDB.action"
	var $dest = $("#divEmergenteGestionDB");
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
				width: 300,
				height: 250,
				buttons : {
					Seleccionar : function() {
						var estado = document.getElementById("estado").value;
						limpiarHiddenInput("idDocBase");
						input = $("<input>").attr("type", "hidden").attr("name", "idDocBase").val(codigos);
						$('#formData').append($(input));
						
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estado);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoGestionDB.action").trigger("submit");
					},
					Cancelar : function() {
						$(this).dialog("close");
						ocultarLoadingGestionDB('idDbCambiarEstado','Cambiar Estado');
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

