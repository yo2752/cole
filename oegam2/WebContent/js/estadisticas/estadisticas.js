
function limpiarFormGeneralEstadisticas() {

	if (document.getElementById("idEstadoTramite")) {
		document.getElementById("idEstadoTramite").value = -1;
	}

	if (document.getElementById("idTipoCreacion")) {
		document.getElementById("idTipoCreacion").value = -1;
	}

	if (document.getElementById("idTipoTramite")) {
		document.getElementById("idTipoTramite").value = -1;
	}

	if (document.getElementById("idNumcolegiado")) {
		document.getElementById("idNumcolegiado").value = "";
	}

	if (document.getElementById("idTasa")) {
		document.getElementById("idTasa").value = -1;
	}

	if (document.getElementById("idNumBastidor")) {
		document.getElementById("idNumBastidor").value = -1;
	}

	if (document.getElementById("idMatricula")) {
		document.getElementById("idMatricula").value = -1;
	}

	if (document.getElementById("idTipoVehiculo")) {
		document.getElementById("idTipoVehiculo").value = -1;
	}

	if (document.getElementById("idProvincia")) {
		document.getElementById("idProvincia").value = -1;
	}

	if (document.getElementById("idJefatura")) {
		document.getElementById("idJefatura").value = -1;
		document.getElementById("idJefatura").length = 0;
		document.getElementById("idJefatura").options[0] = new Option(
				"Cualquier Jefatura", "", true, true);
		document.getElementById("jefaturaSeleccionado").value = "";

	}

	if (document.getElementById("codigoMarca")) {
		document.getElementById("codigoMarca").value = "";
		document.getElementById("idMarcaVehiculo").value = "";
	}

	if (document.getElementById("idAgrupacion")) {
		document.getElementById("idAgrupacion").value = -1;
	}

	borrarFechasEstadisticas();

}

function limpiarFormJustificantesEstadisticas() {

	if (document.getElementById("idNumcolegiado")) {
		document.getElementById("idNumcolegiado").value = "";
	}

	borrarFechasEstadisticas();

}

function limpiarFormEvolucionTasa() {

	if (document.getElementById("idNumExpediente")) {
		document.getElementById("idNumExpediente").value = "";
	}

	if (document.getElementById("idCodigoTasa")) {
		document.getElementById("idCodigoTasa").value = "";
	}

}

function limpiarFormUsuariosIPEstadisticas() {

	if (document.getElementById("idNumcolegiado")) {
		document.getElementById("idNumcolegiado").value = "";
	}
	if (document.getElementById("idFrontal").value != "-1") {
		document.getElementById("idFrontal").value = "-1";
	}
	borrarFechasEstadisticas();
}

function limpiarFormVehiculosEstadisticas() {
	if (document.getElementById("idNumcolegiado")) {
		document.getElementById("idNumcolegiado").value = "";
	}
	
	if (document.getElementById("idTipoTramite")) {
		document.getElementById("idTipoTramite").value = -1;
	}
	
	if (document.getElementById("idNumMatricula")) {
		document.getElementById("idNumMatricula").value = "";
	}
	
	if (document.getElementById("idLetraMatricula")) {
		document.getElementById("idLetraMatricula").value = "";
	}

	borrarFechasEstadisticas();
	borrarFechasPrimeraMatriculaEstadisticas();

}

function limpiarFormFechaPrimeraMatriculaEstadisticas() {
	if (document.getElementById("idNumMatricula")) {
		document.getElementById("idNumMatricula").value = "";
	}
	
	if (document.getElementById("idLetraMatricula")) {
		document.getElementById("idLetraMatricula").value = "";
	}
}

function borrarFechasEstadisticas() {

	if (document.getElementById("diaMatrIni")) {
		document.getElementById("diaMatrIni").value = "";
	}
	if (document.getElementById("mesMatrIni")) {
		document.getElementById("mesMatrIni").value = "";
	}
	if (document.getElementById("anioMatrIni")) {
		document.getElementById("anioMatrIni").value = "";
	}

	if (document.getElementById("diaMatrFin")) {
		document.getElementById("diaMatrFin").value = "";
	}
	if (document.getElementById("mesMatrFin")) {
		document.getElementById("mesMatrFin").value = "";
	}
	if (document.getElementById("anioMatrFin")) {
		document.getElementById("anioMatrFin").value = "";
	}
}

