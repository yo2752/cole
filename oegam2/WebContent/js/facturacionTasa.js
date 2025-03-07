function buscarFacturacionTasa() {
	
	var numExpediente = document.getElementById("numExpediente").value;
	
	if (numExpediente != null) {		
		numExpediente = numExpediente.replace(/\s/g,'');
		
	if (isNaN(numExpediente)) {
		alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
			return false;
		}		
					
		document.getElementById("numExpediente").value = numExpediente;
	}
	
	document.formData.action = "buscarConsultaFacturacionTasas.action";
	document.formData.submit();
}

function limpiarFacturacionTasa(){
	document.getElementById("numExpediente").value = "";
	document.getElementById("numColegiado").value = "";
	document.getElementById("bastidor").value = "";
	document.getElementById("matricula").value = "";
	document.getElementById("nif").value = "";
	document.getElementById("codigoTasa").value = "";
	document.getElementById("diaAplicacionIni").value = "";
	document.getElementById("mesAplicacionIni").value = "";
	document.getElementById("anioAplicacionIni").value = "";
	document.getElementById("diaAplicacionFin").value = "";
	document.getElementById("mesAplicacionFin").value = "";
	document.getElementById("anioAplicacionFin").value = "";
}

function buscarIntervinienteFacturacionTasa(idNif) {
	$("#nifBusqueda").val( $("#"+ idNif).val());
	document.formData.action = "consultarPersonaAltaTramiteFacturacionTasas.action";
	document.formData.submit();
}

function guardarFacturacion() {
	
	if (document.getElementById("codigoTasaFacturacion").value == "-1") {
		alert("C\u00f3digo Tasa obligatorio.");
		return false;
	}
	
	if (document.getElementById("diaAplicacion").value == "" || document.getElementById("mesAplicacion").value == "" || document.getElementById("anioAplicacion").value == "") {
		alert("Fecha de Primera Aplicaci\u00f3n obligatoria.");
		return false;
	}
	
	if (document.getElementById("idBastidorFact").value == "" && document.getElementById("idMatriculaFact").value == "") {
		alert("Debe indicar un bastidor o una matr\u00edcula.");
		return false;
	}
	
	if (document.getElementById("nifTasaFacturacion").value == "") {
		alert("NIF del titular obligatorio.");
		return false;
	}
	
	if (document.getElementById("apellido1").value == "") {
		alert("Primer Apellido / Raz\u00f3n Social obligatorio.");
		return false;
	}
	
	document.formData.action = "guardarFacturacionAltaTramiteFacturacionTasas.action";
	document.formData.submit();
}

function recargarTasasFacturacion(select_tipoTasa, id_select_codigoTasa, id_codigoTasaSeleccionado) {
	selectTasa = document.getElementById(select_tipoTasa);
	if(selectTasa != null && selectTasa.selectedIndex > 0) {
		obtenerCodigosTasaFacturacionPorTipoTasa(selectTasa, document.getElementById(id_select_codigoTasa), id_codigoTasaSeleccionado);
	}
}

function obtenerTasasFacturacion(select_tipoTasa, id_select_codigoTasa, id_codigoTasaSeleccionado) {
	document.getElementById(id_codigoTasaSeleccionado).value = "-1";
	obtenerCodigosTasaFacturacionPorTipoTasa(select_tipoTasa, document.getElementById(id_select_codigoTasa), id_codigoTasaSeleccionado);
}

function obtenerCodigosTasaFacturacionPorTipoTasa(selectTipoTasa, selectCodigosTasa, id_CodigoTasaSeleccionado) {
	if(selectTipoTasa.selectedIndex==0) {
		selectCodigosTasa.length = 0;
		selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
		return;
	}
	var selectedOption = selectTipoTasa.options[selectTipoTasa.selectedIndex].value;
	var url = "recuperarTasasFacturacionTraficoAjax.action?tipoTasa="+selectedOption;		
	var req_generico_codigosTasa = NuevoAjax();
	req_generico_codigosTasa.onreadystatechange = function () { 
		rellenarListaCodigosTasaFacturacion(req_generico_codigosTasa,selectCodigosTasa,id_CodigoTasaSeleccionado);
	}
	req_generico_codigosTasa.open("POST", url, true);			
	req_generico_codigosTasa.send(null);			
}

function rellenarListaCodigosTasaFacturacion(req_generico_codigosTasa,selectCodigosTasa,id_codigoTasaSeleccionado){
	selectCodigosTasa.options.length = 0;
	if (req_generico_codigosTasa.readyState == 4) { 
		if (req_generico_codigosTasa.status == 200) { 
			textToSplit = req_generico_codigosTasa.responseText;
			
			returnElements=textToSplit.split("||");
			selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");

			var codigoTasaSeleccionado = document.getElementById(id_codigoTasaSeleccionado).value;
			var existe = false;
			for ( var i=0; i<returnElements.length; i++ ){
				 value = returnElements[i].split(";");
				 if(!isNaN(value[0])) {
					 selectCodigosTasa.options[i+1] = new Option(value[0], value[1]);
					 if(selectCodigosTasa.options[i+1].value == codigoTasaSeleccionado){
						 selectCodigosTasa.options[i+1].selected = true;
						 existe = true;
					 }
				 }
			}
			if(!existe && codigoTasaSeleccionado!="-1") {
				selectCodigosTasa.options[selectCodigosTasa.options.length] = new Option(codigoTasaSeleccionado, codigoTasaSeleccionado, true, true);
			}
		}
	}
}
