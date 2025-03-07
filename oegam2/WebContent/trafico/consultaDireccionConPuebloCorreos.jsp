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
		if (pestania=="TitularMatriculacion"){
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
			window.opener.document.formData.idHiddenDireccionTitular.value = idDireccion;
			window.opener.document.formData.idDireccionAdminTitMatr.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaTitular','idMunicipioTitular','municipioSeleccionadoTitular');
			window.opener.recargarComboLocalidadesDGT('idProvinciaTitular','municipioSeleccionadoTitular','idPuebloTitular', 'puebloSeleccionadoTitular');
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
			window.opener.document.formData.idHiddenDireccionRepresentante.value = idDireccion;
			window.opener.document.formData.idDireccionAdminRepTitMatr.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaRepresentante','idMunicipioRepresentante','municipioSeleccionadoRepresentante');
			window.opener.recargarComboLocalidadesDGT('idProvinciaRepresentante','municipioSeleccionadoRepresentante','idPuebloRepresentante', 'puebloSeleccionadoRepresentante');
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
			window.opener.document.formData.idHiddenDireccionArrendatario.value = idDireccion;
			window.opener.document.formData.idDireccionAdminRenMatr.value = idDireccion;
			window.opener.recargarComboMunicipios('idProvinciaArrendatario','idMunicipioArrendatario','municipioSeleccionadoArrendatario');
			window.opener.recargarComboLocalidadesDGT('idProvinciaArrendatario','municipioSeleccionadoArrendatario','idPuebloArrendatario', 'puebloSeleccionadoArrendatario');
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
		<a href="javascript:actualizarPadre('${row.ID_PROVINCIA}','${row.provincia}','${row.ID_MUNICIPIO}','${row.municipio}','${row.ID_TIPO_VIA}','${row.NOMBRE_VIA}','${row.NUMERO}','${row.PUEBLO_CORREOS}','${row.COD_POSTAL_CORREOS}','${row.LETRA}','${row.ESCALERA}','${row.BLOQUE}','${row.PUERTA}','${row.PLANTA}','${row.KM}','${row.HM}','${row.ID_DIRECCION}', '<%=request.getParameter("pestania") %>');">${row.NOMBRE_VIA}</a>
		</display:column>
	<display:column style="width:4%" property="NUMERO" title="Número" />
	<display:column style="width:4%" property="COD_POSTAL_CORREOS"
		title="Código Postal" />
	<display:column style="width:10%" property="PUEBLO_CORREOS" title="Pueblo" />
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