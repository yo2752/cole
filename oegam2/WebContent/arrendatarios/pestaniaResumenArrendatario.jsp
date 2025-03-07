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
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRNumExpeConsulta">N&uacute;m. Expediente: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{arrendatarioDto.numExpediente}" />
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120"><label
				for="labelRNumExpeConsulta">Ref. Propia: </label></td>
			<td align="left" nowrap="nowrap"><s:label
					value="%{arrendatarioDto.refPropia}" /></td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRidConsulta">Id Consulta: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{arrendatarioDto.idArrendatario}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRMatriculaConsulta">Matr&iacute;cula: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{arrendatarioDto.matricula}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap" width="120">
				<label for="labelRBastidorConsulta">Bastidor: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{arrendatarioDto.bastidor}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRestado">Estado: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.arrendatarios.model.enumerados.EstadoCaycEnum@convertirTexto(arrendatarioDto.estado)}"/>
			</td>
		</tr>
		<tr>
			<td align="left" nowrap="nowrap">
				<label for="labelRestado">Tipo Operaci&oacute;n: </label>
			</td>
			<td align="left" nowrap="nowrap">
				<s:label value="%{@org.gestoresmadrid.core.arrendatarios.model.enumerados.TipoOperacionCaycEnum@convertirTexto(arrendatarioDto.operacion)}"/>
			</td>
		</tr>
		<s:if test="arrendatarioDto.respuesta != null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRRespuesta">Respuesta: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{arrendatarioDto.respuesta}"/>
				</td>
			</tr>
		</s:if>
		<s:if test="arrendatarioDto.fechaPresentacion != null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRRespuesta">Fecha Presentaci&oacute;n: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{arrendatarioDto.fechaPresentacion}" />
				</td>
			</tr>
		</s:if>
		<s:if test="arrendatarioDto.numRegistro != null">
			<tr>
				<td align="left" nowrap="nowrap">
					<label for="labelRRespuesta">N&uacute;mero de registro: </label>
				</td>
				<td align="left" nowrap="nowrap">
					<s:label value="%{arrendatarioDto.numRegistro}" />
				</td>
			</tr>
		</s:if>
	</table>
</div>