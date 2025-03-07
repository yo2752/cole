function buscarConsultaInterviniente() {
	if ($("#idNif").val() == "" && $("#idNumColegiado").val() == "" && $("#idNumExpediente").val() == "") {
		alert("Debe rellenar uno de los tres campos");
		return false;
	}
	$("#formData").attr("action", "buscarConsultaInterviniente.action").trigger("submit");
}

function limpiarConsultaInterviniente(){
	$("#idNif").val("");
	$("#idNumColegiado").val("");
	$("#idNumExpediente").val("");
}

function cambiarElementosPorPaginaConsulta() {
	document.location.href = 'navegarConsultaInterviniente.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

function recuperarInterviniente(nif, numColegiado, numExpediente, tipoInterviniente){
	limpiarHiddenInput("nif");
	input = $("<input>").attr("type", "hidden").attr("name", "nif").val(nif);
	$('#formData').append($(input));
	limpiarHiddenInput("numColegiado");
	input = $("<input>").attr("type", "hidden").attr("name", "numColegiado").val(numColegiado);
	$('#formData').append($(input));
	limpiarHiddenInput("numExpediente");
	input = $("<input>").attr("type", "hidden").attr("name", "numExpediente").val(numExpediente);
	$('#formData').append($(input));
	limpiarHiddenInput("tipoInterviniente");
	input = $("<input>").attr("type", "hidden").attr("name", "tipoInterviniente").val(tipoInterviniente);
	$('#formData').append($(input));
	$("#formData").attr("action", "detalleDetalleInterviniente.action").trigger("submit");
}
function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function mostrarLoadingConsultaInterv(boton) {
	deshabilitarBotonesConsultaInterv();
	document.getElementById("bloqueLoadingIntervinientes").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingIntervinientes(boton, value) {
	document.getElementById("bloqueLoadingIntervinientes").style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaInterv();
}

function deshabilitarBotonesConsultaInterv(){
	$("#idBuscar").prop('disabled', true);
	$("#idBuscar").css('color', '#6E6E6E');	
	$("#idLimpiar").prop('disabled', true);
	$("#idLimpiar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');	
}

function habilitarBotonesConsultaInterv(){
	$("#idBuscar").prop('disabled', false);
	$("#idBuscar").css('color', '#000000');	
	$("#idLimpiar").prop('disabled', false);
	$("#idLimpiar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');	
}

function eliminarInterviniente(){
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaInterv("idEliminar");
	var codigos = "";
	var contCodigos = 0;
	var nombreCheckInterv = null;
	$("input[name='listaChecks']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
			contCodigos++;
			nombreCheckInterv = this.id;
		}
	});
	
	if(codigos == ""){
		ocultarLoadingIntervinientes('idEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn interviniente para eliminar.");
	}else if(contCodigos > 1){
		ocultarLoadingIntervinientes('idEliminar',valueBoton);
		return alert("Solo se podra eliminar los intervinientes de forma individual.");
	}
	var datosInterv = codigos.split("_");
	if(datosInterv.length != 4){
		ocultarLoadingIntervinientes('idEliminar',valueBoton);
		return alert("Faltan datos obligatorios para poder eliminar el interviniente.");
	}
	var descTipoInterv = $("#idDescTipoInterv"+nombreCheckInterv).val();
	if (confirm("\u00BFEst\u00E1 usted seguro que desea eliminar el interviniente con nif: " + datosInterv[0] +", del colegiado: " + datosInterv[1] +
		", del tramite: " + datosInterv[2] + " y del tipo : " + descTipoInterv)){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaInterviniente.action").trigger("submit");
	}else{
		ocultarLoadingIntervinientes('idEliminar',valueBoton);
	}
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