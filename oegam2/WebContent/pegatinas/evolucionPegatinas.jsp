<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
	</head>

	<script src="js/tabs.js" type="text/javascript"></script>
	<script src="js/contrato.js" type="text/javascript"></script>
	<script src="js/validaciones.js" type="text/javascript"></script>
	<script src="js/genericas.js" type="text/javascript"></script>
	<script src="js/general.js" type="text/javascript"></script>
	<script src="js/pegatinasBotones.js" type="text/javascript"></script>

	<body class="popup">
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular">
						<span class="titulo">EVOLUCIÓN DE DISTINTIVOS MEDIOAMBIENTALES</span>
					</td>
				</tr>
			</table>
		</div>

		<div id="tablaformbasica" class="tablaformbasica">
			<display:table name="listaRespuestas" excludedParams="*" class="tablanotifi"
				uid="tablaEvoPegatinas" summary="Evolucion Pegatinas" cellspacing="0" cellpadding="0" sort="external">

				<display:column property="idPegatina" title="IdPegatina" sortable="true" headerClass="sortable" defaultorder="descending" style="width:2%" />
				<display:column property="estado" title="Estado" sortable="true" headerClass="sortable" defaultorder="descending" style="width:4%" />
				<display:column property="fecha" title="Fecha" sortable="true" headerClass="sortable" sortProperty="fecha" style="width:4%"/>
				<display:column property="numColegiado" title="Num. Colegiado" sortable="true" headerClass="sortable" sortProperty="numColegiado" style="width:4%"/>
			</display:table>

			<table class="acciones">
				<tr>
					<td>
						<div>
							<input class="boton" type="button" id="bCerrarVentana" name="bCerrarVentana" value="Cerrar" onClick="return cerrar();" onKeyPress="this.onClick" />
						</div>
					</td>
				</tr>
			</table>
		</div>
	</body>
</html>