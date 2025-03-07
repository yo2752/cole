<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<s:set var="isAdmin" value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}"></s:set>

<s:form method="post" id="formData" name="formData">
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Gasto mensual de cr&eacute;ditos</span>
				</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
	<div id="busqueda">				
		<table class="tablaformbasica">
			<s:if test="%{#isAdmin==true}">
				<tr>
					<td align="left" nowrap="nowrap" style="width:15%; vertical-align: middle;">
						<label for="fechaDesde">Fecha facturaci&oacute;n desde:</label> 
					</td>
					<td align="left">
						<table width="20%">
							<tr>
								<td>
									<s:select name="gastoMensualCreditosBean.fechaCreditos.mesInicio" id="mesAltaIni" style="margin-left: 22px"
										onblur="this.className='input2';" cssStyle="display: inline;"
										onfocus="this.className='inputfocus';"
										list="#{'1':'Enero','2':'Febrero','3':'Marzo','4':'Abril','5':'Mayo','6':'Junio',
										'7':'Julio','8':'Agosto','9':'Septiembre','10':'Octubre','11':'Noviembre','12':'Diciembre' }"
										headerKey="" emptyOption="false" />
								</td>
								<td>
									<s:select name="gastoMensualCreditosBean.fechaCreditos.anioInicio" id="anioAltaIni"
										onblur="this.className='input2';" cssStyle="display: inline;"
										onfocus="this.className='inputfocus';"
										list="anios"
										headerKey="" emptyOption="false" />
								</td>
							</tr>
						</table>
					</td>
					<td align="left" style="vertical-align: middle;">
						<label for="fechaHasta">Hasta:</label>
					</td>
					<td align="left">
						<table width="20%">
							<tr>
								<td>
									<s:select name="gastoMensualCreditosBean.fechaCreditos.mesFin" id="mesAltaFin" style="margin-left: 22px"
										onblur="this.className='input2';" cssStyle="display: inline;"
										onfocus="this.className='inputfocus';"
										list="#{'1':'Enero','2':'Febrero','3':'Marzo','4':'Abril','5':'Mayo','6':'Junio',
										'7':'Julio','8':'Agosto','9':'Septiembre','10':'Octubre','11':'Noviembre','12':'Diciembre' }"
										headerKey="" emptyOption="false" />
								</td>
								<td>
									<s:select name="gastoMensualCreditosBean.fechaCreditos.anioFin" id="anioAltaFin"
										onblur="this.className='input2';" cssStyle="display: inline;"
										onfocus="this.className='inputfocus';"
										list="anios"
										headerKey="" emptyOption="false" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="contrato">Contrato:</label>
				</td>
				<td align="left">
					<s:select id="contrato"
						list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getComboContratosHabilitados()" 
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						listKey="codigo"
						listValue="descripcion"
						cssStyle="width:150px"
						name="gastoMensualCreditosBean.idContrato"
						headerKey=""
						headerValue="Todos">
					</s:select>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="concepto">Concepto: </label>
				</td>
				<td align="left">
					<s:select
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboConceptoCreditoFacturado()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						headerKey="" headerValue="Todos"
						cssStyle="width:150px"
						name="gastoMensualCreditosBean.keyconcepto"
						listKey="valorEnum" listValue="nombreEnum"
						id="concepto"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="tipoCredito">Tipo Crédito:</label>
				</td>
				<td align="left">
					<s:select id="tipoCredito"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@org.gestoresmadrid.oegamCreditos.view.utiles.UtilesVistaCredito@getInstance().obtenerTiposCreditos()"
						headerKey=""
						headerValue="Todos"
						name="gastoMensualCreditosBean.tipoCredito"
						cssStyle="width:170px"
						listKey="tipoCredito"
						listValue="tipoCredito + ' - ' + descripcion"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="numExpediente">Núm. Trámite: </label>
				</td>
				<td align="left">
					<s:textfield name="gastoMensualCreditosBean.tramite"
							id="numExpediente"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							maxlength="15"
							cssStyle="width:170px;" />
				</td>
			</tr>
		</table>
		<div class="acciones center">
			<input type="button" value="Buscar" class="boton" onClick="doPost('formData', 'buscarGastoMensualCredito.action')" />
			<input type="button" value="Limpiar" class="boton" onClick="return limpiar()" />
		</div>
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
	<div id="resultado" style="width:100%;background-color:transparent;">
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
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									id="idResultadosPorPagina"
									value="resultadosPorPagina"
									title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsulta('navegarGastoMensualCredito.action', 'displayTagDiv', this.value);"
									/>
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

	<div id="displayTagDiv">
		<div style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>
		<display:table name="lista" excludedParams="*" sort="external"
			requestURI="navegarGastoMensualCredito.action" class="tablacoin"
			uid="tablaCreditosFacturados" summary="Historico de creditos"
			cellspacing="0" cellpadding="0">

			<display:column property="fecha" title="Fecha" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="fecha" format="{0,date,dd/MM/yyyy}" />

			<display:column property="tipoTramite.tipoCredito.tipoCredito" title="Tipo" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="tipoTramite.tipoCredito.tipoCredito" />

			<display:column property="tipoTramite.descripcion" title="Tipo Trámite" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="tipoTramite.descripcion" />

			<display:column property="numeroCreditos" title="Créditos" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:4%;"
				paramId="numeroCreditos" />

			<display:column property="concepto" title="Concepto" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:8%;"
				paramId="concepto" />

			<display:column title="Tr&aacute;mites" style="width:1%;">
				<table align="center">
				<tr>
					<td style="border: 0px;">
						<img src="img/history.png" alt="ver trámites" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
						onclick="abrirConsultaTramites(<s:property value="%{#attr.tablaCreditosFacturados.idCreditoFacturado}" />);" title="Ver trámites implicados"/>
					</td>
				</tr>
				</table>
			</display:column>

		</display:table>
	</div>
	<!-- FIN TABLA DE RESULTADOS -->
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() && lista.getList().size()>0}">
		<input class="botonGrande" id="bExportar" name="bExportar" value="Exportar tabla completa" onclick="generarFichero();return false;" onkeypress="this.onClick" type="button">
	</s:if>
</s:form>

<div id="listCreditoFacturadoTramites" style="display: none; width: 800px;"></div>

<script type="text/javascript">
	function limpiar() {
		$('#contrato').attr("value", "");
		$('#tipoCredito').attr("value", "");
		$('#concepto').attr("value", "");
		$('#numExpediente').attr("value", "");
	}

	function abrirConsultaTramites(id){
		var $url = "consultaTramitesGastoMensualCredito.action?idCreditoFacturado="+id;
		popUp("listCreditoFacturadoTramites", $url);
	}

	function generarFichero() {
		document.formData.action="exportarResultadosGastoMensualCredito.action";
		document.formData.submit();
	}
</script>