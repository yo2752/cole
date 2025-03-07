function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
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

function inicializarSolInfo(){
	
	//Seleccionamos por defecto informe completo y posible adquisicion
	if($("#idTipoInformeTramSolInfo").val() == ""){
		$("#idTipoInformeTramSolInfo").val("0");
	}
	
	if($("#idMotivoInformeTramSolInfo").val() == ""){
		$("#idMotivoInformeTramSolInfo").val("0");
	}
}

function limpiarSolInfoVehiculo(){
	$("#idConsultaVehiculoLabel").hide();
	$("#idTramSolInfoVehiculo").val("");
	$("#idTramSolInfoVehiculo").hide();
	$("#idMatriculaTramSolInfo").val("");
	$("#idBastidorTramSolInfo").val("");
	$("#idCodTasaTramSolInfo").val("");
	$("#idTipoInformeTramSolInfo").val("0");
	$("#idMotivoInformeTramSolInfo").val("");
	$("#idEstadoTramSolInfoVehiculos").val("");
}

function guardarTramSolInfo(){
	deshabilitarBotonesTramSolInfo();
	habilitarCampos();
	pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarAltaTramSolInformacion#"+pestania).trigger("submit");
}

function generarXmlTramSolInfo(){
	deshabilitarBotonesTramSolInfo();
	habilitarCampos();
	pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "generarXmlAltaTramSolInformacion#"+pestania).trigger("submit");
}

function descargarXML(){
	//deshabilitarBotonesTramSolInfo();
	//habilitarCampos();
	//pestania = obtenerPestaniaSeleccionada();
	limpiarHiddenInput("numExp");
	input = $("<input>").attr("type", "hidden").attr("name", "numExp").val($("#idNumExpTramSolInfo").val());
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarXmlAltaTramSolInformacion").trigger("submit");
}

function tramitarXmlTramSolInfo(){
	 window.location='http://192.168.50.48/solicitudes.jnlp';
}

function imprimirTramSolInfo(){
	//deshabilitarBotonesTramSolInfo();
	habilitarCampos();
	pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "descargarZipAltaTramSolInformacion#"+pestania).trigger("submit");
}

function descargarCliente(){
	
	var url = $("#urlJNLP").val();
	window.location =url;
}

function reiniciarEstadoTramSolInfo(){
	var codigos = getChecksMarcados("listaChecksSolInfoVehiculos");
	if(codigos == null || codigos == ""){
		alert("Debe seleccionar alguna solicitud para poder reiniciarla.")
	} else {
		deshabilitarBotonesTramSolInfo();
		habilitarCampos();
		pestania = obtenerPestaniaSeleccionada();
		limpiarHiddenInput("matriculaSolInfo");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "reiniciarSolicitudesAltaTramSolInformacion#"+pestania).trigger("submit");
	}
}


function descargarZip(){
	var codigos = getChecksMarcados("listaChecksSolInfoVehiculos");
	if(codigos == null || codigos == ""){
		alert("Debe seleccionar alguna solicitud para poder descargarla.")
	} else {
		deshabilitarBotonesTramSolInfo();
		habilitarCampos();
		pestania = obtenerPestaniaSeleccionada();
		limpiarHiddenInput("matriculaSolInfo");
		input = $("<input>").attr("type", "hidden").attr("name", "matriculasSolInfo").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "descargarZipAltaTramSolInformacion#"+pestania).trigger("submit");
	}
}

function volverTramSolInfo(){
	deshabilitarBotonesTramSolInfo();
	$("#formData").attr("action", "inicioConsultaInteve").trigger("submit");
}

function desasignarTasa(){
	var codigos = getChecksMarcados("listaChecksSolInfoVehiculos");
	if(codigos == null || codigos == ""){
		alert("Debe seleccionar alguna solicitud para poder eliminarla.")
	} else {
		deshabilitarBotonesTramSolInfo();
		habilitarCampos();
		pestania = obtenerPestaniaSeleccionada();
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "desasignarTasaAltaTramSolInformacion#"+pestania).trigger("submit");
	}
}

function eliminarSolInfoVehiculo(){
	var codigos = getChecksMarcados("listaChecksSolInfoVehiculos");
	if(codigos == null || codigos == ""){
		alert("Debe seleccionar alguna solicitud para poder eliminarla.")
	} else {
		deshabilitarBotonesTramSolInfo();
		habilitarCampos();
		pestania = obtenerPestaniaSeleccionada();
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarSolicitudesAltaTramSolInformacion#"+pestania).trigger("submit");
	}
}

