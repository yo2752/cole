var ventanaNotario;
var ventanaBienRustico;
var ventanaBienUrbano;
var ventanaBienOtro;
var opciones_ventana = 'width=850,height=400,top=150,left=280';
var opciones_ventana_bienes = 'width=850,height=600,top=150,left=280';
var input;

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function consultarModelos(){
	document.formData.action = "buscarConsultaMd.action";
	document.formData.submit();
}

function inicializarVentanas(concepto,modelo){
	if($("#"+concepto).val() != ""){
		inicializarVentanasConcepto($("#"+concepto).val(),$("#"+modelo).val());
		cargarExentoNoSujeto($("#"+concepto).val(), 'true');
	}else{
		$("#idValorDeclarado").prop("disabled", true);
		if($("#"+modelo).val()=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
	}
	if($("#idNumExpediente").val() == ""){
		$("#Resumen").hide();
	}
	if($("#idRemesa").val() != null && $("#idRemesa").val() != ""){
		$("#idConceptoDocumento").prop("disabled", true);
		$("#botonNotario").hide();
		$("#idBotonBuscarNifSujPasivo").hide();
		$("#idBotonBuscarNifTransmitente").hide();
		$("#idConsultarBienesUrbano").hide();
		$("#idEliminarBienesUrbano").hide();
		$("#idConsultarBienesRustico").hide();
		$("#idEliminarBienesRustico").hide();
		$("#idEliminarOtrosBienes").hide();
		$("#idBonificaciones").prop("disabled", true);
		//$("#idFundamentoExento").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idNoSujeto").prop("disabled", true);
		$("#idLiquiComp").prop("disabled", true);
	}
	
	$("#tipoDocPRI").prop("disabled", true);
	$("#tipoDocADM").prop("disabled", true);
	$("#tipoDocJUD").prop("disabled", true);
	$("#tipoDocPUB").prop("checked", true);
	if($("#idEstadoModelos").val() != "3"){
		$("#idProtocoloBis").prop("disabled", false);
		$("#idNumProtocolo").prop("readOnly", false);
		$("#botonNotario").show();
	}
	if($("#idLiqManual").is(':checked')){
		habilitarCamposLiquidacion();
	}
}

function seleccionarSexoPorTipoPersona(idTipoPersona,idSexo){
	if($("#"+idTipoPersona).val() == "JURIDICA" || $("#"+idTipoPersona).val() == "JURIDICA_PUBLICA"){
		$("#"+idSexo).val("X");
		$("#"+idSexo).prop("disabled",true);
	}else{
		$("#"+idSexo).val("-1");
		$("#"+idSexo).prop("disabled",false);
	}
}

function cambiarElementosPorPaginaConsultaModelos() {
	
	document.location.href = 'navegarConsultaMd.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaImprimirModelos(){
	document.location.href = 'navegarImpMd.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
}

function limpiarConsultaModelos(){
	$('#idEstadoModelos').val("");
	$('#idModeloModelos').val("");
	$('#idConceptoModelos').val("");
	$('#idCodNotarioModelos').val("");
	$('#idNumExpedienteModelos').val("");
	$('#idNumColegiadoModelos').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
	$('#idNumExpRemesa').val("");
	$('#diaFechaPreFin').val("");
	$('#mesFechaPreFin').val("");
	$('#anioFechaPreFin').val("");
	$('#diaFechaPreIni').val("");
	$('#mesFechaPreIni').val("");
	$('#anioFechaPreIni').val("");
	$('#idReferenciaPropia').val("");
}

function deshabilitarNotarioProtocolo(idForm){	
	var $checkTipoDocPRI = $("#tipoDocPRI");
	var $checkTipoDocADM = $("#tipoDocADM");
	var $checkTipoDocJUD = $("#tipoDocJUD");
	var $checkTipoDocPUB = $("#tipoDocPUB");
	var $protocoloBis = $("#idProtocoloBis");
	var $numProtocolo = $("#idNumProtocolo");
	var $botonNotario = $("#botonNotario");
	if($("#idEstadoModelos").val() != "3"){
		if($checkTipoDocPRI.is(':checked') || $checkTipoDocADM.is(':checked') || $checkTipoDocJUD.is(':checked')){
			$protocoloBis.prop("disabled", true);
			$protocoloBis.prop("checked", false);
			$numProtocolo.prop("readOnly", true);
			$botonNotario.hide();
			$('#idCodNotario').val("");
			$('#idNotarioNombre').val("");
			$('#idNotarioApellidos').val("");
		}else if($checkTipoDocPUB.is(':checked')){
			$protocoloBis.prop("disabled", false);
			$numProtocolo.prop("readOnly", false);
			$botonNotario.show();
		}else{		
			$protocoloBis.prop("disabled", true);
			$numProtocolo.prop("readOnly", true);
			$botonNotario.hide();
			$('#idCodNotario').val("");
			$('#idNotarioNombre').val("");
			$('#idNotarioApellidos').val("");
		}
	}
}

function recargarExpAbreviadaConcepto(concepto,expAbreviada){
	$("#"+expAbreviada).val($("#"+concepto).val());
	if($("#"+concepto).val() == ""){
		 $("#Bienes").hide();
	}
}

function cargarExpAbreviadaConcepto(concepto,expAbreviada,modelo){
	$("#"+expAbreviada).val(concepto.value);
	if(concepto.value == ""){
		 $("#Bienes").hide();
	}else{
		inicializarVentanasConcepto(concepto.value,$("#"+modelo).val());
	}
	if($("#idExprAbreviada").val() == "AU0"){
		alert("No es necesaria la presentación de autoliquidación si el alquiler dedicado a vivienda" +
				"es inferior de 1250€/mensuales");
	}
	cargarExentoNoSujeto(concepto.value, 'false');
}

function inicializarVentanasConcepto(concepto,modelo){
	$.ajax({
		url:"getGrupoValidacionAjaxMd.action#Documento",
		data:"codigo="+ concepto,
		type:'POST',
		dataType: 'json',
		success: function(data){
			habilitarVentanasPorConcepto(data,modelo);
		},
		error : function(xhr, status) {
	    }
	});
}

function habilitarExentoNoSujeto(descripcion, esInicio){
	if (!descripcion=="" && descripcion == "S"){
		$("#idNoSujeto").prop("checked", true);
		$("#idNoSujeto").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idExento").prop("checked", false);
		$("#idFundamentoExento").hide();
		$("#idFundamentoNoSujeto").show();
		$("#idFundamentoNoSujeto").prop("disabled", true);
		$("#idFundamentoNoSujeto").val("OP. NO SUJETAS");
	}else if (!descripcion=="" && descripcion == "E"){
		$("#idExento").prop("checked", true);
		$("#idNoSujeto").prop("checked", false);
		$("#idNoSujeto").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idFundamentoExento").show();
		$("#idFundamentoExento").prop("disabled", false);
		$("#idFundamentoNoSujeto").hide();
		if('false' == esInicio){
			$("#idFundamentoExento").val("");
		}
		$("#idFundamentoNoSujeto").val("");
	}else if (!descripcion=="" && descripcion != "S"){
		$("#idNoSujeto").prop("checked", false);
		$("#idNoSujeto").prop("disabled", true);
		$("#idExento").prop("checked", true);
		$("#idExento").prop("disabled", true);
		$("#idFundamentoNoSujeto").hide();
		$("#idFundamentoExento").show();
		$("#idFundamentoExento").prop("disabled", true);
		$("#idFundamentoExento").val(descripcion);
	}else{
		if('false' == esInicio){
			$("#idExento").prop("checked", false);
			$("#idNoSujeto").prop("checked", false);
			$("#idFundamentoExento").show();
			$("#idFundamentoNoSujeto").hide();
			$("#idFundamentoExento").val("");
			$("#idFundamentoNoSujeto").val("");
		}
		$("#idNoSujeto").prop("disabled", false);
		$("#idExento").prop("disabled", false);
		$("#idFundamentoExento").prop("disabled", false);
		
	}
}


function cargarExentoNoSujeto(concepto, esInicio){
	$.ajax({
		url:"getExentoNoSujetoAjaxMd.action#Autoliquidacion",
		data:"codigo="+ concepto,
		type:'POST',
		success: function(data, xhr, status){
			habilitarExentoNoSujeto(data, esInicio);
		},
		error : function(xhr, status) {
	    }
	});
}


function habilitarVentanasPorConcepto(grupoValidacion,modelo){
	switch (grupoValidacion) {
	case 1:
		/*La base imponible y el valor declarado deben de estar rellenos con los cual los habilitamos*/
		$("#Bienes").hide();
		$("#idBaseImponible").prop("readonly", false);
		$("#idValorDeclarado").prop("disabled", false);
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 2:
		/*Habilitamos la renta*/
		$("#Bienes").hide();
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		$("#idValorDeclarado").prop("disabled", false);
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").show();
			deshabilitarPeriodoRenta();
			$("#divDerechosReales").hide();
		}
		break;
	case 3:
		/* Debe de haber bienes rústicos. Habilitamos la pestaña de bienes rusticos*/
		$("#idTipoBien").val("RUSTICO");
		$("#idTipoBien").prop("disabled", true);
		$("#divListadoBienesUrbano").hide();
		$("#divListadoBienesRustico").show();
		$("#divListadoOtrosBienes").hide();
		$("#divOtroBien").hide();
		$("#divBienUrbano").hide();
		$("#divBienRustico").show();
		$("#Bienes").show();
		$("#idValorDeclarado").prop("disabled", true);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 4:
		/* Debe de haber bienes urbanos. Habilitamos la pestaña de bienes urbanos*/
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$("#divListadoBienesRustico").hide();
		$("#divListadoBienesUrbano").show();
		$("#divListadoOtrosBienes").hide();
		$("#divBienRustico").hide();
		$("#divBienUrbano").show();
		$("#divOtroBien").hide();
		$("#Bienes").show();
		$("#idValorDeclarado").prop("disabled", true);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 5:
		/* Habilitamos los derechos reales*/
		$("#Bienes").hide();
		$("#idValorDeclarado").prop("disabled", false);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			calcularBaseImponibleDerReal();
			if($("#idDerechoReal").val() == "-1"){
				$("#idValorDeclarado").prop("disabled", false);
			}else if($("#idDerechoReal").val() != "OTROS"){
				$("#idValorDeclarado").prop("disabled", false);
			}else{
				$("#idValorDeclarado").prop("disabled", true);
			}
		}
		break;
	case 6:
		/* Debe de haber bienes rusticos y derechos reales*/
		$("#idTipoBien").val("RUSTICO");
		$("#idTipoBien").prop("disabled", true);
		$("#divListadoBienesUrbano").hide();
		$("#divListadoBienesRustico").show();
		$("#divListadoOtrosBienes").hide();
		$("#divOtroBien").hide();
		$("#divBienUrbano").hide();
		$("#divBienRustico").show();
		$("#Bienes").show();
		$("#idValorDeclarado").prop("disabled", true);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			calcularBaseImponibleDerReal();
			if($("#idDerechoReal").val() == "-1"){
				$("#idValorDeclarado").prop("disabled", false);
			}else if($("#idDerechoReal").val() != "OTROS"){
				$("#idValorDeclarado").prop("disabled", false);
			}else{
				$("#idValorDeclarado").prop("disabled", true);
			}
		}
		break;
	case 7:
		/* Debe de haber bienes urbanos y derechos reales*/
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$("#divListadoBienesRustico").hide();
		$("#divListadoBienesUrbano").show();
		$("#divListadoOtrosBienes").hide();
		$("#divOtroBien").hide();
		$("#divBienRustico").hide();
		$("#divBienUrbano").show();
		$("#Bienes").show();
		$("#idValorDeclarado").prop("disabled", true);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").show();
			calcularBaseImponibleDerReal();
			if($("#idDerechoReal").val() == "-1"){
				$("#idValorDeclarado").prop("disabled", false);
			}else if($("#idDerechoReal").val() != "OTROS"){
				$("#idValorDeclarado").prop("disabled", false);
			}else{
				$("#idValorDeclarado").prop("disabled", true);
			}
		}
		break;
	case 8:
		/* Debe de haber bienes urbanos y la base imponible debe de estar habilitada por si quieren introducir un valor*/
		$("#idTipoBien").val("URBANO");
		$("#idTipoBien").prop("disabled", true);
		$("#divListadoBienesRustico").hide();
		$("#divListadoBienesUrbano").show();
		$("#divListadoOtrosBienes").hide();
		$("#divOtroBien").hide();
		$("#divBienRustico").hide();
		$("#divBienUrbano").show();
		$("#Bienes").show();
		$("#idValorDeclarado").prop("disabled", true);
		$("#idBaseImponible").prop("readonly", false);
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 9:
		/* Debe de haber bienes (cualquiera de los dos tipos) y la base imponible y valor declarado debe estar habilitado para introducir un valor*/
		$("#Bienes").show();
		$("#divListadoBienesRustico").show();
		$("#divListadoBienesUrbano").show();
		$("#divListadoOtrosBienes").hide();
		$("#divOtroBien").hide();
		$("#idValorDeclarado").prop("disabled", true);
		$("#idBaseImponible").prop("readonly", false);
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		break;
	case 10:
		/* Debe de haber bienes (cualquiera de los dos tipos)*/
		$("#Bienes").show();
		$("#divListadoBienesRustico").show();
		$("#divListadoBienesUrbano").show();
		$('#divListadoOtrosBienes').hide();
		$('#divOtroBien').hide();
		$("#idValorDeclarado").prop("disabled", true);
		if(!$("#idLiqManual").is(':checked')){
			$("#idBaseImponible").prop("readonly", true);
		}
		if($("#idEstadoModelos").val() == null){
			$("#idValorDeclarado").val("0");
		}
		if(modelo=="600"){
			$("#divRenta").hide();
			$("#divDerechosReales").hide();
		}
		$("#idTipoBien option[value='OTRO']").remove();
		break;
	case 11:
		/* Debe de haber bienes (solo de tipo otros)*/
	
		$("#Bienes").show();
		$("#divListadoOtrosBienes").show();
		$("#divListadoBienesUrbano").hide();
		$("#divListadoBienesRustico").hide();
		$("#divBienUrbano").hide();
		$("#divBienRustico").hide();
		$("#divOtroBien").show();
		$("#idTipoBien").val("OTRO");
		$("#divAltaModiBienes").hide();
		$("#idValorDeclarado").prop("disabled", true);
		break;
	default:
		break;
	}
}


