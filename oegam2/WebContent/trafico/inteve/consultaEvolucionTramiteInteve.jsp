<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Evoluci&oacute;n del Tr&aacute;mite: <s:property value="consultaEvolucionTramiteTraficoBean.numExpediente" /></span>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<div class="busqueda">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>

		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="90%">
				<tr>
					<td align="right">
						<table width="50%">
							<tr>
								<td width="90%" align="right">
									<label for="idResultadosPorPagina">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaEvTramiteTraf.action', 'displayTagDiv', this.value);" />
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

		<div id="displayTagDiv">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>

			<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="tablaEvolucionTramiteTrafico"
					summary="Listado de Evolucion Trámite" cellspacing="0" cellpadding="0" requestURI="navegarConsultaEvTramiteTraf.action"
					decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolucionTramite">
				<display:column property="fechaCambio" paramName="id.fechaCambio" paramProperty="id.fechaCambio" sortProperty="id.fechaCambio" title="Fecha" style="width:4%" sortable="true" headerClass="sortable" defaultorder="descending"/>
				<display:column property="estadoAnterior" paramName="id.estadoAnterior" paramProperty="id.estadoAnterior" sortProperty="id.estadoAnterior" title="Estado anterior" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="estadoNuevo" paramName="id.estadoNuevo" paramProperty="id.estadoNuevo" sortProperty="id.estadoNuevo" title="Estado nuevo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%"/>
			</display:table>
		</div>
	</div>