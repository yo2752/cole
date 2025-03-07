function cargarListaMunicipiosCAYC(provincia, municipio) {
	var $dest = $("#" + municipio);
	$
			.ajax({
				url : "cargarMunicipiosAjaxCayc.action",
				data : "provincia=" + $("#" + provincia).val(),
				type : 'POST',
				success : function(data) {
					if (data != "") {
						$dest
								.find('option')
								.remove()
								.end()
								.append(
										'<option value="">Seleccione Municipio</option>')
								.val('whatever');
						var listMuni = data.split("|");
						$.each(listMuni, function(i, o) {
							var municipio = listMuni[i].split("_");
							$dest.append($('<option>', {
								value : municipio[0],
								text : municipio[1]
							}));
						});
					}
				},
				error : function(xhr, status) {
					alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
				}
			});
}
function habilitarBotonesConsultaArrendatarios() {
	$("#idCambiarEstado").prop('disabled', false);
	$("#idCambiarEstado").css('color', '#000000');
	$("#idConsultar").prop('disabled', false);
	$("#idConsultar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
	$("#idValidar").prop('disabled', false);
	$("#idValidar").css('color', '#000000');
	$("#idFinalizar").prop('disabled', false);
	$("#idFinalizar").css('color', '#000000');

}
function deshabilitarBotonesConsultaArrendatarios() {
	$("#idCambiarEstado").prop('disabled', true);
	$("#idCambiarEstado").css('color', '#6E6E6E');
	$("#idConsultar").prop('disabled', true);
	$("#idConsultar").css('color', '#6E6E6E');
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#idValidar").prop('disabled', true);
	$("#idValidar").css('color', '#6E6E6E');
	$("#idFinalizar").prop('disabled', true);
	$("#idFinalizar").css('color', '#6E6E6E');
}

function mostrarLoadingArrendatarios(bloqueLoading, boton) {
	document.getElementById(bloqueLoading).style.display = "block";
	$('#' + boton).val("Procesando..");
}

function ocultarLoadingArrendatarios(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#' + boton).val(value);
}
function getChecksConsultaMarcados(name) {
	var codigos = "";
	$("input[name='" + name + "']:checked").each(function() {
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

function eliminar() {
	deshabilitarBotonesConsultaArrendatarios();
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingArrendatarios("bloqueLoadingConsultaArrendatarios",
			"idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingArrendatarios('idEliminar', valueBoton,
				"bloqueLoadingConsultaArrendatarios");
		alert("Debe seleccionar algun tramite para poder eliminarlo.");
		habilitarBotonesConsultaArrendatarios();
		return false;
	}

	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden")
			.attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaArrendatarios.action")
			.trigger("submit");

}

function cargarListaTipoViaCAYC(provincia, tipoVia) {
	var $dest = $("#" + tipoVia);
	$
			.ajax({
				url : "cargarListaTipoViaAjaxCayc.action",
				type : 'POST',
				success : function(data) {
					if (data != "") {
						$dest
								.find('option')
								.remove()
								.end()
								.append(
										'<option value="-1">Seleccione Tipo Via</option>')
								.val('whatever');
						var listTpVias = data.split("|");
						$.each(listTpVias, function(i, o) {
							var tipoVia = listTpVias[i].split("_");
							$dest.append($('<option>', {
								value : tipoVia[0],
								text : tipoVia[1]
							}));
						});
					}
				},
				error : function(xhr, status) {
					alert('Ha sucedido un error a la hora de cargar los tipos de vias de la provincia.');
				}
			});
}

function borrarComboPuebloCAYC(pueblo) {
	$('#' + pueblo).find('option').remove().end().append(
			'<option value="-1">Seleccione Pueblo</option>').val('whatever');
}

function cargarListaPueblosCAYC(provincia, municipio, pueblo) {
	var $dest = $("#" + pueblo);
	$.ajax({
		url : "cargarPuebloAjaxCayc.action",
		data : "provincia=" + $("#" + provincia).val() + "&municipio="
				+ $("#" + municipio).val(),
		type : 'POST',
		success : function(data) {
			if (data != "") {
				$dest.find('option').remove().end().append(
						'<option value="-1">Seleccione Pueblo</option>').val(
						'whatever');
				var listPueblo = data.split("_");
				$.each(listPueblo, function(i, o) {
					$dest.append($('<option>', {
						value : listPueblo[i],
						text : listPueblo[i]
					}));
				});
			}
		},
		error : function(xhr, status) {
			alert('Ha sucedido un error a la hora de cargar los pueblos.');
		}
	});
}

function obtenerCodigoPostalPorMunicipioCAYC(provincia, municipio, codPostal) {
	var $dest = $("#" + codPostal);
	$dest.val("");
	$
			.ajax({
				url : "obtenerCodPostalAjaxCayc.action",
				data : "provincia=" + $("#" + provincia).val() + "&municipio="
						+ $("#" + municipio).val(),
				type : 'POST',
				success : function(data) {
					if (data != "") {
						$dest.val(data);
					}
				},
				error : function(xhr, status) {
					alert('Ha sucedido un error a la hora de cargar el codigo postal del municipio.');
				}
			});
}

function marcarTodas(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) {
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}

function buscarConsultaArrendarario() {
	document.formData.action = "buscarConsultaArrendatarios.action";
	var nif = new String(document.getElementById("nif").value);
	var doivehiculo = new String(document.getElementById("doiVehiculo").value);

	var numRegistro = new String(document.getElementById("numRegistro").value);
	var idnumexpediente = new String(
			document.getElementById("idNumExpediente").value);
	var temp = Limpia_Caracteres(idnumexpediente);

	var matricula = new String(document.getElementById("matricula").value);
	matricula = Limpia_Caracteres_Matricula(matricula);

	nif = Limpia_Caracteres_Estranos(nif);
	doivehiculo = Limpia_Caracteres_Estranos(doivehiculo);
	numRegistro = Limpia_Caracteres_Estranos(numRegistro);

	document.getElementById("idNumExpediente").value = temp;
	document.getElementById("nif").value = nif.toUpperCase();
	document.getElementById("doiVehiculo").value = doivehiculo.toUpperCase();
	document.getElementById("matricula").value = matricula.toUpperCase();
	document.getElementById("numRegistro").value = numRegistro.toUpperCase();

	document.formData.submit();
}

function Volver_Arrendarario() {

	window.location.href = "inicioConsultaArrendatarios.action";
}

function buscarModiArrendarario() {
	document.formData.action = "buscarArrendatarios.action";
	var nif = new String(document.getElementById("nif").value);
	var doivehiculo = new String(document.getElementById("doiVehiculo").value);
	var matricula = new String(document.getElementById("matricula").value);
	var bastidor = new String(document.getElementById("bastidor").value);

	document.getElementById("nif").value = nif.toUpperCase();
	document.getElementById("doiVehiculo").value = doivehiculo.toUpperCase();
	document.getElementById("matricula").value = matricula.toUpperCase();
	document.getElementById("bastidor").value = bastidor.toUpperCase();

	document.formData.submit();
}

function limpiarConsultaArrendatario_Pantalla() {

	$("#idContrato").val("");
	document.getElementById("nif").value = "";
	document.getElementById("doiVehiculo").value = "";
	document.getElementById("matricula").value = "";
	document.getElementById("numRegistro").value = "";
	document.getElementById("diaInicio").value = "";
	document.getElementById("mesInicio").value = "";
	document.getElementById("anioInicio").value = "";
	document.getElementById("diaFinInicio").value = "";
	document.getElementById("mesFinInicio").value = "";
	document.getElementById("anioFinInicio").value = "";
	document.getElementById("diaInicio2").value = "";
	document.getElementById("mesInicio2").value = "";
	document.getElementById("anioInicio2").value = "";
	document.getElementById("diaFinInicio2").value = "";
	document.getElementById("mesFinInicio2").value = "";
	document.getElementById("anioFinInicio2").value = "";
	document.getElementById("diaInicio3").value = "";
	document.getElementById("mesInicio3").value = "";
	document.getElementById("anioInicio3").value = "";
	document.getElementById("diaFinInicio3").value = "";
	document.getElementById("mesFinInicio3").value = "";
	document.getElementById("anioFinInicio3").value = "";
	document.getElementById("diaInicio4").value = "";
	document.getElementById("mesInicio4").value = "";
	document.getElementById("anioInicio4").value = "";
	document.getElementById("diaFinInicio4").value = "";
	document.getElementById("mesFinInicio4").value = "";
	document.getElementById("anioFinInicio4").value = "";
	document.getElementById("idNumExpediente").value = "";
	document.getElementById("referenciaPropia").value = "";

	document.getElementById("estado").value = "0";
	document.getElementById("operacion").value = "0";

}

$(function() {
	$("#displayTagDiv").displayTagAjax();
})

function buscarArrendatario() {
	var nif = $('#idNifArrendatario').val();
	if (nif != "") {
		limpiarFormularioPersonaArrendatario_SinDNI();
		var $dest = $("#divDatosPersona", parent.document);
		deshabilitarBotonesArrendatario();
		$.ajax({
			url : "buscarPersonaArrendatarioAjaxCayc.action#Consulta",
			data : "nif=" + nif + "&idContrato=" + $("#idContrato").val(),
			type : 'POST',
			success : function(data, xhr, status) {
				$dest.empty().append(data);
				if ($('#idPrimerApeRazonSocialArrendatario').val() != "") {
					$("#idLimpiarArrendatario").show();
					$("#idBotonBuscarNifArrendatario").prop("disabled", true);
					$('#idNifArrendatario').prop("disabled", true);
				} else {
					$("#idBotonBuscarNifArrendatario").prop("disabled", false);
					$('#idNifArrendatario').prop("disabled", false);
				}
				habilitarBotonesArrendatario();

			},
			error : function(xhr, status) {
				alert('Ha sucedido un error a la hora de cargar la persona.');
				habilitarBotonesArrendatario();
			}
		});
	} else {
		alert("Para poder realizar la busqueda del sujeto pasivo debe rellenar el NIF/CIF.");
	}
}

function limpiarFormularioPersonaArrendatario_SinDNI() {

	$('#idDireccionArrendatario').val("");
	$('#idTipoPersonaArrendatario').val("-1");
	$('#idSexoArrendatario').val("-1");
	$('#idPrimerApeRazonSocialArrendatario').val("");
	$('#idSegundoApeArrendatario').val("");
	$('#idNombreArrendatario').val("");
	$('#idDiaNacArrendatario').val("");
	$('#idMesNacArrendatario').val("");
	$('#idAnioNacArrendatario').val("");
	$('#idProvinciaArrendatario').val("-1");
	$('#idMunicipioArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioArrendatario').val("-1");
	$('#idTipoViaArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Tipo de Via</option>').val(
			'whatever');
	$('#idTipoViaArrendatario').val("-1");
	$('#idPuebloArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#idPuebloArrendatario').val("-1");
	$('#idNombreViaArrendatario').val("");
	$('#idCodPostalArrendatario').val("");
	$('#idNumeroArrendatario').val("");
	$('#idLetraDireccionArrendatario').val("");
	$('#idEscaleraDireccionArrendatario').val("");
	$('#idPisoDireccionArrendatario').val("");
	$('#idPuertaDireccionArrendatario').val("");
	$('#idBloqueDireccionArrendatario').val("");
	$('#idKmDireccionArrendatario').val("");
	$('#idNifArrendatario').prop("disabled", false);
	$("#idBotonBuscarNifArrendatario").prop("disabled", false);
	$('#idTipoPersonaArrendatario').prop("disabled", false);
	$('#idSexoArrendatario').prop("disabled", false);
	$("#idLimpiarArrendatario").hide();
}

function limpiarFormularioPersonaArrendatario() {
	$('#idNifArrendatario').val("");
	$('#idDireccionArrendatario').val("");
	$('#idTipoPersonaArrendatario').val("-1");
	$('#idSexoArrendatario').val("-1");
	$('#idPrimerApeRazonSocialArrendatario').val("");
	$('#idSegundoApeArrendatario').val("");
	$('#idNombreArrendatario').val("");
	$('#idDiaNacArrendatario').val("");
	$('#idMesNacArrendatario').val("");
	$('#idAnioNacArrendatario').val("");
	$('#idProvinciaArrendatario').val("-1");
	$('#idMunicipioArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioArrendatario').val("-1");
	$('#idTipoViaArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Tipo de Via</option>').val(
			'whatever');
	$('#idTipoViaArrendatario').val("-1");
	$('#idPuebloArrendatario').find('option').remove().end().append(
			'<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#idPuebloArrendatario').val("-1");
	$('#idNombreViaArrendatario').val("");
	$('#idCodPostalArrendatario').val("");
	$('#idNumeroArrendatario').val("");
	$('#idLetraDireccionArrendatario').val("");
	$('#idEscaleraDireccionArrendatario').val("");
	$('#idPisoDireccionArrendatario').val("");
	$('#idPuertaDireccionArrendatario').val("");
	$('#idBloqueDireccionArrendatario').val("");
	$('#idKmDireccionArrendatario').val("");
	$('#idNifArrendatario').prop("disabled", false);
	$("#idBotonBuscarNifArrendatario").prop("disabled", false);
	$('#idTipoPersonaArrendatario').prop("disabled", false);
	$('#idSexoArrendatario').prop("disabled", false);
	$("#idLimpiarArrendatario").hide();
}

function cargarListaNombresVia_Arrendatario(select_provincia, select_municipio,
		select_tipoVia, id_select_nombreVia, via) {

	document.getElementById(id_select_nombreVia).value = "";
	via.processor.setWords([]);
	obtenerNombreViasPorProvinciaMunicipioYTipoVia(document
			.getElementById(select_provincia), select_municipio,
			select_tipoVia, document.getElementById(id_select_nombreVia), via);
}

function Combo_Tipo_Persona() {

	if ($("#idTipoPersonaArrendatario", parent.document).val() != "FISICA") {

		$("#idSexoArrendatario", parent.document).val("X");
		$("#idSexoArrendatario", parent.document).attr('disabled', 'disabled');

	} else {
		$("#idSexoArrendatario", parent.document).val("-");
		$("#idSexoArrendatario", parent.document).removeAttr('disabled');
		;
	}

}

function Validar_Datos_Arrendatario(pestania) {

	if (pestania == 'Vehiculo') {

		if (($("#idMatriculaArrendatario", parent.document).val() == "")
				|| $("#idMatriculaArrendatario", parent.document).val() == null) {
			return "Matricula es campo oblicatorio";

		} else {
			var temp = $("#idMatriculaArrendatario", parent.document).val()
					.toUpperCase();

			$("#idMatriculaArrendatario", parent.document).val(temp)
		}

		if (($("#idBastidorArrendatario", parent.document).val() == "")
				|| $("#idBastidorArrendatario", parent.document).val() == null) {

			return "Bastidor es campo obligatorio";

		} else {
			var temp = $("#idBastidorArrendatario", parent.document).val()
					.toUpperCase();

			$("#idBastidorArrendatario", parent.document).val(temp)
		}

		if (($("#idDoiVehiculoArrendatario", parent.document).val() == "")
				|| $("#idDoiVehiculoArrendatario", parent.document).val() == null) {

		} else {
			var temp = $("#idDoiVehiculoArrendatario", parent.document).val()
					.toUpperCase();

			$("#idDoiVehiculoArrendatario", parent.document).val(temp)
		}

	}
	return null;
}

function guardarArrendatario() {

	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	var strtemp = Validar_Datos_Arrendatario(pestania);

	habilitarCampos();
	$("#formData")
			.attr("action", "guardarAltaArrendatarios.action#" + pestania)
			.trigger("submit");
}

function guardarArrendatarioModi() {

	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	var strtemp = Validar_Datos_Arrendatario(pestania);

	habilitarCampos();
	$("#formData").attr("action",
			"guardarModificarArrendatarios.action#" + pestania).trigger(
			"submit");
}

function consultarArrendatario() {
	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCampos();
	$("#formData").attr("action",
			"consultarAltaArrendatarios.action#" + pestania).trigger("submit");
}

function consultarArrendatarioModi() {
	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCampos();
	$("#formData").attr("action",
			"consultarModificarArrendatarios.action#" + pestania).trigger(
			"submit");
}

function validarArrendatario() {
	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCampos();
	$("#formData")
			.attr("action", "validarAltaArrendatarios.action#" + pestania)
			.trigger("submit");
}

function validarArrendatarioModi() {
	deshabilitarBotonesArrendatario();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCampos();
	$("#formData").attr("action",
			"validarModificarArrendatarios.action#" + pestania).trigger(
			"submit");
}

function deshabilitarBotonesArrendatario() {
	$("#idGuardarArrendatario").prop('disabled', true);
	$("#idGuardarArrendatario").css('color', '#6E6E6E');
	$("#idConsultarArrendatario").prop('disabled', true);
	$("#idConsultarArrendatario").css('color', '#6E6E6E');
	$("#idValidarArrendatario").prop('disabled', true);
	$("#idValidarArrendatario").css('color', '#6E6E6E');
	$("#idFinalizar").prop('disabled', true);
	$("#idFinalizar").css('color', '#6E6E6E');
}

function habilitarBotonesArrendatario() {
	if (!$('#idLimpiarArrendatario').is(':hidden')) {
		$("#idLimpiarArrendatario").css("color", "#000000");
		$("#idLimpiarArrendatario").prop("disabled", false);
	}

	if (!$('#idGuardarArrendatario').is(':hidden')) {
		$("#idGuardarArrendatario").css("color", "#000000");
		$("#idGuardarArrendatario").prop("disabled", false);
	}

	if (!$('#idValidarArrendatario').is(':hidden')) {
		$("#idValidarArrendatario").css("color", "#000000");
		$("#idValidarArrendatario").prop("disabled", false);
	}

	if (!$('#idFinalizar').is(':hidden')) {
		$("#idFinalizar").css("color", "#000000");
		$("#idFinalizar").prop("disabled", false);
	}

}

function habilitarCampos() {
	$('#idNifArrendatario').prop("disabled", false);
	$("#idBotonBuscarNifArrendatario").prop("disabled", false);
	$('#idTipoPersonaArrendatario').prop("disabled", false);
	$('#idSexoArrendatario').prop("disabled", false);
	$("#idDireccionArrendatario").prop('disabled', false);
}

function datosInicialesArrendatario() {
	var $botonLimpiar = $("#idLimpiarArrendatario");
	var $botonBuscarNif = $("#idBotonBuscarNifArrendatario");
	if ($("#idNifArrendatario", parent.document).val() != "") {
		$("#idNifArrendatario", parent.document).prop("disabled", true);
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
	} else {
		$botonLimpiar.hide();
	}
}

function cambiarEstadoBloque() {

	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingArrendatarios("bloqueLoadingConsultaArrendatarios",
			"idCambiarEstado");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingArrendatarios('idCambiarEstado', valueBoton,
				"bloqueLoadingConsultaArrendatarios");
		return alert("Debe seleccionar algun tramite para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaArrendatarios");

	$
			.post(
					"cargarPopUpCambioEstadoConsultaArrendatarios.action",
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
												width : 350,
												buttons : {
													Cambiar : function() {
														var estadoNuevo = $(
																"#idEstadoNuevo")
																.val();
														if (estadoNuevo == "") {
															alert("Seleccione un estado");
															return false;
														}
														$(
																"#divEmergenteConsultaArrendatarios")
																.dialog("close");
														limpiarHiddenInput("estadoNuevo");
														input = $("<input>")
																.attr("type",
																		"hidden")
																.attr("name",
																		"estadoNuevo")
																.val(
																		estadoNuevo);
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
														$("#formData")
																.attr("action",
																		"cambiarEstadoConsultaArrendatarios.action")
																.trigger(
																		"submit");
													},
													Cerrar : function() {
														ocultarLoadingArrendatarios(
																'idCambiarEstado',
																valueBoton,
																"bloqueLoadingConsultaArrendatarios");
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

function FinalizarBloque() {

	var valueBoton = $("#idFinalizar").val();

	mostrarLoadingArrendatarios("bloqueLoadingConsultaArrendatarios",
			"idFinalizar");

	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingArrendatarios('idFinalizar', valueBoton,
				"bloqueLoadingConsultaArrendatarios");
		return alert("Debe seleccionar algún tramite");
	}
	var $dest = $("#divEmergenteConsultaArrendatarios");

	$.post("cargarPopUpFinalizarConsultaArrendatarios.action",
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
							width : 350,
							buttons : {
								Modificar : function() {
									var Ini_Diat = $("#Ini_Dia").val();
									var Ini_Mest = $("#Ini_Mes").val();
									var Ini_Aniot = $("#Ini_Anio").val();
									var Fin_Diat = $("#Fin_Dia").val();
									var Fin_Mest = $("#Fin_Mes").val();
									var Fin_Aniot = $("#Fin_Anio").val();

									$("#divEmergenteConsultaArrendatarios").dialog("close");
									input = $("<input>").attr("type","hidden").attr("name","codSeleccionados").val(codigos);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Fin_Dia").val(Fin_Diat);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Fin_Mes").val(Fin_Mest);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Fin_Anio").val(Fin_Aniot);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Ini_Dia").val(Ini_Diat);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Ini_Mes").val(Ini_Mest);
									$('#formData').append($(input));
									input = $("<input>").attr("type","hidden").attr("name","Ini_Anio").val(Ini_Aniot);
									$('#formData').append($(input));
									$("#formData").attr("action","modificarConsultaArrendatarios.action").trigger("submit");
								},
								Cerrar : function() {
									ocultarLoadingArrendatarios('idFinalizar',valueBoton,"bloqueLoadingConsultaArrendatarios");
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

function consultarBloque() {

	deshabilitarBotonesConsultaArrendatarios();
	var valueBoton = $("#idConsultar").val();
	mostrarLoadingArrendatarios("bloqueLoadingConsultaArrendatarios",
			"idConsultar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingArrendatarios('idConsultar', valueBoton,
				"bloqueLoadingConsultaArrendatarios");
		alert("Debe seleccionar algun tramite para poder realizar la consulta.");
		habilitarBotonesConsultaArrendatarios();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden")
			.attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaArrendatarios.action")
			.trigger("submit");
}

function validarBloque() {

	deshabilitarBotonesConsultaArrendatarios();
	var valueBoton = $("#idValidar").val();
	mostrarLoadingArrendatarios("bloqueLoadingConsultaArrendatarios",
			"idValidar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if (codigos == "") {
		ocultarLoadingArrendatarios('idValidar', valueBoton,
				"bloqueLoadingConsultaArrendatarios");
		alert("Debe seleccionar algun tramite para validar.");
		habilitarBotonesConsultaArrendatarios();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden")
			.attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "validarConsultaArrendatarios.action")
			.trigger("submit");
}

function cambiarElementosPorPaginaEvolucionCayc() {
	var $dest = $("#displayTagEvolucionCaycDiv");
	$
			.ajax({
				url : "navegarEvolucionCayc.action",
				data : "resultadosPorPagina="
						+ $("#idResultadosPorPaginaEvolArrendatario").val(),
				type : 'POST',
				success : function(data) {
					filteredResponse = $(data).find($dest.selector);
					if (filteredResponse.size() == 1) {
						$dest.html(filteredResponse);
					}
					$dest.displayTagAjax();
				},
				error : function(xhr, status) {
					alert('Ha sucedido un error a la hora de cargar las evoluciones de la consulta de arrendatarios.');
				}
			});
}

function abrirEvolucionAltaCayc(idNumExpediente, divDestino) {
	return abrirEvolucionCayc($("#" + idNumExpediente).val(), divDestino);
}

function abrirEvolucionCayc(numExpediente, destino) {
	if (numExpediente == null || numExpediente == "") {
		return alert("Debe seleccionar algun tramite para poder obtener su evolucion.");
	}
	var $dest = $("#" + destino);
	var url = "cargarEvolucionCayc.action?numExpediente=" + numExpediente;
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
