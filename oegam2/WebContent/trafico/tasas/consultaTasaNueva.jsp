<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/gestionTasa.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular"><span class="titulo">
				Consulta de tasas
			</span></td>
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
<div>	
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
			<div id="busqueda">					
				<table cellSpacing="3" class="tablaformbasica">
					<tr>
						<td align="left" nowrap="nowrap">
	       					<label for="TipoTasa">Tipo de Tasa:</label>
	       				</td>
	       				<td align="left">       
		        			<s:select
								list="@org.gestoresmadrid.core.tasas.model.enumeration.TipoTasaDGT@values()"
								headerKey="" headerValue="" name="consultaTasa.tipoTasa"
								listKey="valorEnum" listValue="nombreEnum" id="TipoTasa"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" />
						</td>
	       				<td align="left" nowrap="nowrap">
	       					<label for="CodigoTasa">C&oacute;digo de Tasa:</label>
	       				</td>
	       				<td align="left">
					    		<s:textfield id="CodigoTasa"
								onblur="this.className='input2';" 
			       				onfocus="this.className='inputfocus';" name="consultaTasa.codigoTasa">
			       				</s:textfield>
	       				</td>
	       			</tr>
	       			<tr>
						<td align="left" nowrap="nowrap">
							<label for="idAsignada">Asignada:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@escrituras.utiles.UtilesVista@getInstance().getComboDecisionTrafico()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue=""
									name="consultaTasa.asignada" 
									listKey="valorEnum" listValue="nombreEnum"
									id="idAsignada"/>
						</td>	        	
		        	 
		        		<td align="left" nowrap="nowrap">
							<label for="idTipoTramite">Tipo de tr&aacute;mite:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@trafico.utiles.UtilesVistaTrafico@getInstance().obtenerTipoTramite()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="TODOS"
									name="consultaTasa.tipoTramite" 
									listKey="tipoTramite" listValue="descripcion"
									id="idTipoTramite"/>
						</td>
					</tr>
					<tr>
		        		<td align="left" nowrap="nowrap">
	       					<label for="NumeroExpediente">Nº Expediente:</label>
	       				</td>
	       				<td align="left">
	       					<s:textfield name="consultaTasa.numExpediente" id="NumeroExpediente" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
	       				</td>       	       			
	       				<td align="left" nowrap="nowrap">
	       					<label for="ReferenciaPropia">Referencia Propia:</label>        				
	       				</td>
	       				<td align="left">
	       					<s:textfield name="consultaTasa.refPropia" id="ReferenciaPropia" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="20" maxlength="50"/>
	       				</td>
	       			</tr>
	       			<tr>
		        		<td align="left" nowrap="nowrap">
							<label for="idFormato">Formato:</label>
						</td>			    				    				
						<td align="left">
							<s:select	list="@org.gestoresmadrid.core.tasas.model.enumeration.FormatoTasa@values()"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';"
									headerKey=""
			           				headerValue="TODOS"
									name="consultaTasa.formato" 
									listKey="codigo" listValue="descripcion"
									id="idFormato"/>
						</td>
	       			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
	       				<td align="left" nowrap="nowrap">
	       					<label for="NumeroColegiado">Num.Colegiado:</label>        				
	       				</td>
	       				<td align="left" nowrap="nowrap">
	       					<s:textfield name="consultaTasa.numColegiado" id="numColegiado" onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="4" maxlength="4"></s:textfield>
	       				</td>
	       			</s:if>
	       			</tr>
	       			<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().esGenerarCertificadoTasasNuevo()">
	        			<tr>
							<td align="left">
			   					<label for="conNive">Tasa ICOGAM: </label>
			   				</td>
			   				<td align="left">
			   					<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaTasa@getInstance().getImportadoColegio()"
									name="consultaTasa.importadoIcogam" id="esImportColegio" headerKey="" headerValue="Todos" listKey="valorEnum"
									listValue="nombreEnum" />
			       			</td>
						</tr>
					</s:if>
		        </table>
	        <table class="tablaformbasica">
	        	<tr>
	        		<td align="right" nowrap="nowrap">
       				<label>Fecha de alta:</label>
       				</td>
					<td align="left"><TABLE WIDTH=100%>
						<tr>
							<td align="right"><label for="diaAltaIni">desde: </label></td>
							<td>
								<s:textfield name="consultaTasa.fechaAlta.diaInicio" id="diaAltaIni"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaAlta.mesInicio" id="mesAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaAlta.anioInicio" id="anioAltaIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
			    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" HIDEFOCUS title="Seleccionar fecha">
			    				<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
			    				</a>
							</td>
							<td width="2%"></td>
						</tr></TABLE>
					</td>
					<td align="left"><TABLE WIDTH=100%>
						<tr>
					<td align="left"><label for="diaAltaFin">hasta:</label></td>
					<td>
						<s:textfield name="consultaTasa.fechaAlta.diaFin" id="diaAltaFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td>/</td>
					<td>
						<s:textfield name="consultaTasa.fechaAlta.mesFin" id="mesAltaFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td>/</td>
					<td>
						<s:textfield name="consultaTasa.fechaAlta.anioFin" id="anioAltaFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" />
					</td>
					<td>
			    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" HIDEFOCUS title="Seleccionar fecha">
			    			<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	   			    	</a>
					</td>
					
						</tr></TABLE></td>
				</tr>



				<tr>
	        		<td align="right" nowrap="left">
       				<label>Fecha de asignación:</label>
       				</td>
					<td align="left"><TABLE WIDTH=100%>
						<tr>
							<td align="right"><label for="diaAsignacionIni">desde: </label></td>
							<td>
								<s:textfield name="consultaTasa.fechaAsignacion.diaInicio" id="diaAsignacionIni"
								onblur="this.className='input2';"
								onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaAsignacion.mesInicio" id="mesAsignacionIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaAsignacion.anioInicio" id="anioAsignacionIni"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>
			    				<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAsignacionIni, document.formData.mesAsignacionIni, document.formData.diaAsignacionIni);return false;" HIDEFOCUS title="Seleccionar fecha">
			    				<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
			    				</a>
							</td>
							<td width="2%"></td>
						</tr></TABLE>
					</td>
					<td align="left"><TABLE WIDTH=100%>
						<tr>
					<td align="left"><label for="diaAsignacionFin">hasta:</label></td>
					<td>
						<s:textfield name="consultaTasa.fechaAsignacion.diaFin" id="diaAsignacionFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td>/</td>
					<td>
						<s:textfield name="consultaTasa.fechaAsignacion.mesFin" id="mesAsignacionFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="2" maxlength="2" />
					</td>
					<td>/</td>
					<td>
						<s:textfield name="consultaTasa.fechaAsignacion.anioFin" id="anioAsignacionFin"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" />
					</td>
					<td>
			    		<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAsignacionFin, document.formData.mesAsignacionFin, document.formData.diaAsignacionFin);return false;" HIDEFOCUS title="Seleccionar fecha">
			    			<img class="PopcalTrigger" align="absmiddle" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	   			    	</a>
					</td>
