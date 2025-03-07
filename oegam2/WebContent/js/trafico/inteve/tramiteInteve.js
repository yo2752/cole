function guardarTramiteInteve(){
	bloqueaBotonesTramiteInteve();
	var pestania = obtenerPestaniaSeleccionada();
	$("#idNumExpedienteTRI").prop('disabled', false);
	$("#idNifSolicitanteTRI").prop('disabled', false);
	$("#idNifRepresentanteTRI").prop('disabled', false);
	var url = "guardarAltaTramIntv.action#" + pestania;
	var $form = $("#formData");
	$form.attr("action", url).trigger("submit");
}

function buscarConsultaTRI(){
	bloqueaBotonesConsultaTramiteInteve();
	$("#formData").attr("action", "buscarConsultaTrI").trigger("submit");
}

function cambiarElementosPorPaginaConsultaTrI(){
	var $dest = $("#displayTagDivConsultaTrI");
	$.ajax({
		url:"navegarConsultaTrI.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaTrI").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los tramites.');
	    }
	});
}

function bloqueaBotonesConsultaTramiteInteve(){
	$('#idBBuscarCTRI').prop('disabled', true);
	$('#idBBuscarCTRI').css('color', '#6E6E6E');
	$('#bLimpiarCTRI').prop('disabled', true);
	$('#bLimpiarCTRI').css('color', '#6E6E6E');
	
	$('#idCambiarEstadoTRI').prop('disabled', true);
	$('#idCambiarEstadoTRI').css('color', '#6E6E6E');
	$('#idBValidarTRI').prop('disabled', true);
	$('#idBValidarTRI').css('color', '#6E6E6E');
	$('#idBSolicitarTRI').prop('disabled', true);
	$('#idBSolicitarTRI').css('color', '#6E6E6E');
	$('#idBDescargarTRI').prop('disabled', true);
	$('#idBDescargarTRI').css('color', '#6E6E6E');
	$('#idBReiniciarEstadosTRI').prop('disabled', true);
	$('#idBReiniciarEstadosTRI').css('color', '#6E6E6E');
	$('#idBEliminarTRI').prop('disabled', true);
	$('#idBEliminarTRI').css('color', '#6E6E6E');
}

function desbloqueaBotonesConsultaTramiteInteve(){
	$('#idBBuscarCTRI').prop('disabled', false);
	$('#idBBuscarCTRI').css('color', '#000000');	
	$('#bLimpiarCTRI').prop('disabled', false);
	$('#bLimpiarCTRI').css('color', '#000000');	
	$('#idCambiarEstadoTRI').prop('disabled', false);
	$('#idCambiarEstadoTRI').css('color', '#000000');
	$('#idBValidarTRI').prop('disabled', false);
	$('#idBValidarTRI').css('color', '#000000');
	$('#idBSolicitarTRI').prop('disabled', false);
	$('#idBSolicitarTRI').css('color', '#000000');
	$('#idBDescargarTRI').prop('disabled', false);
	$('#idBDescargarTRI').css('color', '#000000');
	$('#idBReiniciarEstadosTRI').prop('disabled', false);
	$('#idBReiniciarEstadosTRI').css('color', '#000000');
	$('#idBEliminarTRI').prop('disabled', false);
	$('#idBEliminarTRI').css('color', '#000000');
	
}