function abrirVentanaNotarioPopUp(){
	ventanaNotario = window.open('inicioConsultaNotarioRM.action', 'popup', opciones_ventana + ',resizable=NO,status=NO,menubar=NO');
	document.getElementById("divEmergenteDocumento").style.display = "block";
	ventanaNotario.focus();
}

function abrirVentanaBienRusticoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposBienRustico();
	ventanaBienRustico = window.open('inicioConsultaBienRustico.action', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienRustico.focus();
}

function abriVentanaBienUrbanoPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposBienUrbano();
	ventanaBienUrbano = window.open('inicioConsultaBienUrbano.action', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienUrbano.focus();
}


function abrirVentanaBienOtroPopUp(){
	document.getElementById("divEmergenteBienes").style.display = "block";
	limpiarCamposOtroBien();
	ventanaBienOtro = window.open('inicioConsultaBnS.action?esPopup=S', 'popUp', opciones_ventana_bienes + ',resizable=NO,status=NO,menubar=NO');
	ventanaBienOtro.focus();
}

function invokeSeleccionBienRustico(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divBienRustico");
	$.ajax({
		url:"bienNuevoAjaxMd.action",
		data:"codigo="+ codigo + "&tipoBien=RUSTICO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				$dest.show();
				$("#idTipoBien").val("RUSTICO");
				limpiarCamposBienUrbano();
				$("#divBienUrbano").hide();
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	    }
	});
	ventanaBienRustico.close();
}

function invokeSeleccionBienUrbano(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divBienUrbano");
	$.ajax({
		url:"bienNuevoAjaxMd.action",
		data:"codigo="+ codigo + "&tipoBien=URBANO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				$dest.show();
				$("#idTipoBien").val("URBANO");
				limpiarCamposBienRustico();
				$("#divBienRustico").hide();
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	    }
	});
	ventanaBienUrbano.close();
}

function invokeSeleccionBienOtro(codigo){
	document.getElementById("divEmergenteBienes").style.display="none";
	var $dest = $("#divOtroBien");
	$.ajax({
		url:"bienNuevoAjaxMd.action",
		data:"codigo="+ codigo + "&tipoBien=OTRO&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
				$dest.show();
				$("#idTipoBien").val("OTRO");
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	    }
	});
	ventanaBienOtro.close();
}

function invokeSeleccionNotario(codigo){
	document.getElementById("divEmergenteDocumento").style.display = "none";
	deshabilitarBotones();
	var $dest = $("#divNotario");
	$.ajax({
		url:"notarioNuevoAjaxMd.action",
		data:"codigo="+ codigo + "&tipoModelo=" + $("#tipoModelo").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			habilitarBotones();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
	        habilitarBotones();
	    }
	});
	ventanaNotario.close();
}

function habilitarNotario(){
	var $checkTipoDocPRI = $("#tipoDocPRI");
	var $checkTipoDocADM = $("#tipoDocADM");
	var $checkTipoDocJUD = $("#tipoDocJUD");
	var $checkTipoDocPUB = $("#tipoDocPUB");
	var $botonNotario = $("#botonNotario");
	if($checkTipoDocPRI.is(':checked') || $checkTipoDocADM.is(':checked') || $checkTipoDocJUD.is(':checked')){
		$botonNotario.hide();
	}else if($checkTipoDocPUB.is(':checked')){
		$botonNotario.show();
	}else{		
		$botonNotario.hide();
	}
}

function buscarIntervinienteSujetoPasivo(){
	var nif = $('#idNifSujPasivo').val();
	if(nif != ""){
		limpiarFormularioSujetoPasivo();
		var $dest = $("#divSujetoPasivo", parent.document);
		deshabilitarBotonesSujetoPasivo();
		$.ajax({
			url:"buscarPersonaAjaxMd.action#Bienes",
			data:"nif="+ nif + "&tipoInterviniente=001" + "&idContrato=" + $("#idContrato").val() +  "&idModelo=" + $("#idModelo").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idPrimerApeRazonSocialSujPasivo').val() != ""){
					$("#idLimpiarSujPasivo").show();
					$("#idBotonBuscarNifSujPasivo").prop("disabled", true);
					$('#idNifSujPasivo').prop("disabled", true);
				}else{
					$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
					$('#idNifSujPasivo').prop("disabled", false);
				}
				habilitarCamposSujetoPasivo();
				habilitarBotonesSujetoPasivo();
				$('#idTipoPersonaSujPasivo').prop("disabled", true);
				$('#idSexoSujPasivo').prop("disabled", true);
				$('#idDireccionSujPasivo').prop("disabled", true);
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el sujeto pasivo.');
		        habilitarBotonesSujetoPasivo();
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del sujeto pasivo debe rellenar el NIF/CIF.");
	}
}

function deshabilitarBotonesSujetoPasivo(){
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#BDBDBD");
		$("#idLimpiarSujPasivo").prop("disabled",true);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#BDBDBD");
		$("#idGuardarModelo").prop("disabled",true);
	}
}

function habilitarBotonesSujetoPasivo(){
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#000000");
		$("#idLimpiarSujPasivo").prop("disabled",false);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#000000");
		$("#idGuardarModelo").prop("disabled",false);
	}
}

function limpiarFormularioSujetoPasivo(){
	$('#idNifSujPasivo').val("");
	$('#idDireccionSujPasivo').val("");
	$('#trDireccionSujPasivo').hide();
	$('#idTipoPersonaSujPasivo').val("-1");
	$('#idSexoSujPasivo').val("-1");
	$('#idPrimerApeRazonSocialSujPasivo').val("");
	$('#idSegundoApeSujPasivo').val("");
	$('#idNombreSujPasivo').val("");
	$('#idDiaNacSujPasivo').val("");
	$('#idMesNacSujPasivo').val("");
	$('#idAnioNacSujPasivo').val("");
	$('#idProvinciaSujPasivo').val("-1");
	$('#idMunicipioSujPasivo').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioSujPasivo').val("-1");
	$("#municipioSeleccionadoSujPasivo").val("");
	$('#idTipoViaSujetoPasivo').find('option').remove().end().append('<option value="-1">Seleccione Tipo de Via</option>').val('whatever');
	$('#idTipoViaSujetoPasivo').val("-1");
	$('#idPuebloSujPasivo').find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#idPuebloSujPasivo').val("-1");
	$('#idNombreViaSujPasivo').val("");
	$('#idCodPostalSujPasivo').val("");
	$('#idNumeroDireccionSujPasivo').val("");
	$('#idLetraDireccionSujPasivo').val("");
	$('#idEscaleraDireccionSujPasivo').val("");
	$('#idPisoDireccionSujPasivo').val("");
	$('#idPuertaDireccionSujPasivo').val("");
	$('#idBloqueDireccionSujPasivo').val("");
	$('#idKmDireccionSujPasivo').val("");
	$('#idNifSujPasivo').prop("disabled", false);
	$("#idBotonBuscarNifSujPasivo").prop("disabled", false);
	$("#idLimpiarSujPasivo").hide();
}

function limpiarFormularioTransmitente(){
	$('#idNifTransmitente').val("");
	$('#idDireccionTransmitente').val("");
	$('#trDireccionTransmitente').hide();
	$('#idTipoPersonaTransmitente').val("-1");
	$('#idSexoTransmitente').val("-1");
	$('#idPrimerApeRazonSocialTransmitente').val("");
	$('#idSegundoApeTransmitente').val("");
	$('#idNombreTransmitente').val("");
	$('#idDiaNacTransmitente').val("");
	$('#idMesNacTransmitente').val("");
	$('#idAnioNacTransmitente').val("");
	$('#idProvinciaTransmitente').val("-1");
	$('#idMunicipioTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioTransmitente').val("-1");
	$('#idPuebloTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idPuebloTransmitente').val("-1");
	$("#municipioSeleccionadoTransmitente").val("");
	$('#idTipoViaTransmitente').find('option').remove().end().append('<option value="-1">Seleccione Tipo de Via</option>').val('whatever');
	$('#idTipoViaTransmitente').val("-1");
	$('#tipoViaSeleccionadaTransmitente').val("");
	$('#idNombreViaTransmitente').val("");
	$('#idCodPostalTransmitente').val("");
	$('#idNumeroDireccionTransmitente').val("");
	$('#idLetraDireccionTransmitente').val("");
	$('#idEscaleraDireccionTransmitente').val("");
	$('#idPisoDireccionTransmitente').val("");
	$('#idPuertaDireccionTransmitente').val("");
	$('#idBloqueDireccionTransmitente').val("");
	$('#idKmDireccionTransmitente').val("");
	$('#idNifTransmitente').prop("disabled", false);
	$("#idBotonBuscarNifTransmitente").prop("disabled", false);
	$("#idLimpiarTransmitente").hide();
}

function datosInicialesSujpasivoModelo(){
	var $botonLimpiar = $("#idLimpiarSujPasivo");
	var $botonBuscarNif = $("#idBotonBuscarNifSujPasivo");
	var $botonModif = $("#idModificarSujPasivo");
	var $botonEliminar = $("#idEliminarSujPasivo");
	var $tablaSujPasivo = $("#tablaSujetosPasivos");
	var $divSujPasivo = $("#divListSujetoPasivo");
	if($("#idNifSujPasivo",parent.document).val() != ""){
		$("#idNifSujPasivo",parent.document).prop("disabled", true);
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
	}else{
		$botonLimpiar.hide();
	}
	if($tablaSujPasivo.length > 0){
		$botonEliminar.show();
		$botonModif.show();
		$divSujPasivo.show();
	}else{
		$divSujPasivo.hide();
		$botonEliminar.hide();
		$botonModif.hide();
	}
	if($('#idTipoPersonaSujPasivo').val() != "-1"){
		$('#idSexoSujPasivo').prop("disabled",false);
	}
}

