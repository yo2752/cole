<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<!-- Including CryptoJS required libraries -->
<script
	src="//cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/rollups/hmac-sha256.js"></script>
<script
	src="//cdnjs.cloudflare.com/ajax/libs/crypto-js/3.1.2/components/enc-base64-min.js"></script>

<div class="contenido">
	<%@include file="../../includes/erroresMasMensajes.jspf"%>

	<s:hidden name="Administrador_id" id="Administrador_id" />
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0"
		align="left">
		<tr>
			<s:if test="%{empresaDIReDto.numExpediente != null}">
				<tr>
					<td align="left" nowrap="nowrap" width="120"><label
						for="labelRBastidorConsulta">Num Expediente: </label></td>
					<td align="left" nowrap="nowrap"><s:label
							value="%{empresaDIReDto.numExpediente}" /></td>
				</tr>
			</s:if>
	</table>

	<table class="nav" cellSpacing="1" cellPadding="1" width="100%"
		align="left">
		<tr>
			<td class="tabular"><span class="titulo">Administrador:</span></td>
		</tr>
	</table>
	<table class="" cellSpacing="1" cellPadding="1" width="100%"
		align="center">
		<tr>
			<td align="center"><input type="button"
				onclick="javascript:aniadeAdministrador('idNumExpArrendatario','divEmergente_Administrador');"
				id="btnContactos" class="button corporeo" value="Añadir Administradores" /></td>
		</tr>
	</table>
	<br>
	<div id="displayTagDiv" class="divScroll">
		<display:table name="lista_Administrador" excludedParams="*" uid="tablaContactos"
			id="tablaContactos" summary="Listado Contactos" cellspacing="0"
			cellpadding="0" sort="external" class="tablacoin">

			<display:column property="dni" title="DNI"
				headerClass="sortable" />
			<display:column property="certificado" title="Certificado"
				headerClass="sortable" />
			<display:column property="descripcion" title="Descripcion"
				headerClass="sortable" />
			<display:column style="width:1%">
				<table align="center">
					<tr>
						<td align="left" nowrap="nowrap" style="width: 2%"><img
							src="img/ocultar.gif" alt="borrar " id="bBorrarContacto"
							style="height: 20px; width: 20px; cursor: pointer;"
							onclick="eliminar_administrador('${tablaContactos.id}');"
							title="Borrar Administrador"></td>
					</tr>
				</table>
			</display:column>
		</display:table>
	</div>
</div>