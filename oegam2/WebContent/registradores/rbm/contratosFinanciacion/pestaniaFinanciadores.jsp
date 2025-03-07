<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contenido">
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Financiador:</span>
			</td>
		</tr>
	</table>	  
	<table width="100%" border="0" class="tablaformbasica">
		<tr>
            <td align="left" nowrap="nowrap" style="width:42%"><label for="personaIndeterminada" class="small" >Seleccionar financiador/es<span class="naranja">*</span></label></td>
			<td><s:select 
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListFinanciadores()"
				listKey="idInterviniente" 
				listValue="persona.nombre + ' ' + persona.apellido1RazonSocial + ' - ' + nif" 
				headerKey=""
				headerValue="Seleccionar Financiador"
				name="idFinanciador"
				id="financingDossierCorpmePersonType" /></td>
			<td></td>
		</tr>
	</table>
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:doPostValidate('formData', 'aniadirFinanciadorFinanciacion.action#tab9', '', '');" id="btnAniadirFinanciadorFinanciacion" class="button corporeo" value="Añadir Financiador" />
				</s:if>
			</td>
		</tr>
	</table>
	
	
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Financiadores</span>
			</td>
		</tr>
	</table>	  
	
	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
   			 	<jsp:include page="financiadoresList.jsp" flush="true"/>
   			</td>
	    </tr>
	</table> 

</div>