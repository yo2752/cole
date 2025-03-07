package org.gestoresmadrid.oegam2comun.estadisticas.service.impl;

import java.text.ParseException;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.gestoresmadrid.core.model.enumerados.Provincias;
import org.gestoresmadrid.core.vehiculo.model.enumerados.Combustible;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.estadisticas.service.ServicioEstadisticasCorreoMate;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.vehiculo.view.dto.VehiculoDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioEstadisticasCorreoMateImpl implements ServicioEstadisticasCorreoMate {

	private static final long serialVersionUID = 4470463045462913622L;

	private static ILoggerOegam log = LoggerOegam.getLogger(ServicioEstadisticasCorreoMateImpl.class);

	@Autowired
	ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	ServicioCorreo servicioCorreo;

	@Autowired
	ServicioVehiculo servicioVehiculo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Override
	public ResultBean enviarMailEstadisticasMate() throws OegamExcepcion, Exception {
		StringBuffer sb = datosEstadisticasMate();
		String recipient = "";
		String subject = "";

		String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.envio.estadisticas");
		if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.MATE.destinatarios1");
			subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.MATE.subject");
		} else {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
			subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - MATE";
		}

		return servicioCorreo.enviarCorreo(sb.toString(), false, null, subject, recipient, null, null, null, null);
	}

	@Override
	public ResultBean enviarMailFirstMate() throws OegamExcepcion, Exception {
		VehiculoDto vehiculo = servicioVehiculo.primeraMatricula(new Date());
		String recipient = "";
		String subject = "";
		String direccionesOcultas = "";

		if (vehiculo != null && vehiculo.getMatricula() != null && !vehiculo.getMatricula().isEmpty()) {
			StringBuffer sb = new StringBuffer("<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>");
			sb.append("La primera matr&iacute;cula de hoy ");
			sb.append(utilesFecha.formatoFecha(new Date()));
			sb.append(" ha sido: <b>").append(vehiculo.getMatricula());
			sb.append("</b> con hora: ");
			sb.append(vehiculo.getFechaMatriculacion().getHora());
			sb.append(":");
			sb.append(vehiculo.getFechaMatriculacion().getMinutos());
			sb.append(":");
			sb.append(vehiculo.getFechaMatriculacion().getSegundos());
			sb.append("<br></br><br></br></span>");

			guardarPrimeraMatricula(vehiculo.getMatricula());

			String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.primera.matricula");
			if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
				recipient = gestorPropiedades.valorPropertie("direccion.envio.primera.matricula.destinatarios1");
				direccionesOcultas = gestorPropiedades.valorPropertie("direccion.envio.primera.matricula.destinatarios2");
				subject = gestorPropiedades.valorPropertie("direccion.envio.primera.matricula.subject");
			} else {
				recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
				subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - Primera Matricula";
				direccionesOcultas = "";
			}

			return servicioCorreo.enviarCorreo(sb.toString(), null, null, subject, recipient, null, direccionesOcultas, null, null);
		} else {
			// sb.append("Hoy " + utilesFecha.formatoFecha(new Date()) + ", aún no se ha procesado la primera matriculación.<br></br><br></br></span>");
			ResultBean result = new ResultBean(Boolean.TRUE);
			result.setMensaje("Hoy " + utilesFecha.formatoFecha(new Date()) + ", a&uacute;n no se ha procesado la primera matriculaci&oacute;n.");
			return result;
		}
	}

	@Override
	public ResultBean enviarMailUltMate() throws OegamExcepcion, Exception {
		VehiculoDto vehiculo = servicioVehiculo.ultimaMatricula(new Date());
		String recipient = "";
		String subject = "";
		String direccionesOcultas = "";

		StringBuffer sb = new StringBuffer("<br><span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\"><br>");
		if (vehiculo != null && vehiculo.getMatricula() != null && !vehiculo.getMatricula().isEmpty()) {
			sb.append("La &uacute;ltima matr&iacute;cula de hoy ");
			sb.append(utilesFecha.formatoFecha(new Date()));
			sb.append(" ha sido: <b>").append(vehiculo.getMatricula());
			sb.append("</b> con hora: ");
			sb.append(vehiculo.getFechaMatriculacion().getHora());
			sb.append(":");
			sb.append(vehiculo.getFechaMatriculacion().getMinutos());
			sb.append(":");
			sb.append(vehiculo.getFechaMatriculacion().getSegundos());
			sb.append("<br></br><br></br></span>");
		} else {
			sb.append("Hoy ");
			sb.append(utilesFecha.formatoFecha(new Date()));
			sb.append(", no se ha procesado ninguna matriculaci&oacute;n.<br></br><br></br></span>");
		}

		String habilitar = gestorPropiedades.valorPropertie("habilitar.proceso.ultima.matricula");
		if (StringUtils.isNotBlank(habilitar) && "SI".equals(habilitar)) {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.ultima.matricula.destinatarios1");
			direccionesOcultas = gestorPropiedades.valorPropertie("direccion.envio.ultima.matricula.destinatarios2");
			subject = gestorPropiedades.valorPropertie("direccion.envio.ultima.matricula.subject");
		} else {
			recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.pruebas");
			subject = gestorPropiedades.valorPropertie("subject.envio.estadisticas.pruebas") + " - Ultima Matricula";
			direccionesOcultas = "";
		}

		return servicioCorreo.enviarCorreo(sb.toString(), null, null, subject, recipient, null, direccionesOcultas, null, null);
	}

	private void guardarPrimeraMatricula(String matricula) {
		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0'?><info><matricula><b>" + matricula + "</b></matricula></info>");
		try {
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.FICHERO_PRIMERA_MATRICULA, null, null, "primera_matricula", ConstantesGestorFicheros.EXTENSION_XML, String.valueOf(xml)
					.getBytes());
		} catch (OegamExcepcion e) {
			e.printStackTrace();
		}
	}

	private StringBuffer datosEstadisticasMate() throws OegamExcepcion, ParseException {
		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";

		Fecha fechaFin = utilesFecha.getFechaActual();
		Fecha fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);

		resultadoHtml.append("<br><span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
		resultadoHtml.append("<br>Estad&iacute;sticas MATE en Oegam. Mensaje generado el ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFecha, new Date()));
		resultadoHtml.append(".<br></br>Consulta realizada para el periodo del ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaInicio.getFecha()));
		resultadoHtml.append(" al ");
		resultadoHtml.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()));
		resultadoHtml.append(".<br></br>");

		resultadoHtml.append("</span><span style=\"font-size:12pt;font-family:Tunga;\">");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(TOTALES, fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(Provincias.Madrid.toString(), fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(Provincias.Guadalajara.toString(), fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(Provincias.Segovia.toString(), fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(Provincias.Ciudad_Real.toString(), fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(Provincias.Cuenca.toString(), fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(PROVINCIA_AVILA, fechaInicio, fechaFin));
		resultadoHtml.append("<br>");
		resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE(TOTAL_DELEGACIONES, fechaInicio, fechaFin));
		resultadoHtml.append("</span><br></br><br></br>");

		String nombreFichero = FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.MATE, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, resultadoHtml.toString()
				.getBytes());

		return resultadoHtml;
	}

	private StringBuffer cadenaHTMLResultadoEstadisticasMATE(String idProvincia, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {
		StringBuffer cadenaResultado = new StringBuffer("<b><u>");
		String jefatura = "";

		if (TOTALES.equals(idProvincia)) {
			cadenaResultado.append("Total Matriculaciones Oegam :");
		} else if (Provincias.Madrid.toString().equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de Madrid :");
		} else if (Provincias.Segovia.toString().equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de Segovia :");
			jefatura = JEFATURA_PROVINCIAL_SEGOVIA;
		} else if (PROVINCIA_AVILA.equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de &Aacute;vila :");
			jefatura = JEFATURA_PROVINCIAL_AVILA;
		} else if (Provincias.Cuenca.toString().equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de Cuenca :");
			jefatura = JEFATURA_PROVINCIAL_CUENCA;
		} else if (Provincias.Ciudad_Real.toString().equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de Ciudad Real :");
			jefatura = JEFATURA_PROVINCIAL_CIUDAD_REAL;
		} else if (Provincias.Guadalajara.toString().equals(idProvincia)) {
			cadenaResultado.append("Delegaci&oacute;n de Guadalajara :");
			jefatura = JEFATURA_PROVINCIAL_GUADALAJARA;
		} else if (TOTAL_DELEGACIONES.equals(idProvincia)) {
			cadenaResultado.append("Total Delegaciones (Excluyendo Madrid):");
		}

		cadenaResultado.append("</u></b><br></br>");

		Integer sumarioMateTelProvincia = servicioTramiteTraficoMatriculacion.obtenerTotalesTramitesMATE(true, idProvincia, fechaInicio, fechaFin);
		Integer sumarioMateNoTelProvincia = servicioTramiteTraficoMatriculacion.obtenerTotalesTramitesMATE(false, idProvincia, fechaInicio, fechaFin);

		if (sumarioMateTelProvincia == null) {
			sumarioMateTelProvincia = 0;
		}

		if (sumarioMateNoTelProvincia == null) {
			sumarioMateNoTelProvincia = 0;
		}

		cadenaResultado.append("<tr> <td> Total: ");
		cadenaResultado.append((sumarioMateTelProvincia + sumarioMateNoTelProvincia));
		cadenaResultado.append("</td> </tr>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>N&uacute;mero de Matriculaciones</th> <th>Tipo </th> </tr> </span> ");
		cadenaResultado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>");
		cadenaResultado.append(sumarioMateTelProvincia);
		cadenaResultado.append("</td><td>Telem&aacute;ticas</td> </tr><tr> <td>");
		cadenaResultado.append(sumarioMateNoTelProvincia);
		cadenaResultado.append("</td><td>No Telem&aacute;ticas</td> </tr></span>");
		cadenaResultado.append("</table>");

		if (Provincias.Madrid.toString().equals(idProvincia)) {
			try {
				cadenaResultado = madrid(cadenaResultado, sumarioMateTelProvincia, sumarioMateNoTelProvincia, fechaInicio, fechaFin);
			} catch (Exception e) {
				log.error("Error al crear las estad&iacute;sticas de Mate para MADRID", e);
			}
		} else if (!TOTALES.equals(idProvincia) && !TOTAL_DELEGACIONES.equals(idProvincia)) {
			try {
				cadenaResultado = restoDelegaciones(cadenaResultado, idProvincia, jefatura, sumarioMateTelProvincia, sumarioMateNoTelProvincia, fechaInicio, fechaFin);
			} catch (Exception e) {
				log.error("Error al crear las estad&iacute;sticas de Mate para la jefatura: " + jefatura + "y provincia: " + idProvincia, e);
			}
		}

		return cadenaResultado;
	}

	private StringBuffer madrid(StringBuffer cadenaResultado, int sumarioMateTelProvincia, int sumarioMateNoTelProvincia, Fecha fechaInicio, Fecha fechaFin) throws ParseException {
		if (sumarioMateTelProvincia > 0) {
			cadenaResultado.append("<br></br>");
			cadenaResultado.append("<u>Desglose Telem&aacute;ticas:</u>");

			cadenaResultado = jefaturaTel(cadenaResultado, fechaInicio, fechaFin);
			cadenaResultado = tipoVehiculoTel(cadenaResultado, fechaInicio, fechaFin, Provincias.Madrid.toString());
			cadenaResultado = tipoCarburanteTel(cadenaResultado, fechaInicio, fechaFin, Provincias.Madrid.toString());
		}

		if (sumarioMateNoTelProvincia > 0) {
			cadenaResultado.append("<br></br>");
			cadenaResultado.append("<u>Desglose No Telem&aacute;ticas:</u>");

			cadenaResultado = jefaturaNoTelMadrid(cadenaResultado, fechaInicio, fechaFin);
		}
		return cadenaResultado;
	}

	private StringBuffer restoDelegaciones(StringBuffer cadenaResultado, String idProvincia, String jefatura, int sumarioMateTelProvincia, int sumarioMateNoTelProvincia, Fecha fechaInicio,
			Fecha fechaFin) throws ParseException {
		if (sumarioMateTelProvincia > 0) {
			cadenaResultado.append("<br></br>");
			cadenaResultado.append("<u>Desglose Telem&aacute;ticas:</u>");

			cadenaResultado = tipoVehiculoTel(cadenaResultado, fechaInicio, fechaFin, idProvincia);
			cadenaResultado = tipoCarburanteTel(cadenaResultado, fechaInicio, fechaFin, idProvincia);
		}

		if (sumarioMateNoTelProvincia > 0) {
			cadenaResultado.append("<br></br>");
			cadenaResultado.append("<u>Desglose No Telem&aacute;ticas:</u>");

			cadenaResultado = jefaturaNoTelRestoDelegacion(cadenaResultado, fechaInicio, fechaFin, idProvincia, jefatura);
		}
		return cadenaResultado;
	}

	private StringBuffer jefaturaTel(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones Telem&aacute;ticas por Jefatura: </td> </tr>");

		List<Object[]> detalleTramitesMatrTelJ = servicioTramiteTraficoMatriculacion.obtenerTramitesMATETelJefatura(Provincias.Madrid.toString(), fechaInicio, fechaFin);

		int madridTelematicas = 0;
		int alcorconTelematicas = 0;
		int alcalaconTelematicas = 0;
		for (Object[] obj : detalleTramitesMatrTelJ) {
			if (JEFATURA_PROVINCIAL_MADRID.equalsIgnoreCase((obj[1].toString()))) {
				madridTelematicas = Integer.parseInt(obj[0].toString());
			} else if (JEFATURA_PROVINCIAL_ALCORCON.equalsIgnoreCase((obj[1].toString()))) {
				alcorconTelematicas = Integer.parseInt(obj[0].toString());
			} else if (JEFATURA_PROVINCIAL_ALCALA.equalsIgnoreCase((obj[1].toString()))) {
				alcalaconTelematicas = Integer.parseInt(obj[0].toString());
			}
		}

		StringBuffer listadoTelJefatura = new StringBuffer();
		listadoTelJefatura.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>");
		listadoTelJefatura.append(madridTelematicas);
		listadoTelJefatura.append("</td><td>MADRID</td> </tr><tr> <td>");
		listadoTelJefatura.append(alcorconTelematicas);
		listadoTelJefatura.append("</td><td>ALCORCON</td> </tr><tr> <td>");
		listadoTelJefatura.append(alcalaconTelematicas);
		listadoTelJefatura.append("</td><td>ALCALA</td> </tr></span>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Jefatura </th> </tr> </span> ");
		cadenaResultado.append(listadoTelJefatura);
		cadenaResultado.append("</table>");

		return cadenaResultado;
	}

	private StringBuffer tipoVehiculoTel(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, String idProvincia) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones Telem&aacute;ticas por tipo de Veh&iacute;culo: </td> </tr>");

		List<Object[]> detalleTramitesMatrTelVehicM = servicioTramiteTraficoMatriculacion.obtenerTramitesMATETelTipoVehiculo(idProvincia, fechaInicio, fechaFin);

		StringBuffer listadoTelVehiculo = new StringBuffer();
		int sinEspecificar = 0;
		listadoTelVehiculo.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		for (Object[] obj : detalleTramitesMatrTelVehicM) {
			if (obj[1].toString() != null && !obj[1].toString().isEmpty() && !obj[1].toString().equals("-1")) {
				listadoTelVehiculo.append("<tr> <td>");
				listadoTelVehiculo.append(obj[0].toString());
				listadoTelVehiculo.append("</td><td>");
				listadoTelVehiculo.append(obj[1].toString());
				listadoTelVehiculo.append("</td> </tr>");
			} else {
				sinEspecificar = sinEspecificar + Integer.parseInt(obj[0].toString());
			}
		}
		if (sinEspecificar != 0) {
			listadoTelVehiculo.append("<tr> <td>");
			listadoTelVehiculo.append(sinEspecificar);
			listadoTelVehiculo.append("</td><td>");
			listadoTelVehiculo.append("SIN ESPECIFICAR");
			listadoTelVehiculo.append("</td> </tr>");
		}
		listadoTelVehiculo.append("</span>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Tipo de Vehículo </th> </tr> </span> ");
		cadenaResultado.append(listadoTelVehiculo);
		cadenaResultado.append("</table>");

		return cadenaResultado;
	}

	private StringBuffer tipoCarburanteTel(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, String idProvincia) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones Telem&aacute;ticas por tipo de Combustible: </td> </tr>");

		List<Object[]> detalleTramitesMatrTelCarbM = servicioTramiteTraficoMatriculacion.obtenerTramitesMATETelCarburante(idProvincia, fechaInicio, fechaFin);

		StringBuffer listadoTelComb = new StringBuffer();
		int sinEspecificar = 0;
		listadoTelComb.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		for (Object[] obj : detalleTramitesMatrTelCarbM) {
			if (obj.length > 1 && obj[1] != null && obj[1].toString() != null && !obj[1].toString().isEmpty() && !"-1".equals(obj[1].toString())) {
				listadoTelComb.append("<tr> <td>");
				listadoTelComb.append(obj[0].toString());
				listadoTelComb.append("</td><td>");
				listadoTelComb.append(Combustible.convertirAString(obj[1].toString()));
				listadoTelComb.append("</td> </tr>");
			} else {
				sinEspecificar = sinEspecificar + Integer.parseInt(obj[0].toString());
			}
		}
		if (sinEspecificar != 0) {
			listadoTelComb.append("<tr> <td>");
			listadoTelComb.append(sinEspecificar);
			listadoTelComb.append("</td><td>");
			listadoTelComb.append("SIN ESPECIFICAR");
			listadoTelComb.append("</td> </tr>");
		}
		listadoTelComb.append("</span>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Total</th> <th>Tipo de Combustible </th> </tr> </span> ");
		listadoTelComb.append("</span>");
		cadenaResultado.append(listadoTelComb).append("</table>");

		return cadenaResultado;
	}

	private StringBuffer jefaturaNoTelMadrid(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones No Telem&aacute;ticas por Jefatura: </td> </tr>");

		// Totales de Ficha Tecnica A o C por Jefatura
		int numTramitesMatrPdfItvM = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_MADRID, fechaInicio,
				fechaFin);
		int numTramitesMatrPdfItvA = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_ALCORCON, fechaInicio,
				fechaFin);
		int numTramitesMatrPdfItvAlc = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_ALCALA, fechaInicio,
				fechaFin);

		// Totales Que no son ni Ficha Tecnica A ni C por Jefatura
		int numTramitesMatrPdfNoItvM = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_MADRID,
				fechaInicio, fechaFin);
		int numTramitesMatrPdfNoItvA = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_ALCORCON,
				fechaInicio, fechaFin);
		int numTramitesMatrPdfNoItvAlc = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(Provincias.Madrid.toString(), JEFATURA_PROVINCIAL_ALCALA,
				fechaInicio, fechaFin);

		StringBuffer listadoITV = new StringBuffer();
		listadoITV.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>Ficha Tecnica Tipo A o C</td> <td>");
		listadoITV.append(numTramitesMatrPdfItvM);
		listadoITV.append("</td> <td>");
		listadoITV.append(numTramitesMatrPdfItvA);
		listadoITV.append("</td> <td>");
		listadoITV.append(numTramitesMatrPdfItvAlc);
		listadoITV.append("</td> <td>");
		listadoITV.append((numTramitesMatrPdfItvM + numTramitesMatrPdfItvA + numTramitesMatrPdfItvAlc));
		listadoITV.append("</td> </tr><tr> <td> Veh&iacute;culos No Admitidos </td> <td>");
		listadoITV.append(numTramitesMatrPdfNoItvM);
		listadoITV.append("</td> <td>");
		listadoITV.append(numTramitesMatrPdfNoItvA);
		listadoITV.append("</td> <td>");
		listadoITV.append(numTramitesMatrPdfNoItvAlc);
		listadoITV.append("</td> <td>");
		listadoITV.append((numTramitesMatrPdfNoItvM + numTramitesMatrPdfNoItvA + numTramitesMatrPdfNoItvAlc));
		listadoITV.append("</td> </tr></span>");

		cadenaResultado.append(
				"<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Tipo </th> <th>A.SORIA</th> <th>ALCORCON</th> <th>ALCALA</th> <th>SUBTOTAL</th></tr> </span> ");
		cadenaResultado.append(listadoITV);
		cadenaResultado.append("</table>");

		if (numTramitesMatrPdfNoItvM > 0 || numTramitesMatrPdfNoItvA > 0 || numTramitesMatrPdfNoItvAlc > 0) {
			if (numTramitesMatrPdfNoItvM > 0) {
				noAdmitidos(cadenaResultado, fechaInicio, fechaFin, JEFATURA_PROVINCIAL_MADRID, Provincias.Madrid.toString());
			}
			if (numTramitesMatrPdfNoItvA > 0) {
				noAdmitidos(cadenaResultado, fechaInicio, fechaFin, JEFATURA_PROVINCIAL_ALCORCON, Provincias.Madrid.toString());
			}
			if (numTramitesMatrPdfNoItvAlc > 0) {
				noAdmitidos(cadenaResultado, fechaInicio, fechaFin, JEFATURA_PROVINCIAL_ALCALA, Provincias.Madrid.toString());
			}
		}
		return cadenaResultado;
	}

	private StringBuffer jefaturaNoTelRestoDelegacion(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, String idProvincia, String jefatura) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones No Telem&aacute;ticas: </td> </tr>");

		// Totales de Ficha Tecnica A o C por Jefatura
		int numTramitesMatrPdfItv = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(idProvincia, jefatura, fechaInicio, fechaFin);

		// Totales Que no son ni Ficha Tecnica A ni C por Jefatura
		int numTramitesMatrPdfNoItv = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(idProvincia, jefatura, fechaInicio, fechaFin);

		StringBuffer listadoITV = new StringBuffer();
		listadoITV.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>Ficha Tecnica Tipo A o C</td> <td>");
		listadoITV.append(numTramitesMatrPdfItv);
		listadoITV.append("</td> </tr><tr> <td> Veh&iacute;culos No Admitidos </td> <td>");
		listadoITV.append(numTramitesMatrPdfNoItv);
		listadoITV.append("</td> </tr></span>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Tipo </th> <th>SUBTOTAL</th></tr> </span> ");
		cadenaResultado.append(listadoITV);
		cadenaResultado.append("</table>");

		if (numTramitesMatrPdfNoItv > 0) {
			noAdmitidos(cadenaResultado, fechaInicio, fechaFin, jefatura, idProvincia);
		}
		return cadenaResultado;
	}

	private StringBuffer noAdmitidos(StringBuffer cadenaResultado, Fecha fechaInicio, Fecha fechaFin, String jefatura, String provincia) throws ParseException {
		cadenaResultado.append("<br></br><tr> <td> N&uacute;mero de Matriculaciones No Telem&aacute;ticas para Vehículos No Admitidos: </td> </tr>");
		List<Object[]> detalleTramitesMatrPdfVehicM = servicioTramiteTraficoMatriculacion.obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(provincia, jefatura, fechaInicio, fechaFin);

		StringBuffer listadoNoTelVehiculoM = new StringBuffer();
		listadoNoTelVehiculoM.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		for (Object[] obj : detalleTramitesMatrPdfVehicM) {
			listadoNoTelVehiculoM.append("<tr> <td>");
			listadoNoTelVehiculoM.append(obj[0].toString());
			listadoNoTelVehiculoM.append("</td><td>");
			listadoNoTelVehiculoM.append(obj[1].toString());
			listadoNoTelVehiculoM.append("</td> </tr>");
		}
		listadoNoTelVehiculoM.append("</span>");

		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>");
		if (jefatura.equals(JEFATURA_PROVINCIAL_ALCORCON)) {
			cadenaResultado.append("Alcorcon");
		} else if (jefatura.equals(JEFATURA_PROVINCIAL_ALCALA)) {
			cadenaResultado.append("Alcala");
		} else if (jefatura.equals(JEFATURA_PROVINCIAL_MADRID)) {
			cadenaResultado.append("A.Soria");
		} else {
			cadenaResultado.append("Total");
		}
		cadenaResultado.append("</th> <th>Tipo de Vehículo </th> </tr> </span> ");
		cadenaResultado.append(listadoNoTelVehiculoM);
		cadenaResultado.append("</table>");

		return cadenaResultado;
	}
}