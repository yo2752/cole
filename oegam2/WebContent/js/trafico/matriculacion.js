/**
 * Funciones JS relacionadas con Matriculacion
 */

/** Obtiene un interviniente por NIF, en Matriculacion** */
function buscarIntervinienteMatriculacion(tipoInterviniente, pestania) {
	// hidden en el action con el campo tipoIntervinienteBuscar
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "buscarIntervinienteAltasMatriculacion.action#"
			+ pestania;
	document.formData.submit();
}

/** Obtiene un interviniente por NIF, en Matriculacion Matw** */
function buscarIntervinienteMatriculacionMatw(tipoInterviniente, pestania) {
	// hidden en el action con el campo tipoIntervinienteBuscar
	document.getElementsByName('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.getElementById('tipoIntervinienteBuscar').value = tipoInterviniente;
	document.formData.action = "buscarIntervinienteAltasMatriculacionMatw.action#"
			+ pestania;
	document.formData.submit();
}

/** ************************* GUARDAR ALTA MATRICULACION *********** */
//GUARDAR ALTAS NUEVAS DE TRAMITES DE MATRICULACION
function guardarAltaMatriculacion(pestania) {

	if (!validaNIF(document.getElementById("nifTitular"))) {
		alert("NIF de Titular no valido.");
		return false;
	}

	if (!validaNIF(document.getElementById("nifRepresentante"))) {
		alert("NIF de Titular no valido.");
		return false;
	}

	if (!validaNIF(document.getElementById("idNifArrendatario"))) {
		alert("NIF de Arrendatario no valido.");
		return false;
	}

	if (document.getElementById("diaAltaIni") != null
			&& document.getElementById("mesAltaIni") != null
			&& document.getElementById("anioAltaIni") != null
			&& document.getElementById("diaAltaFin") != null
			&& document.getElementById("mesAltaFin") != null
			&& document.getElementById("anioAltaFin") != null
			&& document.getElementById("diaAltaIni").value != ""
			&& document.getElementById("mesAltaIni").value != ""
			&& document.getElementById("anioAltaIni").value != ""
			&& document.getElementById("diaAltaFin").value != ""
			&& document.getElementById("mesAltaFin").value != ""
			&& document.getElementById("anioAltaFin").value != "") {
		var fInicioConductorHabitual = new Date(document
				.getElementById("anioAltaIni").value, parseInt(document
				.getElementById("mesAltaIni").value) - 1, document
				.getElementById("diaAltaIni").value).getTime();
		var fFinConductorHabitual = new Date(document
				.getElementById("anioAltaFin").value, parseInt(document
				.getElementById("mesAltaFin").value) - 1, document
				.getElementById("diaAltaFin").value).getTime();
		var fActual = new Date().getTime();

		if (fInicioConductorHabitual > fFinConductorHabitual) {
			alert("La fecha de Inicio del Titular no puede ser mayor que la fecha de Fin.");
			return false;
		}

		if (fInicioConductorHabitual > fActual) {
			alert("La fecha de Inicio del Titular no puede ser mayor que la fecha actual.");
			return false;
		}
	}

	var valueConcepto = document.getElementById("conceptoRepresentanteTitular")
		.options[document.getElementById("conceptoRepresentanteTitular").selectedIndex].value;

	var motivoTutela = document.getElementById("motivoRepresentanteTitular")
		.options[document.getElementById("motivoRepresentanteTitular").selectedIndex].value;

	if(valueConcepto == "TUTELA O PATRIA POTESTAD"){
		if(motivoTutela == null || motivoTutela == "-1"){
			alert("Requerido el motivo de la tutela o patria potestad");
			return false;
		}
	}

	if (document.getElementById("diaInicioTutela") != null && document.getElementById("diaInicioTutela").value != ""
			&& document.getElementById("mesInicioTutela") != null && document.getElementById("mesInicioTutela").value != ""
			&& document.getElementById("anioInicioTutela") != null && document.getElementById("anioInicioTutela").value != ""
			&& document.getElementById("diaFinTutela") != null && document.getElementById("diaFinTutela").value != ""
			&& document.getElementById("mesFinTutela") != null && document.getElementById("mesFinTutela").value != ""
			&& document.getElementById("anioFinTutela") != null && document.getElementById("anioFinTutela").value != "") {
		var fInicioTutela = new Date(document
				.getElementById("anioInicioTutela").value, parseInt(document
				.getElementById("mesInicioTutela").value) - 1, document
				.getElementById("diaInicioTutela").value).getTime();
		var fFinTutela = new Date(
				document.getElementById("anioFinTutela").value,
				parseInt(document.getElementById("mesFinTutela").value) - 1,
				document.getElementById("diaFinTutela").value).getTime();
		var fActualTutela = new Date().getTime();

		if (fInicioTutela > fFinTutela) {
			alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha de Fin.");
			return false;
		}

		if (fInicioTutela > fActual) {
			alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha actual.");
			return false;
		}
	}

	if (document.getElementById("diaInicioRenting").value != ""
			&& document.getElementById("mesInicioRenting").value != ""
			&& document.getElementById("anioInicioRenting").value != ""
			&& document.getElementById("diaFinRenting").value != ""
			&& document.getElementById("mesFinRenting").value != ""
			&& document.getElementById("anioFinRenting").value != "") {
		var fInicioRenting = new Date(document
				.getElementById("anioInicioRenting").value, parseInt(document
				.getElementById("mesInicioRenting").value) - 1, document
				.getElementById("diaInicioRenting").value).getTime();
		var fFinRenting = new Date(
				document.getElementById("anioFinRenting").value,
				parseInt(document.getElementById("mesFinRenting").value) - 1,
				document.getElementById("diaFinRenting").value).getTime();
		var fActualRenting = new Date().getTime();

		/*
		 * if(fInicioRenting>fFinRenting){ alert("La fecha de Inicio de Renting
		 * no puede ser mayor que la fecha de Fin de Renting."); return false; }
		 * 
		 * if(fInicioRenting>fActual){ alert("La fecha de Inicio de Renting no
		 * puede ser mayor que la fecha actual."); return false; }
		 */
	}

	/*
	 * Validar si la direccion del vehiculo tiene mas de 26 caracteres
	 */
	if ((document.getElementById("tipoViaVehiculo").value.length
			+ document.getElementById("nombreViaDireccionVehiculo").value.length
			+ document.getElementById("numeroDireccionVehiculo").value.length
			+ document.getElementById("letraDireccionVehiculo").value.length
			+ document.getElementById("pisoDireccionVehiculo").value.length
			+ document.getElementById("puertaDireccionVehiculo").value.length
			+ document.getElementById("bloqueDireccionVehiculo").value.length
			+ document.getElementById("kmDireccionVehiculo").value.length
			+ document.getElementById("escaleraDireccionVehiculo").value.length + document
			.getElementById("hmDireccionVehiculo").value.length) > 26) {
		if (!confirm("El conjunto de los campos que forman el domicilio del vehiculo ("
				+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?"))
			return false;
	} else if ((document.getElementById("tipoViaDireccionTitular").value.length
			+ document.getElementById("nombreViaDireccionTitular").value.length
			+ document.getElementById("numeroDireccionTitular").value.length
			+ document.getElementById("letraDireccionTitular").value.length
			+ document.getElementById("pisoDireccionTitular").value.length
			+ document.getElementById("puertaDireccionTitular").value.length
			+ document.getElementById("bloqueDireccionTitular").value.length
			+ document.getElementById("kmDireccionTitular").value.length
			+ document.getElementById("escaleraDireccionTitular").value.length + document
			.getElementById("hmDireccionTitular").value.length) > 26) {
		if (!confirm("El conjunto de los campos que forman el domicilio del titular del vehiculo ("
				+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?"))
			return false;
	}

	/*
	 * Validar si la Reduccion, no sujecion o exencion solicitada (05)
	 * seleccionada es diferente de NS2 entonces se debe rellenar el Numero de
	 * registro.
	 */
	if (exento05()) {
		if (document.formData.numRegistro.options[document.formData.numRegistro.selectedIndex].value == '') {
			alert("Debe seleccionar el numero de registro asociado a la Reduccion, no sujecion o exencion solicitada");
		}
	}

	if (!validarMaximos()) {
		return false;
	}

	if (!campoVacio("idBastidor")){
		//Mantis 0025314: El campo nivel de emisiones no puede ser vacio
/*		if (campoVacio("idNivelEmisiones")){
			alert ("El campo de nivel de emisiones no puede ir vacío.");
			return false;	
		}*/

		//Mantis 0025314: El campo codigo ECO no puede ser vacio si se ha seleccionado ECO innovación a SI
		if (document.getElementById("idEcoInnovacion").selectedIndex == 1){
			if (campoVacio("idCodigoEco")){
				alert ("El campo codigo ECO es obligatorio.");
				return false;
			}
		}

		if ($("#idCategoriaElectrica").val() != null && $("#idCategoriaElectrica").val() == "PHEV"){
			if ($("#idAutonomiaElectrica").val() == null || $("#idAutonomiaElectrica").val() == ""){
				if (!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
					return false;
				}
			} else if ($("#idAutonomiaElectrica").val() == "0") {
				if (!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica y esta no puede ser '0' para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
					return false;
				}
			}
		}
	}

	document.formData.action = "guardarAltasMatriculacion.action#" + pestania;
	document.formData.submit();
	loadingGuardarMatriculacion(pestania);
	return true;
}

	function guardarAltaMatriculacionMatw(pestania) {
		if (!validaNIF(document.getElementById("nifTitular"))) {
			alert("El NIF del titular no es v\u00e1lido.");
			return false;
		}

		if (!validaNIF(document.getElementById("nifRepresentante"))) {
			alert("El NIF del representante del titular no es v\u00e1lido.");
			return false;
		}

		if (!validaNIF(document.getElementById("nifConductorHabitual"))) {
			alert("El NIF del conductor habitual no es v\u00e1lido.");
			return false;
		}

		if (!validaNIF(document.getElementById("idNifArrendatario"))) {
			alert("El NIF del arrendatario no es v\u00e1lido.");
			return false;
		}

		// Mantis 7841
		var nifTitular = document.getElementById("nifTitular").value;
		var nifConductorHabitual = document.getElementById("nifConductorHabitual").value;
		if((nifTitular != null && nifConductorHabitual != null) &&
				(nifTitular != "" && nifConductorHabitual != "" ) &&
				(nifTitular == nifConductorHabitual)){
			alert("Indique datos de conductor habitual, solo cuando sea distinto del titular del veh\u00edculo");
			return false;
		}
		// Fin Mantis 7841

		if (document.getElementById("diaAltaIni") != null
				&& document.getElementById("mesAltaIni") != null
				&& document.getElementById("anioAltaIni") != null
				&& document.getElementById("diaAltaFin") != null
				&& document.getElementById("mesAltaFin") != null
				&& document.getElementById("anioAltaFin") != null
				&& document.getElementById("diaAltaIni").value != ""
				&& document.getElementById("mesAltaIni").value != ""
				&& document.getElementById("anioAltaIni").value != ""
				&& document.getElementById("diaAltaFin").value != ""
				&& document.getElementById("mesAltaFin").value != ""
				&& document.getElementById("anioAltaFin").value != "") {
			var fInicioConductorHabitual = new Date(document
					.getElementById("anioAltaIni").value, parseInt(document
					.getElementById("mesAltaIni").value) - 1, document
					.getElementById("diaAltaIni").value).getTime();
			var fFinConductorHabitual = new Date(document
					.getElementById("anioAltaFin").value, parseInt(document
					.getElementById("mesAltaFin").value) - 1, document
					.getElementById("diaAltaFin").value).getTime();
			var fActual = new Date().getTime();

			if (fInicioConductorHabitual > fFinConductorHabitual) {
				alert("La fecha de Inicio del Titular no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioConductorHabitual > fActual) {
				alert("La fecha de Inicio del Titular no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		var valueConcepto = document.getElementById("conceptoRepresentanteTitular")
			.options[document.getElementById("conceptoRepresentanteTitular").selectedIndex].value;

		var motivoTutela = document.getElementById("motivoRepresentanteTitular")
			.options[document.getElementById("motivoRepresentanteTitular").selectedIndex].value;

		var titularEsEmpresa = document.getElementById("sexoTitular").options[document.getElementById("sexoTitular").selectedIndex].value;

		if(valueConcepto == "TUTELA O PATRIA POTESTAD" && titularEsEmpresa == "X"){
			alert("El concepto del representante no es v\u00e1lido para una empresa");
			return false;
		}

		if(valueConcepto == "TUTELA O PATRIA POTESTAD"){
			if(motivoTutela == null || motivoTutela == "-1"){
				alert("Requerido el motivo de la tutela o patria potestad");
				return false;
			}
		}

		if (document.getElementById("diaInicioTutela") != null && document.getElementById("diaInicioTutela").value != ""
				&& document.getElementById("mesInicioTutela") != null && document.getElementById("mesInicioTutela").value != ""
				&& document.getElementById("anioInicioTutela") != null && document.getElementById("anioInicioTutela").value != ""
				&& document.getElementById("diaFinTutela") != null && document.getElementById("diaFinTutela").value != ""
				&& document.getElementById("mesFinTutela") != null && document.getElementById("mesFinTutela").value != ""
				&& document.getElementById("anioFinTutela") != null && document.getElementById("anioFinTutela").value != "") {
			var fInicioTutela = new Date(document
					.getElementById("anioInicioTutela").value, parseInt(document
					.getElementById("mesInicioTutela").value) - 1, document
					.getElementById("diaInicioTutela").value).getTime();
			var fFinTutela = new Date(
					document.getElementById("anioFinTutela").value,
					parseInt(document.getElementById("mesFinTutela").value) - 1,
					document.getElementById("diaFinTutela").value).getTime();
			var fActualTutela = new Date().getTime();

			if (fInicioTutela > fFinTutela) {
				alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha de Fin.");
				return false;
			}

			if (fInicioTutela > fActual) {
				alert("La fecha de Inicio de la tutela no puede ser mayor que la fecha actual.");
				return false;
			}
		}

		if (document.getElementById("diaInicioRenting").value != ""
				&& document.getElementById("mesInicioRenting").value != ""
				&& document.getElementById("anioInicioRenting").value != ""
				&& document.getElementById("diaFinRenting").value != ""
				&& document.getElementById("mesFinRenting").value != ""
				&& document.getElementById("anioFinRenting").value != "") {
			var fInicioRenting = new Date(document
					.getElementById("anioInicioRenting").value, parseInt(document
					.getElementById("mesInicioRenting").value) - 1, document
					.getElementById("diaInicioRenting").value).getTime();
			var fFinRenting = new Date(
					document.getElementById("anioFinRenting").value,
					parseInt(document.getElementById("mesFinRenting").value) - 1,
					document.getElementById("diaFinRenting").value).getTime();
			var fActualRenting = new Date().getTime();
		}

		/*
		 * Validar si la direccion del vehiculo tiene mas de 26 caracteres
		 */

		if ((document.getElementById("tipoViaVehiculo").value.length
				+ document.getElementById("nombreViaDireccionVehiculo").value.length
				+ document.getElementById("numeroDireccionVehiculo").value.length
				+ document.getElementById("letraDireccionVehiculo").value.length
				+ document.getElementById("pisoDireccionVehiculo").value.length
				+ document.getElementById("puertaDireccionVehiculo").value.length
				+ document.getElementById("bloqueDireccionVehiculo").value.length
				+ document.getElementById("kmDireccionVehiculo").value.length
				+ document.getElementById("escaleraDireccionVehiculo").value.length + document
				.getElementById("hmDireccionVehiculo").value.length) > 26) {
			if (!confirm("El conjunto de los campos que forman el domicilio del vehiculo ("
					+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?"))
				return false;
		} else if ((document.getElementById("tipoViaDireccionTitular").value.length
				+ document.getElementById("nombreViaDireccionTitular").value.length
				+ document.getElementById("numeroDireccionTitular").value.length
				+ document.getElementById("letraDireccionTitular").value.length
				+ document.getElementById("pisoDireccionTitular").value.length
				+ document.getElementById("puertaDireccionTitular").value.length
				+ document.getElementById("bloqueDireccionTitular").value.length
				+ document.getElementById("kmDireccionTitular").value.length
				+ document.getElementById("escaleraDireccionTitular").value.length + document
				.getElementById("hmDireccionTitular").value.length) > 26) {
			if (!confirm("El conjunto de los campos que forman el domicilio del titular del vehiculo ("
					+ "Tipo de via, nombre via, numero, letra, escalera, piso, puerta, bloque, km y hm) no puede superar 26 caracteres. Est\u00E1 seguro de que desea continuar?"))
				return false;
		}

		/*
		 * Validar si la Reduccion, no sujeccion o exencion solicitada (05)
		 * seleccionada es diferente de NS2 entonces se debe rellenar el Numero de
		 * registro.
		 */
		if (exento05()) {
			if (document.formData.numRegistro.options[document.formData.numRegistro.selectedIndex].value == '') {
				alert("Debe seleccionar el numero de registro asociado a la Reduccion, no sujecion o exencion solicitada");
			}
		}

		if(!validarMaximos()){
			return false;
		}

		if (!campoVacio("idBastidor")){
			//Mantis 0025314: El campo nivel de emisiones no puede ser vacio
		/*	if (campoVacio("idNivelEmisiones")){
				alert ("El campo de nivel de emisiones no puede ir vacío.");
				return false;
			}*/

			//Mantis 0025314: El campo codigo ECO no puede ser vacio si se ha seleccionado ECO innovación a SI
			if (document.getElementById("idEcoInnovacion").selectedIndex == 1){
				if (campoVacio("idCodigoEco")){
					alert ("El campo codigo ECO es obligatorio.");
					return false;
				}
			}

			if($("#idCategoriaElectrica").val() != null && $("#idCategoriaElectrica").val() == "PHEV"){
				if($("#idAutonomiaElectrica").val() == null || $("#idAutonomiaElectrica").val() == ""){
					if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
						return false;
					}
				} else if($("#idAutonomiaElectrica").val() == "0"){
					if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica y esta no puede ser '0' para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
						return false;
					}
				}
			}
		}

		document.formData.action = "guardarAltasMatriculacionMatw.action#" + pestania;
		document.formData.submit();
		loadingGuardarMatriculacion(pestania);
		return true;
	}

	//Tomar el codigo de matriculacion DE MATRICULACION
	function buscarCodigoITV() {
		if (document.getElementById("idCodigoItv").value != ""
				&& document.getElementById("idCodigoItv").value != "SINCODIG") {
			if (document.getElementById("idCodigoItv").value.length < 8
					|| document.getElementById("idCodigoItv").value.length > 9) {
				alert("El codigo ITV debe tener 8 o 9 caracteres");
			} else {
				document.formData.action = "tomarAltasMatriculacion.action#Vehiculo";
				document.formData.submit();
				return true;
			}
		} else {
			document.getElementById("idBastidor").value = "";
			document.getElementById("idTipoVehiculo").value = "-1";
			document.getElementById("modelo").value = "";
			document.getElementById("idMarca").value = "-1";
			document.getElementById("idCarburanteVehiculo").value = "-1";
			document.getElementById("idPlazas").value = "";
			document.getElementById("idPotenciaFiscal").value = "";
			document.getElementById("idNcilindros").value = "";
			document.getElementById("idCo2").value = "";
			document.getElementById("idtara").value = "";
			document.getElementById("idMma").value = "";
			document.getElementById("idPotenciaNeta").value = "";
			document.getElementById("idTipo").value = "";
			document.getElementById("idPotenciaPeso").value = "";
		}
	}

	function buscarCodigoITVMatw() {
		if (document.getElementById("idCodigoItv").value != ""
				&& document.getElementById("idCodigoItv").value != "SINCODIG") {
			if (document.getElementById("idCodigoItv").value.length < 8
					|| document.getElementById("idCodigoItv").value.length > 9) {
				alert("El codigo ITV debe tener 8 o 9 caracteres");
			} else {
				document.formData.action = "tomarAltasMatriculacionMatw.action#Vehiculo";
				document.formData.submit();
				return true;
			}
		} else {
			document.getElementById("idTipoVehiculo").value = "-1";
			document.getElementById("modelo").value = "";
			document.getElementById("idMarca").value = "-1";
			document.getElementById("idCarburanteVehiculo").value = "-1";
			document.getElementById("idPlazas").value = "";
			document.getElementById("idPotenciaFiscal").value = "";
			document.getElementById("idNcilindros").value = "";
			document.getElementById("idCo2").value = "";
			document.getElementById("idtara").value = "";
			document.getElementById("idMma").value = "";
			document.getElementById("idPotenciaNeta").value = "";
			document.getElementById("idTipo").value = "";
			document.getElementById("idPotenciaPeso").value = "";
		}
	}

	function consultaDatosITV() {
		if (document.getElementById("idCodigoItv").value != "" && document.getElementById("idCodigoItv").value != "SINCODIG") {
			if (document.getElementById("idCodigoItv").value.length < 8 || document.getElementById("idCodigoItv").value.length > 9) {
				alert("El codigo ITV debe tener 8 o 9 caracteres");
			} else {
				document.formData.action = "consultaDatosITVDetalleVehiculo.action";
				document.formData.submit();
				return true;
			}
		} else {
			document.getElementById("idBastidor").value = "";
			document.getElementById("idTipoVehiculo").value = "-1";
			document.getElementById("modelo").value = "";
			document.getElementById("idMarca").value = "-1";
			document.getElementById("idCarburanteVehiculo").value = "-1";
			document.getElementById("idPlazas").value = "";
			document.getElementById("idPotenciaFiscal").value = "";
			document.getElementById("idNcilindros").value = "";
			document.getElementById("idCo2").value = "";
			document.getElementById("idtara").value = "";
			document.getElementById("idMma").value = "";
			document.getElementById("idPotenciaNeta").value = "";
			document.getElementById("idTipo").value = "";
			document.getElementById("idPotenciaPeso").value = "";
		}
	}

	// Función al pulsar el boton de validar un tramite de matriculacion
	function validarMatriculacion(pestania) {
		if (confirm(document.getElementById("textoLegal").value)) {// Se añade texto legal
			if (validarMatriculacionPrevia() && validarMaximos()) {
				document.formData.action = "validarMatriculacionAltasMatriculacion.action#"
						+ pestania;
				document.formData.submit();
				loadingPestaniaResumen("idBotonValidarMatricular");
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	function validarMatriculacionMatw(pestania) {
		if (!campoVacio("idBastidor")){
			if ($("#idCategoriaElectrica").val() != null && $("#idCategoriaElectrica").val() == "PHEV"){
				if ($("#idAutonomiaElectrica").val() == null || $("#idAutonomiaElectrica").val() == ""){
					if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
						return false;
					}
				} else if($("#idAutonomiaElectrica").val() == "0"){
					if(!confirm("Para los vehículos con categoria electrica: 'Vehículo eléctrico hibrido enchufable', debe de rellenar la autonomia eléctrica y esta no puede ser '0' para poder generar su distintivo, en el caso que no la rellene no se podra obtener el distintivo.")){
						return false;
					}
				}
			}
		}
		if (confirm(document.getElementById("textoLegal").value)) {// Se añade texto legal
			if (validarMatriculacionPrevia() && validarMaximos()) {
				document.formData.action = "validarMatriculacionAltasMatriculacionMatw.action#"
						+ pestania;
				document.formData.submit();
				loadingPestaniaResumen("idBotonValidarMatricular");
				return true;
			} else
				return false;
		} else {
			return false;
		}
	}

	function validarMaximos(){
		// VALIDACIÓN RANGO PERMITIDO CO2 (0 A 999.999)
		if(document.getElementById('idCo2')){
			var valorCo2 = document.getElementById('idCo2').value;
			if(valorCo2 > 999.999){
				alert("El valor del campo co2 supera el m\u00e1ximo permitido de 999.999");
				return false;
			}
		}
		// VALIDACIÓN RANGO PERMITIDO POTENCIA/PESO (0 A 99.99)
		if(document.getElementById('idPotenciaPeso')){
			var valorPotenciaPeso = document.getElementById('idPotenciaPeso').value;
			if(valorPotenciaPeso > 99.99){
				alert("El valor del campo potencia/peso supera el m\u00e1ximo permitido de 99.99");
				return false;
			}
		}

		// VALIDACION RANGO PERMITIDO CILINDRADA (0 A 999999)
		if(document.getElementById('idNcilindros')){
			var valorCilindrada = document.getElementById('idNcilindros').value;
			if(valorCilindrada > 999999){
				alert("El valor del campo cilindrada supera el m\u00e1ximo permitido de 999999");
				return false;
			}
		}

		// VALIDACION RANGO PERMITIDO CILINDRADA (0 A 999.99) para motocicletas
		if(document.getElementById('idNcilindros')){
			var valorCilindrada = document.getElementById('idNcilindros').value;
			var valorTipoVehiculo = document.getElementById('idTipoVehiculo').value;
			if (valorTipoVehiculo.substring(0, 1)=="9" && valorCilindrada > 999.99) {
				alert("El valor del campo cilindrada para ciclomotores supera el m\u00e1ximo permitido de 999.99");
				return false;
			}
		}

		// VALIDACION RANGO PERMITIDO POTENCIA FISCAL (0 A 9999999999.99)
		if(document.getElementById('idPotenciaFiscal')){
			var valorPotenciaFiscal = document.getElementById('idPotenciaFiscal').value;
			if(valorPotenciaFiscal > 9999999999.99){
				alert("El valor del campo potencia fiscal supera el m\u00e1ximo permitido de 9999999999.99");
				return false;
			}
		}
		// VALIDACION RANGO PERMITIDO POTENCIA NETA (0 A 999999999.999)
		if(document.getElementById('idPotenciaNeta')){
			var valorPotenciaNeta = document.getElementById('idPotenciaNeta').value;
			if(valorPotenciaNeta > 999999999.999){
				alert("El valor del campo potencia neta supera el m\u00e1ximo permitido de 999999999.999");
				return false;
			}
		}
		return true;
	}

	function descargarDocBase(pestania){
		document.formData.action = "descargarDocBaseAltasMatriculacion.action#"
			+ pestania;
		document.formData.submit();
		return true;
	}

	function descargarDocBaseMatw(pestania){
		document.formData.action = "descargarDocBaseAltasMatriculacionMatw.action#"
			+ pestania;
		document.formData.submit();
		return true;
	}

	function imprimirPdfMatriculacion(numExpediente) {
		numeroExpediente = document.getElementById(numExpediente).value;
		document.forms[0].action = "impresionConsultaTramiteTrafico.action?listaExpedientes="
				+ numeroExpediente;
		document.forms[0].submit();
		loadingPestaniaResumen("idBotonImprimir");
		return true;
	}

	function clonarExpediente(numExpediente, estado, tipoTramite){
		document.formData.action = "clonarAltasMatriculacionMatw.action#";
		document.formData.submit();
		return true;
	}

	// Validaciones previas al envío del formulario de matriculación
	function validarMatriculacionPrevia() {
		if (!campoVacio("idCem")
				&& (!validaExpresionAlfanumerica(document.getElementById("idCem").value))) {
			alert("El C\u00f3digo Electr\u00f3nico de Matriculaci\u00f3n introducido no es v\u00e1lido.");
			return false;
		}

		if (!campoVacio("idCem")) {
			var cemValue = document.getElementById("idCem").value;
			if (cemValue.length != 8 || cemValue == "00000000") {
				alert("El C\u00f3digo Electr\u00f3nico de Matriculaci\u00f3n introducido no es v\u00e1lido.");
				return false;
			}
		}

		if (!campoVacio("idBastidor")){
			//Mantis 0025314: El campo nivel de emisiones no puede ser vacio
		/*	if (campoVacio("idNivelEmisiones")){
				alert ("El campo de nivel de emisiones no puede ir vacío.");
				return false;
			}*/

			//Mantis 0025314: El campo codigo ECO no puede ser vacio si se ha seleccionado ECO innovación a SI
			if (document.getElementById("idEcoInnovacion").selectedIndex == 1) {
				if (campoVacio("idCodigoEco")) {
					alert ("El campo codigo ECO es obligatorio.");
					return false;
				}
			}
		}
		return true;
	}

	function matricularTelematicamenteMatw(pestania) {
		var textoAlert = "Se le va a descontar 1 credito";
		textoAlert += "\n\n\nEl tr\u00e1mite ser\u00e1 enviado a DGT con los datos con los que fue validado.\n\n\nEsta usted procediendo a realizar tr\u00E1mites de matriculaci\u00F3n de vehiculo/s, para la realizacion de tal/es tr\u00E1mites es obligatorio que el Gestor Administrativo verifique de forma correcta y tenga en su poder la ficha t\u00E9cnica del veh\u00EDculo, as\u00ED como pagada y/o sellada la tasa de tr\u00E1fico, el modelo 576 del impuesto sobre veh\u00EDculos de transporte y el impuesto de veh\u00EDculos de tracci\u00F3n mec\u00E1nica de la localidad correspondiente. La realizaci\u00F3n de la operaci\u00F3n de matriculaci\u00F3n sin cumplir todos los requisitos de la encomienda de gesti\u00F3n suponen una infracci\u00F3n muy grave que puede ser sancionada con la inhabilitaci\u00F3n profesional del gestor administrativo, ademas de no estar cubierta tal contingencia por la p\u00F3liza de seguros de responsabilidad civil, en cuyo caso el infractor sera responsable de las contingencias econ\u00F3micas que surjan. Todo ello sin perjuicio de las responsabilidades administrativas y jur\u00EDdico penales en que puedan incurrir de forma directa los part\u00EDcipes de dicho tr\u00E1mite."
			+ "\n\n"
			+ "En el caso de cumplir tales requisitos pulse continuar y ultime el proceso de matriculaci\u00F3n, en el caso de no cumplir los requisitos alguno de los veh\u00EDculos que en este proceso se matriculan, se debera cancelar el proceso telem\u00E1tico de matriculaci\u00F3n referido a dichos veh\u00EDculos hasta el correcto cumplimiento de los mismos.";

		if (!confirm(textoAlert))
			return false;
		document.formData.action = "matricularTelematicamenteAltasMatriculacionMatw.action#"
				+ pestania;

		document.formData.submit();
		loadingPestaniaResumen("idBotonMatriculacionTelematica");
		return true;
	}

	// Se puede eliminar, no se le llama de JSP
	function anularTramiteMatriculacion(pestania) {
		document.formData.action = "anularTramiteAltasMatriculacion.action#"
				+ pestania;
		document.formData.submit();
		loadingAnularMatriculacion();
		return true;
	}

	// Se puede eliminar, no se le llama de JSP
	function anularTramiteMatriculacionMatw(pestania) {
		document.formData.action = "anularTramiteAltasMatriculacionMatw.action#"
				+ pestania;
		document.formData.submit();
		loadingAnularMatriculacion();
		return true;
	}

	/** ************************* DUPLICACION TRAMITES *********************** */

	//Se puede eliminar no se le llama de JSP
	//DUPLICACION DE TRAMITES
	function duplicarTramiteMatriculacion(pestania) {
		document.formData.action = "duplicarAltasMatriculacion.action#" + pestania;
		document.formData.submit();
		loadingDuplicar();

		return true;
	}
	//Se puede eliminar no se le llama de JSP
	function duplicarTramiteMatriculacionMatw(pestania) {
		document.formData.action = "duplicarAltasMatriculacionMatw.action#" + pestania;
		document.formData.submit();
		loadingDuplicar();

		return true;
	}

	/** ************************* CONSULTA MATRICULACION ********************* */
	//Se puede eliminar no se le llama de JSP
	//CANCELAR ENVIO FICHERO
	function volverAltasMatriculacion() {
		document.location.href = "inicioAltasMatriculacion.action";
		return true;
	}
	//Se puede eliminar no se le llama de JSP
	function volverAltasMatriculacionMatw() {
		// document.location.href =
		// "traficoTramiteMatriculacionList!buscarTraficoTramite.action";
		document.location.href = "inicioAltasMatriculacionMatw.action";
		return true;
	}

	/** *************************** CONSULTA MATRICULACION *********************** */

	//BUSCAR CONSULTAS DE MATRICULACION
	function consultaBuscar() {
		if (!validaNIF(document.getElementById("dniTitular"))) {
			alert("No has introducido un dni correcto");
			return false;
		}
		document.formData.action = "buscarConsultaTramiteMatriculacion.action";
		document.formData.submit();
		return true;
	}

	function descarga576Matw() {
		var actionPoint = "exportarFichero576AltasMatriculacionMatw.action";
		var mostrarLink = document.getElementById("descargar576Link");
		mostrarLink.style.display = "none";
		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	}

	function descargaRespuesta576Matw() {
		var actionPoint = "exportarRespuesta576AltasMatriculacionMatw.action";
		var mostrarLink = document.getElementById("respuesta576Link");
		mostrarLink.style.display = "none";
		document.forms[0].action = actionPoint;
		document.forms[0].submit();
	}

	// Para presentar el 576 en lote mediante el proceso 576
	function presentacion576(boton) {
		if (numCheckedImprimir() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}

/*		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","presentacion576ConsultaTramiteTrafico.action");
		$('#formData').submit();*/
		alert('Desactivado el proceso para la presentación del 576, por favor, ingrese en el trámite y vaya a la pestaña del 576, presione el botón \'Presentar en la AEAT\' para generar el XML para usarlo en la AEAT')
	}

	function descargarPresentacionAEAT(){
		$('#formData').attr("action","recuperar576AltasMatriculacionMatw.action#576");
		$('#formData').submit();
	}

	// Para realizar la consulta de la tarjeta eitv de los vehiculos por lotes
	function consultaTarjetaEitv(boton) {
		if (numCheckedImprimir() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}

		mostrarLoadingConsultar(boton);
		$('#formData').attr("action","consultaTarjetaEitvConsultaTramiteTrafico.action");
		$('#formData').submit();
	}

	function loadingPestaniaResumen(idBoton){
		if (document.getElementById("idBotonValidarMatricular") != null) {
			document.getElementById("idBotonValidarMatricular").disabled = "true";
			document.getElementById("idBotonValidarMatricular").style.color = "#6E6E6E";
			if(idBoton == "idBotonValidarMatricular"){
				document.getElementById("idBotonValidarMatricular").value = "Procesando...";
			}
		}
		if (document.getElementById("idBotonMatriculacionTelematica") != null) {
			document.getElementById("idBotonMatriculacionTelematica").disabled = "true";
			document.getElementById("idBotonMatriculacionTelematica").style.color = "#6E6E6E";
			if(idBoton == "idBotonMatriculacionTelematica"){
				document.getElementById("idBotonMatriculacionTelematica").value = "Procesando...";
			}
		}
		if (document.getElementById("idBotonImprimir") != null) {
			document.getElementById("idBotonImprimir").disabled = "true";
			document.getElementById("idBotonImprimir").style.color = "#6E6E6E";
			if(idBoton == "idBotonImprimir"){
				document.getElementById("idBotonImprimir").value = "Procesando...";
			}
		}
		if (document.getElementById("idBotonGuardarResumen") != null) {
			document.getElementById("idBotonGuardarResumen").disabled = "true";
			document.getElementById("idBotonGuardarResumen").style.color = "#6E6E6E";
			if(idBoton == "idBotonGuardarResumen"){
				document.getElementById("idBotonGuardarResumen").value = "Guardando...";
			}
		}
		if (document.getElementById("idBotonClonar") != null) {
			document.getElementById("idBotonClonar").disabled = "true";
			document.getElementById("idBotonClonar").style.color = "#6E6E6E";
			if(idBoton == "idBotonClonar"){
				document.getElementById("idBotonClonar").value = "Clonando...";
			}
		}
		if (document.getElementById("idBotonPermDistint") != null) {
			document.getElementById("idBotonPermDistint").disabled = "true";
			document.getElementById("idBotonPermDistint").style.color = "#6E6E6E";
			if(idBoton == "idBotonPermDistint"){
				document.getElementById("idBotonPermDistint").value = "Solicitando...";
			}
		}
		document.getElementById("loadingImage").style.display = "block";
	}

	function loadingGuardarMatriculacion(pestania) {
		if(pestania == "Resumen"){
			loadingPestaniaResumen("idBotonGuardar");
			return;
		}
		if(pestania == '576' && document.getElementById("bPresentarAEAT") != null){
			document.getElementById("bPresentarAEAT").disabled = "true";
			document.getElementById("bPresentarAEAT").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonGuardar" + pestania) != null) {
			document.getElementById("idBotonGuardar" + pestania).disabled = "true";
			document.getElementById("idBotonGuardar" + pestania).style.color = "#6E6E6E";
			document.getElementById("idBotonGuardar" + pestania).value = "Guardando...";
		}
		if (document.getElementById("idBotonDuplicar") != null) {
			document.getElementById("idBotonDuplicar").disabled = "true";
			document.getElementById("idBotonDuplicar").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonVolver") != null) {
			document.getElementById("idBotonVolver").disabled = "true";
			document.getElementById("idBotonVolver").style.color = "#6E6E6E";
		}
		if (document.getElementById("idBotonAnular") != null) {
			document.getElementById("idBotonAnular").disabled = "true";
			document.getElementById("idBotonAnular").style.color = "#6E6E6E";
		}
		if (document.getElementById("bConsultaTarjetaEitv") != null) {
			document.getElementById("bConsultaTarjetaEitv").disabled = "true";
			document.getElementById("bConsultaTarjetaEitv").style.color = "#6E6E6E";
		}
		document.getElementById("loadingImage").style.display = "block";
	}

	// No es llamado desde jsp
	function onchangeCheckFechaCaducidadItv(checkFechaCaducidadItv){
		if(checkFechaCaducidadItv.checked){
			// Verifica que existe el vehiculo sobre el que calcular la fecha:
			if(document.getElementById("idVehiculo").value == null || document.getElementById("idVehiculo").value == ""){
				alert("Guarde el veh\u00edculo para poder realizar el c\u00e1lculo");
				checkFechaCaducidadItv.checked = false;
				return false;
			}
			calcularFechaCaducidadItv(document.getElementById("idExpediente").value);
		}else{
			document.getElementById("diaCaducidadTarjetaITV").value = "";
			document.getElementById("mesCaducidadTarjetaITV").value = "";
			document.getElementById("anioCaducidadTarjetaITV").value = "";
		}
	}

	function loadingMatrSolDatos(idBoton, _idBloqueLoading) {
		if(document.getElementById("bloqueLoadingMatrSolDatos")){
			document.getElementById("bloqueLoadingMatrSolDatos").style.display = "block";
		}
		if(document.getElementById("bBuscar")){
			document.getElementById("bBuscar").disabled = "true";
			document.getElementById("bBuscar").style.color = "#6E6E6E";
		}
		if(idBoton == "bBuscar"){
			if(document.getElementById("bBuscar")){
				document.getElementById("bBuscar").value = "Procesando...";
			}
		}
	}

	/**
	 * Deshabilita los nodos padre del combo de carrocerias, para que no puedan ser seleccionados
	 * Sirve para navegadores que no sean Firefox, que no admiten despliegue de arbol en los select
	 */
	function deshabilitaCarroceriaParents(){
		// Seleccionamos el combo de carrocerias y recorremos sus opciones
		var comboCarrocerias = $('#idComboCarroceria option');
		comboCarrocerias.each(function(_index, object){
			var option = $(object);

			// Primero deshabilitamos la seleccion todas aquellas opciones de arbol (las que comienzan por +++)
			if(option.text().substr(0, 3)=='+++'){
				option.text(option.text().toUpperCase());
				option.css({
					color: '#000000',
					fontWeight: 'bold',
					fontStyle: 'italic',
					margin: '.5em 0 .5em 0'
				});
			}
		});
	}

	/**
	 * Deshabilita los nodos padre y oculta las opciones que le pertenecen, en el combo de carroceria
	 * Indicado para firefox y navegadores que admitan despliegue y repliegue de las options
	 */
	function deshabilitaCarroceriaOptions(){

		// Seleccionamos el combo de carrocerias y recorremos sus opciones
		var comboCarrocerias = $('#idComboCarroceria option');
		var grupoSel;
		comboCarrocerias.each(function(_index, object){

			var option = $(object);

			// Primero deshabilitamos la seleccion todas aquellas opciones de arbol (las que comienzan por +++)
			if(option.text().substr(0, 3)=='+++'){
				option.prop('disabled', true);
				option.css({
					color : '#000000',
					fontWeight : 'bold',
					fontStyle : 'italic',
					margin : '.5em 0 .5em 0'
				});

				// A estas opciones les damos funcionalidad sobre su evento onclick
				option.click(function(){
					/*
					 * Si hacemos click en una, visualizamos u ocultamos las que comiencen
					 * por los mismos caracteres de grupo (ej: BX 01)
					 */
					if(option.text().substr(0, 3)=='+++' || option.text().substr(0, 3)=='---'){
						comboCarrocerias.each(function(_inIndex, inObject){

							var inObject = $(inObject);

							/* 
							 * Solo hacemos cambios de visibilidad sobre las que tienen una
							 * clave de mas de dos caracteres. Las de dos son padres.
							 */
							if(inObject.val().length > 2){
								showHideChildCarrocerias(option, inObject);
							}
						});

						// Con el click, cambiamos la opcion de ocultar a mostrar y viceversa
						showHideIconCarrocerias(option);
					}
				});
			}

			// Si la opcion coincide con la seleccionada en el tramite guardado, la volvemos a activar a ella, y a todo el nodo padre
			if(option.val()==$('#idComboCarroceria option:selected').val()){

				grupoSel = option.val().substr(0, 2);
				var parent;

				comboCarrocerias.each(function(_inIndex, inObject){
					var inObject = $(inObject);

					if(inObject.val().substr(0, 2)==grupoSel){

						if(inObject.text().substr(0, 3)=='+++'){
							parent = inObject;
						}

						if(parent!=null && parent!='' && parent!='undefined'){
							option.show();
							showHideChildCarrocerias(parent, inObject);
						}

					}
				});
				if(parent!=null && parent!='' && parent!='undefined'){
					showHideIconCarrocerias(parent);
				}
			}

			/* 
			 * Nos aseguramos de ocultar elementos hijos (aquellos cuyo valor es
			 * superior a 2 caracteres. Ej: BX 01), y que no estan en el grupo de la seleccionada
			 */
			if(option.val().length > 2 && option.val().substr(0, 2)!=grupoSel){
				option.hide();
			}

		});
	}

	/**
	 * Decide si muestra u oculta una option hija en el combo de carrocerias
	 * @param parent
	 * @param inObject
	 */
	function showHideChildCarrocerias(parent, inObject){
		// Mostramos las hijas cuando la padre comienza por +
		if(parent.text().substr(0, 3)=='+++'){
			if(inObject.val().substr(0, 2)==parent.val()){
				inObject.show();
			}
		// Desactivamos las hijas si la padre comienza por -
		} else if (parent.text().substr(0, 3)=='---'){
			if(inObject.val().substr(0, 2)==parent.val()){
				inObject.hide();
			}
		}
	}

	/**
	 * Decide si el icono del nodo padre debe cambiar de +++ a --- segun su estado de
	 * despliegue en el combo de carrocerias
	 * @param option
	 */
	function showHideIconCarrocerias(option){
		var optionText = option.text().substr(3);
		var newText = '';
		if(option.text().substr(0, 3)=='+++'){
			newText = '---' + optionText;
		} else {
			newText = '+++' + optionText;
		}
		option.text(newText);
	}

	//Los tipos de vehiculos que ahora van a poder matricularse por telematico si llevan la ficha tecnica segun RD750/2010 son:
	//	- Ciclomotores
	//	- Motocicletas
	//	- Vehiculos especiales
	//	- Remolques y semiremolques
	//	- Tractores 
	function habilitaCheckFichaTecnica(tipo){
		var tipoVehiculo = new Array('RA','RC','RD','RE','RF','RH','R0','R1','R2','R3','R4','R5','R6','R7','R8','SA','SC','SD','SE','SF','SH','S0','S1','S2','S3','S4','S5','S6','S7','S8','50','51','52',
				'53','54','90','91','92','70','71','72','73','74','75','76','77','78','79','7A','7B','7C','7D','7E','7F','7G','7H','7I','7J','7K','80','81','82');
		var flagg = false;
		for (var i=0;i<tipoVehiculo.length;i++){
			if (tipoVehiculo[i] == tipo){
				flagg = true;
				break;
			}
		}
		if (flagg) {
			$('#idFichaTecnicaRD750').prop("disabled", false);
		} else {
			$('#idFichaTecnicaRD750').prop("checked",false);
			$('#idFichaTecnicaRD750').prop("disabled", true);
		}
	}

	function cargarListaFabricantes(idMarca, idFabricante, fabricante){
		fabricante.processor.setWords([]);
		obtenerFabricantes(document.getElementById(idMarca), document.getElementById(idFabricante), fabricante); 
	}

	function cargarListaMarcasVehiculoEst(marca){
		marca.processor.setWords([]);
		obtenerMarcasEst(marca);
	}

	function cargarCriteriosConstruccion(tipoVehiculo){
		obtenerCriteriosConstruccion(tipoVehiculo);
	}

	function cargarCriteriosUtilizacion(tipoVehiculo){
		obtenerCriteriosUtilizacion(tipoVehiculo);
	}

	function cargarListaHomologaciones(criterioConstruccion){
		obtenerListaHomologaciones(criterioConstruccion);
	}

	//No es llamado desde jsp
	//Generar la llamada Ajax para obtener las estaciones ITV dependiendo de la provincia
	function obtenerEstacionesItvDeProvincia(selectProvincia, selectEstacionItv,idEstacionItvSeleccionada){
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			selectEstacionItv.length = 0;
			selectEstacionItv.options[0] = new Option("Seleccione Estacion ITV", "");
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
		url="obtenerEstacionesItvDeProvinciaTraficoAjax.action?provinciaSeleccionada="+selectedOption;
		var req_generico_estacionItv = NuevoAjax();
		//Hace la llamada Ajax
		req_generico_estacionItv.onreadystatechange = function () {
			rellenarEstacionesItvDeProvincia(req_generico_estacionItv, selectEstacionItv,idEstacionItvSeleccionada);
		};
		req_generico_estacionItv.open("POST", url, true);
		req_generico_estacionItv.send(null);
	}
	//No es llamado desde jsp
	//Generar la llamada Ajax para obtener municipios
	function obtenerTipoViaPorProvincia(selectProvincia,selectTipoVia,id_tipoViaSeleccionada){
		//Sin elementos
		if(selectProvincia.selectedIndex==0){
			selectTipoVia.length = 0;
			selectTipoVia.options[0] = new Option("Seleccione Tipo Via", "");
			return;
		}
		selectedOption = selectProvincia.options[selectProvincia.selectedIndex].value;
		url=urlTipoVia+selectedOption;
		var req_generico_tipo_via = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_tipo_via.onreadystatechange = function () { 
				rellenarListaTipoVia(req_generico_tipo_via,selectTipoVia,id_tipoViaSeleccionada);
			};
		req_generico_tipo_via.open("POST", url, true);
		req_generico_tipo_via.send(null);
	}

	//Generar la llamada Ajax para obtener municipios
	function obtenerPueblosPorMunicipio(selectProvincia, hiddenMunicipio, selectPueblo,id_PuebloSeleccionado){
		//Sin elementos
		if(hiddenMunicipio.value==0){
			selectPueblo.length = 0;
			selectPueblo.options[0] = new Option("Seleccione Pueblo", "");
			return;
		}
		if (selectProvincia.selectedIndex==0){
			selectIndex.length=0;
			selectProvincia.options[0]= new Option("Seleccione Provincia","");
			return;
		}
		municipioSeleccionado = hiddenMunicipio.value;
		provinciaSeleccionado = selectProvincia.options[selectProvincia.selectedIndex].value;

		var urlPueblo = "recuperarPueblosPorMunicipioTraficoAjax.action?municipioSeleccionado=";
		url=urlPueblo+municipioSeleccionado;
		url=url+"&provinciaSeleccionado=" + provinciaSeleccionado;

		var req_generico_pueblo = NuevoAjax();
		//Hace la llamada a ajax
		req_generico_pueblo.onreadystatechange = function () {
			rellenarListaPueblos(req_generico_pueblo,selectPueblo,id_PuebloSeleccionado);
		}
		req_generico_pueblo.open("POST", url, true);
		req_generico_pueblo.send(null);
	}

	//Generar la llamada Ajax para fabricantes
	function obtenerFabricantes(idMarca, idFabricante, fabricante){
		var codigoMarca = idMarca.value;
		if(codigoMarca!=null && codigoMarca!='' && codigoMarca!='undefined'){
			var url = "recuperarFabricantesTraficoAjax.action?codigoMarca=" + codigoMarca;

			var req_generico_fabricante = NuevoAjax();
			//Hace la llamada a Ajax
			req_generico_fabricante.onreadystatechange = function () {
				rellenarListaFabricantes(req_generico_fabricante, fabricante);
			};
			req_generico_fabricante.open("POST", url, true);
			req_generico_fabricante.send(null);
		}
	}

	function rellenarListaFabricantes(req_generico_fabricante, fabricante){
		if (req_generico_fabricante.readyState == 4) { // Complete
			if (req_generico_fabricante.status == 200) { // OK response
				var textToSplit = req_generico_fabricante.responseText;
				textToSplit += '||ND';
				//alert(textToSplit);
				//Split the document
				returnElements=textToSplit.split("||");
				var anterior = fabricante;
				fabricante.processor.setWords(returnElements);
				fabricante = anterior;
			}
		}
	}

	// Generar la llamada Ajax para fabricantes inactivos
	function obtenerFabricantesInactivos(fabricante, id_fabr, CheckMensaje) {
		var url = "recuperarFabricantesInactivosTraficoAjax.action";
		var req_generico_fab = NuevoAjax();

		req_generico_fab.onreadystatechange = function() {
			rellenarListaFabricantesInactivos(req_generico_fab, fabricante,
					id_fabr, CheckMensaje);
		};

		req_generico_fab.open("POST", url, true);
		req_generico_fab.send(null);
	}

	//Generar la llamada Ajax para marcas
	function obtenerMarcasEst(marca){
		//var valorMarca = idMarca.value;
		var valorMarca = '';
		var url = "recuperarMarcasTraficoAjax.action?nombreMarca="+valorMarca;

		var req_generico_marca = NuevoAjax();
		//Hace la llamada a Ajax
		req_generico_marca.onreadystatechange = function () {
			rellenarListaMarcasVehiculoEst(req_generico_marca, marca);
		}
		req_generico_marca.open("POST", url, true);
		req_generico_marca.send(null);
	}

	//Generar la llamada Ajax para criterios
	function obtenerCriteriosConstruccion(tipoVehiculo){
		var url = "recuperarCriteriosConstruccionTraficoAjax.action?tipoVehiculo="+tipoVehiculo;

		var req_generico_criterios = NuevoAjax();
		//Hace la llamada a Ajax
		req_generico_criterios.onreadystatechange = function () {
			rellenarListaCriteriosConstruccion(req_generico_criterios, tipoVehiculo);
		}
		req_generico_criterios.open("POST", url, true);
		req_generico_criterios.send(null);
	}

	//Generar la llamada Ajax para criterios
	function obtenerCriteriosUtilizacion(tipoVehiculo){
		var url = "recuperarCriteriosUtilizacionTraficoAjax.action?tipoVehiculo="+tipoVehiculo;

		var req_generico_criterios = NuevoAjax();
		//Hace la llamada a Ajax
		req_generico_criterios.onreadystatechange = function () {
			rellenarListaCriteriosUtilizacion(req_generico_criterios, tipoVehiculo);
		}
		req_generico_criterios.open("POST", url, true);
		req_generico_criterios.send(null);
	}

	//Generar la llamada Ajax para homologaciones EU
	function obtenerListaHomologaciones(criterioConstruccion){
		var url = "recuperarHomologacionesEUTraficoAjax.action?criterioConstruccion="+criterioConstruccion;

		var req_generico_homologaciones = NuevoAjax();
		//Hace la llamada a Ajax
		req_generico_homologaciones.onreadystatechange = function () {
			rellenarListaHomologaciones(req_generico_homologaciones, criterioConstruccion);
		}
		req_generico_homologaciones.open("POST", url, true);
		req_generico_homologaciones.send(null);
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
		//Hace la llamada a ajax
		req_generico_codigosTasa.onreadystatechange = function () {
					rellenarListaCodigosTasa(req_generico_codigosTasa,selectCodigosTasa,id_CodigoTasaSeleccionado);
				};
		req_generico_codigosTasa.open("POST", url, true);
		req_generico_codigosTasa.send(null);
	}

	function rellenarListaPueblos(req_generico_pueblo,selectPueblo, id_PuebloSeleccionado){
		selectPueblo.options.length = 0;

		if (req_generico_pueblo.readyState == 4) { // Complete
			if (req_generico_pueblo.status == 200) { // OK response
				textToSplit = req_generico_pueblo.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				selectPueblo.options[0] = new Option("Seleccione Pueblo", "");
				//Process each of the elements
				var puebloSeleccionado = document.getElementById(id_PuebloSeleccionado).value;
				for ( var i=0; i<returnElements.length; i++ ){
					 value = returnElements[i].split(";");
					selectPueblo.options[i+1] = new Option(value[0], value[1]);
					if(selectPueblo.options[i+1].value == puebloSeleccionado){
						selectPueblo.options[i+1].selected = true;
					}
				}
			}
		}
	}

	function rellenarListaNombreVias(req_NombreVia, selectNombreVia, via){
		if (req_NombreVia.readyState == 4) { // Complete
			if (req_NombreVia.status == 200) { // OK response
				var textToSplit = req_NombreVia.responseText;
				//Split the document
				returnElements=textToSplit.split("||");
				var anterior = selectNombreVia.value;
				via.processor.setWords(returnElements);
				selectNombreVia.value=anterior;
			}
		}
	}

	function actualizaRadios(req_generico_fabricante, fabricante){
		if (req_generico_fabricante.readyState == 4) { // Complete
			if (req_generico_fabricante.status == 200) { // OK response
				var textToSplit = req_generico_fabricante.responseText;
				textToSplit += '||ND';
				//Split the document
				returnElements=textToSplit.split("||");
				var anterior = fabricante;
				fabricante.processor.setWords(returnElements);
				fabricante = anterior;
			}
		}
	}

	function rellenarListaFabricantesInactivos(req_generico_fabricante, fabricante, id_Fabr, CheckMensaje) {
		if (req_generico_fabricante.readyState == 4) { // Complete
			if (req_generico_fabricante.status == 200) { // OK response
				var textToSplit = req_generico_fabricante.responseText;
				var ArrayFabIn = textToSplit.split('||');
				var Nom_fabricante = document.getElementById(id_Fabr).value;

				for (var i = 0; i < ArrayFabIn.length; i++) {
					if (Nom_fabricante == ArrayFabIn[i])
						document.getElementById(CheckMensaje).innerHTML = "Fabricante no verificado";
				}
				returnElements = textToSplit.split("||");
				var anterior = fabricante;
				fabricante.processor.setWords(returnElements);
				fabricante = anterior;
			}
		}
	}

	function rellenarListaMarcasVehiculoEst(req_generico_marca, marca){

		if (req_generico_marca.readyState == 4) { // Complete
			if (req_generico_marca.status == 200) { // OK response
				var textToSplit = req_generico_marca.responseText;

				//Split the document
				var marcascodigos = textToSplit.split("[#]");
				var marcas = marcascodigos[0].split("||");
				var codigos = marcascodigos[1].split("||");

				// Asignamos las palabras sugeridas a la lista de nombres de marcas
				marca.processor.setWords(marcas);

				// Asignamos las claves sugeridas
				marca.processor.setKeys(marcas, codigos);

				// Recuperamos el nombre de la marca a través del código cuando obtenemos el detalle de un trámite
				for(x = 0; x < codigos.length; x++){
					if(document.getElementById('idMarcaVehiculo').value == codigos[x]) document.getElementById('codigoMarca').value = marcas[x];
				}
			}
		}
	}

	function rellenarListaCriteriosConstruccion(req_generico_criterios, tipoVehiculo){
		if (req_generico_criterios.readyState == 4) { // Complete
			if (req_generico_criterios.status == 200) { // OK response
				var textToSplit = req_generico_criterios.responseText;
				//Split the document
				var indicesvalores = textToSplit.split("[#]");
				var indices = indicesvalores[0].split("||");
				var valores = indicesvalores[1].split("||");

				var desplegable = document.getElementById('idClasificacionCriteriosConstruccion');

				// Vacío el desplegable
				desplegable.options.length = 0;

				desplegable.options[0] = new Option("Criterio de Construccion", "-1");
				for(var i = 0; i < indices.length; i++){
					desplegable.options[(i + 1)] = new Option(indices[i] + " - " + valores[i], indices[i]);
				}

				// Vacío el combo de homologaciones EU, ya que depende de lo seleccionado en el de criterios de construcción
				document.getElementById('idComboCategoriaEU').options.length = 0;
				document.getElementById('idComboCategoriaEU').options[0] = new Option("Homologacion UE", "");
			}
		}
	}

	function rellenarListaCriteriosUtilizacion(req_generico_criterios, tipoVehiculo){
		if (req_generico_criterios.readyState == 4) { // Complete
			if (req_generico_criterios.status == 200) { // OK response
				var textToSplit = req_generico_criterios.responseText;
				//alert(textToSplit);
				//Split the document
				var indicesvalores = textToSplit.split("[#]");
				var indices = indicesvalores[0].split("||");
				var valores = indicesvalores[1].split("||");

				var desplegable = document.getElementById('idClasificacionCriteriosUtilizacion');

				// Vacío el desplegable
				desplegable.options.length = 0;

				desplegable.options[0] = new Option("Criterio de Utilizacion", "-1");
				for(var i = 0; i < indices.length; i++){
					desplegable.options[(i + 1)] = new Option(indices[i] + " - " + valores[i], indices[i]);
				}
			}
		}
	}

	//Gilber-Mantis 0002982: HOMOLOGACION UE
	function rellenarListaHomologaciones(req_generico_homologaciones, tipoVehiculo){
		if (req_generico_homologaciones.readyState == 4) { // Complete
			if (req_generico_homologaciones.status == 200) { // OK response
				var textToSplit = req_generico_homologaciones.responseText;

				//Split the document
				var indicesvalores = textToSplit.split("[#]");
				var indices = indicesvalores[0].split("||");
				var valores = indicesvalores[1].split("||");

				var desplegable = document.getElementById('idComboCategoriaEU');

				// Vacío el desplegable
				desplegable.options.length = 0;

				desplegable.options[0] = new Option("Homologacion UE", "");
				for(i = 0; i < indices.length; i++){
					desplegable.options[(i + 1)] = new Option(indices[i], indices[i]);
				}

				// Actualizo los titles para los nuevos valores
				actualizarTitlesHomologacion();
			}
		}
	}

	function rellenarListaMarcas(req_generico_marca,selectMarca,id_marcaSeleccionada){
		selectMarca.options.length = 0;

		if (req_generico_marca.readyState == 4) { // Complete
			if (req_generico_marca.status == 200) { // OK response
				textToSplit = req_generico_marca.responseText;

				//Split the document
				returnElements=textToSplit.split("||");

				selectMarca.options[0] = new Option("Seleccione Marca", "");
				//Process each of the elements
				var marcaSeleccionada = document.getElementById(id_marcaSeleccionada).value;
				for (var i=0; i<returnElements.length; i++) {
					value = returnElements[i].split(";");
					if (value[1] == undefined) value[1]=" ";
					selectMarca.options[i+1] = new Option(value[0], value[1]);
					if(selectMarca.options[i+1].value == marcaSeleccionada){
						selectMarca.options[i+1].selected = true;
					}
				}
			}
		}
	}

	function habilitarCamposBase(tarjetaITV, flagDisabledHidden) {
		var color = "#EEEEEE";

		if (flagDisabledHidden == 'false') {
			if (tarjetaITV == "A" || tarjetaITV == "AT" || tarjetaITV == "AR" || tarjetaITV == "AL" 
				|| tarjetaITV == "D" || tarjetaITV == "DT" || tarjetaITV == "DR" || tarjetaITV == "DL") {
				color = "white";
				document.getElementById("codigoMarcaBase").disabled = false;
				document.getElementById("codigoMarcaBase").style.backgroundColor = color;
				document.getElementById("idFabricanteBase").disabled = false;
				document.getElementById("idFabricanteBase").style.backgroundColor = color;
				document.getElementById("idNumHomologacionBase").disabled = false;
				document.getElementById("idNumHomologacionBase").style.backgroundColor = color;
				document.getElementById("idVarianteBase").disabled = false;
				document.getElementById("idVarianteBase").style.backgroundColor = color;
				document.getElementById("idVersionBase").disabled = false;
				document.getElementById("idVersionBase").style.backgroundColor = color;
				document.getElementById("idMomBase").disabled = false;
				document.getElementById("idMomBase").style.backgroundColor = color;
				document.getElementById("tipoItvBase").disabled = false;
				document.getElementById("tipoItvBase").style.backgroundColor = color;
				document.getElementById("idEstacionITV").disabled = false;
				document.getElementById("idEstacionITV").style.backgroundColor = color;
			} else {
				document.getElementById("codigoMarcaBase").disabled = true;
				document.getElementById("codigoMarcaBase").value = "";
				document.getElementById("idMarcaBase").value = "";
				document.getElementById("codigoMarcaBase").style.backgroundColor = color;
				document.getElementById("idFabricanteBase").disabled = true;
				document.getElementById("idFabricanteBase").value = "";
				document.getElementById("idFabricanteBase").style.backgroundColor = color;
				document.getElementById("idNumHomologacionBase").disabled = true;
				document.getElementById("idNumHomologacionBase").value = "";
				document.getElementById("idNumHomologacionBase").style.backgroundColor = color;
				document.getElementById("idVarianteBase").disabled = true;
				document.getElementById("idVarianteBase").value = "";
				document.getElementById("idVarianteBase").style.backgroundColor = color;
				document.getElementById("idVersionBase").disabled = true;
				document.getElementById("idVersionBase").value = "";
				document.getElementById("idVersionBase").style.backgroundColor = color;
				document.getElementById("idMomBase").disabled = true;
				document.getElementById("idMomBase").value = "";
				document.getElementById("idMomBase").style.backgroundColor = color;
				document.getElementById("tipoItvBase").disabled = true;
				document.getElementById("tipoItvBase").value = "";
				document.getElementById("tipoItvBase").style.backgroundColor = color;
			}
		}
	}

	function habilitarCamposElectrico(carburante, flagDisabledHidden) {
		var color = "#EEEEEE";
		if (flagDisabledHidden == 'false') {
			if (carburante == 'E' || carburante == 'G'|| carburante == 'D' || carburante == 'G/E') {
				color = "white";
				document.getElementById("idAutonomiaElectrica").disabled = false;
				document.getElementById("idAutonomiaElectrica").style.backgroundColor = color;
				document.getElementById("idCategoriaElectrica").disabled = false;
				document.getElementById("idCategoriaElectrica").style.backgroundColor = color;
			} else {
				document.getElementById("idAutonomiaElectrica").disabled = true;
				document.getElementById("idAutonomiaElectrica").value = "";
				document.getElementById("idAutonomiaElectrica").style.backgroundColor = color;
				document.getElementById("idCategoriaElectrica").disabled = true;
				document.getElementById("idCategoriaElectrica").value = "";
				document.getElementById("idCategoriaElectrica").style.backgroundColor = color;
			}
		}
	}

function habilitarMarcaBase(tarjetaITV) {
	if (tarjetaITV == "A" || tarjetaITV == "AT" || tarjetaITV == "AR" || tarjetaITV == "AL" 
		|| tarjetaITV == "D" || tarjetaITV == "DT" || tarjetaITV == "DR" || tarjetaITV == "DL") {
		document.getElementById("codigoMarcaBase").disabled = false;
	} else {
		document.getElementById("codigoMarcaBase").disabled = true;
	}
}

//Gilber-Mantis 0002982: HOMOLOGACION UE
// 'carga' los title del combo de Homologacion EU
function actualizarTitlesHomologacion(){
	var itemsOcultos = document.getElementById("idComboCategoriaEUOculto");
	var itemsVisibles = document.getElementById("idComboCategoriaEU");
	for(i=0; i<itemsOcultos.length; i++) {
		for(j=0; j<itemsVisibles.length; j++){
			if(itemsOcultos[i].value == itemsVisibles[j].value) itemsVisibles[j].title=itemsOcultos[i].text;
		}
	}
}

/* Mantis 24989 */
function checkDocHomologacion(){
	var textoAlert = "\nEl gestor administrativo presenta documentaci\u00F3n sobre el vehiculo, acreditando que el vehiculo se encuentra en un fin de serie autorizado por la autoridad de homologaci\u00F3n"
		+ "\n\n"
		+ "En el caso de no aportar dicha documentaci\u00F3n, se rechazará la matriculaci\u00F3n";
		alert(textoAlert);
}
//	/* Mantis 24989 */
//	function activarCheckDocumentacion(){
//		var comboCategoria = document.getElementById("idComboCategoriaEU");
//		if(comboCategoria.value =='L3e' || comboCategoria.value =='L4e' ||comboCategoria.value =='L5e'||comboCategoria.value =='L7e'){
//			if(document.getElementById("idNivelEmisiones").value == 'EURO 3'){
//				document.getElementById("idDocHomologacion").disabled = false;
//			}
//		}else{
//			document.getElementById("idDocHomologacion").disabled = true;
//		}
//	}

function checkVelMaxObligatoria() {
	var listTiposVehiculos = ['80', '81', '82', 'R0', 'RH', 'S0', 'SH'];
	var listTiposVehiculos7Exentos = ['79', '7A', '7B', '7C', '7E', '7F', '7G,', '7K'];
	var tipoVehiculo = $('#idTipoVehiculo').val();
	var servicio = $('#idServicioTrafico').val();
	var categoriaEU = $('#idComboCategoriaEU').val();

	$('#asteriscoVelocidadMaxima').hide();
	if (servicio == 'B06'
			&& (listTiposVehiculos.includes(tipoVehiculo) || (tipoVehiculo.charAt(0) == '7' && !listTiposVehiculos7Exentos.includes(tipoVehiculo)))
		|| categoriaEU.charAt(0) == 'T') {
		$('#asteriscoVelocidadMaxima').show();
	}
}

function comprobacionCo2(){
	var combo = document.getElementById('idCarburanteVehiculo');
	if(combo[combo.selectedIndex].value == 'E' ||
			combo[combo.selectedIndex].value == 'H' ||
			combo[combo.selectedIndex].value == 'BM'){
		document.getElementById('idCo2').value = "";
		document.getElementById('idCo2').disabled = true;
		document.getElementById("idCo2").style.backgroundColor = "#EEEEEE";
	}else{
		document.getElementById('idCo2').disabled = false;
		document.getElementById("idCo2").style.backgroundColor = "white";
	}
}

function onchangeExentoCem(){
	var check = document.getElementById("exentoCem").checked;
	if (check) {
		document.getElementById("idCem").disabled = true;
	} else {
		document.getElementById("idCem").disabled = false;
	}
}

function bloquearITVCamposMatr() {
	var tipoMatriculacion = document.getElementById('tipoMatriculacion');
	var nive = document.getElementById("idNive");
	var codigoITV = document.getElementById("idCodigoItv");

	if(tipoMatriculacion.value=='NIVE'){
		//Decidimos si los campos tienen que ir bloqueados o no
		var bloqueado = true;
		var color = "#EEEEEE";
		//var visible = "hidden";
		nive.disabled = false;
		nive.style.backgroundColor = "";
		codigoITV.disabled = true;
		codigoITV.style.backgroundColor = color;
		//tipoMatriculacion.options[tipoMatriculacion.selectedIndex].value='NIVE';
	} else {
		var bloqueado = false;
		var color = "";
		//var visible = "visible";
		nive.disabled = true;
		nive.style.backgroundColor = "#EEEEEE";
		codigoITV.disabled = false;
		codigoITV.style.backgroundColor = color;
		//tipoMatriculacion.options[tipoMatriculacion.selectedIndex].value='ITV';
	}
	//bloqueamos o desbloqueamos los elementos dependiendo del codigo ITV

	document.getElementById("idMarca").style.backgroundColor = color;
	document.getElementById("idMarca").disabled = bloqueado;
	document.getElementById("modelo").style.backgroundColor = color;
	document.getElementById("modelo").disabled = bloqueado;
	document.getElementById("idColor").disabled = bloqueado;
	document.getElementById("idColor").style.backgroundColor = color;
	document.getElementById("idCarburanteVehiculo").disabled = bloqueado;
	document.getElementById("idCarburanteVehiculo").style.backgroundColor = color;
	document.getElementById("idClasificacionCriteriosConstruccion").disabled = bloqueado;
	document.getElementById("idClasificacionCriteriosConstruccion").style.backgroundColor = color;
	document.getElementById("idClasificacionCriteriosUtilizacion").disabled = bloqueado;
	document.getElementById("idClasificacionCriteriosUtilizacion").style.backgroundColor = color;
	document.getElementById("idDiplomatico").disabled = bloqueado;
	document.getElementById("idDiplomatico").style.backgroundColor = color;
	document.getElementById("idExcesoPeso").disabled = bloqueado;
	document.getElementById("idExcesoPeso").style.backgroundColor = color;
	document.getElementById("idTipoVehiculo").style.backgroundColor = color;
	document.getElementById("idPlazas").disabled = bloqueado;
	document.getElementById("idPlazas").style.backgroundColor = color;
	document.getElementById("idPotenciaFiscal").disabled = bloqueado;
	document.getElementById("idPotenciaFiscal").style.backgroundColor = color;
	document.getElementById("idNcilindros").disabled = bloqueado;
	document.getElementById("idNcilindros").style.backgroundColor = color;
	document.getElementById("idCo2").disabled = bloqueado;
	document.getElementById("idCo2").style.backgroundColor = color;
	document.getElementById("idtara").disabled = bloqueado;
	document.getElementById("idtara").style.backgroundColor = color;
	document.getElementById("idMma").disabled = bloqueado;
	document.getElementById("idMma").style.backgroundColor = color;
	document.getElementById("idPotenciaNeta").disabled = bloqueado;
	document.getElementById("idPotenciaNeta").style.backgroundColor = color;
	document.getElementById("idComboFabricacion").disabled = bloqueado;
	document.getElementById("idComboFabricacion").style.backgroundColor = color;
	document.getElementById("idNumSerie").disabled = bloqueado;
	document.getElementById("idNumSerie").style.backgroundColor = color;
	document.getElementById("idTipoTarjetaITV").disabled = bloqueado;
	document.getElementById("idTipoTarjetaITV").style.backgroundColor = color;
	document.getElementById("idEpigrafe").disabled = bloqueado;
	document.getElementById("idEpigrafe").style.backgroundColor = color;
	document.getElementById("idClasificacionItvPrever").disabled = bloqueado;
	document.getElementById("idClasificacionItvPrever").style.backgroundColor = color;
	document.getElementById("idVariante").disabled = bloqueado;
	document.getElementById("idVariante").style.backgroundColor = color;
	document.getElementById("idVersion").disabled = bloqueado;
	document.getElementById("idVersion").style.backgroundColor = color;
	document.getElementById("idNumHomologacion").disabled = bloqueado;
	document.getElementById("idNumHomologacion").style.backgroundColor = color;
	document.getElementById("tipoItv").disabled = bloqueado;
	document.getElementById("tipoItv").style.backgroundColor = color;
	document.getElementById("idTipo").disabled = bloqueado;
	document.getElementById("idTipo").style.backgroundColor = color;
	document.getElementById("idMtaMma").disabled = bloqueado;
	document.getElementById("idMtaMma").style.backgroundColor = color;
	document.getElementById("idPotenciaPeso").disabled = bloqueado;
	document.getElementById("idPotenciaPeso").style.backgroundColor = color;
	document.getElementById("idPlazasPie").disabled = bloqueado;
	document.getElementById("idPlazasPie").style.backgroundColor = color;
	document.getElementById("idNumRuedas").disabled = bloqueado;
	document.getElementById("idNumRuedas").style.backgroundColor = color;
	document.getElementById("idPotenciaNeta").disabled = bloqueado;
	document.getElementById("idPotenciaNeta").style.backgroundColor = color;
	document.getElementById("idConsumo").disabled = bloqueado;
	document.getElementById("idConsumo").style.backgroundColor = color;
	document.getElementById("idComboCarroceria").disabled = bloqueado;
	document.getElementById("idComboCarroceria").style.backgroundColor = color;
	document.getElementById("idDistanciaEntreEjes").disabled = bloqueado;
	document.getElementById("idDistanciaEntreEjes").style.backgroundColor = color;
	document.getElementById("idViaAnterior").disabled = bloqueado;
	document.getElementById("idViaAnterior").style.backgroundColor = color;
	document.getElementById("idViaPosterior").disabled = bloqueado;
	document.getElementById("idViaPosterior").style.backgroundColor = color;
	document.getElementById("idComboAlimentacion").disabled = bloqueado;
	document.getElementById("idComboAlimentacion").style.backgroundColor = color;
	document.getElementById("idSubasta").disabled = bloqueado;
	document.getElementById("idSubasta").style.backgroundColor = color;
	document.getElementById("idNivelEmisiones").disabled = bloqueado;
	document.getElementById("idNivelEmisiones").style.backgroundColor = color;
	document.getElementById("idEcoInnovacion").disabled = bloqueado;
	document.getElementById("idEcoInnovacion").style.backgroundColor = color;
	document.getElementById("idReduccionEco").disabled = bloqueado;
	document.getElementById("idReduccionEco").style.backgroundColor = color;
	document.getElementById("idCodigoEco").disabled = bloqueado;
	document.getElementById("idCodigoEco").style.backgroundColor = color;
	document.getElementById("idComboCategoriaEU").disabled = bloqueado;
	document.getElementById("idComboCategoriaEU").style.backgroundColor = color;
	document.getElementById("idFabricante").disabled = bloqueado;
	document.getElementById("idFabricante").style.backgroundColor = color;
	//document.getElementById("calendarioVehiculo").style.visibility = visible;

	editarFechaCaducidad(document.getElementById('idTipoTarjetaITV').value);
}

//Mantis 7841
function mostrarOcultarPestaniaConductorHabitual(){
	if(document.getElementById("marcadoConductorHabitual").checked == true){
		document.getElementById("pestaniaConductorHabitual").style.display = "block";
	}else{
		document.getElementById("pestaniaConductorHabitual").style.display = "none";
		limpiarFormularioConductorHabitualMatriculacionMatw();
	}
}
function marcarConductorHabitual(){
	document.getElementById("marcadoConductorHabitual").checked = true;
}

function limpiarFormularioConductorHabitualMatriculacionMatw(){
	document.getElementById("nifConductorHabitual").value = "";
	document.getElementById("conductorHabitualDiaCadNif").value = "";
	document.getElementById("conductorHabitualMesCadNif").value = "";
	document.getElementById("conductorHabitualAnioCadNif").value = "";
	document.getElementById("conductorHabitualOtroDocumentoIdentidad").checked = false;
	document.getElementById("conductorHabitualIndefinido").checked = false;
	document.getElementById("conductorHabitualTipoDocumentoAlternativo").selectedIndex = 0;
	document.getElementById("conductorHabitualDiaCadAlternativo").value = "";
	document.getElementById("conductorHabitualMesCadAlternativo").value = "";
	document.getElementById("conductorHabitualMesCadAlternativo").value = "";
	document.getElementById("conductorHabitualAnioCadAlternativo").value = "";
	document.getElementById("sexoConductorHabitual").selectedIndex = 0;
	document.getElementById("apellido1RazonSocialConductorHabitual").value = "";
	document.getElementById("apellido2ConductorHabitual").value = "";
	document.getElementById("nombreConductorHabitual").value = "";
	document.getElementById("diaNacConductorHabitual").value = "";
	document.getElementById("mesNacConductorHabitual").value = "";
	document.getElementById("anioNacConductorHabitual").value = "";
	document.getElementById("estadoCivilConductorHabitual").selectedIndex = 0;
	document.getElementById("idProvinciaConductorHabitual").selectedIndex = 0;
	document.getElementById("municipioSeleccionadoConductorHabitual").value = "";
	document.getElementById("puebloSeleccionadoConductorHabitual").value = "";
	document.getElementById("codPostalConductorHabitual").value = "";
	document.getElementById("tipoViaSeleccionadaDireccionConductorHabitual").value = "";
	document.getElementById("nombreViaDireccionConductorHabitual").value = "";
	document.getElementById("numeroDireccionConductorHabitual").value = "";
	document.getElementById("letraDireccionConductorHabitual").value = "";
	document.getElementById("escaleraDireccionConductorHabitual").value = "";
	document.getElementById("pisoDireccionConductorHabitual").value = "";
	document.getElementById("puertaDireccionConductorHabitual").value = "";
	document.getElementById("bloqueDireccionConductorHabitual").value = "";
	document.getElementById("kmDireccionConductorHabitual").value = "";
	document.getElementById("hmDireccionConductorHabitual").value = "";
	document.getElementById("idCambioDomicilioConductorHabitual").checked = false;
	document.getElementById("diaAltaFin").value = "";
	document.getElementById("mesAltaFin").value = "";
	document.getElementById("anioAltaFin").value = "";
	document.getElementById("horaAltaFin").value = "";
}
// Fin Mantis 7841

/**
 * Consultar Datos eITV
 */

function consultarTarjetaEitv(pestania) {
	if (document.getElementById('idBastidor').value == null || document.getElementById('idBastidor').value == "") {
		alert("Por favor, introduzca el bastidor");
		return false;
	}

	if (document.getElementById('idBastidor').value.length != 17) {
		alert("El bastidor debe tener una longitud de 17 caracteres.");
		return false;
	}

	if ((!document.getElementById('idNive').value == null || !document.getElementById('idNive').value == "") 
			&& document.getElementById('idNive').value.length != 32) {
			alert("El NIVE debe tener una longitud de 32 caracteres.");
		return false;
	}

	document.formData.action = "getDataEitvAltasMatriculacion.action#" + pestania;
	document.formData.submit();
	procesandoConsultaTarjetaEitv();
}

function consultarTarjetaEitvMatw(pestania) {
	if (document.getElementById('idBastidor').value == null || document.getElementById('idBastidor').value == "") {
		alert("Por favor, introduzca el bastidor");
		return false;
	}

	if (document.getElementById('idBastidor').value.length != 17) {
		alert("El bastidor debe tener una longitud de 17 caracteres.");
		return false;
	}

	if ((!document.getElementById('idNive').value == null || !document.getElementById('idNive').value == "") 
			&& document.getElementById('idNive').value.length != 32) {
		alert("El NIVE debe tener una longitud de 32 caracteres.");
		return false;
	}

	document.formData.action = "getDataEitvAltasMatriculacionMatw.action#" + pestania;
	document.formData.submit();
	procesandoConsultaTarjetaEitv();
}

function consultarTarjetaEitvMatwNuevo(pestania) {
	if (document.getElementById('idBastidor').value == null || document.getElementById('idBastidor').value == "") {
		alert("Por favor, introduzca el bastidor");
		return false;
	}

	if (document.getElementById('idBastidor').value.length != 17) {
		alert("El bastidor debe tener una longitud de 17 caracteres.");
		return false;
	}

	if ((!document.getElementById('idNive').value == null || !document.getElementById('idNive').value == "") 
		&& document.getElementById('idNive').value.length != 32) {
		alert("El NIVE debe tener una longitud de 32 caracteres.");
		return false;
	}

	document.formData.action = "getDataEitvAltasMatriculacionMatw.action#" + pestania;
	document.formData.submit();
	procesandoConsultaTarjetaEitv();
}

function procesandoConsultaTarjetaEitv() {
	if (document.getElementById("bConsultaTarjetaEitv") != null) {
		document.getElementById("bConsultaTarjetaEitv").disabled = "true";
		document.getElementById("bConsultaTarjetaEitv").style.color = "#6E6E6E";
		document.getElementById("bConsultaTarjetaEitv").value = "Procesando...";
	}
	if (document.getElementById("idBotonGuardarVehiculo") != null) {
		document.getElementById("idBotonGuardarVehiculo").disabled = "true";
		document.getElementById("idBotonGuardarVehiculo").style.color = "#6E6E6E";
	}
	document.getElementById("loadingImage").style.display = "block";
}

function validarYGenerarJustificanteProAltaDuplicados(pestania) {
	document.formData.action = "validarYGenerarJustificanteProAltasTramiteDuplicado.action#"
			+ pestania;
	document.formData.submit();
	return true;
}

//Bloquea o desbloquea los campos del vehiculo usado dependiendo del check
function desbloquearUsado(checked) {
	var bloqueado = true;
	var color = "#EEEEEE";
	var visible = "hidden";

	if (checked == true) {
		bloqueado = false;
		color = "white";
		visible = "visible";
	}
	if(document.getElementById("idMatriculaAyuntamiento")){
		document.getElementById("idMatriculaAyuntamiento").disabled = bloqueado;
		document.getElementById("idMatriculaAyuntamiento").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("idMatriculaAyuntamiento").value = "";
		}
	}
	if(document.getElementById("dialimiteMatrTuris")){
		document.getElementById("dialimiteMatrTuris").disabled = bloqueado;
		document.getElementById("dialimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("dialimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("meslimiteMatrTuris")){
		document.getElementById("meslimiteMatrTuris").disabled = bloqueado;
		document.getElementById("meslimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("meslimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("aniolimiteMatrTuris")){
		document.getElementById("aniolimiteMatrTuris").disabled = bloqueado;
		document.getElementById("aniolimiteMatrTuris").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("aniolimiteMatrTuris").value = "";
		}
	}
	if(document.getElementById("imgCalendarLimiteVigenciaMatriculaTuristica")){
		document.getElementById("imgCalendarLimiteVigenciaMatriculaTuristica").style.visibility = visible;
	}
	if(document.getElementById("diaPrimMatri")){
		document.getElementById("diaPrimMatri").disabled = bloqueado;
		document.getElementById("diaPrimMatri").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("diaPrimMatri").value = "";
		}
	}
	if(document.getElementById("mesPrimMatri")){
		document.getElementById("mesPrimMatri").disabled = bloqueado;
		document.getElementById("mesPrimMatri").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("mesPrimMatri").value = "";
		}
	}
	if(document.getElementById("anioPrimMatri")){
		document.getElementById("anioPrimMatri").disabled = bloqueado;
		document.getElementById("anioPrimMatri").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("anioPrimMatri").value = "";
		}
	}
	if(document.getElementById("imgCalendarFechaPrimeraMatriculacion")){
		document.getElementById("imgCalendarFechaPrimeraMatriculacion").style.visibility = visible;
	}
	if(document.getElementById("idLugarPrimeraMatriculacion")){
		document.getElementById("idLugarPrimeraMatriculacion").disabled = bloqueado;
		document.getElementById("idLugarPrimeraMatriculacion").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("idLugarPrimeraMatriculacion").selectedIndex = 0
			document.getElementById("idLugarPrimeraMatriculacion").disabled = bloqueado;
			document.getElementById("idLugarPrimeraMatriculacion").style.backgroundColor = color;
		}
	}
	if(document.getElementById("kmUso")){
		document.getElementById("kmUso").disabled = bloqueado;
		document.getElementById("kmUso").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("kmUso").value = "";
		}
	}
	if(document.getElementById("horasUso")){
		document.getElementById("horasUso").disabled = bloqueado;
		document.getElementById("horasUso").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("horasUso").value = "";
		}
	}
	if(document.getElementById("idMatriculaOrigen")){
		document.getElementById("idMatriculaOrigen").disabled = bloqueado;
		document.getElementById("idMatriculaOrigen").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("idMatriculaOrigen").value = "";
		}
	}

	if(document.getElementById("idImportado")){
		document.getElementById("idImportado").disabled = bloqueado;
		document.getElementById("idImportado").style.backgroundColor = color;
		if(bloqueado){
			document.getElementById("idImportado").checked = false;
		}
	}

	// Los combos de pais de importacion (mate) , procedencia (matw) y matricula extranjera no se deben habilitar, solo deshabilitar
	if (bloqueado) {
		if(document.getElementById("sigla3PaisProcedencia")){
			document.getElementById("sigla3PaisProcedencia").disabled = bloqueado;
			document.getElementById("sigla3PaisProcedencia").style.backgroundColor = color;
			if(bloqueado){
				document.getElementById("sigla3PaisProcedencia").value = "";
			}
		}
		if(document.getElementById("idComboPaisImportacion")){
			document.getElementById("idComboPaisImportacion").disabled = bloqueado;
			document.getElementById("idComboPaisImportacion").style.backgroundColor = color;
			if(bloqueado){
				document.getElementById("idComboPaisImportacion").selectedIndex = 0;
			}
		}

		if(document.getElementById("idMatriculaOrigenExtranjero")){
			$('#idMatriculaOrigenExtranjero').prop("disabled",bloqueado);
			if(bloqueado){
				$("#idMatriculaOrigenExtranjero").val("");
			}

		}

	}
}

//Desbloquea los combos de pais de importacion (mate) , procedencia (matw) y matricula_origen_extranjero (matw)
function desbloquearImportado(checked) {
	var bloqueado = true;
	var color = "#EEEEEE";

	if (checked == true) {
		bloqueado = false;
		color = "white";
	}

	if(document.getElementById("sigla3PaisProcedencia")){
		document.getElementById("sigla3PaisProcedencia").disabled = bloqueado;
		document.getElementById("sigla3PaisProcedencia").style.backgroundColor = color;
		if (!$('#idMatriculaOrigenExtranjero').is(':hidden')){
			$('#idMatriculaOrigenExtranjero').prop("disabled",bloqueado);
		}
		if(bloqueado){
			document.getElementById("sigla3PaisProcedencia").value = "";
			if (!$('#idMatriculaOrigenExtranjero').is(':hidden')){
				$("#idMatriculaOrigenExtranjero").val("");
			}
		}
	}
	if(document.getElementById("idComboPaisImportacion")){
		document.getElementById("idComboPaisImportacion").disabled = bloqueado;
		document.getElementById("idComboPaisImportacion").style.backgroundColor = color;

		if(bloqueado){
			document.getElementById("idComboPaisImportacion").selectedIndex = 0;
		}
	}
}

function limpiarFormularioAltaMarcaDgt(){
	if(document.getElementById("marca")){
		document.getElementById("marca").value = "";
	}
	if(document.getElementById("idFabricante")){
		document.getElementById("idFabricante").value = "";
	}
	if(document.getElementById("checkMate")){
		document.getElementById("checkMate").checked = false;
	}
	if(document.getElementById("checkMatw")){
		document.getElementById("checkMatw").checked = false;
	}
}

function altaMarcaDgt() {
	if (document.getElementById("marca").value == "" && document.getElementById("idFabricante").value == "") {
		alert("Introduzca el nombre de la nueva marca o fabricante");
		return false;
	}
	document.formData.action = "altaMarcaDgt.action";
	document.formData.submit();
	loadingGuardarMarcaDgt();
	return true;
}
function altaNuevaMarcaDgt() {
	if (document.getElementById("marca").value == "" && document.getElementById("idFabricante").value == "") {
		alert("Introduzca el nombre de la nueva marca o fabricante");
		return false;
	}
	document.formData.action = "altaMarcaDgtNueva.action";
	document.formData.submit();
	loadingGuardarMarcaDgt();
	return true;
}

function loadingGuardarMarcaDgt() {
	if(document.getElementById("idBotonGuardarMarcaDgt")){
		document.getElementById("idBotonGuardarMarcaDgt").disabled = "true";
		document.getElementById("idBotonGuardarMarcaDgt").style.color = "#6E6E6E";
		document.getElementById("idBotonGuardarMarcaDgt").value = "Procesando";
	}

	if(document.getElementById("idBotonLimpiarMarcaDgt")){
		document.getElementById("idBotonLimpiarMarcaDgt").disabled = "true";
		document.getElementById("idBotonLimpiarMarcaDgt").style.color = "#6E6E6E";
	}

	document.getElementById("bloqueLoadingGuardarMarcaDgt").style.display = "block";
}

function cambioConceptoRepresentanteTitular(){
	if(document.getElementById("conceptoRepresentanteTitular").value=="TUTELA O PATRIA POTESTAD"){
		document.getElementById("motivoRepresentanteTitular").disabled=false;
	} else {
		document.getElementById("motivoRepresentanteTitular").disabled=true;
		document.getElementById("motivoRepresentanteTitular").value="-1";
	}
}

function limpiarFormularioTitularMatriculacion(){
	var nifTitular = document.getElementById("nifTitular").value;
	if (nifTitular!=null && nifTitular!="") {
		document.getElementById("apellido1RazonSocialTitular").value = "";
		document.getElementById("apellido2Titular").value = "";
		document.getElementById("nombreTitular").value = "";
		document.getElementById("sexoTitular").value = "-1";
		document.getElementById("anagramaTitular").value = "";
		document.getElementById("diaNacTitular").value = "";
		document.getElementById("mesNacTitular").value = "";
		document.getElementById("anioNacTitular").value = "";
		document.getElementById("estadoCivilTitular").value = "-1";
		document.getElementById("telefonoTitular").value = "";
		document.getElementById("correoElectronicoTitular").value = "";
		document.getElementById("idProvinciaTitular").value = "-1";
		document.getElementById("idMunicipioTitular").length = 0;
		document.getElementById("idMunicipioTitular").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoTitular").value = "";
		document.getElementById("idPuebloTitular").length = 0;
		document.getElementById("idPuebloTitular").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoTitular").value = "";
		document.getElementById("tipoViaDireccionTitular").value = "-1";
		document.getElementById("nombreViaDireccionTitular").value = "";
		document.getElementById("numeroDireccionTitular").value = "";
		document.getElementById("codPostalTitular").value = "";
		document.getElementById("letraDireccionTitular").value = "";
		document.getElementById("escaleraDireccionTitular").value = "";
		document.getElementById("pisoDireccionTitular").value = "";
		document.getElementById("puertaDireccionTitular").value = "";
		document.getElementById("bloqueDireccionTitular").value = "";
		document.getElementById("kmDireccionTitular").value = "";
		document.getElementById("hmDireccionTitular").value = "";
		document.getElementById("idAutonomo").value = "";
		document.getElementById("epigrafe").value = "";
		document.getElementById("iae").value = "";
	}
}

function limpiarFormularioConductoHabitualMatriculacion(){
	var nifConductorHabitual = document.getElementById("nifConductorHabitual").value;
	if (nifConductorHabitual!=null && nifConductorHabitual!="") {
		document.getElementById("apellido1RazonSocialConductorHabitual").value = "";
		document.getElementById("apellido2ConductorHabitual").value = "";
		document.getElementById("nombreConductorHabitual").value = "";
		document.getElementById("diaAltaIni").value = "";
		document.getElementById("mesAltaIni").value = "";
		document.getElementById("anioAltaIni").value = "";
		document.getElementById("diaAltaFin").value = "";
		document.getElementById("mesAltaFin").value = "";
		document.getElementById("anioAltaFin").value = "";
	}
}

function limpiarFormularioRepresentanteMatriculacion(){
	if(document.getElementById("nifRepresentante")){
		var nifRepresentante = document.getElementById("nifRepresentante").value;
		if (nifRepresentante!=null && nifRepresentante!="") {
			document.getElementById("apellido1Representante").value = "";
			document.getElementById("apellido2Representante").value = "";
			document.getElementById("nombreRepresentante").value = "";
			document.getElementById("sexoRepresentante").value = "-1";
			document.getElementById("diaNacRepresentante").value = "";
			document.getElementById("mesNacRepresentante").value = "";
			document.getElementById("anioNacRepresentante").value = "";
			document.getElementById("estadoCivilRepresentante").value = "-1";
			document.getElementById("idProvinciaRepresentante").value = "-1";
			document.getElementById("idMunicipioRepresentante").length = 0;
			document.getElementById("idMunicipioRepresentante").options[0] = new Option("Seleccione Municipio", "", true, true);
			document.getElementById("municipioSeleccionadoRepresentante").value = "";
			document.getElementById("idPuebloRepresentante").length = 0;
			document.getElementById("idPuebloRepresentante").options[0] = new Option("Seleccione Pueblo", "", true, true);
			document.getElementById("puebloSeleccionadoRepresentante").value = "";
			document.getElementById("tipoViaRepresentanteTitular").value = "-1";
			document.getElementById("nombreViaRepresentanteTitular").value = "";
			document.getElementById("numeroDireccionRepresentanteTitular").value = "";
			document.getElementById("codPostalRepresentante").value = "";
			document.getElementById("letraDireccionRepresentanteTitular").value = "";
			document.getElementById("escaleraDireccionRepresentanteTitular").value = "";
			document.getElementById("pisoDireccionRepresentanteTitular").value = "";
			document.getElementById("puertaDireccionRepresentanteTitular").value = "";
			document.getElementById("bloqueDireccionRepresentanteTitular").value = "";
			document.getElementById("kmDireccionRepresentanteTitular").value = "";
			document.getElementById("hmDireccionRepresentanteTitular").value = "";
			if(document.getElementById("diaInicioTutela")){
				document.getElementById("diaInicioTutela").value = "";
			}
			if(document.getElementById("mesInicioTutela")){
				document.getElementById("mesInicioTutela").value = "";
			}
			if(document.getElementById("anioInicioTutela")){
				document.getElementById("anioInicioTutela").value = "";
			}
			document.getElementById("conceptoRepresentanteTitular").value = "-1";
			document.getElementById("motivoRepresentanteTitular").value = "-1";	
		}
	}
}

function limpiarFormularioRentingMatriculacion(){
	var nifArrendatario = document.getElementById("nifArrendatario").value;
	if (nifArrendatario!=null && nifArrendatario!="") {
		document.getElementById("apellido1RazonSocialArrendatario").value = "";
		document.getElementById("apellido2Arrendatario").value = "";
		document.getElementById("nombreArrendatario").value = "";
		document.getElementById("sexoArrendatario").value = "-1";
		document.getElementById("diaNacArrendatario").value = "";
		document.getElementById("mesNacArrendatario").value = "";
		document.getElementById("anioNacArrendatario").value = "";
		document.getElementById("telefonoArrendatario").value = "";
		document.getElementById("correoElectronicoArrendatario").value = "";
		document.getElementById("idProvinciaArrendatario").value = "-1";
		document.getElementById("idMunicipioArrendatario").length = 0;
		document.getElementById("idMunicipioArrendatario").options[0] = new Option("Seleccione Municipio", "", true, true);
		document.getElementById("municipioSeleccionadoArrendatario").value = "";
		document.getElementById("idPuebloArrendatario").length = 0;
		document.getElementById("idPuebloArrendatario").options[0] = new Option("Seleccione Pueblo", "", true, true);
		document.getElementById("puebloSeleccionadoRepresentante").value = "";
		document.getElementById("tipoViaRepresentanteTitular").value = "-1";
		document.getElementById("nombreViaRepresentanteTitular").value = "";
		document.getElementById("numeroDireccionRepresentanteTitular").value = "";
		document.getElementById("codPostalRepresentante").value = "";
		document.getElementById("letraDireccionRepresentanteTitular").value = "";
		document.getElementById("escaleraDireccionRepresentanteTitular").value = "";
		document.getElementById("pisoDireccionRepresentanteTitular").value = "";
		document.getElementById("puertaDireccionRepresentanteTitular").value = "";
		document.getElementById("bloqueDireccionRepresentanteTitular").value = "";
		document.getElementById("kmDireccionRepresentanteTitular").value = "";
		document.getElementById("hmDireccionRepresentanteTitular").value = "";
		document.getElementById("diaInicioTutela").value = "";
		document.getElementById("mesInicioTutela").value = "";
		document.getElementById("anioInicioTutela").value = "";
		document.getElementById("conceptoRepresentanteTitular").value = "-1";
		document.getElementById("motivoRepresentanteTitular").value = "-1";
	}
}

function obtenerDetalleConsultaEEFF(numExpedienteEEFF){
	if(numExpedienteEEFF == ""){
		return alert("No se puede obtener el detalle de la consulta porque no se le indica el numero de expediente de EEFF.")
	}
	$("#divEmergenteMatriculacionConsultaEEFF").show();
	var $dest = $("#divEmergenteMatriculacionConsultaEEFF");
	$.post("cargarPopUpResultadoWSAltaConEEFF.action?consultaEEFF.numExpediente="+numExpedienteEEFF, function(data) {
		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal: true,
				show: {
					effect: "blind",
					duration: 300
				},
				dialogClass: 'no-close',
				width: 720,
				buttons: {
					Cerrar : function() {
						$("#divEmergenteMatriculacionConsultaEEFF").show();
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

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function incluirFichero(){
	$("#formData").attr("action", "subirJustificanteIVTMAltasMatriculacionMatw.action#PagosEImpuestos").trigger("submit");
}

function descargarJustificanteIVTM(){
	$("#formData").attr("action", "verJustificanteIVTMAltasMatriculacionMatw.action#PagosEImpuestos").trigger("submit");
}

//	function checkJustifIvtm(){
//		if(document.getElementById('idCheckJustifIVTM').checked){
//			document.getElementById('fichero').disabled = false;
//			document.getElementById('botonSubirFichero').disabled = false;
//			document.getElementById('idVerJustificanteIVTM').disabled = false;
//		} else {
//			document.getElementById('fichero').disabled = true;
//			document.getElementById('botonSubirFichero').disabled = true;
//			document.getElementById('idVerJustificanteIVTM').disabled = true;
//		}
//	}

function checkIvtm(){
	if(document.getElementById("idMunicipioIVTM").value == "MADRID"){
		visibilidadMostrar="block";
		visibilidadOcultar="none";
		read=true;
	}else{
		visibilidadMostrar="none";
		visibilidadOcultar="block";
		read=false;
	}
	bloquearCampos(read);
	cambiarVisibilidadMostrar(visibilidadMostrar);
	cambiarVisibilidadOcultar(visibilidadOcultar);
	deshabilitarBotonIvtm(visibilidadMostrar);
	deshabilitarBotonPagoIvtm(visibilidadMostrar);
	if(document.getElementById("idMunicipioIVTM").value == "MADRID"){
		cambiarColorFondo("#EEEEEE", "");
	} else {
		cambiarColorFondo("", "#EEEEEE");
	}
	activarMedioAmbienteIVTM();
}