function modificarSolInfoVehiculo(){
	var numMarcados = getNumChecksSeleccionados("listaChecksSolInfoVehiculos");
	
	if(numMarcados == 0){
		alert("Debe seleccionar alguna solicitud para poder modificarla.")
	} else if(numMarcados == 1){
		var $dest = $("#divDetalleSolInfoVehiculo");
		deshabilitarBotonesTramSolInfo();
		var codigo = getChecksMarcados("listaChecksSolInfoVehiculos");
		$.ajax({
			url:"modificarSolInfoVehiculoAjaxSolInformacion.action#Vehiculos",
			data:"idTramiteSolInfo="+ codigo +"&idContrato="+$("#idContratoTramSolInfo").val(),
			type:'POST',
			success: function(data, xhr, status){
				$("#idCodTasaTramSolInfo").find('option').remove();
				$("#idCodTasaTramSolInfo").append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
				$dest.empty().append(data);
				$("input[name='listaChecksSolInfoVehiculos']").prop('checked',false);
				habilitarBotonesTramSolInfo();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar la solicitud.');
		        $("input[name='listaChecksSolInfoVehiculos']").prop('checked',false);
		        habilitarBotonesTramSolInfo();
		    }
		});
	} else {
		alert("Solo se puede modificar una solicitud")
	}
}

