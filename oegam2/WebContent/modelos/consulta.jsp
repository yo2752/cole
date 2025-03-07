<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>
<script src="js/modelos/modelosFunction.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Modelos</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174"
	height="189"
	name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js"
	src="calendario/ipopeng.htm"
	scrolling="no"
	frameborder="0"
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<s:form method="post" id="formData" name="formData" enctype="multipart/form-data">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado Modelos:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getEstadoModelos()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
							name="modeloFilter.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoModelos"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Modelo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getModelos()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Modelo" 
							name="modeloFilter.modelo" listKey="valorEnum" listValue="valorEnum" id="idModeloModelos" onchange="javascript:recargarConceptoPorModelo('idConceptoModelos','idModeloModelos');"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Codigo Notario:</label>
					</td>
					<td  align="left">
						<s:textfield name="modeloFilter.codigoNotario" id="idCodNotarioModelos" size="10" maxlength="10" onblur="this.className='input';"
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExp">Nº Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="modeloFilter.numExpediente" id="idNumExpedienteModelos" size="15" maxlength="15" onblur="this.className='input';" 
									onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpRemesa">Nº Expediente Remesa:</label>
					</td>
					<td  align="left">
						<s:textfield name="modeloFilter.numExpRemesa" id="idNumExpRemesa" size="15" maxlength="15" onblur="this.className='input';" 
									onfocus="this.className='inputfocus';"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelNombre">Num. Colegiado:</label>
						</td>
						<td  align="left">
							<s:textfield name="modeloFilter.numColegiado" id="idNumColegiadoModelos" size="4" maxlength="4" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</s:if>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Ref. Propia:</label>
					</td>
					<td  align="left">
						<s:textfield name="modeloFilter.referenciaPropia" id="idReferenciaPropia" size="20" maxlength="20" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Concepto:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesModelos@getInstance().getListaConceptosPorModeloYVersion(modeloFilter.modelo,modeloFilter.version)" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Concepto"
							name="modeloFilter.concepto" listKey="concepto" listValue="descConcepto" id="idConceptoModelos"/>
					</td>
				<tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.anioInicio" id="anioFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIni, document.formData.mesFechaAltaIni, document.formData.diaFechaAltaIni);return false;" 
	    								title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaAlta.anioFin" id="anioFechaAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPreDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.diaInicio" id="diaFechaPreIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.mesInicio" id="mesFechaPreIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.anioInicio" id="anioFechaPreIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPreIni, document.formData.mesFechaPreIni, document.formData.diaFechaPreIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Presentación</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.diaFin" id="diaFechaPreFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.mesFin" id="mesFechaPreFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="modeloFilter.fechaPresentacion.anioFin" id="anioFechaPreFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPreFin, document.formData.mesFechaPreFin, document.formData.diaFechaPreFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarModelos();"/>
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaModelos();" value="Limpiar"/>
		</div>
		<br/>
		<s:if test="%{resumenValidacion}">
			<br>
			<div id="resumenValidacionModelos" style="text-align: center;">
				<%@include file="resumenValidacionModelos.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenCambEstado}">
			<br>
			<div id="resumenCambEstadoModelos" style="text-align: center;">
				<%@include file="resumenCambEstadoModelos.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenAutoLiq}">
			<br>
			<div id="resumenAutoLiqModelos" style="text-align: center;">
				<%@include file="resumenAutoLiqModelos.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenPresentacion}">
			<br>
			<div id="resumenPresentacionModelos" style="text-align: center;">
				<%@include file="resumenPresentacionModelos.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenEliminacion}">
			<br>
			<div id="resumenEliminacionModelos" style="text-align: center;">
				<%@include file="resumenEliminacionModelos.jspf" %>
			</div>
			<br><br>
		</s:if>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
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
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPagina"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaModelos();">
									</s:select>
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
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaModelos" requestURI= "navegarConsultaMd.action"
					id="tablaConsultaModelos" summary="Listado de Modelos 600/601"
					excludedParams="*" sort="list"
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">

				<display:column property="numExpediente" title="Nº Expediente" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%"  href="detalleConsultaMd.action" paramId="numExpediente"/>

				<display:column property="codNotario" title="Notario" sortProperty="notario.codigoNotario" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:3%"/>

				<display:column property="referenciaPropia" title="Ref. Propia" sortProperty="referenciaPropia" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:3%"/>

				<display:column property="modelo" title="Modelo" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%"/>

				<display:column property="concepto" title="Concepto" sortProperty="idConcepto" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="valorDeclarado" title="Valor Declarado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="descEstado" title="Estado" sortable="false" headerClass="sortable"
					defaultorder="descending" style="width:4%"/>

				<display:column property="numExpedienteRemesa" title="Remesa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" sortProperty="remesa.numExpediente"/>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosModelos(this)'
					onKeyPress='this.onClick'/>" style="width:4%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionModelos600601(<s:property value="#attr.tablaConsultaModelos.numExpediente"/>);" title="Ver evolución"/>
							</td>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaModelos.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaModelos.numExpediente"/>' />
								<input type="hidden" id="idNumExpRemesa<s:property value="#attr.tablaConsultaModelos.numExpediente"/>" value="<s:property value="#attr.tablaConsultaModelos.numExpedienteRemesa"/>">
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<oegam:botonesPaginaConsultaModelos/>
			<div id="bloqueLoadingModelo" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaModelo" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaModeloEvolucion" style="display: none; background: #f4f4f4;"></div>
</div>