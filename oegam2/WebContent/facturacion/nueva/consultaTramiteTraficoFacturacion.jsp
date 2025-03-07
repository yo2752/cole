<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>
<script  type="text/javascript"></script>
<s:hidden id="textoLegal" name="propTexto"/>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de trámites de tráfico</span>
				</td>
			</tr>
		</table>
	</div>	
	
	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">					
			<table class="tablaformbasica">
				<tr>
       				<td align="left" nowrap="nowrap">
						<label for="idEstadoTramite">Estado del trámite: </label>
					</td>			    				    				
 
					<td align="left">
						<s:select list="@trafico.utiles.UtilesVistaTrafico@getInstance().getEstadosTramiteTrafico()"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							headerKey="-1"
	           				headerValue="TODOS"
							name="consultaTramiteTraficoNuevaFacturacion.estado" 
							listKey="valorEnum" listValue="nombreEnum"
							id="idEstadoTramite"/>

					</td>	        		
	    			        	      
	    			<td align="left" nowrap="nowrap">
        				<label for="dniTitular">Tipo Tramite: </label>
       				</td>
    
       				<td align="left">
    					<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().getComboTipoTramite()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						headerKey="-1"
           				headerValue="TODOS"
						name="consultaTramiteTraficoNuevaFacturacion.tipoTramite" 
						listKey="valorEnum" listValue="nombreEnum"
						id="idTipoTramite"/> 
       				</td>        	
       				<td align="left" nowrap="nowrap">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Num Colegiado: </label> 
       				</s:if>
       				</td>     
       				<td align="left">
       				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()||
					@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
       					<s:textfield name="consultaTramiteTraficoNuevaFacturacion.numColegiado" 
        					id="numColegiado" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="5" maxlength="4"/>
        			</s:if>
       				</td>							        	
		        </tr>
		        	
	        	<tr>        	       			
       				<td align="left" nowrap="nowrap">
        				<label for="refPropia">Referencia Propia: </label>        				
       				</td>

       				<td align="left">
       					<s:textfield name="consultaTramiteTraficoNuevaFacturacion.referenciaPropia" 
	       					id="refPropia" 
	       					onblur="this.className='input2';" 
	       					onfocus="this.className='inputfocus';" 
	       					size="30" maxlength="50"/>
       				</td>
        				
	        		<td align="left" nowrap="nowrap">
        				<label for="numExpediente">NºExpediente: </label>
       				</td>

       				<td align="left" colspan="3">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.numExpediente" 
	        				id="numExpediente" 
	        				onblur="this.className='input2';" 
		       				onfocus="this.className='inputfocus';" 
	        				maxlength="15"
	        				cssStyle="width:16em;" />
       				</td>
	        	</tr>

				<tr>
				    <td align="left" nowrap="nowrap">
        				<label for="dniTitular">NIF Titular: </label>
       				</td>
    
       				<td align="left">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.titular.nif" 
        					id="dniTitular" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="9" maxlength="9"/>
       				</td>
       				 <td align="left" nowrap="nowrap">
        				<label for="dniTitular">NIF Facturación: </label>
       				</td>
    
       				<td align="left">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.nifFacturacion" 
        					id="dniFacturacion" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';"
        					size="9" maxlength="9"/>
       				</td>
       				</tr>
       				<tr>	
					<td align="left" nowrap="nowrap">
        				<label for="numBastidor">Nº Bastidor: </label>
      				</td>

       				<td align="left">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.numBastidor" 
        					id="numBastidor" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="22" maxlength="18"/>
       				</td>
       				
	        		<td align="left" nowrap="nowrap">
        				<label for="matricula">Matrícula: </label>
      				</td>
       				<td align="left">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.matricula" 
        					id="matricula" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="10" maxlength="10" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"   />
       				</td>
		        </tr>
		        <tr>
		        	<td align="left" nowrap="nowrap">
        				<label for="nive">NIVE: </label>
      				</td>
		        	<td align="left" colspan="3">
        				<s:textfield name="consultaTramiteTraficoNuevaFacturacion.nive" 
        					id="nive" 
        					onblur="this.className='input2';" 
        					onfocus="this.className='inputfocus';" 
        					size="30" maxlength="30"/>
       				</td>
		        </tr>
			</table>
		    
		    <table class="tablaformbasica">	
	        	<tr>
	        		<td align="right" nowrap="nowrap">
        				<label>Fecha de alta: </label>
       				</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaAltaIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.anioInicio" 
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
						</TABLE>
					</td>
					
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaAltaFin">hasta:</label>
								</td>
						
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaAlta.anioFin" 
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
						</TABLE>
					</td>
				</tr>

				<tr>
	        		<td align="right" nowrap="nowrap">
       					<label>Fecha de presentación:</label>
       				</td>
						
					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaPresentacionIni">desde: </label>
								</td>
								
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.diaInicio" 
										id="diaPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>

								<td>/</td>

								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.mesInicio" 
										id="mesPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
				
								<td>/</td>
						
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.anioInicio" 
										id="anioPresentacionIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
						
								<td>
				    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionIni, document.formData.mesPresentacionIni, document.formData.diaPresentacionIni);return false;" 
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
						<TABLE WIDTH=100%>
							<tr>
								<td align="left">
									<label for="diaPresentacionFin">hasta:</label>
								</td>
									
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.diaFin" 
										id="diaPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
									
								<td>/</td>
									
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.mesFin" 
										id="mesPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
									
								<td>/</td>
									
								<td>
									<s:textfield name="consultaTramiteTraficoNuevaFacturacion.fechaPresentacion.anioFin" id="anioPresentacionFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="5" maxlength="4" />
								</td>
									
								<td>
						   			<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioPresentacionFin, document.formData.mesPresentacionFin, document.formData.diaPresentacionFin);return false;" 
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
			
	    		<table class="acciones">
					<tr>
						<td>
							<input type="button" 
								class="boton" 
								name="bBuscar" 
								id="bBuscar" 
								value="Buscar"  
								onkeypress="this.onClick" 
								onclick="return buscarTramiteTraficoFacturacion(true);"/>			
							&nbsp;
							<input type="button" 
								class="boton" 
								name="bLimpiar" 
								id="bLimpiar" 
								value="Limpiar"  
								onkeypress="this.onClick" 
								onclick="return limpiarFormConsulta();"/>			
						</td>
					</tr>
				</table>
			
		</div>
				<!--<div id="bloqueLoadingBuscar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>-->

