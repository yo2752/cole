<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/transporte/notificaciones/notificacionesTransporte.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Notificaciones Transporte</span>
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
						<label for="labelNifEmpresaNotTrans">NIF Empresa:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaNotificacionTransporte.nifEmpresa" id="idNifEmpresaNotTrans" size="15" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNombreEmprNotTrans">Nombre Empresa:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaNotificacionTransporte.nombreEmpresa" id="idNombreEmpresaNotTrans" size="30" maxlength="70" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaNotificacionTransporte@getInstance().getEstadosNotificaciones()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey=""	headerValue="Seleccione Estado" 
				    		name="consultaNotificacionTransporte.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstadoNotTrans"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoNotTrans"
								list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaNotificacionTransporte@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="consultaNotificacionTransporte.idContrato"></s:select>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="consultaNotificacionTransporte.idContrato"/>
					</s:else>
				</tr>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaDesdeNotTrans">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.diaInicio" id="diaFechaAltaIniNotTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.mesInicio" id="mesFechaAltaIniNotTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.anioInicio" id="anioFechaAltaIniNotTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIniNotTrans, document.formData.mesFechaAltaIniNotTrans, document.formData.diaFechaAltaIniNotTrans);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaAltaHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.diaFin" id="diaFechaAltaFinNotTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.mesFin" id="mesFechaAltaFinNotTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaNotificacionTransporte.fechaAlta.anioFin" id="anioFechaAltaFinNotTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFinNotTrans, document.formData.mesFechaAltaFinNotTrans, document.formData.diaFechaAltaFinNotTrans);return false;" 
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
			<input type="button" class="boton" name="bBuscarNotTrans" id="idBuscarNotTrans" value="Buscar"  onkeypress="this.onClick" onclick="return buscarNotTrans();"/>			
			<input type="button" class="boton" name="bLimpiarNotTrans" id="idLimpiarNotTrans" onclick="javascript:limpiarConsultaNotTrans();" value="Limpiar"/>		
		</div>
		<br/>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaNotTrans" style="text-align: center;">
				<%@include file="resumenConsultaNotificacionesTrans.jspf" %>
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
												id="idResultadosPorPaginaConsultaNotTrans"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaNotTrans();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaNotTrans" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaNotTrans" requestURI= "navegarNotifTransporte.action"
					id="tablaConsultaNotTrans" summary="Listado de Consultas Notificaciones de Transporte"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	

				<display:column property="idNotificacionTransporte" title="" media="none" paramId="idNotificacionTransporte"/>
				
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="contrato" title="Contrato" sortable="true" headerClass="sortable " 
						defaultorder="descending" style="width:6%"/>
				</s:if>
				
				<display:column property="nifEmpresa" title="NIF Empresa" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:3%"/>	
						
				<display:column property="nombreEmpresa" title="Nombre Empresa" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:7%"/>	
				
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaNotTrans(this)' 
					onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaNotTrans.idNotificacionTransporte"/>" 
									value='<s:property value="#attr.tablaConsultaNotTrans.idNotificacionTransporte"/>' />
								<input type="hidden" id="idCheckNotTrans<s:property value="#attr.tablaConsultaNotTrans.idNotificacionTransporte"/>" value="<s:property value="#attr.tablaConsultaNotTrans.idNotificacionTransporte"/>">
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaNotTrans" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bImprimirPdf" id="idImprimirPdf" value="Aceptar" onClick="javascript:imprimirNotTransBloque();"
					onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bRechazarNotTrans" id="idRechazarNotTrans" value="Rechazar" onClick="javascript:rechazarNotTransBloque();"
					onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bDescargarNotTrans" id="idDescargarNotTrans" value="Descargar Pdfs" onClick="javascript:descargarNotTransBloque();"
					onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
</div>
