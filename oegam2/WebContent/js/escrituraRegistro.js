
function mostrarBien() {
	var inmatriculada = document.getElementById("inmatriculada").value;
	if (inmatriculada == 'S') {
		document.getElementById('pestaniaBienes').style.display = "block";
		document.getElementById('pestaniaBienes').visible = true;
		mostrarDivsTipoBien();
	} else {
		limpiarDiv('inmuebles');
		document.getElementById('pestaniaBienes').style.display = "none";
		document.getElementById('pestaniaBienes').visible = false;

	}
}


function eliminarInmueblesPorExpediente() {
	var numExpediente = document.getElementById('idTramiteRegistro').value;
	if (numExpediente != null && numExpediente != "") {
		var pestania = obtenerPestaniaSeleccionada();
		document.formData.action="eliminarInmueblesPorExpedienteTramiteRegistroMd6.action?#" + pestania;
		document.formData.submit();
	}
}

function guardarEscritura(){
	if($('#idTipoBien option:selected').val() == "URBANO"){
		if($("#idTipoInmuebleUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		if($("#idProvinciaBienUrbano",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienUrbano",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
		
		if($("#idTipoVia",parent.document).val() == "-1" || $("#tipoViaSeleccionadaUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de vía para la dirección del bien.");
		}
		if($("#idNombreViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el nombre de la vía del bien.");
		}
		if($("#idNumeroViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el número de la vía del bien.");
		}
		$("#idBienUrbanoDto",parent.document).prop("disabled", false);
		$("#idVHabitual", parent.document).prop("disabled", false);
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		if($("#idTipoInmuebleRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		
		if($("#idUsoRusticoGanaderia",parent.document).val() == "" && $("#idUsoRusticoCultivo",parent.document).val() == "" && $("#idUsoRusticoOtros",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de uso para poder guardar el bien.");
		}
		if($("#idSuperficieRustico",parent.document).val() == ""){
			return alert("Debe rellenar la superficie del bien.");
		}
		if( $("#idUsoRusticoCultivo",parent.document).val() != ""){
			if($("#idUnidadMetricaRustico",parent.document).val() == ""){
				return alert("Debe seleccionar alguna Unidad M\u00E9trica para poder guardar el bien.");
			}
		}
		if($("#idProvinciaBienRustico",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienRustico",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
	}

	var pestania = obtenerPestaniaSeleccionada();
	iniciarProcesando("botonGuardarEscritura","loadingImage");
	document.formData.action="guardarTramiteRegistroMd6.action?#" + pestania;
	document.formData.submit();
}

function mostrarResumenSinIfs(){
	document.getElementById('pestaniaResumen').style.display="block";
	document.getElementById('pestaniaResumen').visible = true;
}

function seleccionadaIdentificacion1(){
	var checkBox = document.getElementById('identificacionNotarial');
	if(checkBox.checked){
		var checkBox2 = document.getElementById('numeroEntrada');
		checkBox2.checked = false;
		desactivarCamposNumeroEntrada();
		activarCamposIdentificacionNotarial();
	}else{
		desactivarCamposIdentificacionNotarial();
	}
}

function seleccionadaIdentificacion2(){
	var checkBox = document.getElementById('numeroEntrada');
	if(checkBox.checked){
		var checkBox2 = document.getElementById('identificacionNotarial');
		checkBox2.checked = false;
		desactivarCamposIdentificacionNotarial();
		activarCamposNumeroEntrada();
	}else{
		desactivarCamposNumeroEntrada();
	}
}

function desactivarCamposIdentificacionNotarial(){
	var etCodNotaria = document.getElementById('etCodNotaria');
	var etCodNotario = document.getElementById('etCodNotario');
	var etAnyoProtocolo = document.getElementById('etAnyoProtocolo');
	var etProtocolo = document.getElementById('etProtocolo');
	var etBusqueda = document.getElementById('etBusqueda');
	etCodNotaria.className = "gris";
	etCodNotario.className = "gris";
	etAnyoProtocolo.className = "gris";
	etProtocolo.className = "gris";
	etBusqueda.className = "gris";
	var teCodNotaria = document.getElementById('teCodNotaria');
	var teCodNotario = document.getElementById('codigoNotario');
	var teAnyoProtocolo = document.getElementById('teAnyoProtocolo');
	var teProtocolo = document.getElementById('teProtocolo');
	var boton = document.getElementById('botonNotario');
	teCodNotaria.disabled = true;
	teCodNotario.disabled = true;
	teAnyoProtocolo.disabled = true;
	teProtocolo.disabled = true;
	boton.disabled = true;
	teCodNotaria.value = "";
	teCodNotario.value = "";
	teAnyoProtocolo.value = "";
	teProtocolo.value = "";
}

function activarCamposIdentificacionNotarial(){
	var etCodNotaria = document.getElementById('etCodNotaria');
	var etCodNotario = document.getElementById('etCodNotario');
	var etAnyoProtocolo = document.getElementById('etAnyoProtocolo');
	var etProtocolo = document.getElementById('etProtocolo');
	var etBusqueda = document.getElementById('etBusqueda');
	etCodNotaria.className = "negro";
	etCodNotario.className = "negro";
	etAnyoProtocolo.className = "negro";
	etProtocolo.className = "negro";
	etBusqueda.className = "negro";
	var teCodNotaria = document.getElementById('teCodNotaria');
	var teCodNotario = document.getElementById('codigoNotario');
	var teAnyoProtocolo = document.getElementById('teAnyoProtocolo');
	var teProtocolo = document.getElementById('teProtocolo');
	var boton = document.getElementById('botonNotario');
	teCodNotaria.disabled = false;
	teCodNotario.disabled = false;
	teAnyoProtocolo.disabled = false;
	teProtocolo.disabled = false;
	boton.disabled = false;
}

function desactivarCamposNumeroEntrada(){
	var etAnyo = document.getElementById("etAnyoEntrada");
	var etNumero = document.getElementById("etNumeroEntrada");
	etAnyo.className = "gris";
	etNumero.className = "gris";
	var teAnyo = document.getElementById("teAnyoEntrada");
	var teNumero = document.getElementById("teNumeroEntrada");
	teAnyo.disabled = true;
	teNumero.disabled = true;
	teAnyo.value = "";
	teNumero.value = "";
}

function activarCamposNumeroEntrada(){
	var etAnyo = document.getElementById("etAnyoEntrada");
	var etNumero = document.getElementById("etNumeroEntrada");
	etAnyo.className = "negro";
	etNumero.className = "negro";
	var teAnyo = document.getElementById("teAnyoEntrada");
	var teNumero = document.getElementById("teNumeroEntrada");
	teAnyo.disabled = false;
	teNumero.disabled = false;
}

function mostrarIdentificacion() {
	var checkPrimera = document.getElementById('primera');
	var checkSub = document.getElementById('subsanacion');
	if(checkSub.checked) {
		seleccionadaSubsanacion();
	} else if (checkPrimera.checked) {
		seleccionadaPrimera();
	} else {
		seleccionadaPrimera();
	}
}

function estadoIdentificacion(){
	if(document.getElementById("identificacionNotarial").checked) {
		seleccionadaIdentificacion1();
	}
	if(document.getElementById("numeroEntrada").checked) {
		seleccionadaIdentificacion2();
	}
}

function abrirVentanaNotario(){
	
	var url = "inicioNotarioReg.action";
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
				height: 500,
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

var ventanaBienRustico;
var ventanaBienUrbano;
var ventanaBienOtro;

function buscarBienes(){
	var tipoBien = document.getElementById("idTipoBien").value;
	if("" == tipoBien) {
		alert("Debe indicar un tipo de bien a buscar")
	} else if("RUSTICO" == tipoBien){
		abrirVentanaBienRusticoPopUp();
	}else if("URBANO" == tipoBien){
		abriVentanaBienUrbanoPopUp();
	} else if("OTRO" == tipoBien){
		abrirVentanaBienOtroPopUp();
	}
}

function mostrarDivsTipoBien(){
	
	var tipoBien =document.getElementById('idTipoBien').value;  
	if("RUSTICO" == tipoBien){
		habilitarDiv('divBienRustico');
		deshabilitarCamposUsoBienRustico()
		deshabilitarDiv('divBienUrbano');
		deshabilitarDiv('divOtroBien');
		$('#divBienUrbano').hide();
		$('#divBienRustico').show();
		$('#divOtroBien').hide();
	}else if("URBANO" == tipoBien){
		deshabilitarDiv('divBienRustico');
		habilitarDiv('divBienUrbano');
		deshabilitarDiv('divOtroBien');
		$('#divBienUrbano').show();
		$('#divBienRustico').hide();
		$('#divOtroBien').hide();
	} else if("OTRO" == tipoBien){
		deshabilitarDiv('divBienRustico');
		deshabilitarDiv('divBienUrbano');
		habilitarDiv('divOtroBien');
		$('#divBienUrbano').hide();
		$('#divBienRustico').hide();
		$('#divOtroBien').show();
		$('#idTipoInmuebleOtro').val("OT");
	} else if("" == tipoBien){
		deshabilitarDiv('divBienRustico');
		deshabilitarDiv('divBienUrbano');
		deshabilitarDiv('divOtroBien');
		$('#divBienUrbano').hide();
		$('#divBienRustico').hide();
		$('#divOtroBien').hide();
	}
}

function habilitarVHabitual(selectVH){
	var opcion = $("#"+selectVH).val();
	if(opcion == "VI" || opcion == "VU" || opcion == "VH" || opcion == "PG" || opcion == "CT"){
		$("#idVHabitual", parent.document).prop("disabled", false);
	}else{
		$("#idVHabitual").prop('checked',false);
		$("#idVHabitual", parent.document).prop("disabled", true);
	}
}

function abrirVentanaBienRusticoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarDiv('formularioInmuebles');
	ventanaBienRustico = window.open('inicioConsultaBienRustico.action', 'popUp', 'width=850,height=600,top=150,left=280,resizable=NO,status=NO,menubar=NO');
	ventanaBienRustico.focus();
}

function abriVentanaBienUrbanoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarDiv('formularioInmuebles');
	ventanaBienUrbano = window.open('inicioConsultaBienUrbano.action', 'popUp', 'width=850,height=600,top=150,left=280,resizable=NO,status=NO,menubar=NO');
	ventanaBienUrbano.focus();
}


function abrirVentanaBienOtroPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarDiv('formularioInmuebles');
	ventanaBienOtro = window.open('inicioConsultaBnS.action?esPopup=S', 'popUp', 'width=850,height=600,top=150,left=280,resizable=NO,status=NO,menubar=NO');
	ventanaBienOtro.focus();
}

function invokeSeleccionBienRustico(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#formularioInmuebles");
	$.ajax({
		url:"seleccionarInmuebleRegistradoresAjax.action",
		data:"codigo="+ codigo+ "&tipoBien=RUSTICO",
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			$("#divBienUrbano").hide();
			$("#divOtroBien").hide();
			$("#divBienRustico").show();
			$('#idTipoBien').val("RUSTICO"); 
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos del bien rústico.');
	    }
	});
	ventanaBienRustico.close();
}

function invokeSeleccionBienUrbano(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#formularioInmuebles");
	$.ajax({
		url:"seleccionarInmuebleRegistradoresAjax.action",
		data:"codigo="+ codigo+ "&tipoBien=URBANO",
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			$("#divBienRustico").hide();
			$("#divOtroBien").hide();
			$('#idTipoBien').val("URBANO"); 
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos del bien urbano.');
	    }
	});
	ventanaBienUrbano.close();
}

function invokeSeleccionBienOtro(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#formularioInmuebles");
	$.ajax({
		url:"seleccionarInmuebleRegistradoresAjax.action",
		data:"codigo="+ codigo+ "&tipoBien=OTRO",
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			$("#divBienUrbano").hide();
			$("#divBienRustico").hide();
			$("#divOtroBien").show();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos del bien.');
	    }
	});
	ventanaBienOtro.close();
}

function cargarListaMunicipiosCam(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxBn.action",
		data:"provincia="+ $("#"+provincia).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
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

function cargarListaTipoViaCam(provincia,tipoVia){
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaCamAjaxBn.action",
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Tipo Via</option>').val('whatever');
				var listTpVias = data.split("|");
				$.each(listTpVias,function(i,o){
					var tipoVia = listTpVias[i].split("_");
					 $dest.append($('<option>', { 
					        value: tipoVia[0],
					        text : tipoVia[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los tipos de vias de la provincia.');
	    }
	});
}

function seleccionarUsoRustico(idHiddenTipoUsoRustico, valor){
	var $tipoUsoRustico = $("#" + idHiddenTipoUsoRustico);
	$tipoUsoRustico.val(valor);
	deshabilitarCamposUsoBienRustico();
}

function deshabilitarCamposUsoBienRustico(){
	var $usoGanaderia = $("#idUsoRusticoGanaderia", parent.document);
	var $usoCultivo = $("#idUsoRusticoCultivo", parent.document);
	var $usoOtros = $("#idUsoRusticoOtros", parent.document);
	if($usoGanaderia.val() != ""){
		$usoCultivo.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoCultivo.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoOtros.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoCultivo.prop("disabled", true);
	}else{
		$usoGanaderia.prop("disabled", false);
		$usoCultivo.prop("disabled", false);
		$usoOtros.prop("disabled", false);
	}
}

function seleccionadaPrimera() {
	var checkBox = document.getElementById('primera');
	if(checkBox.checked) {
		var checkBox2 = document.getElementById('subsanacion');
		checkBox2.checked = false;
		var datosRegistrales = document.getElementById("datosRegistrales");
		datosRegistrales.style.display="none";
		desactivarCamposIdentificacionNotarial();
		desactivarCamposNumeroEntrada();
		var checkBox3 = document.getElementById('numeroEntrada');
		checkBox3.checked = false;
		var checkBox4 = document.getElementById('identificacionNotarial');
		checkBox4.checked = false;
	}
}

function seleccionadaSubsanacion() {
	var checkBox = document.getElementById('subsanacion');
	var datosRegistrales = document.getElementById("datosRegistrales");
	if(checkBox.checked){
		var checkBox2 = document.getElementById('primera');
		checkBox2.checked = false;
		datosRegistrales.style.display="inline";
	}else{
		datosRegistrales.style.display="none";
		desactivarCamposIdentificacionNotarial();
		desactivarCamposNumeroEntrada();
		var checkBox3 = document.getElementById('numeroEntrada');
		checkBox3.checked = false;
		var checkBox4 = document.getElementById('identificacionNotarial');
		checkBox4.checked = false;
	}
}

function alertCodigoNotario(){
	alert("Para establecer este dato pulse el bot\u00F3n 'B\u00FAsqueda notario'");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function inscripcionCartaPago(){
	var tipoOperacion =$('#tipoOperacion').val();
	 if (tipoOperacion == 1000){
		 $("#primera").attr('disabled', true);
		 $("#subsanacion").attr('checked', true);

		 $("#identificacionNotarial").attr('checked', true);
		 $("#identificacionNotarial").attr('disabled', false);
		 
		 $("#numeroEntrada").attr('checked', false);
		 $("#numeroEntrada").attr('disabled', true);
		 seleccionadaIdentificacion1();
		 
		 seleccionadaSubsanacion();
		 $("#idFormaPago2").prop("checked", true);
		 $("#idFormaPago2").prop("disabled", false);
		 $("#divIdentificacionCorpme").hide();
		 $("#idFormaPago3").prop("disabled", true);
	 }else{
		 $("#primera").attr('disabled', false);
		 $("#numeroEntrada").attr('disabled', false);
		 $("#idFormaPago3").prop("checked", true);
		 $("#idFormaPago3").prop("disabled", false);
		 $("#divCuentaBancaria").hide();
		 $("#divIdentificacionCorpme").show();
		 $("#idFormaPago2").prop("disabled", true);
	 }
}

