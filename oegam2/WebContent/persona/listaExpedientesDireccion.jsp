<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Listado de expedientes asociados a la persona y dirección</title>
	</head>
	<body>
		<div class="nav">
			<table cellSpacing="0" cellPadding="5" width="100%">
				<tr>
					<td class="tabular">
						<span class="titulo">Lista de expedientes asociada a la persona y dirección</span>
					</td>
				</tr>
			</table>
		</div>

		<div style="float:left; width:50%; margin:1em; padding:1em; text-align:left;">
			<strong>NIF</strong>
			&nbsp;
			<span><s:property value="nif" /></span>
			<br />
			<strong>Dirección</strong>
			<br/>
			<span><s:property value="direccionExpediente.idTipoVia" /> <s:property value="direccionExpediente.nombreVia" />, <s:property value="direccionExpediente.numero" /> <s:property value="direccion.letra" /></span>
			<br />
			<s:property value="direccionExpediente.codPostal" /> <s:property value="direccionExpediente.nombreMunicipio" />, <s:property value="direccionExpediente.nombreProvincia" />
		</div>

		<table width="100%" class="tablacoin" cellpadding="1" cellspacing="0">
			<tr>
				<th>Número de Expediente</th>
				<th>Tipo de Interviniente</th>
			</tr>
			<s:iterator value="listaExpedientesDireccion" status="row">
				<tr>
					<td><s:property value="numExpediente" /></td>
					<td>
						<s:property value="tipoIntervinienteDes"/>
					</td>
				</tr>
			</s:iterator>
		</table>

		<table class="acciones" width="95%" align="left">
			<tr>
				<td align="center" style="size: 100%; TEXT-ALIGN: center; list-style: none;">
					<input type="button" value="Volver" onclick="history.back()" class="boton" />
				</td>
			</tr>
		</table>
	</body>
</html>