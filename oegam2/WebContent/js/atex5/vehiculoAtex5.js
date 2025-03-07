function volverConsultaVehiculo(){
	deshabilitarBotonesVehiculoAtex5();
	$("#formData").attr("action", "inicioConsultaAtx5Vehiculo").trigger("submit");
}

function consultarVehiculo(){
	$("#idEstadoConsultaVehiculo").prop("disabled",false);
	$("#idNumExpConsultaVehiculo").prop("disabled",false);
	$("#idConsultaVehiculoAtex5").prop("disabled",false);
	var pestania = obtenerPestaniaSeleccionada();
	deshabilitarBotonesVehiculoAtex5();
	$("#formData").attr("action", "consultarAltaAtx5Vehiculo.action#"+pestania).trigger("submit");
}

function guardarConsultaVehiculo(){
	$("#idEstadoConsultaVehiculo").prop("disabled",false);
	$("#idNumExpConsultaVehiculo").prop("disabled",false);
	$("#idConsultaVehiculoAtex5").prop("disabled",false);
	deshabilitarBotonesVehiculoAtex5();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltaAtx5Vehiculo.action#"+pestania).trigger("submit");
}

function buscarConsultaVehiculoAtex5(){
    var matricula = new String(document.getElementById("idMatricula").value);
	matricula=Limpia_Caracteres_Matricula(matricula);
	document.getElementById("idMatricula").value = matricula.toUpperCase();
	
	var bastidor = new String(document.getElementById("idBastidor").value);
	bastidor=Limpia_Caracteres_Bastidor(bastidor);
	document.getElementById("idBastidor").value = bastidor.toUpperCase();
	
	$("#formData").attr("action", "buscarConsultaAtx5Vehiculo.action").trigger("submit");
}

function limpiarConsulta(){
	$("#idMatricula").val("");
	$("#idEstado").val("");
	$("#idBastidor").val("");
	$("#idTasa").val("");
	$("#idNumExpediente").val("");
	$("#idNive").val("");
	$("#idContrato").val("");
	$("#idConsultaMasivaAtex5").val("");
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
	$("#idNumColegiado").val("");
	$("#idNumExpediente").val("");
}

function imprimirPdfVehiculo(){
	$("#idEstadoConsultaVehiculo").prop("disabled",false);
	$("#idNumExpConsultaVehiculo").prop("disabled",false);
	$("#idConsultaVehiculoAtex5").prop("disabled",false);
	var pestania = obtenerPestaniaSeleccionada();
	deshabilitarBotonesVehiculoAtex5();
	$("#formData").attr("action", "imprimirPdfAltaAtx5Vehiculo.action#"+pestania).trigger("submit");
	habilitarBotonesVehiculoAtex5();
}

function deshabilitarBotonesVehiculoAtex5(){
	$("#idGuardarVehAtex5").prop('disabled', true);
	$("#idGuardarVehAtex5").css('color', '#6E6E6E');	
	$("#idConsultarVehAtex5").prop('disabled', true);
	$("#idConsultarVehAtex5").css('color', '#6E6E6E');	
	$("#idDescargarVehAtex5").prop('disabled', true);
	$("#idDescargarVehAtex5").css('color', '#6E6E6E');
	$("#idVolverVehAtex5").prop('disabled', true);
	$("#idVolverVehAtex5").css('color', '#6E6E6E');
}

function habilitarBotonesVehiculoAtex5(){
	$("#idGuardarVehAtex5").prop('disabled', false);
	$("#idGuardarVehAtex5").css('color', '#000000');	
	$("#idConsultarVehAtex5").prop('disabled', false);
	$("#idConsultarVehAtex5").css('color', '#000000');	
	$("#idDescargarVehAtex5").prop('disabled', false);
	$("#idDescargarVehAtex5").css('color', '#000000');
	$("#idVolverVehAtex5").prop('disabled', false);
	$("#idVolverVehAtex5").css('color', '#000000');
}


