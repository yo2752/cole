<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<s:if test="'URBANO'.equals(bien.tipoInmueble.idTipoBien)">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Bien Urbano</td>
			</tr>
		</table>
	</s:if>
	<s:elseif test="'RUSTICO'.equals(bien.tipoInmueble.idTipoBien)">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Bien Rústico</td>
			</tr>
		</table>
	</s:elseif>
	<s:else>
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Datos del Bien</td>
			</tr>
		</table>
	</s:else>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Listado de Inmuebles Asociados al Bien</span>
			</td>
		</tr>
	</table>
	
	<display:table name="bien.listaInmuebles" class="tablacoin" uid="tablaInmuebleBien" style="width:100%"
		id="tablaInmuebleBien" summary="" excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
				<display:column property="bien.tipoInmueble.idTipoBien" title="Tipo Bien" style="text-align:left;" headerClass="sortable"/>
				<display:column property="idTramiteRegistro" title="Núm. Expediente" headerClass="sortable" href="verInmuebleBN.action" paramId="numExpediente" />
				<display:column property="bien.idufir" title="IDUFIR/CRU" style="text-align:left;" headerClass="sortable"/>
				<display:column property="bien.direccion.nombreProvincia" title="Provincia" style="text-align:left;" headerClass="sortable"/>
				<display:column property="bien.direccion.nombreMunicipio" title="Municipio" style="text-align:left;" headerClass="sortable"/>
				<display:column property="bien.seccion" title="Sección" style="text-align:right;" headerClass="sortable"/>
				<display:column property="bien.numeroFinca" title="Nº Finca" style="text-align:right;" headerClass="sortable"/>
				<display:column property="bien.subnumFinca" title="Sub. Nº Finca" style="text-align:right;" headerClass="sortable"/>
				<display:column property="bien.numFincaDupl" title="Nº Finca Dup." style="text-align:right;" headerClass="sortable"/>
	</display:table>

</div>