function datosInicialesTransmitente(){
	var $botonLimpiar = $("#idLimpiarTransmitente");
	var $botonBuscarNif = $("#idBotonBuscarNifTransmitente");
	var $botonModif = $("#idModificarTransmitente");
	var $botonEliminar = $("#idEliminarTransmitente");
	var $tablaTransmitente = $("#tablaTransmitentes");
	var $divTransmitente = $("#divListTransmitente");
	if($("#idNifTransmitente",parent.document).val() != ""){
		$("#idNifTransmitente",parent.document).prop("disabled", true);
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
	}else{
		$botonLimpiar.hide();
	}
	if($tablaTransmitente.length > 0){
		$botonEliminar.show();
		$botonModif.show();
		$divTransmitente.show();
	}else{
		$divTransmitente.hide();
		$botonEliminar.hide();
		$botonModif.hide();
	}
	if($('#idTipoPersonaTransmitente').val() != "-1"){
		$('#idSexoTransmitente').prop("disabled",false);
	}
}

function buscarIntervinienteTransmitente(){
	var nif = $('#idNifTransmitente').val();
	if(nif != ""){
		limpiarFormularioTransmitente();
		var $dest = $("#divTransmitente", parent.document);
		deshabilitarBotonesTransmitente();
		$.ajax({
			url:"buscarPersonaAjaxMd.action#Bienes",
			data:"nif="+ nif + "&tipoInterviniente=003" + "&idContrato=" + $("#idContrato").val() +  "&idModelo=" + $("#idModelo").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idPrimerApeRazonSocialTransmitente').val() != "" || $('#idPrimerApeRazonSocialTransmitente').val() != null){
					$("#idLimpiarTransmitente").show();
					$("#idBotonBuscarNifTransmitente").prop("disabled", true);
					$('#idNifTransmitente').prop("disabled", true);
				}else{
					$("#idBotonBuscarNifTransmitente").prop("disabled", false);
					$('#idNifTransmitente').prop("disabled", false);
				}
				habilitarBotonesTransmitente();
				habilitarCamposTransmitente();
				$('#idTipoPersonaTransmitente').prop("disabled", true);
				$('#idSexoTransmitente').prop("disabled", true);
				$('#idDireccionTransmitente').prop("disabled", true);
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el transmitente.');
		        habilitarBotonesTransmitente();
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del transmitente debe rellenar el NIF/CIF.");
	}
}

function deshabilitarBotonesTransmitente(){
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#BDBDBD");
		$("#idLimpiarTransmitente").prop("disabled",true);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#BDBDBD");
		$("#idGuardarModelo").prop("disabled",true);
	}
}

function habilitarBotonesTransmitente(){
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#000000");
		$("#idLimpiarTransmitente").prop("disabled",false);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#000000");
		$("#idGuardarModelo").prop("disabled",false);
	}
}

function habilitarTipoBien(){
	var $divUrbano = $("#divBienUrbano");
	var $divRustico = $("#divBienRustico");
	var $divOtro = $("#divOtroBien");

	if($('#idTipoBien option:selected').val() == "URBANO"){
		$divRustico.hide();
		$divUrbano.show();
		$divOtro.hide();
		if($("#idEstadoModelos").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		$divRustico.show();
		$divUrbano.hide();
		$divOtro.hide();
		if($("#idEstadoModelos").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}else if($('#idTipoBien option:selected').val() == "OTRO"){
		$divRustico.hide();
		$divUrbano.hide();
		$divOtro.show();
		if($("#idEstadoModelos").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}else{
		$divRustico.hide();
		$divUrbano.hide();
		$divOtro.hide();
		if($("#idEstadoModelos").val() != "3"){
			$("#idLimpiarBien").show();
		}
	}
	if($("#idEstadoModelos").val() != "3"){
		var $tablaBienesRustico = $("#tablaBienesRusticos");
		if($tablaBienesRustico.length > 0){
			$("#idEliminarBienesRustico").show();
			$("#idModificarBienesRustico").show();
		}else{
			$("#idEliminarBienesRustico").hide();
			$("#idModificarBienesRustico").hide();
		}
		var $tablaBienesUrbano = $("#tablaBienesUrbanos");
		if($tablaBienesUrbano.length > 0){
			$("#idEliminarBienesUrbano").show();
			$("#idModificarBienesUrbano").show();
		}else{
			$("#idEliminarBienesUrbano").hide();
			$("#idModificarBienesUrbano").hide();
		}
		var $tablaOtrosBienes = $("#tablaOtrosBienes");
		if($tablaOtrosBienes.length > 0){
			$("#idEliminarOtrosBienes").show();
			$("#idModificarOtrosBienes").show();
		}else{
			$("#idEliminarOtrosBienes").hide();
			$("#idModificarOtrosBienes").hide();
		}
		
	}
}

/**
 * Borra el contenido de los inputs de tipo texto contenidos en el div con id pasado por parametro.
 * Tambien limpia los combos (asegurate de que los combos tienen un ID unico)
 * @param idDiv
 * 			Identificador de la capa que contiene los campos a borrar
 */
function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " input[type=hidden]").attr("value", "");
	$("#" + idDiv + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
}

function limpiarBien(){
	$("#bloqueBotonesBienes").show();
	deshabilitarBotonesBienes();
	if($('#idTipoBien option:selected').val() == "URBANO"){
		limpiarCamposBienUrbano();
	}else if($('#idTipoBien option:selected').val() == "RUSTICO"){
		limpiarCamposBienRustico();
	} else {
		limpiarCamposOtroBien();
	}
	habilitarBotonesBienes();
}

function limpiarCamposBienUrbano(){
	limpiarDiv('bienesUrbanos');
}

function limpiarCamposBienRustico(){
	limpiarDiv('bienesRusticos');
}

function limpiarCamposOtroBien(){
	limpiarDiv('otrosBienes');
}


function deshabilitarBotonesBienes(){
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#BDBDBD");
		$("#idConsultarBienesRustico").prop("disabled",true);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#BDBDBD");
		$("#idEliminarBienesRustico").prop("disabled",true);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#BDBDBD");
		$("#idModificarBienesRustico").prop("disabled",true);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#BDBDBD");
		$("#idConsultarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#BDBDBD");
		$("#idEliminarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#BDBDBD");
		$("#idModificarBienesUrbano").prop("disabled",true);
	}
	
	if (!$('#idConsultarOtrosBienes').is(':hidden')){
		$("#idConsultarOtrosBienes").css("color","#BDBDBD");
		$("#idConsultarOtrosBienes").prop("disabled",true);
	}
	if (!$('#idEliminarOtrosBienes').is(':hidden')){
		$("#idEliminarOtrosBienes").css("color","#BDBDBD");
		$("#idEliminarOtrosBienes").prop("disabled",true);
	}
	if (!$('#idModificarOtrosBienes').is(':hidden')){
		$("#idModificarOtrosBienes").css("color","#BDBDBD");
		$("#idModificarOtrosBienes").prop("disabled",true);
	}
	
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#BDBDBD");
		$("#idLimpiarBien").prop("disabled",true);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#BDBDBD");
		$("#idGuardarModelo").prop("disabled",true);
	}
	if (!$('#idValidarModelo').is(':hidden')){
		$("#idValidarModelo").css("color","#BDBDBD");
		$("#idValidarModelo").prop("disabled",true);
	}
	if (!$('#idAutoLiquidarModelo').is(':hidden')){
		$("#idAutoLiquidarModelo").css("color","#BDBDBD");
		$("#idAutoLiquidarModelo").prop("disabled",true);
	}
}

function buscarBienes(tipoBien){
	if("RUSTICO" == tipoBien){
		abrirVentanaBienRusticoPopUp();
	}else if("URBANO" == tipoBien){
		abriVentanaBienUrbanoPopUp();
	} else if("OTRO" == tipoBien){
		abrirVentanaBienOtroPopUp();
	}
}

