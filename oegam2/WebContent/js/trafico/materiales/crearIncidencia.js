/**
 * 
 */

var accion;

$('document').ready( function() {
	accion = $('#idAccion').val();
	
	toggleButtonSerial();
	toggleButtonInterval()
});

function toggleButtonSerial() {
	var n = $( "input:checkbox[id^='serial']:checked" ).length;
	if (n > 0) {
		$('#idRemoveSerial').show();
	} else {
		$('#idRemoveSerial').hide();
	}
}

function toggleButtonInterval() {
	var n = $( "input:checkbox[id^='interval']:checked" ).length;
	if (n > 0) {
		$('#idRemoveInterval').show();
	} else {
		$('#idRemoveInterval').hide();
	}
}

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function crearIncidencia() {
	$("#formData").attr("action", "crearCrearIncidencia.action").trigger("submit");
}

function modificarIncidencia() {
	$("#formData").attr("action", "modificarCrearIncidencia.action").trigger("submit");
}

function confirmarIncidencia() {
	$("#formData").attr("action", "confirmaCrearIncidencia.action").trigger("submit");
}

function limpiarIncidencia() {
	$("#labelJefaturaProvJpt").val("");
	$("#celdaMateriales").val("");
	$("#labelobservaciones").val("");
	$("#labeTipo").val("");
	
	$("#numSerie").val("");
	$("#numSerieIni").val("");
	$("#numSerieFin").val("");
	
}

function addSerial() {
	if ( $("#idPrefijo").val() == '' ) {
		alert('Debes especificar un prefijo');
		return;
	}
		
	if ( $("#idSerial").val() == '' ) {
		alert('Debes especificar un número de serie');
		return;
	} 
		
	if ( !$.isNumeric( $("#idSerial").val() ) ) {
		alert('Serial debe ser númerico');
	} 
	
	var numSerieStr = $("#idPrefijo").val() + '-' + $("#idSerial").val();
	$("#idNumSerie").val( numSerieStr );
		
	$("#formData").attr("action", "addSerialCrearIncidencia.action?accionIncidencia=" + accion).trigger("submit");

}

function removeSerial() {
	var n = $( "input:checkbox[id^='serial']:checked" ).length;
	if (n == 0) {
		alert('Debes seleccionar al menos un número de serie');
		return;
	}
	$("#formData").attr("action", "removeSerialCrearIncidencia.action?accionIncidencia=" + accion).trigger("submit");
}

function addInterval() {
	if ( $("#prefijoIntId").val() == '' ) {
		alert('Debes especificar un prefijo para el intervalo');
		return;
	}
		
	if ( $("#serialIniId").val() == '' ) {
		alert('Debes especificar un número de serie para Serial Inicial');
		return;
	} 

	var serial1 = 0;
	if ( !$.isNumeric( $("#serialIniId").val() ) ) {
		alert('Serial Inicial debe ser númerico');
	} else {
		serial1 = parseInt( $("#serialIniId").val() );
	}
	
	if ( $("#serialFinId").val() == '' ) {
		alert('Debes especificar un número de serie para Serial Final');
		return;
	} 
	
	var serial2 = 0;
	if ( !$.isNumeric( $("#serialFinId").val() ) ) {
		alert('Serial Final debe ser númerico');
		return;
	} else {
		serial2 = parseInt( $("#serialFinId").val() );
	}
	
	if (serial1 >= serial2) {
		alert('Serial Final debe ser mayor que Serial Ini');
		return;
	}
	
	if ($("#serialIniId").val().length != $("#serialFinId").val().length) {
		alert("Los seriales deben ser de la misma longitud");
		return;
	}
	
	var numSerieIniStr = $("#prefijoIntId").val() + '-' + $("#serialIniId").val();
	$("#idNumSerieIni").val( numSerieIniStr );
	
	var numSerieFinStr = $("#prefijoIntId").val() + '-' + $("#serialFinId").val();
	$("#idNumSerieFin").val( numSerieFinStr );
		
	$("#formData").attr("action", "addIntervaloCrearIncidencia.action?accionIncidencia=" + accion).trigger("submit");
}

function removeInterval() {
	var n = $( "input:checkbox[id^='interval']:checked" ).length;
	if (n == 0) {
		alert('Debes seleccionar al menos un intervalo de números de serie');
		return;
	}
	$("#formData").attr("action", "removeIntervaloCrearIncidencia.action?accionIncidencia=" + accion).trigger("submit");
}