<s:hidden key="contrato.idContrato"/>

<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js" 
	id="gToday:normal:agenda.js" 
	src="calendario/ipopeng.htm" 
	scrolling="no" 
	frameborder="0" 
	style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

	
		<s:if test="%{resumenValidacion}">
			<br>
			<div id="estadisticasValidacion" style="text-align: center;">
				<%@include file="../../includes/resumenValidacion.jspf" %>
			</div>
			<br><br>
		</s:if>
		
		<s:if test="%{resumenTramitacion}">
			<br>
			<div id="estadisticasTramitacion" style="text-align: center;">
				<%@include file="../../includes/resumenTramitacionTelematica.jspf" %>
			</div>
			<br><br>
		</s:if>
		
		<s:if test="%{resumenPendienteEnvioExcel}">
			<br>
			<div id="estadisticasPendientesExcel" style="text-align: center;">
				<%@include file="../../includes/resumenPendienteEnvioExcel.jspf" %>
			</div>
			<br><br>
		</s:if>
		
		<s:if test="%{resumenCertificadoTasasFlag}">
			<br>
			<div id="estadisticasCertificadoTasas" style="text-align: center;">
				<s:set var="funcionDescargarCertificado" value ="'descargarCertificado()'" />
				<%@include file="../../includes/resumenCertificadoTasas.jspf" %>
			</div>
			<br><br>
		</s:if>
		
		<s:if test="%{resumenCambiosEstadoFlag}">
			<br>
			<div id="estadisticasCambiosEstado" style="text-align: center;">
				<%@include file="../../includes/resumenCambiosEstado.jspf" %>
			</div>
			<br><br>
		</s:if>
		
	<!--</div>		-->
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		<s:if test="%{!resumenValidacion && !resumenTramitacion && !resumenPendienteEnvioExcel && !resumenCertificadoTasasFlag && !resumenCambiosEstadoFlag}">
				<%@include file="../../includes/erroresMasMensajes.jspf" %>
		</s:if>

		

		<s:if test="%{listaConsultaTramiteTraficoFacturacion.calculaTamanoListaBD()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
									onchange="return cambiarElementosPorPaginaConsultaFactura();" 
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
	<display:table name="listaConsultaTramiteTraficoFacturacion" excludedParams="*"
					requestURI="navegarGestionFacturar.action"
					class="tablacoin"
					uid="tablaConsultaTramite"
					summary="Listado de Tramites"
					cellspacing="0" cellpadding="0"
					decorator="trafico.utiles.DecoradorTablas"
	>		

	<display:column property="numExpediente" title="Num Expediente"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;" 
			paramId="numExpediente" 	 
			/>
			
	<display:column property="referenciaPropia" title="Referencia Propia"
			sortable="true" headerClass="sortable"
			defaultorder="descending" maxLength="15"
			style="width:4%" />
			
	<display:column property="vehiculo.bastidor" title="Bastidor"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%" />
				
	<display:column property="vehiculo.matricula" title="Matricula"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" />
			
	<display:column property="tasa.codigoTasa" title="Codigo Tasa"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />
	
	<display:column title="Tipo Tramite" property="tipoTramite.nombreEnum"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" 
			paramId="idTipoTramite" >
	</display:column>
		
	<display:column title="Estado" property="estado.nombreEnum"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:2%" >
	</display:column>

	
	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarExpedientes(this, document.formData.listaChecksConsultaTramite);' onKeyPress='this.onClick'/>" 
		style="width:1%" >
	<table align="center">
	<tr>
		<td style="border: 0px;">
			<input type="checkbox" name="listaChecksConsultaTramite" id="<s:property value="#attr.tablaConsultaTramite.tipoTramite.valorEnum"/>-<s:property value="#attr.tablaConsultaTramite.numExpediente"/>" 
			value='<s:property value="#attr.tablaConsultaTramite.numExpediente"/>' 
			onClick="return seleccionarExpedientes();"/>
		</td>			
	</tr>
	</table>
		
 	</display:column>
	

	
				
	<input type="hidden" name="resultadosPorPagina"/>
