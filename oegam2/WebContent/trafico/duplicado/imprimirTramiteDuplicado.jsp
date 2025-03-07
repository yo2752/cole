<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>
	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Impresos de trámites de duplicado</td>
		</tr>
	</table>
	<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		<tr>
			<td width="2%"></td><td></td>
		</tr>
		<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().noBorradorPdf()}">
			<tr>
				<td align="left">
					<input type="radio" disabled="disabled" name="impreso" id="BorradorPDF417" value="BorradorPDF417" />
				</td>
				<td align="left">
					<label for="BorradorPDF417">PDF presentaci&oacute;n en D.G.T. (nube de puntos PDF 417) (BORRADOR)</label>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" disabled="disabled" id="PDF417" value="PDF417" />
			</td>
			<td align="left">
				<label for="PDF417">PDF presentaci&oacute;n en D.G.T. (nube de puntos PDF 417)</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="MandatoGenerico" value="MandatoGenerico" checked />
			</td>
			<td align="left">
					<label for="MandatoGenerico">Mandato Genérico</label>
				</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="MandatoEspecifico" value="MandatoEspecifico" />
			</td>
			<td align="left">
				<label for="MandatoEspecifico">Mandato Específico</label>
			</td>
		</tr>

		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="ListadoMatriculas" value="ListadoBastidores" />
			</td>
			<td align="left">
				<label for="ListadoMatriculas">Listado de Matrículas</label>
			</td>
		</tr>
	<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaDuplicado@getInstance().esUsuarioSive()">
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="PermisoTemporalDuplicado" value="PermisoTemporalDuplicado" />
			</td>
			<td align="left">
				<label for="PermisoTemporalDuplicado">PTC</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="JustifRegEntrada" value="JustifRegEntrada" />
			</td>
			<td align="left">
				<label for="JustifRegEntrada">Justificante Registro Entrada</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="JustificanteDuplicado" value="JustificanteDuplicado" />
			</td>
			<td align="left">
				<label for="JustificanteDuplicado">Justificante Duplicado</label>
			</td>
		</tr>
		</s:if>
	</table>