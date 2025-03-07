<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html>
<head>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Evolucion de la Presentacion del Tramite a la Jefatura Provincial de Trafico: </title>
</head>

<body class="popup">
	<div id="contenido" class="contentTabs" style="display: block;text-align: center;">	
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular">
						<span class="titulo">Evolución expediente: <s:property value="numExpedienteEvolucion"/> </span>
					</td>
				</tr>
			</table>
		</div>
	</div>
	<div>
		<display:table name="listaEvolucion" class="tablacoin" summary="Listado de Evolucion Expediente"
					cellspacing="0" cellpadding="0" style="width: 100%">
			<display:column property="fechaCambio" title="Fecha" format="{0,date,dd-MM-yyyy HH:mm:ss}" />
			<display:column property="usuario" title="Usuario" />
			<display:column property="jefatura" title="Jefatura" />
			<display:column property="estadoAnterior" title="Estado anterior" />
			<display:column property="estadoNuevo" title="Estado nuevo" />
		</display:table>

		<table class="acciones">
			<tr>
				<td>
					<div>
					<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrarEvolucionTramite();" onKeyPress="this.onClick" />
					</div>
				</td>
			</tr>
		</table>
	</div>
</body>