function cambiarElementosPorPaginaConsultaVehiculoAtex5(){
	var $dest = $("#displayTagDivConsultaVehiculoAtex5");
	$.ajax({
		url:"navegarConsultaAtx5Vehiculo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsVehiAtex5").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las consulta de vehiculos.');
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

function imprimirBloque(){
	var valueBoton = $("#idImprimir").val();
	var codigos = getChecksConsultaMarcados("listaChecks");
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idImprimir");
	if(codigos == ""){
		alert("Debe seleccionar alguna consulta para poder realizar su impresion.");
		ocultarLoadingConsultaVehiculoAtex5('idImprimir',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "imprimirConsultaAtx5Vehiculo.action").trigger("submit");
	ocultarLoadingConsultaVehiculoAtex5('idImprimir',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
}

function cambiarEstadoBloque(){
	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idCambiarEstado");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaVehiculoAtex5('idCambiarEstado',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		return alert("Debe seleccionar alguna consulta para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaVehiculoAtex5");
	$.post("cargarPopUpCambioEstadoConsultaAtx5Vehiculo.action", function(data) {
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
						$("#divEmergenteConsultaVehiculoAtex5").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaAtx5Vehiculo.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaVehiculoAtex5('idCambiarEstado',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
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

function mostrarLoadingConsultaVehiculoAtex5(bloqueLoading,boton) {
	deshabilitarBotonesConsultaVehiculoAtex5();
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaVehiculoAtex5(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaVehiculoAtex5();
}

function habilitarBotonesConsultaVehiculoAtex5(){
	$("#idCambiarEstado").prop('disabled', false);
	$("#idCambiarEstado").css('color', '#000000');	
	$("#idConsultar").prop('disabled', false);
	$("#idConsultar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
	$("#idDescargar").prop('disabled', false);
	$("#idDescargar").css('color', '#000000');
	$("#idImprimir").prop('disabled', false);
	$("#idImprimir").css('color', '#000000');
	$("#idAsignar").prop('disabled', false);
	$("#idAsignar").css('color', '#000000');
	$("#idDesasignar").prop('disabled', false);
	$("#idDesasignar").css('color', '#000000');
}

function deshabilitarBotonesConsultaVehiculoAtex5(){
	$("#idCambiarEstado").prop('disabled', true);
	$("#idCambiarEstado").css('color', '#6E6E6E');	
	$("#idConsultar").prop('disabled', true);
	$("#idConsultar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#idDescargar").prop('disabled', true);
	$("#idDescargar").css('color', '#6E6E6E');
	$("#idImprimir").prop('disabled', true);
	$("#idImprimir").css('color', '#6E6E6E');
	$("#idAsignar").prop('disabled', true);
	$("#idAsignar").css('color', '#6E6E6E');
	$("#idDesasignar").prop('disabled', true);
	$("#idDesasignar").css('color', '#6E6E6E');
}

function eliminar(){
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaVehiculoAtex5('idEliminar',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		return alert("Debe seleccionar alguna consulta para poder eliminarla.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaAtx5Vehiculo.action").trigger("submit");
}

function consultarBloque(){
	var valueBoton = $("#idConsultar").val();
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idConsultar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaVehiculoAtex5('idConsultar',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		return alert("Debe seleccionar alguna consulta para poder realizar la peticion.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaAtx5Vehiculo.action").trigger("submit");
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

function asignarTasa(){
	var valueBoton = $("#idAsignar").val();
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idAsignar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaVehiculoAtex5('idAsignar',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		return alert("Debe seleccionar alguna consulta para asignar una tasa.");
	}
	var $dest = $("#divEmergenteConsultaVehiculoAtex5");
	$.post("cargarPopUpAsignarTasaConsultaAtx5Vehiculo.action", function(data) {
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
					AsignarTasa : function() {
						var tasaSeleccionada = $("#idTasaAtex5").val();
						if(tasaSeleccionada == ""){
							alert("Seleccione código de tasa");
							return false;
						}
					$("#divEmergenteConsultaVehiculoAtex5").dialog("close");
						limpiarHiddenInput("tasaSeleccionada");
						input = $("<input>").attr("type", "hidden").attr("name", "tasaSeleccionada").val(tasaSeleccionada);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "asignarTasaConsultaAtx5Vehiculo.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaVehiculoAtex5('idAsignar',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
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

function desasignarTasa(){
	deshabilitarBotonesConsultaVehiculoAtex5();
	var valueBoton = $("#idDesasignar").val();
	mostrarLoadingConsultaVehiculoAtex5("bloqueLoadingConsultaVehiculoAtex5","idDesasignar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaVehiculoAtex5('idDesasignar',valueBoton,"bloqueLoadingConsultaVehiculoAtex5");
		alert("Debe seleccionar algun trámite para poder desasignar la tasa.");
		habilitarBotonesConsultaVehiculoAtex5();
		return false;
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea desasignar las tasas seleccionadas de la lista? Una vez realizada la acción, las tasas dejaran de estar asociadas a todos los trámites")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarTasaConsultaAtx5Vehiculo.action").trigger("submit");
	}
}


function limpiarVehiAtex5Masiva(){
	$("#idNifConsultaMasivaPersona").val("");
	$("#idRznSocialConsultaMasivaPersona").val("");
	$("#idPrimerApellidoConsultaMasivaPersona").val("");
	$("#idSegundoApellidoConsultaMasivaPersona").val("");
	$("#idNombreConsultaMasivaPersona").val("");
	$("#idAnioNacConsultaMasivaPersona").val("");
	$("#idMasivaDiaFecNac").val("");
	$("#idMasivaMesFecNac").val("");
	$("#idMasivaAnioFecNac").val("");
	$("#idConsultaMasivaAtex5").val("");
	$("#idConsMasivaAtex5").hide();
	
}

function modificarVehiAtex5Masiva(){
	var valueBoton = $("#idModificarVehiAtex5").val();
	mostrarLoadingMasivaAtex5("bloqueLoadingListaVehiMasivaAtex5","idModificarVehiAtex5");
	var codigo = "";
	var numChecks = 0;
	$("input[name='listaChecksMasivas']:checked").each(function() {
		if(this.checked){
			if(codigo==""){
				codigo += this.value;
			}else{
				codigo += "-" + this.value;
			}
			numChecks++;
		}
	});
	if(codigo == ""){
		ocultarLoadingMasivaAtex5('idModificarVehiAtex5',valueBoton,"bloqueLoadingListaVehiMasivaAtex5");
		return alert("Debe seleccionar alguna consulta de persona para poder modificarla.");
	}else if(numChecks > 1){
		ocultarLoadingMasivaAtex5('idModificarVehiAtex5',valueBoton,"bloqueLoadingListaVehiMasivaAtex5");
		return alert("Las Consultas de Personas no se pueden modificar en bloque, por favor seleccione solo una.");
	}
	var $dest = $("#divVehiculoMasiva");
	$.ajax({
		url:"modificarVehiMasivaAjaxAtex5.action#DatosConsultaVeh",
		data:"numExpediente="+ codigo,
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			ocultarLoadingMasivaAtex5('idModificarVehiAtex5',valueBoton,"bloqueLoadingListaVehiMasivaAtex5");
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la consulta de persona.');
	        ocultarLoadingMasivaAtex5('idModificarVehiAtex5',valueBoton,"bloqueLoadingListaVehiMasivaAtex5");
	    }
	});
}

function eliminarVehiAtex5Masiva(){
	deshabilitarCamposComunesMasivas();
	mostrarLoadingMasivaAtex5("bloqueLoadingListaVehiMasivaAtex5","idEliminarVehiAtex5");
	var codigos = getChecksConsultaMarcados("listaChecksMasivas");
	if(codigos == ""){
		ocultarLoadingMasivaAtex5('idEliminarVehiAtex5',valueBoton,"bloqueLoadingListaVehiMasivaAtex5");
		return alert("Debe seleccionar alguna consulta para poder eliminarla.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "eliminarConsultasAltaAtx5Masivo.action#"+pestania).trigger("submit");
}

function marcarTodasMasivasVehiculos(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksMasivas) {
		if (!document.formData.listaChecksMasivas.length) { 
			document.formData.listaChecksMasivas.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksMasivas.length; i++) {
			document.formData.listaChecksMasivas[i].checked = marcar;
		}
	}
}
