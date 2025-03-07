function buscarPropiedades(idBoton){
	document.formData.action = "buscarPropiedades.action";
	document.formData.submit();
	loadingPropiedades(idBoton, "bloqueLoadingPropiedades");
	return true;
}

function refrescarPropiedades(idBoton){
	document.formData.action = "refrescarPropiedades.action";
	document.formData.submit();
	loadingPropiedades(idBoton, "bloqueLoadingPropiedades");
	return true;
}

function loadingPropiedades(idBoton, idBloqueLoading) {

	if(document.getElementById("bloqueLoadingPropiedades")){
		document.getElementById("bloqueLoadingPropiedades").style.display = "block";
	}
	if(document.getElementById("bBuscar")){
		document.getElementById("bBuscar").disabled = "true";
		document.getElementById("bBuscar").style.color = "#6E6E6E";
	}
	if(document.getElementById("bRefrescar")){
		document.getElementById("bRefrescar").disabled = "true";
		document.getElementById("bRefrescar").style.color = "#6E6E6E";
	}
	if(idBoton == "bBuscar"){
		if(document.getElementById("bBuscar")){
			document.getElementById("bBuscar").value = "Procesando...";
		}
	}
	if(idBoton == "bRefrescar"){
		if(document.getElementById("bRefrescar")){
			document.getElementById("bRefrescar").value = "Procesando...";
		}
	}

}

function modificarPropiedad(nombre){
	var splitResult = nombre.split("|");
	document.getElementById("idPropiedadAModificar").value = splitResult[1];
	document.getElementById("nombrePropiedadAModificar").value = splitResult[0];
	document.getElementById("nuevoValor").value = document.getElementById(splitResult[0]).value;
	document.formData.action = "guardarPropiedades.action";
	document.formData.submit();
	return true;
}

function cambiarElementosPorPaginaPropiedades() {

	document.location.href = 'navegarPropiedades.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}