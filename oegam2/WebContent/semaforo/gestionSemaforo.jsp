<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/semaforo/gestionSemaforos.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Semáforos</span>
			</td>
		</tr>
	</table>
</div>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="idProceso">Proceso:</label>
					</td>
					<td  align="left">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getListaProcesos()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Proceso" 
					    		name="semaforoFilterBean.proceso" listKey="valorEnum" listValue="nombreEnum" id="idProceso"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="idEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosSemaf()" 
								onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
					    		name="semaforoFilterBean.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
				</tr>
			</table>
			<s:hidden name="semaforoFilterBean.nodo"/>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscarSemaforo" id="idBuscarSemaforo" value="Buscar"  onkeypress="this.onClick" onclick="return buscarSemaforos();"/>			
			<input type="button" class="boton" name="bLimpiarSemaforo" id="idLimpiarSemaforo" onclick="javascript:limpiarSemaforos();" value="Limpiar"/>		
		</div>
		<br/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
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
												id="idResultadosPorPaginaSemaforos"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaSemaforos();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaSemaforo" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaSemaforos" requestURI="navegarGestionSemaforos.action"
					id="tablaConsultaSemaforos" summary="Listado de Semaforos"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0">	

				<display:column property="idSemaforo" title="Id. Semáforo" paramId="idSemaforo" style="width:2%"/>
						
				<display:column property="proceso" title="Descripción Proceso" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:7%"/>	
				
				<display:column title="Estado" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:4%" >
					<s:property value="%{@org.gestoresmadrid.core.semaforo.model.enumerados.EstadoSemaforo@convertirTexto(#attr.tablaConsultaSemaforos.estado)}" />
				</display:column>	
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaSemaforos(this)' 
					onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecksSemaforos" id="check<s:property value="#attr.tablaConsultaSemaforos.idSemaforo"/>" 
									value='<s:property value="#attr.tablaConsultaSemaforos.idSemaforo"/>' />
							</td>
							<td style="border: 0px;">
		  						<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  							onclick="consultaEvSemaforo('<s:property value="#attr.tablaConsultaSemaforos.idSemaforo"/>');" title="Ver evolución"/>
				  			</td>
						</tr>
					</table>
					<s:if test="#attr.tablaConsultaSemaforos.estado == 0">	
						<s:set var="valorAnulado" value="1"/>
					</s:if>
					<s:else>
						<s:set var="valorAnulado" value="0"/>
					</s:else>
					<input type="hidden" name="estaAnulado<s:property value="#attr.tablaConsultaSemaforos.idSemaforo"/>" id="estaAnulado<s:property value="#attr.tablaConsultaSemaforos.idSemaforo"/>" value="<s:property value="#valorAnulado"/>"/>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaSemaforo" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bCambiarEstado" id="idCambiarEstado" value="Cambiar Estado" onClick="javascript:abrirVentanaSeleccionEstados();"
					onKeyPress="this.onClick"/>
				<input type="button" class="botonMasGrande" name="bDeshabHab" id="idDeshabHab" value="Habilitar / Deshabilitar" onClick="javascript:desabHabSemaforos();"
					onKeyPress="this.onClick"/>
			</div>
		</s:if>
		<s:hidden id="estadoSemaforoSel" name="estadoSemaforoSel"/>
		<s:hidden id="semaforosChequeados" name="semaforosChequeados"/>
	</s:form>
</div>
