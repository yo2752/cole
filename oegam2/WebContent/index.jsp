<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>

<!-- Se invalida la sesión, si la hubiera  -->
<html>
	<head>
		<script src="js/session.js" type="text/javascript"></script>
	</head>
	<body>
		<script type="text/javascript">
			var insideIframe = window.top !== window.self;

			if (insideIframe == true) {
				parent.location.href = '/oegam2';
			}
		</script>

		<s:if test="#application.AutenticacionSISTEMA=='false'">
			<META HTTP-EQUIV="Refresh" CONTENT="0;URL=Registrar.action">
		</s:if>
		<s:elseif test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().getNifUsuarioSession() != null">
			<META HTTP-EQUIV="Refresh" CONTENT="0;URL=notificarRegistrar.action">
		</s:elseif>
		<s:else>
			<script>
				if (insideIframe == false) {
					window.addEventListener('storage', storageCapturado, true);
				}
			</script>
			<iframe style="border: none; height: 100%; position: absolute; width: 100%;"
					name="frame_viafirma" src="Login.action" id="frame_viafirma">
			</iframe>
		</s:else>

	</body>
</html>