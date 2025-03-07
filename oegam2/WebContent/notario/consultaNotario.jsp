<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:hidden name="esSeleccionadoNotario" id="esSeleccionadoNotario"/>
<s:hidden name="codigo" id="codigo"/>
<script src="js/tabs.js" type="text/javascript"></script>
<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Consulta de Notarios</span></td>
			</tr>
		</table>
	</div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<s:form method="post" id="formDataNotario" name="formData">

	<div id="busqueda">
		<table class="tablaformbasica">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelNombre">Nombre:</label>
				</td>
				<td align="left">
					<s:textfield name="notario.nombre" id="idNotarioNombre" size="25" maxlength="100" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelApellido1">Apellidos:</label>
				</td>
				<td align="left">
					<s:textfield name="notario.apellidos" id="idNotarioApellidos" size="25" maxlength="100" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelCodNotario">Código Notario:</label>
				</td>
				<td align="left">
					<s:textfield name="notario.codigoNotario" id="idCodNotario" size="25" maxlength="7" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="labelCodNotaria">Código Notaría:</label>
				</td>
				<td align="left">
					<s:textfield name="notario.codigoNotaria" id="idCodNotaria" size="25" maxlength="9" onblur="this.className='input';"
								onfocus="this.className='inputfocus';"/>
				</td>
			</tr>
		</table>
		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bConsulta" id="bConsConsulta" value="Consultar" onkeypress="this.onClick" onclick="return consultarNotario();"/>
				</td>
				<td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<input type="button" class="boton" name="bAltaNotario" onclick="javascript:altaNotario();" value="Alta Notario"/>
				</s:if>
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="javascript:limpiarNotario();" value="Limpiar"/>
				</td>
			</tr>

		</table>

		<div id="resultado" style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultados de la
						Búsqueda:</td>
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
												onblur="this.className='input2';"
												onfocus="this.className='inputfocus';"
												id="idResultadosPorPagina"
												name= "resultadosPorPagina"
												value="resultadosPorPagina"
												title="Resultados por página"
												onchange="cambiarElementosPorPaginaNotario('navegarConsultaNotarioRM', 'displayTagDiv', this.value);">
									</s:select>
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
				<%@include file="../../../../includes/bloqueLoading.jspf"%>
			</div>
			<display:table name="lista" class="tablacoin"
					uid="tablaConsultaNotarios" requestURI= "navegarConsultaNotarioRM.action"
					id="tablaConsultaNotarios" summary="Listado de Notarios"
					excludedParams="*" sort="list"
					cellspacing="0" cellpadding="0"
					decorator="${decorator}">

				<display:column property="codigoNotario" title="Codigo Notario" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<display:column property="codigoNotaria" title="Codigo Notaria" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:3%"/>

				<display:column property="nombre" title="Nombre" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%"/>

				<display:column property="apellidos" title="Apellidos" sortable="true" headerClass="sortable"
					defaultorder="descending" style="width:4%" />

				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column title="" style="width:1%">
						<!-- <td style="border: 0px;"> -->
									<img src="img/editar.gif" alt="Editar" style="margin-right:5px;height:15px;width:15px;cursor:pointer;"
										onclick="editarNotario('<s:property value="#attr.tablaConsultaNotarios.codigoNotario"/>');" title="Editar"/>
					<!-- 	</td> -->
					</display:column>
				</s:if>
			</display:table>
		</div>
	</div>
</s:form>
<script src="js/notario/notarioFunction.js" type="text/javascript"/>
