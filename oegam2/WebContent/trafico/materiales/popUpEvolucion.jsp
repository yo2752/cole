<%@ page language="java" contentType="text/html; charset=ISO-8859-1"	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.css">

<script src="https://cdnjs.cloudflare.com/ajax/libs/jquery-confirm/3.3.0/jquery-confirm.min.js"></script>
<script src="js/trafico/materiales/gestionStockMateriales.js" type="text/javascript"></script>


<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Evolución del Material</span>
				</td>
			</tr>
		</table>
	</div>
</div>

	
<div id="displayTagDivStockMateriales">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<div id="displayTagDivStockMateriales">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaStockMateriales" requestURI= "retornarEvolucionStockMateriales.action"
				id="tablaGestionMateriales" summary="Listado de Materiales" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	
				
				<display:column property="usuarioId" title="Usuario"
					defaultorder="descending" style="width:1%" sortProperty="usuarioId"/>	
					
				<display:column property="unidadesAnteriores" title="Unidades Anteriores" 
					defaultorder="descending" style="width:1%" sortProperty="unidadesAnteriores"/>	
					
				<display:column property="unidadesActuales" title="Unidades Actuales" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="unidadesActuales"/>
					
				<display:column property="fecha" title="Fecha Ultima Recarga" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="fecha"/>
					
				<display:column property="operacion" title="Operacion" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="operacion"/>
					
			</display:table>
		</div>
</div>