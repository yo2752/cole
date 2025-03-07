<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisoInternacional/permisoInternacional.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Tramites de Permiso Internacional</span>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpPIntern">Num.Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="permisoInternacional.numExpediente" id="idNumExpPIntern" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelRefPropiaPIntern">Ref.Propia:</label>
					</td>
					<td  align="left">
						<s:textfield name="permisoInternacional.refPropia" id="idRefPropiaPIntern" size="25" maxlength="50" onblur="this.className='input';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNifTitularPIntern">Nif Titular:</label>
					</td>
					<td  align="left">
						<s:textfield name="permisoInternacional.nifTitular" id="idNifTitularPIntern" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>	
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoTramPIntern">Estado Tramite:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosTramiInterga()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
				    		name="permisoInternacional.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoPIntern"/>
					</td>	
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresionPIntern">Estado Documentacion:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getEstadosTramiteDocumentacion()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
				    		name="permisoInternacional.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresionPIntern"/>
					</td>	
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContratoPInter">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoPIntern" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="permisoInternacional.idContrato"
								/>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="permisoInternacional.idContrato"/>
					</s:else>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
       						<label for="jefaturaId">Jefatura provincial:</label>
       					</td>
       					<td align="left" nowrap="nowrap">
       						<s:select id="idJefaturaPIntern" name="permisoInternacional.jefatura" 
       							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
								headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
   						</td>
					</tr>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tieneAlgunPermisoInterga()}">	
					<tr>
						<td align="left" nowrap="nowrap">
       						<label for="jefaturaId">Jefatura provincial:</label>
       					</td>
       					<td align="left" nowrap="nowrap">
       						<s:select name="permisoInternacional.jefatura" 
       							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaInterga@getInstance().getJefaturasJPTEnum()"
								headerKey="" headerValue="Seleccione jefatura provincial" listKey="jefatura" listValue="descripcion"
								onblur="this.className='input2';" onfocus="this.className='inputfocus';" disabled="true"/>
   						</td>
					</tr>
				</s:elseif>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaAltaPIntern">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaIniPInternDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.diaInicio" id="diaFechaAltaIniPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.mesInicio" id="mesFechaAltaIniPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.anioInicio" id="anioFechaAltaIniPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIniPIntern, document.formData.mesFechaAltaIniPIntern, document.formData.diaFechaAltaIniPIntern);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaHAltaPIntern">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHastaAltaPIntern">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.diaFin" id="diaFechaAltaFinPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.mesFin" id="mesFechaAltaFinPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaAlta.anioFin" id="anioFechaAltaFinPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFinPIntern, document.formData.mesFechaAltaFinPIntern, document.formData.diaFechaAltaFinPIntern);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFechaPIntern">Fecha Presentacion:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaIniPInternDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.diaInicio" id="diaFechaPrtIniPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.mesInicio" id="mesFechaPrtIniPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.anioInicio" id="anioFechaPrtIniPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIniPIntern, document.formData.mesFechaPrtIniPIntern, document.formData.diaFechaPrtIniPIntern);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaHPIntern">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaHastaPIntern">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.diaFin" id="diaFechaPrtFinPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.mesFin" id="mesFechaPrtFinPIntern" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="permisoInternacional.fechaPresentacion.anioFin" id="anioFechaPrtFinPIntern"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFinPIntern, document.formData.mesFechaPrtFinPIntern, document.formData.diaFechaPrtFinPIntern);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bBuscarPIntern" id="idBBuscarPIntern" value="Buscar"  onkeypress="this.onClick" onclick="return buscarConsultaIntern();"/>			
			<input type="button" class="boton" name="bLimpiarPIntern" id="idBLimpiarPIntern" onclick="javascript:limpiarConsultaIntern();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaPIntern" style="text-align: center;">
				<%@include file="resumenConsultaIntern.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
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
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaConsultaPIntern" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaIntern();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaIntern" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaIntern" requestURI= "navegarConsultaPIntern.action"
				id="tablaConsultaIntern" summary="Listado de Tramites de Permiso Internacional" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="idPermiso" title="" media="none" paramId="idPermiso"/>	

				<display:column property="numExpediente" title="Num Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%;"  href="detalleAltaPermisoInternacional.action"
					paramId="numExpediente" />
				
				<display:column property="refPropia" title="Ref.Propia" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />	

				<display:column property="codigoTasa" title="Codigo Tasa" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />	
					
				<display:column property="doiTitular" title="NIF Titular" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
					
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>
				
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
					
				<display:column property="estadoDoc" title="Estado Doc." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosPIntern(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecksPIntern" id="check<s:property value="#attr.tablaConsultaIntern.idPermiso"/>" 
									value='<s:property value="#attr.tablaConsultaIntern.idPermiso"/>' />
							</td>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionPIntern('<s:property value="#attr.tablaConsultaIntern.idPermiso"/>','divEmergenteConsultaIntern');" title="Ver evolución"/>
				  			</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaIntern" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bPICambiarEstado" id="idPICambiarEstado" value="Cambiar Estado" onClick="javascript:cargarVentanaCambioEstado();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPIImprimir" id="idPIImprimir" value="Imprimir" onClick="javascript:imprimirConsultaIntern();"onKeyPress="this.onClick"/>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tieneAlgunPermisoInterga()}">
					<input type="button" class="boton" name="bPIImprimir" id="idPIImprimir" value="Imprimir" onClick="javascript:imprimirConsultaIntern();"onKeyPress="this.onClick"/>
				</s:elseif>
				<input type="button" class="boton" name="bPIValidar" id="idPIValidar" value="Validar" onClick="javascript:validarConsultaIntern();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bPITramitar" id="idPITramitar" value="Tramitar" onClick="javascript:tramitarConsultaIntern();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bPIEliminar" id="idPIEliminar" value="Eliminar" onClick="javascript:eliminarConsultaIntern();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteConsultaIntern" style="display: none; background: #f4f4f4;"></div>
