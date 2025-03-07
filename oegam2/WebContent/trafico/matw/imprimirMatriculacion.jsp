<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@ taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://displaytag.sf.net" prefix="display"%>

	<table class="subtitulo" cellSpacing="0" cellspacing="0">
			<tr>
				<td>Impresos de trámites de matriculación</td>
			</tr>
		</table>

		<table cellSpacing="3" class="tablaformbasica" cellPadding="0">
			<tr><td width="2%"></td><td></td></tr>
			<s:if test="%{!@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().noBorradorPdf()}">
				<tr>
					<td align="left">
						<input type="radio" name="impreso" id="BorradorPDF417" value="BorradorPDF417" onchange="return cambiarRadioImprimir();" checked />
					</td>
					<td align="left" style="vertical-align: middle;">
						<label for="BorradorPDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417) (BORRADOR)</label>
					</td>
				</tr>
			</s:if>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="PDF417" value="PDF417" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="PDF417">PDF presentaci&oacute;n en DGT (nube de puntos PDF 417)</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="PDFPresentacionTelematica" value="PDFPresentacionTelematica" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="PDFPresentacionTelematica">PDF Presentación Telemática</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="PermisoTemporalCirculacion" value="PermisoTemporalCirculacion" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="PermisoTemporalCirculacion">Permiso Temporal de Circulación</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="FichaTecnica" value="FichaTecnica" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="FichaTecnica">Ficha Técnica</label>
				</td>
			</tr>
			<tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="GenerarDistintivo" value="GenerarDistintivo" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="GenerarDistintivo">Generar distintivo</label>
				</td>
			</tr>
			<tr>
				<td></td>
					<td id="botonGeneracionDistintivo" style="display:none;text-align: left;">
						<table>
							<tr>
								<td>
									<div>
										<input type="button" class="boton" name="bGenerarDistintivo" id="bGenerarDistintivo"
											value="Generar Distintivo" onClick="javascript:generarDistintivoDesdeImprDocum();"onKeyPress="this.onClick"/>
									</div>
								</td>
							</tr>
						</table>
					</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="MandatoGenerico" value="MandatoGenerico" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="Mandato">Mandato Genérico</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="MandatoEspecifico" value="MandatoEspecifico" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="Mandato">Mandato Específico</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="CartaPagoIVTM" value="CartaPagoIVTM" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="CartaPagoIVTM">Carta de pago IVTM</label>
				</td>
			</tr>

			<!-- tr>
				<td align="left">
					<input type="radio" name="impreso" id="ModeloAEAT" value="ModeloAEAT" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="ModeloAEAT">Modelo AEAT</label>
				</td>
			</tr-->

			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="PegatinasEtiquetaMatricula" value="PegatinasEtiquetaMatricula" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="PegatinasEtiquetaMatricula">Pegatinas para la Etiqueta de la Matrícula</label>
				</td>
			</tr>
			<tr>
				<td></td>
				<td id="bloqueNumEtiquetas" style="display:none;text-align: left;">
					<table>
						<tr>
							<td>Numero Etiquetas<hr/>
							Por Trámite: <s:textfield name="etiquetaParametros.etiquetasTramite" id="etiquetasTramite" />
							Por Fila: <s:textfield name="etiquetaParametros.etiquetasFila" id="etiquetasFila" />
							Por Columna: <s:textfield name="etiquetaParametros.etiquetasColumna" id="etiquetasColumna" /></td>
							<td>Margen(mm)<hr/>
							Sup:<s:textfield name="etiquetaParametros.margenSup" id="margenSup" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
							Inf:<s:textfield name="etiquetaParametros.margenInf" id="margenInf" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
							Dcho:<s:textfield name="etiquetaParametros.margenDcho" id="margenDcho" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
							Izqd:<s:textfield name="etiquetaParametros.margenIzqd" id="margenIzqd" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/></td>
							<td>Espaciado(mm)<hr/>
							Horizontal:<s:textfield name="etiquetaParametros.horizontal" id="horizontal" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/>
							Vertical:<s:textfield name="etiquetaParametros.vertical" id="vertical" onkeypress="return cambiaComaPorPunto(this);" onchange="return cambiaComaPorPunto(this);"/></td>
							<td>Posición Primera Etiqueta:<hr/>
							Fila:<s:textfield name="etiquetaParametros.filaPrimer" id="filaPrimer" />
							Columna:<s:textfield name="etiquetaParametros.columnaPrimer" id="columnaPrimer" /></td>
						</tr>
					</table>

				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="ListadoBastidores" value="ListadoBastidores" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="ListadoBastidores">Relación de Matrículas</label>
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
					<input type="radio" name="impreso" id="PDFJustificantePresentacion576" value="PDFJustificantePresentacion576" onchange="return cambiarRadioImprimir();"/>
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="PDFJustificantePresentacion576">PDF justificante presentaci&oacute;n 576</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="idXMLConsultaEitv" value="XMLConsultaEitv" onchange="return cambiarRadioImprimir();" />
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="labelXMLConsultaEitv">XML de Consulta Eitv / Datos técnicos</label>
				</td>
			</tr>
			<tr>
				<td align="left">
					<input type="radio" name="impreso" id="ConsultaEitv" value="ConsultaEITV" onchange="return cambiarRadioImprimir();" />
				</td>
				<td align="left" style="vertical-align: middle;">
					<label for="labelCosnultaEitv">Documento de datos técnicos necesarios para la liquidación de impuestos</label>
				</td>
			</tr>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<tr>
					<td align="left">
						<input type="radio" name="impreso" id="txtNre" value="TXTNRE" onchange="return cambiarRadioImprimir();" />
					</td>
					<td align="left" style="vertical-align: middle;">
						<label for="labelTxtNre">TXT NRE</label>
					</td>
				</tr>
			</s:if>
			<s:if test="%{@org.gestoresmadrid.oegam2.utiles.UtilesVistaAcceso@getInstance().tienePermisoAdmin()}">
				<tr>
					<td align="left">
						<input type="radio" name="impreso" id="certAutorizaColegial" value="CertificadoRevisionColegial" onchange="return cambiarRadioImprimir();" />
					</td>
					<td align="left" style="vertical-align: middle;">
						<label for="labelCertAutorizaColegial">Certificado Revisión Colegial</label>
					</td>
				</tr>
			</s:if>
		</table>