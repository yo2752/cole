function inicializarVentanasConsultaDev(){
	if($("#idEstadoConsultaDev").val() == null || $("#idEstadoConsultaDev").val() == "" ||
			($("#idEstadoConsultaDev").val() == "1" || $("#idEstadoConsultaDev").val() == "4" )){
		$("#idCifConsultaDev").prop("disabled",false);
		$("#idContrato").prop("disabled",false);
	}else{
		$("#idCifConsultaDev").prop("disabled",true);
		$("#idContrato").prop("disabled",true);
	}
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

function consultarDev(){
	deshabilitarCampos();
	$("#idCifConsultaDev").val($.trim($("#idCifConsultaDev").val()));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "consultarAltasDEV.action#"+pestania).trigger("submit");
}

function guardarConsultaDev(){
	deshabilitarCampos();
	$("#idCifConsultaDev").val($.trim($("#idCifConsultaDev").val()));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltasDEV.action#"+pestania).trigger("submit");
}

function volverConsultaDev(){
	$("#formData").attr("action", "inicioConsultaDEV").trigger("submit");
}

function deshabilitarCampos(){
	$("#idCodProcedimiento").prop("disabled",false);
	$("#idDescProcedimiento").prop("disabled",false);
	$("#idCifConsultaDev").prop("disabled",false);
	$("#idEstadoCif").prop("disabled",false);
	$("#idEmisor").prop("disabled",false);
	$("#idCodEstado").prop("disabled",false);
	$("#idDiaSuscripcion").prop("disabled",false);
	$("#idMesSuscripcion").prop("disabled",false);
	$("#idAnioSuscripcion").prop("disabled",false);
	$("#idEstadoConsultaDev").prop("disabled",false);
}


function abrirDetalle(idConsultaDev){
	limpiarHiddenInput("idConsultaDev");
	input = $("<input>").attr("type", "hidden").attr("name", "idConsultaDev").val(idConsultaDev);
	$('#formData').append($(input));
	$("#formData").attr("action", "detalleConsultaDEV.action").trigger("submit");
}

function abrirEvolucion(idConsultaDev){
	if(idConsultaDev == null || idConsultaDev == ""){
		return alert("No se puede obtener la evolución de la consulta dev seleccionada.");
	}
	var $dest = $("#divEmergenteConsultaDevEvolucion");
	var url = "abrirEvolucionConsultaDEV.action?idConsultaDev=" + idConsultaDev;
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

function buscarConsultaDev(){
	$("#formData").attr("action", "buscarConsultaDEV.action").trigger("submit");
}

function limpiarConsulta(){
	$("#idCif").val("");
	$("#idEstado").val("");
	$("#idEstadoCif").val("");
	$("#idContrato").val("");
	$("#diaFechaAltaIniDev").val("");
	$("#mesFechaAltaIniDev").val("");
	$("#anioFechaAltaIniDev").val("");
	$("#diaFechaAltaFinDev").val("");
	$("#mesFechaAltaFinDev").val("");
	$("#anioFechaAltaFinDev").val("");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function cambiarEstadoBloque(){
	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingConsultaDev("idCambiarEstado");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaDev('idCambiarEstado',valueBoton);
		return alert("Debe seleccionar alguna consulta Dev para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaDev");
	$.post("cargarPopUpCambioEstadoConsultaDEV.action", function(data) {
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
						$("#divEmergenteConsultaDev").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaDEV.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaDev('idCambiarEstado',valueBoton);
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

function consultarDevBloque(){
	var valueBoton = $("#idConsultar").val();
	mostrarLoadingConsultaDev("idConsultar");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaDev('idConsultar',valueBoton);
		return alert("Debe seleccionar alguna consulta Dev para poder realizar la peticion.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaDEV.action").trigger("submit");
}

function eliminar(){
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaDev("idEliminar");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaDev('idEliminar',valueBoton);
		return alert("Debe seleccionar alguna consulta Dev para poder eliminarla.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaDEV.action").trigger("submit");
}



function exportar(){
	var valueBoton = $("#idExportar").val();
	mostrarLoadingConsultaDev("idExportar");
	var codigos = getTramiteFinalizado();
	if(codigos == ""){
		ocultarLoadingConsultaDev('idExportar',valueBoton);
		return alert("Debe seleccionar alguna consulta Dev en estado FINALIZADO para poder exportar.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "exportarConsultaDEV.action").trigger("submit");
	ocultarLoadingConsultaDev('idExportar',valueBoton);
}



function mostrarLoadingConsultaDev(boton) {
	deshabilitarBotonesConsultaDev();
	document.getElementById("bloqueLoadingConsultaDev").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaDev(boton, value) {
	document.getElementById("bloqueLoadingConsultaDev").style.display = "none";
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

function deshabilitarBotonesConsultaDev(){
	$("#idCambiarEstado").prop('disabled', true);
	$("#idCambiarEstado").css('color', '#6E6E6E');	
	$("#idConsultar").prop('disabled', true);
	$("#idConsultar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaDev(){
	$("#idCambiarEstado").prop('disabled', false);
	$("#idCambiarEstado").css('color', '#000000');	
	$("#idConsultar").prop('disabled', false);
	$("#idConsultar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
}

function cambiarElementosPorPaginaEvolucionConsultaDev(){
	var $dest = $("#displayTagEvolucionConsultaDevDiv");
	$.ajax({
		url:"navegarEvolucionConDEV.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolConsultaDev").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones.');
	    }
	});
}

function cambiarElementosPorPaginaConsultaDev(){
	var $dest = $("#displayTagDivConsultaDev");
	$.ajax({
		url:"navegarConsultaDEV.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaDev").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las consultas dev.');
	    }
	});
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

function altaMasivasConsultasDev(){
	var archivo = document.getElementById("idFicheroAltaMas").value;
	if($("#idContratoMasivasDev").val() == ""){
		return alert("Debe de seleccionar alg\u00FAn contrato para poder realizar el alta Masiva de Consultas Dev.");
	}
	if (archivo.length > 0) {
		extension = (archivo.substring(archivo.lastIndexOf(".")))
				.toLowerCase();
		if (".txt" != extension){
			return alert("La extension del fichero no es la indica, solo se permiten el alta de ficheros con extension '.txt'");
		}
		mostrarLoadingMasivasDev();
		$("#formData").attr("action", "altasAltasMasivasDev.action").trigger("submit");
	}else{
		return alert("Debe de introducir alg\u00FAn fichero para poder realizar el alta Masiva de Consultas Dev.");
	}
}

function mostrarLoadingMasivasDev() {
	$("#idEnviarDev").prop('disabled', true);
	$("#idEnviarDev").css('color', '#6E6E6E');	
	document.getElementById("bloqueLoadingMasivaConsultaDev").style.display = "block";
	$('#idEnviarDev').val("Procesando..");
}

function marcarTodosConsultaDev(objCheck) {
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

function getTramiteFinalizado(){
	var codigos = "";
	var finalizado = false;
	$("input[name='listaChecks']:checked").each(function() {
		$(this).parents().parents().each(function() {
			
			if($(this).closest('tr').hasClass('enlaceImpreso impreso')){
				finalizado = true;
			}
			
		});
		if(this.checked && finalizado){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	return codigos;
}


