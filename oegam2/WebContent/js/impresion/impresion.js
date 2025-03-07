function descargarFichero(nombreFichero) {
	if(confirm("Este fichero se borrará en 5 minutos una vez impreso ¿Continuar?")) {
		document.formData.nombreFicheroDescargar.value = nombreFichero;
		document.formData.action="imprimirConsultaImpresion.action";
		document.formData.submit();
	}
}

function obtenerListaImpresiones(){
	document.formData.action = "buscarConsultaImpresion.action";
	document.formData.submit();
}

function limpiarCamposConsultaImpresion(){
	if ($('#diaInicioCreacion')!=null){
		$("#diaInicioCreacion").val('');
	}
	if ($('#mesInicioCreacion')!=null){
		$("#mesInicioCreacion").val('');
	}
	if ($('#anioInicioCreacion')!=null){
		$("#anioInicioCreacion").val('');
	}
	if ($('#diaFinCreacion')!=null){
		$("#diaFinCreacion").val('');
	}
	if ($('#mesFinCreacion')!=null){
		$("#mesFinCreacion").val('');
	}
	if ($('#anioFinCreacion')!=null){
		$("#anioFinCreacion").val('');
	}
	if ($('#nombreDocumento')!=null){
		$('#nombreDocumento').val('');
	}
	if ($('#tipoDocumento')!=null){
		$('#tipoDocumento').val('-1');
	}
	if ($('#estado')!=null){
		$('#estado').val('-1');
	}
	if ($('#idContrato')!=null){
		$('#idContrato').val('');
	}
	if ($('#tipoTramite')!=null){
		$('#tipoTramite').val('-1');
	}
	if ($('#numExpediente')!=null){
		$('#numExpediente').val('');
	}
}

function mostrarVentana(evento, indice, lineas) {
	var targ;
	var e = evento;
	if (!evento) {
		e = window.event;
	}
	if (e.target) {
		targ = e.target;
	} else {
		targ = e.srcElement;
	}
	document.getElementById("ventana").innerHTML = document.getElementById("detalles" + indice).innerHTML;
	document.getElementById("ventana").style.top = (targ.y + 23) + "px";
	document.getElementById("ventana").style.left = (targ.x - 18) + "px";
	document.getElementById("ventana").style.height = "auto";
	document.getElementById("ventana").style.visibility = "visible";
}

function ocultarVentana() {
	document.getElementById("ventana").style.visibility = "hidden";
	document.getElementById("ventana").innerHTML = "";
}