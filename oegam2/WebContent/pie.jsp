<%@ taglib prefix="s" uri="/struts-tags"%>

<div align="center">Oficina Electr�nica de Gesti�n Administrativa (OEGAM)</div>
<div align="center">Correo Electr�nico de contacto: informatica@gestoresmadrid.org</div>

<!-- Avisa al usuario de que ha firmado la notificacion y que va a firmar el env�o del mensaje. -->
<s:if test="%{#request.acuseFirmado!=null}"> 
	<script>
		alert("La notificaci�n se ha firmado correctamente. A continuaci�n se requerir� de nuevo la informaci�n de su certificado para firmar el env�o del mensaje.");
		ejecutarAccion("construirContinueAcuse.action");
	</script>
</s:if>

