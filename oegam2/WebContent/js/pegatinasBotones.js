var opciones_ventana = 'width=850,height=200,top=250,left=200';
var tipoNotificacion= "OK";

function abrirVentanaPedirStock(){
	
	var jefatura = "";
	if (document.getElementById('idJefaturaJPT') == null){
		jefatura = document.getElementById('idJefaturaJPTSelect').value;
	}else{
		jefatura = document.getElementById('idJefaturaJPT').value;
	}
	if (jefatura == ""){
		alert("Debe seleccionar una jefatura antes de pedir stock");
	}else{
	
		var $dest = $("#emergenteStock");
		
		$.post("popupStockGestionPegatinas.action", function(data) {
			
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
					title : "Introduzca el tipo y numero de distintivos",
					title_html : true,
					dialogClass : 'no-close',
					open: function(event, ui) {
				        $(".ui-dialog-titlebar-close", ui.dialog | ui).hide();
				    },
					buttons : {
						PedirStock : {
							class: 'leftButton',
							text: 'Pedir Stock',
							click: function() {
							
								var tipo = document.getElementById("tipoPegatina").value;
								var numPegatinas = document.getElementById("numPegatinas").value;
								var jefatura = document.getElementById('idJefaturaJPTSelect').value;
								if (tipo != null && tipo != '' && tipo != 'undefined' && 
										numPegatinas != null && numPegatinas != '' && numPegatinas != 'undefined'){
									if (numPegatinas % 150 == 0){
										document.forms[0].action = "pedirStockGestionPegatinas.action?jefaturaJpt="+jefatura+
										"&pegatinasStockPeticionesBean.tipo="+tipo+"&pegatinasStockPeticionesBean.numPegatinas="+numPegatinas;
										$(this).dialog("close");
										document.forms[0].submit();
								
									}
									else{
										alert("El número de pegatinas a solicitar ha de ser múltiplo de 150");
									}
								}else{
									
									alert("Debe de cumplimentar tanto el tipo como el numero de pegatinas");
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
}

function abrirEvolucion(idPegatina) {
	
	var ventana = window.open('verEvolucionImpresionPegatinas.action?codigo=' + idPegatina , 'popup',
			opciones_ventana);
	document.forms[0].action = "";
}

function cerrar() {
	this.close();
}

function descargarPdfPegatina(boton){
	
	if (numCheckedImpresionPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para descargar");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPegatina");
	var pegatina = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (pegatina == "") {
				pegatina += checks[i].value;
			} else {
				pegatina += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "imprimirImpresionPegatinas.action?idPegatina="
			+ pegatina;
	document.forms[0].submit();
	return true;
}

function marcarImpresoPegatinas(boton){
	
	if (numCheckedImpresionPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para marcar impresa");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPegatina");
	var pegatina = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (pegatina == "") {
				pegatina += checks[i].value;
			} else {
				pegatina += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "mostrarVistaImpresionPegatinas.action?idPegatina=" + pegatina;
	document.forms[0].submit();
	return true;
}

function numCheckedImpresionPegatinas() {
	var checks = document.getElementsByName("listaChecksPegatina");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}	
	return numChecked;
}

function openPopupInvalido(boton){
	var opciones_ventana2 = 'width=350,height=225,top=250,left=400';
	window.open('abrirPopupNotificaImpresionPegatinas.action','popup',opciones_ventana2);	
}

function notificar(boton){
	var motivo = document.getElementById("motivo").value;
	var identificadorPegatina = document.getElementById("identificadorPegatina").value;
	if (motivo != null && motivo != ''&& motivo != 'undefined' && 
			identificadorPegatina != null && identificadorPegatina != '' && identificadorPegatina != 'undefined'){
		document.forms[0].action = "notificarPegatinasImpresionPegatinas.action";
		document.forms[0].submit();
		window.close();
	}else{
		alert("Debe de cumplimentar el identificador de la pegatina y el motivo de la notificación");
	}
	
	return true;
}

function pedirStock(boton){
	var tipo = document.getElementById("tipo").value;
	var numPegatinas = document.getElementById("numPegatinas").value;
	var jefatura = document.getElementById('idJefaturaJpt').value;
	if (tipo != null && tipo != ''&& tipo != 'undefined' && 
			numPegatinas != null && numPegatinas != '' && numPegatinas != 'undefined'){
		if (numPegatinas % 150 == 0){
			document.forms[0].action = "pedirStockGestionPegatinas.action?jefaturaJpt="+jefatura;
			document.forms[0].submit();
			window.close();
		}
		else{
			alert("El número de pegatinas a solicitar ha de ser múltiplo de 150");
		}
	}else{
		
		alert("Debe de cumplimentar el identificador de la pegatina y el motivo de la notificación");
	}
	
	return true;
}

function verDetalleStock(tipo){
	document.location.href='detalleConsultaPegatinas.action?tipo=' + tipo ;
}

function limpiarFormularioStockPegatina() {

	if (document.getElementById("tipo").value != null ) {
		document.getElementById("tipo").value = "";
	}
	if (document.getElementById("estado").value != null ) {
		document.getElementById("estado").value = "";
	}
}

function consultaStock() {

	document.formData.action = "buscarConsultaPegatinas.action";
	document.formData.submit();
}

function abrirPeticiones(idStock) {
	
	document.forms[0].action="inicioPegatinasPeticionStock.action?idStock=" + idStock;
	document.forms[0].submit();
	return true;
}

function abrirHistorico(idStock) {
	
	document.forms[0].action="inicioPegatinasHistoricoStock.action?idStock=" + idStock;
	document.forms[0].submit();
	return true;
}

function buscarPegatinasPeticionStock(){
	document.formData.action = "buscarPegatinasPeticionStock.action";
	document.formData.submit();
}

function buscarPegatinasHistoricoStock(){
	document.formData.action = "buscarPegatinasHistoricoStock.action";
	document.formData.submit();
}

function volverConsulta() {
	document.forms[0].action = "inicioGestionPegatinas.action";
	document.forms[0].submit();
}


function volverNotificarImpresion() {
	document.forms[0].action = "inicioImpresionPegatinas.action";
	document.forms[0].submit();
}
function confirmarRecepcion(boton){
	
	if (numCheckedPeticionesPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para confirmar recepción");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPeticion");
	var datos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (datos == "") {
				datos += checks[i].value;
			} else {
				datos += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "confirmarRecepcionPegatinasPeticionStock.action?datos="
			+ datos;
	document.forms[0].submit();
	return true;
}

function numCheckedPeticionesPegatinas() {
	var checks = document.getElementsByName("listaChecksPeticion");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}	
	return numChecked;
}

function anularPeticion(boton){
	
	if (numCheckedPeticionesPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para anular petición");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPeticion");
	var datos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (datos == "") {
				datos += checks[i].value;
			} else {
				datos += "-" + checks[i].value;
			}
		}
		i++;
	}
	
	document.forms[0].action = "anularPeticionPegatinasPeticionStock.action?datos="
			+ datos;
	document.forms[0].submit();
	return true;
}



function activarRadiosImprimirDistintivo(value){

	if(value === '1')
    {
		tipoNotificacion= "Rotura";
		document.getElementById('tipoNotificacion').value = tipoNotificacion;
		document.getElementById('impresoRoturaKO').checked = true;
        document.getElementById('impresoOK').checked = false;
        document.getElementById('impresoErrorKO').checked = false;
    }
	if(value === '2')
    {

		tipoNotificacion= "KO";
		document.getElementById('tipoNotificacion').value = tipoNotificacion;
        document.getElementById('impresoErrorKO').checked = true;
        document.getElementById('impresoRoturaKO').checked = false;
        document.getElementById('impresoOK').checked = false;
    }
	if(value === '3')
    {
		tipoNotificacion= "OK";
		document.getElementById('tipoNotificacion').value = tipoNotificacion;
        document.getElementById('impresoOK').checked = true;
        document.getElementById('impresoRoturaKO').checked = false;
        document.getElementById('impresoErrorKO').checked = false;
    }


}


var opciones_ventana3 = 'width=850,height=200,top=250,left=200';

function abrirEvolucionPeticion(idPeticion) {
	
	var ventana = window.open('verEvolucionPegatinasPeticionStock.action?idPeticion=' + idPeticion , 'popup',
			opciones_ventana3);
	document.forms[0].action = "";
}


function notificarTodos(){
	
	if (numCheckedImpresionPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para notificar");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPegatina");
	var pegatina = "";
	var totalPegatinas="";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (pegatina == "") {
				pegatina += checks[i].value;
				totalPegatinas++
			} else {
				pegatina += "-" + checks[i].value;
				totalPegatinas++
			}
		}
		i++;
	}
	
	if(tipoNotificacion=='KO' || tipoNotificacion=='Rotura'){
		var ventana = window.open('identificadoresImpresionPegatinas.action?listaPrueba='+pegatina+'&tipoNotificacion='+tipoNotificacion, 'popup', opciones_ventana3);
		document.formData.action = "";
	}else{	
		document.getElementById('listaPrueba').value = pegatina;
		document.formData.action = "notificarImpresionPegatinas.action?listaPrueba"+pegatina;
		document.formData.submit();
	return true;
	}

}


function enviarIdentificadores(){
	
	if (numCheckedImpresionPegatinas() == 0) {
		alert("Debe seleccionar alguna pegatina para notificar");
		return false;
	}

	var checks = document.getElementsByName("listaChecksPegatina");
	var pegatina = "";
	var totalPegatinas="";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (pegatina == "") {
				pegatina += checks[i].value;
				totalPegatinas++
			} else {
				pegatina += "-" + checks[i].value;
				totalPegatinas++
			}
		}
		i++;
	}
	
	if(tipoNotificacion=='KO' || tipoNotificacion=='Rotura'){
		var ventanaConf = 'width=3500,height=2250,top=2500,left=4000';
		var ventana = window.open('identificadoresImpresionPegatinas.action?listaPrueba=' + pegatina , 'popup', ventanaConf);
		document.formData.action = "";
	}else{
		
		document.getElementById('listaPrueba').value = pegatina;
		
		document.formData.action = "notificarImpresionPegatinas.action";
		document.formData.action();
	return true;
	}

}



function notificar(){
	
	if (isEmptyInput() != 0) {
		alert("Debe rellenar los identificadores de todas las peticiones");
		return false;
	}
	
	
	var inputs = document.querySelectorAll("#tablaIdentificadores input[type=text]");
	var datos = "";
	var i = 0;
	while (inputs[i] != null) {
		if (inputs[i].value!="") {
			if (datos == "") {
				datos += inputs[i].value;
			} else {
				datos += "," + inputs[i].value;
			}
		}
		i++;
	}
	
	document.formData.action = "confirmarInvalidosImpresionPegatinas.action?listaPrueba=" + datos;
	document.formData.submit();
		
	return true;
	

}

function isEmptyInput() {
	
	var inputs = document.querySelectorAll("#tablaIdentificadores input[type=text]");
	var numInput = 0;
	for(var i=0; i<inputs.length; i++) {
		if(inputs[i].value =='') numInput++;
	}	
	return numInput;
}

function limpiarFormularioStockPegatina() {
	if (document.getElementById("jefatura").value != null ) {
		document.getElementById("jefatura").value = "";
	}
	if (document.getElementById("tipo").value != null ) {
		document.getElementById("tipo").value = "";
	}
	if (document.getElementById("estado").value != null ) {
		document.getElementById("estado").value = "";
	}
}

