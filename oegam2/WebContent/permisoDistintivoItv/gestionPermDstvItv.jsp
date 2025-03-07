<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/gestionPermDstvFichas.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
			<s:if test="%{donde == 'trafico'}">
				<span class="titulo">Imprimir Distintivos</span>
			</s:if>
			<s:else>
				<span class="titulo">Gestionar Documentación DGT</span>	
			</s:else>
			</td>
		</tr>
	</table>
</div>

<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm" 
	scrolling="no" frameborder="0" style="visibility:visible; z-index:999; position:absolute; top:-500px; left:-500px;">
</iframe>

<div>
	<s:form method="post" id="formData" name="formData">
		<s:hidden id="donde" name="donde"/>
		<%@include file="../../../includes/erroresYMensajes.jspf" %>
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Id.Doc:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermDstvItvFilterBean.docIdPerm" id="idDocId" size="16" maxlength="16" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Tipo Documento:</label>
						</td>
						<td align="left">
							<s:select id="idTipoDocumento" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTiposDocumentos()"
								onblur="this.className='input2';" headerValue="Seleccione Tipo Doc." onfocus="this.className='inputfocus';" listKey="valorEnum" headerKey=""
								listValue="nombreEnum" cssStyle="width:220px" name="gestionPermDstvItvFilterBean.tipoDocumento"
							/>
						</td>
					</s:if>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermDstvItvFilterBean.matricula" id="idMatriculaPrmDstv" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num.Expediente:</label>
					</td>
					<td  align="left">
						<s:textfield name="gestionPermDstvItvFilterBean.numExpediente" id="idNumExpedientePrmDstv" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoPrmDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="gestionPermDstvItvFilterBean.idContrato"
								/>
						</td>
						<td align="left" nowrap="nowrap">
							<label for="labelJefatura">Jefatura:</label>
						</td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@tienePermisoImprJefatura()}">
							<td  align="left">
								<s:select id="idJefaturaPrmDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getJefaturasJPTEnum()"
									onblur="this.className='input2';" headerValue="Seleccione Jefatura" onfocus="this.className='inputfocus';" listKey="jefatura" headerKey=""
									listValue="descripcion" cssStyle="width:220px" name="gestionPermDstvItvFilterBean.jefatura" disabled="true"/>
							</td>
						</s:if>
						<s:else>
							<td  align="left">
								<s:select id="idJefaturaPrmDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getJefaturasJPTEnum()"
									onblur="this.className='input2';" headerValue="Seleccione Jefatura" onfocus="this.className='inputfocus';" listKey="jefatura" headerKey=""
									listValue="descripcion" cssStyle="width:220px" name="gestionPermDstvItvFilterBean.jefatura"/>
							</td>
						</s:else>
					</tr>
				</s:if>
				<s:else>
					<s:hidden id="idContratoFilter" name="gestionPermDstvItvFilterBean.idContrato"/>
					<s:hidden name="gestionPermDstvItvFilterBean.jefatura"/>
				</s:else>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoJustif">Estado:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosImpresion()"
							onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado"
							name="gestionPermDstvItvFilterBean.estado" listKey="valorEnum" listValue="nombreEnum" id="idEstado"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDistintivo">Tipo Distintivo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Distintivo"
							name="gestionPermDstvItvFilterBean.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoPrmDstv"/>
					</td>
				</tr>
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
									<label for="labelFechaPrtDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.anioInicio" id="anioFechaPrtIni"
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
						<label for="labelFechaH">Fecha de Alta:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermDstvItvFilterBean.fechaAlta.anioFin" id="anioFechaPrtFin"
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
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bPDIConsultaPrmDstv" id="idBPDIConsultaPrmDstv" value="Consultar"  onkeypress="this.onClick" onclick="return buscarGestionPDI();"/>			
			<input type="button" class="boton" name="bPDILimpiarPrmDstv" id="idBPDILimpiarPrmDstv" onclick="javascript:limpiarGestionPDI();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaPrmDstv" style="text-align: center;">
				<%@include file="resumenPermisoDistintivo.jspf" %>
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
										id="idResultadosPorPaginaPDI" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaPDI();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivPDI" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaGestionPrmDstvItv" requestURI= "navegarGestionPDI.action"
				id="tablaGestionPrmDstvItv" summary="Listado de Tramites con Permisos/Distintivos/Itv" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">	

				<display:column property="docId" title="docId" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%"  href="buscarTramitesDetalleDocumento.action" paramId="docId" sortProperty="docIdPerm"/>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="tipoDocumento" title="Tipo de documento" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:1%" sortProperty="tipo"/>
				</s:if>

				<display:column property="tipoDistintivo" title="Tipo Distintivo" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="tipoDistintivo"/>	

				<display:column property="fechaImpresion" title="Fecha Impresion" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%"/>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="contrato" title="Contrato" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="contrato.colegiado.numColegiado"/>
				</s:if>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="jefatura" title="Jefatura" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:1%" />
				</s:if>
					
				<display:column property="fechaAlta" title="Fecha Alta"	sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" format="{0,date,dd/MM/yyyy}" />
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />	

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosPDI(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaGestionPrmDstvItv.docId"/>"
									value='<s:property value="#attr.tablaGestionPrmDstvItv.docId"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
									onclick="abrirEvolucionGestionDoc('<s:property value="#attr.tablaGestionPrmDstvItv.docId"/>','divEmergenteEvolucionPDI');" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingGestionPrmDstv" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosCiudadReal_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('CR');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('CR');"onKeyPress="this.onClick"/>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosCuenca_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('CU');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('CU');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosGuadalajara_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('GU');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('GU');"onKeyPress="this.onClick"/>				
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosSegovia_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('SG');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('SG');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosAvila_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('AV');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('AV');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosAlcorcon_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('M1');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('M1');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIImprimir" id="idPDIImprimir" value="Imprimir Manual" onClick="javascript:imprimirDocumento('M1');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIDescargar" id="idPDIDescargar" value="Descg. Manual" onClick="javascript:descargarDocumento('M1');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosAlcala_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('M2');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('M2');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisosMadrid_IMPR()}">
					<input type="button" class="boton" name="bPDIImprimirAuto" id="idPDIImprimirAuto" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('M');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('M');"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bPDIImprimir" id="idPDIImprimir" value="Imprimir Manual" onClick="javascript:imprimirDocumento('CO');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIDescargar" id="idPDIDescargar" value="Descg. Manual" onClick="javascript:descargarDocumento('CO');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDICBEstado" id="idPDICBEstado" value="Cambiar Estado" onClick="javascript:cambiarEstadoDocumento();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIReactivar" id="idPDIReactivar" value="Reactivar" onClick="javascript:reactivarDocumentoJefatura('CO');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIImprimirAutoCole" id="idPDIImprimirAutoCole" value="Imprimir" onClick="javascript:imprimirDocumentoAuto('CO');"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bPDIAnularCole" id="idPDIAnularCole" value="Anular" onClick="javascript:anularDocumento('CO');"onKeyPress="this.onClick"/>
					<input type="button" class="botonGrande" name="bReiniciarImpr" id="idBReiniciarImpr" value="Reiniciar IMPR" onClick="javascript:reiniciarImpr();"onKeyPress="this.onClick"/>
				</s:elseif>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoImpresionDstvGestor()}">
					<input type="button" class="boton" name="bImprimirDstvGestor" id="idImprimirDstvGestor" value="Imprimir Distintivo" onClick="javascript:imprimirDocumentoGestor('gestor');"onKeyPress="this.onClick"/>
				</s:elseif>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteEvolucionPDI" style="display: none; background: #f4f4f4;"></div>
	<div id="divEmergenteConsultaDocumentos" style="display: none; background: #f4f4f4;"></div>
</div>