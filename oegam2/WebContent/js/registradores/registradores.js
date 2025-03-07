
function showTooltips(){
	$(".tooltips").tooltip(
			{
				// track: true,
				position : {
					my : "bottom-20",
					target : 'mouse',
					at : "right",
					using : function(position, feedback) {
						$(this).css(position);
						$("<div>")
						/* .addClass( "arrow" ) */
						.addClass(feedback.vertical).addClass(
								feedback.horizontal).appendTo(this);
					}
				}
			});
}

/*
 * Metodo que realiza la validación previa a la llamada PostLayer del formulario del
 * que se pasa el id (idForm). llamando a la url que se pasa por parametro
 * (url). idCapaShow es la capa donde se carga el contenido, e idCapaHide
 * es la capa que se ocultará, cuando la validación sea correcta. success, opcional,
 * función que se ejecutará de modo callback cuando el POST acabe con exito.
 * (Si hay que anidar muchos ", poner %)
 */
function doPostLayersValidate(idForm, url, idCapaShow, idCapaHide, success) {
	var $form = $("#" + idForm);
	$.post(
			url,
			$form.serialize()
			+ "&struts.enableJSONValidation=true&struts.validateOnly=true",
			function() {

			}).success(function() {
			}).error(function(data) {
			}).complete(function(data) {
				StrutsUtils.clearValidationErrors($form[0]);

				var text = data.responseText;
				var errorsObject = StrutsUtils.getValidationErrors(text);

				// Si hay errores, se muestran
				if (errorsObject.fieldErrors) {
					mostrarErrores($form, errorsObject);
				} else {
					// Se se pasa la validación, se hace submit
					doPostLayers(idForm, url, idCapaShow, idCapaHide, success);
				}
			});
}

/*
 * Metodo que realiza la validación previa a la llamada Post del formulario del
 * que se pasa el id (idForm). llamando a la url que se pasa por parametro
 * (url). dest, opcional. Si dest viene informado, se carga el contenido en el
 * elemento que tenga por id, dest. Si no viene informado, el contenido se carga
 * en la pagina actual success, opcional, función que se ejecutará de modo
 * callback cuando el POST acabe con exito. (Si hay que anidar muchos ", poner %)
 */
function doPostValidate(idForm, url, dest, success) {
	var $form = $("#" + idForm);
	doPost(idForm, url, dest, success);
//	$.post(
//	url,
//	$form.serialize()
//	+ "&struts.enableJSONValidation=true&struts.validateOnly=true",
//	function() {

//	}).success(function() {
//	}).error(function(data) {
//	}).complete(function(data) {
//	StrutsUtils.clearValidationErrors($form[0]);

//	var text = data.responseText;
//	var errorsObject = StrutsUtils.getValidationErrors(text);

	// Si hay errores, se muestran
//	if (errorsObject.fieldErrors) {
//	mostrarErrores($form, errorsObject);
//	} else {
	// Se se pasa la validación, se hace submit
	//doPost(idForm, url, dest, success);
//	}
//	});
}
/*
 * Metodo que realiza la llamada Post del formulario del que se pasa el id
 * (idForm). llamando a la url que se pasa por parametro (url). dest, opcional.
 * Si dest viene informado, se carga el contenido en el elemento que tenga por
 * id, dest. Si no viene informado, el contenido se carga en la pagina actual
 * success, opcional, función que se ejecutará de modo callback cuando el POST
 * acabe con exito. (Si hay que anidar muchos ", poner %)
 */
function doUploadFile(idForm, url, check, dest, success) {
	$dest = $("#" + dest);
	if (!$dest.size()) {
		$dest = $("#" + dest, parent.document);
	}
	var data = new FormData();
	jQuery.each($('#fichero')[0].files, function(i, file) {
		data.append('fichero', file);
	});
	check = 'CODE-'+check;
	var $origen = $("#" + idForm).closest("div");
	var $hmtlOrigen = $origen.html();
	// pausar
	$origen.empty().append('<div id="box_loading"><div><img src="img/preloader.gif"/></div></div>');
	$.ajax({
		url: url,
		data: data,
		cache: false,
		contentType: false,
		processData: false,
		type: 'POST',
		success: function(data){
			if (data.indexOf(check) >= 0) {
				$dest.empty().append(data);
				if (success != null && success.length > 0) {
					eval(success.replace(/%/g, "\""));
				}
			} else {
				alert("No se ha podido adjuntar el fichero");
				$origen.empty().append($hmtlOrigen);
			}
		}
	});
}

function downloadFile(idForm, fileType, contractType, documentType, dossierNumber) {
	$("#" + idForm).attr("action", "downloadFilefiles.action?fileType="+fileType+"&contractType="+contractType+"&documentType="+documentType+"&dossierNumber="+dossierNumber).trigger("submit");
}

/*
 * Metodo que realiza la llamada Post del formulario del que se pasa el id
 * (idForm). llamando a la url que se pasa por parametro (url). dest, opcional.
 * Si dest viene informado, se carga el contenido en el elemento que tenga por
 * id, dest. Si no viene informado, el contenido se carga en la pagina actual
 * success, opcional, función que se ejecutará de modo callback cuando el POST
 * acabe con exito. (Si hay que anidar muchos ", poner %)
 */
function doPost(idForm, url, dest, success) {
	$("#propiedadDtoCategoria").attr('disabled', false);
	var $form = $("#" + idForm);
	if (dest != null && dest.length > 0) {
		$dest = $("#" + dest);
		if (!$dest.size()) {
			$dest = $("#" + dest, parent.document);
		}
		$.post(url, $form.serialize(), function(data) {
			$dest.empty().append(data);
		}).success(function(data) {
			if (success != null && success.length > 0) {
				eval(success.replace(/%/g, "\""));
			}
		});
	} else {
		$('input[type=button]').attr('disabled', true);
		$form.attr("action", url).trigger("submit");
	}
}

/*
 * Metodo que realiza un confirm del mensaje pasado por parametro, y si el
 * usuario acepta realiza la llamada Post del formulario del que se pasa el id
 * (idForm). llamando a la url que se pasa por parametro (url). dest, opcional.
 * Si dest viene informado, se carga el contenido en el elemento que tenga por
 * id, dest. Si no viene informado, el contenido se carga en la pagina actual
 * success, opcional, función que se ejecutará de modo callback cuando el POST
 * acabe con exito. (Si hay que anidar muchos ", poner %)
 */
function doConfirmPost(message, idForm, url, dest, success) {
	if (confirm(message)) {
		doPost(idForm, url, dest, success);
	}
}

function marcarTodos(objCheck, arrayObjChecks) {
	var marcar = objCheck.checked;
  	if(!arrayObjChecks.length){ //Controlamos el caso en que solo hay un elemento...
		arrayObjChecks.checked = marcar;
  	}
    for (var i=0; i< arrayObjChecks.length; i++) {
    	arrayObjChecks[i].checked = marcar;
	}
}

/*
 * Metodo que carga la url pasa por parametro en un colorbox.
 */
function lightbox(url) {
	$.colorbox({
		iframe : true,
		width : "1040px",
		height : "550px",
		scrolling : true,
		overlayClose : true,
		escKey : true,
		href : url
	});
}

function doPostLayers(idForm, url, idCapaShow, idCapaHide, success) {
	$capaShow = $("#" + idCapaShow);
	$capaHide = $("#" + idCapaHide);
	var $form = $("#" + idForm);
	$.post(url, $form.serialize(), function(data) {
		$capaShow.empty().append(data);
		$capaHide.slideUp("fast");
		$capaHide.css("display", "block");
		$capaShow.css("display", "block");
		$capaShow.slideDown("slow");
		if (success != null && success.length > 0) {
			eval(success.replace(/%/g, "\""));
			showTooltips();
		}
		$('.transformable').jqTransform({
			imgPath : '../css/img/'
		});
	});
}

function ocultarSlide(idCapa, idCapaList, idTitulo) {
	$capa = $("#" + idCapa);
	$capaList = $("#" + idCapaList);
	$capaTitulo = $("#" + idTitulo);
	$capa.slideUp("fast");
	$capa.css("display", "block");
	$capaTitulo.css("display", "block");
	$capaList.css("display", "block");
	$capaList.slideDown("slow");
}

//CARGAR MUNICIPIOS SEGÚN PROVINCIA
function cargarMunicipiosProvincia(selectProvincia, person) {
	if (selectProvincia.selectedIndex == 0) {
		document.getElementById(person + "SelectMunicipioId").length = 0;
		document.getElementById(person + "SelectMunicipioId").options[0] = new Option("Seleccione municipio", "");
		document.getElementById(person + "HiddenMunicipioId").value = "";
		return;
	}
	idProvincia = selectProvincia.options[selectProvincia.selectedIndex].value;
	url = "cargar_Municipios.action?idProvincia=" + idProvincia + "&person="+person;
	// Hace la llamada a ajax
	if (window.XMLHttpRequest) { // Non-IE browsers
		reqMunicipios = new XMLHttpRequest();
		reqMunicipios.onreadystatechange = rellenarMunicipios;
		reqMunicipios.open("POST", url, true);
		reqMunicipios.send(null);
	} else if (window.ActiveXObject) { // IE
		reqMunicipios = new ActiveXObject("Microsoft.XMLHTTP");
		if (reqMunicipios) {
			reqMunicipios.onreadystatechange = rellenarMunicipios;
			reqMunicipios.open("POST", url, true);
			reqMunicipios.send();
		}
	}
}

//Callback function
function rellenarMunicipios() {
	var person = null;
	if (reqMunicipios.readyState == 4) { // Complete
		if (reqMunicipios.status == 200) { // OK response
			textReturn = reqMunicipios.responseText;
			arrayReturn = textReturn.split("&");
			person = arrayReturn[0];
			// Split the document
			returnElementMunicipio = arrayReturn[1].split("||");
			document.getElementById(person + "SelectMunicipioId").length = 0;
			document.getElementById(person + "SelectMunicipioId").options[0] = new Option("Seleccione municipio", "");
			// Process each of the elements
			for ( var i = 0; i < returnElementMunicipio.length; i++) {
				value = returnElementMunicipio[i].split(";");
				document.getElementById(person + "SelectMunicipioId").options[i + 1] = new Option(value[0], value[1]);
			}

			marcarMunicipioSeleccionado(person);

		}
	}
}

function seleccionMunicipio(comboMunicipio, person) {
	municipioId = comboMunicipio.options[comboMunicipio.selectedIndex].value;
	document.getElementById(person + "HiddenMunicipioId").value = municipioId;
	comboProvincia = document.getElementById(person + "SelectProvinciaId");
	provinciaId = comboProvincia.options[comboProvincia.selectedIndex].value;
	document.getElementById(person + "HiddenMunicipality").value = provinciaId;
	document.getElementById(person + "HiddenMunicipality").value += municipioId;
	return true;
}

//Recarga el combo cuando se recarga la página
function recargarComboMunicipiosRegistradores(person) {
	selectProvinciaId = document.getElementById(person + 'SelectProvinciaId');
	var indice = selectProvinciaId.selectedIndex;
	if (indice > 0) {
		cargarMunicipiosProvincia(selectProvinciaId, person);
	}
}

//Selecciona el municipio cuando se recarga la pagina
function marcarMunicipioSeleccionado(person) {
	if (person != null && person != "") {
		var codigoMunicipio = document.getElementById(person+'HiddenMunicipioId').value;
		var selectMunicipio = document.getElementById(person+'SelectMunicipioId');
		var selectProvincia = $('#'+person+'SelectProvinciaId :selected').val();
		var codigoMunicipality = document.getElementById(person+'HiddenMunicipality').value;

		if (codigoMunicipio != null && codigoMunicipio != "" && ((selectProvincia + codigoMunicipio) == codigoMunicipality)) {
			for ( var q = 0; q < selectMunicipio.length; q++) {
				var valueSelect = selectMunicipio[q].value;
				if (valueSelect.indexOf(codigoMunicipio) != -1) {
					selectMunicipio.selectedIndex = q;
					return;
				}
			}
		} else {
			document.getElementById(person + "HiddenMunicipioId").value = "";
		}
	}
}

//Importación de ficheros
function importarFichero() {
	try {
		archivo = document.getElementById("fichero").value;
		if (archivo.length > 0) {
			extension = (archivo.substring(archivo.lastIndexOf(".")))
			.toLowerCase();
			if (".xml" == extension) {
				document.formData.action = "importar_ImportarFinancingDossier.action";
				document.formData.submit();
			} else {
				alert("El formato del fichero a importar deber ser '.xml'");
			}

		} else {
			alert("Seleccione el fichero a importar");
		}
	} catch (e) {
		alert("No se ha seleccionado una ruta correcta al fichero");
	}
}

//Importación de ficheros
function importarFicheroLeasingRenting() {
	try {
		archivo = document.getElementById("fichero").value;
		if (archivo.length > 0) {
			extension = (archivo.substring(archivo.lastIndexOf(".")))
			.toLowerCase();
			if (".xml" == extension) {
				document.formData.action = "importar_ImportarLeasingRentingDossier.action";
				document.formData.submit();
			} else {
				alert("El formato del fichero a importar deber ser '.xml'");
			}

		} else {
			alert("Seleccione el fichero a importar");
		}
	} catch (e) {
		alert("No se ha seleccionado una ruta correcta al fichero");
	}
}

function mostrarErrores($form, errorsObject) {
	for ( var fieldName in errorsObject.fieldErrors) {
		for (var i = 0; i < errorsObject.fieldErrors[fieldName].length; i++) {
			if (errorsObject.fieldErrors[fieldName][i].match(/^Invalid field value for field .*/)) {
				errorsObject.fieldErrors[fieldName][i] = 'Formato incorrecto';
			}
		}
	} 
	StrutsUtils.showValidationErrors($form[0], errorsObject);
	var vissible = false;
	$("div .errorMessage").each(function (i) {
		$(this).addClass("smallWithoutColor");
		vissible |= $(this).is(":visible");
	});
	if (!vissible) {
		// Buscamos la pestaña del primer error
		$('a[href$="#' + $("div .errorMessage").first().closest("div .tab_content").attr("id") + '"]').click();
	}
	// Nos situamos en el primer error
	$('html, body').animate({
		scrollTop: $("div .errorMessage").first().offset().top
	}, 1000);
}

function aniadirFirma(accion,form, url){
	var valueBoton = $("#btnAniadirFirma" + accion).val();
	mostrarLoadingRegistro('btnAniadirFirma'+ accion);
	generarPopUpDatosFirmaRegistro(valueBoton, accion, form, url);
}

