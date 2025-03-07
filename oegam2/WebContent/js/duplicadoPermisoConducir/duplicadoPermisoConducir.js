function buscarIntervinienteDuplPermCond(){
	deshabilitarBotonesDuplPermCond();
	var nif = $('#idNifTitularDPC').val();
	if(nif != ""){
		var numColegiado = $("#numColegiadoDPC").val();
		if(numColegiado != null && numColegiado != ""){
			limpiarFormularioTitular();
			var $dest = $("#divTitularDuplPermCond", parent.document);
			$.ajax({
				url:"buscarTitularAjaxDPC.action#Titular",
				data:"nif="+ nif +"&numColegiado=" + numColegiado,
				type:'POST',
				success: function(data, xhr, status){
					$dest.empty().append(data);
					habilitarBotonesDuplPermCond();
				},
				error : function(xhr, status) {
			        alert('Ha sucedido un error a la hora de cargar el titular.');
			        habilitarBotonesDuplPermCond();
			    }
			});
		} else {
			alert("Para poder realizar la busqueda del titular debe tener número de colegiado.");
			habilitarBotonesDuplPermCond();
		}
	}else{
		alert("Para poder realizar la busqueda del titular debe rellenar el NIF/CIF.");
		habilitarBotonesDuplPermCond();
	}
}