function recargarTasas(){
	var $dest = $("#idCodTasaTramSolInfo");
	$.ajax({
		url:"recuperarTasasAjaxSolInformacion.action",
		data:"idContrato="+ $("#idContratoTramSolInfo").val(),
		type:'POST',
		success: function(data){
			$dest.find('option').remove();
			$dest.append('<option value="">Seleccione Código de Tasa</option>').val('whatever');
			var valor = data.split("||");
			if(valor != null && valor != ""){
				for(var i=0;i<valor.length;i++){
					var tasa = valor[i].split(";");
					$dest.append('<option value="'+tasa[0]+'">'+tasa[1]+'</option>');
				}
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las tasas del contrato.');
	    }
	});
}

function getChecksMarcados(name){
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

function deshabilitarBotonesTramSolInfo(){
	$("#idGuardarTramSolInfo").prop('disabled', true);
	$("#idGuardarTramSolInfo").css('color', '#6E6E6E');	
	$("#idGenerarXmlTramSolInfo").prop('disabled', true);
	$("#idGenerarXmlTramSolInfo").css('color', '#6E6E6E');	
	$("#idTramitarXmlTramSolInfo").prop('disabled', true);
	$("#idTramitarXmlTramSolInfo").css('color', '#6E6E6E');
	$("#idImprimirTramSolInfo").prop('disabled', true);
	$("#idImprimirTramSolInfo").css('color', '#6E6E6E');
	$("#idReinEstadoTramSolInfo").prop('disabled', true);
	$("#idReinEstadoTramSolInfo").css('color', '#6E6E6E');
	$("#idVolverTramSolInfo").prop('disabled', true);
	$("#idVolverTramSolInfo").css('color', '#6E6E6E');
	$("#idEliminarSolInfoVehiculo").prop('disabled', true);
	$("#idEliminarSolInfoVehiculo").css('color', '#6E6E6E');
	$("#idModificarSolInfoVehiculo").prop('disabled', true);
	$("#idModificarSolInfoVehiculo").css('color', '#6E6E6E');
	$("#idLimpiarSolInfoVehiculo").prop('disabled', true);
	$("#idLimpiarSolInfoVehiculo").css('color', '#6E6E6E');
	
	$("#idDesasignarTasa").prop('disabled', true);
	$("#idDesasignarTasa").css('color', '#6E6E6E');
}

function habilitarBotonesTramSolInfo(){
	$("#idGuardarTramSolInfo").prop('disabled', false);
	$("#idGuardarTramSolInfo").css("color","#000000");	
	$("#idGenerarXmlTramSolInfo").prop('disabled', false);
	$("#idGenerarXmlTramSolInfo").css('color', '#000000');	
	$("#idTramitarXmlTramSolInfo").prop('disabled', false);
	$("#idTramitarXmlTramSolInfo").css('color', '#000000');
	$("#idImprimirTramSolInfo").prop('disabled', false);
	$("#idImprimirTramSolInfo").css('color', '#000000');
	$("#idReinEstadoTramSolInfo").prop('disabled', false);
	$("#idReinEstadoTramSolInfo").css('color', '#000000');
	$("#idVolverTramSolInfo").prop('disabled', false);
	$("#idVolverTramSolInfo").css('color', '#000000');
	$("#idEliminarSolInfoVehiculo").prop('disabled', false);
	$("#idEliminarSolInfoVehiculo").css('color', '#000000');
	$("#idModificarSolInfoVehiculo").prop('disabled', false);
	$("#idModificarSolInfoVehiculo").css('color', '#000000');
	$("#idLimpiarSolInfoVehiculo").prop('disabled', false);
	$("#idLimpiarSolInfoVehiculo").css('color', '#000000');
	
	$("#idDesasignarTasa").prop('disabled', false);
	$("#idDesasignarTasa").css('color', '#000000');
}

function habilitarCampos(){
	$("#idNumExpTramSolInfo").prop('disabled', false);
	$("#idEstadoTramSolInfo").prop('disabled', false);
	$("#idEstadoTramSolInfoVehiculos").prop('disabled', false);
}

function marcarTodosSolInfoVehiculos(){
	if($("#checkTodasSolInfoVehiculos").is(':checked')){
		$("input[name='listaChecksSolInfoVehiculos']").prop('checked',true);
	} else{
		$("input[name='listaChecksSolInfoVehiculos']").prop('checked',false);
	}
}

function guardarFacturacion() {
	doPost("formData", "guardarTitularFacturacionAltaTramSolInformacion.action");
}

/** Obtiene un interviniente por NIF, en Solicitudes** */
function buscarIntervinienteFacturacionInteve() {
	var $nif = $('#nifFacturacion').attr('value').trim();
	if ($nif != "") {
		var $url = "buscarIntervinienteAltaTramSolInformacion.action?nifBusqueda="
				+ $nif
				+ "&tramiteInteveDto.numColegiado="
				+ $('#numColegiado').attr('value')
				+"&tramiteInteveDto.numExpediente="
				+ $('#idNumExpTramSolInfo').attr('value');
		doPostWithTarget("facturacionDiv", $url, null);
	}
}

/********CONSULTA TRAMITES INTEVE***********/

function consultaInteve(){
	doPost('formData', 'buscarConsultaInteve.action');
}

function limpiarFormularioInteve(){
	
	$("#matricula").val("");
	$("#bastidor").val("");
	$("#idEstadoTramite").val("");
	$("#numExpediente").val("");
	$("#numColegiado").val("");
	$("#diaInicio").val("");
	$("#mesInicio").val("");
	$("#anioInicio").val("");
	$("#diaFin").val("");
	$("#mesFin").val("");
	$("#anioFin").val("");
	$("#idContrato").val("");
	$("#codTasa").val("");
	
}

function generarXML(){
	
	var checks = document.getElementsByName("listaChecksConsultasInteve");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	
	$('#codSeleccionados').attr("value",codigos);
	$('#formData').attr("action","generarXMLConsultaInteve.action");
	$('#formData').submit();
}


function descargarInformes(){
	
	var checks = document.getElementsByName("listaChecksConsultasInteve");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	
	$('#codSeleccionados').attr("value",codigos);
	$('#formData').attr("action","descargarInformesConsultaInteve.action");
	$('#formData').submit();
}


function descargarXMLExterno(){
	
	var checks = document.getElementsByName("listaChecksConsultasInteve");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	
	$('#codSeleccionados').attr("value",codigos);
	$('#formData').attr("action","descargarXMLConsultaInteve.action");
	$('#formData').submit();
}



function cambiarEstado() {
	if (getNumChecksSeleccionados('listaChecksConsultasInteve') == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
	window.open('abrirPopUpConsultaInteve.action','popup','width=400,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

// Función invocada desde el padre del pop up de cambios de estados de tramites
function invokeCambiarEstado(estado) {
	var checks = document.getElementsByName("listaChecksConsultasInteve");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	$('#valorEstadoCambio').attr("value",estado);
	
	$('#formData').attr("action","cambiarEstadosConsultaInteve.action?valorEstadoCambio="+estado+"&codSeleccionados="+codigos);
	$('#formData').submit();
	
}


function abrirEvolucionInteve(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alguna consulta para poder obtener su evolucion.");
	}
	var $dest = $("#"+destino);
	var url = "cargarEvolucionInteve.action?numExpediente=" + numExpediente;
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
/********CONSULTA TRAMITES INTEVE***********/