function cambiarElementosPorPaginaConsultaGrupoUsuarios() {
	document.location.href = 'navegarGrupoUsuarios.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function mostrarDiv(){
	$('#altaGrupo').show("slow");
}

function guardarModificarGrupo(){
	if (document.getElementById('idGrupo').value == '' || document.getElementById('idGrupo').value == null){
		alert('Es obligatorio rellenar el campo id grupo');
		return false;
	}
	
	if(document.getElementById('idGrupo').value.lenght > 3){
		alert('El campo id grupo no puede contener más de 3 caracteres.');
		return false;
	}
	var formulario = document.getElementById('formData');
	formulario.action = 'guardarModificarGrupoGrupoUsuarios.action';
	formulario.submit();
}

function ocultarDiv(){
	$('#altaGrupo').hide("slow");
	show("slow");
}

function eliminarGrupo(idGrupo){
	if(confirm("Va a eliminar el grupo seleccionado. \u00bfContinuar?")){
		var formulario = document.getElementById('formData');
		formulario.action = 'eliminarGrupoGrupoUsuarios.action?idGrupo='+idGrupo;
		formulario.submit();
	}
}

function modificarGrupo(idGrupo, correoElectronico, descGrupo, tipo){
	document.getElementById("idGrupo").value=idGrupo;
	document.getElementById("idCorreoElectronico").value=correoElectronico;
	document.getElementById("idDescGrupo").value=descGrupo;
	document.getElementById("idTipo").value=tipo;
	mostrarDiv();
}