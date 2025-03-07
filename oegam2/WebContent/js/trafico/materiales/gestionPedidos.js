/**
 * 
 */
var $dialogSerial, $formSerial, $allFields, $prefijo, $codigoInicial, $codigoFinal, $tips;
var refreshWindow = false;	   

$( document ).ready( function() { 
	$prefijo =       $( "#labelprefijo" );
	$codigoInicial = $( "#labelcodInicial" );
	$codigoFinal =   $( "#labelcodFinal" );
	
	refreshWindow = false;
	
	allFields = $( [] ).add( $prefijo ).add( $codigoInicial ).add( $codigoFinal ),
    $tips = $( ".validateTips" );
	
	$formSerial = $('idFormSerial');
		
	$dialogSerial = $( "#dialog-serial" ).dialog({
        autoOpen: false,
        height: 180,
        width: 450,
        modal: true,
        buttons: {
          "Añadir Codigos": addSeriales,
          Limpiar: limpiarFormSeriales,
          Cancel: function() {
	      	  $dialogSerial.dialog( "close" );
	      	  if (refreshWindow) {
	      		 window.location.reload();
	      	  }
	      }
        },
        close: function() {
          limpiarFormSeriales();
          allFields.removeClass( "ui-state-error" );	
        }
    });
    
});

function limpiarFormSeriales() {
	  $( "#labelprefijo" ).val('');
	  $( "#labelcodInicial" ).val('');
	  $( "#labelcodFinal" ).val('');
}

function rellenarSeriales(pedido, unidades){ 
	var data = $("#dialog-serial").data();   
	data.pedido = pedido;  
	data.unidades = unidades;
	
	$dialogSerial.dialog( "open" );
}

function rellenarCodigoFinal() {
	var codigoInicial = $( "#labelcodInicial" ).val();
	var unidades = $( "#dialog-serial" ).data('unidades');
	var numInicial, numUnidades;
	var result = 0;
	
	if ( codigoInicial.length != 0 && $.isNumeric(codigoInicial) ) {
		numInicial = parseInt(codigoInicial);
		numUnidades = parseInt(unidades);
		result = numInicial + numUnidades;
		
		var padding = codigoInicial.length;
		var strResult = ("0".repeat(padding) + result).slice(-padding); 
		
		$codigoFinal.val( strResult );
	}
}

function addSeriales() {
	
    var valid = validarFormularioSerial();
    
    if ( valid ) {
    	var pedido = $( "#dialog-serial" ).data('pedido');
    	var prefijo   = $prefijo.val();
    	var serialIni = $codigoInicial.val();
    	var serialFin = $codigoFinal.val();
    	
		var url = 'actualizarSerialesCrearPedidoAjax.action';
		
		var resultado = '';
		$.ajax ({  
		    url: url,
		    data: { 'pedido' : pedido, 'prefijo': prefijo, 'serialIni': serialIni, 'serialFin': serialFin},
		    type: 'POST',
		    async: false,
		    traditional: true,
		    dataType: 'text',
		    success: function (response) {
		    	updateTipsSuccess(response);
		    	setTimeout(cerrarVentanaSeriales, 3000);
		    }
		});

    }
	
}

function cerrarVentanaSeriales() {
	//refreshWindow = true;
	window.history.back();
	location.reload();
	$dialogSerial.dialog( "close" );
}

