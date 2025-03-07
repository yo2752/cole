/**
 * 
 */

$('document').ready(function(){
	var $tdEstadoNegro = $('table#tablaGestionStock tr.negro td.estado');
	$tdEstadoNegro.css({
		   //'background-color' : 'black',
		   'color' : 'red'
		});

	var $tdEstadoRojo = $('table#tablaGestionStock tr.rojo td.estado');
	$tdEstadoRojo.css({
		   //'background-color' : 'red',
		   'color' : 'green'
		});

	var $tdEstadoAmarillo = $('table#tablaGestionStock tr.amarillo td.estado');
	$tdEstadoAmarillo.css({
		   //'background-color' : 'orange',
		   'color' : 'orange'
		});
});

function mostrarNotificacion(textToShow) {
	alert(textToShow);
}

function actualizarInformacionStock() {
	var $checkChecked = $( "input[type=checkbox]:checked" );

	console.log($checkChecked.length);
	if ($checkChecked.length != 0) {
		var idActualizaciones = [];
		$.each($checkChecked, function( index ) {
			idActualizaciones[index] = $( this ).attr('value') ;	
		    console.log( index + '  ' +$( this ).attr('id') + ' ==> ' + $( this ).attr('value') );
		});
		
		var url = 'json/sincronizarStockActualizarInformacionAjax.action';
		
		var resultado = '';
		$.ajax ({  
		    url: url,
		    data: { 'idActualizaciones' : idActualizaciones },
		    type: 'POST',
		    async: false,
		    traditional: true,
		    dataType: 'text',
		    success: function (response) {
		    	mostrarNotificacion(response);
		    }
		});
	} else {
		mostrarNotificacion('Debes seleccionar al menos un pedido a sincronizar');
	}
	
}

function buscarGestionStockMateriales() {
	document.formData.action = "buscarGestionStock.action";
	document.formData.submit();
}

function limpiarGestionStockMateriales() {
	$("#labelJefaturaProvJpt").val("");
	$("#labelMateriales").val("");
	
	
	$("#diaFecUltRecargaIni").val("");
	$("#mesFecUltRecargaIni").val("");
	$("#anioFecUltRecargaIni").val("");
	$("#diaFecUltRecargaFin").val("");
	$("#mesFecUltRecargaFin").val("");
	$("#anioFecUltRecargaFin").val("");
	
	
	$("#diaFecUltConsumoIni").val("");
	$("#mesFecUltConsumoIni").val("");
	$("#anioFecUltConsumoIni").val("");
	$("#diaFecUltConsumoFin").val("");
	$("#mesFecUltConsumoFin").val("");
	$("#anioFecUltConsumoFin").val("");

}


