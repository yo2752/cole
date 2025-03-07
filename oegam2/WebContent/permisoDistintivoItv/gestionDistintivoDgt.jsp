<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisosFichasDistintivosDgt/distintivoDgt.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular">
				<span class="titulo">Gestión Distintivo MedioAmbiental</span>
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
						<s:textfield name="consultaDistintivo.matricula" id="idMatriculaDstv" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaDistintivo.bastidor" id="idBastidorDstv" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaDistintivo.numExpediente" id="idNumExpedienteDstv" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="refPropia">Referencia Propia:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaDistintivo.refPropia" id="idRefPropiaDstv" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="30" maxlength="50"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoDistintivo">Tipo Distintivo:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Distintivo"
							name="consultaDistintivo.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoDstv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDistintivo">Distintivo:</label>
					</td>
					<td  align="left">
						<s:select id="idDistintivoDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getListaTipoSINO()"
							onblur="this.className='input2';" headerValue="TODOS" onfocus="this.className='inputfocus';" listKey="codigo" headerKey=""
							listValue="descripcion" cssStyle="width:95px" name="consultaDistintivo.distintivo"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoDstv">Estado Peticion Distintivo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosDistintivoConsulta()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado"
							name="consultaDistintivo.estadoSolicitud" listKey="valorEnum" listValue="nombreEnum" id="idEstadoPetDstv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresion">Estado Petición Impresión:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosImpresion()"
						onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
							name="consultaDistintivo.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresion"/>
					</td>
				</tr>
				<tr>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td align="left">
							<s:select id="idContratoDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="consultaDistintivo.idContrato"
								/>
						</td>
					</s:if>
					<s:else>
						<s:hidden name="consultaDistintivo.idContrato"/>
					</s:else>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">NIF Titular:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaDistintivo.nif" id="idNifTitularDstv" size="9" maxlength="9" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
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
									<s:textfield name="consultaDistintivo.fechaPresentacion.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDistintivo.fechaPresentacion.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDistintivo.fechaPresentacion.anioInicio" id="anioFechaPrtIni"
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
						<label for="labelFechaH">Fecha de Presentación:</label>
					</td>
					<td align="left">
						<table style="width:20%">
							<tr>
								<td align="right">
									<label for="labelFechaPrtHasta">hasta:</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDistintivo.fechaPresentacion.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDistintivo.fechaPresentacion.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaDistintivo.fechaPresentacion.anioFin" id="anioFechaPrtFin"
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
			<input type="button" class="boton" name="bConsultaDstv" id="idBConsultaDstv" value="Consultar"  onkeypress="this.onClick" onclick="javascript:buscarConsultarDistintivo();"/>			
			<input type="button" class="boton" name="bLimpiarDstv" id="idBLimpiarDstv" onclick="javascript:limpiarConsultaDstv();" value="Limpiar"/>		
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaPrmDstv" style="text-align: center;">
				<%@include file="resumenDistintivoDgt.jspf" %>
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
										id="idResultadosPorPaginaConsDstv" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaDstv();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaDstv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaDstv" requestURI= "navegarGestionDstv.action"
				id="tablaConsultaDstv" summary="Listado de Tramites con Distintivos" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column property="refPropia" title="Ref.Propia" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column property="matricula" title="Matricula" sortable="false" headerClass="sortable" sortProperty="vehiculo.matricula"
					defaultorder="descending" style="width:2%" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:7%" />
				</s:if>
				<display:column property="estadoDstv" title="Estado Dstv" sortable="true" sortProperty="estadoPetPermDstv" headerClass="sortable"
						defaultorder="descending" style="width:4%" />

				<display:column property="tipoDistintivo" title="Tipo Distintivo" sortable="true" sortProperty="tipoDistintivo" headerClass="sortable" 
					defaultorder="descending" style="width:2%" />

				<display:column property="estadoPetImp" title="Estado Pet.Impresion" sortable="true" headerClass="sortable" sortProperty="estadoImpDstv" 
								defaultorder="descending" style="width:7%"/>
				<display:column  style="width:1%">

					<table align="center">
						<s:if test="%{#attr.tablaConsultaDstv.estadoDstv != null && #attr.tablaConsultaDstv.estadoDstv.equals(@org.gestoresmadrid.core.docPermDistItv.model.enumerados.EstadoPermisoDistintivoItv@Finalizado_Error.getNombreEnum())}">
							<td style="border: 0px;">
							<div>
								<img src="img/botonDameInfo.gif"  style="margin-right:10px;height:15px;width:15px;cursor:pointer";
								 title ='<s:property value="#attr.tablaConsultaDstv.respPetPermDstv"/>'/>
							</div>
							</td>
						</s:if>
					</table>

				</display:column>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosDstv(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaDstv.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaDstv.numExpediente"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionDstv(<s:property value="#attr.tablaConsultaDstv.numExpediente"/>,'divEmergenteConsultaDistintivo');" title="Ver evolución"/>
							</td>
							
						</tr>
					</table>
				</display:column>

			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaDstv" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="botonGrande" name="bSolDstv" id="idBSolDstv" value="Solicitar Distintivo" onClick="javascript:solicitarDstvBloque();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bGenerarDist" id="idGenerarDist" value="Generar Distintivo" onClick="javascript:generarDistintivo();"onKeyPress="this.onClick"/>	
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="botonGrande" name="bCmbEstadoSolDstv" id="idBCmbEstadoSolDstv" value="Cambiar Estado" onClick="javascript:cambiarEstadoDstvBloque();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bGenerarDemandaDstv" id="idGenerarDemandaDstv" value="Gen.Demanda Dstv" onClick="javascript:generarDemandaDstv();"onKeyPress="this.onClick"/>	
					<input type="button" class="boton" name="bRevertirDstv" id="idRevertirDstv" value="Revertir" onClick="javascript:revertirDstv();"onKeyPress="this.onClick"/>	
					<input type="button" class="botonGrande" name="bDesasignarDocumento" id="idDesasignarDocumento" value="Desasignar Documento" onClick="javascript:desasignarDocumentoDstv();"onKeyPress="this.onClick"/>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoImpresionDstvGestor()}">
					<input type="button" class="boton" name="bGenerarDemandaDstv" id="idGenerarDemandaDstv" value="Gen.Demanda Dstv" onClick="javascript:generarDemandaDstv();"onKeyPress="this.onClick"/>
				</s:elseif>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaDistintivo" style="display: none; background: #f4f4f4;"></div>
</div>