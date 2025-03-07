function cambiarElementosPorPaginaConsultaTasa(){
	var $dest = $("#displayTagDivConsultaTasa");
	$.ajax({
		url:"navegarConsultaTasas.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaConsTasa").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error: function() {
			alert('Ha sucedido un error a la hora de cargar la consulta de tasas.');
		}
	});
}

function cambiarElementosPorPaginaEvolucionConsultaTasas(){
	var $dest = $("#displayTagEvolucionConsultaTasaDiv");
	$.ajax({
		url:"navegarEvolucionTs.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaEvolConsTasa").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error: function() {
			alert('Ha sucedido un error a la hora de cargar las evolucion de consulta de tasa');
		}
	});
	
}

function volverConsultaTasas(){
	deshabilitarBotonesConsultaTasas();
	$("#formData").attr("action", "inicioConsultaTasas").trigger("submit");
}

function marcarTodas(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { //Controlamos el caso en que solo hay un elemento...
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}

function consultaTasaNBuscar(){
	$("#formData").attr("action", "buscarConsultaTasas.action").trigger("submit");
}

function mostrarBotonTasa(){
	$("#idDesasignar").show();
}

function ocultarBotonTasa(){
	$("#idDesasignar").hide();
}

function obtenerPestaniaSeleccionada() {
	var toc = document.getElementById("toc");
	if (toc) {
		var lis = toc.getElementsByTagName("li");
		for (var j = 0; j < lis.length; j++) {
			var li = lis[j];
			if (li.className == "current") {
				return li.id;
			}
		}
	}
}

function eliminarBloque(){
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idEliminar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder eliminarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("idCodigoTasa");
	input = $("<input>").attr("type", "hidden").attr("name", "idCodigoTasa").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarBloqueConsultaTasas.action").trigger("submit");
}
function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}
function desasignarBloque(){
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idDesasignar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idDesasignar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idDesasignar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder eliminarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("idCodigoTasa");
	input = $("<input>").attr("type", "hidden").attr("name", "idCodigoTasa").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "desasignarBloqueConsultaTasas.action").trigger("submit");
}

function exportar(){
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idExportar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idExportar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idExportar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder exportarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("idCodigoTasa");
	input = $("<input>").attr("type", "hidden").attr("name", "idCodigoTasa").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "exportarConsultaTasas.action").trigger("submit");
}

function cargarFicheroTasas() {
	limpiarHiddenInput("ficheroOK");
	input = $("<input>").attr("type", "hidden").attr("name", "ficheroOK").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "descargarFicheroConsultaTasas.action").trigger("submit");

	/*if (document.getElementById("ficheroOK").value=="true") {
		document.forms[0].action="descargarFichero${action}";
		document.forms[0].submit();
	}
	document.getElementById("ficheroOK").value="false";*/
}

function generarCertifTasasBloque(boton) {
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idGenerar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idGenerar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idGenerar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder eliminarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("idCodigoTasa");
	input = $("<input>").attr("type", "hidden").attr("name", "idCodigoTasa").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "generarCertificadoBloqueConsultaTasas.action").trigger("submit");
}

function eliminar(){
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idEliminar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder eliminarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("codigoTasa");
	input = $("<input>").attr("type", "hidden").attr("name", "codigoTasa").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarGestionTasa.action").trigger("submit");
}

function desasignar(){
	deshabilitarBotonesConsultaTasas();
	var valueBoton = $("#idDesasignar").val();
	mostrarLoadingConsultaTasa("bloqueLoadingConsultaTasas","idDesasignar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConsultaTasa('idDesasignar',valueBoton,"bloqueLoadingConsultaTasas");
		alert("Debe seleccionar alguna tasa para poder eliminarla.");
		habilitarBotonesConsultaTasas();
		return false;
	}
	limpiarHiddenInput("numExpedientes");
	input = $("<input>").attr("type", "hidden").attr("name", "numExpedientes").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "desasignarGestionTasa.action").trigger("submit");
}

