

function limpiarFormularioEmbarcaciones(){
	limpiarTitular();
	limpiarRepresentante();
	$("#nifTitular").val("");
	$("#nifRepresentante").val("");
	$("#representante").val("");
	$("#hin").val("");
	$("#metrosEslora").val("");
	$("#formData").attr("action", "limpiarCamposAltaFormulario.action#embarcacionTab").trigger("submit");
}

function limpiarTitular(){
	$("#sexoTitular").val("-1");
	$("#apellido1").val("");
	$("#apellido2").val("");
	$("#nombre").val("");
	$("#idTipoPersonaTitular").val("-1");
	
}

function limpiarRepresentante(){
	$("#sexoRepresentante").val("-1");
	$("#apellido1Representante").val("");
	$("#apellido2Representante").val("");
	$("#nombreRepresentante").val("");
	$("#idTipoPersonaRepresentante").val("-1");
}


function buscarPersona(){
	var nif = $('#nifTitular').val();
	$('#idTipoPersonaTitular').prop("disabled", false);
	$('#idTipoPersonaRepresentante').prop("disabled", false);
	if(nif != ""){
		$("#formData").attr("action", "buscarPersonaAltaFormulario.action?nif="+nif+"#titularTab").trigger("submit");
	}else{
		alert("Para poder realizar la busqueda del titular debe rellenar el NIF/CIF.");
	}

}

function guardarDatos(){
	$("#formData").attr("action", "guardarDatosAltaFormulario.action#resumenTab").trigger("submit");
}

function buscarRepresentante(){
	var nif = $('#nifRepresentante').val();
	$('#idTipoPersonaRepresentante').prop("disabled", false);
	$('#idTipoPersonaTitular').prop("disabled", false);
	if(nif != ""){
		$("#formData").attr("action", "buscarRepresentanteAltaFormulario.action?nifRepresentante="+nif+"#representanteTab").trigger("submit");
	}else{
		alert("Para poder realizar la busqueda del representante debe rellenar el NIF/CIF.");
	}

}

function generarPDF() {
	$('#idTipoPersonaTitular').prop("disabled", false);
	$('#idTipoPersonaRepresentante').prop("disabled", false);
	$("#formData").attr("action", "generarPdfAltaFormulario.action#resumenTab").trigger("submit");
}

function generarTXT() {
	$('#idTipoPersonaTitular').prop("disabled", false);
	$('#idTipoPersonaRepresentante').prop("disabled", false);
	$("#formData").attr("action", "generarTxtAltaFormulario.action#resumenTab").trigger("submit");
}

function cambiarComa(valor){
	valor.value = valor.value.replace(',','.');
}

function soloNumeros(e){
	var key = window.Event ? e.which : e.keyCode;
	return (key >= 48 && key <= 57) || key == 46 || key == 44  || (key==8) 
}
