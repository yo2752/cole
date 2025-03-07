
var idCreditos = new Array();




function  Limpia_Caracteres_Matricula(cadena){
		var temporal="";
	return  cadena.replace(/[^a-zA-Z 0-9.]+/g,'');
	}

function  Limpia_Caracteres_Bastidor(cadena){
	return  cadena.replace(/[^a-zA-Z 0-9. -]+/g,'');	
}

function  Limpia_Caracteres_Estranos(cadena){
		var temporal="";
	return  cadena.replace(/[^a-zA-Z 0-9.]+/g,'');
	}

function mostrarLoadingConsultaContratosAnt(boton) {
	deshabilitarBotonesConsultaContrato();
	document.getElementById("bloqueLoadingContratos").style.display = "block";
	$('#' + boton).val("Procesando..");
}

function ocultarLoadingContratosAnt(boton, value) {
	document.getElementById("bloqueLoadingContratos").style.display = "none";
	$('#' + boton).val(value);
	habilitarBotonesConsultaContrato();
}

function habilitarBotonesConsultaContrato() {
	$("#bBloquear").prop('disabled', false);
	$("#bBloquear").css('color', '#000000');
	$("#bDesbloquear").prop('disabled', false);
	$("#bDesbloquear").css('color', '#000000');
	$("#bEliminar").prop('disabled', false);
	$("#bEliminar").css('color', '#000000');
	$("#bConsConsulta").prop('disabled', false);
	$("#bConsConsulta").css('color', '#000000');
	$("#bLimpiar").prop('disabled', false);
	$("#bLimpiar").css('color', '#000000');
}

function deshabilitarBotonesConsultaContrato() {
	$("#bBloquear").prop('disabled', true);
	$("#bBloquear").css('color', '#6E6E6E');
	$("#bDesbloquear").prop('disabled', true);
	$("#bDesbloquear").css('color', '#6E6E6E');
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#bConsConsulta").prop('disabled', true);
	$("#bConsConsulta").css('color', '#6E6E6E');
	$("#bLimpiar").prop('disabled', true);
	$("#bLimpiar").css('color', '#6E6E6E');
}

function getChecksConsultaMarcados() {
	var codigos = "";
	$("input[name='idContratos']:checked").each(function() {
		if (this.checked) {
			if (codigos == "") {
				codigos += this.value;
			} else {
				codigos += "-" + this.value;
			}
		}
	});
	return codigos;
}

function habilitarContrato() {
	var valueBoton = $("#bBloquear").val();
	mostrarLoadingConsultaContratosAnt("bBloquear");
	var codigos = getChecksConsultaMarcados();
	if (codigos == 0) {
		alert("Debe seleccionar un contrato.");
		ocultarLoadingContratosAnt('bBloquear', valueBoton);
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea habilitar los contratos seleccionados?")) {
		return false;
	}
	generarPopPupMotivo("habilitar", "divEmergenteConsultaContrato", codigos,
			valueBoton, "bBloquear");
}

