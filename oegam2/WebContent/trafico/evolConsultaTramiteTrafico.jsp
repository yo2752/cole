<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/trafico/consultaTramiteTrafico.js" type="text/javascript"></script>
<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion</span>
				</td>
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
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											id="idResultadosPorPaginaEvolConsTramTraf"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvoConsTramTraf();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagEvoConsTramTraf" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvoConsTramTraf" requestURI= "navegarEvoConsultaTramTraf.action"
				id="tablaEvoConsTramTraf" summary="Listado Evolucion"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">	
				
			<display:column property="numExpediente" title="Num Expediente" style="width:4%" />

			<display:column property="fecha" title="Fecha Cambio" sortProperty="id.fechaCambio"
				sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:4%"
				format="{0,date,dd-MM-yyyy HH:mm:ss}" />

			<display:column property="estadoAnterior" title="Estado anterior" sortable="true" sortProperty="id.estadoAnterior" headerClass="sortable" defaultorder="descending" style="width:4%" />

			<display:column property="estadoNuevo" title="Estado nuevo" sortable="true" sortProperty="id.estadoNuevo" headerClass="sortable" defaultorder="descending" style="width:4%" />

			<display:column property="usuario" title="Usuario" sortable="true" sortProperty="usuario.apellidosNombre" headerClass="sortable" defaultorder="descending" style="width:4%" />

		</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvoConsTramTraf").displayTagAjax();
		});
	</script>	