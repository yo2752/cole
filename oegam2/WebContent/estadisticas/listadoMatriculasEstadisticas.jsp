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
					<span class="titulo">ESTADISTICAS: Listado de Fecha de Primera Matriculacion</span>
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
						onclick="return comprobarPasswordListadoMatriculasEstadisticas();"/>			
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
						<td align="left" nowrap="nowrap">
							<label for="idMatricula">Matrícula (Válida para turismos):</label> 
		       			</td>   
						<td align="left" nowrap="nowrap" width="30%">
							<table>
								<tr>
									<td nowrap="nowrap">
										<s:textfield name="consultaEstadisticas.numMatricula" 
					        				id="idNumMatricula" onblur="this.className='input2';" 
					        				onfocus="this.className='inputfocus';" 
					        				size="4" maxlength="4"/>
				        			</td>
				        			<td nowrap="nowrap">
				        				&nbsp;-&nbsp;
				        			</td>
				        			<td nowrap="nowrap">
					        			<s:textfield name="consultaEstadisticas.letraMatricula" 
					        				id="idLetraMatricula" onblur="this.className='input2';" 
					        				onfocus="this.className='inputfocus';" 
					        				style="text-transform: uppercase;" 
					        				size="3" maxlength="3"/>
				        			</td>
				        		</tr>	
		        			</table>	
						</td>
						<td align="left" nowrap="nowrap">
							<input type="button" 
								class="boton" 
								name="bCalcularMatricula" 
								id="bCalcularMatricula" 
								value="Calcular Fecha"							
								onkeypress="this.onClick" 
								onclick="return generarCalcularMatriculaEstadisticas();"/>
							&nbsp;
							<input type="button" 
								class="boton" 
								name="bLimpiar" 
								id="bLimpiar" 
								value="Limpiar"  
								onkeypress="this.onClick" 
								onclick="return limpiarFormFechaPrimeraMatriculaEstadisticas();"/>
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
			 	
			 	<display:column property="numRegistros" title="Num Registros"
						sortable="true" headerClass="sortable"
						defaultorder="descending"
						style="width:6%" />
						
				<display:column property="campo" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:8%; text-align:left; padding-left:150px;" 
						title="${myTest}"/>
							
				<input type="hidden" name="resultadosPorPagina"/>
			</display:table>
		</div>
	
		<s:if test="%{listaConsultaEstadisticasAgrupadas.size() > 999999}">
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
	    	comprobarPasswordListadoMatriculas();
	    }
	});
});
</script>