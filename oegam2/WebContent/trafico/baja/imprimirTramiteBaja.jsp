<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

<table class="subtitulo" cellSpacing="0" cellspacing="0">
	<tr>
		<td>Impresos de trámites de bajas</td>
	</tr>
</table>

<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
	<tr><td width="2%"></td><td></td></tr>
	<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().noBorradorPdf()}">
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="BorradorPDF417" value="BorradorPDF417" onchange="return cambiarRadioImprimirBaja();" checked/>
			</td>
			<td align="left"  style="vertical-align:bottom">
				<label for="BorradorPDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417) (BORRADOR)</label>
			</td>
		</tr>
	</s:if>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="PDF417" value="PDF417" onchange="return cambiarRadioImprimirBaja();"/>
		</td>
		<td align="left"  style="vertical-align:bottom">
			<label for="PDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417)</label>
		</td>
	</tr>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
		<tr>
			<td align="left">
				<input type="radio" name="impreso" id="PDFPresentacionTelematica" value="PDFPresentacionTelematica" onchange="return cambiarRadioImprimirBaja();"/>
			</td>
			<td align="left"  style="vertical-align:bottom">
				<label for="PDFPresentacionTelematica">PDF presentaci&oacute;n Telem&aacute;tica</label>
			</td>
		</tr>
	</s:if>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="MandatoGenerico" value="MandatoGenerico" onchange="return cambiarRadioImprimirBaja();"/>
		</td>
		<td align="left"  style="vertical-align:bottom">
			<label for="MandatoGenerico">Mandato Genérico</label>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="MandatoEspecifico" value="MandatoEspecifico" onchange="return cambiarRadioImprimirBaja();"/>
		</td>
		<td align="left"  style="vertical-align:bottom">
			<label for="MandatoEspecifico">Mandato Específico</label>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="ListadoMatriculas" value="ListadoBastidores" onchange="return cambiarRadioImprimirBaja();"/>
		</td>
		<td align="left"  style="vertical-align:bottom">
			<label for="ListadoMatriculas">Listado de Matrículas</label>
		</td>
	</tr>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
		<s:if test="!@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esTramiteBajaSive(#attr.tablaConsultaTramite.numExpediente)">
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="InformeBajaTelematica" value="InformeBajaTelematica" onchange="return cambiarRadioImprimirBaja();"/>
				</td>
				<td align="left"  style="vertical-align:bottom">
					<label for="informeBaja">Informe de baja telemática</label>
				</td>
			</tr>
		</s:if>
	</s:if>
	<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esNuevasBajas()}">
		<s:if test="@org.gestoresmadrid.oegam2.utiles.UtilesVistaBaja@getInstance().esTramiteBajaSive(#attr.tablaConsultaTramite.numExpediente)">
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="InformeBajaSive" value="InformeBajaSive" onchange="return cambiarRadioImprimirBaja();"/>
				</td>
				<td align="left"  style="vertical-align:bottom">
					<label for="InformeBajaSive">Informe de baja telemática</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="JustifRegEntradaBajaSive" value="JustifRegEntradaBajaSive" onchange="return cambiarRadioImprimirBaja();" />
				</td>
				<td align="left"  style="vertical-align:bottom">
					<label for="JustifRegEntradaBajaSive">Justificante Registro Entrada Baja Temporal en DGT</label>
				</td>
			</tr>
			</s:if>
		</s:if>
	<tr>
		<td align="left">
			<input type="radio" name="impreso"	id="DeclaracionJuradaExportacionTransito" value="DeclaracionJuradaExportacionTransito" onchange="return cambiarRadioImprimir();" />
		</td>
		<td align="left" style="vertical-align:bottom">
			<label for="DeclaracionJuradaExportacionTransito">Declaración jurada por exportación o tránsito comunitario</label>
		</td>
	</tr>
	<tr>
		<td></td>
		<td id="bloqueTipoRepresentaDeclaracionET" style="display:none;text-align: left;">
			<table>
				<tr>
					<td>Seleccione a quién se va a representar:<hr/>
						<select name="tipoRepresentacion1" title="Tipo de Representación">
							<option value="Titular">Titular</option>
							<option value="Adquiriente">Adquiriente</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="DeclaracionJuradaExtravioFicha" value="DeclaracionJuradaExtravioFicha" onchange="return cambiarRadioImprimir();" />
		</td>
		<td align="left" style="vertical-align:bottom">
			<label for="DeclaracionJuradaExtravioFicha">Declaración jurada de extravío de la ficha técnica</label>
		</td>
	</tr>
	<tr>
		<td></td>
		<td id="bloqueTipoRepresentaDeclaracionExFt" style="display:none;text-align: left;">
			<table>
				<tr>
					<td>Seleccione a quién se va a representar:<hr/>
						<select name="tipoRepresentacion2" title="Tipo de Representación">
							<option value="Titular">Titular</option>
							<option value="Adquiriente">Adquiriente</option>
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
			<label for="DeclaracionJuradaExtravioPermisoFicha">Declaración jurada de extravío del permiso de ciculación y de la ficha técnica</label>
		</td>
	</tr>
	<tr>
		<td></td>
		<td id="bloqueTipoRepresentaDeclaracionExPermFt" style="display:none;text-align: left;">
			<table>
				<tr>
					<td>Seleccione a quién se va a representar:<hr/>
						<select name="tipoRepresentacion3" title="Tipo de Representación">
							<option value="Titular">Titular</option>
							<option value="Adquiriente">Adquiriente</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="DeclaracionJuradaExtravioPermisoLicencia" value="DeclaracionJuradaExtravioPermisoLicencia" onchange="return cambiarRadioImprimir();" />
		</td>
		<td align="left" style="vertical-align:bottom">
			<label for="DeclaracionJuradaExtravioPermisoLicencia">Declaración jurada de extravío del permiso o licencia de circulación</label>
		</td>
	</tr>
	<tr>
		<td></td>
		<td id="bloqueTipoRepresentaDeclaracionExPermL" style="display:none;text-align: left;">
			<table>
				<tr>
					<td>Seleccione a quién se va a representar:<hr/>
						<select name="tipoRepresentacion4" title="Tipo de Representación">
							<option value="Titular">Titular</option>
							<option value="Adquiriente">Adquiriente</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
	<tr>
		<td align="left">
			<input type="radio" name="impreso" id="DeclaracionJuradaEntregaAnteriorPermiso" value="DeclaracionJuradaEntregaAnteriorPermiso"	onchange="return cambiarRadioImprimir();" />
		</td>
		<td align="left" style="vertical-align:bottom">
			<label for="DeclaracionJuradaEntregaAnteriorPermiso">Declaración jurada de entrega anterior del permiso de circulación</label>
		</td>
	</tr>
	<tr>
		<td></td>
		<td id="bloqueTipoRepresentaDeclaracionEAPerm" style="display:none;text-align: left;">
			<table>
				<tr>
					<td>Seleccione a quién se va a representar:<hr/>
						<select name="tipoRepresentacion5" title="Tipo de Representación">
							<option value="Transmitente">Titular</option>
							<option value="Adquiriente">Adquiriente</option>
						</select>
					</td>
				</tr>
			</table>
		</td>
	</tr>
</table>