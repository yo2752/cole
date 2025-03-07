<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<table width="100%" class="subtitulo" cellSpacing="0" cellspacing="0" align="left">
		<tr>
			<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
			<td colspan="4">Resultado Presentación Jefatura Provincial de Tráfico</td>
		</tr>
	</table>

<s:form id="formData" name="formData" action="presentacionPresentacionJPT.action">

	<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica" style="text-align: left; vertical-align: middle;">
		<tr height="10px">
			<td><span style="font-size: 150%;"></span></td>
		</tr>
		<s:iterator value="resumenExpedientes">
			<tr height="20px">
				<td>
					<span style="font-size: 150%;"><s:property value="idDocumento"/></span>
				</td>
			</tr>
			<tr>
				<td>
					<strong><span style="font-size: 130%;">
						<s:property	value="resultadoPresentacion" /></span>
					</strong>
				</td>
			</tr>
			<tr>
				<td>
					<div style="clear: both; height: 3em;"></div>
				</td>
			</tr>
		</s:iterator>
	</table>

	<table class="acciones">
		<tr>
			<td>
				<div>
					<input type="button" class="boton" name="bVolver"
						id="bVolver" value="Nueva alta" onClick="window.location='PresentacionJPT.action'" />
				</div>
			</td>
		</tr>
	</table>
</s:form>

<script type="text/javascript">
	$(window).load(function() {
		document.getElementById("bVolver").focus();
	});
</script>