function eliminarBien(tipoBien){
	var codigos = "";
	var checksEliminarBien = null;
	if(tipoBien == 'URBANO'){
		checksEliminarBien = document.getElementsByName("listaChecksBienUrbano");
	}else if(tipoBien == 'RUSTICO'){
		checksEliminarBien = document.getElementsByName("listaChecksBienRustico");
	}else{
		checksEliminarBien = document.getElementsByName("listaChecksOtroBien");
	}
	var i = 0;
	while(checksEliminarBien[i] != null) {
		if(checksEliminarBien[i].checked) {
			if(codigos==""){
				codigos += checksEliminarBien[i].value;
			}else{
				codigos += "-" + checksEliminarBien[i].value;
			}
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los Bienes seleccionados de la lista?")){
		$("#bloqueBotonesListBienes").show();
		deshabilitarBotones();
		if("RUSTICO" == tipoBien){
			limpiarCamposBienRustico();
		}else if("URBANO" == tipoBien){
			limpiarCamposBienUrbano();
		}
		deshabilitarCamposComunes();
		limpiarHiddenInput("codigo");
		input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
		$('#formData').append($(input));
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "eliminarBienMD.action#"+pestania).trigger("submit");
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

function deshabilitarCamposComunes(){
	$("#idNifPresentador").prop("disabled", false);
	$("#idTipoPersonaPresentador").prop("disabled", false);
	$("#idSexoPresentador").prop("disabled", false);
	$("#idPrimerApeRazonSocialPresentador").prop("disabled", false);
	$("#idSegundoApePresentador").prop("disabled", false);
	$("#idNombrePresentador").prop("disabled", false);
	$("#idDiaNacPresentador").prop("disabled", false);
	$("#idMesNacPresentador").prop("disabled", false);
	$("#idAnioNacPresentador").prop("disabled", false);
	$("#idProvinciaPresentador").prop("disabled", false);
	$("#idNombreViaPresentador").prop("disabled", false);
	$("#idCodPostalPresentador").prop("disabled", false);
	$("#idNumeroDireccionPresentador").prop("disabled", false);
	$("#idLetraDireccionPresentador").prop("disabled", false);
	$("#idEscaleraDireccionPresentador").prop("disabled", false);
	$("#idPisoDireccionPresentador").prop("disabled", false);
	$("#idPuertaDireccionPresentador").prop("disabled", false);
	$("#idBloqueDireccionPresentador").prop("disabled", false);
	$("#idKmDireccionPresentador").prop("disabled", false);
	$("#idMunicipioPresentador").prop("disabled", false);
	$("#idPuebloPresentador").prop("disabled", false);
	$("#idTipoViaPresentador").prop("disabled", false);
	$("#idHmDireccionPresentador").prop("disabled", false);
	$("#idCodNotario").prop("disabled", false);
	$("#idNotarioNombre").prop("disabled", false);
	$("#idNotarioApellidos").prop("disabled", false);
	$("#idExprAbreviada").prop("disabled", false);
	$("#idTipoBien").prop("disabled", false);
	$("#idProvinciaOficinaLiquid").prop("disabled", false);
	$("#idNumExpediente").prop("disabled", false);
	$("#idBonificacionMonto").prop("disabled", false);
	$("#idEstadoModelo").prop("disabled", false);
	$("#idNumBienes").prop("disabled", false);
	$("#idNifSujPasivo",parent.document).prop("disabled", false);
	$("#idNifTransmitente",parent.document).prop("disabled", false);
	$("#idBienRusticoDto",parent.document).prop("disabled", false);
	$("#idBienUrbanoDto",parent.document).prop("disabled", false);
	$("#idValorDeclarado",parent.document).prop("disabled", false);
	habilitarCamposSujetoPasivo();
	habilitarCamposTransmitente();
	$("#idNifPagadorDatBancarios").prop("disabled", false);
	$("#idDescripcionDatBancarios").prop("disabled", false);
	$("#idEntidad").prop("disabled", false);
	$("#idOficina").prop("disabled", false);
	$("#idDC").prop("disabled", false);
	$("#idNumeroCuentaPago").prop("disabled", false);
	$("#idFavoritoDatBancario").prop("disabled", false);
	$("#idDireccionRustico").prop("disabled", false);
	$("#idDireccionUrbano").prop("disabled", false);
	$("#idFundamentoNoSujeto").prop("disabled", false);
	$("#idNoSujeto").prop("disabled", false);
	$("#idExento").prop("disabled", false);
	$("#idFundamentoExento").prop("disabled", false);
	$("#idEstadoModelos").prop("disabled", false);
	$("#idValorDeclarado").prop("disabled", false);
	$("#idBonificaciones").prop("disabled", false);
	$("#idExento").prop("disabled", false);
	$("#idNoSujeto").prop("disabled", false);
	$("#idFundamentoNoSujeto").prop("disabled", false);
	$("#idFundamentoExento").prop("disabled", false);
	$("#idLiquiComp").prop("disabled", false);
	$("#idDiaFechaPrimLiq").prop("disabled", false);
	$("#idMesFechaPrimLiq").prop("disabled", false);
	$("#idAnioFechaPrimLiq").prop("disabled", false);
	$("#idNumJustPrimLiq").prop("disabled", false);
	$("#idNumPrimLiq").prop("disabled", false);
	$("#duracionRenta").prop("disabled", false);
	$("#periodoRenta").prop("disabled", false);
	$("#idNumPeriodo").prop("disabled", false);
	$("#idImporteRenta").prop("disabled", false);
	$("#idDerechoReal").prop("disabled", false);
	$("#duracion").prop("disabled", false);
	$("#idNumAnio").prop("disabled", false);
	$("#idDiaNacUsufructurario").prop("disabled", false);
	$("#idMesNacUsufructurario").prop("disabled", false);
	$("#idAnioNacUsufructurario").prop("disabled", false);
	$("#idBaseImponible").prop("disabled", false);
	$("#idReduccion").prop("disabled", false);
	$("#idTextoLiq").prop("disabled", false);
	$("#idBaseLiquidable").prop("disabled", false);
	$("#idTipo").prop("disabled", false);
	$("#idCuota").prop("disabled", false);		
	$("#idBoniMonto").prop("disabled", false);
	$("#idBoniMonto").prop("disabled", false);
	$("#idBoniCuota").prop("disabled", false);
	$("#idIngresar").prop("disabled", false);
	$("#idTotalIngresar").prop("disabled", false);
}

function deshabilitarBotones(){
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#BDBDBD");
		$("#idGuardarModelo").prop("disabled",true);
	}
	if (!$('#idValidarModelo').is(':hidden')){
		$("#idValidarModelo").css("color","#BDBDBD");
		$("#idValidarModelo").prop("disabled",true);
	}
	if (!$('#idAutoLiquidarModelo').is(':hidden')){
		$("#idAutoLiquidarModelo").css("color","#BDBDBD");
		$("#idAutoLiquidarModelo").prop("disabled",true);
	}
	$("#bloqueBotonesSujeto").show();
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#BDBDBD");
		$("#idLimpiarSujPasivo").prop("disabled",true);
	}
	$("#bloqueBotonesTransmitente").show();
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#BDBDBD");
		$("#idLimpiarTransmitente").prop("disabled",true);
	}
	$("#bloqueBotonesBienes").show();
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#BDBDBD");
		$("#idConsultarBienesRustico").prop("disabled",true);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#BDBDBD");
		$("#idEliminarBienesRustico").prop("disabled",true);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#BDBDBD");
		$("#idModificarBienesRustico").prop("disabled",true);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#BDBDBD");
		$("#idConsultarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#BDBDBD");
		$("#idEliminarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#BDBDBD");
		$("#idModificarBienesUrbano").prop("disabled",true);
	}
	if (!$('#idEliminarOtrosBienes').is(':hidden')){
		$("#idEliminarOtrosBienes").css("color","#BDBDBD");
		$("#idEliminarOtrosBienes").prop("disabled",true);
	}
	if (!$('#idModificarOtrosBienes').is(':hidden')){
		$("#idModificarOtrosBienes").css("color","#BDBDBD");
		$("#idModificarOtrosBienes").prop("disabled",true);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#BDBDBD");
		$("#idLimpiarBien").prop("disabled",true);
	}
	if (!$('#idModificarBienes').is(':hidden')){
		$("#idModificarBienes").css("color","#BDBDBD");
		$("#idModificarBienes").prop("disabled",true);
	}
	if (!$('#idImprimirCartaPago').is(':hidden')){
		$("#idImprimirCartaPago").css("color","#BDBDBD");
		$("#idImprimirCartaPago").prop("disabled",true);
	}
	if (!$('#idImprimirDiligencia').is(':hidden')){
		$("#idImprimirDiligencia").css("color","#BDBDBD");
		$("#idImprimirDiligencia").prop("disabled",true);
	}
	if (!$('#idPresentarModelo').is(':hidden')){
		$("#idPresentarModelo").css("color","#BDBDBD");
		$("#idPresentarModelo").prop("disabled",true);
	}
}

