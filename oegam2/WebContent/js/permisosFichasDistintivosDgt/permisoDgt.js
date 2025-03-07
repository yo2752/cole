function  buscarGestionPrm(){
	var nif = $("#idNifTitular").val();
	var tipoTramite = $("#idTipoTramite").val();
	if(nif != ""  && tipoTramite == ""){
		return alert("Tiene que seleccionar un tipo de trámite para realizar la consulta.");
	}
	bloqueaBotonesPrmDstv();
	$("#formData").attr("action", "buscarGestionPrm").trigger("submit");
}

function limpiarGestionPrm(){
	$("#idMatriculaPrm").val("");
	$("#idNive").val("");
	$("#conNive").val("");
	$("#idBastidorPrm").val("");
	$("#idNumExpedientePrm").val("");
	$("#idNumSeriePermisoPrm").val("");
	$("#idEstadoSolPrm").val("");
	$("#idEstadoImpresionPrm").val("");
	$("#idContratoPrm").val("");
	$("#diaFechaPrtIniPrm").val("");
	$("#mesFechaPrtIniPrm").val("");
	$("#anioFechaPrtIniPrm").val("");
	$("#diaFechaPrtFinPrm").val("");
	$("#mesFechaPrtFinPrm").val("");
	$("#anioFechaPrtFinPrm").val("");
	$("#idTipoTramite").val("");
	$("#idNifTitular").val("");
	$("#idTipoJefatura").val("");
	$("#idTipoTransferencia").val("");
	activarTipTran();
}

function marcarTodosPrm(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksPrm) {
		if (!document.formData.listaChecksPrm.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksPrm.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksPrm.length; i++) {
			document.formData.listaChecksPrm[i].checked = marcar;
		}
	}
}

function bloqueaBotonesPrmDstv() {	
	$('#idBSolPrmDstv').prop('disabled', true);
	$('#idBSolPrmDstv').css('color', '#6E6E6E');	
	$('#idBDstvImprimir').prop('disabled', true);
	$('#idBDstvImprimir').css('color', '#6E6E6E');	
	$('#idBPrmImprimir').prop('disabled', true);
	$('#idBPrmImprimir').css('color', '#6E6E6E');
	$('#idBCmbEstadoSolPrmDstv').prop('disabled', true);
	$('#idBCmbEstadoSolPrmDstv').css('color', '#6E6E6E');
	$('#idBCmbEstadoSolEitv').prop('disabled', true);
	$('#idBCmbEstadoSolEitv').css('color', '#6E6E6E');
	$('#idBDescargarDstv').prop('disabled', true);
	$('#idBDescargarDstv').css('color', '#6E6E6E');
	$('#idIntroducirNumeroSerie').prop('disabled', true);
	$('#idIntroducirNumeroSerie').css('color', '#6E6E6E');
	$('#idPDIDescargar').prop('disabled', true);
	$('#idPDIDescargar').css('color', '#6E6E6E');	
	$('#idBEitv').prop('disabled', true);
	$('#idBEitv').css('color', '#6E6E6E');	
	$('#idGenerarPerm').prop('disabled', true);
	$('#idGenerarPerm').css('color', '#6E6E6E');	
	$('#idGenerarDist').prop('disabled', true);
	$('#idGenerarDist').css('color', '#6E6E6E');	
	$('#idGenerarDemandaDstv').prop('disabled', true);
	$('#idGenerarDemandaDstv').css('color', '#6E6E6E');	
	$('#idBCbEstadoSolPrm').prop('disabled', true);
	$('#idBCbEstadoSolPrm').css('color', '#6E6E6E');	
	$('#idBSolicitarPrm').prop('disabled', true);
	$('#idBSolicitarPrm').css('color', '#6E6E6E');	
	$('#idBImprimirPrm').prop('disabled', true);
	$('#idBImprimirPrm').css('color', '#6E6E6E');	
	$('#idBRevertirPrm').prop('disabled', true);
	$('#idBRevertirPrm').css('color', '#6E6E6E');	
	$('#idDesasignarDocumento').prop('disabled', true);
	$('#idDesasignarDocumento').css('color', '#6E6E6E');	

}

