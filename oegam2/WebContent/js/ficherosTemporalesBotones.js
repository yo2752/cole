
function consultaFicheros(){
	document.formData.action="buscarConsultaFicherosTemporales.action";
	document.formData.submit();
}

function limpiarConsultaFicheros(){
	document.getElementById("idNombreFichero").value = null;
	document.getElementById("diaIni").value 		 = null;
	document.getElementById("mesIni").value 		 = null;
	document.getElementById("anioIni").value 		 = null;
	document.getElementById("diaFin").value 		 = null;
	document.getElementById("mesFin").value 		 = null;
	document.getElementById("anioFin").value 		 = null;
	if(document.getElementById("idNumColegiado") != null){
		document.getElementById("idNumColegiado").value  = null;
	}
	if(document.getElementById("idTipoFichero")){
		document.getElementById("idTipoFichero").value 	 = "-1";
	}
}

function cambiarElementosPorPaginaConsultaFicheros() {
	document.location.href = 'navegarConsultaFicherosTemporales.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function descargarFichero(idFichero, estado){
	if(estado == "0"){
		if(confirm("Este fichero se borrar\xe1 en 5 minutos una vez impreso \u00BFDesea Continuar?")) {
			document.formData.action="imprimirConsultaFicherosTemporales.action?idFichero="+idFichero;
			document.formData.submit();
		}
	}else{
		if(confirm("Este fichero ya ha sido descargado alguna vez y en breves memomentos sera borrado \u00BFDesea volver ha descargarlo?")) {
			document.formData.action="imprimirConsultaFicherosTemporales.action?idFichero="+idFichero;
			document.formData.submit();
		}
	}
}

function mostrarAlertFichero(){
	var ficheroBorrado = document.getElementById("idFicheroBorrado");
	if(ficheroBorrado != null && ficheroBorrado.value == "true"){
		ficheroBorrado.value="false";
		alert("El fichero que acaba de imprimir se borrara automaticamente de la plataforma pasados 5 minutos.");
	}
}

function ponerTitulo(){
	var texto = document.getElementById("textoTitulo");
	var tipoFich = document.getElementById("idTipoFichero");
	var hTipoFich = document.getElementById("hTipoFichero");
	if(tipoFich != null && tipoFich.value == "0"){
		texto.innerHTML  = "Consulta Ficheros Tasas de Pegatinas";
	}else if(hTipoFich != null && hTipoFich.value == "0"){
		texto.innerHTML  = "Consulta Ficheros Tasas de Pegatinas";
	}
}