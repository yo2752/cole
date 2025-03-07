function buscarConsultarDistintivo(){
	bloqueaBotonesDstv();
	$("#formData").attr("action", "buscarGestionDstv").trigger("submit");
}

function buscarGestionPDI(){
	var matricula = $("#idMatriculaPrmDstv").val();
	var numExpediente = $("#idNumExpedientePrmDstv").val();
	var tipoDocumento = $("#idTipoDocumento").val();
	if((matricula != "" || numExpediente != "") && tipoDocumento == ""){
		return alert("Tiene que seleccionar un tipo de documento para realizar la consulta.");
	}
	bloqueaBotonesPrmDstv();
	$("#formData").attr("action", "buscarGestionPDI").trigger("submit");
}


function limpiarConsultaDstv(){
	$("#idMatriculaDstv").val("");
	$("#idBastidorDstv").val("");
	$("#idNumExpedienteDstv").val("");
	$("#idEstadoPetDstv").val("");
	$("#idNumSeriePermisoDstv").val("");
	$("#idTipoDistintivoDstv").val("");
	$("#idContratoDstv").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#idPermisoDstv").val("");
	$("#idDistintivoDstv").val("");
	$("#idTipoDocumento").val("");
	$("#idDocId").val("");
	$("#idEstadoPetDstv").val("");
	$("#idEstadoImpresion").val("");
	$("#idNifTitularDstv").val("");
	$("#idRefPropiaDstv").val("");
}

function bloqueaBotonesGestionPrmDstv(){
	$('#idPDIDescargar').prop('disabled', true);
	$('#idPDIDescargar').css('color', '#6E6E6E');	
	$('#idPDIImprimir').prop('disabled', true);
	$('#idPDIImprimir').css('color', '#6E6E6E');
	$('#idBPDIConsultaPrmDstv').prop('disabled', true);
	$('#idBPDIConsultaPrmDstv').css('color', '#6E6E6E');
	$('#idBPDILimpiarPrmDstv').prop('disabled', true);
	$('#idBPDILimpiarPrmDstv').css('color', '#6E6E6E');
}

function desbloqueaBotonesGestionPrmDstv() {	
	$('#idPDIDescargar').prop('disabled', false);
	$('#idPDIDescargar').css('color', '#000000');	
	$('#idPDIImprimir').prop('disabled', false);
	$('#idPDIImprimir').css('color', '#000000');	
	$('#idBPDIConsultaPrmDstv').prop('disabled', false);
	$('#idBPDIConsultaPrmDstv').css('color', '#000000');
	$('#idBPDILimpiarPrmDstv').prop('disabled', false);
	$('#idBPDILimpiarPrmDstv').css('color', '#000000');
}

function bloqueaBotonesDstv() {	
	$('#idBSolDstv').prop('disabled', true);
	$('#idBSolDstv').css('color', '#6E6E6E');	
	$('#idBDstvImprimir').prop('disabled', true);
	$('#idBDstvImprimir').css('color', '#6E6E6E');	
	$('#idBCmbEstadoSolDstv').prop('disabled', true);
	$('#idBCmbEstadoSolDstv').css('color', '#6E6E6E');
	$('#idBDescargarDstv').prop('disabled', true);
	$('#idBDescargarDstv').css('color', '#6E6E6E');
	$('#idIntroducirNumeroSerie').prop('disabled', true);
	$('#idIntroducirNumeroSerie').css('color', '#6E6E6E');
	$('#idPDIDescargar').prop('disabled', true);
	$('#idPDIDescargar').css('color', '#6E6E6E');	
	$('#idGenerarDist').prop('disabled', true);
	$('#idGenerarDist').css('color', '#6E6E6E');	
	$('#idGenerarDemandaDstv').prop('disabled', true);
	$('#idGenerarDemandaDstv').css('color', '#6E6E6E');	
	$('#idModifVeh').prop('disabled', true);
	$('#idModifVeh').css('color', '#6E6E6E');
	$('#idBotonActualizar').prop('disabled', true);
	$('#idBotonActualizar').css('color', '#6E6E6E');
	$('#idRevertirDstv').prop('disabled', true);
	$('#idRevertirDstv').css('color', '#6E6E6E');
	$('#idDesasignarDocumento').prop('disabled', true);
	$('#idDesasignarDocumento').css('color', '#6E6E6E');
}

