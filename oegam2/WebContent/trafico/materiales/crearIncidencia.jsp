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
<!-- <script src="js/trafico/materiales/crearPedido.js" type="text/javascript"></script> -->
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script src="js/trafico/materiales/crearIncidencia.js" type="text/javascript"></script>

<s:set var="accion" value="accionIncidencia" />

<s:if test="%{#accion == 'consultar'}">
	<s:set var="mensajeCab" value="%{'Consulta de Materiales Defectuosos'}" />
</s:if>
<s:if test="%{#accion == 'crear'}">
	<s:set var="mensajeCab" value="%{'Alta de Materiales Defectuosos'}" />
</s:if>
<s:if test="%{#accion == 'modificar'}">
	<s:set var="mensajeCab" value="%{'Modificación de Materiales Defectuosos'}" />
</s:if>

<s:hidden name="accionIncidencia" id="idAccion"/>

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo"><s:property value="mensajeCab" /></span>
				</td>
			</tr>
		</table>
	</div>

	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Datos Incidencia</td>
			</tr>
		</tbody>
	</table>
	
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="incidenciaDto.incidenciaId" id="idIncidenciaId" />

		<div id="contenido">					
			
		    <%@include file="../../includes/erroresMasMensajes.jspf" %>
				
			<table class="tablaformbasica" border="0">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelJefaturaProvJpt">Jefatura Provincial<span class="naranja">*</span>: </label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:if test="%{#accion == 'crear'}">
							<s:select id="labelJefaturaProvJpt"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getJefaturasJPTEnums()"
								headerKey=""
						   		headerValue="Seleccione Jefatura"
								name="incidenciaDto.jefaturaProvincial" 
								listKey="jefatura" 
								listValue="descripcion"/>
						</s:if>
						<s:else>	
							<s:hidden name="incidenciaDto.jefaturaProvincial" id="idjefaturaProvincial" />
							<s:textfield name="incidenciaDto.jefaturaDescripcion" id="labelJefaturaProvJpt" 
							             size="25" maxlength="25" 
							             onblur="this.className='input';" 
										 onfocus="this.className='inputfocus';"
										 readonly="true"/>												
						</s:else>		
					</td>
					
					<td align="left" nowrap="nowrap">
						<label for="celdaMateriales">Material<span class="naranja">*</span>:</label>
					</td>
					<td align="left">
						<s:if test="%{#accion == 'crear'}">
							<s:select id="celdaMateriales"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getComboMateriales()"
								headerKey=""
						   		headerValue="Seleccione Material"
								name="incidenciaDto.materiaId" 
								listKey="materialId" 
								listValue="nombre"/>	
						</s:if>
						<s:else>	
							<s:hidden name="incidenciaDto.materiaId" id="idmateriaId" />
							<s:textfield name="incidenciaDto.materiaNombre" id="celdaMateriales" 
							             size="25" maxlength="25" 
							             onblur="this.className='input';" 
										 onfocus="this.className='inputfocus';"
										 readonly="#accion != 'crear'"/>												
						</s:else>		
					</td>
				</tr>
				
				<tr>
					<td align="left" nowrap="nowrap" >
						<label for="labelobservaciones">Observaciones:</label>
					</td>
					<td  align="left" colspan="3">
						<s:textarea id="labelobservaciones" 
									name="incidenciaDto.observaciones" 
									cols="80" rows="3" maxlength="1900"
									onblur="this.className='input';" 
							        onfocus="this.className='inputfocus';" 
							        readonly="#accion == 'consultar'"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labeTipo">Tipo<span class="naranja">*</span>:</label>
					</td>
					<td align="left">
						<s:if test="%{#accion != 'consultar'}">
							<s:select id="labeTipo"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								list="@org.gestoresmadrid.oegam2.trafico.materiales.util.vista.UtilVistaMaterial@getInstance().getTipoIncidencia()"
								headerKey=""
						   		headerValue="Seleccione Tipo Incidencia"
								name="incidenciaDto.tipo" 
								listKey="valorEnum" 
								listValue="nombreEnum"/>	
						</s:if>
						<s:else>
							<s:textfield name="incidenciaDto.tipoNombre" id="labeTipo" 
							             size="25" maxlength="25" 
							             onblur="this.className='input';" 
										 onfocus="this.className='inputfocus';"
										 readonly="#accion == 'consultar'"/>												
						</s:else>		
					</td>
					<td></td>
					<td></td>
				</tr>
			</table>
			
			<table class="subtitulo" align="left" cellspacing="0">
				<tbody>
					<tr>
						<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
						<td>Relación de Números de Serie</td>
					</tr>
				</tbody>
			</table>
	
			<table class="tablaformbasica" border="0">
				<tr>
					<td>
						<table border="0">
							<tr>
								<s:if test="%{#accion != 'consultar'}">
									<td align="left" nowrap="nowrap">
										<table width="400">
											<tr>
												<td align="left" nowrap="nowrap">
													<table>
														<tr>
															<td>
																<label for="prefijoid">Prefijo:</label>
															</td>
															<td>
																<s:textfield name="prefijo" id="idPrefijo" size="4" maxlength="4" />
															</td>
															<td>
																<label for="serialid">Serial:</label>
															</td>
															<td>
																<s:textfield name="serial"  
																             id="idSerial" 
																             size="10" maxlength="10" 
																             onkeypress="return isNumber(event)" />
															</td>
															<s:hidden name="numSerie" id="idNumSerie" />
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td align="left" nowrap="nowrap">
													<input type="button" class="boton" 
												       name="bAddSerial" 
												       id="idAddSerial" 
												       value="Añadir Serial" 
												       onClick="javascript:addSerial();"
										 			   onKeyPress="this.onClick"/> 
													<input type="button" class="boton" 
												       name="bRemoveSerial" 
												       id="idRemoveSerial" 
												       value="Eliminar Serial" 
												       onClick="javascript:removeSerial();"
										 			   onKeyPress="this.onClick"/>
												</td>
											</tr>
										</table>
									</td>
								</s:if>			
								<td align="left" nowrap="nowrap" colspan="2">
									<s:if test="%{incidenciaDto.listaSerial.size > 0}">
										<table border="0" width="200">
										  <tr>
											    <th bgcolor="#9e233f" align="center">
											    	<font color="#fff">Inc.</font>
											    </th>
											    <th bgcolor="#9e233f" >
											    	<font color="#fff">Numero Serie</font>
											    </th>
										  		<th bgcolor="#9e233f" colspan="2">&nbsp;</th>
										  </tr>					
										  <s:iterator value="incidenciaDto.listaSerial" status="key">
										  	  <tr>
												  <td align="center">
	  												  <s:if test="%{incidenciaDto.listaSerial[#key.index].incidenciaInve != ''}">
														  <s:property value="incidenciaDto.listaSerial[#key.index].incidenciaInve" /> 
														  <s:hidden name="incidenciaDto.listaSerial[%{#key.index}].incidenciaInve" />
												      </s:if>
												      <s:else>
														  &nbsp;
												      </s:else>
											      </td>
												  <td align="center">
													  <s:property value="incidenciaDto.listaSerial[#key.index].numSerie" /> 
													  <s:hidden name="incidenciaDto.listaSerial[%{#key.index}].numSerie" />
												  </td>
												  <td align="center">
													  <s:if test="%{#accion != 'consultar'}">
														  <s:checkbox name="incidenciaDto.listaSerial[%{#key.index}].borrar"
														   			  id="serial_%{#key.index}"   
														  			  value="false" onchange="javascript:toggleButtonSerial();"/>
													  </s:if>
												  </td>		  
											  </tr>
										  </s:iterator>								
										</table>
									</s:if>		
								<td>
							</tr>
						</table>
					</td>	
				</tr>
			</table>
			
			<table class="subtitulo" align="left" cellspacing="0">
				<tbody>
					<tr>
						<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
						<td>Relación de Intervalos de Números de Serie</td>
					</tr>
				</tbody>
			</table>
	
			<table class="tablaformbasica" border="0">
				<tr>
					<td>
						<table border="0">
							<tr>
							    <s:if test="%{#accion != 'consultar'}">
									<td>
										<table width="400" border="0">
											<tr>
												<td>
													<table>
														<tr>
															<td align="left" nowrap="nowrap">
																<label for="prefijoInt">Prefijo Seriales:</label>
															</td>
															<td>
																<s:textfield name="prefijoInt" id="prefijoIntId"  size="4" maxlength="4" />
															</td>
														</tr>
													</table>
												</td>
											</tr>
											<tr>
												<td>
													<table>
														<tr>
															<td align="left" nowrap="nowrap">
																<label for="serialIniId">Serial Inicial:</label>
															</td>
															<td>
																<s:textfield name="serialIni" 
																             id="serialIniId" 
																             size="10" maxlength="10"
																             onkeypress="return isNumber(event)" />
															</td>
															
															<td align="left" nowrap="nowrap">
																<label for="serialFinId">Serial Final:</label>
															</td>
															<td>
																<s:textfield name="serialFin" 
																             id="serialFinId"  
																             size="10" maxlength="10" 
																             onkeypress="return isNumber(event)" />
															</td>
															<s:hidden name="numSerieIni" id="idNumSerieIni" />
															<s:hidden name="numSerieFin" id="idNumSerieFin" />
														</tr>
													</table>
												</td>
											</tr>
											
											<tr>
												<td align="left" nowrap="nowrap">
													<input type="button" class="boton" 
												       name="bAddInterval" 
												       id="idAddInterval" 
												       value="Añadir Intervalo" 
												       onClick="javascript:addInterval();"
										 			   onKeyPress="this.onClick"/> 
													<input type="button" class="boton" 
												       name="bRemoveInterval" 
												       id="idRemoveInterval" 
												       value="Eliminar Intervalo" 
												       onClick="javascript:removeInterval();"
										 			   onKeyPress="this.onClick"/>
												</td>
											</tr>
										</table>			 			   
									</td>
								</s:if>
								<s:if test="%{incidenciaDto.listaIntervalos.size > 0}">
									<td align="left" nowrap="nowrap">
										<table width="300" border="0">
										  <tr>
											    <th bgcolor="#9e233f" align="center">
											    	<font color="#fff">Num. Serie Inicial</font>
											    </th>
											    <th bgcolor="#9e233f" align="center">
											    	<font color="#fff">Num. Serie Final</font>
											    </th>
										  		<th bgcolor="#9e233f" colspan="2">&nbsp;</th>
										  </tr>					
										  <s:iterator value="incidenciaDto.listaIntervalos" status="key">
										  	  <tr>
												  <td align="center">
													  <s:property value="incidenciaDto.listaIntervalos[#key.index].numSerieIni" /> 
													  <s:hidden name="incidenciaDto.listaIntervalos[%{#key.index}].numSerieIni" />
												  </td>
												  <td align="center">
													  <s:property value="incidenciaDto.listaIntervalos[#key.index].numSerieFin" /> 
													  <s:hidden name="incidenciaDto.listaIntervalos[%{#key.index}].numSerieFin" />
												  </td>
												  <td align="center">
													  <s:if test="%{#accion != 'consultar'}">
														  <s:checkbox name="incidenciaDto.listaIntervalos[%{#key.index}].borrar"
														  			  id="interval_%{#key.index}"   
														  			  value="false"  onchange="javascript:toggleButtonInterval();"/>
													  </s:if>
												  <td>
											  </tr>
										  </s:iterator>								
										</table>		
									<td>
								</s:if>
							</tr>
						</table>
					</td>	
				</tr>
			</table>
		
		</div>	
		
		<s:if test="%{#accion != 'consultar'}">
			<div class="acciones center">
				<s:if test="%{#accion == 'crear'}">
					<input type="button" class="boton" 
					       name="bGuardarIncidencia" 
					       id="idGuardarIncidencia" 
					       value="Crear" 
					       onClick="javascript:crearIncidencia();"
			 			   onKeyPress="this.onClick"/>
		 		</s:if>
		 			   
				<s:if test="%{#accion == 'modificar'}">
					<input type="button" class="boton" 
					       name="bModificarIncidencia" 
					       id="idModificarIncidencia" 
					       value="Modificar" 
					       onClick="javascript:modificarIncidencia();"
			 			   onKeyPress="this.onClick"/>
					<input type="button" class="boton" 
					       name="bConfirmarIncidencia" 
					       id="idConfirmarIncidencia" 
					       value="Confirmar" 
					       onClick="javascript:confirmarIncidencia();"
			 			   onKeyPress="this.onClick"/>
		 		</s:if>
		 		
				<input type="button" class="boton" 
				       name="bLimpiarIncidencia" 
				       id="idLimpiarIncidencia" 
				       value="Limpiar" 
				       onClick="javascript:limpiarIncidencia();"
		 			   onKeyPress="this.onClick"/>
			</div>		    
		</s:if>    
</s:form>
 

