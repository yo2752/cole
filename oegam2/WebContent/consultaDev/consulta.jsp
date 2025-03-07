<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/consultaDev/consultaDev.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Dev</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCif">CIF:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaDevFilter.cif" id="idCif" size="15" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Respuesta DEV:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getEstadoCif()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Respuesta DEV" 
				    		name="consultaDevFilter.estadoCif" listKey="valorEnum" listValue="nombreEnum" id="idEstadoCif"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getEstadoConsulta()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
				    		name="consultaDevFilter.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContrato"
								list="@org.gestoresmadrid.oegam2comun.consultaDev.utiles.UtilesConsultaDev@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="consultaDevFilter.idContrato"></s:select>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="consultaDevFilter.idContrato"/>
					</s:else>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha Consulta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.diaInicio" id="diaFechaAltaIniDev"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.mesInicio" id="mesFechaAltaIniDev" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.anioInicio" id="anioFechaAltaIniDev"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIniDev, document.formData.mesFechaAltaIniDev, document.formData.diaFechaAltaIniDev);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha Consulta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.diaFin" id="diaFechaAltaFinDev" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.mesFin" id="mesFechaAltaFinDev" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDevFilter.fechaAlta.anioFin" id="anioFechaAltaFinDev"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFinDev, document.formData.mesFechaAltaFinDev, document.formData.diaFechaAltaFinDev);return false;" 
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
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return buscarConsultaDev();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsulta();" value="Limpiar"/>		
		</div>
		<br/>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaDev" style="text-align: center;">
				<%@include file="resumenConsultaDev.jspf" %>
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
												id="idResultadosPorPaginaConsultaDev"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaDev();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaDev" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaDev" requestURI= "navegarConsultaDEV.action"
					id="tablaConsultaDev" summary="Listado de Consultas Dev"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	

				<display:column property="idConsultaDev" title="" media="none" paramId="idConsultaDev"/>
				
				<display:column property="cif" title="CIF" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:2%"/>	
				
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="numColegiado" title="Num.Colegiado" sortable="true" headerClass="sortable" 
						sortProperty="contrato.colegiado.numColegiado" defaultorder="descending" style="width:1%"/>
					
					<display:column property="descContrato" title="Contrato" sortable="true" headerClass="sortable " 
						sortProperty="contrato.via" defaultorder="descending" style="width:6%"/>
				</s:if>
				
				<display:column property="fechaAlta" title="Fecha Consulta"
						sortable="true" headerClass="sortable" defaultorder="descending" style="width:3%"
						format="{0,date,dd/MM/yyyy}" />
				
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
				
				<display:column property="estadoCif" title="Respuesta DEV" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
						
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaDev(this)' 
					onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucion(<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>);" title="Ver evolución"/>
				  			</td>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>" 
									value='<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>' />
								<input type="hidden" id="idCheckConsultaDev<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>" value="<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>">
							</td>
							<td style="border: 0px;">
				  				<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirDetalle('<s:property value="#attr.tablaConsultaDev.idConsultaDev"/>');" title="Ver Detalle"/>
				  			</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaDev" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bCambiarEstado" id="idCambiarEstado" value="Cambiar Estado" onClick="javascript:cambiarEstadoBloque();"
			 			onKeyPress="this.onClick"/>
			 	</s:if>
				<input type="button" class="boton" name="bConsultar" id="idConsultar" value="Consultar" onClick="javascript:consultarDevBloque();"
					onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bEliminar" id="idEliminar" value="Eliminar" onClick="javascript:eliminar();"
					onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bExportar" id="idExportar" value="Exportar Excel" onClick="javascript:exportar();"
					onKeyPress="this.onClick"/>		
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaDev" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaDevEvolucion" style="display: none; background: #f4f4f4;"></div>
</div>
