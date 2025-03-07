<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<s:hidden name="tramiteRegRbmDto.otroImporteDto.idOtroImporte" />
<s:hidden name="tramiteRegRbmDto.otroImporteDto.idDatosFinancieros" />
<s:hidden name="tramiteRegRbmDto.otroImporteDto.fecCreacion" />		


<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
	<tr>
		<td class="tabular">
			<span class="titulo">Otros importes:</span>
		</td>
	</tr>
</table>	
		
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="tipoOtroImporte" class="small">Tipo importe<span class="naranja">*</span></label></td>
		<td colspan="3"><s:select name="tramiteRegRbmDto.otroImporteDto.tipoOtroImporte"
				list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getListOtherImportsType()"
				listKey="key" 
				headerKey=""
				headerValue="Seleccione el tipo" 
				id="tipoOtroImporte"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="otroImporteDtoImporte" class="small">Importe<span class="naranja">*</span></label></td>
		<td width="30%"><s:textfield name="tramiteRegRbmDto.otroImporteDto.importe" id="otroImporteDtoImporte" size="14" maxlength="13" onkeypress="return soloNumeroDecimal(event, this, '10', '2')"/></td>
		
		<td align="left" nowrap="nowrap"><label for="otroImporteDtoPorcentaje" >Porcentaje</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Se indica si el importe no es fijo y se aplica sobre una base"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.otroImporteDto.porcentaje" id="otroImporteDtoPorcentaje" size="14"  maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"/></td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><label for="otroImporteDtoPorcentajeBase" >Base</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Base sobre la que se aplica el porcentaje"/>
		</td>
		<td><s:textfield name="tramiteRegRbmDto.otroImporteDto.porcentajeBase" id="otroImporteDtoPorcentajeBase" size="14" maxlength="14" onkeypress="return soloNumeroDecimal(event, this, '3', '10')"/></td>
		
		<!-- Este campos solo se muestra si el contrato es de tipo Financiación -->
		<s:if test="tramiteRegRbmDto.tipoTramite == @org.gestoresmadrid.core.registradores.model.enumerados.TipoTramiteRegistro@MODELO_7.getValorEnum() ">	
			<td align="left" nowrap="nowrap"><label for="otroImporteDtoCondicionante" >Es condicionante</label>
				<img src="img/botonDameInfo.gif" alt="Info" title="Indica si el concepto o servicio es condicionante para la concesi&oacute;n del cr&eacute;dito"/>
			</td>
			<td ><s:checkbox name="tramiteRegRbmDto.otroImporteDto.condicionante"
					id="otroImporteDtoCondicionante" fieldValue="true" /><span class="ms">SI
					(por defecto NO)</span></td>
			<td></td>
		</s:if>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	<tr>
		<td align="left" nowrap="nowrap"><label for="otroImporteDtoObservaciones" >Observaciones</label>
			<img src="img/botonDameInfo.gif" alt="Info" title="Observaciones/Aclaraciones sobre el importe"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap"><s:textarea name="tramiteRegRbmDto.otroImporteDto.observaciones" id="otroImporteDtoObservaciones" cols="100" rows="5"></s:textarea></td>
	</tr>
</table>