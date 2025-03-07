function obtenerListaSanciones(){
	document.formData.action = "buscarConsultaSancion.action";
	document.formData.submit();
}

function borraSancion(boton){
	
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksSanciones");
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
	
	document.forms[0].action = "borrarConsultaSancion.action?idSancion="
			+ codigos;
	document.forms[0].submit();
	return true;
}



function modificarSancion() {
	if (numChecked() != 1 ) {
		alert("El n\u00famero de tr\u00E1mites seleccionados no es correcto, s\u00f3lo se puede tener un tr\u00E1mite seleccionado");
		return false;
	}
	
	var checks = document.getElementsByName("listaChecksSanciones");
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
	
	document.forms[0].action = "modificarAltaSancion.action?idSancion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function listadoSancion(boton){
	
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}

	var checks = document.getElementsByName("listaChecksSanciones");
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
	
	document.forms[0].action = "listadoConsultaSancion.action?idSancion=" + codigos;
	
	document.forms[0].submit();
	return true;
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksSanciones");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function cambiarElementosPorPaginaConsultaSancion() {

	document.location.href = 'navegarConsultaSancion.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function doPostWithChecked(url, boton, dest){
	var checks =  $("input[name='listaChecksSanciones']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
	});
	if(dest!=null && dest.length>0){
		$.post(url+"?idSanciones=" + arrayCodigos,
				$("#formData").serialize(),function(data) {
				alert(data);
			  dest.html(data);
			});
		
	}else{
		var input = $("<input>").attr("type", "hidden").attr("name", "idSanciones").val(arrayCodigos);
		$('#formData').append($(input)).attr("action", url).trigger("submit");
	}
	mostrarLoadingConsultar(boton);
}

function limpiarCamposConsultaSancion(){
	if ($('#diaInicioPresentacion')!=null){
		$("#diaInicioPresentacion").val('');
	}
	if ($('#mesInicioPresentacion')!=null){
		$("#mesInicioPresentacion").val('');
	}
	if ($('#anioInicioPresentacion')!=null){
		$("#anioInicioPresentacion").val('');
	}
	if ($('#diaFinPresentacion')!=null){
		$("#diaFinPresentacion").val('');
	}
	if ($('#mesFinPresentacion')!=null){
		$("#mesFinPresentacion").val('');
	}
	if ($('#anioFinPresentacion')!=null){
		$("#anioFinPresentacion").val('');
	}
	if ($('#motivo')!=null){
		$('#motivo').val('-1');
	}
	if ($('#estadoSancion')!=null){
		$('#estadoSancion').val('-1');
	}
	if ($('#numColegiado')!=null){
		$('#numColegiado').val('');
	}
	if ($('#nombre')!=null){
		$('#nombre').val('');
	}
	if ($('#apellidos')!=null){
		$('#apellidos').val('');
	}
	if ($('#dni')!=null){
		$('#dni').val('');
	}
}