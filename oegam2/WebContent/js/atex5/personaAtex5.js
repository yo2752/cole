function volverConsultaPersona(){
	deshabilitarBotonesPersonaAtex5();
	$("#formData").attr("action", "inicioConsultaAtx5Persona").trigger("submit");
}

function consultarPersona(){
	$("#idEstadoConsultaPersona").prop("disabled",false);
	$("#idNumExpConsultaPersona").prop("disabled",false);
	$("#idConsultaPersonaAtex5").prop("disabled",false);
	var pestania = obtenerPestaniaSeleccionada();
	deshabilitarBotonesPersonaAtex5();
	$("#formData").attr("action", "consultarAltaAtx5Persona.action#"+pestania).trigger("submit");
}

function guardarConsultaPersona(){
	$("#idEstadoConsultaPersona").prop("disabled",false);
	$("#idNumExpConsultaPersona").prop("disabled",false);
	$("#idConsultaPersonaAtex5").prop("disabled",false);
	deshabilitarBotonesPersonaAtex5();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltaAtx5Persona.action#"+pestania).trigger("submit");
}

function buscarConsultaPersonaAtex5(){
	$("#formData").attr("action", "buscarConsultaAtx5Persona.action").trigger("submit");
}

function limpiarConsulta(){
	$("#idNif").val("");
	$("#idEstado").val("");
	$("#idRazonSocial").val("");
	$("#idContrato").val("");
	$("#idNumColegiado").val("");
	$("#idNumExpediente").val("");
	$("#idConsultaMasivaAtex5").val("");
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
}

function cambiarElementosPorPaginaConsultaPersonaAtex5(){
	var $dest = $("#displayTagDivConsultaPersonaAtex5");
	$.ajax({
		url:"navegarConsultaAtx5Persona.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsPerAtex5").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las consulta de personas.');
	    }
	});
}

function cambiarElementosPorPaginaEvolucionPersonaAtex5(){
	var $dest = $("#displayTagEvolucionPersonaAtex5Div");
	$.ajax({
		url:"navegarEvolucionPersonaAtex5.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolPersAtex5").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evolucion de consulta de persona atex5.');
	    }
	});
}

function marcarTodas(objCheck) {
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


function imprimirPdfPersona(){
	deshabilitarBotonesPersonaAtex5();
	$("#idEstadoConsultaPersona").prop("disabled",false);
	$("#idNumExpConsultaPersona").prop("disabled",false);
	$("#idConsultaPersonaAtex5").prop("disabled",false);
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "imprimirPdfAltaAtx5Persona.action#"+pestania).trigger("submit");
	habilitarBotonesPersonaAtex5();
}

function cambiarEstadoBloque(){
	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingPersonaAtex5("bloqueLoadingConsultaPersonaAtex5","idCambiarEstado");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingPersonaAtex5('idCambiarEstado',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
		return alert("Debe seleccionar alguna consulta para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaPersonaAtex5");
	$.post("cargarPopUpCambioEstadoConsultaAtx5Persona.action", function(data) {
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
						$("#divEmergenteConsultaPersonaAtex5").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaAtx5Persona.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingPersonaAtex5('idCambiarEstado',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
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

function mostrarLoadingPersonaAtex5(bloqueLoading,boton) {
	deshabilitarBotonesConsultaPerAtex5();
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingPersonaAtex5(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaPerAtex5();
}

function getChecksConsultaMarcados(name){
	var codigos = "";
	$("input[name='"+name+"']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	return codigos;
}

function deshabilitarBotonesConsultaPerAtex5(){
	$("#idCambiarEstado").prop('disabled', true);
	$("#idCambiarEstado").css('color', '#6E6E6E');	
	$("#idConsultar").prop('disabled', true);
	$("#idConsultar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#idImprimir").prop('disabled', true);
	$("#idImprimir").css('color', '#6E6E6E');
}

function habilitarBotonesConsultaPerAtex5(){
	$("#idCambiarEstado").prop('disabled', false);
	$("#idCambiarEstado").css('color', '#000000');	
	$("#idConsultar").prop('disabled', false);
	$("#idConsultar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
	$("#idImprimir").prop('disabled', false);
	$("#idImprimir").css('color', '#000000');
}

function eliminar(){
	deshabilitarBotonesConsultaPerAtex5();
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingPersonaAtex5("bloqueLoadingConsultaPersonaAtex5","idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingPersonaAtex5('idEliminar',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
		alert("Debe seleccionar alguna consulta para poder eliminarla.");
		habilitarBotonesConsultaPerAtex5();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaAtx5Persona.action").trigger("submit");
}

function consultarBloque(){
	deshabilitarBotonesConsultaPerAtex5();
	var valueBoton = $("#idConsultar").val();
	mostrarLoadingPersonaAtex5("bloqueLoadingConsultaPersonaAtex5","idConsultar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingPersonaAtex5('idConsultar',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
		alert("Debe seleccionar alguna consulta para poder realizar la peticion.");
		habilitarBotonesConsultaPerAtex5();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaAtx5Persona.action").trigger("submit");
}

function imprimirBloque(){
	var valueBoton = $("#idImprimir").val();
	deshabilitarBotonesConsultaPerAtex5();
	mostrarLoadingPersonaAtex5("bloqueLoadingConsultaPersonaAtex5","idImprimir");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		alert("Debe seleccionar alguna consulta para poder realizar su impresión.");
		ocultarLoadingPersonaAtex5('idImprimir',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
		habilitarBotonesConsultaPerAtex5();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "imprimirConsultaAtx5Persona.action").trigger("submit");
	ocultarLoadingPersonaAtex5('idImprimir',valueBoton,"bloqueLoadingConsultaPersonaAtex5");
	habilitarBotonesConsultaPerAtex5();
}

function deshabilitarBotonesPersonaAtex5(){
	$("#idGuardarPerAtex5").prop('disabled', true);
	$("#idGuardarPerAtex5").css('color', '#6E6E6E');	
	$("#idConsultarPerAtex5").prop('disabled', true);
	$("#idConsultarPerAtex5").css('color', '#6E6E6E');	
	$("#idImprimirPerAtex5").prop('disabled', true);
	$("#idImprimirPerAtex5").css('color', '#6E6E6E');
	$("#idVolverPerAtex5").prop('disabled', true);
	$("#idVolverPerAtex5").css('color', '#6E6E6E');
}

function habilitarBotonesPersonaAtex5(){
	$("#idGuardarPerAtex5").prop('disabled', false);
	$("#idGuardarPerAtex5").css('color', '#000000');	
	$("#idConsultarPerAtex5").prop('disabled', false);
	$("#idConsultarPerAtex5").css('color', '#000000');
	$("#idImprimirPerAtex5").prop('disabled', false);
	$("#idImprimirPerAtex5").css('color', '#000000');
	$("#idVolverPerAtex5").prop('disabled', false);
	$("#idVolverPerAtex5").css('color', '#000000');
}


