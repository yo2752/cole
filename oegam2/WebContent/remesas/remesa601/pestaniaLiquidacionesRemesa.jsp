<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<div class="contenido">
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Remesa 601</td>
		</tr>
	</table>
	<s:if test="%{esResumenP}">
		<br>
		<div id="resumenPresentacionModelos" style="text-align: center;">
			<%@include file="../resumenPresentacionRemesas.jspf" %>
		</div>
		<br><br>
	</s:if>
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Listado de Liquidaciones</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<display:table name="remesa.listaModelos" class="tablacoin"
							uid="tablaModelos" 
							id="tablaModelos" summary=""
							excludedParams="tablaformbasica" cellspacing="0" cellpadding="0">
					
					<display:column property="numExpediente" title="Num. Expediente" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
							
					<display:column property="valorDeclarado" title="Valor Declarado" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
							
					<display:column property="baseImponible" title="Base Imponible" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
							
					<display:column property="ingresar" title="Total Ingresar" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
						
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosRemesa(this)' 
						onKeyPress='this.onClick'/>" style="width:4%" >
						<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaModelos.numExpediente"/>" 
							value='<s:property value="#attr.tablaModelos.numExpediente"/>' />
					</display:column>
				</display:table>
			</td>
		</tr>
	</table>
	<br/>
	<div class="acciones center">
		<input type="button" class="boton" name="bModificarModelo" id="idModificarModelo" 
		  	value="Modificar" onClick="javascript:modificarModelo();"onKeyPress="this.onClick"/>	
		<s:if test="%{esModeloPresentable}">
			<input type="button" class="boton" name="bPresentarModelo" id="idPresentarModelo" 
			  	value="Presentar" onClick="javascript:presentarModelos();"onKeyPress="this.onClick"/>	
		</s:if>
	</div>
	<div id="divEmergentePresentacionModelo" style="display: none; background: #f4f4f4;"></div>
</div>
	
	