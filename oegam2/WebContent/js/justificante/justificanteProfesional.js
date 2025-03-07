function generarJP(){
	deshabilitarBotonesAltaJP();
	deshabilitarCamposGuardado();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "generarJustificanteAltaJustificanteProfesional.action#"+pestania).trigger("submit");
	return true;
}

function deshabilitarCamposGuardado(){
	$("#estadoId").prop("disabled", false);
	$("#idNifAdquiriente").prop("disabled", false);
	$("#tipoPersonaAdquiriente").prop("disabled", false);
	$("#idMatricula").prop("disabled", false);
	$("#tipoTramiteId").prop("disabled", false);
	$("#idFa").prop("disabled", false);
}

function limpiarConsultaJustificantesProfesionales() {
	$('#numExpediente').val("");
	$('#idContrato').val("");
	$('#estado').val("-1");
	$('#idJustificante').val("");
	if ($('#numColegiado')) {
		$('#numColegiado').val("");
	}
	$('#matricula').val("");
	$('#diaAltaInicio').val("");
	$('#mesAltaInicio').val("");
	$('#anioAltaInicio').val("");
	$('#diaAltaFin').val("");
	$('#mesAltaFin').val("");
	$('#anioAltaFin').val("");
	$('#diaInicio').val("");
	$('#mesInicio').val("");
	$('#anioInicio').val("");
	$('#diaFin').val("");
	$('#mesFin').val("");
	$('#anioFin').val("");
	if ($('#idJustificanteInterno')) {
		$('#idJustificanteInterno').val("");
	}
}

function deshabilitarBotonesAltaJP(){
	$("#idBotonGenerarJustificantesPro").prop("disabled", true);
	$("#idBotonGenerarJustificantesPro").css("color", "#6E6E6E");	
	$("#idVolverJP").prop("disabled", true);
	$("#idVolverJP").css("color", "#6E6E6E");	
	$("#idLimpiarVehiculo").prop("disabled", true);
	$("#idLimpiarVehiculo").css("color", "#6E6E6E");	
	$("#idLimpiarTitular").prop("disabled", true);
	$("#idLimpiarTitular").css("color", "#6E6E6E");
	$("#idForzarJustificantesPro").prop("disabled", true);
	$("#idForzarJustificantesPro").css("color", "#6E6E6E");
	$("#idAnularJustificantesPro").prop("disabled", true);
	$("#idAnularJustificantesPro").css("color", "#6E6E6E");
	$("#idPteAutoJustificantesPro").prop("disabled", true);
	$("#idPteAutoJustificantesPro").css("color", "#6E6E6E");
	$("#idImprimirJustificantesPro").prop("disabled", true);
	$("#idImprimirJustificantesPro").css("color", "#6E6E6E");
}

function habilitarBotonesAltaJP(){
	$("#idBotonGenerarJustificantesPro").prop("disabled", false);
	$("#idBotonGenerarJustificantesPro").css("color","#000000");
	$("#idVolverJP").prop("disabled", false);
	$("#idVolverJP").css("color","#000000");	
	$("#idLimpiarVehiculo").prop("disabled", false);
	$("#idLimpiarVehiculo").css("color","#000000");	
	$("#idLimpiarTitular").prop("disabled", false);
	$("#idLimpiarTitular").css("color","#000000");
	$("#idForzarJustificantesPro").prop("disabled", false);
	$("#idForzarJustificantesPro").css("color","#000000");
	$("#idAnularJustificantesPro").prop("disabled", false);
	$("#idAnularJustificantesPro").css("color","#000000");
	$("#idPteAutoJustificantesPro").prop("disabled", false);
	$("#idPteAutoJustificantesPro").css("color","#000000");
	$("#idImprimirJustificantesPro").prop("disabled", false);
	$("#idImprimirJustificantesPro").css("color","#000000");
}


function abrirDetalle(idNumJustificanteInterno){
	aniadirHidden("justificanteProfDto.idJustificanteInterno", idNumJustificanteInterno, "formData");
	aniadirHidden("vieneDeResumen", true, "formData");
	$("#formData").attr("action", "detalleAltaJustificanteProfesional.action").trigger("submit");
}

