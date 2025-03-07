<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/conductor/conductor.js" type="text/javascript"></script>
<script src="js/arrendatarios/arrendatarios.js" type="text/javascript"></script>


<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta de
						Conductores</span></td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>
<iframe width="174" height="189" name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>


<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="4" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap"><label for="nif">NIF:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.nif" id="nif"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onfocus="this.className='inputfocus';" size="10" maxlength="9" />
				</td>
				<s:if
					test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td align="left" nowrap="nowrap"><label for="labelContrato">Contrato:</label>
					</td>
					<td align="left"><s:select
							list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';"
							headerValue="Seleccione Contrato"
							onfocus="this.className='inputfocus';" listKey="codigo"
							headerKey="" listValue="descripcion" cssStyle="width:220px"
							name="consultaConductorFilter.idContrato" id="idContrato" /></td>
				</s:if>
				<td align="left" nowrap="nowrap"><label
					for="labelNumExpediente">N&uacute;m. Expediente:</label></td>
				<td align="left"><s:textfield
						name="consultaConductorFilter.numExpediente" id="idNumExpediente"
						align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="15"
						onkeypress="return validarNumerosEnteros(event)" /></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="nif">DOI
						Veh&iacute;culo:</label></td>

				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.doiVehiculo" id="doivehiculo"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onfocus="this.className='inputfocus';" size="10" maxlength="9" />
				</td>

				<td align="left" nowrap="nowrap"><label for="labelEstado">Estado:</label>
				</td>
				<td align="left"><s:select
						list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getEstados()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="" headerValue=""
						name="consultaConductorFilter.estado" listKey="valorEnum"
						listValue="nombreEnum" id="estado" /></td>

				<td align="left" nowrap="nowrap"><label
					for="labelTipoOperacion">Tipo operaci&oacute;n:</label></td>
				<td align="left"><s:select
						list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getTiposOperacion()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="" headerValue=""
						name="consultaConductorFilter.operacion" listKey="valorEnum"
						listValue="nombreEnum" id="operacion" /></td>

			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="matricula">Matr&iacute;cula:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.matricula" id="matricula"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onChange="return validarMatricula_alPegar(event)"
						onmousemove="return validarMatricula_alPegar(event)"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" />
				</td>

				<td align="left" nowrap="nowrap"><label for="bastidor">N&uacute;m. Registro:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.numRegistro" id="numRegistro"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onfocus="this.className='inputfocus';" size="18" maxlength="17" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;" ><label for="referenciaPropia">Referencia Propia:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaConductorFilter.refPropia" id="referenciaPropia" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="4" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left"><label>Fecha de Inicio:</label></td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.diaInicio"
									id="diaInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.mesInicio"
									id="mesInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.anioInicio"
									id="anioInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.diaFin"
									id="diaFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.mesFin"
									id="mesFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.anioFin"
									id="anioFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio, document.formData.mesFinInicio, document.formData.diaFinInicio);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left"><label>Fecha de Fin:</label></td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.diaInicio"
									id="diaInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.mesInicio"
									id="mesInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.anioInicio"
									id="anioInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio2, document.formData.mesInicio2, document.formData.diaInicio2);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.diaFin"
									id="diaFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.mesFin"
									id="mesFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.anioFin"
									id="anioFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio2, document.formData.mesFinInicio2, document.formData.diaFinInicio2);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left"><label>Fecha de Alta:</label></td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.diaInicio"
									id="diaInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.mesInicio"
									id="mesInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.anioInicio"
									id="anioInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio3, document.formData.mesInicio3, document.formData.diaInicio3);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.diaFin"
									id="diaFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.mesFin"
									id="mesFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.anioFin"
									id="anioFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio3, document.formData.mesFinInicio3, document.formData.diaFinInicio3);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="left"><label>Fecha de Presentación:</label></td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.diaInicio"
									id="diaInicio4" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.mesInicio"
									id="mesInicio4" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.anioInicio"
									id="anioInicio4" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio4, document.formData.mesInicio4, document.formData.diaInicio4);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
								</a>
							</td>
						</tr>
					</table>
				</td>
				<td colspan="2">
					<table width="20%">
						<tr>
							<td align="left">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.diaFin"
									id="diaFinInicio4" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.mesFin"
									id="mesFinInicio4" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaPresentacion.anioFin"
									id="anioFinInicio4" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio4, document.formData.mesFinInicio4, document.formData.diaFinInicio4);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Buscar" onkeypress="this.onClick"
					onclick="return buscarConsultaConductor();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					onclick="limpiarConsultaConductor_Pantalla()" value="Limpiar" /></td>
			</tr>
		</table>
		
		
		<s:if test="%{resumen != null}">
		
			<br>
			<div id="resumenConsultaArrendatario" style="text-align: center;">
				<%@include file="resumenConductor.jspf" %>
			</div>
			<br><br>
 		</s:if> 
		
		
		
		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>
		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la
						b&uacute;squeda</td>
				</tr>
			</table>
		</div>
		<s:if
			test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaConductorHabitual.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		
		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" excludedParams="*"
				requestURI="navegarConsultaConductorHabitual.action"
				class="tablacoin" uid="tablaConductores" decorator="${decorator}"
				summary="Listado de Conductores" cellspacing="0" cellpadding="0"
				sort="external">
				
			
				<display:column title="Expediente" sortProperty="numExpediente"
						sortable="true" headerClass="sortable" defaultorder="descending">
						<s:if test="#attr.tablaConductores.estadotxt != 'Anulado'">
						
								<a href="altaAltaConductorHabitual.action?numExpediente=${tablaConductores.numExpediente}">
								<s:property value="#attr.tablaConductores.numExpediente"/>
							</a>
						</s:if>
    					<s:else>
								<s:property value="#attr.tablaConductores.numExpediente"/>
						</s:else>
				</display:column>
				
				<display:column property="refPropia" title="Ref. Propia" sortable="true" 
					headerClass="sortable" defaultorder="descending" />
				<display:column property="nif" title="NIF" sortable="true"
					headerClass="sortable" defaultorder="descending" />
				<display:column property="doiVehiculo" title="DOI" sortable="true"
					headerClass="sortable" defaultorder="descending" />
				<display:column property="matricula" title="Matri."
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:2%" />
				
				<display:column property="numRegistro" title="N.Registro" sortable="true"
					headerClass="sortable" defaultorder="descending" sortProperty="numRegistro"  />
				
				<display:column property="consentimientotxt" sortProperty="consentimiento" title="Con."
					sortable="true" headerClass="sortable" defaultorder="descending"/>

				<display:column property="estadotxt" title="Estado" sortProperty="estado" sortable="true"
					headerClass="sortable" defaultorder="descending" />

				 <display:column property="operaciontxt" title="Oper." sortable="true"
					headerClass="sortable" defaultorder="descending" sortProperty="operacion" />	
				<display:column
					title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>"
					style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionCayc(<s:property value="#attr.tablaConductores.numExpediente"/>,'divEmergenteEvolucionAltaCayc');" title="Ver evolución"/>
				  			</td> 
							<td style="border: 0px;"><input type="checkbox"
								name="listaChecks"
								id="check<s:property value="#attr.tablaConductores.numExpediente"/>"
								value='<s:property value="#attr.tablaConductores.numExpediente"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">		
		             
			<div id="bloqueLoadingConsultaConductor"
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<div class="acciones center">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bCambiarEstado"
						id="idCambiarEstado" value="Cambiar Estado"
						onClick="javascript:cambiarEstadoBloqueConductor();"
						onKeyPress="this.onClick" />
				</s:if>
				<input type="button" class="boton" name="bConsultar"
					id="idValidar" value="Validar"
					onClick="javascript:validarBloqueConductor();" onKeyPress="this.onClick" />
				<input type="button" class="boton" name="bConsultar"
					id="idConsultar" value="Consultar"
					onClick="javascript:consultarBloqueConductor();" onKeyPress="this.onClick" />						
				<input type="button" class="boton" name="bEliminar" id="idEliminar"
					value="Eliminar" onClick="javascript:eliminarConductor();"
					onKeyPress="this.onClick" />
					
				<input type="button" class="boton" name="bModificarFechaConductor" id="idModificarFechaConductor"
					value="Modificar Fechas" onClick="javascript:modificarFechaConductor();"
					onKeyPress="this.onClick" />
			</div>
			<div id="divEmergenteConsultaConductor" style="display: none; background: #f4f4f4;"></div>
			<div id="divEmergenteEvolucionAltaCayc" style="display: none; background: #f4f4f4;"></div>
			<div id="divEmergenteModificarFechaConductor" style="display: none; background: #f4f4f4;"></div>
		</s:if>
	</div>
</s:form>