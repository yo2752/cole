<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
	</head>

	<body class="popup">

		<script src="js/pegatinasBotones.js" type="text/javascript"></script>

		<s:form method="post" id="formData" name="formData">
			<div class="nav">
				<table width="100%">
					<tr>
						<td class="tabular">
							<span class="titulo">INTRODUZCA LOS IDENTIFICADORES ERRONEOS DE LAS PETICIONES SELECCIONADAS</span>
						</td>
					</tr>
				</table>
			</div>
			<%@include file="../../includes/erroresYMensajes.jspf"%>
			<div id="tablaformbasica">
				<table width="100%" id="tablaIdentificadores">
					<s:iterator value="listaNotificacion" status="indice">
						<tr>
							<td>
								<span class="titulo">Identificador para matrícula <s:property value="matricula"/> del expediente <s:property value="numExpediente"/></span>
									<center> <input type="text" name="input" value=""></center>
								<input type="hidden" value='<s:property value="numExpediente"/>' name="listaNotificacion.numExpediente" />
							</td>
						</tr>
					</s:iterator>
				</table>
			<input type="hidden" name="" value="">
			<table class="acciones">
				<tr>
					<td>
						<div>
							<input type="hidden" value='<s:property value="tipoNotificacion"/>' name="tipoNotificacion" />
							<input class="boton" type="button" id="bEnviar" name="bEnviar" value="Enviar" onClick="return notificar();"/>
							<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrar();" onKeyPress="this.onClick" />
						</div>
					</td>
				</tr>
			</table>
		</div>
		</s:form>
	</body>
</html>