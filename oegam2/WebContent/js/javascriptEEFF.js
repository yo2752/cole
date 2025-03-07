
function actualizarCamposEEFFLiberacion(){
	if (document.getElementById("idNive")!=null){
		document.getElementById("tarjetaNiveLiberarEEFF").value = document.getElementById("idNive").value;
	}
	if (document.getElementById("idBastidor")!=null){
		document.getElementById("tarjetaBastidorLiberarEEFF").value = document.getElementById("idBastidor").value;
	}
}

function cambiarActivacionLiberarEEFF(){
	if (document.getElementById("idNive")!=null && document.getElementById("idNive").value != ""){
		document.getElementById("pestaniaEnlaceLiberacionEEFF").style.display="block";
	} else {
		document.getElementById("pestaniaEnlaceLiberacionEEFF").style.display="none";
	}
}

function ocultarCamposEEFFLiberacion(){
	if (document.getElementById("exentoLiberarEEFF").checked) {
		document.getElementById("datosLiberacionEEFF").style.display="none";
	} else {
		document.getElementById("datosLiberacionEEFF").style.display="block";
	}
}

function  liberarEEFF(pestania){
	document.formData.action = "liberarTramiteAltasMatriculacionMatw.action#"+ pestania;
	document.formData.submit();
}

function  liberarEEFFMatriculacion(){
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action = "liberarTramiteAltaTramiteTraficoMatriculacion.action#" + pestania;
	document.formData.submit();
}

function consultarEEFFMatriculacionMatw(pestania){
	document.formData.action = "consultaLiberacionAltasMatriculacionMatw.action#" + pestania;
	document.formData.submit();
	
	loadingGuardarMatriculacion(pestania);
	return true;
}

function liberarEEFFMasivo(boton){
	
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}

	
	mostrarLoadingConsultar(boton);
	$('#formData').attr("action","liberarMasivoEeffConsultaTramiteTrafico.action");
	$('#formData').submit();
	
	
}

function consultarEEFF(botton){
	if (numCheckedImprimir() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	
	
	$('#formData').attr("action","consultaLiberacionConsultaTramiteTrafico.action");
	$('#formData').submit();
	
}


function mostrarBloqueLiberadosEEFF() {
	var divValidos = document.getElementById("bloqueLiberadosEEFF");
	var desplegable = document.getElementById("imgLiberadosEEFF");
	
	if(divValidos.style.display == 'none'){
		divValidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";	
	}
	else if(divValidos.style.display == 'block'){
		divValidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";	
	}

}

function mostrarBloqueFallidosLiberadosEEFF() {
	var divFallidos = document.getElementById("bloqueFallidosLiberadoEEFF");
	var desplegable = document.getElementById("imgFallidosTelematicamente");
	
	if(divFallidos.style.display == 'none'){
		divFallidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";	
	}
	else if(divFallidos.style.display == 'block'){
		divFallidos.style.display='none';	
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";	
	}
}

function buscarConsultaEEFF() {
	
	document.formData.action = "buscarConsultaEEFF.action";
	document.formData.submit();
}

function enviarConsultaEEFF() {
	document.formData.action = "consultarConsultaEEFF.action";
	document.formData.submit();
}
function limpiarConsultaEEFF(){
	
	document.getElementById("nive").value = "";
	document.getElementById("bastidor").value = "";
	document.getElementById("cif").value = "";
	document.getElementById("marca").value = "";
	document.getElementById("nombreRepresentado").value = "";
	document.getElementById("nifRepresentado").value = "";


}

function limpiarConsultaBuscarEEFF(){
	
	document.getElementById("tarjetaNive").value = "";
	document.getElementById("tarjetaBastidor").value = "";
	document.getElementById("diaBusquedaIni").value = "";
	document.getElementById("mesBusquedaIni").value = "";
	document.getElementById("anioBusquedaIni").value = "";
	document.getElementById("diaBusquedaFin").value = "";
	document.getElementById("mesBusquedaFin").value = "";
	document.getElementById("anioBusquedaFin").value = "";
	document.getElementById("cifConcesionario").value = "";
	
}

function marcarTodosConsultaTramiteEEFF(boton, listaCheck){
	var obj = $('[name="'+listaCheck+'"]');
	$.each( obj, function( key, objeto) {
		$(this).attr('checked', boton.checked);
	});
}

function obtenerDetalleConsultaEEFF(numExpediente){
	
	var opciones_ventana = 'width=850,height=800,top=150,left=280,resizable=no';
	var ventana_evolucion = window.open(
			'obtenerDetalleConsultaEEFF.action?numExpediente='+numExpediente, 'popup',
			opciones_ventana);
}

