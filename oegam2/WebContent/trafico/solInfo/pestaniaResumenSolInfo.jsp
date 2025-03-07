<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div align="center">
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Solicitud de Información</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120">
       			<label for="labelResNumExpediente">Num.Expediente: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{tramiteInteveDto.numExpediente}"/>
       		</td>	
			<td align="left" nowrap="nowrap" width="120">
       			<label for="labelResRefpropia">Ref.Propia: </label>
       		</td>
       		<td align="left" nowrap="nowrap">
       			<s:label value="%{tramiteInteveDto.refPropia}"/>
       		</td>	
		</tr>
		<tr>        	       			
			<td align="left" nowrap="nowrap" width="200">
      			<label for="labelResEstado">Estado: </label>
      		</td>
      		<td align="left" nowrap="nowrap">
      			<s:label value="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getDescripcionEstado(tramiteInteveDto)}"/>
      		</td>	
			<td align="left" nowrap="nowrap">
	   			<label for="labelResFechaPresentacion">Fecha Presentacion: </label>
	   		</td>
         	<td align="left" nowrap="nowrap">
   				<s:label value="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().convertirFechaPresentacion(tramiteInteveDto)}"/>
   			</td>	
    	</tr>
    	<s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().esAdmin()}">
    		<tr>
    			<td align="left" nowrap="nowrap" width="200">
	      			<label for="labelResContrato">Contrato: </label>
	      		</td>
	      		<td align="left" nowrap="nowrap">
	      			<s:label value="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().getDescripcionContrato(tramiteInteveDto)}"/>
	      		</td>
    		</tr>
    	</s:if>
    </table>
    <s:if test="%{@org.gestoresmadrid.oegam2.trafico.solInfo.utiles.UtilesVistaSolInfo@getInstance().existeListaSolInfoVehiculos(tramiteInteveDto)}">
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
			<tr>
				<td class="tabular">
					<span class="titulo">Vehículos</span>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<s:iterator value="%{tramiteInteveDto.solicitudes}">
				<tr>
					<s:if test="%{matricula != null}">
					<td align="left" nowrap="nowrap"width="5%">
				   		Matricula: <s:property value="%{matricula}"/>
				   	</td>
				   	</s:if>
				   	<s:if test="%{bastidor != null}">
				   	<td align="left" nowrap="nowrap"width="5%">
				   		Bastidor: 
				   		
				   		<s:property value="%{bastidor}"/>
				   	</td>
				   	</s:if>
				   	<td align="left" nowrap="nowrap"width="5%">
				   		Tasa: <s:property value="%{tasa.codigoTasa}"/>
				   	</td>
				   	<td align="left" nowrap="nowrap"width="5%">
				   		Motivo: <s:property value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.MotivoInforme@convertir(motivoInforme)}"/>
				   	</td>
				   	<td align="left" nowrap="nowrap"width="5%">
				   		Tipo de informe: <s:property value="%{@org.gestoresmadrid.core.trafico.solInfo.model.enumerados.TipoInforme@convertir(tipoInforme)}"/>
				   	</td>
				</tr>
			</s:iterator>
		</table>
	</s:if>
</div> 		