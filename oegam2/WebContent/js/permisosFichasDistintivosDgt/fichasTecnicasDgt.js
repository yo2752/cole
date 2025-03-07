function  buscarGestionFchTc(){
	var nif = $("#idNifTitular").val();
	var tipoTramite = $("#idTipoTramite").val();
	$("#idTipoTramite").prop("disabled", false);
	if(nif != ""  && tipoTramite == ""){
		return alert("Tiene que seleccionar como tipo de trámite, MATRICULACIÓN, para realizar la consulta.");
	}
	bloqueaBotonesFchTcDstv();
	$("#formData").attr("action", "buscarGestionFchTc").trigger("submit");
}

function generarKOFchTc(){
	var valueBoton = $("#idBGenKOFchTc").val();
	mostrarLoadingGestionFchTcDstv('idBGenKOFchTc');
	var codigos = "";
	$("input[name='listaChecksFchTc']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	bloqueaBotonesFchTcDstv();
	if(codigos == ""){
		ocultarLoadingGestionFchTcDstv('idBGenKOFchTc',valueBoton);
		desbloqueaBotonesFchTcDstv();
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar su documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento con los expedientes seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarKOGestionFchTc.action").trigger("submit");
	}else {
		ocultarLoadingGestionFchTcDstv('idBGenKOFchTc',valueBoton);
		desbloqueaBotonesFchTcDstv();
	}
}

function limpiarGestionFchTc(){
	$("#idMatriculaFchTc").val("");
	$("#idNiveFchTc").val("");
	$("#idBastidorFchTc").val("");
	$("#idNumExpedienteFchTc").val("");
	$("#idNumSeriePermisoFchTc").val("");
	$("#idEstadoSolFchTc").val("");
	$("#idEstadoImpresionFchTc").val("");
	$("#idContratoFchTc").val("");
	$("#diaFechaPrtIniFchTc").val("");
	$("#mesFechaPrtIniFchTc").val("");
	$("#anioFechaPrtIniFchTc").val("");
	$("#diaFechaPrtFinFchTc").val("");
	$("#mesFechaPrtFinFchTc").val("");
	$("#anioFechaPrtFinFchTc").val("");
	$("#idTipoTramite").val("");
	$("#idNifTitular").val("");
	$("#idTipoJefaturaFichas").val("");
}

function marcarTodosFchTc(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksFchTc) {
		if (!document.formData.listaChecksFchTc.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksFchTc.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksFchTc.length; i++) {
			document.formData.listaChecksFchTc[i].checked = marcar;
		}
	}
}

function bloqueaBotonesFchTcDstv() {	
	$('#idBSolFchTcDstv').prop('disabled', true);
	$('#idBSolFchTcDstv').css('color', '#6E6E6E');	
	$('#idBDstvImprimir').prop('disabled', true);
	$('#idBDstvImprimir').css('color', '#6E6E6E');	
	$('#idBFchTcImprimir').prop('disabled', true);
	$('#idBFchTcImprimir').css('color', '#6E6E6E');
	$('#idBCmbEstadoSolFchTcDstv').prop('disabled', true);
	$('#idBCmbEstadoSolFchTcDstv').css('color', '#6E6E6E');
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
	$('#idBCbEstadoSolFchTc').prop('disabled', true);
	$('#idBCbEstadoSolFchTc').css('color', '#6E6E6E');
	$('#idBSolicitarFchTc').prop('disabled', true);
	$('#idBSolicitarFchTc').css('color', '#6E6E6E');
	$('#idBImprimirFchTc').prop('disabled', true);
	$('#idBImprimirFchTc').css('color', '#6E6E6E');
	$('#idBRevertirFchTc').prop('disabled', true);
	$('#idBRevertirFchTc').css('color', '#6E6E6E');
}

function desbloqueaBotonesFchTcDstv() {	
	$('#idBSolFchTcDstv').prop('disabled', false);
	$('#idBSolFchTcDstv').css('color', '#000000');	
	$('#idBDstvImprimir').prop('disabled', false);
	$('#idBDstvImprimir').css('color', '#000000');	
	$('#idBFchTcImprimir').prop('disabled', false);
	$('#idBFchTcImprimir').css('color', '#000000');	
	$('#idBCmbEstadoSolFchTcDstv').prop('disabled', false);
	$('#idBCmbEstadoSolFchTcDstv').css('color', '#000000');
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
	$('#idBCbEstadoSolFchTc').prop('disabled', false);
	$('#idBCbEstadoSolFchTc').css('color', '#000000');
	$('#idBSolicitarFchTc').prop('disabled', false);
	$('#idBSolicitarFchTc').css('color', '#000000');
	$('#idBImprimirFchTc').prop('disabled', false);
	$('#idBImprimirFchTc').css('color', '#000000');
	$('#idBRevertirFchTc').prop('disabled', false);
	$('#idBRevertirFchTc').css('color', '#000000');

}

