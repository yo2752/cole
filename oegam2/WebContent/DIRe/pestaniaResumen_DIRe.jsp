<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="s" uri="/struts-tags" %>

<div class="contenido">
	<%@include file="../../includes/erroresMasMensajes.jspf" %>

	<table class="nav" cellSpacing="1" cellPadding="5" width="100%" align="left">
		<tr>
			<td class="tabular">
				<span class="titulo">Resumen</span>
			</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0" align="left">
		<tr>
			<td align="left" nowrap="nowrap" width="120"><label
				for="labelRNumExpeConsulta">Num Expediente: </label>
			</td>
			<td align="left" nowrap="nowrap"><s:label
						value="%{empresaDIReDto.numExpediente}" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRMatriculaConsulta">DIRe: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{empresaDIReDto.codigoDire}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRBastidorConsulta">Nombre: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{empresaDIReDto.nombre}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRestado">Estado: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum@convertirTexto(empresaDIReDto.estado)}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRestado">Tipo Operación: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum@convertirTexto(empresaDIReDto.operacion)}"/>
			</td>
		</tr>
		<s:if test="empresaDIReDto.respuesta != null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRRespuesta">Respuesta: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{empresaDIReDto.respuesta}"/>
				</td>
			</tr>
		</s:if>
		<s:if test="empresaDIReDto.numRegistro != null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRRespuesta">Número de registro: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{empresaDIReDto.numRegistro}"/>
				</td>
			</tr>
		</s:if>
	</table>
</div>