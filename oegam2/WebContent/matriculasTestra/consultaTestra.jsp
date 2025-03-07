<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/trafico/testra.js" type="text/javascript"></script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta Testra</span></td>
			</tr>
		</table>
	</div>
</div>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="right"><label for="tipo">Tipo de dato:</label></td>
				<td>
					<s:select name="testraViewBean.tipo" id="tipo"
						list="@org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum@values()"
						listKey="clave" listValue="descripcion"
						headerKey="" headerValue="Todos"
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
				</td>
				<td align="right"><label for="dato">Dato: </label></td>
				<td>
					<s:textfield name="testraViewBean.dato" id="dato"
						align="left" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="15" maxlength="9" />
				</td>
			</tr>
			<tr>
				<td align="right"><label for="activo">Estado:</label></td>
				<td>
					<s:select name="testraViewBean.activo" id="activo"
						list="@org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo@values()"
						listKey="valorEnum" listValue="nombreEnum"
						headerKey="" headerValue="Todos" 
						onblur="this.className='input2';" onfocus="this.className='inputfocus';" />
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td align="right"><label for="numColegiado">Num Colegiado: </label></td>
					<td>
						<s:textfield name="testraViewBean.numColegiado"
							id="numColegiado" align="left" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';"
							onkeypress="return validarNumerosEnteros(event)" readonly="false"
							size="15" maxlength="4" />
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="right"><label>Fecha de alta:</label></td>
				<td colspan="3">
					<table width="100%">
						<tr>
							<td align="right">Desde:</td>
							<td>
								<s:textfield name="testraViewBean.fechaAlta.diaInicio"
									id="diaInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="testraViewBean.fechaAlta.mesInicio"
									id="mesInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" />
							</td>
							<td>/</td>
							<td>
								<s:textfield name="testraViewBean.fechaAlta.anioInicio"
									id="anioInicio" align="left" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" />
							</td>
							<td>
								<a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicio, document.formData.mesInicio, document.formData.diaInicio);return false;"
								title="Seleccionar fecha">
									<img class="PopcalTrigger" align="left" src="img/ico_calendario.gif" width="15" height="16" border="0" alt="Calendario" />
								</a>
							</td>
							<td align="right">Hasta:</td>
							<td><s:textfield name="testraViewBean.fechaAlta.diaFin"
									id="diaFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="testraViewBean.fechaAlta.mesFin"
									id="mesFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield name="testraViewBean.fechaAlta.anioFin"
									id="anioFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFin, document.formData.mesFin, document.formData.diaFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
						</tr>
					</table>
				</td>
			</tr>
			<tr>
				<td align="right"><label>Fecha última sanción:</label></td>
				<td colspan="3">
					<table width="100%">
						<tr>
							<td align="right">Desde:</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.diaInicio"
									id="diaInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.mesInicio"
									id="mesInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.anioInicio"
									id="anioInicioSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioInicioSancion, document.formData.mesInicioSancion, document.formData.diaInicioSancion);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>
							<td align="right">Hasta:</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.diaFin"
									id="diaFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.mesFin"
									id="mesFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>
							<td>/</td>
							<td><s:textfield
									name="testraViewBean.fechaUltimaSancion.anioFin"
									id="anioFinSancion" align="left"
									onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>
							<td><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioFinSancion, document.formData.mesFinSancion, document.formData.diaFinSancion);return false;"
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
					id="bBuscar" value="Consultar" onkeypress="this.onClick"
					onclick="consultaMatriculaTestra();" /></td>
				<td><input type="button" class="boton" name="bLimpiar"
					id="bLimpiar" value="Limpiar"
					onclick="return limpiarFormularioConsultaTestra();" /></td>

			</tr>

		</table>
	</div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado" style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultados de la
					Búsqueda:</td>
			</tr>
		</table>
	</div>
	<%@include file="../../includes/erroresYMensajes.jspf"%>

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
									onchange="cambiarElementosPorPaginaConsulta('navegarConsultaMatriculaTestra.action', 'displayTagDiv', this.value);" />
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
			requestURI="navegarConsultaMatriculaTestra.action" class="tablacoin"
			uid="tablaConsultas" summary="Listado de Vehículos" cellspacing="0"
			cellpadding="0" sort="external">

			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="id" title="Id Consulta" sortable="true" headerClass="sortable"
						defaultorder="descending" style="width:4%;"/>	
			</s:if>
			<s:if
				test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<display:column property="numColegiado" title="Colegiado"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:4%;" />					
			</s:if>

			<display:column property="fechaAlta" title="Fecha Alta"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="width:4%" format="{0,date,dd/MM/yyyy}" />

			<display:column title="Tipo"
					sortable="true" headerClass="sortable" 
					sortProperty="tipo"
					defaultorder="descending"
					style="width:2%" >
					<s:property 
						value="%{@org.gestoresmadrid.oegam2comun.trafico.testra.view.beans.TipoConsultaTestraEnum@convertirTexto(#attr.tablaConsultas.tipo)}" />
			</display:column>
				
			<display:column property="dato" title="Dato" sortable="true"
				headerClass="sortable" defaultorder="descending" maxLength="20"
				style="width:4%" />
			
			<display:column property="correoElectronico" title="Correo Electrónico" sortable="true"
				headerClass="sortable" defaultorder="descending" maxLength="20"
				style="width:4%" />


			<display:column property="fechaUltimaSancion"
				title="Fecha Última Sanción" sortable="true" headerClass="sortable"
				defaultorder="descending" style="width:4%"
				format="{0,date,dd/MM/yyyy}" />	

			<display:column title="Estado"
					sortable="true" headerClass="sortable" 
					sortProperty="activo"
					defaultorder="descending"
					style="width:2%" >
					<s:property 
						value="%{@org.gestoresmadrid.core.vehiculo.model.enumerados.EstadoVehiculo@convertirTexto(#attr.tablaConsultas.activo)}" />
			</display:column>			

			<display:column
				title="<input type='checkbox' name='checkAll' onClick='marcarTodos(this, document.formData.listaChecksConsultasTestra);' 
				onKeyPress='this.onClick'/>"
				style="width:1%">
				<table align="center">
					<tr>
						<td style="border: 0px;"><input type="checkbox"
							name="listaChecksConsultasTestra"
							value='<s:property value="#attr.tablaConsultas.id"/>' />
						</td>
						<td style="border: 0px;">
					  			<img src="img/mostrar.gif" alt="ver detalle" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" 
					  				onclick="abrirDetalle('${tablaConsultas.id}');" title="Ver Detalle"/>
					  		</td>
					</tr>
				</table>
			</display:column>


		</display:table>


	</div>
	<div class="acciones center">
		<s:if test="%{lista.getFullListSize()>0 }">
			<input type="button" value="Activar" class="boton" onkeypress="this.onClick" onclick="cambiarEstadoTestra('1');" />
			<input type="button" value="Desactivar" class="boton" onkeypress="this.onClick" onclick="cambiarEstadoTestra('0');" />
		</s:if>
	</div>
</s:form>