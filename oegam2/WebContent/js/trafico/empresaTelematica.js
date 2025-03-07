function consultaEmpresa(){
	doPost('formData', 'buscarConsultaEmpresaTelematica.action');
}

function limpiarFormularioEmpresa(){
	$("#empresa").val("");
	$("#cifEmpresa").val("");
	$("#codigoPostal").val("");

	if (document.getElementById("idContrato")) {
		document.getElementById("idContrato").value = -1;
	}

	if (document.getElementById("idEstado")) {
		document.getElementById("idEstado").value = -1;
	}

	if (document.getElementById("idMunicipioEmpresaTelematica")) {
		document.getElementById("idMunicipioEmpresaTelematica").value = -1;
	}

	if (document.getElementById("idProvinciaEmpresaTelematica")) {
		document.getElementById("idProvinciaEmpresaTelematica").value = -1;
	}

	$("#diaAltaInicio").val("");
	$("#mesAltaInicio").val("");
	$("#anioAltaInicio").val("");
	$("#diaAltaFin").val("");
	$("#mesAltaFin").val("");
	$("#anioAltaFin").val("");

	$("#diaBajaInicio").val("");
	$("#mesBajaInicio").val("");
	$("#anioBajaInicio").val("");

	$("#diaBajaFin").val("");
	$("#mesBajaFin").val("");
	$("#anioBajaFin").val("");
}

function guardarEmpresaTelematica(){
	deshabilitarBotonesEmpresaTelematica();
	habilitarCampos();
	$("#formData").attr("action", "guardarAltaEmpresaTelematica").trigger("submit");
}

function volverEmpresaTelematica(){
	$("#formData").attr("action", "inicioConsultaEmpresaTelematica").trigger("submit");
}

function deshabilitarBotonesEmpresaTelematica(){
	$("#idGuardarEmpresaTelematica").prop('disabled', true);
	$("#idGuardarEmpresaTelematica").css('color', '#6E6E6E');
	$("#idGenerarXmlEmpresaTelematica").prop('disabled', true);
	$("#idGenerarXmlEmpresaTelematica").css('color', '#6E6E6E');
	$("#idTramitarXmlEmpresaTelematica").prop('disabled', true);
	$("#idTramitarXmlEmpresaTelematica").css('color', '#6E6E6E');
	$("#idImprimirEmpresaTelematica").prop('disabled', true);
	$("#idImprimirEmpresaTelematica").css('color', '#6E6E6E');
	$("#idReinEstadoEmpresaTelematica").prop('disabled', true);
	$("#idReinEstadoEmpresaTelematica").css('color', '#6E6E6E');
	$("#idVolverEmpresaTelematica").prop('disabled', true);
	$("#idVolverEmpresaTelematica").css('color', '#6E6E6E');
}

function habilitarBotonesEmpresaTelematica(){
	$("#idGuardarEmpresaTelematica").prop('disabled', false);
	$("#idGuardarEmpresaTelematica").css("color","#000000");
	$("#idGenerarXmlEmpresaTelematica").prop('disabled', false);
	$("#idGenerarXmlEmpresaTelematica").css('color', '#000000');
	$("#idTramitarXmlEmpresaTelematica").prop('disabled', false);
	$("#idTramitarXmlEmpresaTelematica").css('color', '#000000');
	$("#idImprimirEmpresaTelematica").prop('disabled', false);
	$("#idImprimirEmpresaTelematica").css('color', '#000000');
	$("#idReinEstadoEmpresaTelematica").prop('disabled', false);
	$("#idReinEstadoEmpresaTelematica").css('color', '#000000');
	$("#idVolverEmpresaTelematica").prop('disabled', false);
	$("#idVolverEmpresaTelematica").css('color', '#000000');
}

function habilitarCampos(){
	$("#idNumExpEmpresaTelematica").prop('disabled', false);
	$("#idEstadoEmpresaTelematica").prop('disabled', false);
}

function abrirDetalle (id){
	aniadirHidden("id", id, "formData");
	aniadirHidden("vieneDeResumen", true, "formData");
	$("#formData").attr("action", "inicioAltaEmpresaTelematica.action").trigger("submit");
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

function cargarListaMunicipiosCAYC(provincia, municipio) {
	var $dest = $("#" + municipio);
	$
		.ajax({
			url: "cargarMunicipiosAjaxCayc.action",
			data: "provincia=" + $("#" + provincia).val(),
			type: 'POST',
			success: function(data) {
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
			error: function() {
				alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
			}
		});
}