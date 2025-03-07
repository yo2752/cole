<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de las Sanciones</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Sanciones</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaPersonaAtex5.personaAtex5.listaSanciones" class="tablacoin"
								uid="tablaSancionesPerAtex5" 
								id="tablaSancionesPerAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
						
						<display:column property="tipoSancion" title="Tipo Sancion" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="fecha" title="Fecha Inicio" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
							
						<display:column property="fechaFin" title="Fecha Fin" sortable="false" headerClass="sortable"
							defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
						
						<display:column property="motivo" title="Motivo" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
																			
						<display:column property="duracion" title="Duracion" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
						
						<display:column property="autoridadSancionadora" title="Auto.Sancionadora" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
						<display:column property="expediente" title="Expediente" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />	
								
						<display:column property="anotacion" title="Anotacion" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />	
					</display:table>
				</td>
			</tr>
	</table>
</div>