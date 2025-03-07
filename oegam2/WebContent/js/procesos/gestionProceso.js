	

function parar(ordenProceso) {
	limpiarHiddenInput("ordenProceso");
	input = $("<input>").attr("type", "hidden").attr("name", "ordenProceso").val(ordenProceso);
	$('#formData').append($(input));
	$('#formData').attr("action","pararProcesoGestionProceso.action");
	$('#formData').submit();
}
	
function activar(ordenProceso) {
	limpiarHiddenInput("ordenProceso");
	input = $("<input>").attr("type", "hidden").attr("name", "ordenProceso").val(ordenProceso);
	$('#formData').append($(input));
	$('#formData').attr("action","activarProcesoGestionProceso.action");
	$('#formData').submit();
}


function activarExcel(ordenProceso,esMensajeHoras){
	if(esMensajeHoras){
		var mensaje = "Son las "+rellenaCerosIzqVar(d.getHours().toString(),2)+":"+rellenaCerosIzqVar(d.getMinutes().toString(),2)+". Si hubiera un fallo en la BBDD o en la validación activar este proceso \n\ra esta hora del día, puede significar un segundo envío. ¿Estás seguro de querer hacerlo?";
		if(!confirm(mensaje)) return false;
	}
	limpiarHiddenInput("ordenProceso");
	input = $("<input>").attr("type", "hidden").attr("name", "ordenProceso").val(ordenProceso);
	$('#formData').append($(input));
	$('#formData').attr("action","activarProcesoGestionProceso.action");
	$('#formData').submit();
	
}

function modificar(orden) {
	if (confirm("\u00BFEst\u00E1 seguro de que desea modificar los datos del proceso?")){
		limpiarHiddenInput("ordenProceso");
		input = $("<input>").attr("type", "hidden").attr("name", "ordenProceso").val(orden);
		$('#formData').append($(input));
		limpiarHiddenInput("intentosMax");
		input = $("<input>").attr("type", "hidden").attr("name", "intentosMax").val($("#intentosMax_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("hilosColas");
		input = $("<input>").attr("type", "hidden").attr("name", "hilosColas").val($("#hilosColas_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("horaInicio");
		input = $("<input>").attr("type", "hidden").attr("name", "horaInicio").val($("#horaInicio_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("horaFin");
		input = $("<input>").attr("type", "hidden").attr("name", "horaFin").val($("#horaFin_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("tiempoCorrecto");
		input = $("<input>").attr("type", "hidden").attr("name", "tiempoCorrecto").val($("#tiempoCorrecto_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("tiempoRecuperable");
		input = $("<input>").attr("type", "hidden").attr("name", "tiempoRecuperable").val($("#tiempoRecuperable_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("tiempoNoRecuperable");
		input = $("<input>").attr("type", "hidden").attr("name", "tiempoNoRecuperable").val($("#tiempoNoRecuperable_"+orden).val());
		$('#formData').append($(input));
		limpiarHiddenInput("tiempoSinDatos");
		input = $("<input>").attr("type", "hidden").attr("name", "tiempoSinDatos").val($("#tiempoSinDatos_"+orden).val());
		$('#formData').append($(input));
		$('#formData').attr("action","modificarGestionProceso.action");
		$('#formData').submit();
	}
}

function actualizar() {
	$('#formData').attr("action","actualizarGestionProceso.action");
	$('#formData').submit();
}

function arrancarPatron(){
	if($("#idPatron").val() != ""){
		$('#formData').attr("action","arrancarPatronGestionProceso.action");
		$('#formData').submit();
	} else {
		alert("Debe seleccionar algún patrón para realizar esta acción");
	}
}

// Para los procesos de un patrón
function pararPatron(){
	if($("#idPatron").val() != ""){
		$('#formData').attr("action","pararPatronGestionProceso.action");
		$('#formData').submit();
	} else {
		alert("Debe seleccionar algún patrón para realizar esta acción");
	}
	
}

function cargarProcesosPatron(){
	$("#formData").attr("action", "listaPorPatronGestionProceso.action");
	$('#formData').submit();
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}