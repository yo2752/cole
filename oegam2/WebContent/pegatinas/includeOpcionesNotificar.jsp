<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table class="subtitulo" cellSpacing="0">
	<tr>
		<td>Notificar Impresión de distintivo</td>
	</tr>
</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		<tr>
			<td width="2%"></td><td></td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="OK" id="impresoOK" value="impresoOK" onchange="return activarRadiosImprimirDistintivo('3')" checked/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="NotificarOK">Notificar como impresión correcta</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="rotura" id="impresoRoturaKO" value="impresoRoturaKO" onchange="return activarRadiosImprimirDistintivo('1')"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="NotificarRoturaKO">Notificar como impresión erronea. Motivo de error: ROTURA </label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="error" id="impresoErrorKO" value="impresoErrorKO" onchange="return activarRadiosImprimirDistintivo('2')"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="NotificarRoturaKO">Notificar como impresión erronea. Motivo de error: IMPRESION ERRONEA</label>
			</td>
		</tr>
	</table>
	<input type="hidden" id='tipoNotificacion' value='OK' name="tipoNotificacion">
	<input type="hidden" id='listaPrueba' value='' name="listaPrueba">