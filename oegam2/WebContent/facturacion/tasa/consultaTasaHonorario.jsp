<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Tasa-Honorarios</span>
				</td>
			</tr>
		</table>
	</div>	
	
	<s:form method="post" id="formData" name="formData">
			    
	<table class="tablacoin" cellSpacing="0" cellPadding="5" align="left" style="width:100%;">
		<thead>
			<tr>
				<th class="sortable">Num Colegiado</th>
				<th class="sortable">Nombre Tasa</th>
				<th class="sortable">Tipo Tasa</th>
				<th class="sortable">Codigo Tasa</th>
				<th class="sortable">Precio Tasa</th>
				<th class="sortable">Honorario</th>
				<th class="sortable">Fecha</th>
				<th class="sortable"></th>
			</tr>
		</thead>
		
	
		
		<tbody>
			<tr>
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.numColegiado" size="10" id="idNumColegiado" maxlength="255"/>
				</td>
				
	       		<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.nomTasa" size="15" id="idNomTasa" maxlength="255"/>
				</td>
				
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.tipoTasa" size="8" id="idTipoTasa"  maxlength="255"/>
				</td>
				
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.codTasa" size="15" id="idCodTasa"  maxlength="255"/>
				</td>				        		
				
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.precioTasa" size="8" id="idPrecioTasa"  maxlength="255"/>
				</td>
	       	       			
	       		<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.honorarioTasa" id="idHonorarioTasa"  size="8"/>
				</td>				
				
				<td align="left" nowrap="nowrap">
					<s:textfield name="datosTasa.fechaActualTasa" id="idFechaActual"  size="10" disabled="true"/>
				</td>
	       	       			
	       		<td align="left" nowrap="nowrap">
					<input type="checkbox" onkeypress="this.onClick" onclick="marcarTodos(this, document.formData.listaChecksConsultaTramite);" name="checkAll">
				</td>
			</tr>
		</tbody>
	</table>
	
	<display:table name="listaConsultaTasaHonorario" excludedParams="*"
					requestURI="navegarConsultaTramiteTrafico.action"
					class="tablacoin"
					uid="tablaConsultaTramite"
					summary="Listado de Tramites"
					cellspacing="0" cellpadding="0"
					decorator="trafico.utiles.DecoradorTablas">
					
	<display:column title="Estado" property="estado.nombreEnum"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" >
	</display:column>
					
		<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/>" style="width:1%" >
			<table align="center">
			<tr>
				<td style="border: 0px;">
					<input type="checkbox" name="listaChecksConsultaTramite" id="<s:property value="#attr.tablaConsultaTramite.tipoTramite.valorEnum"/>-<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
					value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' />
				</td>
				<td style="border: 0px;">				  		
			  		<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
			  		onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);" title="Ver evolución"/>
			  	</td>
			</tr>
			</table>
	 	</display:column>
	 	
 	</display:table>
	
	</s:form >
	<%@include file="pestaniaBotonesTasaHonorarios.jspf" %>
