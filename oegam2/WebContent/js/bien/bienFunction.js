function limpiarBienRustico(){
	document.getElementById("idProvincia").value = "";
	document.getElementById("municipioSeleccionado").value = "";
	var $municipio = $("#idMunicipio", parent.document);
	$municipio.find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
	document.getElementById("idCodPostal").value = "";
	document.getElementById("idParaje").value = "";
	document.getElementById("idPoligono").value = "";
	document.getElementById("idParcela").value = "";
	document.getElementById("idSubParcela").value = "";
	document.getElementById("idRefCatrastal").value = "";
	document.getElementById("idUsoRusticoGanaderia").value = "";
	document.getElementById("idUsoRusticoCultivo").value = "";
	document.getElementById("idUsoRusticoOtros").value = "";
	document.getElementById("idSistemaExplotacion").value = "";
	document.getElementById("idTipoInmuebleRustico").value = "";
}


function limpiarBienUrbano(){
	document.getElementById("idProvincia").value = "";
	document.getElementById("municipioSeleccionado").value = "";
	var $municipio = $("#idMunicipio", parent.document);
	$municipio.find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
	document.getElementById("idNombreVia").value = "";
	document.getElementById("idCodPostal").value = "";
	document.getElementById("idArrendamiento").value = "";
	document.getElementById("idVpo").value = "";
	document.getElementById("idTipoInmuebleRustico").value = "";
	document.getElementById("idAnioContrato").value = "";
	document.getElementById("idSuperfCons").value = "";
	document.getElementById("idValorDeclarado").value = "";
	document.getElementById("idRefCatrastal").value = "";
	document.getElementById("idEstado").value = "";
	document.getElementById("diaBusquedaIni").value = "";
	document.getElementById("mesBusquedaIni").value = "";
	document.getElementById("anioBusquedaIni").value = "";
	document.getElementById("diaBusquedaFin").value = "";
	document.getElementById("mesBusquedaFin").value = "";
	document.getElementById("anioBusquedaFin").value = "";
}

function consultarBienRustico(){
	document.formData.action = "buscarConsultaBienRustico.action";
	document.formData.submit();
}

function consultarBienUrbano(){
	document.formData.action = "buscarConsultaBienUrbano.action";
	document.formData.submit();
}

function consultarBienesOtros() {
	
	document.formData.action = "buscarConsultaBnS.action?esPopup=S";
	document.formData.submit();
}

function cambiarElementosPorPaginaBienRustico() {
	var $dest = $("#displayTagConsulRusticoDiv");
	$.ajax({
		url:"navegarConsultaBienRustico.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPagina").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de recargar los bienes.');
	    }
	});
}

function cambiarElementosPorPaginaBienUrbano() {
	var $dest = $("#displayTagConsultaUrbanoDiv");
	$.ajax({
		url:"navegarConsultaBienUrbano.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPagina").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de recargar los bienes.');
	    }
	});
}
function cambiarElementosPorPaginaConsultBien() {
	
	document.location.href = 'navegarConsultaBnS.action?resultadosPorPagina='
	+ document.formData.idResultadosPorPagina.value;
}

var ventanaDatos;
var opciones_ventana = 'width=550,height=150,top=150,left=280';


function bienesSeleccionados(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecks");
	var i = 0;
	var contCodigos = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			contCodigos++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien.");
	}else if(contCodigos > 1){
		return alert("Debe seleccionar los bienes de forma individual.");
	}
	
	window.opener.invokeSeleccionBienRustico(codigos);
}


function bienesSeleccionadosOtros(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecks");
	var i = 0;
	var contCodigos = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			contCodigos++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien.");
	}else if(contCodigos > 1){
		return alert("Debe seleccionar los bienes de forma individual.");
	}
	
	window.opener.invokeSeleccionBienOtro(codigos);
}


