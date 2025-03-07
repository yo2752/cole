
function limpiarFormConsultaPeticionesPendientes(){
	
	document.getElementById("numExpediente").value = "";
	document.getElementById("numColegiado").value = "";
	document.getElementById("tipoTramite").selectedIndex = 0;
	document.getElementById("estadoSolicitud").selectedIndex = 0;
	document.getElementById("procesoSolicitud").selectedIndex = 0;
	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
	document.getElementById("numeroCola").value = "-1";
}

function buscarSolicitudesPendientes() {
	
	document.getElementById("bloqueBusqueda").style.display = 'block';
	var numColegiado = document.getElementById("numColegiado").value;
	if(isNaN(numColegiado)){
		alert("El campo n\u00FAmero de colegiado debe ser num\u00e9rico");
		return false;
	}
	var numExpediente = document.getElementById("numExpediente").value; 
	if(isNaN(numExpediente)){
		alert("El campo n\u00FAmero de expediente debe ser num\u00e9rico");
		return false;
	}
	mostrarLoadingPeticionesPendientes(1);
	document.formData.action = "buscarPeticionesPendientes.action";
	document.formData.submit();
}

function verEjecucionesProcesos(){
	
	if(document.getElementById("procesoSolicitud").selectedIndex == 0){
		alert("Seleccione un proceso para ver sus \u00faltimas ejecuciones");
		return false;
	}
	document.getElementById("bloqueBusqueda").style.display = 'none';
	mostrarLoadingPeticionesPendientes(2);
	document.formData.action = "verEjecucionesProcesosPeticionesPendientes.action";
	document.formData.submit();
}
function ejecucionesProcesosUltimo (){
	
	if(document.getElementById("procesoSolicitud").selectedIndex == 0){
		alert("Seleccione un proceso para ver sus \u00faltimas ejecuciones");
		return false;
	}
	document.getElementById("bloqueBusqueda").style.display = 'none';
	mostrarLoadingPeticionesPendientes(2);
	document.formData.action = "ejecucionesProcesosUltimoPeticionesPendientes.action";
	document.formData.submit();
}

function verPeticionesPorCola(){
	
	document.getElementById("bloqueBusqueda").style.display = 'none';
	mostrarLoadingPeticionesPendientes(3);
	document.formData.action = "consultarPeticionesPorColasPeticionesPendientes.action";
	document.formData.submit();
}

function mostrarLoadingPeticionesPendientes(param){
	
	if(param == 1){
		document.getElementById("bBuscar").disabled = "true";
		document.getElementById("bBuscar").style.color = "#6E6E6E";
		document.getElementById("bBuscar").value = "Procesando...";
		document.getElementById("bEjecuciones").disabled = "true";
		document.getElementById("bEjecuciones").style.color = "#6E6E6E";
		document.getElementById("bPeticionesPorCola").disabled = "true";
		document.getElementById("bPeticionesPorCola").style.color = "#6E6E6E";
		
	}else if (param == 2){
		document.getElementById("bEjecuciones").disabled = "true";
		document.getElementById("bEjecuciones").style.color = "#6E6E6E";
		document.getElementById("bEjecuciones").value = "Procesando...";
		document.getElementById("bBuscar").disabled = "true";
		document.getElementById("bBuscar").style.color = "#6E6E6E";
		document.getElementById("bPeticionesPorCola").disabled = "true";
		document.getElementById("bPeticionesPorCola").style.color = "#6E6E6E";
	}else{
		document.getElementById("bPeticionesPorCola").disabled = "true";
		document.getElementById("bPeticionesPorCola").style.color = "#6E6E6E";
		document.getElementById("bPeticionesPorCola").value = "Procesando...";
		document.getElementById("bBuscar").disabled = "true";
		document.getElementById("bBuscar").style.color = "#6E6E6E";
		document.getElementById("bEjecuciones").disabled = "true";
		document.getElementById("bEjecuciones").style.color = "#6E6E6E";
	}
	document.getElementById("bLimpiar").disabled = "true";
	document.getElementById("bLimpiar").style.color = "#6E6E6E";
}

