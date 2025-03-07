
function habilitarFicheroITVM(){
	document.formData.hiddenAceptaIVTM.value=document.formData.aceptaIVTM.value;
	if(document.formData.aceptaIVTM.checked==false){
		document.formData.bImportarIVTM.disabled=true;
		document.formData.fichero.disabled=true;
	}else{
		document.formData.aceptaIVTM.disabled=true;
		document.formData.bImportarIVTM.disabled=false;
		document.formData.fichero.disabled=false;
	}
}