var ventanaNotario;

function consultarNotario(){
	document.formData.action = "buscarConsultaNotarioRM.action";
	document.formData.submit();
}

function consultarNotarioPropiedad(){
	document.formData.action = "buscarNotarioReg.action";
	document.formData.submit();
}


function deshabilitar(){
	$("#bGuardar").prop('disabled', true);
	$("#bGuardar").css('color', '#6E6E6E');	
	$("#bLimpiar").prop('disabled', true);
	$("#bLimpiar").css('color', '#6E6E6E');	
	$("#bVolver").prop('disabled', true);
	$("#bVolver").css('color', '#6E6E6E');

	
}
function limpiarNotario(){
	document.getElementById("idNotarioNombre").value = "";
	document.getElementById("idNotarioApellidos").value = "";
	document.getElementById("idCodNotario").value = "";
	document.getElementById("idCodNotario").readOnly = false;
	document.getElementById("idCodNotaria").value = "";
}

function cambiarElementosPorPaginaNotario() {
	
	document.location.href = 'navegarConsultaNotarioRM.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}


function redireccionRemesas(){
	var esRedireccion = document.getElementById("esSeleccionadoNotario");
	var codBien = document.getElementById("codigo");
	if(esRedireccion.value == "true"){
		window.opener.invokeSeleccionNotario(codBien.value);
	}
}

function altaNotario(){
	document.location.href='altaConsultaNotarioRM.action';
}

function guardarNotario(){
	
	if (campoVacio("idNotarioNombre")){
		alert("Debe introducir un nombre.");
		return false;
	}
	if (campoVacio("idNotarioApellidos")){
		alert("Debe introducir apellidos.");
		return false;
	}
	if (campoVacio("idCodNotario")){
		alert("Debe introducir un código de notario.");
		return false;
	}
	deshabilitar();
	document.formData.action = "guardarConsultaNotarioRM.action";
	document.formData.submit();
	
	limpiarNotario();
}

function editarNotario(codNotario){
	document.location.href='editarConsultaNotarioRM.action?codNotario='+codNotario;
}

function volver(){	
	document.location.href='inicioConsultaNotarioRM.action';
}


//VALIDA CAMPO NO VACÍO
function campoVacio(idCampo){
	var text = document.getElementById(idCampo).value;
	text = trim(text);
	if (text.length == 0){
		return true;
	}else{
		document.getElementById(idCampo).value = text;
		return false;
	}
}

//ELIMINA ESPACIOS EN BLANCO
function trim(text){
    //delete initial spaces
    var counter = 0, lastPosition;
    while(text.indexOf(" ", counter)==counter){
        counter++;
    }
    text = text.substr(counter);

    //delete ending spaces
    lastPosition = text.length - 1;
    while(text.lastIndexOf(" ", lastPosition) == lastPosition){
        lastPosition--;
    }
    text = text.substr(0, lastPosition + 1);

    return text;
}


