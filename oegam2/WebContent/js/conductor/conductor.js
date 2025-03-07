
function buscarConductor(){	
		var nif = $('#idNifConductor').val();
		if(nif != ""){		
			limpiarFormularioPersonaConductor();
			var $dest = $("#divDatosPersona", parent.document);
			deshabilitarBotonesConductor();
			$.ajax({
				url:"buscarPersonaConductorAjaxCayc.action#Consulta" ,
				data:"nif="+ nif + "&idContrato=" + $("#idContrato").val(),
				type:'POST',
				success: function(data, xhr, status){
					$dest.empty().append(data);
					if($('#idPrimerApeRazonSocialConductor').val() != ""){
						$("#idLimpiarConductor").show();
						$("#idBotonBuscarNifConductor").prop("disabled", true);
						$('#idNifConductor').prop("disabled", true);
						
					}else{
						$("#idBotonBuscarNifConductor").prop("disabled", false);
						$('#idNifConductor').prop("disabled", false);						
					}
					habilitarBotonesConductor();
					
				},
				error : function(xhr, status) {
			        alert('Ha sucedido un error a la hora de cargar la persona.');
			        habilitarBotonesConductor();
			    }
			});
		}else{
			alert("Para poder realizar la busqueda del sujeto pasivo debe rellenar el NIF/CIF.");
		}
}
function Volver_Conductor() {
	
	
	   window.location.href="inicioConsultaConductorHabitual.action";

}


