function aceptarConsultaNotificacion(boton){
	
	if (numCheckedConsultaNotificacionesSS() == 0) {
		alert("Debe seleccionar alg\u00fana notificaci\u00F3n");
		return false;
	}

	var checks = document.getElementsByName("listaChecksNotificacion");
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
	
	document.forms[0].action = "aceptarConsultaNotificacion.action?idNotificacion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function rechazarConsultaNotificacion(boton){
	
	if (numCheckedConsultaNotificacionesSS() == 0) {
		alert("Debe seleccionar alg\u00fana notificaci\u00F3n");
		return false;
	}

	var checks = document.getElementsByName("listaChecksNotificacion");
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
	
	document.forms[0].action = "rechazarConsultaNotificacion.action?idNotificacion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

function imprimirConsultaNotificacion(boton){
	
	if (numCheckedConsultaNotificacionesSS() == 0) {
		alert("Debe seleccionar alg\u00fana notificaci\u00F3n");
		return false;
	}

	var checks = document.getElementsByName("listaChecksNotificacion");
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
	
	document.forms[0].action = "imprimirConsultaNotificacion.action?idNotificacion="
			+ codigos;
	document.forms[0].submit();
	return true;
}

var opciones_ventana = 'width=850,height=200,top=250,left=200';

function abrirEvolucion(codigo, numColegiado) {
	
	var ventana = window.open('verEvolucionConsultaNotificacion.action?codigo=' + codigo + '&numColegiado=' + numColegiado, 'popup',
			opciones_ventana);
	document.forms[0].action = "";
}

function cerrar() {
	this.close();
}
