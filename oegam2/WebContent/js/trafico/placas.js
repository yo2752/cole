	/**
	 * Funciones JS relacionadas con Placas
	 */

	function solicitarPlacasMatricula(formId) {
		if (numCheckedImprimir() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}

		var checks = document.getElementsByName("listaExpedientes");
		var codigos = "";
		var omitidos = "";
		var omitidosMatriculas = "";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				var estadoTramite = document.getElementById('estado_' + checks[i].value).value;
				if(estadoTramite == '12' || estadoTramite == '13' || estadoTramite == '14'){
					var matricula = document.getElementById('matricula_' + checks[i].value).value;
					if (matricula != null && matricula != "") {
						if (codigos == "") {
							codigos += checks[i].value;
						} else {
							codigos += "-" + checks[i].value;
						}
					} else {
						if(omitidosMatriculas == "") {
							omitidosMatriculas += "Trámites omitidos por no tener matrícula: " + checks[i].value;
						} else {
							omitidosMatriculas += ", " + checks[i].value;
						}
					}
				} else {
					if (omitidos == "") {
						omitidos += "Trámites omitidos por no estar finalizados: " + checks[i].value;
					} else {
						omitidos += ", " + checks[i].value;
					}
				}
			}
			i++;
		}

		if (codigos != null && codigos != '' && codigos != 'undefined' && omitidos == "" && omitidosMatriculas == ""){
			var formulario = document.getElementById(formId);
			formulario.action = 'nuevaSolicitudPlacas.action?numsExpedientes=' + codigos;

			formulario.submit();
		} else {
			alert("No hay expedientes válidos para solicitar las placas de matrícula.\n\r" + omitidos + "\n\r" + omitidosMatriculas);
			return false;
		}

	}

	function dibujarMatricula(elemento, matricula, tipoPlaca, posicion) {
		var trozo1;
		var trozo2;
		var trozo3;

		var tipoMatricula = analizarMatricula(matricula);
		var imagenMatricula = $('#imagenMatricula_' + posicion + '_' + elemento);
		var letrasMatricula = $('#letrasMatricula_' + posicion + '_' + elemento);

		if (tipoPlaca == null && tipoMatricula=='ordinaria') {
			tipoPlaca = '1';
		} else if (tipoPlaca == null && tipoMatricula=='ciclomotor') {
			tipoPlaca = '6';
		} else if (tipoPlaca == null && tipoMatricula=='tractor') {
			tipoPlaca = '5';
		}

		imagenMatricula.fadeOut("fast");
		letrasMatricula.fadeOut("fast");

		setTimeout(function(){
			switch(tipoMatricula){
				case "ordinaria":
					trozo1 = matricula.substring(0,4);
					trozo2 = matricula.substring(4,7);

					if (tipoPlaca == "1") {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/matricula_normal.png');
						imagenMatricula.css({
							'top': '65px',
							'left': '55px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;&nbsp;' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '3.8em',
							'fontWeight': 'bold',
							'top': '95px',
							'left': '100px',
							'text-align' :'left'
						});
					} else if (tipoPlaca == "2") {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/matricula_alfa.png');
						imagenMatricula.css({
							'top': '70px',
							'left': '75px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '3.4em',
							'fontWeight': 'normal',
							'top': '95px',
							'left': '110px',
							'text-align' :'left'
						});
					} else if (tipoPlaca == "3") {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/motocicleta.png');
						imagenMatricula.css({
							'top': '40px',
							'left': '95px'
						});
						letrasMatricula.html(trozo1 + '<br /><br /><br /><br />' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '4em',
							'fontWeight': 'bold',
							'top': '70px',
							'left': '150px',
							'text-align' :'left'
						});
					} else if (tipoPlaca == "4") {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/motocicleta_trial.png');
						imagenMatricula.css({
							'top': '50px',
							'left': '105px'
						});
						letrasMatricula.html(trozo1 + '<br /><br /><br /><br />' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '4em',
							'fontWeight': 'normal',
							'top': '75px',
							'left': '150px',
							'text-align' :'left'
						});
					} else if(tipoPlaca=="7"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/ordinaria_alta.png');
						imagenMatricula.css({
							'top': '27px',
							'left': '55px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;&nbsp;' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '3.8em',
							'fontWeight': 'bold',
							'top': '95px',
							'left': '100px',
							'text-align' :'left'
						});
					} else if (tipoPlaca == "8") {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/matricula_vtc-taxi.png');
						imagenMatricula.css({
							'top': '65px',
							'left': '55px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;&nbsp;' + trozo2);
						letrasMatricula.css({
							'color': 'white',
							'font-size': '4em',
							'fontWeight': 'normal',
							'top': '95px',
							'left': '100px',
							'text-align' :'left'
						});
					} else {
						imagenMatricula.removeAttr('src');
						imagenMatricula.removeAttr('alt');
						letrasMatricula.css({
							'color': 'black',
							'font-size': '1em',
							'fontWeight': 'bold',
							'top': '70px',
							'left': '100px',
							'text-align' :'left'
						});
						letrasMatricula.html('La matr&iacute;cula no parece aplicable<br />al tipo de placa seleccionado');
					}
				break;

				case "ciclomotor":
					trozo1 = matricula.substring(0,2);
					trozo2 = matricula.substring(2,5);
					trozo3 = matricula.substring(5,8);

					if (tipoPlaca == '6') {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/ciclomotor.png');
						imagenMatricula.css({
							'top': '45px',
							'left': '140px'
						});
						letrasMatricula.html(trozo1 + '<br /><br /><br />' + trozo2 + '<br /><br /><br />' + trozo3);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '2.7em',
							'fontWeight': 'normal',
							'top': '70px',
							'left': '145px',
							'text-align' :'center'
						});
					} else {
						imagenMatricula.removeAttr('src');
						imagenMatricula.removeAttr('alt');
						letrasMatricula.css({
							'color': 'black',
							'font-size': '1em',
							'fontWeight': 'bold',
							'top': '45px',
							'left': '140px',
							'text-align' :'left'
						});
						letrasMatricula.html('La matr&iacute;cula no parece aplicable<br />al tipo de placa seleccionado');
					}
				break;

				case "tractor":
					trozo1 = matricula.substring(0,1);
					trozo2 = matricula.substring(1,5);
					trozo3 = matricula.substring(5,8);

					if (tipoPlaca == '5') {
						imagenMatricula.attr('src', '/oegam2/img/matriculas/tractor_alta.png');
						imagenMatricula.css({
							'top': '40px',
							'left': '95px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;&nbsp;' + trozo2 + '<br /><br /><br /><br /><br /><br />' + trozo3);
						letrasMatricula.css({
							'color': 'red',
							'font-size': '4.4em',
							'fontWeight': 'normal',
							'top': '70px',
							'left': '110px',
							'text-align' :'center'
						});
					} else {
						imagenMatricula.removeAttr('src');
						imagenMatricula.removeAttr('alt');
						letrasMatricula.css({
							'color': 'black',
							'font-size': '1em',
							'fontWeight': 'bold',
							'top': '70px',
							'left': '95px',
							'text-align' :'left'
						});
						letrasMatricula.html('La matr&iacute;cula no parece aplicable<br />al tipo de placa seleccionado');
					}
				break;

				case "ordinaria_antigua":
					if(/(^([A-Z]{1})([0-9]{4})([A-Z]{1,2}))$/.test(matricula)){
						trozo1 = '&nbsp;' + matricula.substring(0,1);
						trozo2 = matricula.substring(1,5);
						trozo3 = matricula.substring(5);
					} else if(/(^([A-Z]{2})([0-9]{4})([A-Z]{1,2}))$/.test(matricula)){
						trozo1 = matricula.substring(0,2);
						trozo2 = matricula.substring(2,6);
						trozo3 = matricula.substring(6);
					}

					if(tipoPlaca=="1"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/matricula_normal.png');
						imagenMatricula.css({
							'top': '65px',
							'left': '55px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;' + trozo2 + '&nbsp;' + trozo3);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '3.8em',
							'fontWeight': 'bold',
							'top': '95px',
							'left': '100px',
							'text-align' :'left'
						});
					} else if(tipoPlaca=="2"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/matricula_alfa.png');
						imagenMatricula.css({
							'top': '70px',
							'left': '75px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;' + trozo2 + '&nbsp;' + trozo3);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '3.4em',
							'fontWeight': 'normal',
							'top': '95px',
							'left': '110px',
							'text-align' :'left'
						});
					} else if(tipoPlaca=="3"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/motocicleta.png');
						imagenMatricula.css({
							'top': '40px',
							'left': '95px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;-&nbsp;' + trozo3 + '<br /><br /><br /><br />' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '4em',
							'fontWeight': 'bold',
							'top': '70px',
							'left': '150px',
							'text-align' :'left'
						});
					} else if(tipoPlaca=="4"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/motocicleta_trial.png');
						imagenMatricula.css({
							'top': '50px',
							'left': '105px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;-&nbsp;' + trozo3 + '<br /><br /><br /><br />' + trozo2);
						letrasMatricula.css({
							'color': 'black',
							'font-size': '4em',
							'fontWeight': 'normal',
							'top': '75px',
							'left': '150px',
							'text-align' :'left'
						});
					} else {
						imagenMatricula.removeAttr('src');
						imagenMatricula.removeAttr('alt');
						letrasMatricula.css({
							'color': 'black',
							'font-size': '1em',
							'fontWeight': 'bold',
							'top': '70px',
							'left': '100px',
							'text-align' :'left'
						});
						letrasMatricula.html('La matr&iacute;cula no parece aplicable<br />al tipo de placa seleccionado');
					}
					break;

				default:
					imagenMatricula.removeAttr('src');
					imagenMatricula.removeAttr('alt');
					letrasMatricula.css({
						'color': 'black',
						'font-size': '1em',
						'fontWeight': 'bold',
						'top': '70px',
						'left': '95px',
						'text-align' :'left'
					});
					letrasMatricula.html('La matr&iacute;cula no parece aplicable<br />al tipo de placa seleccionado');
				break;
			}

			imagenMatricula.fadeIn("fast");
			letrasMatricula.fadeIn("fast");
		}, 250);
		
	}

	function analizarMatricula(matricula){
		var trozo1;
		var trozo2;
		var trozo3;

		// Analisis matricula ordinaria antigua
		var expregMatricula = /(^([A-Z]{1,2})([0-9]{4})([A-Z]{1,2}))$/;
		if (expregMatricula.test(matricula)) {
			tipoMatricula = "ordinaria_antigua";
			return tipoMatricula;
		}

		// Análisis matrícula ordinaria
		trozo1 = matricula.substring(0,4);
		trozo2 = matricula.substring(4,7);

		if (!isNaN(trozo1) && isNaN(trozo2)) {
			tipoMatricula = "ordinaria";
			return tipoMatricula;
		}

		// Análisis tipo matrícula antigua

		// Análisis matrícula tractor y agrícolas
		trozo1 = matricula.substring(0,1);
		trozo2 = matricula.substring(1,5);
		trozo3 = matricula.substring(5,8);

		if (isNaN(trozo1) && trozo1 == 'E' && !isNaN(trozo2) && isNaN(trozo3)) {
			tipoMatricula = "tractor";
			return tipoMatricula;
		}

		// Análisis matrícula ciclomotores
		trozo1 = matricula.substring(0,2);
		trozo2 = matricula.substring(2,5);
		trozo3 = matricula.substring(5,8);

		if (isNaN(trozo1) && trozo1.substr(0,1)=='C' && !isNaN(trozo2) && isNaN(trozo3)) {
			tipoMatricula = "ciclomotor";
			return tipoMatricula;
		}
	}

	function realizarSolicitudPlacas(formulario) {
		formulario.action = 'realizarSolicitudSolicitudPlacas.action';
		formulario.submit();
	}

	function confirmarSolicitudPlacas(formulario) {
		formulario.action = 'confirmarSolicitudSolicitudPlacas.action';
		formulario.submit();
	}

	function finalizarSolicitudPlacas(formulario) {
		formulario.action = 'finalizarSolicitudSolicitudPlacas.action';
		formulario.submit();
	}

	function cambiarEstadoSolicitudPlacas(formulario, estado){
		var campoEstado = document.createElement("INPUT");
		campoEstado.type = "hidden";
		campoEstado.name = "estado";
		campoEstado.value = estado;
		formulario.appendChild(campoEstado);

		formulario.action = 'cambiarEstadoSolicitudSolicitudPlacas.action';
		formulario.submit();
		formulario.removeChild(campoEstado);
	}

	function editarSolicitudesPlacas(formulario) {
		if (numChecked() == 0) {
			alert("Debe seleccionar alguna Solicitud de placa");
			return false;
		}

		var checks = document.getElementsByName("listaChecksConsultaTramite");
		var codigos = "";
		var omitidos = "";
		var estadosInd ="";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				var estadoSolicitud = document.getElementById('estado_' + checks[i].value).value;
				estadosInd = estadosInd + estadoSolicitud + "-";
				if(estadoSolicitud == '1' || estadoSolicitud == '2' || estadoSolicitud == '3' || estadoSolicitud == '4'){	
					if (codigos == "") {
						codigos += checks[i].value;
					} else {
						codigos += "-" + checks[i].value;
					}
				} else {
					alert("El estado no puede estar vacío");
					return false;
				}
			}
			i++;
		}

		var campoCodigos = document.createElement("INPUT");
		var estadosSolicitudes = document.createElement("INPUT");
		campoCodigos.type = "hidden";
		campoCodigos.name = "idSolicitud";
		campoCodigos.value = codigos;

		estadosSolicitudes.type = "hidden";
		estadosSolicitudes.name = "estadoSolicitudId";
		estadosSolicitudes.value = estadosInd;

		formulario.appendChild(campoCodigos);
		formulario.appendChild(estadosSolicitudes);
		formulario.action = 'modificarSolicitudPlacas.action';

		formulario.submit();
		formulario.removeChild(campoCodigos);
		formulario.removeChild(estadosSolicitudes);
	}

	function borrarSolicitudesPlacas(formulario){
		if (numChecked() == 0) {
			alert("Debe seleccionar alguna Solicitud de placa");
			return false;
		}

		if (!confirm("¿Est\u00E1 seguro de querer borrar las solicitudes seleccionadas?")) {
			return false;
		}
		var checks = document.getElementsByName("listaChecksConsultaTramite");
		var codigos = "";
		var omitidos = "";
		var estadosInd ="";
		var i = 0;
		while (checks[i] != null) {
			if (checks[i].checked) {
				var estadoSolicitud = document.getElementById('estado_' + checks[i].value).value;
				estadosInd = estadosInd + estadoSolicitud + "-";
				if(estadoSolicitud == '1' || estadoSolicitud == '2' || estadoSolicitud == '3' || estadoSolicitud == '4'){	
					if (codigos == "") {
						codigos += checks[i].value;
					} else {
						codigos += "-" + checks[i].value;
					}
				} else {
					if(omitidos = ""){
						omitidos += "Trámites omitidos por no estar finalizados: " + checks[i].value;
					} else {
						omitidos += ", " + checks[i].value;
					}
				}
			}
			i++;
		}

		var campoCodigos = document.createElement("INPUT");
		var estadosSolicitudes = document.createElement("INPUT");
		campoCodigos.type = "hidden";
		campoCodigos.name = "idSolicitud";
		campoCodigos.value = codigos;

		estadosSolicitudes.type = "hidden";
		estadosSolicitudes.name = "estadoSolicitudId";
		estadosSolicitudes.value = estadosInd;

		formulario.appendChild(campoCodigos);
		formulario.appendChild(estadosSolicitudes);
		formulario.action = 'borrarSolicitudPlacas.action';

		formulario.submit();
		formulario.removeChild(campoCodigos);
		formulario.removeChild(estadosSolicitudes);
	}

	function cambiarElementosPorPaginaConsulPlacas() {
		document.location.href = 'navegarConsultaPlacas.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
	}

	function validaMatriculasIguales(idTabla, idFormulario){

		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var numRows = $(tbody).find('tr').length;

		for (var numRow = 1; numRow <= numRows; numRow ++){
			var tr = $(tbody).find('tr:nth-child(' + numRow + ')')
			var numCols = $(tr).find('td').length;

			for (var numCol = 1; numCol <= numCols; numCol++) {
				var column = $(tr).find('td:nth-child(' + numCol + ')');
				var field;
				if ($(column).find('input').length != 0) {
					field = $(column).find('input').first();
				} else {
					field = '';
				}

				// Se clona el campo de la columna actual (si existe), se le modifica el id y se inserta a la nueva columna
				if (field!='') {
					var idField = $(field).attr('id');
					if (idField.indexOf('matricula') != -1){
						for (var numRowSecond = numRow + 1 ; numRowSecond <= numRows; numRowSecond ++){
							var tr = $(tbody).find('tr:nth-child(' + numRowSecond + ')')

							var columnSecond = $(tr).find('td:nth-child(' + numCol + ')');
							var fieldSecond;
							if($(columnSecond).find('input').length!=0){
								fieldSecond = $(columnSecond).find('input').first();
							} else {
								fieldSecond = '';
							}

							if (fieldSecond != '') {
								var idFieldSecond = $(fieldSecond).attr('id');
								if (idFieldSecond.indexOf('matricula') != -1) {
									if ($(fieldSecond).val() == $(field).val()) {
										alert("No puede haber dos matrículas iguales");
										return false;
									}
								}
							}
						}
					}
				}
			}
		}

		validarFormulario(idFormulario);
	}

	function validarContenidoCampos(campo, e) {
		var errores = $('#divError');
		var mensajesInfo = $('#mensajesInfo');
		var mensajesError = $('#mensajesError');
		mensajesError.html("");

		var nombreCampo = campo.id.substr(0, campo.id.length - 2);

		switch (nombreCampo) {
			case "matricula":
				if(!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>La matrícula no puede estar vacía</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
			case "bastidor":
				if (!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El bastidor no puede estar vacío</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
			case "nif":
				if (!e || e.type == 'change') {
					if (campo.value == '') {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El nif del titular no puede estar vacío</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else if (!valida_nif_cif_nie(campo)){
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.append("<li>El nif introducido no es válido</li>");
						mensajesError.css('display', 'block');
						errores.css('display', 'block');
						return false;
					} else {
						campo.style.color = '#000000';
						campo.value = campo.value.toUpperCase();
						return true;
					}
				}
				break;
			default:
				break;
		}

		return true;
	}

	function validarFormulario(idFormulario){
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				if (!validarContenidoCampos(elementos[i], null)) {
					return false;
				}
			}
		}

		formulario.action = 'nuevaSolicitudPlacas.action';
		formulario.submit();
	}

	function limpiarFormulario(idFormulario) {
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				//alert(elementos[i].tagName+" - "+elementos[i].type);
				elementos[i].value = "";
			}
		}
	}

	/*
	 * Función que añade filas a una tabla cuyo id es especificado por parámetro.
	 * La tabla debe tener un elemento tbody que contiene las filas sobre las que trabajar
	 * y excluye las cabeceras de la tabla.
	 */
	function addRow(idTabla) {
		// Inicializamos variables y obtenemos la fila inicial
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var tr = $(tbody).find('tr').first();
		var numRows = $(tbody).find('tr').length;
		var numCols = $(tr).find('td').length;
		var deleteButton = tr.find('.delete').first();

		deleteButton.css('display', 'inline');

		// Clonamos la fila inicial y borramos el contenido de la nueva
		var newTR = $(tr).clone();
		$(newTR).empty();

		// Se recorren las columnas obtenidas de la fila inicial
		for(var numCol = 1; numCol <= numCols; numCol++){
			var column = $(tr).find('td:nth-child(' + numCol + ')');
			var field;
			if($(column).find('input').length!=0){
				field = $(column).find('input').first();
			} else if($(column).find('select').length!=0){
				field = $(column).find('select').first();
			} else if($(column).find('button').length!=0){
				field = $(column).find('button').first();
			} else {
				field = '';
			}

			// Clonamos la columna actual y vaciamos la nueva
			var newColumn = $(column).clone();
			$(newColumn).empty();

			// Se clona el campo de la columna actual (si existe), se le modifica el id y se inserta a la nueva columna
			if (field != '') {
				var idField = $(field).attr('id');
				var idNewField = idField.substr(0, (idField.indexOf("_") + 1)) + numRows;

				var newField = $(field).clone();
				$(newField).attr('id', idNewField);

				$(newColumn).append($(newField));
			} else {
				$(newColumn).append($(column).html());
			}

			// Se añade la nueva columna a la nueva fila
			$(newTR).append($(newColumn));
		}

		// Se añade la nueva fila a la tabla
		$(tbody).append($(newTR));

		deleteButton.css('display', 'none');
	}

	function deleteRow(idTabla, remitente){
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var fila = $(remitente).closest('tr');

		if (tbody.find('tr').length > 1 && fila.html()!=tbody.find('tr').first().html()){
			tbody.find(fila).remove();
		} else {
			alert("No se puede eliminar la primera fila");
		}
	}

	function reindexRows(idTabla, accion, remitente) {
		if (accion == 'add') {
			addRow(idTabla);
		} else {
			deleteRow(idTabla, remitente);
		}

		// Inicializamos variables y obtenemos la fila inicial
		var tabla = $('#' + idTabla);
		var tbody = $(tabla).find('tbody');
		var numRows = $(tbody).find('tr').length;

		for (var numRow = 1; numRow <= numRows; numRow++) {
			var tr = $(tbody).find('tr:nth-child(' + numRow + ')');
			var numCols = $(tbody).find('td').length;

			for (var numCol = 1; numCol <= numCols; numCol++) {
				var column = $(tr).find('td:nth-child(' + numCol + ')');

				if($(column).find('input').length!=0){
					field = $(column).find('input').first();
				} else if($(column).find('select').length!=0){
					field = $(column).find('select').first();
				} else if($(column).find('button').length!=0){
					field = $(column).find('button').first();
				} else {
					field = '';
				}

				if (field != '' && numRow > 1) {
					var fieldName = $(field).attr('name');
					var fieldId = $(field).attr('id');
					var nameSearchValue = "", idSearchValue = "";
					if (accion == 'add') {
						nameSearchValue = "[0]";
						idSearchValue = "_0";
					} else {
						nameSearchValue = "[" + (numRow) + "]";
						idSearchValue = "_" + (numRow);
					}
					var nameNewValue = "[" + (numRow - 1) + "]";
					var idNewValue = "_" + (numRow - 1);
					var newFieldName = fieldName.replace(nameSearchValue, nameNewValue);
					var newFieldId = fieldId.replace(idSearchValue, idNewValue);

					var newField = $(field).clone();
					$(newField).attr('name', newFieldName);
					$(newField).attr('id', newFieldId);

					$(column).empty();
					$(column).append($(newField));
				}
			}

		}

	}

	function validarContenidoCamposConsulta(campo, e) {
		var divError = document.getElementById('divError');
		var mensajesError = document.getElementById('mensajesError');

		if (mensajesError == null) {
			divError.inneHTML = '<table class=\"tablaformbasica\" style=\"width:93.8%;margin-left:1.3em\"><tr><td align=\"left\"><ul id=\"mensajesError\" style=\"color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;\"><li><span><s:property escape=\"false\" /></span></li></ul></td></tr></table>';
			mensajeError = document.getElementById('mensajeError');
		}

		switch (campo.id) {
		case "numColegiado":
			if(e){
				if (e.type == 'focus') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						campo.value = '';
						return true;
					} else if (campo.value == 'Introduzca un número de colegiado...') {
						campo.style.color = '#000000';
						return true;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				} else if (e.type == 'blur') {
				} else if (e.type == 'change') {
					if ((campo.value != '' && isNaN(campo.value)) || campo.value.length != 4) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = "<li>El número de colegiado debe tener 4 caracteres y ser numérico</li>";
						divError.style.display = 'block';
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
			} else {
				if (campo.value != '' && campo.value!='Introduzca un número de colegiado...' && (isNaN(campo.value) || campo.value.length != 4)) {
					campo.style.color = '#ff0000';
					campo.focus();
					mensajesError.innerHTML = '<li>El número de colegiado debe tener 4 caracteres y ser numérico</li>';
					divError.style.display = 'block';
					return false;
				} else {
					campo.style.color = '#000000';
					return true;
				}
			}
			break;
		case "numExpediente":
			if (e) {
				if (e.type == 'focus') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						campo.value = '';
						return true;
					} else if (campo.value != '' && (isNaN(campo.value) || campo.value.length != 15)) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						divError.style.display = 'block';
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				} else if (e.type == 'blur') {
					if (campo.value == '') {
						campo.style.color = '#000000';
						return true;
					} else if ((campo.value != '' && (isNaN(campo.value)) || campo.value.length != 15)) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						divError.style.display = 'block';
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				} else if (e.type == 'change') {
					if ((campo.value != '' && (isNaN(campo.value)) || campo.value.length != 15)) {
						campo.style.color = '#ff0000';
						campo.focus();
						mensajesError.innerHTML = '<li>El número de expediente debe tener 15 caracteres y ser numérico</li>';
						divError.style.display = 'block';
						return false;
					} else {
						campo.style.color = '#000000';
						return true;
					}
				}
			} else {
				{
					campo.style.color = '#000000';
					return true;
				}
			}
			break;
		default:
			break;
		}
		return true;
	}

	function validarFormularioConsulta(idFormulario){
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				if(!validarContenidoCamposConsulta(elementos[i], null)) {
					return false;
				}
			}
		}

		buscarConsultaSolicitudPlacas();
	}

	function limpiarFormulario(idFormulario) {
		var formulario = document.getElementById(idFormulario);
		var elementos = formulario.getElementsByTagName("*");
		for (var i = 0; i < elementos.length; i++) {
			if (elementos[i].tagName == 'SELECT'
					|| (elementos[i].tagName == 'INPUT'
							&& elementos[i].type != 'hidden'
							&& elementos[i].type != 'submit'
							&& elementos[i].type != 'reset' && elementos[i].type != 'button')) {
				elementos[i].value = "";
			}
		}
	}

	function buscarConsultaSolicitudPlacas() {
		$('#formData').attr("action", "buscarConsultaPlacas.action").trigger("submit");
	}

	function buscarSolicitudPlacas() {
		$('#formData').attr("action", "busquedaSolicitudPlacas.action").trigger("submit");
	}

	function modificarSolicitudPlacas(idSolicitud) {
		$('#formData').attr("target","").attr("action", "modificarSolicitudPlacas.action");
	}

	function consultaPlacas() {
		if (isNaN(document.getElementById('numExpediente').value)) {
			document.getElementById('numExpediente').value = '';
		}

		document.getElementById('formData').action = 'buscarSolicitudPlacas.action';
		document.getElementById('formData').submit();
	}

	function generarEstadisticas() {
		var formulario = document.getElementById('formData');
		formulario.action = 'estadisticasSolicitudPlacas.action';
		formulario.submit();
	}

	function volverConsOAlta() {
		document.forms[0].action = "volverSolicitudPlacas.action";
		document.forms[0].submit();
	}
