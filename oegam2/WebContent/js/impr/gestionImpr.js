function cambiarElementosPorPaginaConsultaImpr(){
	var $dest = $("#displayTagDivConsultaImpr");
	$.ajax({
		url:"navegarGestImpr.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsImpr").val(),
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

function buscarImpr(){
	bloqueaBotonesImpr();
	$("#formData").attr("action", "buscarGestImpr").trigger("submit");
}

function cambiarEstadoImpr(){
	var valueBoton = $("#idCambiarEstadoImpr").val();
	mostrarLoading('idCambiarEstadoImpr');
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
		ocultarLoading('idCambiarEstadoImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder cambiar estado impr.");
	}
	var $dest = $("#divEmergenteConsultaImpr");
	$.post("cargarPopUpCambioEstadoGestImpr.action", function(data) {
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
						$("#formData").attr("action", "cambiarEstadoGestImpr.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idCambiarEstadoImpr',valueBoton);
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

function modificarCarpeta(){
	var valueBoton = $("#idModificarCarpetaImpr").val();
	mostrarLoading('idModificarCarpetaImpr');
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
		ocultarLoading('idModificarCarpetaImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder modificar la carpeta impr.");
	}
	var $dest = $("#divEmergenteConsultaImpr");
	$.post("cargarPopUpModificaCarpetaGestImpr.action", function(data) {
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
					Modificar : function() {
						var carpetaNueva = $("#idCarpetaNueva").val();
						if(carpetaNueva == ""){
							alert("Indique una carpeta");
							return false;
						}
						$("#divEmergenteConsultaImpr").dialog("close");
						limpiarHiddenInput("carpetaNueva");
						input = $("<input>").attr("type", "hidden").attr("name", "carpetaNueva").val(carpetaNueva);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "modificarCarpetaGestImpr.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idModificarCarpetaImpr',valueBoton);
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

function limpiarConsultaImpr(){
	$("#idMatriculaImpr").val("");
	$("#idBastidorImpr").val("");
	$("#idNiveImpr").val("");
	$("#idCarpetaImpr").val("");
	$("#idDocIdImpr").val("");
	$("#idTipoTramiteImpr").val("");
	$("#idEstadoSolImpr").val("");
	$("#idNumExpedienteImpr").val("");
	$("#idIdImpr").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	
}

function bloqueaBotonesImpr() {	
	$('#idBConsultaImpr').prop('disabled', true);
	$('#idBConsultaImpr').css('color', '#6E6E6E');	
	$('#idBLimpiarImpr').prop('disabled', true);
	$('#idBLimpiarImpr').css('color', '#6E6E6E');
	$('#idCambiarEstadoImpr').prop('disabled', true);
	$('#idCambiarEstadoImpr').css('color', '#6E6E6E');
	$('#idModificarCarpetaImpr').prop('disabled', true);
	$('#idModificarCarpetaImpr').css('color', '#6E6E6E');
	$('#idSolicitarImpr').prop('disabled', true);
	$('#idSolicitarImpr').css('color', '#6E6E6E');
	$('#idGenerarKoImpr').prop('disabled', true);
	$('#idGenerarKoImpr').css('color', '#6E6E6E');
	$('#idDesasignarDocumentoImpr').prop('disabled', true);
	$('#idDesasignarDocumentoImpr').css('color', '#6E6E6E');
}

function desbloqueaBotonesImpr() {	
	$('#idBConsultaImpr').prop('disabled', false);
	$('#idBConsultaImpr').css('color', '#000000');	
	$('#idBLimpiarImpr').prop('disabled', false);
	$('#idBLimpiarImpr').css('color', '#000000');	
	$('#idCambiarEstadoImpr').prop('disabled', false);
	$('#idCambiarEstadoImpr').css('color', '#000000');	
	$('#idModificarCarpetaImpr').prop('disabled', false);
	$('#idModificarCarpetaImpr').css('color', '#000000');	
	$('#idSolicitarImpr').prop('disabled', false);
	$('#idSolicitarImpr').css('color', '#000000');
	$('#idGenerarKoImpr').prop('disabled', false);
	$('#idGenerarKoImpr').css('color', '#000000');
	$('#idDesasignarDocumentoImpr').prop('disabled', false);
	$('#idDesasignarDocumentoImpr').css('color', '#000000');
	
}

function mostrarLoading(boton){
	bloqueaBotonesImpr();
	document.getElementById("bloqueLoadingConsultaImpr").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoading(boton, value) {
	document.getElementById("bloqueLoadingConsultaImpr").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesImpr();
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

function solicitarImpr(){
	var valueBoton = $("#idSolicitarImpr").val();
	mostrarLoading('idSolicitarImpr');
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
		ocultarLoading('idSolicitarImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el IMPR.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el IMPR para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarImprGestImpr.action").trigger("submit");
	}else{
		ocultarLoading('idSolicitarImpr',valueBoton);
	}
}

function generarKoImpr(){
	var valueBoton = $("#idGenerarKoImpr").val();
	mostrarLoading('idGenerarKoImpr');
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
		ocultarLoading('idGenerarKoImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar KO.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar KO para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarKoGestImpr.action").trigger("submit");
	}else{
		ocultarLoading('idGenerarKoImpr',valueBoton);
	}
}

function desasignardocumentoImpr(){
	var valueBoton = $("#idDesasignarDocumentoImpr").val();
	mostrarLoading('idDesasignarDocumentoImpr');
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
		ocultarLoading('idDesasignarDocumentoImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder desasignar el documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea desasignar el documento para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarDocumentoGestImpr.action").trigger("submit");
	}else{
		ocultarLoading('idDesasignarDocumentoImpr',valueBoton);
	}
}