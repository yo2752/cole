<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css"
			media="screen" />

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Información del certificado</title>

		<script src="js/keyStores.js" type="text/javascript"></script>
	</head>
	<body class="popup">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Información del certificado</td>
			</tr>
		</table>

		<table class="tablaformbasica" cellspacing="3" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="center" style="vertical-align: middle" width="50%">
					<s:textarea id="infoCertificado"
					name="infoCertificado" cols="75" rows="30" />
				</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="center" style="vertical-align: middle">
					<input type="button" value="Cerrar" class="boton"
						onclick="javascript:invalidarInfo();window.close();" />
				</td>
			</tr>
		</table>
	</body>
</html>