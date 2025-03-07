<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<script type="text/javascript" src="js/registradores/registradores.js"></script>

<script type="text/javascript">
	function volver() {
		window.history.back();
	}
</script>

<div>
	<h4>${contenido}</h4>
</div>

<br></br><br></br>
<s:form id="formularioDocumento" name="formularioDocumento" cssStyle="width:100%;background-color:transparent;">
	<s:hidden name="tramiteRegistro.tipoTramite"/>
	<s:hidden name="tramiteRegistro.idTramiteRegistro"/>
</s:form>

<center>
	<input type="button" class="boton" name="bVolver" id="botonVolver" value="Volver" onClick="cerrarVistaDocumento();" onKeyPress="this.onClick" />
	&nbsp;
	<img id="loading9Image" style="display:none;margin-left:auto;margin-right:auto;" src="img/loading.gif">
</center>

