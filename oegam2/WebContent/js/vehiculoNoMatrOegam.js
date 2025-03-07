function guardarVehNoMatr(){
	habilitarCamposAltaVNMO();
	$("#formData").attr("action", "guardarAltaVehNo.action").trigger("submit");
}

function habilitarCamposAltaVNMO(){
	$("#idVNMO").prop('disabled', false);
	$("#idTipoDistintivoVNMO").prop('disabled', false);
	$("#idEstadoSolVNMO").prop('disabled', false);
	$("#idEstadoImpresionVNMO").prop('disabled', false);
	$("#idRespuestaSolVNMO").prop('disabled', false);
}

function volverVehNoMatr(){
	$("#formData").attr("action", "inicioConsultaVehNo.action");
	$('#formData').submit();
}

function altaVehiculoVNMO(){
	$("#formData").attr("action", "inicioAltaVehNo.action").trigger("submit");
}

function solicitarVehNoMatr(){
	deshabilitarBotonesVehNoMatr();
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el distintivo?")){
		habilitarCamposAltaVNMO();
		$("#formData").attr("action", "solicitarAltaVehNo.action").trigger("submit");
	} else {
		habilitarBotonesVehNoMatr();
	}
}

function autorizarVehNoMatr() {
	deshabilitarBotonesVehNoMatr();
	if (confirm("\u00BFEst\u00E1 seguro de que desea confirmar la primera impresión del duplicado de distintivo? Dicha impresion sera facturada como un distintivo normal no como un duplicado.")){
		habilitarCamposAltaVNMO();
		$("#formData").attr("action", "autorizarAltaVehNo.action").trigger("submit");
	} else {
		habilitarBotonesVehNoMatr();
	}
}

function generarVehNoMatr(){
	deshabilitarBotonesVehNoMatr();
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar la impresi\u00F3n del distintivo?")){
		habilitarCamposAltaVNMO();
		$("#formData").attr("action", "generarAltaVehNo.action").trigger("submit");
	} else {
		habilitarBotonesVehNoMatr();
	}
}

function deshabilitarBotonesVehNoMatr(){
	$("#idGuardarVehNoMatr").prop('disabled', true);
	$("#idGuardarVehNoMatr").css('color', '#6E6E6E');
	$("#idVolverVehNoMatr").prop('disabled', true);
	$("#idVolverVehNoMatr").css('color', '#6E6E6E');	
	$("#idSolicitarVehNoMatr").prop('disabled', true);
	$("#idSolicitarVehNoMatr").css('color', '#6E6E6E');
	$("#idSolImpresionVehNoMatr").prop('disabled', true);
	$("#idSolImpresionVehNoMatr").css('color', '#6E6E6E');	
	$("#idAutorizarVehNoMatr").prop('disabled', true);
	$("#idAutorizarVehNoMatr").css('color', '#6E6E6E');	
}

function buscarVehiculoNoMatOegam(){
	bloqueaBotonesDstv();
	$("#formData").attr("action", "buscarConsultaVehNo").trigger("submit");
}


function habilitarBotonesVehNoMatr(){
	$("#idGuardarVehNoMatr").prop('disabled', false);
	$("#idGuardarVehNoMatr").css("color","#000000");
	$("#idVolverVehNoMatr").prop('disabled', false);
	$("#idVolverVehNoMatr").css("color","#000000");
	$("#idSolicitarVehNoMatr").prop('disabled', false);
	$("#idSolicitarVehNoMatr").css("color","#000000");
	$("#idSolImpresionVehNoMatr").prop('disabled', false);
	$("#idSolImpresionVehNoMatr").css("color","#000000");
	$("#idAutorizarVehNoMatr").prop('disabled', false);
	$("#idAutorizarVehNoMatr").css("color","#000000");
}

function bloqueaBotonesDstv() {	
	$('#idBConsultaVeh').prop('disabled', true);
	$('#idBConsultaVeh').css('color', '#6E6E6E');	
	$('#idBLimpiarVeh').prop('disabled', true);
	$('#idBLimpiarVeh').css('color', '#6E6E6E');
	$('#idBAltaVNMO').prop('disabled', true);
	$('#idBAltaVNMO').css('color', '#6E6E6E');	
	$('#idGenerarDistVNMO').prop('disabled', true);
	$('#idGenerarDistVNMO').css('color', '#6E6E6E');	
	$('#idBSolDstvVNMO').prop('disabled', true);
	$('#idBSolDstvVNMO').css('color', '#6E6E6E');	
	$('#idBCmbEstadoSolDstvVNMO').prop('disabled', true);
	$('#idBCmbEstadoSolDstvVNMO').css('color', '#6E6E6E');
	$('#idGenerarDemandaDstvVNMO').prop('disabled', true);
	$('#idGenerarDemandaDstvVNMO').css('color', '#6E6E6E');
	$('#idRevertirDstvVNMO').prop('disabled', true);
	$('#idRevertirDstvVNMO').css('color', '#6E6E6E');
	$('#idbAutorizarDstvVNMO').prop('disabled', true);
	$('#idbAutorizarDstvVNMO').css('color', '#6E6E6E');
}

