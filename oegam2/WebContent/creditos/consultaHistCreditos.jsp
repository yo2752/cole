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
					<span class="titulo">Hist&oacute;rico de carga de cr&eacute;ditos</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<s:hidden name="volverResumenCargaCredito" id="volverResumenCargaCredito"></s:hidden>
	<s:hidden name="volverCargaCredito" id="volverCargaCredito" ></s:hidden>
	<s:hidden name="idContratoYProvincia" id="idContratoYProvincia"></s:hidden>
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
						name="historicoCreditosBean.idContrato"
						listKey="codigo"
						listValue="descripcion" />
					</td>
				</s:if>
				<s:else>
					<s:hidden id="contratoColegiado" name="historicoCreditosBean.idContrato"/>
				</s:else>

				<td align="right" style="vertical-align: middle;">
					<label for="idTipoCredito">Tipo de Cr&eacute;dito:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="tipoCredito" onblur="this.className='input2';" onfocus="this.className='inputfocus';" list="@org.gestoresmadrid.oegamCreditos.view.utiles.UtilesVistaCredito@getInstance().obtenerTiposCreditos()" headerKey="" headerValue="Todos" name="historicoCreditosBean.tipoCredito" listKey="tipoCredito" listValue="tipoCredito + ' - ' + descripcion"/>					
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
											<s:textfield name="historicoCreditosBean.fechaAlta.diaInicio" id="diaAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="historicoCreditosBean.fechaAlta.mesInicio" id="mesAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="historicoCreditosBean.fechaAlta.anioInicio" id="anioAltaIni" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
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
											<s:textfield name="historicoCreditosBean.fechaAlta.diaFin" id="diaAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="historicoCreditosBean.fechaAlta.mesFin" id="mesAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
										</td>
										<td>/</td>
										<td>
											<s:textfield name="historicoCreditosBean.fechaAlta.anioFin" id="anioAltaFin" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
										</td>
										<td>
											<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" title="Seleccionar fecha">
												<img class="PopcalTrigger" src="img/ico_calendario.gif" height="16" border="0" alt="Calendario" />
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
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onClick="doPost('formData', 'buscarConsultaHistCredito.action')" />
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarFormHistoricoCargaCreditos();" />
				</td>
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
					<s:textfield name="cantidadSumadaTotal" id="totalCreditosSumados" cssClass="inputview" readonly="true" maxlength="4"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">Total Cr&eacute;ditos Restados</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="cantidadRestadaTotal" id="totalCreditosRestados" cssClass="inputview" readonly="true" maxlength="4"/>
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
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaHistCredito.action', 'displayTagDiv', this.value);" />
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

			<display:table name="lista" excludedParams="*" requestURI="navegarConsultaHistCredito.action"
				class="tablacoin" summary="Listado de Historicos" cellspacing="0" cellpadding="0" uid="listaHistorico"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorHistCreditos">

				<display:column property="fecha" title="Fecha" sortable="true" headerClass="sortable" defaultorder="descending" style="width:6%" format="{0,date,dd-MM-yyyy HH:mm:ss}" />

				<display:column property="contrato.razonSocial" title="Razón Social Contrato" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />

				<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />

				 <display:column property="credito.tipoCreditoVO.tipoCredito" title="Tipo Crédito" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%" />

				<display:column property="credito.tipoCreditoVO.descripcion" title="Descripción" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />

				<display:column property="cantidadSumada" title="Créditos Añadidos" sortable="true" headerClass="sortable" defaultorder="descending" style="width:0.5%" maxLength="4" />

				<display:column property="cantidadRestada" title="Créditos Restados" sortable="true" headerClass="sortable" defaultorder="descending" style="width:0.5%" maxLength="4" />
			</display:table>
		</div>
	</div>
	<s:if test="volverResumenCargaCredito || volverCargaCredito">
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver" onClick="return volverPantallasCredito();" onKeyPress="this.onClick" />
				</td>
			</tr>
		</table>
	</s:if>
</s:form>

<script type="text/javascript">
	if (document.getElementById('diaAltaIni').value != null && document.getElementById("diaAltaIni").value != "") {
		document.getElementById('diaAltaIni').value = rellenaCerosIzqVar(document.getElementById('diaAltaIni').value, 2);
	}
	if (document.getElementById('mesAltaIni').value != null && document.getElementById('mesAltaIni').value != "") {
		document.getElementById('mesAltaIni').value = rellenaCerosIzqVar(document.getElementById('mesAltaIni').value, 2);
	}
	if (document.getElementById('diaAltaFin').value != null && document.getElementById('diaAltaFin').value != "") {
		document.getElementById('diaAltaFin').value = rellenaCerosIzqVar(document.getElementById('diaAltaFin').value, 2);
	}
	if (document.getElementById('mesAltaFin').value != null && document.getElementById('mesAltaFin').value != "") {
		document.getElementById('mesAltaFin').value = rellenaCerosIzqVar(document.getElementById('mesAltaFin').value, 2);
	}

	function volverPantallasCredito() {
		var volverResumenCargaCredito = document.getElementById('volverResumenCargaCredito').value;
		var volverCargaCredito = document.getElementById('volverCargaCredito').value;

		if (volverResumenCargaCredito=="true") {
			return cancelarForm("inicioResumenCargaCredito.action");
		} else if (volverCargaCredito=="true") {
			var idContratoYProvincia = document.getElementById('idContratoYProvincia').value;
			return cancelarForm("buscarConsultaCreditoCargar.action?idContratoProvincia=" + idContratoYProvincia);
		}
	}
</script>