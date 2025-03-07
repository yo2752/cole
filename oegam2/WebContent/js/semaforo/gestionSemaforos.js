function nuevoSemaforo(){
	if ($("#idProceso").val() == "") {
        alert('Es obligatorio seleccionar un proceso');
	} else if ($("#idEstado").val() == "") {
        alert('Es obligatorio seleccionar un estado');
	} else {
		$('#formData').attr("action","ejecutarCreacionSemaforo.action");
		$('#formData').submit();
	}
}

function limpiarSemaforo(){
	$("#idProceso").val("");
	$("#idEstado").val("");
}

function consultaEvSemaforo(idSemaforo) {
	var url = 'inicioEvolucionSemaforos.action?idSemaforo=' + idSemaforo;
	var evolucion_ventana = window.open(url, 'popup', 'width=880,height=495,top=150,left=200');
}

function buscarSemaforos(){
	$('#formData').attr('action','buscarGestionSemaforos.action');
	$('#formData').submit();
}

function capturarChequeados() {
	var checks = document.getElementsByName("listaChecksSemaforos");
	var codigos = "";
	var i = 0;
	var j = 0;
	while (i < checks.length) {
		if (checks[i].checked) {
			if (j > 0) {
				codigos = codigos + "_";
			}
			codigos = codigos + checks[i].value;
			j++;
		}
		i++;
	}
	
	return codigos;
}

function cambiarEstadoSemaforos() {
	if (document.getElementById("estadoSemaforoSel").value == '') {
		alert("Tiene que seleccionar un estado");
		return false
	}
	window.opener.invokeCambiarEstado(document.getElementById("estadoSemaforoSel").value);
}

function desabHabSemaforos(){
	var codigos = capturarChequeados();
	if (codigos == '') {
		alert('Debe seleccionar al menos un sem\u00E1foro para deshabilitar / habilitar');
		return;
	}
	
	var arrCodigos = codigos.split('_');
	var hayAnulado = -1;
	for (var i=0; i<arrCodigos.length; i++) {
		var estaAnulado = document.getElementById("estaAnulado" + arrCodigos[i]);
		if (hayAnulado == -1) {
			hayAnulado = estaAnulado.value;
		}
		if ((hayAnulado == '1' && estaAnulado.value == '0') || (hayAnulado == '0' && estaAnulado.value == '1')) {
			alert("para poder deshabilitar tienen que estar todos los sem\u00E1foros sin anular, y para rehabilitar tienen que estar todos anulados");
			return;
		}
	}

	document.getElementById("semaforosChequeados").value = codigos;
	if (hayAnulado == '1') {
		ventanaEstados = window.open('cambiarEstadoGestionSemaforos.action','popup','width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
	} else {
		$('#formData').attr("action","deshabilitarGestionSemaforos.action");
		$('#formData').submit();
		$('#formData').remove(codigos);
	}
}

function limpiarSemaforos(){
	$("#idProceso").val("");
	$("#idEstado").val("");
}

function cambiarElementosPorPaginaConsultaSemaforos() {	
	document.location.href = 'navegarGestionSemaforos.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPaginaSemaforos.value;
}

function cambiarElementosPorPaginaConsultaEvolucionSemaforos() {	
	document.location.href = 'navegarEvolucionSemaforos.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPaginaEvolucionSemaforo.value;
}

function marcarTodosConsultaSemaforos(objCheck){
	var marcar = objCheck.checked;
	if (document.formData.listaChecksSemaforos) {
		if (!document.formData.listaChecksSemaforos.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecksSemaforos.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecksSemaforos.length; i++) {
			document.formData.listaChecksSemaforos[i].checked = marcar;
		}
	}
}

function abrirVentanaSeleccionEstados(){
	var codigos = capturarChequeados();
	if (codigos == '') {
		alert('Debe seleccionar al menos un sem\u00E1foro para el cambio de estado');
		return;
	}
	document.getElementById("semaforosChequeados").value = codigos;
	ventanaEstados = window.open('cambiarEstadoGestionSemaforos.action', 'popup','width=300,height=200,top=250,left=550,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

function invokeCambiarEstado(estadoSel) {
	ventanaEstados.close();

	document.getElementById('estadoSemaforoSel').value = estadoSel;
	
	document.formData.action = "realizarCambioEstadoGestionSemaforos.action";
	document.formData.submit();
	
}