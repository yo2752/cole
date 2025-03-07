<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de los Vehículos</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Matriculas</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
					<display:table name="consultaPersonaAtex5.personaAtex5.listaMatriculas" class="tablacoin"
								uid="tablaMatriculasPerAtex5" 
								id="tablaMatriculasPerAtex5" summary=""
								excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
					
					<display:column property="matricula" title="Matricula" sortable="false" headerClass="sortable"
								defaultorder="descending" style="width:4%" />
								
					</display:table>
				</td>
			</tr>
	</table>
</div>