<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags"%>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
<s:hidden name="esSeleccionadoNotario" id="esSeleccionadoNotario"/>
<s:hidden name="codigo" id="codigo"/>
<script src="js/tabs.js" type="text/javascript"></script>
<div id="contenido" class="contentTabs" style="display: block; text-align: center;">
	<div class="nav">
		<table width="100%">
			<tr>
				<td class="tabular"><span class="titulo">Alta / Modificaci&oacute;n notario</span></td>
			</tr>
		</table>
	</div>
</div>
<%@include file="../../includes/erroresYMensajes.jspf"%>
<s:form method="post" id="formDataNotario" name="formData">

	<div id="busqueda">
			<table class="tablaformbasica">
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelNombre">Nombre:</label>
					</td>
					<td  align="left">
						<s:textfield name="notarioDto.nombre" id="idNotarioNombre" size="25" maxlength="100" onblur="this.className='input';"
									onfocus="this.className='inputfocus';"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelApellido1">Apellidos:</label>
					</td>
					<td  align="left">
						<s:textfield name="notarioDto.apellidos" id="idNotarioApellidos" size="25" maxlength="100" onblur="this.className='input';"
									onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
				<tr>
					<td align="left" nowrap="nowrap">
						<label for="labelCodNotario">Código Notario:</label>
					</td>
					<td  align="left">
						<s:textfield name="notarioDto.codigoNotario" id="idCodNotario" size="25" maxlength="7" onblur="this.className='input';"
									onfocus="this.className='inputfocus';" readonly="%{notarioDto.codigoNotario!=null}"/>
					</td>
					<td align="left" nowrap="nowrap">
						<label for="labelCodNotaria">Código Notaría:</label>
					</td>
					<td  align="left">
						<s:textfield name="notarioDto.codigoNotaria" id="idCodNotaria" size="25" maxlength="9" onblur="this.className='input';"
									onfocus="this.className='inputfocus';"/>
					</td>
				</tr>
			</table>
			<table class="acciones">
				<tr>
					<td>
						<input type="button" class="boton" name="bGuardar" id="bGuardar" value="Guardar"  onkeypress="this.onClick" onclick="return guardarNotario();"/>
					</td>
					<td>
						<input type="button" class="boton" name="bLimpiar" id="bLimpiar" value="Limpiar"  onkeypress="this.onClick" onclick="return limpiarNotario();"/>
					</td>
					<td>
						<input type="button" class="boton" name="bVolver" id="bVolver" value="Volver"  onkeypress="this.onClick" onclick="return volver();"/>
					</td>
				</tr>
		</table>
	</div>
</s:form>
<script src="js/notario/notarioFunction.js" type="text/javascript"/>