var opciones_ventanaEvolucion = 'width=850,height=450,top=050,left=200';


function initDialogo(){
	var hayCircular= $("#existeCircular").val();
	if('SI' == hayCircular){
		$(document).ready(function() {
			$("#dialog").dialog({
				open:function () {
					$(".ui-dialog-titlebar").hide();
				}, 
				modal: true,
				width: 645,
				height: 780,
				buttons : {
					Aceptar : function(){
						$(this).dialog('close');
					}
				}
			});
		});    
	}
}


function  Limpia_Caracteres(cadena){

	var temporal="";

	for (var i=0; i<cadena.length; i++)
	{
	var caracter = cadena[i];
	if (!isNaN(caracter))
	{
		temporal=temporal + caracter;
	
	}
	}

	temporal=temporal.replace(" ", "");	
	
	
return temporal;	
}




function validarLetrasNumeros(e,modo) {
		
		tecla = (document.all) ? e.keyCode : e.which; 
		if (tecla==8) return true; 
		 if (tecla==0) return true;
		patron = (modo=='letra') ? /[A-Za-zs]/ : /d/; 
		te = String.fromCharCode(tecla); 
		return patron.test(te);
	}

	function validarLetras(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    if (tecla==0) return true; 
	    var patron = /[A-Za-z]+/;  
	    var te = String.fromCharCode(tecla);
	    return patron.test(te); 
	}
	
	function validarLetrasEspacios(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true; 
	    if (tecla==0) return true; 
	    if (tecla == 32) return true; 
	    var patron = /[A-Za-z]+/; 
	    var te = String.fromCharCode(tecla);
	    return patron.test(te); 
	}
	
	function validarMatricula(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true; 
	    if (tecla==0) return true;	    
	    var patron = /[A-Za-z0-9]+/;  
	    var te = String.fromCharCode(tecla);	    
	    return patron.test(te); 
	}

	function validarMatricula_alPegar(e) {
		var source = e.target || e.srcElement;
		var texto = document.getElementById(source.id).value

	    texto = texto.replace(' ','');
	    texto = texto.replace('-','');
	    texto = texto.toUpperCase();
	    document.getElementById(source.id).value = texto;
	    return true;
	} 
	
	function validarBastidor(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    if (tecla==0) return true;	    
	    var patron = /[A-Za-z0-9]+/; 
	    var te = String.fromCharCode(tecla);	    
	    return patron.test(te); 
	}
	
	function validarNumeros(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true; 
	    if (tecla==0) return true; 
	    var patron = /[0-9.,]+/; 
	    var te = String.fromCharCode(tecla);
	    return patron.test(te); 
	} 

	function validarNumerosDecimales(e) {
		
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==8) return true;
	    if (tecla==0) return true;
	    if (tecla == 46){ 	
	    	var valorAhora = document.getElementById(e.target.id).value;
	    	if(valorAhora != null){
	    		
		    	if(valorAhora.length == 0 || valorAhora.indexOf(".") != -1){
		    		return false;
		    	}
	    	}
	    }
	    var patron = /[0-9.]+/;  
	    var te = String.fromCharCode(tecla);
	    return patron.test(te); 
	}
	
	function validarDecimal(event, decimalesPermitidos) {

		var tecla = (document.all) ? event.keyCode : event.which;
		if (tecla==8) return true; 
		if (tecla==0) return true; 
		var patron = /[0-9.]+/;  
		var te = String.fromCharCode(tecla);
		var teclaPermitida = patron.test(te);
		if(teclaPermitida){
			var valorAhora = document.getElementById(event.target.id).value;
			if (tecla == 46){ 
				if(valorAhora != null){
					if(valorAhora.length == 0 || valorAhora.indexOf(".") != -1){
						return false;
					}
				}
			}

			if(valorAhora.length > 0 && valorAhora.indexOf(".") != -1){
				var decimales = valorAhora.substring(valorAhora.indexOf(".") + 1);
				if(decimales.length >= decimalesPermitidos){
					return false;
				}
			}
			return true;
		}else{
			return false;
		}
	} 
	
	function validarNumerosEnteros(e) {
	    var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==0) return true; 
	    if (tecla==8) return true;
	    var patron = /[0-9]+/;
	    var te = String.fromCharCode(tecla);
	    return patron.test(te); 
	} 
	
	function validarDia(t,e){
		var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==0) return true;
	    if (tecla==8) return true; 
	    var te = t.value + String.fromCharCode(tecla);
	    var ok = false;
	    if ((0 <= parseFloat(te)) && (parseFloat(te) < 32)){
	    	ok = true;
	    }else{
	    	ok = false
	    }
	    return ok;
	}

	
	function validarMes(t,e){
		var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==0) return true; 
	    if (tecla==8) return true; 
	    var te = t.value + String.fromCharCode(tecla);
	    var ok = false;
	    if ((0 <= parseFloat(te)) && (parseFloat(te) < 13)){
	    	ok = true;
	    }else{
	    	ok = false
	    }
	    return ok;
	}
	 	
	function validarAnio(t,e){
		var tecla = (document.all) ? e.keyCode : e.which;
	    if (tecla==0) return true; 
	    if (tecla==8) return true; 
	    var te = t.value + String.fromCharCode(tecla);
	    var ok = false;
	    if ((0 < parseFloat(te)) && (parseFloat(te) < 2500)){
	    	ok = true;
	    }else{
	    	ok = false
	    }
	    return ok;
	}
	
	function abrirEvolucionJPT(numExpediente){ 
	    var opciones_ventana2 = 'width=850,height=450,top=050,left=200';
		window.open('evolucionPresentacionJPT.action?numExpediente='+numExpediente,'popup',opciones_ventana2);	
	}
	
	function limpiarFormulario(idFormulario){
		var formulario = document.getElementById(idFormulario);
        var elementos = formulario.getElementsByTagName("*");
        for(i = 0; i < elementos.length; i++){
        	 if((elementos[i].tagName=='SELECT' && elementos[i] != document.getElementById('idResultadosPorPagina'))
            		|| (elementos[i].tagName=='INPUT' && elementos[i].type!='hidden' &&
            				  elementos[i].type!='submit' && elementos[i].type!='reset' && 
            				  elementos[i].type!='button')){
                    elementos[i].value = "";
              }
        }
	}
	
	function redimensionarCuerpo(){
		var menu = document.getElementById('dynamicMenu');
		var content = document.getElementById('principal_tiles');
		
		var alto = menu.offsetHeight; alert(content.offsetHeight+"<->"+alto);
		
		if(content.offsetHeight < alto){
			alert(content.offsetHeight+"<->"+alto);
			content.style.minHeight = alto+"px";
			alert(content.offsetHeight+"<->"+alto);
		}
	}
	
	function newInfoAjax(){
	    var xmlhttp=false;
	    try{
	        xmlhttp = new ActiveXObject("Msxml2.XMLHTTP");
	    }catch(e){
	        try{
	            xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
	        }catch(E){
	            xmlhttp = false;
	        }
	    }

	    if(!xmlhttp && typeof XMLHttpRequest!='undefined'){
	        xmlhttp = new XMLHttpRequest();
	    }
	    return xmlhttp;
	}

	function dameInfo(accion, elemento){		
		if(accion == 'mostrar'){			
			var url = "recuperarInfo.action?elemento="+elemento;
			
			var ajax = newInfoAjax();

			ajax.onreadystatechange = function () {
				if (ajax.readyState == 2){
					document.getElementById('info').innerHTML='Cargando datos informativos...';
				}
				if (ajax.readyState == 4){ 
					if (ajax.status == 200){ 
						muestraInfo(accion, elemento, ajax.responseText);
					}
				}
			}
			ajax.open("POST", url, true);
			ajax.send(null);
		} else if(accion == 'ocultar') {		
			muestraInfo(accion, elemento, '');		
		} else {}		
	}
	
	function dameInfoModelos600601(accion, elemento){		
		if(accion == 'mostrar'){			
			var url = "recuperarModelos600601Info.action?elemento="+elemento;
			
			var ajax = newInfoAjax();
			ajax.onreadystatechange = function () {
				if (ajax.readyState == 2){
					document.getElementById('info').innerHTML='Cargando datos informativos...';
				}
				if (ajax.readyState == 4){ 
					if (ajax.status == 200){ 
						muestraInfoModelos600601(accion, elemento, ajax.responseText);
					}
				}
			}
			ajax.open("POST", url, true);
			ajax.send(null);
		} else if(accion == 'ocultar') {		
			muestraInfo(accion, elemento, '');		
		} else {}		
	}
	
	function muestraInfoModelos600601(accion, elemento, infoText){
		
		if(accion == 'mostrar'){
			$('#info').css('display', 'block');
			$('#info').html(infoText);
			$('#info').offset({top: 260, left: 550});
		} else {
			$('#info').css('display', 'none');
		}	
	}

	function muestraInfo(accion, elemento, infoText){
		
		if(accion == 'mostrar'){
			$('#info').css('display', 'block');
			$('#info').html(infoText);
			$('#info').offset({top: $('#' + elemento).offset().top, left: $('#' + elemento).offset().left + 15});
		} else {
			$('#info').css('display', 'none');
		}	
	}
	
	function getProcesosAfectados(){

		var procesosAfectados = document.getElementById('procesosAfectados');
		var listaProcesosAfectados = document.getElementById('listaProcesosAfectados');
		var patron = document.getElementById('idPatron').value;
		if(patron!='' && patron!=null && patron!=undefined){

			var url = "getProcesosPatronTraficoAjax.action?patron=" + patron;
			
			var req_patron = newInfoAjax();
			req_patron.onreadystatechange = function () {
				if (req_patron.readyState == 4) { 
					if (req_patron.status == 200) { 
						procesosAfectados.style.display = 'block';
						listaProcesosAfectados.innerHTML = req_patron.responseText;
					}
				}
			}
			req_patron.open("POST", url, true);
			req_patron.send(null);
			
		} else {
			procesosAfectados.style.display = 'none';
		}

	}
	
	function inspeccionar(obj) {
	  var msg = '';

	  for (var property in obj) {
	    if (typeof obj[property] == 'function') {
	    	var inicio = obj[property].toString().indexOf('function');
	      	var fin = obj[property].toString().indexOf(')')+1;
	      	var propertyValue=obj[property].toString().substring(inicio,fin);
	      	msg +=(typeof obj[property])+' '+property+' : '+propertyValue+' ;\n';
	    } else if (typeof obj[property] == 'unknown') {
	    	msg += 'unknown '+property+' : unknown ;\n';
	    } else {
	    	msg +=(typeof obj[property])+' '+property+' : '+obj[property]+' ;\n';
	    }
	  }
	  return msg;
	}
	
	function dameInfoSinAjax(accion, elemento, texto) {
		var sTexto = "";
		
		if (texto == 'fechaCaducidadITV') {
			sTexto = "Seleccione la casilla de verificaci\u00f3n si <b>no</b> desea que la fecha de caducidad ITV se calcule de forma autom\u00e1tica.";
		}else if(texto == 'cambioDomicilio'){
			sTexto = "Marque la casilla 'Cambio de domicilio' para env\u00edar nuevos datos de la direcci\u00f3n del interviniente. Distintos de los que constan actualmente en DGT";
		}else if(texto == 'calcularFechaCaducidadITV'){
			sTexto = "Seleccione la casilla de verificaci\u00f3n si <b>no</b> desea que la fecha de caducidad ITV se calcule de forma autom\u00e1tica.";
		}else if(texto == "expColaYb"){
			sTexto = "EXPEDIENTES EN COLA <br /> (Cuadro A) <br /><br /> Son todos aquellos de los que se ha solicitado un documento base nuevo a demanda, o aquellos que pertenecen a un documento base pendiente de modificar. <br /><br /> (Cuadro A = Cuadro B + Cuadro C)";
		}else if(texto == "expGenerarYb"){
			sTexto = "EXP. PENDIENTES DE GENERAR <br /> (Cuadro B) <br /><br /> Son aquellos de los que se ha solicitado un documento base nuevo a demanda. <br /><br /> (Cuadro B = Cuadro A - Cuadro C)";
		}else if(texto == "expModificarYb"){
			sTexto = "EXP. PENDIENTES DE MODIFICAR <br /> (Cuadro C) <br /><br /> Son aquellos que pertenecen a un documento base existente, del que se ha solicitado modificaci\u00f3n. <br /><br /> (Cuadro C = Cuadro A - Cuadro B)";
		}else if(texto == "docBaseModYb"){
			sTexto = "DOC. BASE PENDIENTES DE MODIFICAR <br /> (Cuadro D) <br /><br /> Son aquellos de los que se ha solicitado una modificaci\u00f3n por parte del colegiado. <br /><br /> (Cuadro D <= Cuadro C)";
		}else if(texto == "docBaseRemYb"){
			sTexto = "DOC. BASE PENDIENTES DE ELIMINAR <br /> (Cuadro E) <br /><br /> Son aquellos de los que se ha solicitado eliminaci\u00f3n.";
		}else if(texto == "docBaseBlCola"){
			sTexto = "DOC. BASE BLOQUEAN COLA <br />  <br /><br /> Son aquellos que están bloqueando la cola de documentos base.";
		}else if(texto == "ultComprYb"){
			sTexto = "\u00daLTIMA COMPROBACI\u00d3N <br /> (Cuadro F) <br /><br /> Es la \u00faltima vez que se ha refrescado la monitorizaci\u00f3n. Si no se cambian los par\u00e1metros de b\u00fasqueda, s\u00f3lo se buscar\u00e1 en base de datos cada 30 segundos, devolviendo el mismo resultado si no ha pasado este tiempo. Si dos comprobaciones arrojan el mismo resultado, el estado de la cola aparecer\u00e1 como bloqueado.";
		}else if(texto.indexOf("Comentario Propiedad:") > -1){
			sTexto = texto.substring("Comentario Propiedad:".length);
		}else if(texto == "modificarPropiedad"){
			sTexto = "Guardar nuevo valor";
		}else if (texto == "fichaTecnicaRD750"){
			sTexto = "Seleccione la casilla de verificaci\u00f3n si la ficha t&eacute;cnica del veh&iacute;culo cumple la norma RD 750/2010";
		}else if (texto == "creditosBloqueados"){
			sTexto = "Los cr\u00e9ditos bloqueados son aquellos que reflejan los tr\u00e1mites que a\u00fan est\u00e1n pendientes de env\u00edo o dieron error de servicio y deben reiniciarse o borrarse.";
		}else if (texto == "caducidadDatosSensibles"){
			sTexto = "Una vez se realice una operaci\u00f3n sobre un dato sensible, dicho dato se dar\u00e1 de baja, pasadas las horas seleccionadas.";
		}else if(texto == "firCif"){
			sTexto = "CIF del Fabricante del Veh\u00edculo.";
		}else if(texto == "firMarca"){
			sTexto = "Marca del Fabricante del Veh\u00edculo.";
		}else if(texto == "asignarPrincipal"){
			sTexto = "S\u00f3lo si la direcci\u00f3n es nueva (SI [ser\u00e1 la nueva direcci\u00f3n principal] - NO [ser\u00e1 una nueva direcci\u00f3n m\u00e1s])";
		}
		
		if(accion == 'mostrar'){
			muestraInfo(accion, elemento, sTexto);
		} else if(accion == 'ocultar') {		
			muestraInfo(accion, elemento, '');		
		} else {}		
	}
	
	function dameInfo(accion, elemento, constante) {
		var sTexto = "";
		sTexto = eval(constante);
		if(accion == 'mostrar'){
			muestraInfo(accion, elemento, sTexto);
		} else if(accion == 'ocultar') {		
			muestraInfo(accion, elemento, '');		
		}
	}
	
	document.write("<script type='text/javascript' src='js/textosInformacion.js'></script>");
	
	
	
	function mensajeCampoReadOnly(accion, elemento, indice) {
		var sTexto = "Campo de 'solo lectura'";
		var divMensajeCampoReadOnly = document.getElementById('mensajeCampoReadOnly' + indice);
		if(accion == 'mostrar'){
			divMensajeCampoReadOnly.style.display = 'block';
			divMensajeCampoReadOnly.innerHTML = sTexto;
			divMensajeCampoReadOnly.style.marginTop = (document.getElementById(elemento).style.top + 15) + 'px';
			divMensajeCampoReadOnly.style.marginLeft = document.getElementById(elemento).style.left + 50;
		} else if(accion == 'ocultar') {		
			divMensajeCampoReadOnly.style.display = 'none';		
		}		
	}

	function onlyNumbers (event){
		try {
        if ( $.inArray(event.keyCode,[46,8,9,27,13,190]) !== -1 ||
            (event.keyCode == 65 && event.ctrlKey === true) || 
            (event.keyCode >= 35 && event.keyCode <= 39)) {
                 return;
        }
        else {
            if (event.shiftKey || (event.keyCode < 48 || event.keyCode > 57) && (event.keyCode < 96 || event.keyCode > 105 )) {
                event.preventDefault(); 
            }   
        }
		} catch (error) {
			console.log(error.message);
		}
    }

	function addFXToCalendars() {
		try {
			$("a[onclick*=fPopCalendarSplit]").each(function(index) {
				try {
					var s = $(this).attr('onclick').replace(/^.*fPopCalendarSplit\(/g, '').split(',');
					var $dia = eval("$("+ s[2].substring(0, s[2].indexOf(")")) + ")");
					var $mes = eval("$(" + s[1] + ")");
					var $anio = eval("$(" + s[0] + ")");
					var $salto = false; 
					
					$dia.keydown(function(event) {onlyNumbers(event);});
					$mes.keydown(function(event) {onlyNumbers(event);});
					$anio.keydown(function(event) {onlyNumbers(event);});
					
					$mes.keyup(function(e) {
						try {
							if ($mes.val().length == 2) {
								if (e.which != 9 && e.which != 16) {
									if(!$salto){
										$anio.select(); 
										$salto=true;
										return;
									}
								}
							}
							$salto = false;
						} catch (error) {
							console.log(error.message);
						}
					});
					
					$dia.keyup(function(e) {
						try {
							if ($dia.val().length == 2) {
								if (e.which != 9 && e.which != 16) {
									if(!$salto){
										$mes.select(); 
										$salto = true;
										return;
									}
								}
							}
							$salto=false;
						} catch (error) {
							console.log(error.message);
						}
					});
				} catch (error) {
					console.log(error.message);
				}
			});
		} catch (error) {
			console.log(error.message);
		}
	}

	$( document ).ready(function() {
		addFXToCalendars();
	});
	
	function validarNif(nif){
		var dni=nif.substring(0, nif.length-1);
		var letr=nif.charAt(nif.length-1);
		if (!isNaN(letr)) {
			return false;
		} else {
			cadena = "TRWAGMYFPDXBNJZSQVHLCKET";
			posicion = dni % 23;
			letra = cadena.substring(posicion,posicion+1);
			if (letra!=letr.toUpperCase()){
				return false;
			}
		}
		return true;
	}
	

	function validaCif(a) {
		var resul = true;
		var temp = ($(a)).val();
		if (temp !== '') {
			
	        suma = parseInt(temp[2]) + parseInt(temp[4]) + parseInt(temp[6]);
	        for (i = 1; i < 8; i += 2) {
	            temp1 = 2 * parseInt(temp[i]);
	            temp1 += '';
	            temp1 = temp1.substring(0,1);
	            temp2 = 2 * parseInt(temp[i]);
	            temp2 += '';
	            temp2 = temp2.substring(1,2);
	            if (temp2 == '') {
	                temp2 = '0';
	            }
	            suma += (parseInt(temp1) + parseInt(temp2));
	        }
	        suma += '';
	        n = 10 - parseInt(suma.substring(suma.length-1, suma.length));

			if (/^[ABCDEFGHJNPQRSUVW]{1}/.test(temp)) {
				var temp_n = n + '';
				if (temp[8] == String.fromCharCode(64 + n) || temp[8] == parseInt(temp_n.substring(temp_n.length-1, temp_n.length))) {
					return 2;
				} else if (temp[8] != String.fromCharCode(64 + n)) {
					($(a)).val(temp.substr(0,8) + String.fromCharCode(64 + n));
					resul = false;
				} else if (temp[8] != parseInt(temp_n.substring(temp_n.length-1, temp_n.length))) {
					($(a)).val(temp.substr(0,8) + parseInt(temp_n.substring(temp_n.length-1, temp_n.length)));
					resul = false;
				} else {
					($(a)).val(temp);
					resul = false;
				}
			}
		}
		if (!resul) {      
			($(a)).focus();
			return resul;
		}
	}
	
	function valida_nif_cif_nie(a) {
	    var resul = true;
	    var temp = ($(a)).val();
	    temp=temp.toUpperCase();
	    var cadenadni = "TRWAGMYFPDXBNJZSQVHLCKE";
	    if (temp !== '') {
	        suma = parseInt(temp[2]) + parseInt(temp[4]) + parseInt(temp[6]);
	        for (i = 1; i < 8; i += 2) {
	            temp1 = 2 * parseInt(temp[i]);
	            temp1 += '';
	            temp1 = temp1.substring(0,1);
	            temp2 = 2 * parseInt(temp[i]);
	            temp2 += '';
	            temp2 = temp2.substring(1,2);
	            if (temp2 == '') {
	                temp2 = '0';
	            }
	            suma += (parseInt(temp1) + parseInt(temp2));
	        }
	        suma += '';
	        n = 10 - parseInt(suma.substring(suma.length-1, suma.length));
	        if ((!/^[A-Z]{1}[0-9]{7}[A-Z0-9]{1}$/.test(temp) && !/^[T]{1}[A-Z0-9]{8}$/.test(temp)) && !/^[0-9]{8}[A-Z]{1}$/.test(temp)) {
	            if ((temp.length == 9) && (/^[0-9]{9}$/.test(temp))) {
	                var posicion = temp.substring(8,0) % 23;
	                var letra = cadenadni.charAt(posicion);
	                var letradni = temp.charAt(8);
	                ($(a)).val(temp.substr(0,8) + letra);
	                resul = false;
	            } else if (temp.length == 8) {
	                if (/^[0-9]{1}/.test(temp)) {
	                    var posicion = temp.substring(8,0) % 23;
	                    var letra = cadenadni.charAt(posicion);
	                    var tipo = 'NIF';
	                } else if (/^[KLM]{1}/.test(temp)) {
	                    var letra = String.fromCharCode(64 + n);
	                    var tipo = 'NIF';
	                } else if (/^[ABCDEFGHJNPQRSUVW]{1}/.test(temp)) {
	                    var letra = String.fromCharCode(64 + n);
	                    var tipo = 'CIF';
	                } else if (/^[T]{1}/.test(temp)) {
	                    var letra = String.fromCharCode(64 + n);
	                    var tipo = 'NIE';
	                } else if (/^[XYZ]{1}/.test(temp)) {
	                    var pos = str_replace(['X', 'Y', 'Z'], ['0','1','2'], temp).substring(0, 8) % 23;
	                    var letra = cadenadni.substring(pos, pos + 1);
	                    var tipo = 'NIE';
	                }
	                if (letra !== '') {
	                	($(a)).val(temp + letra);
	                } else {
	                    ($(a)).val(temp);
	                }
	                resul = false;
	            } else if (temp.length < 8) {
	                ($(a)).val(temp);
	                resul = false;
	            } else {
	                ($(a)).val(temp);
	                resul = false;
	            }
	        }
	        else if (/^[0-9]{8}[A-Z]{1}$/.test(temp)) {
	            var posicion = temp.substring(8,0) % 23;
	            var letra = cadenadni.charAt(posicion);
	            var letradni = temp.charAt(8);
	            if (letra == letradni) {
	                return 1;
	            } else if (letra != letradni) {
	                resul = false;
	            } else {
	                ($(a)).val(temp);
	                resul = false;
	            }
	        }
	        else if (/^[KLM]{1}/.test(temp)) {
	            if (temp[8] == String.fromCharCode(64 + n)) {
	                return 1;
	            } else if (temp[8] != String.fromCharCode(64 + n)) {
	                resul = false;
	            } else {
	                resul = false;
	            }
	        }
	        else if (/^[ABCDEFGHJNPQRSUVW]{1}/.test(temp)) {
	            var temp_n = n + '';
	            if (temp[8] == String.fromCharCode(64 + n) || temp[8] == parseInt(temp_n.substring(temp_n.length-1, temp_n.length))) {
	                return 2;
	            } else if (temp[8] != String.fromCharCode(64 + n)) {
	                ($(a)).val(temp.substr(0,8) + String.fromCharCode(64 + n));
	                resul = false;
	            } else if (temp[8] != parseInt(temp_n.substring(temp_n.length-1, temp_n.length))) {
	                ($(a)).val(temp.substr(0,8) + parseInt(temp_n.substring(temp_n.length-1, temp_n.length)));
	                resul = false;
	            } else {
	                ($(a)).val(temp);
	                resul = false;
	            }
	        }
	        else if (/^[T]{1}/.test(temp)) {
	            if (temp[8] == /^[T]{1}[A-Z0-9]{8}$/.test(temp)) {
	                return 3;
	            } else if (temp[8] != /^[T]{1}[A-Z0-9]{8}$/.test(temp)) {
	                var letra = String.fromCharCode(64 + n);
	                var letranie = temp.charAt(8);
	                if (letra != letranie) {
	                    resul = false;
	                } else {
	                    ($(a)).val(temp);
	                    resul = false;
	                }
	            }
	        }
	        else if (/^[XYZ]{1}/.test(temp)) {
	            var pos = str_replace(['X', 'Y', 'Z'], ['0','1','2'], temp).substring(0, 8) % 23;
	            var letra = cadenadni.substring(pos, pos + 1);
	            var letranie = temp.charAt(8);
	            if (letranie == letra) {
	                return 3;
	            } else if (letranie != letra) {
	                resul = false;
	            } else {
	                ($(a)).val(temp);
	                resul = false;
	            }
	        }
	    }
	    if (!resul) {      
	    	($(a)).focus();
	        return resul;
	    }
	}
	
	function valida_nif_cif_nie_alert(a){
		if (!valida_nif_cif_nie(a)){
			alert("El DNI/CIF/NIE no es correcto");
			return false;
		}
		return true
	}
	
	function str_replace(busca, repla, orig)
	{
		var str = new String(orig.replace('X', '0'));
		var str2 = new String(str.replace('Y', '1'));
		var str3 = new String(str2.replace('Z', '2'));
		
		return str3;
	}
	
	function deshabilitarTipoControl(){
		var tipoDato = document.getElementById("idTipoAgrupacion").value;
		if(tipoDato == 'Bastidor'){
			document.getElementById("idTipoControl").disabled = false;
			document.getElementById("idTipoControl").style.color = "#000000";
		}else{
			document.getElementById("idTipoControl").value = "-1";
			document.getElementById("idTipoControl").disabled = true;
			document.getElementById("idTipoControl").style.color = "#6E6E6E";
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
	
	function consultaEvolucionTramiteTrafico(numExpediente) {
		if (numExpediente != null && numExpediente != "") {
			var ventana = window.open('inicioConsultaEvTramiteTraf?numExpediente=' + numExpediente, 'popup',
				opciones_ventanaEvolucion);
		}
	}
	
	function checkFA(){
		 var check = document.getElementById('idCheckFa').checked && 
		!document.getElementById('idCheckFa').disabled;
	
	 if (check) {
		 document.getElementById('idFa').disabled =false;
		
		 document.getElementById("idLabelFa").style.color = "black";
	 }
	 else 
	 {
		 document.getElementById('idFa').disabled =true;
		 document.getElementById('idFa').value ="";
		 document.getElementById("idLabelFa").style.color = "gray";
	 }
	return;
}

	function faConValor() {

		if (document.getElementById("idFa").value != null
				&& document.getElementById("idFa").value != "") {
			document.getElementById("idCheckFa").checked = true;
			document.getElementById("idFa").disabled = false;
			document.getElementById("idLabelFa").style.color = "black";
		}
		else 
			{
			document.getElementById("idCheckFa").checked = false;
			document.getElementById("idFa").disabled = true;
			}
	}

	function abrirVentanaCatastroPopUp(){
		ventanaCatastro = window.open('https://www1.sedecatastro.gob.es/CYCBienInmueble/OVCBusqueda.aspx', 'popup', 'width=900,height=500,top=150,left=280' ,'resizable=NO,status=NO,menubar=NO');
		ventanaCatastro.focus();
	}

	function abrirVentanaCorreosPopUp(){
		ventanaCatastro = window.open('https://www.correos.es/es/es/herramientas/codigos-postales/detalle', 'popup', 'width=900,height=500,top=150,left=280' ,'resizable=NO,status=NO,menubar=NO');
		ventanaCatastro.focus();
	}
