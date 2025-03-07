/**
 * 
 */

var accion;
var spinner;

$('document').ready( function() {
	accion = $('#idAccion').val();
	
    spinner = $( "#celdaUnidades" ).spinner({
        range: "max",
        min: 1000,
        max: 5000,
        step: 1000,
        spin: function( event, ui ) {
        }
    });	
    
    if (spinner.spinner("value") == null) {
        spinner.spinner( "value", 1000 );
    }
    
    if (accion == 'crear') {
    	$("#labelJefaturaProvJpt").val("");
    }
});

function limpiarPedido() {
	$("#labelobservaciones").val("");
	
	if (accion == 'crear') {
		$("#labelJefaturaProvJpt").val("");
	}
	
	$("#celdaMateriales").val("");

    spinner.spinner( "value", 1000 );

}


function validarPedido() {
	var textToExcept = 'Debes tener en cuenta: ';
	var ok = true;

	if ($('#labelobservaciones').val() == "") {
		textToExcept += 'observaciones no rellena, ';
		ok = false;
	}

	if ($('#labelJefaturaProvJpt').val() == "") {
		textToExcept += 'Jefatura Provincial no rellena, ';
		ok = false;
	}
	
	if ($('#celdaMateriales').val() == "") {
		textToExcept += 'Material no relleno, ';
		ok = false;
	}
	
	if ($('#celdaUnidades').val() == "") {
		textToExcept += 'Unidades no rellena, ';
		ok = false;
	}
	
	if ( $('#labelJefaturaProvJpt').val() != "" && $('#celdaMateriales').val() != "" )  {
		var resultado = validarMaterial();
		
		var pedidoExistente = Boolean(resultado);
		if (pedidoExistente) {
			textToExcept += 'Material solicitado en pedido ' + resultado + ', ';
			ok = false;
		}
	}
	
	if (!ok) {
		var textToExceptFin = textToExcept.substring(0, textToExcept.length - 2);
		alert(textToExceptFin);
	}

	return (ok);

}

function guardarPedido() {

	if (validarPedido()) {
		if (accion == 'crear') {
			console.log("Paso por crear ==> " + $('#labelidpedido').attr('value'))
			$("#formData").attr("action", "guardarCrearPedido.action").trigger("submit");
		} else if (accion == 'modifica') {
			console.log("Paso por modify ==> " + $('#labelidpedido').attr('value'))
			$("#formData").attr("action", "guardarModifyPedido.action").trigger("submit");
		}
	}

}

function confirmarPedido() {
	console.log('Confirmar pedido')
	$("#formData").attr("action", "confirmarCrearPedido.action").trigger("submit");
	
}

function elimiarPedido() {
	console.log('Eliminar pedido')
	$("#formData").attr("action", "eliminarCrearPedido.action").trigger("submit");
}


function validarMaterial() {
	var $material = $('#celdaMateriales').val();
	var $jefatura = $('#labelJefaturaProvJpt').val();
	
	var url = 'validarMaterialCrearPedidoAjax.action' 
		+ '?jefatura=' + $jefatura
		+ '&material=' + $material;
	
	var resultado = '';
	$.ajax ({  
	    url: url,
	    type: 'GET',
	    async: false,
	    dataType: 'text',
	    success: function (data) {
	    	resultado = data;
	        console.log(data);
	    }
	});	
	
	if (resultado === 'valido' ) {
		return (false);
	}
	else {
		return (resultado);
	}
}

function ocultarBotonGuardar() {
	$("#idGuardarModelo").hide();
}

function mostrarBotonGuardar() {
	$("#idGuardarModelo").show();
}