function bloqueaBotonesTramiteInteve(){
	$('#idBGuardarTRInteve').prop('disabled', true);
	$('#idBGuardarTRInteve').css('color', '#6E6E6E');
	$('#idBValidarTRInteve').prop('disabled', true);
	$('#idBValidarTRInteve').css('color', '#6E6E6E');
	$('#idBSolicitarTRInteve').prop('disabled', true);
	$('#idBSolicitarTRInteve').css('color', '#6E6E6E');
	$('#idBModificarSolicitudIntb').prop('disabled', true);
	$('#idBModificarSolicitudIntb').css('color', '#6E6E6E');
	$('#idEliminarSolicitudesIntv').prop('disabled', true);
	$('#idEliminarSolicitudesIntv').css('color', '#6E6E6E');
	$('#idDescargarSolicitudesIntv').prop('disabled', true);
	$('#idDescargarSolicitudesIntv').css('color', '#6E6E6E');
	$('#idBotonBuscarNifSolTRI').prop('disabled', true);
	$('#idBotonBuscarNifSolTRI').css('color', '#6E6E6E');
	$('#idBLimpiarSolicitanteTR').prop('disabled', true);
	$('#idBLimpiarSolicitanteTR').css('color', '#6E6E6E');
	$('#idBLimpiarRepresentanteTR').prop('disabled', true);
	$('#idBLimpiarRepresentanteTR').css('color', '#6E6E6E');
	
}

function desbloqueaBotonesTramiteInteve(){
	$('#idBGuardarTRInteve').prop('disabled', false);
	$('#idBGuardarTRInteve').css('color', '#000000');	
	$('#idBValidarTRInteve').prop('disabled', false);
	$('#idBValidarTRInteve').css('color', '#000000');	
	$('#idBSolicitarTRInteve').prop('disabled', false);
	$('#idBSolicitarTRInteve').css('color', '#000000');	
	$('#idBModificarSolicitudIntb').prop('disabled', false);
	$('#idBModificarSolicitudIntb').css('color', '#000000');	
	$('#idEliminarSolicitudesIntv').prop('disabled', false);
	$('#idEliminarSolicitudesIntv').css('color', '#000000');
	$('#idDescargarSolicitudesIntv').prop('disabled', false);
	$('#idDescargarSolicitudesIntv').css('color', '#000000');
	$('#idBotonBuscarNifSolTRI').prop('disabled', false);
	$('#idBotonBuscarNifSolTRI').css('color', '#000000');
	$('#idBLimpiarSolicitanteTR').prop('disabled', false);
	$('#idBLimpiarSolicitanteTR').css('color', '#000000');
	$('#idBLimpiarRepresentanteTR').prop('disabled', false);
	$('#idBLimpiarRepresentanteTR').css('color', '#000000');
}

function validarTramite(){
	bloqueaBotonesTramiteInteve();
	var pestania = obtenerPestaniaSeleccionada();
	var url = "validarAltaTramIntv.action#" + pestania;
	var $form = $("#formData");
	$form.attr("action", url).trigger("submit");
}

function solicitarInteve(){
	bloqueaBotonesTramiteInteve();
	var pestania = obtenerPestaniaSeleccionada();
	var url = "solicitarAltaTramIntv.action#" + pestania;
	var $form = $("#formData");
	$form.attr("action", url).trigger("submit");
}

function limpiarSolicitanteTRI(){
	$("#idNombreSolicitanteTRI").val("");
	$("#idPrApeRzsSolicitanteTRI").val("");
	$("#idApellido2SolicitanteTRI").val("");
	$("#idNifSolicitanteTRI").val("");
	$('#idNifSolicitanteTRI').prop('disabled', false);
}

function limpiarRepresentanteTRI(){
	$("#idNombreRepresentanteTRI").val("");
	$("#idPrApeRzsRepresentanteTRI").val("");
	$("#idApellido2RepresentanteTRI").val("");
	$("#idNifRepresentanteTRI").val("");
	$('#idNifRepresentanteTRI').prop('disabled', false);
}

function limpiarConsultaTramiteTRI(){
	$("#idNumExpedienteTRI").val("");
	$("#idMatriculaTRI").val("");
	$("#idBastidorTRI").val("");
	$("#idNiveTRI").val("");
	$("#idTasaTRI").val("");
	$("#idTipoInformeTRI").val("");
	$("#idEstadoTRI").val("");
	$("#idContratoTRI").val("");
	$("#diaFechaAltIniTRI").val("");
	$("#mesFechaAltIniTRI").val("");
	$("#anioFechaAltIniTRI").val("");
	$("#diaFechaAltFinTRI").val("");
	$("#mesFechaAltFinTRI").val("");
	$("#anioFechaAltFinTRI").val("");
	$("#diaFechaPrtIniTRI").val("");
	$("#mesFechaPrtIniTRI").val("");
	$("#anioFechaPrtIniTRI").val("");
	$("#diaFechaPrtFinTRI").val("");
	$("#mesFechaPrtFinTRI").val("");
	$("#anioFechaPrtFinTRI").val("");
}

