<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Usuarios por IP</td>
			</tr>
		</tbody>
	</table>

	<div id="busquedaIP">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap"
					style="vertical-align: middle;"><label
					for="idNumcolegiado">Núm. Colegiado: </label>
				</td>
				<td align="left" style="vertical-align: middle;">
					<s:textfield
						name="listadoUsuariosIPBean.numColegiado" id="idNumColegiado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="4" />
				</td>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idFrontal">Frontal: </label>
				</td>

				<td align="left" style="vertical-align: middle;">
					<s:select id="idFrontal"
						list="@org.gestoresmadrid.oegam2comun.estadisticas.listados.utiles.UtilesVistaEstadisticas@getInstance().getListaFrontalesActivos()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" headerKey=""
						headerValue="Todos" name="listadoUsuariosIPBean.frontal"/>
				</td>
			</tr>

			<tr>
				<td align="left" nowrap="nowrap" style="vertical-align: middle;">
					<label>Fecha de Login: </label>
				</td>

				<td>
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaMatrIni">desde: </label>
							</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.diaInicio"
									id="diaMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>

							<td style="vertical-align: middle;">/</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.mesInicio"
									id="mesMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>

							<td style="vertical-align: middle;">/</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.anioInicio"
									id="anioMatrIni" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>

							<td style="vertical-align: middle;"><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrIni, document.formData.mesMatrIni, document.formData.diaMatrIni);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
							</a></td>

							<td width="2%"></td>
						</tr>
					</table>
				</td>

				<td colspan="2">
					<table style="width:20%">
						<tr>
							<td width="10%" align="left" style="vertical-align: middle;">
								<label for="diaMatrFin">hasta:</label>
							</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.diaFin"
									id="diaMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>

							<td style="vertical-align: middle;">/</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.mesFin"
									id="mesMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="2" maxlength="2" /></td>

							<td style="vertical-align: middle;">/</td>

							<td><s:textfield
									name="listadoUsuariosIPBean.fechaLogin.anioFin"
									id="anioMatrFin" onblur="this.className='input2';"
									onfocus="this.className='inputfocus';" readonly="false"
									size="5" maxlength="4" /></td>

							<td style="vertical-align: middle;"><a href="javascript:;"
								onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrFin, document.formData.mesMatrFin, document.formData.diaMatrFin);return false;"
								title="Seleccionar fecha"> <img class="PopcalTrigger"
									align="left" src="img/ico_calendario.gif" width="15"
									height="16" border="0" alt="Calendario" />
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
					<input type="button" class="botonMasGrande" name="bListarUsuarios"
					id="bListarUsuarios" value="Listar Usuarios"
					onkeypress="this.onClick"
					onclick="return listarUsuariosPorIPListadoUsuarios();" /> 
					
					&nbsp; 
					
					<input type="button" class="botonMasGrande" name="bLimpiar" id="bLimpiar"
					value="Limpiar" onkeypress="this.onClick"
					onclick="limpiarFormulario(this.form.id);" /> 
				</td>
					
			</tr>
		</table>
	</div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<div id="resultado"
		style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		
 
		<s:if test="%{listaUsuariosPorIP.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPagina">&nbsp;Mostrar resultados:</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPagina" value="resultadosPorPagina"
										title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarUsuariosPorIpListadoUsuarios.action', 'displayTagDivConsulta', this.value);" /> 
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>
		</s:if>
	</div>

	<script type="text/javascript">
		$(function() {
			$("#displayTagDivConsulta").displayTagAjax();
		});
	</script>

	<div id="displayTagDivConsulta" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="listaUsuariosPorIP"
			excludedParams="*" requestURI="navegarUsuariosPorIpListadoUsuarios.action"
			class="tablacoin" uid="tablaListadoUsuariosIP" id="tablaListadoUsuariosIP" 
			summary="Listado de Usuarios" cellspacing="0" cellpadding="0">

			<display:column property="numColegiado" title="Núm Colegiado"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="idUsuario" title="Id Usuario"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="apellidosNombre" title="Apellidos Nombre"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="fechaLogin" title="Fecha Login" sortable="true"
				headerClass="sortable" defaultorder="descending" format="{0,date,dd/MM/yyyy HH:mm}" />
				 
			<display:column title="Tiempo Sesion" sortable="true" headerClass="sortable" defaultorder="descending" sortProperty="fechaFin">
				<s:property
					value="%{@general.utiles.UtilesCadenaCaracteres@getTiempoSesion(#attr.tablaListadoUsuariosIP.fechaLogin,#attr.tablaListadoUsuariosIP.fechaFin)}" />
			</display:column>

			<display:column property="frontal" title="IP Frontal"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="text-align:center">
			</display:column>

			<display:column property="ip" title="IP Origen" sortable="true"
				headerClass="sortable" defaultorder="descending"/>

			<display:column title="Id Session" sortable="true"
				defaultorder="descending" style="text-align:center" sortProperty="idSesion">
				<img src="img/mostrar.gif"
					alt="<s:property value="#attr.tablaListadoUsuariosIP.idSesion"/>"
					style="height: 20px; width: 20px; cursor: pointer;"
					title="<s:property value="#attr.tablaListadoUsuariosIP.idSesion"/>" />
			</display:column>

		</display:table>
	</div>
	