
function validar(idCreditos, update, esNuevaAccion){
	eliminaEspacios(new Array("cif", "razonSocial", "via", "aliasColegiado", "numero", "letra", "escalera", "piso", "puerta", "codPostal", "telefono", 
	"correoElectronico", "numColegiadoUsuarioPpal","apellido1UsuarioPpal","apellido2UsuarioPpal","nombreUsuarioPpal", "nifNieUsuarioPpal", "correoElectronicoUsuarioPpal",
	"idColegio", "idJefatura"));
	if(	campoVacio("cif") ||
		campoVacio("razonSocial") ||
		campoVacio("via") ||
		campoVacio("numero") ||
		campoVacio("codPostal") ||
		campoVacio("correoElectronico") ||
		campoVacio("numColegiadoUsuarioPpal") || 
		campoVacio("idColegio") ||
		campoVacio("idJefatura") || 
		campoVacio("telefono")){
			alert("Debe rellenar todos los campos obligatorios.\nPor favor repase los datos.");
			return false;
	}
	
	if(
		campoVacio("aliasColegiado") ||
		campoVacio("nifNieUsuarioPpal")||
		campoVacio("apellido1UsuarioPpal")||
		campoVacio("apellido2UsuarioPpal")||
		campoVacio("nombreUsuarioPpal")||
		campoVacio("correoElectronicoUsuarioPpal")){
		alert("El colegiado debe ser valido para que se carguen los datos.");
	}
	if(!validaNIF(document.getElementById("cif"))) {
		alert("El CIF introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if(!validaNumeroPositivo(document.getElementById("numero").value)
			|| !validaNumeroEntero(document.getElementById("numero").value)){
		alert("El numero s\u00f3lo admite caracteres num\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
	if((!validaExpresionAlfanumerica(document.getElementById("letra").value)) && (document.getElementById("letra").value.length > 0)){
		alert("La letra s\u00f3lo admite caracteres alfanum\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
	if(!validaExpresionAlfanumerica(document.getElementById("escalera").value)){
		alert("La escalera s\u00f3lo admite caracteres alfanum\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
	if((!validaNumeroPositivo(document.getElementById("piso").value) || !validaNumeroEntero(document.getElementById("piso").value)) 
			&& (document.getElementById("piso").value.length > 0)){
		alert("El piso s\u00f3lo admite caracteres num\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
	if(!validaExpresionAlfanumerica(document.getElementById("puerta").value)){
		alert("La puerta s\u00f3lo admite caracteres alfanum\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
	if(document.getElementById("idProvincia").options[0].selected){
		alert("La provincia no es v\u00e1lida.\nPor favor repase los datos.");
		return false;
	}
	if(document.getElementById("idMunicipio").options[0].selected){
		alert("El municipio no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	
	if(document.getElementById("codPostal").value.length!=5){
		alert("El c\u00f3digo postal debe tener 5 caracteres.\nPor favor repase los datos.");
		return false;
	}
	if(!validaNumeroPositivo(document.getElementById("codPostal").value)
			|| !validaNumeroEntero(document.getElementById("codPostal").value)){
		alert("El c\u00f3digo postal introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if((!validaNumeroPositivo(document.getElementById("telefono").value) || !validaNumeroEntero(document.getElementById("telefono").value)) 
			&& (document.getElementById("telefono").value.length > 0)){
		alert("El tel\u00e9fono introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if(!validaEmail(document.getElementById("correoElectronico").value)){
		alert("El email del contrato introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if(document.getElementById("numColegiadoUsuarioPpal").value.length!=4){
		alert("El n\u00famero del colegiado debe tener 4 caracteres.\nPor favor repase los datos.");
		return false;
	}
	if(!validaNumeroPositivo(document.getElementById("numColegiadoUsuarioPpal").value) 
			|| !validaNumeroEntero(document.getElementById("numColegiadoUsuarioPpal").value)){
		alert("El n\u00famero del colegiado no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if(!validaNIF(document.getElementById("nifNieUsuarioPpal"))){
		alert("El nif del usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if((!validaEmail(document.getElementById("correoElectronicoUsuarioPpal").value)) && (document.getElementById("correoElectronicoUsuarioPpal").value.length > 0)){
		alert("El email de usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	
	eliminaEspacios(idCreditos);
	for(var i=0;i<idCreditos.length;i++){
		if(!update){
			if(!validaNumeroPositivo(document.getElementById(idCreditos[i]).value)
					|| !validaNumeroEntero(document.getElementById(idCreditos[i]).value)){
				alert("El n\u00famero de cr\u00e9ditos debe ser un n\u00famero positivo.\nPor favor repase los datos.");
				return false;
			}
		}else{
			if(!validaNumeroEntero(document.getElementById(idCreditos[i]).value)){
				alert("El n\u00famero de cr\u00e9ditos debe ser un valor num\u00e9rico.\nPor favor repase los datos.");
				return false;
			}
		}
	}
	if(esNuevaAccion){
		if(document.getElementById("idObservaciones").value.length > 500){
			alert("El tama\u00F1o del campo observaciones supera lo permitido.");
			return false;
		}
	}
	return true;
}

function validarUsuario(){
	
	eliminaEspacios(new Array( "nombreUsuario", "nifNieUsuario", "correoElectronicoUsuario"));
	
	if(
			campoVacio("nifNieUsuario")||
			campoVacio("nombreUsuario")||
			campoVacio("correoElectronicoUsuario")){
		alert("Debe rellenar todos los campos obligatorios.\nPor favor repase los datos.");
		return false;
		}
	
	if(!validaNIF(document.getElementById("nifNieUsuario"))){
		alert("El nif del usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if((!validaEmail(document.getElementById("correoElectronicoUsuario").value)) && (document.getElementById("correoElectronicoUsuario").value.length > 0)){
		alert("El email de usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarUsuarioSeleccionado(){
	
	eliminaEspacios(new Array( "nombreUsuarioSeleccionado", "nifNieUsuarioSeleccionado", "correoElectronicoUsuarioSeleccionado"));
	
	if(
			campoVacio("nifNieUsuarioSeleccionado")||
			campoVacio("nombreUsuarioSeleccionado")||
			campoVacio("correoElectronicoUsuarioSeleccionado")){
		alert("Debe rellenar todos los campos obligatorios.\nPor favor repase los datos.");
		return false;
		}
	
	if(!validaNIF(document.getElementById("nifNieUsuarioSeleccionado"))){
		alert("El nif del usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	if((!validaEmail(document.getElementById("correoElectronicoUsuarioSeleccionado").value)) && (document.getElementById("correoElectronicoUsuarioSeleccionado").value.length > 0)){
		alert("El email de usuario introducido no es v\u00e1lido.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarBusqueda(){
	eliminaEspacios(new Array("razonSocial", "cif")); 
	if(!campoVacio("cif")
			&& document.getElementById("cif").value.length == 9){
		if(!validaNIF(document.getElementById("cif"))){
			alert("El CIF introducido no es v\u00e1lido.\nPor favor repase los datos.");
			return false;
		}
	}
	return true;
}

function recuperarContrato(idContrato){
	document.location.href='detalleDetalleContrato.action?idContrato='+idContrato;
}

function recuperarAplicacion(codAplicacion){
	document.formData.action = 'detalleAplicacionDetalleContrato.action?codAplicacionSeleccionada='+ codAplicacion + "#AplicacionesAsociadas";
	document.formData.submit();
}

function recuperarUsuario(idUsuario){
	document.formData.action = 'detalleUsuarioDetalleContrato.action?idUsuarioSeleccionado='+ idUsuario + "#UsuariosAsociados";
	document.formData.submit();
}

function modificacionContrato(pestania){
	if(!validar(idCreditos, true, true)) {
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea modificar el contrato?")) {
		return false;
	}
	document.formData.action="guardarDetalleContrato.action#" + pestania;
	
	document.formData.submit();
	return true;
}

function modificacionCorpme(pestania){
	if(!confirm(String.fromCharCode(191)+"Realmente desea modificar el contrato?")) {
		return false;
	}
	
	if(campoVacio("usuarioCorpme") || campoVacio("passwordCorpme")) {
		alert("Debe rellenar el usuario y el password de Corpme.");
		return false;
	}
	
	document.formData.action="guardarCorpmeDetalleContrato.action#" + pestania;
	
	document.formData.submit();
	return true;
}

function volverContrato(){
	document.formData.action="inicioConsultaContrato.action";
	
	document.formData.submit();
}

function guardarPermisosContrato(){
	if(!confirm(String.fromCharCode(191)+"Quiere guardar los permisos?")) {
		return false;
	}
	document.formData.action="guardarPermisosDetalleContrato.action#AplicacionesAsociadas";
	document.formData.submit();
	return true;	
}

function guardarUsuarioContrato(){
	if(!validarUsuarioSeleccionado()) {
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Quiere guardar el usuario?")) {
		return false;
	}
	document.formData.action="guardarUsuarioDetalleContrato.action#UsuariosAsociados";
	document.formData.submit();
	return true;
}


function eliminarUsuariosContrato() {
	if(numCheckedUsuariosContrato() == 0) {
		alert("Debe seleccionar al menos un usuario.");
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Realmente desea eliminar los usuarios seleccionados?")) {
		return false;
	}
	document.formData.action="eliminarUsuariosDetalleContrato.action#UsuariosAsociados";
	document.formData.submit();
	return true;
}

function altaNuevoUsuarioContrato(){
	if(!validarUsuario()) {
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Quiere dar de alta el usuario?")) {
		return false;
	}
	document.formData.action="nuevoUsuarioDetalleContrato.action#UsuariosAsociados";
	document.formData.submit();
	return true;
}


function altaNuevoUsuarioPorContrato(){
	if(!validarUsuario()) {
		return false;
	}
	if(!confirm(String.fromCharCode(191)+"Quiere dar de alta el usuario?")) {
		return false;
	}
	document.formData.action="nuevoUsuarioDetalleContrato.action#UsuariosAsociadosContrato";
	document.formData.submit();
	return true;
}


function buscarContratos(){
	$("#formData").attr("action", "buscarConsultaContrato.action").trigger("submit");
}

function limpiarConsultaContratos(){
	$("#idCif").val("");
	$("#idEstado").val("");
	$("#idRazonSocial").val("");
	$("#idNumColegiado").val("");
	$("#idProvincia").val("");
	$("#idAlias").val("");
	$("#diaFechaCaducidadIni").val("");
	$("#mesFechaCaducidadIni").val("");
	$("#anioFechaCaducidadIni").val("");
	$("#diaFechaCaducidadFin").val("");
	$("#mesFechaCaducidadFin").val("");
	$("#anioFechaCaducidadFin").val("");
	
}

function cambiarElementosPorPaginaConsultaContratos() {
	document.location.href = 'navegarConsultaContrato.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

function abrirDetalle(idContrato){
	limpiarHiddenInput("idContrato");
	input = $("<input>").attr("type", "hidden").attr("name", "idContrato").val(idContrato);
	$('#formData').append($(input));
	$("#formData").attr("action", "detalleConsultaContrato.action").trigger("submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function mostrarLoadingConsultaContratos(boton) {
	deshabilitarBotonesConsultaContrato();
	document.getElementById("bloqueLoadingContratos").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingContratos(boton, value) {
	document.getElementById("bloqueLoadingContratos").style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaContrato();
}

function deshabilitarBotonesConsultaContrato(){
	$("#idHabilitar").prop('disabled', true);
	$("#idHabilitar").css('color', '#6E6E6E');	
	$("#idDeshabilitar").prop('disabled', true);
	$("#idDeshabilitar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');	
	$("#bConsConsulta").prop('disabled', true);
	$("#bConsConsulta").css('color', '#6E6E6E');
	$("#bLimpiar").prop('disabled', true);
	$("#bLimpiar").css('color', '#6E6E6E');	
	$("#idActualizarAlias").prop('disabled', true);
	$("#idActualizarAlias").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaContrato(){
	$("#idHabilitar").prop('disabled', false);
	$("#idHabilitar").css('color', '#000000');	
	$("#idDeshabilitar").prop('disabled', false);
	$("#idDeshabilitar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');	
	$("#bConsConsulta").prop('disabled', false);
	$("#bConsConsulta").css('color', '#000000');	
	$("#bLimpiar").prop('disabled', false);
	$("#bLimpiar").css('color', '#000000');	
	$("#idActualizarAlias").prop('disabled', false);
	$("#idActualizarAlias").css('color', '#000000');	
}


function habilitarContratos(){
	var valueBoton = $("#idHabilitar").val();
	mostrarLoadingConsultaContratos("idHabilitar");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingContratos('idHabilitar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn contrato para habilitar.");
	}
	if (confirm("\u00BFEst\u00E1 usted seguro que desea habilitar los contratos seleccionados?")){
		generarPopPupMotivo("habilitar","divEmergenteConsultaContrato",codigos,valueBoton,"idHabilitar");
	}else{
		ocultarLoadingContratos('idHabilitar',valueBoton);
	}
}

function generarPopPupMotivo(accion,dest,codigos,valueBoton,boton){
	var $dest = $("#"+dest);
	$.post("cargarPopUpMotivoConsultaContrato.action?accion="+ accion, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 800,
				height: 300,
				buttons : {
					Continuar : function() {
						if($("#idMotivo").val() == null && $("#idMotivo").val() == ""){
							return alert("Debe de introducir el motivo.");
						}else if($("#idSolicitante").val() == null && $("#idSolicitante").val() == ""){
							return alert("Debe de introducir el solicitante.");
						}
						limpiarHiddenInput("motivo");
						input = $("<input>").attr("type", "hidden").attr("name", "motivo").val($("#idMotivo").val());
						$('#formData').append($(input));
						limpiarHiddenInput("solicitante");
						input = $("<input>").attr("type", "hidden").attr("name", "solicitante").val($("#idSolicitante").val());
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						var action = "";
						if(accion == "habilitar"){
							action = "habilitarConsultaContrato.action";
						}else if(accion == "deshabilitar"){
							action = "deshabilitarConsultaContrato.action";
						}else if(accion = "eliminar"){
							action = "eliminarConsultaContrato.action";
						}
						$("#formData").attr("action", action).trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingContratos(boton,valueBoton);
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function generarPopPupActualizarAlias(accion,dest,codigos,valueBoton,boton){
	var $dest = $("#"+dest);
	$.post("cargarPopUpActualizarAliasConsultaContrato.action?accion="+ accion, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 350,
				height: 350,
				buttons : {
					Actualizar : function() {
						var aliasActualizado = $("#idAliasPopPup").val();
						if(aliasActualizado == null && aliasActualizado == ""){
							return alert("Debe de introducir el alias para ser actualizado.");
						}
						$("#divEmergenteConsultaContrato").dialog("close");
						limpiarHiddenInput("aliasActualizado");
						input = $("<input>").attr("type", "hidden").attr("name", "alias").val(aliasActualizado);
						$('#formData').append($(input));
						limpiarHiddenInput("fechaCaducidadDia");
						input = $("<input>").attr("type", "hidden").attr("name", "fecha.diaInicio").val($("#diaFechaCaducidadIniPopPup").val());
						$('#formData').append($(input));
						limpiarHiddenInput("fechaCaducidadMes");
						input = $("<input>").attr("type", "hidden").attr("name", "fecha.mesInicio").val($("#mesFechaCaducidadIniPopPup").val());
						$('#formData').append($(input));
						limpiarHiddenInput("fechaCaducidadAnio");
						input = $("<input>").attr("type", "hidden").attr("name", "fecha.anioInicio").val($("#anioFechaCaducidadIniPopPup").val());
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
					
						var action = "";
						if(accion == "actualizar"){
							action = "actualizarAliasConsultaContrato.action";
						}
						$("#formData").attr("action", action).trigger("submit");
					},
					Cerrar : function() {
						ocultarLoadingContratos(boton,valueBoton);
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}


function getChecksConsultaMarcados(){
	var codigos = "";
	$("input[name='listaChecks']:checked").each(function() {
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

function mostrarBloqueActualizados(){
	$("#bloqueFallidos").hide();
	if($("#bloqueActualizados").is(":visible")){
		$("#bloqueActualizados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueActualizados").show();	
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidos(){
	$("#bloqueActualizados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();	
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
		
	}
}

function abrirEvolucionContrato(idContrato){
	if(idContrato == null || idContrato == ""){
		return alert("No se puede obtener la evolución del contrato seleccionado.");
	}
	var $dest = $("#divEmergenteConsultaContratoEvolucion");
	var url = "abrirEvolucionConsultaContrato.action?idContrato=" + idContrato;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950,height: 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}



function abrirEvolucionUsuario(nif){
	if(nif == null || nif == ""){
		return alert("No se puede obtener la evolución del usuario seleccionado.");
	}
	var $dest = $("#divEmergenteConsultaUsuarioEvolucion");
	var url = "abrirEvolucionUsuarioDetalleContrato.action?nif=" + nif;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right', at: 'right' },
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 950,height: 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function cambiarElementosPorPaginaEvolucionUsuario(){
	var $dest = $("#displayTagEvolucionUsuarioDiv");
	$.ajax({
		url:"navegarEvolucionUsuario.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolUsuario").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones.');
	    }
	});
}




function cambiarElementosPorPaginaEvolucionContratos(){
	var $dest = $("#displayTagEvolucionContratoDiv");
	$.ajax({
		url:"navegarEvolucionContrato.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolContrato").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las evoluciones.');
	    }
	});
}

function deshabilitarContratos(){
	var valueBoton = $("#idDeshabilitar").val();
	mostrarLoadingConsultaContratos("idDeshabilitar");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingContratos('idDeshabilitar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn contrato para deshabilitar.");
	}
	if (confirm("\u00BFEst\u00E1 usted seguro que desea deshabilitar los contratos seleccionados?")){
		generarPopPupMotivo("deshabilitar","divEmergenteConsultaContrato",codigos,valueBoton,"idDeshabilitar");
	}else{
		ocultarLoadingContratos('idDeshabilitar',valueBoton);
	}
}

function eliminarContratos(){
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaContratos("idEliminar");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingContratos('idEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn contrato para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 usted seguro que desea eliminar los contratos seleccionados?")){
		generarPopPupMotivo("eliminar","divEmergenteConsultaContrato",codigos,valueBoton,"idEliminar");
	}else{
		ocultarLoadingContratos('idEliminar',valueBoton);
	}
}

function actualizarAliasContratos(){
	var valueBoton = $("#idActualizarAlias").val();
	mostrarLoadingConsultaContratos("idActualizarAlias");
	var codigos = getChecksConsultaMarcados();
	if(codigos == ""){
		ocultarLoadingContratos('idActualizarAlias',valueBoton);
		return alert("Debe seleccionar alg\u00FAn contrato para actualizar el alias.");
	}
	if (confirm("\u00BFEst\u00E1 usted seguro que desea actualizar el alias de los contratos seleccionados?")){
		generarPopPupActualizarAlias("actualizar","divEmergenteConsultaContrato",codigos,valueBoton,"idActualizarAlias");
	}else{
		ocultarLoadingContratos('idActualizarAlias',valueBoton);
	}
}

function resizeMeFechaPopUp(){
	var $cal = $('iframe:eq(1)', parent.document);
	var position = $("#diaFechaCaducidadIniPopPup").position();
	$cal.css({
	    left:  position.left + "px",
	    top: (position.top+20) + "px"
	});
}

function buscarTipoTramitePorAplicacion(){
	var codigoAplicacion =$('#codigoAplicacion').val();
	if (codigoAplicacion == ""){
		document.getElementById("tipoTramite").options.length = 0;
		document.getElementById("tipoTramite").selectedIndex=0;
		document.getElementById("tipoTramite").options[0] = new Option("TODOS", "");	
	}else{
		$("#tipoTramite").attr('style', 'display:block');
		var url="buscarTipoTramitePorAplicacionContratosAjax.action?codigoAplicacion=" + codigoAplicacion;
		//Hace la llamada a ajax
		if (window.XMLHttpRequest) { // Non-IE browsers
			reqTipoTramites = new XMLHttpRequest();
			reqTipoTramites.onreadystatechange = rellenarTipoTramitesBuscados;
			reqTipoTramites.open("POST", url, true);
			reqTipoTramites.send(null);
		} else if (window.ActiveXObject) { // IE      
			reqTipoTramites = new ActiveXObject("Microsoft.XMLHTTP");
			if (reqTipoTramites) {
				reqTipoTramites.onreadystatechange = rellenarTipoTramitesBuscados;
				reqTipoTramites.open("POST", url, true);
				reqTipoTramites.send();
			}
		}
	}
}

//Callback function
function rellenarTipoTramitesBuscados() {
	document.getElementById("tipoTramite").options.length = 0;
	if (reqTipoTramites.readyState == 4) { // Complete
		if (reqTipoTramites.status == 200) { // OK response
			var textToSplitTipoTramites = reqTipoTramites.responseText;
			
			//Split the document
			var returnElementTipoTramites=textToSplitTipoTramites.split("||");
			
      		document.getElementById("tipoTramite").options[0] = new Option("TODOS", "");		
			//Process each of the elements
			for ( var i = 0 ; i < returnElementTipoTramites.length ; i ++ ) {
				value = returnElementTipoTramites[i].split(";");
				if (value != '') {
					document.getElementById("tipoTramite").options[i+1] = new Option(value[0], value[1]);
				}
			}	
		}
	}
}

function validaCorreoElectronico(email) {
    var re  = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (email.length == 0){
		return true;
	}
    if (!re.test(email)) {
    	alert("El email introducido no es v\u00e1lido.\nPor favor repase los datos.");
        return false;
    }
    return true;
}

function eliminarCorreoContratoTramite(idCorreoContratoTramite){

	if(!confirm(String.fromCharCode(191)+"Realmente desea eliminar el correo asociado al trámite?")) {
		return false;
	}
	document.formData.action="eliminarCorreoContratoTramiteDetalleContrato.action?idCorreoContratoTramite="+ idCorreoContratoTramite + "#DatosdelColegiado";
	document.formData.submit();
	return true;
}

function modificarCorreoContratoTramite(idCorreoContratoTramite) {
	document.formData.action="modificarCorreoContratoTramiteDetalleContrato.action?idCorreoContratoTramite="+ idCorreoContratoTramite + "#DatosdelColegiado";
	document.formData.submit();
}


function mostrarCamposEnvioCorreoImpresion() {
	var tipoTramite =$('#tipoTramite').val();
	if (tipoTramite == "T1" || tipoTramite == "T3" || tipoTramite == "T7" || tipoTramite == "T8") {
		$("#divCamposEnvioCorreoImpresion").attr('style', 'display:block');
	} else {
		$("#divCamposEnvioCorreoImpresion").attr('style', 'display:none');
		$("#tipoImpresion").val("");
		$("#enviarCorreo").val("");
	}
}



