<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion Remesas</span>
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
											id="idResultadosPorPaginaEvolRemesa"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucionRemesas();">
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
			$("#displayTagEvolRemesasDiv").displayTagAjax();
		});
	</script>	
	<div id="displayTagEvolRemesasDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucionRemesas" requestURI= "navegarEvolucionReme.action"
				id="tablaEvolucionRemesas" summary="Listado Evolucion Remesa"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">	
				
			<display:column property="numExpediente" title="Nº Expediente" sortable="true" headerClass="sortable"  sortProperty="id.numExpediente"
				defaultorder="descending" style="width:2%"/>	
				
			<display:column property="fechaCambio" title="Fecha Cambio"
			sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:3%"
			format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="id.fechaCambio"/>
			
			<display:column property="estadoAnterior" title="Estado anterior" sortable="true" 
			headerClass="sortable" defaultorder="descending" style="width:2%" sortProperty="id.estadoAnterior"/>

			<display:column property="estadoNuevo" title="Estado nuevo" sortable="true" 
				headerClass="sortable" defaultorder="descending" style="width:2%" sortProperty="id.estadoNuevo"/>

			<display:column property="apellidosNombre" title="Usuario" sortable="true"  headerClass="sortable" 
				defaultorder="descending" style="width:4%" sortProperty="usuario.apellidosNombre"/>

		</display:table>
	</div>
</div>
