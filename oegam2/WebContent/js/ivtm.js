//TODO MPC. Cambio IVTM. Javascript añadido. 
function recargarDireccionIVTM(){
	var selectProvincia = document.getElementById("idProvinciaTitular");
	var selectMunicipio = document.getElementById("idMunicipioTitular");
	var selectPueblo = document.getElementById("idPuebloTitular");
	var selectProvinciaVeh = document.getElementById("idProvinciaVehiculo");
	var selectMunicipioVeh = document.getElementById("idMunicipioVehiculo");
	var selectPuebloVeh = document.getElementById("idPuebloVehiculo");
	var direccVehiculo = document.getElementById("id_direccV");	

	if (selectProvinciaVeh.value == '-1' 
		&& selectMunicipioVeh.value ==  '-1' 
			&& selectPuebloVeh.value ==  '-1' ){
		document.getElementById('idProvinciaIVTM').value=selectProvincia.options[selectProvincia.selectedIndex].text;
		document.getElementById('idMunicipioIVTM').value=selectMunicipio.options[selectMunicipio.selectedIndex].text;
		document.getElementById('idPuebloIVTM').value=selectPueblo.options[selectPueblo.selectedIndex].text;
		
	}else {
		document.getElementById('idProvinciaIVTM').value=selectProvinciaVeh.options[selectProvinciaVeh.selectedIndex].text;
		document.getElementById('idMunicipioIVTM').value=selectMunicipioVeh.options[selectMunicipioVeh.selectedIndex].text;
		document.getElementById('idPuebloIVTM').value=selectPuebloVeh.options[selectPuebloVeh.selectedIndex].text;	
	
	}
	
	
	
/*	
	
	if (selectProvincia.options[selectProvincia.selectedIndex].text!="Seleccione Provincia") document.getElementById('idProvinciaIVTM').value=selectProvincia.options[selectProvincia.selectedIndex].text;
	else document.getElementById('idProvinciaIVTM').value = ""; 
	if (selectMunicipio.options[selectMunicipio.selectedIndex].text!="Seleccione Municipio") document.getElementById('idMunicipioIVTM').value=selectMunicipio.options[selectMunicipio.selectedIndex].text;
	else document.getElementById('idMunicipioIVTM').value = "";
	if (selectPueblo.options[selectPueblo.selectedIndex].text!="Seleccione Pueblo") document.getElementById('idPuebloIVTM').value=selectPueblo.options[selectPueblo.selectedIndex].text;
	else document.getElementById('idPuebloIVTM').value= "";*/
}

function comprobarIBANEs (){
	var cc = document.getElementById("idCCIvtm");
	if (cc!=null){
		if (cc.value.substring(0,2) != 'ES' || cc.value.length != 24){
			cc.value = "";
		} 
	}
	if (cc==null){
		return alert("La cuenta corriente es un campo obligatorio");
	}
}

function bloquearImporteIVTM(){
	if (document.getElementById("idExentoIvtm").checked){
		document.getElementById("importeIvtm").value = "0";
		document.getElementById("importeIvtm").readOnly = true;
		cambiarColorFondoElemento("importeIvtm", "#EEEEEE");
	} else {
		document.getElementById("importeIvtm").readOnly = false;
		cambiarColorFondoElemento("importeIvtm", "");
	}
}

function activarMedioAmbienteIVTM(){
	if (document.getElementById("idBonMedAmbIvtm") != null) {
		if (document.getElementById("idBonMedAmbIvtm").checked){
			document.getElementById("idPorcentajeBonMedAmbIvtm").readOnly = false;
			cambiarColorFondoElemento("idPorcentajeBonMedAmbIvtm", "");
		} else {
			document.getElementById("idPorcentajeBonMedAmbIvtm").value = "0";
			document.getElementById("idPorcentajeBonMedAmbIvtm").readOnly = true;
			cambiarColorFondoElemento("idPorcentajeBonMedAmbIvtm", "#EEEEEE");
		}
	}
}

