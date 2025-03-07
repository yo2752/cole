<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta de Vehiculo</td>
			<td style="border: 0px;" align="right">
				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  	onclick="abrirEvolucionAlta('idNumExpConsultaVehiculo','divEmergenteEvolucionAltaConsultaVehiculoAtex5');" title="Ver evolución"/>
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
       			<label for="labelRMatriculaConsulta">Matricula: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaVehiculoAtex5Dto.matricula}"/>
       		</td>
       	</tr>
       	<tr>
       		<td align="left" nowrap="nowrap" width="120">
       			<label for="labelRBastidorConsulta">Bastidor: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaVehiculoAtex5Dto.bastidor}"/>
       		</td>	
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
       			<label for="labelRNiveConsulta">Nive: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaVehiculoAtex5Dto.nive}"/>
       		</td>
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelRestado">Estado: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{@org.gestoresmadrid.core.atex5.model.enumerados.EstadoAtex5@convertirTexto(consultaVehiculoAtex5Dto.estado)}"/>
   			</td>	
       	</tr>
       	<s:if test="consultaVehiculoAtex5Dto.respuesta != null">
			<tr>
				<td align="left" nowrap="nowrap">
		   			<label for="labelRRespuesta">Respuesta: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{consultaVehiculoAtex5Dto.respuesta}"/>
	   			</td>
			</tr>
		</s:if>
	</table>
</div>
	
	