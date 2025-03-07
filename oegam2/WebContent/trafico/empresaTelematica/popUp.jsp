<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<html>
	<head>
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<script src="js/validaciones.js" type="text/javascript"></script>
		<script src="js/genericas.js" type="text/javascript"></script>
		<script src="js/jquery-1.8.2.min.js" type="text/javascript"></script>
		<script src="jquery.displaytag-ajax-oegam.js" type="text/javascript"></script>
		<script src="js/trafico/empresaTelematica.js" type="text/javascript"></script>

		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Selección de estado</title>
	</head>

	<body class="popup">
	<s:form method="post" name="formData" id="formData" action="" cssStyle="overflow:hidden;">

		<div class="nav">
			<table width="100%" >
				<tr>
					<td class="tabular">
						<span class="titulo">Selección de estado</span>
					</td>
				</tr>
			</table>
		</div><!-- div nav -->

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
					<label for="estadoLabel">Estados<span class="naranja">*</span>:</label>
					<s:select label="Estado Empresa:" name="estadoEmpresa" id="estadoEmpresa"
						list="@org.gestoresmadrid.oegam2.trafico.empresa.telematica.utiles.UtilesVistaEmpresaTelematica@getInstance().getEstados()"
						listKey="valorEnum" listValue="nombreEnum"
						onblur="this.className='input2';"
						onfocus="this.className='inputfocus';"
						required="true" value="%{tipoTramite}"
						headerKey="-"
						headerValue="-" />
				</td>
			</tr>
			<tr>
				<td>&nbsp;</td>
			</tr>
			<tr>
				<td nowrap="nowrap" width="30%" align="left" style="vertical-align:middle">
					<input type="button" value="Seleccionar" class="boton" onclick="estadoSeleccionado()"/>
					&nbsp;
					<input type="button" value="Cancelar" class="boton" onclick="javascript:window.close();"/>
				</td>
			</tr>
		</table>
	</s:form>
	<script type="text/javascript">
		function estadoSeleccionado(){
			var estado = document.getElementById("estadoEmpresa").value;
			if(estado == "-"){
				alert("Seleccione un estado");
				return false;
			}
			window.opener.invokeCambiarEstadoEmpresa(estado);
		}
	</script>
	</body>
</html>	