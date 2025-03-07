function iniciarProcesando(idBoton,idImagen) {
	document.getElementById(idBoton).disabled = "true";
	document.getElementById(idBoton).style.color = "#6E6E6E";
	document.getElementById(idBoton).value = "Procesando...";
	document.getElementById(idImagen).style.display = "block";
	$('input[type=button]').attr('disabled', true);
}

function ocultarLoadingConsultarLic(boton, value) {
	document.getElementById("bloqueLoadingconsultaLicenciasCam").style.display = "none";
	boton.value = value;
	desbloqueaBotonesConsulta();
}


function desbloqueaBotonesConsulta() {
	var inputs = document.getElementsByTagName('input');
	for ( var i = 0; i < inputs.length; i++) {

		var input = inputs[i];

		if (input.type == 'button') {
			input.disabled = false;
			input.style.color = '#000000';
		}
	}
}


function doConfirmPost(message, idForm, url, dest, success) {
	if (confirm(message)) {
		doPost(idForm, url, dest, success);
	}
}

function trimCamposInputs(){
	 $('input[type="text"]').blur(function(){
	     this.value = $.trim(this.value);
	 });
}

function marcarTodos(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
  	if(!arrayObjChecks.length){ 
		arrayObjChecks.checked = marcar;
  	}
    for (var i=0; i< arrayObjChecks.length; i++) {
    	arrayObjChecks[i].checked = marcar;
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
function buscarpersonaNotLc(nifFacturacion){
	
	$.ajax({
		url:"buscarPersonaFacturacion.action",
		data:"nif="+$("#"+ nifFacturacion).val(),
		dataType:'json',
		type:'POST',
		success: function(data){
			
			var datosFact = data['datosFacturacion'];
			
			$("#tipoSujeto").val(datosFact.persona.estado);
			$("#nombre").val(datosFact.persona.nombre);
			$("#apellido1").val(datosFact.persona.apellido1RazonSocial);
			$("#apellido2").val(datosFact.persona.apellido2);
			$("#idProvinciaFacturacion").val(datosFact.direccion.municipio.idProvincia);
			recargarComboMunicipios('idProvinciaFacturacion','idMunicipioFacturacion','municipioSeleccionadoFacturacion');
			$("#idMunicipioFacturacion").val(datosFact.direccion.municipio.idMunicipio);
			$("#municipioSeleccionadoFacturacion").val(datosFact.direccion.municipio.idMunicipio);
			recargarComboPueblos('idProvinciaFacturacion','municipioSeleccionadoFacturacion','idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
			$("#idPuebloFacturacion").val(datosFact.direccion.municipio.nombre);
			$("#puebloSeleccionadoFacturacion").val(datosFact.direccion.municipio.nombre);
			$("#tipoViaSeleccionadaFacturacion").val(datosFact.direccion.tipoVia.idTipoVia);
			recargarComboTipoVia('idProvinciaFacturacion','tipoViaSeleccionadaFacturacion','tipoViaSeleccionadaFacturacion');
			$("#cpFacturacion").val(datosFact.direccion.codPostal);
			$("#nombreViaFacturacion").val(datosFact.direccion.nombreVia);
			$("#numeroDireccionFacturacion").val(datosFact.direccion.numero);
			$("#letraDireccionFacturacion").val(datosFact.direccion.letra);
			$("#escaleraDireccionFacturacion").val(datosFact.direccion.escalera);
			$("#pisoDireccionFacturacion").val(datosFact.direccion.piso);
			$("#puertaDireccionFacturacion").val(datosFact.direccion.puerta);
			$("#bloqueDireccionFacturacion").val(datosFact.direccion.bloque);
			$("#kmDireccionFacturacion").val(datosFact.direccion.bloque);
			$("#hmDireccionFacturacion").val(datosFact.direccion.hm);
			$("#tipoViaFacturacion").val(datosFact.direccion.tipoVia.idTipoVia);
			$("#datosFacturacionIdDireccion").val(datosFact.direccion.idDireccion);
			
			recargarNombreVias('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'tipoViaSeleccionadaFacturacion','nombreViaFacturacion', viaFacturacionDuplicado);
		}
	});
}

function mostrarDivInfoEdificioBajaLic() {
	
	var tipoDemoliciom = $("#tipoDemolicion").val();
	var industrial = $("#industrial").val();
	var pestania = obtenerPestaniaSeleccionada();
	var $form = $("#formData");

	if(tipoDemoliciom == null || tipoDemoliciom == ""){
		return alert("El tipo de demolición de la Edificación Baja Licencia es obligatorio");
	}else if(industrial == null || industrial == ""){
		return alert("El campo Industrial de la Edificación Baja Licencia es obligatorio");
	}
	
	var url = "cargarDivInfoEdificioBajaLicenciaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
	
}

function mostrarDivDatosPantasBaja() {
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "cargarDivPlantaBajaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
}

function mostrarDivInfoEdificioAltaLic() {
	
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "cargarDivInfoEdificioAltaLicenciaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
}

function mostrarDivDatosPortalAlta() {
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "cargarDivPortalAltaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
}

function mostrarDivDatosPlantasAlta() {
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "cargarDivPlantaAltaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
}

function mostrarDivSuperficieNoComputablePlanta() {
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "cargarDivSupNoComputablePlantaAltaSolicitudLicencia.action#" + pestania;
	$form.attr("action", url).trigger("submit");
}

function guardarSolicitudLC(){
	var pestania = obtenerPestaniaSeleccionada();
	var url = "guardarAltaSolicitudLicencia.action" + '#' + pestania
	$('input[type=button]').attr('disabled', true);
	$("#formData").attr("action", url).trigger("submit");
}

function validarDireccion(tipoInterviniente){
	$('input[type=button]').attr('disabled', true);
	url = "inicioValidarDireccion.action?tipoInterviniente=" + tipoInterviniente;
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
				height: 300,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
						$('input[type=button]').attr('disabled', false);
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

function guardarPlantaBaja(){
	var pestania = obtenerPestaniaSeleccionada();
	
	var url = "aniadirDatosPlantaBajaAltaSolicitudLicencia.action" + '#' + pestania

	$('input[type=button]').attr('disabled', true);
	
	$("#formData").attr("action", url).trigger("submit");
}

function cargarListaPueblosCam(provincia, municipio, pueblo){
	var $dest = $("#"+pueblo);
	$.ajax({
		url:"cargarPuebloAjaxLc.action",
		data:"provincia="+ $("#"+provincia).val() +"&municipio=" +$("#"+municipio).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
				var listPueblo = data.split("_");
				$.each(listPueblo,function(i,o){
					 $dest.append($('<option>', { 
					        value: listPueblo[i],
					        text : listPueblo[i]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los pueblos.');
	    }
	});
}

function abrirEvolucion(numExpediente, destino){
	if(numExpediente == null || numExpediente == ""){
		return alert("Debe seleccionar alguna consulta para poder obtener su evolución.");
	}
	var $dest = $("#"+destino);
	var url = "inicioEvolucionLicencia.action?numExpediente=" + numExpediente;
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
				width: 900,height: 500,
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

function cargarListaMunicipiosCam(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxLc.action",
		data:"provincia="+ $("#"+provincia).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
				var listMuni = data.split("|");
				$.each(listMuni,function(i,o){
					var municipio = listMuni[i].split("_");
					 $dest.append($('<option>', { 
					        value: municipio[0],
					        text : municipio[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
	    }
	});
}


function buscarpersona(tipo, dest, tipoDni){
	if(nif != ""){
		var destino = $("#"+dest);
		var dni = $("#"+tipoDni);
		var numColegiado = $("#numColegiadoLic");
		var $dest = $(destino, parent.document);
		$.ajax({
			url:"buscarPersonaAjaxLc.action#IntervinienteLC",
			data:"nif="+ dni.val() + "&tipoInterviniente="+ tipo + "&numColegiado="+ numColegiado.val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el sujeto pasivo.');
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del Interesado Principal debe rellenar el NIF/CIF.");
	}
}

function comprobarTitular(){
	if($("#utilizarTitular").is(':checked') == false){
		deshabilitarDiv("divDatosNotificacion");
		limpiarDiv("divDatosNotificacion");
	}else{
		habilitarDiv("divDatosNotificacion");
	}
}

function incluirFichero() {
	if (selectVacio("idTipoDocumentoLic")) {
		alert("El tipo de documento es obligatorio");
	} else {
		iniciarProcesando("botonSubirFichero","loading3Image");
		var pestania = obtenerPestaniaSeleccionada();
		document.formData.action="incluirFicheroAltaSolicitudLicencia.action#" + pestania;
		document.formData.submit();
	}
}

function enviarDocumentacion() {
	$('input[type=button]').attr('disabled', true);
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="enviarDocumentacionAltaSolicitudLicencia.action#" + pestania;
	document.formData.submit();
}

function eliminarFichero(idFichero){
	if (confirm("¿Está seguro de que desea borrar este fichero?")) {
		var pestania = obtenerPestaniaSeleccionada();
		
		document.formData.idFicheroEliminar.value = idFichero;
		document.formData.action="eliminarFicheroAltaSolicitudLicencia.action#" + pestania;
		document.formData.submit();
	}
}

function descargarDocumentacion(nombre) {
	
	limpiarHiddenInput("nombreDoc");
		
	var input = $("<input>").attr("type", "hidden").attr("name", "nombreDoc").val(nombre);
	$('#formData').append($(input));
	
	var pestania = obtenerPestaniaSeleccionada();
	
	$("#formData").attr("action", "descargarDocumentacionAltaSolicitudLicencia.action#" + pestania).trigger("submit");

}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function deshabilitarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr('readonly', 'true');
	$("#" + idDiv + " select").attr('disabled','true');
	$("#" + idDiv + " input[type=button]").attr('disabled','true');
	if (idDiv != "divDatosNotificacion") {
		$("#" + idDiv + " input[type=checkbox]").attr('disabled','true');
	}
}

function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
	$("#" + idDiv + " input[type=checkbox]").attr("checked", false);
}

function habilitarDiv(idDiv){
	$("#" + idDiv + " input[type=text]").removeAttr('readonly');
	$("#" + idDiv + " select").removeAttr('disabled');
	$("#" + idDiv + " input[type=button]").removeAttr('disabled');
	$("#" + idDiv + " input[type=checkbox]").removeAttr('disabled');
}


function validarAltaSolicitudLicencia(){
	var pestania = obtenerPestaniaSeleccionada();
	$('input[type=button]').attr('disabled', true);
	$("#formData").attr("action", "validarAltaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function comprobar(){
	var pestania = obtenerPestaniaSeleccionada();
	$('input[type=button]').attr('disabled', true);
	$("#formData").attr("action", "comprobarAltaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function enviar(){
	var pestania = obtenerPestaniaSeleccionada();
	$('input[type=button]').attr('disabled', true);
	$("#formData").attr("action", "enviarAltaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function consultar(){
	var pestania = obtenerPestaniaSeleccionada();
	$('input[type=button]').attr('disabled', true);
	$("#formData").attr("action", "consultarAltaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function presentar(){
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "presentarAltaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function validarConsultaSolicitudLicencia(){
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "validarConsultaSolicitudLicencia.action#"+pestania).trigger("submit");
}

function seleccionarPais(nomPais){
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "seleccionarPaisValidarDireccion.action?nomPais=" + nomPais + "#" + pestania;
	$form.attr("action", url).trigger("submit");
	$("#divEmergentePopUp").dialog("close");
}

function seleccionarProvincia(nomProvincia){
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "seleccionarProvinciaValidarDireccion.action?nomProvincia=" + nomProvincia + "#" + pestania;
	$form.attr("action", url).trigger("submit");
	$("#divEmergentePopUp").dialog("close");
}

function seleccionarPoblacion(nomPoblacion){
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "seleccionarPoblacionValidarDireccion.action?nomPoblacion=" + nomPoblacion + "#" + pestania;
	$form.attr("action", url).trigger("submit");
	$("#divEmergentePopUp").dialog("close");
}

function seleccionarVial(nomVial){
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "seleccionarVialValidarDireccion.action?nomVial=" + nomVial + "#" + pestania;
	$form.attr("action", url).trigger("submit");
	$("#divEmergentePopUp").dialog("close");
}

function seleccionarNumero(numero){
	var $form = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	var url = "seleccionarNumeroValidarDireccion.action?numero=" + numero + "#" + pestania;
	$form.attr("action", url).trigger("submit");
	$("#divEmergentePopUp").dialog("close");
}

function abrirVentanaSeleccionEstados(){
	var checks =  $("input[name='listaChecksLicencias']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
	
	iniciarProcesando("idCambiarEstado","loadingImage");
	
	url = "abrirSeleccionEstadoConsultaSolicitudLicencia.action"
	var $dest = $("#divEmergenteconsultaLicenciasCam");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-400', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 200
				},
				dialogClass : 'no-close',
				width: 300,
				height: 250,
				buttons : {
					Seleccionar : function() {
						var estado = document.getElementById("estado").value;
						doPost('formData', "cambiarEstadoConsultaSolicitudLicencia.action?nuevoEstado=" + estado, '', '');
					},
					Cancelar : function() {
						$(this).dialog("close");
						ocultarLoadingConsultarLic(document.getElementById('idCambiarEstado'),'Cambiar Estado');
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

function mostrarBloqueValidados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueValidados").is(":visible")){
		$("#bloqueValidados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueValidados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueValidados").hide();
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

function validarBloque(){
	var valueBoton = $("#bValidarLC").val();
	var codigos = "";
	$("input[name='listaChecksLicencias']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoadingModelo('bValidarLC',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para validar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea validar los modelos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "validarConsultaSolicitudLicencia.action#").trigger("submit");
	}else{
		ocultarLoadingModelo('bValidarLC',valueBoton);
	}
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function ocultarLoadingModelo(boton, value) {
	$('#'+boton).val(value);
	desbloqueaBotonesModelo();
	habilitarBotonesConsultaModelo();
}

function eliminarLicencia(idBoton){
	var checks =  $("input[name='listaChecksLicencias']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
	iniciarProcesando('idEliminar','loadingImage');

	document.formData.action="eliminarLicenciaConsultaSolicitudLicencia.action";
	document.formData.submit();
}

function soloNumeroDecimal(e, campo, enteros, decimales) {
	key = e.keyCode ? e.keyCode : e.which
			if (key == 8) return true
			
			if (key == 9) return true
			
			if (key == 39) return true
			
			if (key == 37) return true

			campo.value = campo.value.replace(',','.');

	if (campo.value != "") {
		if ((campo.value.indexOf(".")) > 0) {

			if (key > 47 && key < 58) {
				if (campo.value == "") return true
				
				var re = new RegExp("[0-9]{" + decimales + "}$");
				return !(re.test(campo.value))
			}
		}
	}

	if (key > 47 && key < 58) {
		if (campo.value == "") return true
		var reg = new RegExp("[0-9]{"+ enteros + "}");
			return !(reg.test(campo.value))
	}

	if (key == 44) {
		if (campo.value == "") return false
		regexp = /^[0-9]+$/
			return regexp.test(campo.value)
	}

	if (key == 46) {
		if (campo.value == "") return false
		regexp = /^[0-9]+$/
			return regexp.test(campo.value)
	}

	return false
}

function validaEmail(email) {
    var re  = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
    if (!re.test(email)) {
    	alert("El email introducido no es v\u00e1lido.\nPor favor repase los datos.");
        return false;
    }
    return true;
}


$(document).ready(function() {
	tratarDivSegunCheck('hayInstalacionesRelevantes','divEquiposInstaladosRelevantes');
	tratarDivSegunCheck('climatizacionAc','divClimatizacionAireAcondicionado');
	tratarDivSegunCheck('ventilacionForzada','divVentilacionForzada');
	tratarDivSegunCheck('aguaCalienteSanitaria','divAguaCalienteSanitaria');
	tratarDivSegunCheck('instalacionesCalefaccion','divInstalacionesCalefaccion');
	tratarDivSegunCheck('captEnergiaSolar','divCaptacionEnergiaSolar');
	
	tratarCampoSegunCheck('andamios','duracionAndamios');
	tratarCampoSegunCheck('vallas','duracionValla');
	tratarCampoSegunCheck('instGrua','duracionGrua');
	tratarCampoSegunCheck('contenedor','duracionContenedor');
	
	ckeckAccesoPrincipal('accesoPrincipalIgual','divAccesoPrincipalIgual')
	comprobarTitular();
});

function ckeckAccesoPrincipal(check, div) {
	check = $('#'+check);
	if (check.is(':checked')) {
		limpiarDiv(div);
		deshabilitarDiv(div);
	} else {
		habilitarDiv(div);
	}
}

function tratarDivSegunCheck(check, div) {
	check = $('#'+check);
	if (check.is(':checked')) {
		habilitarDiv(div);
	} else {
		limpiarDiv(div);
		deshabilitarDiv(div);
	}
}

function tratarCampoSegunCheck(check, idCampo) {
	check = $('#'+check);
	if (check.is(':checked')) {
		$("#" + idCampo).removeAttr('disabled');
	} else {
		$("#" + idCampo).attr("value", "");
		$("#" + idCampo).attr('disabled', 'true');
	}
}

function comprobarTamanioTextArea(e, tamanio){
	var texto = (e.clipboardData || windows.clipboardData).getData('text');
	var length = texto.length;
	if (length > tamanio) {
		alert("No se puede poner más de 600 caracteres.");
		return false;
	}
}