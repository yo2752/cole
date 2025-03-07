<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
<head>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<link href="css/estilos.css" rel="stylesheet" type="text/css"
	media="screen" />
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Consulta Dirección:</title>

<script type="text/javascript">
	function actualizarPadre(idProvincia, provincia, idMunicipio, municipio,
			idTipoVia, nombreVia, numero, pueblo, codPostal, letra, escalera,
			bloque, puerta, planta, km, hm, idDireccion, pestania) {
		if (pestania=="TitularBaja"||pestania=="TitularDuplicado"){
			window.opener.document.formData.idProvinciaTitular.value = idProvincia;
			window.opener.document.formData.idMunicipioTitular.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoTitular.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoTitular.value = pueblo;
			window.opener.document.formData.idPuebloTitular.value = pueblo;
			window.opener.document.formData.cpTitular.value = codPostal;
			window.opener.document.formData.tipoViaTitular.value = idTipoVia;
			window.opener.document.formData.nombreViaTitular.value = nombreVia;
			window.opener.document.formData.numeroDireccionTitular.value = numero;
			window.opener.document.formData.letraDireccionTitular.value = letra;
			window.opener.document.formData.escaleraDireccionTitular.value = escalera;
			window.opener.document.formData.bloqueDireccionTitular.value = bloque;
			window.opener.document.formData.puertaDireccionTitular.value = puerta;
			window.opener.document.formData.pisoDireccionTitular.value = planta;
			window.opener.document.formData.kmDireccionTitular.value = km;
			window.opener.document.formData.hmDireccionTitular.value = hm;
			window.opener.document.formData.idHiddenDireccionTitularBaja.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
			window.opener.recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
		}else if (pestania=="AdquirienteBaja"){
			window.opener.document.formData.idProvinciaAdquiriente.value = idProvincia;
			window.opener.document.formData.idMunicipioAdquiriente.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoAdquiriente.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoAdquiriente.value = pueblo;
			window.opener.document.formData.idPuebloAdquiriente.value = pueblo;
			window.opener.document.formData.cpAdquiriente.value = codPostal;
			window.opener.document.formData.tipoViaAdquiriente.value = idTipoVia;
			window.opener.document.formData.nombreViaAdquiriente.value = nombreVia;
			window.opener.document.formData.numeroDireccionAdquiriente.value = numero;
			window.opener.document.formData.letraDireccionAdquiriente.value = letra;
			window.opener.document.formData.escaleraDireccionAdquiriente.value = escalera;
			window.opener.document.formData.pisoDireccionAdquiriente.value = planta;
			window.opener.document.formData.puertaDireccionAdquiriente.value = puerta;
			window.opener.document.formData.bloqueDireccionAdquiriente.value = bloque;
			window.opener.document.formData.kmDireccionAdquiriente.value = km;
			window.opener.document.formData.hmDireccionAdquiriente.value = hm;
			window.opener.document.formData.idHiddenDireccionAdquirienteBaja.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaAdquiriente','idMunicipioAdquiriente','municipioSeleccionadoAdquiriente');
			window.opener.recargarComboPueblos('idProvinciaAdquiriente','municipioSeleccionadoAdquiriente','idPuebloAdquiriente', 'puebloSeleccionadoAdquiriente');
		}else if (pestania=="TitularMatriculacion"){
			window.opener.document.formData.idProvinciaTitular.value = idProvincia;
			window.opener.document.formData.idMunicipioTitular.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoTitular.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoTitular.value = pueblo;
			window.opener.document.formData.idPuebloTitular.value = pueblo;
			window.opener.document.formData.codPostalTitular.value = codPostal;
			window.opener.document.formData.tipoViaDireccionTitular.value = idTipoVia;
			window.opener.document.formData.nombreViaDireccionTitular.value = nombreVia;
			window.opener.document.formData.numeroDireccionTitular.value = numero;
			window.opener.document.formData.letraDireccionTitular.value = letra;
			window.opener.document.formData.escaleraDireccionTitular.value = escalera;
			window.opener.document.formData.bloqueDireccionTitular.value = bloque;
			window.opener.document.formData.puertaDireccionTitular.value = puerta;
			window.opener.document.formData.pisoDireccionTitular.value = planta;
			window.opener.document.formData.kmDireccionTitular.value = km;
			window.opener.document.formData.hmDireccionTitular.value = hm;
			window.opener.recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
			window.opener.recargarComboPueblos('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
		}else if (pestania=="RepresentanteMatriculacion"){
			window.opener.document.formData.idProvinciaRepresentante.value = idProvincia;
			window.opener.document.formData.idMunicipioRepresentante.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoRepresentante.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoRepresentante.value = pueblo;
			window.opener.document.formData.idPuebloRepresentante.value = pueblo;
			window.opener.document.formData.codPostalRepresentante.value = codPostal;
			window.opener.document.formData.tipoViaRepresentanteTitular.value = idTipoVia;
			window.opener.document.formData.nombreViaRepresentanteTitular.value = nombreVia;
			window.opener.document.formData.numeroDireccionRepresentanteTitular.value = numero;
			window.opener.document.formData.letraDireccionRepresentanteTitular.value = letra;
			window.opener.document.formData.escaleraDireccionRepresentanteTitular.value = escalera;
			window.opener.document.formData.pisoDireccionRepresentanteTitular.value = planta;
			window.opener.document.formData.puertaDireccionRepresentanteTitular.value = puerta;
			window.opener.document.formData.bloqueDireccionRepresentanteTitular.value = bloque;
			window.opener.document.formData.kmDireccionRepresentanteTitular.value = km;
			window.opener.document.formData.hmDireccionRepresentanteTitular.value = hm;
			window.opener.recargarComboMunicipios('idProvinciaRepresentante','idMunicipioRepresentante','municipioSeleccionadoRepresentante');
			window.opener.recargarComboPueblos('idProvinciaRepresentante','municipioSeleccionadoRepresentante','idPuebloRepresentante', 'puebloSeleccionadoRepresentante');
		}else if (pestania=="AdquirienteTransmision"||pestania=="AdquirienteTransmisionActual"||pestania=="TitularJustificante"){
			window.opener.document.formData.idProvinciaAdquiriente.value = idProvincia;
			window.opener.document.formData.idMunicipioAdquiriente.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoAdquiriente.value = idMunicipio;
			window.opener.document.formData.idPuebloAdquiriente.value = pueblo;
			window.opener.document.formData.puebloSeleccionadoAdquiriente.value = pueblo;
			window.opener.document.formData.codPostalAdquiriente.value = codPostal;
			window.opener.document.formData.tipoViaAdquiriente.value = idTipoVia;
			window.opener.document.formData.viaAdquiriente.value = nombreVia;
			window.opener.document.formData.numeroAdquiriente.value = numero;
			window.opener.document.formData.letraAdquiriente.value = letra;
			window.opener.document.formData.escaleraAdquiriente.value = escalera;
			window.opener.document.formData.pisoAdquiriente.value = planta;
			window.opener.document.formData.puertaAdquiriente.value = puerta;
			window.opener.document.formData.bloqueAdquiriente.value = bloque;
			window.opener.document.formData.kmAdquiriente.value = km;
			window.opener.document.formData.hmAdquiriente.value = hm;
			window.opener.document.formData.idDireccionAdquiriente.value = idDireccion;
			window.opener.document.formData.idDireccionAdminAdTran.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaAdquiriente','idMunicipioAdquiriente','municipioSeleccionadoAdquiriente');
			window.opener.recargarComboPueblos('idProvinciaAdquiriente','municipioSeleccionadoAdquiriente','idPuebloAdquiriente', 'puebloSeleccionadoAdquiriente');
		}else if (pestania=="TransmitenteTransmision"||pestania=="TransmitenteTransmisionActual"){
			window.opener.document.formData.idProvinciaTransmitente.value = idProvincia;
			window.opener.document.formData.idMunicipioTransmitente.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoTransmitente.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoTransmitente.value = pueblo;
			window.opener.document.formData.codPostalTransmitente.value = codPostal;
			window.opener.document.formData.idPuebloTransmitente.value = pueblo;
			window.opener.document.formData.tipoViaTransmitente.value = idTipoVia;
			window.opener.document.formData.viaTransmitente.value = nombreVia;
			window.opener.document.formData.numeroTransmitente.value = numero;
			window.opener.document.formData.letraTransmitente.value = letra;
			window.opener.document.formData.escaleraTransmitente.value = escalera;
			window.opener.document.formData.pisoTransmitente.value = planta;
			window.opener.document.formData.puertaTransmitente.value = puerta;
			window.opener.document.formData.bloqueTransmitente.value = bloque;
			window.opener.document.formData.kmTransmitente.value = km;
			window.opener.document.formData.hmTransmitente.value = hm;
			window.opener.document.formData.idDireccionTransmitente.value = idDireccion;
			window.opener.document.formData.idDireccionAdminTraTran.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaTransmitente','idMunicipioTransmitente','municipioSeleccionadoTransmitente');
			window.opener.recargarComboPueblos('idProvinciaTransmitente','municipioSeleccionadoTransmitente','idPuebloTransmitente', 'puebloSeleccionadoTransmitente');
		}else if (pestania=="PresentadorTransmision"||pestania=="PresentadorTransmisionActual"){
			window.opener.document.formData.idProvinciaPresentador.value = idProvincia;
			window.opener.document.formData.idMunicipioPresentador.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoPresentador.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoPresentador.value = pueblo;
			window.opener.document.formData.idPuebloPresentador.value = pueblo;
			window.opener.document.formData.codPostalPresentador.value = codPostal;
			window.opener.document.formData.tipoViaPresentador.value = idTipoVia;
			window.opener.document.formData.viaPresentador.value = nombreVia;
			window.opener.document.formData.numeroPresentador.value = numero;
			window.opener.document.formData.letraPresentador.value = letra;
			window.opener.document.formData.escaleraPresentador.value = escalera;
			window.opener.document.formData.pisoPresentador.value = planta;
			window.opener.document.formData.puertaPresentador.value = puerta;
			window.opener.document.formData.bloquePresentador.value = bloque;
			window.opener.document.formData.kmPresentador.value = km;
			window.opener.document.formData.hmPresentador.value = hm;
			window.opener.document.formData.idDireccionPresentador.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaPresentador','idMunicipioPresentador','municipioSeleccionadoPresentador');
			window.opener.recargarComboPueblos('idProvinciaPresentador','municipioSeleccionadoPresentador','idPuebloPresentador', 'puebloSeleccionadoPresentador');
		}else if (pestania=="PoseedorTransmision"||pestania=="CompraventaTransmisionActual"){
			window.opener.document.formData.idProvinciaPoseedor.value = idProvincia;
			window.opener.document.formData.idMunicipioPoseedor.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoPoseedor.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoPoseedor.value = pueblo;
			window.opener.document.formData.idPuebloPoseedor.value = pueblo;
			window.opener.document.formData.codPostalPoseedor.value = codPostal;
			window.opener.document.formData.tipoViaPoseedor.value = idTipoVia;
			window.opener.document.formData.viaPoseedor.value = nombreVia;
			window.opener.document.formData.numeroPoseedor.value = numero;
			window.opener.document.formData.letraPoseedor.value = letra;
			window.opener.document.formData.escaleraPoseedor.value = escalera;
			window.opener.document.formData.pisoPoseedor.value = planta;
			window.opener.document.formData.puertaPoseedor.value = puerta;
			window.opener.document.formData.bloquePoseedor.value = bloque;
			window.opener.document.formData.kmPoseedor.value = km;
			window.opener.document.formData.hmPoseedor.value = hm;
			window.opener.document.formData.idDireccionPoseedor.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaPoseedor','idMunicipioPoseedor','municipioSeleccionadoPoseedor');
			window.opener.recargarComboPueblos('idProvinciaPoseedor','municipioSeleccionadoPoseedor','idPuebloPoseedor', 'puebloSeleccionadoPoseedor');	
		}else if (pestania=="CotitularDuplicado"){
			window.opener.document.formData.idProvinciaCotitular.value = idProvincia;
			window.opener.document.formData.idMunicipioCotitular.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoCotitular.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoCotitular.value = pueblo;
			window.opener.document.formData.idPuebloCotitular.value = pueblo;
			window.opener.document.formData.cpCotitular.value = codPostal;
			window.opener.document.formData.tipoViaCotitular.value = idTipoVia;
			window.opener.document.formData.nombreViaCotitular.value = nombreVia;
			window.opener.document.formData.numeroDireccionCotitular.value = numero;
			window.opener.document.formData.letraDireccionCotitular.value = letra;
			window.opener.document.formData.escaleraDireccionCotitular.value = escalera;
			window.opener.document.formData.pisoDireccionCotitular.value = planta;
			window.opener.document.formData.puertaDireccionCotitular.value = puerta;
			window.opener.document.formData.bloqueDireccionCotitular.value = bloque;
			window.opener.document.formData.kmDireccionCotitular.value = km;
			window.opener.document.formData.hmDireccionCotitular.value = hm;
			window.opener.recargarComboMunicipios('idProvinciaCotitular','idMunicipioCotitular','municipioSeleccionadoCotitular');
			window.opener.recargarComboPueblos('idProvinciaCotitular','municipioSeleccionadoCotitular','idPuebloCotitular', 'puebloSeleccionadoCotitular');
		}else if (pestania=="RentingMatriculacion"){
			window.opener.document.formData.idProvinciaArrendatario.value = idProvincia;
			window.opener.document.formData.idMunicipioArrendatario.value = idMunicipio;
			window.opener.document.formData.municipioSeleccionadoArrendatario.value = idMunicipio;
			window.opener.document.formData.puebloSeleccionadoArrendatario.value = pueblo;
			window.opener.document.formData.idPuebloArrendatario.value = pueblo;
			window.opener.document.formData.codPostalArrendatario.value = codPostal;
			window.opener.document.formData.tipoViaDireccionArrendatario.value = idTipoVia;
			window.opener.document.formData.nombreViaDireccionArrendatario.value = nombreVia;
			window.opener.document.formData.numeroDireccionArrendatario.value = numero;
			window.opener.document.formData.letraDireccionArrendatario.value = letra;
			window.opener.document.formData.escaleraDireccionArrendatario.value = escalera;
			window.opener.document.formData.pisoDireccionArrendatario.value = planta;
			window.opener.document.formData.puertaDireccionArrendatario.value = puerta;
			window.opener.document.formData.bloqueDireccionArrendatario.value = bloque;
			window.opener.document.formData.kmDireccionArrendatario.value = km;
			window.opener.document.formData.hmDireccionArrendatario.value = hm;
			window.opener.recargarComboMunicipios('idProvinciaArrendatario','idMunicipioArrendatario','municipioSeleccionadoArrendatario');
			window.opener.recargarComboPueblos('idProvinciaArrendatario','municipioSeleccionadoArrendatario','idPuebloArrendatario', 'puebloSeleccionadoArrendatario');
		}
		window.close();
	}
</script>

</head>
<body>
<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
<div class="nav">
<table width="100%">
	<tr>
		<td class="tabular"><span class="titulo">Consulta
		Dirección</span></td>
	</tr>
</table>
</div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<div class="tablaformbasica" style="width: 90%"><display:table
	name="session.listaConsultaDirecciones" excludedParams="*"
	class="tablacoin" uid="row" summary="Listado de Direcciones"
	cellspacing="0" cellpadding="0" >
	<display:column style="width:10%" property="provincia" title="Provincia" />
	<display:column style="width:10%" property="municipio" title="Municipio" />
	<display:column style="width:10%" property="ID_TIPO_VIA" title="Tipo Via" />
	<display:column style="width:41%" title="Nombre de la vía" >
		<a href="javascript:actualizarPadre('${row.ID_PROVINCIA}','${row.provincia}','${row.ID_MUNICIPIO}','${row.municipio}','${row.ID_TIPO_VIA}','${row.NOMBRE_VIA}','${row.NUMERO}','${row.PUEBLO}','${row.COD_POSTAL}','${row.LETRA}','${row.ESCALERA}','${row.BLOQUE}','${row.PUERTA}','${row.PLANTA}','${row.KM}','${row.HM}','${row.ID_DIRECCION}', '<%=request.getParameter("pestania") %>');">${row.NOMBRE_VIA}</a>
		</display:column>
	<display:column style="width:4%" property="NUMERO" title="Número" />
	<display:column style="width:4%" property="COD_POSTAL"
		title="Código Postal" />
	<display:column style="width:10%" property="PUEBLO" title="Pueblo" />
	<display:column style="width:3%" property="LETRA" title="Letra" />
	<display:column style="width:3%" property="ESCALERA" title="Escalera" />
	<display:column style="width:3%" property="BLOQUE" title="Bloque" />
	<display:column style="width:3%" property="PLANTA" title="Planta" />
	<display:column style="width:3%" property="PUERTA" title="Puerta" />
	<display:column style="width:3%" property="KM" title="KM" />
	<display:column style="width:3%" property="HM" title="HM" />
</display:table>

<table class="acciones">
   	<tr>
   		<td>
    		<div>
    		<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="window.close();" onKeyPress="this.onClick" />
			</div>
    	</td>
    </tr>
</table>
</div>
</body>
</html>