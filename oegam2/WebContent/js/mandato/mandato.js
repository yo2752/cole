function  validarDatosMandato() {
	
	var validar = true;
	
	/*if (campoVacio("idCodigoMandato")) {
		alert("El código de mandato es obligatorio");
		validar = false;
	}*/
	
	if(campoVacio("idDiaFechaMandato") || campoVacio("idMesFechaMandato") || campoVacio("idAnioFechaMandato")) {
		alert("La fecha de Mandato es obligatoria");
		validar = false;
	}
	
	if(campoVacio("idDiaFechaCaducidad") || campoVacio("idMesFechaCaducidad") || campoVacio("idAnioFechaCaducidad")) {
		 alert("La fecha de Caducidad el Mandato es obligatoria");
		validar = false;
	}
	
	if(campoVacio("idCif")){
		 alert("El CIF de la empresa es obligatorio");
		 validar = false;
	} else if (!validaNIF(document.getElementById("idCif"))) {
		alert("El CIF introducido no es v\u00e1lido.\nPor favor repase los datos.");
		validar = false;
	}
	
	if(campoVacio("idNombreEmpresa")){
		alert("El nombre de la empresa es obligatorio");
		validar = false;
	}
	
	if (selectVacio("idContrato")) {
		alert("El contrato es obligatorio");
		validar = false;
	}
	
	if(campoVacio("idNombreAdministrador")){
		alert("El nombre del administrador es obligatorio");
		validar = false;
	}
	
	if(campoVacio("idDniAdministrador")){
		 alert("El dni del administrador es obligatorio");
		 validar = false;
	} else if (!validaNIF(document.getElementById("idDniAdministrador"))) {
		alert("El dni del administrador introducido no es v\u00e1lido.\nPor favor repase los datos.");
		validar = false;
	}
	
	if(campoVacio("idDiaFechaCadDniAdmin") || campoVacio("idMesFechaCadDniAdmin") || campoVacio("idAnioFechaCadDniAdmin")) {
		alert("La fecha de caducidad del DNI del administrador es obligatoria es obligatoria");
		validar = false;
	} 
	
	if (!campoVacio("idDniAdministrador2") && !validaNIF(document.getElementById("idDniAdministrador2"))) {
		alert("El dni del 2º administrador introducido no es v\u00e1lido.\nPor favor repase los datos.");
		validar = false;
	}
	
	return validar;
}


function guardarMandato(){
	if (validarDatosMandato()) {
		$("#formData").attr("action", "guardarAltaMandato.action").trigger("submit");
	}
}

function buscarConsultaMandatos(){
	$("#formData").attr("action", "buscarConsultaMandatos").trigger("submit");
}

function limpiarConsultaMandatos(){
	$("#idCodigoMandato").val("");
	$("#diaFechaMandatoIni").val("");
	$("#mesFechaMandatoIni").val("");
	$("#anioFechaMandatoIni").val("");
	$("#diaFechaMandatoFin").val("");
	$("#mesFechaMandatoFin").val("");
	$("#anioFechaMandatoFin").val("");
	$("#idCif").val("");
	$("#idEmpresa").val("");
	$("#idDniAdministrador").val("");
	$("#idNombreAdministrador").val("");
	$("#idDniAdministrador2").val("");
	$("#idNombreAdministrador2").val("");
	$("#idContrato").val(-1);
}

function cambiarElementosPorPaginaConsultaMandatos(){
	var $dest = $("#displayTagDivConsultaMandatos");
	$.ajax({
		url:"navegarConsultaMandatos.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaMandatos").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los mandatos.');
	    }
	});
}

function eliminarMandatos() {
	// Se desabilitan los botones mientras se realiza el proceso
	deshabilitarBotonesConsultaMandatos();
	var valueBoton = $("#idBEliminarMandatos").val();
	mostrarLoadingMandatos("bloqueLoadingConsultaMandatos", "idBEliminarMandatos");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingMandatos('idBEliminarMandatos', valueBoton, "bloqueLoadingConsultaMandatos");
		alert("Debe seleccionar algun tramite para poder eliminarlo.");
		habilitarBotonesConsultaMandatos();
		return false;
	}

	if (!confirm("Puede que el codigo mandato este asociado a una empresa, ¿desea continuar?")) {
		ocultarLoadingMandatos('idBEliminarMandatos', valueBoton, "bloqueLoadingConsultaMandatos");
		habilitarBotonesConsultaMandatos();
		return false;
	}
	
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaMandatos.action").trigger("submit");

}

function getChecksConsultaMarcados(name) {
	var codigos = "";
	$("input[name='" + name + "']:checked").each(function() {
		if (this.checked) {
			if (codigos == "") {
				codigos += this.value;
			} else {
				codigos += "-" + this.value;
			}
		}
	});
	return codigos;
}

function deshabilitarBotonesConsultaMandatos() {
	$("#idBEliminarMandatos").prop('disabled', true);
	$("#idBEliminarMandatos").css('color', '#6E6E6E');
	$("#idBModificarMandatos").prop('disabled', true);
	$("#idBModificarMandatos").css('color', '#6E6E6E');
	$("#idBAltaMandatos").prop('disabled', true);
	$("#idBAltaMandatos").css('color', '#6E6E6E');
}

function habilitarBotonesConsultaMandatos() {
	$("#idBEliminarMandatos").prop('disabled', false);
	$("#idBEliminarMandatos").css('color', '#000000');
	$("#idBModificarMandatos").prop('disabled', false);
	$("#idBModificarMandatos").css('color', '#000000');
	$("#idBAltaMandatos").prop('disabled', false);
	$("#idBAltaMandatos").css('color', '#000000');
}

function mostrarLoadingMandatos(bloqueLoading, boton) {
	document.getElementById(bloqueLoading).style.display = "block";
	$('#' + boton).val("Procesando..");
}

function ocultarLoadingMandatos(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#' + boton).val(value);
}

function modificarMandatos() {
	// Se desabilitan los botones mientras se realiza el proceso
	deshabilitarBotonesConsultaMandatos();
	var valueBoton = $("#idBModificarMandatos").val();
	mostrarLoadingMandatos("bloqueLoadingConsultaMandatos", "idBModificarMandatos");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingMandatos('idBModificarMandatos', valueBoton, "bloqueLoadingConsultaMandatos");
		alert("Debe seleccionar algun tramite para poder modificarlo.");
		habilitarBotonesConsultaMandatos();
		return false;
	}

	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "modificarAltaMandato.action").trigger("submit");

}

function altaMandato() {
	// Se desabilitan los botones mientras se realiza el proceso
	deshabilitarBotonesConsultaMandatos();
	var valueBoton = $("#idBAltaMandatos").val();
	mostrarLoadingMandatos("bloqueLoadingConsultaMandatos", "idBAltaMandatos");
	$("#formData").attr("action", "inicioAltaMandato.action").trigger("submit");
}

function abrirEvolucionMandatos(idMandato, destino) {
	var $dest = $("#" + destino);
	var url = "cargarEvolucionMandatos.action?idMandato=" + idMandato;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html") < 0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : {
					my : 'right',
					at : 'right'
				},
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width : 950,
				height : 500,
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

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function cambiarElementosPorPaginaConsultaEvolucionMandatos(){
	var $dest = $("#displayTagEvolucionMandatosDiv");
	$.ajax({
		url:"navegarEvolucionMandatos.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolMandatos").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con las evoluciones de los mandatos.');
	    }
	});
}

function volverConsultaMandato(){
	$("#formData").attr("action", "inicioConsultaMandatos").trigger("submit");
}

