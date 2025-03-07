function descargarPoder(){
	$("#idProvinciaEmpresa").prop("disabled",false);
	$('#formData').attr("action","descargarPdfAltaPoderes.action");
	$('#formData').submit();
}

function generarPoder(){
	$("#idProvinciaEmpresa").prop("disabled",false);
	$('#formData').attr("action","generarPoderAltaPoderes.action");
	$('#formData').submit();
}

function limpiarPoder(){
	$("#idNifPoderdante").val("");
	$("#idPrimerApePoderdante").val("");
	$("#idSegundoApePoderdante").val("");
	$("#idNombrePoderdante").val("");
	$("#idNifEmpresa").val("");
	$("#idNombreEmpresa").val("");
	$("#idProvinciaEmpresa").val("");
	$("#idMunicipioEmpresa").val("");
	$('#idPuebloEmpresa').find('option').remove().end().append('<option value="">Seleccione Pueblo</option>').val('whatever');
	$("#idTipoViaEmpresa").val("");
	$("#idNombreViaEmpresa").val("");
	$("#idCodPostalEmpresa").val("");
	$("#idNumeroEmpresa").val("");
	$("#idProvincia").val("");
}

function cargarListaMunicipios(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxPoderTrans.action",
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

function cargarListaTipoVia(provincia,tipoVia){
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaAjaxPoderTrans.action",
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

function borrarComboPueblo(pueblo){
	$('#'+pueblo).find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
}

function cargarListaPueblos(provincia, municipio, pueblo){
	var $dest = $("#"+pueblo);
	$.ajax({
		url:"cargarPuebloAjaxPoderTrans.action",
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


function obtenerCodigoPostalPorMunicipio(provincia,municipio,codPostal){
	var $dest = $("#"+codPostal);
	$dest.val("");
	$.ajax({
		url:"obtenerCodPostalAjaxPoderTrans.action",
		data:"provincia="+ $("#"+provincia).val() +"&municipio=" +$("#"+municipio).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.append(data);
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el codigo postal del municipio.');
	    }
	});
}

function buscarPodTrans(){
	$('#formData').attr("action","buscarConsultaPoderes.action");
	$('#formData').submit();
}

function limpiarConsultaPodTrans(){
	$("#idNifEmpresaPodTrans").val("");
	$("#idNifPoderdantePodTrans").val("");
	$("#idContratoPodTrans").val("");
	$("#diaFechaAltaIniPodTrans").val("");
	$("#mesFechaAltaIniPodTrans").val("");
	$("#anioFechaAltaIniPodTrans").val("");
	$("#diaFechaAltaFinPodTrans").val("");
	$("#mesFechaAltaFinPodTrans").val("");
	$("#anioFechaAltaFinPodTrans").val("");
}

function cambiarElementosPorPaginaConsultaPodTrans(){
	var $dest = $("#displayTagDivConsultaPodTrans");
	$.ajax({
		url:"navegarConsultaPoderes.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsultaNotTrans").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los poderes de transporte.');
	    }
	});
}

function marcarTodosConsultaPodTrans(objCheck){
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

function descargarPoderesBloque(){
	var valueBoton = $("#idDescargarPdf").val();
	mostrarLoadingConsultaPodTrans("idRechazarNotTrans");
	var codigos = getChecksConsultaPodTransMarcados();
	if(codigos == ""){
		ocultarLoadingConsultaPodTrans('idDescargarPdf',valueBoton);
		return alert("Debe seleccionar algún poder para poder descargarlo.");
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarConsultaPoderes.action").trigger("submit");
	ocultarLoadingConsultaPodTrans('idDescargarPdf',valueBoton);
}

function mostrarLoadingConsultaPodTrans(boton) {
	deshabilitarBotonesConsultaPodTrans();
	$('#bloqueLoadingConsultaPodTrans').show();
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaPodTrans(boton, value) {
	$('#bloqueLoadingConsultaPodTrans').hide();
	$('#'+boton).val(value);
	habilitarBotonesConsultaPodTrans();
}

function deshabilitarBotonesConsultaPodTrans(){
	$("#idDescargarPdf").prop('disabled', true);
	$("#idDescargarPdf").css('color', '#6E6E6E');	
	$("#idBuscarPodTrans").prop('disabled', true);
	$("#idBuscarPodTrans").css('color', '#6E6E6E');	
	$("#idLimpiarPodTrans").prop('disabled', true);
	$("#idLimpiarPodTrans").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaPodTrans(){
	$("#idDescargarPdf").prop('disabled', false);
	$("#idDescargarPdf").css('color', '#000000');	
	$("#idBuscarPodTrans").prop('disabled', false);
	$("#idBuscarPodTrans").css('color', '#000000');
	$("#idLimpiarPodTrans").prop('disabled', false);
	$("#idLimpiarPodTrans").css('color', '#000000');
}

function getChecksConsultaPodTransMarcados(){
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "_" + this.value;
			}
		}
	});
	return codigos;
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function abrirDetallePoder(idPoderTransporte){
	limpiarHiddenInput("poderTransporte.idPoderTransporte");
	input = $("<input>").attr("type", "hidden").attr("name", "poderTransporte.idPoderTransporte").val(idPoderTransporte);
	$('#formData').append($(input));
	$('#formData').attr("action","altaAltaPoderes.action");
	$('#formData').submit();
}