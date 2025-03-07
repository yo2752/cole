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
<script src="js/EmpresaDIRe/EmpresaDIRe.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs"
	style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Empresas DIRe</span></td>
			</tr>
		</table>
	</div>
</div>

<!-- En el siguiente iframe se presentan los errores de forma standar -->
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
						name="consultaEmpresaDIReFilter.nif" id="nif"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
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
							name="consultaEmpresaDIReFilter.idContrato" id="idContrato" />
					</td>
				</s:if>

				<td align="left" nowrap="nowrap"><label
					for="labelNumExpediente">Num.Expediente:</label></td>
				<td align="left"><s:textfield
						name="consultaEmpresaDIReFilter.numExpediente"
						id="idNumExpediente" align="left"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="15"
						onkeypress="return validarNumerosEnteros(event)" /></td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap"><label>Codigo DIRe:</label></td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaEmpresaDIReFilter.codigoDire" id="codigoDire"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onfocus="this.className='inputfocus';" size="8" maxlength="7" />
				</td>

				<td align="left" nowrap="nowrap"><label for="Nun.Registro">Nombre:</label>
				</td>
				<td align="left" nowrap="nowrap"><s:textfield
						name="consultaEmpresaDIReFilter.nombre" id="nombre"
						onblur="this.className='input2';"
						onkeypress="return validarMatricula(event)" 
						onfocus="this.className='inputfocus';" size="18" maxlength="17" />
				</td>
			</tr>
			<tr>
			</tr>
			<tr>
				<td align="right"><label>Fecha de Creacion:</label></td>
				<td colspan="3">
					<table width="50%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaIni.diaInicio"
									id="diaInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaIni.mesInicio"
									id="mesInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaIni.anioInicio"
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
									name="consultaEmpresaDIReFilter.fechaIni.diaFin"
									id="diaFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaIni.mesFin"
									id="mesFinInicio" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaIni.anioFin"
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
				<td align="right"><label>Fecha de Actualizacion:</label></td>
				<td colspan="3">
					<table width="50%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaFin.diaInicio"
									id="diaInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaFin.mesInicio"
									id="mesInicio2" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaFin.anioInicio"
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
									name="consultaEmpresaDIReFilter.fechaFin.diaFin"
									id="diaFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaFin.mesFin"
									id="mesFinInicio2" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="consultaEmpresaDIReFilter.fechaFin.anioFin"
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

		</table>
		<!-- 
En esta table se situan los botones de las acciones que se pueden realizar con el filtro es decir BUSCAR y LIMPIAR
-->

		<table class="acciones">
			<tr>
				<td><input type="button" class="boton" name="bBuscar"
					id="bBuscar" value="Buscar" onkeypress="this.onClick"
					onclick="return buscarConsultaDIRe();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					onclick="return limpiarConsultaDIRe_Pantalla();" value="Limpiar" /></td>
			</tr>
		</table>

		<s:if test="%{resumen != null}">

			<br>
			<div id="resumenConsultaArrendatario" style="text-align: center;">
				<%@include file="resumenArrendatario.jspf"%>
			</div>
			<br>
			<br>
		</s:if>

		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<!-- 
El resultado de la consulta se presenta en el siguiente DIV, se comprueba que existen resultados

 -->
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
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaEmpresaDIRe.action', 'displayTagDiv', this.value);" />
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>

		<div id="displayTagDiv" class="divScroll">
			<div
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>

			<display:table name="lista" excludedParams="*"
				requestURI="navegarConsultaEmpresaDIRe.action" class="tablacoin"
				uid="tablaDIRe" id="tablaDIRe"
				decorator="${decorator}" summary="Listado DIRe"
				cellspacing="0" cellpadding="0" sort="external">

				<display:column title="Expediente" sortProperty="numExpediente"
						sortable="true" headerClass="sortable" defaultorder="descending"
						style="width:1%">

							<a href="altaAltaEmpresaDIRe.action?numExpediente=${tablaDIRe.numExpediente}">
								<s:property value="#attr.tablaDIRe.numExpediente"/>
							</a>
				</display:column>

				<display:column property="codigoDire" title="DIRe" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:2%" />
					
				<display:column property="fechaCreacion" format="{0,date,dd-MM-yyyy}"
					title="Inicio" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />
				<display:column property="fechaActualizacion" format="{0,date,dd-MM-yyyy}"
					title="Fin" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:6%" />

				<display:column property="nombre" title="Nombre"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:2%" sortProperty="operacion" />
				<display:column
					title="<input type='checkbox' name='checkAll' onClick='marcarTodas(this)' onKeyPress='this.onClick'/>"
					style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;"><img src="img/history.png"
								alt="ver evolución"
								style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
								onclick="abrirEvolucionCayc(<s:property value="#attr.tablaDIRe.numExpediente"/>,'divEmergenteEvolucionAltaCayc');"
								title="Ver evolución" /></td>
							<td style="border: 0px;"><input type="checkbox"
								name="listaChecks"
								id="check<s:property value="#attr.tablaDIRe.numExpediente"/>"
								value='<s:property value="#attr.tablaDIRe.numExpediente"/>' />
							</td>
						</tr>
					</table>
				</display:column>
			</display:table>
		</div>

		<s:if test="%{lista.getFullListSize() > 0}">
			<div id="bloqueLoadingConsultaDIRe"
				style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
				<%@include file="../../includes/bloqueLoading.jspf"%>
			</div>
			<div class="acciones center">
				<input type="button" class="boton" name="bEliminar" id="idEliminar"
					value="Eliminar" onClick="javascript:eliminar();"
					onKeyPress="this.onClick" />
			</div>
			<div id="divEmergenteConsultaDIRe"
				style="display: none; background: #f4f4f4;"></div>

			<div id="divEmergenteEvolucionAltaCayc"
				style="display: none; background: #f4f4f4;"></div>

		</s:if>
	</div>
</s:form>