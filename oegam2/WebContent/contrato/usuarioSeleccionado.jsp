 <%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<s:hidden name="usuarioDto.idUsuario"></s:hidden>
<s:hidden name="usuarioDto.jefaturaJPT"></s:hidden>
<s:hidden name="usuarioDto.password"></s:hidden>
<s:hidden name="usuarioDto.anagrama"></s:hidden>
<s:hidden name="usuarioDto.usuario"></s:hidden>
<s:hidden name="usuarioDto.estadoUsuario"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.dia"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.mes"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.anio"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.hora"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.minutos"></s:hidden>
<s:hidden name="usuarioDto.ultimaConexion.segundos"></s:hidden>
<s:hidden name="usuarioDto.fechaNotificacion.dia"></s:hidden>
<s:hidden name="usuarioDto.fechaNotificacion.mes"></s:hidden>
<s:hidden name="usuarioDto.fechaNotificacion.anio"></s:hidden>
<s:hidden name="usuarioDto.fechaRenovacion.dia"></s:hidden>
<s:hidden name="usuarioDto.fechaRenovacion.mes"></s:hidden>
<s:hidden name="usuarioDto.fechaRenovacion.anio"></s:hidden>

<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Datos del Usuario</td>
	</tr>
</table> 
	
<table cellSpacing="3" class="tablaformbasica" cellPadding="0" summary="Datos usuario seleccionado">
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="nombre">Apellidos y Nombre<span class="naranja">*</span></label>
			<s:textfield name="usuarioDto.apellidosNombre" id="nombreUsuarioSeleccionado" onblur="this.className='input2';" 
				onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
		<td  align="left" nowrap="nowrap">
			<label for="nifNieUsuarioSeleccionado">NIF/NIE</label>			
			<s:textfield name="usuarioDto.nif" id="nifNieUsuarioSeleccionado" onblur="this.className='input2'; calculaLetraDni('nifNieUsuarioSeleccionado');" 
				onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
		</td>
		<td align="left" nowrap="nowrap">
	        <label for="estadoUsuario">Estado Usuario</label>
			<s:textfield name="usuarioDto.estadoUsuario" value="%{@org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario@convertirTexto(usuarioDto.estadoUsuario)}"
	       		id="estadoUsuarioSeleccionado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" cssClass="inputview" readonly="true" disabled="true"/>
		</td>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<label for="correoElectronicoUsuario">Correo Electr&oacute;nico<span class="naranja">*</span></label>	
			<s:textfield name="usuarioDto.correoElectronico" id="correoElectronicoUsuarioSeleccionado" onblur="this.className='input2';" 
				onfocus="this.className='inputfocus';" size="60" maxlength="100"/>
		</td>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
			<td align="left" nowrap="nowrap"><br/>
				<label for="fechaAlta">Fecha Alta</label>			                        					                      		
				<s:textfield name="usuarioDto.fechaAlta" id="fechaAlta" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					cssClass="inputview" readonly="true" disabled="true"/>
				<s:hidden id="idFechaAlta" name="usuarioDto.fechaAlta.dia"></s:hidden>
				<s:hidden id="idFechaAlta" name="usuarioDto.fechaAlta.mes"></s:hidden>
				<s:hidden id="idFechaAlta" name="usuarioDto.fechaAlta.anio"></s:hidden>
			</td>
			<td align="left" nowrap="nowrap"><br/>
				<label for="fechaFin">Fecha Fin</label>			                        					                      		
				<s:textfield name="usuarioDto.fechaFin" id="fechaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
					cssClass="inputview" readonly="true" disabled="true"/>
				<s:hidden id="idFechaFin" name="usuarioDto.fechaFin.dia"></s:hidden>
				<s:hidden id="idFechaFin" name="usuarioDto.fechaFin.mes"></s:hidden>
				<s:hidden id="idFechaFin" name="usuarioDto.fechaFin.anio"></s:hidden>
			</td>				
		</s:if>
	</tr>
	<tr>
		<td align="left" nowrap="nowrap">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<label for="GrupoUsuario">Grupo Usuario Especial</label>
				<s:select list="@trafico.datosSensibles.utiles.UtilesVistaDatosSensibles@getInstance().listarGrupos()"					
					name="usuarioDto.idGrupo" listKey="idGrupo" listValue="descGrupo" headerKey="-1" headerValue="Ninguno" 						
					id="idGrupoUsuario" />
			</s:if>
			<s:else>
				<s:hidden name="usuarioDto.idGrupo"/>
			</s:else>
		</td>
	</tr>
