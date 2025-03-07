
var contadorFecha = 0;

function modificarNumFactura(tableId) {
	var numFactura = document.getElementById("idNumFactura").value;
	var nuevaFactura = "";
	var nuevaFecha = document.getElementById("idFechaFactura").value;
	nuevaFecha =  nuevaFecha.substring(3, 5) + nuevaFecha.substring(8, 10);
	
	if (numFactura.substring(0, 4) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(0, 4);		
	}
	if (numFactura.substring(1, 5) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(1, 5);

	} else if (numFactura.substring(2, 6) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(2, 6);

	} else if (numFactura.substring(3, 7) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(3, 7);

	} else if (numFactura.substring(4, 8) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(4, 8);

	} else if (numFactura.substring(5, 9) == document.getElementById("NumColegiado").value) {
		nuevaFactura = numFactura.substring(5, 9);
	}
	
	
	document.getElementById("idNumFactura").value = document.getElementById("idNumSerie").value
													+ nuevaFactura + nuevaFecha + document.getElementById("numeracion").value;

	if (document.getElementById("idNumSerie").value.length > 1
			&& document.getElementById("idNumSerie").value.substring(0, 1) != "F") {
		document.getElementById("idNumSerie").value = "F"
				+ document.getElementById("idNumSerie").value;
		document.getElementById("idNumFactura").value = "F" + nuevaFactura + nuevaFecha	+ document.getElementById("numeracion").value;
	}

	if (document.getElementById("idNumSerie").value.length == 1) {
		document.getElementById("idNumSerie").value = "F";
		if (document.getElementById("idNumFactura").value.substring(0, 1) != "F")
			document.getElementById("idNumFactura").value = "F" + nuevaFactura + nuevaFecha	+ document.getElementById("numeracion").value;
	}
	if (document.getElementById("idNumSerie").value.length == 0) {
		document.getElementById("idNumSerie").value = "F";
		if (document.getElementById("idNumFactura").value.substring(0, 1) != "F")
			document.getElementById("idNumFactura").value = "F" + nuevaFactura + nuevaFecha	+ document.getElementById("numeracion").value;
	}
	
	document.getElementById("FechaFactHidden").value = document.getElementById("idFechaFactura").value;
}

function mostrarLoadingFacturacion(boton){
	document.getElementById("bloqueLoadingFactura").style.display = "block";
}

function modificarFacturaRetardo(){	
		
	if (contadorFecha < 6){		
		if (document.getElementById("FechaFactHidden").value != document.getElementById("idFechaFactura").value){
			modificarNumFactura(this);
		}else{
			setTimeout("modificarFacturaRetardo()", 8000);			
			contadorFecha++;
	
		}
		
	}
}

function rellenarCerosNumeracion() {

	while (document.getElementById("numeracion").value.length < 5) {
		document.getElementById("numeracion").value = "0"
				+ document.getElementById("numeracion").value;
	}

}

function rellenarFechaFactura() {

	var nuevaFecha = document.getElementById("idFechaFactura").value;
	
	var separador         = "/";
	
	var dia = "";
	var mes = "";
	var anio = "";
	
	if (nuevaFecha != null && nuevaFecha != "") {
		var elementoFecha = nuevaFecha.split(separador);
				
		if (elementoFecha[0].length == 1){
			
			dia = "0"+ elementoFecha[0];
			
		} else{
			dia = elementoFecha[0];
		}
				
		if (elementoFecha[1].length == 1){
			
			mes = "0"+ elementoFecha[1];
		} else{
			mes = elementoFecha[1];
		}
				
		if (elementoFecha[2].length == 2){
			anio = "20"+ elementoFecha[2];
		} else{
			anio = elementoFecha[2];
		}
	
		nuevaFecha =  dia + "/" + mes + "/" + anio;				
		document.getElementById("idFechaFactura").value = nuevaFecha;
		document.getElementById("FechaFactHidden").value = nuevaFecha;
	}
}

function rellenarFechaNacimientoFactura() {

	var diaNac = document.getElementById("diaNacTitular").value;
	var mesNac = document.getElementById("mesNacTitular").value;
	
	
	if (diaNac != null && diaNac != "") {		
				
		if (diaNac.length == 1){
			
			document.getElementById("diaNacTitular").value = "0"+ diaNac;
			
		} else{
			document.getElementById("diaNacTitular").value = elementoFecha[0];
		}
				
		if (mesNac.length == 1){
			
			document.getElementById("mesNacTitular").value = "0"+ mesNac;
		} else{
			document.getElementById("mesNacTitular").value = mesNac;
		}	
	}
}

