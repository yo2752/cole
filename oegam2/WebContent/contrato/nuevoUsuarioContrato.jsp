 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
  	
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Agregar de un nuevo Usuario Contrato </td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos usuario alta">
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="nombre">Apellidos y Nombre<span class="naranja">*</span></label>
			
			
				<s:textfield name="usuarioNuevoDto.apellidosNombre" id="nombreUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					size="60" maxlength="100"/>
					
					
					
					
			</td>
			<td  align="left" nowrap="nowrap">
				<label for="nifNieUsuarioPpal">NIF/NIE<span class="naranja">*</span></label>
				<s:textfield name="usuarioNuevoDto.nif" id="nifNieUsuario" onblur="this.className='input2'; calculaLetraDni('nifNieUsuario');" 
					onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="correoElectronicoUsuario">Correo Electr&oacute;nico<span class="naranja">*</span></label>	
			 		<s:textfield name="usuarioNuevoDto.correoElectronico" id="correoElectronicoUsuario" onblur="this.className='input2';" 
						onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
			</td>
			<td align="left" nowrap="nowrap">
				<label for="estadoUsuario">Estado Usuario</label>
        		<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstados()"
					id="estadoUsuario" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
					name="usuarioNuevoDto.estadoUsuario" listValue="nombreEnum" listKey="valorEnum" title="Estado Usuario"/>
			</td>
		</tr>
		
	</table>
	<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bGuardarUsuario" id="idBotonGuardarUsuario" value="Alta Usuario" onClick="return altaNuevoUsuarioPorContrato();" onKeyPress="this.onClick"/>
				&nbsp;
			</td>
			<td align="center"> 
				<input type="button" class="boton" name="bCancelarUsuario" id="bCancelarUsuario" value="Cancelar" onClick="cancelarUsuarioContrato('nuevoUsuarioContrato');" onKeyPress="this.onClick" />
				&nbsp;                     
		     </td>
		</tr>
	</table>