function desbloqueaBotonesPrmDstv() {	
	$('#idBSolPrmDstv').prop('disabled', false);
	$('#idBSolPrmDstv').css('color', '#000000');	
	$('#idBDstvImprimir').prop('disabled', false);
	$('#idBDstvImprimir').css('color', '#000000');	
	$('#idBPrmImprimir').prop('disabled', false);
	$('#idBPrmImprimir').css('color', '#000000');	
	$('#idBCmbEstadoSolPrmDstv').prop('disabled', false);
	$('#idBCmbEstadoSolPrmDstv').css('color', '#000000');
	$('#idBCmbEstadoSolEitv').prop('disabled', false);
	$('#idBCmbEstadoSolEitv').css('color', '#000000');
	$('#idBDescargarDstv').prop('disabled', false);
	$('#idBDescargarDstv').css('color', '#000000');
	$('#idIntroducirNumeroSerie').prop('disabled', false);
	$('#idIntroducirNumeroSerie').css('color', '#000000');
	$('#idPDIDescargar').prop('disabled', false);
	$('#idPDIDescargar').css('color', '#000000');	
	$('#idBEitv').prop('disabled', false);
	$('#idBEitv').css('color', '#000000');	
	$('#idGenerarPerm').prop('disabled', false);
	$('#idGenerarPerm').css('color', '#000000');
	$('#idGenerarDist').prop('disabled', false);
	$('#idGenerarDist').css('color', '#000000');
	$('#idGenerarDemandaDstv').prop('disabled', false);
	$('#idGenerarDemandaDstv').css('color', '#000000');
	$('#idBCbEstadoSolPrm').prop('disabled', false);
	$('#idBCbEstadoSolPrm').css('color', '#000000');	
	$('#idBSolicitarPrm').prop('disabled', false);
	$('#idBSolicitarPrm').css('color', '#000000');
	$('#idBImprimirPrm').prop('disabled', false);
	$('#idBImprimirPrm').css('color', '#000000');
	$('#idBRevertirPrm').prop('disabled', false);
	$('#idBRevertirPrm').css('color', '#000000');
	$('#idDesasignarDocumento').prop('disabled', false);
	$('#idDesasignarDocumento').css('color', '#000000');
}

function desasignarDocumentoPrm(){
	var valueBoton = $("#idDesasignarDocumento").val();
	mostrarLoadingGestionPrmDstv('idDesasignarDocumento');
	var codigos = "";
	$("input[name='listaChecksPrm']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionPrmDstv('idDesasignarDocumento',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder desasignar el id.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea desasignar los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarGestionPrm.action").trigger("submit");
	}
}
	
function cambiarElementosPorPaginaGestionPrm(){
	var $dest = $("#displayTagDivGestionPrm");
	$.ajax({
		url:"navegarGestionPrm.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaGestionPrm").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los expedientes.');
	    }
	});
}

