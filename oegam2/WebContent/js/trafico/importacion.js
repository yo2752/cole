	/**
	 * Funciones JS relacionadas con Importacion de tramites
	 */
	
	/** ************************** IMPORTAR ETIQUETAS MATRICULACION ************* */
	var importarPulsado = false;
	
	// ACEPTAR ENVIO FICHERO
	function aceptarImportarEtiquetas() {
		if (importarPulsado) {
			alert(MSG_PETICION_REALIZADA);
			return false;
		}
		try {
			if (document.getElementById("fileUpload").value.length > 0) {
				importarPulsado = true;
				// document.forms[0].action="etiquetasImportar!importarEtiquetas.action";
				document.formData.action = "importarEtiquetasMatricula.action";
				document.formData.submit();
			} else {
				alert(ERROR_RUTA_FICHERO_VACIA);
			}
		} catch (e) {
			alert(ERROR_RUTA_FICHERO_NO_VALIDA);
		}
	}
	
	// CANCELAR ENVIO FICHERO
	function cancelarImportarEtiquetas() {
		document.location.href = "inicioEtiquetasMatricula.action";
		return true;
	}
	
	/**
	 * ************************** IMPORTAR FICHERO DGT * **********************************
	 */
	
	// ACEPTAR IMPORTAR FICHERO
	function aceptarImportarDGT() {
		if (importarPulsado) {
			alert(MSG_PETICION_REALIZADA);
			return false;
		}
		try {
			archivo = document.getElementById("fichero").value;
			if (archivo.length > 0) {
				extension = (archivo.substring(archivo.lastIndexOf(".")))
						.toLowerCase();
	
				// COMPROBAR EXTENSIONES DE LOS ARCHIVOS, EL RADIO Y MANDAR AL
				// ACTION CORRESPONDIENTE
				if ((".dgt" == extension) && document.getElementById("dgt").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "dgt";
					document.formData.action = "importarDGTImportar.action";
					document.formData.submit();
					loadingImport();
			
				} else if ((".xml" == extension)
						&& null != document.getElementById("xml_transmision")
						&& document.getElementById("xml_transmision").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "transmision";
					document.formData.action = "importarTransmisionActualImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xml" == extension)
						&& null != document
								.getElementById("xml_transmision_electronica")
						&& document.getElementById("xml_transmision_electronica").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "transmision";
					document.formData.action = "importarTransmisionElectronicaImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xml" == extension)
						&& null != document.getElementById("xml_cet")
						&& document.getElementById("xml_cet").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "transmision";
					document.formData.action = "importarCETImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xml" == extension)
						&& null != document.getElementById("xml_baja")
						&& document.getElementById("xml_baja").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "baja";
					document.formData.action = "importarBajaImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xml" == extension)
						&& null != document.getElementById("xml_matriculacion_matw")
						&& document.getElementById("xml_matriculacion_matw").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "matriculacion_matw";
					document.formData.action = "importarMATEGEMatwImportar.action";
					document.formData.submit();
					loadingImport();
				/* INICIO MANTIS 0013149: importar duplicados */
				} else if ((".xml" == extension)
						&& null != document.getElementById("xml_duplicado")
						&& document.getElementById("xml_duplicado").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "xml_duplicado";
					document.formData.action = "importarDuplicadoImportar.action";
					document.formData.submit();
					loadingImport();
				/* FIN MANTIS 0013149 */
				}else if (null != document.getElementById("archivo_solicitud")
						&& document.getElementById("archivo_solicitud").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "solicitud";
					document.formData.action = "importarAVPOImportar.action";
					document.formData.submit();
					loadingImport();
				// Mantis 14125. David Sierra
				}else if (null != document.getElementById("xml_archivo_solicitud")
						&& document.getElementById("xml_archivo_solicitud").checked) {
					importarPulsado = true;
					document.getElementById("tipoFichero").value = "xml_archivo_solicitud";
					document.formData.action = "importarSolicitudImportar.action";
					document.formData.submit();
					loadingImport();
				// Fin Mantis 14125
				} else if ((".xls" == extension)
						&& document.getElementById("xls_baja").checked) {
					document.formData.action = "importarExcelBajasImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xls" == extension)
						&& document.getElementById("xls_duplicado").checked) {
					document.formData.action = "importarExcelDuplicadosImportar.action";
					document.formData.submit();
					loadingImport();
				} else if ((".xls" == extension)
						&& document.getElementById("xls_cambioServicio").checked) {
					document.formData.action = "importarExcelCambioServicioImportar.action";
					document.formData.submit();
					loadingImport();
				}else if ((".txt" == extension)
						&& document.getElementById("importarInteve").checked) {
					document.formData.action = "importarInteveImportar.action";
					document.formData.submit();
					loadingImport();
				}else if (document.getElementById("PegatinasEtiquetaMatricula").checked) {
	
					var etiquetasTramite = document
							.getElementById("etiquetasTramite");
					var etiquetasFila = document.getElementById("etiquetasFila");
					var etiquetasColumna = document
							.getElementById("etiquetasColumna");
					var filaPrimer = document.getElementById("filaPrimer");
					var columnaPrimer = document.getElementById("columnaPrimer");
					var margenSup = document.getElementById("margenSup");
					var margenInf = document.getElementById("margenInf");
					var margenDcho = document.getElementById("margenDcho");
					var margenIzqd = document.getElementById("margenIzqd");
					var horizontal = document.getElementById("horizontal");
					var vertical = document.getElementById("vertical");
	
					// Compruebo si es un valor numerico
					if (isNaN(etiquetasTramite.value)
							|| etiquetasTramite.value == 0) {
						alert("Introduzca un valor correcto en el n\u00FAmero de etiquetas.");
						return false;
					}
					if (isNaN(etiquetasFila.value) || etiquetasFila.value == 0) {
						alert("Introduzca un valor correcto en el n\u00FAmero de etiquetas por fila.");
						return false;
					}
					if (isNaN(etiquetasColumna.value)
							|| etiquetasColumna.value == 0) {
						alert("Introduzca un valor correcto en el n\u00FAmero de etiquetas por columna.");
						return false;
					}
					if (isNaN(filaPrimer.value) || filaPrimer.value == 0) {
						alert("Introduzca un valor correcto en la la primera fila.");
						return false;
					}
					if (isNaN(columnaPrimer.value) || columnaPrimer.value == 0) {
						alert("Introduzca un valor correcto en la primera columna.");
						return false;
					}
	
					// Decimales
					if (!validaNumero(margenSup.value)) {
						alert("Introduzca un valor correcto para el margen superior.");
						return false;
					}
					if (!validaNumero(margenInf.value)) {
						alert("Introduzca un valor correcto para el margen inferior.");
						return false;
					}
					if (!validaNumero(margenDcho.value)) {
						alert("Introduzca un valor correcto para el margen derecho.");
						return false;
					}
					if (!validaNumero(margenIzqd.value)) {
						alert("Introduzca un valor correcto para el margen izquierdo.");
						return false;
					}
					if (!validaNumero(horizontal.value)) {
						alert("Introduzca un valor correcto para el espaciado horizontal.");
						return false;
					}
					if (!validaNumero(vertical.value)) {
						alert("Introduzca un valor correcto para el espaciado vertical.");
						return false;
					}
	
					document.formData.action = "importarPegatinasImportar.action";
					document.formData.submit();
					loadingImportThenDeactivate();
				} else {
					alert(ERROR_EXTENSION_FICHERO);
				}
	
			} else {
				alert(ERROR_RUTA_FICHERO_VACIA);
			}
		} catch (e) {
			alert(ERROR_RUTA_FICHERO_NO_VALIDA);
		}
	}
	
	// Muestra u oculta los mensajes de informacion de la importacion
	function cambiarMostrarInfo(imagen) {
		var info = document.getElementById("mensajesInfo");
		if (info != null) {
			if (info.style.display == "block") {
				info.style.display = "none";
				imagen.src = "img/plus.gif";
			} else {
				info.style.display = "block";
				imagen.src = "img/minus.gif";
			}
		}
		cambiarMostrarResumen();
	}
	
	// Muestra u oculta los mensajes de error de la importacion
	function cambiarMostrarError(imagen) {
		var errores = document.getElementById("mensajesError");
		if (errores != null) {
			if (errores.style.display == "block") {
				errores.style.display = "none";
				imagen.src = "img/plus.gif";
			} else {
				errores.style.display = "block";
				imagen.src = "img/minus.gif";
			}
		}
		cambiarMostrarResumen();
	}
	
	function cambiarMostrarResumen() {
		var errores = document.getElementById("mensajesError");
		var info = document.getElementById("mensajesInfo");
		if ((errores != null && errores.style.display == "block")
				|| (info != null && info.style.display == "block")) {
			document.getElementById("idResumenImportacion").style.display = "block";
		} else
			document.getElementById("idResumenImportacion").style.display = "none";
	}
	
	function loadingDuplicar() {
		document.getElementById("idBotonGuardar").disabled = "true";
		document.getElementById("idBotonGuardar").style.color = "#6E6E6E";
		document.getElementById("idBotonDuplicar").disabled = "true";
		document.getElementById("idBotonDuplicar").style.color = "#6E6E6E";
		document.getElementById("idBotonVolver").disabled = "true";
		document.getElementById("idBotonVolver").style.color = "#6E6E6E";
		document.getElementById("idBotonDuplicar").value = "Duplicando";
		document.getElementById("loadingImage").style.display = "block";
		document.getElementById("idBotonValidarMatricular").disabled = "true";
		document.getElementById("idBotonValidarMatricular").style.color = "#6E6E6E";
	
	}
	
	// DESHABILITA EL BOTON DE ACEPTAR, Y HACE VISIBLE LA IMAGEN DE LOADING
	function loadingImport() {
		document.getElementById("bAceptar").disabled = "true";
		document.getElementById("bAceptar").style.color = "#6E6E6E";
		document.getElementById("bAceptar").value = "Importando";
		document.getElementById("loadingImage").style.display = "block";
	}
	
	function loadingImportThenDeactivate() {
		document.getElementById("bAceptar").disabled = "true";
		document.getElementById("bAceptar").value = "Importando";
		document.getElementById("loadingImage").style.display = "block";
		;
		setTimeout("deactivateLoadingImport()", 3500);
	}
	function deactivateLoadingImport() {
		document.getElementById("loadingImage").style.display = "none";
		document.getElementById("bAceptar").disabled = false;
		document.getElementById("bAceptar").value = "Enviar";
	}
	
	//CANCELAR ENVIO FICHERO
	function cancelarImportarDGT() {
		// document.location.href =
		// "traficoTramiteMatriculacionList!buscarTraficoTramite.action";
		document.location.href = "inicioImportarDGT.action";
		return true;
	}
	
	//ACEPTAR IMPORTAR FICHERO
	function aceptarImportarTasas() {
		if (importarPulsado) {
			alert(MSG_PETICION_REALIZADA);
			return false;
		}
		try {
			archivo = document.getElementById("ficheroTasas").value;
			if (archivo.length > 0) {
				extension = (archivo.substring(archivo.lastIndexOf(".")))
						.toLowerCase();
				if (".txt" == extension) {
					importarPulsado = true;
					document.formData.action = "importarTasasImportarTasas.action";
					document.formData.submit();
					loadingImport();
				} else {
					alert(ERROR_EXTENSION_FICHERO);
				}
			} else {
				alert(ERROR_RUTA_FICHERO_VACIA);
			}
		} catch (e) {
			alert(ERROR_RUTA_FICHERO_NO_VALIDA);
		}
	}
	
	// CANCELAR ENVIO FICHERO
	function cancelarImportarTasas() {
		document.location.href = "inicioImportarTasas.action";
		return true;
	}