function accionCertificado(accion, idBoton){
	if(numCheckedClaves() == 0) {
		alert("Marque un certificado");
		return false;
	}
	if(numCheckedClaves() > 1) {
		alert("Por seguridad, las operaciones deben hacerse de una en una. Marque s\u00f3lo un certificado");
		return false;
	}
	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar el certificado seleccionado?")) {
	
		var checks = document.getElementsByName("listaChecksCertificados");	
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked){
				alias = checks[i].value;
			}
		}
		document.getElementById("aliasCertificado").value = alias;
		document.formData.action = accion + "KeyStore.action";
		document.formData.submit();
		loadingKeyStore(idBoton, "bloqueLoadingAccionesCertificados");
		return true;
	
	} else {
		return false;
	}
}

function numCheckedClaves() {
	var checks = document.getElementsByName("listaChecksCertificados");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function abrirKeyStore(idBoton) {
	if (document.getElementById("keyStoreName").selectedIndex == 0) {
		alert("Seleccione el almac\u00e9n");
		return false;
	}

	if (document.getElementById("keyStorePassword").value == "") {
		alert("Indique la password");
		return false;
	}

	document.formData.action = "loadKeyStore.action";
	document.formData.submit();
	loadingKeyStore(idBoton, "bloqueLoadingAbrirKeyStore");
	return true;
}

function cerrarKeyStore(idBoton){
	document.formData.action = "closeKeyStore.action";
	document.formData.submit();
	loadingKeyStore(idBoton, "bloqueLoadingAbrirKeyStore");
	return true;
}

function mostrarBloqueImportacion(){
	document.getElementById("bloqueImportacion").style.display = "block";
}

function importarKeyStore(){
	if(document.getElementById("almacenAbierto").value == "CLAVES_PRIVADAS" &&
			(document.getElementById("passwordClavePrivada") == null || document.getElementById("passwordClavePrivada").value == "")){
		alert("Introduzca la password del certificado para importar su clave privada");
		return false;
	}
	document.formData.action = "importarKeyStore.action";
	document.formData.submit();
	return true;
}

function loadingKeyStore(idBoton, idBloqueLoading) {

	if(idBloqueLoading == "bloqueLoadingAbrirKeyStore"){
		if(document.getElementById("bloqueLoadingAbrirKeyStore")){
			document.getElementById("bloqueLoadingAbrirKeyStore").style.display = "block";
		}
	}else if(idBloqueLoading == "bloqueLoadingAccionesCertificados"){
		if(document.getElementById("bloqueLoadingAccionesCertificados")){
			document.getElementById("bloqueLoadingAccionesCertificados").style.display = "block";
		}
	}

	if(document.getElementById("bAbrirKeyStore")){
		document.getElementById("bAbrirKeyStore").disabled = "true";
		document.getElementById("bAbrirKeyStore").style.color = "#6E6E6E";
	}
	if(document.getElementById("bCerrarKeyStore")){
		document.getElementById("bCerrarKeyStore").disabled = "true";
		document.getElementById("bCerrarKeyStore").style.color = "#6E6E6E";
	}
	if(document.getElementById("idBotonLimpiarKeystore")){
		document.getElementById("idBotonLimpiarKeystore").disabled = "true";
		document.getElementById("idBotonLimpiarKeystore").style.color = "#6E6E6E";
	}
	if(document.getElementById("bImportarCertificado")){
		document.getElementById("bImportarCertificado").disabled = "true";
		document.getElementById("bImportarCertificado").style.color = "#6E6E6E";
	}
	if(document.getElementById("bExportarCertificado")){
		document.getElementById("bExportarCertificado").disabled = "true";
		document.getElementById("bExportarCertificado").style.color = "#6E6E6E";
	}
	if(document.getElementById("bEliminarCertificado")){
		document.getElementById("bEliminarCertificado").disabled = "true";
		document.getElementById("bEliminarCertificado").style.color = "#6E6E6E";
	}
	if(document.getElementById("bRenombrarCertificado")){
		document.getElementById("bRenombrarCertificado").disabled = "true";
		document.getElementById("bRenombrarCertificado").style.color = "#6E6E6E";
	}
	if(document.getElementById("bDetalleCertificado")){
		document.getElementById("bDetalleCertificado").disabled = "true";
		document.getElementById("bDetalleCertificado").style.color = "#6E6E6E";
	}
	if(document.getElementById("bComprobarUrl")){
		document.getElementById("bComprobarUrl").disabled = "true";
		document.getElementById("bComprobarUrl").style.color = "#6E6E6E";
	}

	if(idBoton == "bAbrirKeyStore"){
		if(document.getElementById("bAbrirKeyStore")){
			document.getElementById("bAbrirKeyStore").value = "Procesando...";
		}
	}
	if(idBoton == "bCerrarKeyStore"){
		if(document.getElementById("bCerrarKeyStore")){
			document.getElementById("bCerrarKeyStore").value = "Procesando...";
		}
	}
	if(idBoton == "bEliminarCertificado"){
		if(document.getElementById("bEliminarCertificado")){
			document.getElementById("bEliminarCertificado").value = "Procesando...";
		}
	}
	if(idBoton == "bRenombrarCertificado"){
		if(document.getElementById("bRenombrarCertificado")){
			document.getElementById("bRenombrarCertificado").value = "Procesando...";
		}
	}
	if(idBoton == "bComprobarUrl"){
		if(document.getElementById("bComprobarUrl")){
			document.getElementById("bComprobarUrl").value = "Procesando...";
		}
	}

}

function limpiarFormularioAbrirKeyStore(){
	if(document.getElementById("keyStorePassword")){
		document.getElementById("keyStorePassword").value = "";
	}
	if(document.getElementById("keyStoreName")){
		document.getElementById("keyStoreName").selectedIndex = 0;
	}
}

function abrirVentanaDetalleCertificado(idBoton){
	if(numCheckedClaves() == 0) {
		alert("Marque un certificado");
		return false;
	}
	if(numCheckedClaves() > 1) {
		alert("Por seguridad, las operaciones deben hacerse de una en una. Marque s\u00f3lo un certificado");
		return false;
	}
	var alias = null;
	var checks = document.getElementsByName("listaChecksCertificados");	
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked){
			alias = checks[i].value;
		}
	}
	ventanaDetalleCertificado = window.open('abrirPopUpDetallesKeyStore.action?aliasCertificado=' + alias,'popup','width=740,height=600,top=250,left=320,scrollbars=NO,resizable=NO,status=NO,menubar=NO');
}

