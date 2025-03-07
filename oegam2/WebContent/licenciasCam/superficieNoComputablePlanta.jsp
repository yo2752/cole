<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="superficieNocumputablePlanta">
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Superficie No cumputable Planta </td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
       		<tr> 	
				<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblTipoPlanta">Tipo: <span class="naranja">*</span></label></td>
       			<td align="left" nowrap="nowrap">
					<s:select id="tipo" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.lcSupNoComputablePlanta.tipo"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().superficieNoComputable()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo"/>
				</td>
            	<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblNPlanta">Tamaño: <span class="naranja">*</span></label></td>
       			<td align="left" nowrap="nowrap"><s:textfield id="tamanio" name="lcTramiteDto.lcEdificacionAlta.lcInfoEdificioAlta.lcDatosPortalAlta.lcDatosPlantaAlta.lcSupNoComputablePlanta.tamanio" size="20" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
      		</tr>
       		<tr> 	
				<td align="center" nowrap="nowrap"  style="vertical-align:middle" colspan="8"><input type="button" class="boton" name="bguardarSuperficieNocumputablePlanta" id="idSuperficieNocumputablePlanta" value="Guardar superficie" onClick="javascript:doPost('formData', 'aniadirSupNoComputablePlantaAltaSolicitudLicencia.action\u0023AltaEdificacionLC');"onKeyPress="this.onClick"/></td>
       		</tr>
	     </table>
	</div>
</div>
