
function limpiarFormularioActualizacionMF(){
	if (document.getElementById("tipo")) {
		document.getElementById("tipo").value = "";
	}
	
	if (document.getElementById("activo")) {
		document.getElementById("activo").value = "";
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
	if (document.getElementById("diaInicio")) {
		document.getElementById("diaInicio").value = "";
	}
	if (document.getElementById("mesInicio")) {
		document.getElementById("mesInicio").value = "";
	}
	if (document.getElementById("anioInicio")) {
		document.getElementById("anioInicio").value = "";
	}

	if (document.getElementById("diaFinSancion")) {
		document.getElementById("diaFinSancion").value = "";
	}
	if (document.getElementById("mesFinSancion")) {
		document.getElementById("mesFinSancion").value = "";
	}
	if (document.getElementById("anioFinSancion")) {
		document.getElementById("anioFinSancion").value = "";
	}
	if (document.getElementById("diaInicioSancion")) {
		document.getElementById("diaInicioSancion").value = "";
	}
	if (document.getElementById("mesInicioSancion")) {
		document.getElementById("mesInicioSancion").value = "";
	}
	if (document.getElementById("anioInicioSancion")) {
		document.getElementById("anioInicioSancion").value = "";
	}
	
}

function consultaActualizacionMF(){
	doPost('formData', 'buscarConsultaMF.action');
}

function cambiarElementosPorPagina() {
	document.location.href = 'navegarConsultaMF.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}


function realizarActualizacion() {
	var numero = getNumChecksSeleccionados('listaChecksActualizacionMF');
	var codigo  = getChecksMarcados('listaChecksActualizacionMF');
	if(numero>1){
		return alert('Solo se puede actualizar un fichero simultaneamente');
		
	}else{
		$('#formData').attr("action","generarPeticionConsultaMF.action?idActualizacion="+codigo);
		$('#formData').submit();			
	}
	
 
}

function descargarFicheros(){
	
	var numero = getNumChecksSeleccionados('listaChecksActualizacionMF');
	var codigo  = getChecksMarcados('listaChecksActualizacionMF');

	$('#formData').attr("action","descargarFicherosConsultaMF.action?codigos="+codigo);
	$('#formData').submit();			

	
}


function getChecksMarcados(name){
	var codigos = "";
	$("input[name='"+name+"']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	return codigos;
}

function getNumChecksSeleccionados(name){
	var numMarcados = 0;
	$("input[name='"+name+"']:checked").each(function() {
		if(this.checked){
			numMarcados++;
		}
	});
	return numMarcados;
}


