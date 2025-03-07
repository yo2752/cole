<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/mensajes.js" type="text/javascript"></script>
<script src="js/procesos/procesos.js" type="text/javascript"></script>

<div class="nav">
	<table width="100%">
		<tr>
			<td class="tabular"><span class="titulo">Monitor del Gestor de Procesos</span></td>
		</tr>
	</table>
</div>

<s:form method="post" id="formData" name="formData">
	<s:hidden name="pantalla"/>
	<table width="100%">
		<tr>
			<td align="center">
				<label for="idUserName">Usuario:</label>
			</td>
			<td align="left">
				<s:textfield onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idUsername"
					maxlength="40" size="20" name="username"/>
			<td align="center">
				<label for="idPassword">Password:</label>
			</td>
			<td align="left">
				<s:password autocomplete="off" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idPassword" maxlength="50" size="40" name="password"/>
			</td>
			<td align="left">
				<input type="button" class="boton" name="bEntrar" id="bEntrar" value="Entrar" onkeypress="this.onClick" onclick="return comprobarPasswordGestionProcesos();" />				
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<input type="button" class="botonGrande" name="bCambiar" id="bCambiar" value="Cambiar Contraseña" onkeypress="this.onClick" onclick="return irCambioPassword();" />				
			</td>
		</tr>
	</table>
</s:form>
<%@include file="../../includes/erroresMasMensajes.jspf"%>

<script>
$(document).ready(function(){
	$("#idPassword").keypress(function(e) {
	    if(e.which == 13) {
	    	comprobarPasswordGestionProcesos();
	    }
	});
});
</script>
