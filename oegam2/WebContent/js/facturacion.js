function buscarPersona(nifFacturacion){
	
	$.ajax({
		url:"buscarPersonaFacturacion.action",
		data:"nif="+$("#"+ nifFacturacion).val(),
		dataType:'json',
		type:'POST',
		success: function(data){
			
			var datosFact = data['datosFacturacion'];
			
			$("#tipoPersona").val(datosFact.persona.estado);
			$("#nombre").val(datosFact.persona.nombre);
			$("#apellido1").val(datosFact.persona.apellido1RazonSocial);
			$("#apellido2").val(datosFact.persona.apellido2);
			$("#idProvinciaFacturacion").val(datosFact.direccion.municipio.idProvincia);
			recargarComboMunicipios('idProvinciaFacturacion','idMunicipioFacturacion','municipioSeleccionadoFacturacion');
			$("#idMunicipioFacturacion").val(datosFact.direccion.municipio.idMunicipio);
			$("#municipioSeleccionadoFacturacion").val(datosFact.direccion.municipio.idMunicipio);
			recargarComboPueblos('idProvinciaFacturacion','municipioSeleccionadoFacturacion','idPuebloFacturacion', 'puebloSeleccionadoFacturacion');
			$("#idPuebloFacturacion").val(datosFact.direccion.municipio.nombre);
			$("#puebloSeleccionadoFacturacion").val(datosFact.direccion.municipio.nombre);
			$("#tipoViaSeleccionadaFacturacion").val(datosFact.direccion.tipoVia.idTipoVia);
			recargarComboTipoVia('idProvinciaFacturacion','tipoViaSeleccionadaFacturacion','tipoViaSeleccionadaFacturacion');
			$("#cpFacturacion").val(datosFact.direccion.codPostal);
			$("#nombreViaFacturacion").val(datosFact.direccion.nombreVia);
			$("#numeroDireccionFacturacion").val(datosFact.direccion.numero);
			$("#letraDireccionFacturacion").val(datosFact.direccion.letra);
			$("#escaleraDireccionFacturacion").val(datosFact.direccion.escalera);
			$("#pisoDireccionFacturacion").val(datosFact.direccion.piso);
			$("#puertaDireccionFacturacion").val(datosFact.direccion.puerta);
			$("#bloqueDireccionFacturacion").val(datosFact.direccion.bloque);
			$("#kmDireccionFacturacion").val(datosFact.direccion.bloque);
			$("#hmDireccionFacturacion").val(datosFact.direccion.hm);
			$("#tipoViaFacturacion").val(datosFact.direccion.tipoVia.idTipoVia);
			$("#datosFacturacionIdDireccion").val(datosFact.direccion.idDireccion);
			
			recargarNombreVias('idProvinciaFacturacion', 'municipioSeleccionadoFacturacion', 'tipoViaSeleccionadaFacturacion','nombreViaFacturacion', viaFacturacionDuplicado);
		}
	});
}