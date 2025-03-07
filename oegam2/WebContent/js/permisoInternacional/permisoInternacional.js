function buscarIntervinientePermInter(){
	deshabilitarBotonesPermInter();
	var nif = $('#idNifTitularPI').val();
	if(nif != ""){
		var numColegiado = $("#numColegiadoPI").val();
		var cambioDomicilio;
		if($("#cambioDomicilio").is(':checked') == false){
			cambioDomicilio = false;
		} else {
			cambioDomicilio = true;
		}
		if(numColegiado != null && numColegiado != ""){
			limpiarFormularioTitular();
			var $dest = $("#divTitularPermInter", parent.document);
			$.ajax({
				url:"buscarTitularAjaxPIT.action#Titular",
				data:"nif="+ nif +"&numColegiado=" + numColegiado + "&cambioDomicilio=" + cambioDomicilio,
				type:'POST',
				success: function(data, xhr, status){
					$dest.empty().append(data);
					habilitarBotonesPermInter();
				},
				error : function(xhr, status) {
			        alert('Ha sucedido un error a la hora de cargar el titular.');
			        habilitarBotonesPermInter();
			    }
			});
		} else {
			alert("Para poder realizar la busqueda del titular debe tener número de colegiado.");
			habilitarBotonesPermInter();
		}
	}else{
		alert("Para poder realizar la busqueda del titular debe rellenar el NIF/CIF.");
		habilitarBotonesPermInter();
	}
}

