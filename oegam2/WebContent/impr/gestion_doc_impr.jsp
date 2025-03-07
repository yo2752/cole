<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/impr/gestionDocImpr.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión DocumentaciónIMPR</span>
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
						<label for="labelDocId">Doc.Id:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaDocImpr.docImpr" id="idDocIdImpr" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaDocImpr.matricula" id="idMatriculaImpr" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelCarpeta">Carpeta:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaDocImpr.carpeta" id="idCarpetaImpr" size="15" maxlength="15" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelnumExp">Num.Expediente:</label>
					</td>
						<td  align="left">
						<s:textfield name="consultaDocImpr.numExpediente" id="idNumExpedienteImpr" size="15" maxlength="15" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelJefatura">Jefatura:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getJefatura()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaDocImpr.jefatura" listKey="descripcion" listValue="descripcion" id="idJefatura"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Trámite:</label>
					</td>
					<td  align="left">
						<s:select id="idTipoTramiteImpr" list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getListaTipoTramitesIMPR()"
							onblur="this.className='input2';" headerValue="TODOS" onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey="" 
							listValue="nombreEnum" cssStyle="width:95px" name="consultaDocImpr.tipoTramite"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoImpr">Tipo IMPR:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getListaTipoImprConsulta()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaDocImpr.tipoImpr" listKey="valorEnum" listValue="nombreEnum" id="idTipoImpr"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstado">Estado:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegamComun.utiles.UtilesVistaTramites@getInstance().getEstadosSolicitud()" onblur="this.className='input2';" 
				    		onfocus="this.className='inputfocus';"  headerKey="" headerValue="TODOS" 
				    		name="consultaDocImpr.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
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
									<label for="labelFechaPrtDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.anioInicio" id="anioFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIni, document.formData.mesFechaPrtIni, document.formData.diaFechaPrtIni);return false;" 
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
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaAlta.anioFin" id="anioFechaPrtFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFin, document.formData.mesFechaPrtFin, document.formData.diaFechaPrtFin);return false;" 
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
						<label for="labelFecha">Fecha Impr:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaImprDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaImpresion.diaInicio" id="diaFechaImprIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaImpresion.mesInicio" id="mesFechaImprIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="cconsultaDocImpr.fechaImpresion.anioInicio" id="anioFechaImprIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaImprIni, document.formData.mesFechaImprIni, document.formData.diaFechaImprIni);return false;" 
	    								title="Seleccionar fecha">
	    								<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
	    							</a>
								</td>
							</tr>
						</table>
					</td>
					<td align="left">
						<label for="labelFechaH">Fecha Impr:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaImprHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaImpresion.diaFin" id="diaFechaImprFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaImpresion.mesFin" id="mesFechaImprFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDocImpr.fechaImpresion.anioFin" id="anioFechaImprFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaImprFin, document.formData.mesFechaImprFin, document.formData.diaFechaImprFin);return false;" 
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
			<input type="button" class="boton" name="bConsultaImpr" id="idBConsultaImpr" value="Consultar"  onkeypress="this.onClick" onclick="javascript:buscarDocImpr();"/>			
			<input type="button" class="boton" name="bLimpiarImpr" id="idBLimpiarImpr" onclick="javascript:limpiarConsultaDocImpr();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaImpr" style="text-align: center;">
				<%@include file="resumenErroresImpr.jspf" %>
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
										id="idResultadosPorPaginaConsDocImpr" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaDocImpr();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaDocImpr" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaDocImpr" requestURI= "navegarGestDocImpr.action"
				id="tablaConsultaDocImpr" summary="Listado Documentación IMPR" excludedParams="*" sort="list"				
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="id" title="ID" sortable="true" headerClass="sortable"
					defaultorder="descending"/>
				
				<display:column property="docImpr" title="Doc.Id" sortable="true" headerClass="sortable"
					defaultorder="descending"/>
					
			    <display:column property="tipoImpr" title="Tipo IMPR" sortable="false"  headerClass="sortable" 
					defaultorder="descending"/>		
					
			   <display:column property="carpeta" title="Carpeta" sortable="false"  headerClass="sortable" 
					defaultorder="descending"/>
					
			   <display:column property="estado" title="Estado" sortable="false"  headerClass="sortable" 
				defaultorder="descending"/>			
				
			   <display:column property="fechaImpresion" title="Fecha Impresión" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
			
			    <display:column property="fechaAlta" title="Fecha Alta" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
			
			    <display:column property="tipoTramite" title="Tipo Tramite" sortable="true" headerClass="sortable"
						defaultorder="descending"/>
				
				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosImpr(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
				  			<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaDocImpr.id"/>" 
									value='<s:property value="#attr.tablaConsultaDocImpr.id"/>' />
							</td>
						</tr>
					</table>
				</display:column>
		
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaDocImpr" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="boton" name="bImpManual" id="idBImpManual" value="Imp. Manual" onClick="javascript:impresionManual();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bDescgManual" id="idBDescgManual" value="Descg. Manual" onClick="javascript:descargaManual();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bCBEstado" id="idBCBEstado" value="Cambiar Estado" onClick="javascript:cambiarEstado();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bReactiva" id="idBReactivar" value="Reactivar" onClick="javascript:reactivar();"onKeyPress="this.onClick"/>
				<input type="button" class="botonGrande" name="bImprimir" id="idBImprimir" value="Imprimir" onClick="javascript:imprimir();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bAnular" id="idAnular" value="Anular" onClick="javascript:anular();"onKeyPress="this.onClick"/>	
				<input type="button" class="boton" name="bReiniciar" id="idBReiniciar" value="Reiniciar IMPR" onClick="javascript:reiniciar();"onKeyPress="this.onClick"/>
				<input type="button" class="botonGrande" name="bErrorMail" id="idBErrorMail" value="Enviar Mail" onClick="javascript:errorMail();"onKeyPress="this.onClick"/>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaDocImpr" style="display: none; background: #f4f4f4;"></div>
</div>
