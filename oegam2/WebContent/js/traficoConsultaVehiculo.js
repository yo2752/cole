/**
 * *************************** CONSULTA VEHICULO ***********************
 */
var opciones_ventanaEvVeh = 'width=850,height=450,top=050,left=200';

function guardarVehiculo() {
	document.formData.action = "modificacionDetalleVehiculo.action";
	document.formData.submit();
}


function buscarConsultaVehiculo() {
	var matricula = new String(document.getElementById("matricula").value);
	matricula=Limpia_Caracteres_Matricula(matricula);
	document.getElementById("matricula").value = matricula.toUpperCase();
	
	
	var bastidor = new String(document.getElementById("bastidor").value);
	bastidor=Limpia_Caracteres_Bastidor(bastidor);
	document.getElementById("bastidor").value = bastidor.toUpperCase();
	
	document.formData.action = "buscarConsultaVehiculo.action";
	document.formData.submit();
}

function limpiarConsultaVehiculo(){
	$("#idTipoVehiculo").val("");
	$("#idVehiculo").val("");
	$("#numColegiado").val("");
	$("#bastidor").val("");
	$("#matricula").val("");
	$("#diaAltaIni").val("");
	$("#mesAltaIni").val("");
	$("#anioAltaIni").val("");
	$("#diaAltaFin").val("");
	$("#mesAltaFin").val("");
	$("#anioAltaFin").val("");
}

function cambiarElementosPorPaginaConsultaVehiculo() {
	document.location.href = 'navegarConsultaVehiculo.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaEvolucion() {
	document.location.href = 'navegarTrafico.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function consultaEvVehiculo(idVehiculo, numColegiado) {
	var ventana_evolucion = window.open('inicioConsultaEvVehiculo.action?idVehiculo=' + idVehiculo + '&numColegiado=' + numColegiado, 'popup',
		opciones_ventanaEvVeh);
}

function consultaEvolucionVehiculo(idDivVehiculo, numColegiado) {
	var idVehiculo = document.getElementById(idDivVehiculo);
	if (idVehiculo != null && idVehiculo.value != null && idVehiculo.value != "") {
		var ventana = window.open('inicioConsultaEvVehiculo.action?idVehiculo=' + idVehiculo.value + '&numColegiado=' + numColegiado, 'popup',
			opciones_ventanaEvVeh);
	} else {
		alert('No hay ning\u00FAn veh\u00EDculo guardado en el tr\u00E1mite');
	}
}

function consultaEvolucionKmVehiculo(idDivVehiculo, numColegiado){
    var opciones_ventanaKm = 'width=850,height=450,top=050,left=200';
	if (idDivVehiculo!=null && idDivVehiculo!="") {	
		var ventana = window.open('inicioConsultaEvolucionKmVehiculo.action?idVehiculo=' + idDivVehiculo + '&numColegiado=' + numColegiado,'popup',opciones_ventanaKm);
	}
	else alert('No hay ning\u00FAn kilometraje de veh\u00EDculo guardado');
}

function recuperarVehiculo(idVehiculo){
	document.location.href='detalleDetalleVehiculo.action?idVehiculo='+idVehiculo;
}

