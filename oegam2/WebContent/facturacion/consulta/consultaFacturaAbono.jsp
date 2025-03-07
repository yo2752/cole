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
<script src="js/facturacionBotones.js" type="text/javascript"></script>
<script src="js/trafico/facturacion.js" type="text/javascript"></script>

<!-- <div id="contenido" class="contentTabs" style="display: block;"> -->
	<!-- Se hace referencia a la parte de errores de los mensajes -->
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de Facturas de Abono</span>
				</td>
			</tr>
		</table>
	</div>	
	
	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">					
			<table class="tablaformbasica">
		        <tr>    			
		    		<td align="left" nowrap="nowrap" >
				       	<label for="numExpediente">Num Expediente: </label>
				    </td>				        		
				    <td align="left" nowrap="nowrap">
				       	<s:textfield name="beanAbon.numExpediente" 
				       				 id="numExpediente" 
				       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
				       				 size="20" maxlength="25"/>
				    </td>		    
				    <td align="left" nowrap="nowrap">
		       			<label for="numColegiado">Num Colegiado: </label>
		       		</td>				        		
		       		<td align="left" nowrap="nowrap">
		       			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
		       	       	<s:textfield name="beanAbon.numColegiado"
		       	       				 id="numColegiado"
		       	       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
		       	       				 size="6" maxlength="25"/>
		       	       	</s:if>
		       	       	<s:else>
		       	       		<s:textfield name="beanAbon.numColegiado"
		       	       				 id="numColegiado"
		       	       				 value="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNumColegiadoSession()}"
		       	       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
		       	       				 size="6" maxlength="25" readonly="true"/>
		       	       	</s:else>
		       		</td>
	       		</tr> 
	       		<tr>    			
		    		<td align="left" nowrap="nowrap" >
				       	<label for="nif">NIF/CIF Receptor: </label>
				    </td>				        		
				    <td align="left" nowrap="nowrap">
				       	<s:textfield name="beanAbon.nif"
				       				 id="nif"
				       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
				       				 size="9" maxlength="25"/>
				    </td>		    
				    <td align="left" nowrap="nowrap">
		       			<label for="numFactura">Num Factura:</label>
		       		</td>				        		
		       		<td align="left" nowrap="nowrap">
		       	       	<s:textfield name="beanAbon.numFactura"
		       	       				 id="numFactura"
		       	       				 onblur="this.className='input2';" 
	       						     onfocus="this.className='inputfocus';" 
		       	       				 size="20" maxlength="20"/>
		       		</td>
	       		</tr>
	       		<tr>    			
		    		<td align="left" nowrap="nowrap" >
				       	<label for="numSerie">Num Serie: </label>
				    </td>				        		
				    <td align="left" nowrap="nowrap">
				       	<s:textfield name="beanAbon.numSerie"
				       				 id="numSerie"
				       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
	       							 onKeyUp="modificarNumSerieAbono(this)"
				       				 size="10" maxlength="25"/>
				    </td>		    
				    <td align="left" nowrap="nowrap">
		       			<label for="numCodigo">Num Código:</label>
		       		</td>				        		
		       		<td align="left" nowrap="nowrap">
		       	       	<s:textfield name="beanAbon.numCodigo"
		       	       				 id="numCodigo"
		       	       				 onblur="this.className='input2';" 
	       							 onfocus="this.className='inputfocus';"
		       	       				 size="10" maxlength="25"/>
		       		</td>
	       		</tr>
	       		
			</table>
		    <table class="tablaformbasica">	
	        	<tr>
	        		<td align="right" nowrap="nowrap">
        				<label>Fecha de factura: </label>
       				</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right">
									<label for="diaAltaIni">desde: </label>
								</td>
	
								<td>
									<s:textfield name="beanAbon.fecha.diaInicio" 
										id="diaAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="beanAbon.fecha.mesInicio" 
										id="mesAltaIni"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2"/>
								</td>
	
								<td>/</td>
	
								<td>
									<s:textfield name="beanAbon.fecha.anioInicio" 
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
									<s:textfield name="beanAbon.fecha.diaFin" 
										id="diaAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
						
								<td>
									<s:textfield name="beanAbon.fecha.mesFin" 
										id="mesAltaFin"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" 
										size="2" maxlength="2" />
								</td>
						
								<td>/</td>
								
								<td>
									<s:textfield name="beanAbon.fecha.anioFin" 
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
							onclick="return buscarFacturaAbono();"/>			
						&nbsp;
						<input type="button" 
							class="boton" 
							name="bLimpiar" 
							id="bLimpiar" 
							value="Limpiar"  
							onkeypress="this.onClick" 
							onclick="return limpiarFormConsultaFactura();"/>			
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
		
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		
		<s:if test="%{listaConsultaFacturaAbon.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
									onchange="return cambiarElementosPorPaginaConsultaFacturaAbono();" 
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
	<display:table name="listaConsultaFacturaAbon" excludedParams="*"
					requestURI="navegarAbonoGestionFacturar.action"
					class="tablacoin"
					uid="tablaConsultaFactura"
					summary="Listado de Facturas"
					cellspacing="0" cellpadding="0"
					pagesize="${resultadosPorPagina}"					
	>		
	
	<display:column property="checkPdf" title="Estado"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;" 
			paramId="checkPdf" 	 
	/>	
	
	<display:column property="numSerie" title="Num Serie"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />

	<display:column property="id.numFactura" title="Num Factura"
			sortable="true" headerClass="sortable"
			defaultorder="descending"
			style="width:4%;"	
			href="detalleGestionFacturar.action"					
			paramId="numFactura" 	 
			/>			
				
	<display:column property="facAnulada" title="Factura de Abono"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" />
	
	<display:column title="Emisor CIF/NIF" property="nifEmisor"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" >
	</display:column>
	<display:column title="Receptor CIF/NIF" property="persona.id.nif"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" >
	</display:column>
	<display:column title="Importe(€)" property="importeTotal"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" >
	</display:column>
		
	<display:column title="Fecha Factura" property="fechaFactura"
			sortable="true" headerClass="sortable" 
			defaultorder="descending"
			style="width:4%" >
	</display:column>
	
	<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultaFacturaAbono);' onKeyPress='this.onClick'/>" 
			style="width:1%" >
		<table align="center">
		<tr>
			<td style="border: 0px;">
				<input type="checkbox" name="listaChecksConsultaFacturaAbono" id="<s:property value="#attr.tablaConsultaFactura.id.numFactura"/>-<s:property value="#attr.tablaConsultaFactura.id.numColegiado"/>" 
				value='<s:property value="#attr.tablaConsultaFactura.id.numFactura"/>' 
				onClick="return seleccionarConsultaCheckAbono();"/>
			</td>
			<!--  
			<td style="border: 0px;">				  		
		  		<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
		  		onclick="abrirEvolucionTramite(<s:property value="#attr.tablaConsultaTramite.numFactura"/>);" title="Ver evolución"/>
		  	</td>
		  	-->
		</tr>
		</table>		

 	</display:column>		
	<input type="hidden" name="resultadosPorPagina"/>