function generarPopUpDatosFirmaRegistro(valueBoton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 380,
				buttons : {
					Añadir : function() {
						var tipo = $("#datoFirmaDtoTipoIntervencion").val();
						
						var provincia = $("#datoFirmaSelectProvinciaId").val();
						var municipio = $("#datoFirmaSelectMunicipioId").val();
						var codPostal = $("#datoFirmaCodPostal").val();
						var tipoVia = $("#tipoViaId").val();
						var calle = $("#datoFirmaNombreVia").val();
						var numero = $("#datoFirmaNumero").val();
						var bloque = $("#datoFirmaBloque").val();
						var planta = $("#datoFirmaPlanta").val();
						var puerta = $("#datoFirmaPuerta").val();
						var bis = $("#datoFirmaNumeroBis").val();
						var escalera = $("#datoFirmaEscalera").val();
						var km = $("#datoFirmaKm").val();
						var portal = $("#datoFirmaPortal").val();
						
						var diaFecFirma =  $("#diaFecFirma").val();
						var mesFecFirma = $("#mesFecFirma").val();
						var anioFecFirma = $("#anioFecFirma").val();

						if(tipo == null || tipo == ""){
							return alert("El tipo de intervención es obligatorio");
						}
						
						if((provincia == null || provincia == "")
							&& (municipio == null || municipio == "")
							&& (codPostal == null || codPostal == "")
							&& (tipoVia == null || tipoVia == "")
							&& (calle == null || calle == "")
							&& (numero == null || numero == "")
							&& (bloque == null || bloque == "")
							&& (planta == null || planta == "")
							&& (puerta == null || puerta == "")
							&& (bis == null || bis == "")
							&& (escalera == null || escalera == "")
							&& (km == null || km == "")
							&& (portal == null || portal == "")){
							return alert("El lugar de la firma es obligatorio");
						}
						
						if(diaFecFirma == null || diaFecFirma == ""
							|| mesFecFirma == null || mesFecFirma == ""
								|| anioFecFirma == null || anioFecFirma == ""){
							return alert("La fecha de la firma es obligatoria");
						}
							
						doPostValidate('formData', 'aniadirDatosFirma' + accion + '.action', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirFirma'+ accion,valueBoton);
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

function aniadirImporte(accion,form, url){
	var valueBoton = $("#btnAniadirImporte" + accion).val();
	mostrarLoadingRegistro('btnAniadirImporte'+ accion);
	generarPopUpOtrosImportesRegistro(valueBoton, accion, form, url);
}

function generarPopUpOtrosImportesRegistro(valueBoton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 320,
				buttons : {
					Añadir : function() {
						
						var tipoImporte = $("#tipoOtroImporte").val();
						var importe =  $("#otroImporteDtoImporte").val();
						
						if(tipoImporte == null || tipoImporte == ""){
							return alert("El tipo de importe es obligatorio");
						}
						
						if(importe == null || importe == ""){
							return alert("El importe es obligatorio");
						}
						
						doPostValidate('formData', 'aniadirOtrosImportes' + accion + '.action', '', '');
					},
					Cerrar : function() {
						ocultarLoadingRegistro('btnAniadirImporte'+ accion,valueBoton);
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

function aniadirDeuda(accion,form, url){
	var valueBoton = $("#btnAniadirDeuda" + accion).val();
	mostrarLoadingRegistro('btnAniadirDeuda'+ accion);
	generarPopUpReconocimientoDeudaRegistro(valueBoton, accion, form, url);
}

function generarPopUpReconocimientoDeudaRegistro(valueBoton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 350,
				buttons : {
					Añadir : function() {
						
						var importeReconocido = $("#reconocimientoDeudaDtoImpReconocido").val();
						var importePlazos =  $("#reconocimientoDeudaDtoImpPlazos").val();
						var numPlazos =  $("#reconocimientoDeudaDtoNumPlazos").val();
						
						if(importeReconocido == null || importeReconocido == ""){
							return alert("El importe reconocido es obligatorio");
						}
						if(numPlazos == null || numPlazos == ""){
							return alert("El número de plazos es obligatorio");
						}
						if(importePlazos == null || importePlazos == ""){
							return alert("El importe plazos es obligatorio");
						}
						
						if(accion == "Renting" || accion == "Leasing"){
							var diaVencimiento = $("#reconocimientoDeudaDtoDiaVencimiento").val();
							
							var diaFecPrimerVencimiento =  $("#diafecPrimerVencimiento").val();
							var mesFecPrimerVencimiento = $("#mesfecPrimerVencimiento").val();
							var anioFecPrimerVencimiento = $("#aniofecPrimerVencimiento").val();

							var tiempoEntrePagos =  $("#reconocimientoDeudaDtoTiempoEntrePagos").val();
							
							if(diaVencimiento == null || diaVencimiento == ""){
								return alert("El día del mes de vencimiento es obligatorio");
							}
							
							if(diaFecPrimerVencimiento == null || diaFecPrimerVencimiento == ""
								|| mesFecPrimerVencimiento == null || mesFecPrimerVencimiento == ""
									|| anioFecPrimerVencimiento == null || anioFecPrimerVencimiento == ""){
								return alert("La fecha 1er vencimiento es obligatoria");
							}
							
							if(tiempoEntrePagos == null || tiempoEntrePagos == ""){
								return alert("El tiempo entre pagos es obligatorio");
							}
						}
						
						doPostValidate('formData', 'aniadirReconocimientoDeuda' + accion + '.action', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirDeuda'+ accion,valueBoton);
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

function aniadirCuadro(accion,form, url, boton){
	var valueBoton = $("#"+ boton).val();
	mostrarLoadingRegistro(boton);
	generarPopUpCuadroAmortizacionRegistro(valueBoton, boton, accion, form, url);

}

function generarPopUpCuadroAmortizacionRegistro(valueBoton, boton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 350
				},
				dialogClass : 'no-close',
				width: 860,
				height: 350,
				buttons : {
					Añadir : function() {
						
						var diaFechaVencimiento =  $("#diaFechaVencimiento").val();
						var mesFechaVencimiento = $("#mesFechaVencimiento").val();
						var anioFechaVencimiento = $("#anioFechaVencimiento").val();

						if(diaFechaVencimiento == null || diaFechaVencimiento == ""
							|| mesFechaVencimiento == null || mesFechaVencimiento == ""
								|| anioFechaVencimiento == null || anioFechaVencimiento == ""){
							return alert("La fecha de vencimiento es obligatoria");
						}
						
						if(accion == "Financiacion"){
							var capitalPendiente =  $("#cuadroAmortizacionDtoImpCapitalPendiente").val();
							var importePlazo =  $("#cuadroAmortizacionDtoImpPlazo").val();
							var capitalAmortizado = $("#cuadroAmortizacionDtoImpCapitalAmortizado").val();
							var interesesPlazo =  $("#cuadroAmortizacionDtoImpInteresesPlazo").val();
							var interesDevengado =  $("#cuadroAmortizacionDtoImpInteresDevengado").val();
							
							if(capitalPendiente == null || capitalPendiente == ""){
								return alert("El capital pendiente es obligatorio");
							}
							if(importePlazo == null || importePlazo == ""){
								return alert("El importe del plazo es obligatorio");
							}
							if(capitalAmortizado == null || capitalAmortizado == ""){
								return alert("El capital amortizado es obligatorio");
							}
							if(interesesPlazo == null || interesesPlazo == ""){
								return alert("Intereses plazo es obligatorio");
							}
							if(interesDevengado == null || interesDevengado == ""){
								return alert("Intereses devengados es obligatorio");
							}
						}else if(accion == "Leasing"){
							
							var tipoPlazoCuadro =  $("#tipoPlazoCuadro").val();
								
							if(tipoPlazoCuadro == "GENERIC"){
								var recuperacionCoste =  $("#cuadroAmortizacionDtoImpRecupCoste").val();
								var cargaFinanciera = $("#cuadroAmortizacionDtoImpCargaFinan").val();
								var cargaNeta =  $("#cuadroAmortizacionDtoImpCuotaNeta").val();
								var importeImpuesto =  $("#cuadroAmortizacionDtoImpImpuesto").val();
								var totalCuota =  $("#cuadroAmortizacionDtoImpTotalCuota").val();
								
								if(recuperacionCoste == null || recuperacionCoste == ""){
									return alert("El importe recuperación del coste es obligatorio");
								}

								if(cargaFinanciera == null || cargaFinanciera == ""){
									return alert("La carga financiera es obligatoria");
								}
								if(cargaNeta == null || cargaNeta == ""){
									return alert("La carga neta es obligatoria");
								}
								if(importeImpuesto == null || importeImpuesto == ""){
									return alert("El importe impuesto es obligatorio");
								}
								if(totalCuota == null || totalCuota == ""){
									return alert("El total de la cuota es obligatorio");
								}
								
							}else if(tipoPlazoCuadro == "ANTICIPATED_FINANCING" || tipoPlazoCuadro == "INSURANCE_FINANCING"){
								var impAmortizacion = $("#cuadroAmortizacionDtoImpAmortizacion").val();
								var capitalPendiente =  $("#cuadroAmortizacionDtoImpCapitalPendiente").val();
								var cargaFinanciera = $("#cuadroAmortizacionDtoImpCargaFinan").val();
								var totalCuota =  $("#cuadroAmortizacionDtoImpTotalCuota").val();
								
								if(impAmortizacion == null || impAmortizacion == ""){
									return alert("El importe de amortización es obligatorio");
								}
								if(capitalPendiente == null || capitalPendiente == ""){
									return alert("El capital pendiente es obligatorio");
								}
								if(cargaFinanciera == null || cargaFinanciera == ""){
									return alert("La carga financiera es obligatoria");
								}
								if(totalCuota == null || totalCuota == ""){
									return alert("El total de la cuota es obligatorio");
								}
								
							}
						}
						
						doPostValidate('formData', 'aniadirCuadroAmortizacion' + accion + '.action', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro(boton,valueBoton);
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

function aniadirClausula(accion,form, url){
	var valueBoton = $("#btnAniadirClausula" + accion).val();
	mostrarLoadingRegistro('btnAniadirClausula'+ accion);
	generarPopUpClausulaRegistro(valueBoton, accion, form, url);
}

function generarPopUpClausulaRegistro(valueBoton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 300,
				buttons : {
					Añadir : function() {
						

						var tipoClausula =  $("#clausulaTipoClausula").val();
						var numeroClausula =  $("#clausulaNumero").val();
						var nombreClausula = $("#clausulaNombre").val();
						var descripcionClausula =  $("#clausulaDescripcion").val();
						

						if(tipoClausula == null || tipoClausula == ""){
							return alert("El tipo de clásula es obligatorio");
						}
						if(numeroClausula == null || numeroClausula == ""){
							return alert("El número de cláusula es obligatorio");
						}
						if(nombreClausula == null || nombreClausula == ""){
							return alert("El nombre de clásula es obligatorio");
						}
						if(descripcionClausula == null || descripcionClausula == ""){
							return alert("La descripción de clásula es obligatoria");
						}
						
						doPostValidate('formData', 'aniadirClausula' + accion + '.action#tab5', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirClausula'+ accion,valueBoton);
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

function aniadirComision(accion, form, url){
	var valueBoton = $("#btnAniadirComision" + accion).val();
	mostrarLoadingRegistro('btnAniadirComision'+ accion);
	generarPopUpComisionRegistro(valueBoton, accion, form, url);
}

function generarPopUpComisionRegistro(valueBoton, accion, form, url){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo : $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 350,
				buttons : {
					Añadir : function() {
						
						var tipoComision =  $("#comisionTipoComision").val();
						var porcentajeComision =  $("#comisionPorcentaje").val();
						var importeMinimo = $("#commisionImporteMinimo").val();
						var importeMaximo =  $("#commisionImporteMaximo").val();
						

						if(tipoComision == null || tipoComision == ""){
							return alert("El tipo de comisión es obligatorio");
						}
						if(porcentajeComision == null || porcentajeComision == ""){
							return alert("El porcentaje de la comisión es obligatorio");
						}
						if(importeMinimo == null || importeMinimo == ""){
							return alert("El importe mínimo de la comisión es obligatorio");
						}
						if(importeMaximo == null || importeMaximo == ""){
							return alert("El importe máximo de la comisión es obligatorio");
						}
						
						doPostValidate('formData', 'aniadirComision' + accion + '.action', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirComision'+ accion,valueBoton);
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


function aniadirMotor(form,identificador){	
	
	var accion = calcularContrato();
	var valueBoton = $("#btnAniadirMotor").val();
	mostrarLoadingRegistro('btnAniadirMotor');
	generarPopUpMotorRegistro(valueBoton,accion,form,identificador);
}

function generarPopUpMotorRegistro(valueBoton, accion, form, identificador){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#" + form);
	$("#propiedadDtoCategoria").attr('disabled', false);
	$.post("cargarPopUpMotorRegistro" + accion + ".action" + identificador, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 300,
				buttons : {
					Añadir : function() {
						
						var tipoMotor =  $("#motorTipo").val();
						var marcaMotor =  $("#motorMarca").val();
						var modeloMotor = $("#motorModelo").val();
						var anioConstruccion = $("#motorAnioConstruccion").val();
						
						

						if(tipoMotor == null || tipoMotor == ""){
							return alert("El tipo de motor es obligatorio");
						}
						if(marcaMotor == null || marcaMotor == ""){
							return alert("La marca del motor es obligatoria");
						}
						if(modeloMotor == null || modeloMotor == ""){
							return alert("El modelo del motor es obligatorio");
						}	
						
						if(anioConstruccion.length != 4){
							return alert("El año de construcción del motor no tiene el formato correcto");
						}
						
						doPostValidate('formData', 'aniadirMotor' + accion + '.action#tab6', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirMotor',valueBoton);
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

function borrarMotor(form, identificador){
	var accion = calcularContrato();
	doConfirmPost('¿Está seguro de que desea borrar este motor?', form, "borrarMotor" + accion + ".action" + identificador + "\u0023tab6")
}


function aniadeRepresentanteFinanciador(form,url){
	var valueBoton = $("#btnAniadirRepresentanteFinanciador").val();
	mostrarLoadingRegistro('btnAniadirRepresentanteFinanciador');
	generarPopUpRepresentanteFinanciadorRegistro(valueBoton,form,url);
}


function generarPopUpRepresentanteFinanciadorRegistro(valueBoton,form,url){
	var $dest = $("#divEmergentePopUpFinanciador");
	var $idForm = $("#" + form);
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 600,
				buttons : {
					Añadir : function() {
						
						var tipoPersona = $("#representantCorpmePersonType").val();
						var nif =  $("#representanteNif").val();
						var nombre = $("#representanteNombre").val();
						var apellido1RazonSocial = $("#representante1Apellido").val();
						var cargo = $("#representanteCargo").val();
						var pais = $("#representantCorpmeCountryId").val();
						var region = $("#representanteRegion").val();
						var provincia = $("#representanteFinanciadoresSelectProvinciaId").val();
						var municipio = $("#representanteFinanciadoresSelectMunicipioId").val();
						var codPostal = $("#representanteCodPostal").val();
						var tipoVia = $("#representantAddressCorpmeStreetTypeId").val();
						var nombreVia = $("#representanteNombreVia").val();
						var numVia = $("#representanteNumero").val();

						var tipoPersonaNotario = $("#representanteTipoPersonaNotario").val();
						var nifNotario =  $("#representanteNotarioNif").val();
						var nombreNotario = $("#representanteNotarioNombre").val();
						var apellido1Notario = $("#representanteNotarioApellido1").val();
						var apellido2Notario = $("#representanteNotarioApellido2").val();
						var provinciaNotario = $("#representanteAvalistaNotarioSelectProvinciaId").val();
						var municipioNotario = $("#representanteAvalistaNotarioSelectMunicipioId").val();
						var diaFecOtorgamiento = $("#diaFecOtorgamiento").val();
						var mesFecOtorgamiento = $("#mesFecOtorgamiento").val();
						var anioFecOtorgamiento = $("#anioFecOtorgamiento").val();
						var numProtocolo = $("#representanteNumProtocoloNotario").val();
						
						if(tipoPersona == null || tipoPersona == ""){
							return alert("El tipo de persona del representante es obligatorio");
						}else if(nif == null || nif == ""){
							return alert("El DNI / NIE / NIF del representante es obligatorio");
						}else if(!valida_nif_cif_nie("#representanteNif"))	{
							return alert("El DNI / NIE / NIF del representante no tiene el formato correcto");
						
						}else if(tipoPersona != ""
							&& ("J" == tipoPersona || "P"== tipoPersona) 
							&&( apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona el representante como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
						}else if(tipoPersona != ""
							&& ("F" == tipoPersona || "E"== tipoPersona) 
							&&(nombre == null || nombre == "" || apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona el representante como persona Física o Extranjero debe rellenar nombre y dos apellidos");
						}else if(cargo == null || cargo == ""){
							return alert("El cargo del representante es obligatorio");
						}else if(pais == null || pais == ""){
							return alert("El país de la dirección es obligatorio");
						}else if(pais != "" && pais != "ES" && (region == null || region == "")){
							return alert("Si el país de la dirección es distinta de España la región es obligatoria");
						}else if(pais != "" && pais == "ES" && (provincia == null || provincia == "")){
							return alert("Si el país de la dirección es España la provincia es obligatoria");
						}else if(pais != "" && pais == "ES" && (municipio == null || municipio == "")){
							return alert("Si el país de la dirección es España el municipio es obligatorio");
						}else if(codPostal == null || codPostal == "" || codPostal.length != 5){
							return alert("Código postal de la dirección es obligatorio");
						}else if(pais != "" && pais == "ES" && (tipoVia == null || tipoVia == "")){
							return alert("Si el país de la dirección es España el tipo de vía es obligatorio");
						}else if(nombreVia == null || nombreVia == ""){
							return alert("Nombre calle de la dirección es obligatorio");
						}else if(numVia == null || numVia == ""){
							return alert("Número calle de la dirección es obligatorio");
						}
						
						if((tipoPersonaNotario != null && tipoPersonaNotario != "")
							|| (nifNotario != null && nifNotario != "")
							|| (nombreNotario != null && nombreNotario != "")
							|| (apellido1Notario != null && apellido1Notario !=  "")
							|| (apellido2Notario !=  null && apellido2Notario !=  "")
							|| (provinciaNotario !=  null && provinciaNotario !=  "")
							|| (municipioNotario !=  null && municipioNotario !=  "")
							|| (diaFecOtorgamiento !=  null && diaFecOtorgamiento !=  "")
							|| (mesFecOtorgamiento !=  null && mesFecOtorgamiento !=  "")
							|| (anioFecOtorgamiento !=  null && anioFecOtorgamiento !=  "")
							|| (numProtocolo !=  null && numProtocolo !=  "")){
							
							if(tipoPersonaNotario == null || tipoPersonaNotario == ""){
								return alert("El tipo de persona del notario es obligatorio");
							}else if(nifNotario == null || nifNotario == ""){
								return alert("El DNI / NIE / NIF del notario es obligatorio");
							}else if(!valida_nif_cif_nie("#representanteNotarioNif"))	{
								return alert("El DNI / NIE / NIF del notario no tiene el formato correcto");
							
							}else if(tipoPersonaNotario != ""
								&& ("J" == tipoPersonaNotario || "P"== tipoPersonaNotario) 
								&&( apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
							}else if(tipoPersonaNotario != ""
								&& ("F" == tipoPersonaNotario || "E"== tipoPersonaNotario) 
								&&(nombreNotario == null || nombreNotario == "" || apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Física o Extranjero debe rellenar nombre y dos apellidos");
							}
						}
						
						
						
						doPostValidate('formDG', 'aniadirRepresentanteFinanciadores.action', '', '');
						ocultarLoadingRegistro('btnAniadirRepresentanteFinanciador',valueBoton);
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirRepresentanteFinanciador',valueBoton);
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

//Para mostrar el loading en los botones de consultar.
function mostrarLoadingRegistro(boton) {
	document.getElementById("bloqueLoadingRegistro").style.display = "block";
	$('#'+boton).val("Procesando..");
	$('input[type=button]').attr('disabled', true);
}

function ocultarLoadingRegistro(boton, value) {
	document.getElementById("bloqueLoadingRegistro").style.display = "none";
	$('#'+boton).val(value);
	$('input[type=button]').attr('disabled', false);
}

function buscarFinanciadores(){
	var nif = $('#financiadorNif').val();
	
	if(nif == null || nif == ""){
		return alert("El DNI / NIE / NIF debe ir relleno");
	}else if(!valida_nif_cif_nie("#financiadorNif"))	{
		return alert("El DNI / NIE / NIF no es válido");
	}

	var $dest = $("#divContenidoFinanciador", parent.document);
	$.ajax({
		url:"buscarPersonaFinanciadoresAjax.action",
		data:"financiadorNif="+ nif + "&tipoInterviniente=021" + "&idContrato=" + $("#idContrato").val() +  "&idRemesa=" + $("#idRemesa").val(),
		type:'POST',
		success: function(data, xhr, status){
			$dest.empty().append(data);
		},
		error : function(xhr, status) {
			alert('Ha sucedido un error a la hora de cargar el financiador.');
		}
	});
}

function buscarRepresentanteFinanciadores(identificador){
	var nif = $('#representanteNif').val();
	var $form = $("#formDG");
	
	if(nif == null || nif == ""){
		return alert("El DNI / NIE / NIF debe ir relleno");
	}else if(!valida_nif_cif_nie("#representanteNif"))	{
		return alert("El DNI / NIE / NIF no es válido");
	}
	var $dest = $("#divEmergentePopUpFinanciador");
	$.post("buscarPersonaRepresentanteFinanciadoresAjax.action" + "?representanteNif="+ nif +"&identificador="+ identificador, $form.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo: $form,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 600,
				buttons : {
					Añadir : function() {
						var tipoPersona = $("#representantCorpmePersonType").val();
						
						var nombreInterv = $("#representanteNombre").val();
						var apellido1RazonSocial = $("#representante1Apellido").val();
						var cargo = $("#representanteCargo").val();
						var pais = $("#representantCorpmeCountryId").val();
						var region = $("#representanteRegion").val();
						var provincia = $("#representanteFinanciadoresSelectProvinciaId").val();
						var municipio = $("#representanteFinanciadoresSelectMunicipioId").val();
						var codPostal = $("#representanteCodPostal").val();
						var tipoVia = $("#representantAddressCorpmeStreetTypeId").val();
						var nombreVia = $("#representanteNombreVia").val();
						var numVia = $("#representanteNumero").val();
						
						var tipoPersonaNotario = $("#representanteTipoPersonaNotario").val();
						var nifNotario =  $("#representanteNotarioNif").val();
						var nombreNotario = $("#representanteNotarioNombre").val();
						var apellido1Notario = $("#representanteNotarioApellido1").val();
						var apellido2Notario = $("#representanteNotarioApellido2").val();
						var provinciaNotario = $("#representanteFinanciadoresNotarioSelectProvinciaId").val();
						var municipioNotario = $("#representanteFinanciadoresNotarioSelectMunicipioId").val();
						var diaFecOtorgamiento = $("#diaFecOtorgamiento").val();
						var mesFecOtorgamiento = $("#mesFecOtorgamiento").val();
						var anioFecOtorgamiento = $("#anioFecOtorgamiento").val();
						var numProtocolo = $("#representanteNumProtocoloNotario").val();

						if(tipoPersona == null || tipoPersona == ""){
							return alert("El tipo de persona es obligatorio");
						}else if(tipoPersona != ""
							&& ("J" == tipoPersona || "P"== tipoPersona) 
							&&( apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
						}else if(tipoPersona != ""
							&& ("F" == tipoPersona || "E"== tipoPersona) 
							&&(nombreInterv == null || nombreInterv == "" || apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona persona Física o Extranjero debe rellenar nombre y apellidos");
						}else if(cargo == null || cargo == ""){
							return alert("El cargo del representante es obligatorio");
						}else if(pais == null || pais == ""){
							return alert("El país de la dirección es obligatorio");
						}else if(pais != "" && pais != "ES" && (region == null || region == "")){
							return alert("Si el país de la dirección es distinta de España la región es obligatoria");
						}else if(pais != "" && pais == "ES" && (provincia == null || provincia == "")){
							return alert("Si el país de la dirección es España la provincia es obligatoria");
						}else if(pais != "" && pais == "ES" && (municipio == null || municipio == "")){
							return alert("Si el país de la dirección es España el municipio es obligatorio");
						}else if(codPostal == null || codPostal == "" || codPostal.length != 5){
							return alert("Código postal de la dirección es obligatorio");
						}else if(pais != "" && pais == "ES" && (tipoVia == null || tipoVia == "")){
							return alert("Si el país de la dirección es España el tipo de vía es obligatorio");
						}else if(nombreVia == null || nombreVia == ""){
							return alert("Nombre calle de la dirección es obligatorio");
						}else if(numVia == null || numVia == ""){
							return alert("Número calle de la dirección es obligatorio");
						}
						
						if((tipoPersonaNotario != null && tipoPersonaNotario != "")
							|| (nifNotario != null && nifNotario != "")
							|| (nombreNotario != null && nombreNotario != "")
							|| (apellido1Notario != null && apellido1Notario !=  "")
							|| (apellido2Notario !=  null && apellido2Notario !=  "")
							|| (provinciaNotario !=  null && provinciaNotario !=  "")
							|| (municipioNotario !=  null && municipioNotario !=  "")
							|| (diaFecOtorgamiento !=  null && diaFecOtorgamiento !=  "")
							|| (mesFecOtorgamiento !=  null && mesFecOtorgamiento !=  "")
							|| (anioFecOtorgamiento !=  null && anioFecOtorgamiento !=  "")
							|| (numProtocolo !=  null && numProtocolo !=  "")){
							
							if(tipoPersonaNotario == null || tipoPersonaNotario == ""){
								return alert("El tipo de persona del notario es obligatorio");
							}else if(nifNotario == null || nifNotario == ""){
								return alert("El DNI / NIE / NIF del notario es obligatorio");
							}else if(!valida_nif_cif_nie("#representanteNotarioNif"))	{
								return alert("El DNI / NIE / NIF del notario no tiene el formato correcto");
							
							}else if(tipoPersonaNotario != ""
								&& ("J" == tipoPersonaNotario || "P"== tipoPersonaNotario) 
								&&( apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
							}else if(tipoPersonaNotario != ""
								&& ("F" == tipoPersonaNotario || "E"== tipoPersonaNotario) 
								&&(nombreNotario == null || nombreNotario == "" || apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Física o Extranjero debe rellenar nombre y apellidos");
							}
						}
						
						doPostValidate('formDG', 'aniadirRepresentanteFinanciadores.action', '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						$('input[type=button]').attr('disabled', true);
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

function buscarInterviniente(idNif, tipoInterviniente){
	$("#propiedadDtoCategoria").attr('disabled', false);
	var nif = $('#' + idNif).val();

	if(nif == null || nif == ""){
		return alert("El DNI / NIE / NIF debe ir relleno");
	}else if(!valida_nif_cif_nie('#' + idNif))	{
		return alert("El DNI / NIE / NIF no es válido");
	}

	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "buscarInterviniente" + accion + ".action" + "?nifInterviniente="+ nif + "&tipoInterviniente=" + tipoInterviniente + '#' + pestania;

	$('input[type=button]').attr('disabled', true);
	
	$form.attr("action", url).trigger("submit");

}

function buscarIntervinienteEscritura(idNif, tipoInterviniente) {
	var nif = $('#' + idNif).val();

	if(nif == null || nif == ""){
		return alert("El NIF debe ir relleno");
	}else if(!valida_nif_cif_nie('#' + idNif))	{
		return alert("El NIF no es válido");
	}
	
	//Reiniciamos los campos
	limpiarDiv(tipoInterviniente);

	$("#nifDes").val(nif);

	var url="buscarIntervinienteRegistradoresAjax.action?nifInterviniente="+ nif;
	$.getJSON(url, function(data) {

		if("" == data || null == data){
			alert("Interviniente no encontrado");
		}else {

			$("#" + tipoInterviniente + "TipoPersona").val(data.tipoPersona);

			if(null != data.persona && "" != data.persona){
				$("#nombreDestinatario").val(data.persona.nombre);
				$("#apellido1RazonSocDestinatario").val(data.persona.apellido1RazonSocial);
				$("#apellido2Destinatario").val(data.persona.apellido2);
				$("#telefonosDestinatario").val(data.persona.telefonos);
			}
			if(null != data.direccion && "" != data.direccion){
				$("#idProvinciaDestinatario").val(data.direccion.idProvincia);
				buscarMunicipiosProvincia(data.direccion.idProvincia, "idMunicipioDestinatario", data.direccion.idMunicipio)
				$("#municipioSeleccionadoDestinatario").val(data.direccion.idMunicipio);
				cargarListaTipoVia(document.getElementById("idProvinciaDestinatario"),'tipoViaDestinatario','tipoViaSeleccionadaDestinatario');
				$("#tipoViaSeleccionadaDestinatario").val(data.direccion.idTipoVia);
				$("#tipoViaDestinatario").val(data.direccion.idTipoVia);
				$("#nombreViaDestinatario").val(data.direccion.nombreVia);
				$("#numeroDestinatario").val(data.direccion.numero);
				$("#escaleraDestinatario").val(data.direccion.escalera);
				$("#plantaDestinatario").val(data.direccion.planta);
				$("#puertaDestinatario").val(data.direccion.puerta);
				$("#codPostalDestinatario").val(data.direccion.codPostal);
			}
		}
	});
}


function buscarIntervinienteAjax(idNif,tipoInterviniente){
	var nif = $('#' + idNif).val();

	if(nif == null || nif == ""){
		return alert("El NIF debe ir relleno");
	}else if(!valida_nif_cif_nie('#' + idNif))	{
		return alert("El NIF no es válido");
	}
	
	//Reiniciamos los campos
	limpiarDiv(tipoInterviniente);

	$("#" + tipoInterviniente + "Nif").val(nif);

	var url="buscarIntervinienteRegistradoresAjax.action?nifInterviniente="+ nif;
	$.getJSON(url, function(data) {

		if("" == data || null == data){
			alert("Interviniente no encontrado");
		}else {

			$("#" + tipoInterviniente + "TipoPersona").val(data.tipoPersona);

			if(null != data.persona && "" != data.persona){
				$("#" + tipoInterviniente + "Nombre").val(data.persona.nombre);
				$("#" + tipoInterviniente + "Apellido1RazonSocial").val(data.persona.apellido1RazonSocial);
				$("#" + tipoInterviniente + "Apellido2").val(data.persona.apellido2);
				$("#" + tipoInterviniente + "CorreoElectronico").val(data.persona.correoElectronico);
				$("#" + tipoInterviniente + "Telefonos").val(data.persona.telefonos);
			}
			if(null != data.direccion && "" != data.direccion){
				$("#" + tipoInterviniente + "Pais").val(data.direccion.pais);
				$("#" + tipoInterviniente + "Pueblo").val(data.direccion.regionExtranjera);
				$("#" + tipoInterviniente + "SelectProvinciaId").val(data.direccion.idProvincia);
				
				buscarMunicipiosProvincia(data.direccion.idProvincia, tipoInterviniente + "SelectMunicipioId", data.direccion.idMunicipio)

				$("#" + tipoInterviniente + "NumLocal").val(data.direccion.pueblo);
				$("#" + tipoInterviniente + "CodPostal").val(data.direccion.codPostal);
				$("#" + tipoInterviniente + "TipoVia").val(data.direccion.idTipoVia);
				$("#" + tipoInterviniente + "NombreVia").val(data.direccion.nombreVia);
				$("#" + tipoInterviniente + "Numero").val(data.direccion.numero);
				$("#" + tipoInterviniente + "NumeroBis").val(data.direccion.numeroBis);
				$("#" + tipoInterviniente + "Bloque").val(data.direccion.bloque);
				$("#" + tipoInterviniente + "Planta").val(data.direccion.planta);
				$("#" + tipoInterviniente + "Puerta").val(data.direccion.puerta);
				$("#" + tipoInterviniente + "Escalera").val(data.direccion.escalera);
				$("#" + tipoInterviniente + "Portal").val(data.direccion.portal);
				$("#" + tipoInterviniente + "Km").val(data.direccion.km);
				$("#" + tipoInterviniente + "LugarUbicacion").val(data.direccion.lugarUbicacion);
			}
		}
	});
}

function buscarMunicipiosProvincia(codProvincia, selectMunicipio, municipioSeleccionado){
	
	var url="buscarMunicipiosProvinciaRegistradoresAjax.action?codProvincia=" + codProvincia;
	$.getJSON(url, function(data) {

		if( null != data && "" != data && data.length >0 && null != document.getElementById(selectMunicipio)){
			document.getElementById(selectMunicipio).options[0] = new Option("Seleccione Municipio", "-1");
			for ( var i = 0 ; i < data.length ; i ++ ) {
				document.getElementById(selectMunicipio).options[i+1] = new Option(data[i].nombre, data[i].idMunicipio);
				if(document.getElementById(selectMunicipio).options[i+1].value == municipioSeleccionado){
					document.getElementById(selectMunicipio).options[i+1].selected = true;
				}
			}	
		}
	});
}


function aniadirRepresentanteRBM(url,tipoInterviniente){
	var accion = calcularContrato();
	var valueBoton = $("#btnAniadirRepresentante" + tipoInterviniente + accion).val();
	mostrarLoadingRegistro('btnAniadirRepresentante' + tipoInterviniente + accion);
	generarPopUpRepresentanteRBM(valueBoton,accion,url,tipoInterviniente);
}

function generarPopUpRepresentanteRBM(valueBoton, accion,url,tipoInterviniente){
	var $dest = $("#divEmergentePopUp" + accion);
	var $idForm = $("#formData");
	var pestania = obtenerPestaniaSeleccionada();
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-80', at: 'right' },
				overflow: 'hidden',
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 860,
				height: 600,

				buttons : {
					Añadir : function() {
						var tipoPersona = $("#representante" + tipoInterviniente + "TipoPersona").val();
						var nif =  $("#representante" + tipoInterviniente + "Nif").val();
						var nombreInterv = $("#representante" + tipoInterviniente + "Nombre").val();
						var apellido1RazonSocial = $("#representante" + tipoInterviniente + "Apellido1RazonSocial").val();
						var cargo = $("#representante" + tipoInterviniente + "Cargo").val();
						var pais = $("#representante" + tipoInterviniente + "Pais").val();
						var region = $("#representante" + tipoInterviniente + "Region").val();
						var provincia = $("#representante" + tipoInterviniente + "SelectProvinciaId").val();
						var municipio = $("#representante" + tipoInterviniente + "SelectMunicipioId").val();
						var codPostal = $("#representante" + tipoInterviniente + "CodPostal").val();
						var tipoVia = $("#representante" + tipoInterviniente + "TipoVia").val();
						var nombreVia = $("#representante" + tipoInterviniente + "NombreVia").val();
						var numVia = $("#representante" + tipoInterviniente + "Numero").val();
						
						var tipoPersonaNotario = $("#representante" + tipoInterviniente + "TipoPersonaNotario").val();
						var nifNotario =  $("#representante" + tipoInterviniente + "NotarioNif").val();
						var nombreNotario = $("#representante" + tipoInterviniente + "NotarioNombre").val();
						var apellido1Notario = $("#representante" + tipoInterviniente + "NotarioApellido1").val();
						var apellido2Notario = $("#representante" + tipoInterviniente + "NotarioApellido2").val();
						var provinciaNotario = $("#representante" + tipoInterviniente + "NotarioSelectProvinciaId").val();
						var municipioNotario = $("#representante" + tipoInterviniente + "NotarioSelectMunicipioId").val();
						var diaFecOtorgamiento = $("#diaFecOtorgamiento").val();
						var mesFecOtorgamiento = $("#mesFecOtorgamiento").val();
						var anioFecOtorgamiento = $("#anioFecOtorgamiento").val();
						var numProtocolo = $("#representante" + tipoInterviniente + "NumProtocoloNotario").val();
						
						if(accion == "Cancelacion"){
							var plazaNotario = $("#representante" + tipoInterviniente + "PlazaNotario").val();
							var registroMercantil = $("#representante" + tipoInterviniente + "RegistoMercantil").val();
							var tomo = $("#representante" + tipoInterviniente + "Tomo").val();
							var folio = $("#representante" + tipoInterviniente + "Folio").val();
							var numInscripcion = $("#representante" + tipoInterviniente + "NumInscripcion").val();
						}

						if(tipoPersona == null || tipoPersona == ""){
							return alert("El tipo de persona es obligatorio");
						}else if(nif == null || nif == ""){
							return alert("El DNI / NIE / NIF es obligatorio");
						}else if(!valida_nif_cif_nie("#representante" + tipoInterviniente + "Nif"))	{
							return alert("El DNI / NIE / NIF no tiene el formato correcto");
						
						}else if(tipoPersona != ""
							&& ("J" == tipoPersona || "P"== tipoPersona) 
							&&( apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
						}else if(tipoPersona != ""
							&& ("F" == tipoPersona || "E"== tipoPersona) 
							&&(nombreInterv == null || nombreInterv == "" || apellido1RazonSocial == null || apellido1RazonSocial == "")){
								return alert("Si selecciona persona Física o Extranjero debe rellenar nombre y dos apellidos");
						}
						
						if(accion == "Cancelacion"){

							if(tipoPersonaNotario == null || tipoPersonaNotario == ""){
								return alert("El tipo de persona del notario es obligatorio");
							}else if(nifNotario == null || nifNotario == ""){
								return alert("El DNI / NIE / NIF del notario es obligatorio");
							}else if(!valida_nif_cif_nie("#representante" + tipoInterviniente + "NotarioNif"))	{
								return alert("El DNI / NIE / NIF del notario no tiene el formato correcto");

							}else if(tipoPersonaNotario != ""
								&& ("J" == tipoPersonaNotario || "P"== tipoPersonaNotario) 
								&&( apellido1Notario == null || apellido1Notario == "")){
								return alert("Si selecciona el notario como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
							}else if(tipoPersonaNotario != ""
								&& ("F" == tipoPersonaNotario || "E"== tipoPersonaNotario) 
								&&(nombreNotario == null || nombreNotario == "" || apellido1Notario == null || apellido1Notario == "")){
								return alert("Si selecciona el notario como persona Física o Extranjero debe rellenar nombre y dos apellidos");
							}else if(plazaNotario == null || plazaNotario == ""){
								return alert("La plaza del notario es obligatoria");
							}	else if(diaFecOtorgamiento == null || diaFecOtorgamiento == ""
								|| mesFecOtorgamiento == null || mesFecOtorgamiento == ""
									|| anioFecOtorgamiento == null || anioFecOtorgamiento == ""){
								return alert("La fecha de otorgamiento del notario es obligatoria");
							}else if(numProtocolo == null || numProtocolo == ""){
								return alert("El número de protocolo del notario es obligatorio");
							}else if(registroMercantil == null || registroMercantil == ""){
								return alert("El registro mercantil es obligatorio");
							}else if(tomo == null || tomo == ""){
								return alert("El tomo del registro mercantil es obligatorio");
							}else if(folio == null || folio == ""){
								return alert("El folio del registro mercantil es obligatorio");
							}else if(numInscripcion == null || numInscripcion == ""){
								return alert("El número de inscripción del registro mercantil es obligatorio");
							}

						}else{
							if(cargo == null || cargo == ""){
								return alert("El cargo del representante es obligatorio");
							}else if(pais == null || pais == ""){
								return alert("El país de la dirección es obligatorio");
							}else if(pais != "" && pais != "ES" && (region == null || region == "")){
								return alert("Si el país de la dirección es distinta de España la región es obligatoria");
							}else if(pais != "" && pais == "ES" && (provincia == null || provincia == "")){
								return alert("Si el país de la dirección es España la provincia es obligatoria");
							}else if(pais != "" && pais == "ES" && (municipio == null || municipio == "")){
								return alert("Si el país de la dirección es España el municipio es obligatorio");
							}else if(codPostal == null || codPostal == "" || codPostal.length != 5){
								return alert("Código postal de la dirección es obligatorio");
							}else if(pais != "" && pais == "ES" && (tipoVia == null || tipoVia == "")){
								return alert("Si el país de la dirección es España el tipo de vía es obligatorio");
							}else if(nombreVia == null || nombreVia == ""){
								return alert("Nombre calle de la dirección es obligatorio");
							}else if(numVia == null || numVia == ""){
								return alert("Número calle de la dirección es obligatorio");
							}

							if((tipoPersonaNotario != null && tipoPersonaNotario != "")
									|| (nifNotario != null && nifNotario != "")
									|| (nombreNotario != null && nombreNotario != "")
									|| (apellido1Notario != null && apellido1Notario !=  "")
									|| (apellido2Notario !=  null && apellido2Notario !=  "")
									|| (provinciaNotario !=  null && provinciaNotario !=  "")
									|| (municipioNotario !=  null && municipioNotario !=  "")
									|| (diaFecOtorgamiento !=  null && diaFecOtorgamiento !=  "")
									|| (mesFecOtorgamiento !=  null && mesFecOtorgamiento !=  "")
									|| (anioFecOtorgamiento !=  null && anioFecOtorgamiento !=  "")
									|| (numProtocolo !=  null && numProtocolo !=  "")){

								if(tipoPersonaNotario == null || tipoPersonaNotario == ""){
									return alert("El tipo de persona del notario es obligatorio");
								}else if(nifNotario == null || nifNotario == ""){
									return alert("El DNI / NIE / NIF del notario es obligatorio");
								}else if(!valida_nif_cif_nie("#representante" + tipoInterviniente + "NotarioNif"))	{
									return alert("El DNI / NIE / NIF del notario no tiene el formato correcto");

								}else if(tipoPersonaNotario != ""
									&& ("J" == tipoPersonaNotario || "P"== tipoPersonaNotario) 
									&&( apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Jurídica o Compañía pública debe rellenar el primer apellido (Razón Social)");
								}else if(tipoPersonaNotario != ""
									&& ("F" == tipoPersonaNotario || "E"== tipoPersonaNotario) 
									&&(nombreNotario == null || nombreNotario == "" || apellido1Notario == null || apellido1Notario == "")){
									return alert("Si selecciona el notario como persona Física o Extranjero debe rellenar nombre y dos apellidos");
								}
							}
						}
						
						doPostValidate('formData', 'aniadirRepresentante' + tipoInterviniente + accion + '.action#' + pestania, '', '');
					},
					Cerrar : function() {
						$(this).dialog("close");
						ocultarLoadingRegistro('btnAniadirRepresentante' + tipoInterviniente + accion,valueBoton);
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


/**
 * Borra el contenido de los inputs de tipo texto contenidos en el div con id pasado por parametro.
 * Tambien limpia los combos (asegurate de que los combos tienen un ID unico)
 * @param idDiv
 * 			Identificador de la capa que contiene los campos a borrar
 */
function limpiarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr("value", "");
	$("#" + idDiv + " input[type=hidden]").attr("value", "");
	$("#" + idDiv + " select").each(function () {
		$("#" + this.id + " option:first").attr('selected','selected');
	});
}

function deshabilitarDiv(idDiv) {
	$("#" + idDiv + " input[type=text]").attr('disabled', true);
	$("#" + idDiv + " input[type=hidden]").attr('disabled', true);
	$("#" + idDiv + " select").attr('disabled',true);
	$("#" + idDiv + " input[type=button]").attr('disabled',true);
	$("#" + idDiv + " input[type=checkbox]").attr('disabled',true);
	
}

function habilitarDiv(idDiv){
	$("#" + idDiv + " input[type=text]").removeAttr('disabled');
	$("#" + idDiv + " input[type=hidden]").removeAttr('disabled');
	$("#" + idDiv + " select").removeAttr('disabled');
	$("#" + idDiv + " input[type=button]").removeAttr('disabled');
	$("#" + idDiv + " input[type=checkbox]").removeAttr('disabled');
}


function construirDprLista(){
	if (numChecked() == 0) {
		alert("Debe seleccionar algun tr\u00E1mite");
		return false;
	}
	iniciarProcesando('btnEnviarDpr','loadingImage');
	
	document.formData.action = "construirDprListaRecuperarTramite.action";
	document.formData.submit();
}


function calcularContrato(){

	var tipoContrato = $("#tipoTramiteRegistro").val();	
	var accion = '';
	
	if(tipoContrato == 'R1'){
		accion = 'TramiteRegistroMd1';
	}else if(tipoContrato == 'R2'){
		accion = 'TramiteRegistroMd2';	
	}else if(tipoContrato == 'R3'){
		accion = 'TramiteRegistroMd3';	
	}else if(tipoContrato == 'R4'){
		accion = 'TramiteRegistroMd4';
	}else if(tipoContrato == 'R5'){
		accion = 'TramiteRegistroMd5';	
	}else if(tipoContrato == 'R6'){
		accion = 'TramiteRegistroMd6';	
	}else if(tipoContrato == 'R7'){
		accion = 'Financiacion';	
	}else if(tipoContrato == 'R8'){
		accion = 'Leasing';
	}else if(tipoContrato == 'R9'){
		accion = 'Renting';
	}else if(tipoContrato == 'R10'){
		accion = 'Cancelacion';
	}else if(tipoContrato == 'R11'){
		accion = 'Cuenta';
	}else if(tipoContrato == 'R12'){
		accion = 'Libro';
	}else if(tipoContrato == 'R13'){
		accion = 'Desistimiento';
	}
	
	return accion;

}

//------------------------Validaciones--------------------------------------

function validaInputFecha(diaFech,mesFech,anyoFech) {

	var bisiesto;
	var days = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	var dia = parseInt(diaFech.value,10);
	var mes = parseInt(mesFech.value,10);
	var ano = parseInt(anyoFech.value,10);

	if (isNaN(dia)) {
		alert(ERROR_FECHA_NAN_DIA);
		diaFech.focus();
		return false;
	}

	if (isNaN(mes)){
		alert(ERROR_FECHA_NAN_MES);
		mesFech.focus();
		return false;
	}

	if (isNaN(ano)) {
		alert(ERROR_FECHA_NAN_ANYO);
		anyoFech.focus();
		return false;
	}

	if (anyoFech.value.length < 4) {
		alert(ERROR_FECHA_TAM_ANYO);
		anyoFech.focus();
		return false;
	}

	var limite=0 ;
	var indice;
	var fecha1 = new Date();
	var actual = Date.parse(fecha1);

	if (mes>12 || mes<1) {
		alert(ERROR_FECHA_MES_LIMITE);
		mesFech.focus();
		return false;
	}

	indice = mes--;

	(ano % 4 == 0) ? ((ano %100 ==0) ? ((ano % 400 ==0) ? bisiesto = true : bisiesto = false) : bisiesto = true) : bisiesto = false;

	if((bisiesto== true) && (indice ==2)) {
		limite = 29;
	}
	else {
		limite = days[mes];
	}

	if(dia<0 || dia>limite) { alert (ERROR_FECHA_DIA_LIMITE); diaFech.focus();return false;}
}


function validaNumero(valor, campo){
	var valor = valor.value;
	if(null != valor && valor!=""){
		if(!isNaN(valor)){
			return true;
		}
		alert("El campo " + campo + " s\u00f3lo admite caracteres num\u00e9ricos.\nPor favor repase los datos.");
		return false
	}
}

//Funcion que permite introducir hasta 10 números enteros y dos decimales
function soloNumeroDecimal(e, campo, enteros, decimales) {
	key = e.keyCode ? e.keyCode : e.which
			// backspace
			if (key == 8) return true
			
			// Tabulador
			if (key == 9) return true
			
			//Flecha derecha
			if (key == 39) return true
			
			//Flecha derecha
			if (key == 37) return true

			campo.value = campo.value.replace(',','.');

	// 0-9 a partir del .decimal  
	if (campo.value != "") {
		if ((campo.value.indexOf(".")) > 0) {

			//si tiene un punto valida dos digitos en la parte decimal
			if (key > 47 && key < 58) {
				if (campo.value == "") return true
				
				var re = new RegExp("[0-9]{" + decimales + "}$");
				return !(re.test(campo.value))
			}
		}
	}

	// 0-9 
	if (key > 47 && key < 58) {
		if (campo.value == "") return true
		var reg = new RegExp("[0-9]{"+ enteros + "}");
			return !(reg.test(campo.value))
	}

	//,
	if (key == 44) {
		if (campo.value == "") return false
		regexp = /^[0-9]+$/
			return regexp.test(campo.value)
	}

	// .
	if (key == 46) {
		if (campo.value == "") return false
		regexp = /^[0-9]+$/
			return regexp.test(campo.value)
	}

	// Otra tecla
	return false
}


function validaNumeroPositivo(valor, campo){
	var valor = valor.value;
	if(null != valor && valor!=""){
		if(!isNaN(valor)){
			var num = parseInt(valor);
			if(num>=0){
				return true;
			}
		}
		alert("El campo " + campo + " s\u00f3lo admite caracteres num\u00e9ricos.\nPor favor repase los datos.");
		return false;
	}
}

function cambiarComa(valor){
	valor.value = valor.value.replace(',','.');
}

function construirRmFinanciacion() {	
	document.formData.action="construirRmFinanciacion.action";	
	document.formData.submit();
	return true;

}

function construirDpr(){
	var pestania = obtenerPestaniaSeleccionada();
	var tipoContrato = calcularContrato();
	
	iniciarProcesando('btnEnviarDpr','loadingImage');
	
	document.formData.action = "construirDpr"+tipoContrato+".action#" + pestania;
	document.formData.submit();	
}


//Esta función deshabilita los botones informados en nombreBotones separados por coma
function desactivarBotones(nombreBotones){
	var partesBotones = nombreBotones.split(",");
	for(i=0;i<partesBotones.length;i++){
		var boton = document.getElementById(partesBotones[i]);
		if(boton!=null)boton.disabled = true;
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
function mostrarDivCategoriaBien(){
		var idPropiedad =$('#idPropiedad').val();
		
	    var valorCambiado =$('#propiedadDtoCategoria').val();
		$("#divVehiculo").attr('style', 'display:none');
		$("#divMaquinaria").attr('style', 'display:none');
		$("#divEstablecimiento").attr('style', 'display:none');
		$("#divBuque").attr('style', 'display:none');
		$("#divAeronave").attr('style', 'display:none');
		$("#divPropiedadIndustrial").attr('style', 'display:none');
		$("#divPropiedadIntelectual").attr('style', 'display:none');
		
		$("#divOtrosBienes").attr('style', 'display:none');
	    
		switch (valorCambiado) {
		case '01'://Vehículo
			$("#divVehiculo").attr('style', 'display:block');
			break;
		case '02'://Maquinaria
			$("#divMaquinaria").attr('style', 'display:block');
			break;
		case '03'://Establecimiento
			$("#divEstablecimiento").attr('style', 'display:block');
			break;
		case '04'://Buque
			$("#divBuque").attr('style', 'display:block');
			break;
		case '05'://Aeronave
			$("#divAeronave").attr('style', 'display:block');
			break;
		case '06'://Propiedad Industrial
			$("#divPropiedadIndustrial").attr('style', 'display:block');
			break;
		case '07'://Propiedad Intelectual
			$("#divPropiedadIntelectual").attr('style', 'display:block');
			break;
		case '08'://Otros Bienes
			$("#divOtrosBienes").attr('style', 'display:block');
			break;
		}
		
		if(null== idPropiedad || ""== idPropiedad || 0==idPropiedad){
			$("#propiedadDtoCategoria").attr('disabled', false);
		}else{
			$("#propiedadDtoCategoria").attr('disabled', true);
		}
}

function mostrarDivCategoriaBienCancelacion(){
	
    var valorCambiado =$('#propiedadDtoCategoria').val();
	$("#divVehiculo").attr('style', 'display:none');
	$("#divMaquinaria").attr('style', 'display:none');
	$("#divEstablecimiento").attr('style', 'display:none');
	$("#divBuque").attr('style', 'display:none');
	$("#divAeronave").attr('style', 'display:none');
	$("#divPropiedadIndustrial").attr('style', 'display:none');
	$("#divPropiedadIntelectual").attr('style', 'display:none');
	
	$("#divOtrosBienes").attr('style', 'display:none');
    
	switch (valorCambiado) {
	case '01'://Vehículo
		$("#divVehiculo").attr('style', 'display:block');
		break;
	case '02'://Maquinaria
		$("#divMaquinaria").attr('style', 'display:block');
		break;
	case '03'://Establecimiento
		$("#divEstablecimiento").attr('style', 'display:block');
		break;
	case '04'://Buque
		$("#divBuque").attr('style', 'display:block');
		break;
	case '05'://Aeronave
		$("#divAeronave").attr('style', 'display:block');
		break;
	case '06'://Propiedad Industrial
		$("#divPropiedadIndustrial").attr('style', 'display:block');
		break;
	case '07'://Propiedad Intelectual
		$("#divPropiedadIntelectual").attr('style', 'display:block');
		break;
	case '08'://Otros Bienes
		$("#divOtrosBienes").attr('style', 'display:block');
		break;
	}
}

function borrarBienCancelacion(){
	
	var valorCambiado =$('#propiedadDtoCategoria').val();
	$("#divVehiculo").attr('style', 'display:none');
	$("#divMaquinaria").attr('style', 'display:none');
	$("#divEstablecimiento").attr('style', 'display:none');
	$("#divBuque").attr('style', 'display:none');
	$("#divAeronave").attr('style', 'display:none');
	$("#divPropiedadIndustrial").attr('style', 'display:none');
	$("#divPropiedadIntelectual").attr('style', 'display:none');
	
	$("#divOtrosBienes").attr('style', 'display:none');
    
	switch (valorCambiado) {
	case '01'://Vehículo
		$("#divVehiculo").attr('style', 'display:block');
		break;
	case '02'://Maquinaria
		$("#divMaquinaria").attr('style', 'display:block');
		break;
	case '03'://Establecimiento
		$("#divEstablecimiento").attr('style', 'display:block');
		break;
	case '04'://Buque
		$("#divBuque").attr('style', 'display:block');
		break;
	case '05'://Aeronave
		$("#divAeronave").attr('style', 'display:block');
		break;
	case '06'://Propiedad Industrial
		$("#divPropiedadIndustrial").attr('style', 'display:block');
		break;
	case '07'://Propiedad Intelectual
		$("#divPropiedadIntelectual").attr('style', 'display:block');
		break;
	case '08'://Otros Bienes
		$("#divOtrosBienes").attr('style', 'display:block');
		break;
	}
	
	var idPropiedad =$('#idPropiedad').val();
	if(null!= idPropiedad && ""!= idPropiedad && 0!=idPropiedad){
		var $form = $("#formData");
		var pestania = obtenerPestaniaSeleccionada();
		var url = "borrarBienCancelacion.action" + '#' + pestania
		$form.attr("action", url).trigger("submit");
	}
}


function guardarTramiteRegRbm(){
	
	$("#propiedadDtoCategoria").attr('disabled', false);

	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "guardar" + accion + ".action" + '#' + pestania
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");

}

function guardarTramiteRegistro(){
	
	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "guardar" + accion + ".action" + '#' + pestania

	$('input[type=button]').attr('disabled', true);
	
	$form.attr("action", url).trigger("submit");

}

function validarTramiteRegRbm(){
	
	$("#propiedadDtoCategoria").attr('disabled', false);

	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "validarContrato" + accion + ".action" + '#' + pestania
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");

}

function importarTramiteRegistro(){
	var tipoContratoRegistro;
	if(	$("#rbCancelacion").is(':checked')){
		tipoContratoRegistro = "cancelacion";
	}else if($("#rbFinanciacion").is(':checked')){
		tipoContratoRegistro = "financiacion";
	}else if($("#rbLeasing").is(':checked')){
		tipoContratoRegistro = "leasing";
	}else if($("#rbRenting").is(':checked')){
		tipoContratoRegistro = "renting";
	}else if($("#rbFinanciadores").is(':checked')){
		tipoContratoRegistro = "financiadores";
	}else if($("#rbCyNJuntaAccionistas").is(':checked')){
		tipoContratoRegistro = "cyNJuntaAccionistas";
	}else if($("#rbAyNReunionJunta").is(':checked')){
		tipoContratoRegistro = "ayNReunionJunta";
	}else if($("#rbDelegacionFacultades").is(':checked')){
		tipoContratoRegistro = "delegacionFacultades";
	}else if($("#rbCyNReunionJunta").is(':checked')){
		tipoContratoRegistro = "cyNReunionJunta";
	}else if($("#rbCyNJuntaSocios").is(':checked')){
		tipoContratoRegistro = "cyNJuntaSocios";
	}else if($("#rbEscrituras").is(':checked')){
		tipoContratoRegistro = "escrituras";
	}
	$("#formData").attr("action", "importarImportarTramiteRegistro.action?tipoContratoRegistro=" + tipoContratoRegistro).trigger("submit");

}


function buscarEntidad(idNif,tipo){
	var cif = $('#' + idNif).val();
	var idContrato = $("#idContrato").val();
	
	//Reiniciamos los campos
	$("#idEntidadSucesora").val("");
	$("#entidadSucesoraRazonSocial").val("");
	$("#entidadRegistoMercantil").val("");
	$("#entidadTomo").val("");
	$("#entidadLibro").val("");
	$("#entidadFolio").val("");
	$("#entidadHoja").val("");
	$("#entidadNumInscripcion").val("");

	if(cif == null || cif == ""){
		return alert("El CIF debe ir relleno");
	}else if(!valida_nif_cif_nie('#' + idNif))	{
		return alert("El CIF no es válido");
	}
	
	var url="buscarEntidadRegistradoresAjax.action?cifEntidad=" + cif + '&idContrato=' + idContrato;
	$.getJSON(url, function(data) {

		if("" == data || null == data){
			alert("Entidad no encontrada");
		}else {
			if(tipo=='Sucesora'){
				$("#idEntidadSucesora").val(data.idEntidad);
				$("#entidadSucesoraRazonSocial").val(data.razonSocial);
				if(null != data.datRegMercantil && "" != data.datRegMercantil){
					$("#entidadRegistoMercantil").val(data.datRegMercantil.codRegistroMercantil);
					$("#entidadTomo").val(data.datRegMercantil.tomo);
					$("#entidadLibro").val(data.datRegMercantil.libro);
					$("#entidadFolio").val(data.datRegMercantil.folio);
					$("#entidadHoja").val(data.datRegMercantil.hoja);
					$("#entidadNumInscripcion").val(data.datRegMercantil.numInscripcion);
				}
			}else{
				$("#idEntidadSuscriptora").val(data.idEntidad);
				$("#entidadSuscriptoraRazonSocial").val(data.razonSocial);
			}
		}
	});
}

function seleccionarSociedad(cifSeleccion){

	var $form = $("#formData");

	var url = "seleccionarSociedad.action?cifSeleccion=" + cifSeleccion

	$form.attr("action", url).trigger("submit");
	
	$("#divEmergentePopUp").dialog("close");

}

function seleccionarCertificante(){

	var $form = $("#formData");
	
	var marcados="";
	var primero = true;
	$("input:checked[name='certificantesMarcados']").each(function() {
		if (!primero) {
			marcados+="&";
		} else {
			primero = false;
		}
		marcados+=this.name+"="+this.value;
	});
	if (!primero) {
		var numExpediente = document.getElementById('idTramiteRegistro').value;
		var url = "seleccionarCertificante.action?"+marcados+"&numExpediente="+numExpediente+"#Certificantes";
		$form.attr("action", url).trigger("submit");
	}
	
	$("#divEmergentePopUp").dialog("close");
}

function seleccionarAsistente(){

	var $form = $("#formData");
	
	var marcados="";
	var primero = true;
	$("input:checked[name='asistentesMarcados']").each(function() {
		if (!primero) {
			marcados+="&";
		} else {
			primero = false;
		}
		marcados+=this.name+"="+this.value;
	});
	if (!primero) {
		var numExpediente = document.getElementById('idTramiteRegistro').value;
		var url = "seleccionarAsistente.action?"+marcados+"&numExpediente="+numExpediente+"#Asistentes";
		$form.attr("action", url).trigger("submit");
	}
	
	$("#divEmergentePopUp").dialog("close");
}

function limpiarCamposConsultaSociedad() {
	document.getElementById("cif").value="";
	document.getElementById("denominacionSocial").value="";
	document.getElementById("ius").value="";
	document.getElementById("seccion").value="";
	document.getElementById("hoja").value="";
	document.getElementById("hojaBisBuscar").selectedIndex = 0;
	document.getElementById("tipoPersonaBuscar").selectedIndex = 0;
	document.getElementById("email").value="";
}



////////////////////////////////////-------------------------/////////////////////////////////////////////////
/////////////////////////////////////Registradores.js antiguo/////////////////////////////////////////////////
////////////////////////////////////-------------------------/////////////////////////////////////////////////

function iniciarProcesando(idBoton,idImagen) {
	document.getElementById(idBoton).disabled = "true";
	document.getElementById(idBoton).style.color = "#6E6E6E";
	document.getElementById(idBoton).value = "Procesando...";
	document.getElementById(idImagen).style.display = "block";
	$('input[type=button]').attr('disabled', true);
}

function cargarRegistrosProvincia() {
	document.getElementById('registroSeleccionadoOculto').value = "";
	document.getElementById('registroSeleccionadoCaja').value = "";
	obtenerRegistrosProvincia();
}

// REGISTROS SEGÚN PROVINCIA
function obtenerRegistrosProvincia() {
	var selectProvincia = document.getElementById('provinciaSeleccionada');
	var tipoTramiteRegistro = document.getElementById('tipoTramiteRegistro');
	//Sin elementos
	if(selectProvincia.selectedIndex==0) {
		document.getElementById('registroSeleccionado').length = 0;
		document.getElementById('registroSeleccionado').options[0] = new Option("Seleccione Registro", "-1");
		return;
	}
	var idPro = selectProvincia.options[selectProvincia.selectedIndex].value;
	var tipoTR = tipoTramiteRegistro.value;
	url="recuperarRegistrosRegistradoresAjax.action?idProvincia=" + idPro + '&tipoTramiteRegistro=' + tipoTR;

	//Hace la llamada a ajax
	if (window.XMLHttpRequest) { // Non-IE browsers
		reqRegistros = new XMLHttpRequest();
		reqRegistros.onreadystatechange = rellenarRegistros;
		reqRegistros.open("POST", url, true);
		reqRegistros.send(null);
	} else if (window.ActiveXObject) { // IE      
		reqRegistros = new ActiveXObject("Microsoft.XMLHTTP");
		if (reqRegistros) {
			reqRegistros.onreadystatechange = rellenarRegistros;
			reqRegistros.open("POST", url, true);
			reqRegistros.send();
		}
	}
}

//Callback function
function rellenarRegistros() {
	document.getElementById("registroSeleccionado").options.length = 0;
	if (reqRegistros.readyState == 4) { // Complete
		if (reqRegistros.status == 200) { // OK response
			var textToSplitRegistros = reqRegistros.responseText;
			
			//Split the document
			var returnElementRegistro=textToSplitRegistros.split("||");
			
      		document.getElementById("registroSeleccionado").options[0] = new Option("Seleccione Registro", "-1");		
			//Process each of the elements
			var registroSeleccionado = document.getElementById("registroSeleccionadoOculto").value;
			for ( var i = 0 ; i < returnElementRegistro.length ; i ++ ) {
				value = returnElementRegistro[i].split(";");
				if (value != '') {
					document.getElementById("registroSeleccionado").options[i+1] = new Option(value[0], value[1]);
					if(document.getElementById("registroSeleccionado").options[i+1].value == registroSeleccionado){
						document.getElementById("registroSeleccionado").options[i+1].selected = true;
					}
				}
			}	
		}
	}
}

function seleccionarRegistro() {
	var codigo = document.getElementById('registroSeleccionado').value;
	if(codigo != -1) {
		document.getElementById('registroSeleccionadoCaja').value = codigo;
		document.getElementById('registroSeleccionadoOculto').value = codigo;
		
		url="recuperarIdRegistroRegistradoresAjax.action?id=" + codigo;
		if (window.XMLHttpRequest) { // Non-IE browsers
			reqRegistros = new XMLHttpRequest();
			reqRegistros.onreadystatechange = rellenarIdRegistro;
			reqRegistros.open("POST", url, true);
			reqRegistros.send(null);
		} else if (window.ActiveXObject) { // IE      
			reqRegistros = new ActiveXObject("Microsoft.XMLHTTP");
			if (reqRegistros) {
				reqRegistros.onreadystatechange = rellenarIdRegistro;
				reqRegistros.open("POST", url, true);
				reqRegistros.send();
			}
		}
	}
}


function rellenarIdRegistro() {
	if (reqRegistros.readyState == 4) { // Complete
		if (reqRegistros.status == 200) { // OK response
			document.getElementById("registroSeleccionadoCaja").value = reqRegistros.responseText;
		}
	}
}

// Recarga el combo cuando se recarga la pagina
function recargarComboRegistros() {
	var selectProvinciaRegistro = document.getElementById('provinciaSeleccionada');
	var indice = selectProvinciaRegistro.selectedIndex;
	if(indice > 0) {
		obtenerRegistrosProvincia();
	}
}

function validaEmail(email) {
    var re  = /^([a-zA-Z0-9_.-])+@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
	if (email.length == 0){
		return true;
	}
    if (!re.test(email)) {
    	alert("El email introducido no es v\u00e1lido.\nPor favor repase los datos.");
        return false;
    }
    return true;
}

function validacionCese() {
	if(!validarCese()) {
		return false;
	}
	iniciarProcesando('botonAltaCese','loading6Image');
	document.formData.action="altaAcuerdo.action?tipoAcuerdo=CESE#Acuerdos";	
	document.formData.submit();
	return true;
}

function validacionMedio() {
	if(!validarMedio()) {
		return false;
	}
	iniciarProcesando('botonAnadirMedio','loading3.3Image');
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="altaMedioReunion.action#" + pestania;	
	document.formData.submit();
	return true;
}

function validarMedio() {
	if (campoVacio("tipoMedio")) {
		alert("Debe rellenar el campo 'Tipo Medio'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("descripcionMed")) {
		alert("Debe rellenar el campo 'Descripción Medio'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("diaMedio") || campoVacio("mesMedio") || campoVacio("anioMedio")) {
		alert("Debe rellenar el campo 'Fecha Convocatoria'.\nPor favor repase los datos.");
		return false;
	}
	
	return true;
}

function validarCese() {
	if (campoVacio("nombreC")) {
		alert("Debe rellenar el campo 'Nombre'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("apellido1C")) {
		alert("Debe rellenar el campo 'Apellido 1'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("nifC")) {
		alert("Debe rellenar el campo 'Nif'.\nPor favor repase los datos.");
		return false;
	}
	var cargoCer = document.getElementById("cargoC").value;
	if (cargoCer == "") {
		alert("Debe seleccionar un 'Cargo'.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validacionNombramiento() {
	if(!validarNombramiento()) {
		return false;
	}
	iniciarProcesando('botonAltaNombramiento','loading7Image');
	document.formData.action="altaAcuerdo.action?tipoAcuerdo=NOMBRAMIENTO#Acuerdos";	
	document.formData.submit();
	return true;
}

function validarNombramiento() {
	var cargoCer = document.getElementById("cargoNom").value;
	if (cargoCer == "") {
		alert("Debe seleccionar un 'Cargo'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("nombreNom")) {
		alert("Debe rellenar el campo 'Nombre'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("apellido1Nom")) {
		alert("Debe rellenar el campo 'Apellido 1'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("nifNom")) {
		alert("Debe rellenar el campo 'Nif'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("emailNom")) {
		alert("Debe rellenar el campo 'Correo Electr\u00F3nico'.\nPor favor repase los datos.");
		return false;
	}
	
	if (!campoVacio("emailNom")) {
		if(!this.validaEmail(document.getElementById("emailSoc").value)) {
			alert("El campo 'Correo Electr\u00F3nico' no contiene una direcci\u00F3n de correo v\u00E1lida.\nPor favor repase los datos.");
			return false;
		}
	}
	

	return true;
}

function guardarAsistente() {
	if(!validarCamposLugarFechaAsis()) {
		return false;
	}
	iniciarProcesando('botonActualizarAsistente','loadingImage');
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="guardarAsistente.action#" + pestania;	
	document.formData.submit();
	return true;
}

function guardarCertificante() {
	if(!validarCamposLugarFecha()) {
		return false;
	}
	iniciarProcesando('botonActualizarCertificante','loadingImage');
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="guardarCertificante.action#" + pestania;	
	document.formData.submit();
	return true;
}

function guardarReunion() {
	if(!validarCamposReunion()) {
		return false;
	}
	guardarTramiteRegistro();
	return true;
}

function guardarReunionAccionistas() {
	if(!validarCamposReunionAccionistas()) {
		return false;
	}
	guardarTramiteRegistro();
	return true;
}

function guardarReunionSocios() {
	if(!validarCamposReunionSocios()) {
		return false;
	}
	guardarTramiteRegistro();
	return true;
}

function validarCamposReunionSocios() {
	remplazarComas("capitalAsistente");
	remplazarComas("porcentajeCapitalAcuerdo");
	remplazarComas("sociosAsistente");
	remplazarComas("porcentajeSociosAcuerdo");
	
	if (selectVacio("tipoReunion")) {
		alert("Debe seleccionar el campo 'Tipo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("lugar")){
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("capitalAsistente")) {
		alert("Debe rellenar el campo 'Porcentaje Capital Asistente.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeCapitalAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Capital Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("sociosAsistente")){
		alert("Debe rellenar el campo 'Porcentaje Socios Asistente'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeSociosAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Socios Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("transcripcionContenidoIntegro")){
		alert("Debe rellenar el campo 'Contenido'.\nPor favor repase los datos.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("capitalAsistente").value)) {
		alert("El porcentaje capital asistente debe ser un valor numérico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("porcentajeCapitalAcuerdo").value)) {
		alert("El porcentaje capital acuerdo debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("sociosAsistente").value)) {
		alert("El porcentaje socios asistente debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("porcentajeSociosAcuerdo").value)) {
		alert("El porcentaje socios acuerdo debe ser un valor numerico.");
		return false;
	}
	if (campoVacio("dia") || campoVacio("mes") || campoVacio("anio")) {
		alert("Campo 'Fecha' incorrecto.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarCamposReunionAccionistas() {
	
	remplazarComas("capitalAsistente");
	remplazarComas("porcentajeCapitalAcuerdo");
	remplazarComas("sociosAsistente");
	remplazarComas("porcentajeSociosAcuerdo");
	
	if (selectVacio("tipoReunion")) {
		alert("Debe seleccionar el campo 'Tipo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("dia") || campoVacio("mes") || campoVacio("anio")) {
		alert("Campo 'Fecha' incorrecto.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("lugar")){
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (selectVacio("convocatoria")){
		alert("Debe rellenar el campo 'Numero de la convocatoria'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("capitalAsistente")) {
		alert("Debe rellenar el campo 'Porcentaje Capital Asistente.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeCapitalAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Capital Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("sociosAsistente")){
		alert("Debe rellenar el campo 'Porcentaje Socios Asistente'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("porcentajeSociosAcuerdo")){
		alert("Debe rellenar el campo 'Porcentaje Socios Acuerdo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("formaAprobacionActa")){
		alert("Debe rellenar el campo 'Forma aprobación acta'.\nPor favor repase los datos.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("capitalAsistente").value)) {
		alert("El porcentaje capital asistente debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("porcentajeCapitalAcuerdo").value)) {
		alert("El porcentaje capital acuerdo debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("sociosAsistente").value)) {
		alert("El porcentaje socios asistente debe ser un valor numerico.");
		return false;
	}
	if (!validaNumeroPositivo(document.getElementById("porcentajeSociosAcuerdo").value)) {
		alert("El porcentaje socios acuerdo debe ser un valor numerico.");
		return false;
	}
	
	return true;
}

function validarCamposReunion() {
	if (campoVacio("tipoReunion")) {
		alert("Debe rellenar el campo 'Tipo'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("lugar")) {
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("dia") || campoVacio("mes") || campoVacio("anio")) {
		alert("Campo 'Fecha' incorrecto.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarAltaCertificante() {
	if (campoVacio("nombreCertificante")) {
		alert("Debe rellenar el campo 'Nombre'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("apellido1Certificante")) {
		alert("Debe rellenar el campo 'Apellido 1'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("nifCertificante")) {
		alert("Debe rellenar el campo 'Nif'.\nPor favor repase los datos.");
		return false;
	} else if(!valida_nif_cif_nie("#nifCertificante"))	{
		alert("El DNI / NIE / NIF no tiene el formato correcto");
		return false;
	}
	
	var cargoCer = document.getElementById("cargoCertificante").value;
	if (cargoCer == "") {
		alert("Debe seleccionar un 'Cargo'.\nPor favor repase los datos.");
		return false;
	}
	var email = document.getElementById("correoElectronicoCertificante").value;
	if (tipoTramiteRegistro = 'R2' && campoVacio("correoElectronicoCertificante")) {
		alert("Debe rellenar el campo 'Correo Electrónico'.\nPor favor repase los datos.");
		return false;
	} else if(email.length > 0) {
		if(!validaEmail(email)) {
			alert("El correo electrónico especificado no tiene formato válido");
			return false;
		}
	}
	return true;
}

function validarAltaAsistente() {
	if (campoVacio("nombreAsistente")) {
		alert("Debe rellenar el campo 'Nombre'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("apellido1Asistente")) {
		alert("Debe rellenar el campo 'Apellido 1'.\nPor favor repase los datos.");
		return false;
	}
	
	if (campoVacio("nifAsistente")) {
		alert("Debe rellenar el campo 'Nif'.\nPor favor repase los datos.");
		return false;
	} else if(!valida_nif_cif_nie("#nifAsistente"))	{
		alert("El DNI / NIE / NIF no tiene el formato correcto");
		return false;
	}
	
	var cargoCer = document.getElementById("cargoAsistente").value;
	if (cargoCer == "") {
		alert("Debe seleccionar un 'Cargo'.\nPor favor repase los datos.");
		return false;
	}
	var email = document.getElementById("correoElectronicoAsistente").value;
	if (tipoTramiteRegistro = 'R2' && campoVacio("correoElectronicoAsistente")) {
		alert("Debe rellenar el campo 'Correo Electrónico'.\nPor favor repase los datos.");
		return false;
	} else if(email.length > 0) {
		if(!validaEmail(email)) {
			alert("El correo electrónico especificado no tiene formato válido");
			return false;
		}
	}
	return true;
}

function construirRm(rmFirmar, modelo) {
	if (rmFirmar) {
		iniciarProcesando('btnFirmar','loadingImage');
	} else {
		iniciarProcesando('btnVerDocumento','loadingImage');
	}
	document.formData.action="construirRmTramiteRegistroMd"+modelo+".action?rmFirmar=" + rmFirmar + "#Resumen";	
	document.formData.submit();
	return true;
}

function construirRmLista() {
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00FAn tr\u00E1mite");
		return false;
	}
	iniciarProcesando('btnFirmar','loadingImage');
	
	document.formData.action="firmarListaRecuperarTramite.action";	
	document.formData.submit();
	return true;
}

function construirAcuseLista() {
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00FAn tr\u00E1mite");
		return false;
	}
	iniciarProcesando('btnFirmarAcuse','loadingImage');
	
	document.formData.action="firmarAcuseListaRecuperarTramite.action";	
	document.formData.submit();
	return true;
}

function exportarTramites() {
	if (numChecked() == 0) {
		alert("Debe seleccionar alg\u00FAn tr\u00E1mite");
		return false;
	}
	
	document.formData.action="exportarRecuperarTramite.action";	
	document.formData.submit();
	return true;
}

function cerrarVistaDocumento() {
	iniciarProcesando('botonVolver','loading9Image');
	document.formularioDocumento.action="cerrarVistaDocumentoRm.action#Resumen";		
	document.formularioDocumento.submit();
}

function duplicar() {
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="duplicarRecuperarTramite.action#" + pestania;	
	document.formData.submit();
	return true;
}

function subsanar() {
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="subsanarRecuperarTramite.action#" + pestania;	
	document.formData.submit();
	return true;
}

// Limpia las cajas del formulario de selección de la sociedad:
function limpiarCamposSoc() {
	document.getElementById("cifSoc").value = "";
	document.getElementById("denominacionSocialSoc").value = "";
	document.getElementById("iusSoc").value = "";
	document.getElementById("seccionSoc").value = "";
	document.getElementById("hojaSoc").value = "";
	document.getElementById("seccionSoc").value = "";
	document.getElementById("emailSoc").value = "";
	document.getElementById("hojaBisSoc").selectedIndex = 0;
	document.getElementById("tipoPersona").selectedIndex = 0;
	// Registro destino:
	document.getElementById("provinciaSeleccionada").selectedIndex = 0;
	document.getElementById("registroSeleccionado").selectedIndex = 0;
	document.getElementById("registroSeleccionadoCaja").value = "";
	document.getElementById("registroSeleccionadoOculto").value = "";
	// Caja mensaje:
	//	document.getElementById("cajaMsgSociedad").style.display = 'none';
}

function limpiarCamposCese() {
	document.getElementById("nombreC").value="";
	document.getElementById("apellido1C").value="";
	document.getElementById("apellido2C").value="";
	document.getElementById("nifC").value="";
	document.getElementById("cargoC").selectedIndex=0;
}

function limpiarCamposNombramiento() {
	document.getElementById("nombreNom").value="";
	document.getElementById("apellido1Nom").value="";
	document.getElementById("apellido2Nom").value="";
	document.getElementById("nifNom").value="";
	document.getElementById("emailNom").value="";
	document.getElementById("cargoNom").selectedIndex=0;
	document.getElementById("aceptacion").selectedIndex=0;
	document.getElementById("indefinido").selectedIndex=0;
	document.getElementById("temporal").style.display='none';
	document.getElementById("meses").value="";
	document.getElementById("anios").value="";
	document.getElementById("diaInicio").value="";
	document.getElementById("mesInicio").value="";
	document.getElementById("anioInicio").value="";
	document.getElementById("diaFin").value="";
	document.getElementById("mesFin").value="";
	document.getElementById("anioFin").value="";
	//document.getElementById("ejercicios").value="";
	document.getElementById("circunstanciasPersonales").value="";
	document.getElementById("facultadesDelegadas").value="";
	//var array=new Array(3);
	var array;
	array=document.getElementsByName("duracion");
	//var nombreCaja;
	for(var i=0;i<array.length;i++) {
		array[i].checked=false;
	}
	document.getElementById("tipoDuracion1").style.display="none";
	document.getElementById("tipoDuracion2").style.display="none";
	// document.getElementById("tipoDuracion3").style.display="none";
	//document.getElementById("cajaMsgNombramiento").style.display = "none";
}

function limpiarCamposReunionJunta() {
	document.getElementById("tipoReunion").value="";
	document.getElementById("caracter").selectedIndex = 0;
	document.getElementById("dia").value="";
	document.getElementById("mes").value="";
	document.getElementById("anio").value="";
	document.getElementById("lugar").value="";
	document.getElementById("divError").style.display = "none";
}



function certificantes() {
	var nombre=document.getElementById("seleccionOpeCertif").value;
	if(nombre=="verLista"){
		document.getElementById("verLista").style.display = 'block';
		document.getElementById("lugarFechaCertif").style.display = 'none';
		document.getElementById("certificanteFisico").style.display = 'none';
	}else if(nombre=="lugarFechaCertif"){
		document.getElementById("lugarFechaCertif").style.display = 'block';
		document.getElementById("verLista").style.display = 'none';
		document.getElementById("certificanteFisico").style.display = 'none';
	}else if(nombre=="altaCertificante"){
		document.getElementById("certificanteFisico").style.display = 'block';
		document.getElementById("verLista").style.display = 'none';
		document.getElementById("lugarFechaCertif").style.display = 'none';
	}else if(nombre==""){
		document.getElementById("certificanteFisico").style.display='none';
		document.getElementById("verLista").style.display='none';
		document.getElementById("lugarFechaCertif").style.display='none';
	}
}

function validacionCamposSociedad() {
	if(!validarCamposSociedad()) {
		return false;
	}
	iniciarProcesando('botonSeleccionarSociedad','loadingImage');
	document.formData.action="guardarSociedad.action";
	document.formData.submit();
	return true;
}

function validacionCamposSociedadGuardar(tramite) {
	if(!validarCamposSociedad()) {
		return false;
	}
	iniciarProcesando('botonSeleccionarSociedad','loadingImage');
	document.formData.action="guardar" + tramite + ".action";
	document.formData.submit();
	return true;
}

function validarCamposLugarFechaAsis() {
	if (campoVacio("lugarAsis")) {
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("diaAsis") || campoVacio("mesAsis") || campoVacio("anioAsis")) {
		alert("Debe rellenar el campo 'Fecha'.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarCamposLugarFecha() {
	if (campoVacio("lugarCertif")) {
		alert("Debe rellenar el campo 'Lugar'.\nPor favor repase los datos.");
		return false;
	}
	if (campoVacio("diaCertif") || campoVacio("mesCertif") || campoVacio("anioCertif")) {
		alert("Debe rellenar el campo 'Fecha'.\nPor favor repase los datos.");
		return false;
	}
	return true;
}

function validarCamposSociedad() {
	if (campoVacio("cifSoc")) {
		alert("Debe rellenar el campo 'CIF'.\nPor favor repase los datos.");
		return false;
	}else if (!valida_nif_cif_nie(cifSoc)){
		alert("El DNI/CIF/NIE no es correcto");
		return false;
	}
	
	if (campoVacio("denominacionSocialSoc")) {
		alert("Debe rellenar el campo 'Denominaci\u00F3n Social'.\nPor favor repase los datos.");
		return false;
	}
	if (!campoVacio("emailSoc")) {
		if(!this.validaEmail(document.getElementById("emailSoc").value)) {
			alert("El campo 'Correo Electr\u00F3nico' no contiene una direcci\u00F3n de correo v\u00E1lida.\nPor favor repase los datos.");
			return false;
		}
	}
	if (campoVacio("registroSeleccionadoCaja")) {
		alert("Seleccione 'Provincia' y 'Registro' para establecer el c\u00F3digo correspondiente.");
		return false;
	}
	return true;
}

function eliminarAcuerdo(idAcuerdo, nifCargo, codigoCargo) {
	if (confirm("¿Está seguro de que desea eliminar este acuerdo?")) {
		document.formData.action="eliminarAcuerdo.action?idAcuerdo="+ idAcuerdo + "&nifCargo=" + nifCargo + "&codigoCargo="+ codigoCargo +"#Acuerdos";
		document.formData.submit();
	}
}

function eliminarAsistente(nifCargo, codigoCargo) {
	if (confirm("¿Está seguro de que desea eliminar este asistente?")) {
		document.formData.action="eliminarAsistente.action?nifCargo=" + nifCargo + "&codigoCargo="+ codigoCargo +"#Asistentes";
		document.formData.submit();
	}
}

function eliminarInmueble(idInmueble) {
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="eliminarInmuebleTramiteRegistroMd6.action?idInmueble="+ idInmueble + "#" + pestania;
	document.formData.submit();
}

function modificarInmueble(idInmueble) {
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="prepararModificacionInmuebleTramiteRegistroMd6.action?idInmueble="+ idInmueble + "#" + pestania;
	document.formData.submit();
}

function eliminarMedio(idMedio) {
	if (confirm("¿Está seguro de que desea eliminar este medio?")) {
		document.formData.action="eliminarMedioReunion.action?idMedio="+ idMedio +"#Junta";
		document.formData.submit();
	}
}

function enviarAcusePendiente(idRegistroFueraSecuencia, estadoAcusePendiente) {
	document.formData.action = idRegistroFueraSecuencia;
	document.formData.action = estadoAcusePendiente;
	document.formData.action="construirAcuse.action?idRegistroFueraSecuencia="+idRegistroFueraSecuencia+"&estadoAcusePendiente="+estadoAcusePendiente+"#Pendientes";
	document.formData.submit();
}

function acuerdos() {
	var nombre=document.getElementById("seleccionAcuerdo").value;
	if(nombre=="NOMBRAMIENTO") {
		document.getElementById("nombramiento").style.display = 'block';
		document.getElementById("cese").style.display = 'none';
	}else if(nombre=="CESE") {
		document.getElementById("cese").style.display = 'block';
		document.getElementById("nombramiento").style.display = 'none';
	}else if(nombre=="") {
		document.getElementById("nombramiento").style.display='none';
		document.getElementById("cese").style.display='none';
	}
}

function numChecked() {
	var checks = document.getElementsByName("listaChecksRegistradores");	
	var numChecked = 0;
	for(var i=0; i<checks.length; i++) {
		if(checks[i].checked) numChecked++;
	}
	return numChecked;
}

function mostrarLoadingConsultarReg(boton) {
	bloqueaBotonesConsulta();
	document.getElementById("bloqueLoadingConsultarReg").style.display = "block";
	boton.value = "Procesando..";
}


function ocultarLoadingConsultarReg(boton, value) {
	document.getElementById("bloqueLoadingConsultarReg").style.display = "none";
	boton.value = value;
	desbloqueaBotonesConsulta();
}


function ejecutarAccion(accion){
	document.location.href=accion;
}

function mostrarDuraciones(){
	var nombre=document.getElementById("indefinido").value;
	if(nombre=='N'){
		document.getElementById("temporal").style.display='block';
	}else if(nombre=='S'){
		document.getElementById("meses").value="";
		document.getElementById("anios").value="";
		document.getElementById("diaInicio").value="";
		document.getElementById("mesInicio").value="";
		document.getElementById("anioInicio").value="";
		document.getElementById("diaFin").value="";
		document.getElementById("mesFin").value="";
		document.getElementById("anioFin").value="";
		document.getElementById("temporal").style.display='none';
	}
}

function abrirVentanaBusquedaSociedades(){
	
	url = "inicioSociedad.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function buscarSociedad(){
	url = "buscarSociedad.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function buscarCertificante(){
	url = "buscarCertificante.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function buscarAsistente(){
	url = "buscarAsistente.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function buscarCargo(){
	url = "buscarCargo.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function abrirVentanaBusquedaCertificantes(){
	var cif = document.getElementById('cifSoc').value;
	
	url = "inicioCertificante.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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


function editarCertificante(nifCargo, codigoCargo){
	
	document.getElementById("rowidCargo").value = nifCargo + "-" + codigoCargo;
	document.formData.action="editarCertificante.action#Certificantes";	
	document.formData.submit();
}

function editarAsistente(nifCargo, codigoCargo){
	
	document.getElementById("rowidCargo").value = nifCargo + "-" + codigoCargo;
	document.formData.action="editarAsistente.action#Asistentes";	
	document.formData.submit();

}

function validacionAltaAsistente(){
	if(!validarAltaAsistente()) {
		return false;
	}
	
	iniciarProcesando('botonAltaAsistente','loading7Image');
	document.formData.action="altaAsistente.action#Asistentes";	
	document.formData.submit();
	return true;
}

function validacionAltaCertificante(){
	if(!validarAltaCertificante()) {
		return false;
	}
	
	iniciarProcesando('botonAltaCertificante','loading7Image');
	document.formData.action="altaCertificante.action#Certificantes";	
	document.formData.submit();
	return true;
}

function abrirVentanaBusquedaAsistentes(){
	url = "inicioAsistente.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function abrirVentanaBuscarMedios(){
	
	url = "inicioMedio.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function buscarMedio(){
	url = "buscarMedio.action";
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function limpiarCamposMedios() {
	document.getElementById("descripcion").value="";
	document.getElementById("tipoMedioBuscar").selectedIndex = "0";
}

function abrirVentanaBusquedaCargos(tipoAcuerdo) {
	
	$("#tipoAcuerdo").val(tipoAcuerdo);
	
	var numColegiado = document.getElementById('numColegiadoRegistro').value;
	
	url = 'inicioCargo.action?numColegiado='+numColegiado;
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 600,
				buttons : {
					Cerrar : function() {
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

function mostrarIntervaloFechas(){
	document.getElementById("temporal").style.display="block";
	document.getElementById("tipoDuracion1").style.display="none";
	document.getElementById("tipoDuracion2").style.display="block";
	// document.getElementById("tipoDuracion3").style.display="none";
}

function mostrarMesesAnios(){
	document.getElementById("temporal").style.display="block";
	document.getElementById("tipoDuracion2").style.display="none";
	document.getElementById("tipoDuracion1").style.display="block";
	// document.getElementById("tipoDuracion3").style.display="none";
}

function mostrarCajaDuracion(){
	var array;
	//var array=new Array(2);
	array=document.getElementsByName("duracion");
	var nombreCaja;
	for(var i=0;i<array.length;i++){
		nombreCaja="tipoDuracion"+array[i].value;
        if(array[i].checked){
        	document.getElementById(nombreCaja).style.display="block";
        }else{
        	document.getElementById(nombreCaja).style.display="none";
        	if(i==0){
        		document.getElementById("meses").value="";
        		document.getElementById("anios").value="";
        	}else if(i==1){
        		document.getElementById("diaInicio").value="";
        		document.getElementById("mesInicio").value="";
        		document.getElementById("anioInicio").value="";
        		document.getElementById("diaFin").value="";
        		document.getElementById("mesFin").value="";
        		document.getElementById("anioFin").value="";
        	}/*else if(i==2){
        		document.getElementById("ejercicios").value="";
        	}*/
        }
	}
}

function habilitarPestanias(){
	document.getElementById('pesCertif').style.display="block";
	document.getElementById('pesCertif').visible = true;
	document.getElementById('pesJunta').style.display="block";
	document.getElementById('pesJunta').visible = true;
	document.getElementById('pesResumen').style.display="block";
	document.getElementById('pesResumen').visible = true;
	document.getElementById('pesDocumentacion').style.display="block";
	document.getElementById('pesDocumentacion').visible = true;
	document.getElementById('pesPresentador').style.display="block";
	document.getElementById('pesPresentador').visible = true;
	document.getElementById('pesFormaPago').style.display="block";
	document.getElementById('pesFormaPago').visible = true;
}


function habilitarPestaniasLibro(){
	document.getElementById('pesResumen').style.display="block";
	document.getElementById('pesResumen').visible = true;
	document.getElementById('Libro').style.display="block";
	document.getElementById('Libro').visible = true;
	document.getElementById('presentador').style.display="block";
	document.getElementById('presentador').visible = true;
//	document.getElementById('formaPago').style.display="block";
//	document.getElementById('formaPago').visible = true;
}

function habilitarPestaniasCuenta(){
	document.getElementById('pesResumen').style.display="block";
	document.getElementById('pesResumen').visible = true;
//	document.getElementById('Cuenta').style.display="block";
//	document.getElementById('Cuenta').visible = true;
	document.getElementById('presentador').style.display="block";
	document.getElementById('presentador').visible = true;
//	document.getElementById('formaPago').style.display="block";
//	document.getElementById('formaPago').visible = true;
}

function habilitarAcuerdos(){
	document.getElementById('pesAcuerdos').style.display="block";
	document.getElementById('pesAcuerdos').visible = true;
}

function mostrarResumen(){
	location.href="#Resumen";
}

function mostrarBotones(){
	var nombreEstado = document.getElementById('estado').value;
	if (nombreEstado != null && (nombreEstado=="Firmado")||(nombreEstado=="Pendiente de envío")){
		
		document.getElementById('btnFirmar').visible = false;
		document.getElementById('btnFirmar').style.display="none";
		document.getElementById('btnEnviarDpr').visible = true;
		document.getElementById('btnEnviarDpr').style.display="inline";
	}else{
		document.getElementById('btnFirmar').visible = true;
		document.getElementById('btnFirmar').style.display="inline";
		document.getElementById('btnEnviarDpr').visible = false;
		document.getElementById('btnEnviarDpr').style.display="none";
		
	}
	if (nombreEstado != null && nombreEstado=="Enviado"){
		
		document.getElementById('btnFirmar').visible = false;
		document.getElementById('btnFirmar').style.display="none";
		document.getElementById('btnEnviarDpr').visible = false;
		document.getElementById('btnEnviarDpr').style.display="none";
	}
	
	
}

function limpiarCamposJuntaAccionistas(){
	document.getElementById("tipoReunion").value="";
	document.getElementById("caracter").selectedIndex = 0;
	document.getElementById("dia").value="";
	document.getElementById("mes").value="";
	document.getElementById("anio").value="";
	document.getElementById("lugar").value="";
	document.getElementById("diaActa").value="";
	document.getElementById("mesActa").value="";
	document.getElementById("anioActa").value="";
	document.getElementById("formaAprobacionActa").value="";
	document.getElementById("porcentajeSociosAcuerdo").value="";
	document.getElementById("porcentajeCapitalAcuerdo").value="";
	document.getElementById("sociosAsistente").value="";
	document.getElementById("capitalAsistente").value="";
	document.getElementById("convocatoria").selectedIndex = 0;
	document.getElementById("tipoMedio").selectedIndex = 0;
	document.getElementById("diaMedio").value="";
	document.getElementById("mesMedio").value="";
	document.getElementById("anioMedio").value="";
	document.getElementById("descripcionMed").value="";
	var cajaError = document.getElementById("divError"); 
	if(cajaError != null){
		cajaError.style.display = "none";
	}
	document.getElementById("cajaMsgMedio").style.display = "none";
}

function limpiarCamposJuntaSocios(){
	document.getElementById("tipoReunion").value="";
	document.getElementById("caracter").selectedIndex = 0;
	document.getElementById("dia").value="";
	document.getElementById("mes").value="";
	document.getElementById("anio").value="";
	document.getElementById("lugar").value="";
	document.getElementById("porcentajeSociosAcuerdo").value="";
	document.getElementById("porcentajeCapitalAcuerdo").value="";
	document.getElementById("sociosAsistente").value="";
	document.getElementById("capitalAsistente").value="";
	document.getElementById("convocatoria").selectedIndex = 0;
	document.getElementById("transcripcionContenidoIntegro").value = "";
	document.getElementById("tipoMedio").selectedIndex = 0;
	document.getElementById("diaMedio").value="";
	document.getElementById("mesMedio").value="";
	document.getElementById("anioMedio").value="";
	document.getElementById("descripcion").value="";
	var cajaError = document.getElementById("cajaMsgSocios"); 
	if(cajaError != null){
		cajaError.style.display = "none";
	}
	document.getElementById("cajaMsgMedio").style.display = "none";
}

function incluirFichero() {
	iniciarProcesando("botonSubirFichero","loading3Image");
	var pestania = obtenerPestaniaSeleccionada();
	var accion = calcularContrato();
	document.formData.action="incluirFichero" + accion + ".action#" + pestania;
	document.formData.submit();
}

function eliminarFichero(idFichero){
	if (confirm("¿Está seguro de que desea borrar este fichero?")) {
		var accion = calcularContrato();
		var pestania = obtenerPestaniaSeleccionada();
		
		document.formData.idFicheroEliminar.value = idFichero;
		document.formData.action="eliminarFichero" + accion + ".action#" + pestania;
		document.formData.submit();
	}
}


function descargarAdjuntos() {
	var accion = calcularContrato();
	var pestania = obtenerPestaniaSeleccionada();
	document.formData.action="descargarAdjuntos" + accion + ".action#" + pestania;	
	document.formData.submit(); 
	return true;
}

function descargarDocumentacion(nombre,tipo) {
	
	limpiarHiddenInput("tipoDoc");
	limpiarHiddenInput("nombreDoc");
	
	var accion = calcularContrato();
		
	var input = $("<input>").attr("type", "hidden").attr("name", "nombreDoc").val(nombre);
	$('#formData').append($(input));
	
	var input2 = $("<input>").attr("type", "hidden").attr("name", "tipoDoc").val(tipo);
	$('#formData').append($(input2));
	
	var pestania = obtenerPestaniaSeleccionada();
	
	$("#formData").attr("action", "descargarDocumentacion" + accion + ".action#" + pestania).trigger("submit");

}

function limpiarHiddenInput(nombre){
	if ($("input[name='"+nombre+"']").is(':hidden')){
		$("input[name='"+nombre+"']").remove();
	}
}

function abrirVentanaSeleccionEstados(){
	
	var checks =  $("input[name='listaChecksRegistradores']:checked");
	if(checks.size() == 0) {
		alert("Debe seleccionar alg\u00fan tr\u00E1mite");
		return false;
	}
    var arrayCodigos = new Array();
	checks.each(function() {
        arrayCodigos.push($(this).val());
	});
	
	mostrarLoadingConsultarReg('cambiarEstadoRegistradores');
	url = "abrirSeleccionEstadoRecuperarTramite.action"
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-400', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 400,
				height: 250,
				buttons : {
					Seleccionar : function() {
						var estado = document.getElementById("estado").value;
						doPostValidate('formData', "cambiarEstadoRecuperarTramite.action?cambioEstado=" + estado + "&idRegistradores=" + arrayCodigos, '', '');
					},
					Cancelar : function() {
						$(this).dialog("close");
						ocultarLoadingConsultarReg('cambiarEstadoRegistradores','Cambiar Estado');
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

function cambiarAFinalizadoLista(){
	if (numChecked() == 0) {
		alert("Debe seleccionar algún tr\u00E1mite");
		return false;
	}
	
	if (confirm("¿Está seguro que desea cambiar a estado Finalizado el/los trámite/s seleccionado/s? \n Esta modificación se realizará bajo su responsabilidad teniendo en cuenta que no se ha enviado la factura y el trámite no seguirá el flujo establecido.")) {
		iniciarProcesando('btnCambiarAFinalizado','loadingImage');
		document.formData.action = "cambiarAFinalizadoListaRecuperarTramite.action";
		document.formData.submit();
	}
}


function consultaEvTramiteRegistro(idTramiteRegistro){
	
	url = "inicioConsultaEvTramiteRg.action?idTramiteRegistro=" + idTramiteRegistro;
	var $dest = $("#divEmergentePopUp");
	var $idForm = $("#formData");
	$.post(url, $idForm.serialize(), function(data) {

		if (data.toLowerCase().indexOf("<html")<0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : { my: 'right-60', at: 'right' },
				overflow: 'hidden',
				resizable: false,
				appendTo: $idForm,
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width: 900,
				height: 450,
				buttons : {
					Cerrar : function() {
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


function validarTramiteRegistradores(idBoton, idForm, url){

	iniciarProcesando(idBoton,'loadingImage');
	
	doPost(idForm, url, null);
	
}


function habilitarPorTipoDatoBancario(){
	var $checkTipoNueva = $("#idTipoDatoBancario0");
	var $checkTipoExistente = $("#idTipoDatoBancario1");
	if($('#estado').val() != "" && $('#estado').val() != "1" && $('#estado').val() != "5" && $('#estado').val() != "28"){
		if($("#idDatosBancarios").val() != null && $("#idDatosBancarios").val() != ""){
			$checkTipoExistente.prop("checked", true);
			$("#idComboExistente").val($("#idDatosBancarios").val());
			$('#idComboExistente').prop("disabled", true);
		}else if($("#idNombreTitularDatBancarios").val() != null && $("#idNombreTitularDatBancarios").val() != ""){
			$checkTipoNueva.prop("checked", true);
			$("#idSeleccionDatosBancFavoritos").hide();
			$("#idFavoritoDatBancario").prop("disabled", true);
		}
		deshabilitarCamposCuentaBancaria();
		 $('#idTipoDatoBancario0').prop("disabled", true);
		 $('#idTipoDatoBancario1').prop("disabled", true);
		 $('#idFormaPago1').prop("disabled", true);
		 $('#idFormaPago2').prop("disabled", true);
		 $('#idFormaPago3').prop("disabled", true);
	}else{
		if($checkTipoNueva.is(':checked')){
			 $("#divDatosBancarios").show();
			 $("#idSeleccionDatosBancFavoritos").hide();
			 $("#idFavoritoDatBancario").prop("disabled", false);
			 habilitarCamposCuentaBancaria();
			 if($("#idDatosBancarios").val() != null && $("#idDatosBancarios").val() != ""){
				 limpiarCamposCuentaBancaria();
			 }
			
		}else if($checkTipoExistente.is(':checked')){
			 $("#divDatosBancarios").hide();
			 $("#idSeleccionDatosBancFavoritos").show();
			 $("#idFavoritoDatBancario").prop("disabled", true);
			 if($("#idDatosBancarios").val() != null && $("#idDatosBancarios").val() != ""){
				$("#idFavoritoDatBancario").prop("disabled", false);
				deshabilitarCamposCuentaBancaria();
				$("#divDatosBancarios").show();
				$("#idComboExistente").val($("#idDatosBancarios").val());
			 }
		}else{
			 $("#divDatosBancarios").hide();
			 $("#idSeleccionDatosBancFavoritos").hide();
			 $("#idFavoritoDatBancario").prop("disabled", true);
			 limpiarCamposCuentaBancaria();
		}
	}
}

function habilitarAliasCuentaBancaria(){
	if ($("#idFavoritoDatBancario").is(':checked')){
		$("#idDescripcionDatBancarios").show();
		$("#labelDescripcionDatBanc").show();			
	}else{
		$("#idDescripcionDatBancarios").hide();
		$("#labelDescripcionDatBanc").hide();
	}
}


function borrarCuentaSiFavorito(){

		if( $("#idEntidad").val()== "****"){
			$("#idIban").val("");
			$("#idEntidad").val("");
			$("#idOficina").val("");
			$("#idDC").val("");
			$("#idNumeroCuentaPago").val("");
		}
}

function habilitarPorFormaDePago(){
	var $checkCuenta = $("#idFormaPago1");
	var $checkTransferencia = $("#idFormaPago2");
	var $checkIdentificCorpme = $("#idFormaPago3");
	
	if($checkCuenta.is(':checked')){
		 $("#divCuentaBancaria").show();
		 $("#divIdentificacionCorpme").hide();
		 habilitarPorTipoDatoBancario();
	}else if($checkTransferencia.is(':checked')){
		 $("#divCuentaBancaria").hide();
		 $("#divIdentificacionCorpme").hide();
		 limpiarCamposCuentaBancaria();
		 habilitarPorTipoDatoBancario();
	}else if($checkIdentificCorpme.is(':checked')){
		 $("#divCuentaBancaria").hide();
		 $("#divIdentificacionCorpme").show();
		 limpiarCamposCuentaBancaria();
		 habilitarPorTipoDatoBancario();
	}else{
		 $("#divCuentaBancaria").hide();
		 $("#divIdentificacionCorpme").hide();
		 limpiarCamposCuentaBancaria();
	}
}


function cargarDatosBancariosFavoritos(){
	var idDatosBancarios = $("#idComboExistente").val();

	var $form = $("#formData");
	
	var url = "cargarDatosBancariosTramiteRegistroMd6.action?idDatosBancarios="+ idDatosBancarios + "#formaPago" 

	$form.attr("action", url).trigger("submit");
}

function limpiarCamposCuentaBancaria(){
	$("#idNombreTitularDatBancarios").val("");
	$("#cuentaSelectProvinciaId").val("");
	$("#cuentaSelectMunicipioId").val("");
	document.getElementById("cuentaSelectMunicipioId").options[0] = new Option("Seleccione municipio", "");
	document.getElementById("cuentaSelectMunicipioId").selectedIndex = "";
	
	$("#idDescripcionDatBancarios").val("");
	$("#idIban").val("");
	$("#idEntidad").val("");
	$("#idOficina").val("");
	$("#idDC").val("");
	$("#idNumeroCuentaPago").val("");
	$("#idFavoritoDatBancario").prop("checked",false);
//	$("#idDatosBancarios").val("");
//	$("#idComboExistente").val("");
	document.getElementById("idComboExistente").selectedIndex = "";
}

function limpiarCamposTransferencia(){
	$("#diafechaPago").val("");
	$("#mesfechaPago").val("");
	$("#aniofechaPago").val("");
	
	$("#idTransferencia").val("");
	$("#cifEmisor").val("");
	$("#serieFactura").val("");
	$("#ejercicioCuenta").val("");
	$("#numFactura").val("");
	
	$("#diaFecFactura").val("");
	$("#mesFecFactura").val("");
	$("#anioFecFactura").val("");
}

function deshabilitarCamposCuentaBancaria(){
	 $("#idNombreTitularDatBancarios").prop("disabled", true);
	 $("#cuentaSelectProvinciaId").prop("disabled", true);
	 $("#cuentaSelectMunicipioId").prop("disabled", true);
	 $("#idDescripcionDatBancarios").prop("disabled", true);
	 $("#idIban").prop("disabled", true);
	 $("#idEntidad").prop("disabled", true);
	 $("#idOficina").prop("disabled", true);
	 $("#idDC").prop("disabled", true);
	 $("#idNumeroCuentaPago").prop("disabled", true);
	 $("#idFavoritoDatBancario").prop("disabled", true);
}

function habilitarCamposCuentaBancaria(){
	 $("#idNombreTitularDatBancarios").prop("disabled", false);
	 $("#cuentaSelectProvinciaId").prop("disabled", false);
	 $("#cuentaSelectMunicipioId").prop("disabled", false);
	 $("#idDescripcionDatBancarios").prop("disabled", false);
	 $("#idIban").prop("disabled", false);
	 $("#idEntidad").prop("disabled", false);
	 $("#idOficina").prop("disabled", false);
	 $("#idDC").prop("disabled", false);
	 $("#idNumeroCuentaPago").prop("disabled", false);
	 $("#idFavoritoDatBancario").prop("disabled", false);
}

function mostrarVentana(evento, indice, lineas) {
	var targ;
	var e = evento;
	if (!evento) {
		e = window.event;
	}
	if (e.target) {
		targ = e.target;
	} else {
		targ = e.srcElement;
	}
	document.getElementById("ventana").innerHTML = document.getElementById("detalles" + indice).innerHTML;
	document.getElementById("ventana").style.top = (targ.y + 23) + "px";
	document.getElementById("ventana").style.left = (targ.x - 18) + "px";
	document.getElementById("ventana").style.height = "auto";
	document.getElementById("ventana").style.visibility = "visible";
}

function ocultarVentana() {
	document.getElementById("ventana").style.visibility = "hidden";
	document.getElementById("ventana").innerHTML = "";
}

function mostrarRegistro() {
	var tipoRegistro =$('#tipoRegistro').val();
	if (tipoRegistro == ""){
		document.getElementById("registro").selectedIndex=0;
		$("#registro").attr('style', 'display:none');
	}else{
		$("#registro").attr('style', 'display:block');
	}
}

function buscarRegistro(){
	
	var tipoRegistro =$('#tipoRegistro').val();
	if (tipoRegistro == ""){
		document.getElementById("registro").selectedIndex=0;
		$("#registro").attr('style', 'display:none');
	}else{
		$("#registro").attr('style', 'display:block');
		
		var tipoRegistro = $('#tipoRegistro').val();

		var url="buscarRegistroRegistradoresAjax.action?tipoRegistro=" + tipoRegistro;
		//Hace la llamada a ajax
		if (window.XMLHttpRequest) { // Non-IE browsers
			reqRegistros = new XMLHttpRequest();
			reqRegistros.onreadystatechange = rellenarRegistrosBuscados;
			reqRegistros.open("POST", url, true);
			reqRegistros.send(null);
		} else if (window.ActiveXObject) { // IE      
			reqRegistros = new ActiveXObject("Microsoft.XMLHTTP");
			if (reqRegistros) {
				reqRegistros.onreadystatechange = rellenarRegistrosBuscados;
				reqRegistros.open("POST", url, true);
				reqRegistros.send();
			}
		}
	}
}

//Callback function
function rellenarRegistrosBuscados() {
	document.getElementById("registro").options.length = 0;
	if (reqRegistros.readyState == 4) { // Complete
		if (reqRegistros.status == 200) { // OK response
			var textToSplitRegistros = reqRegistros.responseText;
			
			//Split the document
			var returnElementRegistro=textToSplitRegistros.split("||");
			
      		document.getElementById("registro").options[0] = new Option("TODOS", "");		
			//Process each of the elements
			for ( var i = 0 ; i < returnElementRegistro.length ; i ++ ) {
				value = returnElementRegistro[i].split(";");
				if (value != '') {
					document.getElementById("registro").options[i+1] = new Option(value[0], value[1]);
				}
			}	
		}
	}
}


function mostrarIrpf() {
	var aplicaIrpf =$('#aplicaIrpf').val();
	if (aplicaIrpf == "S"){
		$("#porcentajeIrpf").attr('style', 'display:block');
		$("#labelPorcentajeIrpf").attr('style', 'display:block');
	}else{
		$("#porcentajeIrpf").attr('style', 'display:none');
		$("#porcentajeIrpf").val("");
		$("#labelPorcentajeIrpf").attr('style', 'display:none');
	}
}

function construirFirmarAcuse(estadoAcusePendiente, idRegistroFueraSecuencia){

	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "construirFirmarAcuse" + accion + ".action?idRegistroFueraSecuencia="+idRegistroFueraSecuencia+"&estadoAcusePendiente="+estadoAcusePendiente + "#" + pestania
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");
}

function construirConfirmacionFactura(idFactura){

	var $form = $("#formData");
	
	$("#idTransferencia").val(document.getElementById("idTransferencia_"+idFactura).value);
	$("#idFactura").val(idFactura);
	
	$("#diafechaPago").val(document.getElementById("diafechaPago_"+idFactura).value);
	$("#mesfechaPago").val(document.getElementById("mesfechaPago_"+idFactura).value);
	$("#aniofechaPago").val(document.getElementById("aniofechaPago_"+idFactura).value);
	
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "confirmarPagoFactura" + accion + ".action#" + pestania;
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");
}

function trimCamposInputs(){
	 $('input[type="text"]').blur(function(){
	     this.value = $.trim(this.value);
	 });
}

function duplicarRBM() {
	
	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "duplicar" + accion + ".action#" + pestania
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");

}

function subsanarRBM() {
	var $form = $("#formData");
	
	var pestania = obtenerPestaniaSeleccionada();
	
	var accion = calcularContrato();

	var url = "subsanar" + accion + ".action#" + pestania
	
	$('input[type=button]').attr('disabled', true);

	$form.attr("action", url).trigger("submit");
}
