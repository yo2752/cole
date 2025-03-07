<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<s:if test="false">
<style>
	#bienvenida{display:none;}
	#avisoPolicia{display:block;}
</style>
<div id="avisoPolicia">
<!-- Para poner un mensaje de aviso de pantalla principal.
		Se debe de cambiar el if a true y dentro del div realizar los cambios que se necesiten  --> 
 
	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular" align="center"><span class="titulo">
					AVISO MUY IMPORTANTE. 
				</span></td>
			</tr>
			<tr>
				<td class="tabular"><span class="titulo">
					Con objeto de no incurrir en posibles responsabilidades penales es OBLIGATORIO leer el documento entero. En dos minutos será redirigido...
				</span></td>
			</tr>
		</table>
	<img class="PopcalTrigger" align="left" src="img/avisoPolicia3.png" width="700" height="900"
	border="0" alt="Aviso Policía" />
	</div>
</div>

<%
	if("/oegam2/Registrar.action".equals(request.getAttribute("javax.servlet.forward.request_uri"))){
%>

<script type="text/javascript">
window.setTimeout("document.getElementById('avisoPolicia').style.display='none'", 120000);
window.setTimeout("document.getElementById('bienvenida').style.display='block'", 120000);
</script> 
<%
}else{
%>

<script type="text/javascript">
document.getElementById('avisoPolicia').style.display='none';
document.getElementById('bienvenida').style.display='block';
</script> 
<%
}
%>
</s:if>