</display:table>
</div>
	
	<!--  DRC@06/08/2012 Se encargan de recoger los mensajes a mostrar, a traves de los botones borrador y pdf. El texto se recoje de facturacion.propertis. -->
	<s:hidden id="mensajeBorrador" name="beanAbon.MensajeBorrador"/>
	<s:hidden id="mensajePDF"      name="beanAbon.MensajePDF"/>
	<s:hidden id="mensajeImprimir" name="beanAbon.MensajeImprimir"/>
	
	<s:hidden id="estadoPdf" name="datosCliente.EstadoPDF" />
	<s:hidden id="tipoPdf"   name="datosCliente.TipoPDF" />
	
	<table align="center">
    	<tr>
    		<td>
	    		<input class="boton" type="button" id="idBorrar" name="bBorrar" value="Borrar Factura" onClick="return eliminarFacturaAbono(this);" onKeyPress="this.onClick" disabled="true" style="color:#6E6E6E"/>
	    	</td>
    		<td>
				<input class="boton" type="button" id="idGenerarBorrador" name="bGenerarBorrador" value="Generar Borrador" onClick="return generarBorradorConsultaFacturacionAbono(this, 'cabono');" onKeyPress="this.onClick" disabled="true" style="color:#6E6E6E"/>
			</td>
    		<td>
	    		<input class="boton" type="button" id="idGenerarPdf" name="bGenerarPdf" value="Generar PDF" onClick="return generarPDFConsultaFacturacionAbono(this, 'cabono');" onKeyPress="this.onClick" disabled="true" style="color:#6E6E6E"/>
	    	</td>   	
    	</tr>
    </table>
    
    <div id="bloqueLoadingConsultar" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
		<%@include file="../../includes/bloqueLoading.jspf" %>
	</div>

</s:form> 

<s:if test="%{impresoEspera}">
		<script type="text/javascript">muestraDocumento();</script>
</s:if>

<script type="text/javascript">compruebaPDFConsulta(this, 'cabono');</script>