function buscarConsultaConductor() {
	document.formData.action = "buscarConsultaConductorHabitual.action";
	
    var nif 		= new String(document.getElementById("nif").value);
	var doivehiculo = new String(document.getElementById("doivehiculo").value);
	var matricula 	= new String(document.getElementById("matricula").value);
	var numRegistro = new String(document.getElementById("numRegistro").value);
	var idnumexpediente	= new String(document.getElementById("idNumExpediente").value);
	var temp= Limpia_Caracteres(idnumexpediente);
	
	
	var matricula = new String(document.getElementById("matricula").value);
	matricula=Limpia_Caracteres_Matricula(matricula);
	nif=Limpia_Caracteres_Estranos(nif);
	doivehiculo=Limpia_Caracteres_Estranos(doivehiculo);
	numRegistro=Limpia_Caracteres_Estranos(numRegistro);
	
	
	document.getElementById("idNumExpediente").value = temp;
	document.getElementById("nif").value = nif.toUpperCase();
	document.getElementById("doivehiculo").value = doivehiculo.toUpperCase();
	document.getElementById("matricula").value = matricula.toUpperCase();
	document.getElementById("numRegistro").value = numRegistro.toUpperCase();
	document.formData.submit();
}
function buscarModificarConductor() {
	document.formData.action = "buscarModificarConductorHabitual.action";
	
    var nif 		= new String(document.getElementById("nif").value);
	var doivehiculo = new String(document.getElementById("doivehiculo").value);
	var matricula 	= new String(document.getElementById("matricula").value);
	var bastidor	= new String(document.getElementById("bastidor").value);
	
	document.getElementById("nif").value = nif.toUpperCase();
	document.getElementById("doivehiculo").value = doivehiculo.toUpperCase();
	document.getElementById("matricula").value = matricula.toUpperCase();
	document.getElementById("bastidor").value = bastidor.toUpperCase();

	document.formData.submit();
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

function limpiarConsultaConductor_Pantalla() {

	$("#idContrato").val("");
	document.getElementById("nif").value = "";
	document.getElementById("doivehiculo").value = "";
	document.getElementById("matricula").value = "";
	document.getElementById("numRegistro").value = "";
	document.getElementById("diaInicio").value = "";
	document.getElementById("mesInicio").value = "";
	document.getElementById("anioInicio").value = "";
	document.getElementById("diaFinInicio").value = "";
	document.getElementById("mesFinInicio").value = "";
	document.getElementById("anioFinInicio").value= "";
	document.getElementById("diaInicio2").value = "";
	document.getElementById("mesInicio2").value = "";
	document.getElementById("anioInicio2").value = "";
	document.getElementById("diaFinInicio2").value = "";
	document.getElementById("mesFinInicio2").value = "";
	document.getElementById("anioFinInicio2").value = "";
	document.getElementById("diaInicio3").value = "";
	document.getElementById("mesInicio3").value = "";
	document.getElementById("anioInicio3").value = "";
	document.getElementById("diaFinInicio3").value = "";
	document.getElementById("mesFinInicio3").value = "";
	document.getElementById("anioFinInicio3").value = "";
	document.getElementById("diaInicio4").value = "";
	document.getElementById("mesInicio4").value = "";
	document.getElementById("anioInicio4").value = "";
	document.getElementById("diaFinInicio4").value = "";
	document.getElementById("mesFinInicio4").value = "";
	document.getElementById("anioFinInicio4").value = "";
	document.getElementById("estado").value = "";
	document.getElementById("operacion").value = "0";
	document.getElementById("idNumExpediente").value = "";
	document.getElementById("referenciaPropia").value = "";
}



function limpiarFormularioPersonaConductor(){
	$('#idNifConductor').val("");
	$('#idDireccionConductor').val("");	
	$('#idTipoPersonaConductor').val("-1");
	$('#idSexoConductor').val("-1");
	$('#idPrimerApeRazonSocialConductor').val("");
	$('#idSegundoApeConductor').val("");
	$('#idNombreConductor').val("");
	$('#idDiaNacConductor').val("");
	$('#idMesNacConductor').val("");
	$('#idAnioNacConductor').val("");
	$('#idProvinciaConductor').val("-1");
	$('#idMunicipioConductor').find('option').remove().end().append('<option value="-1">Seleccione Municipio</option>').val('whatever');
	$('#idMunicipioConductor').val("-1");	
	$('#idTipoViaConductor').find('option').remove().end().append('<option value="-1">Seleccione Tipo de Via</option>').val('whatever');
	$('#idTipoViaConductor').val("-1");
	$('#idPuebloConductor').find('option').remove().end().append('<option value="-1">Seleccione Pueblo</option>').val('whatever');
	$('#idPuebloConductor').val("-1");
	$('#idNombreViaConductor').val("");
	$('#idCodPostalConductor').val("");
	$('#idNumeroConductor').val("");
	$('#idLetraDireccionConductor').val("");
	$('#idEscaleraDireccionConductor').val("");
	$('#idPisoDireccionConductor').val("");
	$('#idPuertaDireccionConductor').val("");
	$('#idBloqueDireccionConductor').val("");
	$('#idKmDireccionConductor').val("");
	$('#idNifConductor').prop("disabled", false);
	$("#idBotonBuscarNifConductor").prop("disabled", false);
	$('#idTipoPersonaConductor').prop("disabled", false);
	$('#idSexoConductor').prop("disabled", false);	
	$("#idLimpiarConductor").hide();
}

function Combo_Tipo_Persona(){
	
	if ($("#idTipoPersonaConductor",parent.document).val() != "FISICA"){
		
		$("#idSexoConductor",parent.document).val("X");
		$("#idSexoConductor",parent.document).attr('disabled', 'disabled');
		
	} else{
		$("#idSexoConductor",parent.document).val("-");
		$("#idSexoConductor",parent.document).removeAttr('disabled');;
	}
}

function cargarListaNombresVia_Conductor(select_provincia,select_municipio, select_tipoVia, id_select_nombreVia, via){
	
	document.getElementById(id_select_nombreVia).value="";
	via.processor.setWords([]);
	obtenerNombreViasPorProvinciaMunicipioYTipoVia(document.getElementById(select_provincia),select_municipio, select_tipoVia,document.getElementById(id_select_nombreVia),via); 
}

function  Validar_Datos_Conductor( pestania){

	if (pestania == 'Vehiculo') {
		if (($("#idMatriculaConductor", parent.document).val() == "")
				|| $("#idMatriculaConductor", parent.document).val() == null) {
			return "Matricula es campo oblicatorio";
		} else {
			var temp = $("#idMatriculaConductor", parent.document).val()
					.toUpperCase();
			$("#idMatriculaConductor", parent.document).val(temp)
		}

		if (($("#idBastidorConductor", parent.document).val() == "")
				|| $("#idBastidorConductor", parent.document).val() == null) {
			return "Bastidor es campo obligatorio";
		} else {
			var temp = $("#idBastidorConductor", parent.document).val()
					.toUpperCase();
			$("#idBastidorConductor", parent.document).val(temp)
		}

		if (($("#idDoiVehiculoConductor", parent.document).val() == "")
				|| $("#idDoiVehiculoConductor", parent.document).val() == null) {
		} else {
			var temp = $("#idDoiVehiculoConductor", parent.document).val()
					.toUpperCase();
			$("#idDoiVehiculoConductor", parent.document).val(temp)
		}
	}
	return null;
}

function guardarConductor(){
	deshabilitarBotonesConductor();
	var pestania = obtenerPestaniaSeleccionada();
	Validar_Datos_Conductor( pestania);
	habilitarCamposConductor();
	$("#formData").attr("action", "guardarAltaConductorHabitual.action#"+pestania).trigger("submit");
}

function consultarConductor(){	
	deshabilitarBotonesConductor();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCamposConductor();
	$("#formData").attr("action", "consultarAltaConductorHabitual.action#"+pestania).trigger("submit");
}

function validarConductor(){
	deshabilitarBotonesConductor();
	var pestania = obtenerPestaniaSeleccionada();
	habilitarCamposConductor();
	$("#formData").attr("action", "validarAltaConductorHabitual.action#"+pestania).trigger("submit");
}

function deshabilitarBotonesConductor(){
	$("#idGuardarConductor").prop('disabled', true);
	$("#idGuardarConductor").css('color', '#6E6E6E');	
	$("#idConsultarConductor").prop('disabled', true);
	$("#idConsultarConductor").css('color', '#6E6E6E');	
	$("#idValidarConductor").prop('disabled', true);
	$("#idValidarConductor").css('color', '#6E6E6E');		
}

function habilitarBotonesConductor(){
	if (!$('#idLimpiarConductor').is(':hidden')){
		$("#idLimpiarConductor").css("color","#000000");
		$("#idLimpiarConductor").prop("disabled",false);
	}
	
	if (!$('#idGuardarConductor').is(':hidden')){
		$("#idGuardarConductor").css("color","#000000");
		$("#idGuardarConductor").prop("disabled",false);
	}
	
	if (!$('#idValidarConductor').is(':hidden')){
		$("#idValidarConductor").css("color","#000000");
		$("#idValidarConductor").prop("disabled",false);
	}
}

function habilitarCamposConductor(){
	$('#idNifConductor').prop("disabled", false);
	$("#idBotonBuscarNifConductor").prop("disabled", false);
	$('#idTipoPersonaConductor').prop("disabled", false);
	$('#idSexoConductor').prop("disabled", false);
	$("#idDireccionConductor").prop('disabled', false);
}

function datosInicialesConductor(){
	var $botonLimpiar = $("#idLimpiarConductor");
	var $botonBuscarNif = $("#idBotonBuscarNifConductor");
	if($("#idNifConductor",parent.document).val() != ""){
		$("#idNifConductor",parent.document).prop("disabled", true);
		$botonBuscarNif.prop("disabled", true);
		$botonLimpiar.show();
	}else{
		$botonLimpiar.hide();
	}
}
function habilitarBotonesConsultaConductor(){
	$("#idCambiarEstado").prop('disabled', false);
	$("#idCambiarEstado").css('color', '#000000');	
	$("#idConsultar").prop('disabled', false);
	$("#idConsultar").css('color', '#000000');
	$("#idEliminar").prop('disabled', false);
	$("#idEliminar").css('color', '#000000');
	$("#idValidar").prop('disabled', false);
	$("#idValidar").css('color', '#000000');
	
	
}
function deshabilitarBotonesConsultaConductor(){
	$("#idCambiarEstado").prop('disabled', true);
	$("#idCambiarEstado").css('color', '#6E6E6E');	
	$("#idConsultar").prop('disabled', true);
	$("#idConsultar").css('color', '#6E6E6E');	
	$("#idEliminar").prop('disabled', true);
	$("#idEliminar").css('color', '#6E6E6E');
	$("#idValidar").prop('disabled', true);
	$("#idValidar").css('color', '#6E6E6E');
}

function mostrarLoadingConductor(bloqueLoading,boton) {
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
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
function ocultarLoadingConductor(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
}

function eliminarConductor(){
	deshabilitarBotonesConsultaConductor();
	var valueBoton = $("#idEliminar").val();
	mostrarLoadingConductor("bloqueLoadingConsultaConductor","idEliminar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConductor('idEliminar',valueBoton,"bloqueLoadingConsultaConductor");
		alert("Debe seleccionar algun tramite para poder eliminarlo.");
		habilitarBotonesConsultaConductor();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "eliminarConsultaConductorHabitual.action").trigger("submit");


}
function mostrarLoadingConductor(bloqueLoading,boton) {
	document.getElementById(bloqueLoading).style.display = "block";
	$('#'+boton).val("Procesando..");
}

function ocultarLoadingConductor(boton, value, bloqueLoading) {
	document.getElementById(bloqueLoading).style.display = "none";
	$('#'+boton).val(value);
}

function cambiarEstadoBloqueConductor(){
	var valueBoton = $("#idCambiarEstado").val();
	mostrarLoadingConductor("bloqueLoadingConsultaConductor","idCambiarEstado");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConductor('idCambiarEstado',valueBoton,"bloqueLoadingConsultaConductor");
		return alert("Debe seleccionar algun tramite para cambiar el estado.");
	}
	var $dest = $("#divEmergenteConsultaConductor");
	
	$.post("cargarPopUpCambioEstadoConsultaConductorHabitual.action", function(data) {
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
						$("#divEmergenteConsultaConductor").dialog("close");
						limpiarHiddenInput("estadoNuevo");
						input = $("<input>").attr("type", "hidden").attr("name", "estadoNuevo").val(estadoNuevo);
						$('#formData').append($(input));
						limpiarHiddenInput("codSeleccionados");
						input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
						$('#formData').append($(input));
						$("#formData").attr("action", "cambiarEstadoConsultaConductorHabitual.action").trigger("submit");
					},
					Cerrar : function() {
						
						
						ocultarLoadingConductor('idCambiarEstado',valueBoton,"bloqueLoadingConsultaConductor");
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

function consultarBloqueConductor(){
	
	deshabilitarBotonesConsultaConductor();
	var valueBoton = $("#idConsultar").val();
	mostrarLoadingConductor("bloqueLoadingConsultaConductor","idConsultar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConductor('idConsultar',valueBoton,"bloqueLoadingConsultaConductor");
		alert("Debe seleccionar algun tramite para realizar la consulta.");
		habilitarBotonesConsultaConductor();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "consultarConsultaConductorHabitual.action").trigger("submit");
}

function validarBloqueConductor(){
	
	deshabilitarBotonesConsultaConductor();
	var valueBoton = $("#idValidar").val();
	mostrarLoadingConductor("bloqueLoadingConsultaConductor","idValidar");
	var codigos = getChecksConsultaMarcados("listaChecks");
	if(codigos == ""){
		ocultarLoadingConductor('idValidar',valueBoton,"bloqueLoadingConsultaConductor");
		alert("Debe seleccionar algun tramite para poder validar.");
		habilitarBotonesConsultaConductor();
		return false;
	}
	limpiarHiddenInput("codSeleccionados");
	input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
	$('#formData').append($(input));
	$("#formData").attr("action", "validarConsultaConductorHabitual.action").trigger("submit");
}

function modificarFechaConductor(){
	
		var valueBoton = $("#idModificarFechaConductor").val();
		mostrarLoadingConductor("bloqueLoadingConsultaConductor","idModificarFechaConductor");
		var codigos = getChecksConsultaMarcados("listaChecks");
		if(codigos == ""){
			ocultarLoadingConductor('idModificarFechaConductor',valueBoton,"bloqueLoadingConsultaConductor");
			return alert("Debe seleccionar algún trámite para modificar sus fechas.");
		}
		var $dest = $("#divEmergenteModificarFechaConductor");
		
		$.post("cargarPopUpModificarFechasConsultaConductorHabitual.action", function(data) {
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
						Modificar : function() {								
							var Ini_Diat = $("#Ini_Dia").val();
							var Ini_Mest = $("#Ini_Mes").val();
							var Ini_Aniot = $("#Ini_Anio").val();							
							var Fin_Diat = $("#Fin_Dia").val();
							var Fin_Mest = $("#Fin_Mes").val();
							var Fin_Aniot = $("#Fin_Anio").val();
							
							if(Ini_Diat == "" || Ini_Mest == "" || Ini_Aniot == "" ){
								alert("Faltan datos la fecha de inicio");
								return false;
							}							
							if(Fin_Diat == "" || Fin_Mest == "" || Fin_Aniot == "" ){
								alert("Faltan datos la fecha de fin");
								return false;
							}							
							
							$("#divEmergenteModificarFechaConductor").dialog("close");
							
							input = $("<input>").attr("type", "hidden").attr("name", "codSeleccionados").val(codigos);
							$('#formData').append($(input));
							
							input = $("<input>").attr("type", "hidden").attr("name", "fechaFinModificacion.dia").val(Fin_Diat);
							$('#formData').append($(input));
						
							input = $("<input>").attr("type", "hidden").attr("name", "fechaFinModificacion.mes").val(Fin_Mest);
							$('#formData').append($(input));
						
							input = $("<input>").attr("type", "hidden").attr("name", "fechaFinModificacion.anio").val(Fin_Aniot);
							$('#formData').append($(input));
						
							input = $("<input>").attr("type", "hidden").attr("name", "fechaIniModificacion.dia").val(Ini_Diat);
							$('#formData').append($(input));
						
							input = $("<input>").attr("type", "hidden").attr("name", "fechaIniModificacion.mes").val(Ini_Mest);
							$('#formData').append($(input));
						
							input = $("<input>").attr("type", "hidden").attr("name", "fechaIniModificacion.anio").val(Ini_Aniot);
							$('#formData').append($(input));
																				
							$("#formData").attr("action", "modificarFechasConsultaConductorHabitual.action").trigger("submit");
						},
						Cerrar : function() {							
							ocultarLoadingConductor('idModificarFechaConductor',valueBoton,"bloqueLoadingConsultaConductor");
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