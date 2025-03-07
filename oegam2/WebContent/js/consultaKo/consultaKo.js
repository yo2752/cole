function buscarKo(){
	bloqueaBotonesConsultaKo();
	$("#formData").attr("action", "buscarConsMatrKo").trigger("submit");
}
function cambiarElementosPorPaginaConsultaKo(){
	var $dest = $("#displayTagDivConsultaKo");
	$.ajax({
		url:"navegarConsMatrKo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsKo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las consulta de Ko.');
	    }
	});
}
function cambiarEstadoCKO(){
	var valueBoton = $("#idBCambiarEstadoKo").val();
	mostrarLoadingConsultaKo('idBCambiarEstadoKo');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingConsultaKo('idBCambiarEstadoKo',valueBoton);
		return alert("Debe seleccionar algún KO para poder cambiar su estado.");
	}
	
	
	var $dest = $("#divEmergenteConsultaKo");
	$.post("cargarPopUpCambioEstadoConsMatrKo.action", function(data) {
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
						$("#divEmergenteConsultaKo").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsMatrKo.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaKo('idBCambiarEstadoKo',valueBoton);
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

function validarKo(){
	var valueBoton = $("#bValidarKo").val();
	mostrarLoadingConsultaKo('bValidarKo');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingConsultaKo('bValidarKo',valueBoton);
		return alert("Debe seleccionar algún trámite para poder validar los trámites.");
	}
	
	if (confirm("¿Desea validar los trámites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
	$("#formData").attr("action", "validarConsMatrKo").trigger("submit");
	}else{
		ocultarLoadingConsultaKo('bValidarKo',valueBoton);
	}
}

function mostrarLoadingConsultaKo(boton){
	bloqueaBotonesConsultaKo();
	document.getElementById("bloqueLoadingConsultaKo").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaKo(boton, value) {
	document.getElementById("bloqueLoadingConsultaKo").style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaKo();
}
function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function bloqueaBotonesConsultaKo() {	
	$('#idAltaKo').prop('disabled', true);
	$('#idAltaKo').css('color', '#6E6E6E');	
	$('#bGenerarExcelKo').prop('disabled', true);
	$('#bGenerarExcelKo').css('color', '#6E6E6E');	
	$('#bValidarKo').prop('disabled', true);
	$('#bValidarKo').css('color', '#6E6E6E');	
	$('#idBCambiarEstadoKo').prop('disabled', true);
	$('#idBCambiarEstadoKo').css('color', '#6E6E6E');	
	$('#bDescargarExcel').prop('disabled', true);
	$('#bDescargarExcel').css('color', '#6E6E6E');	
}
function habilitarBotonesConsultaKo(){
	$("#idAltaKo").prop('disabled', false);
	$("#idAltaKo").css('color', '#000000');	
	$("#bGenerarExcelKo").prop('disabled', false);
	$("#bGenerarExcelKo").css('color', '#000000');	
	$("#bValidarKo").prop('disabled', false);
	$("#bValidarKo").css('color', '#000000');
	$("#idBCambiarEstadoKo").prop('disabled', false);
	$("#idBCambiarEstadoKo").css('color', '#000000');	
	$("#bDescargarExcel").prop('disabled', false);
	$("#bDescargarExcel").css('color', '#000000');

}
function resizeMeFechaIncPopUp(){
	var $cal = $('iframe:eq(1)', parent.document);
	var position = $("#anioIncidenciaId").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top+20) + "px"
	});
}

function limpiarConsulta(){
	$("#idMatricula").val("");
	$("#idTipoTramite").val("");
	$("#idTipo").val("");
	$("#idContrato").val("");
	$("#idTipoJefatura").val("");
	$("#idEstado").val("");
	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
}
function generarExcelKo(){
	var valueBoton = $("#bGenerarExcelKo").val();
	mostrarLoadingConsultaKo('bGenerarExcelKo');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingConsultaKo('bGenerarExcelKo',valueBoton);
		return alert("Debe seleccionar algún trámite para poder generar excel.");
	}
	
	if (confirm("¿Está seguro de que desea enviar excel de los trámites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarExcelConsMatrKo.action").trigger("submit");
	}else{
		ocultarLoadingConsultaKo('bGenerarExcelKo',valueBoton);
	}
}

function descargarExcel(){
	var valueBoton = $("#idBDescargExcel").val();
	mostrarLoadingConsultaKo('idBDescargExcel');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingConsultaKo('idBDescargExcel',valueBoton);
		return alert("Debe seleccionar algún trámite para poder descargar el excel.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarConsMatrKo").trigger("submit");
	ocultarLoadingConsultaKo('idBDescargExcel',valueBoton);
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

function abrirEvolucion(id, destino){
	if(id == null || id == ""){
		return alert("Debe seleccionar algún trámite para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvMatrKo.action?id=" + id;
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

function cambiarElementosPorPaginaEvolucionConsultaKo(){
	var $dest = $("#displayTagEvolucionConsultaKoDiv");
	$.ajax({
		url:"navegarEvolConsMatrKo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolConsultaKo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la evolucion.');
	    }
	});
}