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

function imprimirDocumentoAutoColegio(){
	var valueBoton = $("#idPDIImprimirAutoCole").val();
	mostrarLoadingGestionPDI('idPDIImprimirAutoCole');
	var codigos = "";
	var cont=0;
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
				cont++;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionPDI('idPDIImprimirAutoCole',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder descargar el documento.");
	}
	if(cont != "1"){
		ocultarLoadingGestionPDI('idPDIImprimirAutoCole',valueBoton);
		return alert("Solo se pueden imprimir los documentos de forma individual.");
	} else {
		if (confirm("\u00BFEst\u00E1 seguro de que desea imprimir los documentos en el colegio? Recuerde que una vez impresos en el Colegio debera pasar a recogerlos al mismo.")){
			var $dest = $("#divEmergenteConsultaDocumentos");
			$.post("cargarPopUpNumSerieGestionPDI.action?codSeleccionados="+codigos, function(data) {
				if (data.toLowerCase().indexOf("<html")<0) {
					$dest.empty().append(data).dialog({
						modal : true,
						show : {
							effect : "blind",
							duration : 300
						},
						dialogClass : 'no-close',
						width: 500,
						buttons : {
							Aceptar : function() {
								var sereIniT = $("#idserieInicial").val();
								var sereIniI = $("#idserieFinalI").val();
								var sereIniF = $("#idserieFinalF").val();
								var esPermisos = $("#esDocPermisos").val();
								if(esPermisos){
									if(sereIniT == "" || sereIniI == "" || sereIniF == ""){
										alert("Debe rellenar todos los valores");
										return false;
									}
									$("#divEmergenteConsultaDocumentos").dialog("close");
									limpiarHiddenInput("numeroSeriePermiso.serieInicial");
									input = $("<input>").attr("type", "hidden").attr("name", "numeroSeriePermiso.serieInicial").val(sereIniT);
									$('#formData').append($(input));
									limpiarHiddenInput("numeroSeriePermiso.serieFinalI");
									input = $("<input>").attr("type", "hidden").attr("name", "numeroSeriePermiso.serieFinalI").val(sereIniI);
									$('#formData').append($(input));
									limpiarHiddenInput("numeroSeriePermiso.serieFinalF");
									input = $("<input>").attr("type", "hidden").attr("name", "numeroSeriePermiso.serieFinalF").val(sereIniF);
									$('#formData').append($(input));
								}
								limpiarHiddenInput("codSeleccionados");
								input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
								$('#formData').append($(input));
								$("#formData").attr("action", "impresionColegioGestionPDI.action").trigger("submit");
								ocultarLoadingGestionPDI('idPDIImprimirAutoCole',valueBoton);
							},
							Cerrar : function() {
								ocultarLoadingGestionPDI('idPDIImprimirAutoCole',valueBoton);
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
	}
}

function imprimirDocumentoAuto(jefatura){
	var valueBoton = $("#idPDIImprimirAuto").val();
	mostrarLoadingGestionPDI('idPDIImprimirAuto');
	var codigos = "";
	var cont=0;
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos = this.value;
				cont++;
			}else{
				codigos = this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingGestionPDI('idPDIImprimirAuto',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder imprimirlo.");
	}
	if(cont > 1){
		ocultarLoadingGestionPDI('idPDIImprimirAuto',valueBoton);
		return alert("Solo se pueden imprimir los documentos de forma individual.");
	} else {
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		limpiarHiddenInput("jefaturaImpr");
		input = $("<input>").attr("type", "hidden").attr("name", "jefaturaImpr").val(jefatura);
		$('#formData').append($(input));
		$("#formData").attr("action", "impresionAutoGestionPDI.action").trigger("submit");
	}
}

function reactivarDocumentoJefatura(jefatura){
	var valueBoton = $("#idPDIReactivar").val();
	mostrarLoadingGestionPDI('idPDIReactivar');
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
		ocultarLoadingGestionPDI('idPDIReactivar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder reactivar.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea reactivar los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		limpiarHiddenInput("jefaturaImpr");
		input = $("<input>").attr("type", "hidden").attr("name", "jefaturaImpr").val(jefatura);
		$('#formData').append($(input));
		$("#formData").attr("action", "reactivarJefaturaGestionPDI.action").trigger("submit");
	}
}

function reiniciarImpr(){
	var valueBoton = $("#idBReiniciarImpr").val();
	mostrarLoadingGestionPDI('idBReiniciarImpr');
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
		ocultarLoadingGestionPDI('idBReiniciarImpr',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder reiniciar.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea reiniciar los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reiniciarImprGestionPDI.action").trigger("submit");
	}
}

function reactivarDocumento(){
	var valueBoton = $("#idPDIReactivar").val();
	mostrarLoadingGestionPDI('idPDIReactivar');
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
		ocultarLoadingGestionPDI('idPDIReactivar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder reactivar.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea reactivar los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reactivarGestionPDI.action").trigger("submit");
	}
}

function cambiarEstadoDocumento(){
	var valueBoton = $("#idPDICBEstado").val();
	mostrarLoadingGestionPDI("idPDICBEstado");
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
		ocultarLoadingGestionPDI('idPDICBEstado',valueBoton);
		return alert("Debe seleccionar algun documento para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaDocumentos");
	$.post("cargarPopUpCambioEstadoGestionPDI.action", function(data) {
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
						$("#divEmergenteConsultaDocumentos").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoGestionPDI.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingGestionPDI('idPDICBEstado',valueBoton);
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

function anularDocumento(){
	var valueBoton = $("#idPDIAnularCole").val();
	mostrarLoadingGestionPDI('idPDIAnularCole');
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
		ocultarLoadingGestionPDI('idPDIAnularCole',valueBoton);
		return alert("Debe seleccionar alg\u00FAn documento para poder anular.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea anular los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "anularGestionPDI.action").trigger("submit");
	}
}

function mostrarLoadingGestionPDI(boton){
	bloqueaBotonesPDI();
	document.getElementById("bloqueLoadingGestionPrmDstv").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingGestionPDI(boton, value) {
	document.getElementById("bloqueLoadingGestionPrmDstv").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesPDI();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function descargarDocumento(jefatura){
	var valueBoton = $("#idPDIDescargar").val();
	//mostrarLoadingGestionPDI('idPDIDescargar');
	var codigos = "";
	var cont=0;
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
				cont++;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		//ocultarLoadingGestionPDI('idPDIDescargar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder descargar el documento.");
	}
	if(cont > 1){
		//ocultarLoadingGestionPDI('idPDIDescargar',valueBoton);
		return alert("Solo se pueden descargar los documentos de forma individual.");
	} else {
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		limpiarHiddenInput("jefaturaImpr");
		input = $("<input>").attr("type", "hidden").attr("name", "jefaturaImpr").val(jefatura);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarGestionPDI.action").trigger("submit");
	//	ocultarLoadingGestionPrmDstv('idPDIDescargar',valueBoton);
	}
}

function imprimirDocumentoGestor(rol){
	var codigos = "";
	var cont=0;
	
	var checks = document.getElementsByName("listaChecks");
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
				pos=i;
			} else {
				codigos += ";" + checks[i].value;
			}
			cont++;
		}
		i++;
	}
	
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder descargar el documento.");
	}
	if(cont > 1){
		return alert("Solo se pueden descargar los documentos de forma individual.");
	} else {
		var checks = document.getElementsByName("listaChecks");
		var estado;
		if (rol == 'admin') {
			estado = document.getElementById("tablaGestionPrmDstvItv").rows[pos+1].cells[7].innerText;
		} else {
			estado = document.getElementById("tablaGestionPrmDstvItv").rows[pos+1].cells[4].innerText;
		}
		
		if (estado != "Generado") {
			alert("El documento seleccionado no se puede imprimir. Debe estar en estado Generado.");
			return false;
		}
		var url = "https://"+ jQuery(location).attr('host')+"/oegam2/jnlpServlet";
		
		document.formData.action = url + "?docId=" + codigos;
		document.formData.submit();
	}
}

function buscarGestionPDI(){
	var matricula = $("#idMatriculaPrmDstv").val();
	var numExpediente = $("#idNumExpedientePrmDstv").val();
	var tipoDocumento = $("#idTipoDocumento").val();
	if((matricula != "" || numExpediente != "") && tipoDocumento == ""){
		return alert("Tiene que seleccionar un tipo de documento para realizar la consulta.");
	}
	bloqueaBotonesPDI();
	$("#formData").attr("action", "buscarGestionPDI").trigger("submit");
}

function limpiarGestionPDI(){
	$("#idMatriculaPrmDstv").val("");
	$("#idBastidorPrmDstv").val("");
	$("#idNumExpedientePrmDstv").val("");
	$("#idEstadoPrmDstv").val("");
	$("#idNumSeriePermisoPrmDstv").val("");
	$("#idTipoDistintivoPrmDstv").val("");
	$("#idContratoPrmDstv").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#idPermisoPrmDstv").val("");
	$("#idDistintivoPrmDstv").val("");
	$("#idTipoDocumento").val("");
	$("#idDocId").val("");
	$("#idEitvPrmDstv").val("");
	$("#idEstado").val("");
	$("#idEstado").val("");
	$("#idJefaturaPrmDstv").val("");
}


function bloqueaBotonesPDI() {	
	$('#idPDIImprimir').prop('disabled', true);
	$('#idPDIImprimir').css('color', '#6E6E6E');	
	$('#idPDIDescargar').prop('disabled', true);
	$('#idPDIDescargar').css('color', '#6E6E6E');	
	$('#idPDICBEstado').prop('disabled', true);
	$('#idPDICBEstado').css('color', '#6E6E6E');
	$('#idPDIReactivar').prop('disabled', true);
	$('#idPDIReactivar').css('color', '#6E6E6E');
	$('#idPDIImprimirAutoCole').prop('disabled', true);
	$('#idPDIImprimirAutoCole').css('color', '#6E6E6E');
	$('#idBPDIConsultaPrmDstv').prop('disabled', true);
	$('#idBPDIConsultaPrmDstv').css('color', '#6E6E6E');
	$('#idBPDILimpiarPrmDstv').prop('disabled', true);
	$('#idBPDILimpiarPrmDstv').css('color', '#6E6E6E');
	$('#idPDIAnularCole').prop('disabled', true);
	$('#idPDIAnularCole').css('color', '#6E6E6E');
	$('#idBReiniciarImpr').prop('disabled', true);
	$('#idBReiniciarImpr').css('color', '#6E6E6E');
}

function desbloqueaBotonesPDI() {	
	$('#idBPDIConsultaPrmDstv').prop('disabled', false);
	$('#idBPDIConsultaPrmDstv').css('color', '#000000');	
	$('#idBPDILimpiarPrmDstv').prop('disabled', false);
	$('#idBPDILimpiarPrmDstv').css('color', '#000000');	
	$('#idPDIImprimir').prop('disabled', false);
	$('#idPDIImprimir').css('color', '#000000');	
	$('#idPDIDescargar').prop('disabled', false);
	$('#idPDIDescargar').css('color', '#000000');
	$('#idPDICBEstado').prop('disabled', false);
	$('#idPDICBEstado').css('color', '#000000');
	$('#idPDIReactivar').prop('disabled', false);
	$('#idPDIReactivar').css('color', '#000000');
	$('#idPDIImprimirAutoCole').prop('disabled', false);
	$('#idPDIImprimirAutoCole').css('color', '#000000');	
	$('#idPDIAnularCole').prop('disabled', false);
	$('#idPDIAnularCole').css('color', '#000000');
	$('#idBReiniciarImpr').prop('disabled', false);
	$('#idBReiniciarImpr').css('color', '#000000');
}


function cambiarElementosPorPaginaPDI(){
	var $dest = $("#displayTagDivPDI");
	$.ajax({
		url:"navegarGestionPDI.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaPDI").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los documentos.');
	    }
	});
}

function cambiarElementosPorPaginaDetalleDocumento(){
	var $dest = $("#displayTagDivDetalleDocPrmDstv");
	$.ajax({
		url:"navegarDetalleDocumento.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaDetalleDocPrmDstv").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los documentos.');
	    }
	});
}


function marcarTodosPDI(objCheck) {
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


function abrirEvolucionGestionDoc(docId,destino){
	if(docId == null || docId == ""){
		return alert("Debe seleccionar alg\u00FAn documento para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvoDocPrmDstvFicha.action?docId=" + docId;
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

function imprimirDocumento(jefatura){
	var valueBoton = $("#idPDIImprimir").val();
	mostrarLoadingGestionPDI('idPDIImprimir');
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
		ocultarLoadingGestionPDI('idPDIImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tr\u00E1mite para poder imprimir el documento seleccionado para generar.");
	}
	
	if (confirm("\u00BFEst\u00E1 seguro de que desea imprimir los documentos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		limpiarHiddenInput("jefaturaImpr");
		input = $("<input>").attr("type", "hidden").attr("name", "jefaturaImpr").val(jefatura);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirGestionPDI.action").trigger("submit");
	}
}
function volverDatosDocumentosGenerados(){
	$("#formData").attr("action", "inicioGestionPDI.action");
	$('#formData').submit();
}