function borrarFechasPrimeraMatriculaEstadisticas() {
	if (document.getElementById("diaPriMatrIni")) {
		document.getElementById("diaPriMatrIni").value = "";
	}
	if (document.getElementById("mesPriMatrIni")) {
		document.getElementById("mesPriMatrIni").value = "";
	}
	if (document.getElementById("anioPriMatrIni")) {
		document.getElementById("anioPriMatrIni").value = "";
	}

	if (document.getElementById("diaPriMatrFin")) {
		document.getElementById("diaPriMatrFin").value = "";
	}
	if (document.getElementById("mesPriMatrFin")) {
		document.getElementById("mesPriMatrFin").value = "";
	}
	if (document.getElementById("anioPriMatrFin")) {
		document.getElementById("anioPriMatrFin").value = "";
	}
}

function limpiarFormMensajeErrorServicio() {
	if (document.getElementById("dia")) {
		document.getElementById("dia").value = "";
	}
	if (document.getElementById("mes")) {
		document.getElementById("mes").value = "";
	}
	if (document.getElementById("anio")) {
		document.getElementById("anio").value = "";
	}
}

function obtenerExcel() {
	document.formData.action = "obtenerExcelMensajeErrorServicio.action";
	document.formData.submit();
}

function generarExcel() {
	document.formData.action = "generarExcelMensajeErrorServicio.action";
	document.formData.submit();
}

function generarInformeGeneralEstadisticas() {
	document.formData.action = "generarListadoGeneralEstadisticas.action";
	document.formData.submit();
}

function generarInformeEvolucionTasa() {
	document.formData.action = "generarListadoEvolucioTasaExpEstadisticas.action";
	document.formData.submit();
}

function generarInformeJustificantesEstadisticas() {
	document.formData.action = "generarListadoJustificantesEstadisticas.action";
	document.formData.submit();
}

function generarInformeUsuariosIPEstadisticas() {
	document.formData.action = "generarListadoUsuariosIPEstadisticas.action";
	document.formData.submit();
}

function generarCalcularMatriculaEstadisticas() {
	document.formData.action = "generarCalcularMatriculaEstadisticas.action";
	document.formData.submit();
}

function generarInformeVehiculosEstadisticas() {
	document.formData.action = "generarListadoVehiculosEstadisticas.action";
	document.formData.submit();
}

function generarInformeUsuariosActivosIPEstadisticas() {
	document.formData.action = "generarListadoUsuariosActivosIPEstadisticas.action";
	document.formData.submit(); 
}

function generarInformeUsuariosActivosAgrupadosIPEstadisticas() {
	document.formData.action = "generarListadoUsuariosActivosAgrupadosIPEstadisticas.action";
	document.formData.submit();
}

function generarInformeUsuariosActivosRepetidos() {
	document.formData.action = "generarListadoUsuariosRepetidosEstadisticas.action";
	document.formData.submit();
}

function cambiarElementosPorPaginaConsultaUsuariosIP() {

	document.location.href = 'navegarEstadisticas.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaJustificantes() {

	document.location.href = 'navegarJustificantesEstadisticas.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultageneralEstadisticas() {

	document.location.href = 'navegarGeneralEstadisticas.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaDatosSensibles() {

	document.location.href = 'navegarDatosSensibles.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaDatosSensiblesBastidor() {

	document.location.href = 'navegarBastidorDatosSensibles.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaVehiculosEstadisticas() {
	document.location.href = 'navegarVehiculosEstadisticas.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaEvolucionTasa(){
	document.location.href = 'navegarTasaEstadisticas.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
}

function comprobarPasswordListadoGeneral() {
	document.formData.action = "comprobarPasswordGeneralEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordEvolucionTasa() {
	document.formData.action = "comprobarPasswordEvolucionTasaExpedienteEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordListadoUsuarios() {
	document.formData.action = "comprobarPasswordUsuariosEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordListadoJustificantes() {
	document.formData.action = "comprobarPasswordJustificantesEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordListadoVehiculosEstadisticas() {
	document.formData.action = "comprobarPasswordVehiculosEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordListadoMatriculasEstadisticas() {
	document.formData.action = "comprobarPasswordMatriculasEstadisticas.action";
	document.formData.submit();
}

