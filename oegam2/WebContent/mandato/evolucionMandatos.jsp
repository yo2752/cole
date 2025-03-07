<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolución Mandato</span>
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
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"
									id="idResultadosPorPaginaEvolMandatos" name="resultadosPorPagina"
									value="resultadosPorPagina" title="Resultados por página"
									onchange="cambiarElementosPorPaginaConsultaEvolucionMandatos();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagEvolucionMandatosDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" class="tablacoin" uid="tablaEvolucionMandatos" requestURI= "navegarEvolucionMandatos.action"
			id="tablaEvolucionMandatos" summary="Listado Evolucion Mandatos" excludedParams="*" sort="list"				
			cellspacing="0" cellpadding="0" decorator="${decorator}">	
				
			<display:column property="codigoMandato" title="C&oacutedigo Mandato" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:2%" sortProperty="codigoMandato"/>
				
			<display:column title="Tipo Actuaci&oacuten" sortProperty="tipoActuacion" headerClass="sortable" defaultorder="descending" style="width:2%">
			 	<s:property value="%{@org.gestoresmadrid.core.model.enumerados.TipoActualizacion@getNombrePorValor(#attr.tablaEvolucionMandatos.tipoActuacion)}" />
			</display:column>
				
			<display:column property="fechaCambio" title="Fecha Cambio" sortable="true" headerClass="sortable" defaultorder="descending" maxLength="22" 
				style="width:2%" format="{0,date,dd-MM-yyyy HH:mm:ss}" sortProperty="id.fechaCambio"/>
				
			<display:column property="usuario.apellidosNombre" title="Usuario" sortable="true"  headerClass="sortable" 
				defaultorder="descending" style="width:5%"/>
		</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionMandatosDiv").displayTagAjax();
		});
	</script>	