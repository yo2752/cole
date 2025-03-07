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

	<div class="divScroll">
		<display:table name="listaConsultaTasaHonorario" excludedParams="*"
					requestURI="rellenarDatosTasaGestionFacturar.action"
					class="tablacoin"
					uid="tablaConsultaTasa"
					summary="Listado de Tasas"
					cellspacing="0" cellpadding="0"
					decorator="trafico.utiles.DecoradorTablas">
			
			<display:column title="Numero Colegiado" 
					property="datosTasa.numColegiado"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Nombre Tasa" 
					property="datosTasa.nomTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Numero Colegiado" 
					property="numColegiado"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Nombre Tasa" 
					property="nomTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Tipo Tasa" 
					property="datosTasa.tipoTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Codigo Tasa" 
					property="datosTasa.codTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Precio Tasa" 
					property="datosTasa.precioTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>
			
			<display:column title="Honorario Tasa" 
					property="datosTasa.honorarioTasa"
					sortable="true" headerClass="sortable" 
					defaultorder="descending"
					style="width:2%" >
			</display:column>	
						
			<display:column title="Fecha Actual" 
					property="datosTasa.fechaActualTasa"
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
 		</div>	
	</s:form >
	
	<%@include file="pestaniaBotonesTasaHonorarios.jspf" %>
