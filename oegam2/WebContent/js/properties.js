function buscarProperties(){
	$("#formData").attr("action", "buscarProperties").trigger("submit");	
	loadingProperties();
	return true;
}

function refrescarProperties(){
	$("#formData").attr("action", "refrescarProperties").trigger("submit");	
	loadingProperties();
	return true;
}

function loadingProperties() {
	document.getElementById("bloqueLoadingProperties").style.display = "block";
	$('input[type=button]').val("Procesando...");
	$('input[type=button]').attr('disabled', true);
}

function guardarBBDD(idProperty, numFila, nombreProperty) {
	if (nombreProperty == "nuevo.gestorAccesos") {
		if (!confirm("Para actualizar esta propiedad es necesario volver a acceder a la aplicación. \u00BFDesea continuar?")) {
			return true;
		}
	}
	limpiarHiddenInput("valorNuevo");
	input = $("<input>").attr("type", "hidden").attr("name",
			"valorNuevo").val($('#valor_' + numFila).val());
	$('#formData').append($(input));
	limpiarHiddenInput("idProperty");
	input = $("<input>").attr("type", "hidden").attr("name",
			"idProperty").val(idProperty);
	$('#formData').append($(input));
	limpiarHiddenInput("nombreProperty");
	input = $("<input>").attr("type", "hidden").attr("name",
	"nombreProperty").val(nombreProperty);
	$('#formData').append($(input));
	$("#formData").attr("action", "guardarBBDDProperties").trigger(
			"submit");
}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " input[type=hidden]").attr("value", "");
}