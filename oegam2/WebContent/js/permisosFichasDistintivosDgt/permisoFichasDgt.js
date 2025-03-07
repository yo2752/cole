function mostrarBloqueActualizados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueActualizados").is(":visible")){
		$("#bloqueActualizados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function buscarGestionPrmFchMsv(){
	bloqueaBotonesPrmFchMsv();
	$("#formData").attr("action", "buscarGPFMasivo").trigger("submit");
}


function mostrarBloqueFallidos(){
	$("#bloqueActualizados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
		
	}
}

function cambiarElementosPorPaginaGestionPrmFchMsv(){
	var $dest = $("#displayTagDivGestionPrmFchMsv");
	$.ajax({
		url:"navegarGPFMasivo.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaGestionPrmFchMsv").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con los expedientes.');
	    }
	});
}
function limpiarGestionPrmFchMsv(){
	$("#idTipoTramitePrmFchMsv").val("");
	$("#idContratoPrmFchMsv").val("");
	$("#diaFechaPrtPrmFchMsv").val("");
	$("#mesFechaPrtPrmFchMsv").val("");
	$("#anioFechaPrtPrmFchMsv").val("");
}

function solicitarPrmMsv(valueBoton){
	mostrarLoadingGestionPrmFchMsv('idBSolPrmFchMsv');
	if($("#idTipoTramitePrmFchMsv").val() == ""){
		alert("Debe seleccionar algún tipo de trámite para poder generar la solicitud de permisos.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	}else if($("#idContratoPrmFchMsv").val() == ""){
		alert("Debe seleccionar algún contrato para poder generar la solicitud de permisos.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#diaFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el día de presentación para poder generar la solicitud de permisos.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#mesFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el mes de presentación para poder generar la solicitud de permisos.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#anioFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el año de presentación para poder generar la solicitud de permisos.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else{
		if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar la documentación para el contrato y la fecha de presentación seleccionados?")){
			$("#formData").attr("action", "solicitarPermisosGPFMasivo.action").trigger("submit");
		}else{
			ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
		}
	}
}

function solicitarFchMsv(valueBoton){
	mostrarLoadingGestionPrmFchMsv('idBSolPrmFchMsv');
	if($("#idTipoTramitePrmFchMsv").val() == ""){
		alert("Debe seleccionar algún tipo de trámite para poder generar la solicitud de fichas.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	}else if($("#idContratoPrmFchMsv").val() == ""){
		alert("Debe seleccionar algún contrato para poder generar la solicitud de fichas.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#diaFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el día de presentación para poder generar la solicitud de fichas.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#mesFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el mes de presentación para poder generar la solicitud de fichas.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else if($("#anioFechaPrtPrmFchMsv").val() == ""){
		alert("Debe seleccionar el año de presentación para poder generar la solicitud de fichas.");
		ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
	} else{
		if (confirm("\u00BFEst\u00E1 seguro de que desea solicitar la documentación para el contrato y la fecha de presentación seleccionados?")){
			$("#formData").attr("action", "solicitarFichasGPFMasivo.action").trigger("submit");
		}else{
			ocultarLoadingGestionPrmFchMsv('idBSolPrmFchMsv',valueBoton);
		}
	}
}

function mostrarLoadingGestionPrmFchMsv(boton){
	bloqueaBotonesPrmFchMsv();
	document.getElementById("bloqueLoadingGestionPrmFchMsv").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingGestionPrmFchMsv(boton, value) {
	document.getElementById("bloqueLoadingGestionPrmFchMsv").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesPrmFchMsv();
}

function bloqueaBotonesPrmFchMsv() {	
	$('#idBSolPrmFchMsv').prop('disabled', true);
	$('#idBSolPrmFchMsv').css('color', '#6E6E6E');	
	$('#idBConsultaPrmFchMsv').prop('disabled', true);
	$('#idBConsultaPrmFchMsv').css('color', '#6E6E6E');	
	$('#idBLimpiarPrmFchMsv').prop('disabled', true);
	$('#idBLimpiarPrmFchMsv').css('color', '#6E6E6E');

}

function desbloqueaBotonesPrmFchMsv() {	
	$('#idBSolPrmFchMsv').prop('disabled', false);
	$('#idBSolPrmFchMsv').css('color', '#000000');	
	$('#idBConsultaPrmFchMsv').prop('disabled', false);
	$('#idBConsultaPrmFchMsv').css('color', '#000000');	
	$('#idBLimpiarPrmFchMsv').prop('disabled', false);
	$('#idBLimpiarPrmFchMsv').css('color', '#000000');	
}
