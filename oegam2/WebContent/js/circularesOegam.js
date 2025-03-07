function guardaCircularOegam(){
	$("#formData").attr("action", "guardarAltaCircular.action").trigger("submit");
}

function volverCircularOegam(){
	$("#formData").attr("action", "buscarConsultaCircular.action");
	$('#formData').submit();
}

function consultaCircular(){
	doPost('formData', 'buscarConsultaCircular.action');
}

function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " input[type=hidden]").attr("value", "");
	$("#" + idDiv + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
}

function cambiarElementosPorPaginaCircular(){
	var $dest = $("#displayTagDivCircular");
	$.ajax({
		url:"navegarConsultaCircular.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaCircular").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar las consulta de circulares.');
	    }
	});
}

function finalizarEstadoCircular(){
	var valueBoton = $("#idFinalizarCircular").val();
	mostrarLoading("idFinalizarCircular");
	var codigos = "";
	$("input[name='listaChecksCircular']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += "-" + this.value;
			}
		}
	});
	if(codigos == ""){
		ocultarLoading('idFinalizarCircular',valueBoton);
		return alert("Debe seleccionar alguna circular para cambiar su estado.");
	}
	var $dest = $("#divEmergenteConsultaCircular");
	$.post("abrirPopUpConsultaCircular.action", function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 350,
				buttons : {
					CambiarEstado : function() {
						var estadoNuevo = $("#idEstadoNuevo").val();
						if(estadoNuevo == ""){
							alert("Seleccione un estado");
							return false;
						}
						$("#divEmergenteConsultaCircular").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadosConsultaCircular.action").trigger("submit");
					},
					Cerrar : function() {
						ocultarLoading('idFinalizarCircular',valueBoton);
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

function mostrarLoading(boton){
	bloqueaBotonesCircular();
	document.getElementById("bloqueLoadingConsultaCircular").style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoading(boton, value) {
	document.getElementById("bloqueLoadingConsultaCircular").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesCircular();
}

function bloqueaBotonesCircular() {	
	$('#idFinalizarCircular').prop('disabled', true);
	$('#idFinalizarCircular').css('color', '#6E6E6E');	
	$('#idRevertirCircular').prop('disabled', true);
	$('#idRevertirCircular').css('color', '#6E6E6E');

}

function desbloqueaBotonesCircular() {	
	$('#idFinalizarCircular').prop('disabled', false);
	$('#idFinalizarCircular').css('color', '#000000');	
	$('#idRevertirCircular').prop('disabled', false);
	$('#idRevertirCircular').css('color', '#000000');	

}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
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
function revertirCircular(){
	var valueBoton = $("#idRevertirCircular").val();
	mostrarLoading('idRevertirCircular');
	var codigos = "";
	$("input[name='listaChecksCircular']:checked").each(function() {
		if(this.checked){
			if(codigos==""){
				codigos += this.value;
			}else{
				codigos += ";" + this.value;
			}
		}
	});
	
	if(codigos == ""){
		ocultarLoading('idRevertirCircular',valueBoton);
		return alert("Debe seleccionar para poder revertir la circular.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea revertir las circulares seleccionadas?")){
		limpiarHiddenInput("codSeleccionados");
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "revertirConsultaCircular.action").trigger("submit");
	}
	else{
		ocultarLoading('idRevertirDstv',valueBoton);
	}
}


function cambiarCheckRepe(repeticiones,dias,fecha,dia,mes,anio,calendario){
	if($("#"+repeticiones).is(':checked')){
		$("#"+dias).prop("disabled", false);
		$("#"+calendario).hide();
		$("#"+dia).val("");
		$("#"+dia).prop("disabled", true);
		$("#"+mes).val("");
		$("#"+mes).prop("disabled", true);
		$("#"+anio).val("");
		$("#"+anio).prop("disabled", true);
	}
	$("#"+fecha).prop("checked", false);
}
function cambiarCheckFecha(repeticiones,dias,fecha,dia,mes,anio,calendario){
	 if($("#"+fecha).is(':checked')){
		$("#"+calendario).show();
		$("#"+fecha).prop("disabled", false);
		$("#"+dia).prop("disabled", false);
		$("#"+mes).prop("disabled", false);
		$("#"+anio).prop("disabled", false);
		$("#"+dias).val("");
		$("#"+dias).prop("disabled", true);
	}
	$("#"+repeticiones).prop("checked", false);
}