function buscarSolicitante(){
	bloqueaBotonesTramiteInteve();
	var nif = $('#idNifSolicitanteTRI').val();
	if(nif != ""){
		var idContrato = $("#idContratoTRI").val();
		if(idContrato == null || idContrato == ""){
			idContrato = $("#idContratoTRIHidden").val();
		}
		if(idContrato != null && idContrato != ""){
			limpiarSolicitanteTRI();
			var $dest = $("#divSolicitanteTRI", parent.document);
			$.ajax({
				url:"buscarSolicitanteAjaxTRI.action#TramiteInteve",
				data:"nif="+ nif +"&idContrato=" + idContrato,
				type:'POST',
				success: function(data, xhr, status){
					$dest.empty().append(data);
					if($('#idPrApeRzsSolicitanteTRI').val() != ""){
						$('#idNifSolicitanteTRI').prop("disabled", true);
					}else{
						$('#idNifSolicitanteTRI').prop("disabled", false);
					}
					desbloqueaBotonesTramiteInteve();
				},
				error : function(xhr, status) {
			        alert('Ha sucedido un error a la hora de cargar el solicitante.');
			        desbloqueaBotonesTramiteInteve();
			    }
			});
		} else {
			alert("Para poder realizar la busqueda del solicitante primero tiene que seleccionar un contrato.");
			desbloqueaBotonesTramiteInteve();
		}
	}else{
		alert("Para poder realizar la busqueda del solicitante debe rellenar el NIF/CIF.");
		desbloqueaBotonesTramiteInteve();
	}
}

function buscarRepresentante(){
	bloqueaBotonesTramiteInteve();
	var nif = $('#idNifRepresentanteTRI').val();
	if(nif != ""){
		var idContrato = $("#idContratoTRI").val();
		if(idContrato == null || idContrato == ""){
			idContrato = $("#idContratoTRIHidden").val();
		}
		if(idContrato != null && idContrato != ""){
			limpiarRepresentanteTRI();
			var $dest = $("#divRepresentanteTRI", parent.document);
			$.ajax({
				url:"buscarRepresentanteAjaxTRI.action#TramiteInteve",
				data:"nif="+ nif +"&idContrato=" + idContrato,
				type:'POST',
				success: function(data, xhr, status){
					$dest.empty().append(data);
					if($('#idPrApeRzsRepresentanteTRI').val() != ""){
						$('#idNifRepresentanteTRI').prop("disabled", true);
					}else{
						$('#idNifRepresentanteTRI').prop("disabled", false);
					}
					desbloqueaBotonesTramiteInteve();
				},
				error : function(xhr, status) {
			        alert('Ha sucedido un error a la hora de cargar el representante.');
			        desbloqueaBotonesTramiteInteve();
			    }
			});
		} else {
			alert("Para poder realizar la busqueda del representante primero tiene que seleccionar un contrato.");
			desbloqueaBotonesTramiteInteve();
		}
	}else{
		alert("Para poder realizar la busqueda del representante debe rellenar el NIF/CIF.");
		desbloqueaBotonesTramiteInteve();
	}
}


function eliminarSolicitudesInteve(){
	var checks = document.getElementsByName("listaChecksSolInteve");
	var numChecks = 0;
	var codigos = "";
	var i=0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "," + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		alert("Debe seleccionar alguna solicitud de inteve para eliminar.");
	} else {
		if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar las solicitudes seleccionadas?")){
			limpiarHiddenInput("codSeleccionados");
			input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
			var pestania = obtenerPestaniaSeleccionada();
			var url = "eliminarSolicitudesAltaTramIntv.action?codSeleccionados=" + codigos + "#" + pestania;
			var $form = $("#formData");
			$form.attr("action", url).trigger("submit");
		}
	}
}

