
function altaNuevoDatosSensibles() {

	if (document.getElementById("idTipoAgrupacion").value == "-1") {
		alert("Debe seleccionar un tipo de Dato.");
		return false;
	}

	if (document.getElementById("idTextoAgrupacion").value == "") {
		alert("Debe introducir un dato no vacio");
		return false;
	}

	if (document.getElementById("idTipoAgrupacion").value == "Matricula") {
		if (document.getElementById("idTextoAgrupacion").value.length > 12) {
			alert("El valor de la matricula es demasiado largo");
			return false;
		}
	}

	if (document.getElementById("idTipoAgrupacion").value == "Nif") {
		if (document.getElementById("idTextoAgrupacion").value.length > 9) {
			alert("El valor del nif es demasiado largo");
			return false;
		}
		if (!validaNIF(document.getElementById("idTextoAgrupacion"))) {
			alert("El Nif introducido no es valido");
			return false;
		}
	}
	
	if (document.getElementById("idTipoAgrupacion").value == "Bastidor") {
		if (document.getElementById("idTipoControl").value == "-1") {
			alert("Para los bastidores el tipo de Control es obligatorio");
			return false;
		}
	}

	document.formData.action = "guardarDatosSensibles.action";
	document.formData.submit();
	loadingGuardarDatosSensibles();
	return true;
}

function altaNuevoBastidor() {

	if (document.getElementById("idTextoAgrupacion").value == "") {
		alert("Debe introducir un dato no vacio");
		return false;
	}
	
	if (document.getElementById("idTipoControl").value == "-1") {
		alert("Debe rellenar el tipo de Control.");
		return false;
	}

	document.formData.action = "guardarBastidorDatosSensibles.action";
	document.formData.submit();
	loadingGuardarBastidor();
	return true;
}

function trimCampo(campo){
campo.value = campo.value.trim();
}

function consultaDatosSensibles() {
	trimCampo(document.getElementById("idTextoAgrupacion"));
	if (document.getElementById("idTipoAgrupacion").value == "-1") {
		alert("Debe seleccionar un tipo de Dato.");
		return false;
	}
	
	document.formData.action = "consultarDatosSensibles.action";
	document.formData.submit();
	loadingConsultarDatosSensibles();
	return true;
}

function consultaBastidor() {

	document.formData.action = "consultarBastidorDatosSensibles.action";
	document.formData.submit();
	loadingConsultarDatosSensibles();
	return true;
}

function limpiarNuevoDatosSensibles() {

	if (document.getElementById("idTipoAgrupacion")) {
		document.getElementById("idTipoAgrupacion").value = -1;
	}

	if (document.getElementById("idTextoAgrupacion")) {
		document.getElementById("idTextoAgrupacion").value = "";
	}
	
	if (document.getElementById("idTiempo")) {
		document.getElementById("idTiempo").value = -1;
	}
	

	if (document.getElementById("idTipoControl")) {
		document.getElementById("idTipoControl").value = -1;
		document.getElementById("idTipoControl").disabled = true;
		document.getElementById("idTipoControl").style.color = "#6E6E6E";
	}
	
	if (document.getElementById("idGrupo")) {
		document.getElementById("idGrupo").value = -1;
	}

	return true;
}

function limpiarNuevoBastidor() {

	if (document.getElementById("idTextoAgrupacion")) {
		document.getElementById("idTextoAgrupacion").value = "";
	}
	
	if (document.getElementById("idTiempo")) {
		document.getElementById("idTiempo").value = -1;
	}
	
	if (document.getElementById("idTipoControl")) {
		document.getElementById("idTipoControl").value = -1;
	}

	return true;
}

function limpiarConsultaDatosSensibles() {

	if (document.getElementById("diaIni")) {
		document.getElementById("diaIni").value = "";
	}
	if (document.getElementById("mesIni")) {
		document.getElementById("mesIni").value = "";
	}
	if (document.getElementById("anioIni")) {
		document.getElementById("anioIni").value = "";
	}

	if (document.getElementById("diaFin")) {
		document.getElementById("diaFin").value = "";
	}
	if (document.getElementById("mesFin")) {
		document.getElementById("mesFin").value = "";
	}
	if (document.getElementById("anioFin")) {
		document.getElementById("anioFin").value = "";
	}

	if (document.getElementById("idTipoAgrupacion")) {
		document.getElementById("idTipoAgrupacion").value = -1;
	}

	if (document.getElementById("idTextoAgrupacion")) {
		document.getElementById("idTextoAgrupacion").value = "";
	}
	
	if (document.getElementById("idTipoControl")) {
		document.getElementById("idTipoControl").value = -1;
		document.getElementById("idTipoControl").disabled = true;
		document.getElementById("idTipoControl").style.color = "#6E6E6E";
	}
	
	if (document.getElementById("idGrupo")) {
		document.getElementById("idGrupo").value = -1;
	}
	
	if (document.getElementById("idEstadoDatoSensible")) {
		document.getElementById("idEstadoDatoSensible").value = -1;
	}

	return true;
}

