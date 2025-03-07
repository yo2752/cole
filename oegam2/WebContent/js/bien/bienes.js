function cambiarTipoBien(){

	document.formData.consultaBienes.value = true;
	
	if(document.formData.tipoBien.value == "URBANO"){
		var divRustico = document.getElementById('divRustico');
		var divUrbano = document.getElementById('divUrbano');
		divRustico.style.display="none";
		divUrbano.style.display="inline";
		if (document.formData.listarUrbano.value == ""){
			document.formData.action="listaConsultaBienesUrbanos.action";
			document.formData.submit();
		}
	}
	else if (document.formData.tipoBien.value == "RUSTICO"){
		var divRustico = document.getElementById('divRustico');
		var divUrbano = document.getElementById('divUrbano');
		divRustico.style.display="inline";
    	divUrbano.style.display="none";
    	if (document.formData.listarRustico.value == ""){
			document.formData.action="listaConsultaBienesRusticos.action";
			document.formData.submit();
		}
	}
	
}

function buscarBienUrbano(){
	document.formData.consultaBienes.value = "true";
	document.formData.action="buscarConsultaBienesUrbanos.action";
	document.formData.submit();
}

function buscarBienRustico(){
	document.formData.consultaBienes.value = "true";
	document.formData.action="buscarConsultaBienesRusticos.action";
	document.formData.submit();
}

function modificarBien(idBien,tipoBien){
	if (tipoBien == "RUSTICO"){
		modificarBienRustico(idBien);		
	}else if (tipoBien == "URBANO"){
		modificarBienUrbano(idBien);
	}
}

function modificarBienRustico(idBien){
	document.formData.idBienModificar.value = idBien;
	document.formData.action="modificarBienRusticoBienesRegistro.action#Bienes";
	document.formData.submit();
}

function modificarBienUrbano(idBien){
	document.formData.idBienModificar.value = idBien;
	document.formData.action="modificarBienUrbanoBienesRegistro.action#Bienes";
	document.formData.submit();
}


function cambiarElementosPorPaginaUrbano(){

	document.formData.consultaBienes.value = true;
	
	document.location.href='navegarConsultaBienesUrbanos.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function municipiosBienesUrb(){
	
	document.formData.action="cambioProvinciaBienesUrbanos.action";
	document.formData.submit();
}

function obtenerMunicipiosBienUrbano(){
	selectProvinciaBienUrbano = document.getElementById('idProvinciaBienUrbano');
	if(selectProvinciaBienUrbano.selectedIndex==0){
		document.getElementById('idMunicipioBienUrbano').length = 0;
		document.getElementById('idMunicipioBienUrbano').options[0] = new Option("Seleccione Municipio", "");
		return;
	}
	  opcionBienUrbano = selectProvinciaBienUrbano.options[selectProvinciaBienUrbano.selectedIndex].value;
	  url="modificarMunicipiosBienUrbanoBienesUrbanos.action?opcionBienUrbano="+opcionBienUrbano;

	if (window.XMLHttpRequest){ 
		reqBienUrbano = new XMLHttpRequest();
		reqBienUrbano.onreadystatechange = rellenarMunicipiosBienUrbano;
		reqBienUrbano.open("POST", url, true);
		
		reqBienUrbano.send(null);
	} else if (window.ActiveXObject) {       
		reqBienUrbano = new ActiveXObject("Microsoft.XMLHTTP");
		if (reqBienUrbano) {
			reqBienUrbano.onreadystatechange = rellenarMunicipiosBienUrbano;
			reqBienUrbano.open("POST", url, true);
			reqBienUrbano.send();
		}
	}
}

function rellenarMunicipiosBienUrbano(){
	
	document.getElementById('idMunicipioBienUrbano').options.length = 0;
	
	if (reqBienUrbano.readyState == 4) { 
		if (reqBienUrbano.status == 200) { 
			textToSplitBienUrbano = reqBienUrbano.responseText;
			
			returnElementBienUrbano=textToSplitBienUrbano.split("||");
			
      		document.getElementById('idMunicipioBienUrbano').options[0] = new Option("TODOS", "");		
			var municipioSeleccionadoBienUrbano = document.getElementById('municipioSeleccionadoBienUrbano').value;
			for ( var i=0; i<returnElementBienUrbano.length; i++ ){
				 value = returnElementBienUrbano[i].split(";");
				document.getElementById('idMunicipioBienUrbano').options[i+1] = new Option(value[0], value[1]);
				
				if(selectMunicipioBienUrbano.options[i+1].value == municipioSeleccionadoBienUrbano){
					selectMunicipioBienUrbano.options[i+1].selected = true;
				}
			}	
		}
	}
}

function recargarComboMunicipiosBienUrbanos(){

	 selectProvinciaBienUrbano = document.getElementById('idProvinciaBienUrbano');
	 selectMunicipioBienUrbano = document.getElementById('idMunicipioBienUrbano');
	if(selectProvinciaBienUrbano.selectedIndex > 0){
		obtenerMunicipiosBienUrbano();
	}
}

function seleccionarMunicipioBienUrbano(){
	document.getElementById('municipioSeleccionadoBienUrbano').value = document.getElementById('idMunicipioBienUrbano').value; 
}

function cambiarMunicipiosBienUrbano(){
	document.getElementById('municipioSeleccionadoBienUrbano').value = "-1";
	obtenerMunicipiosBienUrbano();
}


function cambiarElementosPorPaginaRustico(){

	document.formData.consultaBienes.value = true;
	
	document.location.href='navegarConsultaBienesRusticos.action?resultadosPorPagina=' + document.formData.idResultadosPorPagina.value;
}

