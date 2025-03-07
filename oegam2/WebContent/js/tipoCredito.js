function validar(){
	eliminaEspacios(new Array("tipoCredito", "importe", "descripcion"));
	remplazarComas("importe");
	if(	campoVacio("tipoCredito") ||
		campoVacio("importe")||
		campoVacio("descripcion")){
			alert("Debe rellenar todos los campos obligatorios.\nPor favor repase los datos.");
			return false;
	}
	if(!validaNumeroPositivo(document.getElementById("importe").value)){
		alert("El importe debe ser un valor numérico.");
		return false;
	}
	return true;
}

function validarBusqueda(){
	eliminaEspacios(new Array("tipoCredito")); 
	return true;
}

function deshabilitarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr('disabled', true);
	$("#" + idDiv + " input[type=hidden]").attr('disabled', true);
	$("#" + idDiv + " select").attr('disabled',true);
	$("#" + idDiv + " input[type=button]").attr('disabled',true);
	$("#" + idDiv + " input[type=checkbox]").attr('disabled',true);
}