function limpiarConsultaBastidor() {

	if (document.getElementById("diaIni")) {
		document.getElementById("diaIni").value = "";
	}
	if (document.getElementById("mesIni")) {
		document.getElementById("mesIni").value = "";
	}
	if (document.getElementById("anioIni")) {
		document.getElementById("anioIni").value = "";
	}

	if (document.getElementById("diaFin")) {
		document.getElementById("diaFin").value = "";
	}
	if (document.getElementById("mesFin")) {
		document.getElementById("mesFin").value = "";
	}
	if (document.getElementById("anioFin")) {
		document.getElementById("anioFin").value = "";
	}

	if (document.getElementById("idTextoAgrupacion")) {
		document.getElementById("idTextoAgrupacion").value = "";
	}
	
	if (document.getElementById("idTipoControl")) {
		document.getElementById("idTipoControl").value = -1;
		document.getElementById("idTipoControl").disabled = true;
		document.getElementById("idTipoControl").style.color = "#6E6E6E";
	}
	
	if (document.getElementById("idGrupo")) {
		document.getElementById("idGrupo").value = -1;
	}

	return true;
}

function loadingGuardarDatosSensibles() {

	document.getElementById("idBotonGuardarDatosSensibles").disabled = "true";
	document.getElementById("idBotonGuardarDatosSensibles").style.color = "#6E6E6E";
	document.getElementById("idBotonGuardarDatosSensibles").value = "Procesando";

	document.getElementById("idBotonLimpiar").disabled = "true";
	document.getElementById("idBotonLimpiar").style.color = "#6E6E6E";

	document.getElementById("bloqueLoadingDatosSensibles").style.display = "block";

}

function loadingGuardarBastidor() {

	document.getElementById("idBotonGuardarDatosSensibles").disabled = "true";
	document.getElementById("idBotonGuardarDatosSensibles").style.color = "#6E6E6E";
	document.getElementById("idBotonGuardarDatosSensibles").value = "Procesando";

	document.getElementById("idBotonLimpiar").disabled = "true";
	document.getElementById("idBotonLimpiar").style.color = "#6E6E6E";

	document.getElementById("bloqueLoadingDatosSensibles").style.display = "block";

}

function loadingConsultarDatosSensibles() {

	document.getElementById("idBotonConsultarDatosSensibles").disabled = "true";
	document.getElementById("idBotonConsultarDatosSensibles").style.color = "#6E6E6E";
	document.getElementById("idBotonConsultarDatosSensibles").value = "Procesando";

	document.getElementById("idBotonLimpiarConsulta").disabled = "true";
	document.getElementById("idBotonLimpiarConsulta").style.color = "#6E6E6E";
	
	if (document.getElementById("idBotonEliminar") != null) {
		document.getElementById("idBotonEliminar").disabled = "true";
		document.getElementById("idBotonEliminar").style.color = "#6E6E6E";
	 }
	
	if (document.getElementById("idBotonActivar") != null) {
		document.getElementById("idBotonActivar").disabled = "true";
		document.getElementById("idBotonActivar").style.color = "#6E6E6E";
	}
	
	if (document.getElementById("bloqueLoadingDatosSensibles") != null) {
		document.getElementById("bloqueLoadingDatosSensibles").style.display = "block";
	}

}

function loadingEliminarDatosSensibles() {

	document.getElementById("idBotonConsultarDatosSensibles").disabled = "true";
	document.getElementById("idBotonConsultarDatosSensibles").style.color = "#6E6E6E";

	document.getElementById("idBotonLimpiarConsulta").disabled = "true";
	document.getElementById("idBotonLimpiarConsulta").style.color = "#6E6E6E";

	document.getElementById("idBotonEliminar").disabled = "true";
	document.getElementById("idBotonEliminar").style.color = "#6E6E6E";
	
	if (document.getElementById("idBotonActivar") != null) {
		document.getElementById("idBotonActivar").disabled = "true";
		document.getElementById("idBotonActivar").style.color = "#6E6E6E";
	}

	document.getElementById("idBotonEliminar").value = "Procesando";
	document.getElementById("bloqueLoadingDatosSensibles").style.display = "block";

}

function eliminarConsultaDatosSensibles() {

	if (numCheckedDatosSensibles() == 0) {
		alert("Debe seleccionar algun dato Sensible");
		return false;
	}
	var checks = document.getElementsByName("listaChecksDatosSensibles");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar los datos Sensibles seleccionados")) {
		loadingEliminarDatosSensibles();
		document.formData.action = "eliminarDatosSensibles.action?codigos="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}

}

