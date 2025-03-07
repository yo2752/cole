<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/general.js" type="text/javascript"></script>
<script src="js/traficoConsultaPersona.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<script type="text/javascript">

function buscarConsultaPersona() {
	document.formData.action = "buscarConsultaPersona.action";
	document.formData.submit();
}

function limpiarConsultaPersona(){
	document.getElementById("estadoPersona").selectedIndex = 0;
	document.getElementById("tipoPersona").selectedIndex = 0;
	document.getElementById("estadoCivil").selectedIndex = 0;
	if (document.getElementById("numColegiado") != null) {
		document.getElementById("numColegiado").value = "";
	}
	document.getElementById("nombre").value = "";
	document.getElementById("nif").value = "";
	document.getElementById("apellido1").value = "";
	document.getElementById("apellido2").value = "";
}

</script>

<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular">
					<span class="titulo">Consulta de personas</span>
				</td>
			</tr>
		</table>
	</div>
</div>

<%@include file="../../includes/erroresYMensajes.jspf"%>

<s:form method="post" id="formData" name="formData">
	<div id="busqueda">
		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nif">NIF:</label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:textfield name="consultaPersonaBean.nif" id="nif" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="9" />
				</td>
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<td align="left" nowrap="nowrap">
						<label for="numColegiado">Num Colegiado:</label>
					</td>
					<td align="left">
						<s:textfield name="consultaPersonaBean.numColegiado" id="numColegiado" onblur="this.className='input2';"
							onfocus="this.className='inputfocus';" size="10" maxlength="9" />
					</td>
				</s:if>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="nombre">Nombre:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaPersonaBean.nombre" id="nombre" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="30" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="estado">Estado:</label>
				</td>
				<td>
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstados()"
						id="estadoPersona" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="consultaPersonaBean.estado" listValue="nombreEnum" listKey="valorEnum" title="Estado Persona"
						headerKey="-1" headerValue="TODOS"/>
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="apellido1">Apellido o Razón Social:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaPersonaBean.apellido1RazonSocial" id="apellido1" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="30" />
				</td>
				<td align="left" nowrap="nowrap">
					<label for="apellido2">Segundo Apellido:</label>
				</td>
				<td align="left">
					<s:textfield name="consultaPersonaBean.apellido2" id="apellido2" onblur="this.className='input2';"
						onfocus="this.className='inputfocus';" size="35" maxlength="30" />
				</td>
			</tr>
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="tipoPersona">Tipo Persona:</label>
				</td>
				<td>
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboTipoPersonas()"
						id="tipoPersona" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="consultaPersonaBean.tipoPersona" title="Tipo Persona" listValue="nombreEnum"
						listKey="valorEnum" headerKey="" headerValue="TODOS"/>
				</td>
				<td align="left" nowrap="nowrap">
					<label for="estadoCivil">Estado Civil:</label>
				</td>
				<td>
					<s:select list="@escrituras.utiles.UtilesVista@getInstance().getComboEstadosCivil()"
						id="estadoCivil" onblur="this.className='input2';" onfocus="this.className='inputfocus';"
						name="consultaPersonaBean.estadoCivil" listValue="nombreEnum" listKey="valorEnum" title="Estado Civil"
						headerKey="" headerValue="TODOS" />
				</td>
			</tr>
		</table>

		<table class="acciones">
			<tr>
				<td>
					<input type="button" class="boton" name="bBuscar" id="bBuscar" value="Buscar" onkeypress="this.onClick" onclick="return buscarConsultaPersona();"/>
				</td>
				<td>
					<input type="button" class="boton" name="bLimpiar" onclick="limpiarConsultaPersona()" value="Limpiar" />
				</td>
			</tr>
		</table>

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
										onchange="cambiarElementosPorPaginaConsulta('navegarConsultaPersona.action', 'displayTagDiv', this.value);" />
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
			<display:table name="lista" excludedParams="*" requestURI="navegarConsultaPersona.action" class="tablacoin"
				uid="tablaPersonas" summary="Listado de Personas" cellspacing="0" cellpadding="0" sort="external">

				<display:column property="id.nif" title="Nif" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
					<display:column property="id.numColegiado" title="NºColegiado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				</s:if>
				<display:column property="nombre" title="Nombre" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column property="apellido1RazonSocial" title="Apellido o Razón Social" sortable="true" headerClass="sortable" sortProperty="apellido1RazonSocial" style="width:10%" />
				<display:column property="apellido2" title="Segundo Apellido" sortable="true" headerClass="sortable" defaultorder="descending" style="width:8%" />
				<display:column style="width:1%">
					<table align="center">
						<tr>
							<td style="border: 0px;">
								<img title="Ver evolución" onclick="consultaEvolucionPersona('${tablaPersonas.id.nif}', '${tablaPersonas.id.numColegiado}');" style="margin-right:10px;height:20px;width:20px;cursor:pointer;" alt="Ver evolución" src="img/history.png">
							</td>
							<td style="border: 0px;">
								<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoMantenimientoPersonas() && !(@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoEspecial())}">
									<img src="img/mostrar.gif" alt="Modificar" style="height:20px;width:20px;cursor:pointer;"
										onclick="recuperarPersona('${tablaPersonas.id.nif}','${tablaPersonas.id.numColegiado}');" title="Modificar "/>
								</s:if>
								<s:else>
									<img src="img/mostrar.gif" alt="Modificar" style="height:20px;width:20px;cursor:pointer;" 
										onclick="recuperarPersona('${tablaPersonas.id.nif}','${tablaPersonas.id.numColegiado}');" title="Ver"/>
								</s:else>
							</td>
						</tr>
					</table>
				</display:column>

			</display:table>
		</div>
		<div id="divEmergentePopUp" style="display: none; background: #f4f4f4;"></div>
	</div>
</s:form>