function mostrarLoadingGestionPrmDstv(boton){
	bloqueaBotonesPrm();
	document.getElementById("bloqueLoadingGestionPrm").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingGestionPrmDstv(boton, value) {
	document.getElementById("bloqueLoadingGestionPrm").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesPrmDstv();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

/*function getChecksMarcados(name){
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
function getNumChecksSeleccionados(name){
var numMarcados = 0;
$("input[name='"+name+"']:checked").each(function() {
	if(this.checked){
		numMarcados++;
	}
});
return numMarcados;
}

function solicitarPrm(){
var valueBoton = $("#idBSolicitarPrm").val();
mostrarLoadingGestionPrmDstv('idBSolicitarPrm');
var codigo  = getChecksMarcados('listaChecksPrm');
var numero = getNumChecksSeleccionados('listaChecksPrm');

if(codigo == ""){
	ocultarLoadingGestionPrmDstv('idBSolicitarPrm',valueBoton);
	return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso.");
}
if (numero > 1){
	ocultarLoadingGestionPrmDstv('idBSolicitarPrm',valueBoton);
	return alert('Solo se puede solicitar un trámite a la vez.');
}else{
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el permiso para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigo);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarGestionPrm.action").trigger("submit");
	}else{
		ocultarLoadingGestionPrmDstv('idBSolicitarPrm',valueBoton);
	}
}
}*/
function solicitarPrm(){
var valueBoton = $("#idBSolicitarPrm").val();
mostrarLoadingGestionPrmDstv('idBSolicitarPrm');
var codigos = "";
$("input[name='listaChecksPrm']:checked").each(function() {
	if(this.checked){
		if(codigos==""){
			codigos += this.value;
		}else{
			codigos += "-" + this.value;
		}
	}
});

if(codigos == ""){
	ocultarLoadingGestionPrmDstv('idBSolicitarPrm',valueBoton);
	return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso.");
}
if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el permiso para los tr\u00E1mites seleccionados?")){
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "solicitarGestionPrm.action").trigger("submit");
}else{
	ocultarLoadingGestionPrmDstv('idBSolicitarPrm',valueBoton);
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

function imprimirPrm(){
	var valueBoton = $("#idBImprimirPrm").val();
	mostrarLoadingGestionPrmDstv('idBImprimirPrm');
	var codigos = "";
	$("input[name='listaChecksPrm']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionPrmDstv('idPDIImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar la impresi\u00F3n del permiso.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar la impresi\u00F3n del los permisos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirGestionPrm.action").trigger("submit");
	}else {
		ocultarLoadingGestionPrmDstv('idBImprimirPrm',valueBoton);
	}
}

function revertirPrm(){
	var valueBoton = $("#idBRevertirPrm").val();
	mostrarLoadingGestionPrmDstv('idBRevertirPrm');
	var codigos = "";
	$("input[name='listaChecksPrm']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionPrmDstv('idBRevertirPrm',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder revertir su documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea revertir los expedientes seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "revertirGestionPrm.action").trigger("submit");
	}else {
		ocultarLoadingGestionPrmDstv('idBRevertirPrm',valueBoton);
	}
}

function generarKOPrm(){
	var valueBoton = $("#idBGenKOPrm").val();
	mostrarLoadingGestionPrmDstv('idBGenKOPrm');
	var codigos = "";
	$("input[name='listaChecksPrm']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	bloqueaBotonesPrm();
	if(codigos == ""){
		ocultarLoadingGestionPrmDstv('idBGenKOPrm',valueBoton);
		desbloqueaBotonesPrmDstv();
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar su documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento con los expedientes seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarKOGestionPrm.action").trigger("submit");
	}else {
		ocultarLoadingGestionPrmDstv('idBGenKOPrm',valueBoton);
		desbloqueaBotonesPrmDstv()
	}
}

function bloqueaBotonesPrm() {	
	$('#idBSolicitarPrm').prop('disabled', true);
	$('#idBSolicitarPrm').css('color', '#6E6E6E');	
	$('#idBImprimirPrm').prop('disabled', true);
	$('#idBImprimirPrm').css('color', '#6E6E6E');	
	$('#idBRevertirPrm').prop('disabled', true);
	$('#idBRevertirPrm').css('color', '#6E6E6E');
	$('#idBGenKOPrm').prop('disabled', true);
	$('#idBGenKOPrm').css('color', '#6E6E6E');
	$('#idDesasignarDocumento').prop('disabled', true);
	$('#idDesasignarDocumento').css('color', '#6E6E6E');

}

function desbloqueaBotonesPrmDstv() {	
	$('#idBSolicitarPrm').prop('disabled', false);
	$('#idBSolicitarPrm').css('color', '#000000');	
	$('#idBImprimirPrm').prop('disabled', false);
	$('#idBImprimirPrm').css('color', '#000000');	
	$('#idBRevertirPrm').prop('disabled', false);
	$('#idBRevertirPrm').css('color', '#000000');	
	$('#idBGenKOPrm').prop('disabled', false);
	$('#idBGenKOPrm').css('color', '#000000');
	$('#idDesasignarDocumento').prop('disabled', false);
	$('#idDesasignarDocumento').css('color', '#000000');
}

function cambiarEstadoSolPrm(){
	var valueBoton = $("#idBCbEstadoSolPrm").val();
	mostrarLoadingGestionPrmDstv("idBCbEstadoSolPrm");
	var codigos = "";
	$("input[name='listaChecksPrm']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionPrmDstv('idBCbEstadoSolPrm',valueBoton);
		return alert("Debe seleccionar alg\u00FAn expediente para cambiar su estado.");
	}
	var $dest = $("#divEmergenteGestionPrm");
	$.post("cargarPopUpCambioEstadoGestionPrm.action", function(data) {
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
						$("#divEmergenteGestionPrm").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoGestionPrm.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingGestionPrmDstv('idBCbEstadoSolPrm',valueBoton);
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

function abrirEvolucionPrm(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alg\u00FAn expediente para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvolucionPrmDstvFicha.action?numExpediente=" + numExpediente + "&tipoDocumento=PC";
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

function activarTipTran(){
	var tipoTramite = $("#idTipoTramite").val();
	if(tipoTramite == 'T7'){
		$("#comboTipoTrans").show();
		$("#lblTipoTrans").show();
	}else{
		$("#comboTipoTrans").css("display","none");
		$("#lblTipoTrans").css("display","none");
	}
}