function bienesSeleccionadosUrbano(){
	var codigos = "";
	var checks = document.getElementsByName("listaChecks");
	var i = 0;
	var contCodigos = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-" + checks[i].value;
			}
			contCodigos++;
		}
		i++;
	}
	if(codigos == ""){
		return alert("Debe seleccionar alg\u00FAn bien.");
	}else if(contCodigos > 1){
		return alert("Debe seleccionar los bienes de forma individual.");
	}
	
	window.opener.invokeSeleccionBienUrbano(codigos);
}

function marcarTodosSeleccionados(objCheck) {
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { 
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { 
			document.formData.v.checked = marcar;
		}
		for (var j = 0; j < document.formData.listaChecks.length; j++) {
			document.formData.listaChecks[j].checked = marcar;
		}
	}
}

function marcarTodosCBienes(objCheck){
	var marcar = objCheck.checked;
	if (document.formData.listaChecks) {
		if (!document.formData.listaChecks.length) { 
			document.formData.listaChecks.checked = marcar;
		}
		for (var i = 0; i < document.formData.listaChecks.length; i++) {
			document.formData.listaChecks[i].checked = marcar;
		}
	}
}

function cargarListaMunicipiosBien(select_provincia,id_select_municipio,id_municipioSeleccionado){
	document.getElementById(id_municipioSeleccionado).value = "";
	obtenerMunicipiosPorProvincia(select_provincia,document.getElementById(id_select_municipio),id_municipioSeleccionado);
}

