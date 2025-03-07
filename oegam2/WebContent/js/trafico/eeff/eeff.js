
function actualizarCamposEEFFLiberacion(){
	if($("#idNive").val() != null){
		$("#tarjetaNiveLiberarEEFF").val($("#idNive").val());
	}
	if($("#idBastidor").val() != null){
		$("#tarjetaBastidorLiberarEEFF").val($("#idBastidor").val());
	}
}

function cambiarActivacionLiberarEEFF(){
	if($("#idNive").val() != null && $("#idNive").val() != ""){
		$("#pestaniaEnlaceLiberacionEEFF").show();
	} else {
		$("#pestaniaEnlaceLiberacionEEFF").hide();
	}
}

function ocultarCamposEEFFLiberacion(){
	if ($('#exentoLiberarEEFF').prop('checked')) {
		$("#datosLiberacionEEFF").hide();
		$("#tituloDatosLiberacionEEFF").hide();
		$("#datosDeLasConsultasEEFF").hide();
		//$("#consultasEEFFAsociadas").hide();
		$("#idBotonLiberarEEFF").hide();
	} else {
		$("#datosLiberacionEEFF").show();
		$("#tituloDatosLiberacionEEFF").show();
		$("#datosDeLasConsultasEEFF").show();
		//$("#consultasEEFFAsociadas").show();
		if($("#tarjetaNiveLiberarEEFF").val() != null && $("#tarjetaNiveLiberarEEFF").val() != "" 
			&& $("#estadoId").val() == "8"){
			$("#idBotonLiberarEEFF").show();
		}
	}
}

function  liberarEEFF(pestania){
	$("#formData").attr("action", "liberarTramiteAltasMatriculacionMatw.action#"+pestania).trigger("submit");
}

function  liberarEEFFMatriculacion(){
	$("#tarjetaNiveLiberarEEFF").prop("disabled",false);
	$("#tarjetaBastidorLiberarEEFF").prop("disabled",false);
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "liberarTramiteAltaTramiteTraficoMatriculacion.action#"+pestania).trigger("submit");
}

function buscarConsultaEEFF() {
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
	
	// Validamos el número de expediente Trámite
	var numExpedienteTramite = document.getElementById("numExpedienteTramite").value;
	
	if (numExpedienteTramite != null) {		
		numExpedienteTramite = numExpedienteTramite.replace(/\s/g,'');
		
	if (isNaN(numExpedienteTramite)) {
		alert("El campo n\u00FAmero de expediente trámite debe contener solo n\u00FAmeros");
			return false;
		}		
					
		document.getElementById("numExpedienteTramite").value = numExpedienteTramite;
	}
	
	$('#formData').attr("action","buscarConsultaEEFF.action");
	$('#formData').submit();
}

function limpiarConsultaEEFF(){
	$("#tarjetaNive").val("");
	$("#tarjetaBastidor").val("");
	$("#cifConcesionario").val("");
	$("#idContrato").val("");
	$("#diaBusquedaIni").val("");
	$("#mesBusquedaIni").val("");
	$("#anioBusquedaIni").val("");
	$("#diaBusquedaFin").val("");
	$("#mesBusquedaFin").val("");
	$("#anioBusquedaFin").val("");
	$("#diaAltaIni").val("");
	$("#mesAltaIni").val("");
	$("#anioAltaIni").val("");
	$("#diaAltaFin").val("");
	$("#mesAltaFin").val("");
	$("#anioAltaFin").val("");
	$("#numExpediente").val("");
	$("#numExpedienteTramite").val("");
}

function limpiarConsultaBuscarEEFF(){
	$("#tarjetaNive").val("");
	$("#tarjetaBastidor").val("");
	$("#cifConcesionario").val("");
	$("#idContrato").val("");
	$("#diaBusquedaIni").val("");
	$("#mesBusquedaIni").val("");
	$("#anioBusquedaIni").val("");
	$("#diaBusquedaFin").val("");
	$("#mesBusquedaFin").val("");
	$("#anioBusquedaFin").val("");
	$("#diaAltaIni").val("");
	$("#mesAltaIni").val("");
	$("#anioAltaIni").val("");
	$("#diaAltaFin").val("");
	$("#mesAltaFin").val("");
	$("#anioAltaFin").val("");
	$("#numExpediente").val("");
	$("#numExpedienteTramite").val("");
}

