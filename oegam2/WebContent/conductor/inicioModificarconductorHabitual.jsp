<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/conductor/conductor.js" type="text/javascript"></script>


<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta modificar
						Conductores</span></td>
			</tr>
		</table>
	</div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<iframe width="174" height="189" name="gToday:normal:agenda.js"
	id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
	scrolling="no" frameborder="0"
	style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
</iframe>
<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="4" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap"><label for="nif">NIF:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.nif" id="nif"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="10" maxlength="9" />
				</td>
				<s:if
					test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td align="left" nowrap="nowrap"><label for="labelContrato">Contrato:</label>
					</td>
					<td align="left"><s:select
							list="@escrituras.utiles.UtilesVista@getInstance().getComboContratosHabilitados()"
							onblur="this.className='input2';"
							headerValue="Seleccione Contrato"
							onfocus="this.className='inputfocus';" listKey="codigo"
							headerKey="" listValue="descripcion" cssStyle="width:220px"
							name="consultaConductorFilter.idContrato" id="idContrato" /></td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="nif">DOI
						Vehiculo:</label></td>

				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.doiVehiculo" id="doivehiculo"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="10" maxlength="9" />
				</td>

				<td align="left" nowrap="nowrap"><label for="labelEstado">Estado:</label>
				</td>
				<td align="left"><s:select
						list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getEstados()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="" headerValue=""
						name="consultaConductorFilter.estado" listKey="valorEnum"
						listValue="nombreEnum" id="estado" /></td>

				<td align="left" nowrap="nowrap"><label
					for="labelTipoOperacion">Tipo operacion:</label></td>
				<td align="left"><s:select
						list="@org.gestoresmadrid.oegam2comun.conductor.utiles.UtilesVistaConductor@getInstance().getTiposOperacion()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey="" headerValue=""
						name="consultaConductorFilter.operacion" listKey="valorEnum"
						listValue="nombreEnum" id="operacion" /></td>

			</tr>
			<tr>
				<td align="left" nowrap="nowrap"><label for="matricula">Matricula:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.matricula" id="matricula"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="8" maxlength="7" onkeypress="return validarMatricula(event)" onChange="return validarMatricula_alPegar(event)" onmousemove="return validarMatricula_alPegar(event)" />
				</td>

				<td align="left" nowrap="nowrap"><label for="bastidor">Bastidor:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaConductorFilter.bastidor" id="bastidor"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="18" maxlength="17" />
				</td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td align="right"><label>Fecha de Inicio:</label></td>
				<td colspan="3">
					<table width="50%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.diaInicio"
									id="diaInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.mesInicio"
									id="mesInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.anioInicio"
									id="anioInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.diaFin"
									id="diaFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.mesFin"
									id="mesFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaIni.anioFin"
									id="anioFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio, document.formData.mesFinInicio, document.formData.diaFinInicio);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right"><label>Fecha de Fin:</label></td>
				<td colspan="3">
					<table width="50%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.diaInicio"
									id="diaInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.mesInicio"
									id="mesInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.anioInicio"
									id="anioInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio2, document.formData.mesInicio2, document.formData.diaInicio2);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>

							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.diaFin"
									id="diaFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.mesFin"
									id="mesFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaFin.anioFin"
									id="anioFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio2, document.formData.mesFinInicio2, document.formData.diaFinInicio2);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right"><label>Fecha de Alta:</label></td>
				<td colspan="3">
					<table width="50%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.diaInicio"
									id="diaInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.mesInicio"
									id="mesInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.anioInicio"
									id="anioInicio3" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio3, document.formData.mesInicio3, document.formData.diaInicio3);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.diaFin"
									id="diaFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.mesFin"
									id="mesFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaConductorFilter.fechaAlta.anioFin"
									id="anioFinInicio3" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinInicio3, document.formData.mesFinInicio3, document.formData.diaFinInicio3);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Buscar" onkeypress="this.onClick"
					onclick="return buscarModificarConductor();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					onclick="limpiarConsultaConductor_Pantalla()" value="Limpiar" /></td>
			</tr>
		</table>		
		<s:if test="%{resumen != null}">		
			<br>
			<div id="resumenConsultaArrendatario" style="text-align: center;">
				<%@include file="resumenConductor.jspf" %>
			</div>
			<br><br>
 		</s:if> 		
		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>
		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la
						b&uacute;squeda</td>
				</tr>
			</table>
		</div>
		<s:if
			test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										onchange="cambiarElementosPorPaginaConsulta('navegarModificarConductorHabitual.action', 'displayTagDiv', this.value);" />
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
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>			
			<display:table name="lista" excludedParams="*"
				requestURI="navegarModificarConductorHabitual.action"
				class="tablacoin" uid="tablaConductores" decorator=""
				summary="Listado de Conductores" cellspacing="0" cellpadding="0"
				sort="external">			
				<display:column property="numExpediente" title="Expediente" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:1%" href="altaAltaConductorHabitual.action" paramId="numExpediente"/>	
			<display:column property="nif" title="NIF" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />
				<display:column property="doiVehiculo" title="DOI" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />
				<display:column property="matricula" title="Matricula"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:2%" />
				<display:column property="bastidor" title="Bastidor" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />
				<display:column property="consentimientotxt" title="Consentimiento"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:2%" sortProperty="consentimiento" />
				<display:column property="fechaIni" format="{0,date,dd-MM-yyyy}"
					title="Inicio" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:2%" />
				<display:column property="fechaFin" format="{0,date,dd-MM-yyyy}"
					title="Fin" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:6%" />
				<display:column property="fechaAlta" format="{0,date,dd-MM-yyyy}"
					title="Alta" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:6%" />
				<display:column property="estadotxt" title="Estado" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%"
					sortProperty="estado"  />
				<%-- <display:column property="operaciontxt" title="Operacion" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />	 --%>
				<%-- <display:column
					title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>"
					style="width:1%">
					<table align="center">
						<tr>
								<td style="border: 0px;">
				  				<img src="img/history.png" alt="ver evolución" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
				  					onclick="abrirEvolucion(<s:property value="#attr.tablaArrendatarios.numExpediente"/>,'divEmergenteConsultaVehiculoAtex5Evolucion');" title="Ver evolución"/>
				  			</td>
							<td style="border: 0px;"><input type="checkbox"
								name="listaChecks"
								id="check<s:property value="#attr.tablaConductores.numExpediente"/>"
								value='<s:property value="#attr.tablaConductores.numExpediente"/>' />
							</td>
						</tr>
					</table>
				</display:column> --%>
			</display:table>
		</div>		
	</div>
</s:form>