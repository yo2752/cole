<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@ taglib uri="etiquetasOegam" prefix="oegam" %>
<script src="js/remesas/remesasFunction.js" type="text/javascript"></script>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Remesas</span>
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
<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Estado Remesa:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaEstadosRemesas()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
				    		name="remesaFilter.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoRemesa"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Modelo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getModelos()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Modelo" 
				    		name="remesaFilter.modelo" listKey="valorEnum" listValue="valorEnum" id="idModeloRemesa" onchange="javascript:recargarConceptoPorModelo('idConceptoRemesa','idModeloRemesa');"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Codigo Notario:</label>
					</td>
					<td  align="left">
						<s:textfield name="remesaFilter.codNotario" id="idCodNotarioRemesa" size="10" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Nº Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="remesaFilter.numExpediente" id="idNumExpedienteRemesa" size="15" maxlength="15" onblur="this.className='input';" 
									onfocus="this.className='inputfocus';"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelNombre">Num. Colegiado:</label>
						</td>
						<td  align="left">
							<s:textfield name="remesaFilter.numColegiado" id="idNumColegiadoRemesa" size="4" maxlength="4" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</s:if>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Concepto:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2comun.modelos.utiles.UtilesRemesas@getInstance().getListaConceptosPorModelo(remesaFilter.modelo,remesaFilter.version)" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Concepto" 
				    		name="remesaFilter.concepto" listKey="concepto" listValue="descConcepto" id="idConceptoRemesa"/>
					</td>				
				<tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.diaInicio" id="diaFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.mesInicio" id="mesFechaAltaIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.anioInicio" id="anioFechaAltaIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIni, document.formData.mesFechaAltaIni, document.formData.diaFechaAltaIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.diaFin" id="diaFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.mesFin" id="mesFechaAltaFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaAlta.anioFin" id="anioFechaAltaFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFin, document.formData.mesFechaAltaFin, document.formData.diaFechaAltaFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPreDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.diaInicio" id="diaFechaPreIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.mesInicio" id="mesFechaPreIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.anioInicio" id="anioFechaPreIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPreIni, document.formData.mesFechaPreIni, document.formData.diaFechaPreIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.diaFin" id="diaFechaPreFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.mesFin" id="mesFechaPreFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="remesaFilter.fechaPresentacion.anioFin" id="anioFechaPreFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPreFin, document.formData.mesFechaPreFin, document.formData.diaFechaPreFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNif">Ref. Propia:</label>
					</td>
					<td  align="left">
						<s:textfield name="remesaFilter.referenciaPropia" id="idReferenciaPropia" size="20" maxlength="20" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return consultarRemesa();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaRemesa();" value="Limpiar"/>		
		</div>
		<br/>
		<s:if test="%{resumenValidacion}">
			<br>
			<div id="resumenValidacionRemesas" style="text-align: center;">
				<%@include file="resumenValidacionRemesas.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenCambEstado}">
			<br>
			<div id="resumenCambEstadoRemesas" style="text-align: center;">
				<%@include file="resumenCambEstadoRemesas.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenGenerarModelo}">
			<br>
			<div id="resumenGenerarRemesas" style="text-align: center;">
				<%@include file="resumenGenerarRemesas.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenPresentacion}">
			<br>
			<div id="resumenPresentarRemesas" style="text-align: center;">
				<%@include file="resumenPresentarRemesas.jspf" %>
			</div>
			<br><br>
		</s:if>
		<s:if test="%{resumenEliminacion}">
			<br>
			<div id="resumenEliminacionRemesas" style="text-align: center;">
				<%@include file="resumenEliminacionRemesas.jspf" %>
			</div>
			<br><br>
		</s:if>
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
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaRemesa();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaRemesa" requestURI= "navegarConsultaRmS.action"
					id="tablaConsultaRemesa" summary="Listado de Remesas"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	
					
				<display:column property="numExpediente" title="Nº Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%"  href="detalleConsultaRmS.action" paramId="numExpediente"/>
					
					
				<display:column property="codNotario" title="Notario" sortable="true" headerClass="sortable" 
					sortProperty="notario.codigoNotario" defaultorder="descending" style="width:3%"/>
					
				<display:column property="referenciaPropia" title="Ref. Propia" sortable="true" headerClass="sortable" 
				defaultorder="descending" style="width:3%"/>
				
				<display:column property="modelo" title="Modelo" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:4%"/>
					
				<display:column property="concepto" title="Concepto" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="importeTotal" title="Importe Total" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="descEstado" title="Estado" sortable="true" headerClass="sortable"
					sortProperty="estado" defaultorder="descending" style="width:4%"/>
					
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosRemesa(this)' 
					onKeyPress='this.onClick'/>" style="width:4%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionRemesa(<s:property value="#attr.tablaConsultaRemesa.numExpediente"/>);" title="Ver evolución"/>
				  			</td>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaRemesa.numExpediente"/>" 
									value='<s:property value="#attr.tablaConsultaRemesa.numExpediente"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<oegam:botonesPaginaConsultaRemesas/>
			<div id="bloqueLoadingRemesa" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
		</s:if>
	</s:form>
 	<div id="divEmergenteConsultaRemesa" style="display: none; background: #f4f4f4;"></div>
 	<div id="divEmergenteConsultaEvolucionRemesa" style="display: none; background: #f4f4f4;"></div>
</div>
