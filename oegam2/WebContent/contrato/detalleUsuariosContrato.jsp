<%-- <%@ page import="utilidades.propiedades.PropertiesReader" %> --%>
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
  
    
 
<div class="contentTabs" id="UsuariosAsociadosContrato" >  
	<div class="contenido">		
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Usuarios Asociados al Contrato</td>
			</tr>
		</table>
			<display:table name="contratoDto.usuariosContrato" excludedParams="*" class="tablacoin" 
			summary="Listado de Usuarios Contrato" cellspacing="0" cellpadding="0" uid="listaUsuariosContratoTable"
			decorator="${decorator}">

				<display:column property="idUsuario" title="" media="none" paramId="idUsuario"/>
				
				<display:column property="nif" title="NIF" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%" />
				
			
				<display:column property="apellidosNombre" title="Apellidos y Nombre" sortable="false" headerClass="sortable" defaultorder="descending" style="width:20%" />
				
												
				<display:column property="correoElectronico" title="Correo Electrónico" sortable="false" headerClass="sortable" defaultorder="descending" style="width:25%" />	
					
				<display:column property="ultimaConexion" title="Última Conexión" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%" />	
					
				
		    	
				<display:column  style="width:1%" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idUsuariosContrato);' onKeyPress='this.onClick'/>">	
						<table align="left"> 
						
							<tr>
								<td style="border: 0px;">
 									<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
  										onclick="abrirEvolucionUsuario('<s:property value="#attr.listaUsuariosContratoTable.nif"/>');" title="Ver evolución"/>
				  				</td>
								<td style="border: 0px;">
				    			<input 	type="checkbox" name="idUsuariosContrato" 
				    				id="check_<s:property value="#attr.listaUsuariosContratoTable.idUsuario"/>" 
		      						value="${listaUsuariosContratoTable.idUsuario}"
		      						class="checkbox" />
								</td>
								
								
							</tr>
						</table>
				</display:column>   
			</display:table>
		
		<table class="acciones">
			<tr>
				<s:if test="%{!altaNuevo && (@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio())}">
	        		<td align="center">
                	 	<input type="button" class="boton" name="bAlta" id="bAlta" value="Asociar Usuarios" onClick="nuevoUsuarioAsociadoContrato();" onKeyPress="this.onClick" />&nbsp;                     
	        		</td> 
	        	</s:if>
	        	<s:if test="%{(contratoDto.usuariosContrato.size>0) && (@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio())}">
	          		<td align="center">
            			<input type="button" class="boton" name="bEliminar" id="bEliminar" value="Desasociar Usuarios" onClick="return eliminarUsuariosAsociadosContrato(<s:property value="#attr.contratoDto.idContrato"/>);" onKeyPress="this.onClick" />
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
	
	<div id="usuariosGestor"  style="display:none" >		
		<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Usuarios Asociados al Colegiado</td>
			</tr>
		</table>
	     
	     <display:table name="contratoDto.usuariosHabilitados" excludedParams="*" class="tablacoin" summary="Listado de Usuarios" cellspacing="0" cellpadding="0" uid="listaUsuariosTable">
			
				
				
				<display:column property="nif" title="NIF" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%;" />
				
				<display:column property="apellidosNombre" title="Apellidos y Nombre" sortable="false" headerClass="sortable" defaultorder="descending" style="width:20%;" />
					
					
					
				<display:column property="ultimaConexion" title="Última Conexión" sortable="false" headerClass="sortable" defaultorder="descending" style="width:15%;" />	
				
				
					
				<display:column  title="Estado Usuario"  headerClass="sortable" sortable="false" defaultorder="descending" style="width:20% ;color:red;" >
			   
					<s:property value="%{@org.gestoresmadrid.core.contrato.model.enumerados.EstadoUsuario@convertirTexto(#attr.listaUsuariosTable.estadoUsuario)}" />
				</display:column>

				<display:column style="width:1%;" title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.idUsuariosGestoria);' onKeyPress='this.onClick'/>">	
						
						<table align="left"> 
							<tr>
							<td style="border: 0px;">
 									<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
  										onclick="abrirEvolucionUsuario('<s:property value="#attr.listaUsuariosTable.nif"/>');" title="Ver evolución"/>
				  				</td>
						
								<td style="border: 0px;">
								<s:if test="#attr.listaUsuariosTable.estadoUsuario==1">	
				    			<input 	type="checkbox" name="idUsuariosGestoria" 
				    				id="check_<s:property value="#attr.listaUsuariosTable.idUsuario"/>" 
		      						value="${listaUsuariosTable.idUsuario}"
		      						class="checkbox" />
		      						</s:if>
								</td>
							
							</tr>
							
						</table>
						
					</display:column>
			
				
		</display:table>
	</div>	
	

	<table class="acciones">
		<tr>
			<td>
				<input type="button" class="boton" name="bGuardarUsuario" id="idBotonAgregarUsuarioContrato" value="Asociar" onClick="return agregarUsuarioGestoria(<s:property value="#attr.contratoDto.idContrato"/>);" onKeyPress="this.onClick"/>
				&nbsp;
			</td>
			<td align="center"> 
				<input type="button" class="boton" name="bCancelarUsuario" id="bCancelarUsuario" value="Cancelar" onClick="cancelarUsuarioContrato('nuevoUsuarioContrato');" onKeyPress="this.onClick" />
				&nbsp;                     
		     </td>
		</tr>
	</table>
		</div>
	</div>
<div id="divEmergenteConsultaUsuarioEvolucion" style="display: none; background: #f4f4f4;"></div>	

