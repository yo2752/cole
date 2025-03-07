
function cambiarElementosPorPaginaConsultaImpresionMasivas() {

	document.location.href = 'navegarConsultaImpresionMasiva.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function obtenerListaImpresionMasivas(){
	document.formData.action = "buscarConsultaImpresionMasiva.action";
	document.formData.submit();
}

function limpiarCamposConsultaImpresionMasiva(){
	if ($('#diaInicioEnviadoProceso')!=null){
		$("#diaInicioEnviadoProceso").val('');
	}
	if ($('#mesInicioEnviadoProceso')!=null){
		$("#mesInicioEnviadoProceso").val('');
	}
	if ($('#anioInicioEnviadoProceso')!=null){
		$("#anioInicioEnviadoProceso").val('');
	}
	if ($('#diaFinEnviadoProceso')!=null){
		$("#diaFinEnviadoProceso").val('');
	}
	if ($('#mesFinEnviadoProceso')!=null){
		$("#mesFinEnviadoProceso").val('');
	}
	if ($('#anioFinEnviadoProceso')!=null){
		$("#anioFinEnviadoProceso").val('');
	}
	if ($('#nombreFichero')!=null){
		$('#nombreFichero').val('');
	}
	if ($('#tipoDocumento')!=null){
		$('#tipoDocumento').val('-1');
	}
	if ($('#estadoImpresion')!=null){
		$('#estadoImpresion').val('-1');
	}
	if ($('#numColegiado')!=null){
		$('#numColegiado').val('');
	}
}
