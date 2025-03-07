/**
 * 
 */

var $dialogInci;

$('document').ready(function() {
	
	$dialogInci = $( "#dialog-form" ).dialog({
	      autoOpen: false,
	      height: 300,
	      width: 850,
	      top: 250,
	      left:300,
	      modal: true,
	      buttons: {
	          Guardar: function() {
	        	  guardarIncidencia();
	          },
	          Limpiar: function() {
	        	  limpiarIncidencia2();
	          },
	          Cancel: function() {
	        	   $dialogInci.dialog( "close" );
	          }
	        }
    });
	
	$dialogConfir = $( "#dialog-confirm" ).dialog({
        resizable: false,
        height: "auto",
        width: "auto",
        modal: true,
        buttons:{ 
          OK: function() {
            $( this ).dialog( "close" );
          }
        }
     });
	
	$dialogConfir.dialog("open");
});

function abrirVentanaCrearIncidencia2(pedido) {
	var $dest =  $( "#dialog-form" );
	$dest.empty();
	$url = 'altaCrearIncidencia.action?pedido=' + pedido
	
	$.ajax({
		  type: "GET",
		  url: $url,
		  //data: $formu.serialize(),
		  success: function(response) {
			  console.log('recibida respuesta ' + response);
			  $dest.append(response);
		  }
	});
	
	$dialogInci.dialog("open");
}


function cerrarVentanaCrearIncidencia2() {
	$dialogInci.dialog( "close" );
	
}

function limpiarIncidencia2() {
	
	$("#celdaMateriales").val("");
	$("#celdaNumSerie").val("");
	$("#labelobservaciones").val("");
	$("#celdaTipo").val("");
}