function generarPopPupMotivo(accion, dest, codigos, valueBoton, boton) {
	var $dest = $("#" + dest);
	$
			.post(
					"cargarPopUpMotivoContratoNuevo.action?accion=" + accion,
					function(data) {
						if (data.toLowerCase().indexOf("<html") < 0) {
							$dest
									.empty()
									.append(data)
									.dialog(
											{
												modal : true,
												show : {
													effect : "blind",
													duration : 300
												},
												dialogClass : 'no-close',
												width : 800,
												height : 300,
												buttons : {
													Continuar : function() {
														if ($("#idMotivo")
																.val() == null
																&& $(
																		"#idMotivo")
																		.val() == "") {
															return alert("Debe de introducir el motivo.");
														} else if ($(
																"#idSolicitante")
																.val() == null
																&& $(
																		"#idSolicitante")
																		.val() == "") {
															return alert("Debe de introducir el solicitante.");
														}
														limpiarHiddenInput("motivo");
														input = $("<input>")
																.attr("type",
																		"hidden")
																.attr("name",
																		"motivo")
																.val(
																		$(
																				"#idMotivo")
																				.val());
														$('#formData').append(
																$(input));
														limpiarHiddenInput("solicitante");
														input = $("<input>")
																.attr("type",
																		"hidden")
																.attr("name",
																		"solicitante")
																.val(
																		$(
																				"#idSolicitante")
																				.val());
														$('#formData').append(
																$(input));
														limpiarHiddenInput("codSeleccionados");
														input = $("<input>")
																.attr("type",
																		"hidden")
																.attr("name",
																		"codSeleccionados")
																.val(codigos);
														$('#formData').append(
																$(input));
														var action = "";
														if (accion == "habilitar") {
															action = "habilitarContratoNuevo.action";
														} else if (accion == "deshabilitar") {
															action = "deshabilitarContratoNuevo.action";
														} else if (accion = "eliminar") {
															action = "eliminarContratoNuevo.action";
														}
														$("#formData")
																.attr("action",
																		action)
																.trigger(
																		"submit");
													},
													Cerrar : function() {
														ocultarLoadingContratos(
																boton,
																valueBoton);
														$(this).dialog("close");
													}
												}
											});
						} else {
							var newDoc = document.open("text/html", "replace");
							newDoc.write(data);
							newDoc.close();
						}
						$(".ui-dialog-titlebar").hide();
					}).always(function() {
				unloading("loadingImage");
			});
}

function deshabilitarContrato() {
	var valueBoton = $("#bDesbloquear").val();
	mostrarLoadingConsultaContratosAnt("bDesbloquear");
	var codigos = getChecksConsultaMarcados();
	if (codigos == 0) {
		alert("Debe seleccionar un contrato.");
		ocultarLoadingContratosAnt('bDesbloquear', valueBoton);
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea deshabilitar los contratos seleccionados?")) {
		return false;
	}
	generarPopPupMotivo("deshabilitar", "divEmergenteConsultaContrato",
			codigos, valueBoton, "bDesbloquear");
}

function eliminarContrato() {
	var valueBoton = $("#bEliminar").val();
	mostrarLoadingConsultaContratosAnt("bEliminar");
	var codigos = getChecksConsultaMarcados();
	if (codigos == 0) {
		alert("Debe seleccionar un contrato.");
		ocultarLoadingContratosAnt('bEliminar', valueBoton);
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea eliminar los contratos seleccionados?")) {
		return false;
	}
	generarPopPupMotivo("eliminar", "divEmergenteConsultaContrato", codigos,
			valueBoton, "bEliminar");
}

function abrirEvolucionContratoAnt(idContrato) {
	if (idContrato == null || idContrato == "") {
		return alert("No se puede obtener la evolución del contrato seleccionado.");
	}
	var $dest = $("#divEmergenteConsultaContratoEvolucion");
	var url = "abrirEvolucionContratoNuevo.action?idContrato=" + idContrato;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html") < 0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : {
					my : 'right',
					at : 'right'
				},
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width : 950,
				height : 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}


