function comprobarPassword() {
	document.formData.action = "comprobarPasswordProcesosFrontal.action";
	document.formData.submit();
}

function comprobarPasswordGestionProcesos() {
	var validar = true;
	
	if(campoVacio("idUsername")){
		alert("Debe rellenar el Usuario");
		validar = false;
	}
	
	if (campoVacio("idPassword")) {
		alert("Debe introducir la contrase\u00F1a\n");
		validar = false;
	}
	
	if (validar) {
		document.formData.action = "loginLoginCYP.action";
		document.formData.submit();	
	}
}

function irCambioPassword() {
	var validar = true;
	
	if (campoVacio("idUsername")) {
		alert("Debe rellenar el Usuario");
		validar = false;
	}
	
	if (validar) {
		document.formData.action = "irCambioPasswordLoginCYP.action?cambio="+true;
		document.formData.submit();	
	}
}

function cambiarPasswordGestionProcesos() {
	var validar = true;
	
	if (campoVacio("idPasswordCamb")) {
		alert("Debe introducir la contrase\u00F1a\n antigua");
		validar = false;
	}
	
	if (campoVacio("idPasswordNueva")) {
		alert("Debe introducir la contrase\u00F1a\n nueva");
		validar = false;
	}
	
	if (validar) {
		document.formData.action = "cambioPasswordInicioLoginCYP.action";
		document.formData.submit();	
	}
}