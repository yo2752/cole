function buscarFactDstv(){
	if (!validaCampoExpediente()) {
		return false;
	}
		
	bloqueaBotonesFact();
	$("#formData").attr("action", "buscarFactDstv").trigger("submit");
}

function bloqueaBotonesFact() {	
	$('#idBFactConsulta').prop('disabled', true);
	$('#idBFactConsulta').css('color', '#6E6E6E');	
	$('#idBFactLimpiar').prop('disabled', true);
	$('#idBFactLimpiar').css('color', '#6E6E6E');	
	$('#idNotificar').prop('disabled', true);
	$('#idNotificar').css('color', '#6E6E6E');	
	$('#idGenerarExcel').prop('disabled', true);
	$('#idGenerarExcel').css('color', '#6E6E6E');	
	$('#idGenerarExcelDetallado').prop('disabled', true);
	$('#idGenerarExcelDetallado').css('color', '#6E6E6E');	

}
function habilitarBotonesFact(){
	$("#idBFactConsulta").prop('disabled', false);
	$("#idBFactConsulta").css('color', '#000000');	
	$("#idBFactLimpiar").prop('disabled', false);
	$("#idBFactLimpiar").css('color', '#000000');
	$("#idNotificar").prop('disabled', false);
	$("#idNotificar").css('color', '#000000');	
	$("#idGenerarExcel").prop('disabled', false);
	$("#idGenerarExcel").css('color', '#000000');
	$("#idGenerarExcelDetallado").prop('disabled', false);
	$("#idGenerarExcelDetallado").css('color', '#000000');
}

function limpiarFactDstv(){
	$("#idDocIdFctDstv").val("");
	$("#idMatriculaFctDstv").val("");
	$("#idNumExpedienteFctDstv").val("");
	$("#idContratoFctDstv").val("");
	$("#diaFechaPrtIni").val("");
	$("#mesFechaPrtIni").val("");
	$("#anioFechaPrtIni").val("");
	$("#diaFechaPrtFin").val("");
	$("#mesFechaPrtFin").val("");
	$("#anioFechaPrtFin").val("");
	$("#idTipoDistintivoFctDstv").val("");
	$("#idNifTitularFctDstv").val("");
}

function cambiarElementosPorPaginaConsFactDstv(){
	var $dest = $("#displayTagDivFactDstv");
	$.ajax({
		url:"navegarFactDstv.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaFact").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los expedientes.');
	    }
	});
}

function marcarTodosFactDstv(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { 
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}
function mostrarLoadingFacturacionDstv(bloqueLoading,boton) {
	bloqueaBotonesFact();
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingFacturacionDstv(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesFact();
}


function notificarIncidencia(){
	var valueBoton = $("#idNotificar").val();
	mostrarLoadingFacturacionDstv("bloqueLoadingFacturacionDstv","idNotificar");
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoadingFacturacionDstv('idNotificar',valueBoton,"bloqueLoadingFacturacionDstv");
		return alert("Debe seleccionar un registro para poder notificar la incidencia.");
	}
	var $dest = $("#divEmergenteFacturacionDstv");
	$.post("cargarPopUpNotificaIncidenciaFactDstv.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 700,
				height: 400,
				buttons : {
					Guardar : function() {
					var motivo = $("#idMotivo").val();
					var cantidad = $("#idCantidad").val();
					var duplicado = $("#comboEsDuplicado").val();
					$("#divEmergenteFacturacionDstv").dialog("close");
						limpiarHiddenInput("facturacionDstvInc.motivoIncidencia");
						input = $("<input>").attr("type", "hidden").attr("name", "facturacionDstvInc.motivoIncidencia").val(motivo);
						$('#formData').append($(input));
						limpiarHiddenInput("facturacionDstvInc.cantidad");
						input = $("<input>").attr("type", "hidden").attr("name", "facturacionDstvInc.cantidad").val(cantidad);
						$('#formData').append($(input));
						limpiarHiddenInput("facturacionDstvInc.duplicado");
						input = $("<input>").attr("type", "hidden").attr("name", "facturacionDstvInc.duplicado").val(duplicado);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "guardarFactDstv.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingFacturacionDstv('idNotificar',valueBoton,"bloqueLoadingFacturacionDstv");
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
function resizeMeFechaIncPopUp(){
	var $cal = $('iframe:eq(1)', parent.document);
	var position = $("#anioIncidenciaId").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top+20) + "px"
	});
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function descargarExcel(){
	$("#formData").attr("action", "descargarFactDstv").trigger("submit");
}

function generarExcel(){
	var valueBoton = $("#idBDescargExcel").val();
	mostrarLoadingFacturacionDstv("bloqueLoadingFacturacionDstv","idBDescargExcel");
	$("#formData").attr("action", "generarExcelFactDstv").trigger("submit");
}

function generarExcelDetallado(){
	var valueBoton = $("#idBDescargExcel").val();
	mostrarLoadingFacturacionDstv("bloqueLoadingFacturacionDstv","idBDescargExcel");
	$("#formData").attr("action", "generarExcelDetalladoFactDstv").trigger("submit");
}

function validaCampoExpediente() {
	var campo = document.getElementById("idNumExpedienteFctDstv");
	var divError = document.getElementById("divError");
	var mensajesError = document.getElementById('mensajesError');
	if ((campo.value != '' && (isNaN(campo.value)) || (campo.value != '' && campo.value.length > 20))) {
		campo.style.color = '#ff0000';
		campo.focus();
		mensajesError.innerHTML = '<li>El número de expediente debe tener máximo 20 caracteres y ser numérico</li>';
		divError.style.display = 'block';
		return false;
	} else {
		campo.style.color = '#000000';
		return true;
	}
}
