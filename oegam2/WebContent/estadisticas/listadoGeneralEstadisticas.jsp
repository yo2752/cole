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
<script src="js/trafico/matriculacion.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

<c:set var="myTest" value="${consultaEstadisticas.textoAgrupacion}"/>
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">ESTADISTICAS: Listado General y Personalizado</span>
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
		        	 value="" maxlength="40" size="20" 
		        	 name="consultaEstadisticas.password">		
		        </td>						
				<td align="left" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="boton" 
						name="bLimpiar" 
						id="bLimpiar" 
						value="Verificar Contraseña"  
						onkeypress="this.onClick" 
						onclick="return comprobarPasswordListadoGeneral();"/>			
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
			    	<tr></tr> 
			        <tr></tr>
			        <tr></tr>
		        	<tr>
		        		<td align="left" nowrap="nowrap">
	        				<label>Fecha de Presentaci&oacute;n: </label>
	       				</td>
	
						<td align="left">
							<TABLE WIDTH="100%" border="0">
								<tr>
									<td align="right">
										<label for="diaMatrIni">desde: </label>
									</td>
		
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.diaInicio" 
											id="diaMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.mesInicio" 
											id="mesMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td>/</td>	
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.anioInicio" 
											id="anioMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="5" maxlength="4"/>
									</td>	
									<td>
					    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrIni, document.formData.mesMatrIni, document.formData.diaMatrIni);return false;" 
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
							<TABLE WIDTH="100%" border="0">
								<tr>
									<td align="left">
										<label for="diaMatrFin">hasta:</label>
									</td>
							
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.diaFin" 
											id="diaMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td>/</td>						
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.mesFin" 
											id="mesMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td>/</td>								
									<td>
										<s:textfield name="consultaEstadisticas.fechaMatriculacion.anioFin" 
											id="anioMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="5" maxlength="4" />
									</td>								
									<td>
							    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrFin, document.formData.mesMatrFin, document.formData.diaMatrFin);return false;" 
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
			
				<table class="tablaformbasica" border="0">
				<tr> 
	       			<td align="left" nowrap="nowrap">
						<label for="idNumcolegiado">Num Colegiado: </label> 
	       			</td>     
	       			<td align="left" nowrap="nowrap">						
						<s:textfield name="consultaEstadisticas.numColegiado" 
	        				id="idNumcolegiado" 
	        				onblur="this.className='input2';" 
	        				onfocus="this.className='inputfocus';" 
	        				size="5" maxlength="4"/>
					</td>		
					
					<td align="left" nowrap="nowrap">
						<label for="idTipoCreacion">Tipo Creacion: </label> 
	       			</td>     
	       			<td align="left" nowrap="nowrap">						
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoCreaciones()"	 
							name="consultaEstadisticas.idTipoCreacion" 
	        				id="idTipoCreacion" 
	        				headerKey="-1"
		          			headerValue="Cualquier Tipo"
	        				listKey="idTipoCreacion" listValue="descripcionCreacion"/>
					</td>       			
				 </tr>	
	 			 <tr>    
			       <td align="left" nowrap="nowrap">   				
						<label for="idProvincia">Provincia:</label>
					</td>				        		
	       	       			
	       			<td align="left" nowrap="nowrap">
						<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey="-1"
			           		headerValue="Cualquier Provincia"
							name="consultaEstadisticas.provincia.idProvincia" 
							listKey="idProvincia" 
							listValue="nombre"
							id="idProvincia"
							onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';"
							onchange="cargarListaJefaturas(this,'idJefatura','jefaturaSeleccionado');"/>	
					</td>
	
					<td align="left" nowrap="nowrap">
						<label for="idJefatura">Jefatura Provincial:</label>
					</td>
					<td>
						<select id="idJefatura" 
							onchange="seleccionarCampo('jefaturaSeleccionado','idJefatura');" >
							<option value="-1">Cualquier Jefatura</option>
						</select>
						<s:hidden id="jefaturaSeleccionado" name="consultaEstadisticas.jefatura.jefaturaProvincial"/>	
					</td>
		       	</tr>		  
				<tr>
	      			<td align="left" nowrap="nowrap">
						<label for="idEstadoTramite">Estado del trámite: </label>
					</td>			    				    				
	
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
	           				headerValue="Cualquier Estado"
							name="consultaEstadisticas.estadoMultiple" 
							listKey="valorEnum" listValue="nombreEnum"
							multiple="true"
							id="idEstadoTramite"/>
	
					</td>	        		
	    			        	      
	    			<td align="left" nowrap="nowrap">
	       				<label for="idTipoTramite">Tipo Tramite: </label>
	      				</td>
	   
	      				<td align="left">
	   					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						headerKey="-1"
	          				headerValue="Cualquier Tipo"
						name="consultaEstadisticas.tipoTramite" 
						listKey="valorEnum" listValue="nombreEnum"
						id="idTipoTramite"/> 
	      				</td>       	       											        	
			    </tr>
	       		<tr></tr> 
			    <tr></tr> 
			    <tr></tr>
			    <tr></tr>
			    <tr>	
		        	<td align="left" nowrap="nowrap">
	       				<label for="idTipoVehiculo">Tipo Vehiculo: </label>
	      				</td>    
	      					        		
				    <td align="left" nowrap="nowrap">
					   <s:select id="idTipoVehiculo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('X')"
						headerKey="-1"
				   		headerValue="Cualquier Tipo"
						name="consultaEstadisticas.tipoVehiculoBean.tipoVehiculo" 
						listKey="tipoVehiculo" 
						listValue="descripcion"/>	
				     </td>
				     
					 <td align="left" nowrap="nowrap">
						<label for="codigoMarca">Marca:</label>
					 </td>    
	       	
					<td align="left" nowrap="nowrap">
						<s:textfield id="codigoMarca" 
		      				name="codigoMarca" 
		      				onblur="this.className='input2'; validarMarca(this, document.getElementById('idMarcaVehiculo'), marcaVehiculoMatriculacionEst, document.getElementById('checkCodigoMarca'), 'MATE');" 
		      				onfocus="this.className='inputfocus';"
		      				size="23"
		      				maxlength="22"
		      				autocomplete="off"
		      				cssStyle="position:relative; float:left;" />
		      			&nbsp;	
		      			<span id="checkCodigoMarca"></span>
		      			
		      			<s:hidden id="idMarcaVehiculo" name="consultaEstadisticas.marcaBean.codigoMarca" />
					</td>
				</tr>	
				<tr>
	      			<td align="left" nowrap="nowrap">
						<label for="idAgrupacion">Agrupacion: </label>
					</td>			    				    				
	
					<td align="left" colspan="3">
						<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboAgrupacion()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
	           				headerValue="Sin Agrupacion"
							name="consultaEstadisticas.agrupacion" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idAgrupacion"/>
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
							onclick="return generarInformeGeneralEstadisticas();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarFormGeneralEstadisticas();"/>			
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
										onchange="return cambiarElementosPorPaginaConsultageneralEstadisticas();" 
										/>
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
		</div>
	
		<div class="divScroll">
			<display:table name="listaConsultaEstadisticasAgrupadas" excludedParams="*"
							requestURI="navegarGeneralEstadisticas.action"
							class="tablacoin"
							uid="tablaConsultaGeneralEstadistica"
							summary="Listado de estadisticas de tramite"
							cellspacing="0" cellpadding="0"
							pagesize="${resultadosPorPagina}"
							partialList="false" size="5"
							>		
		
			 	<display:setProperty name="paging.banner.all_items_found">
			 		<table class="totalresults" summary="Numero total y paginacion" >
			 			<tr>
			 				<td align="left">
			 					<strong>N&ordm; de agrupados:&nbsp;</strong>${totalList}
			 				</td>
			 			</tr>
			 			<tr>
							<td align="left">
			 					<strong>Total de registros:&nbsp;</strong>${totalRows}
			 				</td>
			 			</tr>
			 		</table>
			 	</display:setProperty>
			 	
			 	<display:setProperty name="paging.banner.some_items_found">
			 		<table class="totalresults" summary="Numero total y paginacion" >
			 			<tr>
			 				<td align="left">
			 					<strong>N&ordm; de agrupados:&nbsp;</strong>${totalList}
			 				</td>
			 			</tr>
			 			<tr>
							<td align="left">
			 					<strong>Total de registros:&nbsp;</strong>${totalRows}
			 				</td>
			 			</tr>
			 		</table>
			 	</display:setProperty>
			 	
			 	<display:setProperty name="paging.banner.one_item_found">
			 		<table class="totalresults" summary="Numero total y paginacion" >
			 			<tr>
			 				<td align="left">
			 					<strong>N&ordm; de agrupados:&nbsp;</strong>${totalList}
			 				</td>
			 			</tr>
			 			<tr>
							<td align="left">
			 					<strong>Total de registros:&nbsp;</strong>${totalRows}
			 				</td>
			 			</tr>
			 		</table>
			 	</display:setProperty>
			 	
				<display:column property="campo" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:8%" title="${myTest}"/>
						
				<display:column property="numRegistros" title="Num Registros"
						sortable="true" headerClass="sortable"
						defaultorder="descending"
						style="width:6%" />
							
				<input type="hidden" name="resultadosPorPagina"/>
			</display:table>	
		</div>
		
		<s:if test="%{listaConsultaEstadisticasAgrupadas.size() > 0}">
			<table class="acciones">
				<tr>
					<td>
						<div>
							<input type="button" class="boton" name="bExcel" id="bExcel" value="Consultar Excel" onClick="return imprimirEstadisticas();" onKeyPress="this.onClick" />
						</div>			
					</td>
				</tr>
			</table>
		</s:if>
	</s:form>
</s:if>

<script>
	var marcaVehiculoMatriculacionEst = new BasicContentAssist(document.getElementById('codigoMarca'), [], null, true);
	recargarComboJefaturas('idProvincia','idJefatura','jefaturaSeleccionado');
	cargarListaMarcasVehiculoEst(marcaVehiculoMatriculacionEst);	
</script>

<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoGeneral();
	    }
	});
});
</script>