function descargarSolicitudesInteve() {
	var checks = document.getElementsByName("listaChecksSolInteve");
	var numChecks = 0;
	var codigos = "";
	var i=0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "," + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		alert("Debe seleccionar alguna solicitud de inteve para descargar.");
	} else {
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "descargarSolicitudesAltaTramIntv.action"+ "#" + pestania).trigger("submit");
	}
}

function reiniciarSolicitudesInteve(){
	var checks = document.getElementsByName("listaChecksSolInteve");
	var numChecks = 0;
	var codigos = "";
	var i=0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "," + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		alert("Debe seleccionar alguna solicitud de inteve para reiniciar su estado.");
	} else {
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		var pestania = obtenerPestaniaSeleccionada();
		$('#formData').append($(input));
		$("#formData").attr("action", "reiniciarSolicitudesAltaTramIntv.action"+ "#" + pestania).trigger("submit");
	}
}

function modificarSolictudInteve(){
	bloqueaBotonesTramiteInteve();
	var checks = document.getElementsByName("listaChecksSolInteve");
	var numChecks = 0;
	var codigos = "";
	var i=0;
	var idContrato = $('#idContratoTRI').val();
	if(idContrato == null || idContrato == ""){
		idContrato = $('#idContratoTRIHidden').val();
	}
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			numChecks++;
		}
		i++;
	}
	if(codigos == ""){
		alert("Debe seleccionar alguna solicitud de inteve para modificar.");
		desbloqueaBotonesTramiteInteve();
	}else if(numChecks > 1){
		alert("Las solictudes de Inteve solo se pueden modificar de manera individual.");
		desbloqueaBotonesTramiteInteve();
	} else {
		limpiarSolicitudInteve();
		var $dest = $('#divAltaSolicitudInteve', parent.document);
		$.ajax({
			url:"buscarSolicitudInteveAjaxTRI.action#Solicitudes",
			data:"idSolInteve="+ codigos + "&idContrato="+ idContrato,
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				desbloqueaBotonesTramiteInteve();
				mostrarAceptacion();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar la solicitud.');
		        desbloqueaBotonesTramiteInteve();
		    }
		});
	}
}

function inicializarCampos(){
	cargarTasas();
	if($('#idNifSolicitanteTRI').val() != null && $('#idNifSolicitanteTRI').val() != ""){
		$('#idNifSolicitanteTRI').prop('disabled', true);
	}
	if($('#idNifRepresentanteTRI').val() != null && $('#idNifRepresentanteTRI').val() != ""){
		$('#idNifRepresentanteTRI').prop('disabled', true);
	}
}

