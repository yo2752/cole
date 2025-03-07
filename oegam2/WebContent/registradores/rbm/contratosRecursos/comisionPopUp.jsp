<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.comisionDto.idComision" />
<s:hidden name="tramiteRegRbmDto.comisionDto.idDatosFinancieros" />
<s:hidden name="tramiteRegRbmDto.comisionDto.fecCreacion" />	
	
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Comisiones:</span>
			</td>
		</tr>
	</table>
</div>
<div class="contenido" id="divComision">	
	<table class="tablaformbasica" >
		<tr>
			<td align="left" nowrap="nowrap"><label for="comisionTipoComision" class="small">Tipo comisi&oacute;n<span class="naranja">*</span></label></td>
			<td colspan="3"><s:select name="tramiteRegRbmDto.comisionDto.tipoComision"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListCommission()" 
				listKey="key" 
				headerKey=""
				headerValue="Seleccione el tipo" 
				id="comisionTipoComision"/></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap"><label for="comisionPorcentaje" class="small">Porcentaje<span class="naranja">*</span></label></td>
			<td width="30%"><s:textfield name="tramiteRegRbmDto.comisionDto.porcentaje" id="comisionPorcentaje" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"></s:textfield></td>
			<td align="left" nowrap="nowrap"><label for="commisionImporteMinimo" class="small">Importe M&iacute;nimo<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.comisionDto.importeMinimo" id="commisionImporteMinimo" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
		</tr>
		<tr>	
			<td align="left" nowrap="nowrap"><label for="commisionImporteMaximo" class="small">Importe m&aacute;ximo<span class="naranja">*</span></label></td>
			<td><s:textfield name="tramiteRegRbmDto.comisionDto.importeMaximo" id="commisionImporteMaximo" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"></s:textfield></td>
			<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_7.getValorEnum() ">
				<td align="left" nowrap="nowrap"><label for="comisionFinanciado" class="small">Ha sido financiado</label></td>
				<td width="35%"><s:checkbox name="tramiteRegRbmDto.comisionDto.financiado" id="comisionFinanciado" fieldValue="true" /><span class="ms">SI (por defecto NO)</span></td>
			</s:if>
		</tr>
	</table>
	
	<!-- Estos campos solo se llenan si el contrato es de tipo Financiación -->
	<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_7.getValorEnum() ">
		
		<table class="tablaformbasica" >
	        <tr>
				<td align="left" nowrap="nowrap"><label for="comisionCondicionAplicacion" class="small">Condici&oacute;n de Aplicaci&oacute;n</label></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<s:textarea name="tramiteRegRbmDto.comisionDto.condicionAplicacion" id="comisionCondicionAplicacion" cols="100" rows="5"></s:textarea>
				</td>
			</tr>
		  
		</table>
	</s:if>


</div>
