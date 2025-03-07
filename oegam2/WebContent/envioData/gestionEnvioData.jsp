<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/envioData/gestionEnvioData.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Envio Data Procesos</span></td>
		</tr>
	</table>
</div>
<%@include file="../../includes/erroresMasMensajes.jspf" %>
<s:form id="formData" name="formData">
	<s:hidden name="proceso"/>
	<s:hidden name="cola"/>
	<s:hidden name="tipoActualizacion"/>
	<s:if test="{listaEnvioData != null && !listaEnvioData.isEmpty()}">
		<display:table name="listaEnvioData" excludedParams="*"
				list="listaEnvioData" class="tablacoin" summary="Listado de Procesos" 
				style="width:90%;margin-left:auto" cellspacing="0" cellpadding="0" uid="listaEnvioDataTable">
				
			<display:column property="proceso" title="Proceso" headerClass="centro" style="width:20%;text-align:left;padding-left:10px;"/>
			
			<display:column property="cola" title="Cola" headerClass="centro" style="width:5%;"/>
			
			<display:column property="fechaEnvio" title=" Fecha ejecución" headerClass="centro" style="width:5%;" 
				format="{0,date,yyyy-MM-dd HH:mm:ss}"/>
			
			<display:column property="respuesta" title="Respuesta" headerClass="centro" style="width:5%;" />
			
			<display:column property="numExpediente" title="Num. Expediente" headerClass="centro" style="width:5%;" />
				
			<display:column title="Fecha Anterior" headerClass="centro" style="text-align:center; width:10%;">
				<input type="button" class="boton" id="btnFechaAnterior<s:property value="#attr.listaEnvioDataTable.proceso"/>" 
					onclick="javascript:actualizarFechaAnterior('<s:property value="#attr.listaEnvioDataTable.proceso"/>','<s:property value="#attr.listaEnvioDataTable.cola"/>')" 
					title="Modificar la fecha de envio data" value="Última Fecha Anterior"/>
			</display:column>
		
			<display:column title="Fecha Hoy" headerClass="centro" style="text-align:center; width:10%;">
				<input type="button" class="boton" id="btnFechaHoy<s:property value="#attr.listaEnvioDataTable.proceso"/>" 
					onclick="javascript:actualizarFechaHoy('<s:property value="#attr.listaEnvioDataTable.proceso"/>','<s:property value="#attr.listaEnvioDataTable.cola"/>')" 
					title="Modificar la fecha de envio data" value="Fecha de Hoy"/>
			</display:column>
			
		</display:table>
	</s:if>
</s:form>