function validarFormularioSerial() {
	var valid = true;

    allFields.removeClass( "ui-state-error" );	
	
    valid = valid && checkLength( $prefijo,       "Prefijo"       , 2, 4 );
    valid = valid && checkLength( $codigoInicial, "Código Inicial", 6, 10 );
    valid = valid && checkLength( $codigoFinal,   "Código Final"  , 6, 10 );
    
    valid = valid && checkRegexp( $prefijo, /([0-9a-z])+$/i, "Prefijo puede consistir de a-z, 0-9." );
    valid = valid && checkRegexp( $codigoInicial, /^\d+$/, "Codigo Inicial debe consistir de 0-9." );
    valid = valid && checkRegexp( $codigoFinal, /^\d+$/, "Codigo Inicial debe consistir de 0-9." );
    
    valid = valid && checkLongSerial($codigoInicial, $codigoFinal, "Los seriales tienen que tener la misma longitud.");

    if ( valid ) {
    	$tips.empty();
        $tips = $( ".validateTips" );
    }

    return valid;
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function buscarGestionPedidos() {
	document.formData.action = "buscarGestionPedidos.action";
	document.formData.submit();
}

function mostrarNotificacion(textToShow) {
	alert(textToShow);
}


function limpiarGestionPedidos() {
	$("#labelMateriales").val("");
	$("#labelinventarioId").val("");
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
}

function actualizarInformacionPedido() {
	var $checkChecked = $( "input:checkbox[id ^= 'check_sin__']:checked" );

	if ($checkChecked.length != 0) {
		var idActualizaciones = [];
		$.each($checkChecked, function( index ) {
			idActualizaciones[index] = $( this ).attr('value') ;	
		});
		
		var url = 'json/sincronizarPedidosActualizarInformacionAjax.action';
		
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

function abrirEvolucion(pedido, destino, action){
	var $dest = $("#"+destino);
	var url = action + "?pedido=" + pedido;
	
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
				width: 950, height: 350,
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

function abrirPaquetes(pedido, destino, form, action){
	var $dest = $("#"+destino);
	var $idForm = $("#" + form);
	var url = action + "?pedido=" + pedido;
	
	$.post(url, function(data) {
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


function solicitarPedidos() {
	var $checkChecked = $( "input:checkbox[id ^= 'check_sol_']:checked" );

	if ($checkChecked.length != 0) {
		var idSolicitudes = [];
		$.each($checkChecked, function( index ) {
			idSolicitudes[index] = $( this ).attr('value') ;	
		});
		
		var url = 'json/solicitarSolicitarPedidoAjax.action';
		
		var resultado = '';
		$.ajax ({  
		    url: url,
		    data: { 'idSolicitudes' : idSolicitudes },
		    type: 'POST',
		    async: false,
		    traditional: true,
		    dataType: 'text',
		    success: function (response) {
		        mostrarNotificacion(response);

		    }
		});
	} else {
		mostrarNotificacion('Debes seleccionar al menos un pedido a solicitar');
	}
	

}

function entregarPedidos() {
	var $checkChecked = $( "input:checkbox[id ^= 'check_ent_']:checked" );

	if ($checkChecked.length != 0) {
		var idEntregados = [];
		$.each($checkChecked, function( index ) {
			idEntregados[index] = $( this ).attr('value') ;	
		});
		
		var url = 'json/entregarEntregarPedidoAjax.action';
		
		var resultado = '';
		$.ajax ({  
		    url: url,
		    data: { 'idEntregados' : idEntregados },
		    type: 'POST',
		    async: false,
		    traditional: true,
		    dataType: 'text',
		    success: function (response) {
		        mostrarNotificacion(response);

		    }
		});
	} else {
		mostrarNotificacion('Debes seleccionar al menos un pedido a entregar');
	}
	

}

//function solicitarPedido(pedido) {
//	alert('Pedido ==> ' + pedido);
//	var url = 'solicitarSolicitarPedidoAjax.action' + "?pedido=" + pedido;
//	
//	$.post(url, function(data) {
//		
//		alert('data ==> ' + data);
//		if (data.toLowerCase().indexOf("<html") < 0) {
//			$dest.empty().append(data).dialog({
//				modal : true,
//				position : { my: 'right', at: 'right' },
//				appendTo : $idForm,
//				show : {
//					effect : "blind",
//					duration : 300
//				},
//				dialogClass : 'no-close',
//				width: 950, height: 500,
//				buttons : {
//					Cerrar : function() {
//						$(this).dialog("close");
//					}
//				}
//			});
//		} else {
//			// Viene <html>, así que no es un modal
//			var newDoc = document.open("text/html", "replace");
//			newDoc.write(data);
//			newDoc.close();
//		}
//		$(".ui-dialog-titlebar").hide();
//	}).always(function() {
//		unloading("loadingImage");
//	});
//}

function cambiarElementosPorPaginaEvolucion() {
	var $dest = $("#displayTagEvolucionDiv");
	$.ajax({
		url:"navegarEvolucionPedido.action",
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
	        alert('Ha sucedido un error a la hora de cargar las evolucionesde pedidos.');
	    }
	});
}

function cambiarElementosPorPaginaPaquetes() {
	var $dest = $("#displayTagPaquetesDiv");
	$.ajax({
		url:"navegarPaquetesPedido.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaPedidos").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los paquetes del pedido.');
	    }
	});
}

function cambiarElementosPorPaginaDetalle() {
	var $dest = $("#displayTagDetalleDiv");
	$.ajax({
		url:"navegarDetallePedido.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaDetalle").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar el detalle de un pedido.');
	    }
	});
}

function checkLength( o, n, min, max ) {
    if ( o.val().length > max || o.val().length < min ) {
      o.addClass( "ui-state-error" );
      updateTips( "Longitud de " + n + " debe ser entre " +
        min + " y " + max + "." );
      return false;
    } else {
      return true;
    }
}

function updateTips( t ) {
    $tips
      .text( t )
      .addClass( "ui-state-highlight" );
    setTimeout(function() {
      $tips.removeClass( "ui-state-highlight", 1500 );
    }, 500 );
  }

function updateTipsSuccess( t ) {
    $tips
      .text( t )
      .addClass( "ui-state-highlight" );
    setTimeout(function() {
      $tips.removeClass( "ui-state-highlight", 1500 );
    }, 500 );
}

function checkRegexp( o, regexp, n ) {
    if ( !( regexp.test( o.val() ) ) ) {
      o.addClass( "ui-state-error" );
      updateTips( n );
      return false;
    } else {
      return true;
    }
}

function checkLongSerial( serialIni, serialFin, n ) {
    if ( serialIni.val().length != serialFin.val().length  ) {
      serialIni.addClass( "ui-state-error" );
      serialFin.addClass( "ui-state-error" );
      updateTips( n );
      return false;
    } else {
      return true;
    }
}