function validarPorcentajeBonificacionMedioAmbiente(e){
	if (document.getElementById("idPorcentajeBonMedAmbIvtm").value > 100){
		document.getElementById("idPorcentajeBonMedAmbIvtm").value = 100;
	}
}

function cambiarColorFondo (color1, color2){
	cambiarColorFondoElemento("importeIvtm", color1);
	cambiarColorFondoElemento("idNrcIvtm", color1);
}

function cambiarColorFondoElemento (campo, color){
	document.getElementById(campo).style.backgroundColor = color;
} 

function cargarDireccionPago() {
	if (document.getElementById('idProvinciaTitular')!= null ){
		document.getElementById('idProvinciaIVTM').value=document.getElementById('idProvinciaTitular').options[document.getElementById('idProvinciaTitular')].text;
	}
	if (document.getElementById('municipioSeleccionadoTitular')!=null){
		document.getElementById('idMunicipioIVTM').value=document.getElementById('municipioSeleccionadoTitular').value;
	}
	if(document.getElementById('puebloSeleccionadoTitular')!= null){
		document.getElementById('idPuebloIVTM').value=document.getElementById('puebloSeleccionadoTitular').value;	
	}
}


function cambiarElementosPorPaginaConsultaModificacionIVTM() {

	document.location.href = 'navegarModificacionIVTM.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function cambiarElementosPorPaginaConsultaDeudaIVTM() {

	document.location.href = 'navegarConsultaIVTM.action?resultadosPorPagina='
			+ document.formData.idResultadosPorPagina.value;
}

function limpiarFormularioPeticionesPreviasConsultaIVTM(){
	limpiarFormularioFechasAltaIVTM();
	limpiarFormularioNumColegiadoIVTM();
	limpiarFormularioMatriculaPeticionPreviaConsultaIVTM();
}

function limpiarFormularioMatriculaPeticionPreviaConsultaIVTM(){
	if (document.getElementById("matriculaPeticionPreviaConsultaIVTM")) {
		document.getElementById("matriculaPeticionPreviaConsultaIVTM").value = "";
	}
}

function limpiarFormularioConsultaIVTM(){
	if (document.getElementById("matricula")) {
		document.getElementById("matricula").value = "";
	}
	
}

function limpiarFormularioNumColegiadoIVTM(){
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}
}

function limpiarFormularioFechasAltaIVTM(){
	if (document.getElementById("diaAltaIni")) {
		document.getElementById("diaAltaIni").value = "";
	}
	if (document.getElementById("mesAltaIni")) {
		document.getElementById("mesAltaIni").value = "";
	}
	if (document.getElementById("anioAltaIni")) {
		document.getElementById("anioAltaIni").value = "";
	}

	if (document.getElementById("diaAltaFin")) {
		document.getElementById("diaAltaFin").value = "";
	}
	if (document.getElementById("mesAltaFin")) {
		document.getElementById("mesAltaFin").value = "";
	}
	if (document.getElementById("anioAltaFin")) {
		document.getElementById("anioAltaFin").value = "";
	}
}



function limpiarFormularioIVTMModificacion(){
	
	if (document.getElementById("autoliquidacion")) {
		document.getElementById("autoliquidacion").value = "";
	}
	if (document.getElementById("numExpediente")) {
		document.getElementById("numExpediente").value = "";
	}
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}

	if (document.getElementById("diaAltaIni")) {
		document.getElementById("diaAltaIni").value = "";
	}
	if (document.getElementById("mesAltaIni")) {
		document.getElementById("mesAltaIni").value = "";
	}
	if (document.getElementById("anioAltaIni")) {
		document.getElementById("anioAltaIni").value = "";
	}
	
	if (document.getElementById("diaAltaFin")) {
		document.getElementById("diaAltaFin").value = "";
	}
	if (document.getElementById("mesAltaFin")) {
		document.getElementById("mesAltaFin").value = "";
	}
	if (document.getElementById("anioAltaFin")) {
		document.getElementById("anioAltaFin").value = "";
	}
	if (document.getElementById("bastidor")) {
		document.getElementById("bastidor").value = "";
	}
}


