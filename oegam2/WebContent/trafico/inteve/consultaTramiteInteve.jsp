<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/inteve/tramiteInteve.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Tr&aacute;mites Inteve</span>
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
						<label for="labelNumExpedienteTRI">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteInteve.numExpediente" id="idNumExpedienteTRI" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatriculaTRI">Matr&iacute;cula:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteInteve.matricula" id="idMatriculaTRI" size="10" maxlength="10" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidorTRI">Bastidor:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteInteve.bastidor" id="idBastidorTRI" size="23" maxlength="21" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNiveTRI">NIVE:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteInteve.nive" id="idNiveTRI" size="34" maxlength="32" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" />
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTasaTRI">Tasa:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteInteve.codigoTasa" id="idTasaTRI" size="23" maxlength="21" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoInformeTRI">Tipo de Informe:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getListaTiposInformes()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey=""
							headerValue="Seleccione Tipo" name="consultaTramiteInteve.tipoInforme" listKey="valorEnum"
							listValue="nombreEnum" id="idTipoInformeTRI"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoTRI">Estado:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getEstadosTramiteInteve()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado"
							name="consultaTramiteInteve.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoTRI"/>
					</td>	
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContratoTRI">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoTRI" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTramiteInteve@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px" name="consultaTramiteInteve.idContrato"
								/>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="consultaTramiteInteve.idContrato"/>
					</s:else>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaDB">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaIniTRIAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.diaInicio" id="diaFechaAltIniTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.mesInicio" id="mesFechaAltIniTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.anioInicio" id="anioFechaAltIniTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltIniTRI, document.formData.mesFechaAltIniTRI, document.formData.diaFechaAltIniTRI);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtTRIHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.diaFin" id="diaFechaAltFinTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.mesFin" id="mesFechaAltFinTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaAlta.anioFin" id="anioFechaAltFinTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltFinTRI, document.formData.mesFechaAltFinTRI, document.formData.diaFechaAltFinTRI);return false;" 
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
						<label for="labelFechaDB">Fecha Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaIniTRIPrtDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.diaInicio" id="diaFechaPrtIniTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.mesInicio" id="mesFechaPrtIniTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.anioInicio" id="anioFechaPrtIniTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIniTRI, document.formData.mesFechaPrtIniTRI, document.formData.diaFechaPrtIniTRI);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtTRIHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.diaFin" id="diaFechaPrtFinTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.mesFin" id="mesFechaPrtFinTRI" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteInteve.fechaPresentacion.anioFin" id="anioFechaPrtFinTRI"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFinTRI, document.formData.mesFechaPrtFinTRI, document.formData.diaFechaPrtFinTRI);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscarCTRI" id="idBBuscarCTRI" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaTRI();"/>
			<input type="button" class="boton" name="bLimpiarCTRI" id="idBLimpiarCTRI" onclick="javascript:limpiarConsultaTramiteTRI();" value="Limpiar"/>
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaTRI" style="text-align: center;">
				<%@include file="resumenConsultaTramiteInteve.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
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
										id="idResultadosPorPaginaConsultaTrI" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaTrI();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaTrI" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaTrI" requestURI= "navegarConsultaTrI.action"
				id="tablaConsultaTrI" summary="Listado de Tramites de Inteve" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column title="Num.Expediente" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
					<s:if test="#attr.tablaConsultaTrI.estado == 'Anulado'">
						<s:property value="#attr.tablaConsultaTrI.numExpediente"/>
					</s:if>
					<s:else>
						<a href="detalleAltaTramIntv.action?numExpediente=${attr.tablaConsultaTrI.numExpediente}">
							<s:property value="#attr.tablaConsultaTrI.numExpediente"/>
						</a>
					</s:else>
				</display:column>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>

				<display:column property="matriculas" title="Matricula" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="bastidores" title="Bastidor" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="nives" title="Nive" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="fechaPresentacion" title="Fecha Presentación" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosCTRI(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksTRI" id="check<s:property value="#attr.tablaConsultaTrI.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaTrI.numExpediente"/>' />
							</td>
							<td align="right">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
								onclick="consultaEvolucionTramiteInteve(<s:property value="#attr.tablaConsultaTrI.numExpediente"/>);" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingGestionDB" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().usuariosTrafico()}">
					<input type="button" class="boton" name="bCambiarEstadoTRI" id="idCambiarEstadoTRI" value="Cambiar Estado" onClick="javascript:abrirVentanaSeleccionEstados();"onKeyPress="this.onClick"/>
				</s:if>
				<input type="button" class="boton" name="bValidarTRI" id="idBValidarTRI" value="Validar" onClick="javascript:validarConsultaTRI();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bSolictarTRI" id="idBSolicitarTRI" value="Solicitar Inteve" onClick="javascript:solicitarConsultaTRI();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bDescargarRI" id="idBDescargarTRI" value="Descargar" onClick="javascript:descargarConsultaTRI();"onKeyPress="this.onClick"/>
				<!-- <input type="button" class="boton" name="bReiniciarEstadosTRI" id="idBReiniciarEstadosTRI" value="Reiniciar Estados" onClick="javascript:reiniciarEstadosConsultaTRI();"onKeyPress="this.onClick"/> -->
				<input type="button" class="boton" name="bEliminarTRI" id="idBEliminarTRI" value="Eliminar" onClick="javascript:eliminarConsultaTRI();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<div id="bloqueLoadingConsultarTRI" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
	<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	</s:form>
</div>
<div id="divEmergenteConsultaTRI" style="display: none; background: #f4f4f4;"></div>