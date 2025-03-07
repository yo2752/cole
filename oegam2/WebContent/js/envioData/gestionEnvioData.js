	
function actualizarFechaAnterior(proceso,cola){
	if (confirm("Se va a actualizar el proceso "+ proceso +" de la tabla de Envio Data con la fecha del último día Laborable. ¿Está usted seguro?")){
		deshabilitarBotonesTabla();
		limpiarHiddenInput("proceso");
		input = $("<input>").attr("type", "hidden").attr("name", "proceso").val(proceso);
		$('#formData').append($(input));
		limpiarHiddenInput("cola");
		input = $("<input>").attr("type", "hidden").attr("name", "cola").val(cola);
		$('#formData').append($(input));
		limpiarHiddenInput("tipoActualizacion");
		input = $("<input>").attr("type", "hidden").attr("name", "tipoActualizacion").val("0");
		$('#formData').append($(input));
		$('#formData').attr("action","actualizarFechaEnvioData.action");
		$('#formData').submit();
	}
}

function actualizarFechaHoy(proceso,cola){
	if (confirm("Se va a actualizar el proceso "+ proceso +" de la tabla de Envio Data con la fecha de hoy. ¿Está usted seguro?")){
		deshabilitarBotonesTabla();
		limpiarHiddenInput("proceso");
		input = $("<input>").attr("type", "hidden").attr("name", "proceso").val(proceso);
		$('#formData').append($(input));
		limpiarHiddenInput("cola");
		input = $("<input>").attr("type", "hidden").attr("name", "cola").val(cola);
		$('#formData').append($(input));
		limpiarHiddenInput("tipoActualizacion");
		input = $("<input>").attr("type", "hidden").attr("name", "tipoActualizacion").val("1");
		$('#formData').append($(input));
		$('#formData').attr("action","actualizarFechaEnvioData.action");
		$('#formData').submit();
	}
}

function deshabilitarBotonesTabla(){
	$("input[id *= 'btnFechaAnterior']").prop("disabled",true);
	$("input[id *= 'btnFechaAnterior']").css("color","#BDBDBD");
	$("input[id *= 'btnFechaHoy']").prop("disabled",true);
	$("input[id *= 'btnFechaHoy']").css("color","#BDBDBD");
}