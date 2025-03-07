/**
 * 
 */
$('document').ready(function() {

});

function mostrarDescripcion(incidenciaId) {
	var url = 'json/obtenerIncidenciaAjax.action';
	var textToShow;
	
    $.ajax({
        data:  {'incidencia' : incidenciaId },
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

function isNumber(evt) {
    evt = (evt) ? evt : window.event;
    var charCode = (evt.which) ? evt.which : evt.keyCode;
    if (charCode > 31 && (charCode < 48 || charCode > 57)) {
        return false;
    }
    return true;
}

function buscarGestionIncidencias() {
	document.formData.action = "buscarGestionIncidencia.action";
	document.formData.submit();
}

function consultarIncidencia(incidenciaId) {
	
	$("#formData").attr("action", "modificaGestionIncidencia.action?incidenciaId=" + incidenciaId).trigger("submit");
}

function limpiarGestionIncidencias() {
	
	//$("#labelpedidoId").val("");
	$("#labelMateriales").val("");
	$("#labelJefaturaProvJpt").val("");
	$("#labelEstadoIncidencia").val("");
	
	//$("#labelnumSerie").val("");
	$("#labelTipo").val("");
	
	
	$("#diaFechaIni").val("");
	$("#mesFechaIni").val("");
	$("#anioFechaIni").val("");
	$("#diaFechaFin").val("");
	$("#mesFechaFin").val("");
	$("#anioFechaFin").val("");

}
