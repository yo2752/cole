<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta Dev</td>
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
       			<label for="labelRCifConsulta">Cif: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaDev.cif}"/>
       		</td>	
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelRestado">Estado: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{@org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoConsultaDev@convertirTexto(consultaDev.estado)}"/>
   			</td>	
       	</tr>
       	<s:if test="consultaDev.estadoCif != null">
	       	<tr>        	       			
				<td align="left" nowrap="nowrap">
		   			<label for="labelRestadoCif">Respuesta DEV: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{@org.gestoresmadrid.core.consultaDev.model.enumerados.EstadoCif@convertirTexto(consultaDev.estadoCif)}"/>
	   			</td>	
	       	</tr>
	    </s:if>
       	<s:if test="consultaDev.respuesta != null">
			<tr>
				<td align="left" nowrap="nowrap">
		   			<label for="labelRRespuesta">Respuesta: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{consultaDev.respuesta}"/>
	   			</td>
	   			<td width="20"></td>
			</tr>
		</s:if>
	</table>
</div>
	
	