function buscar(){
	$('#formData').attr("action","consultarGestionCola.action").submit();
}

function limpiarConsulta(){
	$('#idNumExpediente').val("");
	$('#idNumColegiado').val("");
	$('#idTipoTramite').val("");
	$('#idNumCola').val("");
	$('#idEstado').val("");
	$('#idProceso').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
	$('#idMatricula').val("");
	$('#idBastidor').val("");
}


function cambiarElementosPorPaginaPeticionesCola(){
	var $dest = $("#displayTagDivPeticionesCola");
	$.ajax({
		url:"recargarPeticionesColaGCAjax.action",
		data:"numPorPage=" + $("#idResultadosPorPaginaSolicitudesCola").val() +"&page=1",
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			 if(filteredResponse.size() == 1){
				 $dest.html(filteredResponse);
			 }
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de recargar la lista con solicitudes pendientes.');
	    }
	});
}

function cambiarElementosPorPaginaUltimaEjecucion(){
	var $dest = $("#displayTagDivUltEjecucionesCola");
	$.ajax({
		url:"recargarUltimaEjecucionGCAjax.action",
		data:"numPorPage=" + $("#idResultadosPorPaginaUltimaEjecucion").val() +"&page=1",
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			 if(filteredResponse.size() == 1){
				 $dest.html(filteredResponse);
			 }
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de recargar la lista con las ultimas ejecuciones.');
	    }
	});
}

function subirPrioridad(){
	var numCheckSeleccionados = numCheckedSolicitudes(); 
	if(numCheckSeleccionados == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	} else if(numCheckSeleccionados > 1){
		alert("Solo se puede subir la prioridad a una solicitud a la vez.");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea subir la prioridad a la solicitud seleccionada?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");
	var codigo = "";
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) {
			codigo = checks[i].value;
			break;
		}
	}
	deshabilitarBotonesCola();
	$("#idBSubirPrioridad").val("Procesando...");
	limpiarHiddenInput("idsEnvios");
	input = $("<input>").attr("type", "hidden").attr("name", "idsEnvios").val(codigo);
	$('#formData').append($(input));
	$('#formData').attr("action","establecerMaxPrioridadGestionCola.action#SolicitudesCola").submit();
}

function reactivarSolicitudes(){
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea reactivar la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	deshabilitarBotonesCola();
	$("#idBReactivarSolicitudes").val("Procesando...");
	limpiarHiddenInput("idsEnvios");
	input = $("<input>").attr("type", "hidden").attr("name", "idsEnvios").val(codigos);
	$('#formData').append($(input));
	$('#formData').attr("action","reactivarGestionCola.action#SolicitudesCola").submit();
	
}

function finalizarError(){
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea finalizar con error la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	var pestaniaSeleccionada = "";
	if(checks.length > 1){
		pestaniaSeleccionada = "#SolicitudesCola";
	} else {
		pestaniaSeleccionada = "#UltimaEjecucion";
	}
	deshabilitarBotonesCola();
	$("#idBFinalizarError").val("Procesando...");
	limpiarHiddenInput("idsEnvios");
	input = $("<input>").attr("type", "hidden").attr("name", "idsEnvios").val(codigos);
	$('#formData').append($(input));
	$('#formData').attr("action","finalizarConErrorGestionCola.action"+pestaniaSeleccionada).submit();
}

function finalizarErrorServicio(){
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea finalizar con error servicio las solicitudes seleccionadas?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	deshabilitarBotonesCola();
	$("#idBFinalizarErrorServicio").val("Procesando...");
	limpiarHiddenInput("idsEnvios");
	input = $("<input>").attr("type", "hidden").attr("name", "idsEnvios").val(codigos);
	$('#formData').append($(input));
	$('#formData').attr("action","finalizarConErrorServicioGestionCola.action#SolicitudesCola").submit();
}

function sacarCola(){
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea eliminar la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	var pestaniaSeleccionada = "";
	if(checks.length > 1){
		pestaniaSeleccionada = "#SolicitudesCola";
	} else {
		pestaniaSeleccionada = "#UltimaEjecucion";
	}
	deshabilitarBotonesCola();
	$("#idBSacarCola").val("Procesando...");
	limpiarHiddenInput("idsEnvios");
	input = $("<input>").attr("type", "hidden").attr("name", "idsEnvios").val(codigos);
	$('#formData').append($(input));
	$('#formData').attr("action","eliminarPorColaGestionCola.action"+pestaniaSeleccionada).submit();
}

function deshabilitarBotonesCola() {
	document.getElementById("bloqueLoadingPestaniaSolicitudes").style.display = 'block';
	$("#idBSubirPrioridad").css("color","#BDBDBD");
	$("#idBSubirPrioridad").prop("disabled",true);
	$("#idBReactivarSolicitudes").css("color","#BDBDBD");
	$("#idBReactivarSolicitudes").prop("disabled",true);
	$("#idBFinalizarError").css("color","#BDBDBD");
	$("#idBFinalizarError").prop("disabled",true);
	$("#idBFinalizarErrorServicio").css("color","#BDBDBD");
	$("#idBFinalizarErrorServicio").prop("disabled",true);
	$("#idBSacarCola").css("color","#BDBDBD");
	$("#idBSacarCola").prop("disabled",true);
	$("#idBConsulta").css("color","#BDBDBD");
	$("#idBConsulta").prop("disabled",true);
	$("#idBLimpiar").css("color","#BDBDBD");
	$("#idBLimpiar").prop("disabled",true);
}

function numCheckedSolicitudes() {
	var checks = document.getElementsByName("listaChecksPeticionesPendientes");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}	
	return numChecked;
}

function marcarTodos(boton){
	var obj = $('[name="listaChecksPeticionesPendientes"]');
	$.each( obj, function( key, objeto) {
		$(this).attr('checked', boton.checked);
		cambiarCheck(this);
	});
}

function cambiarCheck(checkbox){
	var objHidden = $('[name="listaChecksPeticionesPendientes"][value='+checkbox.value+']');
	objHidden.attr('checked', checkbox.checked);
}

function desmarcarTodos(){
	var obj = $('[name="checkAllSolCola"]').attr('checked', false);
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