</table>
		
<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
		<td>Permisos contrato/usuario </td>
	</tr>
</table>
									
<display:table name="usuarioDto.permisos" excludedParams="*" class="tablacoi" summary="Listado de Funciones" decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorAplicacionSeleccionada" cellspacing="0" 
	cellpadding="0" uid="listaPermisosUsuariosTable">
		
		<display:column title="C&oacute;digo Aplicaci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending"							
			style="width:20%;text-align:left;" > 
			<s:iterator var="counter" begin="0" end="#attr.listaPermisosUsuariosTable.nivel - 1" >
    			&nbsp;
				&nbsp;
 			</s:iterator> 			
	 			<s:if test="%{#attr.listaPermisosUsuariosTable.esPadre}">
					<img id="desp${listaPermisosUsuariosTable.codigoFuncion}"  alt="Mostrar" src="img/plus.gif" onclick="return mostrarLineas('${listaPermisosUsuariosTable.codigoFuncion}');" style="cursor:pointer;"/>
				</s:if>
				<s:else>
				&nbsp;
				&nbsp;
				</s:else>
				${listaPermisosUsuariosTable.codigoAplicacion}
		</display:column>
		
		<display:column property="codFuncionPadre" title="Funci&oacute;n Padre" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
		<display:column property="codigoFuncion" title="Funci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
		<display:column property="descFuncion" title="Descripci&oacute;n" sortable="false" headerClass="sortable" defaultorder="descending" style="width:40%" />
						
		<display:column property="url" title="URL" sortable="false" headerClass="sortable" defaultorder="descending" style="width:10%" maxLength="12"/>
			
		<display:column property="nivel" title="Nivel" defaultorder="descending" style="width:5%" />
			
		<display:column property="tipo" title="Tipo" sortable="false" headerClass="sortable" defaultorder="descending" style="width:5%" />
			
	    <display:column style="width:5%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.getElementsByName(\"permisosUsuarios\"));' onKeyPress='this.onClick'/>">
	    	<s:if test="%{ #attr.listaPermisosUsuariosTable.codigoFuncion == 'OT02' || #attr.listaPermisosUsuariosTable.codigoFuncion == 'OT2T' || #attr.listaPermisosUsuariosTable.codigoFuncion == 'OT7M'}">
		    		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">			
				    	<input 	type="checkbox" name="permisosUsuarios" value="${listaPermisosUsuariosTable.codigoFuncion}" class="checkbox"  
		      			<s:if test="%{#attr.listaPermisosUsuariosTable.asignada==1}">
		      				checked=checked
		      			</s:if>/>
  					</s:if>
			</s:if>
			<s:else>
				<input 	type="checkbox" name="permisosUsuarios" value="${listaPermisosUsuariosTable.codigoFuncion}" class="checkbox"  
	      			<s:if test="%{#attr.listaPermisosUsuariosTable.asignada==1}">
	      				checked=checked
	      			</s:if>/>
			</s:else>
		</display:column>
</display:table>
		
		<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bGuardarUsuario" id="idBotonGuardarUsuario" value="Guardar Usuario" onClick="return guardarUsuarioContrato();" onKeyPress="this.onClick"/>
				&nbsp;
					
			</td>
			<td align="center">
				<input type="button" class="boton" name="bCancelarUsuario" id="bCancelarUsuario" value="Cancelar" onClick="cancelarUsuario('nuevoUsuario');" onKeyPress="this.onClick" />&nbsp;                     
		     </td>
		</tr>
	</table>

<script type="text/javascript">ocultaHijos();</script>