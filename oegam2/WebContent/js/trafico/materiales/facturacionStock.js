/**
 * 
 */
$(document).ready(function() {
	if ($('#tipoDocumento').attr('value') != 1 ) {
		$('.tipoDistintivo').hide();
	}
});

function generarTablaExcel() {
	console.log('generarTablaExcel');
	var validos = validarCamposObligatorios();
	if (!validos) {
		return;
	} 
	
	if (!$('#tipoDistintivo').css("visibility") == 'hidden') {
		if ( $('#tipoDocumento').attr('value') == 2 || 
			 $('#tipoDocumento').attr('value') == 3	) {
			$('#tipoDistintivo').attr('value', 1);
		}
	}
		
	$("#formData").attr("action", "generarTablaExcelFacturacionStock.action").trigger("submit");

}

function validarCamposObligatorios(){
	var validos = true;
	
	if ( $('#diaFechaAltaIni').attr('value').length === 0 ||
		 $('#mesFechaAltaIni').attr('value').length === 0 ||
		 $('#anioFechaAltaIni').attr('value').length === 0 ) {
		alert('El campo Fecha Desde es obligatorio');
		validos = false;
	}

	if ( $('#diaFechaAltaFin').attr('value').length === 0 ||
		 $('#mesFechaAltaFin').attr('value').length === 0 ||
		 $('#anioFechaAltaFin').attr('value').length === 0 ) {
			alert('El campo Fecha Hasta es obligatorio');
			validos = false;
		}
	return validos;
}

function limpiar() {
	console.log('limpiar');
	$('#contratoColegiado').attr('value', -1);
	$('#tipoDocumento').attr('value', 2);
	$('#tipoDistintivo').attr('value', 0);
	
	if ( $('#tipoDocumento').attr('value') != 1 ) {
		$('.tipoDistintivo').hide();
	}
	
	$('#diaFechaAltaIni').attr('value', "");
	$('#mesFechaAltaIni').attr('value', "");
	$('#anioFechaAltaIni').attr('value', "");
	
	$('#diaFechaAltaFin').attr('value', "");
	$('#mesFechaAltaFin').attr('value', "");
	$('#anioFechaAltaFin').attr('value', "");
	
}

function cargarComboTipoDistintivo() {
	console.log('cargarComboTipoDistintivo');
	
	var tipoDocumento = $('#tipoDocumento').attr('value');	
	
	var $tipoDistintivo = $('#tipoDistintivo');
	
    $.ajax({
        data:  {'tipoDocumento' : tipoDocumento},
        url:   '/oegam2/json/comboValuesTipoDistintivoAjax.action',
        type:  'post',
        async: false,
        success:  function (response) {
        	console.log(response);
        	$tipoDistintivo.empty();
        	$optionTodos = $('<option>', { value: 0, text : 'Todos' });
        	var optionindex = 0;
        	var $options = [];
        	$.each(response, function(index, value) {
            	console.log(value);
            	$options[optionindex] = $('<option>', { value: index, text : value });
            	optionindex++;
        	});
        	
        	if ($options.length > 1) {
            	$tipoDistintivo.append($optionTodos);
        	}
        	for (var i = 0; i < $options.length; i++) {
            	$tipoDistintivo.append($options[i]);
        	}
        }
    });

	if ( $('#tipoDocumento').attr('value') != 1 ) {
		$('.tipoDistintivo').hide();
	} else {
		$('.tipoDistintivo').show();
		
	}
}

function cargarComboTipoDistintivoFctStock(){
	var tipoDoc = $("#idTipoDocFctStock").val();
	if("DSTV" == tipoDoc){
		$("#idTipoDstvFctStock").prop("disabled",false);
	} else {
		$("#idTipoDstvFctStock").val("");
		$("#idTipoDstvFctStock").prop("disabled",true);
	}
}

function limpiarFctStock() {
	$('#idContratoFctStock').val("");
	$('#idTipoDocFctStock').val("");
	$('#idTipoDstvFctStock').val("");
	$("#idTipoDstvFctStock").prop("disabled",true);
	$('#idDiaFechaIniFctStock').val("");
	$('#idMesFechaIniFctStock').val("");
	$('#idAnioFechaIniFctStock').val("");
	$('#idDiaFechaFinFctStock').val("");
	$('#idMesFechaFinFctStock').val("");
	$('#idAnioFechaFinFctStock').val("");
}

function bloqueaBotonesFctStock() {	
	$('#idBGenFctStock').prop('disabled', true);
	$('#idBGenFctStock').css('color', '#6E6E6E');
	$('#bLimpiarFctStock').prop('disabled', true);
	$('#bLimpiarFctStock').css('color', '#6E6E6E');
}

function desbloqueaBotonesFctStock() {	
	$('#idBGenFctStock').prop('disabled', false);
	$('#idBGenFctStock').css('color', '#000000');	
	$('#bLimpiarFctStock').prop('disabled', false);
	$('#bLimpiarFctStock').css('color', '#000000');	
}

function generarExcelFctStock(){
	bloqueaBotonesFctStock();
	$("#formData").attr("action", "generarExcelFacturacionStock.action").trigger("submit");
}

function descargarFicheroFctStock(){
	$("#formData").attr("action", "descargarFacturacionStock.action").trigger("submit");
}

