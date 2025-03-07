<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<link rel="stylesheet" type="text/css" href="css/gdoc.css" />

<div class="" id="AltaEstacionITV" style="width: 760px; border: none; background-color: rgb(235, 237, 235);">
	<div class="contenido">
		<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td class="espacio"><img src="img/activo.gif" alt=" - " /></td>
				<td>Alta estacion ITV</td>
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

		<s:form method="post" id="formData" name="formData" action="altaEstacionITV.action">
			<table width="100%" border="0" cellspacing="3" cellpadding="0" class="tablaformbasica" style="text-align: center">
				<tr>
					<td style="text-align:right;vertical-align:middle; padding-right:2em; width: 44%">
						<label for="docId">ID Estacion:</label>
					</td>
					<td>
						<s:fielderror fieldName="idEstacion" />	
						<s:textfield size="20" maxlength="5" name="estacionITV.id" id="idEstacion" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right;vertical-align:middle; padding-right:2em; width: 44%">
						<label for="docId">Provincia:</label>
					</td>
					<td>
						<s:textfield size="20" maxlength="30" name="estacionITV.provincia" id="provincia" />
					</td>
				</tr>
				<tr>
					<td style="text-align:right;vertical-align:middle; padding-right:2em; width: 44%">
						<label for="docId">Municipio (Cod.Postal):</label>
					</td>
					<td>
						<s:textfield type="text" size="20" maxlength="45" name="estacionITV.municipio" id="municipio" />
					</td>
				</tr>
			</table>

			<table class="acciones">
				<tr>
					<td align="center">
						<input id="bLimpiar" type="button" value="Limpiar Campos" class="boton" onClick="return limpiarCampos();" />
					</td>
					<td>
						<input id="bEnviar" type="submit" value="Enviar" class="boton" />
					</td>
				</tr>
			</table>
		</s:form>
	</div>
</div>

<script type="text/javascript">
	function limpiarCampos() {
		$('#idEstacion').val('');
		$('#provincia').val('');
		$('#municipio').val('');
	}
</script>