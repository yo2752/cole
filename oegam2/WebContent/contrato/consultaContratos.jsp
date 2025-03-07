<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/bajaBotones.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Contratos</span>
			</td>
		</tr>
	</table>
</div>
<iframe width="174" 
	height="189" 
	name="gToday:normal:agenda.js:fechaConsultaContrato" 
	id="gToday:normal:agenda.js:fechaConsultaContrato" 
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
						<label for="labelCif">CIF:</label>
					</td>
					<td  align="left">
						<s:textfield name="contratoFilter.cif" id="idCif" size="15" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getEstadosContratos()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
				    		name="contratoFilter.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelProvincia">Provincia:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaContrato@getInstance().getListaProvincias()" onblur="this.className='input2';" 
					    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Provincia" 
					    		name="contratoFilter.idProvincia" listKey="idProvincia" listValue="nombre" id="idProvincia"/>
					    	
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelNumColegiado">Número Colegiado:</label>
						</td>
						<td  align="left">
							<s:textfield name="contratoFilter.numColegiado" id="idNumColegiado" size="6" maxlength="4" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
						</td>
					</tr>
				</s:if>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelRazSocial">Razón Social:</label>
					</td>
					<td  align="left">
						<s:textfield name="contratoFilter.razonSocial" id="idRazonSocial" size="35" maxlength="30" onblur="this.className='input';" 
									onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>		
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">	
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha caducidad alias:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaActualizaDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.diaInicio" id="diaFechaCaducidadIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.mesInicio" id="mesFechaCaducidadIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.anioInicio" id="anioFechaCaducidadIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.fechaConsultaContrato)fechaConsultaContrato.fPopCalendarSplit(document.formData.anioFechaCaducidadIni, document.formData.mesFechaCaducidadIni, document.formData.diaFechaCaducidadIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaActualizaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.diaFin" id="diaFechaCaducidadFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.mesFin" id="mesFechaCaducidadFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="contratoFilter.fechaCaducidad.anioFin" id="anioFechaCaducidadFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.fechaConsultaContrato)fechaConsultaContrato.fPopCalendarSplit(document.formData.anioFechaCaducidadFin, document.formData.mesFechaCaducidadFin, document.formData.diaFechaCaducidadFin);return false;" 
					  					title="Seleccionar fecha">
					  					<img class="PopcalTrigger"  align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
	   		    					</a>
								</td>
							</tr>
						</table>
					</td>
				</s:if>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar"  onkeypress="this.onClick" onclick="return buscarContratos();"/>			
			<input type="button" class="boton" name="bLimpiar" id="bLimpiar" onclick="javascript:limpiarConsultaContratos();" value="Limpiar"/>		
		</div>
		<br/>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenContratos" style="text-align: center;">
				<%@include file="resumenContratos.jspf" %>
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
												onchange="cambiarElementosPorPaginaConsultaContratos();">
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
					uid="tablaConsultaContrato" requestURI= "navegarConsultaContrato.action"
					id="tablaConsultaContrato" summary="Listado de Contratos"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	

				<display:column property="idContrato" title="" media="none" paramId="idContrato"/>
				
				<display:column property="cif" title="CIF" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:2%"/>	
			
				<display:column property="provincia" title="Provincia" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:4%"/>
			
				<display:column property="razonSocial" title="Razón Social" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:5%"/>
				
				<display:column property="via" title="Vía" sortable="true" headerClass="sortable " 
					defaultorder="descending" style="width:10%"/>
					
				<display:column property="numColegiado" title="Num.Colegiado" sortable="true" headerClass="sortable" 
					defaultorder="descending" style="width:1%" sortProperty="colegiado.numColegiado"/>
					
				<display:column property="alias" title="Alias" sortable="true" sortProperty="colegiado.alias" headerClass="sortable" 
					defaultorder="descending" style="width:1%"/>
				
				<display:column property="fechaCaducidad" title="Fecha Caducidad" sortProperty="colegiado.fechaCaducidad" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" format="{0,date,dd/MM/yyyy}"/>
					
				<display:column property="estado.nombreEnum" title="estado" sortProperty="estadoContrato" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">	
					<display:column property="mobileGest.nombreEnum" title="Mobile Gest" sortProperty="mobileG" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:2%" />	
				</s:if>	
				<display:column title="<input type='checkbox' name='checkAll'  onClick='marcarTodos(this, document.formData.listaChecks);' 
					onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucionContrato(<s:property value="#attr.tablaConsultaContrato.idContrato"/>);" title="Ver evolución"/>
				  			</td>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaContrato.idContrato"/>" 
									value='<s:property value="#attr.tablaConsultaContrato.idContrato"/>' />
								<input type="hidden" id="idCheckConsultaDev<s:property value="#attr.tablaConsultaContrato.idContrato"/>" value="<s:property value="#attr.tablaConsultaContrato.idContrato"/>">
							</td>
							<td style="border: 0px;">
				  				<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="recuperarContrato('<s:property value="#attr.tablaConsultaContrato.idContrato"/>');" title="Ver Detalle"/>
				  			</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0 && @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
			<div id="bloqueLoadingContratos" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bHabilitar" id="idHabilitar" value="Habilitar"  onkeypress="this.onClick" onclick="return habilitarContratos();"/>			
				<input type="button" class="boton" name="bDeshabilitar" id="idDeshabilitar" value="Deshabilitar" onclick="return deshabilitarContratos();"/>		
				<input type="button" class="boton" name="bEliminar" id="idEliminar" value="Eliminar"  onkeypress="this.onClick" onclick="return eliminarContratos();"/>
				<input type="button" class="boton" name="bActualizarAlias" id="idActualizarAlias" value="Actualizar alias"  onkeypress="this.onClick" onclick="return actualizarAliasContratos();"/>				
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaContrato" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaContratoEvolucion" style="display: none; background: #f4f4f4;"></div>
</div>
