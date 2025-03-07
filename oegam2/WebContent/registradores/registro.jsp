<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap" width="40px">
			<label for="idProvinciaRegistro">Provincia:</label>
		</td>
		<td align="left" nowrap="nowrap" width="140px">
			<s:select list="@org.gestoresmadrid.oegam2comun.registradores.utiles.UtilesVistaRegistradores@getInstance().getProvinciasBienes()"
				onblur="this.className='input2';" onfocus="this.className='inputfocus';"
				headerKey="-1" headerValue="Seleccione Provincia" name="tramiteRegistro.registro.idProvincia"  
				listKey="idProvincia" listValue="nombre" id="provinciaSeleccionada" onchange="cargarRegistrosProvincia()"/>		
		</td>
		<td align="left" nowrap="nowrap" colspan="1" width="40px">
			<label for="nombreRegistro">Registro:</label>
		</td>
		<td align="left" nowrap="nowrap" colspan="1">
			<select id="registroSeleccionado" onchange="seleccionarRegistro();" onblur="this.className='input2';" 
				onfocus="this.className='inputfocus';" name="tramiteRegistro.registro.id">
					<option value="-1">Seleccione Registro</option>
			</select>
		</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>									
	<tr>
		<td align="left" nowrap="nowrap" >
			<label for="registroSeleccionadoCaja">Código registro<span class="naranja">*</span>:</label>
		</td>
		<td align="left" nowrap="nowrap" >
			<s:textfield id="registroSeleccionadoCaja" name="tramiteRegistro.registro.idRegistro"
				size="6" maxlength="5" onblur="this.className='input';" readonly="true"
		        onkeypress="return validarNumeros(event)" onfocus="this.className='inputfocus';" />
		</td>
		<s:hidden id="registroSeleccionadoOculto" name="tramiteRegistro.registroSeleccionadoOculto"/>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
</table>