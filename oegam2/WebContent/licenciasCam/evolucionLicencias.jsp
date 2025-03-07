<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
		<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Evolución de la Licencia <s:property value="consultaLicenciasCamBean.numExpediente" /></span>
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
											id="idResultadosPorPagina" value="resultadosPorPagina"
											onchange="cambiarElementosPorPaginaConsulta('navegarEvolucionLicencia.action', 'displayTagDiv', this.value);" />
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

			<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="tablaEvolucionLicencia"
				summary="Listado de Estados de Licencias" cellspacing="0" cellpadding="0" requestURI="navegarEvolucionLicencia.action"
				decorator="${decorator}">
				
				<display:column property="numExpediente" title="Nº expediente" style="width:15%" />
				
				<display:column property="fechaCambio" title="Fecha" sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" 
					style="width:15%" format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="fechaCambio"/>

				<display:column title="Estado anterior" paramName="estadoAnterior" paramProperty="estadoAnterior" sortable="true" headerClass="sortable" defaultorder="descending" style="width:15%" >
					<s:property value="%{@org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam@convertirTexto(#attr.tablaEvolucionLicencia.estadoAnterior)}" />
				</display:column>
				
				<display:column title="Estado nuevo" paramName="estadoNuevo" paramProperty="id.estadoNuevo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:15%" >
					<s:property value="%{@org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam@convertirTexto(#attr.tablaEvolucionLicencia.estadoNuevo)}" />
				</display:column>
				
				<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending" style="width:30%" />
				
			</display:table>
			
		</div>
	</div>	
</div>