function buscarConsultaJustificante() {
	
	var numExpediente = document.getElementById("numExpediente").value;
	
	if (numExpediente != null) {		
		numExpediente = numExpediente.replace(/\s/g,'');
		
	if (isNaN(numExpediente)) {
		alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
			return false;
		}		
		document.getElementById("numExpediente").value = numExpediente;
	}
	
	var idJustificante = document.getElementById("idJustificante").value;
	
	if (idJustificante != null) {		
		idJustificante = idJustificante.replace(/\s/g,'');
		
	if (isNaN(idJustificante)) {
		alert("El campo n\u00FAmero de justificante debe contener solo n\u00FAmeros");
			return false;
		}		
		document.getElementById("idJustificante").value = idJustificante;
	}
	
	var idJustificanteInterno = document.getElementById("idJustificanteInterno");
	
	if (idJustificanteInterno != null && idJustificanteInterno.value != null) {		
		idJustificanteInterno = idJustificanteInterno.value.replace(/\s/g,'');
		
	if (isNaN(idJustificanteInterno)) {
		alert("El campo n\u00FAmero de justificante interno debe contener solo n\u00FAmeros");
			return false;
		}		
		document.getElementById("idJustificanteInterno").value = idJustificanteInterno;
	}
	
	var matricula = new String(document.getElementById("matricula").value);
	
	matricula=Limpia_Caracteres_Matricula(matricula);
	
	document.getElementById("matricula").value = matricula.toUpperCase();
	
	$("#formData").attr("action", "buscarConsultaJustificanteProfesional.action").trigger("submit");
}

function buscarIntervinienteJustificanteProf() {
	var nif = $("#idNifAdquiriente").val();
	if(nif != ""){
		limpiarFormularioTitular();
		var $dest = $("#divTitularJP", parent.document);
		deshabilitarBotonesAltaJP();
		$.ajax({
			url:"consultarPersonaJustificanteProfAjax.action#Titular",
			data:"nifBusqueda="+ nif + "&numColegiado=" +$("#idHiddenNumColegiado").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idApellidoRazonSocialAdquiriente').val() != ""){
					$("#idLimpiarTitular").show();
					$("#botonBuscarNif").prop("disabled", true);
					$('#idNifAdquiriente').prop("disabled", true);
					if($("#idFa").val() != null && $("#idFa").val() != ""){
						$("#idCheckFa").prop("checked",true);
						$("#idFa").prop("disabled",false);
					}
				}else{
					$("#botonBuscarNif").prop("disabled", false);
					$('#idNifAdquiriente').prop("disabled", false);
					$("#idLimpiarTitular").hide();
					$("#idCheckFa").prop("checked",false);
					$("#idFa").prop("disabled",true);
				}
				habilitarBotonesAltaJP();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el titular.');
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del titular debe rellenar el NIF/CIF.");
	}
}

function buscarVehiculoJustificanteProf() {
	var matricula = $("#idMatricula").val();
	if(matricula != ""){
		limpiarFormularioVehiculo();
		var $dest = $("#divVehiculoJP", parent.document);
		deshabilitarBotonesAltaJP();
		$.ajax({
			url:"buscarVehiculoJustificanteProfAjax.action#Titular",
			data:"matriculaBusqueda="+ matricula + "&numColegiado=" +$("#idHiddenNumColegiado").val(),
			type:'POST',
			success: function(data, xhr, status){
				$dest.empty().append(data);
				if($('#idVehiculoText').val() != null && $('#idVehiculoText').val() != ""){
					$("#idLimpiarVehiculo").show();
					$("#idBotonBuscarVehiculo").prop("disabled", true);
					$('#idMatricula').prop("disabled", true);
				}else{
					$("#idBotonBuscarVehiculo").prop("disabled", false);
					$('#idMatricula').prop("disabled", false);
					$("#idLimpiarVehiculo").hide();
				}
				habilitarBotonesAltaJP();
			},
			error : function(xhr, status) {
		        alert('Ha sucedido un error a la hora de cargar el vehículo.');
		    }
		});
	}else{
		alert("Para poder realizar la busqueda del vehículo debe rellenar la matricula.");
	}
}

function limpiarFormularioTitular(){
	$('#idNifAdquiriente').val("");
	$('#tipoPersonaAdquiriente').val("-1");
	$('#sexoAdquiriente').val("-1");
	$('#idApellidoRazonSocialAdquiriente').val("");
	$('#idApellido2Adquiriente').val("");
	$('#idNombreAdquiriente').val("");
	$('#idAnagramaAdquiriente').val("");
	$('#checkIdFuerzasArmadas').prop("checked",false);
	$('#idFa').val("");
	$('#diaNacAdquiriente').val("");
	$('#mesNacAdquiriente').val("");
	$('#anioNacAdquiriente').val("");
	$('#idTelefonoAdquiriente').val("");
	$('#idDireccionAdquiriente').val("");
	$("#idTablaDireccionAdquiriente").hide();
	$('#idProvinciaAdquiriente').val("-1");
	$('#idMunicipioAdquiriente').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idPuebloAdquiriente').find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#codPostalAdquiriente').val("");
	$('#tipoViaAdquiriente').find('option').remove().end().append('<option value="-1">Seleccione Tipo Via</option>').val('whatever');
	$('#viaAdquiriente').val("");
	$('#numeroAdquiriente').val("");
	$('#letraAdquiriente').val("");
	$('#escaleraAdquiriente').val("");
	$('#pisoAdquiriente').val("");
	$('#puertaAdquiriente').val("");
	$('#bloqueAdquiriente').val("");
	$('#kmAdquiriente').val("");
	$('#hmAdquiriente').val("");
	$("#idLimpiarTitular").hide();
	$("#botonBuscarNif").prop("disabled", false);
	$('#idNifAdquiriente').prop("disabled", false);
	$("#idCheckFa").prop("checked",false);
	$("#idFa").prop("disabled",true);
}


