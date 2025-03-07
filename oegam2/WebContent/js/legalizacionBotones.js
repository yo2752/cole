function obtenerListaPeticiones(){
	document.formData.action = "buscarConsultaLegalizacion.action";
	document.formData.submit();
}

function obtenerListado(){
	$("#formData").attr("action", "listadoDiarioConsultaLegalizacion").trigger("submit");
}

function descargarFicheroLegalizacion(peticion){
	$('#formData').attr("target","").attr("action", "descargarConsultaLegalizacion.action?idPeticion="+peticion).trigger("submit").removeAttribute('target');
}

function borraPeticion(boton){
	
	if (numChecked() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPeticiones");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "borrarConsultaLegalizacion.action?idPeticion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function solicitarDocumentacion(boton){

	if (numChecked() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPeticiones");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "solicitarDocumentacionConsultaLegalizacion.action?idPeticion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksPeticiones");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function cambiarElementosPorPaginaConsultaLegalizacion() {

	document.location.href = 'navegarConsultaLegalizacion.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function mostrarLoadingConsultar(boton) {
	
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultar").style.display = "block";
	boton.value = "Procesando..";

}

function bloqueaBotonesConsulta() {
	
	$('table.acciones input').prop('disabled', 'true');
	$('table.acciones input').css('color', '#6E6E6E');
}

function doPostWithChecked(url, boton, dest){
	var checks =  $("input[name='listaChecksPeticiones']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
	});
	if(dest!=null && dest.length>0){
		$.post(url+"?idPeticiones=" + arrayCodigos,
				$("#formData").serialize(),function(data) {
				alert(data);
			  dest.html(data);
			});
		
	}else{
		var input = $("<input>").attr("type", "hidden").attr("name", "idPeticiones").val(arrayCodigos);
		$('#formData').append($(input)).attr("action", url).trigger("submit");
	}
	mostrarLoadingConsultar(boton);
}

function solicitarLegalizacionesMasivas(){
	if (numChecked() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPeticiones");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	document.formData.action = 'confirmarLegalizacionConsultaLegalizacion.action?idPeticion=' + codigos;
	document.formData.submit();
	
}

function cambiaPantallasFechaLegalizacion(codigos){
	document.formData.action = 'confirmarLegalizacionConsultaLegalizacion.action?idPeticion=' + codigos;
	document.formData.submit();
}

function ocultarDiv(){
	$('#legalizacionMasiva').hide("slow");
	
}

function mostrarDiv(){
	$('#legalizacionMasiva').show("slow");
}

function limpiarCamposConsultaLegalizacion(){
	if ($('#diaInicioLegalizacion')!=null){
		$("#diaInicioLegalizacion").val('');
	}
	if ($('#mesInicioLegalizacion')!=null){
		$("#mesInicioLegalizacion").val('');
	}
	if ($('#anioInicioLegalizacion')!=null){
		$("#anioInicioLegalizacion").val('');
	}
	if ($('#diaFinLegalizacion')!=null){
		$("#diaFinLegalizacion").val('');
	}
	if ($('#mesFinLegalizacion')!=null){
		$("#mesFinLegalizacion").val('');
	}
	if ($('#anioFinLegalizacion')!=null){
		$("#anioFinLegalizacion").val('');
	}
	if ($('#tipoDocumento')!=null){
		$('#tipoDocumento').val('-1');
	}
	if ($('#claseDocumento')!=null){
		$('#claseDocumento').val('-1');
	}
	if ($('#pais')!=null){
		$('#pais').val('');
	}
	if ($('#varFicheroAdjunto')!=null){
		$('#varFicheroAdjunto').val('-1');
	}
	if ($('#varSolicitud')!=null){
		$('#varSolicitud').val('-1');
	}
	if ($('#nombre')!=null){
		$('#nombre').val('');
	}
	if ($('#numColegiado')!=null){
		$('#numColegiado').val('');
	}
	if ($('#referencia')!=null){
		$('#referencia').val('');
	}
	if ($('#estadoPeticion')!=null){
		$('#estadoPeticion').val('-1');
	}
}
