<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/estadisticas/estadisticas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;">	-->

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">ESTADISTICAS:
					Listado de Estadisticas de Usuario por IP</span></td>
		</tr>
	</table>
</div>

<s:if test="%{passValidado=='false' or passValidado=='error'}">
	<s:form method="post" id="formData" name="formData">
		<table class="acciones" border="0">
			<tr>
				<td align="right" nowrap="nowrap"><label for="idPassword">Introduzca
						la clave:</label></td>
				<td align="center" nowrap="nowrap"><input type="password"
					autocomplete="off" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idPassword" value=""
					maxlength="40" size="20" name="consultaEstadisticas.password">
				</td>
				<td align="left" nowrap="nowrap" colspan="2"><input
					type="button" class="boton" name="bLimpiar" id="bLimpiar"
					value="Verificar Contraseña" onkeypress="this.onClick"
					onclick="return comprobarPasswordListadoUsuarios();" /></td>
			</tr>
		</table>
	</s:form>
	<%@include file="../../includes/erroresMasMensajes.jspf"%>
</s:if>

<s:if test="%{passValidado=='true'}">
	<s:form method="post" id="formData" name="formData">
		<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap"
						style="padding-top: .75em; vertical-align: middle"><label
						for="idNumcolegiado">Num Colegiado: </label></td>
					<td align="left" width="50%"
						style="padding-top: .5em; vertical-align: middle"><s:textfield
							name="consultaEstadisticas.numColegiado" id="idNumcolegiado"
							onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="5" maxlength="4" />
					</td>
					<td align="left" nowrap="nowrap"
						style="padding-top: .75em; vertical-align: middle"><label
						for="idFrontal">Frontal: </label></td>

					<td align="left" style="padding-top: .5em; vertical-align: middle">
						<s:select list="listaFrontales" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" headerKey="-1"
							headerValue="Todos" name="consultaEstadisticas.frontal"
							id="idFrontal" />

					</td>

				</tr>
			</table>

			<table class="tablaformbasica">
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap"><label>Fecha de
							Login: </label></td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="right"><label for="diaMatrIni">desde: </label></td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.diaInicio"
										id="diaMatrIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>

								<td>/</td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.mesInicio"
										id="mesMatrIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>

								<td>/</td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.anioInicio"
										id="anioMatrIni" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="5" maxlength="4" /></td>

								<td><a href="javascript:;"
									onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrIni, document.formData.mesMatrIni, document.formData.diaMatrIni);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
										align="left" src="img/ico_calendario.gif" width="15"
										height="16" border="0" alt="Calendario" />
								</a></td>

								<td width="2%"></td>
							</tr>


						</TABLE>
					</td>

					<td align="left">
						<TABLE WIDTH=100%>
							<tr>
								<td align="left"><label for="diaMatrFin">hasta:</label></td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.diaFin"
										id="diaMatrFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>

								<td>/</td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.mesFin"
										id="mesMatrFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="2" maxlength="2" /></td>

								<td>/</td>

								<td><s:textfield
										name="consultaEstadisticas.fechaMatriculacion.anioFin"
										id="anioMatrFin" onblur="this.className='input2';"
										onfocus="this.className='inputfocus';" readonly="false"
										size="5" maxlength="4" /></td>

								<td><a href="javascript:;"
									onClick="if(self.gfPop)gfPop.fPopCalendarSplit(document.formData.anioMatrFin, document.formData.mesMatrFin, document.formData.diaMatrFin);return false;"
									title="Seleccionar fecha"> <img class="PopcalTrigger"
										align="left" src="img/ico_calendario.gif" width="15"
										height="16" border="0" alt="Calendario" />
								</a></td>
							</tr>
						</TABLE>
					</td>
				</tr>
			</table>

			<table class="acciones">
				<tr>
					<td><input type="button" class="botonMasGrande" name="bListarUsuarios"
						id="bListarUsuarios" value="Listar Usuarios"
						onkeypress="this.onClick"
						onclick="return generarInformeUsuariosIPEstadisticas();" /> &nbsp;
						
						<input type="button" class="botonMasGrande" name="bLimpiar" id="bLimpiar"
						value="Limpiar" onkeypress="this.onClick"
						onclick="return limpiarFormUsuariosIPEstadisticas();" /> &nbsp; 
						
						<input type="button" class="botonMasGrande" name="bListarUsuariosOnline"
						id="bListarUsuariosOnline" value="Usuarios Online"
						onkeypress="this.onClick"
						onclick="return generarInformeUsuariosActivosIPEstadisticas();" />
						
						&nbsp; <!--Nuevo botón llamado Usuarios Online Agrup.
							    Este botón da información tipo ActionMessage indicando cuantos usuarios 
							    hay en cada frontal (agregado 31/07/2014) --> 
						<input type="button" class="botonMasGrande"
						name="bListarUsuariosOnlineAgrupados"
						id="bListarUsuariosOnlineAgrupados" value="Usuarios Online Agrup."
						onkeypress="this.onClick"
						onclick="return generarInformeUsuariosActivosAgrupadosIPEstadisticas();" />
						
						&nbsp; <!--Nuevo botón llamado Usuarios Online Repetidos
							   Este botón identifica en el listado encontrado por numero de Colegiado e idUsuario, 
							   quien está conectado más de una vez con el mismo carnét (ID_USUARIO) mediante un ActionMessage 
							   (agregado 04/08/2014) -->
						<input type="button" class="botonMasGrande"
						name="bListarOnlineRepetidos" id="bListarOnlineRepetidos"
						value="Usuarios Online Repetidos" onkeypress="this.onClick"
						onclick="return generarInformeUsuariosActivosRepetidos();" /></td>

				</tr>
			</table>
		</div>
		<iframe width="174" height="189" name="gToday:normal:agenda.js"
			id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
			scrolling="no" frameborder="0"
			style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
		</iframe>

		<%@include file="../../includes/erroresMasMensajes.jspf"%>
		<!--</div>		-->
		<div id="resultado"
			style="width: 100%; background-color: transparent;">
			<table class="subtitulo" cellSpacing="0" style="width: 100%;">
				<tr>
					<td style="width: 100%; text-align: center;">Resultado de la
						b&uacute;squeda</td>
				</tr>
			</table>

			<s:if
				test="%{listaConsultaEstadisticasUsuariosIP.size()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
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
											title="Resultados por página"
											onchange="return cambiarElementosPorPaginaConsultaUsuariosIP();" />
									</td>
								</tr>
							</table>
						</td>
					</tr>
				</table>
			</s:if>
		</div>

		<div class="divScroll">
			<display:table name="listaConsultaEstadisticasUsuariosIP"
				excludedParams="*" requestURI="navegarEstadisticas.action"
				class="tablacoin" uid="tablaConsultaEstadisticasUsuariosIP"
				id="tablaConsultaEstadisticasUsuariosIP"
				summary="Listado de Usuarios" cellspacing="0" cellpadding="0"
				pagesize="${resultadosPorPagina}">

				<display:column property="numColegiado" title="Num Colegiado"
					sortable="true" headerClass="sortable" defaultorder="descending"
					maxLength="15" style="width:1%" />

				<display:column property="idUsuario" title="Num Usuario"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:1%" />

				<display:column property="apellidosNombre" title="Apellidos Nombre"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:12%" />

				<display:column title="Fecha Login" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:8%">
					<s:property
						value="%{@general.utiles.UtilesCadenaCaracteres@getInstance().getFechaSinMilisegundos(#attr.tablaConsultaEstadisticasUsuariosIP.fechaLogin)}" />
				</display:column>

				<display:column title="Tiempo Sesion" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:5%">
					<s:property
						value="%{@general.utiles.UtilesCadenaCaracteres@getTiempoSesion(#attr.tablaConsultaEstadisticasUsuariosIP.fechaLogin,#attr.tablaConsultaEstadisticasUsuariosIP.fechaFin)}" />
				</display:column>

				<display:column property="frontal" title="IP del Frontal"
					sortable="true" headerClass="sortable" defaultorder="descending"
					style="width:4%;text-align:center">
				</display:column>

				<display:column property="ip" title="IP Origen" sortable="true"
					headerClass="sortable" defaultorder="descending" style="width:4%" />

				<display:column title="idSession" sortable="true"
					defaultorder="descending" style="width:1%;text-align:center">
					<img src="img/mostrar.gif"
						alt="<s:property value="#attr.tablaConsultaEstadisticasUsuariosIP.idSesion"/>"
						style="height: 20px; width: 20px; cursor: pointer;"
						title="<s:property value="#attr.tablaConsultaEstadisticasUsuariosIP.idSesion"/>" />
				</display:column>

				<input type="hidden" name="resultadosPorPagina" />
			</display:table>
		</div>
	</s:form>
</s:if>

<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordListadoUsuarios();
	    }
	});
});
</script>