function guardarDuplPermCond() {
	deshabilitarBotonesDuplPermCond();
	if (document.getElementById("idNifTitularDPC").value != "" && document.getElementById("idPrApeRzsSolicitanteDPC").value == "") {
		habilitarBotonesDuplPermCond();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	} 
	
	if((!validaEmail(document.getElementById("idEmailAUTE").value)) && (document.getElementById("idEmailAUTE").value.length > 0)){
		habilitarBotonesDuplPermCond();
		alert("El email AUTE introducido no es válido.\nPor favor repase los datos.");
		return false;
	}
	
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "guardarAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}

function validarDuplPermCond() {
	deshabilitarBotonesDuplPermCond();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "validarAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}

function tramitarDuplPermCond() {
	deshabilitarBotonesDuplPermCond();
	if (!confirm("El Gestor Administrativo se compromete a que ha revisado y verificado que la documentación que se aporta es correcta. ¿Desea continuar?")) {
		habilitarBotonesDuplPermCond();
		return false;
	}
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "tramitarAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}

function limpiarFormularioTitular(){
	var dniTitular = document.getElementById("idNifTitularDPC").value;
	if (dniTitular != null && dniTitular != "") {
		document.getElementById("tipoPersonaTitular").value = "-1";
		document.getElementById("idPrApeRzsSolicitanteDPC").value = "";
		document.getElementById("segundoApellidoTitular").value = "";
		document.getElementById("nombreTitular").value = "";
		document.getElementById("sexoTitular").value = "-1";
		document.getElementById("idProvinciaTitular").value = "-1";
		document.getElementById("idMunicipioTitular").length = 0;
		document.getElementById("idMunicipioTitular").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoTitular").value = "";
		document.getElementById("idPuebloTitular").length = 0;
		document.getElementById("idPuebloTitular").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoTitular").value = "";
		document.getElementById("cpTitular").value = "";
		document.getElementById("tipoViaTitular").length = 0;
		document.getElementById("tipoViaTitular").options[0] = new Option("Seleccione Tipo Via", "", true, true);
		document.getElementById("tipoViaSeleccionadaTitular").value = "";
		document.getElementById("nombreViaTitular").value = "";
		document.getElementById("numeroDireccionTitular").value = "";
		document.getElementById("letraDireccionTitular").value = "";
		document.getElementById("escaleraDireccionTitular").value = "";
		document.getElementById("pisoDireccionTitular").value = "";
		document.getElementById("puertaDireccionTitular").value = "";
		document.getElementById("bloqueDireccionTitular").value = "";
		document.getElementById("kmDireccionTitular").value = "";
		document.getElementById("hmDireccionTitular").value = "";
	}
}

function volverDuplPermCond(){
	deshabilitarBotonesDuplPermCond();
	$("#formData").attr("action", "inicioConsultaDuplPermCond").trigger("submit");
}

function deshabilitarBotonesDuplPermCond(){
	if (!$('#ibGuardarDuplPermCond').is(':hidden')){
		$("#ibGuardarDuplPermCond").css("color","#BDBDBD");
		$("#ibGuardarDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibFirmarDocDuplPermCond').is(':hidden')){
		$("#ibFirmarDocDuplPermCond").css("color","#BDBDBD");
		$("#ibFirmarDocDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibValidarDuplPermCond').is(':hidden')){
		$("#ibValidarDuplPermCond").css("color","#BDBDBD");
		$("#ibValidarDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibTramitarDuplPermCond').is(':hidden')){
		$("#ibTramitarDuplPermCond").css("color","#BDBDBD");
		$("#ibTramitarDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibGenMandatoTitDPC').is(':hidden')){
		$("#ibGenMandatoTitDPC").css("color","#BDBDBD");
		$("#ibGenMandatoTitDPC").prop("disabled",true);
	}
	if (!$('#ibEnviarDocumentacion').is(':hidden')){
		$("#ibEnviarDocumentacion").css("color","#BDBDBD");
		$("#ibEnviarDocumentacion").prop("disabled",true);
	}
	if (!$('#ibImprimirDuplPermCond').is(':hidden')){
		$("#ibImprimirDuplPermCond").css("color","#BDBDBD");
		$("#ibImprimirDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibVolverDuplPermCond').is(':hidden')){
		$("#ibVolverDuplPermCond").css("color","#BDBDBD");
		$("#ibVolverDuplPermCond").prop("disabled",true);
	}
	$("#bloqueBotones").show();
}

function habilitarBotonesDuplPermCond(){
	if (!$('#ibGuardarDuplPermCond').is(':hidden')){
		$("#ibGuardarDuplPermCond").css("color","#000000");
		$("#ibGuardarDuplPermCond").prop("disabled",false);
	}
	if (!$('#ibFirmarDocDuplPermCond').is(':hidden')){
		$("#ibFirmarDocDuplPermCond").css("color","#000000");
		$("#ibFirmarDocDuplPermCond").prop("disabled",false);
	}
	if (!$('#ibValidarDuplPermCond').is(':hidden')){
		$("#ibValidarDuplPermCond").css("color","#000000");
		$("#ibValidarDuplPermCond").prop("disabled",false);
	}
	if (!$('#ibTramitarDuplPermCond').is(':hidden')){
		$("#ibTramitarDuplPermCond").css("color","#000000");
		$("#ibTramitarDuplPermCond").prop("disabled",false);
	}
	if (!$('#ibGenMandatoTitDPC').is(':hidden')){
		$("#ibGenMandatoTitDPC").css("color","#BDBDBD");
		$("#ibGenMandatoTitDPC").prop("disabled",true);
	}
	if (!$('#ibEnviarDocumentacion').is(':hidden')){
		$("#ibEnviarDocumentacion").css("color","#BDBDBD");
		$("#ibEnviarDocumentacion").prop("disabled",true);
	}
	if (!$('#ibImprimirDuplPermCond').is(':hidden')){
		$("#ibImprimirDuplPermCond").css("color","#BDBDBD");
		$("#ibImprimirDuplPermCond").prop("disabled",true);
	}
	if (!$('#ibVolverDuplPermCond').is(':hidden')){
		$("#ibVolverDuplPermCond").css("color","#000000");
		$("#ibVolverDuplPermCond").prop("disabled",false);
	}
	$("#bloqueBotones").hide();
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

function bloqueaBotonesConsultaDPC(){
	$('#idBBuscarDPC').prop('disabled', true);
	$('#idBBuscarDPC').css('color', '#6E6E6E');
	$('#idBLimpiarDPC').prop('disabled', true);
	$('#idBLimpiarDPC').css('color', '#6E6E6E');
	$('#idDPCCambiarEstado').prop('disabled', true);
	$('#idDPCCambiarEstado').css('color', '#6E6E6E');
	$('#idDPCImprimir').prop('disabled', true);
	$('#idDPCImprimir').css('color', '#6E6E6E');
	$('#idDPCValidar').prop('disabled', true);
	$('#idDPCValidar').css('color', '#6E6E6E');
	$('#idDPCTramitar').prop('disabled', true);
	$('#idDPCTramitar').css('color', '#6E6E6E');
	$('#idDPCEliminar').prop('disabled', true);
	$('#idDPCEliminar').css('color', '#6E6E6E');
}

function desbloqueaBotonesConsultaDPC(){
	$('#idBBuscarDPC').prop('disabled', false);
	$('#idBBuscarDPC').css('color', '#000000');	
	$('#idBLimpiarDPC').prop('disabled', false);
	$('#idBLimpiarDPC').css('color', '#000000');	
	$('#idDPCCambiarEstado').prop('disabled', false);
	$('#idDPCCambiarEstado').css('color', '#000000');	
	$('#idDPCImprimir').prop('disabled', false);
	$('#idDPCImprimir').css('color', '#000000');	
	$('#idDPCValidar').prop('disabled', false);
	$('#idDPCValidar').css('color', '#000000');	
	$('#idDPCTramitar').prop('disabled', false);
	$('#idDPCTramitar').css('color', '#000000');	
	$('#idDPCEliminar').prop('disabled', false);
	$('#idDPCEliminar').css('color', '#000000');	
}

function buscarConsultaDPC(){
	bloqueaBotonesConsultaDPC();
	$("#formData").attr("action", "buscarConsultaDuplPermCond.action").trigger("submit");
}

function limpiarConsultaDPC(){
	$("#idNumExpDPC").val("");
	$("#idRefPropiaDPC").val("");
	$("#idNifTitularDPC").val("");
	$("#idEstadoDPC").val("");
	$("#idContratoDPC").val("");
	$("#diaFechaAltaIniDPC").val("");
	$("#mesFechaAltaIniDPC").val("");
	$("#anioFechaAltaIniDPC").val("");
	$("#diaFechaAltaFinDPC").val("");
	$("#mesFechaAltaFinDPC").val("");
	$("#anioFechaAltaFinDPC").val("");
	$("#diaFechaPrtIniDPC").val("");
	$("#mesFechaPrtIniDPC").val("");
	$("#anioFechaPrtIniDPC").val("");
	$("#diaFechaPrtFinDPC").val("");
	$("#mesFechaPrtFinDPC").val("");
	$("#anioFechaPrtFinDPC").val("");
	$("#idEstadoImpresionDPC").val("");
	$("#idJefaturaDPC").val("");
}

function mostrarBloqueActualizadosCPDC(){
	$("#bloqueFallidosCPDC").hide();
	if($("#bloqueActualizadosCPDC").is(":visible")){
		$("#bloqueActualizadosCPDC").hide();
		$("#despValidadoCPDC").attr("src","img/plus.gif");
		$("#despValidadoCPDC").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizadosCPDC").show();	
		$("#despValidadoCPDC").attr("src","img/minus.gif");
		$("#despValidadoCPDC").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidosCPDC(){
	$("#bloqueActualizadosCPDC").hide();
	if($("#bloqueFallidosCPDC").is(":visible")){
		$("#bloqueFallidosCPDC").hide();
		$("#despFallidosCPDC").attr("src","img/plus.gif");
		$("#despFallidosCPDC").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidosCPDC").show();	
		$("#despFallidosCPDC").attr("src","img/minus.gif");
		$("#despFallidosCPDC").attr("alt","Ocultar");
	}
}

function cambiarElementosPorPaginaConsultaDPC(){
	var $dest = $("#displayTagDivConsultaDPC");
	$.ajax({
		url:"navegarConsultaDuplPermCond.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaDPC").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los resultados.');
	    }
	});
}

function marcarTodosDPC(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksDPC) {
		if (!document.formData.listaChecksDPC.length) { 
			document.formData.listaChecksDPC.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksDPC.length; i++) {
			document.formData.listaChecksDPC[i].checked = marcar;
		}
	}
}

function mostrarLoadingConsultaDPC(boton){
	bloqueaBotonesConsultaDPC();
	document.getElementById("bloqueLoadingConsultaDPC").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaDPC(boton, value) {
	document.getElementById("bloqueLoadingConsultaDPC").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesConsultaDPC();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function validarConsultaDPC(){
	var valueBoton = $("#idDPCValidar").val();
	mostrarLoadingConsultaDPC('idDPCValidar');
	var codigos = "";
	$("input[name='listaChecksDPC']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaDPC('idDPCValidar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para validar.");
	} 
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "validarConsultaDuplPermCond.action").trigger("submit");
}

function tramitarConsultaDPC(){
	var valueBoton = $("#idDPCTramitar").val();
	mostrarLoadingConsultaDPC('idDPCTramitar');
	var codigos = "";
	$("input[name='listaChecksDPC']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaDPC('idDPCTramitar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para tramitar.");
	} else if(confirm("El Gestor Administrativo se compromete a que ha revisado y verificado que la documentación que se aporta es correcta. \u00BFEst\u00E1 seguro de que desea tramitar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "tramitarConsultaDuplPermCond.action").trigger("submit");
	} else {
		ocultarLoadingConsultaDPC('idDPCTramitar',valueBoton);
	}
}

function imprimirConsultaDPC(){
	var valueBoton = $("#idPIImprimir").val();
	var codigos = "";
	$("input[name='listaChecksDPC']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para imprimir.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea imprimir los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirConsultaDuplPermCond.action").trigger("submit");
	} else{
	} 
}

function eliminarConsultaDPC(){
	var valueBoton = $("#idDPCEliminar").val();
	mostrarLoadingConsultaDPC('idDPCEliminar');
	var codigos = "";
	$("input[name='listaChecksDPC']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaDPC('idDPCEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para eliminar.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea eliminar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaDuplPermCond.action").trigger("submit");
	} else {
		ocultarLoadingConsultaDPC('idDPCEliminar',valueBoton);
	}
}

function cargarVentanaCambioEstado(){
	var valueBoton = $("#idDPCCambiarEstado").val();
	mostrarLoadingConsultaDPC('idDPCCambiarEstado');
	var codigos = "";
	$("input[name='listaChecksDPC']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaDPC('idDPCCambiarEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaDPC");
	$.post("cargarPopUpCambioEstadoConsultaDuplPermCond.action", function(data) {
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
						var estadoNuevo = $("#idEstadoNuevoDPC").val();
						if(estadoNuevo == ""){
							alert("Seleccione un estado");
							return false;
						}
						$("#divEmergenteConsultaDPC").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaDuplPermCond.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaDPC('idDPCCambiarEstado',valueBoton);
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

function abrirEvolucionDuplPermCond(idDuplicadoPermisoConducir,destino){
	if(idDuplicadoPermisoConducir == null || idDuplicadoPermisoConducir == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvoDPCond.action?idDuplicadoPermisoConducir=" + idDuplicadoPermisoConducir;
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

function cambiarElementosPorPaginaEvoDPC(){
	var $dest = $("#displayTagEvoDPC");
	$.ajax({
		url:"navegarEvoDPCond.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolDPC").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los resultados.');
	    }
	});
	
}

function generarMandatoDPC() {
	deshabilitarBotonesDuplPermCond();
	document.formData.action="generarMandatoAltaDuplicadoPermisoConducir.action#Titular";
	document.formData.submit();
}

function descargarMandatoDPC() {
	if(confirm("Este fichero se borrará en 5 minutos una vez impreso ¿Continuar?")) {
		document.formData.action="descargarMandatoAltaDuplicadoPermisoConducir.action#Mandato";
		document.formData.submit();
	}
}

function eliminarMandatoDPC() {
	document.formData.action="eliminarMandatoAltaDuplicadoPermisoConducir.action#Mandato";
	document.formData.submit();
}

function descargarDocAportadaDPC(numExpedienteSel, nombreFichero, tipoDocumento){
	if(numExpedienteSel != null && numExpedienteSel != ""){
		limpiarHiddenInput("numExpedienteSel");
		limpiarHiddenInput("nombreFicheroSel");
		limpiarHiddenInput("tipoDocumentoSel");
		input = $("<input>").attr("type", "hidden").attr("name", "numExpedienteSel").val(numExpedienteSel);
		$('#formData').append($(input));
		input = $("<input>").attr("type", "hidden").attr("name", "nombreFicheroSel").val(nombreFichero);
		$('#formData').append($(input));
		input = $("<input>").attr("type", "hidden").attr("name", "tipoDocumentoSel").val(tipoDocumento);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarDocumentacionAltaDuplicadoPermisoConducir.action#Documentacion").trigger("submit");
	} else {
		return alert("No se ha podido descargar el fichero porque no se le ha indicado el expediente del tramite.");
	}
}

function eliminarDocAportadaDPC(numExpedienteSel, nombreFichero, tipoDocumento){
	if(numExpedienteSel != null && numExpedienteSel != ""){
		limpiarHiddenInput("numExpedienteSel");
		limpiarHiddenInput("nombreFicheroSel");
		limpiarHiddenInput("tipoDocumentoSel");
		input = $("<input>").attr("type", "hidden").attr("name", "numExpedienteSel").val(numExpedienteSel);
		$('#formData').append($(input));
		input = $("<input>").attr("type", "hidden").attr("name", "nombreFicheroSel").val(nombreFichero);
		$('#formData').append($(input));
		input = $("<input>").attr("type", "hidden").attr("name", "tipoDocumentoSel").val(tipoDocumento);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarDocAportadaAltaDuplicadoPermisoConducir.action#Documentacion").trigger("submit");
	} else {
		return alert("No se ha podido eliminar el fichero porque no se le ha indicado el expediente del tramite.");
	}
}

function subirDocDPC(){
	deshabilitarBotonesDuplPermCond();
	var tipoDocumento = $('#tipoDocumento').val();
	if(tipoDocumento != "") {
		$("#formData").attr("action", "subirDocAportadaAltaDuplicadoPermisoConducir.action#Documentacion").trigger("submit");
	} else{
		alert("Debe seleccionar el tipo de documento.");
		habilitarBotonesDuplPermCond();
	}
	
}

function imprimirDuplPermCond() {
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "imprimirAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}

function verTodasTasas(idContrato,destino){
	var $dest =  $("#"+destino);
	var contrato = $("#"+idContrato);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=4.4",
			type:'POST',
			success: function(data){
				if(data != ""){
					$dest.find('option').remove().end().append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
					var listTasas = data.split(";");
					$.each(listTasas,function(i,o){
						 $dest.append($('<option>', { 
						        value: listTasas[i],
						        text : listTasas[i]
						    }));
					 });
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar las tasas.');
		    }
		});
	}
}

function firmarDeclYSolicitudDuplCond() {
	deshabilitarBotonesDuplPermCond();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "firmarDeclYSolicitudAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}

function enviarDocumentacion() {
	deshabilitarBotonesDuplPermCond();
	if (!confirm("El Gestor Administrativo se compromete a que ha revisado y verificado que la documentación que se aporta es correcta. ¿Desea continuar?")) {
		habilitarBotonesDuplPermCond();
		return false;
	}
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "enviarDocumentacionAltaDuplicadoPermisoConducir.action#" + pestania;
	document.formData.submit();
}