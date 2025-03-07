 function mensajeInformacion(boton){
	
	var diferentesTiposTramites = false; 
	 
	 if (numChecked() == 0) {
			alert("Debe seleccionar algun tr\u00E1mite");
			return false;
		}

	 var tipoAnt = '';
	 $('input:checkbox[name="listaChecksConsultaTramite"]').each(function( index ) {
		  if ($(this).attr('checked')) {
			  var tipoAux = $("#tipoTramite"+$(this).attr('value'));
			  if (tipoAnt == '') {
				  tipoAnt = tipoAux.attr('value');
			  } else if (tipoAnt != tipoAux.attr('value')) {
				  alert("Todos los tramites seleccionados deben ser del mismo tipo");
				  diferentesTiposTramites = true;
			  }
		  }
	 });
	 
	 if (diferentesTiposTramites) {
		 return false;
	 }

		var creditos = document.getElementById("numCreditosSolicitados");
		var contrato = document.getElementById("idContrato");
		var creditosDisponibles = document.getElementById("numCreditosDisp");

		if (tipoAnt != "T8") {
			if (parseFloat(creditos.value) > parseFloat(creditosDisponibles.value)) {
				alert("No tienes cr\u00E9ditos suficientes");
				return false;
			}
		}


		var impreso = document.getElementsByName("impreso");
		var nombreImpreso = "";
		var j = 0;
		while (impreso[j] != null) {
			if (impreso[j].checked) {
				if (impreso[j].disabled) {
					alert("Seleccione un tipo permitido para imprimir.");
					return false;
				}
				nombreImpreso = impreso[j].value;
				break;
			}
			j++;
		}

		if (nombreImpreso == "PegatinasEtiquetaMatricula") {
			if (isNaN(etiquetasTramite.value) || etiquetasTramite.value == 0) {
				alert("Introduzca un valor correcto en el n\u00FAmero de etiquetas.");
				return false;
			}
			if (isNaN(etiquetasFila.value) || etiquetasFila.value == 0) {
				alert("Introduzca un valor correcto en el n\u00FAmero de etiquetas por fila.");
				return false;
			}
			if (isNaN(etiquetasColumna.value) || etiquetasColumna.value == 0) {
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
		}

		if (nombreImpreso == "PDF417") {

			var textoAlert = String.fromCharCode(191)
				+ "Est\u00E1 seguro de que desea imprimir los tr\u00E1mites seleccionados?"
				+ "\n\n\tCr\u00E9ditos disponibles: "
				+ creditosDisponibles.value + "\n\tCr\u00E9ditos necesarios: "
				+ creditos.value;

			if (tipoAnt == "T1"){
				textoAlert += "\n\n\nEl tr\u00e1mite ser\u00e1 enviado a DGT con los datos con los que fue validado.\n\n\nEsta usted procediendo a realizar tr\u00E1mites de matriculaci\u00F3n de vehiculo/s, para la realizacion de tal/es tr\u00E1mites es obligatorio que el Gestor Administrativo verifique de forma correcta y tenga en su poder la ficha t\u00E9cnica del veh\u00EDculo, as\u00ED como pagada y/o sellada la tasa de tr\u00E1fico,  el modelo 576 del impuesto sobre veh\u00EDculos de transporte y el impuesto de veh\u00EDculos de tracci\u00F3n mec\u00E1nica de la localidad correspondiente.  La realizaci\u00F3n de la operaci\u00F3n de matriculaci\u00F3n sin cumplir todos los requisitos de la encomienda de gesti\u00F3n suponen una infracci\u00F3n muy grave que puede ser sancionada con la inhabilitaci\u00F3n profesional del gestor administrativo, ademas de no estar cubierta tal contingencia por la p\u00F3liza de seguros de responsabilidad civil, en cuyo caso el infractor sera responsable de las contingencias econ\u00F3micas que surjan. Todo ello sin perjuicio de las responsabilidades administrativas y jur\u00EDdico penales en que puedan incurrir de forma directa los part\u00EDcipes de dicho tr\u00E1mite."
					+ "\n\n"
					+ "En el caso de cumplir tales requisitos pulse continuar y ultime el proceso de matriculaci\u00F3n, en el caso de no cumplir los requisitos alguno de los veh\u00EDculos que en este proceso se matriculan, se debera cancelar el proceso telem\u00E1tico de matriculaci\u00F3n referido a dichos veh\u00EDculos hasta el correcto cumplimiento de los mismos.";
			}

			if (confirm(textoAlert)) {
			} else {
				return false;
			}
		}
		mostrarLoadingImprimir(boton);
}