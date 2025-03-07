package org.gestoresmadrid.oegam2comun.estadisticas.service.impl;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.Provincias;
import org.gestoresmadrid.core.model.enumerados.TipoTransferencia;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.estadisticas.service.ServicioEstadisticasCorreoCtit;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoTransmision;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import general.beans.HtmlBean;
import general.utiles.UtilesCadenaCaracteres;
import trafico.utiles.constantes.ConstantesEstadisticas;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEstadisticasCorreoCtitImpl implements ServicioEstadisticasCorreoCtit {

	private static final long serialVersionUID = 8729639575168952245L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioEstadisticasCorreoCtitImpl.class);

	@Autowired
	ServicioTramiteTraficoTransmision servicioTramiteTraficoTransmision;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	private float totalTelematicas = 0;
	private float totalTelMadrid = 0;
	private float totalTelSegovia = 0;
	private float totalTelAvila = 0;
	private float totalTelCuenca = 0;
	private float totalTelCiudadReal = 0;
	private float totalTelGuadalajara = 0;
	private float totalPdf = 0;
	private float totalPdfMadrid = 0;
	private float totalPdfSegovia = 0;
	private float totalPdfAvila = 0;
	private float totalPdfCuenca = 0;
	private float totalPdfCiudadReal = 0;
	private float totalPdfGuadalajara = 0;

	@Override
	public ResultBean enviarMailEstadisticasCTIT() throws OegamExcepcion, Exception {
		StringBuffer sb = datosEstadisticasCTIT();
		String recipient = "";
		String subject = "";

		String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.envio.estadisticas");
		if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.destinatarios1");
			subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.subject");
		} else {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
			subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - CTIT";
		}
		return servicioCorreo.enviarCorreo(sb.toString(), false, null, subject, recipient, null, null, null, null);
	}

	@Override
	public ResultBean enviarMailEstadisticasCTITDelegaciones() throws OegamExcepcion, Exception {
		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
		String recipient = "";
		String subject = "";

		StringBuffer sb = datosEstadisticasCTITDelegaciones(fechaInicio, fechaFin);

		String nombrePdfEstadisticas = ConstantesEstadisticas.ESTADISTICASCTIT + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();

		File file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, fechaFin, nombrePdfEstadisticas,
				ConstantesGestorFicheros.EXTENSION_PDF).getFile();

		FicheroBean fichero = new FicheroBean();
		fichero.setFichero(file);
		fichero.setNombreDocumento("EstadisticasCTIT.pdf");

		String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.envio.estadisticas");
		if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTITDelegaciones.destinatarios1");
			subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.subject") + " - CTIT Delegaciones";
		} else {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
			subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - CTIT Delegaciones";
		}
		return servicioCorreo.enviarCorreo(sb.toString(), false, null, subject, recipient, null, null, null, fichero);
	}

	private StringBuffer datosEstadisticasCTITDelegaciones(Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {
		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";
		StringBuffer sbTemporal = new StringBuffer();
		HtmlBean htmlBean = new HtmlBean();

		List<HtmlBean> arrayHtmlBean = new ArrayList<HtmlBean>();

		resultadoHtml.append("<br></br>Estadísticas CTIT en Oegam. Mensaje generado el ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFecha, new Date()));
		resultadoHtml.append(".<br></br>Consulta realizada para el periodo del ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaInicio.getFecha()));
		resultadoHtml.append(" al ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()));
		resultadoHtml.append(".<br></br>");

		resultadoHtml.append("<b><u>");
		resultadoHtml.append(TELEMATICAS);
		resultadoHtml.append(":<br></br>");
		resultadoHtml.append("</u></b>");

		resultadoHtml.append("<b>Total Transmisiones por tipo de transferencia:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, TOTALES, fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Madrid:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, Provincias.Madrid.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Guadalajara:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, Provincias.Guadalajara.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Segovia:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, Provincias.Segovia.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Ciudad Real:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, Provincias.Ciudad_Real.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Cuenca:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, Provincias.Cuenca.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Avila:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(true, PROVINCIA_AVILA, fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b><u>");
		resultadoHtml.append(NO_TELEMATICAS);
		resultadoHtml.append(":<br></br>");
		resultadoHtml.append("</u></b>");

		resultadoHtml.append("<b>Total Transmisiones por tipo de transferencia:</b>");
		resultadoHtml.append(sbTemporal);
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, TOTALES, fechaInicio, fechaFin);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Madrid:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, Provincias.Madrid.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Guadalajara:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, Provincias.Guadalajara.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Segovia:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, Provincias.Segovia.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Ciudad Real:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, Provincias.Ciudad_Real.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Cuenca</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, Provincias.Cuenca.toString(), fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml.append("<b>Delegacion de Avila:</b>");
		sbTemporal = cadenaHTMLResultadoEstadisticasCTITDelegaciones(false, PROVINCIA_AVILA, fechaInicio, fechaFin);
		resultadoHtml.append(sbTemporal);
		resultadoHtml.append("<br></br>");
		htmlBean = new HtmlBean();
		htmlBean.setHtmlCode(sbTemporal.toString());
		arrayHtmlBean.add(htmlBean);
		sbTemporal.delete(0, sbTemporal.length());

		resultadoHtml = anaidirTablasPorcentajes(resultadoHtml);

		try {
			StringBuffer html = new StringBuffer("<html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> </head><body style=\"background-color:#E6E6E6\">");
			html.append(UtilesCadenaCaracteres.stripAccents(resultadoHtml.toString()));
			html.append("</body></html>");

			String nombreFichero = FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, html.toString()
					.getBytes());
		} catch (Throwable e) {
			log.error("No se ha podido guardar el fichero html", e);
		}

		pdfAdjunto(arrayHtmlBean, fechaFin);
		return resultadoHtml;
	}

	private StringBuffer datosEstadisticasCTIT() throws OegamExcepcion, ParseException {
		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";

		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);

		resultadoHtml.append("<span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
		resultadoHtml.append("<br></br>Estadisticas CTIT en Oegam. Mensaje generado el ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFecha, new Date()));
		resultadoHtml.append(".<br></br>Consulta realizada para el periodo del ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaInicio.getFecha()));
		resultadoHtml.append(" al ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()));
		resultadoHtml.append(".<br></br>");

		resultadoHtml.append("</span><span style=\"font-size:12pt;font-family:Tunga;\">");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasCTIT(true, fechaInicio, fechaFin));
		resultadoHtml.append("<br></br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasCTIT(false, fechaInicio, fechaFin));
		resultadoHtml.append("</span>");
		resultadoHtml.append("<br></br><br></br>");

		String nombreFichero = FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT_TOTALES, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, resultadoHtml
				.toString().getBytes());

		return resultadoHtml;
	}

	private StringBuffer totalTelematicosONoTelematicosCTIT(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico) throws ParseException {
		if (esTelematico) {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.telematicas"));
		} else {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.no.telematicas"));
		}
		cadenaResultado.append("</b></u>");

		// Se obtiene el resultado de la consulta
		List<Object[]> listaTotalesCTIT = servicioTramiteTraficoTransmision.obtenerTotalesTramitesCTIT(esTelematico, fechaInicio, fechaFin, "TOTALES");

		StringBuffer listado = new StringBuffer();
		int totalRegistros = 0;

		listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (listaTotalesCTIT != null && !listaTotalesCTIT.isEmpty()) {
			for (Object[] obj : listaTotalesCTIT) {
				listado.append("<tr> <td>");

				totalRegistros = totalRegistros + (Integer) obj[0];

				listado.append(obj[0].toString());
				listado.append("</td><td>");
				Integer tipoTransferencia = new Integer(obj[1] != null ? obj[1].toString() : BigDecimal.ZERO.toString());
				switch (tipoTransferencia) {
					case 1:
						listado.append(TipoTransferencia.tipo1.getNombreEnum());
						break;
					case 2:
						listado.append(TipoTransferencia.tipo2.getNombreEnum());
						break;
					case 3:
						listado.append(TipoTransferencia.tipo3.getNombreEnum());
						break;
					case 4:
						listado.append(TipoTransferencia.tipo4.getNombreEnum());
						break;
					case 5:
						listado.append(TipoTransferencia.tipo5.getNombreEnum());
						break;
					default:
						listado.append("No marcada");
						break;
				}
				listado.append("</td> </tr>");
			}
		}

		listado.append("</span>");

		cadenaResultado.append("<br></br>Total: " + totalRegistros);
		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de transferencia</th> </tr> </span> ");
		cadenaResultado.append(listado);
		cadenaResultado.append("</table>");

		if (totalRegistros > 0) {
			cadenaResultado = totalPorColegiadoCTIT(cadenaResultado, fechaInicio, fechaFin, esTelematico,null);

			List<BigDecimal> listaExpedientes = servicioTramiteTraficoTransmision.obtenerListadoNumerosExpedienteCTIT(esTelematico, fechaInicio, fechaFin);

			cadenaResultado = anotacionGest(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = carpetaFusion(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = carpetaTipoI(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = carpetaTipoB(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = vehiculosAgricolas(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = adjudicacionJudicial(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = sinCETNiFactura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = tramitableEnJefatura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = errorEnJefatura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = finalizadosErrorAFinalizadosPDF(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = validadosTelematicamenteAFinalizadosPDF(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
			cadenaResultado = indeterminados(cadenaResultado, listaExpedientes);
		}
		return cadenaResultado;
	}

	private StringBuffer totalTelematicosONoTelematicosCTITDelegaciones(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, String idProvincia)
			throws ParseException {
		List<Object[]> listaTotalesCTIT = servicioTramiteTraficoTransmision.obtenerTotalesTramitesCTIT(esTelematico, fechaInicio, fechaFin, idProvincia);

		StringBuffer listado = new StringBuffer();
		int totalRegistros = 0;

		listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (listaTotalesCTIT != null && !listaTotalesCTIT.isEmpty()) {
			for (Object[] obj : listaTotalesCTIT) {
				listado.append("<tr> <td>");

				totalRegistros = totalRegistros + (Integer) obj[0];

				listado.append(obj[0].toString());
				listado.append("</td><td>");
				Integer tipoTransferencia = new Integer(obj[1] != null ? obj[1].toString() : BigDecimal.ZERO.toString());
				switch (tipoTransferencia) {
					case 1:
						listado.append(TipoTransferencia.tipo1.getNombreEnum());
						break;
					case 2:
						listado.append(TipoTransferencia.tipo2.getNombreEnum());
						break;
					case 3:
						listado.append(TipoTransferencia.tipo3.getNombreEnum());
						break;
					case 4:
						listado.append(TipoTransferencia.tipo4.getNombreEnum());
						break;
					case 5:
						listado.append(TipoTransferencia.tipo5.getNombreEnum());
						break;
					default:
						listado.append("No marcada");
						break;
				}
				listado.append("</td> </tr>");
			}
		}

		listado.append("</span>");

		cadenaResultado.append("<br></br>Total: " + totalRegistros);
		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de transferencia</th> </tr> </span> ");
		cadenaResultado.append(listado);
		cadenaResultado.append("</table>");

		guardarTotales(totalRegistros, esTelematico, idProvincia);

		if (totalRegistros > 0) {
			if (TOTALES.equals(idProvincia) && esTelematico) {
				cadenaResultado = porcentajes(cadenaResultado);
			}

			if (!TOTALES.equals(idProvincia)) {
				cadenaResultado = totalPorColegiadoCTIT(cadenaResultado, fechaInicio, fechaFin, esTelematico,idProvincia);
				if (!esTelematico) {
					List<BigDecimal> listaExpedientes = servicioTramiteTraficoTransmision.obtenerListadoNumerosExpedienteCTIT(esTelematico, fechaInicio, fechaFin);

					cadenaResultado = anotacionGest(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = carpetaFusion(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = carpetaTipoI(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = carpetaTipoB(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = vehiculosAgricolas(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = adjudicacionJudicial(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = sinCETNiFactura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = tramitableEnJefatura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = errorEnJefatura(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = finalizadosErrorAFinalizadosPDF(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = validadosTelematicamenteAFinalizadosPDF(cadenaResultado, fechaInicio, fechaFin, esTelematico, listaExpedientes);
					cadenaResultado = indeterminados(cadenaResultado, listaExpedientes);
				}
			}
		}

		return cadenaResultado;
	}

	private StringBuffer porcentajes(StringBuffer cadenaResultado) {
		cadenaResultado.append(
				"<b>Porcentajes de Telemáticas: </b><table border='1' width='100%'> <tr> <th> Tipo </th> <th> Madrid </th> <th> Segovia </th> <th> Avila </th> <th> Cuenca </th> <th> Ciudad Real </th> <th> Guadalajara </th> <th> Total </th> </tr> ");
		cadenaResultado.append(CADENA_A_SUSTITUIR);
		cadenaResultado.append("</table>");
		return cadenaResultado;
	}

	private StringBuffer anaidirTablasPorcentajes(StringBuffer resultadoHtml) {
		int inicioCadenaSustituir = resultadoHtml.indexOf(CADENA_A_SUSTITUIR);
		if (inicioCadenaSustituir != -1) {
			int finalCadenaSustituir = inicioCadenaSustituir + CADENA_A_SUSTITUIR.length();
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			DecimalFormat dfN = new DecimalFormat();
			dfN.setMaximumFractionDigits(0);
			StringBuffer tablaFinal = new StringBuffer("<tr> <td> Telemático </td><td>");
			tablaFinal.append(dfN.format(totalTelMadrid));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelSegovia));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelAvila));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelCuenca));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelCiudadReal));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelGuadalajara));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalTelematicas));
			tablaFinal.append("</td> </tr>");
			tablaFinal.append("<tr> <td> PDF </td><td>");
			tablaFinal.append(dfN.format(totalPdfMadrid));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdfSegovia));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdfAvila));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdfCuenca));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdfCiudadReal));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdfGuadalajara));
			tablaFinal.append("</td> <td>");
			tablaFinal.append(dfN.format(totalPdf));
			tablaFinal.append("</td></tr>");
			tablaFinal.append("<tr><td> % Telemáticas </td> <td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelMadrid, totalPdfMadrid)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelSegovia, totalPdfSegovia)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelAvila, totalPdfAvila)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelCuenca, totalPdfCuenca)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelCiudadReal, totalPdfCiudadReal)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelGuadalajara, totalPdfGuadalajara)));
			tablaFinal.append("%</td><td>");
			tablaFinal.append(df.format(calcularPorcentajes(totalTelematicas, totalPdf)));
			tablaFinal.append("%</td> </tr>");
			return resultadoHtml.replace(inicioCadenaSustituir, finalCadenaSustituir, tablaFinal.toString());
		} else {
			return resultadoHtml;
		}
	}

	private void guardarTotales(float totalRegistros, boolean esTelematico, String provincia) {
		if (esTelematico) {
			if (Provincias.Madrid.toString().equals(provincia)) {
				totalTelMadrid = totalRegistros;
			} else if (Provincias.Segovia.toString().equals(provincia)) {
				totalTelSegovia = totalRegistros;
			} else if (PROVINCIA_AVILA.equals(provincia)) {
				totalTelAvila = totalRegistros;
			} else if (Provincias.Cuenca.toString().equals(provincia)) {
				totalTelCuenca = totalRegistros;
			} else if (Provincias.Ciudad_Real.toString().equals(provincia)) {
				totalTelCiudadReal = totalRegistros;
			} else if (Provincias.Guadalajara.toString().equals(provincia)) {
				totalTelGuadalajara = totalRegistros;
			} else {
				totalTelematicas = totalRegistros;
			}
		} else {
			if (Provincias.Madrid.toString().equals(provincia)) {
				totalPdfMadrid = totalRegistros;
			} else if (Provincias.Segovia.toString().equals(provincia)) {
				totalPdfSegovia = totalRegistros;
			} else if (PROVINCIA_AVILA.equals(provincia)) {
				totalPdfAvila = totalRegistros;
			} else if (Provincias.Cuenca.toString().equals(provincia)) {
				totalPdfCuenca = totalRegistros;
			} else if (Provincias.Ciudad_Real.toString().equals(provincia)) {
				totalPdfCiudadReal = totalRegistros;
			} else if (Provincias.Guadalajara.toString().equals(provincia)) {
				totalPdfGuadalajara = totalRegistros;
			} else {
				totalPdf = totalRegistros;
			}
		}
	}

	private float calcularPorcentajes(float totalTel, float totalPdf) {
		float totalPorcentajeTel = 0;
		if (totalTel == 0 && totalPdf == 0) {
			totalPorcentajeTel = 0;
		} else {
			totalPorcentajeTel = (totalTel * 100) / (totalTel + totalPdf);
		}
		return totalPorcentajeTel;
	}

	private StringBuffer indeterminados(StringBuffer cadenaResultado, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<p>Expedientes PDF por motivo indeterminado:</p>");

			if (listaExpedientes != null && !listaExpedientes.isEmpty()) {
				StringBuffer listado = new StringBuffer();
				listado.append("<p>Listado de Gestorias con Trámites PDF por motivo indeterminado</p>");
				listado.append("<table border='1'><tr> <td>Número de trámites</td> <td>Gestoría</td> </tr>");

				List<Object[]> listaTramitesGestorIndefinidos = servicioTramiteTraficoTransmision.obtenerListaTramitesGestorSobreExp(listaExpedientes);
				for (Object[] obj : listaTramitesGestorIndefinidos) {
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}

				listado.append("</table>");

				cadenaResultado.append(listado);
			}
			cadenaResultado.append("<p><em>Total expedientes indeterminados:</em><strong>").append(listaExpedientes.size()).append("</strong></p>");
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes en Finalizado PDF por causa indeterminada", e);
		}
		return cadenaResultado;
	}

	private StringBuffer validadosTelematicamenteAFinalizadosPDF(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes)
			throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("<p>Expedientes que pasan de Validado Telemáticamente a Finalizado PDF:</p>");

			List<EstadoTramiteTrafico> transicionEstados = new ArrayList<EstadoTramiteTrafico>();
			transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
			transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);

			List<BigDecimal> listaExpedientesCTITValidadosAFinalizados = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITEvolucion(esTelematico, fechaInicio, fechaFin, listaExpedientes,
					transicionEstados);

			int detalleTramitesCTITValidadosAFinalizados = 0;
			if (listaExpedientesCTITValidadosAFinalizados != null && !listaExpedientesCTITValidadosAFinalizados.isEmpty()) {
				detalleTramitesCTITValidadosAFinalizados = listaExpedientesCTITValidadosAFinalizados.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITValidadosAFinalizados);
			cadenaResultado.append("</td> <td>Expedientes</td> </tr></span>");
			cadenaResultado.append("</table>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosAFinalizados);

			if (listaExpedientesCTITValidadosAFinalizados != null && !listaExpedientesCTITValidadosAFinalizados.isEmpty()) {
				StringBuffer listado = new StringBuffer();
				listado.append(
						"<p>Listado de Gestorías con Trámites que pasan de Validado Telemáticamente a Finalizado PDF</p><table border='1'><tr> <td>Número de trámites</td> <td>Gestoría</td> </tr>");

				List<Object[]> listaTramitesGestorCTITValidadosAFinalizados = servicioTramiteTraficoTransmision.obtenerListaTramitesGestorSobreExp(listaExpedientesCTITValidadosAFinalizados);
				for (Object[] obj : listaTramitesGestorCTITValidadosAFinalizados) {
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}
				listado.append("</table>");
				cadenaResultado.append(listado);
			}
			cadenaResultado.append("<p><em>Total expedientes que pasan de Validado Telemáticamente a Finalizado PDF:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITValidadosAFinalizados);
			cadenaResultado.append("</strong></p>");
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes que pasan directamente de Validado PDF a Finalizado PDF", e);
		}
		return cadenaResultado;
	}

	private StringBuffer finalizadosErrorAFinalizadosPDF(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes)
			throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Núero de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes que han pasado de Validado Telemáticamente a Finalizado PDF, pasando por Finalizado con Error:<br></br>");

			List<EstadoTramiteTrafico> transicionEstados = new ArrayList<EstadoTramiteTrafico>();
			transicionEstados.add(EstadoTramiteTrafico.Pendiente_Envio);
			transicionEstados.add(EstadoTramiteTrafico.Finalizado_Con_Error);
			transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
			transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);

			List<BigDecimal> listaExpedientesCTITValidadosErrorFinalizados = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITEvolucion(esTelematico, fechaInicio, fechaFin,
					listaExpedientes, transicionEstados);

			int detalleTramitesCTITValidadosErrorFinalizados = 0;
			if (listaExpedientesCTITValidadosErrorFinalizados != null && !listaExpedientesCTITValidadosErrorFinalizados.isEmpty()) {
				detalleTramitesCTITValidadosErrorFinalizados = listaExpedientesCTITValidadosErrorFinalizados.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITValidadosErrorFinalizados);
			cadenaResultado.append("</td> <td>Expedientes</td> </tr></span>");
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes que han pasado de Validado Telemáticamente a Finalizado PDF, pasando por Finalizado con Error:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITValidadosErrorFinalizados);
			cadenaResultado.append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosErrorFinalizados);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Finalizados PDF que pasan por Finalizados con Error", e);
		}
		return cadenaResultado;
	}

	private StringBuffer errorEnJefatura(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes con Error en Jefatura:<br></br>");

			List<Object[]> detalleTramitesCTITErrorJefatura = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTITErrorJefatura(fechaInicio, fechaFin, listaExpedientes);

			StringBuffer listado = new StringBuffer();
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			int sumaErrorJefatura = 0;
			if (detalleTramitesCTITErrorJefatura != null && !detalleTramitesCTITErrorJefatura.isEmpty()) {
				for (Object[] obj : detalleTramitesCTITErrorJefatura) {
					BigDecimal suma = (BigDecimal) obj[0];
					sumaErrorJefatura = sumaErrorJefatura + suma.intValue();
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}
			}
			listado.append("</span>");

			cadenaResultado.append(listado);
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes con Error en Jefatura:</em><strong>");
			cadenaResultado.append(sumaErrorJefatura);
			cadenaResultado.append("</strong></p>");

			List<BigDecimal> listaExpedientesCTITErrorJefatura = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITErrorJefatura(fechaInicio, fechaFin, listaExpedientes);
			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITErrorJefatura);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes con Error en Jefatura", e);
		}
		return cadenaResultado;
	}

	private StringBuffer tramitableEnJefatura(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes Tramitables en Jefatura:<br></br>");

			List<BigDecimal> listaExpedientesCTITEmbargoPrecinto = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITEmbargoPrecinto(esTelematico, fechaInicio, fechaFin, listaExpedientes);

			int detalleTramitesCTITEmbargoPrecinto = 0;
			if (listaExpedientesCTITEmbargoPrecinto != null && !listaExpedientesCTITEmbargoPrecinto.isEmpty()) {
				detalleTramitesCTITEmbargoPrecinto = listaExpedientesCTITEmbargoPrecinto.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITEmbargoPrecinto);
			cadenaResultado.append("</td> <td>Tramitables en Jefatura</td> </tr></span>");
			cadenaResultado.append("</table><p><em>Total expedientes Tramitables en Jefatura:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITEmbargoPrecinto);
			cadenaResultado.append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITEmbargoPrecinto);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tramitables en Jefatura", e);
		}
		return cadenaResultado;
	}

	private StringBuffer sinCETNiFactura(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes sin CET, ni factura:<br></br>");

			List<BigDecimal> listaExpedientesCTITSinCETNiFactura = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITSinCETNiFactura(esTelematico, fechaInicio, fechaFin, listaExpedientes);

			int detalleTramitesCTITSinCETNiFactura = 0;
			if (listaExpedientesCTITSinCETNiFactura != null && !listaExpedientesCTITSinCETNiFactura.isEmpty()) {
				detalleTramitesCTITSinCETNiFactura = listaExpedientesCTITSinCETNiFactura.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITSinCETNiFactura);
			cadenaResultado.append("</td> <td>Sin CET, ni factura</td> </tr></span>");
			cadenaResultado.append("</table><p><em>Total expedientes sin CET, ni factura:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITSinCETNiFactura);
			cadenaResultado.append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITSinCETNiFactura);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes sin CET, ni factura", e);
		}
		return cadenaResultado;
	}

	private StringBuffer adjudicacionJudicial(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes por Modo de Adjudicación judicial o subasta:<br></br>");

			List<BigDecimal> listaExpedientesCTITJudicialSubasta = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTITJudicialSubasta(esTelematico, fechaInicio, fechaFin, listaExpedientes);

			int detalleTramitesCTITJudicialSubasta = 0;
			if (listaExpedientesCTITJudicialSubasta != null && !listaExpedientesCTITJudicialSubasta.isEmpty()) {
				detalleTramitesCTITJudicialSubasta = listaExpedientesCTITJudicialSubasta.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITJudicialSubasta);
			cadenaResultado.append("</td> <td>Adjudicación Judicial o Subasta</td> </tr></span>");
			cadenaResultado.append("</table><p><em>Total Modo de Adjudicación judicial o subasta:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITJudicialSubasta);
			cadenaResultado.append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITJudicialSubasta);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadítica de Adjudicación Judicial / Subasta", e);
		}
		return cadenaResultado;
	}

	private StringBuffer vehiculosAgricolas(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Vehículo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes de Vehículos Agrícolas:<br></br>");

			List<Object[]> detalleTramitesCTITVehiculosAgricolas = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTITVehiculosAgricolas(esTelematico, fechaInicio, fechaFin,
					listaExpedientes);

			StringBuffer listado = new StringBuffer();
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			int sumaAgricolas = 0;
			if (detalleTramitesCTITVehiculosAgricolas != null && !detalleTramitesCTITVehiculosAgricolas.isEmpty()) {
				for (Object[] obj : detalleTramitesCTITVehiculosAgricolas) {
					sumaAgricolas = sumaAgricolas + (Integer) obj[0];
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}
			}
			listado.append("</span>");

			cadenaResultado.append(listado);
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes de vehículos agrícolas:</em><strong>");
			cadenaResultado.append(sumaAgricolas);
			cadenaResultado.append("</strong></p>");

			List<BigDecimal> listaExpedientesCTITVehiculosAgricolas = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITVehiculosAgricolas(esTelematico, fechaInicio, fechaFin,
					listaExpedientes);
			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITVehiculosAgricolas);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de Vehículos agrícolas", e);
		}
		return cadenaResultado;
	}

	private StringBuffer carpetaTipoB(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes de Tipo B:<br></br>");

			List<BigDecimal> listaExpedientesCTITTipoB = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITCarpetaB(esTelematico, fechaInicio, fechaFin, listaExpedientes);
			int detalleTramitesCTITCarpetaB = 0;
			if (listaExpedientesCTITTipoB != null && !listaExpedientesCTITTipoB.isEmpty()) {
				detalleTramitesCTITCarpetaB = listaExpedientesCTITTipoB.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			cadenaResultado.append("<tr> <td>");
			cadenaResultado.append(detalleTramitesCTITCarpetaB);
			cadenaResultado.append("</td> <td>Expedientes Tipo B</td> </tr></span>");
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes Tipo B:</em><strong>");
			cadenaResultado.append(detalleTramitesCTITCarpetaB);
			cadenaResultado.append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoB);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes tipo B", e);
		}
		return cadenaResultado;
	}

	private StringBuffer carpetaTipoI(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Vehículo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes de Tipo I:<br></br>");

			List<Object[]> detalleTramitesCTITCarpetaI = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTITCarpetaI(esTelematico, fechaInicio, fechaFin, listaExpedientes);

			StringBuffer listado = new StringBuffer();
			int sumaTipoI = 0;
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			if (detalleTramitesCTITCarpetaI != null && !detalleTramitesCTITCarpetaI.isEmpty()) {
				for (Object[] obj : detalleTramitesCTITCarpetaI) {
					sumaTipoI = sumaTipoI + (Integer) obj[0];
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}
			}

			listado.append("</span>");

			cadenaResultado.append(listado);
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes Tipo I:</em><strong>");
			cadenaResultado.append(sumaTipoI);
			cadenaResultado.append("</strong></p>");

			List<BigDecimal> listaExpedientesCTITTipoI = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITCarpetaI(esTelematico, fechaInicio, fechaFin, listaExpedientes);
			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoI);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tipo I", e);
		}
		return cadenaResultado;
	}

	private StringBuffer carpetaFusion(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append(
					"<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Vehículo</th> </tr> </span> Expedientes de Tipo Fusión:<br></br>");

			List<Object[]> detalleTramitesCTITCarpetaFusion = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTITCarpetaFusion(esTelematico, fechaInicio, fechaFin, listaExpedientes);

			StringBuffer listado = new StringBuffer();
			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			int sumaTipoFusion = 0;
			if (detalleTramitesCTITCarpetaFusion != null && !detalleTramitesCTITCarpetaFusion.isEmpty()) {
				for (Object[] obj : detalleTramitesCTITCarpetaFusion) {
					sumaTipoFusion = sumaTipoFusion + (Integer) obj[0];
					listado.append("<tr> <td>");
					listado.append(obj[0].toString());
					listado.append("</td> <td>");
					listado.append(obj[1].toString());
					listado.append("</td> </tr>");
				}
			}

			listado.append("</span>");

			cadenaResultado.append(listado);
			cadenaResultado.append("</table>");
			cadenaResultado.append("<p><em>Total expedientes Tipo Fusion:</em><strong>");
			cadenaResultado.append(sumaTipoFusion);
			cadenaResultado.append("</strong></p>");

			List<BigDecimal> listaExpedientesCTITTipoFusion = servicioTramiteTraficoTransmision.obtenerListaExpedientesCTITCarpetaFusion(esTelematico, fechaInicio, fechaFin, listaExpedientes);
			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoFusion);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tipo Fusion", e);
		}
		return cadenaResultado;
	}

	private StringBuffer anotacionGest(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, List<BigDecimal> listaExpedientes) throws ParseException {
		try {
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
			cadenaResultado.append("Expedientes con Anotaciones en GEST:<br></br>");

			List<BigDecimal> listaExpedientesCTITAnotacionesGest = servicioTramiteTraficoTransmision.obtenerListadoNumerosExpedienteCTITAnotacionesGest(esTelematico, fechaInicio, fechaFin,
					listaExpedientes);

			int detalleTramitesCTITAnotacionesGest = 0;
			if (listaExpedientesCTITAnotacionesGest != null && !listaExpedientesCTITAnotacionesGest.isEmpty()) {
				detalleTramitesCTITAnotacionesGest = listaExpedientesCTITAnotacionesGest.size();
			}

			cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>");
			cadenaResultado.append(detalleTramitesCTITAnotacionesGest);
			cadenaResultado.append("</td> <td>Expedientes con Anotaciones en GEST</td> </tr></span>");
			cadenaResultado.append("</table><p><em>Total expedientes con Anotaciones en GEST:</em><strong>").append(detalleTramitesCTITAnotacionesGest).append("</strong></p>");

			listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITAnotacionesGest);
		} catch (Exception e) {
			log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de Anotaciones en GEST", e);
		}
		return cadenaResultado;
	}

	private StringBuffer totalPorColegiadoCTIT(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, boolean esTelematico, String idProvincia) throws ParseException {
		cadenaResultado.append("<br></br>Transferencias por colegiado:<br></br>");
		cadenaResultado.append(
				"<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de colegiado</th> <th>Número de trámites</th> <th>Estado del trámite</th> </tr> </span> ");
		List<Object[]> listaTramitesTelematicos = servicioTramiteTraficoTransmision.obtenerDetalleTramitesCTIT(esTelematico, fechaInicio, fechaFin, idProvincia);

		StringBuffer listado = new StringBuffer();
		listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		if (listaTramitesTelematicos != null && !listaTramitesTelematicos.isEmpty()) {
			for (Object[] obj : listaTramitesTelematicos) {
				listado.append("<tr> <td>");
				listado.append(obj[1]);
				listado.append("</td> <td>");
				listado.append(obj[0].toString());
				listado.append("</td> <td>");
				Integer estadoTramite = new Integer(obj[2] != null ? obj[2].toString() : BigDecimal.ZERO.toString());
				switch (estadoTramite) {
					case 12:
						listado.append(EstadoTramiteTrafico.Finalizado_Telematicamente.getNombreEnum());
						break;
					case 13:
						listado.append(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum());
						break;
					case 14:
						listado.append(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum());
						break;
					default:
						listado.append("No definido");
						break;
				}
				listado.append("</td> </tr>");
			}
		}
		listado.append("</span>");

		cadenaResultado.append(listado);
		cadenaResultado.append("</table>");

		return cadenaResultado;
	}

	private StringBuffer cadenaHTMLResultadoEstadisticasCTIT(boolean esTelematico, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {
		StringBuffer cadenaResultado = new StringBuffer("<b><u>");
		cadenaResultado = totalTelematicosONoTelematicosCTIT(cadenaResultado, fechaInicio, fechaFin, esTelematico);
		return cadenaResultado;
	}

	private StringBuffer cadenaHTMLResultadoEstadisticasCTITDelegaciones(boolean esTelematico, String provincia, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {
		StringBuffer cadenaResultado = new StringBuffer("");
		cadenaResultado = totalTelematicosONoTelematicosCTITDelegaciones(cadenaResultado, fechaInicio, fechaFin, esTelematico, provincia);
		return cadenaResultado;
	}

	private void pdfAdjunto(List<HtmlBean> arrayHtmlBean, Fecha fechaFin) {
		arrayHtmlBean = aniadeTablaGeneralApdf(arrayHtmlBean);
		guardaPdfEstadisticasCtit(arrayHtmlBean, fechaFin);
	}

	private void guardaPdfEstadisticasCtit(List<HtmlBean> listHtmlBean, Fecha fechaFin) {
		String nombreFichero = ConstantesEstadisticas.ESTADISTICASCTIT + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();
		String nombreFicheroHtml = ConstantesEstadisticas.FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();

		FileOutputStream fileOutputStream = null;
		try {

			FicheroBean ficheroBHtml = new FicheroBean();
			ficheroBHtml.setFichero(new File(nombreFichero));
			ficheroBHtml.setTipoDocumento(ConstantesGestorFicheros.ESTADISTICAS);
			ficheroBHtml.setSubTipo(ConstantesGestorFicheros.CTIT);
			ficheroBHtml.setSobreescribir(false);
			ficheroBHtml.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			ficheroBHtml.setNombreDocumento(nombreFichero);
			ficheroBHtml.setFecha(fechaFin);

			File ficheroPdf = gestorDocumentos.guardarFichero(ficheroBHtml);
			File ficheroHtml = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, fechaFin, nombreFicheroHtml,
					ConstantesGestorFicheros.EXTENSION_HTML).getFile();

			ITextRenderer iTextRenderer = new ITextRenderer();
			iTextRenderer.setDocument(ficheroHtml);
			iTextRenderer.layout();
			fileOutputStream = new FileOutputStream(ficheroPdf);
			iTextRenderer.createPDF(fileOutputStream);
		} catch (FileNotFoundException e) {
			log.error("Se ha producido un error al guardar el pdf de estadisticas de ctit. Fichero no existe ", e);
		} catch (IOException e) {
			log.error("Se ha producido un error de lectura/escritura al guardar el pdf de estadisticas de ctit. ", e);
		} catch (DocumentException e) {
			log.error("Se ha producido un error al generar pdf de estadisticas de ctit. ", e);
		} catch (OegamExcepcion e) {
			log.error("Se ha producido un error al guardar el pdf de estadisticas de ctit. ", e);
		} finally {
			if (fileOutputStream != null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
	}

	private List<HtmlBean> aniadeTablaGeneralApdf(List<HtmlBean> arrayHtmlBean) {
		List<HtmlBean> listaHtmlBean = new ArrayList<>();
		HtmlBean htmlBeanAux;
		for (HtmlBean htmlBean : arrayHtmlBean) {
			StringBuffer sb = new StringBuffer();
			sb.append(htmlBean.getHtmlCode());
			StringBuffer sbTemporal = anaidirTablasPorcentajes(sb);
			htmlBeanAux = new HtmlBean();
			htmlBeanAux.setHtmlCode(sbTemporal.toString());
			listaHtmlBean.add(htmlBeanAux);
		}
		return listaHtmlBean;
	}

	private List<BigDecimal> quitarDuplicadosLista(List<BigDecimal> lista, List<BigDecimal> subLista) {
		if (subLista != null && !subLista.isEmpty()) {
			for (int i = 0; i < subLista.size(); i++) {
				for (int j = 0; j < lista.size(); j++) {
					if (lista.get(j).equals(subLista.get(i))) {
						lista.remove(j);
						j--;
						continue;
					}
				}
			}
		}
		return lista;
	}
}