function habilitarBotones(){
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#000000");
		$("#idGuardarModelo").prop("disabled",false);
	}
	if (!$('#idValidarModelo').is(':hidden')){
		$("#idValidarModelo").css("color","#000000");
		$("#idValidarModelo").prop("disabled",false);
	}
	if (!$('#idAutoLiquidarModelo').is(':hidden')){
		$("#idAutoLiquidarModelo").css("color","#000000");
		$("#idAutoLiquidarModelo").prop("disabled",false);
		if($("#idLiqManual").is(':checked')){
			$("#idAutoLiquidarModelo").prop("value","Liquidar");
		}else{
			$("#idAutoLiquidarModelo").prop("value","AutoLiquidar");
		}
	}
	$("#bloqueBotonesSujeto").show();
	if (!$('#idLimpiarSujPasivo').is(':hidden')){
		$("#idLimpiarSujPasivo").css("color","#000000");
		$("#idLimpiarSujPasivo").prop("disabled",false);
	}
	$("#bloqueBotonesTransmitente").show();
	if (!$('#idLimpiarTransmitente').is(':hidden')){
		$("#idLimpiarTransmitente").css("color","#000000");
		$("#idLimpiarTransmitente").prop("disabled",false);
	}
	$("#bloqueBotonesBienes").show();
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#000000");
		$("#idConsultarBienesRustico").prop("disabled",false);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#000000");
		$("#idEliminarBienesRustico").prop("disabled",false);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#000000");
		$("#idModificarBienesRustico").prop("disabled",false);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#000000");
		$("#idConsultarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#000000");
		$("#idEliminarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#000000");
		$("#idModificarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idConsultarOtrosBienes').is(':hidden')){
		$("#idConsultarOtrosBienes").css("color","#000000");
		$("#idConsultarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idEliminarOtrosBienes').is(':hidden')){
		$("#idEliminarOtrosBienes").css("color","#000000");
		$("#idEliminarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idModificarOtrosBienes').is(':hidden')){
		$("#idModificarOtrosBienes").css("color","#000000");
		$("#idModificarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#000000");
		$("#idLimpiarBien").prop("disabled",false);
	}
	if (!$('#idModificarBienes').is(':hidden')){
		$("#idModificarBienes").css("color","#000000");
		$("#idModificarBienes").prop("disabled",false);
	}
	if (!$('#idImprimirCartaPago').is(':hidden')){
		$("#idImprimirCartaPago").css("color","#000000");
		$("#idImprimirCartaPago").prop("disabled",false);
	}
	if (!$('#idImprimirDiligencia').is(':hidden')){
		$("#idImprimirDiligencia").css("color","#000000");
		$("#idImprimirDiligencia").prop("disabled",false);
	}
	if (!$('#idPresentarModelo').is(':hidden')){
		$("#idPresentarModelo").css("color","#000000");
		$("#idPresentarModelo").prop("disabled",false);
	}
}

function modificarBienes(dest,tipo){
	var $dest = $("#" + dest, parent.document);
	var codigos = "";
	var checks = null;
	var i = 0;
	var numChecks = 0;
	if(tipo == 'URBANO'){
		checks = document.getElementsByName("listaChecksBienUrbano");
	}else if(tipo == 'RUSTICO'){
		checks = document.getElementsByName("listaChecksBienRustico");
	}else{
		checks = document.getElementsByName("listaChecksOtroBien");
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
		return alert("Debe seleccionar alg\u00FAn Bien para modificar.");
	}else if(numChecks > 1){
		return alert("Los Bienes no se pueden modificar en bloque, por favor seleccione solo uno.");
	}
	$("#bloqueBotonesListBienes").show();
	deshabilitarBotonesBienes();
	$.ajax({
		url:"modificarBienAjaxMd.action#Bienes",
		data:"codigo="+ codigos +  "&tipoModelo=" + $("#tipoModelo").val() + "&idModelo=" + $("#idModelo").val(),
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
			if("RUSTICO" == tipo){
				deshabilitarCamposUsoBienRustico();
				$("#idTipoBien").val("RUSTICO");
				$("#divBienRustico").show();
				$("#divBienUrbano").hide();
				$("#divOtroBien").hide();
			}else if("URBANO" == tipo){
				$("#idTipoBien").val("URBANO");
				$("#divBienRustico").hide();
				$("#divBienUrbano").show();
				$("#divOtroBien").hide();
			}else{
				$("#idTipoBien").val("OTRO");
				$("#divBienRustico").hide();
				$("#divBienUrbano").hide();
				$("#divOtroBien").show();
			}
			$("#bloqueBotonesListBienes").hide();
			habilitarBotonesBienes();
			if($("#idEstadoModelos").val() != "3"){
				$('#idLimpiarBien').show();
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el bien.');
	        $("#bloqueBotonesListBienes").hide();
	    	habilitarBotonesBienes();
	    }
	});
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

function habilitarBotonesBienes(){
	if (!$('#idConsultarBienesRustico').is(':hidden')){
		$("#idConsultarBienesRustico").css("color","#000000");
		$("#idConsultarBienesRustico").prop("disabled",false);
	}
	if (!$('#idEliminarBienesRustico').is(':hidden')){
		$("#idEliminarBienesRustico").css("color","#000000");
		$("#idEliminarBienesRustico").prop("disabled",false);
	}
	if (!$('#idModificarBienesRustico').is(':hidden')){
		$("#idModificarBienesRustico").css("color","#000000");
		$("#idModificarBienesRustico").prop("disabled",false);
	}
	if (!$('#idConsultarBienesUrbano').is(':hidden')){
		$("#idConsultarBienesUrbano").css("color","#000000");
		$("#idConsultarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idEliminarBienesUrbano').is(':hidden')){
		$("#idEliminarBienesUrbano").css("color","#000000");
		$("#idEliminarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idModificarBienesUrbano').is(':hidden')){
		$("#idModificarBienesUrbano").css("color","#000000");
		$("#idModificarBienesUrbano").prop("disabled",false);
	}
	if (!$('#idConsultarOtrosBienes').is(':hidden')){
		$("#idConsultarOtrosBienes").css("color","#000000");
		$("#idConsultarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idEliminarOtrosBienes').is(':hidden')){
		$("#idEliminarOtrosBienes").css("color","#000000");
		$("#idEliminarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idModificarOtrosBienes').is(':hidden')){
		$("#idModificarOtrosBienes").css("color","#000000");
		$("#idModificarOtrosBienes").prop("disabled",false);
	}
	if (!$('#idLimpiarBien').is(':hidden')){
		$("#idLimpiarBien").css("color","#000000");
		$("#idLimpiarBien").prop("disabled",false);
	}
	if (!$('#idGuardarModelo').is(':hidden')){
		$("#idGuardarModelo").css("color","#000000");
		$("#idGuardarModelo").prop("disabled",false);
	}
	if (!$('#idValidarModelo').is(':hidden')){
		$("#idValidarModelo").css("color","#000000");
		$("#idValidarModelo").prop("disabled",false);
	}
	if (!$('#idAutoLiquidarModelo').is(':hidden')){
		$("#idAutoLiquidarModelo").css("color","#000000");
		$("#idAutoLiquidarModelo").prop("disabled",false);
		if($("#idLiqManual").is(':checked')){
			$("#idAutoLiquidarModelo").prop("value","Liquidar");
		}else{
			$("#idAutoLiquidarModelo").prop("value","AutoLiquidar");
		}
	}
}

function seleccionarUsoRustico(idHiddenTipoUsoRustico, valor){
	var $tipoUsoRustico = $("#" + idHiddenTipoUsoRustico);
	$tipoUsoRustico.val(valor);
	deshabilitarCamposUsoBienRustico();
}

function cargarMonto(comboBonificacion,inputMonto){
	$dest = $("#"+inputMonto);
	$("#idBoniCuota").val("0,00");
	$.ajax({
		url:"getMontoBonificacionAjaxMd.action#Autoliquidacion",
		data:"codigo="+ comboBonificacion.value,
		type:'POST',
		dataType: 'json',
		success: function(data){
			$dest.val(data);
			$("#idBoniMonto").val(data);
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la bonificacion.');
	    }
	});
}


function cambiarExento(checkExento,checkNoSujeto, fundamentoExento,fundamentoNoSujeto){
	if($("#"+checkExento).is(':checked')){
		$("#"+fundamentoExento).show();
		$("#"+fundamentoNoSujeto).hide();
	}else{
		$("#"+fundamentoExento).hide();
		$("#"+fundamentoExento).val("-1");
		$("#"+fundamentoNoSujeto).show();
	}
	$("#"+fundamentoNoSujeto).val("");
	$("#"+fundamentoNoSujeto).prop("disabled", true);
	$("#"+checkNoSujeto).prop("checked", false);
}

function cambiarNoSujeto(checkNoSujeto,checkExento,fundamentoNoSujeto,fundamentoExento){
	if($("#"+checkNoSujeto).is(':checked')){
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).val("OP. NO SUJETAS");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
	}else{
		$("#"+fundamentoNoSujeto).val("");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
	}
	$("#"+fundamentoExento).val("-1");
	$("#"+fundamentoExento).hide();
	$("#"+checkExento).prop("checked", false);
}

function habilitarCamposLiq(dia,mes, anio,calendar,liqComplementaria,numJustiPrimLiq,numPrimLiq){
	if($("#"+liqComplementaria).is(':checked')){
		$("#"+calendar).show();
		$("#"+dia).prop("disabled", false);
		$("#"+mes).prop("disabled", false);
		$("#"+anio).prop("disabled", false);
		$("#"+numJustiPrimLiq).prop("disabled", false);
		$("#"+numPrimLiq).prop("disabled", false);
	}else{
		$("#"+calendar).hide();
		$("#"+dia).val("");
		$("#"+dia).prop("disabled", true);
		$("#"+mes).val("");
		$("#"+mes).prop("disabled", true);
		$("#"+anio).val("");
		$("#"+anio).prop("disabled", true);
		$("#"+numJustiPrimLiq).val("");
		$("#"+numJustiPrimLiq).prop("disabled", true);
		$("#"+numPrimLiq).val("");
		$("#"+numPrimLiq).prop("disabled", true);
	}
}

function inicializarCamposAutoLiq(checkExento,checkNoSujeto,fundamentoExento,fundamentoNoSujeto,dia,mes, anio,calendar,liqComplementaria,numJustiPrimLiq,numPrimLiq){
	
	if($("#"+checkNoSujeto).is(':checked')){
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoExento).val("-1");
		$("#"+fundamentoExento).hide();
	 }else if($("#"+checkExento).is(':checked')){
		$("#"+fundamentoExento).show();
		$("#"+fundamentoNoSujeto).val("");
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoNoSujeto).hide();
	}else{
		$("#"+fundamentoNoSujeto).show();
		$("#"+fundamentoNoSujeto).prop("disabled", true);
		$("#"+fundamentoExento).val("-1");
		$("#"+fundamentoExento).hide();
	}
	
	if($("#tipoImpuestoExento").val() == "true" || $("#tipoImpuestoNoSujeto").val()  == "true"){
		$("#"+checkNoSujeto).prop("disabled", true);
		$("#"+checkExento).prop("disabled", true);
		//$("#"+fundamentoExento).prop("disabled", true);
	}
	
	if($("#"+liqComplementaria).is(':checked')){
		$("#"+calendar).show();
		$("#"+dia).prop("disabled", false);
		$("#"+mes).prop("disabled", false);
		$("#"+anio).prop("disabled", false);
		$("#"+numJustiPrimLiq).prop("disabled", false);
		$("#"+numPrimLiq).prop("disabled", false);
	}else{
		$("#"+calendar).hide();
		$("#"+dia).val("");
		$("#"+dia).prop("disabled", true);
		$("#"+mes).val("");
		$("#"+mes).prop("disabled", true);
		$("#"+anio).val("");
		$("#"+anio).prop("disabled", true);
		$("#"+numJustiPrimLiq).val("");
		$("#"+numJustiPrimLiq).prop("disabled", true);
		$("#"+numPrimLiq).val("");
		$("#"+numPrimLiq).prop("disabled", true);
	}
	
	habilitarCamposDerReal();
	
	if($("#idEstadoModelos").val() == "3"){
		$("#idValorDeclarado").prop("disabled", true);
		$("#idBonificaciones").prop("disabled", true);
		$("#idExento").prop("disabled", true);
		$("#idNoSujeto").prop("disabled", true);
		$("#idFundamentoNoSujeto").prop("disabled", true);
		//$("#idFundamentoExento").prop("disabled", true);
		$("#idLiquiComp").prop("disabled", true);
		$("#"+dia).prop("disabled", true);
		$("#"+mes).prop("disabled", true);
		$("#"+anio).prop("disabled", true);
		$("#"+numJustiPrimLiq).prop("disabled", true);
		$("#"+numPrimLiq).prop("disabled", true);
		$("#duracionRenta").prop("disabled", true);
		$("#periodoRenta").prop("disabled", true);
		$("#idNumPeriodo").prop("disabled", true);
		$("#idImporteRenta").prop("disabled", true);
		$("#idDerechoReal").prop("disabled", true);
		$("#duracion").prop("disabled", true);
		$("#idNumAnio").prop("disabled", true);
		$("#idDiaNacUsufructurario").prop("disabled", true);
		$("#idMesNacUsufructurario").prop("disabled", true);
		$("#idAnioNacUsufructurario").prop("disabled", true);
		$("#idBaseImponible").prop("disabled", true);
		$("#idReduccion").prop("disabled", true);
		$("#idTextoLiq").prop("disabled", true);
		$("#idBaseLiquidable").prop("disabled", true);
		$("#idTipo").prop("disabled", true);
		$("#idCuota").prop("disabled", true);		
		$("#idBoniMonto").prop("disabled", true);
		$("#idBoniMonto").prop("disabled", true);
		$("#idBoniCuota").prop("disabled", true);
		$("#idIngresar").prop("disabled", true);
		$("#idTotalIngresar").prop("disabled", true);
	}
	
}

function recargarConceptoPorModelo(concepto,modelo){
	var $dest = $("#"+concepto);
	$.ajax({
		url:"getConceptoPorModeloAjaxMd.action",
		data:"modelo="+ $("#"+modelo).val(),
		type:'POST',
		success: function(data){
			$dest.find('option').remove();
			$dest.append('<option value="">Seleccione Concepto</option>').val('whatever');
			var valor = data.split("||");
			for(var i=0;i<valor.length;i++){
				var concepto = valor[i].split(";");
				$dest.append('<option value="'+concepto[0]+'">'+concepto[1]+'</option>');
			}
			
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los conceptos por modelo.');
	    }
	});
}

function guardarModelo(){
	deshabilitarCamposComunes();
	deshabilitarNuevoBien();
	deshabilitarBotones();
	crearInputLiquidacion();
	limpiarHiddenInput("modeloDto.baseImponible");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.baseImponible").val($("#idBaseImponible").val());
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "guardarMD.action#"+pestania).trigger("submit");
}

function validarModelo(){
	deshabilitarCamposComunes();
	deshabilitarBotones();
	crearInputLiquidacion();
	limpiarHiddenInput("modeloDto.baseImponible");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.baseImponible").val($("#idBaseImponible").val());
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "validarModeloMD.action#"+pestania).trigger("submit");
}

function autoLiquidarModelo(){
	if (confirm("Recuerde que para poder liquidar el modelo que lleve marcado el check de responsabilidad del Gestor Administrativo, dicho modelo debera tener relleno todos los datos de la liquidaci\u00F3n.")){
		deshabilitarCamposComunes();
		deshabilitarBotones();
		crearInputLiquidacion();
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action", "autoLiquidarMD.action#"+pestania).trigger("submit");
	}
}

function crearInputLiquidacion(){
	limpiarHiddenInput("modeloDto.cuota");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.cuota").val($("#idCuota").val());
	$('#formData').append($(input));
	limpiarHiddenInput("modeloDto.ingresar");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.ingresar").val($("#idIngresar").val());
	$('#formData').append($(input));
	limpiarHiddenInput("modeloDto.totalIngresar");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.totalIngresar").val($("#idTotalIngresar").val());
	$('#formData').append($(input));
	limpiarHiddenInput("modeloDto.bonificacionCuota");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.bonificacionCuota").val($("#idBoniCuota").val());
	$('#formData').append($(input));
	limpiarHiddenInput("modeloDto.baseLiquidable");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.baseLiquidable").val($("#idBaseLiquidable").val());
	$('#formData').append($(input));
	limpiarHiddenInput("modeloDto.baseImponible");
	input = $("<input>").attr("type", "hidden").attr("name", "modeloDto.baseImponible").val($("#idBaseImponible").val());
	$('#formData').append($(input));
}

function deshabilitarPeriodoRenta(){
	var $checkDurRentaGlobal = $("#duracionRentaGLOBAL");
	var $checkDurRentaPeriodica = $("#duracionRentaPERIODICA");
	
	if($checkDurRentaGlobal.is(':checked')){
		$("#periodoRentaMENSUAL").prop("disabled",true);
		$("#periodoRentaANUAL").prop("disabled",true);
		$("#idNumPeriodo").val("");
		$("#idNumPeriodo").prop("disabled",true);
		calcularBaseImponibleRenta();
	}else if($checkDurRentaPeriodica.is(':checked')){
		$("#periodoRentaMENSUAL").prop("disabled",false);
		$("#periodoRentaANUAL").prop("disabled",false);
		$("#idNumPeriodo").prop("disabled",false);
		calcularBaseImponibleRenta();
	}else{
		$("#periodoRentaMENSUAL").prop("disabled",true);
		$("#periodoRentaAnual").prop("disabled",true);
		$("#idNumPeriodo").prop("disabled",true);
	}
}

function habilitarCamposDerReal(){
	var $checkDurDerRealTemporal = $("#duracionTEMPORAL");
	var $checkDurDerRealVitalicio = $("#duracionVITALICIO");
	
	if($checkDurDerRealTemporal.is(':checked')){
		$("#idNumAnio").prop("disabled",false);
		$("#idDiaNacUsufructurario").prop("disabled",true);
		$("#idMesNacUsufructurario").prop("disabled",true);
		$("#idAnioNacUsufructurario").prop("disabled",true);
		$("#idCalendarNacUsufructurario").hide();
	}else if($checkDurDerRealVitalicio.is(':checked')){
		$("#idNumAnio").prop("disabled",true);
		$("#idDiaNacUsufructurario").prop("disabled",false);
		$("#idMesNacUsufructurario").prop("disabled",false);
		$("#idAnioNacUsufructurario").prop("disabled",false);
		$("#idCalendarNacUsufructurario").show();
	}else{
		$("#idNumAnio").prop("disabled",true);
		$("#idDiaNacUsufructurario").prop("disabled",true);
		$("#idMesNacUsufructurario").prop("disabled",true);
		$("#idAnioNacUsufructurario").prop("disabled",true);
		$("#idCalendarNacUsufructurario").hide();
	}
}

function calcularBaseImponibleRenta(){
	var $checkDurRentaGlobal = $("#duracionRentaGLOBAL");
	var $checkDurRentaPeriodica = $("#duracionRentaPERIODICA");
	var importeRenta = $("#idImporteRenta").val();
	var numPeriodo = $("#idNumPeriodo").val();
	var importe = "0,00";
	if($checkDurRentaPeriodica.is(':checked')){
		if(importeRenta != null && importeRenta != "" && numPeriodo != null &&  numPeriodo != ""){
			importeRenta=importeRenta.replace(',','.');
			importe = (importeRenta * numPeriodo).toString();
			if(importe.indexOf('.') > -1){
				importe = importe.replace('.',',');
			}
		}
	}else if($checkDurRentaGlobal.is(':checked')){
		if($("#idImporteRenta").val() != null && $("#idImporteRenta").val() != "" ){
			importe = $("#idImporteRenta").val();
			if(importe.indexOf('.') > -1){
				importe = importe.replace('.',',');
			}
		}
	}
	$("#idBaseImponible").val(importe);
}
function cambiarComa(id){
	$(id).val($(id).val().replace(',','.'));
}

function calcularBaseImponibleDerReal(){
	var anioDevengado = $("#idAnioDevengo").val();
	var valorDeclarado = $("#idValorDeclarado").val();
	var numAnio = $("#idNumAnio").val();
	var anioFechaUsufructo = $("#idAnioNacUsufructurario").val();
	var descripcionDerechoReal = $("#idDerechoReal").val();
	var $checkDurDerRealTemporal = $("#duracionTEMPORAL");
	var $checkDurDerRealVitalicio = $("#duracionVITALICIO");
	if($("#idEstadoModelos").val() == "1" || $("#idEstadoModelos").val() == "2"){
		if (descripcionDerechoReal != "OTROS"){
			if(valorDeclarado != null && valorDeclarado != "0" && valorDeclarado != ""){
				valorDeclarado=valorDeclarado.replace(',','.');
			}else{
				$("#idBaseImponible").val("0,00");
				return null;
			}
			var duracionDerechoReal = null;
			if($checkDurDerRealTemporal.is(':checked')){
				duracionDerechoReal = $checkDurDerRealTemporal.val();
				if(numAnio == null || numAnio == ""){
					$("#idBaseImponible").val("0,00");
					return null;
				}
			}else if($checkDurDerRealVitalicio.is(':checked')){
				duracionDerechoReal = $checkDurDerRealVitalicio.val();
				if(anioFechaUsufructo == null || anioFechaUsufructo == ""){
					$("#idBaseImponible").val("0,00");
					return null;
				}
			}else{
				return null;
			}
			if(anioDevengado == null || anioDevengado == ""){
				return null;
			}
			
			var usufructo = calculaUsufructo(duracionDerechoReal, numAnio, anioFechaUsufructo, anioDevengado);
			
			var baseImponible = 0;
			
			if (descripcionDerechoReal == "USUFRUCTO"){
				baseImponible = parseFloat(valorDeclarado) * parseFloat(usufructo) / 100;
			}else if (descripcionDerechoReal == "NUDA PROPIEDAD"){
				baseImponible = parseFloat(valorDeclarado) * (1- parseFloat(usufructo) / 100);
			}else if (descripcionDerechoReal == "USO O HABITACION"){
				baseImponible = parseFloat(valorDeclarado) * 0.75 * parseFloat(usufructo) / 100;
			}
			
			$("#idBaseImponible").val(Math.round(baseImponible * 100) / 100);
			$("#idBaseImponible").prop("disabled",true);
			$("#idBaseLiquidable").val($("#idBaseImponible").val());
			$("#idTipo").val("0,00");
			$("#idCuota").val("0,00");
			$("#idIngresar").val("0,00");
			$("#idTotalIngresar").val("0,00");
		}else{
			var baseImponible = $("#idBaseImponible").val();
			if(baseImponible == null || baseImponible == 0){
				$("#idBaseImponible").val("0,00");
			}
			$("#idBaseImponible").prop("disabled",false);
			$("#idBaseImponible").prop("readonly",false);
		}
	}
}


function calculaUsufructo(duracionDerechoReal, numAnio, anioNacUsu, anioDevengo){
	var usufructo = 0;
	var edad = 0;
	if (duracionDerechoReal == "TEMPORAL") {
		usufructo = 2 * numAnio;
	}else{
		edad = anioNacUsu - anioDevengo;
		usufructo = 89 - edad;
		if (usufructo > 70){
			usufructo = 70;
		}
	}
	return usufructo;
	
}

function habilitarCamposSujetoPasivo(){
	$('#idTipoPersonaSujPasivo').prop("disabled", false);
	$('#idSexoSujPasivo').prop("disabled", false);
	$('#idPrimerApeRazonSocialSujPasivo').prop("disabled", false);
	$('#idSegundoApeSujPasivo').prop("disabled", false);
	$('#idNombreSujPasivo').prop("disabled", false);
	$('#idDiaNacSujPasivo').prop("disabled", false);
	$('#idMesNacSujPasivo').prop("disabled", false);
	$('#idAnioNacSujPasivo').prop("disabled", false);
	$('#idProvinciaSujPasivo').prop("disabled", false);
	$('#idNombreViaSujPasivo').prop("disabled", false);
	$('#idCodPostalSujPasivo').prop("disabled", false);
	$('#idNumeroDireccionSujPasivo').prop("disabled", false);
	$('#idLetraDireccionSujPasivo').prop("disabled", false);
	$('#idEscaleraDireccionSujPasivo').prop("disabled", false);
	$('#idPisoDireccionSujPasivo').prop("disabled", false);
	$('#idPuertaDireccionSujPasivo').prop("disabled", false);
	$('#idBloqueDireccionSujPasivo').prop("disabled", false);
	$('#idKmDireccionSujPasivo').prop("disabled", false);
	$('#idPorcentajeSujPasivo').prop("disabled", false);
	$("#idDireccionSujPasivo").prop("disabled", false);
}
function habilitarCamposTransmitente(){
	$('#idNifTransmitente').prop("disabled", false);
	$('#idTipoPersonaTransmitente').prop("disabled", false);
	$('#idSexoTransmitente').prop("disabled", false);
	$('#idPrimerApeRazonSocialTransmitente').prop("disabled", false);
	$('#idSegundoApeTransmitente').prop("disabled", false);
	$('#idNombreTransmitente').prop("disabled", false);
	$('#idDiaNacTransmitente').prop("disabled", false);
	$('#idMesNacTransmitente').prop("disabled", false);
	$('#idAnioNacTransmitente').prop("disabled", false);
	$('#idProvinciaTransmitente').prop("disabled", false);
	$('#idNombreViaTransmitente').prop("disabled", false);
	$('#idCodPostalTransmitente').prop("disabled", false);
	$('#idNumeroDireccionTransmitente').prop("disabled", false);
	$('#idLetraDireccionTransmitente').prop("disabled", false);
	$('#idEscaleraDireccionTransmitente').prop("disabled", false);
	$('#idPisoDireccionTransmitente').prop("disabled", false);
	$('#idPuertaDireccionTransmitente').prop("disabled", false);
	$('#idBloqueDireccionTransmitente').prop("disabled", false);
	$('#idKmDireccionTransmitente').prop("disabled", false);
	$('#idPorcentajeTransmitente').prop("disabled", false);
	$("#idDireccionTransmitente").prop("disabled", false);
}

function validarBloque(){
	var valueBoton = $("#bValidarModelo").val();
	mostrarLoadingModelo('bValidarModelo');
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
		ocultarLoadingModelo('bValidarModelo',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para validar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea validar los modelos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "validarConsultaMd.action#").trigger("submit");
	}else{
		ocultarLoadingModelo('bValidarModelo',valueBoton);
	}
}

