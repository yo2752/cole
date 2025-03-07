
function habilitarFundamento576(habilitar){
	if (test="!readOnly")
		document.formData.reduccion576.disabled=habilitar;
	if (habilitar)  document.formData.reduccion576.selectedIndex=0;

}

function errorValidacion(mensaje, guardar){
	if(guardar){
		alert(mensaje);	
		return false;
	}else{
		return confirm(mensaje + "\n¿Desea continuar?");
	}
}

function habilitarLimitacionDisposicion(habilitar){
	if (test="!readOnly")
		habilitarObjText(document.formData.disposicionFecha, habilitar);
	habilitarObjText(document.formData.disposicionReg, habilitar);
	habilitarObjText(document.formData.disposicionFinanciera, habilitar);

}

function habilitarObjText(obj, habilitar){
	obj.disabled=habilitar;
	if (habilitar) obj.value='';
}

function comprobarLimitacionDisposicion(){
	var index = document.formData.disposicionTipo.selectedIndex;
	if(index==0){
		habilitarLimitacionDisposicion(true);
	}else{
		habilitarLimitacionDisposicion(false);
		document.getElementById("disposicionFinanciera").value="EXENTO PR IEDMT";
	}

}

function getValorDecimal(objForm){
	var valor = 0;
	try{
		valor = parseFloat(objForm.value.replace(',','.'));
		if (isNaN(valor)){ valor=0;}
	}catch(e){valor =0;}
	return valor;
}

function redondear(cantidad, decimales) {
	var cantidad = parseFloat(cantidad);
	if (isNaN(cantidad)){
		cantidad=0;
	}
	var decimales = parseFloat(decimales);
	decimales = (!decimales ? 2 : decimales);
	return Math.round(cantidad * Math.pow(10, decimales)) / Math.pow(10, decimales);
}

function deshabilitarCamposNoPermisoPago(idElemento){

	var permiso = document.getElementById(idElemento).value; 


	if(permiso="false"){
		document.formData.idBaseImponible576.value = "permiso false";
	}
	if(permiso="true") {
		document.formData.idBaseImponible576.value = "permiso true";
	}

}


function borrarCampos576(){
	document.formData.idBaseImponible576.value = "";
	document.formData.cuota576.value = "";
	document.formData.cuotaIngresar576.value = "";
	document.formData.importeTotal576.value = "";

	comprobarPresentarAEAT();
}

function actualizarHidden(){
	document.formData.hiddenReduccion576.value = document.formData.reduccion576.checked;
}

function actualizarNRCHidden(){
	document.formData.hiddenExentoNRC.value = document.formData.exentoNRC.checked;
}

function exentoIedtm(deshabilitar){
	habilitarObjText(document.formData.idTipoLimitacionDisposicionIEDTM, deshabilitar);
	habilitarObjText(document.formData.diaInicioExencion, deshabilitar);
	habilitarObjText(document.formData.mesInicioExencion, deshabilitar);
	habilitarObjText(document.formData.anioInicioExencion, deshabilitar);
	habilitarObjText(document.formData.numRegistro, deshabilitar);
	deshabilitarNRC(deshabilitar); 
}


function deshabilitarNRC(deshabilitar){
	if (test="!readOnly")
		habilitarObjText(document.formData.nrc576, deshabilitar);
	habilitarObjText(document.formData.idDiaPago576, deshabilitar);
	habilitarObjText(document.formData.idMesPago576, deshabilitar);
	habilitarObjText(document.formData.idAnioPago576, deshabilitar);
	habilitarObjText(document.formData.ccc576, deshabilitar);	
}

var botonAeatPulsado=false;

function presentarAEAT(){
	if (botonAeatPulsado){
		alert(MSG_PETICION_REALIZADA);
		return false;
	}else{
		loadingPresentarAEAT();
		document.formData.action="presentarAEATAltasMatriculacion.action#576";
		document.formData.submit();
	}
}

function presentarAEATMatw(){
	if (botonAeatPulsado){
		alert(MSG_PETICION_REALIZADA);
		return false;
	}else{
		loadingPresentarAEAT();
		document.formData.action="presentarAEATAltasMatriculacionMatw.action#576";
		document.formData.submit();
	}
}

function presentarAEATMatriculacion(){
	if (botonAeatPulsado){
		alert(MSG_PETICION_REALIZADA);
		return false;
	}else{
		loadingPresentarAEATMatriculacion();
		document.formData.action="presentarAEATAltaTramiteTraficoMatriculacion.action#576";
		document.formData.submit();
	}
}

function comprobarPresentarAEAT(){
	if (test="!readOnly")
		if((document.formData.importeTotal576.value=="" || document.formData.importeTotal576.value==null) && 
				(document.formData.reduccion05.value==""  || document.formData.importeTotal576.value==null) && 
				(document.formData.noSujeccion06.value=="" || document.formData.importeTotal576.value==null)){
			document.formData.bPresentarAEAT.disabled=true;
		}else{
			document.formData.bPresentarAEAT.disabled=false;
		}

}

