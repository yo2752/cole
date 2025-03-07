function limpiarFormularioConsultaTestra(){
	if (document.getElementById("tipo")) {
		document.getElementById("tipo").value = "";
	}
	if (document.getElementById("dato")) {
		document.getElementById("dato").value = "";
	}
	if (document.getElementById("activo")) {
		document.getElementById("activo").value = "";
	}
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}

	// Fecha Alta
	if (document.getElementById("diaFin")) {
		document.getElementById("diaFin").value = "";
	}
	if (document.getElementById("mesFin")) {
		document.getElementById("mesFin").value = "";
	}
	if (document.getElementById("anioFin")) {
		document.getElementById("anioFin").value = "";
	}
	if (document.getElementById("diaInicio")) {
		document.getElementById("diaInicio").value = "";
	}
	if (document.getElementById("mesInicio")) {
		document.getElementById("mesInicio").value = "";
	}
	if (document.getElementById("anioInicio")) {
		document.getElementById("anioInicio").value = "";
	}
	// Fecha última consulta
	if (document.getElementById("diaFinSancion")) {
		document.getElementById("diaFinSancion").value = "";
	}
	if (document.getElementById("mesFinSancion")) {
		document.getElementById("mesFinSancion").value = "";
	}
	if (document.getElementById("anioFinSancion")) {
		document.getElementById("anioFinSancion").value = "";
	}
	if (document.getElementById("diaInicioSancion")) {
		document.getElementById("diaInicioSancion").value = "";
	}
	if (document.getElementById("mesInicioSancion")) {
		document.getElementById("mesInicioSancion").value = "";
	}
	if (document.getElementById("anioInicioSancion")) {
		document.getElementById("anioInicioSancion").value = "";
	}
}

function consultaMatriculaTestra(){
	doPost('formData', 'buscarConsultaMatriculaTestra.action');
}

function cambiarElementosPorPaginaMatriculas() {
	document.location.href = 'navegarConsultaMatriculasTestra.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarEstadoTestra(activo) {
	doPostWithChecked('formData', 'activacionConsultaMatriculaTestra.action?activo='+activo, 'listaChecksConsultasTestra', 'idConsultas', null, 1);
}

function changeTipo() {
	var $tipo = $("#tipo").val();
	if ($tipo == 'M') {
		$("#labelDato").text("Matrícula:");
		$("#idGuardarDatoTestra").attr('value', 'Guardar matrícula');
	} else if ($tipo == 'C') {
		$("#labelDato").text("Nif/Cif:");
		$("#idGuardarDatoTestra").attr('value', 'Guardar nif/cif');
	} else {
		$("#labelDato").text("Dato:");
		$("#idGuardarDatoTestra").attr('value', 'Guardar dato');
	}
}

function validarDatoNoVacio() {
	var $dato = $("#dato").val();
	if ($dato.trim() == '') {
		return false;
	} else {
		$("#dato").val($dato.toUpperCase());
		return true;
	}
}

function guardarDatoTestra() {
	var $tipo = $("#tipo").val();

	if ($tipo == '') {
		alert ("Debe seleccionar el tipo de dato para consultar en TESTRA");
	} else if ($tipo == 'M' && !validarDatoNoVacio()) {
		alert ("Debe indicar una matrícula válida");
	} else if ($tipo == 'C' && !validarDatoNoVacio()) {
		alert ("Debe indicar un cif/nif válido");
	} else {
		doPost('formData', 'guardarMatriculaTestra.action');
	}
}

function limpiarFormularioAltaTestra(){
	if (document.getElementById("tipo")) {
		document.getElementById("tipo").value = "";
	}
	if (document.getElementById("dato")) {
		document.getElementById("dato").value = "";
	}	
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}
	if (document.getElementById("correoElectronico")) {
		document.getElementById("correoElectronico").value = "";
	}
}

function abrirDetalle (idConsultaTestra){
	aniadirHidden("idConsultaTestra", idConsultaTestra, "formData");
	aniadirHidden("vieneDeResumen", true, "formData");
	$("#formData").attr("action", "detalleMatriculaTestra.action").trigger("submit");
}

function aniadirHidden(nombre,valor,formulario){
	limpiarHiddenInput(nombre);
	input = $("<input>").attr("type", "hidden").attr("name", nombre).val(valor);
	$('#'+formulario).append($(input));
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function activar(){
	doPost('formData', 'activarMatriculaTestra.action');
}
function desactivar(){
	doPost('formData', 'desactivarMatriculaTestra.action');
}

function volverConsultaTestra(){
	doPost('formData', 'inicioConsultaMatriculaTestra.action');
}