function eliminarBloque(){
	var valueBoton = $("#bEliminar").val();
	mostrarLoadingModelo('bEliminar');
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
		ocultarLoadingModelo('bEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los modelos seleccionados?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaMd.action#").trigger("submit");
	}else{
		ocultarLoadingModelo('bEliminar',valueBoton);
	}
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

function autoLiquidarBloque(){
	var valueBoton = $("#bAutoLiquidar").val();
	mostrarLoadingModelo('bAutoLiquidar');
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
		ocultarLoadingModelo('bAutoLiquidar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para autoLiquidar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea autoliquidar los modelos seleccionados? Recuerde que para poder liquidar los modelos que lleven marcado el check de responsabilidad del Gestor Administrativo, deberan de tener rellenos todos los datos de la liquidaci\u00F3n.")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "autoliquidarConsultaMd.action#").trigger("submit");
	}else{
		ocultarLoadingModelo('bAutoLiquidar',valueBoton);
	}
}

function cargarVentanaCambioEstado(){
	var valueBoton = $("#bCambiarEstado").val();
	mostrarLoadingModelo('bCambiarEstado');
	var codigos = "";
	var sonRemesas = false;
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
			if(document.getElementById("idNumExpRemesa" + this.value).value != null && 
					document.getElementById("idNumExpRemesa" + this.value).value != ""){
				sonRemesas = true;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingModelo('bCambiarEstado',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaModelo");
	$.post("cargarPopUpCambioEstadoConsultaMd.action", function(data) {
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
						if(estadoNuevo == "7" && sonRemesas){
							alert("No se puede anular un modelo con una remesa asociada sino se anula todo en bloque, dirigase al apartado de remesas y anule dicha remesa completa.");
							return false;
						}else if(estadoNuevo == "1" && sonRemesas){
							alert("No se puede poner en Iniciado un modelo con una remesa asociada.");
							return false;
						}else if(estadoNuevo == "2" && sonRemesas){
							alert("No se puede poner en Validado un modelo con una remesa asociada.");
							return false;
						}
						$("#divEmergenteConsultaModelo").dialog("close");
						limpiarHiddenInput("estadoModeloNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoModeloNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						if(estadoNuevo == "7" && sonRemesas){
							alert("No se puede anular un modelo con una remesa asociada sino se anula todo en bloque, dirigase al apartado de remesas y anule dicha remesa completa.");
						}else{
							$("#formData").attr("action", "cambiarEstadoConsultaMd.action#").trigger("submit");
						}
					},
					Cerrar : function() {
						ocultarLoadingModelo('bCambiarEstado',valueBoton);
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

function presentarBloque(){
	var valueBoton = $("#bPresentar").val();
	mostrarLoadingModelo('bPresentar');
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
		ocultarLoadingModelo('bPresentar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para presentar.");
	}
	if (confirm("Se le va a descontar 1 credito por modelo presentado. Una vez confirmados los datos de la autoliquidaci\u00F3n, "+
			"el usuario acepta expresamente la presentaci\u00F3n telem\u00E1tica de la solicitud a la Oficina Virtual  "+
			"de Impuestos Auton\u00F3micos de la Comunidad de Madrid, as\u00ED como el pago correspondiente de la cantidad autoliquidada en cada modelo.\u00BFEst\u00E1 usted seguro?")){
		$.ajax({
			url:"comprobarContratosModelosAjaxMd.action",
			data:"codigo="+ codigos,
			type:'POST',
			success: function(data){
				if(data){
					generarPopPupPresentacion(codigos,valueBoton);
				}else{
					alert("Para poder presentar en bloque los modelos deben de pertenecer todos al mismo contrato.");
					ocultarLoadingModelo('bPresentar',valueBoton);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de comprobar los contratos de los modelos.');
		        ocultarLoadingModelo('bPresentar',valueBoton);
		    }
		});
	}else{
		  ocultarLoadingModelo('bPresentar',valueBoton);
	}
}

function generarPopPupPresentacion(codigos,valueBoton){
	var $dest = $("#divEmergenteConsultaModelo");
	$.post("cargarPopUpPresentarConsultaMd.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 800,
				height: 300,
				buttons : {
					Presentar : function() {
//						if (document.getElementById("fichero").value.length > 0 || document.getElementById("nombreEscritura").value.length > 0) {
	
							var $checkTipoNueva = $("#idTipoDatoBancario0");
							var $checkTipoExistente = $("#idTipoDatoBancario1");
							var nifPagador = $("#idNifPagadorDatBancarios").val();
							var entidad =  $("#idEntidad").val();
							var oficina = $("#idOficina").val();
							var dc = $("#idDC").val();
							var numCuentaPago = $("#idNumeroCuentaPago").val();
							
							var cloneInput = $("#fichero").clone()[0];
							$('#formData').append(cloneInput);
							$("#fichero").hide();
							
							if($checkTipoNueva.is(':checked')){
								if($("#idNifPagadorDatBancarios").val() == null && $("#idNifPagadorDatBancarios").val() == ""){
									return alert("Debe de introducir el nif del pagador para realizar la presentaciï¿½n.");
								}else if($("#idEntidad").val() == null && $("#idEntidad").val() == ""){
									return alert("Debe de introducir la entidad del numero de cuenta para poderrealizar la presentaciï¿½n.");
								}else if($("#idOficina").val() == null && $("#idOficina").val() == ""){
									return alert("Debe de introducir la oficina del numero de cuenta para poder realizar la presentaciï¿½n.");
								}else if($("#idDC").val() == null && $("#idDC").val() == ""){
									return alert("Debe de introducir el DC del numero de cuenta para poder realizar la presentaciï¿½n.");
								}else if($("#idNumeroCuentaPago").val() == null && $("#idNumeroCuentaPago").val() == ""){
									return alert("Debe de introducir el numero de cuenta para poder realizar la presentaciï¿½n.");
								}
								limpiarHiddenInput("datosBancarios.nifPagador");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.nifPagador").val(nifPagador);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.entidad");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.entidad").val(entidad);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.oficina");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.oficina").val(oficina);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.dc");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.dc").val(dc);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.numeroCuentaPago");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.numeroCuentaPago").val(numCuentaPago);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.tipoDatoBancario");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.tipoDatoBancario").val($checkTipoNueva.val());
								$('#formData').append($(input));
							}else if( $checkTipoExistente.is(':checked')){
								if($("#idComboExistente").val() == "" || $("#idComboExistente").val() == "-1"){
									return alert("Debe de seleccionar alguna cuenta existente para poder realizar la presentaciï¿½n.");
								}
								var idDatosBancarios = $("#idDatosBancarios").val();
								limpiarHiddenInput("datosBancarios.idDatosBancarios");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.idDatosBancarios").val(idDatosBancarios);
								$('#formData').append($(input));
								limpiarHiddenInput("datosBancarios.tipoDatoBancario");
								input = $("<input>").attr("type", "hidden").attr("name", "datosBancarios.tipoDatoBancario").val($checkTipoExistente.val());
								$('#formData').append($(input));
							}
							$("#divEmergenteConsultaModelo").dialog("close");
							limpiarHiddenInput("codSeleccionados");
							habilitarCamposDatosBancariosConsulta();
							input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
							$('#formData').append($(input));
							$("#formData").attr("action", "presentarBloqueConsultaMd.action").trigger("submit");
//						}else{
//							alert("Debe de adjuntar una escritura");
//						}
					},
					Cerrar : function() {
						ocultarLoadingModelo('bPresentar',valueBoton);
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

function habilitarPorTipoDatoBancarioConsulta(){
	var $checkTipoNueva = $("#idTipoDatoBancario0");
	var $checkTipoExistente = $("#idTipoDatoBancario1");
	
	if($checkTipoNueva.is(':checked')){
		 $("#idSeleccionDatosBancFavoritos").hide();
		 $("#tablaDatosBancarios").show();
		 habilitarCamposDatosBancariosConsulta();
	}else if($checkTipoExistente.is(':checked')){
		 $("#idSeleccionDatosBancFavoritos").show();
		 $("#tablaDatosBancarios").hide();
		 deshabilitarCamposDatosBancariosConsulta();
	}else{
		 $("#idSeleccionDatosBancFavoritos").hide();
		 $("#tablaDatosBancarios").hide();
	}
	 limpiarCamposDatosBancariosConsulta();
}

function limpiarCamposDatosBancariosConsulta(){
	$("#idNifPagadorDatBancarios").val("");
	$("#idDescripcionDatBancarios").val("");
	$("#idEntidad").val("");
	$("#idOficina").val("");
	$("#idDC").val("");
	$("#idNumeroCuentaPago").val("");
	$("#idDatosBancarios").val("");
	$("#idComboExistente").val("");
}

function abrirEvolucionModelos600601(numExpediente){
	if(numExpediente == null || numExpediente == ""){
		return alert("No se puede obtener la evolución del modelo seleccionado.");
	}
	var $dest = $("#divEmergenteConsultaModeloEvolucion");
	var url = "abrirEvolucionConsultaMd.action?numExpediente=" + numExpediente;
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

function cambiarElementosPorPaginaEvolucionModelos() {
	var $dest = $("#displayTagEvolucionModeloDiv");
	$.ajax({
		url:"navegarEvolucionMds.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolModelos").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones.');
	    }
	});
}

function habilitarPorTipoDatoBancario(){
	var $checkTipoNueva = $("#idTipoDatoBancario0");
	var $checkTipoExistente = $("#idTipoDatoBancario1");
	var $tipoModelo = $("#tipoModelo");
	if($('#idEstadoModelos').val() == "5" || $('#idEstadoModelos').val() == "6"){
		if($("#idDatosBancarios").val() != null && $("#idDatosBancarios").val() != ""){
			$checkTipoExistente.prop("checked", true);
			$("#idComboExistente").val($("#idDatosBancarios").val());
			deshabilitarCamposDatosBancarios();
			 if($('#idEstadoModelos').val() == "6"){
				 $('#idTipoDatoBancario0').prop("disabled", true);
				 $('#idTipoDatoBancario1').prop("disabled", true);
				 $('#idComboExistente').prop("disabled", true);
			 }
		}else if($("#idNifPagadorDatBancarios").val() != null && $("#idNifPagadorDatBancarios").val() != ""){
			$checkTipoNueva.prop("checked", true);
			 $("#idSeleccionDatosBancFavoritos").hide();
			 $("#idFavoritoDatBancario").prop("disabled", false);
			 if($('#idEstadoModelos').val() == "6"){
				 deshabilitarCamposDatosBancarios();
				 $('#idTipoDatoBancario0').prop("disabled", true);
				 $('#idTipoDatoBancario1').prop("disabled", true);
			 }
		}
	}else{
		if($checkTipoNueva.is(':checked')){
			if("600" == $tipoModelo.val()){
				 $("#divDatosBancarios600").show();
			} else {
				 $("#divDatosBancarios601").show();
			}
			 $("#idSeleccionDatosBancFavoritos").hide();
			 $("#idFavoritoDatBancario").prop("disabled", false);
			 limpiarCamposDatosBancarios();
			 habilitarCamposDatosBancarios();
		}else if($checkTipoExistente.is(':checked')){
			if("600" == $tipoModelo.val()){
				 $("#divDatosBancarios600").hide();
			} else {
				 $("#divDatosBancarios601").hide();
			}
			$("#idSeleccionDatosBancFavoritos").show();
			$("#idFavoritoDatBancario").prop("disabled", true);
		}else{
			if("600" == $tipoModelo.val()){
				 $("#divDatosBancarios600").hide();
			} else {
				 $("#divDatosBancarios601").hide();
			}
			$("#idSeleccionDatosBancFavoritos").hide();
			$("#idFavoritoDatBancario").prop("disabled", true);
			limpiarCamposDatosBancarios();
		}
	}
}

function cargarDatosBancariosFavoritos(destino){
	var $dest = $("#"+destino);
	if($("#idComboExistente").val() == ""){
		limpiarCamposDatosBancarios();
		habilitarCamposDatosBancarios();
	}else{
		$.ajax({
			url:"cargarDatosBancariosAjaxMd.action#DatosBancarios",
			data:"codigo="+ $("#idComboExistente").val() + "&tipoVentana=" + $("#tipoModelo").val(),
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
				$dest.show();
				$("#idFavoritoDatBancario").prop("disabled", false);
				deshabilitarCamposDatosBancarios();
				$("#idDatosBancarios").val($("#idComboExistente").val());
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
		    }
		});
	}
}

function cargarDatosBancariosFavoritosPopPup(){
	var $dest = $("#tablaDatosBancarios");
	if($("#idComboExistente").val() == ""){
		habilitarPorTipoDatoBancarioConsulta();		
		limpiarCamposDatosBancariosConsulta();
	}else{
		$.ajax({
			url:"cargarDatosBancariosAjaxMd.action",
			data:"codigo="+ $("#idComboExistente").val() + "&tipoVentana=2",
			type:'POST',
			success: function(data){
				filteredResponse =  $(data).find($dest.selector);
				if(filteredResponse.size() == 1){
					$dest.html(filteredResponse);
				}
				$dest.show();
				$("#idDatosBancarios").val($("#idComboExistente").val());
				deshabilitarCamposDatosBancariosConsulta();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar los datos bancarios.');
		    }
		});
	}
}

function deshabilitarCamposDatosBancariosConsulta(){
	 $("#idNifPagadorDatBancarios").prop("disabled", true);
	 $("#idDescripcionDatBancarios").prop("disabled", true);
	 $("#idEntidad").prop("disabled", true);
	 $("#idOficina").prop("disabled", true);
	 $("#idDC").prop("disabled", true);
	 $("#idNumeroCuentaPago").prop("disabled", true);
}

function habilitarCamposDatosBancariosConsulta(){
	 $("#idNifPagadorDatBancarios").prop("disabled", false);
	 $("#idDescripcionDatBancarios").prop("disabled", false);
	 $("#idEntidad").prop("disabled", false);
	 $("#idOficina").prop("disabled", false);
	 $("#idDC").prop("disabled", false);
	 $("#idNumeroCuentaPago").prop("disabled", false);
}

function limpiarCamposDatosBancarios(){
	$("#idNifPagadorDatBancarios").val("");
	$("#idDescripcionDatBancarios").val("");
	$("#idEntidad").val("");
	$("#idOficina").val("");
	$("#idDC").val("");
	$("#idNumeroCuentaPago").val("");
	$("#idFavoritoDatBancario").prop("checked",false);
	$("#idDatosBancarios").val("");
	$("#idComboExistente").val("");
}

function deshabilitarCamposDatosBancarios(){
	 $("#idNifPagadorDatBancarios").prop("disabled", true);
	 $("#idDescripcionDatBancarios").prop("disabled", true);
	 $("#idEntidad").prop("disabled", true);
	 $("#idOficina").prop("disabled", true);
	 $("#idDC").prop("disabled", true);
	 $("#idNumeroCuentaPago").prop("disabled", true);
	 $("#idFavoritoDatBancario").prop("disabled", true);
}

function habilitarCamposDatosBancarios(){
	 $("#idNifPagadorDatBancarios").prop("disabled", false);
	 $("#idDescripcionDatBancarios").prop("disabled", false);
	 $("#idEntidad").prop("disabled", false);
	 $("#idOficina").prop("disabled", false);
	 $("#idDC").prop("disabled", false);
	 $("#idNumeroCuentaPago").prop("disabled", false);
	 $("#idFavoritoDatBancario").prop("disabled", false);
}

function presentarModelo(){
	if (document.getElementById("fichero").value.length > 0 || document.getElementById("nombreEscritura").value.length > 0
			|| $.inArray($("#idExprAbreviada").val(),['TU1', 'TU2', 'TU5', 'TU6' , 'TR0', 'PH1', 'PH2', 'PH3', 'PH4'])>-1) {
		if (confirm("Se le va a descontar 1 credito. Una vez confirmados los datos de la autoliquidaci\u00F3n mostrados anteriormente, "+
			"el usuario acepta expresamente la presentaci\u00F3n telem\u00E1tica de la solicitud a la Oficina Virtual  "+
			"de Impuestos Auton\u00F3micos de la Comunidad de Madrid, as\u00ED como el pago correspondiente de la cantidad de "+ $("#idTotalIngresar").val() +
			"\u20AC;. \u00BFEst\u00E1 usted seguro?")){
			var pestania = obtenerPestaniaSeleccionada();
			deshabilitarCamposComunes();
			deshabilitarBotones();
			$("#formData").attr("action", "presentarMD.action#"+pestania).trigger("submit");
		}
	}else{
		alert("Debe de adjuntar una escritura");
	}
		
}

function cargarListaMunicipiosCam(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxMd.action",
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

function cargarListaPueblosCam(provincia, municipio, pueblo){
	var $dest = $("#"+pueblo);
	$.ajax({
		url:"cargarPuebloAjaxMd.action",
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

function cargarListaTipoViaCam(provincia,tipoVia){
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaCamAjaxMd.action",
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

function borrarComboPuebloCam(pueblo){
	$('#'+pueblo).find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
}

function dameInfoModelos(codResultado,labelResult){
	$.ajax({
		url:"cargarInfoCodResultadoAjaxMd.action",
		data:"codigo="+ codResultado,
		type:'POST',
		success: function(data){
			var $dest = $("#info");
			$dest.css('display', 'block');
			$dest.html(data);
			$dest.offset({top: $('#' + labelResult).offset().top, left: $('#' + labelResult).offset().left + 50});
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las descripciones de los errores.');
	    }
	});
}

function dameInfoSinModelos(){
	$('#info').val("");
	$('#info').css('display', 'none');
	$('#info').hide();
	
}

function imprimirDocModelo(tipoFichero){
	var codigos = "";
	$("input[name='listaChecksResultados']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn resultado para imprimir.");
	}
	if ($("input[name='codigo']").is(':hidden')){
		$("input[name='codigo']").remove();
	}
	if ($("input[name='tipoFichero']").is(':hidden')){
		$("input[name='tipoFichero']").remove();
	}
	deshabilitarCamposComunes();
	limpiarHiddenInput("codigo");
	input = $("<input>").attr("type", "hidden").attr("name", "codigo").val(codigos);
	$('#formData').append($(input));
	limpiarHiddenInput("tipoFichero");
	input = $("<input>").attr("type", "hidden").attr("name", "tipoFichero").val(tipoFichero);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "imprimirDocMD.action#"+pestania).trigger("submit");
}

function imprimirEscritura(){
	deshabilitarCamposComunes();
	if ($("input[name='tipoFichero']").is(':hidden')){
		$("input[name='tipoFichero']").remove();
	}
	input = $("<input>").attr("type", "hidden").attr("name", "tipoFichero").val(3);
	$('#formData').append($(input));
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "imprimirDocMD.action#"+pestania).trigger("submit");
}

function deshabilitarBotonesConsultaModelo(){
	$("#bConsConsulta").prop('disabled', true);
	$("#bConsConsulta").css('color', '#6E6E6E');	
	$("#bLimpiar").prop('disabled', true);
	$("#bLimpiar").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaModelo(){
	$("#bConsConsulta").prop('disabled', false);
	$("#bConsConsulta").css('color', '#000000');	
	$("#bLimpiar").prop('disabled', false);
	$("#bLimpiar").css('color', '#000000');	
}

function bloqueaBotonesModelo() {	
	$('table.acciones input').prop('disabled', true);
	$('table.acciones input').css('color', '#6E6E6E');		
}

function desbloqueaBotonesModelo() {	
	$('table.acciones input').prop('disabled', false);
	$('table.acciones input').css('color', '#000000');		
}

// Para mostrar el loading en los botones de consultar.
function mostrarLoadingModelo(boton) {
	bloqueaBotonesModelo();
	deshabilitarBotonesConsultaModelo();
	document.getElementById("bloqueLoadingModelo").style.display = "block";
	$('#'+boton).val("Procesando..");

}

function ocultarLoadingModelo(boton, value) {
	document.getElementById("bloqueLoadingModelo").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesModelo();
	habilitarBotonesConsultaModelo();
}

function marcarTodosModelos(objCheck) {
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


function marcarTodosOtrosBienes(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksOtroBien) {
		if (!document.formData.listaChecksOtroBien.length) { 
			document.formData.listaChecksOtroBien.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksOtroBien.length; i++) {
			document.formData.listaChecksOtroBien[i].checked = marcar;
		}
	}
}

function marcarTodosBienesUrbanos(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksBienUrbano) {
		if (!document.formData.listaChecksBienUrbano.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksBienUrbano.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksBienUrbano.length; i++) {
			document.formData.listaChecksBienUrbano[i].checked = marcar;
		}
	}
}

function marcarTodosBienesRusticos(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecksBienRustico) {
		if (!document.formData.listaChecksBienRustico.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksBienRustico.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksBienRustico.length; i++) {
			document.formData.listaChecksBienRustico[i].checked = marcar;
		}
	}
}

function imprimirBloque(){
	var valueBoton = $("#bImprimir").val();
	mostrarLoadingModelo('bImprimir');
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
		ocultarLoadingModelo('bImprimir',valueBoton);
		return alert("Debe seleccionar alg\u00FAn modelo para imprimir.");
	}
	$.ajax({
		url:"comprobarContratosModelosAjaxMd.action",
		data:"codigo="+ codigos,
		type:'POST',
		success: function(data){
			if(data){
				limpiarHiddenInput("codSeleccionados");
				input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
				$('#formData').append($(input));
				$("#formData").attr("action", "inicioImpMd.action").trigger("submit");
			}else{
				alert("Para poder imprimir en bloque los modelos deben de pertenecer todos al mismo contrato.");
				ocultarLoadingModelo('bImprimir',valueBoton);
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de comprobar los contratos de los modelos.');
	        ocultarLoadingModelo('bImprimir',valueBoton);
	    }
	});
}

function imprimirModelos(){
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
		return alert("Debe seleccionar alg\u00FAn modelo para imprimir.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "obtenerFicheroImpMd.action").trigger("submit");
}

function volverModelos(){
	$("#formData").attr("action", "inicioConsultaMd.action").trigger("submit");
}

function ocultarBotonGuardar(){
	$("#idGuardarModelo").hide();
}

function mostrarBotonGuardar(){
	$("#idGuardarModelo").show();
}

function importarModelo(){
	if(	$("#rbModelo600").is(':checked')){
		$("#formData").attr("action", "importarModelo600importarModelos.action").trigger("submit");
	}
	if(	$("#rbModelo601").is(':checked')){
		$("#formData").attr("action", "importarModelo601importarModelos.action").trigger("submit");
	}

}

function habilitarNuevoBien(){
    $("#divAltaModiBienes").show();
    $("#bienesUrbanos").show();
}

function deshabilitarNuevoBien(){
    $("#divAltaModiBienes").hide();
    $("#bienesUrbanos").hide();
}

function habilitarNuevoBienRustico(){
    $("#divAltaModiBienes").show();
    $("#bienesRusticos").show();
}

function deshabilitarNuevoBienRustico(){
    $("#divAltaModiBienes").hide();
    $("#bienesRusticos").hide();
}

function verEscritura(){
	deshabilitarCamposComunes();
	deshabilitarNuevoBien();
	var pestania = obtenerPestaniaSeleccionada();
	$("#estado").val($("#idEstadoModelos").val());
	$("#formData").attr("action", "verEscrituraMD.action#"+pestania).trigger("submit");
}

function calcularLiquidacion(){
	deshabilitarCamposComunes();
	deshabilitarBotones();
	var pestania = obtenerPestaniaSeleccionada();
	$("#estado").val($("#idEstadoModelos").val());
	$("#formData").attr("action", "calcularLiquidacionMD.action#"+pestania).trigger("submit");
}

function habilitarCamposLiquidacion(){
	
	if($("#idLiqManual").is(':checked')){
		$("#idBaseImponible").prop("readOnly", false);
		$("#idBaseLiquidable").prop("readOnly", false);
		$("#idCuota").prop("readOnly", false);
		$("#idBoniCuota").prop("readOnly", false);
		$("#idIngresar").prop("readOnly", false);
		$("#idTotalIngresar").prop("readOnly", false);
		$("#idBaseImponible").prop("disabled", false);
		$("#idReduccion").prop("disabled", false);
		$("#idTextoLiq").prop("disabled", false);
		$("#idBaseLiquidable").prop("disabled", false);
		$("#idCuota").prop("disabled", false);
		$("#idBoniCuota").prop("disabled", false);
		$("#idIngresar").prop("disabled", false);
		$("#idTotalIngresar").prop("disabled", false);
	}else{
		$("#idBaseImponible").prop("readOnly", true);
		$("#idBaseImponible").val("0,00");
		$("#idReduccion").prop("readOnly", true);
		$("#idTextoLiq").prop("readOnly", true);
		$("#idBaseLiquidable").prop("readOnly", true);
		$("#idBaseLiquidable").val("0,00");
		$("#idTipo").prop("readOnly", true);
		$("#idCuota").prop("readOnly", true);
		$("#idCuota").val("0,00");
		$("#idBoniMonto").prop("readOnly", true);
		$("#idBoniCuota").prop("readOnly", true);
		$("#idBoniCuota").val("0,00");
		$("#idIngresar").prop("readOnly", true);
		$("#idIngresar").val("0,00");
		$("#idTotalIngresar").prop("readOnly", true);
		$("#idTotalIngresar").val("0,00");
	}
}

function actualizarValorDeclarado(){
	$("#idValorDeclarado").val("0");
}

function buscarNotario(){
	var codigo = $('#idCodNotario').val();
	if(codigo != ""){
		limpiarDatosNotario();
		$.ajax({
			url:"buscarNotarioAjaxMd.action#Documento",
			data:"codigo="+ codigo + "&tipoModelo=" + $("#tipoModelo").val(),
			type:'POST',
			success: function(data, xhr, status){
				var datos = data.split("_");
				if(datos[0]=="Error"){
					$('#idMensajeNotario').text('No se ha encontrado el notario');
				}else{
					$('#idCodNotario').val(datos[0]);
					$('#idCodigoNotaria').val(datos[1]);
					$('#idNotarioNombre').val(datos[2]);
					$('#idNotarioApellidos').val(datos[3]);
				}
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el notario.');
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del notario debe rellenar el Codigo.");
	}
}

function limpiarDatosNotario(){
	document.getElementById("idCodNotario").value = "";
	document.getElementById("idCodigoNotaria").value = "";
	document.getElementById("idNotarioNombre").value = "";
	document.getElementById("idNotarioApellidos").value = "";
	$('#idMensajeNotario').text('');
}

function controlNumeros(event){
	var charCode = event.charCode;
	if ((charCode >= 48 && charCode <= 57) || charCode == 9 || charCode == 44 || charCode == 46 || event.keyCode==8){
		return true;
	}else{
		return false;
	}	
}

function validarPorcentaje(id){
	$(id).val($(id).val().replace(',','.'));
	if($(id).val()>100){
		$(id).val($(id).val().substr(0,2));
	}
}
function habilitarVHabitual(selectVH){
	var tipoBien = $("#"+selectVH).val();
	var concepto = $("#idExprAbreviada").val();
	$("#idVHabitual").prop('checked',false);
	$("#idVHabitual", parent.document).prop("disabled", true);
	if(concepto == "TS0" || concepto == "TU1"){
		if(tipoBien == "VI" || tipoBien == "VU" || tipoBien == "VH" || tipoBien == "CT" || tipoBien == "PG"){
			$("#idVHabitual", parent.document).prop("disabled", false);
		}
	}
}

function verificarVhabitual(){
	  alert( "Handler for .change() called." );
}
