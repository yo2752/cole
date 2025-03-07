<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="datosPortalAlta">
	
		<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.idDatosPortalAlta"/>
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos Portal Alta </td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
				<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNombrePortal">Nombre Portal:</label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="nombrePortal" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.nombrePortal" size="4" maxlength="2"/></td>
	       	</tr>
	       	<tr> 	
		 		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Bajo Rasante Unidades: </label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.unidadesBjRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
	          	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Bajo Rasante Superficie Construida: </label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.superficieConstruidaBjRasante" size="10" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	     		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Bajo Rasante Superficie Computable: </label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.superficieComputableBjRasante" size="10" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	     	</tr>
	     	<tr> 	
		 		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUnidades">Sobre Rasante Unidades:<span class="naranja">*</span></label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="unidades" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.unidadesSbRasante" size="5" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '5', '0')"/></td>
	          	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Sobre Rasante Superficie Construida:<span class="naranja">*</span></label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="supConstruida" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.superficieConstruidaSbRasante" size="10" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	     		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupComputable">Sobre Rasante Superficie Computable:<span class="naranja">*</span></label></td>
	     		<td align="left" nowrap="nowrap"><s:textfield id="supComputable" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.superficieComputableSbRasante" size="10" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	     	</tr>
	       	<tr> 	
				<td align="center" nowrap="nowrap"  style="vertical-align:middle" colspan="6"><input type="button" class="boton" name="bguardarDatosPortalAlta" id="idDatosPortalAlta" value="Guardar portal" onClick="javascript:doPost('formData', 'aniadirDatosPortalAltaAltaSolicitudLicencia.action\u0023AltaEdificacionLC');" onKeyPress="this.onClick"/></td>
	       	</tr>
	     </table>
	     
	     <div id="divListadoDatosPlantasAlta">
			<jsp:include page="listDatosPlantasAlta.jsp" flush="false"></jsp:include>
		 </div>
		 
		<br/>
		<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
			<div class="acciones center">
				<input type="button" class="boton" name="bAniadirDatosPlantasAlta" id="idAniadirDatosPlantasAlta" value="Añadir planta" onClick="javascript:mostrarDivDatosPlantasAlta();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<br/>
		 
		 <s:if test="mostrarDivPlantaAlta">
			 <div id="divDatosPlantasAlta">
				<jsp:include page="datosPlantasAlta.jsp" flush="false"></jsp:include>
			 </div> 
		 </s:if>
	</div>
</div>