function buscarVehiculoJustificante(idMatricula) {
	aniadirHidden("matriculaBusqueda", $("#"+ idMatricula).val(), "formData");
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "buscarVehiculoAltaTramiteJustificanteProfesional.action#"+pestania).trigger("submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function aniadirHidden(nombre,valor,formulario){
	limpiarHiddenInput(nombre);
	input = $("<input>").attr("type", "hidden").attr("name", nombre).val(valor);
	$('#'+formulario).append($(input));
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

function volverConsultaJustificante(){
	$("#formData").attr("action", "inicioConsultaJustificanteProfesional.action").trigger("submit");
}

function mostrarLoadingConsultar(boton) {
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultarJustProf").style.display = "block";
	boton.value = "Procesando..";

}

function bloqueaBotonesConsulta() {	
	$('table.acciones input').prop('disabled', 'true');
	$('table.acciones input').css('color', '#6E6E6E');		
}

function faConValor() {
	if ($('#idFa').val() != null && $('#idFa').val() != "") {
		$('#idFa').prop("disabled", false);
		$('#idFa').prop("checked", true);
		document.getElementById("idLabelFa").style.color = "black";
	} else	{
		$('#idFa').prop("disabled", true);
		$('#idFa').prop("checked", false);
	}
}

function consultaEvolucionVehiculo(idVehiculo, numColegiado){
	if (idVehiculo != null && idVehiculo != "") {
	} else {
		return alert('No hay ning\u00FAn veh\u00EDculo guardado en el tr\u00E1mite');
	}
	
	window.open('inicioConsultaEvVehiculo.action?idVehiculo=' + idVehiculo + '&numColegiado=' + numColegiado, 'popup',
			'width=850,height=450,top=050,left=200');
}

function consultaEvolucionTitular(nif, numColegiado){
	if (nif != null && nif != "") {
		window.open('inicioConsultaEvolucionPersona.action?nif=' + nif + '&numColegiado=' + numColegiado, 'popup',
				'width=850,height=450,top=050,left=200');
	} else {
		alert('No hay ninguna persona guardada para el justificante');
	}
}

function limpiarFormularioVehiculo(){
	$('#idMatricula').val("");
	$('#idVehiculoText').val("");
	$('#idBastidor').val("");
	$('#idMarcaVehiculo').val("");
	$('#marca').val("");
	$('#idTipoVehiculo').val("-1");
	$('#idModeloVehiculo').val("");
	$("#idLimpiarVehiculo").hide();
	$("#idBotonBuscarVehiculo").prop("disabled", false);
	$('#idMatricula').prop("disabled", false);
}

function existeChecked(nameChecked) {
	var numChecked = 0;
	$("input[name='"+nameChecked+"']:checked").each(function() {
		if(this.checked){
			numChecked++;
		}
	});
	return numChecked;
}


function pendienteAutorizacionColegio(){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	$("#formData").attr("action", "pendienteAutorizacionColegioConsultaJustificanteProfesional.action").trigger("submit");
	return true;
}

function forzarJpb(){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	$("#formData").attr("action", "forzarConsultaJustificanteProfesional.action").trigger("submit");
	return true;
}

function imprimirJustificante() {
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	$("#formData").attr("action", "imprimirConsultaJustificanteProfesional.action").trigger("submit");
	return true;
}

function recuperarJPb(){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	$("#formData").attr("action", "recuperarConsultaJustificanteProfesional.action").trigger("submit");
	return true;
}

function anularJPb(){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	$("#formData").attr("action", "anularConsultaJustificanteProfesional.action").trigger("submit");
	return true;
}

function mostrarBloqueActualizadosJustifProf(){
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

function mostrarBloqueFallidosJustifProf(){
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

function descargarFicheroJustificante(){
	$("#formData").attr("action", "descargarConsultaJustificanteProfesional.action").trigger("submit");
}

function cargarTipoMotivo(){
	var $dest = $("#idMotivoJP");
	$.ajax({
		url:"getMotivoPorTipoTramiteJustificanteProfAjax.action#TramiteJustificante",
		data:"tipoTramite="+ $("#tipoTramiteId").val(),
		type:'POST',
		success: function(data){
			$dest.find('option').remove();
			$dest.append('<option value="">Seleccione Motivo</option>').val('whatever');
			if(data != null && data != ""){
				var valor = data.split("||");
				for(var i=0;i<valor.length;i++){
					var motivo = valor[i].split(";");
					$dest.append('<option value="'+motivo[0]+'">'+motivo[1]+'</option>');
				}
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los motivos por tipo de tramites.');
	    }
	});
}

function valoresIniciales(){
	if($("#idHiddenNumeroExpediente").val() != null && $("#idHiddenNumeroExpediente").val() != ""){
		$('#botonBuscarNif').prop("disabled", true);
		$('#idBotonBuscarVehiculo').prop("disabled", true);
		$('#tipoTramiteId').prop("disabled", true);
	}
	
	if($('#estadoId').val() != null && $('#estadoId').val() != ""){ 
		if($('#estadoId').val() == "1"){
			$("#idLimpiarVehiculo").hide();
			$("#idBotonBuscarVehiculo").prop("disabled", true);
			$('#idMatricula').prop("disabled", true);
			$("#idLimpiarTitular").hide();
			$("#botonBuscarNif").prop("disabled", true);
			$('#idNifAdquiriente').prop("disabled", true);
		}else{
			$("#idLimpiarVehiculo").hide();
			$("#idBotonBuscarVehiculo").prop("disabled", true);
			$('#idMatricula').prop("disabled", true);
			$("#idLimpiarTitular").hide();
			$("#botonBuscarNif").prop("disabled", true);
			$('#idNifAdquiriente').prop("disabled", true);
		}
	}
	faConValor();
}

function consultaEvolucionJustificante(idJustificanteInterno,dest){
	if(idJustificanteInterno == null || idJustificanteInterno == ""){
		return alert("No se puede obtener la evolución del justificante profesional.");
	}
	var $dest = $("#"+dest);
	var url = "inicioEvolucionJustificantesProf.action?idJustificanteInterno=" + idJustificanteInterno;
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

function forzarJP(){
	deshabilitarBotonesAltaJP();
	deshabilitarCamposGuardado();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "forzarJustificanteAltaJustificanteProfesional.action#"+pestania).trigger("submit");
	return true;
}

function anularJP(){
	deshabilitarBotonesAltaJP();
	deshabilitarCamposGuardado();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "anularJustificanteAltaJustificanteProfesional.action#"+pestania).trigger("submit");
	return true;
}

function pteAutoColegioJP(){
	deshabilitarBotonesAltaJP();
	deshabilitarCamposGuardado();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "pteAutoColegioJustificanteAltaJustificanteProfesional.action#"+pestania).trigger("submit");
	return true;
}

function imprimirJP(){
	deshabilitarCamposGuardado();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action", "imprimirJustificanteAltaJustificanteProfesional.action#"+pestania).trigger("submit");
	return true;
}

function cargarListaMunicipios(provincia,municipio){
	deshabilitarBotonesAltaJP();
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosJustificanteProfAjax.action#Titular",
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
			habilitarBotonesAltaJP();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
	        habilitarBotonesAltaJP();
	    }
	});
}

