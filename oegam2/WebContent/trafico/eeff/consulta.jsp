<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/eeff/eeff.js" type="text/javascript"></script>

<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<script type="text/javascript">
	$(function() {
			$("#displayTagDiv").displayTagAjax();
	})
</script>
<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta de Entidades Financieras</span>
			</td>
		</tr>
	</table>
</div>
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label for="labelConsultaNive">NIVE: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFFFilterBean.tarjetaNive" id="tarjetaNive" style="text-transform : uppercase"
	          				onblur="this.className='input';" onfocus="this.className='inputfocus';" size="32" maxlength="32"></s:textfield>
					</td>
					<td align="left">
						<label for="labelConsultaBastidor">NºBastidor: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFFFilterBean.tarjetaBastidor" id="tarjetaBastidor" style="text-transform : uppercase"
	          				onblur="this.className='input';" onfocus="this.className='inputfocus';" size="21" maxlength="21"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap" style="width:10%;">
						<label for="labelConsultaExpediente">NºExpediente: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFFFilterBean.numExpediente" id="numExpediente" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" size="32" maxlength="32"></s:textfield>
					</td>
					<td align="left">
						<label for="labelConsultaExpedienteTramite">NºExpediente Trámite: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFFFilterBean.numExpedienteTramite" id="numExpedienteTramite"
	          				onblur="this.className='input';" onfocus="this.className='inputfocus';" size="21" maxlength="21"></s:textfield>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelConsultaCIFConcesionario">CIF Concesionario: </label>
					</td>
					<td align="left">
						<s:textfield name="consultaEEFFFilterBean.nifRepresentado" id="cifConcesionario" style="text-transform : uppercase"
	          				onblur="this.className='input';" onfocus="this.className='inputfocus';" size="32" maxlength="32"></s:textfield>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaEEFF@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="consultaEEFFFilterBean.idContrato"></s:select>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="consultaDevFilter.idContrato"/>
					</s:else>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Realización:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.diaInicio" id="diaBusquedaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.mesInicio" id="mesBusquedaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.anioInicio" id="anioBusquedaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaIni, document.formData.mesBusquedaIni, document.formData.diaBusquedaIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Realización:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.diaFin" id="diaBusquedaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.mesFin" id="mesBusquedaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaBusqueda.anioFin" id="anioBusquedaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaFin, document.formData.mesBusquedaFin, document.formData.diaBusquedaFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.diaInicio" id="diaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.mesInicio" id="mesAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.anioInicio" id="anioAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.diaFin" id="diaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.mesFin" id="mesAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaEEFFFilterBean.fechaAlta.anioFin" id="anioAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscar" id="bBuscarConsulta" value="Buscar"onkeypress="this.onClick"onclick="return buscarConsultaEEFF();"/>
			<input type="button" class="boton" name="bLimpiar" onclick="limpiarConsultaBuscarEEFF()" value="Limpiar"  />		
		</div>
		<br/>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaEEFF" style="text-align: center;">
				<%@include file="resumenConsultaEEFF.jspf" %>
			</div>
			<br><br>
		</s:if>
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
												id="idResultadosPorPagina"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsulta('navegarConsultaEEFF', 'displayTagDiv', this.value);">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaEEFF" requestURI= "navegar${action}"
					id="tablaConsultaEEFF" summary="Listado de Consultas"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0">		
				
					<display:column property="numExpediente" title="Expediente"	sortable="true" headerClass="sortable" 
						defaultorder="descending" style="width:5%"  href="altaConsultaEEFF.action" paramId="numExpediente"/>
					
					<display:column property="fechaRealizacion" title="Fecha Realizacion"
							sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%"
							format="{0,date,dd/MM/yyyy}" />
							
					<display:column property="fechaAlta" title="Fecha Alta"
							sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%"
							format="{0,date,dd/MM/yyyy}" />
					
					<display:column property="numExpedienteTramite" title="Expediente Tramite"	sortable="true" headerClass="sortable" 
						defaultorder="descending" style="width:5%" />	
							
					<display:column property="tarjetaBastidor" title="Nº Bastidor" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:8%" />
					
					<display:column property="tarjetaNive" title="Nive" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:8%"/>
					
					<display:column property="descEstado" sortProperty="estado" title="Estado" sortable="true" 
						headerClass="sortable" style="width:2%"/>
						
					<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaEEFF(this)' 
						onKeyPress='this.onClick'/>" style="width:1%" >
						<table align="center">
							<tr>
					  			<td style="border: 0px;">
									<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaEEFF.numExpediente"/>" 
										value='<s:property value="#attr.tablaConsultaEEFF.numExpediente"/>' />
									<input type="hidden" id="idCheckConsultaEEFF<s:property value="#attr.tablaConsultaEEFF.numExpediente"/>" value="<s:property value="#attr.tablaConsultaEEFF.numExpediente"/>">
								</td>
							</tr>
						</table>
					</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaEEFF" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bCambiarEstadoConsultaEEFF" id="idCambiarEstadoConsultaEEFF" value="Cambiar Estado" onClick="javascript:cambiarEstadoBloqueConsultaEEFF();"
			 			onKeyPress="this.onClick"/>
			 	</s:if>
				<input type="button" class="boton" name="bConsultarEEFF" id="idConsultarEEFF" value="Consultar" onClick="javascript:consultarConsultaEEFFBloque();"
					onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bEliminarEEFF" id="idEliminarEEFF" value="Eliminar" onClick="javascript:eliminarConsultaEEFF();"
					onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form> 
	<div id="divEmergenteConsultaEEFF" style="display: none; background: #f4f4f4;"></div>
</div>