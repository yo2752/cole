<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<link rel="stylesheet" type="text/css" href="css/gdoc.css" />

<script src="js/tabs.js" type="text/javascript"></script>
<script src="js/contrato.js" type="text/javascript"></script>
<script src="js/validaciones.js" type="text/javascript"></script>
<script src="js/genericas.js" type="text/javascript"></script>
<script src="js/colas.js" type="text/javascript"></script>
<script type="text/javascript"></script>

	<div class="" id="AltaJPT" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
		<div class="contenido">
			<table class="subtitulo" cellSpacing="0" cellspacing="0">
				<tr>
					<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
					<td>Presentación Jefatura Provincial de Tráfico</td>
				</tr>
			</table>

			<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
				<tr>
					<td>
						<table style="width: 100%;">
							<tr>
								<td style="text-align:right;vertical-align:middle; padding-right:2em; width: 30%">
									<label for="docId">ID Docs. Base:</label>
								</td>
								<td>
									<input id="base" type="text" size="36" maxlength="36" name="listadoIds" onkeyup="startCountdown()" onkeydown="clearCountdown()" />
								</td>
								<td style="text-align: right">
									Jefatura:
									<s:if test="usuario.jefaturaJPT != null">
										<s:property value="usuario.jefaturaJPT" />
									</s:if>
									<s:else>
										<span style="color:red">NO ESPECIFICADA</span>
									</s:else>
								</td>
							</tr>
						</table>
					</td>
				</tr>
			</table>

			<s:if test="hasActionMessages() || hasActionErrors()">
				<div id="divError">
					<table class="tablaformbasica" width="100%">
						<tr>
							<td align="left">
								<ul id="mensajesInfo" style="color:green; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
								<s:if test="hasActionMessages()">
									<s:iterator value="actionMessages">
										<li><span><s:property /></span></li>
									</s:iterator>
								</s:if>
								</ul>
								<ul id="mensajesError" style="color:red; font-family:tahoma, arial, sans-serif; font-size:11px; font-weight:bold;">
								<s:if test="hasActionErrors()">
									<s:iterator value="actionErrors">
										<li><span><s:property /></span></li>
									</s:iterator>
								</s:if>
								</ul>
							</td>
						</tr>
					</table>
				</div>
			</s:if>

			<%--
			<div id="resultado" style="width: 100%; background-color: transparent;">
				<h2 class="subtitulo">Identificadores </h2>
			</div>
			 --%>

			<s:form method="post" id="formData" name="formData" action="confirmarPresentacionJPT.action">
			<div style="max-height:568px; overflow-y: auto;">
				<table id="tablaConsultaTramite" style="width:100%; margin:.5em 0;" cellpadding="0" class="tablacoin" cellspacing="1">
					<thead>
						<tr>
							<th style="text-align: center !important;vertical-align: middle !important;width:3em">#</th>
							<th style="text-align: center !important;vertical-align: middle !important;">ID Documento</th>
							<th style="text-align: center !important;vertical-align: middle !important;width:3em">Eliminar</th>
						</tr>
					</thead>
					<tbody class="input_fields_wrap">
					</tbody>
				</table>
			</div>
			<table class="acciones">
				<tr>
					<td align="center">
						<input id="bLimpiar" type="button" value="Limpiar Tabla" class="boton" onClick="return limpiarCampos();" />
					</td>
					<td>
						<input id="bEnviar" type="button" value="Enviar" class="boton" onClick="return enviarLecturaQR();" />
					</td>
				</tr>
			</table>
			</s:form>
		</div>
	</div>

<script type="text/javascript">
	//setup before functions
	var typingTimer;				//timer identifier
	var doneTypingInterval = 200;	//time in ms, 0.5 second for example

	var x = 0; //initial text box count
	var wrapper		= $(".input_fields_wrap"); //Fields wrapper
	var add_button	= $(".add_field_button"); //Add button ID

	$(document).ready(function() {
		$(wrapper).on("click",".remove_field", function(e) { // user click on remove text
			e.preventDefault(); $(this).parent('td').parent('tr').remove();
			x--;
			document.getElementById(x).focus();
		});
	});

	function startCountdown() {
		clearTimeout(typingTimer);
		typingTimer = setTimeout(autoAddRow, doneTypingInterval);
	}

	function clearCountdown() {
		clearTimeout(typingTimer);
	}

	function autoAddRow() {
		var valor = $('#base').val().trim();
		if (!(valor == '')) {
			x++;
			var contadorAux = '<tr id="_'+x+'"><td style="text-align: center !important;vertical-align: middle !important;width:3em"><label>'+x+'</label> </td>';
			var input = '<td style="text-align: center !important;vertical-align: middle !important;"><label>'+valor+'</label><input id="'+x+'" value="'+valor+'" type="hidden" size="40" maxlength="36" name="listadoIds" /></td>';
			var imagen = '<td style="text-align: center !important;vertical-align: middle !important;"><a href="#" class="remove_field"><img src="img/error2.gif" width="15" height="16" /></a></td></tr>';
			$(wrapper).append(contadorAux+input+imagen);
			$('#base').val('');
			location.hash = "#_" + x;
			$("#base").focus();
		}
	}

	function limpiarCampos() {
		for (x; x >= 1; x--) {
			$('#'+x).parent('td').parent('tr').remove();
		}
		$('#base').val('');
		$('#base').focus();
	}

	function corregirEntradaPistola() {
		var campo = document.getElementById("docId").value;
		//campo = replaceAll('-','/',campo);
		campo = replaceAll("'",'-',campo);
		document.getElementById("docId").value = campo;
	}

	function corregirEntradaEnter(e) {
		var tecla = (document.all) ? e.keyCode : e.which;
		if (tecla == 13) {
			corregirEntradaPistola();
		}
	}

	function replaceAll(find, replace, str) {
		return str.replace(new RegExp(find, 'g'), replace);
	}

	function enviarLecturaQR() {
		deshabilitarBotonesLecturaQR();
		document.formData.action = "resumenPresentacionJPT.action";
		document.formData.submit();
	}

	function deshabilitarBotonesLecturaQR() {
		document.getElementById("bEnviar").disabled = "true";
		document.getElementById("bEnviar").style.color = "#6E6E6E";
		document.getElementById("bLimpiar").disabled = "true";
		document.getElementById("bLimpiar").style.color = "#6E6E6E";
	}
</script>