//Para mostrar el loading en los botones de consultar.
function mostrarLoadingConsultaTasa(bloqueLoading,boton) {
	deshabilitarBotonesConsultaTasas();
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConsultaTasa(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
	habilitarBotonesConsultaTasas();
}

function abrirEvolucionAlta(codigoTasa){
	return abrirEvolucion($("#"+codigoTasa).val());
}

function abrirEvolucionConsultaTasas(codigoTasa){
	if(codigoTasa == null || codigoTasa == ""){
		return alert("Debe seleccionar alguna consulta para poder obtener su evolucion.");
	}
	var $dest = $("#divEmergenteConsultaTasasEvolucion");
	var url = "cargarEvolucionTs.action?codigoTasa=" + codigoTasa;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				position: { my: 'right', at: 'right' },
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 950,height: 500,
				buttons: {
					Cerrar: function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			// Viene <html>, así que no es un modal
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function descargarCertificadoTasas() {
	$('#formData').attr("action","descargarCertificadoConsultaTasas.action");
	$('#formData').submit();
}

function getChecksConsultaMarcados(name){
	var codigos = "";
	$("input[name='"+name+"']:checked").each(function() {
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

function deshabilitarBotonesConsultaTasas(){
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#idDesasignar").prop('disabled', true);
	$("#idDesasignar").css('color', '#6E6E6E');
	$("#idExportar").prop('disabled', true);
	$("#idExportar").css('color', '#6E6E6E');
	$("#idGenerar").prop('disabled', true);
	$("#idGenerar").css('color', '#6E6E6E');
	$("#idGenerarTasas").prop('disabled', true);
	$("#idGenerarTasas").css('color', '#6E6E6E');
}

function habilitarBotonesConsultaTasas(){
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
	$("#idDesasignar").prop('disabled', false);
	$("#idDesasignar").css('color', '#000000');
	$("#idExportar").prop('disabled', false);
	$("#idExportar").css('color', '#000000');
	$("#idGenerar").prop('disabled', false);
	$("#idGenerar").css('color', '#000000');
	$("#idGenerarTasas").prop('disabled', false);
	$("#idGenerarTasas").css('color', '#000000');
}

function limpiarConsultaTasas(){
	$("#TipoTasa").val("");
	$("#CodigoTasa").val("");
	$("#idAsignada").val("");
	$("#idTipoTramite").val("");
	//$("#idTasa").val("");
	$("#NumeroExpediente").val("");
	$("#ReferenciaPropia").val("");
	$("#diaAltaIni").val("");
	$("#mesAltaIni").val("");
	$("#anioAltaIni").val("");
	$("#diaAltaFin").val("");
	$("#mesAltaFin").val("");
	$("#anioAltaFin").val("");
	$("#diaAsignacionIni").val("");
	$("#mesAsignacionIni").val("");
	$("#anioAsignacionIni").val("");
	$("#diaAsignacionFin").val("");
	$("#mesAsignacionFin").val("");
	$("#anioAsignacionFin").val("");
	$("#diaFinVigencia").val("");
	$("#mesFinVigencia").val("");
	$("#anioFinVigencia").val("");
	$("#idFormato").val("");
	if($("#numColegiado")){
		$("#numColegiado").val("");
	}
	if($("#esImportColegio")){
		$("#esImportColegio").val("");
	}
}
function mostrarBloqueValidadosTasa(){
	$("#bloqueFallidos").hide();
	if($("#bloqueValidados").is(":visible")){
		$("#bloqueValidados").hide();
		$("#despValidado").attr("src","img/plus.gif");
		$("#despValidado").attr("alt","Mostrar");
	}else{
		$("#bloqueValidados").show();
		$("#despValidado").attr("src","img/minus.gif");
		$("#despValidado").attr("alt","Ocultar");
	}
}

function mostrarBloqueFallidosTasa(){
	$("#bloqueValidados").hide();
	if($("#bloqueFallidos").is(":visible")){
		$("#bloqueFallidos").hide();
		$("#despFallidos").attr("src","img/plus.gif");
		$("#despFallidos").attr("alt","Mostrar");
	}else{
		$("#bloqueFallidos").show();
		$("#despFallidos").attr("src","img/minus.gif");
		$("#despFallidos").attr("alt","Ocultar");
	}

//------repasar estas funciones

function cargarListaCodigosTasa(select_tipoTasa,id_select_codigoTasa,id_codigoTasaSeleccionado){
	document.getElementById(id_codigoTasaSeleccionado).value = "-1";
	obtenerCodigosTasaPorTipoTasa(select_tipoTasa,document.getElementById(id_select_codigoTasa),id_codigoTasaSeleccionado);
}

//Recarga el combo cuando se recarga la pagina
function recargarComboTasas(id_select_tipoTasa,id_select_codigoTasa,id_codigoTasaSeleccionado){
	selectTipoTasa = document.getElementById(id_select_tipoTasa);
	selectCodigoTasa = document.getElementById(id_select_codigoTasa);

	if(selectTipoTasa != null && selectTipoTasa.selectedIndex > 0){
		obtenerCodigosTasaPorTipoTasa(selectTipoTasa,selectCodigoTasa,id_codigoTasaSeleccionado);
	}
}

//Carga el codigo de tasa del hidden en el combo, si este no la contiene
function anyadeTasaSiNoExiste(id_selectTasa, id_tasaSeleccionadaHidden){
	if(document.getElementById(id_tasaSeleccionadaHidden).value != "-1"){
		document.getElementById(id_selectTasa).value = document.getElementById(id_tasaSeleccionadaHidden).value;
		if(document.getElementById(id_selectTasa).selectedIndex <= 0){
			var selectCodigosTasa = document.getElementById(id_selectTasa);
			var codigoTasaSeleccionado = document.getElementById(id_tasaSeleccionadaHidden).value;
			selectCodigosTasa.options[selectCodigosTasa.options.length] = new Option(codigoTasaSeleccionado, codigoTasaSeleccionado, true, true);
		}
	}
}

//Generar la llamada AJAX para comentarios de una tasa
function cargaComentariosTasa(codigoTasa){

	var url = "recuperarComentariosTasaTraficoAjax.action?codigoTasa=" + codigoTasa;

	var req_generico_comTasa = NuevoAjax();
	//Hace la llamada a Ajax
	req_generico_comTasa.onreadystatechange = function () {
		if (req_generico_comTasa.readyState == 4) { // Complete
			if (req_generico_comTasa.status == 200) { // OK response
				var respuesta = req_generico_comTasa.responseText;
				if(respuesta!=null && respuesta!='undefined' && respuesta!=''){
					alert(respuesta);
				}
			}
		}
	};
	req_generico_comTasa.open("POST", url, true);
	req_generico_comTasa.send(null);
}

//Callback function de ObtenerCodigosTasaPorTipoTasa
function rellenarListaCodigosTasa(req_generico_codigosTasa,selectCodigosTasa,id_codigoTasaSeleccionado){

	selectCodigosTasa.options.length = 0;

	if (req_generico_codigosTasa.readyState == 4) { // Complete
		if (req_generico_codigosTasa.status == 200) { // OK response
			textToSplit = req_generico_codigosTasa.responseText;

			//Split the document
			returnElements=textToSplit.split("||");

			selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
			//Process each of the elements
			var codigoTasaSeleccionado = document.getElementById(id_codigoTasaSeleccionado).value;
			var existe = false;
			for ( var i=0; i<returnElements.length; i++ ){
				value = returnElements[i].split(";");
				if(!isNaN(value[0])){
					selectCodigosTasa.options[i+1] = new Option(value[0], value[1]);
					/* 
					 * La siguiente secuencia serviría para asociar un estilo concreto a una tasa dependiendo de un parámetro.
					 * Por ejemplo, para poner en rojo las importadas por un colegiado. Si son varios estilos, se le puede asociar un className
					 * directamente en vez de modificar repetidamente el style. (Ej: selectCodigosTasa.options[i+1].className = 'optionRoja';)
					 * 
					 * if(i == 0) {
					 * 	selectCodigosTasa.options[i+1].style.fontWeight = 'bold';
					 * 	selectCodigosTasa.options[i+1].style.color = '#ff0000';
					 * }
					 */
					if(selectCodigosTasa.options[i+1].value == codigoTasaSeleccionado){
						 selectCodigosTasa.options[i+1].selected = true;
						 existe = true;
					}
				}
			}
			if(!existe && codigoTasaSeleccionado!="-1"){
				selectCodigosTasa.options[selectCodigosTasa.options.length] = new Option(codigoTasaSeleccionado, codigoTasaSeleccionado, true, true);
			}
		}
	}
}

function quitarTasasSiMasDeQuince(idAnyo,idMes,idDia) {
	if (tieneMenosDeQuinceById(idAnyo,idMes,idDia) ) {
		document.getElementById('codigoTasaPagoPresentacion').disabled=false;
	} else if (document.getElementById("motivoBaja") != null && (document.getElementById("motivoBaja").value==7 || document.getElementById("motivoBaja").value==8)){
		document.getElementById('codigoTasaPagoPresentacion').disabled=true;
		document.getElementById('codigoTasaPagoPresentacion').selectedIndex=0;
	}else if (document.getElementById("motivoBaja") != null && document.getElementById("motivoBaja").value==5){
		document.getElementById('codigoTasaPagoPresentacion').disabled=true;
		document.getElementById('codigoTasaPagoPresentacion').selectedIndex=0;
	}
}

function tieneMenosDeQuince(anyo,mes,dia) {
	var a = parseFloat(anyo)+15;
	var m = parseFloat(mes)-1;
	var d = parseFloat(dia);
	var fechaMatri = new Date(a,m,d);
	var fechaActual = new Date();
	return fechaMatri>fechaActual;
}

function tieneMenosDeQuinceById(idAnyo,idMes,idDia) {
	var anyo = document.getElementById(idAnyo).value;
	var mes = document.getElementById(idMes).value;
	var dia = document.getElementById(idDia).value;
	if (anyo!="" && anyo!=null && mes!="" && mes!=null && dia!="" && dia!=null) return tieneMenosDeQuince(anyo,mes,dia);
	else return true;
}

function habilitarCodigoTasa(idTipoTasa,idCodigoTasa){
	if (document.getElementById(idTipoTasa).selectedIndex!= null && document.getElementById(idTipoTasa).selectedIndex == "0"){
		document.getElementById(idCodigoTasa).disabled=true;
	}else if(document.getElementById(idTipoTasa).selectedIndex == null){
		document.getElementById(idCodigoTasa).disabled=true;
	}else{
		document.getElementById(idCodigoTasa).disabled=false;
	}
}

function deshabilitarCodigoTasa(idCodigoTasa){
	document.getElementById(idCodigoTasa).disabled=true;
}

function validarCodigoTasa(){
	if (document.getElementById("codigoTasa").value == -1){
		alert ("Es obligatorio seleccionar una tasa");
		return false;
	}
	return true;
}

//Generar la llamada Ajax para obtener codigos de tasa
function obtenerCodigosTasaPorTipoTasa(selectTipoTasa,selectCodigosTasa,id_CodigoTasaSeleccionado){
	//Sin elementos
	if(selectTipoTasa.selectedIndex==0){
		selectCodigosTasa.length = 0;
		selectCodigosTasa.options[0] = new Option("Seleccione c\u00f3digo de tasa", "");
		return;
	}
	var selectedOption = selectTipoTasa.options[selectTipoTasa.selectedIndex].value;
	var idContrato = document.getElementById("idContratoTramite").value;
	var url = "recuperarCodigosTasaLibresPorTipoTasaTraficoAjax.action?tipoTasaSeleccionado="+selectedOption+"&idContrato="+idContrato;		
	var req_generico_codigosTasa = NuevoAjax();
	//Hace la llamada a AJAX
	req_generico_codigosTasa.onreadystatechange = function () { 
		rellenarListaCodigosTasa(req_generico_codigosTasa,selectCodigosTasa,id_CodigoTasaSeleccionado);
	}
	req_generico_codigosTasa.open("POST", url, true);
	req_generico_codigosTasa.send(null);
}

/*function desasignar1() {
	var pegatinas = numCheckedPegatina();
	if(numChecked() == 0) {
		if (pegatinas >0) {
			alert("Las tasas de pegatinas no se pueden desasignar");
		} else {
			alert("Debe seleccionar alguna tasa");
		}
		return false;
	}
	if (pegatinas >0) {
		if (!confirm("Las tasas de pegatinas no se desasignarán, únicamente las tasas electrónicas")){
			return false;
		}
	}
	var checks = document.getElementsByName("listaChecksConsultaTasas");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			//Pasamos al action todos los id de los checks marcados, separados por guiones
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+checks[i].value;
			}
		}
		i++;
	}
	if (confirm("¿Está seguro de que desea desasignar las tasas?")){
		document.forms[0].action="desasignar${action}?idCodigoTasaSeleccion="+codigos;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}*/

function comprobarTasasElectronicas(){
	var electronica = numChecked();
	if(electronica > 0){
		alert("Actualmente, las tasas electrónicas no se pueden generar en pdf, únicamente las tasas de pegatinas.");
		return false;
	}else if(numCheckedPegatina() == 0) {
		alert("Debe seleccionar alguna tasa.");
		return false;
	}
	return true;
}

function generarTasas(){
	if(comprobarTasasElectronicas()){
		var checksPegatina = document.getElementsByName("listaChecksConsultaTasaPegatina");
		var codigosPegatina = "";
		i = 0;
		while(checksPegatina[i] != null) {
			if(checksPegatina[i].checked) {
				//Pasamos al action todos los id de los checks marcados, separados por guiones
				if(codigosPegatina==""){
					codigosPegatina += checksPegatina[i].value;
				}else{
					codigosPegatina += "-"+checksPegatina[i].value;
				}
			}
			i++;
		}
		$('#formData').attr("action","generarTasasPegatinas${action}?idCodigoTasaPegatinaSeleccion="+codigosPegatina);
		$('#formData').submit();
		return true;
	}
}

/*js detalleTasa*/

function cancelar(){
	document.forms[0].action="navegar${action}";
	document.forms[0].submit();
	return false;
}

function gestionar(){
	var tasa = document.getElementById("CodigoTasa").value;
	if (tasa != null) {
		tasa = tasa.replace(/\s/g,'');

		document.getElementById("CodigoTasa").value = tasa;
	}
	var numExpediente = document.getElementById("NumeroExpediente").value;
	if (numExpediente != null) {
		numExpediente = numExpediente.replace(/\s/g,'');

		document.getElementById("NumeroExpediente").value = numExpediente;
	}
	var numColegiado = document.getElementById("numColegiado").value;
	if (numColegiado != null) {
		numColegiado = numColegiado.replace(/\s/g,'');

		document.getElementById("numColegiado").value = numColegiado;
	}
	var refPropia = document.getElementById("ReferenciaPropia").value;
	if (refPropia != null) {
		refPropia = refPropia.replace(/\s/g,'');

		document.getElementById("ReferenciaPropia").value = refPropia;
	}

	document.formData.action = "gestionarGestionTasa.action";
	document.formData.submit();
}
}