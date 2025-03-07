function consultarTarjetaEitv(){
	if(document.getElementById("checkDatosTecnicos").checked && document.getElementById("checkFichaTecnica").checked){
		if(document.getElementById("textFieldBastidor").value == ""){
			alert("Introduzca el bastidor del veh\u00edculo");
			return false;
		}
	}else if(document.getElementById("checkDatosTecnicos").checked){
		if(document.getElementById("textFieldBastidor").value == ""){
			alert("Introduzca el bastidor del veh\u00edculo");
			return false;
		}
	}else if(document.getElementById("checkFichaTecnica").checked){
		if(document.getElementById("textFieldBastidor").value == ""){
			alert("Introduzca el bastidor del veh\u00edculo");
			return false;
		}
	}
	document.formData.submit();
	loadingVehiculoPrematriculado();
}

function loadingVehiculoPrematriculado(){
	if(document.getElementById("bloqueLoadingVehiculoPrematriculado")){
		document.getElementById("bloqueLoadingVehiculoPrematriculado").style.display = "block";
	}
	if(document.getElementById("botonConsultar")){
		document.getElementById("botonConsultar").disabled = "true";
		document.getElementById("botonConsultar").style.color = "#6E6E6E";
		document.getElementById("botonConsultar").value = "Procesando...";
	}
	if(document.getElementById("botonLimpiar")){
		document.getElementById("botonLimpiar").disabled = "true";
		document.getElementById("botonLimpiar").style.color = "#6E6E6E";
	}
}

function limpiarFormularioConsultaTarjetaEitv(){
	if(document.getElementById("textFieldNive")){
		document.getElementById("textFieldNive").value = "";
	}
	if(document.getElementById("textFieldBastidor")){
		document.getElementById("textFieldBastidor").value = "";
	}
	if(document.getElementById("checkDatosTecnicos")){
		document.getElementById("checkDatosTecnicos").checked="";
	}
	if(document.getElementById("checkFichaTecnica")){
		document.getElementById("checkFichaTecnica").checked="";
	}
}

function marcarTodosVehiculosPrematriculados(boton, listaCheck){
	var obj = $('[name="'+listaCheck+'"]');
	$.each( obj, function( key, objeto) {
		$(this).attr('checked', boton.checked);
	});
}

function limpiarFormConsultaTarjetaResultado(){
	$('#nive').val('');
	$('#bastidor').val('');
	$('#estadoCaracteristicas').val('');
	$('#estadoFichaTecnica').val('');
	$('#numColegiado').val('');
	$('#diaBusquedaIni').val('');
	$('#mesBusquedaIni').val('');
	$('#anioBusquedaIni').val('');
	$('#diaBusquedaFin').val('');
	$('#mesBusquedaFin').val('');
	$('#anioBusquedaFin').val('');
}