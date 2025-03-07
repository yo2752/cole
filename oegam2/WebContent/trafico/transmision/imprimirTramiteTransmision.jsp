<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
	<%@ taglib prefix="s" uri="/struts-tags" %>
	<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<s:hidden id="electronica" name="imprimirTramiteTraficoDto.electronica"/>
	<s:hidden id="numExp" name="numExpediente"/>

	<table class="subtitulo" cellSpacing="0" cellspacing="0">
		<tr>
			<td>Impresos de tr�mites de transmisi�n</td>
		</tr>
	</table>

	<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
		<tr>
			<td width="2%"></td><td></td>
		</tr>
		<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().noBorradorPdf()}">
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="BorradorPDF417" value="BorradorPDF417" onchange="return cambiarRadioImprimir();" checked/>
				</td>
				<td align="left" style="vertical-align:bottom">
					<label for="BorradorPDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417) (BORRADOR)</label>
				</td>
			</tr>
		</s:if>
		<s:if test="%{!@trafico.utiles.UtilesVistaTrafico@getInstance().esPdfIvtm(#attr.tablaConsultaTramite.numExpediente)}">
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="PDF417" value="PDF417" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align:bottom">
					<label for="PDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417)</label>
				</td>
			</tr>
		</s:if>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="Modelo430" value="Modelo430" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="Modelo430">Modelo 430</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="MandatoGenerico" value="MandatoGenerico" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="MandatoGenerico">Mandato Gen�rico</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="MandatoEspecifico" value="MandatoEspecifico" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="MandatoEspecifico">Mandato Espec�fico</label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td id="bloqueTipoRepresenta" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<s:if test="%{imprimirTramiteTraficoDto.electronica}">
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="PDFPresentacionTelematica" value="PDFPresentacionTelematica" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="PDFPresentacionTelematica">PDF Presentaci�n Telem�tica</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="DocumentosTelematicos" value="DocumentosTelematicos" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DocumentosTelematicos">Autorizaci�n provisional o Informe de cambio de titularidad</label>
			</td>
		</tr>

		</s:if>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="ListadoMatriculas" value="ListadoBastidores" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="ListadoMatriculas">Relaci�n de Matr�culas</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="SolicitudNre06" value="SolicitudNre06" onchange="return cambiarRadioImprimir();"/>
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="SolicitudNre06">Impreso NRE06</label>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="DeclaracionJuradaExportacionTransito" value="DeclaracionJuradaExportacionTransito" onchange="return cambiarRadioImprimir();" />
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DeclaracionJuradaExportacionTransito">Declaraci�n jurada por exportaci�n o tr�nsito comunitario</label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td id="bloqueTipoRepresentaDeclaracionET" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion1" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
							<option value="Presentador">Presentador</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso"	id="DeclaracionJuradaExtravioFicha"	value="DeclaracionJuradaExtravioFicha" onchange="return cambiarRadioImprimir();" />
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DeclaracionJuradaExtravioFicha">Declaraci�n jurada de extrav�o de la ficha t�cnica</label>
			</td>
		</tr>

		<tr>
			<td></td>
			<td id="bloqueTipoRepresentaDeclaracionExFt" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion2" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
							<option value="Presentador">Presentador</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="DeclaracionJuradaExtravioPermisoFicha" value="DeclaracionJuradaExtravioPermisoFicha" onchange="return cambiarRadioImprimir();" />
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DeclaracionJuradaExtravioPermisoFicha">Declaraci�n jurada de extrav�o del permiso de ciculaci�n y de la ficha t�cnica</label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td id="bloqueTipoRepresentaDeclaracionExPermFt" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion3" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
							<option value="Presentador">Presentador</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso"	id="DeclaracionJuradaExtravioPermisoLicencia" value="DeclaracionJuradaExtravioPermisoLicencia" onchange="return cambiarRadioImprimir();" />
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DeclaracionJuradaExtravioPermisoLicencia">Declaraci�n jurada de extrav�o del permiso o licencia de circulaci�n</label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td id="bloqueTipoRepresentaDeclaracionExPermL" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion4" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
							<option value="Presentador">Presentador</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="DeclaracionJuradaEntregaAnteriorPermiso" value="DeclaracionJuradaEntregaAnteriorPermiso" onchange="return cambiarRadioImprimir();" />
			</td>
			<td align="left" style="vertical-align:bottom">
				<label for="DeclaracionJuradaEntregaAnteriorPermiso">Declaraci�n jurada de entrega anterior del permiso de circulaci�n</label>
			</td>
		</tr>
		<tr>
			<td></td>
			<td id="bloqueTipoRepresentaDeclaracionEAPerm" style="display:none;text-align: left;">
				<table>
					<tr>
						<td>Seleccione a qui�n se va a representar:<hr/>
						<select name="tipoRepresentacion5" title="Tipo de Representaci�n">
							<option value="Transmitente">Transmitente</option>
							<option value="Adquiriente">Adquiriente</option>
							<option value="Compraventa">Compraventa</option>
							<option value="Presentador">Presentador</option>
						</select>
						</td>
					</tr>
				</table>
			</td>
		</tr>
	</table>