<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="contenido">	
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de la Licencia</td>
			<s:if test="lcTramiteDto.numExpediente">
				<td  align="right">
					<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
	  					onclick="abrirEvolucion(<s:property value="%{lcTramiteDto.numExpediente}"/>,'divEmergentePopUp');" title="Ver evolución"/> 					
				</td>
			</s:if>
		</tr>
	</table>
		<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">	
		<tr>
			<td class="tabular">
				<span class="titulo">Licencia</span>
			</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelNumExpediente">Núm. Expediente:</label>
			</td> 
			<td align="left" nowrap="nowrap">
				<s:textfield id="idNumExpediente" name="lcTramiteDto.numExpediente"  onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';" size="20" maxlength="20" readonly="true"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelEstado">Estado trámite: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:select list="@org.gestoresmadrid.oegam2comun.licenciasCam.utiles.UtilesLicencias@getInstance().getEstadoLicencias()" onblur="this.className='input2';" 
			    		onfocus="this.className='inputfocus';"  headerKey="-1"	headerValue="Seleccione Estado" 
			    		name="lcTramiteDto.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoLicencias" disabled="true"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelTelefono">Teléfono:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idTelefono" name="lcTramiteDto.telefono"  onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';" size="20" maxlength="15"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="labelCorreoElectronico">Correo Electrónico:</label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:textfield id="idMail" name="lcTramiteDto.correoElectronico"  onblur="this.className='input2'; validaEmail(this.value);" 
			       				onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
   				<label for="refPropiaResumen">Referencia Propia: </label>
   			</td>
         	<td align="left" nowrap="nowrap">
       			<s:textfield id="refPropiaLc" name="lcTramiteDto.refPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="50" maxlength="50"/>
   			</td>	
			<td align="left" nowrap="nowrap" width="25%" style="vertical-align:middle">
				<label for="numAutoliquidacionPagada">Núm. Autoliquidación Pagada: </label>
			</td>
       		<td align="left" nowrap="nowrap"><s:textfield id="numAutoliquidacionPagada" name="lcTramiteDto.numAutoliquidacionPagada" size="20" maxlength="17"/></td>
       	</tr>
      </table>
</div>