function guardarBien(){
	if($('#idTipoBien').val() == "URBANO"){
		if($("#idTipoInmuebleUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		if($("#idProvinciaBienUrbano",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienUrbano",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
		
		if($("#idTipoVia",parent.document).val() == "-1" || $("#tipoViaSeleccionadaUrbano",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de vía para la dirección del bien.");
		}
		if($("#idNombreViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el nombre de la vía del bien.");
		}
		if($("#idNumeroViaUrbano",parent.document).val() == ""){
			return alert("Debe rellenar el número de la vía del bien.");
		}
		$("#idBienUrbanoDto",parent.document).prop("disabled", false);
		$("#idDireccionUrbano",parent.document).prop("disabled", false);
		$("#idVHabitual", parent.document).prop("disabled", false);
		
	}else if($('#idTipoBien').val() == "RUSTICO"){
		if($("#idTipoInmuebleRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de Inmueble para poder guardar el bien.");
		}
		
		if($("#idUsoRusticoGanaderia",parent.document).val() == "" && $("#idUsoRusticoCultivo",parent.document).val() == "" && $("#idUsoRusticoOtros",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn tipo de uso para poder guardar el bien.");
		}
		if($("#idSuperficieRustico",parent.document).val() == ""){
			return alert("Debe rellenar la superficie del bien.");
		}
		if( $("#idUsoRusticoCultivo",parent.document).val() != ""){
			if($("#idUnidadMetricaRustico",parent.document).val() == ""){
				return alert("Debe seleccionar alguna Unidad M\u00E9trica para poder guardar el bien.");
			}
		}
		if($("#idProvinciaBienRustico",parent.document).val() == "-1"){
			return alert("Debe seleccionar alguna provincia para la dirección del bien.");
		}
		if($("#idMunicipioBienRustico",parent.document).val() == "-1" || $("#idMunicipioBienRustico",parent.document).val() == ""){
			return alert("Debe seleccionar alg\u00FAn municipio para la dirección del bien.");
		}
		$("#idBienRusticoDto",parent.document).prop("disabled", false);
		$("#idDireccionRustico",parent.document).prop("disabled", false);
	}
	deshabilitarBotones();
	var pestania = obtenerPestaniaSeleccionada();
	$("#formData").attr("action","guardarBN.action#"+pestania).trigger("submit");
}

function deshabilitarBotones(){
	if (!$('#bGuardarBien').is(':hidden')){
		$("#bGuardarBien").css("color","#BDBDBD");
		$("#bGuardarBien").prop("disabled",true);
	}
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

function cargarListaMunicipiosCam(provincia,municipio){
	var $dest = $("#"+municipio);
	$.ajax({
		url:"cargarMunicipiosAjaxBn.action",
		data:"provincia="+ $("#"+provincia).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
				var listMuni = data.split("|");
				$.each(listMuni,function(i,o){
					var municipio = listMuni[i].split("_");
					 $dest.append($('<option>', { 
					        value: municipio[0],
					        text : municipio[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los municipios de la provincia.');
	    }
	});
}

function cargarListaPueblosCam(provincia, municipio, pueblo){
	var $dest = $("#"+pueblo);
	 $dest.find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$.ajax({
		url:"cargarPuebloAjaxBn.action",
		data:"provincia="+ $("#"+provincia).val() +"&municipio=" +$("#"+municipio).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
				var listPueblo = data.split("_");
				$.each(listPueblo,function(i,o){
					 $dest.append($('<option>', { 
					        value: listPueblo[i],
					        text : listPueblo[i]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los pueblos.');
	    }
	});
}

function cargarListaTipoViaCam(provincia,tipoVia){
	var $dest = $("#"+tipoVia);
	$.ajax({
		url:"cargarListaTipoViaCamAjaxBn.action",
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="-1">Seleccione Tipo Via</option>').val('whatever');
				var listTpVias = data.split("|");
				$.each(listTpVias,function(i,o){
					var tipoVia = listTpVias[i].split("_");
					 $dest.append($('<option>', { 
					        value: tipoVia[0],
					        text : tipoVia[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los tipos de vias de la provincia.');
	    }
	});
}

function cargarListaTiposInmuebles(tipoBien,tipoInmueble){
	var $dest = $("#"+tipoInmueble);
	$.ajax({
		url:"cargarListaTipoInmueblesAjaxBn.action",
		data: "tipoBien=" + $("#"+tipoBien).val(),
		type:'POST',
		success: function(data){
			if(data != ""){
				$dest.find('option').remove().end().append('<option value="">Seleccione Tipo Inmueble</option>').val('whatever');
				var listTpInmuebles = data.split("|");
				$.each(listTpInmuebles,function(i,o){
					var tipoInmueble = listTpInmuebles[i].split("_");
					 $dest.append($('<option>', { 
					        value: tipoInmueble[0],
					        text : tipoInmueble[1]
					    }));
				 });
			}
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar los tipos de inmuebles de los bienes.');
	    }
	});
}

function seleccionarUsoRustico(idHiddenTipoUsoRustico, valor){
	var $tipoUsoRustico = $("#" + idHiddenTipoUsoRustico);
	$tipoUsoRustico.val(valor);
	deshabilitarCamposUsoBienRustico();
}

function deshabilitarCamposUsoBienRustico(){
	var $usoGanaderia = $("#idUsoRusticoGanaderia", parent.document);
	var $usoCultivo = $("#idUsoRusticoCultivo", parent.document);
	var $usoOtros = $("#idUsoRusticoOtros", parent.document);
	if($usoGanaderia.val() != ""){
		$usoCultivo.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoCultivo.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoOtros.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoCultivo.prop("disabled", true);
	}else{
		$usoGanaderia.prop("disabled", false);
		$usoCultivo.prop("disabled", false);
		$usoOtros.prop("disabled", false);
	}
}

function abrirDetalleBien(bien){
	if(bien == null || bien == ""){
		return alert("No se puede obtener el detalle del bien seleccionado.");
	}
	if ($("input[name='idBien']").is(':hidden')){
		$("input[name='idBien']").remove();
	}
	input = $("<input>").attr("type", "hidden").attr("name", "idBien").val(bien);
	$('#formData').append($(input));
	$("#formData").attr("action","altaBN.action").trigger("submit");
}

function consultarBienes(){
	mostrarLoadingConsultaBien('bConsulta');
	$("#formData").attr("action","buscarConsultaBnS.action").trigger("submit");
}

function limpiarConsultaBienes(){
	$('#idProvincia').val("");
	$('#idMunicipio').find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
	$('#idCodPostal').val("");
	$('#idTipoBien').val("");
	$('#idTipoInmueble').find('option').remove().end().append('<option value="">Seleccione Tipo Inmueble</option>').val('whatever');
	$('#idRefCatrastal').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
	$('#idIdufirConsulta').val("");
	$('#idSeccion').val("");
}

function limpiarConsultaBienOtro(){
	$('#idRefCatrastal').val("");
	$('#idIdufirConsulta').val("");
	$('#diaFechaAltaIni').val("");
	$('#mesFechaAltaIni').val("");
	$('#anioFechaAltaIni').val("");
	$('#diaFechaAltaFin').val("");
	$('#mesFechaAltaFin').val("");
	$('#anioFechaAltaFin').val("");
}

function limpiarConsultaBienUrbano(){
	$('#idProvincia').val("");
	$('#idMunicipio').find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
	$('#idCodPostal').val("");
	$('#idNombreVia').val("");
	$('#idArrendamiento').val("");
	$('#idVpo').val("");
	$('#idTipoInmuebleUrbano').val("");
	$('#idAnioContrato').val("");
	$('#idSuperfCons').val("");
	$('#idRefCatrastal').val("");
	$('#idEstado').val("");
	$('#diaBusquedaIni').val("");
	$('#mesBusquedaIni').val("");
	$('#anioBusquedaIni').val("");
	$('#diaBusquedaFin').val("");
	$('#mesBusquedaFin').val("");
	$('#anioBusquedaFin').val("");
	$('#idIdufirConsulta').val("");
}

function limpiarConsultaBienRustico(){
	$('#idProvincia').val("");
	$('#idMunicipio').find('option').remove().end().append('<option value="">Seleccione Municipio</option>').val('whatever');
	$('#idCodPostal').val("");
	$('#idParaje').val("");
	$('#idPoligono').val("");
	$('#idParcela').val("");
	$('#idSubParcela').val("");
	$('#idRefCatrastal').val("");
	$('#idUsoRusticoGanaderia').val("");
	$('#idUsoRusticoCultivo').val("");
	$('#idUsoRusticoOtros').val("");
	$('#idSistemaExplotacion').val("");
	$('#idTipoInmuebleRustico').val("");
	$("#idUsoRusticoCultivo").prop("disabled", false);
	$("#idUsoRusticoOtros").prop("disabled", false);
	$("#idUsoRusticoGanaderia").prop("disabled", false);
	$('#idIdufirConsulta').val("");
}

function deshabilitarCamposUsoBienRustico(){
	var $usoGanaderia = $("#idUsoRusticoGanaderia", parent.document);
	var $usoCultivo = $("#idUsoRusticoCultivo", parent.document);
	var $usoOtros = $("#idUsoRusticoOtros", parent.document);
	if($usoGanaderia.val() != ""){
		$usoCultivo.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoCultivo.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoOtros.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoCultivo.prop("disabled", true);
	}else{
		$usoGanaderia.prop("disabled", false);
		$usoCultivo.prop("disabled", false);
		$usoOtros.prop("disabled", false);
	}
}


function eliminarBloque(){
	var valueBoton = $("#bEliminar").val();
	mostrarLoadingBien('bEliminar');
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
	
	if(codigos == ""){
		ocultarLoadingBien('bEliminar',valueBoton);
		return alert("Debe seleccionar alg\u00FAn bien para eliminar.");
	}
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar los bienes seleccionados?")){
		if ($("input[name='codSeleccionados']").is(':hidden')){
			$("input[name='codSeleccionados']").remove();
		}
		input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
		$('#formData').append($(input));
		$("#formData").attr("action", "eliminarConsultaBnS.action#").trigger("submit");
	}else{
		ocultarLoadingBien('bEliminar',valueBoton);
	}
}

function eliminarBien(){
	if (confirm("\u00BFEst\u00E1 seguro de que desea eliminar el bien?")){
		if($('#idTipoBien').val() == "URBANO"){
			$("#idBienUrbanoDto",parent.document).prop("disabled", false);
			$("#idDireccionUrbano",parent.document).prop("disabled", false);
		}else if($('#idTipoBien').val() == "RUSTICO"){
			$("#idBienRusticoDto",parent.document).prop("disabled", false);
			$("#idDireccionRustico",parent.document).prop("disabled", false);
		}
		deshabilitarBotones();
		var pestania = obtenerPestaniaSeleccionada();
		$("#formData").attr("action","eliminarBN.action#"+pestania).trigger("submit");
	}
}

function bloqueaBotonesBien() {	
	$('#bEliminar').prop('disabled', true);
	$('#bEliminar').css('color', '#6E6E6E');	
	$('#bConsulta').prop('disabled', true);
	$('#bConsulta').css('color', '#6E6E6E');	
	$('#bLimpiar').prop('disabled', true);
	$('#bLimpiar').css('color', '#6E6E6E');	
}

function desbloqueaBotonesBien() {	
	$('#bEliminar').prop('disabled', false);
	$('#bEliminar').css('color', '#000000');			
	$('#bConsulta').prop('disabled', false);
	$('#bConsulta').css('color', '#000000');			
	$('#bLimpiar').prop('disabled', false);
	$('#bLimpiar').css('color', '#000000');			
}

function mostrarLoadingBien(boton) {
	bloqueaBotonesBien();
	document.getElementById("bloqueLoadingBien").style.display = "block";
	$('#'+boton).val("Procesando..");

}

function mostrarLoadingConsultaBien(boton) {
	bloqueaBotonesBien();
	document.getElementById("bloqueLoadingConsultaBien").style.display = "block";
	$('#'+boton).val("Procesando..");

}

function ocultarLoadingBien(boton, value) {
	document.getElementById("bloqueLoadingBien").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesBien();
}

function ocultarLoadingConsultaBien(boton, value) {
	document.getElementById("bloqueLoadingConsultaBien").style.display = "none";
	$('#'+boton).val(value);
	desbloqueaBotonesBien();
}

function inicializarCombos(){
	var $usoGanaderia = $("#idUsoRusticoGanaderia", parent.document);
	var $usoCultivo = $("#idUsoRusticoCultivo", parent.document);
	var $usoOtros = $("#idUsoRusticoOtros", parent.document);
	if($usoGanaderia.val() != ""){
		$usoCultivo.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoCultivo.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoOtros.prop("disabled", true);
	}else if($usoOtros.val() != ""){
		$usoGanaderia.prop("disabled", true);
		$usoCultivo.prop("disabled", true);
	}else{
		$usoGanaderia.prop("disabled", false);
		$usoCultivo.prop("disabled", false);
		$usoOtros.prop("disabled", false);
	}
}

function controlNumeros(event){
	var charCode = event.charCode;
	if ((charCode >= 48 && charCode <= 57) || charCode == 9 || charCode == 44 || charCode == 46 || event.keyCode==8){
		return true;
	}else{
		return false;
	}	
}

function cambiarComa(id){
	$(id).val($(id).val().replace(',','.'));
}


function validarPorcentaje(id){
	$(id).val($(id).val().replace(',','.'));
	if($(id).val()>100){
		$(id).val($(id).val().substr(0,2));
	}
}
function habilitarVHabitual(selectVH){
	var opcion = $("#"+selectVH).val();
	if(opcion == "VI" || opcion == "VU" || opcion == "VH" || opcion == "PG" || opcion == "CT"){
		$("#idVHabitual", parent.document).prop("disabled", false);
	}else{
		$("#idVHabitual").prop('checked',false);
		$("#idVHabitual", parent.document).prop("disabled", true);
	}
}