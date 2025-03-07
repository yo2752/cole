<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="css/gdoc.css" />

<div class="" id="AltaEstacionITV" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Cambio Jefatura Distintivos Medioambientales</td>
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

		<s:form method="post" id="formData" name="formData" action="cambiarJefaturaJPT.action">
			<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica">
				<tr>
					<td style="text-align:left;vertical-align:middle; padding-right:2em;">
						<label for="docId">Usuario:</label>
					</td>
					<td>
						<s:property value="usuario.apellidosNombre"/>
					</td>
					<td>
						Jefatura:
					</td>
					<td>
						<s:select list="@escrituras.utiles.UtilesVista@getInstance().getJefaturaProvincialPegatinas()"
						headerKey="-1" headerValue="Sin Asignar" name="jefaturaSeleccionada" listKey="jefatura"
						value="usuario.jefaturaJPT"/>
					</td>
				</tr>
			</table>

			<table class="acciones">
				<tr>
					<td>
						<input id="bEnviar" type="submit" value="Enviar" class="boton"/>
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</div>