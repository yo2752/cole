<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<div class="contenido">
		
	<%@include file="comprador.jsp"%>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Representantes Compradores</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteCompradorRegistroFinanciacion.action','Comprador');" id="btnAniadirRepresentanteCompradorFinanciacion" class="button corporeo"value="Añadir representante" />
				</s:if>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="representantesCompradoresList.jsp" flush="true" />
			</td>
		</tr>
	</table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Compradores</span>
			</td>
		</tr>
	</table>

	<div id="listCompradoresFinanciacion">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
			<tr>
				<td>
	        		<jsp:include page="compradoresList.jsp" />
	        	</td>
	        </tr>
		</table>
	</div>
	
</div>
  