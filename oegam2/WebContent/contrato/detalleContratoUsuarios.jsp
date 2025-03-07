<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
  
<div class="contentTabs" id="UsuariosAsociados" >  
	<div class="contenido">		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Usuarios Asociados al Colegiado</td>
			</tr>
		</table>

		<display:table name="contratoDto.usuarios" excludedParams="*" class="tablacoin" summary="Listado de Usuarios" cellspacing="0" cellpadding="0" uid="listaUsuariosTable">

				<display:column property="nif" title="NIF" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%" />
				
				<display:column property="apellidosNombre" title="Apellidos y Nombre" sortable="false" headerClass="sortable" defaultorder="descending" style="width:20%" />
					
				<display:column property="correoElectronico" title="Correo Electrónico" sortable="false" headerClass="sortable" defaultorder="descending" style="width:25%" />	
					
				<display:column property="ultimaConexion" title="Última Conexión" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%" />	
					
				<display:column title="Estado Usuario" headerClass="sortable" sortable="false" defaultorder="descending" style="width:20% ;color:red"   >
					<s:property value="%{@org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario@convertirTexto(#attr.listaUsuariosTable.estadoUsuario)}" />
				</display:column>
		    	
				<display:column  style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idUsuarios);' onKeyPress='this.onClick'/>">	
						<table align="left"> 
							<tr>
							    <s:if test="%{@trafico.utiles.UtilesVistaTrafico@getInstance().gestionarUsuariosContrato()}">
							    <td style="border: 0px;">
 									<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
  										onclick="abrirEvolucionUsuario('<s:property value="#attr.listaUsuariosTable.nif"/>');" title="Ver evolución"/>
				  				</td>
				  				</s:if>
								<td style="border: 0px;">
				    			<input 	type="checkbox" name="idUsuarios" 
				    				id="check<s:property value="#attr.listaUsuariosTable.idUsuario"/>" 
		      						value="${listaUsuariosTable.idUsuario}"
		      						class="checkbox" />
								</td>
								<td style="border: 0px;">				  		
							  		<img src="img/mostrar.gif" alt="Modificar Usuario" style="height:20px;width:20px;cursor:pointer;" 
							  		onclick="recuperarUsuario('${listaUsuariosTable.idUsuario}');" title="Modificar Usuario"/>
							  	</td>
							</tr>
						</table>
						
					</display:column>   
		</display:table>
  <table class="acciones">
			<tr>
				<s:if test="%{!altaNuevo}">
	        		<td align="center">
                	 	<input type="button" class="boton" name="bAlta" id="bAlta" value="Alta Nuevo Usuario" onClick="nuevoUsuarioColegiado();" onKeyPress="this.onClick" />&nbsp;                     
	        		</td>
	        	</s:if>
	        	<s:if test="%{contratoDto.usuarios.size>0}">
	        		<td align="center">
	        			<input type="button" class="boton" name="bBloquear" id="bBloquear" value="Habilitar Usuarios" onClick="return habilitarUsuariosColegiado();"  onKeyPress="this.onClick" />
            			 &nbsp;
            		 </td>
            		 <td align="center">
            			<input type="button" class="boton" name="bDesbloquear" id="bDesbloquear" value="Deshabilitar Usuarios" onClick="return deshabilitarUsuariosColegiado();" onKeyPress="this.onClick" />
               			&nbsp;
               		</td>
               		<td align="center">
            			<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Eliminar Usuarios" onClick="return eliminarUsuariosColegiado();" onKeyPress="this.onClick" />
            		</td>
          		</s:if>
	    </tr>
	</table>
	
	
	<table class="acciones">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td>
						<input type="button" class="boton" name="bGuardar" id="idBotonGuardar" value="Guardar" onClick="return modificacionContrato('AplicacionesAsociadas');" onKeyPress="this.onClick"/>
						&nbsp;	
					</td>
				</s:if>
				<td align="center">
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="volverContrato();" onKeyPress="this.onClick" />&nbsp;                     
			     </td>
			</tr>
	</table>

	<div id="nuevoUsuario" style="display: none;">
		<%@include file="nuevoUsuario.jsp" %>
	</div>
	
	<s:if test="%{usuarioSel}">
		<div id="modificarUsuarioSeleccionado" style="display: block;">
			<%@include file="usuarioSeleccionado.jsp" %>
		</div>
	</s:if>
		
	</div>
</div>