function habilitarUsado(checked, inicial) {
	var bloqueado = true;
	var color = "#EEEEEE";
	var colorImpor = "";
	var bloqImport = false;
	
	if (checked == true) {
		bloqueado = false;
		color = "white";
	}
	
	if($('#idImportado').is(":checked")){
		color = "white";
	}else{
		colorImpor = "#EEEEEE";
		bloqImport = true;
	}
	
	if(document.getElementById("idMatriculaAyuntamiento")) {
		document.getElementById("idMatriculaAyuntamiento").readOnly = bloqueado;
		document.getElementById("idMatriculaAyuntamiento").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("idMatriculaAyuntamiento").value = "";
		}
	}
	if(document.getElementById("dialimiteMatrTuris")) {
		document.getElementById("dialimiteMatrTuris").readOnly = bloqueado;
		document.getElementById("dialimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("dialimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("meslimiteMatrTuris")) {
		document.getElementById("meslimiteMatrTuris").readOnly = bloqueado;
		document.getElementById("meslimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("meslimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("aniolimiteMatrTuris")) {
		document.getElementById("aniolimiteMatrTuris").readOnly = bloqueado;
		document.getElementById("aniolimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado && !inicial){
			document.getElementById("aniolimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("diaPrimMatri")) {
		document.getElementById("diaPrimMatri").readOnly = bloqueado;
		document.getElementById("diaPrimMatri").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("diaPrimMatri").value = "";
		}
	}
	if(document.getElementById("mesPrimMatri")) {
		document.getElementById("mesPrimMatri").readOnly = bloqueado;
		document.getElementById("mesPrimMatri").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("mesPrimMatri").value = "";
		}
	}
	if(document.getElementById("anioPrimMatri")) {
		document.getElementById("anioPrimMatri").readOnly = bloqueado;
		document.getElementById("anioPrimMatri").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("anioPrimMatri").value = "";
		}
	}
	if(document.getElementById("imgCalendarLimiteMatrTuris")) {
		if(!bloqueado) {
			document.getElementById("imgCalendarLimiteMatrTuris").style.visibility = "visible";
		} else {
			document.getElementById("imgCalendarLimiteMatrTuris").style.visibility = "hidden";
		}
	}
	if(document.getElementById("imgCalendarFechaPrimMatri")) {
		if(!bloqueado) {
			document.getElementById("imgCalendarFechaPrimMatri").style.visibility = "visible";
		} else {
			document.getElementById("imgCalendarFechaPrimMatri").style.visibility = "hidden";
		}
	}
	if(document.getElementById("idLugarPrimeraMatriculacion")) {
		document.getElementById("idLugarPrimeraMatriculacion").disabled = bloqueado;
		document.getElementById("idLugarPrimeraMatriculacion").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("idLugarPrimeraMatriculacion").selectedIndex = 0;
		}
	}
	if(document.getElementById("kmUso")) {
		document.getElementById("kmUso").readOnly = bloqueado;
		document.getElementById("kmUso").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("kmUso").value = "";
		}
	}
	if(document.getElementById("horasUso")) {
		document.getElementById("horasUso").readOnly = bloqueado;
		document.getElementById("horasUso").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("horasUso").value = "";
		}
	}
	if(document.getElementById("idMatriculaOrigen")) {
		document.getElementById("idMatriculaOrigen").readOnly = bloqueado;
		document.getElementById("idMatriculaOrigen").style.backgroundColor = color;
		if(bloqueado && !inicial) {
			document.getElementById("idMatriculaOrigen").value = "";
		}
	}
	if(document.getElementById("idImportado")) {
		document.getElementById("idImportado").disabled = bloqueado;
		document.getElementById("idImportado").style.backgroundColor = color;
		if(bloqueado && !inicial){
			document.getElementById("idImportado").checked = false;
		}
	}
	
	if(document.getElementById("idComboProcedencia")) {
		document.getElementById("idComboProcedencia").disabled = bloqImport;
		document.getElementById("idComboProcedencia").style.backgroundColor = colorImpor;
		if(bloqImport && !inicial) {
			document.getElementById("idComboProcedencia").selectedIndex = 0;
		}
	}
	
	if($('#idMatriculaOrigenExtranjero').is(':hidden')){
		$('#idMatriculaOrigenExtranjero').prop("disabled",bloqImport);
	}
}

function habilitarImportado(checked) {
	var bloqueado = true;
	var color = "#EEEEEE";
	if (checked == true) {
		bloqueado = false;
		color = "white";
	}
	if(document.getElementById("idComboProcedencia")) {
		document.getElementById("idComboProcedencia").disabled = bloqueado;
		document.getElementById("idComboProcedencia").style.backgroundColor = color;
		if (!$('#idMatriculaOrigenExtranjero').is(':hidden')){
			$('#idMatriculaOrigenExtranjero').prop("disabled",bloqueado);
		}
		if(bloqueado){
			document.getElementById("idComboProcedencia").selectedIndex = 0;
			if (!$('#idMatriculaOrigenExtranjero').is(':hidden')){
				$("#idMatriculaOrigenExtranjero").val("");
			}
		}
	}
}



function comprobacionEstadoTramite(idEstado) {
	var estado = idEstado.value;
	var estadosModificables = [1, 5, 6, 7, 8, 9, 11, 15, 26];
	var resultado = false;
	if (estado == null || estado == "") {
		resultado = true;
	}else {
		for (var i = 0; i < estadosModificables.length; i++) {
		    if(estado == estadosModificables[i]) {
		    	resultado = true;
		    	break;
		    }
		}
	}	
	if(!resultado) {
		if(confirm("El veh\u00EDculo se encuentra asociado a un tr\u00E1mite en estado no modificable. " +
				"Si modifica la matricula o el bastidor, puede provocar alguna incongruencia con el tr\u00E1mite. " +
				"As\u00ED mismo, en caso de que exista alg\u00FAn documento base asociado, deber\u00E1 revertirlo inmediatamente")) {
			guardarVehiculo();
		}
	}else {
		guardarVehiculo();
	}
}

