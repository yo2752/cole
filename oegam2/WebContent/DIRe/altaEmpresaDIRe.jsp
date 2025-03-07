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
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script src="js/EmpresaDIRe/EmpresaDIRe.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><s:if
					test="%{@org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles.UtilesVistaEmpresaDIRe@getInstance().esOperacionAltaEmpresaDIRe(empresaDIReDto)}">
					<span class="titulo">Datos Empresa DIRe</span>
				</s:if> 
				<s:else>
					<span class="titulo">Modificación Empresa DIRe</span>
				</s:else>
			</td>
		</tr>
	</table>
</div>

<div>
	<iframe width="174" height="189" name="gToday:normal:agenda.js"
		id="gToday:normal:agenda.js" src="calendario/ipopeng.htm"
		scrolling="no" frameborder="0"
		style="visibility: visible; z-index: 999; position: absolute; top: -500px; left: -500px;">
	</iframe>

	<ol id="toc">
		<li id="Datos"><a href="#Datos">Datos Empresa</a></li>
		<li id="Contactos"><a href="#Contactos">Contactos</a></li>
		<li id="Administradores"><a href="#Administradores">Administradores</a></li>

		<s:if test="empresaDIReDto != null && empresaDIReDto.estado != null">
			<li id="Resumen"><a href="#Resumen">Resumen</a></li>
		</s:if>
	</ol>

	<table class="subtitulo" cellSpacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<s:if
				test="%{@org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles.UtilesVistaEmpresaDIRe@getInstance().esOperacionAltaEmpresaDIRe(empresaDIReDto)}">
				<td>Datos del Alta de Empresa DIRe</td>
			</s:if>
			<s:else>
				<td>Datos de la Modificación de Empresa DIRe</td>
			</s:else>

			<td style="border: 0px;" align="right">
				<img src="img/history.png" alt="ver evolución"
					style="margin-right: 10px; height: 20px; width: 20px; cursor: pointer;"
					onclick="abrirEvolucionAltaCayc('idNumExpArrendatario','divEmergente');"
					title="Ver evolución" />
			</td>
		</tr>
	</table>

	<s:form method="post" id="formData" name="formData">
		<s:hidden name="empresaDIReDto.numColegiado" />

		<s:hidden name="empresaDIReDto.estado" />
		<s:hidden name="empresaDIReDto.operacion" />

		<div class="contentTabs" id="Datos"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaDatos_DIRe.jsp"%>
		</div>
		<div class="contentTabs" id="Contactos"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestania_Contactos_DIRe.jsp"%>
		</div>
		<div class="contentTabs" id="Administradores"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestania_Administradores_DIRe.jsp"%>
		</div>

		<div class="contentTabs" id="Resumen"
			style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
			<%@include file="pestaniaResumen_DIRe.jsp"%>
		</div>
		&nbsp;
		<div class="acciones center">
			<s:if
				test="%{@org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles.UtilesVistaEmpresaDIRe@getInstance().esEstadoGuardable(empresaDIReDto)}">
				<input type="button" class="boton" name="bGuardarEmpresaDIRe"
					id="idGuardarEmpresaDIRe" value="Guardar"
					onClick="javascript:guardarEmpresaDIRe();"
					onKeyPress="this.onClick" />
			</s:if>
			<s:if
				test="%{@org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles.UtilesVistaEmpresaDIRe@getInstance().esEstadoConsultable(empresaDIReDto)}">
				<input type="button" class="boton" name="bConsultarArrendatario"
					id="idConsultarArrendatario" value="Consultar"
					onClick="javascript:consultarArrendatario();"
					onKeyPress="this.onClick" />
			</s:if>
			<s:if
				test="%{org.gestoresmadrid.oegam2comun.EmpresaDIRe.utiles.UtilesVistaEmpresaDIRe@getInstance().esEstadoIniciado(empresaDIReDto)}">
				<input type="button" class="boton" name="bValidarArrendatario"
					id="idValidarArrendatario" value="Validar"
					onClick="javascript:validarArrendatario();"
					onKeyPress="this.onClick" />
			</s:if>

			<input type="button" class="boton" name="bVolver" id="idVolver"
				value="Volver Listado" onClick="javascript:Volver_EmpresaDIRe();"
				onKeyPress="this.onClick" />

		</div>
<div id="divEmergente_Contacto" style="display: none; background: #f4f4f4;">
</div>
<div id="divEmergente_Administrador" style="display: none; background: #f4f4f4;">
</div>
<div id="divEmergente" style="display: none; background: #f4f4f4;">
</div>
	</s:form>
</div>