<!--						</td>-->
						</tr></TABLE></td>
				</tr>


				<tr>
	        		<td align="right" nowrap="nowrap">
       				<label>Fecha de vigencia:</label>
       				</td>
					<td align="left"><TABLE WIDTH=100%>
						<tr>
							<td align="right"><label for="diaFinVigencia">hasta:&nbsp; </label></td>
							<td>
								<s:textfield name="consultaTasa.fechaFinVigencia.dia" id="diaFinVigencia"
									size="2" maxlength="2" value="31" disabled="true"/>
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaFinVigencia.mes" id="mesFinVigencia"
									size="2" maxlength="2" value="12" disabled="true" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaTasa.fechaFinVigencia.anio" id="anioFinVigencia"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" size="5" maxlength="4" />
							</td>
							<td>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</td>
							<td width="2%"></td>
						</tr></TABLE>
					</td>
				</tr>

			</table>
			</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Buscar"  onkeypress="this.onClick" onclick="return consultaTasaNBuscar();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaTasas();" value="Limpiar"/>		
		</div>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;" >
			<tr>
				<td  style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
				<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaTasa" style="text-align: center;">
				<%@include file="resumenTasaNueva.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td align="right" width="90%">
									<label for=idResultadosPorPagina>&nbsp;Mostrar resultados</label>
								</td>
								<td width="10%" align="right">
									<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()" 
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPaginaConsTasa"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaTasa();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	<div id="resultado" style="width:100%;background-color:transparent;" >
		<s:if test="%{!resumenValidacion && !resumenTramitacion && !resumenPendienteEnvioExcel && !resumenCertificadoTasasFlag && !resumenCambiosEstadoFlag}">
				<%@include file="../../includes/erroresMasMensajes.jspf" %>
		</s:if>
	</div>	
		<div id="displayTagDivConsultaTasa" class="divScroll">
			<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaListaTasaNueva" requestURI= "navegarConsultaTasas.action"
					id="tablaListaTasaNueva" summary="Listado de Tasas"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	
		
			<display:column property="codigoTasa" title="Código Tasa" sortable="true" headerClass="sortable"
							defaultorder="descending" style="width:4%" href="detalleGestionTasa.action" paramId="codigoTasa"/>	
			<display:column property="tipoTasa" title="Tipo Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
			<display:column property="fechaAlta" title="Fecha Alta" sortable="true" headerClass="sortable" format="{0,date,dd-MM-yyyy}"
					defaultorder="descending" style="width:4%" />		
			<display:column property="numExpediente" title="Núm. Expediente" sortable="true" sortProperty="tramiteTrafico.numExpediente" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
			<display:column property="fechaAsignacion" title="Fecha Asignación" sortable="true" headerClass="sortable" format="{0,date,dd-MM-yyyy}"
					defaultorder="descending" style="width:4%" />
			<display:column property="formato" title="Formato" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
			<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' 
						onKeyPress='this.onClick'/>" style="width:1%" >
						<table align="center">
							<tr>
								<td style="border: 0px;">
					  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
					  					onclick="abrirEvolucionConsultaTasas('<s:property value="#attr.tablaListaTasaNueva.codigoTasa"/>');" title="Ver evolución"/>
					  			</td>
					  			<td style="border: 0px;">
									<input type="checkbox" name="listaChecks" id="check<s:property value="##attr.tablaListaTasaNueva.codigoTasa"/>" 
										value='<s:property value="#attr.tablaListaTasaNueva.codigoTasa"/>' />
								</td>
							</tr>
						</table>
			</display:column>
		</display:table>
	</div>
			<s:if test="%{lista.getFullListSize() > 0}">
				<div id="bloqueLoadingConsultaTasas" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
					<%@include file="../../includes/bloqueLoading.jspf" %>
				</div>
			  	<div class="acciones center">
			   			<input class="boton"  type="button" id="idEliminar" name="bEliminar" " value="Eliminar" onClick="javascript:eliminarBloque();"onKeyPress="this.onClick"/>
			   			&nbsp;
			   			<input class="boton" type="button" id="idDesasignar" name="bDesasignar" value="Desasignar" onClick="javascript:desasignarBloque();" onKeyPress="this.onClick" />
			   			&nbsp;
			   			<input class="boton" type="button" id="idExportar" name="bExportar" value="Exportar" onClick="javascript:exportar();" onKeyPress="this.onClick" />
			   			&nbsp;
			   			<input class="boton" type="button" id="idGenerar" name="bGenerar" value="Generar certificado" onClick="javascript:generarCertifTasasBloque();" onKeyPress="this.onClick" />
			   			&nbsp;
			   			<input class="boton" type="button" id="idGenerarTasas" name="bGenerarTasas" value="Generar Tasas" onClick="generarTasas();return false;" onKeyPress="this.onClick" />
				 </div>
			</s:if>
			<s:if test="%{cargarFicheroTasas}">
				<s:form id="ficheroOK" method="post" action="descargarFicheroConsultaTasas">
					<s:hidden name="fileName"></s:hidden>
				</s:form>
			</s:if>
	</s:form>
	<div id="divEmergenteConsultaTasas" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaTasasEvolucion" style="display: none; background: #f4f4f4;"></div>
</div>

