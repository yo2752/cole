
function validar() {
	
	remplazarComas("porcentajeCapitalAsistente");
	remplazarComas("porcentajeCapitalAcuerdo");
	remplazarComas("porcentajeSociosAsistentes");
	remplazarComas("porcentajeSociosAcuerdo");
	
	if (selectVacio("tipoReunion")) {
		alert("Debe seleccionar el campo 'Tipo'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("lugar")

	) {
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	
	if (selectVacio("convocatoria")

	) {
		alert("Debe rellenar el campo 'Numero de la convocatoria'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("porcentajeCapitalAsistente")

	) {
		alert("Debe rellenar el campo 'Porcentaje capital asistente'.\nPor favor repase los datos.");
		return false;
	}

	if (campoVacio("porcentajeCapitalAcuerdo")

	) {
		alert("Debe rellenar el campo 'Porcentaje capital acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("porcentajeSociosAsistentes")

	) {
		alert("Debe rellenar el campo 'Porcentaje socios asistentes'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("porcentajeSociosAcuerdo")

	) {
		alert("Debe rellenar el campo 'Porcentaje socios acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("transcripcionContenidoIntegro")

	) {
		alert("Debe rellenar el campo 'Contenido integro de la convocatoria'.\nPor favor repase los datos.");
		return false;
	}
	
	if (!validaNumeroPositivo(document.getElementById("porcentajeCapitalAsistente").value)) {
		alert("El porcentaje capital asistente debe ser un valor numerico.");
		return false;
	}
	
	if (!validaNumeroPositivo(document.getElementById("porcentajeCapitalAcuerdo").value)) {
		alert("El porcentaje capital acuerdo debe ser un valor numerico.");
		return false;
	}
	
	if (!validaNumeroPositivo(document.getElementById("porcentajeSociosAsistentes").value)) {
		alert("El porcentaje socios asistente debe ser un valor numerico.");
		return false;
	}
	
	if (!validaNumeroPositivo(document.getElementById("porcentajeSociosAcuerdo").value)) {
		alert("El porcentaje socios acuerdo debe ser un valor numerico.");
		return false;
	}
	
	return true;
}

function validarBusqueda() {
	eliminaEspacios(new Array("tipoCredito"));
	return true;
}