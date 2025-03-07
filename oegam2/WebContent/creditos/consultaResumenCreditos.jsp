<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/creditos.js" type="text/javascript"></script>

<script type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Resumen de carga de cr&eacute;ditos</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<s:hidden id="esAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"/>
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="num_Colegiado">Colegiado:</label>
					</td>
					<td align="left">
						<s:select id="contratoColegiado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()"
						cssStyle="width:250px"
						headerKey=""
						headerValue="Seleccione Contrato"
						name="resumenCargaCreditosBean.idContrato"
						listKey="codigo"
						listValue="descripcion" />
					</td>
				</s:if>
				<s:else>
					<s:hidden id="contratoColegiado" name="resumenCargaCreditosBean.idContrato" />
				</s:else>

				<td align="right" style="vertical-align: middle;">
					<label for="idTipoCredito" >Tipo de Cr&eacute;dito:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="tipoCredito" onblur="this.className='input2';" onfocus="this.className='inputfocus';" list="@org.gestoresmadrid.oegamCreditos.view.utiles.UtilesVistaCredito@getInstance().obtenerTiposCreditos()" headerKey="" headerValue="Todos" name="resumenCargaCreditosBean.tipoCredito" listKey="tipoCredito" listValue="tipoCredito + ' - ' + descripcion"/>					
				</td>
			</tr>
			<tr>
				<td colspan="4">
					<table style="width:80%">
						<tr>
							<td align="left" style="vertical-align: middle;"><label for="diaAltaIni">Fecha alta:</label></td>
							<td>
								<table style="width:20%">
									<tr>
										<td align="left" width="10%" style="vertical-align: middle;"><label for="diaAltaIni">Desde:</label></td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.diaInicio" id="diaAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.mesInicio" id="mesAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.anioInicio" id="anioAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
										</td>
										<td>
											<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" title="Seleccionar fecha">
												<img class="PopcalTrigger" src="img/ico_calendario.gif" height="16" border="0" alt="Calendario"/>
											</a>
										</td>
									</tr>
								</table>
							</td>
							<td>
								<table style="width:20%">
									<tr>
										<td align="left" width="10%" style="vertical-align: middle;"><label for="diaAltaFin">Hasta:</label></td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.diaFin" id="diaAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.mesFin" id="mesAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="resumenCargaCreditosBean.fechaAlta.anioFin" id="anioAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
										</td>
										<td>
											<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" title="Seleccionar fecha">
												<img class="PopcalTrigger" src="img/ico_calendario.gif" height="16" border="0" alt="Calendario"/>
											</a>
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onClick="doPost('formData', 'buscarResumenCargaCredito.action')"/>
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarFormResumenCargaCreditos();"/>
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}" >
					<td>
						<input type="button" class="boton" name="bCreditosGBJA" id="bCreditosGBJA" value="Créditos GBJA" onkeypress="this.onClick" onclick="javascript:creditosGBJA();"/>
					</td>
				</s:if>
			</tr>
		</table>
	
		<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<div id="resultado" style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
				</tr>
			</table>
		</div>

		<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">Total Cr&eacute;ditos Sumados</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="cantidadSumadaTotal" id="totalCreditosSumados" cssClass="inputview" readonly="true" maxlength="4" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">Total Cr&eacute;ditos Restados</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="cantidadRestadaTotal" id="totalCreditosRestados" cssClass="inputview" readonly="true" maxlength="4" />
				</td>
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
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarResumenCargaCredito.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<script type="text/javascript">
			$(function() {
				$("#displayTagDiv").displayTagAjax();
			})
		</script>

		<div id="displayTagDiv" class="divScroll">

			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>

			<display:table name="lista" excludedParams="*" requestURI="navegarResumenCargaCredito.action"
				class="tablacoin" summary="Listado de Resumen" cellspacing="0" cellpadding="0" uid="listaResumen">

				<display:column property="contrato.colegiadoDto.numColegiado" title="Colegiado" sortable="true" sortProperty="numColegiado" headerClass="sortable" defaultorder="descending"	 />

				<display:column property="contrato.razonSocial" title="Razón Social Contrato" sortable="true" headerClass="sortable" defaultorder="descending" />

				<display:column title="Tipo Crédito" sortProperty="tipoCredito" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%">
					<a href="javascript:onClick=detalleHistorico('${listaResumen.credito.tipoCredito}', '${listaResumen.idContrato}')">	
						<s:property value="#attr.listaResumen.credito.tipoCredito"/> 
					</a>
				</display:column>

				<display:column property="credito.tipoCreditoDto.descripcion" title="Descripción" sortProperty="descripcion" sortable="true" headerClass="sortable" defaultorder="descending" />

				<display:column property="cantidadSumada" title="Créditos Añadidos" sortProperty= "sumada" sortable="true" headerClass="sortable" defaultorder="descending"/>

				<display:column property="cantidadRestada" title="Créditos Restados" sortProperty= "restada" sortable="true" headerClass="sortable" defaultorder="descending" />

				<display:column property="credito.creditos" title="Créditos Totales Disponibles" sortable="true" headerClass="sortable" defaultorder="descending" />

			</display:table>
		</div>

		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && lista.getList().size()>0}">
			<input class="botonGrande" type="button" id="bExportar" name="bExportar" value="Exportar tabla completa" onClick="generarFichero();return false;" onKeyPress="this.onClick" />
		</s:if>
	</div>
</s:form>

<script type="text/javascript">
	function creditosGBJA() {
		document.formData.action="generarGBJAResumenCargaCredito.action";
		document.formData.submit();
		return true;
	}

	function generarFichero() {
		document.formData.action="exportarResultadosResumenCargaCredito.action";
		document.formData.submit();
	}

	function detalleHistorico(tipoCredito, idContrato) {
		document.formData.action="buscarConsultaHistCredito.action?historicoCreditosBean.tipoCredito=" + tipoCredito + "&historicoCreditosBean.idContrato=" + idContrato
			+ "&historicoCreditosBean.fechaAlta.diaInicio=" + document.getElementById("diaAltaIni").value
			+ "&historicoCreditosBean.fechaAlta.mesInicio=" + document.getElementById("mesAltaIni").value
			+ "&historicoCreditosBean.fechaAlta.anioInicio=" + document.getElementById("anioAltaIni").value
			+ "&historicoCreditosBean.fechaAlta.diaFin=" + document.getElementById("diaAltaFin").value
			+ "&historicoCreditosBean.fechaAlta.mesFin=" + document.getElementById("mesAltaFin").value
			+ "&historicoCreditosBean.fechaAlta.anioFin=" + document.getElementById("anioAltaFin").value
			+ "&volverResumenCargaCredito=true";
		document.formData.submit();
		return true;
	}
</script>