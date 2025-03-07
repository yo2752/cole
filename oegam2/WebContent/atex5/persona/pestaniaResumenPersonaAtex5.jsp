<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta de Persona</td>
			<td style="border: 0px;">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionAlta('idNumExpConsultaPersona','divEmergenteEvolucionAltaConsultaPersonaAtex5');" title="Ver evolución"/>
			</td>
		</tr>
	</table>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen</span>
			</td>
		</tr>
	</table>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
       			<label for="labelRNifConsulta">NIF: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaPersonaAtex5.nif}"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" width="120">
       			<label for="labelRNombreConsulta">Nombre: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaPersonaAtex5.nombre}"/>
       		</td>	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="150">
       			<label for="labelRApe1RznSocialConsulta">1er Apellido / Razón Social: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaPersonaAtex5.apellido1RazonSocial}"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" width="120">
       			<label for="labelRApe2Consulta">2do Apellido: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaPersonaAtex5.apellido2}"/>
       		</td>	
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelRestado">Estado: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{@org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5@convertirTexto(consultaPersonaAtex5.estado)}"/>
   			</td>	
       	</tr>
       	<s:if test="consultaPersonaAtex5.respuesta != null">
			<tr>
				<td align="left" nowrap="nowrap">
		   			<label for="labelRRespuesta">Respuesta: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{consultaPersonaAtex5.respuesta}"/>
	   			</td>
	   			<td width="20"></td>
			</tr>
		</s:if>
	</table>
</div>
	
	