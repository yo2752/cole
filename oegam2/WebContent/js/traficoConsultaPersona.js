
var opciones_ventanaEvPer = 'width=850,height=450,top=050,left=200';

function guardarPersona() {
	if (document.getElementById("apellido1RazonSoc").value == "") {
		alert("Primer Apellido / Raz\u00f3n Social obligatorio");
		return false;
	}
	
	document.formData.action="modificacionDetallePersona.action";
	document.formData.submit();	
}

function guardarInterviniente() {
	document.formData.action="modificacionDetalleInterviniente.action";
	document.formData.submit();	
}


function consultaEvolucionPersona(nif, numColegiado) {
	if (nif == null || nif == "") {
		return alert("No hay ninguna persona guardada en el tr\u00E1mite");
	}
	var $dest = $("#divEmergentePopUp");
	var url = "inicioConsultaEvolucionPersona.action?nif=" + nif + "&numColegiado=" + numColegiado;
	$.post(url, function(data) {
		if (data.toLowerCase().indexOf("<html") < 0) {
			$dest.empty().append(data).dialog({
				modal : true,
				position : {
					my : 'right',
					at : 'right'
				},
				show : {
					effect : "blind",
					duration : 300
				},
				dialogClass : 'no-close',
				width : 950,
				height : 500,
				buttons : {
					Cerrar : function() {
						$(this).dialog("close");
					}
				}
			});
		} else {
			var newDoc = document.open("text/html", "replace");
			newDoc.write(data);
			newDoc.close();
		}
		$(".ui-dialog-titlebar").hide();
	}).always(function() {
		unloading("loadingImage");
	});
}

function recuperarPersona(nif, numColegiado){
	document.location.href='detalleDetallePersona.action?nif=' + nif + '&numColegiado=' + numColegiado;
}

function recuperarInterviniente(nif, numColegiado, numExpediente, tipoInterviniente){
	document.location.href='detalleDetalleInterviniente.action?nif=' + nif + '&numColegiado=' + numColegiado + '&numExpediente=' + numExpediente + '&tipoInterviniente=' + tipoInterviniente;
}

function consultaListaExpedientesDireccion(nif, numColegiado, idDireccion) {
	document.formData.action = 'listarExpedientesDetallePersona.action?nif=' + nif + '&numColegiado=' + numColegiado + '&idDireccion=' + idDireccion;
	document.formData.submit();
}

function documentoIndefinidoPersona(idCheckIndefinido, idDiaDni, idMesDni, idAnioDni, idFecha) {
	var check = document.getElementById(idCheckIndefinido).checked;
	
	if(check){
		document.getElementById(idDiaDni).readOnly = true;
		document.getElementById(idMesDni).readOnly = true;
		document.getElementById(idAnioDni).readOnly = true;
		
		document.getElementById(idDiaDni).style.backgroundColor = '#EEEEEE';
		document.getElementById(idMesDni).style.backgroundColor = '#EEEEEE';
		document.getElementById(idAnioDni).style.backgroundColor = '#EEEEEE';
		
		document.getElementById(idFecha).style.display="none";
	} else {
		document.getElementById(idDiaDni).readOnly = false;
		document.getElementById(idMesDni).readOnly = false;
		document.getElementById(idAnioDni).readOnly = false;
		
		document.getElementById(idDiaDni).style.backgroundColor = 'white';
		document.getElementById(idMesDni).style.backgroundColor = 'white';
		document.getElementById(idAnioDni).style.backgroundColor = 'white';
		
		document.getElementById(idFecha).style.display="block";
	}
}

function documentoAlternativoPersona(idCheckAlternativo, idDocAlternativo, idDiaAlter, idMesAlter, idAnioAlter, idFechaAlter){
	var check = document.getElementById(idCheckAlternativo).checked;
	
	if(check) {
		document.getElementById(idDocAlternativo).readOnly = false;
		document.getElementById(idDocAlternativo).style.backgroundColor = 'white';
		
		document.getElementById(idDiaAlter).readOnly = false;
		document.getElementById(idMesAlter).readOnly = false;
		document.getElementById(idAnioAlter).readOnly = false;
		
		document.getElementById(idDiaAlter).style.backgroundColor = 'white';
		document.getElementById(idMesAlter).style.backgroundColor = 'white';
		document.getElementById(idAnioAlter).style.backgroundColor = 'white';
		
		document.getElementById(idFechaAlter).style.display="block";
	} else {
		document.getElementById(idDocAlternativo).readOnly = true;
		document.getElementById(idDocAlternativo).style.backgroundColor = '#EEEEEE';
		
		document.getElementById(idDiaAlter).readOnly = true;
		document.getElementById(idMesAlter).readOnly = true;
		document.getElementById(idAnioAlter).readOnly = true;
		
		document.getElementById(idDiaAlter).style.backgroundColor = '#EEEEEE';
		document.getElementById(idMesAlter).style.backgroundColor = '#EEEEEE';
		document.getElementById(idAnioAlter).style.backgroundColor = '#EEEEEE';
		
		document.getElementById(idFechaAlter).style.display="none";
	}
}

function cambioConceptoInterviniente(){
	if(document.getElementById("idConceptoInterviniente").value=="TUTELA O PATRIA POTESTAD"){
		document.getElementById("idMotivoInterviniente").disabled=false;
	} else {
		document.getElementById("idMotivoInterviniente").disabled=true;
		document.getElementById("idMotivoInterviniente").value="-1";
	}
}

function cambiarElementosPorPaginaConsultaPersona() {
	var $dest = $("#displayTagDivPersona");
	$.ajax({
		url:"navegarConsultaEvolucionPersona.action",
		data:"resultadosPorPagina="+ $("#idResultadosPorPaginaPersona").val(),
		type:'POST',
		success: function(data){
			filteredResponse =  $(data).find($dest.selector);
			if(filteredResponse.size() == 1){
				$dest.html(filteredResponse);
			}
			$dest.displayTagAjax();
		},
		error : function(xhr, status) {
	        alert('Ha sucedido un error a la hora de cargar la tabla con las evoluciones de personas.');
	    }
	});
}
