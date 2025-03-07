<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">	

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Cl&aacute;usulas</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirClausula('Financiacion','formData', 'cargarPopUpClausulaRegistroFinanciacion.action');" id="btnAniadirClausulaFinanciacion" class="button corporeo" value="Añadir cláusula" />
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<%@include file="clausulasList.jsp" %>
			</td>
		</tr>
	</table>
	
	<%@include file="../contratosRecursos/avalista.jsp"%>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Representantes del avalista</span>
			</td>
		</tr>
	</table>	
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	    <tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteAvalistaRegistroFinanciacion.action','Avalista');" id="btnAniadirRepresentanteAvalistaFinanciacion" class="button corporeo" value="Añadir representante avalista" />
				</s:if>
			</td>
		</tr>
	</table>  
 	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="representantesAvalistaList.jsp" />
        	</td>
        </tr>
	</table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Avalistas</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<%@include file="avalistasList.jsp" %>
        	</td>
        </tr>
	</table>

</div>
