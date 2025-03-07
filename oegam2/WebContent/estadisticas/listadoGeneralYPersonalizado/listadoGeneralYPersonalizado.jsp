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

<s:set var="textoAgrupacion" value="%{@estadisticas.utiles.enumerados.Agrupacion@convertirTexto(listadoGeneralYPersonalizadoBean.agrupacion)}"></s:set>

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">ESTADÍSTICAS: Listado General y Personalizado</span>
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
						onclick="return comprobarPasswordListadoGeneralYPersonalizado();"/>			
				</td>
			</tr>
		</table>
	</s:form>
	<%@include file="../../includes/erroresMasMensajes.jspf" %>
</s:if>

<s:if test="%{passValidado=='true'}">
	<s:form method="post" id="formData" name="formData">
	
	<s:hidden name="listadoGeneralYPersonalizadoBean.tipoInterviniente"/>	
		<div id="busqueda">				
			<table class="tablaformbasica" border="0">	
	        	<tr>
	        		<td align="left" nowrap="nowrap" style="vertical-align: middle;width:20%">
        				<label>Fecha de Presentaci&oacute;n:</label>
       				</td>
       				


					<td align="left" style="vertical-align: middle;">
						<table style="width:20%">
							<tr>
							    <td align="left" style="vertical-align: middle;">
									<label for="diaMatrIni">desde: </label>
								</td>
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.diaInicio" 
										id="diaMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="2" maxlength="2"/>
								</td>	
								<td style="vertical-align: middle;">/</td>	
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.mesInicio" 
										id="mesMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="2" maxlength="2"/>
								</td>	
								<td style="vertical-align: middle;">/</td>	
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.anioInicio" 
										id="anioMatrIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										readonly="false"
										size="5" maxlength="4"/>
								</td>	
								<td style="vertical-align: middle;">
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
						</table>
					</td>
					
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="left" style="vertical-align: middle;">
									<label for="diaMatrFin">hasta:</label>
								</td>
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.diaFin" 
										id="diaMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>						
								<td style="vertical-align: middle;">/</td>						
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.mesFin" 
										id="mesMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>						
								<td style="vertical-align: middle;">/</td>								
								<td>
									<s:textfield name="listadoGeneralYPersonalizadoBean.fechaPresentacion.anioFin" 
										id="anioMatrFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="5" maxlength="4" />
								</td>								
								<td style="vertical-align: middle;">
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
						</table>
					</td>
				</tr>
			</table>
		
			<table class="tablaformbasica" border="0">
				<tr> 
	       			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idNumcolegiado">Núm. Colegiado: </label> 
	       			</td>     
	       			<td align="left" nowrap="nowrap">						
						<s:textfield name="listadoGeneralYPersonalizadoBean.numColegiado" 
	        				id="idNumcolegiado" 
	        				onblur="this.className='input2';" 
	        				onfocus="this.className='inputfocus';" 
	        				size="5" maxlength="4"/>
					</td>		
					
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idTipoCreacion">Tipo Creación: </label> 
	       			</td>     
	       			<td align="left" nowrap="nowrap">						
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTipoCreaciones()"	 
							name="listadoGeneralYPersonalizadoBean.idTipoCreacion" 
	        				id="idTipoCreacion" 
	        				headerKey=""
		          			headerValue="Cualquier Tipo"
	        				listKey="idTipoCreacion" listValue="descripcionCreacion"/>
					</td>       			
				 </tr>	
	 			 <tr>    
			       <td align="left" nowrap="nowrap" style="vertical-align: middle;">   				
						<label for="idProvincia">Provincia:</label>
					</td>				        		
	       	       			
	       			<td align="left" nowrap="nowrap">
						<s:select cssStyle="width:150px;" list="@trafico.utiles.UtilesVistaTrafico@getInstance().getProvincias()"
							headerKey=""
			           		headerValue="Cualquier Provincia"
							name="listadoGeneralYPersonalizadoBean.idProvincia" 
							listKey="idProvincia" 
							listValue="nombre"
							id="idProvincia"
							onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';"
							onchange="cargarListaJefaturasListadoGeneralYPersonalizado(this,'idJefatura','jefaturaSeleccionado');"/>	
					</td>
	
					<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idJefatura">Jefatura Provincial:</label>
					</td>
					<td>
						<select id="idJefatura" 
							onchange="seleccionarCampo('jefaturaSeleccionado','idJefatura');" >
							<option value="">Cualquier Jefatura</option>
						</select>
						<s:hidden id="jefaturaSeleccionado" name="listadoGeneralYPersonalizadoBean.jefaturaProvincial"/>	
					</td>
		       	</tr>		  
				<tr>
	      			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idEstadoTramite">Estado del trámite: </label>
					</td>			    				    				
	
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey=""
	           				headerValue="Cualquier Estado"
							name="listadoGeneralYPersonalizadoBean.listaEstadosPantalla" 
							listKey="valorEnum" listValue="nombreEnum"
							multiple="true"
							id="idEstadoTramite"/>
	
					</td>	        		
	    			        	      
	    			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
	       				<label for="idTipoTramite">Tipo Trámite: </label>
	      			</td>
	   
	      			<td align="left" style="vertical-align: middle;">
	   					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						headerKey=""
	          			headerValue="Cualquier Tipo"
						name="listadoGeneralYPersonalizadoBean.tipoTramite" 
						listKey="valorEnum" listValue="nombreEnum"
						id="idTipoTramite"/> 
	      			</td> 
	      			
			    </tr>
			    <tr>	
		        	<td align="left" nowrap="nowrap" style="vertical-align: middle;">
	       				<label for="idTipoVehiculo">Tipo Vehículo: </label>
	      				</td>    
	      					        		
				    <td align="left" nowrap="nowrap">
					   <s:select id="idTipoVehiculo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('X')"
						headerKey=""
				   		headerValue="Cualquier Tipo"
						name="listadoGeneralYPersonalizadoBean.tipoVehiculo" 
						listKey="tipoVehiculo" 
						listValue="descripcion"/>	
				     </td>
				     
					 <td align="left" nowrap="nowrap" style="vertical-align: middle;">
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
		      			
		      			<s:hidden id="idMarcaVehiculo" name="listadoGeneralYPersonalizadoBean.codigoMarca" />
					</td>
				</tr>	
				<tr>
	      			<td align="left" nowrap="nowrap" style="vertical-align: middle;">
						<label for="idAgrupacion">Agrupación: </label>
					</td>			    				    				
	
					<td align="left">
						<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboAgrupacion()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey=""
	           				headerValue="Sin Agrupación"
							name="listadoGeneralYPersonalizadoBean.agrupacion" 
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
							onclick="return buscarListadoGeneralYPersonalizado();"/>				
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
										onchange="cambiarElementosPorPaginaConsulta('navegarListadoGeneralYPersonalizado.action', 'displayTagDivConsulta', this.value);" /> 
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
							requestURI="navegarListadoGeneralYPersonalizado.action"
							class="tablacoin"
							uid="tablaListadoGeneralYPersonalizado"
							summary="Listado de estadisticas de tramite"
							cellspacing="0" cellpadding="0" style="width:100%">
								
				<display:column  property="campoAgrupacion" sortable="true" headerClass="sortable"
						defaultorder="descending" style="text-align:left; padding-left:50px;" 
						title="${textoAgrupacion}" sortProperty="campo"/>	
						
				<display:column property="numRegistros" title="Núm. Registros"
						sortable="true" headerClass="sortable"
						defaultorder="descending" sortProperty="cantidad"/>
							
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
							<input type="button" class="boton" name="bExcel" id="bExcel" value="Consultar Excel" onClick="return generarExcelListadoGeneralYPersonalizado();" onKeyPress="this.onClick" />
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
	var marcaVehiculoMatriculacionEst = new BasicContentAssist(document.getElementById('codigoMarca'), [], null, true);
	recargarComboJefaturas('idProvincia','idJefatura','jefaturaSeleccionado');
	cargarListaMarcasVehiculoEst(marcaVehiculoMatriculacionEst);	
</script>

<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoGeneralYPersonalizado();
	    }
	});
});
</script>