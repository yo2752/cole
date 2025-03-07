<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Consulta EEFF</td>
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
       			<label for="labelNumExpediente">Num.Expediente: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaEEFF.numExpediente}"/>
       		</td>	
		</tr>
		<s:if test="consultaEEFF.numExpedienteTramite != null">
			<tr>        	       			
				<td align="left" nowrap="nowrap" width="200">
       			<label for="labelNumExpedienteTramite">Num.Expediente Tramite: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{consultaEEFF.numExpedienteTramite}"/>
       		</td>	
	       	</tr>
	    </s:if>
	    <s:if test="consultaEEFF.estado != null">
	    	<tr>        	       			
				<td align="left" nowrap="nowrap">
		   			<label for="labelEstado">Estado: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="%{@org.gestoresmadrid.core.trafico.eeff.model.enumerados.EstadoConsultaEEFF@convertirTexto(consultaEEFF.estado)}"/>
	   			</td>	
	    	</tr>
	    </s:if>
	    <tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelEstado">Fecha Alta: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{consultaEEFF.fechaAlta}"/>
   			</td>	
    	</tr>
    	 <tr>        	       			
			<td align="left" nowrap="nowrap">
	   			<label for="labelEstado">Fecha Realizacion: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{consultaEEFF.fechaRealizacion}"/>
   			</td>	
    	</tr>
       	<s:if test="consultaEEFF.respuesta != null">
	       	<tr>        	       			
				<td align="left" nowrap="nowrap">
		   			<label for="labelRespuesta">Resultado Consulta: </label>
		   		</td>
	         	<td align="left" nowrap="nowrap">
	   				<s:label value="consultaEEFF.respuesta"/>
	   			</td>	
	       	</tr>
	    </s:if>
	</table>
</div>
	
	