function cargarListaPueblos(provincia, municipio, pueblo){
	deshabilitarBotonesAltaJP();
	var $dest = $("#"+pueblo);
	$.ajax({
		url:"cargarPuebloJustificanteProfAjax.action#Titular",
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
			habilitarBotonesAltaJP();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los pueblos.');
	        habilitarBotonesAltaJP();
	    }
	});
}

function cargarListaTipoVia(provincia,tipoVia){
	deshabilitarBotonesAltaJP();
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaJustificanteProfAjax.action#Titular",
		data:"provincia="+ $("#"+provincia).val(),
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
			habilitarBotonesAltaJP();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los tipos de vias de la provincia.');
	        habilitarBotonesAltaJP();
	    }
	});
}

function recuperarCodPostalPorProvinciaYMunicipio(provincia,municipio,codPostal){
	deshabilitarBotonesAltaJP();
	var $dest = $("#"+codPostal);
	$.ajax({
		url:"recuperarCodPostalJustificanteProfAjax.action#Titular",
		data:"provincia="+ $("#"+provincia).val() +"&municipio=" +$("#"+municipio).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.val(data);
			}
			habilitarBotonesAltaJP();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de recuperar el codigo postal.');
	        habilitarBotonesAltaJP();
	    }
	});
}

function borrarComboPueblo(){
	$('#idPuebloAdquiriente').find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
}