function comprobarPasswordListadoMensajes() {
	document.formData.action = "comprobarPasswordMensajeErrorServicio.action";
	document.formData.submit();
}

function deshabilitarListadoGeneral() {
	document.getElementById("bGenerarInforme").style.display = "none";
	document.getElementById("bLimpiar").style.display = "none";
	document.getElementById("bExcel").style.display = "none";
}

function deshabilitarListadoUsuarios() {
	document.getElementById("bListarUsuarios").style.display = "none";
	document.getElementById("bLimpiar").style.display = "none";
	document.getElementById("bListarUsuariosOnline").style.display = "none";
}

function deshabilitarListadoJustificantes() {
	document.getElementById("bGenerarInforme").style.display = "none";
	document.getElementById("bLimpiar").style.display = "none";
	document.getElementById("bExcelJustificantes").style.display = "none";
}

function deshabilitarListadoVehiculos() {
	document.getElementById("bGenerarInforme").style.display = "none";
	document.getElementById("bLimpiar").style.display = "none";
	document.getElementById("bExcel").style.display = "none";
}

function imprimirEstadisticas() {
	document.formData.action = "imprimirExcelEstadisticas.action";
	document.formData.submit();
}

function imprimirVehiculosEstadisticas() {
	document.formData.action = "imprimirExcelVehiculosEstadisticas.action";
	document.formData.submit();
}

function imprimirEstadisticasJustificantes() {
	document.formData.action = "imprimirExcelJustificantesEstadisticas.action";
	document.formData.submit();
}

//--------------------------------------Nuevas pantallas//Borrar lo anterior llegado el momento-------------------------------------------------------

function limpiarFormulario(idFormulario){
	var formulario = document.getElementById(idFormulario);
    var elementos = formulario.getElementsByTagName("*");
    for(i = 0; i < elementos.length; i++){
    	 if((elementos[i].tagName=='SELECT' && elementos[i] != document.getElementById('idResultadosPorPagina') && elementos[i] != document.getElementById('idResultadosPorPaginaOnline'))
        		|| (elementos[i].tagName=='INPUT' && elementos[i].type!='hidden' &&
        				  elementos[i].type!='submit' && elementos[i].type!='reset' && 
        				  elementos[i].type!='button')){
                elementos[i].value = "";
          }
    }
}

function trimCamposInputs(){
	 $('input[type="text"]').blur(function(){
	     this.value = $.trim(this.value);
	 });
}

function iniciarProcesando(idBoton,idImagen) {
	document.getElementById(idBoton).disabled = "true";
	document.getElementById(idBoton).style.color = "#6E6E6E";
	document.getElementById(idBoton).value = "Procesando...";
	document.getElementById(idImagen).style.display = "block";
	$('input[type=button]').attr('disabled', true);
}

function mostrarLoadingConsultarEst(boton) {
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultarEst").style.display = "block";
	boton.value = "Procesando...";
}


function ocultarLoadingConsultarEst(boton, value) {
	document.getElementById("bloqueLoadingConsultarEst").style.display = "none";
	boton.value = value;
	desbloqueaBotonesConsulta();
}

//Desbloquea los botones de la pantalla de consulta de tramites
function desbloqueaBotonesConsulta() {

	var inputs = document.getElementsByTagName('input');
	for ( var i = 0; i < inputs.length; i++) {

		var input = inputs[i];

		if (input.type == 'button') {
			input.disabled = false;
			input.style.color = '#000000';
		}
	}
}

function comprobarPasswordListadosUsuarios() {
	document.formData.action = "comprobarPasswordListadoUsuarios.action";
	document.formData.submit();
}


