<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>


	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Datos de cesi&oacute;n</span>
			</td>
		</tr>
	</table>	  
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap"><label for="cesionPersonaNoDeterminada" title="Indica si la cesi&oacute;n es a persona indeterminada">Persona indeterm.<span class="naranja">*</span></label></td>
			<td><s:checkbox name="tramiteRegRbmDto.personaNoDeterminada" fieldValue="true" label="ejemplo" id="cesionPersonaNoDeterminada"/>
				<span class="ms">SI</span>
			</td>
			<td align="left" nowrap="nowrap"><label for="cesionTipoCesion" class="small">Tipo cesi&oacute;n<span class="naranja">*</span></label></td>
			<td><s:select name="tramiteRegRbmDto.tipoCesionTercero" 
            	list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCessionType()"
				listKey="key"
                headerKey="" 
                headerValue="Seleccionar tipo"
                id="cesionTipoCesion"/></td>
		</tr>
	</table>

	<%@include file="../contratosRecursos/cesionario.jsp"%>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Representantes Cesionarios</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"align="left">
		<tr>
			<td align="center" nowrap="nowrap" colspan="6">
				<s:if test="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().esEditable(tramiteRegRbmDto.estado,tramiteRegRbmDto.numReg)">
					<input type="button" onclick="javascript:aniadirRepresentanteRBM('cargarPopUpRepresentanteCesionarioRegistroLeasing.action','Cesionario');" id="btnAniadirRepresentanteCesionarioLeasing" class="button corporeo"value="Añadir representante" />
				</s:if>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
				<jsp:include page="representantesCesionarioList.jsp" flush="true" />
	    	</td>
	    </tr>
	</table> 
	&nbsp;
	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Lista Cesionarios</span>
			</td>
		</tr>
	</table>

	
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td>
   			 	<jsp:include page="cesionariosList.jsp" flush="true"/>
   			</td>
	    </tr>
	</table> 
