function validarBusqueda(){
	eliminaEspacios(new Array("razonSocial", "numColegiado",
			"diaAltaIni", "mesAltaIni", "anioAltaIni",
			"diaAltaFin", "mesAltaFin", "anioAltaFin")); 
	
	if(!campoVacio("numColegiado")){
		if(!validaNumeroPositivo(document.getElementById("numColegiado").value)
				|| !validaNumeroEntero(document.getElementById("numColegiado").value)){
			alert(MSG_ERR_NUMEROCOLEGIADO_NO_VALIDO);
			return false;
		}
	}
	
	if(	!campoVacio("diaAltaIni") || !campoVacio("mesAltaIni") || !campoVacio("anioAltaIni") || 
		!campoVacio("diaAltaFin") || !campoVacio("mesAltaFin") || !campoVacio("anioAltaFin")){
			if(	!validaRangoFechas(document.formData.diaAltaIni, document.formData.mesAltaIni, 
					document.formData.anioAltaIni, document.formData.diaAltaFin, document.formData.mesAltaFin, document.formData.anioAltaFin, true)){
				return false;
			}
	}
	
	return true;
}