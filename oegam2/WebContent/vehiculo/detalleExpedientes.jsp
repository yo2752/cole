<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="contentTabs" id="Expedientes">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta de Expedientes</span></td>
			</tr>
		</table>
	</div>

	<s:form method="post" id="formData" name="formData">
		<div class="busqueda">

			<div id="resultado"
				style="width: 100%; background-color: transparent;">
				<table class="subtitulo" cellSpacing="0" style="width: 100%;">
					<tr>
						<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
					</tr>
				</table>
			</div>

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
											id="idResultadosPorPagina" value="resultadosPorPagina" title="Resultados por página"
											onchange="cambiarElementosPorPaginaConsulta('navegarDetalleVehiculo.action', 'displayTagDiv', this.value);" />
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

				<display:table name="lista" excludedParams="*" class="tablacoin" requestURI="navegarDetalleVehiculo.action"
					uid="tablaExpedientesDetalleVehiculo" summary="Listado de Tramites" cellspacing="0" cellpadding="0">

						<display:column property="numExpediente" title="Num Expediente" href="detalleConsultaTramiteTrafico.action" paramId="numExpediente"
							sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%;"/>

			 			<display:column title="Tipo Tramite" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%">
								<s:property	value="%{@org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico@convertirTexto(#attr.tablaExpedientesDetalleVehiculo.tipoTramite)}" />
						</display:column>

						<display:column property="titular.persona.nif" title="Nif"
							sortable="false" headerClass="sortable" defaultorder="descending" style="width:3%;"/>

						<display:column title="Datos Del Titular" sortable="false" headerClass="sortable" defaultorder="descending" style="width:3%;">
							<s:property value="#attr.tablaExpedientesDetalleVehiculo.titular.persona.apellido1RazonSocial"/> <s:property value="#attr.tablaExpedientesDetalleVehiculo.titular.persona.apellido2"/>
						</display:column>

						<display:column title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
								<s:property value="%{@org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico@convertirTexto(#attr.tablaExpedientesDetalleVehiculo.estado)}" />
						</display:column>

						<display:column property="fechaPresentacion" title="Fecha Presentación" sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%"/>
				</display:table>
			</div>
		</div>
	</s:form>

	<table class="acciones" width="95%" align="left">
		<tr>
			<td align="center" style="size: 100%; text-align: center; list-style: none;">
				<input type="button" class="boton" name="bVolver" id="idVolver" value="Volver" onClick="javascript:document.location.href='inicioConsultaVehiculo.action';"
				onKeyPress="this.onClick" />
			</td>
		</tr>
	</table>
</div>