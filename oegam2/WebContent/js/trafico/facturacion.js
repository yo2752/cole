	/**
	 * Funciones JS relacionadas con Facturacion
	 */
	
	var urlNuevoHonorario = "nuevoHonorarioFacturacionAjax.action?honorarioTodo=";
	var urlNuevoSuplido = "nuevoSuplidoFacturacionAjax.action?suplidoTodo=";
	var urlNuevoGasto = "nuevoGastoFacturacionAjax.action?gastoTodo=";
	
	function buscarPersonaFacturacion(param) {
		if (param == 'cif') {
			if (document.getElementById('cif') == null
					|| document.getElementById('cif').value == "") {
				alert("Rellene el campo cif para 'buscar'");
				return false;
			}
			document.formData.action = "buscarPersonaFacturacion.action?boton=lupaCif#Facturacion";
			document.formData.submit();
			return true;
		}
		if (param == 'nif') {
			if (document.getElementById('nif') == null
					|| document.getElementById('nif').value == "") {
				alert("Rellene el campo nif para 'buscar'");
				return false;
			}
			document.formData.action = "buscarPersonaFacturacion.action?boton=lupaNif#Facturacion";
			document.formData.submit();
			return true;
		}
	}
	
	//***************************************************************************
	//*************************** CONSULTA DE FACTURAS **************************
	//***************************************************************************
	/* DRC@15/06/2012 Los tramites seleccionados se envian a nueva factura */
	function nuevaFactura(boton) {
	
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite.");
			return false;
		}
		// DRC@25-06-2012 Esta opcion se eliminara para versiones posteriores, pero
		// la version 1.0 solo podra crear una factura a partir de 1 tramite
		var checks = document.getElementsByName("listaChecksConsultaTramite");
	
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
		var combo = document.getElementById("comboFacturacion").value;
	
		if (codigos != null) {
			mostrarLoadingConsultar(boton);
			document.forms[0].action = "nuevaFacturaGestionFacturar.action?numsExpediente="
					+ codigos + "&combo=" + combo;
			document.forms[0].submit();
		}
	}
	
	/*
	* DRC@29-06-2012 Habilita el boton de Nueva Factura, y el combo de Facturacion
	* CFS@28-08-2012 Se permite la seleccion de mas de un tramite para una factura
	*/
	function seleccionarCheck() {
		var checks = document.getElementsByName("listaChecksConsultaTramite");
		var cont = 0;
	
		for ( var i = 0; i < checks.length; i++) {
			if (checks[i].checked)
				cont++;
		}
	
		return true;
	}
	
	/*
	* DRC@29-06-2012 Habilita el boton de Nueva Factura, y el combo de Facturacion
	* CFS@28-08-2012 Se permite la seleccion de mas de un tramite para una factura
	*/
	function seleccionarExpedientes() {
		var checks = document.getElementsByName("listaChecksConsultaTramite");
		var cont = 0;
	
		for ( var i = 0; i < checks.length; i++) {
			if (checks[i].checked)
				cont++;
		}
	
		// DRC@29-06-2012 Esta opcion se eliminara para versiones posteriores, pero
		// la version 1.0 solo podra crear una factura a partir de 1 tramite
		// CFS@28-08-2012 Se permite la seleccion de mas de un tramite para una sola
		// factura
		// Si se incluya el boton de nueva factura resumen descomentar las
		// lineas de "bNuevaFacturaResumen";
		if (cont >= 1) {
			document.getElementById("bNuevaFactura").disabled = false;
			document.getElementById("comboFacturacion").disabled = false;
			// document.getElementById("bNuevaFacturaResumen").disabled = false;
			document.getElementById("bNuevaFactura").style.color = "#000000";
			document.getElementById("comboFacturacion").style.color = "#000000";
			//document.getElementById("bNuevaFacturaResumen").style.color = "#000000";
		} else {
			document.getElementById("bNuevaFactura").disabled = true;
			document.getElementById("comboFacturacion").disabled = true;
			// document.getElementById("bNuevaFacturaResumen").disabled = true;
			document.getElementById("bNuevaFactura").style.color = "#6E6E6E";
			document.getElementById("comboFacturacion").style.color = "#6E6E6E";
			//document.getElementById("bNuevaFacturaResumen").style.color = "#6E6E6E";
		}
		return true;
	}
	
	function buscarTramiteTraficoFacturacion() {
		
		
		var matricula = new String(document.getElementById("matricula").value);
		
		matricula=Limpia_Caracteres_Matricula(matricula);
		
		document.getElementById("matricula").value = matricula.toUpperCase();
		
		
		
		if (!validaNIF(document.getElementById("dniTitular"))) {
			alert("No has introducido un DNI correcto");
			return false;
		}
		var numExpediente = document.getElementById("numExpediente").value;
		if (isNaN(numExpediente)) {
			alert("El campo n\u00FAmero de expediente debe contener solo n\u00FAmeros");
		}
		// //Para que muestre el loading
		// mostrarLoadingBuscar(boton);
		// documen.formData.action="<metodoEnElAction><nombreAction-"Action">.action
		document.formData.action = "buscarTramitesFacturacionGestionFacturar.action";
		document.formData.submit();
	}
	
	function cambiarElementosPorPaginaConsultaFactura() {
	
		document.location.href = 'navegarGestionFacturar.action?resultadosPorPagina='
				+ document.formData.idResultadosPorPagina.value;
	}
	
	function nuevaFacturaSinExpediente(boton) {
	
		var codigos = "";
		var combo = document.getElementById("comboFacturacion").value;
	
		mostrarLoadingConsultar(boton);
		document.forms[0].action = "nuevaFacturaSinExpedienteGestionFacturar.action?numsExpediente="
				+ codigos + "&combo=" + combo;
		document.forms[0].submit();
	}
	
	function cambiarElementosPorPaginaConsultaFacturaAbono() {
	
		document.location.href = 'navegarAbonoGestionFacturar.action?resultadosPorPagina='
				+ document.formData.idResultadosPorPagina.value;
	}
	
	function nuevaFacturaResumen(boton) {
	
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite.");
			return false;
		}
		// DRC@25-06-2012 Esta opcion se eliminara para versiones posteriores, pero
		// la version 1.0 solo podra crear una factura a partir de 1 tramite
		/*
		 * if(numChecked() > 1) { alert("Debe seleccionar un tr\u00E1mite."); return
		 * false; }
		 */
		var checks = document.getElementsByName("listaChecksConsultaTramite");
	
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
		var combo = document.getElementById("comboFacturacion").value;
	
		if (codigos != null) {
			mostrarLoadingConsultar(boton);
			document.forms[0].action = "nuevaFacturaResumenGestionFacturar.action?numsExpediente="
					+ codigos + "&combo=" + combo;
			document.forms[0].submit();
		}
	}
	
	function utilizarDatosTitular(){
		var checkBox = document.getElementById('utilizarTitular');
		if(checkBox.checked){
			desactivarCamposFacturacion();
		}else{
			activarCamposFacturacion();
		}
	}
	
	function desactivarCamposFacturacion(){
		document.getElementById("nif").disabled = true;
		document.getElementById("nombre").disabled = true;
		document.getElementById("apellido1").disabled = true;
		document.getElementById("apellido2").disabled = true;
		// Select:
		document.getElementById("tipoPersona").disabled = true;
		// Select:
		document.getElementById("idProvinciaFacturacion").disabled = true;
		// Select:
		document.getElementById("idMunicipioFacturacion").disabled = true;
		// Select:
		document.getElementById("idPuebloFacturacion").disabled = true;
		document.getElementById("cpFacturacion").disabled = true;
		// Select:
		document.getElementById("tipoViaFacturacion").disabled = true;
		document.getElementById("nombreViaFacturacion").disabled = true;
		document.getElementById("numeroDireccionFacturacion").disabled = true;
		document.getElementById("letraDireccionFacturacion").disabled = true;
		document.getElementById("escaleraDireccionFacturacion").disabled = true;
		document.getElementById("pisoDireccionFacturacion").disabled = true;
		document.getElementById("puertaDireccionFacturacion").disabled = true;
		document.getElementById("bloqueDireccionFacturacion").disabled = true;
		document.getElementById("kmDireccionFacturacion").disabled = true;
		document.getElementById("hmDireccionFacturacion").disabled = true;
		document.getElementById("cpFacturacion").disabled = true;
		
		document.getElementById("nif").value = "";
		document.getElementById("nombre").value = "";
		document.getElementById("apellido1").value = "";
		document.getElementById("apellido2").value = "";
		// Select:
		document.getElementById("tipoPersona").selectedIndex = 0;
		// Select:
		document.getElementById("idProvinciaFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("idMunicipioFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("idPuebloFacturacion").selectedIndex = 0;
		document.getElementById("cpFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("tipoViaFacturacion").selectedIndex = 0;
		document.getElementById("nombreViaFacturacion").value = "";
		document.getElementById("numeroDireccionFacturacion").value = "";
		document.getElementById("letraDireccionFacturacion").value = "";
		document.getElementById("escaleraDireccionFacturacion").value = "";
		document.getElementById("pisoDireccionFacturacion").value = "";
		document.getElementById("puertaDireccionFacturacion").value = "";
		document.getElementById("bloqueDireccionFacturacion").value = "";
		document.getElementById("kmDireccionFacturacion").value = "";
		document.getElementById("hmDireccionFacturacion").value = "";
		document.getElementById("cpFacturacion").value = "";
	}
	
	function activarCamposFacturacion(){
		document.getElementById("nif").disabled = false;
		document.getElementById("nombre").disabled = false;
		document.getElementById("apellido1").disabled = false;
		document.getElementById("apellido2").disabled = false;
		// Select:
		document.getElementById("tipoPersona").disabled = false;
		// Select:
		document.getElementById("idProvinciaFacturacion").disabled = false;
		// Select:
		document.getElementById("idMunicipioFacturacion").disabled = false;
		// Select:
		document.getElementById("idPuebloFacturacion").disabled = false;
		document.getElementById("cpFacturacion").disabled = false;
		// Select:
		document.getElementById("tipoViaFacturacion").disabled = false;
		document.getElementById("nombreViaFacturacion").disabled = false;
		document.getElementById("numeroDireccionFacturacion").disabled = false;
		document.getElementById("letraDireccionFacturacion").disabled = false;
		document.getElementById("escaleraDireccionFacturacion").disabled = false;
		document.getElementById("pisoDireccionFacturacion").disabled = false;
		document.getElementById("puertaDireccionFacturacion").disabled = false;
		document.getElementById("bloqueDireccionFacturacion").disabled = false;
		document.getElementById("kmDireccionFacturacion").disabled = false;
		document.getElementById("hmDireccionFacturacion").disabled = false;
	}
	
	function loadingFacturacion(){
		document.getElementById("bGuardarFac").disabled = "true";
		document.getElementById("bGuardarFac").style.color = "#6E6E6E";
		document.getElementById("bGuardarFac").value = "Procesando...";
		document.getElementById("loadingImageFac").style.display = "block";
	}
	
	function validacionesTitularFacturacion(tieneTitular){
		if(tieneTitular){
			if (document.getElementById("utilizarTitular").checked == true) {
				return true;
			}
		}
		if (document.getElementById("tipoPersona").selectedIndex == 0) {
			alert("Debe introducir el tipo de persona");
			return false;
		} 
		if (document.getElementById("tipoPersona").selectedIndex == 1) {
			if (document.getElementById("nif").value == "") {
				alert("Debe introducir un nif para personas fisicas.");
				return false;
			} 
			if (!validaNIF(document.getElementById("nif"))) {
				alert("nif no valido.");
				return false;
			}
			if (document.getElementById("nombre").value == "") {
				alert("Debe introducir un nombre");
				return false;
			}
			if (document.getElementById("apellido1").value == "") {
				alert("Debe introducir los apellidos para personas fisicas");
				return false;
			}
			// DRC@19-10-2012 Incidencia: 2635
			//if (document.getElementById("apellido2").value == "") {
			//	alert("Debe introducir los apellidos para personas fisicas");
			//	return false;
			//}
		}
		if (document.getElementById("tipoPersona").selectedIndex == 2) {
			if (document.getElementById("nif").value == "") {
				alert("Debe introducir un cif para personas juridicas.");
				return false;
			} 
			if (document.getElementById("apellido1").value == "") {
				alert("Debe introducir la razon social para personas juridicas");
				return false;
			}
		}
		if (document.getElementById("idProvinciaFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar provincia");
			return false;
		}
		if (document.getElementById("idMunicipioFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar municipio");
			return false;
		}
		if (document.getElementById("tipoViaFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar el tipo de via");
			return false;
		}
		if (document.getElementById("nombreViaFacturacion").value == "") {
			alert("Debe seleccionar el nombre de la via");
			return false;
		}
		if (document.getElementById("numeroDireccionFacturacion").value == "") {
			alert("Debe seleccionar el numero de la via");
			return false;
		}
		return true;
	}
	
	// Generar la llamada Ajax para eliminar el Concepto
	function eliminarConcepto(conceptoIdBorrar){
		
		var urlEliminarConcepto = "eliminarConceptoFacturacionAjax.action?conceptoIdBorrar="+conceptoIdBorrar;				
		url=urlEliminarConcepto+"&idConceptoActual="+document.getElementById("idConceptoActual").value+
		"&concepto="+escape(document.getElementById("concepto").value)
		+"&idExpAsociado="+document.getElementById("idExpAsociado").value;	
		
		document.getElementById("idConceptoActual").value = "-1";
		document.getElementById("concepto").value = "";
		document.getElementById("idExpAsociado").value = "-1";
		
		
		var req_generico_elimina_concepto = NuevoAjax();
		 
		// Hace la llamada a ajax
		req_generico_elimina_concepto.onreadystatechange = function () { 
			eliminaConcepto(req_generico_elimina_concepto);
			};
		
		req_generico_elimina_concepto.open("POST", url, true);
		
		req_generico_elimina_concepto.send(null);
		
	}
	
	// Generar la llamada Ajax para eliminar un Honorario
	function eliminarHonorario(honorarioIdBorrar){
			
		selectedOption = escape(document.getElementById("honorarioTodo").value);
		
		var urlEliminarHonorario = "eliminarHonorarioFacturacionAjax.action?honorarioTodo=";		
		url=urlEliminarHonorario+selectedOption+"&honorarioIdBorrar="+honorarioIdBorrar;
		
		var req_generico_elimina_honorario = NuevoAjax();
		 
		// Hace la llamada a ajax
		req_generico_elimina_honorario.onreadystatechange = function () { 
				rellenarNuevosHonorarios(req_generico_elimina_honorario,"0");
			}
		
		req_generico_elimina_honorario.open("POST", url, true);
		
		req_generico_elimina_honorario.send(null);
		
	}
	
	function eliminarSuplido(suplidoIdBorrar){
		selectedOption = escape(document.getElementById("suplidoTodo").value);
		
		var urlEliminarSuplido = "eliminarSuplidoFacturacionAjax.action?suplidoTodo=";		
		url=urlEliminarSuplido+selectedOption+"&suplidoIdBorrar="+suplidoIdBorrar;
		
		var req_generico_elimina_suplido = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_elimina_suplido.onreadystatechange = function () { 
				rellenarNuevosSuplidos(req_generico_elimina_suplido,"0");
			}
		
		req_generico_elimina_suplido.open("POST", url, true);
		
		req_generico_elimina_suplido.send(null);
	}
	
	function eliminarGasto(gastoIdBorrar){
		selectedOption = escape(document.getElementById("gastoTodo").value);
		
		var urlEliminarGasto = "eliminarGastoFacturacionAjax.action?gastoTodo=";		
		url=urlEliminarGasto+selectedOption+"&gastoIdBorrar="+gastoIdBorrar;
		
		var req_generico_elimina_gasto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_elimina_gasto.onreadystatechange = function () { 
				rellenarNuevosGastos(req_generico_elimina_gasto);
			};
		
		req_generico_elimina_gasto.open("POST", url, true);
		
		req_generico_elimina_gasto.send(null);
	}
	
	// Generar la llamada Ajax para generar una nueva fila
	function obtenerNuevoConcepto(){
		
		document.getElementById("conceptoActivo").style.display='block';
		document.getElementById("conceptoNoActivo").style.display='none';
		
		var urlNuevoConcepto = "nuevoConceptoFacturacionAjax.action?";		
		url=urlNuevoConcepto+"idConceptoActual="+document.getElementById("idConceptoActual").value+
		"&concepto="+escape(document.getElementById("concepto").value)
		+"&idExpAsociado="+document.getElementById("idExpAsociado").value
		+"&honorarioTodo="+escape(document.getElementById("honorarioTodo").value)
		+"&suplidoTodo="+escape(document.getElementById("suplidoTodo").value)
		+"&gastoTodo="+escape(document.getElementById("gastoTodo").value);
		
		
		var req_generico_nuevo_concepto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_nuevo_concepto.onreadystatechange = function () { 
			rellenarNuevosConceptos(req_generico_nuevo_concepto);
			}
		
		req_generico_nuevo_concepto.open("POST", url, true);
		
		req_generico_nuevo_concepto.send(null);
	}
	
	// Generar la llamada Ajax para generar una nueva fila
	function obtenerNuevoHonorario(){
		
		selectedOption = escape(document.getElementById("honorarioTodo").value);
		
		url=urlNuevoHonorario+selectedOption;
		
		var req_generico_nuevo_honorario = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_nuevo_honorario.onreadystatechange = function () { 
				rellenarNuevosHonorarios(req_generico_nuevo_honorario,"0");
			}
		
		req_generico_nuevo_honorario.open("POST", url, true);
		
		req_generico_nuevo_honorario.send(null);
	}
	
	// Generar la llamada Ajax para generar una nueva fila
	function obtenerNuevoSuplido(){
		
		selectedOption = escape(document.getElementById("suplidoTodo").value);
		
		url=urlNuevoSuplido+selectedOption;
		
		var req_generico_nuevo_suplido = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_nuevo_suplido.onreadystatechange = function () { 
				rellenarNuevosSuplidos(req_generico_nuevo_suplido,"0");
			}
		
		req_generico_nuevo_suplido.open("POST", url, true);
		
		req_generico_nuevo_suplido.send(null);
	
	}
	
	// Generar la llamada Ajax para generar una nueva fila
	function obtenerNuevoGasto(){

		selectedOption = escape(document.getElementById("gastoTodo").value);
		
		url=urlNuevoGasto+selectedOption;
		
		var req_generico_nuevo_gasto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_nuevo_gasto.onreadystatechange = function () { 
				rellenarNuevosGastos(req_generico_nuevo_gasto);
			}
		
		req_generico_nuevo_gasto.open("POST", url, true);
		
		req_generico_nuevo_gasto.send(null);
	
	}
	
	function actualizarHonorariosConceptoParaFacturaResumen(conceptoIdCambio, accion){
		
		var urlActualizaHonorarios = "actualizarHonorariosFacturacionAjax.action?";		
		url=urlActualizaHonorarios+"honorarioTodo="+escape(document.getElementById("honorarioTodo").value)+
		"&idConceptoActual="+document.getElementById("idConceptoActual").value+
		"&concepto="+escape(document.getElementById("concepto").value)
		+"&idExpAsociado= ";
		
		var req_generico_actualiza_honorario = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_honorario.onreadystatechange = function () { 
				actualizaHonorarios(req_generico_actualiza_honorario,conceptoIdCambio, accion);
			};
		
		req_generico_actualiza_honorario.open("POST", url, true);
		
		req_generico_actualiza_honorario.send(null);
	}
	
	function actualizarHonorariosConcepto(conceptoIdCambio, accion){
				
		var urlActualizaHonorarios = "actualizarHonorariosFacturacionAjax.action?";		
		url=urlActualizaHonorarios+"honorarioTodo="+escape(document.getElementById("honorarioTodo").value)+
		"&idConceptoActual="+document.getElementById("idConceptoActual").value+
		"&concepto="+escape(document.getElementById("concepto").value)
		+"&idExpAsociado="+document.getElementById("idExpAsociado").value;
		
		var req_generico_actualiza_honorario = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_honorario.onreadystatechange = function () { 
				actualizaHonorarios(req_generico_actualiza_honorario,conceptoIdCambio, accion);
			};
		
		req_generico_actualiza_honorario.open("POST", url, true);
		
		req_generico_actualiza_honorario.send(null);
	
	}
	
	function actualizarSuplidosConcepto(conceptoIdCambio, accion){
		
		var urlActualizaSuplidos = "actualizarSuplidosFacturacionAjax.action?";		
		url=urlActualizaSuplidos+"suplidoTodo="+escape(document.getElementById("suplidoTodo").value)+
		"&idConceptoActual="+document.getElementById("idConceptoActual").value;
		
		var req_generico_actualiza_suplido = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_suplido.onreadystatechange = function () { 
				actualizaSuplidos(req_generico_actualiza_suplido,conceptoIdCambio, accion);
			};
		
		req_generico_actualiza_suplido.open("POST", url, true);
		
		req_generico_actualiza_suplido.send(null);
		
	}
	
	function actualizarGastosConcepto(conceptoIdCambio, accion){
	
		var urlActualizaGastos = "actualizarGastosFacturacionAjax.action?";		
		url=urlActualizaGastos+"gastoTodo="+escape(document.getElementById("gastoTodo").value)+
		"&idConceptoActual="+document.getElementById("idConceptoActual").value;
		
		var req_generico_actualiza_gastos = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_gastos.onreadystatechange = function () { 
				actualizaGastos(req_generico_actualiza_gastos,conceptoIdCambio,accion);
			};
		
		req_generico_actualiza_gastos.open("POST", url, true);
		
		req_generico_actualiza_gastos.send(null);
	
	}
	
	function recuperarHonorarioConcepto(){
		
		var urlActualizaHonorarioConcepto = "recuperarHonorarioConceptoFacturacionAjax.action?";		
		url=urlActualizaHonorarioConcepto;
		
		var req_generico_actualiza_honorario_concepto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_honorario_concepto.onreadystatechange = function () { 
			rellenarNuevosHonorarios(req_generico_actualiza_honorario_concepto,"1");
			};
		
		req_generico_actualiza_honorario_concepto.open("POST", url, true);
		
		req_generico_actualiza_honorario_concepto.send(null);
		
	}
	
	function recuperarSuplidoConcepto(){
	
		var urlActualizaSuplidoConcepto = "recuperarSuplidoConceptoFacturacionAjax.action?";		
		url=urlActualizaSuplidoConcepto;
		
		var req_generico_actualiza_suplido_concepto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_suplido_concepto.onreadystatechange = function () { 
			rellenarNuevosSuplidos(req_generico_actualiza_suplido_concepto,"1");
			};
		
		req_generico_actualiza_suplido_concepto.open("POST", url, true);
		
		req_generico_actualiza_suplido_concepto.send(null);
		
	}
	
	function recuperarGastoConcepto(){
	
		var urlActualizaGastoConcepto = "recuperarGastoConceptoFacturacionAjax.action?";		
		url=urlActualizaGastoConcepto;
		
		var req_generico_actualiza_gasto_concepto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_actualiza_gasto_concepto.onreadystatechange = function () { 
			rellenarNuevosGastos(req_generico_actualiza_gasto_concepto);
			};
		
		req_generico_actualiza_gasto_concepto.open("POST", url, true);
		
		req_generico_actualiza_gasto_concepto.send(null);
	
	}
	
	// Generar la llamada Ajax para cambiar de concepto
	function seleccionarConcepto(conceptoIdCambio){
		
		document.getElementById("conceptoActivo").style.display='block';
		document.getElementById("conceptoNoActivo").style.display='none';
		
		var urlCambioConcepto = "cambioConceptoFacturacionAjax.action?";
		url=urlCambioConcepto+"conceptoIdCambio="+conceptoIdCambio;		
		
		document.getElementById("idConceptoActual").value = conceptoIdCambio ;
		
		var req_generico_cambio_concepto = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_cambio_concepto.onreadystatechange = function () { 
				rellenarConceptos(req_generico_cambio_concepto);
			};
		
		req_generico_cambio_concepto.open("POST", url, true);
		
		req_generico_cambio_concepto.send(null);
		
	}
	function limpiarFormularioClienteFacturacion(){
		var rowid = document.getElementById("rowidCliente").value;
		if (rowid!=null && rowid!="") {
			document.getElementById("apellido1RazonSocialCliente").value = "";
			document.getElementById("apellido2Cliente").value = "";
			document.getElementById("nombreCliente").value = "";
			document.getElementById("sexoCliente").value = "-1";
			// document.getElementById("anagramaTitular").value = "";
			// document.getElementById("diaNacTitular").value = "";
			// document.getElementById("mesNacTitular").value = "";
			// document.getElementById("anioNacTitular").value = "";
			document.getElementById("idFechaNacimientoCliente").value = "";
			// document.getElementById("estadoCivilTitular").value = "-1";
			document.getElementById("telefonoCliente").value = "";
			document.getElementById("correoElectronicoCliente").value = "";
			document.getElementById("idProvinciaPersonaFacturacion").value = "-1";
			document.getElementById("idMunicipioPersonaFacturacion").length = 0;
			document.getElementById("idMunicipioPersonaFacturacion").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoPersonaFacturacion").value = "";
			document.getElementById("idPuebloPersonaFacturacion").length = 0;
			document.getElementById("idPuebloPersonaFacturacion").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoPersonaFacturacion").value = "";
			document.getElementById("tipoViaDireccionPersonaFacturacion").value = "-1";
			document.getElementById("nombreViaPersonaFacturacion").value = "";
			document.getElementById("numeroDireccionCliente").value = "";
			document.getElementById("codPostalClienteFacturacion").value = "";
			document.getElementById("letraDireccionCliente").value = "";
			document.getElementById("escaleraDireccionCliente").value = "";
			document.getElementById("pisoDireccionCliente").value = "";
			document.getElementById("puertaDireccionCliente").value = "";
			document.getElementById("bloqueDireccionCliente").value = "";
			document.getElementById("kmDireccionCliente").value = "";
			document.getElementById("hmDireccionCliente").value = "";
			// document.getElementById("idAutonomo").value = "";
			// document.getElementById("epigrafe").value = "";
			// document.getElementById("iae").value = "";
		}
	}
	
	// Callback function de RellenarHonorarios
	function rellenarNuevosHonorarios(req_honorarios,valorHon){	
		
		if (req_honorarios.readyState == 4) { // Complete
			if (req_honorarios.status == 200) { // OK response
				
				//document.location = "detalleSesionGestionFacturar.action";
				
				var separador         = "\\";
				
				// Split the document
								
				var returnElementsHon=req_honorarios.responseText;
								
				
				if (returnElementsHon.substring(0, 1) == separador) {
					returnElementsHon = returnElementsHon.substring(1, returnElementsHon.length);
				}
				
				if (returnElementsHon.substring(returnElementsHon.length-1, returnElementsHon.length) == separador) {
					returnElementsHon = returnElementsHon.substring(0, returnElementsHon.length-1);
				}
				
				// DRC@30-08-2012Recorre los valores de los Honorarios
				if (returnElementsHon != null && returnElementsHon != "") {
					var elemento     = returnElementsHon.split(separador);
					
					var tamHonorario = (elemento.length / 12);					
					var elem = 0;
	
					tamTablaHonorario(tamHonorario, 'mitablahonorarioDis', elemento);
															
					for (var i = 0; i < tamHonorario; i++) {
												
						var a = elem;
						var b = elem+1;
						var c = elem+2;
						var d = elem+3;
						var e = elem+4;
						var f = elem+5;
						var g = elem+6;
						var h = elem+7;
						var j = elem+8;
						var k = elem+9;
						var l = elem+10;
						var m = elem+11;				
		//alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c] + ', ' + elemento[d] + ', ' + elemento[e] + ', ' + elemento[f] + ', ' + elemento[g] + ', ' + elemento[h] + ', ' + elemento[j] + ', ' + elemento[k]+ ', ' + elemento[l]+ ', ' + elemento[m]);						
						if (elemento[a] != null) {
							if (elemento[a] == null || elemento[a] == "null" ) {
							document.getElementById("idContadorHonorario" + i).value    = elemento[a];
							} else {							
							document.getElementById("idContadorHonorario"  + i).value = i;
							}
							
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("idHonorarioDescripcion"  + i).value = "";
							} else {
								document.getElementById("idHonorarioDescripcion"  + i).value = elemento[b];
							}
							
							if (elemento[c] == null || elemento[c] == "null") {
								document.getElementById("idHonorario"  + i).value = "";
							} else {
								document.getElementById("idHonorario"  + i).value = elemento[c];
							}
							
							if (elemento[d] == "true" || elemento[d] == "1") {
								document.getElementById("idCheckHonorario" + i).checked = true;
							} else if (elemento[d] == "false" || elemento[d] == "0") {
								document.getElementById("idCheckHonorario" + i).checked = false;
							}
							
							if(!isNaN(elemento[e])){
								document.getElementById("idHonorarioIVA" + i).value   = elemento[e];	
							}
							habilitarCampo("idCheckHonorario" + i, "idHonorarioIVA" + i);
							
	
							if (elemento[g] == "true" || elemento[g] == "1") {
								document.getElementById("idCheckDescuentoHonorario" + i).checked = true;
							} else if (elemento[g] == "false" || elemento[g] == "0") {
								document.getElementById("idCheckDescuentoHonorario" + i).checked = false;
							}							
							
							if (elemento[h] == null || elemento[h] == "null") {
								document.getElementById("idHonorarioDescuento"  + i).value = "";
							} else {
								document.getElementById("idHonorarioDescuento"  + i).value = elemento[h];
							}
							
							habilitarCampo("idCheckDescuentoHonorario" + i, "idHonorarioDescuento" + i);
	
							if (elemento[j] == "true" || elemento[j] == "1") {
								document.getElementById("idCheckIRPFHonorario" + i).checked = true;
							} else if (elemento[j] == "false" || elemento[j] == "0") {
								document.getElementById("idCheckIRPFHonorario" + i).checked = false;
							}
							
							if(!isNaN(elemento[k])){
							document.getElementById("idIRPF" + i).value   = elemento[k];
							}
							habilitarCampo("idCheckIRPFHonorario" + i, "idIRPF" + i);
							
							if (elemento[m] == null || elemento[m] == "null") {
								document.getElementById("idTotalHonorarios"  + i).value = "0";
							} else {
								document.getElementById("idTotalHonorarios"  + i).value = elemento[m];
							}
							
							elem = elem + 12;
						}
					}			
				}
				if (valorHon=="1"){
					recuperarSuplidoConcepto();
				}
				calcularFacturaBoton();
			}
		}
	}
	
	// Callback function de RellenarSuplidos
	function rellenarNuevosSuplidos(req_suplidos,valorSup){
		
		
		if (req_suplidos.readyState == 4) { // Complete
			if (req_suplidos.status == 200) { // OK response
				
				//document.location = "detalleSesionGestionFacturar.action";
				
				var separador         = "\\";
				
				// Split the document
								
				var returnElementsSup=req_suplidos.responseText;
								
				
				if (returnElementsSup.substring(0, 1) == separador) {
					returnElementsSup = returnElementsSup.substring(1, returnElementsSup.length);
				}
				
				if (returnElementsSup.substring(returnElementsSup.length-1, returnElementsSup.length) == separador) {
					returnElementsSup = returnElementsSup.substring(0, returnElementsSup.length-1);
				}
				
				// DRC@30-08-2012Recorre los valores de los Suplidos
				if (returnElementsSup != null && returnElementsSup != "") {
					var elemento     = returnElementsSup.split(separador);
					
					var tamSuplido = (elemento.length / 6);					
					var elem = 0;
	
					tamTablaSuplido(tamSuplido, 'mitablaSuplidoDis', elemento);
															
					for (var i = 0; i < tamSuplido; i++) {
												
						var a = elem;
						var b = elem+1;
						var c = elem+2;
						var d = elem+3;
						var e = elem+4;
						var f = elem+5;
	//					var g = elem+6;
	//					var h = elem+7;
	//					var j = elem+8;
	//					var k = elem+9;
	//					var l = elem+10;
	//					var m = elem+11;				
	//	alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c] + ', ' + elemento[d] + ', ' + elemento[e] + ', ' + elemento[f] /*+ ', ' + elemento[g] + ', ' + elemento[h] + ', ' + elemento[j] + ', ' + elemento[k]+ ', ' + elemento[l]+ ', ' + elemento[m]*/);
						if (elemento[a] != null) {							
							if (elemento[a] == null || elemento[a] == "null" ) {
								document.getElementById("idContadorSuplido" + i).value = elemento[a];
								} else {							
									document.getElementById("idContadorSuplido" + i).value = i;
								}
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("idSuplidoDescripcion"  + i).value = "";
							} else {
								document.getElementById("idSuplidoDescripcion"  + i).value = elemento[b];
							}	
							if (elemento[c] == null || elemento[c] == "null") {
								document.getElementById("idSuplido"  + i).value = "";
							} else {
								document.getElementById("idSuplido"  + i).value = elemento[c];
							}
							
	//						if (elemento[d] == "true" || elemento[d] == "1") {
	//							document.getElementById("idCheckSuplido" + i).checked = true;
	//						} else if (elemento[d] == "false" || elemento[d] == "0") {
	//							document.getElementById("idCheckSuplido" + i).checked = false;
	//						}
	//						
	//						if(!isNaN(elemento[e])){
	//							document.getElementById("idSuplidoIVA" + i).value   = elemento[e];	
	//						}
	//						habilitarCampo("idCheckSuplido" + i, "idSuplidoIVA" + i);
							
	
							if (elemento[d] == "true" || elemento[d] == "1") {
								document.getElementById("idCheckDescuentoSuplido" + i).checked = true;
							} else if (elemento[d] == "false" || elemento[d] == "0") {
								document.getElementById("idCheckDescuentoSuplido" + i).checked = false;
							}							
							if (elemento[e] == null || elemento[e] == "null") {
								document.getElementById("idSuplidoDescuento"  + i).value = "";
							} else {
								document.getElementById("idSuplidoDescuento"  + i).value = elemento[e];
							}
							habilitarCampo("idCheckDescuentoSuplido" + i, "idSuplidoDescuento" + i);
	
	//						if (elemento[j] == "true" || elemento[j] == "1") {
	//							document.getElementById("idCheckIRPFSuplido" + i).checked = true;
	//						} else if (elemento[j] == "false" || elemento[j] == "0") {
	//							document.getElementById("idCheckIRPFSuplido" + i).checked = false;
	//						}
	//						
	//						if(!isNaN(elemento[k])){
	//						document.getElementById("idIRPF" + i).value   = elemento[k];
	//						}
	//						habilitarCampo("idCheckIRPFSuplido" + i, "idIRPF" + i);
							
							if (elemento[f] == null || elemento[f] == "null") {
								document.getElementById("idTotalSuplido"  + i).value = "";
							} else {
								document.getElementById("idTotalSuplido"  + i).value = elemento[f];
							}
							elem = elem + 6;
						}
					}			
				}
				if (valorSup=="1"){
					recuperarGastoConcepto();
				}
				calcularFacturaBoton();
			}
		}
	}
	
	// Callback function de RellenarGastos
	function rellenarNuevosGastos(req_gastos){
		
		if (req_gastos.readyState == 4) { // Complete
			if (req_gastos.status == 200) { // OK response
				
				//document.location = "detalleSesionGestionFacturar.action";
				
				var separador         = "\\";
				
				// Split the document
								
				var returnElementsGasto=req_gastos.responseText;
								
				
				if (returnElementsGasto.substring(0, 1) == separador) {
					returnElementsGasto = returnElementsGasto.substring(1, returnElementsGasto.length);
				}
				
				if (returnElementsGasto.substring(returnElementsGasto.length-1, returnElementsGasto.length) == separador) {
					returnElementsGasto = returnElementsGasto.substring(0, returnElementsGasto.length-1);
				}
				
				// DRC@30-08-2012Recorre los valores de los Suplidos
				if (returnElementsGasto != null && returnElementsGasto != "") {
					var elemento     = returnElementsGasto.split(separador);
					
					var tamGasto = (elemento.length / 6);					
					var elem = 0;
	
					tamTablaGasto(tamGasto, 'mitablaGastoDis', elemento);
															
					for (var i = 0; i < tamGasto; i++) {
												
						var a = elem;
						var b = elem+1;
						var c = elem+2;
						var d = elem+3;
						var e = elem+4;
						var f = elem+5;
	//					var g = elem+6;
	//					var h = elem+7;
	//					var j = elem+8;
	//					var k = elem+9;
	//					var l = elem+10;
	//					var m = elem+11;				
	//	alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c] + ', ' + elemento[d] + ', ' + elemento[e] + ', ' + elemento[f] /*+ ', ' + elemento[g] + ', ' + elemento[h] + ', ' + elemento[j] + ', ' + elemento[k]+ ', ' + elemento[l]+ ', ' + elemento[m]*/);
						if (elemento[a] != null) {							
							if (elemento[a] == null || elemento[a] == "null" ) {
								document.getElementById("idContadorGasto" + i).value = elemento[a];
								} else {							
									document.getElementById("idContadorGasto" + i).value = i;
								}
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("idGastoDescripcion" + i).value = "";
							} else {
								document.getElementById("idGastoDescripcion" + i).value = elemento[b];
							}	
							if (elemento[c] == null || elemento[c] == "null") {
								document.getElementById("idGasto" + i).value = "";
							} else {
								document.getElementById("idGasto" + i).value = elemento[c];
							}
							
							if (elemento[d] == "true" || elemento[d] == "1") {
								document.getElementById("idCheckGasto" + i).checked = true;
							} else if (elemento[d] == "false" || elemento[d] == "0") {
								document.getElementById("idCheckGasto" + i).checked = false;
							}
	//						
							if(!isNaN(elemento[e])){
								document.getElementById("idGastoIVA" + i).value   = elemento[e];	
							}
							habilitarCampo("idCheckGasto" + i, "idGastoIVA" + i);
							
							if (elemento[f] == null || elemento[f] == "null") {
								document.getElementById("idTotalGastos"  + i).value = "";
							} else {
								document.getElementById("idTotalGastos"  + i).value = elemento[f];
							}
							elem = elem + 6;
						}
					}			
				}
				calcularFacturaBoton();
				
			}
		}
	}
	
	function rellenarNuevosConceptos(req_conceptos){
				
		if (req_conceptos.readyState == 4) { // Complete
			if (req_conceptos.status == 200) { // OK response
				
				var returnElementsConHon=req_conceptos.responseText;
				
				seleccionarConcepto(returnElementsConHon);				
			}
		}
	}
	
	// Callback function de actualizaHonorarios
	function actualizaHonorarios(req_actualiza,idConcepto, accion){
		
		
		if (req_actualiza.readyState == 4) { // Complete
			if (req_actualiza.status == 200) { // OK response
				
				var separador         = "1";
								
				var returnElementsHon=req_actualiza.responseText;
								
				pasarSuplidoAjax(idConcepto,accion);
				
			}
		}
	}
	
	// Callback function de actualizaSuplidos
	function actualizaSuplidos(req_actualiza,idConcepto, accion){
		
		
		if (req_actualiza.readyState == 4) { // Complete
			if (req_actualiza.status == 200) { // OK response
				
				var separador         = "1";
								
				var returnElementsHon=req_actualiza.responseText;
								
				pasarGastoAjax(idConcepto, accion);
			
				
			}
		}
	}
	
	
	// Callback function de actualizaGastos
	function actualizaGastos(req_actualiza,idConcepto,accion){
		
		
		if (req_actualiza.readyState == 4) { // Complete
			if (req_actualiza.status == 200) { // OK response
				
				var separador         = "1";
								
				var returnElementsHon=req_actualiza.responseText;
								
				if (accion == "nuevoConcepto"){
					crearConcepto();
				}
				
				if (accion == "defaultConcepto"){
					seleccionarConcepto(idConcepto);
				}
			
				
			}
		}
	}
	// Callback function de RellenarConceptos
	function rellenarConceptos(req_conceptos){	
		
		if (req_conceptos.readyState == 4) { // Complete
			if (req_conceptos.status == 200) { // OK response
				
				//document.location = "detalleSesionGestionFacturar.action";
				
				var separadorConcepto = "*";
				
				// Split the document
								
				var returnElementsCon=req_conceptos.responseText;
								
				
				// DRC@30-08-2012 Recoge los valores de la cadena Concepto y elimina en caso de que existan los guiones que sobren
				if (returnElementsCon.substring(0, 1) == separadorConcepto) {
					returnElementsCon = returnElementsCon.substring(1, returnElementsCon.length);
				}
				if (returnElementsCon.substring(returnElementsCon.length-1, returnElementsCon.length) == separadorConcepto) {
					returnElementsCon = returnElementsCon.substring(0, returnElementsCon.length-1);
				}
	
				// DRC@30-08-2012 Recorre los valores de Coneptos
				if (returnElementsCon != null && returnElementsCon != "") {
					var elemento    = returnElementsCon.split(separadorConcepto);
					var tamConcepto = elemento.length / 3;
					var elem = 0;
					tamTablaConcepto(tamConcepto, 'mitablaconcepto', elemento);
					for (var i = 0; i < tamConcepto; i++) { 				
						var a = elem;
						var b = elem+1;
						var c = elem+2;
				//alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c]);		
						if (elemento[a] != null) {
							
							if (elemento[a] == null || elemento[a] == "null" ) {							
								document.getElementById("idContadorConcepto" + i).value = elemento[a];
								} else {							
								document.getElementById("idContadorConcepto"  + i).value = i;
							}
							
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("concepto" + i).value = "";
							} else {
								document.getElementById("concepto" + i).value = elemento[b];
							}
							if (elemento[c] == null || elemento[c] == "null") {								
								
								document.getElementById("idExpAsociado" + i).value = " ";
	
							} else {
								document.getElementById("idExpAsociado" + i).value      = elemento[c];
							}
						}
						
						//Logica para rellenar el Concepto Actual						
						if (i == document.getElementById("idConceptoActual").value){
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("concepto").value = "";
							} else {
								document.getElementById("concepto").value = elemento[b];
							}							
							if (elemento[c] == null || elemento[c] == "null" || elemento[c] == " ") {
								document.getElementById("idExpAsociado").options[0].selected = true;								
							} else {
								document.getElementById("idExpAsociado").value = elemento[c];								
							}						
							nombreConcepto();
						}
						
						elem = elem + 3;
						
					}
				
			}
				recuperarHonorarioConcepto();
				calcularFacturaBoton();
		}
		}				
	}
	
	// Callback function de EliminarConceptos
	function eliminaConcepto(req_conceptos){
				
		if (req_conceptos.readyState == 4) { // Complete
			if (req_conceptos.status == 200) { // OK response
				
				//document.location = "detalleSesionGestionFacturar.action";
				
				var separadorConcepto = "*";
				
				// Split the document
								
				var returnElementsCon=req_conceptos.responseText;
								
				
				// DRC@30-08-2012 Recoge los valores de la cadena Concepto y elimina en caso de que existan los guiones que sobren
				if (returnElementsCon.substring(0, 1) == separadorConcepto) {
					returnElementsCon = returnElementsCon.substring(1, returnElementsCon.length);
				}
				if (returnElementsCon.substring(returnElementsCon.length-1, returnElementsCon.length) == separadorConcepto) {
					returnElementsCon = returnElementsCon.substring(0, returnElementsCon.length-1);
				}
	
				// DRC@30-08-2012 Recorre los valores de Coneptos
				if (returnElementsCon != null && returnElementsCon != "") {
					var elemento    = returnElementsCon.split(separadorConcepto);
					var tamConcepto = elemento.length / 3;
					var elem = 0;
					tamTablaConcepto(tamConcepto, 'mitablaconcepto', elemento);					
					for (var i = 0; i < tamConcepto; i++) { 				
						var a = elem;
						var b = elem+1;
						var c = elem+2;
						//alert('2.- Vuelta: ' + i + ' (' + elemento[a] + ', ' + elemento[b] + ', ' + elemento[c]);
						if (elemento[a] != null) {
							if (elemento[a] == null || elemento[a] == "null" ) {							
								document.getElementById("idContadorConcepto" + i).value = elemento[a];
								} else {							
								document.getElementById("idContadorConcepto"  + i).value = i;
							}
							if (elemento[b] == null || elemento[b] == "null") {
								document.getElementById("concepto" + i).value = "";
							} else {
								document.getElementById("concepto" + i).value = elemento[b];
							}
							if (elemento[c] == null || elemento[c] == "null") {								
								document.getElementById("idExpAsociado" + i).value = " ";						
							} else {
								document.getElementById("idExpAsociado" + i).value = elemento[c];
							}
						}
					
						elem = elem + 3;
						
					}					
					document.getElementById("conceptoActivo").style.display='none';					
					document.getElementById("conceptoNoActivo").style.display='block';					
			}
		}
	}	
	}	
		
		
	function compruebaPasswordColegiadoModificarFactura(password){
		
		var urlCompruebaPassword = "compruebaPasswordFacturacionAjax.action?password=" + password;		
		
		var req_generico_comprueba_password = NuevoAjax();
		
		// Hace la llamada a ajax
		req_generico_comprueba_password.onreadystatechange = function () { 
				compruebaPasswordColegiado(req_generico_comprueba_password);
			};
		
		req_generico_comprueba_password.open("POST", urlCompruebaPassword, true);
		
		req_generico_comprueba_password.send(null);
	
		
	}
	
	function compruebaPasswordColegiado(req_compruebaPassword){
		
		if (req_compruebaPassword.readyState == 4) { // Complete
			if (req_compruebaPassword.status == 200) { // OK response
				if (req_compruebaPassword.responseText == "true"){
	//				validarModificarFactura();
					llamaActionModificar();
				}else{
					modificarFacturaPasswordRechazada();
				}
			}
		}
	}		
	
	function llamaActionModificar(){
		validarModificarFactura();
	}
	
	function cargarConceptosColegiado(NumColegiado) {
	    var conceptoColegiadoFacturacion = new BasicContentAssist(document.getElementById("concepto"), [], null, true);
	
	    var url = "recuperarNombreConceptoColegiadoFacturacionAjax.action";
	
	    var req_generico_nombre_concepto = NuevoAjax();
	    //Hace la llamada a Ajax           
	    req_generico_nombre_concepto.onreadystatechange = function () {
	    	rellenarListaNombreConcepto(req_generico_nombre_concepto, document.getElementById('concepto'), conceptoColegiadoFacturacion);
	    }
	    req_generico_nombre_concepto.open("POST", url, true);
	    req_generico_nombre_concepto.send(null);
	}
	
	function rellenarListaNombreConcepto(req_generico_nombre_concepto, selectConcepto, conceptoColegiadoFacturacion) {
	      if (req_generico_nombre_concepto.readyState == 4) { // Complete
	            if (req_generico_nombre_concepto.status == 200) { // OK response                
	                  var textToSplit = req_generico_nombre_concepto.responseText;
	                  returnElements=textToSplit.split("||");
	                  var anterior = selectConcepto.value;
	                  conceptoColegiadoFacturacion.processor.setWords(returnElements);                     
	                  selectConcepto.value=anterior;
	            }                 
	      }
	}
	
	//método que cuenta el número de checks seleccionados de Datos Sensibles
	function numCheckedDatosSensibles() {
		var checks = document.getElementsByName("listaChecksDatosSensibles");	
		var numChecked = 0;
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked) numChecked++;
		}	
		return numChecked;
	}
	
	function numCheckedFacturaAbono() {
		var checks = document.getElementsByName("listaChecksConsultaFacturaAbono");	
		var numChecked = 0;
		for(var i=0; i<checks.length; i++) {
			if(checks[i].checked) numChecked++;
		}	
		return numChecked;
	}
	
	function guardarTitularFacturacion(tieneTitular) {
		if (!validacionesTitularFacturacion(tieneTitular)){
			return false;
		}
		
		var numColegiadoFac;
		
		if (document.getElementById("idNumColegiado") != null) {
			numColegiadoFac = document.getElementById("idNumColegiado").value;
		} else if (document.getElementById("numColegiado") != null) {
			numColegiadoFac = document.getElementById("numColegiado").value;
		} else {
			numColegiadoFac = "";
		}
		
		document.formData.action = "guardarTitularFacturacion.action#Facturacion?numColegiadoFac="+numColegiadoFac;
		document.formData.submit();
		loadingFacturacion();
		return true;
	}
	
	function loadingFacturacion(){
		document.getElementById("bGuardarFac").disabled = "true";
		document.getElementById("bGuardarFac").style.color = "#6E6E6E";
		document.getElementById("bGuardarFac").value = "Procesando...";
		document.getElementById("loadingImageFac").style.display = "block";
	}
	
	function validacionesTitularFacturacion(tieneTitular){
		if(tieneTitular){
			if (document.getElementById("utilizarTitular").checked == true) {
				return true;
			}
		}
		if (document.getElementById("tipoPersona").selectedIndex == 0) {
			alert("Debe introducir el tipo de persona");
			return false;
		} 
		if (document.getElementById("tipoPersona").selectedIndex == 1) {
			if (document.getElementById("nif").value == "") {
				alert("Debe introducir un nif para personas fisicas.");
				return false;
			} 
			if (!validaNIF(document.getElementById("nif"))) {
				alert("nif no valido.");
				return false;
			}
			if (document.getElementById("nombre").value == "") {
				alert("Debe introducir un nombre");
				return false;
			}
			if (document.getElementById("apellido1").value == "") {
				alert("Debe introducir los apellidos para personas fisicas");
				return false;
			}
			// DRC@19-10-2012 Incidencia: 2635
			//if (document.getElementById("apellido2").value == "") {
			//	alert("Debe introducir los apellidos para personas fisicas");
			//	return false;
			//}
		}
		if (document.getElementById("tipoPersona").selectedIndex == 2) {
			if (document.getElementById("nif").value == "") {
				alert("Debe introducir un cif para personas juridicas.");
				return false;
			} 
			if (document.getElementById("apellido1").value == "") {
				alert("Debe introducir la razon social para personas juridicas");
				return false;
			}
		}
		if (document.getElementById("idProvinciaFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar provincia");
			return false;
		}
		if (document.getElementById("idMunicipioFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar municipio");
			return false;
		}
		if (document.getElementById("tipoViaFacturacion").selectedIndex == 0) {
			alert("Debe seleccionar el tipo de via");
			return false;
		}
		if (document.getElementById("nombreViaFacturacion").value == "") {
			alert("Debe seleccionar el nombre de la via");
			return false;
		}
		if (document.getElementById("numeroDireccionFacturacion").value == "") {
			alert("Debe seleccionar el numero de la via");
			return false;
		}
		return true;
	}
	
	function desactivarCamposFacturacion(){
		document.getElementById("nif").disabled = true;
		document.getElementById("nombre").disabled = true;
		document.getElementById("apellido1").disabled = true;
		document.getElementById("apellido2").disabled = true;
		// Select:
		document.getElementById("tipoPersona").disabled = true;
		// Select:
		document.getElementById("idProvinciaFacturacion").disabled = true;
		// Select:
		document.getElementById("idMunicipioFacturacion").disabled = true;
		// Select:
		document.getElementById("idPuebloFacturacion").disabled = true;
		document.getElementById("cpFacturacion").disabled = true;
		// Select:
		document.getElementById("tipoViaFacturacion").disabled = true;
		document.getElementById("nombreViaFacturacion").disabled = true;
		document.getElementById("numeroDireccionFacturacion").disabled = true;
		document.getElementById("letraDireccionFacturacion").disabled = true;
		document.getElementById("escaleraDireccionFacturacion").disabled = true;
		document.getElementById("pisoDireccionFacturacion").disabled = true;
		document.getElementById("puertaDireccionFacturacion").disabled = true;
		document.getElementById("bloqueDireccionFacturacion").disabled = true;
		document.getElementById("kmDireccionFacturacion").disabled = true;
		document.getElementById("hmDireccionFacturacion").disabled = true;
		document.getElementById("cpFacturacion").disabled = true;
		
		document.getElementById("nif").value = "";
		document.getElementById("nombre").value = "";
		document.getElementById("apellido1").value = "";
		document.getElementById("apellido2").value = "";
		// Select:
		document.getElementById("tipoPersona").selectedIndex = 0;
		// Select:
		document.getElementById("idProvinciaFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("idMunicipioFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("idPuebloFacturacion").selectedIndex = 0;
		document.getElementById("cpFacturacion").selectedIndex = 0;
		// Select:
		document.getElementById("tipoViaFacturacion").selectedIndex = 0;
		document.getElementById("nombreViaFacturacion").value = "";
		document.getElementById("numeroDireccionFacturacion").value = "";
		document.getElementById("letraDireccionFacturacion").value = "";
		document.getElementById("escaleraDireccionFacturacion").value = "";
		document.getElementById("pisoDireccionFacturacion").value = "";
		document.getElementById("puertaDireccionFacturacion").value = "";
		document.getElementById("bloqueDireccionFacturacion").value = "";
		document.getElementById("kmDireccionFacturacion").value = "";
		document.getElementById("hmDireccionFacturacion").value = "";
		document.getElementById("cpFacturacion").value = "";
	}
	
	function activarCamposFacturacion(){
		document.getElementById("nif").disabled = false;
		document.getElementById("nombre").disabled = false;
		document.getElementById("apellido1").disabled = false;
		document.getElementById("apellido2").disabled = false;
		// Select:
		document.getElementById("tipoPersona").disabled = false;
		// Select:
		document.getElementById("idProvinciaFacturacion").disabled = false;
		// Select:
		document.getElementById("idMunicipioFacturacion").disabled = false;
		// Select:
		document.getElementById("idPuebloFacturacion").disabled = false;
		document.getElementById("cpFacturacion").disabled = false;
		// Select:
		document.getElementById("tipoViaFacturacion").disabled = false;
		document.getElementById("nombreViaFacturacion").disabled = false;
		document.getElementById("numeroDireccionFacturacion").disabled = false;
		document.getElementById("letraDireccionFacturacion").disabled = false;
		document.getElementById("escaleraDireccionFacturacion").disabled = false;
		document.getElementById("pisoDireccionFacturacion").disabled = false;
		document.getElementById("puertaDireccionFacturacion").disabled = false;
		document.getElementById("bloqueDireccionFacturacion").disabled = false;
		document.getElementById("kmDireccionFacturacion").disabled = false;
		document.getElementById("hmDireccionFacturacion").disabled = false;
	}
	