function desbloqueaBotonesDstv() {	
	$('#idBSolDstv').prop('disabled', false);
	$('#idBSolDstv').css('color', '#000000');	
	$('#idBDstvImprimir').prop('disabled', false);
	$('#idBDstvImprimir').css('color', '#000000');	
	$('#idBPrmImprimir').prop('disabled', false);
	$('#idBPrmImprimir').css('color', '#000000');	
	$('#idBCmbEstadoSolDstv').prop('disabled', false);
	$('#idBCmbEstadoSolDstv').css('color', '#000000');
	$('#idBDescargarDstv').prop('disabled', false);
	$('#idBDescargarDstv').css('color', '#000000');
	$('#idIntroducirNumeroSerie').prop('disabled', false);
	$('#idIntroducirNumeroSerie').css('color', '#000000');
	$('#idPDIDescargar').prop('disabled', false);
	$('#idPDIDescargar').css('color', '#000000');	
	$('#idGenerarDist').prop('disabled', false);
	$('#idGenerarDist').css('color', '#000000');
	$('#idGenerarDemandaDstv').prop('disabled', false);
	$('#idGenerarDemandaDstv').css('color', '#000000');
	$('#idModifVeh').prop('disabled', false);
	$('#idModifVeh').css('color', '#000000');
	$('#idBotonActualizar').prop('disabled', false);
	$('#idBotonActualizar').css('color', '#000000');
	$('#idRevertirDstv').prop('disabled', false);
	$('#idRevertirDstv').css('color', '#000000');
	$('#idDesasignarDocumento').prop('disabled', false);
	$('#idDesasignarDocumento').css('color', '#000000');
	
}

