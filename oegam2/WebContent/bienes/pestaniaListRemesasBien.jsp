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
				<span class="titulo">Listado de Remesas Asociadas al Bien</span>
			</td>
		</tr>
	</table>	
	<display:table name="bien.listaRemesas" class="tablacoin" uid="tablaRemesasBien" style="width:100%"
				id="tablaRemesasBien" summary="" excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
				
		<display:column title="Remesa"  property="remesa.modelo.modelo" headerClass="sortable" style="width:1%" />
				
		<display:column property="remesa.numExpediente" title="Núm. Expediente" headerClass="sortable"
			style="width:2%" href="verRemesaBN.action" paramId="numExpediente" />
			
		<display:column title="Estado" headerClass="sortable" style="width:3%" >
			<s:property value="%{@org.gestoresmadrid.core.modelos.model.enumerados.EstadoRemesas@convertirTexto(#attr.tablaRemesasBien.remesa.estado)}" />
		</display:column>
				
		<display:column property="remesa.concepto.descConcepto" title="Concepto" headerClass="sortable" style="width:15%" />
	</display:table>
</div>
