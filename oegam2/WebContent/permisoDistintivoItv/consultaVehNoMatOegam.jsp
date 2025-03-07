<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/vehiculoNoMatrOegam.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%" >
		<tr>
			<td class="tabular">
				<span class="titulo">Consulta Duplicados de Distintivos</span>
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
					<td  align="left">
						<s:textfield name="consultaVehiculo.matricula" id="idMatricula" size="10" maxlength="10" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" />
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Bastidor:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaVehiculo.bastidor" id="idBastidor" size="20" maxlength="20" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelBastidor">Nive:</label>
					</td>
					<td  align="left">
						<s:textfield name="consultaVehiculo.nive" id="idNive" size="20" maxlength="20" onblur="this.className='input';" 
							onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelDistintivo">Tipo Distintivo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getTiposDistintivos()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Tipo Distintivo" 
							name="consultaVehiculo.tipoDistintivo" listKey="valorEnum" listValue="nombreEnum" id="idTipoDistintivoDstv"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoDstv">Estado Peticion Distintivo:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosDistintivoConsulta()" onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
							name="consultaVehiculo.estadoPeticionDstv" listKey="valorEnum" listValue="nombreEnum" id="idEstadoPetDstv"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelEstadoImpresion">Estado Peticion Impresión:</label>
					</td>
					<td  align="left">
						<s:select list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getEstadosImpresion()" 
						onblur="this.className='input2';" 
							onfocus="this.className='inputfocus';"  headerKey="" headerValue="Seleccione Estado" 
							name="consultaVehiculo.estadoImpresionDstv" listKey="valorEnum" listValue="nombreEnum" id="idEstadoImpresion"/>
					</td>
				</tr>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<tr>
						<td align="left" nowrap="nowrap">
							<label for="labelContrato">Contrato:</label>
						</td>
						<td  align="left">
							<s:select id="idContratoDstv" list="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDistintivo@getInstance().getComboContratosHabilitados()"
									onblur="this.className='input2';" headerValue="Seleccione Contrato" onfocus="this.className='inputfocus';" listKey="codigo" headerKey="" 
									listValue="descripcion" cssStyle="width:220px" name="consultaVehiculo.idContrato"/>
						</td>
					</tr>
				</s:if>
				<s:else>
					<s:hidden name="consultaVehiculo.idContrato" id="idContratoDstv"/>
				</s:else>
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
									<s:textfield name="consultaVehiculo.fechaAlta.diaInicio" id="diaFechaPrtIni"
										onblur="this.className='input2';" onfocus="this.className='inputfocus';"  size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaVehiculo.fechaAlta.mesInicio" id="mesFechaPrtIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaVehiculo.fechaAlta.anioInicio" id="anioFechaPrtIni"
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
									<s:textfield name="consultaVehiculo.fechaAlta.diaFin" id="diaFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaVehiculo.fechaAlta.mesFin" id="mesFechaPrtFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" size="2" maxlength="2" />
								</td>
								<td align="left">
									<label class="labelFecha">/</label>
								</td>
								<td align="left">
									<s:textfield name="consultaVehiculo.fechaAlta.anioFin" id="anioFechaPrtFin"
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
			<input type="button" class="boton" name="bConsultaDstv" id="idBConsultaVeh" value="Consultar"  onkeypress="this.onClick" onclick="javascript:buscarVehiculoNoMatOegam();"/>			
			<input type="button" class="boton" name="bLimpiarDstv" id="idBLimpiarVeh" onclick="javascript:limpiarConsultaDstv();" value="Limpiar"/>		
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
										id="idResultadosPorPaginaVehNoMatr" name= "resultadosPorPagina"
										value="resultadosPorPagina" title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsultaVehNoMatr();">
									</s:select>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
		<div id="displayTagDivConsultaVehiculoNMO" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin" uid="tablaConsultaVehiculoNMO" requestURI= "navegarConsultaVehNo.action"
				id="tablaConsultaVehiculoNMO" summary="Listado de Vehiculos NO matriculados en OEGAM" excludedParams="*" sort="list"
				cellspacing="0" cellpadding="0" decorator="${decorator}">

				<display:column property="id" media="none" />

				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:1%" />

				<display:column property="tipoDistintivo" title="Tipo Distintivo" sortable="true" sortProperty="tipoDistintivo" headerClass="sortable" 
					defaultorder="descending" style="width:2%" />

				<display:column property="fechaAlta" title="Fecha" sortable="true" sortProperty="tipoDistintivo" headerClass="sortable" 
					defaultorder="descending" style="width:2%" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<display:column property="descContrato" title="Contrato" sortable="false" headerClass="sortable"
						defaultorder="descending" style="width:7%" />
				</s:if>

				<display:column property="estadoDstv" title="Estado Dstv" sortable="true" sortProperty="estadoSolicitud" headerClass="sortable"
						defaultorder="descending" style="width:4%" />

				<display:column property="estadoPetImp" title="Estado Impresion" sortable="true" headerClass="sortable" sortProperty="estadoImpresion"
								defaultorder="descending" style="width:7%"/>

				<display:column property="primeraImpresion" title="Primera Impresión" sortable="true" headerClass="sortable"
								defaultorder="descending" style="width:7%"/>

				<display:column title="<input type='checkbox' name='checkAll' onClick='marcarTodosDuplicadosDstv(this)' onKeyPress='this.onClick'/>" style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<input type="checkbox" name="listaChecks" id="check<s:property value="#attr.tablaConsultaVehiculoNMO.id"/>"
									value='<s:property value="#attr.tablaConsultaVehiculoNMO.id"/>' />
							</td>
							<td style="border: 0px;">
								<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;"
									onclick="abrirEvolucionVNMO('${tablaConsultaVehiculoNMO.id}','divEmergenteConsultaVehNoMat');" title="Ver evolución"/>
							</td>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="Detalle Vehículo" style="height:20px;width:20px;cursor:pointer;"
									onclick="abrirDetalleVNMO('${tablaConsultaVehiculoNMO.id}');" title="Detalle Vehículo"/>
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
			<input type="button" class="botonGrande" name="bAltaVNMO" id="idBAltaVNMO" value="Alta Duplicado" onClick="javascript:altaVehiculoVNMO()();"onKeyPress="this.onClick"/>
				<input type="button" class="botonGrande" name="bSolDstvVNMO" id="idBSolDstvVNMO" value="Solicitar Distintivo" onClick="javascript:solicitarDstvBloqueVNMO();"onKeyPress="this.onClick"/>
				<input type="button" class="boton" name="bGenerarDistVNMO" id="idGenerarDistVNMO" value="Generar Distintivo" onClick="javascript:generarDstvVNMO();"onKeyPress="this.onClick"/>	
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
					<input type="button" class="botonGrande" name="bCmbEstadoSolDstvVNMO" id="idBCmbEstadoSolDstvVNMO" value="Cambiar Estado" onClick="javascript:cambiarEstadoDstvBloqueVNMO();"onKeyPress="this.onClick"/>
					<input type="button" class="boton" name="bGenerarDemandaDstvVNMO" id="idGenerarDemandaDstvVNMO" value="Gen.Demanda Dstv" onClick="javascript:generarDemandaDstvVNMO();"onKeyPress="this.onClick"/>	
					<input type="button" class="boton" name="bRevertirDstvVNMO" id="idRevertirDstvVNMO" value="Revertir" onClick="javascript:revertirDstvVNMO();"onKeyPress="this.onClick"/>
				</s:if>
				<s:elseif test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoImpresionDstvGestor()}">
					<input type="button" class="boton" name="bGenerarDemandaDstvVNMO" id="idGenerarDemandaDstvVNMO" value="Gen.Demanda Dstv" onClick="javascript:generarDemandaDstvVNMO();"onKeyPress="this.onClick"/>
				</s:elseif>
			</div>
		</s:if>
	</s:form>
	<div id="divEmergenteConsultaVehNoMat" style="display: none; background: #f4f4f4;"></div>
</div>