function borrarCodPostal(){
	$('#codPostalAdquiriente').val("");
}

function ocultarMensageError(){
	$("#erroresJustificante").hide();
	$("#erroresJustificanteTitular").hide();
	$("#erroresJustificanteVehiculo").hide();
	$("#erroresJustificanteResumen").hide();
}

function cambiarElementosPorPaginaEvolucionJustifProf(){
	var $dest = $("#displayTagEvolucionJustifProfDiv");
	$.ajax({
		url:"navegarEvolucionJustificantesProf.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolJustifProf").val(),
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

function convertirIdsToString(nameChecked){
	var codigosString = "";
	$("input[name='"+nameChecked+"']:checked").each(function() {
		if(codigosString == ""){
			codigosString = this.value;
		} else {
			codigosString += "_" + this.value;
		}
	});
	return codigosString;
}

function generarJpBloque(valueBoton){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	
	limpiarHiddenInput("arrayIdsJustificantes");
	input = $("<input>").attr("type", "hidden").attr("name", "arrayIdsJustificantes").val(convertirIdsToString("idJustificanteInternos"));
	$('#formData').append($(input));
	
	$("#formData").attr("action", "generarJustifProConsultaJustificanteProfesional.action").trigger("submit");
}

function generarPopPupJustifProf(valueBoton){
	if(existeChecked("idJustificanteInternos") == 0) {
		alert("Debe seleccionar alg\u00fan justificante");
		return false;
	}
	var codigos = $("#idJustificanteInternos").val();
	var $dest = $("#divEmergenteGenerarJustifProf");
	$.post("popUpGenJustifConsultaJustificanteProfesional.action", {arrayIdsJustificantes : convertirIdsToString("idJustificanteInternos")},function(data) {
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
					Generar : function() {
						var esOk = true;
						if($("#idMotivoCompletar").val() == ""){
							es = false;
						} else if($("#idTipoTramiteCompletar").val() == ""){
							es = false;
						} else if($("#idDocJustifCompletar").val() == ""){
							es = false;
						}
						if(!esOk){
							alert("Debe seleccionar todos los datos obligatorios de los justificantes.");
							return false;
						}
						limpiarHiddenInput("motivo");
						input = $("<input>").attr("type", "hidden").attr("name", "motivo").val($("#idMotivoCompletar").val());
						$('#formData').append($(input));
						
						limpiarHiddenInput("docJustificante");
						input = $("<input>").attr("type", "hidden").attr("name", "docJustificante").val($("#idDocJustifCompletar").val());
						$('#formData').append($(input));
						
						limpiarHiddenInput("arrayIdsJustificantes");
						input = $("<input>").attr("type", "hidden").attr("name", "arrayIdsJustificantes").val(convertirIdsToString("idJustificanteInternos"));
						$('#formData').append($(input));
						
						$("#formData").attr("action", "generarJustifProConsultaJustificanteProfesional.action").trigger("submit");
					},
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

