<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramiteTrafico.js" type="text/javascript"></script>
<script src="js/trafico/placas1.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<s:hidden id="textoLegal" name="propTexto"/>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta de tr&aacute;mites de Tr&aacute;fico</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<s:hidden name="estadoTramiteSeleccionado" id="estadoTramiteSeleccionado"/>
		<s:hidden name="tipoTramiteSeleccionado" id="tipoTramiteSeleccionado"/>
		<s:hidden name="bastidorSeleccionado" id="bastidorSeleccionado"/>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoTramite">Estado del tr&aacute;mite:</label>
					</td>
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey=""
							headerValue="TODOS" name="consultaTramiteBean.estado" listKey="valorEnum" listValue="nombreEnum"
							id="idEstadoTramite"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Tr&aacute;mite:</label>
					</td>
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
							onblur="this.className='input2';" onfocus="this.className='inputfocus';" headerKey=""
							headerValue="Seleccione tipo de trámite" name="consultaTramiteBean.tipoTramite" listKey="valorEnum" listValue="nombreEnum"
							id="idTipoTramite"/> 
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContratoCTram">Contrato:</label>
						</td>

						<td align="left">
							<s:select id="idContratoConsultaTram" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px" name="consultaTramiteBean.idContrato"/></td>
						</tr>
					</s:if>
					<s:else>
						<s:hidden name="consultaTramiteBean.idContrato"/>
					</s:else>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelRefPropia">Ref.Propia:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.refPropia" id="idRefPropia" size="25" maxlength="50" onblur="this.className='input';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">N&uacute;m.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.numExpediente" id="idNumExpediente" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" maxlength="15" cssStyle="width:16em;"
						onkeypress="return validarMatricula(event)"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelDniTitular">NIF Titular: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.nifTitular" id="idDniTitular" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNifFacturacion">NIF Facturaci&oacute;n:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.nifFacturacion" id="idNifFacturacion" onblur="this.className='input2';"
							onkeypress="return validarMatricula(event)" 
							onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumBastidor">Nº Bastidor: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.bastidor" id="idNumBastidor" onblur="this.className='input2';"
							onkeypress="return validarMatricula(event)" 
							onfocus="this.className='inputfocus';" size="22" maxlength="18"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Matr&iacute;cula: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaTramiteBean.matricula" id="idMatricula" onblur="this.className='input2';"
							onkeypress="return validarMatricula(event)"
							onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
							onfocus="this.className='inputfocus';" size="10" maxlength="10"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNive">NIVE: </label>
					</td>
					<td align="left" colspan="0">
						<s:textfield name="consultaTramiteBean.nive" id="idNive" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="30" maxlength="30"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTasa">Tipo Tasa: </label>
					</td>
					<td align="left" colspan="0">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTasa()"
							headerKey="" headerValue="" name="consultaTramiteBean.tipoTasa" listKey="valorEnum" listValue="nombreEnum"
							id="idTipoTasa" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left">
						<label for="labelConNive">Con Nive: </label>
					</td>
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosCombosConsTram()"
							name="consultaTramiteBean.conNive" id="idConNive" headerKey="" headerValue="Todos" listKey="valorEnum"
							listValue="valorEnum" />
					</td>
					<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
						<td align="left">
							<label for="labelPresentadoJPT">Presentado JPT: </label>
						</td>
						<td align="left">
							<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosCombosConsTram()"
								name="consultaTramiteBean.presentadoJPT" id="idPresentadoJPT" headerKey="" headerValue="Todos" listKey="valorEnum"
								listValue="valorEnum" />
						</td>
					</s:if>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaAltaD">Fecha Alta:</label>
					</td>
					<td align="center">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaIni">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.anioInicio" id="anioFechaAltaIni"
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
						<label for="labelFechaAltaH">Fecha de Alta:</label>
					</td>
					<td align="center">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">Hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaAlta.anioFin" id="anioFechaAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;"
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
						<label for="labelFechaPresentacionD">Fecha Presentaci&oacute;n:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPresDesde">Desde:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.diaInicio" id="diaFechaPresIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.mesInicio" id="mesFechaPresIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.anioInicio" id="anioFechaPresIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPresIni, document.formData.mesFechaPresIni, document.formData.diaFechaPresIni);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaPresentacionH">Fecha de Presentaci&oacute;n:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPresHasta">Hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.diaFin" id="diaFechaPresFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.mesFin" id="mesFechaPresFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaTramiteBean.fechaPresentacion.anioFin" id="anioFechaPresFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPresFin, document.formData.mesFechaPresFin, document.formData.diaFechaPresFin);return false;"
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
			<input type="button" class="boton" name="bBuscar" id="idBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaTramiteTrafico();"/>
			<input type="button" class="boton" name="bLimpiar" id="idLimpiar" onclick="javascript:limpiarConsultaIntern();" value="Limpiar"/>
		</div>
		<s:hidden key="contrato.idContrato"/>
		<s:if test="%{resumenValidacion}">
			<br>
				<div id="estadisticasValidacion" style="text-align: center;">
					<%@include file="../../includes/resumenValidacion.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenTramitacion}">
			<br>
				<div id="estadisticasTramitacion" style="text-align: center;">
					<%@include file="../../includes/resumenTramitacionTelematica.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenPendienteEnvioExcel}">
			<br>
				<div id="estadisticasPendientesExcel" style="text-align: center;">
					<%@include file="../../includes/resumenPendienteEnvioExcel.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenCertificadoTasasFlag}">
			<br>
				<div id="estadisticasCertificadoTasas" style="text-align: center;">
					<s:set var="funcionDescargarCertificado" value ="'descargarCertificado()'" />
					<s:hidden name="ficheroDescarga" id ="ficheroDescarga"/>
					<%@include file="../../includes/resumenCertificadoTasas.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenAutoBTV}">
			<br>
				<div id="resumenAutoInformeBtv" style="text-align: center;">
					<%@include file="../../includes/resumenOperacionesTramite.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenEEFFFlag}">
			<br>
				<div id="estadisticasEEFF" style="text-align: center;">
					<%@include file="../../includes/resumenEEFF.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenCambiosEstadoFlag}">
			<br>
				<div id="estadisticasCambiosEstado" style="text-align: center;">
					<%@include file="../../includes/resumenCambiosEstado.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenAsignacionMasivaTasasFlag}">
			<br>
				<div id="estadisticasCambiosEstado" style="text-align: center;">
					<%@include file="../../includes/resumenAsignacionMasiva.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenPresentacion576Flag}">
			<br>
				<div id="estadisticasPresentacion576" style="text-align: center;">
					<%@include file="../../includes/resumenPresentacion576.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenLiberacionEEFFFlag}">
			<br>
				<div id="estadisticasLiberacionEEFF" style="text-align: center;">
					<%@include file="../../includes/resumenLiberacionEEFF.jsp" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenFicheroAEAT}">
			<br>
				<div id="resErroresFicheroAEAT" style="text-align: center;">
					<%@include file="../../includes/resumenErroresFicheroAEAT.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenFicheroMOVE}">
			<br>
				<div id="resErroresFicheroMOVE" style="text-align: center;">
					<%@include file="../../includes/resumenErroresFicheroMOVE.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenConsultaTarjetaEitvFlag}">
			<br>
				<div id="estadisticasConsultaTarjetaEitv" style="text-align: center;">
					<%@include file="../../includes/resumenConsultaTarjetaEitv.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenDocBase != null}">
			<br>
				<div id="resDocBase" style="text-align: center;">
					<%@include file="../trafico/resumenDocBase.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenFicheroSolicitud05Flag}">
			<br>
				<div id="resErroresFicheroSolicitud05" style="text-align: center;">
						<%@include file="../../includes/resumenErroresFicheroSolicitud05.jspf" %>
				</div>
			<br><br>
		</s:if>
			<s:if test="%{resumenSolicitudNRE06Flag}">
			<br>
				<div id="resErroresSolicitudNRE06" style="text-align: center;">
					<%@include file="../../includes/resumenErroresSolicitudNRE06.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenConsultaTramTraf != null}">
			<br>
			<div id="resumenConsultaTramiteTrafico" style="text-align: center;">
				<%@include file="resumenConsultaTramiteTrafico.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resum != null}">
			<br>
				<div id="resPermisoDistintivo" style="text-align: center;">
					<%@include file="../permisoDistintivoItv/resumenGeneracionDistintivo.jspf" %>
				</div>
			<br><br>
		</s:if>
		<s:if test="%{impresoEspera}">
			<script type="text/javascript">muestraDocumento();</script>
		</s:if>
		<s:if test="%{imprimirFichero}">
			<script type="text/javascript">muestraDocumentoFicheroAEAT();</script>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0" style="width:100%;">
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>

		<s:if test="%{!resumenValidacion && !resumenTramitacion && !resumenPendienteEnvioExcel && !resumenCertificadoTasasFlag && !resumenAutoBTV && !resumenEEFFFlag && !resumenCambiosEstadoFlag && !resumenAsignacionMasivaTasasFlag && !resumenPresentacion576Flag && !resumenLiberacionEEFFFlag && !resumenFicheroAEAT && !resumenConsultaTarjetaEitvFlag && !resumenDocBase && !resumenFicheroSolicitud05Flag}">
			<%@include file="../../includes/erroresMasMensajes.jspf" %>
		</s:if>

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
										id="idResultadosPorPaginaConsultaTramiteTraf" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaTramiteTraf();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaTramTraf" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaTramiteTraf" requestURI= "navegarConsTramTraf.action"
					id="tablaConsultaTramiteTraf" summary="Listado de Tramites de Tráfico" excludedParams="*" sort="list"
					cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="numExpediente" title="Núm. Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;" href="detalleConsTramTraf.action" paramId="numExpediente" />

				<display:column property="refPropia" title="Referencia Propia" sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="15" style="width:4%" />

				<display:column property="bastidor" sortProperty="vehiculo.bastidor" title="Bastidor" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="matricula" sortProperty="vehiculo.matricula" title="Matrícula" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<display:column property="tipoTasa" sortProperty="tasa.tipoTasa" title="Tipo Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="codigoTasa" sortProperty="tasa.codigoTasa" title="Código Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column title="Tipo Trámite" property="tipoTramite" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" paramId="idTipoTramite" />

				<display:column title="Estado" property="estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />

				<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
					<display:column title="Pres JPT" property="presentadoJPT" style="width:2%" />
				</s:if>
				<display:column style="width:1%">
					<table align="center">
					<s:if test="%{#attr.tablaConsultaTramiteTraf.estado != null && #attr.tablaConsultaTramiteTraf.estado.equals(@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@Finalizado_Con_Error.getNombreEnum())}">
						<td style="border: 0px;">
						<div>
							<img src="img/botonDameInfo.gif" style="margin-right:10px;height:15px;width:15px;cursor:pointer";
								title ='<s:property value="#attr.tablaConsultaTramiteTraf.respuesta"/>'/>
						</div>
							</td>
						</s:if>
					</table>
				</display:column>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaTramite(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="hidden" name="listaEstadoConsultaTramite" id="estado_<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>"
									value='<s:property value="@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirAValor(#attr.tablaConsultaTramiteTraf.estado)"/>' />

								<input type="hidden" name="listaTipoConsultaTramite" id="tipo_<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>"
									value='<s:property value="@trafico.utiles.enumerados.TipoTramiteTrafico@convertirNombreAValor(#attr.tablaConsultaTramiteTraf.tipoTramite)"/>' />

								<input type="hidden" name="listaMatriculasConsultaTramite" id="matricula_<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaTramiteTraf.matricula"/>' />

								<input type="hidden" name="listaMBastidorConsultaTramite" id="bastidor_<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaTramiteTraf.bastidor"/>' />
							</td>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksConsultaTramite" id="check<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionConsTram('<s:property value="#attr.tablaConsultaTramiteTraf.numExpediente"/>','divEmergenteConsultaTramiteTrafico');" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<s:if test="{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().sinBotonesConsultaTramiteTrafico()}">
				<div id="bloqueLoadingConsultaTramTraf" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
				<div class="acciones">
					<table align="center">
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bValidar" id="idValidar" value="Validar" onClick="javascript:validarTramite();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bComprobar" id="idComprobar" value="Comprobar" onClick="javascript:comprobarBloque();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bDuplicar" id="idDuplicar" value="Duplicar" onClick="javascript:duplicarTramite();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bEliminar" id="idEliminar" value="Eliminar" onClick="javascript:eliminarTramite();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bTramitar" id="idTramitar" value="Tramitar Telemáticamente" onClick="javascript:tramitarTelematicoTramite();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarJustificante" id="idGenerarJustificante" value="Generar Justificante" onClick="javascript:generarJustificantesProfesionales();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bImprimirJustificante" id="idImprimirJustificante" value="Imprimir Justificante" onClick="javascript:imprimirJustificantesProfesionales();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bImpresionDocumentos" id="idImpresionDocumentos" value="Imprimir Documentos" onClick="javascript:impresionVariosTramites();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarCertificado" id="idGenerarCertificado" value="Generar Certificado Tasas" onClick="javascript:generarCertificados();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bPdteExcel" id="idPdteExcel" value="Envío a Excel" onClick="javascript:pendientesEnvioExcel();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarXML" id="idGenerarXML" value="Generar XML 620" onClick="javascript:generarXML620Tramites();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bSolicitarPlacas" id="idSolicitarPlacas" value="Solicitar Placas" onClick="javascript:solicitarPlacasMatricula();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bGenDocBase" id="idGenDocBase" value="Generar Documento Base" onClick="javascript:generarDocBase();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bAsignarTasasMasiva" id="idAsignarTasasMasiva" value="A. Masiva Tasas" onClick="javascript:asignacionMasivaTasas();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bObtenerXML" id="idObtenerXML" value="Obtener XML de DGT" onClick="javascript:obtenerXMLenviadoaDGT();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarJP" id="idGenerarJP" value="Forzar Generar JP" onClick="javascript:generarJP();"onKeyPress="this.onClick"/>
							</td>
							<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
							<td>
								<input type="button" class="botonMasGrande" name="bCambiarEstado" id="idCambiarEstado" value="Cambiar Estado" onClick="javascript:cambiarEstados();"onKeyPress="this.onClick"/>
							</td>
							</s:if>
							<td>
								<input type="button" class="botonMasGrande" name="b576" id="id576" value="Presentación 576" onClick="javascript:presentacion576();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bConsultaTarjetaEitv" id="idConsultaTarjetaEitv" value="Consulta tarjeta eITV" onClick="javascript:consultaTarjetaEitv();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bLiberarEEFFMasivo" id="idLiberarEEFFMasivo" value="Liberar EEFF" onClick="javascript:liberarEEFFMasivo();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bConsultaEEFF" id="idConsultaEEFF" value="Consulta Liberacion" onClick="javascript:consultarEEFF();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bFicheroAEAT" id="idFicheroAEAT" value="Generar Fichero AEAT" onClick="javascript:generarFicheroAEATTramites();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarNRE06" id="idGenerarNRE06" value="Generar Solicitud NRE-06" onClick="javascript:generarSolicitudNRE06();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bClonarTramite" id="idClonarTramite" value="Clonar Trámites" onClick="javascript:clonarTramite();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarSolicitud05" id="idGenerarSolicitud05" value="Generar Fichero Solicitud 05" onClick="javascript:generarFicheroSolicitud05();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bLiquidacionNRE06" id="idLiquidacionNRE06" value="Exportación Datos Modelo06" onClick="javascript:generarliquidacionNRE06();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bLiberarDocBaseNive" id="idLiberarDocBaseNive" value="Liberar Doc Base Nive" onClick="javascript:liberarDocBaseNive();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bCambiaEstadoIni" id="idCambiaEstadoIni" value="Cambiar Estado a Iniciado" onClick="javascript:cambiaEstadoIni();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bCambiaMatricula" id="idCambiaMatricula" value="Introducir Matricula" onClick="javascript:generarPopPupMatriculaConsulta();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bGenerarDistintivo" id="idGenerarDistintivo" value="Generar Distintivo" onClick="javascript:generarDistintivo();"onKeyPress="this.onClick"/>
							</td>
						</tr>
						<tr>
							<td>
								<input type="button" class="botonMasGrande" name="bDescargarP576" id="idDescargarP576" value="Descargar 576" onClick="javascript:descargarPresentacion576();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bOkImprInformaBaja" id="idOkImprInformaBaja" value="Aut. Baja" onClick="javascript:autorizarImpresionBaja();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<input type="button" class="botonMasGrande" name="bTramiteRevisado" id="idTramiteRevisado" value="Trámite Revisado" onClick="javascript:tramiteRevisado();"onKeyPress="this.onClick"/>
							</td>
							<td>
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
									<input type="button" class="botonMasGrande" name="bImprimirDocumentos" id="idImprimirDocumentos" value="Impresión Antigua" onClick="javascript:imprimirVariosTramites();"onKeyPress="this.onClick"/>
									</td>
									<td>
										<input type="button" class="botonMasGrande" name="btramitarNre06" id="btramitarNre06" value="Tramitar NRE06" onClick="javascript:tramitarNRE06();"onKeyPress="this.onClick"/>
									</td>
								</s:if>
							</td>
						</tr>
						<tr>
							<td>
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMOVE()}">
									<input type="button" class="botonMasGrande" name="bFicheroMOVE" id="idFicheroMOVE" value="Generar Fichero MOVE" onClick="javascript:generarFicheroMOVE();"onKeyPress="this.onClick"/>
								</s:if>
							</td>
						</tr>
					</table>
				</div>
			</s:if>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteConsultaTramiteTrafico" style="display: none; background: #f4f4f4;"></div>