<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion Usuario <s:property value="#attr.nif" /></span>
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
											id="idResultadosPorPaginaEvolUsuario"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucionUsuario();">
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
			$("#displayTagEvolucionUsuarioDiv").displayTagAjax();
		});
	</script>	
	<div id="displayTagEvolucionUsuarioDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin"
				uid="tablaEvolucionUsuario" requestURI= "navegarEvolucionUsuario.action"
				id="tablaEvolucionUsuario" summary="Listado Evolucion Usuario"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0"
				decorator="${decorator}">	
			
			
				
			<display:column property="apellidosNombre" title="Apellidos Nombre" sortable="true" headerClass="sortable"  sortProperty="usuario.apellidosNombre"
				defaultorder="descending" style="width:2%"/>
				
			<display:column property="tipoAct" title="Tipo Actuacion" sortable="true" headerClass="sortable"  sortProperty="tipoActuacion"
				defaultorder="descending" style="width:2%"/>		
				
			<display:column property="fechaCambio" title="Fecha Cambio"
			sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" style="width:2%"
			format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="id.fechaCambio"/>
			
			<display:column property="estadoNuevo" title="Estado nuevo" sortable="true" 
			headerClass="sortable" defaultorder="descending" style="width:2%" sortProperty="estadoNuevo"/>
			
			
			<display:column property="estadoAnt" title="Estado anterior" sortable="true" 
			headerClass="sortable" defaultorder="descending" style="width:2%" sortProperty="estadoAnt"/>

			

			<display:column property="contratoAnt" title="Contrato anterior" sortable="true" 
				headerClass="sortable" defaultorder="descending" style="width:2%" sortProperty="contratoAnt"/>
			
			
			
		</display:table>
	</div>
</div>
