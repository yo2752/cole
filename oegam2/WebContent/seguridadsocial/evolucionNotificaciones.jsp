<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>


<html>
	<head>
		<script src="js/notario/notarioFunction.js" type="text/javascript"></script>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
	</head>
	
	<script src="js/tabs.js" type="text/javascript"></script>
	<script src="js/contrato.js" type="text/javascript"></script>
	<script src="js/validaciones.js" type="text/javascript"></script>
	<script src="js/genericas.js" type="text/javascript"></script>
	<script src="js/general.js" type="text/javascript"></script>
	<script src="js/consultaNotificacionBotones.js" type="text/javascript"></script>

	<body class="popup">
	
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">EVOLUCIÓN DE NOTIFICACIONES</span>
				</td>
			</tr>
		</table>
	</div>
	
	<div id="tablaformbasica" class="tablaformbasica">
		<display:table name="listaRespuestas" excludedParams="*" class="tablanotifi"
			uid="tablaRespuestaSS" summary="Respuestas Seguridad Social" cellspacing="0" cellpadding="0" sort="external"
			decorator="org.gestoresmadrid.oegam2.view.decorator.DecoratorRespuestaSeguridadSocial">
			
			<display:column property="notificacion.id.codigo" title="Código Notificacion" sortable="false" defaultorder="descending" style="width:2%" />
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin() || @org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoColegio()}">
				<display:column property="notificacion.id.numColegiado" title="Num. Colegiado" sortable="false" defaultorder="descending" style="width:2%" />
			</s:if>
			<display:column property="estado" title="Estado" sortable="false" defaultorder="descending" style="width:4%" />				
			<display:column property="fechaNotificacion" title="Fecha" sortable="false" sortProperty="fechaNotificacion" style="width:4%"/>	
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
		