function mostrarLoading(boton){
	bloqueaBotonesDstv();
	document.getElementById("bloqueLoadingConsultaDstv").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function mostrarLoadingGestionPrmDstv(boton){
	bloqueaBotonesGestionPrmDstv();
	document.getElementById("bloqueLoadingGestionPrmDstv").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoading(boton, value) {
	document.getElementById("bloqueLoadingConsultaDstv").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesDstv();
}

function ocultarLoadingGestionPrmDstv(boton, value) {
	document.getElementById("bloqueLoadingGestionPrmDstv").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesGestionPrmDstv();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function solicitarDstvBloque(){
	var valueBoton = $("#idBSolDstv").val();
	mostrarLoading('idBSolDstv');
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
		ocultarLoading('idBSolDstv',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder solicitar el permiso o el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar el permiso o el distintivo para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarDstvGestionDstv.action").trigger("submit");
	}else{
		ocultarLoading('idBSolDstv',valueBoton);
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

function mostrarBloqueOKMasivas(){
	$("#bloqueKo").hide();
	if($("#bloqueOk").is(":visible")){
		$("#bloqueOk").hide();
		$("#despValidadoOk").attr("src","img/plus.gif");
		$("#despValidadoOk").attr("alt","Mostrar");
	}else{
		$("#bloqueOk").show();	
		$("#despValidadoOk").attr("src","img/minus.gif");
		$("#despValidadoOk").attr("alt","Ocultar");
	}
}

function mostrarBloqueKOMasivas(){
	$("#bloqueOk").hide();
	if($("#bloqueKo").is(":visible")){
		$("#bloqueKo").hide();
		$("#despFallidosKo").attr("src","img/plus.gif");
		$("#despFallidosKo").attr("alt","Mostrar");
	}else{
		$("#bloqueKo").show();	
		$("#despFallidosKo").attr("src","img/minus.gif");
		$("#despFallidosKo").attr("alt","Ocultar");
		
	}
}

function mostrarBloqueOKImportacion(){
	$("#bloqueKoImportacion").hide();
	if($("#bloqueOkImportacion").is(":visible")){
		$("#bloqueOkImportacion").hide();
		$("#despValidadoOkImpor").attr("src","img/plus.gif");
		$("#despValidadoOkImpor").attr("alt","Mostrar");
	}else{
		$("#bloqueOkImportacion").show();	
		$("#despValidadoOkImpor").attr("src","img/minus.gif");
		$("#despValidadoOkImpor").attr("alt","Ocultar");
	}
}

function mostrarBloqueKOImportacion(){
	$("#bloqueOkImportacion").hide();
	if($("#bloqueKoImportacion").is(":visible")){
		$("#bloqueKoImportacion").hide();
		$("#despFallidosKoImpor").attr("src","img/plus.gif");
		$("#despFallidosKoImpor").attr("alt","Mostrar");
	}else{
		$("#bloqueKoImportacion").show();	
		$("#despFallidosKoImpor").attr("src","img/minus.gif");
		$("#despFallidosKoImpor").attr("alt","Ocultar");
		
	}
}

function importarDupDstv(){
	if($("#idFicheroDupDstv").val() == null || $("#idFicheroDupDstv").val() == ""){
		alert("Debe de seleccionar un fichero para poder solicitar los distintivos.");
	} else if($("#idSolicitudDstv").is(':checked') || $("#idGenerarDstv").is(':checked')){
		alert("Debe de seleccionar algun tipo de operacion para los distintivos.");
	} else {
		$("#formData").attr("action", "importarImportarDupDstv.action").trigger("submit");
	}
}

function solicitarMasivaDstv(){
	if($("#idFicheroMasvDstv").val() == null || $("#idFicheroMasvDstv").val() == ""){
		alert("Debe de seleccionar un fichero para poder importar los distintivos.")
	} else {
		$("#formData").attr("action", "altaSolicitarMasivoDstv.action").trigger("submit");
	}
}

function imprimirDocumento(){
	var valueBoton = $("#idPDIImprimir").val();
	mostrarLoadingGestionPrmDstv('idPDIImprimir');
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
		ocultarLoadingGestionPrmDstv('idPDIImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder imprimir el documento seleccionado para generar.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea imprimir los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirGestionPDI.action").trigger("submit");
	}else {
		ocultarLoadingGestionPrmDstv('idPDIImprimir',valueBoton);
	}
}

function descargarDocumento(){
	var valueBoton = $("#idPDIDescargar").val();
	mostrarLoadingGestionPrmDstv('idPDIDescargar');
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
		ocultarLoadingGestionPrmDstv('idPDIDescargar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder descargar el documento.");
	}
	
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarGestionPDI.action").trigger("submit");
	ocultarLoadingGestionPrmDstv('idPDIDescargar',valueBoton);
	
}

function generarDistintivo(boton) {
	var valueBoton = $("#idGenerarDist").val();
	mostrarLoading('idGenerarDist');
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
		ocultarLoading('idGenerarDist',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar el documento del trámite seleccionado.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDistintivoGestionDstv.action").trigger("submit");
	}
	else{
		ocultarLoading('idGenerarDist',valueBoton);
	}
}

function generarDemandaDstv(boton) {
	var valueBoton = $("#idGenerarDemandaDstv").val();
	mostrarLoading('idGenerarDemandaDstv');
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
		ocultarLoading('idGenerarDemandaDstv',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder generar el documento del trámite seleccionado.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea generar el documento para los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "generarDemandaDstvGestionDstv.action").trigger("submit");
	}else{
		ocultarLoading('idGenerarDemandaDstv',valueBoton);
	}
	
}

function imprimirDstvBloque(){
	var valueBoton = $("#idBDstvImprimir").val();
	mostrarLoading('idBDstvImprimir');
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
		ocultarLoading('idBDstvImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder imprimir el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea imprimir los distintivos de los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirDstvConsultaPDI.action").trigger("submit");
	}else{
		ocultarLoading('idBDstvImprimir',valueBoton);
	}
}

function descargarDistintivosMasivos(){
	var valueBoton = $("#idBDescargarDstv").val();
	mostrarLoading('idBDescargarDstv');
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
		ocultarLoading('idBDescargarDstv',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder descargar su distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea descargar los distintivos de los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarImpresionConsultaPDI.action").trigger("submit");
	}
	ocultarLoading('idBDescargarDstv',valueBoton);
}

function cambiarEstadoDstvBloque(){
	var valueBoton = $("#idBCmbEstadoSolDstv").val();
	mostrarLoading("idBCmbEstadoSolDstv");
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
		ocultarLoading('idBCmbEstadoSolDstv',valueBoton);
		return alert("Debe seleccionar alguna consulta para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaDistintivo");
	$.post("cargarPopUpCambioEstadoGestionDstv.action", function(data) {
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
						$("#divEmergenteConsultaDistintivo").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoDstvGestionDstv.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idBCmbEstadoSolDstv',valueBoton);
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

function desasignarDocumentoDstv(){
	var valueBoton = $("#idDesasignarDocumento").val();
	mostrarLoading('idDesasignarDocumento');
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
		ocultarLoading('idDesasignarDocumento',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder desasignar el id.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea desasignar los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarGestionDstv.action").trigger("submit");
	}
}

function marcarTodosDstv(objCheck) {
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

function marcarTodosPrmDstvItv(objCheck) {
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


function cambiarElementosPorPaginaConsultaDstv(){
	var $dest = $("#displayTagDivConsultaDstv");
	$.ajax({
		url:"navegarGestionDstv.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsDstv").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los expedientes.');
	    }
	});
}

function cambiarElementosPorPaginaConsultaPrmDstvItv(){
	var $dest = $("#displayTagDivConsultaDstv");
	$.ajax({
		url:"navegarGestionDstv.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsDstv").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones de la gestión de distintivos.');
	    }
	});
}

function introducirNumeroSerie(){
	var valueBoton = $("#idIntroducirNumeroSerie").val();
	mostrarLoading('idIntroducirNumeroSerie');
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
	if(codigos == "" ){
		ocultarLoading('idIntroducirNumeroSerie',valueBoton);
		return alert("Debe seleccionar alguna consulta para cambiar su estado.");
	}
	if(codigos.split("-").length > '1'){
		ocultarLoading('idIntroducirNumeroSerie',valueBoton);
		return alert("Solo se puede seleccionar un expediente.");
	}
	
	var $dest = $("#popUpNumeroSerie");
	$.post("cargarPopUpNumeroSerieConsultaPDI.action", function(data) {
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
					Introducir : function() {
						var estadoNuevo = $("#idNumeroSerie").val();
						if(estadoNuevo == ""){
							alert("Introduzca un valor para el numero de serie");
							return false;
						}
						$("#popUpNumeroSerie").dialog("close");
						limpiarHiddenInput("numeroSerie");
						input = $("<input>").attr("type", "hidden").attr("name", "numeroSerie").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "introNumSerieConsultaPDI.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idIntroducirNumeroSerie',valueBoton);
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
function modificarVehiculo(boton){
	var valueBoton = $("#idModifVeh").val();
	mostrarLoading('idModifVeh');
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
		ocultarLoading('idModifVeh',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder modificar los datos del vehiculo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea modificar los datos del vehiculo para el expediente seleccionado?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "modificarVehiculoGestionDstv.action").trigger("submit");
	}else{
		ocultarLoading('idModifVeh',valueBoton);
	}
}
function actualizarVehiculo(boton) {
	var idVehiculo = $("#idVehiculoTextDstv").val();
	limpiarHiddenInput("idVehiculoTextDstv");
	input = $("<input>").attr("type", "hidden").attr("name", "idVehiculoTextDstv").val(idVehiculo);
	$('#formData').append($(input));
	$("#formData").attr("action", "actualizarVehiculoConsultaPDI.action").trigger("submit");

}
function abrirEvolucionDstv(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alg\u00FAn expediente para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvolucionPrmDstvFicha.action?numExpediente=" + numExpediente + "&tipoDocumento=DSTV";
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

function revertirDstv(){
	var valueBoton = $("#idRevertirDstv").val();
	mostrarLoading('idRevertirDstv');
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
		ocultarLoading('idRevertirDstv',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder revertir el distintivo.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea revertir los distintivos de los tr\u00E1mites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "revertirGestionDstv.action").trigger("submit");
	}
	else{
		ocultarLoading('idRevertirDstv',valueBoton);
	}
}



