<%@ taglib prefix="s" uri="/struts-tags"%>

<div align="center">Oficina Electrónica de Gestión Administrativa (OEGAM)</div>
<div align="center">Correo Electrónico de contacto: informatica@gestoresmadrid.org</div>

<!-- Avisa al usuario de que ha firmado la notificacion y que va a firmar el envío del mensaje. -->
<s:if test="%{#request.acuseFirmado!=null}"> 
	<script>
		alert("La notificación se ha firmado correctamente. A continuación se requerirá de nuevo la información de su certificado para firmar el envío del mensaje.");
		ejecutarAccion("construirContinueAcuse.action");
	</script>
</s:if>