function eliminarConsultaBastidor() {

	if (numCheckedDatosSensibles() == 0) {
		alert("Debe seleccionar algun dato Sensible");
		return false;
	}
	var checks = document.getElementsByName("listaChecksDatosSensibles");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar los datos Sensibles seleccionados")) {
		loadingEliminarDatosSensibles();
		document.formData.action = "eliminarBastidorDatosSensibles.action?codigos="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}

}

function seleccionarDatosSensibles() {
	var checks = document.getElementsByName("listaChecksDatosSensibles");
	var cont = 0;

	for ( var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			cont++;
	}
	return true;
}

function importarBastidor(){
	var archivo = document.getElementById("fichero").value;
	var tipoFich = $('input[name=datosSensiblesBean\\.tipoFichero]:checked').attr('value');
	try{
		if (archivo == "") {
			alert("Debe seleccionar el fichero a importar.");
			return false;
		}
		
		if(tipoFich == null){
			alert("Debe seleccionar el tipo de fichero a importar.");
			return false;
		}else{
			var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();

			if(tipoFich == "0"){
				if(".txt" != extension){
					alert("La extension del fichero no es la correcta.");
					return false;
				}
			}else if(tipoFich == "1"){
				if(".xls" != extension){
					alert("La extension del fichero no es la correcta.");
					return false;
				}
			}else if(tipoFich == "2"){
				if(".dat" != extension){
					alert("La extension del fichero no es la correcta.");
					return false;
				}
			}
		}

		if (!confirm("¿Está seguro de importar los datos sensibles a ese Grupo?")) {
			return false;
		}
	
		document.formData.action = "importarDatosSensibles.action";
		document.formData.submit();
		loadingImport();
	} catch (e) {
		alert("Ruta de fichero no v\u00e1lida");
	}
}

function cargarMensageExisteBastidor(){
	var existeBasti = document.getElementById("idExisteBastidor");
	if(existeBasti != null && existeBasti.value == "true"){
		if (confirm("El Bastidor que desea introducir, ya existe y esta dado de baja en la aplicaci&oacute;n &iquest;Desea volver a darlo de alta en la aplicaci&oacute;n?")) {
			document.formData.action = "guardarBastidorDatosSensibles.action?actualizarBastidor=true";
			document.formData.submit();
			loadingGuardarBastidor();
		}else{
			existeBasti.value = "false";
		}
	}
}

function cargarMensageExisteBastidorDatoSensible(){
	var existeBasti = document.getElementById("idExisteBastidor");
	if(existeBasti != null && existeBasti.value == "true"){
		if (confirm("El Bastidor que desea introducir, ya existe y esta dado de baja en la aplicaci&oacute;n &iquest;Desea volver a darlo de alta en la aplicaci&oacute;n?")) {
			document.formData.action = "guardarDatosSensibles.action?actualizarBastidor=true";
			document.formData.submit();
			loadingGuardarBastidor();
		}else{
			existeBasti.value = "false";
		}
	}
}

function enviarPeticion(){
	var checksErrores = document.getElementsByName("listaChecksErrores");
	var bastidoresErroneos = "";
	var grupoBastidores = "";
	var i = 0;
	while(checksErrores[i] != null) {
		if(checksErrores[i].checked) {
			var bastG = checksErrores[i].value.split("_");
			if(bastidoresErroneos ==""){
				bastidoresErroneos += bastG[0];
				grupoBastidores  += bastG[1];
			}else{
				bastidoresErroneos+= "-"+bastG[0];
				grupoBastidores+= "-"+bastG[1];
			}
		}
		i++;
	}
	document.forms[0].action="enviarPeticionDatosSensibles.action?bastidoresErroneos="+ bastidoresErroneos + "&grupoBastidores=" + grupoBastidores;
	document.forms[0].submit();
	return true;
}

function cambiarElementosPorPaginaConsultaDatosSensibles() {

	document.location.href = 'navegarDatosSensibles.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaDatosSensiblesBastidor() {

	document.location.href = 'navegarBastidorDatosSensibles.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function activarConsultaDatosSensibles(){
	if (numCheckedDatosSensibles() == 0) {
		alert("Debe seleccionar algun dato Sensible");
		return false;
	}
	var checks = document.getElementsByName("listaChecksDatosSensibles");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea activar los datos Sensibles seleccionados")) {
		loadingEliminarDatosSensibles();
		document.formData.action = "activarDatosSensibles.action?codigos="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function abrirEvolucionDatosSensibles(id){
	if(id == null || id == ""){
		return alert("No se puede obtener la evolución del dato sensible.");
	}
	var $dest = $("#divEmergenteConsultaEvolucion");
	var url = "abrirEvolucionDatosSensibles.action?id=" + id;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950,height: 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}