function listarUsuariosPorIPListadoUsuarios() {
	var idNumColegiado = document.getElementById("idNumColegiado").value;
		
	if (isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
					
	// //Para que muestre el loading
	iniciarProcesando('bListarUsuarios','loadingImage');
	document.formData.action = "listarUsuariosPorIPListadoUsuarios.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bListarUsuarios'), "Listar Usuarios");
}


function listarUsuariosOnlineListadoUsuarios() {
	var idNumColegiado = document.getElementById("numColegiado").value;
		
	if (isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
					
	// //Para que muestre el loading
	iniciarProcesando('bListarUsuariosOnline','loadingImage');
	document.formData.action = "listarUsuariosOnlineListadoUsuarios.action#usuariosOnline";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bListarUsuariosOnline'), "Usuarios Online");
}

function cerrarSesionUsuariosOnlineListadoUsuarios() {
	var idNumColegiado = document.getElementById("numColegiado").value;
		
	if (isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
	
	if (confirm("    ¿Está seguro que desea cerrar la sesión de todos los usuarios conectados a la plataforma? \nEsta acción no se aplicará a las sesiones de los usuarios administradores iniciadas el día de hoy.")) {
		iniciarProcesando('bCerrarUsuariosOnline','loadingImage');
		document.formData.action = "cerrarSesionUsuariosOnlineListadoUsuarios.action#usuariosOnline";
		document.formData.submit();
		ocultarLoadingConsultarEst(document.getElementById('bCerrarUsuariosOnline'), "Cerrar sesión usuarios");
	}
}

function cerrarSesionUsuariosOnlineListadoUsuarios() {
	var idNumColegiado = document.getElementById("numColegiado").value;
		
	if (isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
	
	if (confirm("¿Está seguro que desea cerrar la sesión de todos los usuarios conectados a la plataforma? \nEsta acción no se aplicará a su sesión actual.")) {
		iniciarProcesando('bCerrarUsuariosOnline','loadingImage');
		document.formData.action = "cerrarSesionUsuariosOnlineListadoUsuarios.action#usuariosOnline";
		document.formData.submit();
		ocultarLoadingConsultarEst(document.getElementById('bCerrarUsuariosOnline'), "Cerrar sesión usuarios");
	}
}

function cerrarSesionUsuarioOnlineListadoUsuarios(idSesionCerrar) {
	
	if (confirm("¿Está seguro que desea cerrar la sesión con id " + idSesionCerrar + "? \nEsta acción no se aplicará a su sesión actual.")) {
		$('input[type=button]').attr('disabled', true);
		document.formData.action = "cerrarSesionUsuarioOnlineListadoUsuarios.action?idSesionCerrar="+idSesionCerrar+"#usuariosOnline";
		document.formData.submit();
		desbloqueaBotonesConsulta();
	}
}


function listarUsuariosOnlineAgrupadosListadoUsuarios(){
	var $dest = $("#divListadoUsuariosOnlineAgrupados");
	$.ajax({
		url:"listarUsuariosOnlineAgrupadosListadoUsuarios.action#usuariosOnlineAgrupados",
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
		},
		error : function(xhr, status) {
			alert('Ha sucedido un error a la hora de obtener los usuarios online agrupados por frontal.');
		}
	});
}

function listarUsuariosOnlineRepetidosListadoUsuarios(){
	var $dest = $("#divListadoUsuariosOnlineRepetidos");
	$.ajax({
		url:"listarUsuariosOnlineRepetidosListadoUsuarios.action#usuariosOnlineRepetidos",
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
		},
		error : function(xhr, status) {
			alert('Ha sucedido un error a la hora de obtener los usuarios online repetidos.');
		}
	});
}

//-----------Listado Justificantes no Ultimados ---------------------------------
function comprobarPasswordListadoJustificantesNoUltimados() {
	document.formData.action = "comprobarPasswordListadoJustificantesNoUltimados.action";
	document.formData.submit();
}

function buscarListadoJustificantesNoUltimados() {
	var idNumColegiado = document.getElementById("idNumColegiado").value;
		
	if (isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
					
	// //Para que muestre el loading
	iniciarProcesando('bBuscar','loadingImage');
	document.formData.action = "buscarListadoJustificantesNoUltimados.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bBuscar'), "Buscar");
}

function generarExcelListadoJustificantesNoUltimados() {
	document.formData.action = "generarExcelListadoJustificantesNoUltimados.action";
	document.formData.submit();
}

//-----------Listado Matrículas ------------------------------------
function comprobarPasswordListadoMatriculas() {
	document.formData.action = "comprobarPasswordListadoMatriculas.action";
	document.formData.submit();
}

function calcularFechaPrimeraMatriculacion() {
	var idNumMatricula = document.getElementById("idNumMatricula").value;
	var idLetraMatricula = document.getElementById("idLetraMatricula").value;
	
	if (isNaN(idNumMatricula) || idNumMatricula.length != 4) {
		alert("El campo n\u00FAmero de matrícula debe ser rellenado con cuatro caracteres.");
		return false;
	}
	
	if (idLetraMatricula.length != 3) {
		alert("El campo letra de matrícula debe ser rellenado con tres caracteres.");
		return false;
	}
					
	// //Para que muestre el loading
	iniciarProcesando('bCalcularMatricula','loadingImage');
	document.formData.action = "calcularFechaPrimeraMatriculacionListadoMatriculas.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bCalcularMatricula'), "Calcular Fecha");
}

//-----------------Listado Tasas por Expediente --------------------------------

function comprobarPasswordListadoTasasPorExpediente() {
	document.formData.action = "comprobarPasswordListadoTasasPorExpediente.action";
	document.formData.submit();
}

function buscarListadoTasasPorExpediente() {
					
	// //Para que muestre el loading
	iniciarProcesando('bBuscar','loadingImage');
	document.formData.action = "buscarListadoTasasPorExpediente.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bBuscar'), "Listar Tasas");
}

//---------------------Listado de Vehículos --------------------------------------------

function comprobarPasswordListadoVehiculos() {
	document.formData.action = "comprobarPasswordListadoVehiculos.action";
	document.formData.submit();
}

function buscarListadoVehiculos() {
	var idNumColegiado = document.getElementById("idNumColegiado").value;
		
	if (idNumColegiado != null && isNaN(idNumColegiado)) {
		alert("El campo n\u00FAmero de Colegiado debe contener solo n\u00FAmeros");
		return false;
	}		
					
	// //Para que muestre el loading
	iniciarProcesando('bGenerarInforme','loadingImage');
	document.formData.action = "buscarListadoVehiculos.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bGenerarInforme'), "Generar Informe");
}

function generarExcelListadoVehiculos() {
	document.formData.action = "generarExcelListadoVehiculos.action";
	document.formData.submit();
}

//---------------------Listado General y Personalizado --------------------------------------------

function comprobarPasswordListadoGeneralYPersonalizado() {
	document.formData.action = "comprobarPasswordListadoGeneralYPersonalizado.action";
	document.formData.submit();
}

function buscarListadoGeneralYPersonalizado() {
					
	// //Para que muestre el loading
	iniciarProcesando('bGenerarInforme','loadingImage');
	document.formData.action = "buscarListadoGeneralYPersonalizado.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bGenerarInforme'), "Generar Informe");
}

function generarExcelListadoGeneralYPersonalizado() {
	document.formData.action = "generarExcelListadoGeneralYPersonalizado.action";
	document.formData.submit();
}

//Recibe el elemento select de las provincias, el id del select de jefaturas a cargar y el id oculto que guarda la jefatura seleccionada
//Es la función que hay que llamar desde el onchange del select de la provincia
function cargarListaJefaturasListadoGeneralYPersonalizado(select_provincia,id_select_jefatura,id_jefaturaSeleccionado){
	document.getElementById(id_jefaturaSeleccionado).value = "";
	obtenerJefaturasPorProvincia(select_provincia,document.getElementById(id_select_jefatura),id_jefaturaSeleccionado);
}

//---------------------Listado Mensajes Error Servicio --------------------------------------------

function comprobarPasswordListadoMensajesErrorServicio() {
	document.formData.action = "comprobarPasswordListadoMensajesErrorServicio.action";
	document.formData.submit();
}

function buscarListadoMensajesErrorServicio() {
					
	// //Para que muestre el loading
	iniciarProcesando('bBuscar','loadingImage');
	document.formData.action = "buscarListadoMensajesErrorServicio.action";
	document.formData.submit();
	ocultarLoadingConsultarEst(document.getElementById('bBuscar'), "Buscar");
}

function generarExcelListadoMensajesErrorServicio() {
	document.formData.action = "generarExcelListadoMensajesErrorServicio.action";
	document.formData.submit();
}