function desbloqueaBotonesDstv() {	
	$('#idBConsultaVeh').prop('disabled', false);
	$('#idBConsultaVeh').css('color', '#000000');	
	$('#idBLimpiarVeh').prop('disabled', false);
	$('#idBLimpiarVeh').css('color', '#000000');	
	$('#idBAltaVNMO').prop('disabled', false);
	$('#idBAltaVNMO').css('color', '#000000');	
	$('#idGenerarDistVNMO').prop('disabled', false);
	$('#idGenerarDistVNMO').css('color', '#000000');	
	$('#idBSolDstvVNMO').prop('disabled', false);
	$('#idBSolDstvVNMO').css('color', '#000000');
	$('#idBCmbEstadoSolDstvVNMO').prop('disabled', false);
	$('#idBCmbEstadoSolDstvVNMO').css('color', '#000000');
	$('#idGenerarDemandaDstvVNMO').prop('disabled', false);
	$('#idGenerarDemandaDstvVNMO').css('color', '#000000');
	$('#idRevertirDstvVNMO').prop('disabled', false);
	$('#idRevertirDstvVNMO').css('color', '#000000');
	$('#idbAutorizarDstvVNMO').prop('disabled', false);
	$('#idbAutorizarDstvVNMO').css('color', '#000000');
}

function limpiarConsultaDstv(){
	$("#idMatricula").val("");
	$("#idBastidor").val("");
	$("#idNive").val("");
	$("#idTipoDistintivoDstv").val("");
	$("#idContratoDstv").val("");
	$("#idDistintivoDstv").val("");
	$("#idEstadoPetDstv").val("");
	$("#idEstadoImpresion").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
}

function mostrarLoading(boton){
	bloqueaBotonesDstv();
	document.getElementById("bloqueLoadingConsultaDstv").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoading(boton, value) {
	document.getElementById("bloqueLoadingConsultaDstv").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesDstv();
}


function solicitarDstvBloqueVNMO(){
	var valueBoton = $("#idBSolDstvVNMO").val();
	mostrarLoading('idBSolDstvVNMO');
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoading('idBSolDstvVNMO',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso o el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el distintivo para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarDstvConsultaVehNo.action").trigger("submit");
	}else{
		ocultarLoading('idBSolDstvVNMO',valueBoton);
	}
}

function generarDstvVNMO(boton) {
	var valueBoton = $("#idGenerarDistVNMO").val();
	mostrarLoading('idGenerarDistVNMO');
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
		ocultarLoading('idGenerarDistVNMO',valueBoton);
		return alert("Debe seleccionar alguna matricula para poder generar el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento para las matriculas seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDistintivoConsultaVehNo.action").trigger("submit");
	}
	else{
		ocultarLoading('idGenerarDistVNMO',valueBoton);
	}
}

function autorizarDstvVNMO(){
	var valueBoton = $("#idbAutorizarDstvVNMO").val();
	mostrarLoading('idbAutorizarDstvVNMO');
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
		ocultarLoading('idbAutorizarDstvVNMO',valueBoton);
		return alert("Debe seleccionar alguna matricula para poder confirmar la impresion del distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea confirmar la primera impresión del duplicado de distintivo? Dicha impresion sera facturada como un distintivo normal no como un duplicado.")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "autorizarConsultaVehNo.action").trigger("submit");
	}
	else{
		ocultarLoading('idbAutorizarDstvVNMO',valueBoton);
	}
}

function generarDemandaDstvVNMO(boton) {
	var valueBoton = $("#idGenerarDemandaDstvVNMO").val();
	mostrarLoading('idGenerarDemandaDstvVNMO');
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
		ocultarLoading('idGenerarDemandaDstvVNMO',valueBoton);
		return alert("Debe seleccionar alguna matricula para poder generar sus distintivos.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento con las matriculas seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDemandaDstvConsultaVehNo.action").trigger("submit");
	}else{
		ocultarLoading('idGenerarDemandaDstvVNMO',valueBoton);
	}
	
}

function revertirDstvVNMO(){
	var valueBoton = $("#idRevertirDstvVNMO").val();
	mostrarLoading('idRevertirDstvVNMO');
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
		ocultarLoading('idRevertirDstvVNMO',valueBoton);
		return alert("Debe seleccionar alguna matricula para poder revertir el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea revertir los distintivos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "revertirConsultaVehNo.action").trigger("submit");
	}
	else{
		ocultarLoading('idRevertirDstvVNMO',valueBoton);
	}
}

function abrirDetalleVNMO(id){
	bloqueaBotonesDstv();
	limpiarHiddenInput("idVehNotMatOegam");
	input = $("<input>").attr("type", "hidden").attr("name", "idVehNotMatOegam").val(id);
	$('#formData').append($(input));
	$("#formData").attr("action", "detalleConsultaVehNo.action").trigger("submit");
}

function cambiarEstadoDstvBloqueVNMO(){
	var valueBoton = $("#idBCmbEstadoSolDstvVNMO").val();
	mostrarLoading("idBCmbEstadoSolDstvVNMO");
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoading('idBCmbEstadoSolDstvVNMO',valueBoton);
		return alert("Debe seleccionar alguna consulta para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaVehNoMat");
	$.post("cargarPopUpCambioEstadoConsultaVehNo.action", function(data) {
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
						$("#divEmergenteConsultaVehNoMat").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoDstvConsultaVehNo.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idBCmbEstadoSolDstvVNMO',valueBoton);
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

function abrirEvolucionVNMO(idVehNoMatOegam, destino){
		var $dest = $("#"+destino);
		var url = "cargarEvolucionVehNo.action?idVehNoMatOegam=" + idVehNoMatOegam;
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
function cambiarElementosPorPaginaConsultaEvolucionVehNoMatr(){
	
	var $dest = $("#displayTagEvolucionVehNoMatrDiv");
	$.ajax({
		url:"navegarEvolucionVehNo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolVehNoMatr").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con las evoluciones de los vehiculos.');
	    }
	});
}

function cambiarElementosPorPaginaConsultaVehNoMatr(){
	
	var $dest = $("#displayTagDivConsultaVehiculoNMO");
	$.ajax({
		url:"navegarConsultaVehNo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaVehNoMatr").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los distintivos de los vehiculos.');
	    }
	});
}

function marcarTodosDuplicadosDstv(objCheck) {
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

