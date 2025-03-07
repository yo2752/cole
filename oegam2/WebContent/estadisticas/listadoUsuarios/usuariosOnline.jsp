<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	
	<table class="subtitulo" align="left" cellspacing="0">
		<tbody>
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - "></td>
				<td>Usuarios Online</td>
			</tr>
		</tbody>
	</table>

	<div id="busquedaOnline">
		<table class="tablaformbasica">
			<tr>
				<td align="left" width="15%"  nowrap="nowrap"
					style="vertical-align: middle;"><label
					for="numColegiado">Núm. Colegiado: </label>
				</td>
				<td align="left" style="vertical-align: middle;">
					<s:textfield
						name="listadoUsuariosOnlineIPBean.numColegiado" id="numColegiado"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="5" maxlength="4" />
				</td>
				<td align="left" width="15%" nowrap="nowrap" style="vertical-align: middle;">
					<label for="idFrontal">Frontal: </label>
				</td>

				<td align="left" style="vertical-align: middle;">
					<s:select id="idFrontal"
						list="@org.gestoresmadrid.oegam2comun.estadisticas.listados.utiles.UtilesVistaEstadisticas@getInstance().getListaFrontalesActivos()"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" 
						headerKey=""
						headerValue="Todos" 
						name="listadoUsuariosOnlineIPBean.frontal"/>
				</td>
			</tr>
		</table>

		<table class="acciones">

			<tr>
				<td>
					<input type="button" class="botonMasGrande" name="bListarUsuariosOnline"
					id="bListarUsuariosOnline" value="Usuarios Online"
					onkeypress="this.onClick"
					onclick="return listarUsuariosOnlineListadoUsuarios();" />
					
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

	<!--</div>		-->
	<div id="resultado"
		style="width: 100%; background-color: transparent;">
		<table class="subtitulo" cellSpacing="0" style="width: 100%;">
			<tr>
				<td style="width: 100%; text-align: center;">Resultado de la b&uacute;squeda</td>
			</tr>
		</table>
		
		<s:if test="%{listaUsuariosOnline.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
			<table width="100%">
				<tr>
					<td align="right">
						<table width="100%">
							<tr>
								<td width="90%" align="right"><label
									for="idResultadosPorPaginaOnline">&nbsp;Mostrar resultados:</label></td>
								<td width="10%" align="right"><s:select
										list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
										onblur="this.className='input2';"
										onfocus="this.className='inputfocus';"
										id="idResultadosPorPaginaOnline" value="resultadosPorPagina"
										title="Resultados por página"
										onchange="cambiarElementosPorPaginaConsulta('navegarUsuariosOnlineListadoUsuarios.action', 'displayTagDivConsultaOnline', this.value);" /> 
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
			$("#displayTagDivConsultaOnline").displayTagAjax();
		});
	</script>

	<div id="displayTagDivConsultaOnline" class="divScroll">
		<div style="display: none; margin-top: auto; margin-left: auto; margin-right: auto;">
			<%@include file="../../includes/bloqueLoading.jspf"%>
		</div>
		<display:table name="listaUsuariosOnline"
			excludedParams="*" requestURI="navegarUsuariosOnlineListadoUsuarios.action"
			class="tablacoin" uid="tablaListadoUsuariosOnline"
			id="tablaListadoUsuariosOnline"
			summary="Listado de Usuarios" cellspacing="0" cellpadding="0">

			<display:column property="numColegiado" title="Núm Colegiado"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="idUsuario" title="ID Usuario"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="apellidosNombre" title="Apellidos Nombre"
				sortable="true" headerClass="sortable" defaultorder="descending" />

			<display:column property="fechaLogin" title="Fecha Login" sortable="true"
				headerClass="sortable" defaultorder="descending" format="{0,date,dd/MM/yyyy HH:mm}" />

			<display:column title="Tiempo Sesión" sortable="true" sortProperty="fechaFin"
				headerClass="sortable" defaultorder="descending">
				<s:property
					value="%{@general.utiles.UtilesCadenaCaracteres@getTiempoSesion(#attr.tablaListadoUsuariosOnline.fechaLogin,#attr.tablaListadoUsuariosOnline.fechaFin)}" />
			</display:column>

			<display:column property="frontal" title="IP Frontal"
				sortable="true" headerClass="sortable" defaultorder="descending"
				style="text-align:center">
			</display:column>

			<display:column property="ip" title="IP Origen" sortable="true"
				headerClass="sortable" defaultorder="descending"/>

			<display:column title="Id Session" sortable="true" sortProperty="idSesion"
				defaultorder="descending" style="text-align:center">
				<img src="img/mostrar.gif"
					alt="<s:property value="#attr.tablaListadoUsuariosOnline.idSesion"/>"
					style="height: 20px; width: 20px; cursor: pointer;"
					title="<s:property value="#attr.tablaListadoUsuariosOnline.idSesion"/>" />
					&nbsp; &nbsp;
					<img src="img/ocultar.gif" onclick="javascript:cerrarSesionUsuarioOnlineListadoUsuarios('${tablaListadoUsuariosOnline.idSesion}')"
					alt="Cerrar Sesión" style="height: 20px; width: 20px; cursor: pointer;"
					title="Cerrar Sesión" />
			</display:column>
			
		</display:table>
	</div>
	
	<s:if test="%{listaUsuariosOnline.getFullListSize()>0}">
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="botonMasGrande" name="bCerrarUsuariosOnline" id="bCerrarUsuariosOnline"
					value="Cerrar sesión usuarios" onkeypress="this.onClick"
					onclick="return cerrarSesionUsuariosOnlineListadoUsuarios();" />
					
				</td>
			</tr>
		</table>
	</s:if>
