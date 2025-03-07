<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam"%>
<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Evolución Persona <s:property value="consultaEvolucionPersonaBean.nif"/></span>
					</td>
				</tr>
			</table>
		</div>
	</div>

	<%@include file="../../includes/erroresYMensajes.jspf"%>

	<div class="busqueda">
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

		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="90%">
				<tr>
					<td align="right">
						<table width="50%">
							<tr>
								<td width="90%" align="right">
									<label for="idResultadosPorPaginaPersona">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaPersona" value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaPersona('navegarConsultaEvolucionPersona.action', 'displayTagDivPersona', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<div id="displayTagDivPersona">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>

			<display:table name="lista" excludedParams="*" sort="external" class="tablacoin" uid="tablaEvolucionPersona"
				summary="Listado de Evolucion Persona" cellspacing="0" cellpadding="0" requestURI="navegarConsultaEvolucionPersona.action"
				decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorEvolucionPersona">

				<display:column property="fechaHora" title="FECHA" style="width:140px;" sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="22" sortProperty="id.fechaHora"/>
				<display:column property="tipoTramite" title="Tipo de trámite" style="width:160px;" sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="40"/>
				<display:column property="numExpediente" title="Número de expediente" sortable="true"
					headerClass="sortable" defaultorder="descending" maxLength="22" style="width:110px;" />
				<display:column title="Tipo Actuación" sortable="true" headerClass="sortable" sortProperty="tipoActuacion"
					defaultorder="descending" maxLength="500" style="width:140px;">
					<s:property value="#attr.tablaEvolucionPersona.tipoActuacion" />
				</display:column>
				<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true" headerClass="sortable" defaultorder="descending"
					maxLength="50" style="width:300px;" />
			</display:table>
		</div>
		<div id="ventana" style="position: absolute; left: 450px; top: 260px; filter: alpha(opacity = 100); opacity: 1.00; width: 340px; height: auto; border: 1px solid black; background-color: #FFDDDD; text-align: left; padding-left: 0px; visibility: hidden">Datos de entrada</div>
	</div>
</div>

<script type="text/javascript">
	$(function() {
		$("#displayTagDivPersona").displayTagAjax();
	})
</script>