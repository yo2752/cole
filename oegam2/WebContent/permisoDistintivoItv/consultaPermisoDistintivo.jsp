<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/permisoDistintivo/permisoDistintivo.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Permiso / Distintivo MedioAmbiental</span>
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
						<s:textfield name="consultaPermisoDistintivo.matricula" id="idMatriculaPrmDstv" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"  />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaPermisoDistintivo.bastidor" id="idBastidorPrmDstv" size="25" maxlength="25" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNumExpediente">Num.Expediente:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaPermisoDistintivo.numExpediente" id="idNumExpedientePrmDstv" size="25" maxlength="25" onblur="this.className='input';"
							onfocus="this.className='inputfocus';"/>
					</td>
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
						<td align="left" nowrap="nowrap">
							<label for="labelNumSeriePermiso">Num. Serie Permiso:</label>
						</td>

						<td align="left">
							<s:textfield name="consultaPermisoDistintivo.numSeriePermiso" id="idNumSeriePermisoPrmDstv" size="25" maxlength="25" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"/>
						</td>
					</s:if>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelTipoDistintivo">Tipo Distintivo:</label>
					</td>
					<td align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Distintivo" 
							name="consultaPermisoDistintivo.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoPrmDstv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDistintivo">Distintivo:</label>
					</td>
					<td align="left">
						<s:select id="idDistintivoPrmDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getListaTipoSINO()"
							onblur="this.className='input2';" headerValue="TODOS" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
							listValue="descripcion" cssStyle="width:95px" name="consultaPermisoDistintivo.distintivo"/>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelEstadoPermDstv">Estado Peticion Distintivo:</label>
						</td>
						<td align="left">
							<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosPermisoDistintivoConsulta()" onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
								name="consultaPermisoDistintivo.estadoPeticionPermDstv" listKey="valorEnum" listValue="nombreEnum" id="idEstadoPetPrmDstv"/>
						</td>
					</tr>
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelEstadoImpresion">Estado Peticion Impresión:</label>
						</td>
						<td  align="left">
							<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosImpresion()" 
							onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
								name="consultaPermisoDistintivo.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresion"/>
						</td>
					</tr>
				</s:if>

					<tr>
						<td align="left" nowrap="nowrap">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelEstadoImpresion">Estado Peticion Impresión:</label>
						</td>
						<td align="left">
							<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getEstadosImpresion()"
							onblur="this.className='input2';" 
								onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado"
								name="consultaPermisoDistintivo.estadoImpresion" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresion"/>
						</td>
					</tr>
				</s:if>

					<tr>
						<td align="left" nowrap="nowrap">
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoPrmDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaPermisoDistintivo@getInstance().getComboContratosHabilitados()"
								onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
								listValue="descripcion" cssStyle="width:220px" name="consultaPermisoDistintivo.idContrato"
								/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="consultaPermisoDistintivo.idContrato"/>
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
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.anioInicio" id="anioFechaPrtIni"
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
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaPermisoDistintivo.fechaPresentacion.anioFin" id="anioFechaPrtFin"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';" size="5" maxlength="4" />
								</td>
								<td align="left">
									<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFechaPrtFin, document.formData.mesFechaPrtFin, document.formData.diaFechaPrtFin);return false;" 
										title="Seleccionar fecha">
										<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif"width="15" height="16" border="0" alt="Calendario"/>
									</a>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</div>
		<div class="acciones center">
			<input type="button" class="boton" name="bConsultaPrmDstv" id="idBConsultaPrmDstv" value="Consultar"  onkeypress="this.onClick" onclick="return buscarConsultaPrmDstv();"/>	
			<input type="button" class="boton" name="bLimpiarPrmDstv" id="idBLimpiarPrmDstv" onclick="javascript:limpiarConsultaPrmDstv();" value="Limpiar"/>
		</div>
		<s:if test="%{resumen != null}">
			<br>
			<div id="resumenConsultaPrmDstv" style="text-align: center;">
				<%@include file="resumenPermisoDistintivo.jspf" %>
			</div>
			<br><br>
		</s:if>
		<br/>
		<table class="subtitulo" cellSpacing="0"  style="width:100%;">
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
										id="idResultadosPorPaginaConsPrmDstv" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaPrmDstv();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaPrmDstv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaPrmDstv" requestURI= "navegarConsultaPDI.action"
				id="tablaConsultaPrmDstv" summary="Listado de Tramites con Permisos/Distintivos" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="numExpediente" title="Num.Expediente" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:7%" />

					<display:column property="estadoPermDstv" title="Estado Prm/Dstv" sortable="true" sortProperty="estadoPetPermDstv" headerClass="sortable"
						defaultorder="descending" style="width:4%" />
				</s:if>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoPermisos()}">
					<display:column property="permiso" title="Permiso" sortable="true" sortProperty="permiso" headerClass="sortable" 
						defaultorder="descending" style="width:1%"/>

					<display:column property="nSeriePermiso" title="Num.Serie Permiso" sortable="true" sortProperty="numSeriePermiso" headerClass="sortable " 
						defaultorder="descending" style="width:3%"/>
				</s:if>
				<display:column property="distintivo" title="Distintivo" sortable="true" sortProperty="distintivo" headerClass="sortable " 
					defaultorder="descending" style="width:2%"/>

				<display:column property="tipoDistintivo" title="Tipo Distintivo" sortable="true" sortProperty="tipoDistintivo" headerClass="sortable" 
					defaultorder="descending" style="width:2%" />

				<display:column property="estadoPetImp" title="Estado Pet.Impresion" sortable="true" headerClass="sortable" sortProperty="estadoImpDstv"
						defaultorder="descending" style="width:7%" />

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosPrmDstv(this)' onKeyPress='this.onClick'/>" style="width:1%" >
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaPrmDstv.numExpediente"/>"
									value='<s:property value="#attr.tablaConsultaPrmDstv.numExpediente"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>
		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaPrmDstv" style="display:none; margin-top:auto; margin-left:auto; margin-right:auto;">
				<%@include file="../../includes/bloqueLoading.jspf" %>
			</div>
			<div class="acciones">
				<input type="button" class="boton" name="bGenerarDist" id="idGenerarDist" value="Generar Distintivo" onClick="javascript:generarDistintivo();"onKeyPress="this.onClick"/>	
				<input type="button" class="botonGrande" name="bSolPrmDstv" id="idBSolPrmDstv" value="Solicitar Distintivo" onClick="javascript:solicitarPrmDstvBloque();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bModifVeh" id="idModifVeh" value="Modificar Vehiculo" onClick="javascript:modificarVehiculo();"onKeyPress="this.onClick"/>	
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="botonGrande" name="bCmbEstadoSolPrmDstv" id="idBCmbEstadoSolPrmDstv" value="Cambiar Estado" onClick="javascript:cambiarEstadoPermDstvBloque();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bIntroducirNumeroSerie" id="idIntroducirNumeroSerie" value="Introducir Num. Serie" onClick="javascript:introducirNumeroSerie();"onKeyPress="this.onClick"/>	
					<input type="button" class="boton" name="bGenerarDemandaDstv" id="idGenerarDemandaDstv" value="Generar Demanda Dstv" onClick="javascript:generarDemandaDstv();"onKeyPress="this.onClick"/>	
				</s:if>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaPermisoDistintivo" style="display: none; background: #f4f4f4;"></div>
	<div id="popUpNumeroSerie" style="display: none; background: #f4f4f4;"></div>
</div>