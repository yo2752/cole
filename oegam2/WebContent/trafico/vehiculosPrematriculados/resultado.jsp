<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/vehiculosPrematriculados.js" type="text/javascript"></script>


<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de resultados de Vehículos Prematriculados</span>
				</td>
			</tr>
		</table>
	</div>	
	
	
	
	<div class="contentTabs" id="consultas"  style="width:100%;">	
	<s:form method="post" id="formData" name="formData" action="buscarConsultaTarjetaResultados.action">
	<%@include file="../../includes/erroresYMensajes.jspf" %>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		<tr>
			<td>
				<label for="nive">Nive: </label>
			</td>
			<td>
				<s:textfield title="NIVE" name="consultaTarjetaBean.nive" id="nive" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="40" maxlength="32"></s:textfield>
			</td>
			<td>
				<label for="bastidor">Bastidor: </label>
			</td>
			<td>
				<s:textfield title="bastidor" name="consultaTarjetaBean.bastidor" id="bastidor" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" 
									size="40" maxlength="21"></s:textfield>
			</td>
		</tr>
		<tr>
			<td>
				Estado Caracteristicas:
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.core.trafico.prematriculados.model.enumerados.EstadoConsultaVehiculoPrematiculado@getEstados()"  
				headerValue="Cualquier Tipo" headerKey="" name="consultaTarjetaBean.estadoCaracteristicas" 
				id="estadoCaracteristicas" listKey="valorEnum" listValue="nombreEnum"/>
			</td>
			<td>
				Estado Ficha Técnica:
			</td>
			<td>
				<s:select list="@org.gestoresmadrid.core.trafico.prematriculados.model.enumerados.EstadoConsultaVehiculoPrematiculado@getEstados()"  
				headerValue="Cualquier Tipo" headerKey="" name="consultaTarjetaBean.estadoFichaTecnica" 
				id="estadoFichaTecnica" listKey="valorEnum" listValue="nombreEnum"/>
			</td>
		</tr>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<tr>
				<td>
					<label for="numColegiado">Num Colegiado: </label>
				</td>
				<td>
					<s:textfield title="numColegiado" name="consultaTarjetaBean.numColegiado" id="numColegiado" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="4" maxlength="5"></s:textfield>
				</td>
			</tr>
		</s:if>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap" width="17%">
   					<label>Fecha de búsqueda:</label>
   				</td>

				<td align="left" nowrap="nowrap" width="5%">
					<label for="desde">desde: </label>
				</td>
				
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.diaInicio" 
						id="diaBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						
						size="2" maxlength="2" />
				</td>

				<td width="1%">/</td>

				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.mesInicio" 
						id="mesBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 						
						size="2" maxlength="2" />
				</td>

				<td width="1%">/</td>
		
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.anioInicio" 
						id="anioBusquedaIni"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						size="5" maxlength="4" />
				</td>
			
				<td align="left" nowrap="nowrap">
    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaIni, document.formData.mesBusquedaIni, document.formData.diaBusquedaIni);return false;" 
    					title="Seleccionar fecha">
    					<img class="PopcalTrigger" 
    						align="left" 
    						src="img/ico_calendario.gif" 
    						width="15" height="16" 
    						border="0" alt="Calendario"/>
    				</a>
				</td>

				<td align="left" align="left" width="5%">
					<label for="diaPresentacionFin">hasta:</label>
				</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.diaFin" 
						id="diaBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						size="2" maxlength="2" />
				</td>
					
				<td width="1%">/</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.mesFin" 
						id="mesBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						size="2" maxlength="2" />
				</td>
					
				<td width="1%">/</td>
					
				<td align="left" nowrap="nowrap" width="5%">
					<s:textfield name="consultaTarjetaBean.fecha.anioFin" 
						id="anioBusquedaFin"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						size="5" maxlength="4" />
				</td>
					
				<td align="left" nowrap="nowrap">
		   			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioBusquedaFin, document.formData.mesBusquedaFin, document.formData.diaBusquedaFin);return false;" 
		   				title="Seleccionar fecha">
		   				<img class="PopcalTrigger" 
		   					align="left" 
		   					src="img/ico_calendario.gif" 
		   					width="15" height="16" 
		   					border="0" alt="Calendario"/>
   			    	</a>
				</td>
			</tr>
		</table>
		<iframe width="174" 
			height="189" 
			name="gToday:normal:agenda.js" 
			id="gToday:normal:agenda.js" 
			src="calendario/ipopeng.htm" 
			scrolling="no" 
			frameborder="0" 
			style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
		</iframe>
		<table align="center">
			<tr>
				<td>
					<input type="submit" class="boton" value="Buscar"/>
					<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar" onkeypress="this.onClick" onclick="return limpiarFormConsultaTarjetaResultado();"/>
				</td>
			</tr>
		</table>
	</s:form>
	<s:form method="post" id="formData1" action="descargarConsultaTarjetaResultados.action">
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
											onchange="cambiarElementosPorPaginaConsulta('navegarConsultaTarjetaResultados', 'displayTagDiv', this.value);" 
											/>
										</td>
									</tr>
								</table>
							</td>
						</tr>
				</table>
		</s:if>
		
	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		});
	</script>
	<div id="displayTagDiv" class="divScroll">
		<div
			style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
			<display:table name="lista" 
				class="tablacoin"
				uid="tablaConsulta"
				requestURI= "navegar${action}"
				id="tablaConsulta"
				summary="Listado de Consultas"
				excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">		
			
				 <display:column property="id" title="Id"
					sortable="true"		
					headerClass="sortable"
					defaultorder="descending"
					style="width:4%"
				/> 
				
				<display:column property="nive" title="Nive"
					sortable="true" 				
					headerClass="sortable"
					defaultorder="descending"
					style="width:4%" 					
				/>
				
				<display:column property="bastidor" title="Bastidor"
					sortable="true" 				
					headerClass="sortable"
					defaultorder="descending"
					style="width:4%" 					
				/>
				
				<display:column property="estadoCaracteristicas" title="Estado características técnicas" 
				 	sortable="true" 
				 	headerClass="sortable"
				 	defaultorder="descending"
				 />

				<display:column property="respuestaCaracteristicas" title="Respuesta características técnicas"
					sortable="true" 
					headerClass="sortable"
					defaultorder="descending"
				/>
				
				<display:column property="estadoFichaTecnica" title="Estado Ficha técnica" 
				 	sortable="true" 
				 	headerClass="sortable"
				 	defaultorder="descending"
				 />

				<display:column property="respuestaFichaTecnica" title="Respuesta Ficha técnica"
					sortable="true" 
					headerClass="sortable"
					defaultorder="descending"
				/>
				
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="numColegiado" title="Num. Colegiado"
						sortable="true" 				
						headerClass="sortable"
						defaultorder="descending"			
					/>
				</s:if>
				
				<display:column property="fechaAlta" title="Fecha Alta"
					sortable="true" 				
					headerClass="sortable"
					defaultorder="descending"
					style="width:4%" 					
				/>
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosVehiculosPrematriculados(this, \"listaVehiculos\");' onKeyPress='this.onClick'/>">
					<input type="checkbox" name="listaVehiculos" id="listaVehiculos" value="<s:property value="#attr.tablaConsulta.id"/>"  />
				</display:column>
				
			</display:table>
		</div>

	<s:if test="%{lista.getFullListSize()>0}">
		<table align="center">
			<tr align="center">
				<td>
					<s:submit value="Descargar"/>
				</td>
			</tr>
		</table>
	</s:if>
</s:form> 
</div>