function guardarPermisoInternacional() {
	deshabilitarBotonesPermInter();
	if (document.getElementById("idNifTitularPI").value != "" && document.getElementById("idPrApeRzsSolicitantePI").value == "") {
		habilitarBotonesPermInter();
		alert("Ha a\u00f1adido NIF del Titular, Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	} else {
		if($("#cambioDomicilio").is(':checked') == true){
			if (!confirm("El check de cambio de domicilio del titular está marcado, recuerde que se realizará un cambio de domicilio en DGT. Deberá estar autorizado para realizar dicha acción y quedará bajo la responsabilidad del Gestor Administrativo Colegiado. ¿Desea continuar?")) {
				habilitarBotonesPermInter();
				return false;
			}
		}
		
		var pestania = obtenerPestaniaSeleccionada();
		document.formData.action = "guardarAltaPermisoInternacional.action#" + pestania;
		document.formData.submit();
	}
}

function firmarDeclPermisoInternacional() {
	deshabilitarBotonesPermInter();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "firmarDeclAltaPermisoInternacional.action#" + pestania;
	document.formData.submit();
}

function enviarDeclaracion() {
	deshabilitarBotonesPermInter();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "enviarDeclAltaPermisoInternacional.action#" + pestania;
	document.formData.submit();
}

function validarPermisoInternacional() {
	deshabilitarBotonesPermInter();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "validarAltaPermisoInternacional.action#" + pestania;
	document.formData.submit();
}

function tramitarPermisoInternacional() {
	deshabilitarBotonesPermInter();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "tramitarAltaPermisoInternacional.action#" + pestania;
	document.formData.submit();
}

function imprimirPermisoInternacional() {
	deshabilitarBotonesPermInter();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "imprimirAltaPermisoInternacional.action#" + pestania;
	document.formData.submit();
	habilitarBotonesPermInter();
}

function limpiarFormularioTitular(){
	var dniTitular = document.getElementById("idNifTitularPI").value;
	if (dniTitular != null && dniTitular != "") {
		document.getElementById("tipoPersonaTitular").value = "-1";
		document.getElementById("idPrApeRzsSolicitantePI").value = "";
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

function volverPermisoInternacional(){
	deshabilitarBotonesPermInter();
	$("#formData").attr("action", "inicioConsultaPIntern").trigger("submit");
}

function deshabilitarBotonesPermInter(){
	if (!$('#ibGuardarPermisoInternacional').is(':hidden')){
		$("#ibGuardarPermisoInternacional").css("color","#BDBDBD");
		$("#ibGuardarPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibFirmarDeclPermisoInternacional').is(':hidden')){
		$("#ibFirmarDeclPermisoInternacional").css("color","#BDBDBD");
		$("#ibFirmarDeclPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibEnviarDeclColegiado').is(':hidden')){
		$("#ibEnviarDeclColegiado").css("color","#BDBDBD");
		$("#ibEnviarDeclColegiado").prop("disabled",true);
	}
	if (!$('#ibEnviarDeclColegio').is(':hidden')){
		$("#ibEnviarDeclColegio").css("color","#BDBDBD");
		$("#ibEnviarDeclColegio").prop("disabled",true);
	}
	if (!$('#ibValidarPermisoInternacional').is(':hidden')){
		$("#ibValidarPermisoInternacional").css("color","#BDBDBD");
		$("#ibValidarPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibTramitarPermisoInternacional').is(':hidden')){
		$("#ibTramitarPermisoInternacional").css("color","#BDBDBD");
		$("#ibTramitarPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibImprimirPermisoInternacional').is(':hidden')){
		$("#ibImprimirPermisoInternacional").css("color","#BDBDBD");
		$("#ibImprimirPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibVolverPermisoInternacional').is(':hidden')){
		$("#ibVolverPermisoInternacional").css("color","#BDBDBD");
		$("#ibVolverPermisoInternacional").prop("disabled",true);
	}
	if (!$('#ibGenMandatoTit').is(':hidden')){
		$("#ibGenMandatoTit").css("color","#BDBDBD");
		$("#ibGenMandatoTit").prop("disabled",true);
	}
	
	$("#bSubirDocPInter").css("color","#BDBDBD");
	$("#bSubirDocPInter").prop("disabled",true);
	
	$("#bloqueBotones").show();
}

function habilitarBotonesPermInter(){
	if (!$('#ibGuardarPermisoInternacional').is(':hidden')){
		$("#ibGuardarPermisoInternacional").css("color","#000000");
		$("#ibGuardarPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibFirmarDeclPermisoInternacional').is(':hidden')){
		$("#ibFirmarDeclPermisoInternacional").css("color","#000000");
		$("#ibFirmarDeclPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibEnviarDeclColegiado').is(':hidden')){
		$("#ibEnviarDeclColegiado").css("color","#000000");
		$("#ibEnviarDeclColegiado").prop("disabled",false);
	}
	if (!$('#ibEnviarDeclColegio').is(':hidden')){
		$("#ibEnviarDeclColegio").css("color","#000000");
		$("#ibEnviarDeclColegio").prop("disabled",false);
	}
	if (!$('#ibValidarPermisoInternacional').is(':hidden')){
		$("#ibValidarPermisoInternacional").css("color","#000000");
		$("#ibValidarPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibTramitarPermisoInternacional').is(':hidden')){
		$("#ibTramitarPermisoInternacional").css("color","#000000");
		$("#ibTramitarPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibImprimirPermisoInternacional').is(':hidden')){
		$("#ibImprimirPermisoInternacional").css("color","#000000");
		$("#ibImprimirPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibVolverPermisoInternacional').is(':hidden')){
		$("#ibVolverPermisoInternacional").css("color","#000000");
		$("#ibVolverPermisoInternacional").prop("disabled",false);
	}
	if (!$('#ibGenMandatoTit').is(':hidden')){
		$("#ibGenMandatoTit").css("color","#000000");
		$("#ibGenMandatoTit").prop("disabled",false);
	}
	$("#bSubirDocPInter").css("color","#000000");
	$("#bSubirDocPInter").prop("disabled",false);
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

function bloqueaBotonesConsultaIntern(){
	$('#idBBuscarPIntern').prop('disabled', true);
	$('#idBBuscarPIntern').css('color', '#6E6E6E');
	$('#idBLimpiarPIntern').prop('disabled', true);
	$('#idBLimpiarPIntern').css('color', '#6E6E6E');
	$('#idPICambiarEstado').prop('disabled', true);
	$('#idPICambiarEstado').css('color', '#6E6E6E');
	$('#idPIImprimir').prop('disabled', true);
	$('#idPIImprimir').css('color', '#6E6E6E');
	$('#idPIValidar').prop('disabled', true);
	$('#idPIValidar').css('color', '#6E6E6E');
	$('#idPITramitar').prop('disabled', true);
	$('#idPITramitar').css('color', '#6E6E6E');
	$('#idPIEliminar').prop('disabled', true);
	$('#idPIEliminar').css('color', '#6E6E6E');
}

function desbloqueaBotonesConsultaIntern(){
	$('#idBBuscarPIntern').prop('disabled', false);
	$('#idBBuscarPIntern').css('color', '#000000');	
	$('#idBLimpiarPIntern').prop('disabled', false);
	$('#idBLimpiarPIntern').css('color', '#000000');	
	$('#idPICambiarEstado').prop('disabled', false);
	$('#idPICambiarEstado').css('color', '#000000');	
	$('#idPIImprimir').prop('disabled', false);
	$('#idPIImprimir').css('color', '#000000');	
	$('#idPIValidar').prop('disabled', false);
	$('#idPIValidar').css('color', '#000000');	
	$('#idPITramitar').prop('disabled', false);
	$('#idPITramitar').css('color', '#000000');	
	$('#idPIEliminar').prop('disabled', false);
	$('#idPIEliminar').css('color', '#000000');	
}

function buscarConsultaIntern(){
	bloqueaBotonesConsultaIntern();
	$("#formData").attr("action", "buscarConsultaPIntern.action").trigger("submit");
}

function limpiarConsultaIntern(){
	$("#idNumExpPIntern").val("");
	$("#idRefPropiaPIntern").val("");
	$("#idNifTitularPIntern").val("");
	$("#idEstadoPIntern").val("");
	$("#idContratoPIntern").val("");
	$("#diaFechaAltaIniPIntern").val("");
	$("#mesFechaAltaIniPIntern").val("");
	$("#anioFechaAltaIniPIntern").val("");
	$("#diaFechaAltaFinPIntern").val("");
	$("#mesFechaAltaFinPIntern").val("");
	$("#anioFechaAltaFinPIntern").val("");
	$("#diaFechaPrtIniPIntern").val("");
	$("#mesFechaPrtIniPIntern").val("");
	$("#anioFechaPrtIniPIntern").val("");
	$("#diaFechaPrtFinPIntern").val("");
	$("#mesFechaPrtFinPIntern").val("");
	$("#anioFechaPrtFinPIntern").val("");
	$("#idEstadoImpresionPIntern").val("");
	$("#idJefaturaPIntern").val("");
}

function mostrarBloqueActualizadosCIntern(){
	$("#bloqueFallidosCIntern").hide();
	if($("#bloqueActualizadosCIntern").is(":visible")){
		$("#bloqueActualizadosCIntern").hide();
		$("#despValidadoCIntern").attr("src","img/plus.gif");
		$("#despValidadoCIntern").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizadosCIntern").show();	
		$("#despValidadoCIntern").attr("src","img/minus.gif");
		$("#despValidadoCIntern").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidosCIntern(){
	$("#bloqueActualizadosCIntern").hide();
	if($("#bloqueFallidosCIntern").is(":visible")){
		$("#bloqueFallidosCIntern").hide();
		$("#despFallidosCIntern").attr("src","img/plus.gif");
		$("#despFallidosCIntern").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidosCIntern").show();	
		$("#despFallidosCIntern").attr("src","img/minus.gif");
		$("#despFallidosCIntern").attr("alt","Ocultar");
		
	}
}

function cambiarElementosPorPaginaConsultaIntern(){
	var $dest = $("#displayTagDivConsultaIntern");
	$.ajax({
		url:"navegarConsultaPIntern.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaPIntern").val(),
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

function marcarTodosPIntern(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksPIntern) {
		if (!document.formData.listaChecksPIntern.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksPIntern.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksPIntern.length; i++) {
			document.formData.listaChecksPIntern[i].checked = marcar;
		}
	}
}

function mostrarLoadingConsultaIntern(boton){
	bloqueaBotonesConsultaIntern();
	document.getElementById("bloqueLoadingConsultaIntern").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaIntern(boton, value) {
	document.getElementById("bloqueLoadingConsultaIntern").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesConsultaIntern();
}


function imprimirConsultaIntern(){
	var valueBoton = $("#idPIImprimir").val();
	mostrarLoadingConsultaIntern('idPIImprimir');
	var codigos = "";
	$("input[name='listaChecksPIntern']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaIntern('idPIImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para imprimir.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea imprimir los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "imprimirConsultaPIntern.action").trigger("submit");
	} else{
		ocultarLoadingConsultaIntern('idPIImprimir',valueBoton);
	} 
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function validarConsultaIntern(){
	var valueBoton = $("#idPIValidar").val();
	mostrarLoadingConsultaIntern('idPIValidar');
	var codigos = "";
	$("input[name='listaChecksPIntern']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaIntern('idPIValidar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para validar.");
	} 
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "validarConsultaPIntern.action").trigger("submit");
}

function tramitarConsultaIntern(){
	var valueBoton = $("#idPITramitar").val();
	mostrarLoadingConsultaIntern('idPITramitar');
	var codigos = "";
	$("input[name='listaChecksPIntern']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaIntern('idPITramitar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para tramitar.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea tramitar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "tramitarConsultaPIntern.action").trigger("submit");
	} else {
		ocultarLoadingConsultaIntern('idPITramitar',valueBoton);
	}
}

function eliminarConsultaIntern(){
	var valueBoton = $("#idPIEliminar").val();
	mostrarLoadingConsultaIntern('idPIEliminar');
	var codigos = "";
	$("input[name='listaChecksPIntern']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaIntern('idPIEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para eliminar.");
	} else if(confirm("\u00BFEst\u00E1 seguro de que desea eliminar los tramites seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaPIntern.action").trigger("submit");
	} else {
		ocultarLoadingConsultaIntern('idPIEliminar',valueBoton);
	}
}

function cargarVentanaCambioEstado(){
	var valueBoton = $("#idPICambiarEstado").val();
	mostrarLoadingConsultaIntern('idPICambiarEstado');
	var codigos = "";
	$("input[name='listaChecksPIntern']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultaIntern('idPICambiarEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn tramite para cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaIntern");
	$.post("cargarPopUpCambioEstadoConsultaPIntern.action", function(data) {
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
						var estadoNuevo = $("#idEstadoNuevoPI").val();
						if(estadoNuevo == ""){
							alert("Seleccione un estado");
							return false;
						}
						$("#divEmergenteConsultaIntern").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaPIntern.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultaIntern('idPICambiarEstado',valueBoton);
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

function descargarDocAportadaPInter(idPermisoInternacional){
	if(idPermisoInternacional != null && idPermisoInternacional != ""){
		limpiarHiddenInput("idPermisoInternacional");
		input = $("<input>").attr("type", "hidden").attr("name", "idPermisoInternacional").val(idPermisoInternacional);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarDocAportadaAltaPermisoInternacional.action#DocumentacionAportada").trigger("submit");
	} else {
		return alert("No se ha podido descargar el fichero porque no se le ha indicado el id del tramite.");
	}
}

function subirDocPIntern(){
	deshabilitarBotonesPermInter();
	$("#formData").attr("action", "subirDocAportadaAltaPermisoInternacional.action#DocumentacionAportada").trigger("submit");
}

function abrirEvolucionPIntern(idPermiso,destino){
	if(idPermiso == null || idPermiso == ""){
		return alert("Debe seleccionar alg\u00FAn tramite para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvoPInter.action?idPermisoIntern=" + idPermiso;
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

function cambiarElementosPorPaginaEvoPermInter(){
	var $dest = $("#displayTagEvoPermInter");
	$.ajax({
		url:"navegarEvoPInter.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolPermInter").val(),
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

function cambioDomicilioPermiso(){
	if($("#cambioDomicilio").is(':checked') == true){
		$("#cambioDomicilio").prop("checked", false);
	}
	document.getElementById('idProvinciaTitular').selectedIndex = 0;
	document.getElementById('idMunicipioTitular').selectedIndex = 0;
	document.getElementById('municipioSeleccionadoTitular').value = "";
	document.getElementById('idPuebloTitular').selectedIndex = 0;
	document.getElementById('puebloSeleccionadoTitular').value = "";
	document.getElementById('cpTitular').value = "";
	document.getElementById('tipoViaTitular').selectedIndex = 0;
	document.getElementById('tipoViaSeleccionadaTitular').value = "";
	document.getElementById('nombreViaTitular').value = "";
	document.getElementById('numeroDireccionTitular').value = "";
	document.getElementById('letraDireccionTitular').value = "";
	document.getElementById('escaleraDireccionTitular').value = "";
	document.getElementById('pisoDireccionTitular').value = "";
	document.getElementById('puertaDireccionTitular').value = "";
	document.getElementById('bloqueDireccionTitular').value = "";
	document.getElementById('kmDireccionTitular').value = "";
	document.getElementById('hmDireccionTitular').value = "";
		
	document.getElementById('idProvinciaTitular').disabled = true;
	document.getElementById('idMunicipioTitular').disabled = true;
	document.getElementById('idPuebloTitular').disabled = true;
	document.getElementById('cpTitular').disabled = true;
	document.getElementById('tipoViaTitular').disabled = true;
	document.getElementById('nombreViaTitular').disabled = true;
	document.getElementById('numeroDireccionTitular').disabled = true;
	document.getElementById('letraDireccionTitular').disabled = true;
	document.getElementById('escaleraDireccionTitular').disabled = true;
	document.getElementById('pisoDireccionTitular').disabled = true;
	document.getElementById('puertaDireccionTitular').disabled = true;
	document.getElementById('bloqueDireccionTitular').disabled = true;
	document.getElementById('kmDireccionTitular').disabled = true;
	document.getElementById('hmDireccionTitular').disabled = true;
}

function generarMandato() {
	deshabilitarBotonesPermInter();
	document.formData.action="generarMandatoAltaPermisoInternacional.action#Titular";
	document.formData.submit();
}

function descargarMandato() {
	if(confirm("Este fichero se borrará en 5 minutos una vez impreso ¿Continuar?")) {
		document.formData.action="descargarMandatoAltaPermisoInternacional.action#Mandato";
		document.formData.submit();
	}
}

function eliminarMandato() {
	document.formData.action="eliminarMandatoAltaPermisoInternacional.action#Mandato";
	document.formData.submit();
}

function verTodasTasas(idContrato,destino){
	var $dest =  $("#"+destino);
	var contrato = $("#"+idContrato);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=4.5",
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