function cambiarElementosPorPaginaEvolucionContratos() {
	var $dest = $("#displayTagEvolucionContratoDiv");
	$.ajax({
		url : "navegarEvolucionContrato.action",
		data : "resultadosPorPagina="
				+ $("#idResultadosPorPaginaEvolContrato").val(),
		type : 'POST',
		success : function(data) {
			filteredResponse = $(data).find($dest.selector);
			if (filteredResponse.size() == 1) {
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
			alert('Ha sucedido un error a la hora de cargar las evoluciones.');
		}
	});
}


function reactivarSolicitudes() {

	if (numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea reactivar las solicitudes seleccionadas?")) {
		return false;
	}
	document.forms[1].action = "reactivarSolicitudes.action";
	document.forms[1].submit();
	return true;
}

function FinalizarConError() {

	if (numCheckedSolicitudes() == 0) {
		alert("Debe seleccionar al menos una solicitud.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea reactivar las solicitudes seleccionadas?")) {
		return false;
	}

	document.forms[1].action = "finalizarConErrorSolicitudes.action";
	document.forms[1].submit();
	return true;
}


function nuevoUsuarioColegiado() {
	var altaUsuario = document.getElementById("nuevoUsuario");

	var usuarioSeleccionado = document
			.getElementById("modificarUsuarioSeleccionado");

	altaUsuario.style.display = "block";
	usuarioSeleccionado.style.display = "none";
}


function nuevoUsuarioAsociadoContrato() {
	var altaUsuario = document.getElementById("nuevoUsuarioContrato");

	var usuarioSeleccionado = document
			.getElementById("modificarUsuarioSeleccionadoContrato");

	usuariosGestor.style.display = "block";
	document.forms[1].action = "cargarUsuariosGestoriaDetalleContrato.action?idContrato="
		+ idcontrato + "#UsuariosAsociadosContrato";
	
	altaUsuario.style.display = "block";
	usuarioSeleccionado.style.display = "none";
}

function agregarUsuarioGestoria(idcontrato) {

	if (numCuentaChecked("idUsuariosGestoria") == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea agregar los usuarios seleccionados?")) {
		return false;
	}
	
	document.forms[1].action = "agregarUsuariosGestoriaDetalleContrato.action?idContrato="
			+ idcontrato + "#UsuariosAsociadosContrato";
	document.forms[1].submit();
	return true;
}



function cancelarUsuario() {

	var altaUsuario = document.getElementById("nuevoUsuario");
	var usuarioSeleccionado = document
			.getElementById("modificarUsuarioSeleccionado");

	usuariosGestor.style.display = "none";

	altaUsuario.style.display = "none";
	usuarioSeleccionado.style.display = "none";

	document.formUsuario.reset();

}

function cancelarUsuarioContrato() {

	var altaUsuario = document.getElementById("nuevoUsuarioContrato");
	var usuarioSeleccionado = document
			.getElementById("modificarUsuarioSeleccionadoContrato");

	usuariosGestor.style.display = "none";
	altaUsuario.style.display = "none";
	usuarioSeleccionado.style.display = "none";
	document.formUsuario.reset();

}

function cancelarPermisos() {

	var aplicacionSeleccionada = document
			.getElementById("modificarAplicacionSeleccionado");

	aplicacionSeleccionada.style.display = "none";

}

function habilitarUsuariosColegiado() {

	if (numCuentaChecked("idUsuarios") == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea habilitar los usuarios seleccionados?")) {
		return false;
	}
	document.forms[1].action = "habilitarUsuariosDetalleContrato.action#UsuariosAsociados";
	document.forms[1].submit();
	return true;

}
function deshabilitarUsuariosColegiado() {

	if (numCuentaChecked("idUsuarios") == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea deshabilitar los usuarios seleccionados?")) {
		return false;
	}
	document.forms[1].action = "deshabilitarUsuariosDetalleContrato.action#UsuariosAsociados";
	document.forms[1].submit();
	return true;
}


function eliminarUsuariosAsociadosContrato(idcontrato) {
                        
	if (numCuentaChecked("idUsuariosContrato") == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea eliminar los usuarios seleccionados?")) {
		return false;
	}
	document.forms[1].action = "eliminarUsuariosAsociadosDetalleContrato.action?idContrato="
			+ idcontrato + "#UsuariosAsociadosContrato";
	document.forms[1].submit();
	return true;
}



function eliminarUsuariosColegiado() {

	if (numCuentaChecked("idUsuarios") == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if (!confirm(String.fromCharCode(191)
			+ "Realmente desea eliminar los usuarios seleccionados?")) {
		return false;
	}
	document.forms[1].action = "eliminarUsuariosDetalleContrato.action#UsuariosAsociadosContrato";
	document.forms[1].submit();
	return true;
}



function marcarTodos(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
	if (!arrayObjChecks.length) {
		arrayObjChecks.checked = marcar;
	}
	for (var i = 0; i < arrayObjChecks.length; i++) {
		arrayObjChecks[i].checked = marcar;
	}
}

function cambiarElementosPorPaginaContratos() {

	document.location.href = 'navegarContratoNuevo.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}
function numCheckedContratos() {
	var checks = document.getElementsByName("idContratos");
	var numCheckedContratos = 0;
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			numCheckedContratos++;
	}
	return numCheckedContratos;
}

function numCheckedSolicitudes() {
	var checks = document.getElementsByName("idEnvios");
	var numChecked = 0;
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			numChecked++;
	}
	return numChecked;
}

function numCheckedUsuarios() {
	var checks = document.getElementsByName("idUsuarios");
	var numCheckedUsuarios = 0;
	for (var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			numCheckedUsuarios++;
	}
	return numCheckedUsuarios;
}

function numCheckedUsuariosContrato() {
	var checks_cont = document.getElementsByName("idUsuariosContrato");

	for (var i = 0; i < checks_cont.length; i++) {
		if (checks_cont[i].checked)
			numCheckedUsuarios++;
	}

	return numCheckedUsuarios;
}



function numCuentaChecked(id) {
	var numChecked = 0;
	var checks_cont = document.getElementsByName(id);
	for (var i = 0; i < checks_cont.length; i++) {
		if (checks_cont[i].checked)
			numChecked++;
	}
	return numChecked;
}


function cancelarForm(action) {
	location.href = action;
	return false;
}


function mostrarLineas(funcionPadre) {

	var filas = document.getElementsByClassName("hijo " + funcionPadre);
	var desplegable = document.getElementById("desp" + funcionPadre);
	var i = 0;

	while (filas[i] != null) {

		if (filas[i].style.display == 'none') {
			filas[i].style.display = 'table-row';
			desplegable.src = "img/minus.gif";
			desplegable.alt = "Ocultar";
		} else if (filas[i].style.display == 'table-row') {
			filas[i].style.display = 'none';
			desplegable.src = "img/plus.gif";
			desplegable.alt = "Mostrar";
		} else {
			filas[i].style.display = 'none';
			desplegable.src = "img/plus.gif";
			desplegable.alt = "Mostrar";
		}
		i++;
	}

}

function ocultaHijos() {
	var filas = document.getElementsByClassName("hijo ");
	var i = 0;

	while (filas[i] != null) {

		filas[i].style.display = 'none';
		i++;
	}
}

function remplazarComas(campo) {
	var valor = document.getElementById(campo).value.replace('.', ',');
	document.getElementById(campo).value = valor;
}


function rellenaCerosIzq(campo, numDigitos) {
	var text = document.getElementById(campo).value;
	if (text.length > 0 && esNumerico(text)) {
		numCeros = numDigitos - text.length;
		for (i = 0; i < numCeros; i++) {
			text = "0" + text;
		}
		document.getElementById(campo).value = text;
	}
}

function rellenaCerosIzqVar(text, numDigitos) {
	if (text.length > 0 && esNumerico(text)) {
		numCeros = numDigitos - text.length;
		for (i = 0; i < numCeros; i++) {
			text = "0" + text;
		}
		return text;
	}
}


function calculaLetraDni(campo) {
	var dni = document.getElementById(campo).value;
	if (dni.length == 8 && esNumerico(dni)) {
		var JuegoCaracteres = "TRWAGMYFPDXBNJZSQVHLCKET";
		var Posicion = dni % 23;
		document.getElementById(campo).value = dni
				+ JuegoCaracteres.charAt(Posicion);
	}
}

function limpiarFormCreditosContrato() {

	document.getElementById("tipoCredito").value = "-1";
	document.getElementById("diaAltaIni").value = "";
	document.getElementById("mesAltaIni").value = "";
	document.getElementById("anioAltaIni").value = "";
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
}

function limpiarFormConsultaContrato() {

	document.getElementById("cif").value = "";
	document.getElementById("estadoContrato").value = "-1";
	document.getElementById("razon_Social").value = "";
	document.getElementById("num_Colegiado").value = "";
}