function deshabilitarTodos(){
	document.formData.idBaseImponible576.disabled = true;
	document.formData.bCalcular.disabled = true;
	document.formData.reduccion576.disabled = true;
	document.formData.fundamento576.disabled = true;
	document.formData.tipoGravamen576.disabled = true;
	document.formData.deduccionlineal576.disabled = true;
	document.formData.aDeducir576.disabled = true;
	document.formData.numDeclaracionComp576.disabled = true;
	document.formData.bPresentarAEAT.disabled = true;
	document.formData.ejercicio.disabled = true;
	document.formData.causaHecho.disabled = true;
	document.formData.observaciones.disabled = true;
	document.formData.reduccion05.disabled = true;
	document.formData.noSujeccion06.disabled = true;
	document.formData.disposicionTipo.disabled = true;
	document.formData.disposicionFecha.disabled = true;
	document.formData.disposicionReg.disabled = true;
	document.formData.disposicionFinanciera.disabled = true;
	document.formData.exentoNRC.disabled = true;
	document.formData.nrc576.disabled = true;
	document.formData.fechaPago576.disabled = true;
	document.formData.ccc576.disabled = true;
}

function habilitarFicheroModeloAEAT(){
	document.formData.hiddenAceptaModeloAEAT.value=document.formData.aceptaModeloAEAT.value;
	if(document.formData.aceptaModeloAEAT.checked==false){
		document.formData.bImportarModeloAEAT.disabled=true;
		document.formData.fichero.disabled=true;
	}else{
		document.formData.aceptaModeloAEAT.disabled=true;
		document.formData.bImportarModeloAEAT.disabled=false;
		document.formData.fichero.disabled=false;
	}
}

function rellenarModelo05(chk576){
	if(chk576){
		document.formData.reduccion05.value = "RE1";
	}else{
		document.formData.reduccion05.value = "";
	}
}

var botonModeloAEATPulsado=false;

function importarModeloAEAT() {
	if (botonModeloAEATPulsado){
		alert(MSG_PETICION_REALIZADA);
		return false;
	}

	if(validar576(true)){

		try{
			if(document.formData.fichero.value.length > 0){
				var archivo = document.forms[0].fichero.value;
				var extension = (archivo.substring(archivo.lastIndexOf("."))).toLowerCase();
				if(extension == ".pdf"){
					botonModeloAEATPulsado=true;
					actualizarHidden();
					document.formData.nextJSP.value = '<s:property value="@com.tbsolutions.oegam.usuario.util.AccionesUsuario@ACCION_ALTA_ONLINE_576"/>';
					document.formData.pageGuardar.value = '<s:property value="@com.tbsolutions.oegam.usuario.util.AccionesUsuario@ACCION_ALTA_ONLINE_576"/>';
					document.formData.documento.value = '<s:property value="@com.tbsolutions.oegam.comun.util.Constantes@TIPO_DOCUMENTO_TRAFICO_AEAT" />';
					document.formData.action="matriculacionAltaOnLine!importacionModeloAEAT.action";
					document.formData.submit();			
				}else{
					alert(ERROR_EXTENSION_FICHERO_PDF);
				}
			}else{
				alert(ERROR_RUTA_FICHERO_VACIA);
			}
		}catch(e){	
			alert(ERROR_RUTA_FICHERO_NO_VALIDA);
		}

	}
}

function exentoIEDTM(){
	if(document.formData.numRegistro.options[document.formData.numRegistro.selectedIndex].text != ''){
		document.formData.idNombreFinanciera.value = "EXENTO PR IEDMT";
	}

	if(document.formData.numRegistro.options[document.formData.numRegistro.selectedIndex].text == ''){
		document.formData.idNombreFinanciera.value = "";		
	}

}

function exento05(){
	if(document.formData.idReduccionNoSujeccion05.options[document.formData.idReduccionNoSujeccion05.selectedIndex].value == ''
		|| document.formData.idReduccionNoSujeccion05.options[document.formData.idReduccionNoSujeccion05.selectedIndex].value == 'NS2'){
		return false;		
	}else{
		return true;
	}

}

function actualizarTitlesObservaciones(){
	var itemsOcultos = document.getElementById("observacionesOculto");
	var itemsVisibles = document.getElementById("observaciones");
	for(i=0; i<itemsOcultos.length; i++){
		itemsVisibles[i].title=itemsOcultos[i].text;				
	}
}

function loadingPresentarAEAT() {
	if(document.getElementById("idBotonGuardar576")){
		document.getElementById("idBotonGuardar576").disabled = "true";
		document.getElementById("idBotonGuardar576").style.color = "#6E6E6E";
		document.getElementById("idBotonGuardar576").value ="Procesando...";
	}
	if(document.getElementById("bPresentarAEAT")){
		document.getElementById("bPresentarAEAT").disabled = "true";
		document.getElementById("bPresentarAEAT").style.color = "#6E6E6E";
		document.getElementById("bPresentarAEAT").value ="Procesando...";
	}
	if(document.getElementById("loadingImagePresentarAEAT")){
		document.getElementById("loadingImagePresentarAEAT").style.display = "block";
	}
	return true;
}

function loadingPresentarAEATMatriculacion() {
	if(document.getElementById("bPresentarAEAT")){
		document.getElementById("bPresentarAEAT").disabled = "true";
		document.getElementById("bPresentarAEAT").style.color = "#6E6E6E";
		document.getElementById("bPresentarAEAT").value ="Procesando...";
	}
	if(document.getElementById("loadingImagePresentarAEAT")){
		document.getElementById("loadingImagePresentarAEAT").style.display = "block";
	}
	return true;
}

