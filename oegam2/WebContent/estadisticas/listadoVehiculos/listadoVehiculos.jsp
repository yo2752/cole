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

<s:set var="textoAgrupacion" value="%{@estadisticas.utiles.enumerados.AgrupacionVehiculos@convertirTexto(listadoVehiculosBean.agrupacionVehiculos)}"></s:set>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">ESTADÍSTICAS: Listado Vehículos</span>
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
		        	 name="password">		
		        </td>						
				<td align="left" nowrap="nowrap" colspan="2">
					<input type="button" 
						class="botonMasGrande" 
						name="bVerificar" 
						id="bVerificar" 
						value="Verificar Contraseña"  
						onclick="return comprobarPasswordListadoVehiculos();"/>			
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
		       			<td align="left" nowrap="nowrap" width="10%" style="vertical-align: middle;">
							<label for="idNumcolegiado">Núm. Colegiado: </label> 
		       			</td>     
		       			<td align="left" nowrap="nowrap" style="vertical-align: middle;">						
							<s:textfield name="listadoVehiculosBean.numColegiado" 
		        				id="idNumColegiado" onblur="this.className='input2';" 
		        				onfocus="this.className='inputfocus';" 
		        				size="5" maxlength="4"/>
						</td>
						
						<td align="right" nowrap="nowrap">&nbsp;</td>
		    			
		    			
		    			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
							<label for="idAgrupacion">Agrupación:</label>
						</td>			    				    				
		
						<td align="left" style="vertical-align: middle;">
							<s:select list="@org.gestoresmadrid.oegam2comun.estadisticas.listados.utiles.UtilesVistaEstadisticas@getInstance().getComboAgrupacionVehiculos()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								headerKey=""
		           				headerValue="Sin agrupar"
								name="listadoVehiculosBean.agrupacionVehiculos" 
								listKey="valorEnum" listValue="nombreEnum"
								id="idAgrupacion"/>
						</td>		       	       											        	
				    </tr>	 
		        	
		        	<tr>
			        	<td align="left" nowrap="nowrap" style="vertical-align: middle;">
							<label for="idEstadoTramite">Estado del trámite: </label>
						</td>			    				    				
		
						<td align="left" nowrap="nowrap" colspan="2" style="vertical-align: middle;">
							<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboEstadosFinal()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								headerKey=""
		           				headerValue="Estados Finalizados"
								name="listadoVehiculosBean.estado" 
								listKey="valorEnum" listValue="nombreEnum"
								id="idEstadoTramite"/>
		
						</td>
						<td align="left" nowrap="nowrap" style="vertical-align: middle;">
		       				<label for="idTipoTramite">Tipo Trámite: </label>
		      			</td>
		   
		      			<td align="left" style="vertical-align: middle;">
		   					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramiteTraficoTransmision()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey=""
		          			headerValue="Ambos Tipos de Transmision"
							name="listadoVehiculosBean.tipoTramite" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idTipoTramite"/> 
		      			</td>			
					</tr>					 
		        		
		        	<tr>
		        		<td align="left" nowrap="nowrap" style="vertical-align: middle;">
	        				<label>Fecha de Presentación: </label>
	       				</td>
	       				
	       				<td align="left" style="vertical-align: middle;">
							<label for="diaMatrIni">desde:</label>
						</td>
	
						<td align="left" style="vertical-align: middle;">
							<table width="20%" border="0">
								<tr>								
									<td>
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.diaInicio" 
											id="diaPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td style="vertical-align: middle;">/</td>	
									<td style="vertical-align: middle;">
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.mesInicio" 
											id="mesPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="2" maxlength="2"/>
									</td>	
									<td style="vertical-align: middle;">/</td>	
									<td style="vertical-align: middle;">
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.anioInicio" 
											id="anioPriMatrIni"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';" 
											readonly="false"
											size="5" maxlength="4"/>
									</td>	
									<td style="vertical-align: middle;">
					    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPriMatrIni, document.formData.mesPriMatrIni, document.formData.diaPriMatrIni);return false;" 
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
						</td>
						
						<td align="left" style="vertical-align: middle;">
							<label for="diaMatrFin">hasta:</label>
						</td>
									
						<td align="left" style="vertical-align: middle;">
							<table width="20%" border="0">
								<tr>
									<td style="vertical-align: middle;">
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.diaFin" 
											id="diaPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td style="vertical-align: middle;">/</td>						
									<td style="vertical-align: middle;">
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.mesFin" 
											id="mesPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="2" maxlength="2" />
									</td>						
									<td style="vertical-align: middle;">/</td>								
									<td style="vertical-align: middle;">
										<s:textfield name="listadoVehiculosBean.fechaPresentacion.anioFin" 
											id="anioPriMatrFin"
											onblur="this.className='input2';"
											onfocus="this.className='inputfocus';"
											readonly="false" 
											size="5" maxlength="4" />
									</td>								
									<td style="vertical-align: middle;">
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
							</table>
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
							onclick="return buscarListadoVehiculos();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="limpiarFormulario(this.form.id);"/>									
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
		
		<c:if test="${not empty textoAgrupacion}">
			<div id="resultado" style="width:100%;background-color:transparent;" >
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
											value="resultadosPorPagina"
											title="Resultados por página"
											onchange="cambiarElementosPorPaginaConsulta('navegarListadoVehiculos.action', 'displayTagDivConsulta', this.value);" /> 
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
				</s:if>
			</div>
				
			<script type="text/javascript">
				$(function() {
					$("#displayTagDivConsulta").displayTagAjax();
				});
			</script>
			
			
			<div id="displayTagDivConsulta" class="divScroll">
				<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
					<%@include file="../../includes/bloqueLoading.jspf"%>
				</div>
	
				 		
				<display:table name="lista" excludedParams="*"
								requestURI="navegarListadoVehiculos.action"
								class="tablacoin"
								uid="tablaListadoVehiculos"
								summary="Listado de Vehiculos"
								cellspacing="0" cellpadding="0" style="width:100%">	
									
						<display:column property="numRegistros" title="Núm. Registros"
									sortable="false" headerClass="sortable"
									defaultorder="descending"
									style="text-align:center;"/>
															
							<display:column property="campoAgrupacion" sortable="flase" headerClass="sortable"
									defaultorder="descending" style="text-align:left; padding-left:50px;" 
									title="${textoAgrupacion}" />
							
							<display:column property="confechaPrimMatriculacion" title="Con Fecha 1º Matrículación"
									sortable="false" headerClass="sortable"
									defaultorder="descending"
									style="text-align:center;" />	
									
				</display:table>
				
				<s:if test="%{lista.getFullListSize() > 0}">
					<table class="totalresults" summary="Numero total y paginacion" >
			 			<tr>
			 				<td align="left">
			 					<strong>Total de registros:&nbsp;${numElementosSinAgrupar}</strong>
			 				</td>
			 			</tr>
			 		</table>
		 		</s:if>
		 				
			</div>
			<s:if test="%{lista.getFullListSize() > 0}">
				<table class="acciones">
					<tr>
						<td>
							<div>
								<input type="button" class="boton" name="bExcelVehiculos" id="bExcelVehiculos" value="Consultar Excel" onClick="return generarExcelListadoVehiculos();" onKeyPress="this.onClick" />
							</div>			
						</td>
					</tr>
				</table>
			</s:if>	
		</c:if>
		<div id="bloqueLoadingConsultarEst" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
			<%@include file="../../includes/bloqueLoading.jspf" %>
		</div>	
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