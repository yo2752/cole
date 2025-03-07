<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/traficoConsultaVehiculo.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>

<script type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de vehículo</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="idVehiculo">Id Vehículo:</label>
					</td>
					<td align="left" nowrap="nowrap">
						<s:textfield name="consultaVehiculoBean.idVehiculo" id="idVehiculo" onblur="this.className='input2';" onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="idTipoVehiculo">Tipo Vehículo:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:select id="idTipoVehiculo" 	onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						list="@trafico.utiles.UtilesVistaTrafico@getInstance().getTiposVehiculo('X')"
						headerKey="" headerValue="Seleccione Tipo" name="consultaVehiculoBean.tipoVehiculo"
						listKey="tipoVehiculo" listValue="descripcion"/>
				</td>
				<td align="left" nowrap="nowrap">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<label for="numColegiado">Num Colegiado:</label>
					</s:if>
				</td>
				<td align="left">
					<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
						<s:textfield name="consultaVehiculoBean.numColegiado" id="numColegiado" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onkeypress="return validarMatricula(event)"
							size="10" maxlength="9"/>
					</s:if>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="bastidor">Bastidor:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaVehiculoBean.bastidor" id="bastidor" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onkeypress="return validarBastidor(event);"
							size="22" maxlength="21"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="matricula">Matrícula:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaVehiculoBean.matricula" id="matricula" onblur="this.className='input2';"
							onkeypress="return validarMatricula(event)"
							onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)"
							onfocus="this.className='inputfocus';" size="9" maxlength="9"/>
				</td>
			</tr>
		</table>
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="right" nowrap="nowrap">
					<label>Fecha de Matriculación:</label>
				</td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="right">
								<label for="diaAltaIni">desde:</label>
							</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.diaInicio" id="diaAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';"  
									size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.mesInicio" id="mesAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2"/>
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.anioInicio" id="anioAltaIni"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="5" maxlength="4"/>
							</td>
							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaIni, document.formData.mesAltaIni, document.formData.diaAltaIni);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
							<td width="2%"></td>
						</tr>
					</table>
				</td>
				<td align="left">
					<table width=100%>
						<tr>
							<td align="left">
								<label for="diaAltaFin">hasta:</label>
							</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.diaFin" id="diaAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.mesFin" id="mesAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="consultaVehiculoBean.fechaMatriculacion.anioFin" id="anioAltaFin"
									onblur="this.className='input2';" onfocus="this.className='inputfocus';" 
									size="5" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;" onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioAltaFin, document.formData.mesAltaFin, document.formData.diaAltaFin);return false;" title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario"/>
								</a>
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaVehiculo();"/>			
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarConsultaVehiculo()" value="Limpiar" />
				</td>
			</tr>
		</table>

	<s:hidden key="contrato.idContrato"/>

	<iframe width="174" height="189" name="gToday:normal:agenda.js" id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0" style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
	</div>

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
									id="idResultadosPorPagina" value="resultadosPorPagina"
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaVehiculo.action', 'displayTagDiv', this.value);" /> 
							</td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDiv").displayTagAjax();
		})
	</script>

	<div id="displayTagDiv" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="lista" excludedParams="*" requestURI="navegarConsultaVehiculo.action" class="tablacoin"
			uid="tablaVehiculos" summary="Listado de Vehículos" cellspacing="0" cellpadding="0" sort="external">
			
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="idVehiculo" title="Id Vehículo" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%;"/>
					<display:column property="numColegiado" title="Colegiado" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%;"/>
				</s:if>
				<display:column property="bastidor" title="Bastidor" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%;"/>
				<display:column property="matricula" title="Matricula" sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="20" style="width:4%" />
				<display:column property="tipoVehiculoDes" title="Tipo Vehiculo" sortable="true" headerClass="sortable" sortProperty="tipoVehiculo"
					defaultorder="descending" style="width:10%" />
				<display:column property="fechaMatriculacion" title="Fecha_Matri" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column title="Coditv" property="codItv" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" sortProperty="coditv"/>
				<display:column title="Activo" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%">
					<s:property value="%{@org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo@convertirTexto(#attr.tablaVehiculos.activo)}" />
				</display:column>
				<display:column style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img title="Ver evolución" onclick="consultaEvVehiculo('${tablaVehiculos.idVehiculo}', '${tablaVehiculos.numColegiado}');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
							</td>
							<td style="border: 0px;">
								<img src="img/mostrar.gif" alt="Consulta Detalle Del Vehículo " style="height:20px;width:20px;cursor:pointer;"
									onclick="recuperarVehiculo('${tablaVehiculos.idVehiculo}');" title="Consulta Detalle Del Vehículo"/>
							</td>
						</tr>
					</table>
				</display:column>
		</display:table>
	</div>
	</div>
</s:form>