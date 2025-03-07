<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/trafico/comunes.js" type="text/javascript"></script>
<script  type="text/javascript"></script>

<!--<div id="contenido" class="contentTabs" style="display: block;"> -->

	<div class="nav">
		<table width="100%" >
			<tr>
				<td class="tabular">
					<span class="titulo">Agencia tributaria: Descarga justificante 576</span>
				</td>
			</tr>
		</table>
	</div>

<s:form method="post" id="formData" name="formData">
	<table class="acciones" border="0">
		<tr>
			<td align="right" nowrap="nowrap">
				<label for="idPassword">Introduzca el código seguro de verificación:</label>
			</td>
			<td align="center" nowrap="nowrap">
				<s:textfield name="csv" id="csv" />
			</td>
			<td align="left" nowrap="nowrap" colspan="2">
				<input type="button" class="botonMasGrande" name="bDescarga576" id="bDescarga576"
					value="Descargar justificante" onkeypress="this.onClick"
					onclick="return descargarJustificante576();" />
			</td>
		</tr>
	</table>
</s:form>
<%@include file="../../includes/erroresMasMensajes.jspf"%>

<script>
function descargarJustificante576() {
	var $csv = $("#csv");
	if (!$csv.val()) {
		alert("Debe introducir un código seguro de verificación");
		$csv.focus();
	} else {
		var $form = $("#formData");
		$("#divError").remove();
		$form.attr('action', 'descargaJustificante576.action');
		$form.submit();
	}
}

$(document).ready(function(){
	$("#csv").keypress(function(e) {
		if(e.which == 13) {
			descargarJustificante576();
		}
	});
});
</script>