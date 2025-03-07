<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<div class="nav">
	<table border="0" cellSpacing="0" cellPadding="0" style="background-color: transparent; width: 100%">
		<tr>
			<td class="tabular" style="">
				<span class="titulo">Cuenta</span>
			</td>
		</tr>
	</table>
</div>
	
<table border="0" class="tablaformbasicaTC" style="width: 100%;" >
	<tr>
		<td align="left" nowrap="nowrap" >
			<label for="ejercicioCuenta">Ejercicio cuenta:</label>
		</td>
		<td align="left" nowrap="nowrap" >
			<s:textfield name="tramiteRegistro.ejercicioCuenta" id="ejercicioCuenta" size="10" maxlength="60" readonly="true"/>
		</td>
		<td align="left" nowrap="nowrap" >
			<label for="claseCuenta">Clase cuenta:</label>
		</td>
		<td align="left" nowrap="nowrap" >
			<s:textfield name="tramiteRegistro.claseCuenta" id="claseCuenta" size="40" maxlength="60" onblur="this.className='input';" readonly="true"/>
		</td>
	</tr>
	<tr>
		<td colspan="4">&nbsp;</td>
	</tr>
</table>