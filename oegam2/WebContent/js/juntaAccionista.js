
function validar() {
	
	remplazarComas("capitalAsistente");
	remplazarComas("porcentajeCapitalAcuerdo");
	remplazarComas("sociosAsistente");
	remplazarComas("porcentajeSociosAcuerdo");
	
	if (selectVacio("tipoReunion")) {
		alert("Debe seleccionar el campo 'Tipo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("lugar")){
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (selectVacio("convocatoria")){
		alert("Debe rellenar el campo 'Numero de la convocatoria'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("capitalAsistente")) {
		alert("Debe rellenar el campo 'Porcentaje Capital Asistente.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeCapitalAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Capital Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("sociosAsistente")){
		alert("Debe rellenar el campo 'Porcentaje Socios Asistente'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeSociosAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Socios Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("formaAprobacionActa")){
		alert("Debe rellenar el campo 'Forma aprobacion acta'.\nPor favor repase los datos.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("capitalAsistente").value)) {
		alert("El porcentaje capital asistente debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("porcentajeCapitalAcuerdo").value)) {
		alert("El porcentaje capital acuerdo debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("sociosAsistente").value)) {
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