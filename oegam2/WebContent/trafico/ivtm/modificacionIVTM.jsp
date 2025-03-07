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
				Modificación Autoliquidación IVTM
			</span></td>
		</tr>
	</table>
</div>
<s:form method="post" id="formData" name="formData">
<div id="busqueda">
<table class="tablaformbasica">	

	        	<tr>
	        		<td align="left" nowrap="nowrap">
        				<label>Fecha de Alta: </label>
       				</td>

					<td align="left">
						<table width=100%>
							<tr>
								<td align="right">
									<label for="diaIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.anioInicio" 
										id="anioAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
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
					
					<td align="left">
						<table width=100%>
							<tr>
								<td align="left">
									<label for="diaFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="ivtmViewBean.fechaRequerida.anioFin" 
										id="anioAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										readonly="false" 
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
        				<label for="autoliquidacion">Num. Autoliquidación: </label>
      				</td>

       				<td align="left">
        				<s:textfield name="ivtmViewBean.nrc" 
        					id="autoliquidacion" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="20" maxlength="20"/>
       				</td>
       				<td align="left" nowrap="nowrap">
        				<label for="numExpediente">Num. Expediente: </label>
      				</td>

       				<td align="left">
        				<s:textfield name="ivtmViewBean.numExpediente" 
        					id="numExpediente" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';"
        					maxlength="15" size="20" />
       				</td>
		        </tr>

		        <tr>    		
	        		<td align="left" nowrap="nowrap">
        				<label for="bastidor">Bastidor: </label>
      				</td>

       				<td align="left">
        				<s:textfield name="ivtmViewBean.bastidor" 
        					id="bastidor" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';"/>
       				</td>
		        
		        <s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial()}">
		          		
	        		<td align="left" nowrap="nowrap">
        				<label for="numColegiado">Num. Colegiado: </label>
      				</td>

       				<td align="left">
        				<s:textfield name="ivtmViewBean.numColegiado" 
        					id="numColegiado" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="4" maxlength="4"/>
       				</td>
       			</s:if>
		        </tr>
		        
			</table>
		    <%@include file="../../includes/erroresYMensajes.jspf" %>	
			<table class="acciones">
				<tr>
					<td>
						<input type="button" 
							class="boton" 
							name="bBuscar" 
							id="bBuscar" 
							value="Buscar"  
							onkeypress="this.onClick" 
							onclick="buscarAutoliquidacionIVTM();"/>			
					</td>
					<td>
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar" 
							onclick="return limpiarFormularioIVTMModificacion();"/>	
					</td>
				</tr>
				
				<tr>
					<td colspan="2">
						<img id="loadingImage" 
							style="display:none;margin-left:auto;margin-right:auto;" 
							src="img/loading.gif">
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
										name="resultadosPorPagina"
										value="resultadosPorPagina"
										title="Resultados por página"
										onchange="return cambiarElementosPorPaginaConsultaModificacionIVTM();" 
										/>
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
				uid="tablaTramitesIVTM"
				requestURI= "navegarModificacionIVTM.action"
				id="tablaConsulta"
				summary="Listado de IVTM" 
				excludedParams="*"				
				cellspacing="0" cellpadding="0">
	
		<display:column property="numExpediente" sortable="true" headerClass="sortable centro"
				defaultorder="descending" style="width:15%" title="Numero Expediente" href="detalleModificacionIVTM.action" paramId="numExpediente"/>
					
		<display:column property="nrc" title="Autoliquidación" sortable="true" headerClass="sortable centro" defaultorder="descending" style="width:13%" />

		<display:column title="Estado" sortable="true" headerClass="sortable centro" defaultorder="descending" style="width:13%">
			<s:property value="%{@escrituras.utiles.UtilesVista@getInstance().getEstadoIvtm(#attr.tablaConsulta.estadoIvtm)}" />
		</display:column>
		<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
			<display:column title="Colegiado" property="numColegiado" sortable="true" headerClass="sortable centro" defaultorder="descending" style="width:13%"/>
		</s:if>			
		<display:column title="Bastidor" property="bastidor" sortable="true" headerClass="sortable centro" defaultorder="descending" style="width:13%"/>
		<display:column property="fechaRequerida" title="Fecha de Alta" sortable="true" headerClass="sortable centro" defaultorder="descending" style="width:13%" format="{0,date,dd-MM-yyyy}" />
		
		
		
		
		
		<%-- <display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this)' 
					onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsulta.numExpediente"/>" 
									value='<s:property value="#attr.tablaConsulta.numExpediente"/>' />
								<input type="hidden" id="idCheckPodTrans<s:property value="#attr.tablaConsulta.numExpediente"/>" value="<s:property value="#attr.tablaConsulta.numExpediente"/>">
							</td>
							<td style="border: 0px;">
				  				<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirDetallePoder('<s:property value="#attr.tablaConsulta.numExpediente"/>');" title="Ver Detalle"/>
				  			</td>
						</tr>
					</table>
		</display:column>--%>
		
	</display:table>


</s:form>
