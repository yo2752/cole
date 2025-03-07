/**
 * 
 */
$('document').ready(function(){
	var $tdEstadoActivo = $('table#tablaGestionMaterial tr.activo td.estado');
	$tdEstadoActivo.css({
		   'color' : 'green'
	});

	var $tdEstadoInactivo = $('table#tablaGestionMaterial tr.inactivo td.estado');
	$tdEstadoInactivo.css({
		   'color' : 'red'
	});
});

function mostrarDescripcion(evolucionMaterialId) {
	var url = 'json/obtenerEvolucionAjax.action';
	var textToShow = '';
	
    $.ajax({
        data:  {'evolucion' : evolucionMaterialId },
        url:   url,
        type:  'post',
        async: false,
        success:  function (response) {
        	textToShow = response;
        }
    });
    
    
    $.alert({
    	boxWidth: '30%',
    	useBootstrap: false,
        title: 'Descripción',
        content: textToShow,
    });
}

function buscarGestionMateriales() {
	document.formData.action = "buscarGestionMateriales.action";
	document.formData.submit();
}

function limpiarGestionMateriales() {
	$("#labelEstado").val("");

	$("#diaFechaAltaIni").val("");
	$("#mesFechaAltaIni").val("");
	$("#anioFechaAltaIni").val("");
	$("#diaFechaAltaFin").val("");
	$("#mesFechaAltaFin").val("");
	$("#anioFechaAltaFin").val("");
}

function abrirEvolucion(evoMatId, materia, destino){
	var $dest = $("#"+destino);
	var url = "cargarEvolucionMateriales.action?materia=" + materia + "&selEvoMatId=" + evoMatId;
	
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html") < 0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
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

function cambiarElementosPorPaginaEvolucion(){
	var $dest = $("#displayTagEvolucionDiv");
	$.ajax({
		url:"navegarEvolucionMateriales.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolucion").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones de la consulta de materiales.');
	    }
	});
}
