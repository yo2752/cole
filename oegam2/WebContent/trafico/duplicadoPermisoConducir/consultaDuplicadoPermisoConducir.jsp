<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/duplicadoPermisoConducir/duplicadoPermisoConducir.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Tramites de Duplicado Permiso Conducir</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpDPC">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="duplicadoPermConducir.numExpediente" id="idNumExpDPC" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelRefPropiaDPC">Ref.Propia:</label>
					</td>
					<td align="left">
						<s:textfield name="duplicadoPermConducir.refPropia" id="idRefPropiaDPC" size="25" maxlength="50" onblur="this.className='input';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifTitularDPC">Nif Titular:</label>
					</td>
					<td align="left">
						<s:textfield name="duplicadoPermConducir.nifTitular" id="idNifTitularDPC" size="10" maxlength="10" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoTramDPC">Estado Tramite:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosTramiIntergaDuplicados()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
							name="duplicadoPermConducir.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoDPC"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresionDPC">Estado Documentacion:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosTramiteDocumentacion()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
							name="duplicadoPermConducir.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresionDPC"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContratoDPC">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoDPC" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px" name="duplicadoPermConducir.idContrato"
								/>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="duplicadoPermConducir.idContrato"/>
					</s:else>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="jefaturaId">Jefatura provincial:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select id="idJefaturaDPC" name="duplicadoPermConducir.jefatura"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
								headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tieneAlgunPermisoInterga()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="jefaturaId">Jefatura provincial:</label>
						</td>
						<td align="left" nowrap="nowrap">
							<s:select name="duplicadoPermConducir.jefatura"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
								headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
						</td>
					</tr>
				</s:elseif>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaAltaDPC">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaIniDPCDesde">Desde:</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.diaInicio" id="diaFechaAltaIniDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.mesInicio" id="mesFechaAltaIniDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.anioInicio" id="anioFechaAltaIniDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIniDPC, document.formData.mesFechaAltaIniDPC, document.formData.diaFechaAltaIniDPC);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaHAltaDPC">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHastaAltaDPC">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.diaFin" id="diaFechaAltaFinDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.mesFin" id="mesFechaAltaFinDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaAlta.anioFin" id="anioFechaAltaFinDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFinDPC, document.formData.mesFechaAltaFinDPC, document.formData.diaFechaAltaFinDPC);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaDPC">Fecha Presentacion:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaIniDPCDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.diaInicio" id="diaFechaPrtIniDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.mesInicio" id="mesFechaPrtIniDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.anioInicio" id="anioFechaPrtIniDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIniDPC, document.formData.mesFechaPrtIniDPC, document.formData.diaFechaPrtIniDPC);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaHDPC">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHastaDPC">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.diaFin" id="diaFechaPrtFinDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.mesFin" id="mesFechaPrtFinDPC" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="duplicadoPermConducir.fechaPresentacion.anioFin" id="anioFechaPrtFinDPC"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFinDPC, document.formData.mesFechaPrtFinDPC, document.formData.diaFechaPrtFinDPC);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario" />
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscarDPC" id="idBBuscarDPC" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaDPC();" />
			<input type="button" class="boton" name="bLimpiarDPC" id="idBLimpiarDPC" onclick="javascript:limpiarConsultaDPC();" value="Limpiar" />
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaDPC" style="text-align: center;">
				<%@include file="resumenConsultaDPC.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0" style="width:100%;" >
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaConsultaDPC" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaDPC();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaDPC" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaDPC" requestURI= "navegarConsultaDuplPermCond.action"
				id="tablaConsultaDPC" summary="Listado de Tramites de Duplicado Permiso Conducir" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="idPermiso" title="" media="none" paramId="idPermiso"/>

				<display:column property="numExpediente" title="Num Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%;" href="detalleAltaDuplicadoPermisoConducir.action"
					paramId="numExpediente" />

				<display:column property="refPropia" title="Ref.Propia" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column property="codigoTasa" title="Codigo Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column property="doiTitular" title="NIF Titular" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>

				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column property="estadoDoc" title="Estado Doc." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosDPC(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksDPC" id="check<s:property value="#attr.tablaConsultaDPC.idDuplicadoPermCond"/>"
									value='<s:property value="#attr.tablaConsultaDPC.idDuplicadoPermCond"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionDuplPermCond('<s:property value="#attr.tablaConsultaDPC.idDuplicadoPermCond"/>','divEmergenteConsultaDPC');" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaDPC" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bDPCCambiarEstado" id="idDPCCambiarEstado" value="Cambiar Estado" onClick="javascript:cargarVentanaCambioEstado();"onKeyPress="this.onClick"/>
				</s:if>
				<input type="button" class="boton" name="bDPCImprimir" id="idDPCImprimir" value="Imprimir" onClick="javascript:imprimirConsultaDPC();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bDPCValidar" id="idDPCValidar" value="Validar" onClick="javascript:validarConsultaDPC();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bDPCTramitar" id="idDPCTramitar" value="Tramitar" onClick="javascript:tramitarConsultaDPC();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bDPCEliminar" id="idDPCEliminar" value="Eliminar" onClick="javascript:eliminarConsultaDPC();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteConsultaDPC" style="display: none; background: #f4f4f4;"></div>