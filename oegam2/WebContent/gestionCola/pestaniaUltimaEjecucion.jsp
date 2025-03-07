<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	<table class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td>Datos de las Últimas Ejecuciones por Cola</td>
		</tr>
	</table>
	&nbsp;
		<script type="text/javascript">
		$(function() {
			$("#displayTagDivUltEjecucionesCola").displayTagAjax();
		})
	</script>
	<div id="displayTagDivUltEjecucionesCola" class="divScroll">
		<s:if test="%{consultaGestionCola.gestionCola.listaUltimaEjecucion.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right">
									<label for="labelResultadosPorPaginaUltimaEjecuion">&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPaginaUltimaEjecucion"
												name= "consultaGestionCola.gestionCola.resultadosPorPaginaUltimaEjecucion"
												value="consultaGestionCola.gestionCola.resultadosPorPaginaUltimaEjecucion"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaUltimaEjecucion();" >
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<s:if test="%{consultaGestionCola.gestionCola.errorUltEjecCola}">
			<%@include file="../../includes/erroresMasMensajes.jspf"%>
		</s:if>
		<display:table name="consultaGestionCola.gestionCola.listaUltimaEjecucion" 
			requestURI="navegarUltimaEjecGCAjax.action?numPorPage=${consultaGestionCola.gestionCola.resultadosPorPaginaUltimaEjecucion}#UltimaEjecucion" 
			excludedParams="*"
			class="tablacoin" summary="Listado de las Ultimas Ejecuciones" 
			cellspacing="0" cellpadding="0" uid="tablaUltimaEjecucionCola">
	  
	  		<display:column property="proceso" title="Proceso" headerClass="centro"	style="width:2%;"/>
	  
			<display:column property="cola" title="Cola" headerClass="centro"  style="width:5%;"/>
		
			<display:column property="numExpediente" title="Ultimo Id Tramite OK" headerClass="centro" style="width:5%;"/>
	 
			<display:column property="fechaEnvio" title="Fecha/Hora" headerClass="centro" style="width:5%;" 
				format="{0,date,dd/MM/yyyy HH:mm:ss}"/>
				
		</display:table>
	</div>
	&nbsp;