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
	<s:hidden name="cambio"/>
	<s:hidden name="pantalla"/>
	<table width="100%">
		<tr>
			<td align="center">
				<label for="idUserName">Usuario:</label>
			</td>
			<td align="left">
				<s:textfield type="text" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idUsernameCamb"
					maxlength="40" size="20" name="username" readonly="true"/>
			<td align="center">
				<label for="idPassword">Password Antigua:</label>
			</td>
			<td align="left">
				<s:password autocomplete="off" onblur="this.className='input2';" 
					onfocus="this.className='inputfocus';" id="idPasswordCamb" maxlength="50" size="30" name="password"/>
			</td>
			<td align="center">
				<label for="idPassword">Password Nueva:</label>
			</td>
			<td align="left">
				<s:password type="password" autocomplete="off" onblur="this.className='input2';"
					onfocus="this.className='inputfocus';" id="idPasswordNueva" value="" maxlength="50" size="30" name="passwordNueva"/>
			</td>
		</tr>
		<tr>
			<td align="center" colspan="6">
				<input type="button" class="botonGrande" name="bCambiar" id="bCambiar" value="Cambiar Contraseña" onkeypress="this.onClick" onclick="return cambiarPasswordGestionProcesos();" />				
			</td>
		</tr>
	</table>
</s:form>
<%@include file="../../includes/erroresMasMensajes.jspf"%>

<script>
$(document).ready(function(){
	$("#idPasswordNueva").keypress(function(e) {
	    if(e.which == 13) {
	    	cambiarPasswordGestionProcesos();
	    }
	});
});
</script>