</display:table>
</div>


<s:if test="%{(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoTrafico())&&
			(listaConsultaTramiteTraficoFacturacion.getFullListSize()>0)}">
    
 	<table align="center">
		<tr>
			<td align="left" nowrap="nowrap">
						<label for="idLCombo">Cliente: </label>
			</td>			    				    				
			<td>		
    			<s:select list="@facturacion.utiles.UtilesVistaFacturacion@getComboClienteFacturacion()"
						id="comboFacturacion"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						listValue="nombreEnum"
						listKey="valorEnum"
						title="Combo Cliente Facturacion"
						disabled="true"
						style="color:#6E6E6E"
						align="left"/>
			</td>
			<td>
    			<input class="botonMasGrande" type="button" id="bNuevaFactura" name="bNuevaFactura" value="Nueva Factura" onClick="return nuevaFactura(this);" 
    				onKeyPress="this.onClick" disabled style="color:#6E6E6E"/>
			</td>
			<!-- <td>
    			<input class="botonMasGrande" type="button" id="bNuevaFacturaResumen" name="bNuevaFacturaResumen" 
    				value="Nueva Factura Resumen" onClick="return nuevaFacturaResumen(this);" onKeyPress="this.onClick" disabled style="color:#6E6E6E"/>
			</td> -->
		</tr>	
 	</table>
 	<table align="center">
 		<tr>
			<td>
    			<input class="botonMasGrande" style="text-align:left" type="button" id="bNuevaFacturaSinExpediente" name="bNuevaFacturaSinExpediente" 
    				value="Nueva Factura Sin asociar" onClick="return nuevaFacturaSinExpediente(this);" onKeyPress="this.onClick"/>
			</td>
		</tr>
	</table>    
    <div id="bloqueLoadingConsultar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>
    </s:if>
</s:form> 

<s:if test="%{impresoEspera}">
		<script type="text/javascript">muestraDocumento();</script>
</s:if>
