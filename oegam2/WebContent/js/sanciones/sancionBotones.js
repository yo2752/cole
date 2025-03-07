var ventanaEstados;

function abrirVentanaSeleccionEstados() {
	if (numChecked() == 0) {
		alert("Debe seleccionar alguna sanci\u00f3n");
		return false;
	}
	ventanaEstados = window
			.open(
					'AbrirPopUpSancion.action',
					'popup',
					'width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

function obtenerListaSanciones(){
	document.formData.action = "buscarConsultaSancionNuevo.action";
	document.formData.submit();
}

function estadoSancionSeleccionado(){
	var estado = document.getElementById("estadoSancion").value;
	if(estado == "-"){
		alert("Seleccione un estado");
		return false;
	}
	window.opener.invokeCambiarEstadoSancion(estado);
}

function borraSancion(boton){
	
	if (numChecked() == 0) {
		alert("Debe seleccionar alguna sanci\u00f3n");
		return false;
	}
	
	if (confirm("¿Está seguro de querer borrar las sanciones seleccionadas?")) {
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
		
		document.forms[0].action = "borrarConsultaSancionNuevo.action?idSancion="
				+ codigos;
		document.forms[0].submit();
		return true;
	}
}



function modificarSancion() {
	if (numChecked() != 1 ) {
		alert("El n\u00famero de sanciones seleccionadas no es correcto, s\u00f3lo se puede tener una sanci\u00f3n seleccionada");
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
	
	document.forms[0].action = "modificarAltaSancionNuevo.action?idSancion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function listadoSancion(boton){
	
	if (numChecked() == 0) {
		alert("Debe seleccionar alguna sanci\u00f3n");
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
	
	document.forms[0].action = "listadoConsultaSancionNuevo.action?idSancion=" + codigos;
	
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

	document.location.href = 'navegarConsultaSancionNuevo.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function doPostWithChecked(url, boton, dest){
	var checks =  $("input[name='listaChecksSanciones']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alguna sanci\u00f3n");
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
			  //ocultarLoadingConsultar(boton);
			});
		
	}else{
		//$.post(url+"?numsExpediente=" + arrayCodigos, $("#formData").serialize());
		var input = $("<input>").attr("type", "hidden").attr("name", "idSanciones").val(arrayCodigos);
		$('#formData').append($(input)).attr("action", url).trigger("submit");
	}
	mostrarLoadingConsultar(boton);
}

function invokeCambiarEstadoSancion(estado) {

	ventanaEstados.close();
	doPostWithChecked(
			"cambiarEstadoConsultaSancionNuevo.action?cambioEstado="
					+ estado, null);

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
		$('#motivo').val('');
	}
	if ($('#estadoSancion')!=null){
		$('#estadoSancion').val('');
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