function consultaDeudaIVTM() {
	//abrirVentanaPedirMatricula();
	doPost('formData', 'consultarConsultaIVTM.action');
}

function abrirVentanaPedirMatricula(){
		
		var ex1 = new RegExp("[0-9]{4}[A-Z]{3}");
		var ex2 = new RegExp("[A-Z]{1,2}[0-9]{4}[A-Z]{0,2}");
		var $dest = $("#emergenteMatricula");
		$.post("popupMatriculaConsultaIVTM.action", function(data) {
			
			
			
			
			if (data.toLowerCase().indexOf("<html")<0) {
				
				$dest.empty().append(data).dialog({
					modal : false,
					show : {
						effect : "blind",
						duration : 300
					},
					resizable:true,
					height: 'auto',
					width: 'auto',
					closeOnEscape: true,
					title : "Introduzca la matrícula",
					title_html : true,
					dialogClass : 'no-close',
					open: function(event, ui) {
				        $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
				    },
					buttons : {
						PedirMatricula : {
							class: 'leftButton',
							text: 'Consultar',
							click: function() {
							
								//EPuerro A veces tarda en encolar la consulta. Deshabilito los demás botones por seguridad.
								document.getElementById("bBuscarPeticionesPrevias").disabled=true;
								document.getElementById("bLimpiar").disabled=true;
								document.getElementById("bBuscar").disabled=true;
								
								var numMatricula = document.getElementById("numMatricula").value;
								if(numMatricula===""){
									return alert("Debe introducir al menos una matrícula");
								
								}
								if(ex1.test(numMatricula) || ex2.test(numMatricula)){
									document.forms[0].action = "detalleConsultaIVTM.action?" +
									"ivtmConsultaDto.matricula="+numMatricula;
									$(this).dialog("close");
									document.forms[0].submit();
									
								}else{
									return alert("El formato de matrícula introducido no es correcto");
								}	
								
										
							}
							
						},
						Cerrar : {
							class: 'rightButton',
							text: 'Cerrar',
							click : function() {
								$(this).dialog("close");
							}
						}
					}
				});
				
			} 
			
		});
	
	
}



function buscarPeticionesPreviasConsultaIVTM(){
	document.formData.action = "buscarConsultaIVTM.action";
	document.formData.submit();
}

function buscarAutoliquidacionIVTM() {
	document.formData.action = "buscarModificacionIVTM.action";
	document.formData.submit();
}

function enviarModificacionIVTM() {
	var c = confirm('El usuario asume la responsabilidad de que los cambios realizados para la modificaci\u00f3n del IVTM, tambi\u00e9n ser\u00e1n reflejados como cambios en el tr\u00e1mite de matriculaci\u00f3n');
	if (c == true) {
		document.formData.action = "modificarModificacionIVTM.action";
		document.formData.submit();
	}
	return true;
}


function volverListadoModificacionIVTM() {
		document.formData.action = "buscarModificacionIVTM.action";
		document.formData.submit();
	
	return true;
}


function consultarPeticionIVTM() {
	document.formData.action = "consultaPeticionModificacionIVTM.action";
	document.formData.submit();
}

function consultarRespuestaIVTM(){
	document.formData.action = "consultaRespuestaModificacionIVTM.action";
	document.formData.submit();	
}

function limpiarFormConsultaIVTM(){
	limpiarFormConsulta();
	if (document.getElementById("docautorizado")) {
		document.getElementById("docautorizado").value = "";
	}
	if (document.getElementById("autoliquidacion")) {
		document.getElementById("autoliquidacion").value = "";
	}
	
}

function autoliquidarMatriculacionIVTM() {
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez autoliquidado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en la autoliquidaci\u00f3n');
	if (c == true) {
		document.formData.action = "autoliquidarIVTMAltasMatriculacionMatw.action#PagosEImpuestos";
		document.formData.submit();
	}
	return true;
}

function autoliquidarIVTMMatriculacion() {
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez autoliquidado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en la autoliquidaci\u00f3n');
	if (c == true) {
		document.formData.action = "autoliquidarIVTMAltaTramiteTraficoMatriculacion.action#PagosEImpuestos";
		document.formData.submit();
	}
	return true;

}