function generarPDFFacturacion(boton, param) {
	
	calcularFactura();
	var codigos = document.getElementById("idNumFactura").value;
	var mensaje = document.getElementById("mensajeImprimir").value;

	if (param == "generar")
		mensaje = document.getElementById("mensajePDF").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {
		loadingValidarPDFAlta();
		if (param == "imprimir") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=pdf&donde=altafactura&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (param == "generar") {
			document.formData.action = "generarPDFAltaGestionFacturar.action?modelo=pdf&donde=altafactura&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		}
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function generarBorradorFacturacion(boton, param) {
	calcularFactura();
	var mensaje = document.getElementById("mensajeBorrador").value;
	var codigos = document.getElementById("idNumFactura").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {
		loadingValidarBorradorAlta();
		if (param == "imprimir") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=pdf&donde=altafactura&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (param == "generar") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&donde=altafactura&estado="
					+ document.getElementById("estadoPdf").value + "&numsFactura=" + codigos;
		}
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function guardarFactura(){
	if (document.getElementById("idNumCodigo").value != null
			&& document.getElementById("idNumCodigo").value != "") {
		if (!validaNumeroEntero(document.getElementById("idNumCodigo").value)) {
			alert("Numero de Codigo No Valido");
			return false;
		}
	}

	if (document.getElementById("telefonoCliente").value != null
			&& document.getElementById("telefonoCliente").value != "") {
		if (!validaNumeroEntero(document.getElementById("telefonoCliente").value)) {
			alert("Numero de Telefono No Valido");
			return false;
		}
		if (document.getElementById("telefonoCliente").value.length < 9) {
			alert("Longitud del Numero de Telefono No Valida");
			return false;
		}
	}

	var tableHonorario = document.getElementById('mitablahonorarioDis');
	
	for(var x = 0; x < tableHonorario.childNodes.length; x++){			
		if( tableHonorario.childNodes[x]!='undefined'){
			if(tableHonorario.childNodes[x].tagName=='TBODY') var tbodyHonorario = tableHonorario.childNodes[x];
		}	
	}
	var posHonorario = tbodyHonorario.rows.length;
	
	for ( var i = 0; i < posHonorario; i++) {				
		if (document.getElementById("idHonorario" + i).value != null
				&& document.getElementById("idHonorario" + i).value != "") {
			if (isNaN(document.getElementById("idHonorario" + i).value)) {
				alert("Precio del Honorario " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idHonorarioDescuento" + i).value != null
				&& document.getElementById("idHonorarioDescuento" + i).value != "") {
			if (isNaN(document.getElementById("idHonorarioDescuento" + i).value)) {
				alert("Descuento del Honorario " + i + " No Valido");
				return false;
			}
		}
	}

	var tableSuplido = document.getElementById('mitablaSuplidoDis');

	for(var x = 0; x < tableSuplido.childNodes.length; x++){			
		if( tableSuplido.childNodes[x]!='undefined'){
			if(tableSuplido.childNodes[x].tagName=='TBODY') var tbodySuplido = tableSuplido.childNodes[x];
		}	
	}
	
	var posSuplido = tbodySuplido.rows.length;
	

	for (var m = 0; m < posSuplido; m++) {
	
		if (document.getElementById("idSuplido" + m).value != null
				&& document.getElementById("idSuplido" + m).value != "") {
			if (isNaN(document.getElementById("idSuplido" + m).value)) {
				alert("Precio del Suplido " + m + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idSuplidoDescuento" + m).value != null
				&& document.getElementById("idSuplidoDescuento" + m).value != "") {
			if (isNaN(document.getElementById("idSuplidoDescuento" + m).value)) {
				alert("Descuento del Suplido " + m + " No Valido");
				return false;
			}
		}
	}

	var tableGasto = document.getElementById('mitablaGastoDis');

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var posGasto = tbodyGasto.rows.length;
	

	for (var n = 0; n < posGasto; n++) {
	
		if (document.getElementById("idGasto" + n).value != null
				&& document.getElementById("idGasto" + n).value != "") {
			if (isNaN(document.getElementById("idGasto" + n).value)) {
				alert("Precio del Gasto " + n + " No Valido");
				return false;
			}
		}

	}

	if (document.getElementById("idOtro").value != null
			&& document.getElementById("idOtro").value != "") {
		if (isNaN(document.getElementById("idOtro").value)) {
			alert("Precio del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idOtroDescuento").value != null
			&& document.getElementById("idOtroDescuento").value != "") {
		if (isNaN(document.getElementById("idOtroIVA").value)) {
			alert("Descuento del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idFondo").value != null
			&& document.getElementById("idFondo").value != "") {
		if (isNaN(document.getElementById("idFondo").value)) {
			alert("Precio del Campo Prov. Fondos No Valido");
			return false;
		}
	}
	mostrarLoadingFacturacion();
	loadingValidarGuardarFactura();
	calcularFactura();	
	pasarHonorarioAjax('0', "defaultConcepto");
	
	setTimeout("guardaFacturaDelay()", 3000);
}


function guardaFacturaDelay(){			
	document.formData.action = "guardarNuevaFacturaGestionFacturar.action";
	document.formData.submit();
}

function validarGuardarFactura() {	
	calcularFactura();	
	if (document.getElementById("idNumCodigo").value != null
			&& document.getElementById("idNumCodigo").value != "") {
		if (!validaNumeroEntero(document.getElementById("idNumCodigo").value)) {
			alert("Numero de Codigo No Valido");
			return false;
		}
	}

	if (document.getElementById("telefonoCliente").value != null
			&& document.getElementById("telefonoCliente").value != "") {
		if (!validaNumeroEntero(document.getElementById("telefonoCliente").value)) {
			alert("Numero de Telefono No Valido");
			return false;
		}
		if (document.getElementById("telefonoCliente").value.length < 9) {
			alert("Longitud del Numero de Telefono No Valida");
			return false;
		}
	}

	if (document.getElementById("idIRPF1").value != null
			&& document.getElementById("idIRPF1").value != "") {
		if (isNaN(document.getElementById("idIRPF1").value)) {
			alert("IRPF No Valido");
			return false;
		}
	}

	var tableHonorario = document.getElementById('mitablahonorarioDis');
	var posHonorario = tableHonorario.rows.length;
	for ( var i = 1; i < posHonorario; i++) {
		if (document.getElementById("idHonorario" + i).value != null
				&& document.getElementById("idHonorario" + i).value != "") {
			if (isNaN(document.getElementById("idHonorario" + i).value)) {
				alert("Precio del Honorario " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idHonorarioDescuento" + i).value != null
				&& document.getElementById("idHonorarioDescuento" + i).value != "") {
			if (isNaN(document.getElementById("idHonorarioDescuento" + i).value)) {
				alert("Descuento del Honorario " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idHonorarioDescripcion" + i).value != null
				&& document.getElementById("idHonorarioDescripcion" + i).value != ""
				&& document.getElementById("idHonorarioDescripcion" + i).value != "Introduzca la descripcion") {
			if (document.getElementById("idHonorario" + i).value == null
					|| document.getElementById("idHonorario" + i).value == "") {
				alert("Debe introducir un Precio Valido en el Campo Honorario "
						+ i + " si introduce una descripcion");
				return false;
			}
		}

	}

	var tableSuplido = document.getElementById('mitabla');
	var posSuplido = tableSuplido.rows.length;
	for ( var i = 0; i < posSuplido; i++) {
		if (document.getElementById("idSuplido" + i).value != null
				&& document.getElementById("idSuplido" + i).value != "") {
			if (isNaN(document.getElementById("idSuplido" + i).value)) {
				alert("Precio del Suplido " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idSuplidoDescuento" + i).value != null
				&& document.getElementById("idSuplidoDescuento" + i).value != "") {
			if (isNaN(document.getElementById("idSuplidoDescuento" + i).value)) {
				alert("Descuento del Suplido " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idSuplidoDescripcion" + i).value != null
				&& document.getElementById("idSuplidoDescripcion" + i).value != ""
				&& document.getElementById("idSuplidoDescripcion" + i).value != "Introduzca la descripcion") {
			if (document.getElementById("idSuplido" + i).value == null
					|| document.getElementById("idSuplido" + i).value == "") {
				alert("Debe introducir un Precio Valido en el Campo Suplido "
						+ i + " si introduce una descripcion");
				return false;
			}
		}

	}

	var tableGasto = document.getElementById('mitablagasto');
	var posGasto = tableGasto.rows.length;
	for ( var i = 0; i < posGasto; i++) {
		if (document.getElementById("idGasto" + i).value != null
				&& document.getElementById("idGasto" + i).value != "") {
			if (isNaN(document.getElementById("idGasto" + i).value)) {
				alert("Precio del Gasto " + i + " No Valido");
				return false;
			}
		}
		if (document.getElementById("idGastoDescripcion" + i).value != null
				&& document.getElementById("idGastoDescripcion" + i).value != ""
				&& document.getElementById("idGastoDescripcion" + i).value != "Introduzca la descripcion") {
			if (document.getElementById("idGasto" + i).value == null
					|| document.getElementById("idGasto" + i).value == "") {
				alert("Debe introducir un Precio Valido en el Campo Gasto " + i
						+ " si introduce una descripcion");
				return false;
			}
		}
	}

	if (document.getElementById("idOtro").value != null
			&& document.getElementById("idOtro").value != "") {
		if (isNaN(document.getElementById("idOtro").value)) {
			alert("Precio del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idOtroDescuento").value != null
			&& document.getElementById("idOtroDescuento").value != "") {
		if (isNaN(document.getElementById("idOtroIVA").value)) {
			alert("Descuento del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idFondo").value != null
			&& document.getElementById("idFondo").value != "") {
		if (isNaN(document.getElementById("idFondo").value)) {
			alert("Precio del Campo Prov. Fondos No Valido");
			return false;
		}
	}


	if (document.getElementById("idOtroDescripcion").value != null
			&& document.getElementById("idOtroDescripcion").value != ""
			&& document.getElementById("idOtroDescripcion").value != "Introduzca la descripcion") {
		if (document.getElementById("idOtro").value == null
				|| document.getElementById("idOtro").value == "") {
			alert("Debe introducir un Precio Valido en el Campo Otro si introduce una descripcion");
			return false;
		}
	}

	if (document.getElementById("idFondoDescripcion").value != null
			&& document.getElementById("idFondoDescripcion").value != ""
			&& document.getElementById("idFondoDescripcion").value != "Introduzca la descripcion") {
		if (document.getElementById("idFondo").value == null
				|| document.getElementById("idFondo").value == "") {
			alert("Debe introducir un Precio Valido en el Campo Fondos si introduce una descripcion");
			return false;
		}
	}

	loadingValidarGuardarFactura();
	document.formData.action = "altaNuevaFacturaGestionFacturar.action";
	document.formData.submit();
}

function compruebaEstadoFactura(){
	
	var numFactura = document.getElementById("idNumFactura").value;
	var urlcompruebaEstadoFactura = "compruebaEstadoFacturaFacturacionAjax.action?numFactura="+ numFactura;		
	
	var req_generico_comprueba_estado = NuevoAjax();
	
	req_generico_comprueba_estado.onreadystatechange = function () { 
			compruebaEstadoFacturaGenerada(req_generico_comprueba_estado);
		}
	
	req_generico_comprueba_estado.open("POST", urlcompruebaEstadoFactura, true);
	
	req_generico_comprueba_estado.send(null);
}

function compruebaEstadoFacturaGenerada(req_compruebaEstado){
	if (req_compruebaEstado.readyState == 4) { 
		if (req_compruebaEstado.status == 200) { 
			if (req_compruebaEstado.responseText == "true"){
				compruebaPassword();
			}else{
				compruebaEstadoFactura();
			}
		}
	}
}

function compruebaPassword(){	
	calcularFactura();
	var password = prompt("Por favor Introduzca la Clave del colegiado","");
	compruebaPasswordColegiadoModificarFactura(password);
}

function modificarFacturaPasswordRechazada(){
	alert("Error en la password");
	return false;
}

function validarModificarFactura() {		
	if (document.getElementById("idNumCodigo").value != null
			&& document.getElementById("idNumCodigo").value != "") {
		if (!validaNumeroEntero(document.getElementById("idNumCodigo").value)) {
			alert("Numero de Codigo No Valido");
			return false;
		}
	}

	if (document.getElementById("telefonoCliente").value != null
			&& document.getElementById("telefonoCliente").value != "") {
		if (!validaNumeroEntero(document.getElementById("telefonoCliente").value)) {
			alert("Numero de Telefono No Valido");
			return false;
		}
		if (document.getElementById("telefonoCliente").value.length < 9) {
			alert("Longitud del Numero de Telefono No Valida");
			return false;
		}
	}

	var tableHonorario = document.getElementById('mitablahonorarioDis');
	
	for(var x = 0; x < tableHonorario.childNodes.length; x++){			
		if( tableHonorario.childNodes[x]!='undefined'){
			if(tableHonorario.childNodes[x].tagName=='TBODY') var tbodyHonorario = tableHonorario.childNodes[x];
		}	
	}
	var posHonorario = tbodyHonorario.rows.length;
	
	for ( var i = 0; i < posHonorario; i++) {				
		if (document.getElementById("idHonorario" + i).value != null
				&& document.getElementById("idHonorario" + i).value != "") {
			if (isNaN(document.getElementById("idHonorario" + i).value)) {
				alert("Precio del Honorario " + i + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idHonorarioDescuento" + i).value != null
				&& document.getElementById("idHonorarioDescuento" + i).value != "") {
			if (isNaN(document.getElementById("idHonorarioDescuento" + i).value)) {
				alert("Descuento del Honorario " + i + " No Valido");
				return false;
			}
		}
	}

	var tableSuplido = document.getElementById('mitablaSuplidoDis');

	for(var x = 0; x < tableSuplido.childNodes.length; x++){			
		if( tableSuplido.childNodes[x]!='undefined'){
			if(tableSuplido.childNodes[x].tagName=='TBODY') var tbodySuplido = tableSuplido.childNodes[x];
		}	
	}
	
	var posSuplido = tbodySuplido.rows.length;
	

	for (var m = 0; m < posSuplido; m++) {
	
		if (document.getElementById("idSuplido" + m).value != null
				&& document.getElementById("idSuplido" + m).value != "") {
			if (isNaN(document.getElementById("idSuplido" + m).value)) {
				alert("Precio del Suplido " + m + " No Valido");
				return false;
			}
		}

		if (document.getElementById("idSuplidoDescuento" + m).value != null
				&& document.getElementById("idSuplidoDescuento" + m).value != "") {
			if (isNaN(document.getElementById("idSuplidoDescuento" + m).value)) {
				alert("Descuento del Suplido " + m + " No Valido");
				return false;
			}
		}
	}

	var tableGasto = document.getElementById('mitablaGastoDis');

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var posGasto = tbodyGasto.rows.length;
	

	for (var n = 0; n < posGasto; n++) {
	
		if (document.getElementById("idGasto" + n).value != null
				&& document.getElementById("idGasto" + n).value != "") {
			if (isNaN(document.getElementById("idGasto" + n).value)) {
				alert("Precio del Gasto " + n + " No Valido");
				return false;
			}
		}

	}

	if (document.getElementById("idOtro").value != null
			&& document.getElementById("idOtro").value != "") {
		if (isNaN(document.getElementById("idOtro").value)) {
			alert("Precio del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idOtroDescuento").value != null
			&& document.getElementById("idOtroDescuento").value != "") {
		if (isNaN(document.getElementById("idOtroIVA").value)) {
			alert("Descuento del Campo Otro No Valido");
			return false;
		}
	}

	if (document.getElementById("idFondo").value != null
			&& document.getElementById("idFondo").value != "") {
		if (isNaN(document.getElementById("idFondo").value)) {
			alert("Precio del Campo Prov. Fondos No Valido");
			return false;
		}
	}

	
	mostrarLoadingFacturacion();
	loadingValidarGuardarFactura();
	calcularFactura();	
	pasarHonorarioAjax('0', "defaultConcepto");
	
	setTimeout("modificarFacturaDelay()", 3000);
	
	
}

function modificarFacturaDelay(){			
	document.formData.action = "modificarFacturaGestionFacturar.action";
	document.formData.submit();
}

function validarModificarFacturaRectificativa() {
	if (document.getElementById("idNumCodigo").value != null
			&& document.getElementById("idNumCodigo").value != "") {
		if (!validaNumeroEntero(document.getElementById("idNumCodigo").value)) {
			alert("Numero de Codigo No Valido");
			return false;
		}
	}

	if (document.getElementById("telefonoCliente").value != null
			&& document.getElementById("telefonoCliente").value != "") {
		if (!validaNumeroEntero(document.getElementById("telefonoCliente").value)) {
			alert("Numero de Telefono No Valido");
			return false;
		}
		if (document.getElementById("telefonoCliente").value.length < 9) {
			alert("Longitud del Numero de Telefono No Valida");
			return false;
		}
	}

	compruebaEstadoFactura();
}



function loadingValidarGuardarFactura() {
	document.getElementById("idBotonGuardar").readOnly = "true";
	document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	document.getElementById("idBotonGuardar").value = "Procesando";
}

function loadingValidarBorradorFin() {
	if (document.getElementById("bloqueLoadingConsultar")!=null){
		document.getElementById("bloqueLoadingConsultar").style.display = "none";
	}
	document.getElementById("loadingImage").style.display = "none";
	document.getElementById("idGenerarBorrador").value = "Generar Borrador";
	document.getElementById("idGenerarPdf").value = "Generar PDF";
}

function loadingValidarPDF() {
	if (document.getElementById("bloqueLoadingConsultar")!=null){
		document.getElementById("bloqueLoadingConsultar").style.display = "block";
		}
	document.getElementById("loadingImage").style.display = "block";
	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idBorrar").readOnly = "true";
	document.getElementById("idBorrar").style.color = "#6E6E6E";
	document.getElementById("idGenerarRectificativa").readOnly = "true";
	document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").readOnly = "true";
	document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").value = "Procesando";
}

function loadingValidarPDFRectificativa() {
	if (document.getElementById("bloqueLoadingConsultar")!=null){
		document.getElementById("bloqueLoadingConsultar").style.display = "block";
	}
	document.getElementById("loadingImage").style.display = "block";
	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idBorrar").readOnly = "true";
	document.getElementById("idBorrar").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").readOnly = "true";
	document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").value = "Procesando";
}


function loadingValidarPDFAlta() {
	if (document.getElementById("bloqueLoadingFactura")!=null){
		document.getElementById("bloqueLoadingFactura").style.display = "block";
	}
	document.getElementById("idBotonGuardar").readOnly = "true";
	document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idGenerarPDF").readOnly = "true";
	document.getElementById("idGenerarPDF").style.color = "#6E6E6E";
	document.getElementById("idGenerarPDF").value = "Procesando";
}

function loadingValidarBorradorAlta() {
	if (document.getElementById("bloqueLoadingConsultar")!=null){
		document.getElementById("bloqueLoadingConsultar").style.display = "block";
	}
	document.getElementById("idBotonGuardar").readOnly = "true";
	document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idGenerarBorrador").value = "Procesando";
	document.getElementById("idGenerarPDF").readOnly = "true";
	document.getElementById("idGenerarPDF").style.color = "#6E6E6E";
}


function loadingValidarBorrador() {
	document.getElementById("bloqueLoadingConsultar").style.display = "block";
	document.getElementById("loadingImage").style.display = "block";

	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idGenerarBorrador").value = "Procesando";
	document.getElementById("idBorrar").readOnly = "true";
	document.getElementById("idBorrar").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").readOnly = "true";
	document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
	document.getElementById("idGenerarRectificativa").readOnly = "true";
	document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
}

function loadingValidarPDFFinAlta() {
	document.getElementById("bloqueLoadingConsultar").style.display = "none";

	if (document.getElementById("idBotonGuardar").value != null) {
		document.getElementById("idBotonGuardar").disabled = "true";
		document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
	}
	document.getElementById("idGenerarBorrador").readOnly = "false";
	document.getElementById("idGenerarBorrador").style.color = "#000000";

	document.getElementById("idGenerarPDF").readOnly = "false";
	document.getElementById("idGenerarPDF").style.color = "#000000";
	document.getElementById("idGenerarPDF").value = "Imprimir PDF";
}

function buscarNifPersona(tipoInterviniente, pestania) {
	document.formData.action = "buscarNifGestionFacturar.action#" + pestania;
	document.formData.submit();
}

function limpiarTextfield(id) {
	if (document.getElementById(id).value == "Introduzca la descripcion") {
		document.getElementById(id).value = "";
		document.getElementById(id).style.color = "#000000";
	}
}

function comprobarTextfield(id) {
	if (document.getElementById(id).value == "") {
		document.getElementById(id).value = "Introduzca la descripcion";
		document.getElementById(id).style.color = "#6E6E6E";
	}
}

function habilitarCampo(check, tipo) {
	if (document.getElementById(check).checked) {
		document.getElementById(tipo).readOnly = false;
		document.getElementById(tipo).disabled = false;
		document.getElementById(tipo).style.color = "#000000";

		document.getElementById(check).value = "1";
	} else {
		document.getElementById(tipo).readOnly = true;
		document.getElementById(tipo).disabled = true;
		document.getElementById(tipo).style.color = "#6E6E6E";
		document.getElementById(check).value = "0";
	}
}

function habilitarCampoConHidden(check, tipo, idHidden) {
	if (document.getElementById(check).checked) {
		document.getElementById(tipo).readOnly = false;
		document.getElementById(tipo).disabled = false;
		document.getElementById(tipo).style.color = "#000000";

		document.getElementById(check).value = "1";
		document.getElementById(idHidden).value = "1";
	} else {
		document.getElementById(tipo).readOnly = true;
		document.getElementById(tipo).disabled = true;
		document.getElementById(tipo).style.color = "#6E6E6E";
		document.getElementById(check).value = "0";
		document.getElementById(idHidden).value = "0";
	}
	calcularFactura();
}

function calcularFactura() {

	var numDecimales = document.getElementById("numDecimales").value;

	var table = document.getElementById("mitablahonorarioDis");

	for(var x = 0; x < table.childNodes.length; x++){			
		if( table.childNodes[x]!='undefined'){
			if(table.childNodes[x].tagName=='TBODY') var tbody = table.childNodes[x];
		}	
	}
	
	var pos = tbody.rows.length;
	
	var totalHonorarioTodos = 0;
	var miCadenaHonorario = "";
	var idHonorario = 0;
	var honorarioDescripcion = "";
	var honorario = "";
	var honorarioIva = 0;
	var honorarioTotalIva = 0;
	var honorarioDescuento = 0;
	var honorarioIrpf = 0;
	var honorarioTotalIrpf = 0;
	var totalHonorario = "";

	for ( var i = 0; i < pos; i++) {
		idHonorario = 0;
		honorarioDescripcion = "";
		honorario = "";
		honorarioIva = 0;
		honorarioTotalIva = 0;
		honorarioDescuento = 0;
		honorarioIrpf = 0;
		honorarioTotalIrpf = 0;
		totalHonorario = "";
		

		if (document.getElementById("idContadorHonorario" + i).value != null && document.getElementById("idContadorHonorario" + i).value > 0)
			idHonorario = document.getElementById("idContadorHonorario" + i).value;
		else
			idHonorario = 0;

		if (document.getElementById("idHonorarioDescripcion" + i).value)
			honorarioDescripcion = document
					.getElementById("idHonorarioDescripcion" + i).value;
		else
			honorarioDescripcion = "Introduzca la descripcion";

		if (honorarioDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idHonorarioDescripcion" + i).value = honorarioDescripcion
					.substring(0, honorarioDescripcion.length - 1);
		}

		if (document.getElementById("idHonorario" + i).value) {
			document.getElementById("idHonorario" + i).value = document.getElementById("idHonorario" + i).value.replace(',','.');			
			honorario = new Number(document.getElementById("idHonorario" + i).value);
		} else
			honorario = 0;

		if (document.getElementById("idCheckHonorario" + i).checked) {
			honorarioIva = new Number(document.getElementById("idHonorarioIVA"
					+ i).value);
			honorarioTotalIva = eval((honorario * honorarioIva) / 100).toFixed(numDecimales);
		}

		if (document.getElementById("idCheckDescuentoHonorario" + i).checked){
			document.getElementById("idHonorarioDescuento" + i).value = document.getElementById("idHonorarioDescuento" + i).value.replace(',','.');
			honorarioDescuento = new Number(document.getElementById("idHonorarioDescuento" + i).value);
		}

		if (document.getElementById("idCheckIRPFHonorario" + i).checked) {
			honorarioIrpf = new Number(	document.getElementById("idIRPF" + i).value);
			honorarioTotalIrpf = eval((honorario * honorarioIrpf) / 100);
		}

		if (honorario != 0) {
			var sumaIva = eval(honorario + ((honorario * honorarioIva) / 100));
			var restaIrpf = eval((honorario * honorarioIrpf) / 100);
			var restDesc = eval((honorario * honorarioDescuento) / 100);

			document.getElementById("idTotalHonorarios" + i).value = eval(new Number(sumaIva) - new Number(restaIrpf)
							- new Number(restDesc)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalHonorarios" + i).value = "0";
		}
		totalHonorario = document.getElementById("idTotalHonorarios" + i).value;
		totalHonorarioTodos = new Number(totalHonorarioTodos) + new Number(totalHonorario);
		miCadenaHonorario += "\\"
				+ idHonorario
				+ "\\"
				+ honorarioDescripcion
				+ "\\"
				+ honorario
				+ "\\"
				+ document.getElementById("idCheckHonorario" + i).checked
				+ "\\"
				+ honorarioIva
				+ "\\"
				+ honorarioTotalIva
				+ "\\"
				+ document.getElementById("idCheckDescuentoHonorario" + i).checked
				+ "\\" + honorarioDescuento + "\\"
				+ document.getElementById("idCheckIRPFHonorario" + i).checked
				+ "\\" + honorarioIrpf + "\\" + honorarioTotalIrpf + "\\"
				+ totalHonorario;

	}
	document.getElementById("honorarioTodo").value = miCadenaHonorario;
	document.getElementsByName("datosCliente.honorarioTodo").value = miCadenaHonorario;

	var tableSup = document.getElementById("mitablaSuplidoDis");

	for(var x = 0; x < tableSup.childNodes.length; x++){			
		if( tableSup.childNodes[x]!='undefined'){
			if(tableSup.childNodes[x].tagName=='TBODY') var tbody = tableSup.childNodes[x];
		}	
	}
	
	var pos = tbody.rows.length;
	
	
	var totalSuplidoTodos = 0;
	var miCadenaSuplidos = "";
	var idSuplido = 0;
	var descSuplido = 0;
	var suplidoDescripcion = "";
	var suplido = "";
	var totalSuplido = "";

	for ( var i = 0; i < pos; i++) {
		idSuplido = 0;
		descSuplido = 0;
		suplidoDescripcion = "";
		suplido = "";
		totalSuplido = "";
									 
		if (document.getElementById("idContadorSuplido" + i).value != null
				&& document.getElementById("idContadorSuplido" + i).value > 0)
			idSuplido = document.getElementById("idContadorSuplido" + i).value;
		else
			idSuplido = 0;

		if (document.getElementById("idSuplidoDescripcion" + i).value)
			suplidoDescripcion = document.getElementById("idSuplidoDescripcion"
					+ i).value;
		else
			suplidoDescripcion = "Introduzca la descripcion";

		if (suplidoDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idSuplidoDescripcion" + i).value = suplidoDescripcion.substring(0, suplidoDescripcion.length - 1);
		}

		document.getElementById("idSuplido" + i).value = document.getElementById("idSuplido" + i).value.replace(',','.');
		suplido = new Number(document.getElementById("idSuplido" + i).value);

		if (document.getElementById("idCheckDescuentoSuplido" + i).checked){
			document.getElementById("idSuplidoDescuento" + i).value = document.getElementById("idSuplidoDescuento" + i).value.replace(',','.');
			descSuplido = new Number(document.getElementById("idSuplidoDescuento" + i).value);
		}

		if (suplido != 0) {
			document.getElementById("idTotalSuplido" + i).value = eval(suplido - ((suplido * descSuplido) / 100)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalSuplido" + i).value = "0";
		}
		totalSuplido = document.getElementById("idTotalSuplido" + i).value;

		totalSuplidoTodos = eval(new Number(totalSuplidoTodos) + new Number(totalSuplido));
		miCadenaSuplidos += "\\"
				+ idSuplido
				+ "\\"
				+ suplidoDescripcion
				+ "\\"
				+ suplido
				+ "\\"
				+ document.getElementById("idCheckDescuentoSuplido" + i).checked
				+ "\\" + descSuplido + "\\" + totalSuplido;

	}
	document.getElementById("suplidoTodo").value = miCadenaSuplidos;
	document.getElementsByName("datosCliente.suplidoTodo").value = miCadenaSuplidos;

	var tableGasto = document.getElementById("mitablaGastoDis");

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var pos = tbodyGasto.rows.length;
	var totalGastoTodos = 0;
	var miCadenaGasto = "";
	var idGasto = 0;
	var gastoIva = 0;
	var gastoTotalIva = 0;
	var gastoDescripcion = "";
	var gasto = "";
	var totalGasto = "";

	for ( var i = 0; i < pos; i++) {
		idGasto = 0;
		gastoIva = 0;
		gastoTotalIva = 0;
		gastoDescripcion = "";
		gasto = "";
		totalGasto = "";
									 
		if (document.getElementById("idContadorGasto" + i).value != null
				&& document.getElementById("idContadorGasto" + i).value > 0)
			idGasto = document.getElementById("idContadorGasto" + i).value;
		else
			idGasto = 0;

		if (document.getElementById("idGastoDescripcion" + i).value)
			gastoDescripcion = document
					.getElementById("idGastoDescripcion" + i).value;
		else
			gastoDescripcion = "Introduzca la descripcion";

		if (gastoDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idGastoDescripcion" + i).value = gastoDescripcion
					.substring(0, gastoDescripcion.length - 1);
		}

		document.getElementById("idGasto" + i).value = document.getElementById("idGasto" + i).value.replace(',','.');
		gasto = new Number(document.getElementById("idGasto" + i).value);
		
		if (document.getElementById("idCheckGasto" + i).checked) {
			gastoIva = new Number(
					document.getElementById("idGastoIVA" + i).value);
			gastoTotalIva = eval((gasto * gastoIva) / 100).toFixed(
					numDecimales);
		} else {
			gastoTotalIva = new Number("0");
		}

		if (gasto != 0) {
			document.getElementById("idTotalGastos" + i).value = eval(
					gasto + ((gasto * gastoIva) / 100)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalGastos" + i).value = "0";
		}
		totalGasto = document.getElementById("idTotalGastos" + i).value;

		totalGastoTodos = eval(new Number(totalGastoTodos)
				+ new Number(totalGasto));
		miCadenaGasto += "\\" + 
			idGasto + 
			"\\" + 
			gastoDescripcion + 
			"\\" + 
			gasto + 
			"\\" + 
			document.getElementById("idCheckGasto" + i).checked	+ 
			"\\" + 
			gastoIva + 
			"\\" + 
			gastoTotalIva + 
			"\\" + 
			totalGasto;
	}

	document.getElementById("gastoTodo").value = miCadenaGasto;
	document.getElementsByName("datosCliente.gastoTodo").value = miCadenaGasto;

	if (document.getElementById("idOtro").value != null){
		document.getElementById("idOtro").value = document.getElementById("idOtro").value.replace(',','.');
	}
	var otro = new Number(document.getElementById("idOtro").value);
	var otroIva = 0;
	var otroPorc = 0;
	var otroDesc = 0;
	var otroTot = 0;
	var descripcion = "";

	document.getElementById("idOtroTotalIva").value = "";

	descripcion = document.getElementById("idOtroDescripcion").value
	if (descripcion.indexOf("\\") > -1) {
		alert("Caracter No Valido");
		document.getElementById("idOtroDescripcion").value = descripcion.substring(0, descripcion.length - 1);
	}

	if (otro != null) {
		
		otroIva = new Number(document.getElementById("idOtroIVA").options[document.getElementById("idOtroIVA").selectedIndex].text);

		if (document.getElementById("idCheckOtro").checked) {
			document.getElementById("idCheckOtro").value = "1";
			document.getElementById("idOtroTotalIva").value = eval((otro * otroIva) / 100);
		} else {
			document.getElementById("idOtroTotalIva").value = 0;
			document.getElementById("idCheckOtro").value = "0";
			otroIva = 0;
		}
		otroTot = eval(otro + ((otro * otroIva) / 100));

		document.getElementById("idOtroDescuento").value = document.getElementById("idOtroDescuento").value.replace(',','.');
		otroPorc = new Number(document.getElementById("idOtroDescuento").value);
		if (document.getElementById("idCheckOtroDescuento").checked)
			document.getElementById("idCheckOtroDescuento").value = "1";
		else {
			document.getElementById("idCheckOtroDescuento").value = "0";
			otroPorc = 0;
		}

		otroDesc = eval((otro * otroPorc) / 100);
		document.getElementById("idTotalOtros").value = eval(otroTot - otroDesc)
				.toFixed(numDecimales);
	}
	if (document.getElementById("idFondo").value != null){
		document.getElementById("idFondo").value = document.getElementById("idFondo").value.replace(',','.');
	}	
	var fondo = new Number(document.getElementById("idFondo").value);
	var fondoIva = '0';
	var fondoSumaIva = 0;
	var fondoSuma = 0;
	var descripcion = "";

	descripcion = document.getElementById("idFondoDescripcion").value;
	if (descripcion.indexOf("\\") > -1) {
		alert("Caracter No Valido");
		document.getElementById("idFondoDescripcion").value = descripcion
				.substring(0, descripcion.length - 1);
	}

	if (fondo != null) {
		
		document.getElementById("idTotalFondo").value = fondo.toFixed(numDecimales);
	}

	var totalConcepto = totalHonorarioTodos + totalSuplidoTodos
			+ totalGastoTodos;

	if (totalConcepto != 0)
		document.getElementById("idTotalConcepto").value = (totalConcepto).toFixed(numDecimales);
	else
		document.getElementById("idTotalConcepto").value = "0";

}

function generarPDFConsultaFacturacionNormal(boton, param) {
	if (numCheckedFacturaNormal() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaNormal() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajePDF").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {
		if (param == "cfactura")
			loadingValidarPDF();
		else
			loadingValidarPDFRectificativa();
		
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=pdf&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
			
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function generarPDFConsultaFacturacionRect(boton, param) {
	if (numCheckedFacturaRect() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaRect() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaRect");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajePDF").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {
		if (param == "cfactura")
			loadingValidarPDF();
		else
			loadingValidarPDFRectificativa();
		document.formData.action = "imprimirPDFGestionFacturar.action?modelo=rectificativa&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function generarBorradorConsultaFacturacionNormal(boton, param) {

	if (numCheckedFacturaNormal() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaNormal() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajeBorrador").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {

		document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function generarBorradorConsultaFacturacionRect(boton, param) {

	if (numCheckedFacturaRect() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaRect() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaRect");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajeBorrador").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {


		document.formData.action = "imprimirPDFGestionFacturar.action?modelo=rectificativa&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}


function generarRectificativaConsultaFacturacion(boton) {
	if (numCheckedFacturaNormal() == 0) {
		alert("Debe seleccionar alguna Factura");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Esta seguro que desea anular la factura seleccionada, y crear una rectificativa?")) {
		mostrarLoadingConsultar(boton);
		document.forms[0].action = "crearRectificativaGestionFacturar.action?numsFactura="
				+ codigos;
		document.forms[0].submit();
		loadingCrearRectificativa(boton);
		return true;
	} else {
		return false;
	}

}

function buscarFactura() {
	document.formData.action = "buscarGestionFacturar.action";
	document.formData.submit();
}

function limpiarFormConsultaFactura() {
	 if(document.getElementById("numSerie")) {
		 document.getElementById("numSerie").value = ""; }
	if (document.getElementById("numFactura")) {
		document.getElementById("numFactura").value = "";
	}
	if (document.getElementById("numColegiado")) {
		document.getElementById("numColegiado").value = "";
	}
	if (document.getElementById("numCodigo")) {
		document.getElementById("numCodigo").value = "";
	}
	if (document.getElementById("numExpediente")) {
		document.getElementById("numExpediente").value = "";
	}
	if (document.getElementById("nif")) {
		document.getElementById("nif").value = "";
	}
	// Fechas
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
	if (document.getElementById("idTipoTramite")) {
		document.getElementById("idTipoTramite").value = "FACT";
	}
	
	if (document.getElementById("idEstadoFactura")) {
		document.getElementById("idEstadoFactura").value = -1;
	}
}

function compruebaPDFConsulta(boton, param) {	
	if (document.getElementById("estadoPdf").value.substring(document.getElementById("estadoPdf").value.length - 3) == "OOK") {
		codigos = document.getElementById("estadoPdf").value.substring(0,
				document.getElementById("estadoPdf").value.length - 3);		

		if (document.getElementById("estadoPdf").value != null) {
			var estado = document.getElementById("estadoPdf").value;

			if (estado.substring(estado.length - 3) == "OOK") {
				document.getElementById("estadoPdf").value = codigos + "PDF";
			} else if (estado.substring(estado.length - 3) == "KOO") {
				document.getElementById("estadoPdf").value = codigos + "FIN";
			} else if (estado.substring(estado.length - 3) == "PDF") {
				document.getElementById("estadoPdf").value = codigos + "FIN";
			}
		} else {
			document.getElementById("estadoPdf").value = codigos + "INI";
		}
		var est = document.getElementById("estadoPdf").value;

		estado = document.getElementById("estadoPdf").value;
		if (document.getElementById("tipoPdf").value == "borrador") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (document.getElementById("tipoPdf").value == "pdf") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=pdf&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (document.getElementById("tipoPdf").value == "rectificativa") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=rectificativa&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (document.getElementById("tipoPdf").value == "anulada") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=anulada&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else if (document.getElementById("tipoPdf").value == "abono") {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=abono&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		} else {
			document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&donde="
					+ param
					+ "&estado="
					+ document.getElementById("estadoPdf").value
					+ "&numsFactura=" + codigos;
		}
			document.formData.submit();
			document.getElementById("estadoPdf").value = "";
			return true;
	
	} else if (document.getElementById("estadoXml").value.substring(document.getElementById("estadoXml").value.length - 3) == "OOK") {
		codigos = document.getElementById("estadoXml").value.substring(0,document.getElementById("estadoXml").value.length - 3);		
		
		if (document.getElementById("estadoXml").value != null) {
			var estado = document.getElementById("estadoXml").value;

			if (estado.substring(estado.length - 3) == "OOK") {
				document.getElementById("estadoXml").value = codigos + "XML";
			} else if (estado.substring(estado.length - 3) == "KOO") {
				document.getElementById("estadoXml").value = codigos + "FIN";
			} else if (estado.substring(estado.length - 3) == "XML") {
				document.getElementById("estadoXml").value = codigos + "FIN";
			}
		} else {
			document.getElementById("estadoXml").value = codigos + "INI";
		}

		estado = document.getElementById("estadoXml").value;
		if (estado.substring(estado.length - 3) == "XML") {
			return true;
		} else {
			return false;
		}
	} 
	
	else {
		return false;
	}
}

function mostrarArchivosAdjuntos() {	
	if (document.getElementById("estadoPdf").value.substring(document.getElementById("estadoPdf").value.length - 3) == "OOK") {
		calcularFactura();
		codigos = document.getElementById("estadoPdf").value.substring(0,document.getElementById("estadoPdf").value.length - 3);

		// Estado inicial
		if (document.getElementById("estadoPdf").value != null) {
			var estado = document.getElementById("estadoPdf").value;

			if (estado.substring(estado.length - 3) == "OOK") {
				document.getElementById("estadoPdf").value = codigos + "PDF";
				var tipo = document.getElementById("tipoPdf").value;
				if (tipo == "pdf") {
					deshabilitarBotonesYModificarPDF();
				}
			} else if (estado.substring(estado.length - 3) == "KOO") {
				document.getElementById("estadoPdf").value = codigos + "FIN";
			} else if (estado.substring(estado.length - 3) == "PDF") {
				document.getElementById("estadoPdf").value = codigos + "FIN";
			}
		} else {
			document.getElementById("estadoPdf").value = codigos + "INI";
		}

		estado = document.getElementById("estadoPdf").value;
		if (estado.substring(estado.length - 3) == "PDF") {
			if (document.getElementById("tipoPdf").value == "borrador") {
				document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&estado="
						+ estado + "&numsFactura=" + codigos;
			} else if (document.getElementById("tipoPdf").value == "pdf") {
				document.formData.action = "imprimirPDFGestionFacturar.action?modelo=pdf&estado="
						+ estado + "&numsFactura=" + codigos;
			} else {
				document.formData.action = "imprimirPDFGestionFacturar.action?modelo=borrador&estado="
						+ estado + "&numsFactura=" + codigos;
			}
			document.formData.submit();
			document.getElementById("estadoPdf").value = "";
			return true;
		} else {
			return false;
		}
	}  else {
		return false;
	}
}

function deshabilitarBotonesYModificarPDF() {
	document.getElementById('idGenerarPDF').setAttribute("onClick",
			"return generarPDFFacturacion(this, 'imprimir');");
	document.getElementById('idGenerarPDF').value = "Imprimir PDF";
	document.getElementById('idBotonGuardar').setAttribute("onClick",
			"return validarModificarFacturaRectificativa(this);");
	document.getElementById('idBotonGuardar').value = "Modificar";

}

function seleccionarConsultaCheckNormal() {
	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var cont = 0;

	for ( var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			cont++;
	}

	if (cont == 1) {
		document.getElementById("idBorrar").disabled = false;
		document.getElementById("idBorrar").style.color = "#000000";
		document.getElementById("idGenerarBorrador").disabled = false;
		document.getElementById("idGenerarBorrador").style.color = "#000000";
		document.getElementById("idGenerarPdf").disabled = false;
		document.getElementById("idGenerarPdf").style.color = "#000000";
		document.getElementById("idGenerarRectificativa").disabled = false;
		document.getElementById("idGenerarRectificativa").style.color = "#000000";
		document.getElementById("idGenerarAbono").disabled = false;
		document.getElementById("idGenerarAbono").style.color = "#000000";
	} else if (cont > 1) {
		document.getElementById("idBorrar").disabled = "true";
		document.getElementById("idBorrar").style.color = "#6E6E6E";
		document.getElementById("idGenerarBorrador").disabled = "true";
		document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
		document.getElementById("idGenerarPdf").disabled = "true";
		document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
		document.getElementById("idGenerarRectificativa").disabled = "true";
		document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
	}else {
		document.getElementById("idBorrar").disabled = "true";
		document.getElementById("idBorrar").style.color = "#6E6E6E";
		document.getElementById("idGenerarBorrador").disabled = "true";
		document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
		document.getElementById("idGenerarPdf").disabled = "true";
		document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
		document.getElementById("idGenerarRectificativa").disabled = "true";
		document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
		document.getElementById("idGenerarAbono").disabled = "true";
		document.getElementById("idGenerarAbono").style.color = "#6E6E6E";
	}
	return true;
}

function seleccionarConsultaCheckRect() {
	var checks = document.getElementsByName("listaChecksConsultaFacturaRect");
	var cont = 0;

	for ( var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			cont++;
	}

	if (cont == 1) {
		document.getElementById("idBorrar").disabled = false;
		document.getElementById("idBorrar").style.color = "#000000";
		document.getElementById("idGenerarBorrador").disabled = false;
		document.getElementById("idGenerarBorrador").style.color = "#000000";
		document.getElementById("idGenerarPdf").disabled = false;
		document.getElementById("idGenerarPdf").style.color = "#000000";
		document.getElementById("idGenerarRectificativa").disabled = false;
		document.getElementById("idGenerarRectificativa").style.color = "#000000";
	} else {
		document.getElementById("idBorrar").disabled = "true";
		document.getElementById("idBorrar").style.color = "#6E6E6E";
		document.getElementById("idGenerarBorrador").disabled = "true";
		document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
		document.getElementById("idGenerarPdf").disabled = "true";
		document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
		document.getElementById("idGenerarRectificativa").disabled = "true";
		document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
	}
	return true;
}

function eliminarFacturaNormal(boton) {
	if (numCheckedFacturaNormal() == 0) {
		alert("Debe seleccionar alguna Factura");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar las Facturas?")) {
		document.forms[0].action = "eliminarNormalGestionFacturar.action?numsFactura="
				+ codigos;
		document.forms[0].submit();
		return true;
	} else {
		return false;
	}
}

function eliminarFacturaRect(boton) {
	if (numCheckedFacturaRect() == 0) {
		alert("Debe seleccionar alguna Factura");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaFacturaRect");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar las Facturas?")) {
		document.forms[0].action = "eliminarRectGestionFacturar.action?numsFactura="
				+ codigos;
		document.forms[0].submit();
		return true;
	} else {
		return false;
	}
}

function modificarNumSerie(tableId) {
	if (document.getElementById("numSerie").value.length <= 1) {
		document.getElementById("numSerie").value = "F";
	}
}

function loadingCrearRectificativa() {
	document.getElementById("bloqueLoadingConsultar").style.display = "block";
	document.getElementById("loadingImage").style.display = "block";
	document.getElementById("idBorrar").readOnly = "true";
	document.getElementById("idBorrar").style.color = "#6E6E6E";
	document.getElementById("idGenerarBorrador").readOnly = "true";
	document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
	document.getElementById("idGenerarPdf").readOnly = "true";
	document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
	document.getElementById("idGenerarRectificativa").readOnly = "true";
	document.getElementById("idGenerarRectificativa").style.color = "#6E6E6E";
	document.getElementById("idGenerarRectificativa").value = "Procesando";
	document.getElementById("idGenerarAbono").readOnly = "true";
	document.getElementById("idGenerarAbono").style.color = "#6E6E6E";
	document.getElementById("idGenerarAbono").value = "Crear Abono";
}

function loadingCrearRectificativa() {
	document.getElementById("bloqueLoadingConsultar").style.display = "none";
	document.getElementById("loadingImage").style.display = "none";
	document.getElementById("idBorrar").readOnly = "false";
	document.getElementById("idBorrar").style.color = "#000000";
	document.getElementById("idGenerarBorrador").readOnly = "false";
	document.getElementById("idGenerarBorrador").style.color = "#000000";
	document.getElementById("idGenerarPdf").readOnly = "false";
	document.getElementById("idGenerarPdf").style.color = "#000000";
	document.getElementById("idGenerarRectificativa").readOnly = "false";
	document.getElementById("idGenerarRectificativa").style.color = "#000000";
	document.getElementById("idGenerarRectificativa").value = "Crear Rectificativa";
	document.getElementById("idGenerarAbono").readOnly = "false";
	document.getElementById("idGenerarAbono").style.color = "#000000";
	document.getElementById("idGenerarAbono").value = "Crear Abono";
}

function buscarFacturaRectificativa() {
	document.formData.action = "buscarRectificativaGestionFacturar.action";
	document.formData.submit();
}

function modificarNumSerieRectificativa(tableId) {
	if (document.getElementById("numSerie").value.length <= 1) {
		document.getElementById("numSerie").value = "R";
	}
}

function eliminarHonorarioAntiguo(honorarioIdBorrar) {

	var table = document.getElementById("mitablahonorarioDis"); 

	for(var x = 0; x < table.childNodes.length; x++){			
		if( table.childNodes[x]!='undefined'){
			if(table.childNodes[x].tagName=='TBODY') var tbody = table.childNodes[x];
		}	
	}
	var pos = tbody.rows.length;

	tbody.deleteRow(honorarioIdBorrar);					 

}

function aniadirHonorario() {

	document.formData.action = "nuevoHonorarioGestionFacturar.action";
	document.formData.submit();

}

function pasarHonorarioAjax(idConcepto, accion) {
	
	var numDecimales = document.getElementById("numDecimales").value;
	var table = document.getElementById("mitablahonorarioDis");
	var pos = table.rows.length - 1;		
	var totalHonorarioTodos = 0;
	var miCadenaHonorario = "";
	var idHonorario = 0;
	var honorarioDescripcion = "";
	var honorario = "";
	var honorarioIva = 0;
	var honorarioTotalIva = 0;
	var honorarioDescuento = 0;
	var honorarioIrpf = 0;
	var honorarioTotalIrpf = 0;
	var totalHonorario = "";

	for ( var i = 0; i < pos; i++) {
		idHonorario = 0;
		honorarioDescripcion = "";
		honorario = "";
		honorarioIva = 0;
		honorarioTotalIva = 0;
		honorarioDescuento = 0;
		honorarioIrpf = 0;
		honorarioTotalIrpf = 0;
		totalHonorario = "";

		if (document.getElementById("idContadorHonorario" + i).value != null && document.getElementById("idContadorHonorario" + i).value > 0)
			idHonorario = document.getElementById("idContadorHonorario" + i).value;
		else
			idHonorario = 0;

		if (document.getElementById("idHonorarioDescripcion" + i).value)
			honorarioDescripcion = document.getElementById("idHonorarioDescripcion" + i).value;
		else
			honorarioDescripcion = "Introduzca la descripcion";

		if (honorarioDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idHonorarioDescripcion" + i).value = honorarioDescripcion.substring(0, honorarioDescripcion.length - 1);
		}

		if (document.getElementById("idHonorario" + i).value) {
			document.getElementById("idHonorario" + i).value = document.getElementById("idHonorario" + i).value.replace(',','.');
			honorario = new Number(document.getElementById("idHonorario" + i).value);
		} else
			honorario = 0;

		if (document.getElementById("idCheckHonorario" + i).checked) {
			honorarioIva = new Number(document.getElementById("idHonorarioIVA"+ i).value);
			honorarioTotalIva = eval((honorario * honorarioIva) / 100).toFixed(numDecimales);
		}

		if (document.getElementById("idCheckDescuentoHonorario" + i).checked){
			document.getElementById("idHonorarioDescuento" + i).value = document.getElementById("idHonorarioDescuento" + i).value.replace(',','.');
			honorarioDescuento = new Number(document.getElementById("idHonorarioDescuento" + i).value);
		}

		if (document.getElementById("idCheckIRPFHonorario" + i).checked) {
			honorarioIrpf = new Number(document.getElementById("idIRPF" + i).value);
			honorarioTotalIrpf = eval((honorario * honorarioIrpf) / 100);
		}

		if (honorario != 0) {
			var sumaIva = eval(honorario + ((honorario * honorarioIva) / 100));
			var restaIrpf = eval((honorario * honorarioIrpf) / 100);
			var restDesc = eval((honorario * honorarioDescuento) / 100);

			document.getElementById("idTotalHonorarios" + i).value = eval(new Number(sumaIva) - new Number(restaIrpf)
							- new Number(restDesc)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalHonorarios" + i).value = "0";
		}
		totalHonorario = document.getElementById("idTotalHonorarios" + i).value;
		totalHonorarioTodos = new Number(totalHonorarioTodos)+ new Number(totalHonorario);
		miCadenaHonorario += "\\"
				+ idHonorario
				+ "\\"
				+ honorarioDescripcion
				+ "\\"
				+ honorario
				+ "\\"
				+ document.getElementById("idCheckHonorario" + i).checked
				+ "\\"
				+ honorarioIva
				+ "\\"
				+ honorarioTotalIva
				+ "\\"
				+ document.getElementById("idCheckDescuentoHonorario" + i).checked
				+ "\\" + honorarioDescuento + "\\"
				+ document.getElementById("idCheckIRPFHonorario" + i).checked
				+ "\\" + honorarioIrpf + "\\" + honorarioTotalIrpf + "\\"
				+ totalHonorario;

	}
	document.getElementById("honorarioTodo").value = miCadenaHonorario;
	document.getElementsByName("datosCliente.honorarioTodo").value = miCadenaHonorario;
	
	actualizarHonorariosConcepto(idConcepto, accion);

}

function pasarHonorarioAjaxFacturaResumen(idConcepto, accion) {
	
	var numDecimales = document.getElementById("numDecimales").value;

	var table = document.getElementById("mitablahonorarioDis");
	var pos = table.rows.length - 1;		
	var totalHonorarioTodos = 0;
	var miCadenaHonorario = "";
	var idHonorario = 0;
	var honorarioDescripcion = "";
	var honorario = "";
	var honorarioIva = 0;
	var honorarioTotalIva = 0;
	var honorarioDescuento = 0;
	var honorarioIrpf = 0;
	var honorarioTotalIrpf = 0;
	var totalHonorario = "";

	for ( var i = 0; i < pos; i++) {
		idHonorario = 0;
		honorarioDescripcion = "";
		honorario = "";
		honorarioIva = 0;
		honorarioTotalIva = 0;
		honorarioDescuento = 0;
		honorarioIrpf = 0;
		honorarioTotalIrpf = 0;
		totalHonorario = "";

		if (document.getElementById("idContadorHonorario" + i).value != null && document.getElementById("idContadorHonorario" + i).value > 0)
			idHonorario = document.getElementById("idContadorHonorario" + i).value;
		else
			idHonorario = 0;

		if (document.getElementById("idHonorarioDescripcion" + i).value)
			honorarioDescripcion = document.getElementById("idHonorarioDescripcion" + i).value;
		else
			honorarioDescripcion = "Introduzca la descripcion";

		if (honorarioDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idHonorarioDescripcion" + i).value = honorarioDescripcion.substring(0, honorarioDescripcion.length - 1);
		}

		if (document.getElementById("idHonorario" + i).value) {
			document.getElementById("idHonorario" + i).value = document.getElementById("idHonorario" + i).value.replace(',','.');
			honorario = new Number(document.getElementById("idHonorario" + i).value);
		} else
			honorario = 0;

		if (document.getElementById("idCheckHonorario" + i).checked) {
			honorarioIva = new Number(document.getElementById("idHonorarioIVA"+ i).value);
			honorarioTotalIva = eval((honorario * honorarioIva) / 100).toFixed(numDecimales);
		}

		if (document.getElementById("idCheckDescuentoHonorario" + i).checked){
			document.getElementById("idHonorarioDescuento" + i).value = document.getElementById("idHonorarioDescuento" + i).value.replace(',','.');
			honorarioDescuento = new Number(document.getElementById("idHonorarioDescuento" + i).value);
		}

		if (document.getElementById("idCheckIRPFHonorario" + i).checked) {
			honorarioIrpf = new Number(document.getElementById("idIRPF" + i).value);
			honorarioTotalIrpf = eval((honorario * honorarioIrpf) / 100);
		}

		if (honorario != 0) {
			var sumaIva = eval(honorario + ((honorario * honorarioIva) / 100));
			var restaIrpf = eval((honorario * honorarioIrpf) / 100);
			var restDesc = eval((honorario * honorarioDescuento) / 100);

			document.getElementById("idTotalHonorarios" + i).value = eval(new Number(sumaIva) - new Number(restaIrpf)
							- new Number(restDesc)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalHonorarios" + i).value = "0";
		}
		totalHonorario = document.getElementById("idTotalHonorarios" + i).value;
		totalHonorarioTodos = new Number(totalHonorarioTodos)+ new Number(totalHonorario);
		miCadenaHonorario += "\\"
				+ idHonorario
				+ "\\"
				+ honorarioDescripcion
				+ "\\"
				+ honorario
				+ "\\"
				+ document.getElementById("idCheckHonorario" + i).checked
				+ "\\"
				+ honorarioIva
				+ "\\"
				+ honorarioTotalIva
				+ "\\"
				+ document.getElementById("idCheckDescuentoHonorario" + i).checked
				+ "\\" + honorarioDescuento + "\\"
				+ document.getElementById("idCheckIRPFHonorario" + i).checked
				+ "\\" + honorarioIrpf + "\\" + honorarioTotalIrpf + "\\"
				+ totalHonorario;

	}
	document.getElementById("honorarioTodo").value = miCadenaHonorario;
	document.getElementsByName("datosCliente.honorarioTodo").value = miCadenaHonorario;
	
	actualizarHonorariosConceptoParaFacturaResumen(idConcepto, accion);

}

function antesDeCrearConcepto(){
	var idConceptoGuardar = document.getElementById("idConceptoActual").value;
	pasarHonorarioAjax(idConceptoGuardar, "nuevoConcepto");
}

function crearConcepto(){

	var numDecimales = document.getElementById("numDecimales").value;

	var table = document.getElementById("mitablahonorarioDis");

	for(var x = 0; x < table.childNodes.length; x++){			
		if( table.childNodes[x]!='undefined'){
			if(table.childNodes[x].tagName=='TBODY') var tbody = table.childNodes[x];
		}	
	}
	
	var pos = tbody.rows.length;
	
	var totalHonorarioTodos = 0;
	var miCadenaHonorario = "";
	var idHonorario = 0;
	var honorarioDescripcion = "";
	var honorario = "";
	var honorarioIva = 0;
	var honorarioTotalIva = 0;
	var honorarioDescuento = 0;
	var honorarioIrpf = 0;
	var honorarioTotalIrpf = 0;
	var totalHonorario = "";

	for ( var i = 0; i < pos; i++) {
		idHonorario = 0;
		honorarioDescripcion = "";
		honorario = "";
		honorarioIva = 0;
		honorarioTotalIva = 0;
		honorarioDescuento = 0;
		honorarioIrpf = 0;
		honorarioTotalIrpf = 0;
		totalHonorario = "";

		if (document.getElementById("idContadorHonorario" + i).value != null && document.getElementById("idContadorHonorario" + i).value > 0)
			idHonorario = document.getElementById("idContadorHonorario" + i).value;
		else
			idHonorario = 0;

		if (document.getElementById("idHonorarioDescripcion" + i).value)
			honorarioDescripcion = document
					.getElementById("idHonorarioDescripcion" + i).value;
		else
			honorarioDescripcion = "Introduzca la descripcion";

		if (honorarioDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idHonorarioDescripcion" + i).value = honorarioDescripcion
					.substring(0, honorarioDescripcion.length - 1);
		}

		if (document.getElementById("idHonorario" + i).value) {
			document.getElementById("idHonorario" + i).value = document.getElementById("idHonorario" + i).value.replace(',','.');
			honorario = new Number(document.getElementById("idHonorario" + i).value);
		} else
			honorario = 0;

		if (document.getElementById("idCheckHonorario" + i).checked) {
			honorarioIva = new Number(document.getElementById("idHonorarioIVA"
					+ i).value);
			honorarioTotalIva = eval((honorario * honorarioIva) / 100).toFixed(numDecimales);
		}

		if (document.getElementById("idCheckDescuentoHonorario" + i).checked){
			document.getElementById("idHonorarioDescuento" + i).value = document.getElementById("idHonorarioDescuento" + i).value.replace(',','.');
			honorarioDescuento = new Number(document.getElementById("idHonorarioDescuento" + i).value);
		}

		if (document.getElementById("idCheckIRPFHonorario" + i).checked) {
			honorarioIrpf = new Number(	document.getElementById("idIRPF" + i).value);
			honorarioTotalIrpf = eval((honorario * honorarioIrpf) / 100);
		}

		if (honorario != 0) {
			var sumaIva = eval(honorario + ((honorario * honorarioIva) / 100));
			var restaIrpf = eval((honorario * honorarioIrpf) / 100);
			var restDesc = eval((honorario * honorarioDescuento) / 100);

			document.getElementById("idTotalHonorarios" + i).value = eval(new Number(sumaIva) - new Number(restaIrpf)
							- new Number(restDesc)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalHonorarios" + i).value = "0";
		}
		totalHonorario = document.getElementById("idTotalHonorarios" + i).value;
		totalHonorarioTodos = new Number(totalHonorarioTodos) + new Number(totalHonorario);
		miCadenaHonorario += "\\"
				+ idHonorario
				+ "\\"
				+ honorarioDescripcion
				+ "\\"
				+ honorario
				+ "\\"
				+ document.getElementById("idCheckHonorario" + i).checked
				+ "\\"
				+ honorarioIva
				+ "\\"
				+ honorarioTotalIva
				+ "\\"
				+ document.getElementById("idCheckDescuentoHonorario" + i).checked
				+ "\\" + honorarioDescuento + "\\"
				+ document.getElementById("idCheckIRPFHonorario" + i).checked
				+ "\\" + honorarioIrpf + "\\" + honorarioTotalIrpf + "\\"
				+ totalHonorario;

	}
	document.getElementById("honorarioTodo").value = miCadenaHonorario;
	document.getElementsByName("datosCliente.honorarioTodo").value = miCadenaHonorario;

	var tableSup = document.getElementById("mitablaSuplidoDis");

	for(var x = 0; x < tableSup.childNodes.length; x++){			
		if( tableSup.childNodes[x]!='undefined'){
			if(tableSup.childNodes[x].tagName=='TBODY') var tbody = tableSup.childNodes[x];
		}	
	}
	
	var pos = tbody.rows.length;
	
	var totalSuplidoTodos = 0;
	var miCadenaSuplidos = "";
	var idSuplido = 0;
	var descSuplido = 0;
	var suplidoDescripcion = "";
	var suplido = "";
	var totalSuplido = "";

	for ( var i = 0; i < pos; i++) {
		idSuplido = 0;
		descSuplido = 0;
		suplidoDescripcion = "";
		suplido = "";
		totalSuplido = "";
									 
		if (document.getElementById("idContadorSuplido" + i).value != null
				&& document.getElementById("idContadorSuplido" + i).value > 0)
			idSuplido = document.getElementById("idContadorSuplido" + i).value;
		else
			idSuplido = 0;

		if (document.getElementById("idSuplidoDescripcion" + i).value)
			suplidoDescripcion = document.getElementById("idSuplidoDescripcion"
					+ i).value;
		else
			suplidoDescripcion = "Introduzca la descripcion";

		if (suplidoDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idSuplidoDescripcion" + i).value = suplidoDescripcion.substring(0, suplidoDescripcion.length - 1);
		}

		document.getElementById("idSuplido" + i).value = document.getElementById("idSuplido" + i).value.replace(',','.');
		suplido = new Number(document.getElementById("idSuplido" + i).value);

		if (document.getElementById("idCheckDescuentoSuplido" + i).checked){
			document.getElementById("idSuplidoDescuento" + i).value = document.getElementById("idSuplidoDescuento" + i).value.replace(',','.');
			descSuplido = new Number(document.getElementById("idSuplidoDescuento" + i).value);
		}

		if (suplido != 0) {
			document.getElementById("idTotalSuplido" + i).value = eval(suplido - ((suplido * descSuplido) / 100)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalSuplido" + i).value = "0";
		}
		totalSuplido = document.getElementById("idTotalSuplido" + i).value;

		totalSuplidoTodos = eval(new Number(totalSuplidoTodos) + new Number(totalSuplido));
		miCadenaSuplidos += "\\"
				+ idSuplido
				+ "\\"
				+ suplidoDescripcion
				+ "\\"
				+ suplido
				+ "\\"
				+ document.getElementById("idCheckDescuentoSuplido" + i).checked
				+ "\\" + descSuplido + "\\" + totalSuplido;

	}
	document.getElementById("suplidoTodo").value = miCadenaSuplidos;
	document.getElementsByName("datosCliente.suplidoTodo").value = miCadenaSuplidos;

	var tableGasto = document.getElementById("mitablaGastoDis");

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var pos = tbodyGasto.rows.length;
	var totalGastoTodos = 0;
	var miCadenaGasto = "";
	var idGasto = 0;
	var gastoIva = 0;
	var gastoTotalIva = 0;
	var gastoDescripcion = "";
	var gasto = "";
	var totalGasto = "";

	for ( var i = 0; i < pos; i++) {
		idGasto = 0;
		gastoIva = 0;
		gastoTotalIva = 0;
		gastoDescripcion = "";
		gasto = "";
		totalGasto = "";
									 
		if (document.getElementById("idContadorGasto" + i).value != null
				&& document.getElementById("idContadorGasto" + i).value > 0)
			idGasto = document.getElementById("idContadorGasto" + i).value;
		else
			idGasto = 0;

		if (document.getElementById("idGastoDescripcion" + i).value)
			gastoDescripcion = document
					.getElementById("idGastoDescripcion" + i).value;
		else
			gastoDescripcion = "Introduzca la descripcion";

		if (gastoDescripcion.indexOf("\\") > -1) {
			alert("Caracter No Valido");
			document.getElementById("idGastoDescripcion" + i).value = gastoDescripcion
					.substring(0, gastoDescripcion.length - 1);
		}

		document.getElementById("idGasto" + i).value = document.getElementById("idGasto" + i).value.replace(',','.');
		gasto = new Number(document.getElementById("idGasto" + i).value);
		
		if (document.getElementById("idCheckGasto" + i).checked) {
			gastoIva = new Number(
					document.getElementById("idGastoIVA" + i).value);
			gastoTotalIva = eval((gasto * gastoIva) / 100).toFixed(
					numDecimales);
		} else {
			gastoTotalIva = new Number("0");
		}

		if (gasto != 0) {
			document.getElementById("idTotalGastos" + i).value = eval(
					gasto + ((gasto * gastoIva) / 100)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalGastos" + i).value = "0";
		}
		totalGasto = document.getElementById("idTotalGastos" + i).value;

		totalGastoTodos = eval(new Number(totalGastoTodos)
				+ new Number(totalGasto));
		miCadenaGasto += "\\" + 
			idGasto + 
			"\\" + 
			gastoDescripcion + 
			"\\" + 
			gasto + 
			"\\" + 
			document.getElementById("idCheckGasto" + i).checked	+ 
			"\\" + 
			gastoIva + 
			"\\" + 
			gastoTotalIva + 
			"\\" + 
			totalGasto;
	}

	document.getElementById("gastoTodo").value = miCadenaGasto;
	document.getElementsByName("datosCliente.gastoTodo").value = miCadenaGasto;

	if (document.getElementById("idOtro").value != null){
		document.getElementById("idOtro").value = document.getElementById("idOtro").value.replace(',','.');
	}
	var otro = new Number(document.getElementById("idOtro").value);
	var otroIva = 0;
	var otroPorc = 0;
	var otroDesc = 0;
	var otroTot = 0;
	var descripcion = "";

	document.getElementById("idOtroTotalIva").value = "";

	descripcion = document.getElementById("idOtroDescripcion").value
	if (descripcion.indexOf("\\") > -1) {
		alert("Caracter No Valido");
		document.getElementById("idOtroDescripcion").value = descripcion.substring(0, descripcion.length - 1);
	}

	if (otro != null) {
		otroIva = new Number(document.getElementById("idOtroIVA").options[document.getElementById("idOtroIVA").selectedIndex].text);

		if (document.getElementById("idCheckOtro").checked) {
			document.getElementById("idCheckOtro").value = "1";
			document.getElementById("idOtroTotalIva").value = eval((otro * otroIva) / 100);
		} else {
			document.getElementById("idOtroTotalIva").value = 0;
			document.getElementById("idCheckOtro").value = "0";
			otroIva = 0;
		}
		otroTot = eval(otro + ((otro * otroIva) / 100));

		document.getElementById("idOtroDescuento").value = document.getElementById("idOtroDescuento").value.replace(',','.');
		otroPorc = new Number(document.getElementById("idOtroDescuento").value);
		if (document.getElementById("idCheckOtroDescuento").checked)
			document.getElementById("idCheckOtroDescuento").value = "1";
		else {
			document.getElementById("idCheckOtroDescuento").value = "0";
			otroPorc = 0;
		}

		otroDesc = eval((otro * otroPorc) / 100);
		document.getElementById("idTotalOtros").value = eval(otroTot - otroDesc).toFixed(numDecimales);
	}

	if (document.getElementById("idFondo").value != null){
		document.getElementById("idFondo").value = document.getElementById("idFondo").value.replace(',','.');
	}
	
	var fondo = new Number(document.getElementById("idFondo").value);
	var fondoIva = '0';
	var fondoSumaIva = 0;
	var fondoSuma = 0;
	var descripcion = "";

	descripcion = document.getElementById("idFondoDescripcion").value;
	if (descripcion.indexOf("\\") > -1) {
		alert("Caracter No Valido");
		document.getElementById("idFondoDescripcion").value = descripcion
				.substring(0, descripcion.length - 1);
	}

	if (fondo != null) {
		
		document.getElementById("idTotalFondo").value = fondo.toFixed(numDecimales);
	}

	
	var totalConcepto = totalHonorarioTodos + totalSuplidoTodos
			+ totalGastoTodos;

	if (totalConcepto != 0)
		document.getElementById("idTotalConcepto").value = (totalConcepto).toFixed(numDecimales);
	else
		document.getElementById("idTotalConcepto").value = "0";
	
	obtenerNuevoConcepto();
	
}

function actualizarHonorarioAjax() {
	
	var numDecimales = document.getElementById("numDecimales").value;

	var table = document.getElementById("mitablahonorarioDis");
	var pos = table.rows.length - 1;		
	var totalHonorarioTodos = 0;
	var miCadenaHonorario = "";
	var idHonorario = 0;
	var honorarioDescripcion = "";
	var honorario = "";
	var honorarioIva = 0;
	var honorarioTotalIva = 0;
	var honorarioDescuento = 0;
	var honorarioIrpf = 0;
	var honorarioTotalIrpf = 0;
	var totalHonorario = "";

	for ( var i = 0; i < pos; i++) {
		idHonorario = 0;
		honorarioDescripcion = "";
		honorario = "";
		honorarioIva = 0;
		honorarioTotalIva = 0;
		honorarioDescuento = 0;
		honorarioIrpf = 0;
		honorarioTotalIrpf = 0;
		totalHonorario = "";

		if (document.getElementById("idContadorHonorario" + i).value != null && document.getElementById("idContadorHonorario" + i).value > 0)
			idHonorario = document.getElementById("idContadorHonorario" + i).value;
		else
			idHonorario = 0;

		if (document.getElementById("idHonorarioDescripcion" + i).value)
			honorarioDescripcion = document.getElementById("idHonorarioDescripcion" + i).value;
		else
			honorarioDescripcion = "Introduzca la descripcion";

		if (honorarioDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idHonorarioDescripcion" + i).value = honorarioDescripcion.substring(0, honorarioDescripcion.length - 1);
		}

		if (document.getElementById("idHonorario" + i).value) {
			document.getElementById("idHonorario" + i).value = document.getElementById("idHonorario" + i).value.replace(',','.');
			honorario = new Number(document.getElementById("idHonorario" + i).value);
		} else
			honorario = 0;

		if (document.getElementById("idCheckHonorario" + i).checked) {
			honorarioIva = new Number(document.getElementById("idHonorarioIVA"+ i).value);
			honorarioTotalIva = eval((honorario * honorarioIva) / 100).toFixed(numDecimales);
		}

		if (document.getElementById("idCheckDescuentoHonorario" + i).checked){
			document.getElementById("idHonorarioDescuento" + i).value = document.getElementById("idHonorarioDescuento" + i).value.replace(',','.');
			honorarioDescuento = new Number(document.getElementById("idHonorarioDescuento" + i).value);
		}

		if (document.getElementById("idCheckIRPFHonorario" + i).checked) {
			honorarioIrpf = new Number(document.getElementById("idIRPF" + i).value);
			honorarioTotalIrpf = eval((honorario * honorarioIrpf) / 100);
		}

		if (honorario != 0) {
			var sumaIva = eval(honorario + ((honorario * honorarioIva) / 100));
			var restaIrpf = eval((honorario * honorarioIrpf) / 100);
			var restDesc = eval((honorario * honorarioDescuento) / 100);

			document.getElementById("idTotalHonorarios" + i).value = eval(new Number(sumaIva) - new Number(restaIrpf)
							- new Number(restDesc)).toFixed(numDecimales);
		} else {
			document.getElementById("idTotalHonorarios" + i).value = "0";
		}
		totalHonorario = document.getElementById("idTotalHonorarios" + i).value;
		totalHonorarioTodos = new Number(totalHonorarioTodos)+ new Number(totalHonorario);
		miCadenaHonorario += "\\"
				+ idHonorario
				+ "\\"
				+ honorarioDescripcion
				+ "\\"
				+ honorario
				+ "\\"
				+ document.getElementById("idCheckHonorario" + i).checked
				+ "\\"
				+ honorarioIva
				+ "\\"
				+ honorarioTotalIva
				+ "\\"
				+ document.getElementById("idCheckDescuentoHonorario" + i).checked
				+ "\\" + honorarioDescuento + "\\"
				+ document.getElementById("idCheckIRPFHonorario" + i).checked
				+ "\\" + honorarioIrpf + "\\" + honorarioTotalIrpf + "\\"
				+ totalHonorario;

	}
	document.getElementById("honorarioTodo").value = miCadenaHonorario;
	document.getElementsByName("datosCliente.honorarioTodo").value = miCadenaHonorario;
	
}

function pasarSuplidoAjax(idConcepto, accion) {
	
	var numDecimalesSup = document.getElementById("numDecimales").value;
	
	var table = document.getElementById("mitablaSuplidoDis");
	var pos = table.rows.length - 1;		
	var totalSuplidoTodos = 0;
	var miCadenaSuplido = "";
	var idSuplido = 0;
	var suplidoDescripcion = "";
	var suplido = "";
	var suplidoDescuento = 0;
	var totalSuplidos = "";

	for ( var i = 0; i < pos; i++) {
		idSuplido = 0;
		suplidoDescripcion = "";
		suplido = "";
		suplidoDescuento = 0;
		totalsuplidos = "";
									 
		if (document.getElementById("idContadorSuplido" + i).value != null && document.getElementById("idContadorSuplido" + i).value > 0)
			idSuplido = document.getElementById("idContadorSuplido" + i).value;
		else
			idSuplido = 0;

		if (document.getElementById("idSuplidoDescripcion" + i).value)
			suplidoDescripcion = document.getElementById("idSuplidoDescripcion" + i).value;
		else
			suplidoDescripcion = "Introduzca la descripcion";

		if (suplidoDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idSuplidoDescripcion" + i).value = suplidoDescripcion.substring(0, suplidoDescripcion.length - 1);
		}

		document.getElementById("idSuplido" + i).value = document.getElementById("idSuplido" + i).value.replace(',','.');
		suplido = new Number(document.getElementById("idSuplido" + i).value);

		if (document.getElementById("idCheckDescuentoSuplido" + i).checked){
			document.getElementById("idSuplidoDescuento" + i).value = document.getElementById("idSuplidoDescuento" + i).value.replace(',','.');
			suplidoDescuento = new Number(document.getElementById("idSuplidoDescuento" + i).value);
		}

		if (suplido != 0) {
			document.getElementById("idTotalSuplido" + i).value = eval(suplido - ((suplido * suplidoDescuento) / 100)).toFixed(numDecimalesSup);
		} else {
			document.getElementById("idTotalSuplido" + i).value = "0";
		
		}
		totalSuplidos = document.getElementById("idTotalSuplido" + i).value;
		totalSuplidoTodos = new Number(totalSuplidoTodos)+ new Number(totalSuplidos);
		miCadenaSuplido += "\\"
				+ idSuplido
				+ "\\"
				+ suplidoDescripcion
				+ "\\"
				+ suplido
				+ "\\"
				+ document.getElementById("idCheckDescuentoSuplido" + i).checked
				+ "\\" + suplidoDescuento + "\\"
				+ totalSuplidos;

	}
	document.getElementById("suplidoTodo").value = miCadenaSuplido;
	document.getElementsByName("datosCliente.suplidoTodo").value = miCadenaSuplido;
	
	actualizarSuplidosConcepto(idConcepto, accion);
	
}

function actualizarSuplidoAjax() {
	
	var numDecimalesSup = document.getElementById("numDecimales").value;
	
	var table = document.getElementById("mitablaSuplidoDis");
	var pos = table.rows.length - 1;		
	var totalSuplidoTodos = 0;
	var miCadenaSuplido = "";
	var idSuplido = 0;
	var suplidoDescripcion = "";
	var suplido = "";
	var suplidoDescuento = 0;
	var totalSuplidos = "";

	for ( var i = 0; i < pos; i++) {
		idSuplido = 0;
		suplidoDescripcion = "";
		suplido = "";
		suplidoDescuento = 0;
		totalsuplidos = "";
									 
		if (document.getElementById("idContadorSuplido" + i).value != null && document.getElementById("idContadorSuplido" + i).value > 0)
			idSuplido = document.getElementById("idContadorSuplido" + i).value;
		else
			idSuplido = 0;

		if (document.getElementById("idSuplidoDescripcion" + i).value)
			suplidoDescripcion = document.getElementById("idSuplidoDescripcion" + i).value;
		else
			suplidoDescripcion = "Introduzca la descripcion";

		if (suplidoDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idSuplidoDescripcion" + i).value = suplidoDescripcion.substring(0, suplidoDescripcion.length - 1);
		}
		
		document.getElementById("idSuplido" + i).value = document.getElementById("idSuplido" + i).value.replace(',','.');
		suplido = new Number(document.getElementById("idSuplido" + i).value);

		if (document.getElementById("idCheckDescuentoSuplido" + i).checked){
			document.getElementById("idSuplidoDescuento" + i).value = document.getElementById("idSuplidoDescuento" + i).value.replace(',','.');
			suplidoDescuento = new Number(document.getElementById("idSuplidoDescuento" + i).value);
		}

		if (suplido != 0) {
			document.getElementById("idTotalSuplido" + i).value = eval(suplido - ((suplido * suplidoDescuento) / 100)).toFixed(numDecimalesSup);
		} else {
			document.getElementById("idTotalSuplido" + i).value = "0";
		
		}
		totalSuplidos = document.getElementById("idTotalSuplido" + i).value;
		totalSuplidoTodos = new Number(totalSuplidoTodos)+ new Number(totalSuplidos);
		miCadenaSuplido += "\\"
				+ idSuplido
				+ "\\"
				+ suplidoDescripcion
				+ "\\"
				+ suplido
				+ "\\"
				+ document.getElementById("idCheckDescuentoSuplido" + i).checked
				+ "\\" + suplidoDescuento + "\\"
				+ totalSuplidos;

	}
	document.getElementById("suplidoTodo").value = miCadenaSuplido;
	document.getElementsByName("datosCliente.suplidoTodo").value = miCadenaSuplido;
	
}

function pasarGastoAjax(idConcepto, accion){
	
	var numDecimalesGas = document.getElementById("numDecimales").value;
	var table = document.getElementById("mitablaGastoDis");
	var pos = table.rows.length - 1;		
	var totalGastoTodos = 0;
	var miCadenaGasto = "";
	var idGasto = 0;
	var gastoDescripcion = "";
	var gasto = "";
	var gastoIva = 0;
	var gastoTotalIva = 0;
	var gastoDescuento = 0;
	var totalGastos = "";

	for ( var i = 0; i < pos; i++) {
		idGasto = 0;
		gastoDescripcion = "";
		gasto = "";
		gastoIva = 0;
		gastoTotalIva = 0;
		totalgastos = "";
									 
		if (document.getElementById("idContadorGasto" + i).value != null && document.getElementById("idContadorGasto" + i).value > 0)
			idGasto = document.getElementById("idContadorGasto" + i).value;
		else
			idGasto = 0;

		if (document.getElementById("idGastoDescripcion" + i).value)
			gastoDescripcion = document.getElementById("idGastoDescripcion" + i).value;
		else
			gastoDescripcion = "Introduzca la descripcion";
		
		if (gastoDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idGastoDescripcion" + i).value = gastoDescripcion.substring(0, gastoDescripcion.length - 1);
		}

		if (document.getElementById("idGasto" + i).value) {
			document.getElementById("idGasto" + i).value = document.getElementById("idGasto" + i).value.replace(',','.');
			gasto = new Number(document.getElementById("idGasto" + i).value);
		} else
			gasto = 0;
		
		if (document.getElementById("idCheckGasto" + i).checked) {
		 	gastoIva = new Number( document.getElementById("idGastoIVA" + i).value);
		 	gastoTotalIva = eval((gasto * gastoIva) / 100).toFixed(numDecimalesGas);
		 } else {
			 gastoTotalIva = new Number("0");
		 } 

		
		if (gasto != 0) {
			var sumaIva = eval(gasto + ((gasto * gastoIva) / 100));

			document.getElementById("idTotalGastos" + i).value = eval(new Number(sumaIva)).toFixed(numDecimalesGas);
		} else {
			document.getElementById("idTotalGastos" + i).value = "0";
		}
		totalGastos = document.getElementById("idTotalGastos" + i).value;
		totalGastoTodos = new Number(totalGastoTodos)+ new Number(totalGastos);
		miCadenaGasto += "\\"
				+ idGasto
				+ "\\"
				+ gastoDescripcion
				+ "\\"
				+ gasto
				+ "\\"
				+ document.getElementById("idCheckGasto" + i).checked
				+ "\\"
				+ gastoIva
				+ "\\"
				+ gastoTotalIva
				+ "\\"
				+ totalGastos;

	}
	document.getElementById("gastoTodo").value = miCadenaGasto;
	document.getElementsByName("datosCliente.gastoTodo").value = miCadenaGasto;
	
	actualizarGastosConcepto(idConcepto, accion);
}

function actualizarGastoAjax(){
	
	var numDecimalesGas = document.getElementById("numDecimales").value;
	var table = document.getElementById("mitablaGastoDis");
	var pos = table.rows.length - 1;		
	var totalGastoTodos = 0;
	var miCadenaGasto = "";
	var idGasto = 0;
	var gastoDescripcion = "";
	var gasto = "";
	var gastoIva = 0;
	var gastoTotalIva = 0;
	var gastoDescuento = 0;
	var totalGastos = "";

	for ( var i = 0; i < pos; i++) {
		idGasto = 0;
		gastoDescripcion = "";
		gasto = "";
		gastoIva = 0;
		gastoTotalIva = 0;
		totalgastos = "";
									 
		if (document.getElementById("idContadorGasto" + i).value != null && document.getElementById("idContadorGasto" + i).value > 0)
			idGasto = document.getElementById("idContadorGasto" + i).value;
		else
			idGasto = 0;

		if (document.getElementById("idGastoDescripcion" + i).value)
			gastoDescripcion = document.getElementById("idGastoDescripcion" + i).value;
		else
			gastoDescripcion = "Introduzca la descripcion";
		
		if (gastoDescripcion.indexOf("\\") > -1) {			
			document.getElementById("idGastoDescripcion" + i).value = gastoDescripcion.substring(0, gastoDescripcion.length - 1);
		}

		if (document.getElementById("idGasto" + i).value) {
			document.getElementById("idGasto" + i).value = document.getElementById("idGasto" + i).value.replace(',','.');
			gasto = new Number(document.getElementById("idGasto" + i).value);
		} else
			gasto = 0;

		 if (document.getElementById("idCheckGasto" + i).checked) {
			 	gastoIva = new Number( document.getElementById("idGastoIVA" + i).value);
			 	gastoTotalIva = eval((gasto * gastoIva) / 100).toFixed(numDecimalesGas);
			 } else {
				 gastoTotalIva = new Number("0");
			 } 
		 
		if (gasto != 0) {
			var sumaIva = eval(gasto + ((gasto * gastoIva) / 100));

			document.getElementById("idTotalGastos" + i).value = eval(new Number(sumaIva)).toFixed(numDecimalesGas);
		} else {
			document.getElementById("idTotalGastos" + i).value = "0";
		}
		totalGastos = document.getElementById("idTotalGastos" + i).value;
		totalGastoTodos = new Number(totalGastoTodos)+ new Number(totalGastos);
		miCadenaGasto += "\\"
				+ idGasto
				+ "\\"
				+ gastoDescripcion
				+ "\\"
				+ gasto
				+ "\\"
				+ document.getElementById("idCheckGasto" + i).checked
				+ "\\"
				+ gastoIva
				+ "\\"
				+ gastoTotalIva
				+ "\\"
				+ totalGastos;

	}
	document.getElementById("gastoTodo").value = miCadenaGasto;
	document.getElementsByName("datosCliente.gastoTodo").value = miCadenaGasto;

}

function nombreConcepto(){
		
	document.getElementById('contadorConcepto').innerHTML = 'Concepto ' + document.getElementById('idConceptoActual').value;
}

function calcularFacturaBoton(){
	
	var url = "calcularFacturaAjaxFacturacionAjax.action";		
	const READY_STATE_UNITIALIZED = 0;
	const READY_STATE_LOADING = 1;
	const READY_STATE_LOADED = 2;
	const READY_STATE_INTERACTIVE = 3;
	const READY_STATE_COMPLETE = 4;
	
	var req_calcular_factura = NuevoAjax();
	
	req_calcular_factura.onreadystatechange = function () {
		
		if (req_calcular_factura.readyState == READY_STATE_LOADED) {
		}
		if (req_calcular_factura.readyState == READY_STATE_COMPLETE) {
			if (req_calcular_factura.status == 200) { // OK response
				
				var totalFactura = 0;
				var conceptos = req_calcular_factura.responseText.split("[CONCEPTO]");
				conceptos.shift();
				
				
				for(var i = 0; i < conceptos.length; i++){
					
					var totalesConcepto = conceptos[i].split("[FIN_TOTAL_CONCEPTO]");
					
					if(i == document.getElementById('idConceptoActual').value){
						
						var totalConcepto = parseFloat(totalesConcepto[0], 2);
						document.getElementById('idTotalConcepto').value = Math.round(totalConcepto * 100) / 100;
					
					}
				}
					
				var facturaIndex1 = req_calcular_factura.responseText.search(/\[FACTURA\]/);
				var facturaIndex2 = req_calcular_factura.responseText.search(/\[FIN_FACTURA\]/);
				var factura = req_calcular_factura.responseText.substring(facturaIndex1+9,facturaIndex2);
				
				var totalOtro=document.getElementById('idTotalOtros').value;
				
				if(totalOtro=="" || isNaN(totalOtro)){
					totalOtro=0;
				}
				totalFactura = parseFloat(factura, 2) + parseFloat(totalOtro);
				document.getElementById('idTotal').value = Math.round(totalFactura * 100) / 100;
					
				
			}
		}
		
	}
	
	req_calcular_factura.open("POST", url, true);
	
	req_calcular_factura.send(null);
	
}

function deshabilitarBotonesFacturacion(){


	
	document.getElementById("bNuevoConcepto").onclick = "";
	
	document.getElementById("idBotonHonorario").disabled = true;
	document.getElementById("idBotonHonorario").style.color = "#6E6E6E";

	document.getElementById("idBotonSuplidos").disabled = true;
	document.getElementById("idBotonSuplidos").style.color = "#6E6E6E";
	
	document.getElementById("idBotonGastos").disabled = true;
	document.getElementById("idBotonGastos").style.color = "#6E6E6E";

	
	var tableC = document.getElementById("mitablaconcepto"); 

	for(var x = 0; x < tableC.childNodes.length; x++){			
		if( tableC.childNodes[x]!='undefined'){
			if(tableC.childNodes[x].tagName=='TBODY') var tbodyC = tableC.childNodes[x];
		}	
	}
	
	var posC = tbodyC.rows.length;


	 for (var i = 0; i < posC; i++) {	
		 document.getElementById("idCambioConcepto" + i).onclick = "";
		 document.getElementById("idEliminaConcepto" + i).onclick = "";
	 }
	
	
	var tableHonorario = document.getElementById('mitablahonorarioDis');
	
	for(var x = 0; x < tableHonorario.childNodes.length; x++){			
		if( tableHonorario.childNodes[x]!='undefined'){
			if(tableHonorario.childNodes[x].tagName=='TBODY') var tbodyHonorario = tableHonorario.childNodes[x];
		}	
	}
	var posHonorario = tbodyHonorario.rows.length;
	
	for ( var i = 0; i < posHonorario; i++) {		
		document.getElementById("eliminarHonorario" + i).onclick = "";
		}
	
	var tableSuplido = document.getElementById('mitablaSuplidoDis');

	for(var x = 0; x < tableSuplido.childNodes.length; x++){			
		if( tableSuplido.childNodes[x]!='undefined'){
			if(tableSuplido.childNodes[x].tagName=='TBODY') var tbodySuplido = tableSuplido.childNodes[x];
		}	
	}
	
	var posSuplido = tbodySuplido.rows.length;
	

	for (var m = 0; m < posSuplido; m++) {
		document.getElementById("eliminarSuplido" + m).onclick = "";
	}
	
	var tableGasto = document.getElementById('mitablaGastoDis');

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var posGasto = tbodyGasto.rows.length;
	

	for (var n = 0; n < posGasto; n++) {
		document.getElementById("eliminarGasto" + n).onclick = "";
	}

}


function habilitarBotonesFacturacion(){
	
	document.getElementById("bNuevoConcepto").onclick = "deshabilitarBotones();obtenerNuevoConcepto();";
	
	document.getElementById("idBotonHonorario").disabled = false;
	document.getElementById("idBotonHonorario").style.color = "#000000";			
	
	document.getElementById("idBotonSuplidos").disabled = false;
	document.getElementById("idBotonSuplidos").style.color = "#000000";
	
	document.getElementById("idBotonGastos").disabled = false;
	document.getElementById("idBotonGastos").style.color = "#000000";
	
	var tableC = document.getElementById("mitablaconcepto"); 

	for(var x = 0; x < tableC.childNodes.length; x++){			
		if( tableC.childNodes[x]!='undefined'){
			if(tableC.childNodes[x].tagName=='TBODY') var tbodyC = tableC.childNodes[x];
		}	
	}
	
	var posC = tbodyC.rows.length;


	 for (var i = 0; i < posC; i++) {	
		 document.getElementById("idCambioConcepto" + i).onclick = "pasarHonorarioAjax('"+i+"', defaultConcepto);";
		 document.getElementById("idEliminaConcepto" + i).onclick = "eliminarConcepto('"+i+"');";
	 }
	
	var tableHonorario = document.getElementById('mitablahonorarioDis');
	
	for(var x = 0; x < tableHonorario.childNodes.length; x++){			
		if( tableHonorario.childNodes[x]!='undefined'){
			if(tableHonorario.childNodes[x].tagName=='TBODY') var tbodyHonorario = tableHonorario.childNodes[x];
		}	
	}
	var posHonorario = tbodyHonorario.rows.length;
	
	for ( var i = 0; i < posHonorario; i++) {		
		document.getElementById("eliminarHonorario" + i).onclick = "actualizarHonorarioAjax();eliminarHonorario('"+i+"');";
		}
	
	var tableSuplido = document.getElementById('mitablaSuplidoDis');

	for(var x = 0; x < tableSuplido.childNodes.length; x++){			
		if( tableSuplido.childNodes[x]!='undefined'){
			if(tableSuplido.childNodes[x].tagName=='TBODY') var tbodySuplido = tableSuplido.childNodes[x];
		}	
	}
	
	var posSuplido = tbodySuplido.rows.length;
	

	for (var m = 0; m < posSuplido; m++) {
		document.getElementById("eliminarSuplido" + m).onclick = "actualizarSuplidoAjax();eliminarSuplidos('"+i+"');";
	}
	
	var tableGasto = document.getElementById('mitablaGastoDis');

	for(var x = 0; x < tableGasto.childNodes.length; x++){			
		if( tableGasto.childNodes[x]!='undefined'){
			if(tableGasto.childNodes[x].tagName=='TBODY') var tbodyGasto = tableGasto.childNodes[x];
		}	
	}
	
	var posGasto = tbodyGasto.rows.length;
	

	for (var n = 0; n < posGasto; n++) {
		document.getElementById("eliminarGasto" + n).onclick = "actualizarGastoAjax();eliminarGasto('"+i+"');";
	}
}

function guardaConcepto() {

	var idConceptoGuardar = document.getElementById("idConceptoActual").value;
	pasarHonorarioAjax(idConceptoGuardar, "defaultConcepto");
	
}

function buscarIntervinienteFacturacion(tipoInterviniente,pestania){

	mostrarLoadingFacturacion();
	calcularFactura();	
	pasarHonorarioAjax('0', "defaultConcepto");
	
	setTimeout("buscaPersonaFacturacion()", 3000);
		
}

function buscaPersonaFacturacion(){
	
	document.formData.action = "buscarIntervinienteGestionFacturar.action";	
	document.formData.submit();	
}

function generarAbonoConsultaFacturacion(boton) {
	if (numCheckedFacturaNormal() == 0) {
		alert("Debe seleccionar alguna Factura");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaFacturaNormal");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Esta seguro que desea crear un abono de factura?")) {
		mostrarLoadingConsultar(boton);
		document.forms[0].action = "crearFacturaAbonoGestionFacturar.action?numsFactura="
				+ codigos;
		document.forms[0].submit();
		loadingCrearRectificativa(boton);
		return true;
	} else {
		return false;
	}

}

function generarPDFConsultaFacturacionAbono(boton, param) {
	if (numCheckedFacturaAbono() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaAbono() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaAbono");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajePDF").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {
		if (param == "cfactura")
			loadingValidarPDF();
		else
			loadingValidarPDFRectificativa();
		document.formData.action = "imprimirPDFGestionFacturar.action?modelo=abono&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function seleccionarConsultaCheckAbono() {
	var checks = document.getElementsByName("listaChecksConsultaFacturaAbono");
	var cont = 0;

	for ( var i = 0; i < checks.length; i++) {
		if (checks[i].checked)
			cont++;
	}

	if (cont == 1) {
		document.getElementById("idBorrar").disabled = false;
		document.getElementById("idBorrar").style.color = "#000000";
		document.getElementById("idGenerarBorrador").disabled = false;
		document.getElementById("idGenerarBorrador").style.color = "#000000";
		document.getElementById("idGenerarPdf").disabled = false;
		document.getElementById("idGenerarPdf").style.color = "#000000";
	} else {
		document.getElementById("idBorrar").disabled = "true";
		document.getElementById("idBorrar").style.color = "#6E6E6E";
		document.getElementById("idGenerarBorrador").disabled = "true";
		document.getElementById("idGenerarBorrador").style.color = "#6E6E6E";
		document.getElementById("idGenerarPdf").disabled = "true";
		document.getElementById("idGenerarPdf").style.color = "#6E6E6E";
	}
	return true;
}

function eliminarFacturaAbono(boton) {
	if (numCheckedFacturaAbono() == 0) {
		alert("Debe seleccionar alguna Factura");
		return false;
	}
	var checks = document.getElementsByName("listaChecksConsultaFacturaAbono");
	var codigos = "";
	var i = 0;
	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}

	if (confirm(String.fromCharCode(191)
			+ "Est\u00E1 seguro de que desea eliminar las Facturas?")) {
		document.forms[0].action = "eliminaFacturaAbonoGestionFacturar.action?numsFactura="
				+ codigos;
		document.forms[0].submit();
		return true;
	} else {
		return false;
	}
}

function generarBorradorConsultaFacturacionAbono(boton, param) {

	if (numCheckedFacturaAbono() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	if (numCheckedFacturaAbono() > 1) {
		alert("Debe generar las facturas de una en una");
		return false;
	}

	var checks = document.getElementsByName("listaChecksConsultaFacturaAbono");
	var codigos = "";
	var i = 0;

	while (checks[i] != null) {
		if (checks[i].checked) {
			if (codigos == "") {
				codigos += checks[i].value;
			} else {
				codigos += "-" + checks[i].value;
			}
		}
		i++;
	}
	var mensaje = document.getElementById("mensajeBorrador").value;

	document.getElementById("estadoPdf").value = codigos + "INI";

	if (confirm(String.fromCharCode(191) + mensaje)) {

		document.formData.action = "imprimirPDFGestionFacturar.action?modelo=abono&donde="
				+ param
				+ "&estado="
				+ document.getElementById("estadoPdf").value
				+ "&numsFactura="
				+ codigos;
		document.formData.submit();
		return true;
	} else {
		return false;
	}
}

function buscarFacturaAbono() {
	document.formData.action = "buscarFacturaAbonoGestionFacturar.action";
	document.formData.submit();
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksNumExpedientes");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function eliminarNumExpediente(boton) {
	if(numChecked() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	var checks = document.getElementsByName("listaChecksNumExpedientes");
	var codigos = "";
	var i = 0;
	while(checks[i] != null) {
		if(checks[i].checked) {
			if(codigos==""){
				codigos += checks[i].value;
			}else{
				codigos += "-"+ checks[i].value;
			}
		}
		i++;
	}
	
	if (confirm(String.fromCharCode(191)+"Est\u00E1 seguro de que desea eliminar los tr\u00E1mites?")){
		document.forms[0].action="eliminarExpedientesGestionFacturar.action?numsExpediente="+codigos;
		document.forms[0].submit();
		return true;
	}else{
		return false;
	}
}

function recargarPadre(){
	parent.opener.document.location.reload();
}

function clickOnCheckEmisorFactura(check){
	if(check.id == "idCheckGestorEmisorFactura"){
		if(check.checked){
			document.getElementById("idCheckGestoriaEmisorFactura").checked = false;
			document.getElementById("hiddenEmisor").value = "GESTOR";
		}else{
			document.getElementById("hiddenEmisor").value = "";
		}
	}else if(check.id == "idCheckGestoriaEmisorFactura"){
		if(check.checked){
			document.getElementById("idCheckGestorEmisorFactura").checked = false;
			document.getElementById("hiddenEmisor").value = "GESTORIA";
		}else{
			document.getElementById("hiddenEmisor").value = "";
		}
	}
}


