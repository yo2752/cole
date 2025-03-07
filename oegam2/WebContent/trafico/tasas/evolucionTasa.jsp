<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/gestionTasa.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script type="text/javascript"></script>


<div class="popup formularios" style="width:95%;">
	<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
		<table width="100%" >
			<tr>
				<td class="tabular" align="left">
					<span class="titulo">Evolucion de la Tasa <s:property value="codigoTasa"/></span>
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
											id="idResultadosPorPaginaEvolConsTasa"
											name= "resultadosPorPagina"
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaEvolucionConsultaTasas();">
								</s:select>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<div id="displayTagEvolucionConsultaTasaDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../../../includes/bloqueLoading.jspf"%>
		</div>
	<display:table name="lista" excludedParams="*"
					class="tablacoin"
					id="tablaEstadosTasa"
					uid="tablaEstadosTasa"
					requestURI= "navegarEvolucionTs.action"
					summary="Listado de Estados de una Tasa"
					cellspacing="0" cellpadding="0">	
		<display:column property="fechaHora" title="Fecha Cambio" 
				sortable="true" headerClass="sortable" 
				defaultorder="descending" maxLength="22" 
				style="width:2%" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>
				
			<display:column property="usuario.apellidosNombre" title="Usuario" 
				sortable="true"  headerClass="sortable" 
				defaultorder="descending" style="width:5%"/>
		<display:column property="numExpediente" title="Núm. Expediente" sortable="true" sortProperty="tramiteTrafico.numExpediente" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
		<display:column property="accion" title="Acción"
				sortable="true" headerClass="sortable"
				sortProperty="accion"
				defaultorder="descending"
				style="width:5%;"  />

 	<input type="hidden" name="resultadosPorPagina"/>
	</display:table>
	</div>
</div>
	<script type="text/javascript">
		$(function() {
			$("#displayTagEvolucionConsultaTasaDiv").displayTagAjax();
		});
	</script>	