function pagoMatriculacionIVTM() {
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez pagado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en el pago');
	if (c == true) {
		document.formData.action = "pagoIVTMAltasMatriculacionMatw.action#PagosEImpuestos";
		document.formData.submit();
	}
	return true;

}

function pagoIVTMMatriculacion() {
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez pagado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en el pago');
	if (c == true) {
		document.formData.action = "pagoIVTMAltaTramiteTraficoMatriculacion.action#PagosEImpuestos";
		document.formData.submit();
	}
	return true;

}

function autoliquidarMasivoIVTM(boton) {
	//TODO MPC. Esta funcion hay que rehacerla (y cambiar también en el action de consulta de trámites), para no enviar los expedientes así, sino de la forma en la que se va a hacer en el resto de la aplicación.
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez autoliquidado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en la autoliquidaci\u00f3n');
	if (c == true) {
		if (numCheckedImprimir() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		
		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var i = 0;
		
		while (checks[i] != null) {
			if (checks[i].checked) {
				if (codigos == "") {
					codigos += checks[i].value;
				} else {
					codigos += "/" + checks[i].value;
				}
			}
			i++;
		}
		mostrarLoadingConsultar(boton);
	    document.formData.action="autoliquidarIVTMMasivoConsultaTramiteTrafico.action?numsExpediente="	+ codigos;       
	    document.formData.submit();
	}
	return true;
}

function pagoIVTM(boton) {
	//TODO MPC. Esta funcion hay que rehacerla (y cambiar también en el action de consulta de trámites), para no enviar los expedientes así, sino de la forma en la que se va a hacer en el resto de la aplicación.
	var c = confirm('El Gestor Administrativo colegiado asume la responsabilidad de que una vez pagado el IVTM, las modificaciones en el tr\u00e1mite de matriculaci\u00f3n no ser\u00e1n reflejadas en el pago');
	if (c == true) {
		var numCheck = numCheckedImprimir();
		if (numCheck == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}
		if (numCheck > 1) {
			alert("No puede seleccionar m\u00E1s de un tr\u00E1mite");
			return false;
		}
		var checks = document.getElementsByName("listaExpedientes");
		var codigo = "";
		
		if (checks[0] != null) {
			if (checks[0].checked) {
				codigo = checks[0].value;
			}
		}
		
		mostrarLoadingConsultar(boton);
	    document.formData.action="pagoIVTMConsultaTramiteTrafico.action?numExpediente=" + codigo;       
	    document.formData.submit();
	}
	return true;
}

function mostrarBloqueAutoliquidados() {
	var divValidos = document.getElementById("bloqueAutoliquidados");
	var desplegable = document.getElementById("imgAutoliquidados");
	
	if(divValidos.style.display == 'none'){
		divValidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";	
	}
	else if(divValidos.style.display == 'block'){
		divValidos.style.display='none';
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";	
	}

}

function mostrarBloqueFallidosAutoliquidados() {
	var divFallidos = document.getElementById("bloqueFallidosAutoliquidadosIVTM");
	var desplegable = document.getElementById("imgFallidosAutoliquidados");
	
	if(divFallidos.style.display == 'none'){
		divFallidos.style.display='block';
		desplegable.src = "img/minus.gif";
		desplegable.alt= "Ocultar";	
	}
	else if(divFallidos.style.display == 'block'){
		divFallidos.style.display='none';	
		desplegable.src = "img/plus.gif";
		desplegable.alt= "Mostrar";	
	}
}


function validaMatricula(){
	var mat = document.getElementById("matricula").value;
	var ex1 = new RegExp("^[0-9]{4} [A-Z]{3}$");
	var ex2 = /^[0-9]{4} [A-Z]{3}$/;

		if(ex1.test(mat)){
			alert("Ok");
		}else{
			alert("Error");
		}
		
		if(ex2.test(mat)){
			alert("Ok");
		}else{
			alert("Error");
		}	
}