function renombrarCertificado(idBoton){
	if(numCheckedClaves() == 0) {
		alert("Marque un certificado");
		return false;
	}
	if(numCheckedClaves() > 1) {
		alert("Por seguridad, las operaciones deben hacerse de una en una. Marque s\u00f3lo un certificado");
		return false;
	}
	var alias = null;
	var checks = document.getElementsByName("listaChecksCertificados");	
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked){
			alias = checks[i].value;
		}
	}
	document.getElementById("aliasCertificado").value = alias;
	nuevoAlias = prompt('Introduzca el nuevo alias para el certificado:', alias);
	if(nuevoAlias == null || nuevoAlias == "" || nuevoAlias == alias){
		return false;
	}
	document.getElementById("nuevoAlias").value = nuevoAlias;
	document.formData.action = "renombrarKeyStore.action";
	document.formData.submit();
	loadingKeyStore(idBoton, "bloqueLoadingAccionesCertificados");
	return true;
}

function exportarCertificado(idBoton){
	if(numCheckedClaves() == 0) {
		alert("Marque un certificado");
		return false;
	}
	if(numCheckedClaves() > 1) {
		alert("Marque s\u00f3lo un certificado");
		return false;
	}
	//
	var actionPoint = "descargarCertificadoKeyStore.action";

	var navegador = '';
	var IE = '\v' == 'v';
	if (navigator.userAgent.toLowerCase().indexOf('safari/') != '-1')
		var navegador = 'safari';

	if (IE || navegador == 'safari') {
		alert("Est\u00E1 utilizando un navegador que no permite la descarga autom\u00E1tica de ficheros por "
				+ "la aplicaci\u00F3n.\n. No se puedes exportar el certificado.\n\n"
				+ "Recomendamos la instalaci\u00F3n de otro navegador para un uso m\u00E1s \u00F3ptimo de la aplicaci\u00F3n.");
	} else {
		document.forms[0].action = actionPoint;
		document.forms[0].submit();

	}
	//
	var alias = null;
	var checks = document.getElementsByName("listaChecksCertificados");	
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked){
			alias = checks[i].value;
		}
	}
	document.getElementById("aliasCertificado").value = alias;
	document.formData.action = "exportarKeyStore.action";
	document.formData.submit();
	return true;
}

function invalidarInfo() {
	document.getElementById("infoCertificado").value = '';
}

function comprobar(idBoton){
	var comprobarUrl = prompt('Introduzca la url a comprobar. Se intentar\u00e1n leer los certificados desplegados en el servidor de dicha url para ver si se encuentra coincidencia con alguno de los contenidos en el almac\u00e9n de claves p\u00fablicas:', "[URL a comprobar]");
	if(comprobarUrl == null || comprobarUrl == ""){
		return false;
	}
	document.getElementById("comprobarUrl").value = comprobarUrl;
	document.formData.action = "comprobarCertificadosUrlKeyStore.action";
	document.formData.submit();
	loadingKeyStore(idBoton, "bloqueLoadingAccionesCertificados");
	return true;
}