function cargarTasas(){
	var $dest = $('#idTasaSolicitudInteveTRI');
	var idContrato = $('#idContratoTRI').val();
	if(idContrato == null || idContrato == ""){
		idContrato = $('#idContratoTRIHidden').val();
	}
	if(idContrato != null && idContrato != ""){
		$.ajax({
			url:"cargarTasaAjaxTRI.action",
			data:"idContrato="+ idContrato +"&codigoTasa=" +$('#idTasaSolicitudInteveTRI').val(),
			type:'POST',
			success: function(data){
				if(data != ""){
					$dest.find('option').remove().end().append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
					var listTasas = data.split("_");
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

function limpiarSolicitudInteve(){
	$('#idEstadoInteveTRI').val("");
	$('#idTipoInformeTRI').val("");
	$('#idTasaSolicitudInteveTRI').val("");
	$('#idMatriculaInteveTRI').val("");
	$('#idBastidorInteveTRI').val("");
	$('#idNiveInteveTRI').val("");
	$('#idRespuestaDgtTRI').val("");
}

function marcarTodosSolInteve(objCheck){
	var marcar = objCheck.checked;
	if (document.formData.listaChecksSolInteve) {
		if (!document.formData.listaChecksSolInteve.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksSolInteve.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksSolInteve.length; i++) {
			document.formData.listaChecksSolInteve[i].checked = marcar;
		}
	}
}

function marcarTodosCTRI(objCheck){
	var marcar = objCheck.checked;
	if (document.formData.listaChecksTRI) {
		if (!document.formData.listaChecksTRI.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksTRI.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksTRI.length; i++) {
			document.formData.listaChecksTRI[i].checked = marcar;
		}
	}
}

function consultaEvolucionTramiteInteve(numExpediente){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alguna consulta para poder obtener su evolución.");
	}
	var $dest = $("#divEmergentePopUp");
	var url = "inicioConsultaEvTramiteTraf.action?numExpediente=" + numExpediente;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,height: 500
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

function validarConsultaTRI(){
	var valueBoton = $("#idBValidarTRI").val();
	mostrarLoadingConsultaTRI('idBValidarTRI');
	var codigos = "";
	$("input[name='listaChecksTRI']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "," + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
		return alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder validarlo.");
	} 
	if(confirm("\u00BFEst\u00E1 seguro de que desea validar los tramites seleccionados?")){
		limpiarHiddenInput("ids");
		input = $("<input>").attr("type", "hidden").attr("name", "ids").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "validarConsultaTrI.action").trigger("submit");
	}
}

function solicitarConsultaTRI(){
	var valueBoton = $("#idBSolicitarTRI").val();
	mostrarLoadingConsultaTRI('idBSolicitarTRI');
	var codigos = "";
	$("input[name='listaChecksTRI']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
		return alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder solicitar su Inteve.");
	} 
	if(confirm("\u00BFEst\u00E1 seguro de que desea solictar los tramites seleccionados?")){
		limpiarHiddenInput("ids");
		input = $("<input>").attr("type", "hidden").attr("name", "ids").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "solicitarConsultaTrI.action").trigger("submit");
	}
}

function descargarConsultaTRI(){
	var valueBoton = $("#idBDescargarTRI").val();
	mostrarLoadingConsultaTRI('idBDescargarTRI');
	
	var checks =  $("input[name='listaChecksTRI']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder descargar su Inteve.");
		ocultarLoadingConsultarTRI('idBDescargarTRI', valueBoton);
		return false;
	}
    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
	});
	limpiarHiddenInput("ids");
	input = $("<input>").attr("type", "hidden").attr("name", "ids").val(arrayCodigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarConsultaTrI.action").trigger("submit");
	ocultarLoadingConsultarTRI('idBDescargarTRI', valueBoton);
}

function reiniciarEstadosConsultaTRI(){
	var valueBoton = $("#idBReiniciarEstadosTRI").val();
	mostrarLoadingConsultaTRI('idBReiniciarEstadosTRI');
	var codigos = "";
	$("input[name='listaChecksTRI']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "," + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
		return alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder reniniciar los estados de los Inteve.");
	} 
	if(confirm("\u00BFEst\u00E1 seguro de que desea reiniciar los estado de los tramites seleccionados?")){
		limpiarHiddenInput("ids");
		input = $("<input>").attr("type", "hidden").attr("name", "ids").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reiniciarEstdConsultaTrI.action").trigger("submit");
	}
}

function eliminarConsultaTRI(){
	var valueBoton = $("#idBEliminarTRI").val();
	mostrarLoadingConsultaTRI('idBEliminarTRI');
	var codigos = "";
	$("input[name='listaChecksTRI']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "," + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
		return alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder eliminar los Inteve.");
	} 
	if(confirm("\u00BFEst\u00E1 seguro de que desea eliminar los tramites seleccionados?")){
		limpiarHiddenInput("ids");
		input = $("<input>").attr("type", "hidden").attr("name", "ids").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaTrI.action").trigger("submit");
	}
}

function abrirVentanaSeleccionEstados(){
	var valueBoton = $("#idCambiarEstadoTRI").val();
	mostrarLoadingConsultaTRI('idCambiarEstadoTRI');
	var codigos = "";
	$("input[name='listaChecksTRI']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
		return alert("Debe seleccionar alg\u00fan tr\u00E1mite para poder cambiar su estado.");
	} 
	var $dest = $("#divEmergenteConsultaTRI");
	$.post("abrirSeleccionEstadoConsultaTrI.action", function(data) {
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
						var estadoNuevo = $("#idEstadoNuevoCTRI").val();
						if(estadoNuevo == ""){
							alert("Seleccione un estado");
							ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
							return false;
						}
						$("#divEmergenteConsultaTRI").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("ids");
						input = $("<input>").attr("type", "hidden").attr("name", "ids").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaTrI.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingConsultarTRI('idCambiarEstadoTRI',valueBoton);
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

function mostrarLoadingConsultaTRI(boton) {
	bloqueaBotonesConsultaTramiteInteve();
	document.getElementById("bloqueLoadingConsultarTRI").style.display = "block";
	boton.value = "Procesando..";
}

function ocultarLoadingConsultarTRI(boton, value) {
	document.getElementById("bloqueLoadingConsultarTRI").style.display = "none";
	boton.value = value;
	desbloqueaBotonesConsultaTramiteInteve();
}

function bloqueaBotonesConsulta() {	
	$('table.acciones input').prop('disabled', 'true');
	$('table.acciones input').css('color', '#6E6E6E');		
}

function doPost(idForm, url, dest, success) {
	$("#propiedadDtoCategoria").attr('disabled', false);
	var $form = $("#" + idForm);
	if (dest != null && dest.length > 0) {
		$dest = $("#" + dest);
		if (!$dest.size()) {
			$dest = $("#" + dest, parent.document);
		}
		$.post(url, $form.serialize(), function(data) {
			$dest.empty().append(data);
		}).success(function(data) {
			if (success != null && success.length > 0) {
				eval(success.replace(/%/g, "\""));
			}
		});
	} else {
		$('input[type=button]').attr('disabled', true);
		$form.attr("action", url).trigger("submit");
	}
}

function doPostValidate(idForm, url, dest, success) {
	var $form = $("#" + idForm);
	doPost(idForm, url, dest, success);
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

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function consultaEvolucionTramiteInteve(numExpediente) {
	url = "inicioConsultaEvTramiteInteve?numExpediente=" + numExpediente;
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 450,
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

function mostrarAceptacion(){
	var tipoInforme = $("#idTipoInformeTRI").val();
	switch (tipoInforme) {
	case '5':
		$("#trAceptacion").css('display', '');
		$("#idMatriculaInteveTRI").prop("disabled",true);
		$("#idBastidorInteveTRI").prop("disabled",true);
		$("#idNiveInteveTRI").prop("disabled",true);
		break;
	case '6':
    	$("#trAceptacion").css('display', 'none');
    	$('#checkAceptacion').prop('checked', false);
		$("#idMatriculaInteveTRI").prop("disabled",true);
		$("#idBastidorInteveTRI").prop("disabled",false);
		$("#idNiveInteveTRI").prop("disabled",false);
		break;
	default:
    	$("#trAceptacion").css('display', 'none');
		$('#checkAceptacion').prop('checked', false);
		$("#idMatriculaInteveTRI").prop("disabled",false);
		$("#idBastidorInteveTRI").prop("disabled",false);
		$("#idNiveInteveTRI").prop("disabled",false);
		break;
	}
}

function verTodasTasas(idContrato,destino){
	var $dest =  $("#"+destino);
	var contrato = $("#"+idContrato);
	if(contrato != null && contrato.val() != ""){
		$.ajax({
			url:"recuperarCodTasaLibresPorTipoTasaTraficoAjax.action",
			data:"idContrato="+ contrato.val() +"&codigoTasa=" +$dest.val() +"&tipoTasaSeleccionado=4.1",
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
