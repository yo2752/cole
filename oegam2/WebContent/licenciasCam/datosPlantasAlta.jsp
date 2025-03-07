<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="datosPlantasAlta">
	
		<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.idDatosPlantaAlta"/>
		<s:hidden name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.lcSupNoComputablePlanta.idSupNoComputablePlanta"/>
			
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos Planta Alta </td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
				<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblTipoPlanta">Tipo Planta: <span class="naranja">*</span></label></td>
				<td align="left" nowrap="nowrap">
					<s:select id="tipoPlanta" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.tipoPlanta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Planta"/>
				</td>
	            <td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNPlanta">Número Planta: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="nPlanta" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.numPlanta" size="5" maxlength="4"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblAlturaLibre">Altura Libre: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="alturaLibre" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.alturaLibre" size="14" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblAlturaPiso">Altura Piso: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="alturaPiso" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.alturaPiso" size="14" maxlength="5" onkeypress="return soloNumeroDecimal(event, this, '2', '2')"/></td>
	      	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblUsoPlanta">Uso Planta: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
					<s:select id="usoPlanta" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.usoPlanta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tiposUso()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Uso"/>
				</td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNUnidades">Número Unidades: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="nUnidades" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.numUnidades" size="5" maxlength="4" onkeypress="return soloNumeroDecimal(event, this, '4', '0')"/></td>
	       	</tr>
	       	<tr>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupCons">Superficie Construida: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="supCons" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.supConstruida" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupCom">Superficie Computable: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="supCom" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.supComputable" size="14" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	        </tr>  
	       	<tr> 	
				<td align="center" nowrap="nowrap"  style="vertical-align:middle" colspan="8"><input type="button" class="boton" name="bguardarDatoPlantaAlta" id="idDatoPlantaAlta" value="Guardar planta" onClick="javascript:doPost('formData', 'aniadirDatosPlantaAltaAltaSolicitudLicencia.action\u0023AltaEdificacionLC');" onKeyPress="this.onClick"/></td>
	       	</tr>
	    </table>
	</div>
	
	<div id="divListadoSuperficiesNoComputablesPlanta">
		<jsp:include page="listSuperficiesNoComputablesPlanta.jsp" flush="false"></jsp:include>
	</div>
	
	<br/>
	<s:if test="%{@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().esEditable(lcTramiteDto)}">
		<div class="acciones center">
			<input type="button" class="boton" name="bAniadirSuperficieNoComputablePlanta" id="idAniadirSuperficieNoComputablePlanta" value="Añadir superficie" onClick="javascript:mostrarDivSuperficieNoComputablePlanta();"onKeyPress="this.onClick"/>
		</div>
	</s:if>
	<br/>
		
	<s:if test="mostrarDivSupNoComputable">
		<div id="divSuperficieNoComputablePlanta">
			<jsp:include page="superficieNoComputablePlanta.jsp" flush="false"></jsp:include>
		</div> 
	</s:if>
</div>