function cambiarElementosPorPaginaConsultaPeticionesPendientes() {

	document.location.href = 'navegarPeticionesPendientes.action?resultadosPorPagina=' + 
		document.formData.idResultadosPorPagina.value;
}

function subirPrioridadSolicitud() {
	
	if(numCheckedSolicitudes() != 1) {
		alert("Debe seleccionar una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea subir la prioridad a la solicitud seleccionada?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksSolicitudes");
	var codigo = "";
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) {
			codigo = checks[i].value;
			break;
		}
	}
	deshabilitarBotonesCola();
	document.getElementById("idSubirPrioridadSolicitud").value = "Procesando...";
	document.forms[0].action="establecerMaxPrioridadPeticionesPendientes.action?numsExpediente=" + codigo;
	document.forms[0].submit();
}

function reactivarSolicitudesPeticionesPendientes(){
	
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea reactivar la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksSolicitudes");
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
	document.getElementById("idReactivarSolicitudes").value = "Procesando...";
	document.forms[0].action="reactivarPeticionesPendientes.action?numsExpediente=" + codigos;
	document.forms[0].submit();
}

function finalizarConErrorPeticionesPendientes(){
	
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea finalizar con error la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksSolicitudes");
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
	document.getElementById("idFinalizarConError").value = "Procesando...";
	document.forms[0].action="finalizarConErrorPeticionesPendientes.action?numsExpediente=" + codigos;
	document.forms[0].submit();
}

function finalizarConErrorServicioPeticionesPendientes(){
	
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea finalizar con error servicio la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksSolicitudes");
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
	document.getElementById("idFinalizarConErrorServicio").value = "Procesando...";
	document.forms[0].action="finalizarConErrorServicioPeticionesPendientes.action?numsExpediente=" + codigos;
	document.forms[0].submit();
}

function eliminarColaPeticionesPendientes() {
	
	if(numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea eliminar la/s solicitud/es seleccionada/s?")) {
		return false;
	}
	var checks = document.getElementsByName("listaChecksSolicitudes");
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
	document.getElementById("idEliminarCola").value = "Procesando...";
	document.forms[0].action="eliminarPorColaPeticionesPendientes.action?numsExpediente=" + codigos;
	document.forms[0].submit();
}


function deshabilitarBotonesCola() {
	document.getElementById("bloqueLoading2").style.display = 'block';
	document.getElementById("idSubirPrioridadSolicitud").disabled = "true";
	document.getElementById("idSubirPrioridadSolicitud").style.color = "#6E6E6E";
	document.getElementById("idReactivarSolicitudes").disabled = "true";
	document.getElementById("idReactivarSolicitudes").style.color = "#6E6E6E";
	document.getElementById("idFinalizarConError").disabled = "true";
	document.getElementById("idFinalizarConError").style.color = "#6E6E6E";
	document.getElementById("idFinalizarConErrorServicio").disabled = "true";
	document.getElementById("idFinalizarConErrorServicio").style.color = "#6E6E6E";
	document.getElementById("idEliminarCola").disabled = "true";
	document.getElementById("idEliminarCola").style.color = "#6E6E6E";
}


function numCheckedSolicitudes() {
	var checks = document.getElementsByName("listaChecksSolicitudes");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}	
	return numChecked;
}

var chartHilos, chartTramites, chartGeneral, procesoAnterior;