function municipiosBienesRus(){
	
	document.formData.action="cambioProvinciaBienesRusticos.action";
	document.formData.submit();
}


function seleccionadoTipoGanaderia(){

	document.getElementById('usoRusticoGanaderia').disabled = false;
	document.getElementById('usoRusticoCultivo').disabled = true;
	document.getElementById('usoRusticoOtros').disabled = true;
	document.formData.elements["bienRustico.sistemaExplotacion.sistemaExplotacion"].disabled = true;
}

function seleccionadoTipoCultivo(){
	
	document.getElementById('usoRusticoCultivo').disabled = false;
	document.getElementById('usoRusticoGanaderia').disabled = true;
	document.getElementById('usoRusticoOtros').disabled = true;
	document.formData.elements["bienRustico.sistemaExplotacion.sistemaExplotacion"].disabled = false;	
}

function seleccionadoTipoOtros(){
	
	document.getElementById('usoRusticoOtros').disabled = false;
	document.getElementById('usoRusticoGanaderia').disabled = true;
	document.getElementById('usoRusticoCultivo').disabled = true;
	document.formData.elements["bienRustico.sistemaExplotacion.sistemaExplotacion"].disabled = false;
}
	
function cambioUsoRustico(){

	if (document.getElementById('usoRusticoGanaderia').value != ""
		&& document.getElementById('usoRusticoGanaderia').value != null){
		seleccionadoTipoGanaderia();
	}else if (document.getElementById('usoRusticoCultivo').value!=""
		&& document.getElementById('usoRusticoCultivo').value != null){			
		seleccionadoTipoCultivo();
	}else if (document.getElementById('usoRusticoOtros').value!=""
		&& document.getElementById('usoRusticoOtros').value != null){
		seleccionadoTipoOtros();
	}else {

		
		document.getElementById('usoRusticoGanaderia').disabled = false;
		document.getElementById('usoRusticoCultivo').disabled = false;
		document.getElementById('usoRusticoOtros').disabled = false;
		document.getElementById('usoRusticoGanaderia').value="";
		document.getElementById('usoRusticoCultivo').value="";
		document.getElementById('usoRusticoOtros').value="";
		document.formData.elements["bienRustico.sistemaExplotacion.sistemaExplotacion"].disabled = false;
	}	

}


function obtenerMunicipiosBienRustico(){
	selectProvinciaBienRustico = document.getElementById('idProvinciaBienRustico');
	if(selectProvinciaBienRustico.selectedIndex==0){
		document.getElementById('idMunicipioBienRustico').length = 0;
		document.getElementById('idMunicipioBienRustico').options[0] = new Option("Seleccione Municipio", "");
		return;
	}
	  opcionBienRustico = selectProvinciaBienRustico.options[selectProvinciaBienRustico.selectedIndex].value;
	  url="modificarMunicipiosBienRusticoBienesRusticos.action?opcionBienRustico="+opcionBienRustico;

	if (window.XMLHttpRequest){ 
		reqBienRustico = new XMLHttpRequest();
		reqBienRustico.onreadystatechange = rellenarMunicipiosBienRustico;
		reqBienRustico.open("POST", url, true);
		
		reqBienRustico.send(null);
	} else if (window.ActiveXObject) {       
		reqBienRustico = new ActiveXObject("Microsoft.XMLHTTP");
		if (reqBienRustico) {
			reqBienRustico.onreadystatechange = rellenarMunicipiosBienRustico;
			reqBienRustico.open("POST", url, true);
			reqBienRustico.send();
		}
	}
}

function rellenarMunicipiosBienRustico(){
	
	document.getElementById('idMunicipioBienRustico').options.length = 0;
	
	if (reqBienRustico.readyState == 4) { 
		if (reqBienRustico.status == 200) { 
			textToSplitBienRustico = reqBienRustico.responseText;
			
			returnElementBienRustico=textToSplitBienRustico.split("||");
			
      		document.getElementById('idMunicipioBienRustico').options[0] = new Option("TODOS", "");		
			var municipioSeleccionadoBienRustico = document.getElementById('municipioSeleccionadoBienRustico').value;
			for ( var i=0; i<returnElementBienRustico.length; i++ ){
				 value = returnElementBienRustico[i].split(";");
				document.getElementById('idMunicipioBienRustico').options[i+1] = new Option(value[0], value[1]);
				
				if(selectMunicipioBienRustico.options[i+1].value == municipioSeleccionadoBienRustico){
					selectMunicipioBienRustico.options[i+1].selected = true;
				}
			}	
		}
	}
}

function recargarComboMunicipiosBienRusticos(){

	 selectProvinciaBienRustico = document.getElementById('idProvinciaBienRustico');
	 selectMunicipioBienRustico = document.getElementById('idMunicipioBienRustico');
	
	if(selectProvinciaBienRustico.selectedIndex > 0){
		obtenerMunicipiosBienRustico();
	}
}

function seleccionarMunicipioBienRustico(){
	document.getElementById('municipioSeleccionadoBienRustico').value = document.getElementById('idMunicipioBienRustico').value; 
}

function cambiarMunicipiosBienRustico(){
	document.getElementById('municipioSeleccionadoBienRustico').value = "-1";
	obtenerMunicipiosBienRustico();
}

function modificarBienRustico(idBien){
	document.formData.idBienModificar.value = idBien;
	document.formData.action="modificarBienRusticoBienesRegistro.action#Bienes";
	document.formData.submit();
}

function modificarBienUrbano(idBien){
	document.formData.idBienModificar.value = idBien;
	document.formData.action="modificarBienUrbanoBienesRegistro.action#Bienes";
	document.formData.submit();
}