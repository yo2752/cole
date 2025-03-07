<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/transporte/poderes/poderesTransporte.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Poderes Transporte</span>
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
						<s:textfield name="consultaPoderTransporte.nifEmpresa" id="idNifEmpresaPodTrans" size="15" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNombreEmprNotTrans">Nif Poderdante:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaPoderTransporte.nifPoderdante" id="idNifPoderdantePodTrans" size="15" maxlength="10" onblur="this.className='input';" 
										onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<tr>
					
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoPodTrans"
								list="@org.gestoresmadrid.oegam2.transporte.utiles.UtilesVistaPoderesTransporte@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato"
								onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px"
								name="consultaPoderTransporte.idContrato"></s:select>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="consultaPoderTransporte.idContrato"/>
				</s:else>
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
									<label for="labelFechaAltaDesdePodTrans">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaPoderTransporte.fechaAlta.diaInicio" id="diaFechaAltaIniPodTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPoderTransporte.fechaAlta.mesInicio" id="mesFechaAltaIniPodTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPoderTransporte.fechaAlta.anioInicio" id="anioFechaAltaIniPodTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaIniPodTrans, document.formData.mesFechaAltaIniPodTrans, document.formData.diaFechaAltaIniPodTrans);return false;" 
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
									<s:textfield name="consultaPoderTransporte.fechaAlta.diaFin" id="diaFechaAltaFinPodTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPoderTransporte.fechaAlta.mesFin" id="mesFechaAltaFinPodTrans" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPoderTransporte.fechaAlta.anioFin" id="anioFechaAltaFinPodTrans"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaAltaFinPodTrans, document.formData.mesFechaAltaFinPodTrans, document.formData.diaFechaAltaFinPodTrans);return false;" 
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
			<input type="button" class="boton" name="bBuscarPodTrans" id="idBuscarPodTrans" value="Buscar"  onkeypress="this.onClick" onclick="return buscarPodTrans();"/>			
			<input type="button" class="boton" name="bLimpiarPodTrans" id="idLimpiarPodTrans" onclick="javascript:limpiarConsultaPodTrans();" value="Limpiar"/>		
		</div>
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
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPaginaConsultaNotTrans"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaConsultaPodTrans();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaPodTrans" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaPodTrans" requestURI= "navegarConsultaPoderes.action"
					id="tablaConsultaPodTrans" summary="Listado de Poderes de Transporte"
					excludedParams="*" sort="list"				
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">	

				<display:column property="idPoderTransporte" title="" media="none" paramId="idPoderTransporte"/>
				
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="contrato" title="Contrato" sortable="true" headerClass="sortable " 
						defaultorder="descending" style="width:6%"/>
				</s:if>
				
				<display:column property="nifEmpresa" title="NIF Empresa" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:3%"/>	
						
				<display:column property="nombreEmpresa" title="Nombre Empresa" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:7%"/>	
				
				<display:column property="nifPoderdante" title="NIF Poderdante" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
					
				<display:column property="poderdante" title="Nombre Poderdante" sortable="true" headerClass="sortable"
						sortProperty="nombrePoderdante"  defaultorder="descending" style="width:7%"/>	
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosConsultaPodTrans(this)' 
					onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaPodTrans.idPoderTransporte"/>" 
									value='<s:property value="#attr.tablaConsultaPodTrans.idPoderTransporte"/>' />
								<input type="hidden" id="idCheckPodTrans<s:property value="#attr.tablaConsultaPodTrans.idPoderTransporte"/>" value="<s:property value="#attr.tablaConsultaNotTrans.idNotificacionTransporte"/>">
							</td>
							<td style="border: 0px;">
				  				<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirDetallePoder('<s:property value="#attr.tablaConsultaPodTrans.idPoderTransporte"/>');" title="Ver Detalle"/>
				  			</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaPodTrans" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bDescargarPdf" id="idDescargarPdf" value="Descargar" onClick="javascript:descargarPoderesBloque();"
					onKeyPress="this.onClick"/>	
			</div>
		</s:if>
	</s:form>
</div>
