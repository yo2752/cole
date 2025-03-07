<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
<head>

<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/sanciones/sancionBotones.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>

<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Selección de estado</title>

</head>

<body class="popup">	
	
		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular">
						<span class="titulo">Selección de estado</span>
					</td>
				</tr>
			</table>
		</div> <!-- div nav -->
						
		
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Seleccione el nuevo estado</td>
			</tr>
		</table>	
		
		<!-- Seleccionar el archivo -->
		<table class="tablaformbasica" cellspacing="3" cellpadding="0">
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" align="left" width="30%" style="vertical-align:middle">
					<label for="estadoTramiteLabel">Estados<span class="naranja">*</span>:</label>
					<s:select id="estadoSancion" 
							listKey="valorEnum"
							listValue="nombreEnum"
							headerKey="-1"
			           		headerValue=""
							list="@org.icogam.sanciones.Utiles.UtilesVistaSancion@getInstance().getEstadoSancion()"/>
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" width="30%" align="left" style="vertical-align:middle">
					<input type="button" value="Seleccionar" class="boton" onclick="estadoSancionSeleccionado()"/>
					&nbsp;
					<input type="button" value="Cancelar" class="boton" onclick="javascript:window.close();"/>
				</td>
			</tr>
		</table>		
			    	
<script type="text/javascript">
// Función que se ejecuta cuando se selecciona estado en el pop up de cambiar estados de trámites</script>

</body>
</html>	