<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/estadisticas/estadisticas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

<c:set var="myTest" value="${consultaEstadisticas.textoAgrupacion}"/>
<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">ESTADISTICAS: Listado Vehiculos</span>
			</td>
		</tr>
	</table>
</div>

<s:if test="%{passValidado=='false' or passValidado=='error'}">
	<s:form method="post" id="formData" name="formData">	
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap">
					<label for="idPassword">Introduzca la clave:</label> 
				</td>
				<td align="center" nowrap="nowrap">					
		        	<input type="password" autocomplete="off" onblur="this.className='input2';"
		        	 onfocus="this.className='inputfocus';" id="idPassword" 
		        	 maxlength="40" size="20" 
		        	 name="consultaEstadisticas.password">		
		        </td>						
				<td align="left" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="boton" 
						name="bVerificar" 
						id="bVerificar" 
						value="Verificar Contraseña"  
						onclick="return comprobarPasswordListadoVehiculosEstadisticas();"/>			
				</td>
			</tr>
		</table>
	</s:form>	
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</s:if>

<s:if test="%{passValidado=='true'}">
		<s:form method="post" id="formData" name="formData">
			<div id="busqueda">
				<table class="tablaformbasica" border="0">
					<tr> 
		       			<td align="left" nowrap="nowrap" width="10%">
							<label for="idNumcolegiado">Num Colegiado: </label> 
		       			</td>     
		       			<td align="left" nowrap="nowrap">						
							<s:textfield name="consultaEstadisticas.numColegiado" 
		        				id="idNumcolegiado" onblur="this.className='input2';" 
		        				onfocus="this.className='inputfocus';" 
		        				size="5" maxlength="4"/>
						</td>
						
						<td align="right" nowrap="nowrap">&nbsp;</td>
		    			
		    			
		    			<td align="left" nowrap="nowrap">
							<label for="idAgrupacion">Agrupacion: </label>
						</td>			    				    				
		
						<td align="left" colspan="3">
							<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboAgrupacionVehiculos()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								headerKey="-1"
		           				headerValue="Sin agrupar"
								name="consultaEstadisticas.agrupacionVehiculos" 
								listKey="valorEnum" listValue="nombreEnum"
								id="idAgrupacion"/>
						</td>	    			       	       											        	
				    </tr>	
		        	
		        	<tr>
			        	<td align="left" nowrap="nowrap">
							<label for="idEstadoTramite">Estado del trámite: </label>
						</td>			    				    				
		
						<td align="left" nowrap="nowrap" colspan="2">
							<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboEstadosFinal()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								headerKey="-1"
		           				headerValue="Estados Finalizados"
								name="consultaEstadisticas.estado" 
								listKey="valorEnum" listValue="nombreEnum"
								id="idEstadoTramite"/>
		
						</td>
						<td align="left" nowrap="nowrap">
		       				<label for="idTipoTramite">Tipo Tramite: </label>
		      			</td>
		   
		   			<!-- getComboTipoTramite y getComboTipoTramiteTraficoTransmision  -->
		      			<td align="left">
		   					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramiteTraficoTransmision()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
		          			headerValue="Ambos Tipos de Transmision"
							name="consultaEstadisticas.tipoTramite" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoTramite"/> 
		      			</td>			
					</tr>					 
		        		
		        	<tr>
		        		<td align="left" nowrap="nowrap">
	        				<label>Fecha de Presentacion: </label>
	       				</td>
	       				
	       				<td align="left">
							<label for="diaMatrIni">desde: </label>
						</td>
	
						<td align="left">
							<TABLE WIDTH="100%" border="0">
								<tr>								
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.diaInicio" 
											id="diaPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.mesInicio" 
											id="mesPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.anioInicio" 
											id="anioPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="5" maxlength="4"/>
									</td>	
									<td>
					    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPriMatrIni, document.formData.mesPriMatrIni, document.formData.diaPriMatrIni);return false;" 
					    					title="Seleccionar fecha">
						    				<img class="PopcalTrigger" 
						    					align="left" 
						    					src="img/ico_calendario.gif" 
						    					width="15" height="16" 
						    					border="0" alt="Calendario"/>
					    				</a>
									</td>	
									<td width="2%"></td>
								</tr>
							</TABLE>
						</td>
						
						<td align="left">
							<label for="diaMatrFin">hasta:</label>
						</td>
									
						<td align="left">
							<TABLE WIDTH="100%" border="0">
								<tr>
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.diaFin" 
											id="diaPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td>/</td>						
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.mesFin" 
											id="mesPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td>/</td>								
									<td>
										<s:textfield name="consultaEstadisticas.fechaPresentacion.anioFin" 
											id="anioPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="5" maxlength="4" />
									</td>								
									<td>
							    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPriMatrFin, document.formData.mesPriMatrFin, document.formData.diaPriMatrFin);return false;" 
							    			title="Seleccionar fecha">
							    			<img class="PopcalTrigger" 
							    				align="left" 
							    				src="img/ico_calendario.gif" 
							    				width="15" height="16" 
							    				border="0" alt="Calendario"/>
					   			    	</a>
									</td>
								</tr>
							</TABLE>
						</td>
					</tr>
				</table>
			    					
			<table class="acciones" border="0">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bGenerarInforme" 
							id="bGenerarInforme" 
							value="Generar Informe"  
							onkeypress="this.onClick" 
							onclick="return generarInformeVehiculosEstadisticas();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarFormVehiculosEstadisticas();"/>								
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
	
		<%@include file="../../includes/erroresMasMensajes.jspf" %>
			<!--</div>		-->
			<c:if test="${not empty myTest}">
				<div id="resultado" style="width:100%;background-color:transparent;" >
					<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
						<tr>
							<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
						</tr>
					</table>
				
					<s:if test="%{listaConsultaEstadisticasAgrupadas.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="return cambiarElementosPorPaginaConsultaVehiculosEstadisticas();" 
												/>
											</td>
										</tr>
									</table>
								</td>
							</tr>
						</table>
					</s:if>				
				</div>
			</c:if>				
	
			<c:if test="${not empty myTest}">
				<div class="divScroll">
					<display:table name="listaConsultaEstadisticasAgrupadas" excludedParams="*"
									requestURI="navegarVehiculosEstadisticas.action"
									class="tablacoin"
									uid="tablaConsultaVehiculosEstadistica"
									summary="Listado de estadisticas de tramite"
									cellspacing="0" cellpadding="0"
									pagesize="${resultadosPorPagina}"
									partialList="false" size="5"
									>		
					
						<display:setProperty name="paging.banner.all_items_found">
						 		<table class="totalresults" summary="Numero total y paginacion" >
						 			<tr>
						 				<td align="left">
						 					<strong>N&ordm; de agrupados: </strong>${totalList}
						 				</td>
						 			</tr>
						 			<tr>
										<td align="left">
						 					<strong>Total de registros: </strong>${totalRows}
						 				</td>
						 			</tr>
						 		</table>
						 		
						 	</display:setProperty>
						 	
						 	<display:setProperty name="paging.banner.some_items_found">
						 		<table class="totalresults" summary="Numero total y paginacion" >
						 			<tr>
						 				<td align="left">
						 					<strong>N&ordm; de agrupados: </strong>${totalList}
						 				</td>
						 			</tr>
						 			<tr>
										<td align="left">
						 					<strong>Total de registros: </strong>${totalRows}
						 				</td>
						 			</tr>
						 		</table>					 		
						 	</display:setProperty>
						 	
						 	<display:setProperty name="paging.banner.one_item_found">
						 		<table class="totalresults" summary="Numero total y paginacion" >
						 			<tr>
						 				<td align="left">
						 					<strong>N&ordm; de agrupados: </strong>${totalList}
						 				</td>
						 			</tr>
						 			<tr>
										<td align="left">
						 					<strong>Total de registros: </strong>${totalRows}
						 				</td>
						 			</tr>
						 		</table>	
						 	</display:setProperty>			 
					 
					 	<display:column property="numRegistros" title="Num Registros"
								sortable="true" headerClass="sortable"
								defaultorder="descending"
								style="width:10%; text-align:center; " />
														
						<display:column property="campo" sortable="true" headerClass="sortable"
								defaultorder="descending" style="width:20%; text-align:left; padding-left:50px;" 
								title="${myTest}"/>
						
						<display:column property="fechaMatriculacion" title="Con Fecha 1º Matricula / Calculada 1º Matricula"
								sortable="true" headerClass="sortable"
								defaultorder="descending"
								style="width:25%; text-align:center; " />		
									
						<input type="hidden" name="resultadosPorPagina"/>					
				</display:table>
			</div>
		</c:if>		
	
		<s:if test="%{listaConsultaEstadisticasAgrupadas.size() > 0}">
			<table class="acciones">
				<tr>
					<td>
						<div>
							<input type="button" class="boton" name="bExcel" id="bExcel" value="Consultar Excel" onClick="return imprimirVehiculosEstadisticas();" onKeyPress="this.onClick" />
						</div>			
					</td>
				</tr>
			</table>
		</s:if>	
	</s:form>
</s:if>
<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoVehiculos();
	    }
	});
});
</script>