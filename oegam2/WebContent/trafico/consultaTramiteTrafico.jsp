<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>

<script
	src="https://cdn.jsdelivr.net/jquery.validation/1.15.1/jquery.validate.min.js"></script>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>

<script src="js/trafico/placas.js" type="text/javascript"></script>

<script src="js/trafico/justificantes.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<!-- //TODO MPC. Cambio IVTM. -->
<script src="js/ivtm.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<s:hidden id="textoLegal" name="propTexto" />
<!--<div id="contenido" class="contentTabs" style="display: block;">-->

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta de tr&aacute;mites de tr&aacute;fico</span>
			</td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData">
	<s:hidden name="valorEstadoCambio" id="valorEstadoCambio" />
	<s:hidden name="estadoTramiteSeleccionado"
		id="estadoTramiteSeleccionado" />
	<s:hidden name="tipoTramiteSeleccionado" id="tipoTramiteSeleccionado" />
	<s:hidden name="bastidorSeleccionado" id="bastidorSeleccionado" />
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap"><label for="idEstadoTramite">Estado
						del trámite: </label></td>
				<td align="left"><s:select
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="-1"
						headerValue="TODOS" name="beanCriterios.estado"
						listKey="valorEnum" listValue="nombreEnum" id="idEstadoTramite" />
				</td>
				<td align="left" nowrap="nowrap"><label for="idTipoTramite">Tipo Trámite:</label></td>
				<td align="left"><s:select
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="-1"
						headerValue="Seleccione tipo de trámite"
						name="beanCriterios.tipoTramite" listKey="valorEnum"
						listValue="nombreEnum" id="idTipoTramite" /></td>
				<td align="left" nowrap="nowrap"><s:if
						test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Núm. Colegiado: </label>
					</s:if></td>
				<td align="left"><s:if
						test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<s:textfield name="beanCriterios.numColegiado" id="numColegiado"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onkeypress="return validarMatricula(event)" size="5"
							maxlength="4" />
					</s:if></td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="refPropia">Referencia
						Propia: </label></td>
				<td align="left"><s:textfield
						name="beanCriterios.referenciaPropia" id="refPropia"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="50" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="numExpediente">Nº Expediente:</label>
				</td>
				<td align="left" colspan="3"><s:textfield
						name="beanCriterios.numExpediente" id="numExpediente"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" maxlength="15"
						cssStyle="width:16em;" onkeypress="return validarMatricula(event)" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="dniTitular">NIF
						Titular: </label></td>
				<td align="left"><s:textfield name="beanCriterios.titular.nif"
						id="dniTitular" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="9" maxlength="9" /></td>
				<td align="left" nowrap="nowrap"><label for="dniTitular">NIF
						Facturación: </label></td>
				<td align="left"><s:textfield
						name="beanCriterios.nifFacturacion" id="dniFacturacion"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)"
						onfocus="this.className='inputfocus';" size="9" maxlength="9" /></td>
				<s:if
					test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
					<td align="left"><label for="presentadoJPT">Presentado
							JPT: </label></td>
					<td align="left"><s:select
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoPresentacionJPT()"
							name="beanCriterios.presentadoJPT" id="presentadoJPT"
							headerKey="" headerValue="Todos" listKey="valorEnum"
							listValue="nombreEnum" /></td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="numBastidor">Nº Bastidor:</label>
				</td>
				<td align="left"><s:textfield name="beanCriterios.numBastidor"
						id="numBastidor" onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)"
						onfocus="this.className='inputfocus';" size="22" maxlength="18" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Matrícula:</label>
				</td>
				<td align="left"><s:textfield name="beanCriterios.matricula"
						id="matricula" onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)"
						onChange="return validarMatricula_alPegar(event)"
						onmousemove="return validarMatricula_alPegar(event)"
						onfocus="this.className='inputfocus';" size="10" maxlength="10" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="nive">NIVE:
				</label></td>
				<td align="left" colspan="0"><s:textfield
						name="beanCriterios.nive" id="nive"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="30" />
				</td>
				<!--Mantis 0003842: Solicitud de Mejora CTIT (FULL/TRADE/NOT) -->
				<td align="left" nowrap="nowrap">
					<label for="tipoTasa">Tipo Tasa:</label>
				</td>
				<td align="left" colspan="0"><s:select
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTasa()"
						headerKey="" headerValue="" name="beanCriterios.tipoTasa"
						listKey="valorEnum" listValue="nombreEnum" id="tipoTasa"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" /></td>
				<s:if
					test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoLiberarEEFF()}">
					<td align="left" nowrap="nowrap">
						<label for="esLiberableEEFF">es Liberable EEFF:</label>
					</td>
					<td align="left" colspan="0">
						<s:select
							list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoPresentacionJPT()"
							name="beanCriterios.esLiberableEEFF" id="esLiberableEEFF"
							headerKey="" headerValue="Todos" listKey="valorEnum"
							listValue="nombreEnum" />
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left"><label for="conNive">Con Nive: </label></td>
				<td align="left"><s:select
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadoPresentacionJPT()"
						name="beanCriterios.conNive" id="conNive" headerKey=""
						headerValue="Todos" listKey="valorEnum" listValue="nombreEnum" />
				</td>
				<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Jefatura:</label>
					</td>
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getJefaturasJPTNuevoEnum()"
							headerKey="" headerValue="Todas" 
							name="beanCriterios.jefaturaProvincial" listKey="jefatura" listValue="descripcion" id="jefaturaProvincial"/>
					</td>
				</s:if>
			</tr>
		</table>
		<table class="tablaformbasica">
			<tr>
				<td align="right" nowrap="nowrap"><label>Fecha de alta:</label></td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="right"><label for="diaAltaIni">desde: </label></td>
							<td><s:textfield name="beanCriterios.fechaAlta.diaInicio"
									id="diaAltaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield name="beanCriterios.fechaAlta.mesInicio"
									id="mesAltaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield name="beanCriterios.fechaAlta.anioInicio"
									id="anioAltaIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td width="2%"></td>
						</tr>
					</table>
				</td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="left"><label for="diaAltaFin">hasta:</label></td>
							<td><s:textfield name="beanCriterios.fechaAlta.diaFin"
									id="diaAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield name="beanCriterios.fechaAlta.mesFin"
									id="mesAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield name="beanCriterios.fechaAlta.anioFin"
									id="anioAltaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right" nowrap="nowrap"><label>Fecha de
						presentación:</label></td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="right">
								<label for="diaPresentacionIni">desde:</label>
							</td>
							<td><s:textfield
									name="beanCriterios.fechaPresentacion.diaInicio"
									id="diaPresentacionIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaPresentacion.mesInicio"
									id="mesPresentacionIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td><s:textfield
									name="beanCriterios.fechaPresentacion.anioInicio"
									id="anioPresentacionIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionIni, document.formData.mesPresentacionIni, document.formData.diaPresentacionIni);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td width="2%"></td>
						</tr>
					</table>
				</td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="left"><label for="diaPresentacionFin">hasta:</label>
							</td>
							<td><s:textfield
									name="beanCriterios.fechaPresentacion.diaFin"
									id="diaPresentacionFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield
									name="beanCriterios.fechaPresentacion.mesFin"
									id="mesPresentacionFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield
									name="beanCriterios.fechaPresentacion.anioFin"
									id="anioPresentacionFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionFin, document.formData.mesPresentacionFin, document.formData.diaPresentacionFin);return false;"
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
					onclick="return buscarTramiteTrafico();" /> &nbsp; <input
					type="button" class="boton" name="bLimpiar" id="bLimpiar"
					value="Limpiar" onkeypress="this.onClick"
					onclick="return limpiarFormConsulta();" /></td>
			</tr>
		</table>
	</div>

	<s:hidden key="contrato.idContrato" />

	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<s:if test="%{resumenValidacion}">
		<br />
		<div id="estadisticasValidacion" style="text-align: center;">
			<%@include file="../../includes/resumenValidacion.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenTramitacion}">
		<br />
		<div id="estadisticasTramitacion" style="text-align: center;">
			<%@include file="../../includes/resumenTramitacionTelematica.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenPendienteEnvioExcel}">
		<br />
		<div id="estadisticasPendientesExcel" style="text-align: center;">
			<%@include file="../../includes/resumenPendienteEnvioExcel.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenCertificadoTasasFlag}">
		<br />
		<div id="estadisticasCertificadoTasas" style="text-align: center;">
			<s:set var="funcionDescargarCertificado"
				value="'descargarCertificado()'" />
			<s:hidden name="ficheroDescarga" id="ficheroDescarga" />
			<%@include file="../../includes/resumenCertificadoTasas.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenAutoBTV}">
		<br />
		<div id="resumenAutoInformeBtv" style="text-align: center;">
			<%@include file="../../includes/resumenOperacionesTramite.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenEEFFFlag}">
		<br />
		<div id="estadisticasEEFF" style="text-align: center;">
			<%@include file="../../includes/resumenEEFF.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenCambiosEstadoFlag}">
		<br>
		<div id="estadisticasCambiosEstado" style="text-align: center;">
			<%@include file="../../includes/resumenCambiosEstado.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenCambiosRefFlag}">
		<br />
		<div id="estadisticasCambiosRef" style="text-align: center;">
			<%@include file="../../includes/resumenCambiosRef.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenAsignacionMasivaTasasFlag}">
		<br />
		<div id="estadisticasCambiosEstado" style="text-align: center;">
			<%@include file="../../includes/resumenAsignacionMasiva.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenPresentacion576Flag}">
		<br />
		<div id="estadisticasPresentacion576" style="text-align: center;">
			<%@include file="../../includes/resumenPresentacion576.jspf"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenLiberacionEEFFFlag}">
		<br />
		<div id="estadisticasLiberacionEEFF" style="text-align: center;">
			<%@include file="../../includes/resumenLiberacionEEFF.jsp"%>
		</div>
		<br />
		<br />
	</s:if>

	<s:if test="%{resumenConsultaTarjetaEitvFlag}">
		<br />
		<div id="estadisticasConsultaTarjetaEitv" style="text-align: center;">
			<%@include file="../../includes/resumenConsultaTarjetaEitv.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<!-- //TODO MPC. Cambio IVTM. -->
	<s:if test="%{resumenAutoliquidacionIVTMFlag}">
		<br />
		<div id="estadisticasAutoliquidacionIVTM" style="text-align: center;">
			<%@include file="../../includes/resumenAutoliquidacionIVTM.jsp"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenDocBase != null}">
		<br />
		<div id="resDocBase" style="text-align: center;">
			<%@include file="../trafico/resumenDocBase.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<!--</div>	-->

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la
					b&uacute;squeda</td>
			</tr>
		</table>

		<s:if
			test="%{!resumenValidacion && !resumenTramitacion && !resumenPendienteEnvioExcel && !resumenCertificadoTasasFlag && !resumenCambiosEstadoFlag && !resumenModificarReferenciaPropiaFlag && !resumenAsignacionMasivaTasasFlag && !resumenPresentacion576Flag && !resumenConsultaTarjetaEitvFlag && !resumenAutoliquidacionIVTMFlag}">
			<%@include file="../../includes/erroresMasMensajes.jspf"%>
		</s:if>
		<s:if test="%{resumenDocumentosGeneradosFlag}">
			<br />
			<div id="resErroresFicheroDescargaPermDstvEitv"
				style="text-align: center;">
				<s:hidden name="fileName" id="fileName" />
				<%@include
					file="../../includes/resumenErroresFicheroDescargaPermDstvEitv.jspf"%>
			</div>
			<br />
			<br />
		</s:if>
		<s:if test="%{resum != null}">
			<br>
			<div id="resPermisoDistintivo" style="text-align: center;">
				<%@include
					file="../permisoDistintivoItv/resumenGeneracionDistintivo.jspf"%>
			</div>
			<br />
			<br />
		</s:if>
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
										title="Resultados por página"
										onchange="return cambiarElementosPorPaginaConsulta('navegarConsultaTramiteTrafico.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		});
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>

		<display:table name="lista" excludedParams="*"
			requestURI="navegar${action}" class="tablacoin"
			id="tablaConsultaTramite" uid="tablaConsultaTramite"
			summary="Listado de Tramites" cellspacing="0" cellpadding="0"
			decorator="${decorator}">

			<display:column property="numExpediente" title="Num Expediente"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%;" href="detalleConsultaTramiteTrafico.action"
				paramId="numExpediente" />

			<display:column property="refPropia" title="Referencia Propia"
				sortable="true" headerClass="sortable" defaultorder="descending"
				maxLength="15" style="width:4%" />

			<display:column property="bastidor" sortProperty="vehiculo.bastidor"
				title="Bastidor" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%" />

			<display:column property="matricula"
				sortProperty="vehiculo.matricula" title="Matricula" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:2%" />

			<display:column property="tipoTasa" sortProperty="tasa.tipoTasa"
				title="Tipo Tasa" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%" />

			<display:column property="codigoTasa" sortProperty="tasa.codigoTasa"
				title="Codigo Tasa" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%" />

			<display:column title="Tipo Tramite" property="tipoTramite"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:2%" paramId="idTipoTramite" />

			<display:column title="Estado" property="estado" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:2%" />

			<s:if
				test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()">
				<display:column title="Pres JPT" property="presentadoJpt"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:2%" />
			</s:if>
			<display:column style="width:1%">
				<table align="center">
					<s:if
						test="%{#attr.tablaConsultaTramite.estado != null && #attr.tablaConsultaTramite.estado.equals(@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@Finalizado_Con_Error.getNombreEnum())}">
						<td style="border: 0px;">
							<div>
								<img src="img/botonDameInfo.gif"
									style="margin-right: 10px; height: 15px; width: 15px; cursor: pointer"
									;
						 title='<s:property value="#attr.tablaConsultaTramite.respuesta"/>' />
							</div>
						</td>
					</s:if>
				</table>
			</display:column>
			<s:if
				test="%{!(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">

				<display:column
					title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaTramite(this, \"listaExpedientes\");' onKeyPress='this.onClick'/>"
					style="width:1%">
					<table align="center">
						<tr>
							<%-- <td style="border: 0px;">
								<input type="checkbox" name="listaChecksConsultaTramite" id="<s:property value="@trafico.utiles.enumerados.TipoTramiteTrafico@convertirNombreAValor(#attr.tablaConsultaTramite.tipoTramite)"/>-<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
									value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' onchange="cambiarCheck(this);"/>
							--%>
							<td style="border: 0px;"><input type="hidden"
								name="listaEstadoConsultaTramite"
								id="estado_<s:property value="#attr.tablaConsultaTramite.numExpediente"/>"
								value='<s:property value="@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirAValor(#attr.tablaConsultaTramite.estado)"/>' />

								<input type="hidden" name="listaTipoConsultaTramite"
								id="tipo_<s:property value="#attr.tablaConsultaTramite.numExpediente"/>"
								value='<s:property value="@trafico.utiles.enumerados.TipoTramiteTrafico@convertirNombreAValor(#attr.tablaConsultaTramite.tipoTramite)"/>' />

								<input type="hidden" name="listaMatriculasConsultaTramite"
								id="matricula_<s:property value="#attr.tablaConsultaTramite.numExpediente"/>"
								value='<s:property value="#attr.tablaConsultaTramite.vehiculo[0].matricula"/>' />

								<input type="hidden" name="listaMBastidorConsultaTramite"
								id="bastidor_<s:property value="#attr.tablaConsultaTramite.numExpediente"/>"
								value='<s:property value="#attr.tablaConsultaTramite.vehiculo[0].bastidor"/>' />
							</td>
							<td style="border: 0px;"><input type="checkbox"
								name="listaExpedientes" id="listaExpedientes"
								value="<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" />
							</td>

							<td style="border: 0px;"><img src="img/history.png"
								alt="ver evolución"
								style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
								onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);"
								title="Ver evolución" /></td>
						</tr>
					</table>
				</display:column>
			</s:if>
			<s:if
				test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
				<display:column style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;"><img src="img/history.png"
								alt="ver evolución"
								style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
								onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numExpediente"/>);"
								title="Ver evolución" /></td>
						</tr>
					</table>
				</display:column>
			</s:if>
			<input type="hidden" name="resultadosPorPagina" />
		</display:table>
	</div>
	<s:if test="%{resumenFicheroAEAT}">
		<br />
		<div id="resErroresFicheroAEAT" style="text-align: center;">
			<%@include file="../../includes/resumenErroresFicheroAEAT.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenFicheroMOVE}">
		<br>
		<div id="resErroresFicheroMOVE" style="text-align: center;">
			<%@include file="../../includes/resumenErroresFicheroMOVE.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenFicheroSolicitud05Flag}">
		<br>
		<div id="resErroresFicheroSolicitud05" style="text-align: center;">
			<%@include
				file="../../includes/resumenErroresFicheroSolicitud05.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenSolicitudNRE06Flag}">
		<br />
		<div id="resErroresSolicitudNRE06" style="text-align: center;">
			<%@include file="../../includes/resumenErroresSolicitudNRE06.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if test="%{resumenLiquidacionNRE06}">
		<br />
		<div id="resErroresFicheroAEAT" style="text-align: center;">
			<%@include file="../../includes/resumenErroresLiquidacionNRE06.jspf"%>
		</div>
		<br />
		<br />
	</s:if>
	<s:if
		test="%{(((@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoTrafico()) && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())) && (lista.getFullListSize()>0))}">
		<!-- Etiqueta personalizada que genera el html de la tabla que contiene los botones de acción de la página. Ver clase BotonesPaginaConsultaTag -->
		<oegam:botonesPaginaConsulta />
		<div id="bloqueLoadingConsultar"
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
	</s:if>
</s:form>
<div id="divEmergenteMatricula"
	style="display: none; background: #f4f4f4;"></div>
<div id="divEmergenteJustfProf"
	style="display: none; background: #f4f4f4;"></div>
<div id="divEmegergenteJustificantes"
	style="display: none; background: #f4f4f4;"></div>
<div id="divEmergenteAutorizar"
	style="display: none; background: #f4f4f4;"></div>
<s:if test="%{impresoEspera}">
	<script type="text/javascript">muestraDocumento();</script>
</s:if>

<s:if test="%{imprimirFichero}">
	<script type="text/javascript">muestraDocumentoFicheroAEAT();</script>
</s:if>

<s:if test="%{generadoNRE06}">
	<s:form id="recuperarFichero" method="post"
		action="descargarFicheroConsultaTramiteTrafico">
		<s:hidden name="fileName"></s:hidden>
	</s:form>
	<script type="text/javascript">
		ocultarLoadingConsultar(document.getElementById('bGenerarNRE06'), 'Generar Solicitud NRE-06');
		$('#recuperarFichero').submit();
		setTimeout(function(){ // Delay for Chrome
			window.location.href = 'mailto:modelo06@gestoresmadrid.org';
		}, 2000);
	</script>
</s:if>

<%--<s:if test="%{generado05}">
	<s:form id="recuperarFichero" method="post" action="descargarFicheroConsultaTramiteTrafico">
		<s:hidden name="fileName"></s:hidden>
	</s:form>
	<script type="text/javascript">
		ocultarLoadingConsultar(document.getElementById('bGenerarSolicitud05'), 'Generar Solicitud 05');
		$('#recuperarFichero').submit();
			setTimeout(function(){ // Delay for Chrome
			//window.location.href = 'mailto:modelo06@gestoresmadrid.org';
		}, 2000);
	</script>
</s:if>
--%>
<script type="text/javascript">
	// Para desconectar la tramitación (desconectar=1), para habilitar (0)
	var desconectar = 0;
	function preTramitarTelematicoTramite(boton) {
		if (desconectar) {
			alert("La tramitación telemática está temporalmente fuera de servicio");
		} else {
			tramitarTelematicoTramite(boton,'${session.razonSocialContratoP}');
		}
	};
</script>