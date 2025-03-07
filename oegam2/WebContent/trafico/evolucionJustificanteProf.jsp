<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<script src="js/tabs.js" type="text/javascript"></script>
		<script src="js/contrato.js" type="text/javascript"></script>
		<script src="js/validaciones.js" type="text/javascript"></script>
		<script src="js/genericas.js" type="text/javascript"></script>
		<script src="js/trafico/comunes.js" type="text/javascript"></script>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Evolución del Justificante Profesional: <s:property value="idJustificante"/></title>
	</head>

	<script type="text/javascript">
	</script>

	<body class="popup">
		<div id="contenido" class="contentTabs" style="display: block;text-align: center;">
			<div class="nav">
				<table width="100%">
					<tr>
						<td class="tabular">
							<span class="titulo">Evolución del Justificante Profesional</span>
						</td>
					</tr>
				</table>
			</div>
		</div>
		<%@include file="../../includes/erroresYMensajes.jspf" %>

		<div>
			<display:table name="lista" excludedParams="*"
							requestURI="navegar${action}"
							class="tablacoin"
							uid="tablaEstadosJustificantesProf"
							summary="Listado de Estados de un Justificante Profesional"
							cellspacing="0" cellpadding="0"
							decorator="${decorator}">

			<display:column property="idJustificante" title="Número de Justificante" style="width:80px;" />

			<display:column property="id.numExpediente" title="Número de expediente" style="width:110px;" />

			<display:column property="id.fechaCambio" title="FECHA" style="width:110px;"  sortable="true" headerClass="sortable"
					defaultorder="descending" maxLength="22" format="{0,date,dd-MM-yyyy HH:mm:ss}"/>

			<display:column title="Estado Anterior" sortProperty="id.estadoAnterior" property="estadoAnterior"
					style="width:110px;" sortable="true" headerClass="sortable" defaultorder="descending">
					<%--<s:property value="%{@trafico.utiles.enumerados.EstadoJustificante@convertirTexto(#attr.tablaEstadosJustificantesProf.id.EstadoAnterior)}" /> --%>
			</display:column>

			<display:column title="Estado Nuevo" sortProperty="id.estado" property="estadoNuevo"
					style="width:110px;" sortable="true" headerClass="sortable" defaultorder="descending">
					<%--<s:property value="%{@trafico.utiles.enumerados.EstadoJustificante@convertirTexto(#attr.tablaEstadosJustificantesProf.id.estado)}" /> --%>
			</display:column>

			<display:column title="Usuario" property="usuario.apellidosNombre" style="width:110px;" sortable="true"
					headerClass="sortable" defaultorder="descending"/>

		</display:table>

		<table class="acciones">
			<tr>
				<td>
					<div>
						<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrarEvolucionJustificante();" onKeyPress="this.onClick" />
					</div>
				</td>
			</tr>
		</table>
		</div>

		<div id="ventana" style="position:absolute;left:450px;top:260px;filter:alpha(opacity=100);opacity:1.00;width:340px;height:auto;border:1px solid black;background-color:#FFDDDD;text-align:left;padding-left:0px;visibility:hidden">Datos de entrada</div>

	</body>
</html>