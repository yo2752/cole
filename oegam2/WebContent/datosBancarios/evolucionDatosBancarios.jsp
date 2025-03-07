<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion Dato Bancario</span>
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
											id="idResultadosPorPaginaEvolModelos"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucionModelos();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionDatBanDiv").displayTagAjax();
		});
	</script>
	<div id="displayTagEvolucionDatBanDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucionDatoBancario" requestURI= "navegarEvolucionDtb.action"
				id="tablaEvolucionDatoBancario" summary="Listado Evolucion Dato Bancario"
				excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">

			<display:column property="fechaCambio" title="Fecha Cambio"
				sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:3%"
				format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="id.fechaCambio"/>

			<display:column property="apellidosNombre" title="Usuario" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:35%" sortProperty="usuario.apellidosNombre"/>

			<display:column property="tipoActuacion" title="Tipo Actuacion" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:10%" sortProperty="tipoActuacion" />

			<display:column property="nifAnt" title="NIF Ant." sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:10%" sortProperty="nifAnt" />

			<display:column property="nifNuevo" title="NIF Nuevo" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:10%" sortProperty="nifNuevo"/>

			<display:column property="estadoAnt" title="Estado Ant." sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:10%" sortProperty="estadoAnt"/>

			<display:column property="estadoNuevo" title="Estado Nuevo" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:10%" sortProperty="estadoNuevo"/>

			<display:column property="formaPagoAnt" title="Forma Pago Ant." sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:8%" sortProperty="formaPagoAnt"/>

			<display:column property="formaPagoNueva" title="Forma Pago Nueva" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:8%" sortProperty="formaPagoNueva"/>

			<display:column property="camposModificacion" title="Campos Modificados" sortable="true"
				headerClass="sortable" defaultorder="descending" style="width:20%" sortProperty="camposModificacion"/>

		</display:table>
	</div>
</div>