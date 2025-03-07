<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.pactoDto.idPacto" />
<s:hidden name="tramiteRegRbmDto.pactoDto.idTramiteRegistro" />
<s:hidden name="tramiteRegRbmDto.pactoDto.fecCreacion" />

<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Acuerdos:</span>
		</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="acuerdoTipoPacto" class="small">Tipo de pacto<span class="naranja">*</span></label></td>
		<td><s:select name="tramiteRegRbmDto.pactoDto.tipoPacto"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListPactType()"
				listKey="key" 
				headerKey=""
				headerValue="Seleccione el tipo" 
				id="acuerdoTipoPacto"/></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="acuerdoPactado" class="small">Ha sido pactado<span class="naranja">*</span></label></td>
		<td><s:checkbox name="tramiteRegRbmDto.pactoDto.pactado" id="acuerdoPactado" fieldValue="true" /></td>
		<td></td>
	</tr>
</table>
