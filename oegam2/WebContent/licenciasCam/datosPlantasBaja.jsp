<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<div id="datosPlantasBaja">
	
		<s:hidden name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDatosPlantaBaja.idDatosPlantaBaja"/>
	
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td>Datos Planta Baja </td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
	       	 <tr> 	
				<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblTipoPlanta">Tipo Planta: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap">
					<s:select id="tipoPlanta" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDatosPlantaBaja.tipoPlanta"
						list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().tipoPlanta()"
						listKey="codigo" listValue="descripcion" headerKey="" headerValue="Seleccionar Tipo Planta"/>
				</td>
	       		<td align="left" nowrap="nowrap"  style="vertical-align:middle"><label for="lblSupConstruida">Superficie Construida: <span class="naranja">*</span></label></td>
	       		<td align="left" nowrap="nowrap"><s:textfield id="superficieConstruida" name="lcTramiteDto.lcEdificacionBaja.lcInfoEdificioBaja.lcDatosPlantaBaja.supConstruida" size="18" maxlength="11" onkeypress="return soloNumeroDecimal(event, this, '7', '3')"/></td>
	       	</tr>
	       	<tr> 	
				<td align="center" nowrap="nowrap"  style="vertical-align:middle" colspan="4"><input type="button" class="boton" name="bguardarDatoPlantaBaja" id="idDatoPlantaBaja" value="Guardar planta" onClick="javascript:guardarPlantaBaja();"onKeyPress="this.onClick"/></td>
	       	</tr>
	     </table>
	</div>
</div>
