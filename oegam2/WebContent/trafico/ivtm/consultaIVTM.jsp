<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<!--  //TODO MPC. Cambio IVTM. Este jsp es el bueno -->
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/576.js" type="text/javascript"></script>
<script src="js/pagosEImpuestos.js" type="text/javascript"></script>
<script src="js/ivtm.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Consulta IVTM Ayuntamiento de Madrid
			</span></td>
		</tr>
	</table>
</div>
<s:form method="post" id="formData" name="formData">
	<div id="busqueda">					
	    <%@include file="../../includes/erroresYMensajes.jspf" %>

		<table class="acciones">
			<tr>
				<td colspan="2">
					<img id="loadingImage" 
						style="display:none;margin-left:auto;margin-right:auto;" 
						src="img/loading.gif">
				</td>
			</tr>
		</table>
	</div>
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Consulta de Peticiones previas del IVTM al Ayuntamiento de Madrid</td>
			</tr>
		</table>
		
		<div id="busqueda">					
			<table cellSpacing="4" class="tablaformbasica" cellPadding="0">
	        	<tr>
	        		<td align="left" nowrap="nowrap">
	       				<label>Fecha de Consulta: </label>
	   				</td>
	
					<td align="left">
						<table width=100%>
							<tr>
								<td>
									<label for="diaAltaIni">Desde: </label>
								</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.anioInicio" 
										id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4"/>
								</td>
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" 
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
					<td align="right">
						<table width=100%>
							<tr>
								<td align="left">
									<label for="diaAltaFin">Hasta:</label>
								</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
								<td>/</td>
								<td>
									<s:textfield name="consultaIvtmViewBean.fechaBusqueda.anioFin" 
										id="anioAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
								<td>
						    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" 
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
				<tr>
					<td align="left" nowrap="nowrap">
	       				<label for="matriculaPeticionPreviaConsultaIVTM">Matrícula: </label>
	     			</td>
	      			<td align="center">
	       				<s:textfield name="consultaIvtmViewBean.matricula" 
	       					id="matriculaPeticionPreviaConsultaIVTM" 
	       					onblur="this.className='input2';" 
	       					onfocus="this.className='inputfocus';" 
	       					size="10" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
	   				</td>
	   				
	   				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		  				<td align="left" nowrap="nowrap">
							<label for="numColegiado">Num Colegiado: </label> 
		       			</td>     
		       			<td align="center">
		       				<s:textfield name="consultaIvtmViewBean.numColegiado" 
		        				id="numColegiado" 
		        				onblur="this.className='input2';" 
		        				onfocus="this.className='inputfocus';" 
		        				size="10" maxlength="9"/>
		    			</td>
		    		</s:if>						        	
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
			<table class="acciones">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bBuscarPeticionesPrevias" 
							id="bBuscarPeticionesPrevias" 
							value="Buscar"  
							onkeypress="this.onClick" 
							onclick="return buscarPeticionesPreviasConsultaIVTM();"/>			
					</td>
					<td>
						<input type="button" 
							class="boton" 
							name="bBuscar" 
							id="bBuscar" 
							value="Consultar"  
							onkeypress="this.onClick" 
							onclick="consultaDeudaIVTM();"/>	
					</td>
					
					<td>
						<input type="button" 
							id="bLimpiar"
							class="boton" 
							name="bLimpiarPeticionesPrevias" 
							onclick="limpiarFormularioPeticionesPreviasConsultaIVTM()"
							value="Limpiar"  />			
					</td>
				</tr>
			</table>
		</div>
		
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultados de la Búsqueda: </td>
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
							<s:select
								list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';"
								id="idResultadosPorPagina" 
								value="resultadosPorPagina"
								name= "resultadosPorPagina"
								title="Resultados por página"
								onchange="return cambiarElementosPorPaginaConsultaDeudaIVTM();"  />
						</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		</s:if>
	</div>
	<display:table name="lista" 
				class="tablacoin"
				uid="tablaConsulta"
				requestURI= "navegarConsultaIVTM.action"
				id="tablaConsulta"
				summary="Listado de Consultas"
				excludedParams="*"
				cellspacing="0" cellpadding="0">	
		<display:column property="matricula" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:15%" title="Matrícula"/>	
		<display:column property="mensaje" title="Respuesta"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:25%" />
				
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">		
		<display:column property="numColegiado" title="Colegiado"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:14%" />
		</s:if>
		<display:column property="fechaReq" title="Fecha Consulta"
				sortable="true" headerClass="sortable"
				defaultorder="descending"
				style="width:14%" />
		
	</display:table>
	<div id="emergenteMatricula" style="width: 100%; background-color: transparent;">

	</div>
</s:form>