function marcarTodosConsultaEEFF(objCheck) {
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

function cargarValoresIniciales(){
	if($("#idEstadoConEEFF").val() != "1"){
		$("#idContrato").propr("disabled",true);
	}
}

function habilitarCamposObligatorios(){
	$("#idEstadoConEEFF").prop("disabled",false);
	$("#idContrato").prop("disabled",false);
	$("#idNumExpediente").prop("disabled",false);
}

function guardarConsultaEEFF(){
	habilitarCamposObligatorios();
	$('#formData').attr("action","guardarAltaConEEFF.action");
	$('#formData').submit();
}

function volverConsultaDev(){
	$("#formData").attr("action", "inicioConsultaEEFF");
	$('#formData').submit();
}

function realizarConsultaEEFF(){
	habilitarCamposObligatorios();
	$('#formData').attr("action","consultarAltaConEEFF.action");
	$('#formData').submit();
}

function cambiarEstadoBloqueConsultaEEFF(){
	var valueBoton = $("#idCambiarEstadoConsultaEEFF").val();
	mostrarLoadingConsultaEEFF("idCambiarEstado");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaEEFF('idCambiarEstadoConsultaEEFF',valueBoton);
		return alert("Debe seleccionar alguna consulta EEFF para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaEEFF");
	$.post("cargarPopUpCambioEstadoConsultaEEFF.action", function(data) {
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
						$("#divEmergenteConsultaEEFF").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaEEFF.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaDev('idCambiarEstado',valueBoton);
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

function ocultarLoadingConsultaEEFF(boton, value) {
	$('#bloqueLoadingConsultaEEFF').hide();
	$('#'+boton).val(value);
	habilitarBotonesConsultaDev();
}

function getChecksConsultaMarcados(){
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

//Para mostrar el loading en los botones de consultar.
function mostrarLoadingConsultaEEFF(boton) {
	deshabilitarBotonesConsultaEEFF();
	$('#bloqueLoadingConsultaEEFF').show();
	$('#'+boton).val("Procesando..");
}

function deshabilitarBotonesConsultaEEFF(){
	$("#idCambiarEstadoConsultaEEFF").prop('disabled', true);
	$("#idCambiarEstadoConsultaEEFF").css('color', '#6E6E6E');	
	$("#idConsultarEEFF").prop('disabled', true);
	$("#idConsultarEEFF").css('color', '#6E6E6E');	
	$("#idEliminarEEFF").prop('disabled', true);
	$("#idEliminarEEFF").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaDev(){
	$("#idCambiarEstadoConsultaEEFF").prop('disabled', false);
	$("#idCambiarEstadoConsultaEEFF").css('color', '#000000');	
	$("#idConsultarEEFF").prop('disabled', false);
	$("#idConsultarEEFF").css('color', '#000000');
	$("#idEliminarEEFF").prop('disabled', false);
	$("#idEliminarEEFF").css('color', '#000000');
}

function consultarConsultaEEFFBloque(){
	var valueBoton = $("#idConsultarEEFF").val();
	mostrarLoadingConsultaEEFF("idConsultarEEFF");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaEEFF('idConsultarEEFF',valueBoton);
		return alert("Debe seleccionar alguna consulta EEFF para realizar su consulta.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaEEFF.action").trigger("submit");
}

function eliminarConsultaEEFF(){
	var valueBoton = $("#idEliminarEEFF").val();
	mostrarLoadingConsultaEEFF("idEliminarEEFF");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaEEFF('idEliminarEEFF',valueBoton);
		return alert("Debe seleccionar alguna consulta EEFF para poder eliminarlas.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaEEFF.action").trigger("submit");
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

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function obtenerDetalleConsulta(numExpedienteEEFF){
	if(numExpedienteEEFF == ""){
		return alert("No se puede obtener el detalle de la consulta porque no se le indica el numero de expediente de EEFF.");
	}
	$("#divEmergenteMatriculacionConsultaEEFF").show();
	var $dest = $("#divEmergenteMatriculacionConsultaEEFF");
	$.post("cargarPopUpResultadoWSAltaConEEFF.action?consultaEEFF.numExpediente="+numExpedienteEEFF, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 720,
				buttons : {
					Cerrar : function() {
						$("#divEmergenteMatriculacionConsultaEEFF").show();
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