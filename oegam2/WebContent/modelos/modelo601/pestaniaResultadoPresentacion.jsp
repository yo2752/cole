<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos del Modelo 601</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">EVOLUCI&Oacute;N MODELO 601</span>
			</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<display:table name="modeloDto.listaResultadoModelo" class="tablacoin"
							uid="tablaResultadoModelo" 
							id="tablaResultadoModelo" summary="" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorResultadoModelo600601"
							excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">

					<display:column property="idResultado" title="" media="none" paramId="idResultado"/>

					<display:column property="codigoResultado" title="" media="none"/>

					<display:column property="fechaEjecucion" title="Fecha Ejecución"
						sortable="false" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%"
						format="{0,date,dd/MM/yyyy}" />

					<display:column title="Resultado" property="codigoResultado" sortable="false"
						headerClass="sortable" defaultorder="descending" style="width:14%"/>

					<display:column property="justificante" title="Justificante" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />

					<display:column property="nccm" title="Nccm" sortable="false" headerClass="sortable " 
						defaultorder="descending" style="width:4%"/>

					<display:column property="cso" title="Cso" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />

					<display:column property="fechaPresentacion" title="Fecha Presentación"
						sortable="false" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%"
						format="{0,date,dd/MM/yyyy HH:mm:ss}" />

					<display:column property="expedienteCAM" title="Expediente CAM" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />

				</display:table>
			</td>
		</tr>
	</table>
	<br/>
</div>