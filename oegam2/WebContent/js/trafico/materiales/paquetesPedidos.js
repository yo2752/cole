/**
 * 
 */

function abrirPaquetesPedido() {
	var $dest = $('#divEmergenteConsultaPaquetesPedido');
	var $idForm = $('#formData');
	var url = 'buscarPaquetesPedido.action';

	var datosFiltro = {};

	if ($('#idpedidoId').attr('value') != '') {
		datosFiltro['pedidoId'] = $('#idpedidoId').attr('value');
	}
	
	if ($('#labelpedPaqueteId').attr('value') != '') {
		datosFiltro['pedPaqueteId'] = $('#labelpedPaqueteId').attr('value');
	}
	
	if ($('#labelEstadoPaquete').attr('value') != '') {
		datosFiltro['estado'] = $('#labelEstadoPaquete').attr('value');
	}
	
	$.post(url, datosFiltro, function(data) {
		if (data.toLowerCase().indexOf("<html") < 0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950, height: 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}


function limpiarPaquetePedidos() {
	$("#labelpedPaqueteId").val("");
	$("#labelEstadoPaquete").val("");
	
	/*
	$("#labelcodigoInicial").val("");
	$("#labelcodigoFinal").val("");
	$("#labelEstado").val("");
	$("#labelJefaturaProvJpt").val("");
	
	$("#diaFechaIni").val("");
	$("#mesFechaIni").val("");
	$("#anioFechaIni").val("");
	$("#diaFechaFin").val("");
	$("#mesFechaFin").val("");
	$("#anioFechaFin").val("");
	*/
}

