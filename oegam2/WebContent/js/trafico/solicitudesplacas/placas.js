	/**
	 * Funciones JS relacionadas con Placas
	 */
	
	


function volverConsultaSolicitudesPlaca(boton) {
	mostrarLoadingImprimir(boton);
	document.forms[0].action = "load_buscarPlacas_ConsultaSolicitudesPlacas.action";
	document.forms[0].submit();
}

	function solicitarPlacasMatricula(formId){
		
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
						if(omitidosMatriculas == ""){
							omitidosMatriculas += "Tramites omitidos por no tener matricula: " + checks[i].value;
						} else {
							omitidosMatriculas += ", " + checks[i].value;
						}
					}
				} else {
					if(omitidos == ""){
						omitidos += "Tramites omitidos por no estar finalizados: " + checks[i].value;
					} else {
						omitidos += ", " + checks[i].value;
					}
				}
			}
			i++;
		}
		
		if(codigos!=null && codigos!='' && codigos!='undefined'){
			var formulario = document.getElementById(formId);
			formulario.action = 'inicioSolicitudSolicitudPlacasAltaSolicitudesPlacas.action?numsExpedientes=' + codigos;
			
			
			formulario.submit();
		} else {
			alert("No hay expedientes validos para solicitar las placas de matricula.\n\r" + omitidos + "\n\r" + omitidosMatriculas);
			return false;
		}
		
	}
	
	function dibujarMatricula(elemento, matricula, tipoPlaca, posicion){
		
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
					
					if(tipoPlaca=="1"){
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
					} else if(tipoPlaca=="2"){
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
					} else if(tipoPlaca=="3"){
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
					} else if(tipoPlaca=="4"){
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
					}else if(tipoPlaca=="7"){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/ordinaria_alta.png');
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
					} else if(tipoPlaca=="8"){
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
					
					if(tipoPlaca=='6'){
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
					
					/* Esto se debe utilizar si se pone la de tractor larga, con el tipoPlaca que le corresponda
					 * if(tipoPlaca=='5'){
						imagenMatricula.attr('src', '/oegam2/img/matriculas/tractor_larga.png');
						imagenMatricula.css({
							'top': '55px',
							'left': '45px'
						});
						letrasMatricula.html(trozo1 + '&nbsp;' + trozo2 + '&nbsp;' + trozo3);
						letrasMatricula.css({
							'color': 'red',
							'font-size': '3.8em',
							'fontWeight': 'normal',
							'top': '90px',
							'left': '70px',
							'text-align' :'left'
						});*/
					if(tipoPlaca=='5'){
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
						//alert('por aqui 1');
						trozo1 = '&nbsp;' + matricula.substring(0,1);
						trozo2 = matricula.substring(1,5);
						trozo3 = matricula.substring(5);
					} else if(/(^([A-Z]{2})([0-9]{4})([A-Z]{1,2}))$/.test(matricula)){
						//alert('por aqui 2');
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
		if(expregMatricula.test(matricula)){
			tipoMatricula = "ordinaria_antigua";
			return tipoMatricula;
		}
		
		
		// Analisis matricula ordinaria
		trozo1 = matricula.substring(0,4);
		trozo2 = matricula.substring(4,7);
		
		if(!isNaN(trozo1) && isNaN(trozo2)){
			tipoMatricula = "ordinaria";
			return tipoMatricula;
		}
		
		// Analisis tipo matricula antigua
		
		// Analisis matricula tractor y agricolas
		trozo1 = matricula.substring(0,1);
		trozo2 = matricula.substring(1,5);
		trozo3 = matricula.substring(5,8);
		
		if(isNaN(trozo1) && trozo1=='E' && !isNaN(trozo2) && isNaN(trozo3)){
			tipoMatricula = "tractor";
			return tipoMatricula;
		}
		
		// Analisis matricula ciclomotores
		trozo1 = matricula.substring(0,2);
		trozo2 = matricula.substring(2,5);
		trozo3 = matricula.substring(5,8);
		
		if(isNaN(trozo1) && trozo1.substr(0,1)=='C' && !isNaN(trozo2) && isNaN(trozo3)){
			tipoMatricula = "ciclomotor";
			return tipoMatricula;
		}	
		
	}
	
	function realizarSolicitudPlacas(formulario){
		
		formulario.action = 'boton_guardarSolicitud_AltaSolicitudesPlacas.action';
		formulario.submit();
		
	}
	
	function confirmarSolicitudPlacas(formulario){
		
		formulario.action = 'boton_confirmarSolicitud_AltaSolicitudesPlacas.action';
		formulario.submit();
		
	}
	
	function finalizarSolicitudPlacas(formulario){
		
		formulario.action = 'boton_finalizarSolicitud_AltaSolicitudesPlacas.action';
		formulario.submit();
		
	}
	
	function cambiarEstadoSolicitudPlacasaTramitado(formulario, estado){
		
		var campoEstado = document.createElement("INPUT");
		campoEstado.type = "hidden";
		campoEstado.name = "estado";
		campoEstado.value = estado;
		formulario.appendChild(campoEstado);
		
		formulario.action = 'boton_tramitarSolicitud_AltaSolicitudesPlacas.action';
		formulario.submit();
		formulario.removeChild(campoEstado);
		
	}
	
	function editarSolicitudesPlacas(formulario){
		
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
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
						omitidos += "Tramites omitidos por no estar finalizados: " + checks[i].value;
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
		formulario.action = 'boton_modificar_ConsultaSolicitudesPlacas.action';
		
		formulario.submit();
		formulario.removeChild(campoCodigos);
		formulario.removeChild(estadosSolicitudes);
		
	}
	
	function borrarSolicitudesPlacas(formulario){
		
		if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
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
						omitidos += "Tramites omitidos por no estar finalizados: " + checks[i].value;
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
		formulario.action = 'boton_borrar_ConsultaSolicitudesPlacas.action';
		
		formulario.submit();
		formulario.removeChild(campoCodigos);
		formulario.removeChild(estadosSolicitudes);
		
	}
	
	function cambiarElementosPorPaginaConsulPlacas() {
		
		document.location.href = 'navegar_ConsultaSolicitudesPlacas.action?resultadosPorPagina='
		+ document.formData.idResultadosPorPagina.value;
	}