function desasignarDocumentoFch(){
	var valueBoton = $("#idDesasignarDocumento").val();
	mostrarLoadingGestionFchTcDstv('idDesasignarDocumento');
	var codigos = "";
	$("input[name='listaChecksFchTc']:checked").each(function() {
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
		$("#formData").attr("action", "desasignarGestionFchTc.action").trigger("submit");
	}
}

function cambiarElementosPorPaginaGestionFchTc(){
	var $dest = $("#displayTagDivGestionFchTc");
	$.ajax({
		url:"navegarGestionFchTc.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaGestionFchTc").val(),
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

function mostrarLoadingGestionFchTcDstv(boton){
	bloqueaBotonesFchTcDstv();
	document.getElementById("bloqueLoadingGestionFchTc").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingGestionFchTcDstv(boton, value) {
	document.getElementById("bloqueLoadingGestionFchTc").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesFchTcDstv();
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

function solicitarFchTc(){
var valueBoton = $("#idBSolicitarFchTc").val();
mostrarLoadingGestionFchTcDstv('idBSolicitarFchTc');
var codigo  = getChecksMarcados('listaChecksFchTc');
var numero = getNumChecksSeleccionados('listaChecksFchTc');

if(codigo == ""){
	ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
	return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso.");
}
if (numero > 1){
	ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
	return alert('Solo se puede solicitar un trámite a la vez.');
}else{
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar las fichas para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigo);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarGestionFchTc.action").trigger("submit");
	}else{
		ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
	}
}



if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar las fichas t\u00E9cnicas para los tr\u00E1mites seleccionados?")){
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "solicitarGestionFchTc.action").trigger("submit");
}else{
	ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
}
}*/

function solicitarFchTc(){
var valueBoton = $("#idBSolicitarFchTc").val();
mostrarLoadingGestionFchTcDstv('idBSolicitarFchTc');
var codigos = "";
$("input[name='listaChecksFchTc']:checked").each(function() {
	if(this.checked){
		if(codigos==""){
			codigos += this.value;
		}else{
			codigos += "-" + this.value;
		}
	}
});

if(codigos == ""){
	ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
	return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso.");
}
if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar las fichas t\u00E9cnicas para los tr\u00E1mites seleccionados?")){
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "solicitarGestionFchTc.action").trigger("submit");
}else{
	ocultarLoadingGestionFchTcDstv('idBSolicitarFchTc',valueBoton);
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

function imprimirFchTc(){
	var valueBoton = $("#idBImprimirFchTc").val();
	mostrarLoadingGestionFchTcDstv('idBImprimirFchTc');
	var codigos = "";
	$("input[name='listaChecksFchTc']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionFchTcDstv('idPDIImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar la impresi\u00F3n del permiso.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar la impresi\u00F3n del las fichas t\u00E9cnicas seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirGestionFchTc.action").trigger("submit");
	}else {
		ocultarLoadingGestionFchTcDstv('idBImprimirFchTc',valueBoton);
	}
}

function revertirFchTc(){
	var valueBoton = $("#idBRevertirFchTc").val();
	mostrarLoadingGestionFchTcDstv('idBRevertirFchTc');
	var codigos = "";
	$("input[name='listaChecksFchTc']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionFchTcDstv('idBRevertirFchTc',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder revertir su documento.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea revertir los expedientes seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "revertirGestionFchTc.action").trigger("submit");
	}else {
		ocultarLoadingGestionFchTcDstv('idBRevertirFchTc',valueBoton);
	}
}

function cambiarEstadoSolFchTc(){
	var valueBoton = $("#idBCbEstadoSolFchTc").val();
	mostrarLoadingGestionFchTcDstv("idBCbEstadoSolFchTc");
	var codigos = "";
	$("input[name='listaChecksFchTc']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingGestionFchTcDstv('idBCbEstadoSolFchTc',valueBoton);
		return alert("Debe seleccionar alg\u00FAn expediente para cambiar su estado.");
	}
	var $dest = $("#divEmergenteGestionFchTc");
	$.post("cargarPopUpCambioEstadoGestionFchTc.action", function(data) {
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
						$("#divEmergenteGestionFchTc").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoGestionFchTc.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingGestionFchTcDstv('idBCbEstadoSolFchTc',valueBoton);
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

function abrirEvolucionFchTc(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alg\u00FAn expediente para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvolucionPrmDstvFicha.action?numExpediente=" + numExpediente + "&tipoDocumento=FCT";
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
