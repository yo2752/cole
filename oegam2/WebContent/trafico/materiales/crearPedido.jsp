<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="js/trafico/materiales/crearPedido.js" type="text/javascript"></script>

<s:set var="accion" value="accionPedido" />
<s:set var="pedidoEliminado" value="pedidoEliminado" />

<s:hidden name="accionPedido"        id="idAccion"/>
<s:hidden name="pedidoEliminado"     id="pedidoEliminado" />

<!-- 
<s:set var="accion2" value="accionPedido" />
 -->
<s:set var="pedidoPermiso" value="%{permitirCambioEstado}" />

<s:if test="%{#accion == 'consulta'}">
	<s:set var="mensajeCab" value="%{'Consultar Pedido'}" />
</s:if>
<s:if test="%{#accion == 'crear'}">
	<s:set var="mensajeCab" value="%{'Crear Pedido'}" />
</s:if>
<s:if test="%{#accion == 'modifica'}">
	<s:set var="mensajeCab" value="%{'Modificar Pedido'}" />
</s:if>
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo"><s:property value="mensajeCab" /></span>
				</td>
			</tr>
		</table>
	</div>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="pedidoDto.pedidoDgt" id="idPedidoDgt"/>
		
		<div id="busqueda">					
			
		    <%@include file="../../includes/erroresMasMensajes.jspf" %>
				
			<table class="tablaformbasica" border="0">
				
				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
				</tr>

				<s:if test="%{#accion != 'crear'}">
					<tr>
						<td align="left" nowrap="nowrap" width="25%">
							<label for="labelidpedido">Pedido:</label>
						</td>
						<td  align="left" width="25%">
							<s:textfield name="pedidoDto.pedidoId" 
										 id="labelidpedido" 
							             size="25" maxlength="25" 
							             readonly="true"/>
						</td>
						<td align="left" nowrap="nowrap" width="10%">
							<label for="labelestado">Estado:</label>
						</td>
						<td align="left" width="40%">
							<s:if test="%{#pedidoPermiso == true}">
			       				<s:select 
									list="@org.gestoresmadrid.oegam2comun.trafico.materiales.utiles.enumerados.UtilesEstadosPedido@getEstadoPedido()"
									id="labelEstado"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									name="pedidoDto.estado"
									headerKey=""
							   		headerValue="Seleccione un estado"
									listValue="nombreEnum"
									listKey="valorEnum"
									title="Estado"
									disabled="false"/>
							</s:if>
							<s:else>
								<s:hidden name="pedidoDto.estado" id="idCodEstado"/>
								<s:textfield name="pedidoDto.nomEstado" 
											 id="labelestado" 
								             size="25" maxlength="25" 
								             readonly="true"/>
							</s:else>
						</td>
						
					</tr>
				</s:if>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
				</tr>
				
		      	<tr>
					<td align="left" nowrap="nowrap" width="25%">
						<label for="labelJefaturaProvJpt">Jefatura Provincial<span class="naranja">*</span>: </label>
					</td>
					<td  align="left" width="25%">
						<s:if test="%{#accion == 'crear'}">
							<s:select id="labelJefaturaProvJpt"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getJefaturasJPTEnums()"
								headerKey=""
						   		headerValue="Seleccione Jefatura"
								name="pedidoDto.jefaturaProvincial" 
								listKey="jefatura" 
								listValue="descripcion"/>
						</s:if>
						<s:else>
							<s:textfield name="pedidoDto.jefaturaDescripcion" id="labelJefaturaProvJpt" 
							             size="25" maxlength="25" readonly="true" />
						</s:else>		
					</td>
					<td nowrap="nowrap" width="10%"></td>
					<td width="40%"></td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
				</tr>
				
				<tr>
					<td align="left" nowrap="nowrap" width="25%">
						<label for="celdaMateriales">Material<span class="naranja">*</span>:</label>
					</td>
					<td width="25%">
						<s:select id="celdaMateriales"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getComboMateriales()"
							headerKey=""
					   		headerValue="Seleccione Material"
							name="pedidoDto.materialId" 
							listKey="materialId" 
							listValue="nombre"/>	
					</td>
					
					<td align="left" nowrap="nowrap" width="10%">
						<label for="celdaUnidades">Unidades<span class="naranja">*</span>:</label>
					</td>
					<td align="left"  width="40%">
						<s:textfield name="pedidoDto.unidades" id="celdaUnidades" 
						             size="20" maxlength="10"
						             readonly="true"/>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
				</tr>

				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelobservaciones">Observaciones<span class="naranja">*</span>:</label>
					</td>
					<td  align="left" colspan="3">
						<s:textarea id="labelobservaciones" 
									name="pedidoDto.observaciones" 
									cols="80" rows="2" maxlength="1900"
									onblur="this.className='input';" 
							        onfocus="this.className='inputfocus';" 
							        readonly="#accion == 'consulta'"/>
					</td>
				</tr>

				<tr>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
					<td nowrap="nowrap" style="vertical-align: middle;" colspan="2" width="50%">&nbsp;</td>
				</tr>

			</table>
	
			<s:if test="#accion == 'crear' || #accion == 'modifica' ">
				<div class="acciones center">
					<input type="button" class="boton" 
					       name="bGuardarPedido" 
					       id="idGuardarPedido" 
					       value="Guardar" 
					       onClick="javascript:guardarPedido();"
			 			   onKeyPress="this.onClick"/>
			 			   
					<input type="button" class="boton" 
					       name="bLimpiarPedido" 
					       id="idLimpiarPedido" 
					       value="Limpiar" 
					       onClick="javascript:limpiarPedido();"
			 			   onKeyPress="this.onClick"/>
					<s:if test="#accion == 'modifica' ">
						<input type="button" class="boton" 
						       name="bConfirmarPedido" 
						       id="idConfirmarPedido" 
						       value="Confirmar" 
						       onClick="javascript:confirmarPedido();"
				 			   onKeyPress="this.onClick"/>
						<input type="button" class="boton" 
						       name="bDeletePedido" 
						       id="idDeletePedido" 
						       value="Eliminar" 
						       onClick="javascript:elimiarPedido();"
				 			   onKeyPress="this.onClick"/>
					
					</s:if>
			 			   
				</div>
			</s:if>	
		</div>		    
    
</s:form>
 

