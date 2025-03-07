<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/permisoDgt.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión Permisos DGT</span>
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
						<label for="labelMatricula">Matricula:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermisos.matricula" id="idMatriculaPrm" size="10" maxlength="10" onblur="this.className='input';"
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermisos.bastidor" id="idBastidorPrm" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermisos.numExpediente" id="idNumExpedientePrm" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNumSeriePermiso">Num. Serie Permiso:</label>
					</td>

					<td align="left">
						<s:textfield name="gestionPermisos.numSeriePermiso" id="idNumSeriePermisoPrm" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoPermDstv">Estado Solicitud Permiso:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosEitvConsulta()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
							name="gestionPermisos.estadoSolicitud" listKey="valorEnum" listValue="nombreEnum" id="idEstadoSolPrm"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresion">Estado Peticion Impresión:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosImpresion()"
						onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Estado" 
							name="gestionPermisos.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresionPrm"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Tipo Trámite:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTipoTramite()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Tipo Trámite" 
							name="gestionPermisos.tipoTramite" listKey="valorEnum" listValue="nombreEnum" id="idTipoTramite" onChange="activarTipTran()"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">NIF Titular:</label>
					</td>
					<td align="left">
						<s:textfield name="gestionPermisos.nif" id="idNifTitular" size="9" maxlength="9" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoTramite">Jefatura:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getJefaturasJPTEnum()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Jefatura" 
							name="gestionPermisos.jefaturaTrafico" listKey="jefatura" listValue="descripcion" id="idTipoJefatura"/>
					</td>
					<td align="left">
						<label for="conNive">Nive: </label>
					</td>
					<td align="left">
						<s:select 
							list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadoConSinNive()"
							name="gestionPermisos.nive" 
							id="conNive" 
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							listKey="valorEnum"
							listValue="nombreEnum"
							title="NIVE"
							disabled="false" />
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoPrm" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
								listValue="descripcion" cssStyle="width:220px" name="gestionPermisos.idContrato"
								/>
						</td>
						<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().esTipoTramiteCtit(gestionPermisos)}">
							<td align="left" nowrap="nowrap" id="lblTipoTrans">
								<label for="labelTipoTramite">Tipo Transferencia:</label>
							</td>
							<td align="left" id="comboTipoTrans">
								<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTipoTransferencia()" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" headerKey="" headerValue="Seleccione Tipo Transferencia" 
									name="gestionPermisos.tipoTransferencia" listKey="valorEnum" listValue="nombreEnum" id="idTipoTransferencia"/>
							</td>
						</s:if>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="gestionPermisos.idContrato"/>
				</s:else>
			</table>
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelFecha">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtDesde">Desde: </label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.diaInicio" id="diaFechaPrtIniPrm"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.mesInicio" id="mesFechaPrtIniPrm" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.anioInicio" id="anioFechaPrtIniPrm"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtIniPrm, document.formData.mesFechaPrtIniPrm, document.formData.diaFechaPrtIniPrm);return false;" 
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
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.diaFin" id="diaFechaPrtFinPrm" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.mesFin" id="mesFechaPrtFinPrm" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="gestionPermisos.fechaPresentacion.anioFin" id="anioFechaPrtFinPrm"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFinPrm, document.formData.mesFechaPrtFinPrm, document.formData.diaFechaPrtFinPrm);return false;"
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario" />
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bPDIConsultaPrm" id="idBPDIConsultaPrm" value="Consultar" onkeypress="this.onClick" onclick="return buscarGestionPrm();" />
			<input type="button" class="boton" name="bPDILimpiarPrm" id="idBPDILimpiarPrm" onclick="javascript:limpiarGestionPrm();" value="Limpiar" />
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenGestionPrm" style="text-align: center;">
				<%@include file="resumenPermisoDgt.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0" style="width:100%;" >
			<tr>
				<td style="width:100%;text-align:center;">Resultado de la b&uacute;squeda</td>
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
										id="idResultadosPorPaginaGestionPrm" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaGestionPrm();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivGestionPrm" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaGestionPrm" requestURI= "navegarGestionPrm.action"
				id="tablaGestionPrm" summary="Listado de Tramites con Permisos" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="docId" title="docId" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="docPermisoVO.docIdPerm"/>

				<display:column property="jefaturaTrafico" title="Jefatura Provincial" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="jefaturaTrafico"/>

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%" sortProperty="numColegiado"/>
				</s:if>

				<display:column property="numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="numExpediente"/>

				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="vehiculo.matricula"/>

				<display:column property="tipoTramite" title="Tipo Tramite" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="tipoTramite"/>	

				<display:column property="estadoSolicitud" title="Estado Sol." sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="estadoSolPerm"/>

				<display:column property="estadoPetImp" title="Estado Impresion" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="estadoImpPerm"/>

				<display:column property="numImpresiones" title="Num.Impr" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" sortProperty="numImpresionesPerm"/>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosPrm(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecksPrm" id="check<s:property value="#attr.tablaGestionPrm.numExpediente"/>"
									value='<s:property value="#attr.tablaGestionPrm.numExpediente"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
									onclick="abrirEvolucionPrm(<s:property value="#attr.tablaGestionPrm.numExpediente"/>,'divEmergenteGestionPrm');" title="Ver evolución"/>
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingGestionPrm" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="boton" name="bCbEstadoSolPrm" id="idBCbEstadoSolPrm" value="Cambiar Estado" onClick="javascript:cambiarEstadoSolPrm();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bSolicitarPrm" id="idBSolicitarPrm" value="Solicitar" onClick="javascript:solicitarPrm();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bImprimirPrm" id="idBImprimirPrm" value="Imprimir" onClick="javascript:imprimirPrm();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bRevertirPrm" id="idBRevertirPrm" value="Revertir" onClick="javascript:revertirPrm();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bGenKOPrm" id="idBGenKOPrm" value="Generar KO" onClick="javascript:generarKOPrm();"onKeyPress="this.onClick"/>
					<input type="button" class="botonGrande" name="bDesasignarDocumento" id="idDesasignarDocumento" value="Desasignar Documento" onClick="javascript:desasignarDocumentoPrm();"onKeyPress="this.onClick"/>
				</s:if>
			</div>
		</s:if>
	</s:form>
</div>
<div id="divEmergenteGestionPrm" style="display: none; background: #f4f4f4;"></div>