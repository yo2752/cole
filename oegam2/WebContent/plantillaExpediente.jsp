
<%@ page contentType="text/html; charset=utf-8" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles" %>
<%@ taglib prefix="s" uri="/struts-tags" %>

<html xmlns="http://www.w3.org/1999/xhtml">
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=iso-8859-1"/>
		<meta http-equiv="Pragma" content="no-cache"/>
		<meta http-equiv="Cache-Control" content="no-cache"/>
		<meta http-equiv="Expires" content="-1" />
		<link rel="shortcut icon" href="img/favIcon2.ico" />
		<link href="css/estilos.css" rel="stylesheet" type="text/css" media="screen" />
		<title><tiles:insertAttribute name="titulo" ignore="true" /></title>

		<style type="text/css">
			body{background:none}
		</style>
	</head>
	<body>
		<script type="text/javascript">
			var arrIssuers = new Array();
			var arrSNs = new Array();

			//FUNCIÓN DE LOGIN
			function login() {
				var formulario = document.forms[0];
				//Las redirecciones hay que leerlas de propiedades o constantes... ¡pero nunca a piñón!
				//firmaOnClick();
				formulario.action = "accesoUsuario!loginByCertificate.action";
				formulario.submit();
				return false;
			}

			function salir() {
				if (confirm(MSG_CONF_CERRAR_APLICACION)) {
					document.forms[0].action='accesoUsuario!cerrarSesion.action';
					document.forms[0].submit();
					return false;
				}
			}
		</script>

		<div class="principal_tiles" id="principal_tiles">
			<div class="menu_cuerpo">
				<div class="cuerpo_tiles">
					<tiles:insertAttribute name="cuerpo" />
				</div>
				<div class="pie_tiles">
					<tiles:insertAttribute name="pie" />
				</div>
			</div>
		</div>
		<script type="text/javascript">
			//redimensionarCuerpo();
		</script>
	</body>
</html>