function actualizarChartHilos(valueProceso, actualizar) {
	$.post("cargarSolicitudesPorHiloMonitorizacion.action", {
		proceso : valueProceso
	}, function(responseJson) {
		if (responseJson != null) {
			if (chartHilos && !actualizar) {
				chartHilos.destroy();
			}
			if (actualizar) {
				actualizarChart(chartHilos, responseJson);
			} else {
				$('#hilos').show();
				chartHilos = new Chart($("#chartHilos"), {
					type : 'bar',
					data : responseJson,
					options: {
				        scales: {
				            xAxes: [{
				                stacked: true
				           }],
				           yAxes: [{
				                stacked: true,
								beginAtZero : true
				           }]
				        }
				    }
				});
		}
			actualizarHora();
		} else {
			$("#hilos").hide();
		}
	});
}

function actualizarChartTramites(valueProceso, actualizar) {
	$.post("cargarFinalizacionesProcesoMonitorizacion.action", {
		proceso : valueProceso
	}, function(responseJson) {
		if (responseJson != null) {
			if (chartTramites && !actualizar) {
				chartTramites.destroy();
			}

			if (actualizar) {
				actualizarChart(chartTramites, responseJson);
			} else {
				$('#tramites').show();
				chartTramites = new Chart($("#chartTramites"), {
					type : 'horizontalBar',
					data : responseJson,
					options: {
				        scales: {
				            xAxes: [{
				                stacked: true,
								beginAtZero : true
				           }],
				           yAxes: [{
				                stacked: true,
								beginAtZero : true
				           }]
				        }
				    }
				});
			}
			actualizarHora();
		} else {
			$("#tramites").hide();
		}
	});
}

function actualizarChartGeneral(actualizar) {
	$.post("cargarEstadisticasGeneralMonitorizacion.action", function(responseJson) {
		if (responseJson != null) {
			if (chartGeneral && !actualizar) {
				chartGeneral.destroy();
			}
			if (actualizar) {
				actualizarChart(chartGeneral, responseJson);
			} else {
				$('#general').show();
				chartGeneral = new Chart( $("#chartGeneral"), {
					type : 'pie',
					data : responseJson
				});
			}
			actualizarHora();
		}
	});
}

function actualizarGraficasProcesos() {
	var proceso = $("#idProceso").val();
	var actualizar;
	if (proceso != procesoAnterior) {
		$("#tramites").hide();
		$("#hilos").hide();
		$("#general").hide();
		procesoAnterior = proceso;
		actualizar = false;
	} else {
		actualizar = true;
	}
	if (proceso) {
		actualizarChartHilos(proceso, actualizar);
		actualizarChartTramites(proceso, actualizar);
	} else {
		actualizarChartGeneral(actualizar);
	}
}

function actualizarHora() {
	var date = new Date();
	$("#ultimaHora").html("&Uacute;ltima actualización&nbsp;" + $.datepicker.formatDate('dd/mm/yy ', date) + date.getHours() + ":" + date.getMinutes() + ":" + date.getSeconds());
}

function activarIntervalo() {
	if ($("#idCheckAuto").attr("checked")) {
		$("#idIntervalo").removeAttr("disabled").css("color", "#000000");
		establecerMonitorizacion();
	} else {
		$("#idIntervalo").attr("disabled", true).css("color", "#6E6E6E");
	}
}

function establecerMonitorizacion() {
	if($("#idCheckAuto").attr("checked")) {
		actualizarGraficasProcesos();
	    setTimeout(function() { 
	    	establecerMonitorizacion();
	    }, $("#idIntervalo").val()); 
	}
}

function actualizarChart(chart, data) {
	$.each(data.datasets, function(i, dataset) {
		chart.data.datasets[i].borderWidth = dataset.borderWidth;
		$.each(dataset.data, function(j, data) {
			chart.data.datasets[i].data[j] = data;
		});
	});
	chart.update();
}

function cambiarElementosPorPaginaSolCola(){
	var $dest = $("#displayTagDivSolCola");
	$.ajax({
		url:"navegarSolColaPeticionesPendientes.action",
		data:"resultadosPorPaginaSolCola="+ $("#idResultadosPorPaginaSolCola").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las solicitudes en cola.');
	    }
	});
}

