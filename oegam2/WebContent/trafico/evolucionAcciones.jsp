<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
<head>
<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/consultaTramites.js" type="text/javascript"></script>
<script type="text/javascript" src="js/jquery-1.8.2.min.js"></script>
<script type="text/javascript" src="js/jquery.displaytag-ajax-oegam.js"></script>

<link href="css/estilos.css" rel="stylesheet" type="text/css"
	media="screen" />
	
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1" />
<title>ACCIONES ASOCIADAS AL TRÁMITE: <s:property value="numExpediente" /></title>
</head>
<body class="popup">
<div>
	<div id="contenido" class="contentTabs"
		style="display: block; text-align: center;">
		<div class="nav">
			<table width="100%">
				<tr>
					<td class="tabular"><span class="titulo">Acciones asociadas al trámite <s:property value="beanCriterios.numExpediente" />
					</span></td>
				</tr>
			</table>
		</div>
	</div>
	<%@include file="../../includes/erroresYMensajes.jspf"%>

	<s:if
		test="%{lista.getFullListSize()>@escrituras.utiles.UtilesVista@RESULTADOS_POR_PAGINA_POR_DEFECTO}">
		<table width="100%">
			<tr>
				<td align="right">
					<table width="100%">
						<tr>
							<td width="90%" align="right"><label
								for="idResultadosPorPagina">&nbsp;Mostrar resultados</label></td>
							<td width="10%" align="right"><s:select
									list="@escrituras.utiles.UtilesVista@getInstance().getComboResultadosPorPagina()"
									id="idResultadosPorPagina" value="resultadosPorPagina"
									title="Resultados por página"
									onchange="return cambiarElementosPorPaginaAcciones();" /></td>
						</tr>
					</table>
				</td>
			</tr>
		</table>
	</s:if>
<%@include file="./evolucionTabla.jspf" %>
	
	<div>
		<table class="acciones">
			<tr>
				<td>
					<div>
						<input class="boton" type="button" id="bCerrarVentana"
							name="bCerrarVentana" value="Cerrar"
							onClick="return cerrarEvolucionTramite();"
							onKeyPress="this.onClick" />
					</div>
				</td>
			</tr>
		</table>

	</div>
</div>
</body>
</html>