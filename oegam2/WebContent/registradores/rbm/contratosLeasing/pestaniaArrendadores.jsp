<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<div class="contenido">	
	
	<%@include file="../contratosLeasingRenting/arrendador.jsp"%>
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular"><span class="titulo">Lista Representantes Arrendadores</span></td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteArrendadorRegistroLeasing.action','Arrendador');" id="btnAniadirRepresentanteArrendadorLeasing" class="button corporeo" value="Añadir representante" />
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td><jsp:include page="representantesArrendadoresList.jsp" flush="true" /></td>
		</tr>
	</table>
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Arrendadores</span>
			</td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
        		<jsp:include page="arrendadoresList.jsp" />
        	</td>
        </tr>
	</table>
</div>
