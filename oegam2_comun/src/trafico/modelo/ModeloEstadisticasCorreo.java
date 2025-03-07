package trafico.modelo;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;
import org.xhtmlrenderer.pdf.ITextRenderer;

import com.lowagie.text.DocumentException;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import escrituras.utiles.enumerados.Provincias;
import estadisticas.utiles.enumerados.ConvierteCodigoALiteral;
import general.beans.HtmlBean;
import general.utiles.UtilesCadenaCaracteres;
import hibernate.entities.trafico.TramiteTrafico;
import trafico.beans.DetalleTramitesCTITBean;
import trafico.beans.TotalesTramitesCTITBean;
import trafico.utiles.constantes.ConstantesEstadisticas;
import trafico.utiles.enumerados.TipoProceso;
import trafico.utiles.enumerados.TipoTramiteTrafico;
import trafico.utiles.enumerados.TipoTransferencia;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class ModeloEstadisticasCorreo {

	private static final String CADENA_A_SUSTITUIR = "cadena a sustituir";

	private static ILoggerOegam log = LoggerOegam.getLogger(ModeloEstadisticasCorreo.class);

	private static final String NO_TELEMATICAS = "NO TELEMÁTICAS";
	private static final String TELEMATICAS = "TELEMÁTICAS";

	private boolean entroTel = true;
	private boolean entroNoTel = true;
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

	private float totalPorcentajeTelMadrid = 0;
	private float totalPorcentajeTelSegovia = 0;
	private float totalPorcentajeTelAvila = 0;
	private float totalPorcentajeTelCuenca = 0;
	private float totalPorcentajeTelCiudadReal = 0;
	private float totalPorcentajeTelGuadalajara = 0;

	private static final String JEFATURA_PROVINCIAL_MADRID = "MADRID";
	private static final String JEFATURA_PROVINCIAL_ALCORCON = "ALCORCON";
	private static final String JEFATURA_PROVINCIAL_ALCALA = "ALCALA DE HENARES";

	private ModeloTrafico modeloTrafico;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	EjecucionesProcesosDAO ejecucionesProcesosDAO;

	public ModeloEstadisticasCorreo() {
		super();
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public ServicioCorreo getServicioCorreo() {
		if(servicioCorreo == null){
			servicioCorreo = ContextoSpring.getInstance().getBean(ServicioCorreo.class);
		}
		return servicioCorreo;
	}

	/**
	 * Método que envía un correo con información de estadísticas de CTIT
	 * 
	 * @return
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	public ResultBean enviarMailEstadisticasCTIT() throws OegamExcepcion, Exception {

		// Se comprueba si el formato va a ser en texto plano o no
		String sTextoPlano = gestorPropiedades.valorPropertie("estadisticas.presentacion.formato.textoPlano");
		boolean textoPlano = "si".equalsIgnoreCase(sTextoPlano!=null ? sTextoPlano.trim() : null);

		// Se llama al método que obtiene los datos y forma la cadena HTML
		StringBuffer sb;
		if (textoPlano) {
			sb = datosEstadisticasCTIT(true);
		} else {
			sb = datosEstadisticasCTIT(false);
		}

		// Recuperar configuracion del envio del correo de estadisticas CTIT
		String recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.destinatarios1");
		String subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.subject");

		// Enviar correo
		ResultBean resultEnvio;
		resultEnvio = getServicioCorreo().enviarCorreo(sb.toString(), textoPlano, null, subject, recipient, null, null, null);
		return resultEnvio;
	}

	/**
	 * Método que envía un correo con información de estadísticas de MATE
	 * 
	 * @return
	 * @throws OegamExcepcion
	 * @throws Exception
	 */
	public ResultBean enviarMailEstadisticasMATE() throws OegamExcepcion, Exception {
		// Se comprueba si el formato va a ser en texto plano o no
		String sTextoPlano = gestorPropiedades.valorPropertie("estadisticas.presentacion.formato.textoPlano");
		boolean textoPlano = "si".equalsIgnoreCase(sTextoPlano.trim());

		// Se llama al método que obtiene los datos y forma la cadena HTML
		StringBuffer sb = datosEstadisticasMATE(textoPlano);

		// Recuperar configuracion del envio del correo de estadisticas MATE
		String subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.MATE.subject");
		String recipient = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.MATE.destinatarios1");

		// Enviar correo
		ResultBean resultEnvio;
		resultEnvio = getServicioCorreo().enviarCorreo(sb.toString(), textoPlano, null, subject, recipient, null, null, null);

		return resultEnvio;
	}

	/**
	 * 
	 * @param esTextoPlano
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	private StringBuffer datosEstadisticasCTIT(boolean esTextoPlano) throws OegamExcepcion, ParseException {

		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";

		Fecha fechaFin = getFechaFinEstadisticas();
		Fecha fechaInicio = getFechaInicioEstadisticas(fechaFin);

		// Encabezamiento del e-mail
		if (!esTextoPlano) {
			resultadoHtml.append("<br><span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">");
		}

		resultadoHtml.append("<br>Estadísticas CTIT en Oegam. Mensaje generado el ")
				.append(utilesFecha.formatoFecha(sFormatoFecha, new Date()))
				.append(".<br></br>Consulta realizada para el período del ")
				.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo,fechaInicio.getFecha()))
				.append(" al ")
				.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()))
				.append(".<br></br>");

		if (!esTextoPlano) {
			resultadoHtml.append("</span><span style=\"font-size:12pt;font-family:Tunga;\">");
		}
		esTextoPlano = false;
		// Trámites telemáticos
		if (!esTextoPlano) {
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasCTIT(true, fechaInicio, fechaFin));
		} else {
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasCTIT(true, fechaInicio, fechaFin));
		}

		resultadoHtml.append("<br>");

		// Trámites no telemáticos
		if (!esTextoPlano) {
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasCTIT(false, fechaInicio, fechaFin));
		} else {
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasCTIT(false, fechaInicio, fechaFin));
		}

		if (!esTextoPlano) {
			resultadoHtml.append("</span>");
		}

		resultadoHtml.append("<br></br><br></br>");

		// Mantis 21977. Jorge Fernandez. Custodia de datos del Correo de Estadisticas con totales de CTIT
		String nombreFichero = ConstantesEstadisticas.FICHEROHTML + "_" 
					+ fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();

		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT_TOTALES,
				fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, resultadoHtml.toString().getBytes());

		return resultadoHtml;
	}

	/**
	 * 
	 * @param esTextoPlano
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	private StringBuffer datosEstadisticasMATE(boolean esTextoPlano) throws OegamExcepcion, ParseException {

		StringBuffer resultadoHtml = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";

		Fecha fechaFin = getFechaFinEstadisticas();
		Fecha fechaInicio = getFechaInicioEstadisticas(fechaFin);

		// Encabezamiento del e-mail
		if (!esTextoPlano) {
			resultadoHtml.append("<br><span style=\"font-size:12pt;font-family:Tunga;margin-left:20px;\">" );
		}

		resultadoHtml.append("<br>Estadísticas MATE en Oegam. Mensaje generado el ")
						.append(utilesFecha.formatoFecha(sFormatoFecha,new Date()))
						.append(".<br></br>Consulta realizada para el período del ")
						.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaInicio.getFecha()))
						.append(" al ")
						.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()))
						.append(".<br></br>");

		// Matriculaciones por delegaciones
		if (!esTextoPlano){
			resultadoHtml.append("</span><span style=\"font-size:12pt;font-family:Tunga;\">");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("TOTALES",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("28",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("19",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("40",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("13",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("16",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("05",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaHTMLResultadoEstadisticasMATE("TODASDELEGACIONES",fechaInicio,fechaFin));
			resultadoHtml.append("</span><br></br><br></br>");
		}else{
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("TOTALES",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("28",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("19",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("40",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("13",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("16",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("05",fechaInicio,fechaFin));
			resultadoHtml.append("<br>");
			resultadoHtml.append(cadenaTextoPlanoResultadoEstadisticasMATE("TODASDELEGACIONES",fechaInicio,fechaFin));
		}

		// Mantis 20706. David Sierra. Custodia de datos del Correo de Estadisticas de Matw
		String nombreFichero = ConstantesEstadisticas.FICHEROHTML + "_" + fechaFin.getDia() + fechaFin.getMes() + fechaFin.getAnio();
		gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.MATE, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, resultadoHtml.toString().getBytes());

		return resultadoHtml;
	}

	/**
	 * Devuelve la fecha de inicio a partir de una property. Si está vacía
	 * se toma la fecha actual. 
	 * @return
	 * @throws OegamExcepcion 
	 */
	private Fecha getFechaFinEstadisticas() throws OegamExcepcion {

		Fecha fechaFin;

		// Se obtiene la fecha de fin del intervalo a partir de fichero de properties
		String sFechaFin = gestorPropiedades.valorPropertie("estadisticas.fecha.fin");

		// si la property tiene datos se formatea a objeto Fecha
		if (sFechaFin != null && sFechaFin.trim().length() > 0) {
			try {
				fechaFin = utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(sFechaFin.trim()));
			} catch (Exception e) {
				fechaFin = utilesFecha.getFechaActual();
			}
		} else {
			// Si la property esta vacía se utiliza la fecha actual
			fechaFin = utilesFecha.getFechaActual();
		}
		return fechaFin;

	}

	/**
	 * Devuelve la fecha de inicio a partir de una property o de la fecha de fin. Si la
	 * property está vacía se calcula el día laborable anterior a la fecha fin.
	 * @param fechaFin
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	private Fecha getFechaInicioEstadisticas(Fecha fechaFin) throws OegamExcepcion, ParseException {

		Fecha fechaInicio;

		// Se obtiene la fecha de fin del intervalo a partir de fichero de
		// properties
		String sFechaInicio = gestorPropiedades.valorPropertie("estadisticas.fecha.inicio");

		// si la property tiene datos se formatea a objeto Fecha
		if (sFechaInicio != null && sFechaInicio.trim().length() > 0) {
			try {
				fechaInicio = utilesFecha.getFechaFracionada(utilesFecha.DDMMAAAAToTimestamp(sFechaInicio.trim()));
			} catch (Exception e) {
				fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
			}
		} else {
			// Si la property esta vacía calcula el dia laborable anterior a la fecha fin
			fechaInicio = utilesFecha.getPrimerLaborableAnterior(fechaFin);
		}

		return fechaInicio;

	}

	/**
	 * Método auxiliar para el cálculo de estadísticas CTIT. Accede a base de datos y forma cadena con formato HTML 
	 * para mostrar en el e-mail. Incluye tablas.
	 * @param isTelematico Indica si la cadena a obtener es para operaciones telemáticas o no
	 * @param fechaInicio Fecha inicio de la consulta
	 * @param fechaFin Fecha fin de la consulta 
	 * @return
	 * @throws OegamExcepcion 
	 * @throws ParseException 
	 * @throws Throwable
	 */
	private StringBuffer cadenaHTMLResultadoEstadisticasCTIT(boolean esTelematico, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {

		// Se 'inicializa' cadena para informar resultado
		StringBuffer cadenaResultado = new StringBuffer("<b><u>");

		// Se obtienen los literales a partir de fichero de properties
		if (esTelematico) {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.telematicas"));
		} else {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.no.telematicas"));
		}

		cadenaResultado.append(":</u></b><br></br>Total: ");

		// Se obtiene el resultado de la consulta
		List<TramiteTrafico> listaTotalesCTIT = getModeloTrafico().obtenerTotalesTramitesCTIT(esTelematico, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
		List<TotalesTramitesCTITBean> sumarioTramitesTelematicos = new ArrayList<TotalesTramitesCTITBean>();
		Iterator<?> iterTotales = listaTotalesCTIT.iterator();
		while(iterTotales.hasNext()){
			Object[] obj = (Object[]) iterTotales.next();
			TotalesTramitesCTITBean tramites = new TotalesTramitesCTITBean();
			tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
			if(obj[2].toString().equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())){
				Integer tipoTransferencia = new Integer(obj[1]!=null ? obj[1].toString() : BigDecimal.ZERO.toString());
				switch(tipoTransferencia){
					case 1: tramites.setTipoTransferencia(TipoTransferencia.tipo1.getNombreEnum()); break;
					case 2: tramites.setTipoTransferencia(TipoTransferencia.tipo2.getNombreEnum()); break;
					case 3: tramites.setTipoTransferencia(TipoTransferencia.tipo3.getNombreEnum()); break;
					case 4: tramites.setTipoTransferencia(TipoTransferencia.tipo4.getNombreEnum()); break;
					case 5: tramites.setTipoTransferencia(TipoTransferencia.tipo5.getNombreEnum()); break;
					default: tramites.setTipoTransferencia("No marcada"); break;
				}
			} else {
				tramites.setTipoTransferencia("Bajas por transferencia temporal");
			}
			sumarioTramitesTelematicos.add(tramites);
		}

		// Totales - trámites telemáticos
		StringBuffer listado = new StringBuffer();
		Integer totalRegistros = 0;

		// Se recuperan los datos y se formatea en HTML la tabla
		listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
		for (TotalesTramitesCTITBean sumario : sumarioTramitesTelematicos) {
			listado.append("<tr> <td>").append(sumario.getNumTransferencias())
					.append("</td><td>").append(sumario.getTipoTransferencia())
					.append("</td> </tr>");
			totalRegistros += sumario.getNumTransferencias().intValue();
		}
		listado.append("</span>");

		cadenaResultado.append(totalRegistros);

		if (totalRegistros>0){//Si hay datos se pintan las dos tablas, sumario y detalle

			// Sumario - tabla para presentación de datos

			// Cabecera
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de transferencia</th> </tr> </span> ");
			// fin cabecera

			cadenaResultado.append(listado).append("</table><br>Transferencias por colegiado:<br>");

			// Detalle - telemáticos
			// Se obtiene el resultado de la consulta
			List<TramiteTrafico> listaTramitesTelematicos = getModeloTrafico().obtenerDetalleTramitesCTIT(esTelematico, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
			List<DetalleTramitesCTITBean> detalleTramitesTelematicos = new ArrayList<DetalleTramitesCTITBean>();
			Iterator<?> iterTramites = listaTramitesTelematicos.iterator();
			while(iterTramites.hasNext()){
				Object[] obj = (Object[]) iterTramites.next();
				DetalleTramitesCTITBean tramites = new DetalleTramitesCTITBean();
				tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
				tramites.setNumColegiado(new BigDecimal(obj[1].toString()));
				Integer estadoTramite = new Integer(obj[2]!=null ? obj[2].toString() : BigDecimal.ZERO.toString());
				switch(estadoTramite){
					case 12: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_Telematicamente.getNombreEnum()); break;
					case 13: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum()); break;
					case 14: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum()); break;
					default: tramites.setEstadoTransferencia("No definido"); break;
				}
				detalleTramitesTelematicos.add(tramites);
			}

			// Se reinician las variables para reutilizarlas
			listado.delete(0, listado.length());

			listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");
			for (DetalleTramitesCTITBean detalle : detalleTramitesTelematicos) {
				listado.append("<tr> <td>").append(detalle.getNumColegiado())
						.append("</td> <td>")
						.append(detalle.getNumTransferencias())
						.append("</td> <td>")
						.append(detalle.getEstadoTransferencia())
						.append("</td> </tr>");
			}
			listado.append("</span>");

			// Tabla para presentación de datos - detalle
				// Cabecera
			cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de colegiado</th> <th>Número de trámites</th> <th>Estado del trámite</th> </tr> </span> ");
				// fin cabecera

			cadenaResultado.append(listado).append("</table>");// fin tabla

			// Obtengo los totales
			esTelematico = false;
			if(!esTelematico){

				List<Long> listaExpedientes = getModeloTrafico().obtenerListadoNumerosExpedienteCTIT(esTelematico, fechaInicio.getDate(), fechaFin.getDate());

				/* ************************************ ANOTACIONES GEST ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes con Anotaciones en GEST:<br>");

					// Se obtiene el resultado de la consulta
					int detalleTramitesCTITAnotacionesGest = getModeloTrafico().obtenerDetalleTramitesCTITAnotacionesGest(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
							.append(detalleTramitesCTITAnotacionesGest)
							.append("</td> <td>Expedientes con Anotaciones en GEST</td> </tr></span>");

					cadenaResultado.append(listado);
					// fin tabla
					cadenaResultado.append("</table><p><em>Total expedientes con Anotaciones en GEST</em>:&nbsp;<strong>")
							.append(detalleTramitesCTITAnotacionesGest)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITAnotacionesGest = getModeloTrafico().obtenerListaExpedientesCTITAnotacionesGest(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITAnotacionesGest);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de Anotaciones en GEST", e);
				}

				/* ************************************ CARPETA TIPO FUSIÓN ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Vehículo</th> </tr> </span> <br>Expedientes de Tipo Fusi&oacute;n:<br>");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITCarpetaFusion = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaFusion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					Iterator<?> it = detalleTramitesCTITCarpetaFusion.iterator();
					int sumaTipoFusion = 0;
					while(it.hasNext()){
						Object[] detalle = (Object[]) it.next();
						sumaTipoFusion += (Integer) detalle[0];
						listado.append("<tr> <td>")
									.append(detalle[0].toString())
									.append("</td> <td>")
									.append(detalle[1].toString())
									.append("</td> </tr>"
						);
					}

					listado.append("</span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes Tipo Fusion</em>:&nbsp;<strong>")
										.append(sumaTipoFusion)
										.append("</strong></p>");

					List<Long> listaExpedientesCTITTipoFusion = getModeloTrafico().obtenerListaExpedientesCTITCarpetaFusion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoFusion);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tipo Fusion", e);
				}

				/* ************************************ CARPETA TIPO I ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Vehículo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes de Tipo I:<br>");

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITCarpetaI = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaI(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					Iterator<?> it = detalleTramitesCTITCarpetaI.iterator();
					int sumaTipoI = 0;
					while(it.hasNext()){
						Object[] detalle = (Object[]) it.next();
						sumaTipoI += (Integer) detalle[0];
						listado.append("<tr> <td>")
								.append(detalle[0].toString())
								.append("</td> <td>")
								.append(detalle[1].toString())
								.append("</td> </tr>"
						);
					}

					listado.append("</span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes Tipo I</em>:&nbsp;<strong>").append(sumaTipoI).append("</strong></p>");

					List<Long> listaExpedientesCTITTipoI = getModeloTrafico().obtenerListaExpedientesCTITCarpetaI(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoI);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tipo I",e);
				}

				/* ************************************ CARPETA TIPO B ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes de Tipo B:<br>");

					// Se obtiene el resultado de la consulta
					int detalleTramitesCTITCarpetaB = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaB(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(detalleTramitesCTITCarpetaB)
								.append("</td> <td>Expedientes Tipo B</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>");
					// fin tabla
					cadenaResultado.append("<p><em>Total expedientes Tipo B</em>:&nbsp;<strong>").append(detalleTramitesCTITCarpetaB).append("</strong></p>");

					List<Long> listaExpedientesCTITTipoB = getModeloTrafico().obtenerListaExpedientesCTITCarpetaB(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoB);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes tipo B",e);
				}

				/* ************************************ VEHÍCULOS AGRÍCOLAS ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo de Veh&iacute;culo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes de Veh&iacute;culos Agr&iacute;colas:<br>");

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITVehiculosAgricolas = getModeloTrafico().obtenerDetalleTramitesCTITVehiculosAgricolas(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					Iterator<?> iterAgricolas = detalleTramitesCTITVehiculosAgricolas.iterator();
					int sumaAgricolas = 0;
					while(iterAgricolas.hasNext()){
						Object[] detalle = (Object[]) iterAgricolas.next();
						sumaAgricolas += (Integer) detalle[0];
						listado.append("<tr> <td>")
									.append(detalle[0].toString())
									.append("</td> <td>")
									.append(detalle[1].toString())
									.append("</td> </tr>"
						);
					}
					listado.append("</span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes de veh&iacute;culos agr&iacute;colas</em>:&nbsp;<strong>").append(sumaAgricolas).append("</strong></p>");

					List<Long> listaExpedientesCTITVehiculosAgricolas = getModeloTrafico().obtenerListaExpedientesCTITVehiculosAgricolas(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITVehiculosAgricolas);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de Veh&iacute;culos agr&iacute;colas", e);
				}

				/* ************************************ ADJUDICACIÓN JUDICIAL ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes por Modo de Adjudicaci&oacute;n judicial o subasta:<br>");

					// Se obtiene el resultado de la consulta
					int detalleTramitesCTITJudicialSubasta = getModeloTrafico().obtenerDetalleTramitesCTITJudicialSubasta(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(detalleTramitesCTITJudicialSubasta)
								.append("</td> <td>Adjudicaci&oacute;n Judicial o Subasta</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table><p><em>Total Modo de Adjudicaci&oacute;n judicial o subasta:</em>&nbsp;<strong>")
										.append(detalleTramitesCTITJudicialSubasta)
										.append("</strong></p>");

					List<Long> listaExpedientesCTITJudicialSubasta = getModeloTrafico().obtenerListaExpedientesCTITJudicialSubasta(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITJudicialSubasta);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de Adjudicaci&oacute;n Judicial / Subasta", e);
				}

				/* ************************************ SIN CET, NI FACTURA ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes sin CET, ni factura:<br>");

					// Se obtiene el resultado de la consulta
					int obtenerDetalleTramitesCTITSinCETNiFactura = getModeloTrafico().obtenerDetalleTramitesCTITSinCETNiFactura(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(obtenerDetalleTramitesCTITSinCETNiFactura)
								.append("</td> <td>Sin CET, ni factura</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes sin CET, ni factura:</em>&nbsp;<strong>").append(obtenerDetalleTramitesCTITSinCETNiFactura).append("</strong></p>");

					List<Long> listaExpedientesCTITSinCETNiFactura = getModeloTrafico().obtenerListaExpedientesCTITSinCETNiFactura(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITSinCETNiFactura);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes sin CET, ni factura", e);
				}

				/* ************************************ TRAMITABLE EN JEFATURA ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes Tramitables en Jefatura:<br>");

					// Se obtiene el resultado de la consulta
					int obtenerDetalleTramitesCTITEmbargoPrecinto = getModeloTrafico().obtenerDetalleTramitesCTITEmbargoPrecinto(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(obtenerDetalleTramitesCTITEmbargoPrecinto)
								.append("</td> <td>Tramitables en Jefatura</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes Tramitables en Jefatura</em>:&nbsp;<strong>").append(obtenerDetalleTramitesCTITEmbargoPrecinto).append("</strong></p>");

					List<Long> listaExpedientesCTITEmbargoPrecinto = getModeloTrafico().obtenerListaExpedientesCTITEmbargoPrecinto(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITEmbargoPrecinto);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Tramitables en Jefatura", e);
				}

				/* ************************************ ERROR EN JEFATURA ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes con Error en Jefatura:<br>");

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					// Se obtiene el resultado de la consulta
					List<Object> detalleTramitesCTITErrorJefatura = getModeloTrafico().obtenerDetalleTramitesCTITErrorJefatura(fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					int sumaErrorJefatura = 0;
					Iterator<?> itErrorJefatura = detalleTramitesCTITErrorJefatura.iterator();
					while(itErrorJefatura.hasNext()){
						Object[] detalle = (Object[]) itErrorJefatura.next();
						sumaErrorJefatura += new BigDecimal(detalle[0].toString()).intValue();
						listado.append("<tr> <td>")
									.append(detalle[0].toString())
									.append("</td> <td>")
									.append(detalle[1].toString())
									.append("</td> </tr>");
					}

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes con Error en Jefatura</em>:&nbsp;<strong>").append(sumaErrorJefatura).append("</strong></p>");

					List<Object> listaExpedientesCTITErrorJefatura = getModeloTrafico().obtenerListaExpedientesCTITErrorJefatura(fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					List<Long> listaCTITErrorJefaturaLong = new ArrayList<Long>();
					Iterator<?> itErrorJefatura2 = listaExpedientesCTITErrorJefatura.iterator();
					while(itErrorJefatura2.hasNext()){
						BigDecimal detalle = (BigDecimal) itErrorJefatura2.next();
						listaCTITErrorJefaturaLong.add(Long.valueOf(detalle.toString()));
					}
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaCTITErrorJefaturaLong);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes con Error en Jefatura", e);
				}

				/* ************************************ FINALIZADOS ERROR -> FINALIZADOS PDF ************************************ */

				// Se obtiene el resultado de la consulta
				List<EstadoTramiteTrafico> transicionEstados;
				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<br>Expedientes que han pasado de Validado Telemáticamente a Finalizado PDF, pasando por Finalizado con Error:<br>");

					transicionEstados = new ArrayList<EstadoTramiteTrafico>();
					transicionEstados.add(EstadoTramiteTrafico.Pendiente_Envio);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_Con_Error);
					transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);
					int detalleTramitesCTITValidadosErrorFinalizados = getModeloTrafico().obtenerDetalleTramitesCTITEvolucion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(detalleTramitesCTITValidadosErrorFinalizados)
								.append("</td> <td>Expedientes</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla
					cadenaResultado.append("<p><em>Total expedientes que han pasado de Validado Telemáticamente a Finalizado PDF, pasando por Finalizado con Error</em>:&nbsp;<strong>").append(detalleTramitesCTITValidadosErrorFinalizados).append("</strong></p>");

					List<Long> listaExpedientesCTITValidadosErrorFinalizados = getModeloTrafico().obtenerListaExpedientesCTITEvolucion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosErrorFinalizados);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes Finalizados PDF que pasan por Finalizados con Error", e);
				}

				/* ************************************ VALIDADOS TELEMATICAMENTE -> FINALIZADOS PDF ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de trámites</th> <th>Tipo</th> </tr> </span> ");
					// fin cabecera

					cadenaResultado.append("<p>Expedientes que pasan de Validado Telemáticamente a Finalizado PDF:</p>");

					// Se obtiene el resultado de la consulta
					transicionEstados = new ArrayList<EstadoTramiteTrafico>();
					transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);
					int detalleTramitesCTITValidadosAFinalizados = getModeloTrafico().obtenerDetalleTramitesCTITEvolucion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<span style=\"font-size:10pt;font-family:Tunga;\">");

					listado.append("<tr> <td>")
								.append(detalleTramitesCTITValidadosAFinalizados)
								.append("</td> <td>Expedientes</td> </tr></span>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>"); // fin tabla

					List<Long> listaExpedientesCTITValidadosAFinalizados = getModeloTrafico().obtenerListaExpedientesCTITEvolucion(esTelematico, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);				
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosAFinalizados);

					// Loggeo la lista de expedientes de este tipo
					String expValFin = "";
					for(int i = 0; i < listaExpedientesCTITValidadosAFinalizados.size(); i++){
						expValFin += listaExpedientesCTITValidadosAFinalizados.get(i).toString() + " - ";
					}
					log.info(expValFin);

					if(listaExpedientesCTITValidadosAFinalizados!=null && listaExpedientesCTITValidadosAFinalizados.size() > BigDecimal.ZERO.intValue()){
						List<Object> listaTramitesGestorCTITValidadosAFinalizados = getModeloTrafico().obtenerListaTramitesGestorSobreExp(listaExpedientesCTITValidadosAFinalizados);
						listado.append("<p>Listado de Gestorías con Trámites que pasan de Validado Telemáticamente a Finalizado PDF</p><table border='1'><tr> <td>N&uacute;mero de tr&aacute;mites</td> <td>Gestor&iacute;a</td> </tr>");
						Iterator<?> iterGestorias = listaTramitesGestorCTITValidadosAFinalizados.iterator();
						while(iterGestorias.hasNext()){
							Object[] detalle = (Object[]) iterGestorias.next();
							listado.append("<tr> <td>")
										.append(detalle[0].toString())
										.append("</td> <td>")
										.append(detalle[1].toString())
										.append("</td> </tr>");
						}
						listado.append("</table>");

						cadenaResultado.append(listado);
					}
					cadenaResultado.append("<p><em>Total expedientes que pasan de Validado Telemáticamente a Finalizado PDF</em>:&nbsp;<strong>").append(detalleTramitesCTITValidadosAFinalizados).append("</strong></p>");
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes que pasan directamente de Validado PDF a Finalizado PDF", e);
				}

				/* ************************************ EXPEDIENTES PDF POR MOTIVO INDETERMINADO ************************************ */

				try {
					// Sumario - tabla para presentación de datos
					cadenaResultado.append("<p>Expedientes PDF por motivo indeterminado:</p>");

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					if(listaExpedientes!=null && listaExpedientes.size() > BigDecimal.ZERO.intValue()){

						listado.append("<p>Listado de Gestorías con Trámites PDF por motivo indeterminado</p>");

						List<Object> listaTramitesGestorIndefinidos = getModeloTrafico().obtenerListaTramitesGestorSobreExp(listaExpedientes);
						listado.append("<table border='1'><tr> <td>N&uacute;mero de tr&aacute;mites</td> <td>Gestor&iacute;a</td> </tr>");
						Iterator<?> iterIndefinidos = listaTramitesGestorIndefinidos.iterator();
						while (iterIndefinidos.hasNext()) {
							Object[] detalle = (Object[]) iterIndefinidos.next();
							listado.append("<tr> <td>")
										.append(detalle[0].toString())
										.append("</td> <td>")
										.append(detalle[1].toString())
										.append("</td> </tr>");
						}
						listado.append("</table>");

						// Loggeo la lista de expedientes de este tipo
						String expIndefinidos = "";
						for(int i = 0; i < listaExpedientes.size(); i++){
							expIndefinidos += listaExpedientes.get(i).toString() + " - ";
						}
						log.info(expIndefinidos);

						cadenaResultado.append(listado);
						cadenaResultado.append("</table>"); // fin tabla
					}
					cadenaResultado.append("<p><em>Total expedientes indeterminados</em>:&nbsp;<strong>").append(listaExpedientes.size()).append("</strong></p>");
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadística de expedientes en Finalizado PDF por causa indeterminada", e);
				}

			}
		}

		return cadenaResultado;
	}

	/**
	 * Método para quitar elementos de una sublista que estén duplicados en la lista de expedientes original
	 * @param lista
	 * @param subLista
	 * @return List<String> lista
	 */
	private List<Long> quitarDuplicadosLista(List<Long> lista, List<Long> subLista){
		for(int i = 0; i < subLista.size(); i++){
			for(int j = 0; j < lista.size(); j++){
				if(lista.get(j).equals(subLista.get(i))){
					lista.remove(j);
					j--;
					continue;
				}
			}
		}
		return lista;
	}

	/**
	 * Método auxiliar para el cálculo de estadísticas MATE. Accede a base de datos y forma cadena con formato HTML
	 * para mostrar en el e-mail. Incluye tablas.
	 * @param idProvincia Indica si la Provincia de la que obtener las Estadisticas
	 * @param fechaInicio Fecha inicio de la consulta
	 * @param fechaFin Fecha fin de la consulta 
	 * @return
	 * @throws ParseException
	 * @throws Throwable
	 */
	private StringBuffer cadenaHTMLResultadoEstadisticasMATE(String idProvincia, Fecha fechaInicio, Fecha fechaFin) throws ParseException {

		// Se 'inicializa' cadena para informar resultado
		StringBuffer cadenaResultado = new StringBuffer("<b><u>");
		String jefatura = "";

		if ("TOTALES".equals(idProvincia)) {
			cadenaResultado.append("Total Matriculaciones Oegam :");
		} else if (Provincias.Madrid.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Madrid :");
		} else if (Provincias.Segovia.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Segovia :");
			jefatura = "SG";
		} else if (Provincias.Avila.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Ávila :");
			jefatura = "AV";
		} else if (Provincias.Cuenca.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Cuenca :");
			jefatura = "CU";
		} else if (Provincias.Ciudad_Real.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Ciudad Real :");
			jefatura = "CR";
		} else if (Provincias.Guadalajara.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Guadalajara :");
			jefatura = "GU";
		} else if ("TODASDELEGACIONES".equals(idProvincia)) {
			cadenaResultado.append("Total Delegaciones (Excluyendo Madrid):");
		}

		cadenaResultado.append("</u></b><br></br>");

		// Se obtienen los totales de la consultas
		int sumarioMateTelProvincia = getModeloTrafico().obtenerTotalesTramitesMATE(true,idProvincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
		int sumarioMateNoTelProvincia = getModeloTrafico().obtenerTotalesTramitesMATE(false,idProvincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

		StringBuffer listado = new StringBuffer();

		//Imprimimos totales
		cadenaResultado.append("<tr> <td> Total: ").append((sumarioMateTelProvincia + sumarioMateNoTelProvincia)).append("</td> </tr>");

		// Se recuperan los datos y se formatea en HTML la tabla
		listado.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
				.append(sumarioMateTelProvincia)
				.append("</td><td>Telemáticas</td> </tr><tr> <td>")
				.append(sumarioMateNoTelProvincia)
				.append("</td><td>No Telemáticas</td> </tr></span>");

		//cadenaResultado.append(totalRegistros);

		// Sumario - tabla para presentación de datos
		// Cabecera
		cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Número de Matriculaciones</th> <th>Tipo </th> </tr> </span> ");
		// fin cabecera

		listado.append("</span>");
		cadenaResultado.append(listado).append("</table>");// fin tabla

		//Desglosamos para Madrid por Jefaturas, Combustible, Tipos de Vehiculos..
		if (Provincias.Madrid.getValorEnum().toString().equals(idProvincia)){

			//Desglosamos para Madrid las telematicas si hay alguna
			if (sumarioMateTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>");
				StringBuffer desgloseTelematicas = new StringBuffer("<u>Desglose Telemáticas :");
				cadenaResultado.append(desgloseTelematicas);

				//Desglosamos las Telematicas por Jefatura

				List<Object[]> detalleTramitesMatrTelJ = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "JEFATURA", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> itera = detalleTramitesMatrTelJ.iterator();

				StringBuffer listadoTelJefatura = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones Telemáticas por Jefatura: </td> </tr>");
				int madridTelematicas = 0;
				int alcorconTelematicas = 0;
				int alcalaconTelematicas = 0;
				while ( itera.hasNext() ) {

					Object[] temp = itera.next();
					if(JEFATURA_PROVINCIAL_MADRID.equalsIgnoreCase((temp[0].toString()))){
						madridTelematicas = Integer.parseInt(temp[1].toString());
					} else if(JEFATURA_PROVINCIAL_ALCORCON.equalsIgnoreCase((temp[0].toString()))) {
						alcorconTelematicas = Integer.parseInt(temp[1].toString());
					} else if(JEFATURA_PROVINCIAL_ALCALA.equalsIgnoreCase((temp[0].toString()))) {
						alcalaconTelematicas = Integer.parseInt(temp[1].toString());
					}
				}

				listadoTelJefatura.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
									.append(madridTelematicas)
									.append("</td><td>MADRID</td> </tr></span><span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
									.append(alcorconTelematicas)
									.append("</td><td>ALCORCON</td> </tr></span><span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
									.append(alcalaconTelematicas)
									.append("</td><td>ALCALA</td> </tr></span>");;

				// Sumario - tabla para presentación de datos
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Jefatura </th> </tr> </span> ");
				// fin cabecera

				listadoTelJefatura.append("</span>");
				cadenaResultado.append(listadoTelJefatura).append("</table>");
				// fin tabla

				//Desglosamos las Telematicas por Tipo de Vehiculo 
				List<Object[]> detalleTramitesMatrTelVehicM = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "TIPOVEHICULO", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);
				//Desglosamos las Telematicas por Carburante
				List<Object[]> detalleTramitesMatrTelCarbM = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "CARBURANTE", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> iter = detalleTramitesMatrTelVehicM.iterator();

				StringBuffer listadoTelVehiculo = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones Telemáticas por tipo de Vehículo: </td> </tr>");
				while ( iter.hasNext() ) {

					Object[] temp = iter.next();

					listadoTelVehiculo.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
										.append(temp[1].toString())
										.append("</td><td>")
										.append(temp[0].toString())
										.append("</td> </tr></span>");
				}

				// Sumario - tabla para presentación de datos
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Tipo de Vehiculo </th> </tr> </span> ");
				// fin cabecera

				listadoTelVehiculo.append("</span>");
				cadenaResultado.append(listadoTelVehiculo).append("</table>");// fin tabla

				//Pintamos agrupados por combustible
				Iterator<Object[]> iterb = detalleTramitesMatrTelCarbM.iterator();
				StringBuffer listadoTelComb = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones Telemáticas por tipo de Combustible: </td> </tr>");
				while ( iterb.hasNext() ) {

					Object[] tempb = iterb.next();

					ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
					listadoTelComb.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
										.append(tempb[1].toString())
										.append("</td><td>")
										.append(convierteCodigoALiteral.getLiteral(tempb[0].toString(), new BigDecimal(5)))
										.append("</td> </tr></span>");

				}

				// Sumario - tabla para presentación de datos
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Total</th> <th>Tipo de Combustible </th> </tr> </span> ");
				// fin cabecera

				listadoTelComb.append("</span>");
				cadenaResultado.append(listadoTelComb).append("</table>");// fin tabla	

			}

			//Desglosamos para Madrid las No telematicas si hay alguna
			if (sumarioMateNoTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>");
				StringBuffer desgloseNoTelematicas = new StringBuffer("<u>");
				desgloseNoTelematicas.append("Desglose No Telemáticas :");
				cadenaResultado.append(desgloseNoTelematicas);

				//Totales de Ficha Tecnica A o C por Jefatura
				String totalTramitesMatrPdfItvM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfItvA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M1", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfItvAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M2", fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfItvM = Integer.valueOf(totalTramitesMatrPdfItvM);
				int numTramitesMatrPdfItvA = Integer.valueOf(totalTramitesMatrPdfItvA);
				int numTramitesMatrPdfItvAlc = Integer.valueOf(totalTramitesMatrPdfItvAlc);

				//Totales Que no son ni Ficha Tecnica A ni C por Jefatura
				String totalTramitesMatrPdfNoItvM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfNoItvA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M1", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfNoItvAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M2", fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfNoItvM = Integer.valueOf(totalTramitesMatrPdfNoItvM);
				int numTramitesMatrPdfNoItvA = Integer.valueOf(totalTramitesMatrPdfNoItvA);
				int numTramitesMatrPdfNoItvAlc = Integer.valueOf(totalTramitesMatrPdfNoItvAlc);

				StringBuffer listadoITV = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas por Jefatura: </td> </tr>");
				listadoITV.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>Ficha Tecnica Tipo A o C</td> <td>")
								.append(numTramitesMatrPdfItvM)
								.append("</td> <td>")
								.append(numTramitesMatrPdfItvA)
								.append("</td> <td>")
								.append(numTramitesMatrPdfItvAlc)
								.append("</td> <td>")
								.append((numTramitesMatrPdfItvM + numTramitesMatrPdfItvA + numTramitesMatrPdfItvAlc))
								.append("</td> </tr><tr> <td> Vehículos No Admitidos </td> <td>")
								.append(numTramitesMatrPdfNoItvM)
								.append("</td> <td>")
								.append(numTramitesMatrPdfNoItvA)
								.append("</td> <td>")
								.append(numTramitesMatrPdfNoItvAlc)
								.append("</td> <td>")
								.append((numTramitesMatrPdfNoItvM + numTramitesMatrPdfNoItvA + numTramitesMatrPdfNoItvAlc))
								.append("</td> </tr></span>");

				// Tabla para presentación de datos - detalle
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Tipo </th> <th>A.SORIA</th> <th>ALCORCON</th> <th>ALCALA</th> <th>SUBTOTAL</th></tr> </span> ");

				listadoITV.append("</span>");
				cadenaResultado.append(listadoITV).append("</table>");// fin tabla

				//Agtupo por Jefatura para cada tipo

				// Desglosamos para la Jefatura de Madrid las No telematicas por Tipo de Vehiculo si existen

				if (numTramitesMatrPdfNoItvM>0){
					List<TramiteTrafico> detalleTramitesMatrPdfVehicM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28", "M",fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterc = detalleTramitesMatrPdfVehicM.iterator();

					StringBuffer listadoNoTelVehiculoM = new StringBuffer();
					cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos por Jefatura : </td> </tr>");
					while (iterc.hasNext()) {
						Object[] temp = (Object[]) iterc.next();

						listadoNoTelVehiculoM.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
												.append(temp[1].toString())
												.append("</td><td>")
												.append(temp[0].toString())
												.append("</td> </tr></span>");
					}

					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>A.Soria</th> <th>Tipo de Vehiculo </th> </tr> </span> ");
					// fin cabecera

					listadoNoTelVehiculoM.append("</span>");
					cadenaResultado.append(listadoNoTelVehiculoM).append("</table>");// fin tabla

					}

					// Desglosamos para la Jefatura de Alcorcon las No telematicas por Tipo de Vehiculo si existen
					if (numTramitesMatrPdfNoItvA>0){
						List<TramiteTrafico> detalleTramitesMatrPdfVehicA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28","M1",fechaInicio.getTimestamp(), fechaFin.getTimestamp());
						Iterator<?> iterd = detalleTramitesMatrPdfVehicA.iterator();

						StringBuffer listadoNoTelVehiculoA = new StringBuffer();
						cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos por Jefatura: </td> </tr>");
						while (iterd.hasNext()) {

							Object[] temp = (Object[]) iterd.next();

							listadoNoTelVehiculoA.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
													.append(temp[1].toString())
													.append("</td><td>")
													.append(temp[0].toString())
													.append("</td> </tr></span>");
						}

						// Sumario - tabla para presentación de datos
						// Cabecera
						cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Alcorcon</th> <th>Tipo de Vehiculo </th> </tr> </span> ");
						// fin cabecera

						listadoNoTelVehiculoA.append("</span>");
						cadenaResultado.append(listadoNoTelVehiculoA).append("</table>");// fin tabla
					}
					// Desglosamos para la Jefatura de Alcala las No telematicas por Tipo de Vehiculo si existen
					if (numTramitesMatrPdfNoItvA>0){
						List<TramiteTrafico> detalleTramitesMatrPdfVehicAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28","M2",fechaInicio.getTimestamp(), fechaFin.getTimestamp());
						Iterator<?> iterd = detalleTramitesMatrPdfVehicAlc.iterator();

						StringBuffer listadoNoTelVehiculoAlc = new StringBuffer();
						cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos por Jefatura: </td> </tr>");
						while ( iterd.hasNext() ) {
							Object[] temp = (Object[]) iterd.next();

							listadoNoTelVehiculoAlc.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
								.append(temp[1].toString())
								.append("</td><td>")
								.append(temp[0].toString())
								.append("</td> </tr></span>");
						}

						// Sumario - tabla para presentación de datos
						// Cabecera
						cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Alcala</th> <th>Tipo de Vehiculo </th> </tr> </span> ");
						// fin cabecera

						listadoNoTelVehiculoAlc.append("</span>");
						cadenaResultado.append(listadoNoTelVehiculoAlc).append("</table>");// fin tabla
					}
			}
		} else if ((!"TOTALES".equals(idProvincia)) && (!"TODASDELEGACIONES".equals(idProvincia))){

			//Desglosamos para las que no son Madrid las telematicas si hay alguna
			if (sumarioMateTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>");
				StringBuffer desgloseTelematicas = new StringBuffer("<u>");
				desgloseTelematicas.append("Desglose Telemáticas :");
				cadenaResultado.append(desgloseTelematicas);

				//Desglosamos las Telematicas por Tipo de Vehiculo 
				List<Object[]> detalleTramitesMatrTelVehic = getModeloTrafico().obtenerTramitesMATETelVehiculos(idProvincia, "TIPOVEHICULO", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);
				//Desglosamos las Telematicas por Carburante
				List<Object[]> detalleTramitesMatrTelCarb = getModeloTrafico().obtenerTramitesMATETelVehiculos(idProvincia, "CARBURANTE", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> iter = detalleTramitesMatrTelVehic.iterator();

				StringBuffer listadoTelVehiculo = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones Telemáticas por tipo de Vehículo: </td> </tr>");
				while ( iter.hasNext() ) {
					Object[] temp = iter.next();

					listadoTelVehiculo.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
										.append(temp[1].toString())
										.append("</td><td>")
										.append(temp[0].toString())
										.append("</td> </tr></span>");
				}

				// Sumario - tabla para presentación de datos
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Tipo de Vehiculo </th> </tr> </span> ");
				// fin cabecera

				listadoTelVehiculo.append("</span>");
				cadenaResultado.append(listadoTelVehiculo).append("</table>");// fin tabla

				//Pintamos agrupados por combustible
				Iterator<Object[]> iterb = detalleTramitesMatrTelCarb.iterator();

				StringBuffer listadoTelComb = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones Telemáticas por tipo de Combustible: </td> </tr>");
				while ( iterb.hasNext() ) {

					Object[] tempb = iterb.next();

					ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
					listadoTelComb.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
										.append(tempb[1].toString())
										.append("</td><td>")
										.append(convierteCodigoALiteral.getLiteral(tempb[0].toString(), new BigDecimal(5)))
										.append("</td> </tr></span>");
				}

				// Sumario - tabla para presentación de datos
				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Tipo de Combustible </th> </tr> </span> ");
				// fin cabecera

				listadoTelComb.append("</span>");
				cadenaResultado.append(listadoTelComb).append("</table>");// fin tabla
			}

			//Desglosamos para las que no son Madrid las No telematicas si hay alguna
			if (sumarioMateNoTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>");
				StringBuffer desgloseNoTelematicas = new StringBuffer("<u>");
				desgloseNoTelematicas.append("Desglose No Telemáticas :");
				cadenaResultado.append(desgloseNoTelematicas);

				//Totales de Ficha Tecnica A o C 
				String totalTramitesMatrPdfItv = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(idProvincia, jefatura, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				//Totales Que no son ni Ficha Tecnica A ni C 
				String totalTramitesMatrPdfNoItv = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(idProvincia, jefatura, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfItv = Integer.valueOf(totalTramitesMatrPdfItv);
				int numTramitesMatrPdfNoItv = Integer.valueOf(totalTramitesMatrPdfNoItv);

				StringBuffer listadoITV = new StringBuffer();
				cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas: </td> </tr>");
				listadoITV.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>Ficha Tecnica Tipo A o C</td> <td>")
								.append(numTramitesMatrPdfItv)
								.append("</td> </tr><tr> <td> Vehículos No Admitidos</td> <td>")
								.append(numTramitesMatrPdfNoItv)
								.append("</td> </tr></span>");

				// Cabecera
				cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Tipo </th> <th>SUBTOTAL</th></tr> </span> ");

				listadoITV.append("</span>");
				cadenaResultado.append(listadoITV).append("</table>");
				// fin tabla

				// Desglosamos las No telematicas por Tipo de Vehiculo si existen

				if (numTramitesMatrPdfNoItv>0){
					List<TramiteTrafico> detalleTramitesMatrPdfVehicM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(idProvincia, jefatura,fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterc = detalleTramitesMatrPdfVehicM.iterator();

					StringBuffer listadoNoTelVehiculo = new StringBuffer();
					cadenaResultado.append("</u></b><br></br><tr> <td> Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos: </td> </tr>");
					while ( iterc.hasNext() ) {
						Object[] temp = (Object[]) iterc.next();

						listadoNoTelVehiculo.append("<span style=\"font-size:10pt;font-family:Tunga;\"><tr> <td>")
												.append(temp[1].toString())
												.append("</td><td>")
												.append(temp[0].toString())
												.append("</td> </tr></span>");
					}

					// Sumario - tabla para presentación de datos
					// Cabecera
					cadenaResultado.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th> Total </th> <th>Tipo de Vehiculo </th> </tr> </span> ");
					// fin cabecera

					listadoNoTelVehiculo.append("</span>");
					cadenaResultado.append(listadoNoTelVehiculo).append("</table>");// fin tabla

				}

			}

		}

		return cadenaResultado;
	}

	/**
	 * Método auxiliar para el cálculo de estadísticas CTIT. Accede a base de datos y forma cadena con formato 
	 * de texto plano para mostrar en el e-mail.
	 * @param isTelematico Indica si la cadena a obtener es para operaciones telemáticas o no
	 * @param fechaInicio Fecha inicio de la consulta
	 * @param fechaFin Fecha fin de la consulta
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	private StringBuffer cadenaTextoPlanoResultadoEstadisticasCTIT(boolean esTelematico, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {

		// Se 'inicializa' cadena para informar resultado
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");

		// Se obtienen los literales a partir de fichero de properties
		if (esTelematico) {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.telematicas"));
		} else {
			cadenaResultado.append(gestorPropiedades.valorPropertie("estadisticas.presentacion.literales.no.telematicas"));
		}

		cadenaResultado.append(":</u></b><br><br>Total: ");

		// Se obtiene el resultado de la consulta
		List<TramiteTrafico> listaTotalesCTIT = getModeloTrafico().obtenerTotalesTramitesCTIT(esTelematico, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
		List<TotalesTramitesCTITBean> sumarioTramitesTelematicos = new ArrayList<TotalesTramitesCTITBean>();
		Iterator<?> iterTotales = listaTotalesCTIT.iterator();
		while(iterTotales.hasNext()){
			Object[] obj = (Object[]) iterTotales.next();
			TotalesTramitesCTITBean tramites = new TotalesTramitesCTITBean();
			tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
			tramites.setTipoTransferencia(obj[1].toString());
			sumarioTramitesTelematicos.add(tramites);
		}

		// Totales - trámites telemáticos
		StringBuffer listado = new StringBuffer();
		Integer totalRegistros = 0;

		// Se recuperan los datos y se formatea en HTML la tabla
		for (TotalesTramitesCTITBean sumario:sumarioTramitesTelematicos){
			listado.append("<br> &nbsp ")
				.append(sumario.getNumTransferencias())
				.append(" &nbsp &nbsp ")
				.append(sumario.getTipoTransferencia());
			totalRegistros += sumario.getNumTransferencias().intValue();
		}

		cadenaResultado.append(totalRegistros)
						.append("<br>");

		if (totalRegistros>0){//Si hay datos se pintan las dos tablas, sumario y detalle

			// Sumario - tabla para presentación de datos

			cadenaResultado.append(listado);

			cadenaResultado.append("<br><br>Transferencias por colegiado:<br><br>");

			// Detalle - telemáticos
			// Se obtiene el resultado de la consulta
			List<DetalleTramitesCTITBean> detalleTramitesTelematicos = new ArrayList<DetalleTramitesCTITBean>();
			Iterator<?> iterTramites = listaTotalesCTIT.iterator();
			while(iterTramites.hasNext()){
				Object[] obj = (Object[]) iterTramites.next();
				DetalleTramitesCTITBean tramites = new DetalleTramitesCTITBean();
				tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
				tramites.setNumColegiado(new BigDecimal(obj[1].toString()));
				tramites.setEstadoTransferencia(obj[2].toString());
				detalleTramitesTelematicos.add(tramites);
			}

			// Se forma la cadena con los datos obtenidos
			for (DetalleTramitesCTITBean detalle:detalleTramitesTelematicos){
				cadenaResultado.append("&nbsp ")
								.append(detalle.getNumColegiado())
								.append("&nbsp &nbsp ")
								.append(detalle.getNumTransferencias())
								.append("&nbsp &nbsp ")
								.append(detalle.getEstadoTransferencia())
								.append("<br>");
			}
		}

		return cadenaResultado;
	}

	/**
	 * Método auxiliar para el cálculo de estadísticas MATE. Accede a base de datos y forma cadena con formato 
	 * de texto plano para mostrar en el e-mail.
	 * @param idProvincia Indica la provincia de la que se quieren obtener las Estadisticas de Mate
	 * @param fechaInicio Fecha inicio de la consulta
	 * @param fechaFin Fecha fin de la consulta 
	 * @return
	 * @throws ParseException 
	 * @throws Throwable
	 */
	private StringBuffer cadenaTextoPlanoResultadoEstadisticasMATE(String idProvincia, Fecha fechaInicio, Fecha fechaFin) throws ParseException {

		// Se 'inicializa' cadena para informar resultado
		StringBuffer cadenaResultado = new StringBuffer("<br><u><b>");
		String jefatura = "";

		if ("TOTALES".equals(idProvincia)) {
			cadenaResultado.append("Total Matriculaciones Oegam :");
		} else if (Provincias.Madrid.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Madrid :");
		} else if (Provincias.Segovia.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Segovia :");
			jefatura = "SG";
		} else if (Provincias.Avila.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Ávila :");
			jefatura = "AV";
		} else if (Provincias.Cuenca.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Cuenca :");
			jefatura = "CU";
		} else if (Provincias.Ciudad_Real.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Ciudad Real :");
			jefatura = "CR";
		} else if (Provincias.Guadalajara.getValorEnum().toString().equals(idProvincia)) {
			cadenaResultado.append("Delegación de Guadalajara :");
			jefatura = "GU";
		} else if ("TODASDELEGACIONES".equals(idProvincia)) {
			cadenaResultado.append("Total Delegaciones (Excluyendo Madrid):");
		}

		cadenaResultado.append("</u></b><br><br>");

		// Se obtiene el resultado de la consulta
		int sumarioMateTelProvincia = getModeloTrafico().obtenerTotalesTramitesMATE(true,idProvincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
		int sumarioMateNoTelProvincia = getModeloTrafico().obtenerTotalesTramitesMATE(false,idProvincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

		// Se recuperan los datos y se formatea en HTML la tabla
		cadenaResultado.append("<br> &nbsp Total: ")
						.append(sumarioMateTelProvincia + sumarioMateNoTelProvincia)
						.append(" &nbsp <br> &nbsp ")
						.append(sumarioMateTelProvincia)
						.append(" &nbsp &nbsp Matriculaciones Telemáticas &nbsp <br> &nbsp ")
						.append(sumarioMateNoTelProvincia)
						.append(" &nbsp &nbsp Matriculaciones No Telemáticas &nbsp <br>");

		//Obtenemos el listado de las Pdf de Madrid
		if ("28".equals(idProvincia)){

			//Desglosamos para Madrid las telematicas si hay alguna
			if (sumarioMateTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>Desglose Telemáticas :");

				//Desglosamos las Telematicas por Jefatura

				List<Object[]> detalleTramitesMatrTelJ = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "JEFATURA", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> itera = detalleTramitesMatrTelJ.iterator();

				cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones Telemáticas por Jefatura:  &nbsp ");

				int madridTelematicas = 0;
				int alcorconTelematicas = 0;
				int alcalaconTelematicas = 0;
				while ( itera.hasNext() ) {

					Object[] temp = itera.next();

					if(JEFATURA_PROVINCIAL_MADRID.equalsIgnoreCase((temp[0].toString()))){
						madridTelematicas = Integer.parseInt(temp[1].toString());
					} else if(JEFATURA_PROVINCIAL_ALCORCON.equalsIgnoreCase((temp[0].toString()))) {
						alcorconTelematicas = Integer.parseInt(temp[1].toString());
					} else if(JEFATURA_PROVINCIAL_ALCALA.equalsIgnoreCase((temp[0].toString()))) {
						alcalaconTelematicas = Integer.parseInt(temp[1].toString());
					}
				}

				cadenaResultado.append("<br> &nbsp")
									.append(madridTelematicas)
									.append(" &nbsp &nbsp MADRID &nbsp <br> &nbsp")
									.append(alcorconTelematicas)
									.append(" &nbsp &nbsp ALCORCON &nbsp <br> &nbsp")
									.append(alcalaconTelematicas)
									.append(" &nbsp &nbsp ALCALA &nbsp ");
					
				//Desglosamos las Telematicas por Tipo de Vehiculo 
				List<Object[]> detalleTramitesMatrTelVehicM = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "TIPOVEHICULO", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);
				//Desglosamos las Telematicas por Carburante
				List<Object[]> detalleTramitesMatrTelCarbM = getModeloTrafico().obtenerTramitesMATETelVehiculos("28", "CARBURANTE", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> iter = detalleTramitesMatrTelVehicM.iterator();

				cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones Telemáticas por tipo de Vehículo:  &nbsp ");
				while (iter.hasNext()) {
					Object[] temp = iter.next();
					cadenaResultado.append("<br> &nbsp ")
							.append(temp[1].toString())
							.append(" &nbsp &nbsp ")
							.append(temp[0].toString())
							.append(" &nbsp ");
				}

				//Pintamos agrupados por combustible
				Iterator<Object[]> iterb = detalleTramitesMatrTelCarbM.iterator();

				cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones Telemáticas por tipo de Combustible:  &nbsp ");
				while (iterb.hasNext()) {
					Object[] tempb = iterb.next();

					ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
					cadenaResultado
							.append("<br> &nbsp ")
							.append(tempb[1].toString())
							.append(" &nbsp &nbsp ")
							.append(convierteCodigoALiteral.getLiteral(tempb[0].toString(), new BigDecimal(5)))
							.append(" &nbsp ");
				}

			}

			//Desglosamos para Madrid las No telematicas si hay alguna
			if (sumarioMateNoTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>Desglose No Telemáticas :");

				//Totales de Ficha Tecnica A o C por Jefatura
				String totalTramitesMatrPdfItvM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfItvA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M1", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfItvAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica("28", "M2", fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfItvM = Integer.valueOf(totalTramitesMatrPdfItvM);
				int numTramitesMatrPdfItvA = Integer.valueOf(totalTramitesMatrPdfItvA);
				int numTramitesMatrPdfItvAlc = Integer.valueOf(totalTramitesMatrPdfItvAlc);

				//Totales Que no son ni Ficha Tecnica A ni C por Jefatura
				String totalTramitesMatrPdfNoItvM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfNoItvA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M1", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
				String totalTramitesMatrPdfNoItvAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica("28", "M2", fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfNoItvM = Integer.valueOf(totalTramitesMatrPdfNoItvM);
				int numTramitesMatrPdfNoItvA = Integer.valueOf(totalTramitesMatrPdfNoItvA);
				int numTramitesMatrPdfNoItvAlc = Integer.valueOf(totalTramitesMatrPdfNoItvAlc);

				cadenaResultado
						.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones No Telemáticas por Jefatura:(A.SORIA-ALCORCON-ALCALA-SUBTOTAL)  &nbsp <br> &nbsp Ficha Tecnica Tipo A o C &nbsp &nbsp ")
						.append(numTramitesMatrPdfItvM)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfItvA)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfItvAlc)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfItvM + numTramitesMatrPdfItvA + numTramitesMatrPdfItvAlc)
						.append(" &nbsp <br> &nbsp Vehículos No Admitidos  &nbsp &nbsp ")
						.append(numTramitesMatrPdfNoItvM)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfNoItvA)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfNoItvAlc)
						.append(" &nbsp &nbsp ")
						.append(numTramitesMatrPdfNoItvM + numTramitesMatrPdfNoItvA + numTramitesMatrPdfNoItvAlc)
						.append(" &nbsp ");

				// Desglosamos las No telematicas por Tipo de Vehiculo si existen

				if (numTramitesMatrPdfNoItvM>0){
					List<TramiteTrafico> detalleTramitesMatrPdfVehicM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28", "M",fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterc = detalleTramitesMatrPdfVehicM.iterator();

					cadenaResultado.append("</u></b><br></br>");
					cadenaResultado.append("<br> &nbsp  Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos para la Jefatura de Madrid :  &nbsp ");
					while (iterc.hasNext()) {
						Object[] temp = (Object[]) iterc.next();
						cadenaResultado.append("<br> &nbsp ")
								.append(temp[1].toString())
								.append(" &nbsp &nbsp ")
								.append(temp[0].toString())
								.append(" &nbsp ");
					}

				}

				//  Desglosamos para la Jefatura de Alcorcon las No telematicas por Tipo de Vehiculo si existen
				if (numTramitesMatrPdfNoItvA > 0) {
					List<TramiteTrafico> detalleTramitesMatrPdfVehicA = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28", "M1", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterd = detalleTramitesMatrPdfVehicA.iterator();

					cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos para la Jefatura de Alcorcon:  &nbsp ");
					while (iterd.hasNext()) {
						Object[] temp = (Object[]) iterd.next();
						cadenaResultado.append("<br> &nbsp ")
								.append(temp[1].toString())
								.append(" &nbsp &nbsp ")
								.append(temp[0].toString())
								.append(" &nbsp ");
					}
				}

				//  Desglosamos para la Jefatura de Alcorcon las No telematicas por Tipo de Vehiculo si existen
				if (numTramitesMatrPdfNoItvAlc > 0) {
					List<TramiteTrafico> detalleTramitesMatrPdfVehicAlc = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos("28", "M2", fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterd = detalleTramitesMatrPdfVehicAlc.iterator();

					cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos para la Jefatura de Alcala:  &nbsp ");
					while (iterd.hasNext()) {
						Object[] temp = (Object[]) iterd.next();
						cadenaResultado.append("<br> &nbsp ")
							.append(temp[1].toString())
							.append(" &nbsp &nbsp ")
							.append(temp[0].toString())
							.append(" &nbsp ");
						}
					}
				}
		} else if ((!"TOTALES".equals(idProvincia)) && (!"TODASDELEGACIONES".equals(idProvincia))){

			//Desglosamos para las que no son Madrid las telematicas si hay alguna
			if (sumarioMateTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>Desglose Telemáticas :");

				//Desglosamos las Telematicas por Tipo de Vehiculo 
				List<Object[]> detalleTramitesMatrTelVehic = getModeloTrafico().obtenerTramitesMATETelVehiculos(idProvincia, "TIPOVEHICULO", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);
				//Desglosamos las Telematicas por Carburante
				List<Object[]> detalleTramitesMatrTelCarb = getModeloTrafico().obtenerTramitesMATETelVehiculos(idProvincia, "CARBURANTE", fechaInicio.getTimestamp(), fechaFin.getTimestamp(), sumarioMateTelProvincia);

				Iterator<Object[]> iter = detalleTramitesMatrTelVehic.iterator();

				cadenaResultado.append("</u></b><br></br>");
				cadenaResultado.append("<br> &nbsp  Número de Matriculaciones Telemáticas por tipo de Vehículo:  &nbsp ");
				while ( iter.hasNext() ) {
					Object[] temp =  (Object[]) iter.next();
					cadenaResultado.append("<br> &nbsp ")
							.append(temp[1].toString())
							.append(" &nbsp &nbsp ")
							.append(temp[0].toString())
							.append(" &nbsp ");
				}

				//Pintamos agrupados por combustible
				Iterator<Object[]> iterb = detalleTramitesMatrTelCarb.iterator();

				cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones Telemáticas por tipo de Combustible:  &nbsp ");
				while (iterb.hasNext()) {

					Object[] tempb = (Object[]) iterb.next();
					ConvierteCodigoALiteral convierteCodigoALiteral = new ConvierteCodigoALiteral();
					cadenaResultado.append("<br> &nbsp ")
							.append(tempb[1].toString())
							.append(" &nbsp &nbsp ")
							.append(convierteCodigoALiteral.getLiteral(tempb[0].toString(), new BigDecimal(5)))
							.append(" &nbsp ");
				}
			}

			//Desglosamos para las que no son Madrid las No telematicas si hay alguna
			if (sumarioMateNoTelProvincia > 0){

				cadenaResultado.append("</u></b><br></br>");
				cadenaResultado.append("Desglose No Telemáticas :");

				//Totales de Ficha Técnica A o C por Jefatura
				String totalTramitesMatrPdfItv = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosFichaTecnica(idProvincia, jefatura, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				//Totales Que no son ni Ficha Técnica A ni C por Jefatura
				String totalTramitesMatrPdfNoItv = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoFichaTecnica(idProvincia, jefatura, fechaInicio.getTimestamp(), fechaFin.getTimestamp());

				int numTramitesMatrPdfItv = Integer.valueOf(totalTramitesMatrPdfItv);
				int numTramitesMatrPdfNoItv = Integer.valueOf(totalTramitesMatrPdfNoItv);

				cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones No Telemáticas:  &nbsp <br> &nbsp  Ficha Tecnica Tipo A o C &nbsp &nbsp ")
						.append(numTramitesMatrPdfItv)
						.append(" &nbsp <br> &nbsp  Vehículos No Admitidos &nbsp &nbsp ")
						.append(numTramitesMatrPdfNoItv)
						.append(" &nbsp ");

				// Desglosamos para la Jefatura las No telematicas por Tipo de Vehiculo si existen						

				if (numTramitesMatrPdfNoItv>0){
					List<TramiteTrafico> detalleTramitesMatrPdfVehicM = getModeloTrafico().obtenerTramitesMATEFinalizadoPdfVehiculosNoAdmitidos(idProvincia, jefatura,fechaInicio.getTimestamp(), fechaFin.getTimestamp());
					Iterator<?> iterc = detalleTramitesMatrPdfVehicM.iterator();

					cadenaResultado.append("</u></b><br></br><br> &nbsp  Número de Matriculaciones No Telemáticas para Vehiculos No Admitidos:  &nbsp ");
					while (iterc.hasNext()) {
						Object[] temp = (Object[]) iterc.next();
						cadenaResultado.append("<br> &nbsp ")
								.append(temp[1].toString())
								.append(" &nbsp &nbsp ")
								.append(temp[0].toString())
								.append(" &nbsp ");
					}
				}
			}
		}
		// Sumario - tabla para presentación de datos			
		return cadenaResultado;
	}

	/**
	 * Metodo que encapsula la logica de actualizacion de Envio Data tras los envios de los correos de CTIT y MATE
	 * @param resultado lo que devolvio el envio del Correo de Estadísticas 
	 */
	public void actualizaEnvioDataEstadisticas(ResultBean resultado) {

		String mensaje = "";

		// Inserción fecha envio estadisticas siempre que no haya habido error en el envio
		String mensajeRB = resultado.getMensaje();
		if(!resultado.getError()){
			try {
				if(mensajeRB == null || mensajeRB.equals("")){
					mensaje = mensaje+ConstantesProcesos.EJECUCION_CORRECTA_MENSAJE_X_DEFECTO;
				}
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(),ConstantesProcesos.EJECUCION_CORRECTA , mensaje,TipoProceso.Estadisticas.getValorEnum());
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.Estadisticas.getValorEnum(), mensaje, ConstantesProcesos.EJECUCION_CORRECTA);
			} catch (Exception e) {
				log.error("Error al Guardar Envio Data para " + TipoProceso.Estadisticas.getNombreEnum(), e);
			}
		}else{
			if(mensajeRB == null || mensajeRB.equals("")){
				mensaje = mensaje+ConstantesProcesos.ERROR_ESTADISTICAS_CTIT;
			}
			if(mensajeRB == null || mensajeRB.equals("")){
				mensaje = mensaje+ConstantesProcesos.ERROR_ESTADISTICAS_MATE;
			}
			try{
				ejecucionesProcesosDAO.actualizarUltimaEjecucion(new ColaBean(),ConstantesProcesos.EJECUCION_NO_CORRECTA , mensaje,TipoProceso.Estadisticas.getValorEnum());
				//ejecucionesProcesosDAO.actualizarUltimaEjecucion(TipoProceso.Estadisticas.getValorEnum(), mensaje, ConstantesProcesos.EJECUCION_NO_CORRECTA);
			}catch(Exception e){
				log.error("ERROR: Fallo en la actualización de la última ejecución en ENVIO_DATA en " + TipoProceso.Estadisticas.getNombreEnum(), e);
			}
		}

	}

	/**
	 * Método que envía un correo con información de estadísticas de CTIT
	 * 
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException 
	 * @throws Exception
	 */
	public ResultBean enviarMailEstadisticasCTITDelegaciones() throws OegamExcepcion, ParseException {

		// Se llama al método que obtiene los datos y forma la cadena HTML

		// Se comprueba si el formato va a ser en texto plano o no
		boolean textoPlano = "si".equalsIgnoreCase(gestorPropiedades.valorPropertie("estadisticas.presentacion.formato.textoPlano"));

		StringBuffer sb = datosEstadisticasCTITDelegaciones(textoPlano);

		String nombrePdfEstadisticas = ConstantesEstadisticas.ESTADISTICASCTIT + "_" + getFechaFinEstadisticas().getDia() 
				+ getFechaFinEstadisticas().getMes() + getFechaFinEstadisticas().getAnio();

		File file = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, 
				getFechaFinEstadisticas(), nombrePdfEstadisticas, ConstantesGestorFicheros.EXTENSION_PDF).getFile();

		FicheroBean fichero = new FicheroBean();
		fichero.setFichero(file);
		fichero.setNombreDocumento("EstadisticasCTIT.pdf");

		// Recuperar configuracion del envio del correo de estadisticas MATE
		String recipient =  gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTITDelegaciones.destinatarios1");
		String subject = gestorPropiedades.valorPropertie("direccion.envio.estadisticas.CTIT.subject");	

		// Enviar correo
		ResultBean resultEnvio;

		try {
			resultEnvio = getServicioCorreo().enviarCorreo(sb.toString(), textoPlano, null, subject, recipient, null, null, null, fichero);
		} catch (IOException e) {
			throw new OegamExcepcion("Error envio email");
		}

		return resultEnvio;

	}

	/**
	 * 
	 * @param esTextoPlano
	 * @return
	 * @throws OegamExcepcion
	 * @throws ParseException
	 * @throws Throwable
	 */
	private StringBuffer datosEstadisticasCTITDelegaciones(boolean esTextoPlano) throws OegamExcepcion, ParseException {

		StringBuffer resultadoHtml = new StringBuffer();
		StringBuffer resultadoHtmlConTablaGeneral = new StringBuffer();
		final String sFormatoFecha = "dd/MM/yyyy 'a las' HH:mm:ss";
		final String sFormatoFechaIntervalo = "dd/MM/yyyy";
		HtmlBean htmlBean;
		List<HtmlBean> arrayHtmlBean = new ArrayList<HtmlBean>();
		List<HtmlBean> arrayHtmlBeanCorrecto = new ArrayList<HtmlBean>();

		Fecha fechaFin = getFechaFinEstadisticas();
		Fecha fechaInicio = getFechaInicioEstadisticas(fechaFin);

		entroTel = true;
		entroNoTel = true;

		// Encabezamiento del e-mail

		resultadoHtml.append("<br></br>Estadísticas CTIT en Oegam. Mensaje generado el ")
					.append(utilesFecha.formatoFecha(sFormatoFecha,new Date()))
					.append(".<br></br>Consulta realizada para el período del ")
					.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaInicio.getFecha()))
					.append(" al ")
					.append(utilesFecha.formatoFecha(sFormatoFechaIntervalo, fechaFin.getFecha()))
					.append(".<br></br>");

		// Trámites telemáticos por delegaciones
				if (!esTextoPlano){	
					StringBuffer sbTemporal = new StringBuffer();
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"TOTALES",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"28",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"19",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"40",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"13",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"16",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(true,"05",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
				}
			// Trámites NO telemáticos por delegaciones
				if (!esTextoPlano){
					StringBuffer sbTemporal = new StringBuffer();
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"TOTALES",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"28",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"19",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"40",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"13",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"16",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
					sbTemporal = cadenaHTMLResultadoEstadisticasCTIT(false,"05",fechaInicio,fechaFin);
						resultadoHtml.append(sbTemporal);
						resultadoHtml.append("<br></br>");
						htmlBean = new HtmlBean();
						htmlBean.setHtmlCode(sbTemporal.toString());
						arrayHtmlBean.add(htmlBean);
						//Limpio el StringBuffer para reutilizarlo.
						sbTemporal.delete(0, sbTemporal.length());
				}
		evitaIndeterminadoEnDivision();
		resultadoHtmlConTablaGeneral = aniadeTablaGeneral(resultadoHtml);
		// Se forma el HTML
		StringBuffer html = new StringBuffer("<html><head> <meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\"/> </head><body style=\"background-color:#E6E6E6\">");
		html.append(UtilesCadenaCaracteres.stripAccents(resultadoHtmlConTablaGeneral.toString()));
		html.append("</body></html>");

		if (!esTextoPlano){
			String nombreFichero = ConstantesEstadisticas.FICHEROHTML+ "_" + fechaFin.getDia() +fechaFin.getMes() + fechaFin.getAnio();
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, fechaFin, nombreFichero, ConstantesGestorFicheros.EXTENSION_HTML, html.toString().getBytes());
		}
		arrayHtmlBeanCorrecto = aniadeTablaGeneralApdf(arrayHtmlBean);
		guardaPdfEstadisticasCtit(arrayHtmlBeanCorrecto, fechaFin);
		return resultadoHtmlConTablaGeneral.append("<br></br><br></br>");
	}

//	//Sacarlo a una clase de utilidades, porque tambien esta en ModeloIVTM
//	/**
//     * Método que normaliza los caracteres acentuados
//     * */
//     private String stripAccents(String strToStrip){
//              String strStripped = null;
//              //Normalizamos en la forma NFD (Canonical decomposition)
//              strToStrip = Normalizer.normalize(strToStrip, Normalizer.Form.NFD);
//              //Reemplazamos los acentos con una una expresión regular de Bloque Unicode
//              strStripped = strToStrip.replaceAll("\\p{IsM}+", "");
//
//              return strStripped;
//     }

	private List<HtmlBean> aniadeTablaGeneralApdf(List<HtmlBean> arrayHtmlBean){
		List<HtmlBean> listaHtmlBean = new ArrayList<HtmlBean>();
		HtmlBean htmlBeanAux;
		for (HtmlBean htmlBean : arrayHtmlBean){
			StringBuffer sb = new StringBuffer();
			sb.append(htmlBean.getHtmlCode());
			StringBuffer sbTemporal = aniadeTablaGeneral(sb);
			htmlBeanAux = new HtmlBean();
			htmlBeanAux.setHtmlCode(sbTemporal.toString());
			listaHtmlBean.add(htmlBeanAux);
		}
		return listaHtmlBean;
	}

	private void guardaPdfEstadisticasCtit(List<HtmlBean> listHtmlBean, Fecha fechaFin){

		String nombreFichero =ConstantesEstadisticas.ESTADISTICASCTIT+ "_" + fechaFin.getDia() +fechaFin.getMes() + fechaFin.getAnio();
		String nombreFicheroHtml =ConstantesEstadisticas.FICHEROHTML+ "_" + fechaFin.getDia() +fechaFin.getMes() + fechaFin.getAnio();

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
			File ficheroHtml = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.ESTADISTICAS, ConstantesGestorFicheros.CTIT, fechaFin, nombreFicheroHtml, ConstantesGestorFicheros.EXTENSION_HTML).getFile();

			ITextRenderer iTextRenderer = new ITextRenderer();

            /** 
             * Setting the document as the url value passed.
             * This means that the iText renderer 
             * has to parse this html document to generate the pdf.
             */
            iTextRenderer.setDocument(ficheroHtml);
            iTextRenderer.layout();
 
            /** The generated pdf will be written 
                to the invoice.pdf file. */
            fileOutputStream = new FileOutputStream(ficheroPdf);
            /** Creating the pdf and 
                writing it in invoice.pdf file. */
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
			if (fileOutputStream!=null) {
				try {
					fileOutputStream.close();
				} catch (IOException e) {
					log.error("No se pudo cerrar el outputstream", e);
				}
			}
		}
	}

	private void evitaIndeterminadoEnDivision(){

		if (totalTelMadrid == 0 && totalPdfMadrid == 0){
			totalPorcentajeTelMadrid = 0;
		}else{
			totalPorcentajeTelMadrid = (totalTelMadrid * 100) / (totalTelMadrid + totalPdfMadrid);
		}

		if (totalTelSegovia == 0 && totalPdfSegovia == 0){
			totalPorcentajeTelSegovia = 0;
		}else{
			totalPorcentajeTelSegovia = (totalTelSegovia * 100) / (totalTelSegovia + totalPdfSegovia);
		}

		if (totalTelAvila == 0 && totalPdfAvila == 0){
			totalPorcentajeTelAvila = 0;
		}else{
			totalPorcentajeTelAvila = (totalTelAvila * 100) / (totalTelAvila + totalPdfAvila);
		}

		if (totalTelCuenca == 0 && totalPdfCuenca == 0){
			totalPorcentajeTelCuenca = 0;
		}else{
			totalPorcentajeTelCuenca = (totalTelCuenca * 100) / (totalTelCuenca + totalPdfCuenca);
		}

		if (totalTelCiudadReal == 0 && totalPdfCiudadReal == 0){
			totalPorcentajeTelCiudadReal = 0;
		}else{
			totalPorcentajeTelCiudadReal = (totalTelCiudadReal * 100) / (totalTelCiudadReal + totalPdfCiudadReal);
		}

		if (totalTelGuadalajara == 0 && totalPdfGuadalajara == 0){
			totalPorcentajeTelGuadalajara = 0;
		}else{
			totalPorcentajeTelGuadalajara = (totalTelGuadalajara * 100) / (totalTelGuadalajara + totalPdfGuadalajara);
		}
	}

	private StringBuffer aniadeTablaGeneral(StringBuffer resultadoHtml) {
		int inicioCadenaSustituir = resultadoHtml.indexOf(CADENA_A_SUSTITUIR);
		if (inicioCadenaSustituir != -1) {
			int finalCadenaSustituir = inicioCadenaSustituir
					+ CADENA_A_SUSTITUIR.length();
			DecimalFormat df = new DecimalFormat();
			df.setMaximumFractionDigits(2);
			DecimalFormat dfN = new DecimalFormat();
			dfN.setMaximumFractionDigits(0);
			StringBuffer tablaFinal = new StringBuffer("<tr> <td> Telemático </td><td>")
					.append(dfN.format(totalTelMadrid))
					.append("</td> <td>")
					.append(dfN.format(totalTelSegovia))
					.append("</td> <td>")
					.append(dfN.format(totalTelAvila))
					.append("</td> <td>")
					.append(dfN.format(totalTelCuenca))
					.append("</td> <td>")
					.append(dfN.format(totalTelCiudadReal))
					.append("</td> <td>")
					.append(dfN.format(totalTelGuadalajara))
					.append("</td> <td>")
					.append(dfN.format(totalTelematicas))
					.append("</td> </tr>")
					.append("<tr> <td> PDF </td><td>")
					.append(dfN.format(totalPdfMadrid))
					.append("</td> <td>")
					.append(dfN.format(totalPdfSegovia))
					.append("</td> <td>")
					.append(dfN.format(totalPdfAvila))
					.append("</td> <td>")
					.append(dfN.format(totalPdfCuenca))
					.append("</td> <td>")
					.append(dfN.format(totalPdfCiudadReal))
					.append("</td> <td>")
					.append(dfN.format(totalPdfGuadalajara))
					.append("</td><td>")
					.append(dfN.format(totalPdf))
					.append("</td></tr>")
					.append("<tr><td> % Telemáticas </td> <td>")
					.append(df.format(totalPorcentajeTelMadrid))
					.append("%</td><td>")
					.append(df.format(totalPorcentajeTelSegovia))
					.append("%</td><td>")
					.append(df.format(totalPorcentajeTelAvila))
					.append("%</td><td>")
					.append(df.format(totalPorcentajeTelCuenca))
					.append("%</td><td>")
					.append(df.format(totalPorcentajeTelCiudadReal))
					.append("%</td><td>")
					.append(df.format(totalPorcentajeTelGuadalajara))
					.append("%</td><td>")
					.append(df.format(totalTelematicas * 100 / (totalTelematicas + totalPdf)))
					.append("%</td> </tr>");
			return resultadoHtml.replace(inicioCadenaSustituir,
					finalCadenaSustituir, tablaFinal.toString());
		} else {
			return resultadoHtml;
		}
	}

	/**
	 * Método auxiliar para el cálculo de estadísticas CTIT. Accede a base de datos y forma cadena con formato HTML 
	 * para mostrar en el e-mail. Incluye tablas.
	 * @param isTelematico Indica si la cadena a obtener es para operaciones telemáticas o no
	 * @param fechaInicio Fecha inicio de la consulta
	 * @param fechaFin Fecha fin de la consulta 
	 * @return
	 * @throws OegamExcepcion 
	 * @throws ParseException 
	 * @throws Throwable
	 */
	private StringBuffer cadenaHTMLResultadoEstadisticasCTIT(boolean esTelematico, String provincia, Fecha fechaInicio, Fecha fechaFin) throws OegamExcepcion, ParseException {

		// Se 'inicializa' cadena para informar resultado
		StringBuffer cadenaResultado = new StringBuffer("<b><u>");

		// Se obtienen los literales a partir de fichero de properties
		if (esTelematico){
			if (entroTel){
				cadenaResultado.append(TELEMATICAS);
				cadenaResultado.append(":<br></br>");
				entroTel=false;
			}
		}else{
			if (entroNoTel){
				cadenaResultado.append(NO_TELEMATICAS);
				cadenaResultado.append(":<br></br>");
				entroNoTel=false;
			}
		}

		cadenaResultado.append("</u></b><b>");
		if ("TOTALES".equals(provincia)) {
			cadenaResultado.append("Total Transmisiones por tipo de transferencia :");
		} else if ("28".equals(provincia)) {
			cadenaResultado.append("Delegacion de Madrid :");
		} else if ("40".equals(provincia)) {
			cadenaResultado.append("Delegacion de Segovia :");
		} else if ("05".equals(provincia)) {
			cadenaResultado.append("Delegacion de Avila :");
		} else if ("16".equals(provincia)) {
			cadenaResultado.append("Delegacion de Cuenca :");
		} else if ("13".equals(provincia)) {
			cadenaResultado.append("Delegacion de Ciudad Real :");
		} else if ("19".equals(provincia)) {
			cadenaResultado.append("Delegacion de Guadalajara :");
		}

		cadenaResultado.append("</b><br></br>Total: ");

		Integer totalRegistros = 0;
		StringBuffer listado = new StringBuffer();

		if ("TOTALES".equals(provincia)){
			// Se obtiene el resultado de la consulta
			List<TramiteTrafico> listaTotalesCTIT = getModeloTrafico().obtenerTotalesTramitesCTIT(esTelematico, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
			List<TotalesTramitesCTITBean> sumarioTramitesTelematicos = new ArrayList<TotalesTramitesCTITBean>();
			Iterator<?> iterTotales = listaTotalesCTIT.iterator();
			while(iterTotales.hasNext()){
				Object[] obj = (Object[]) iterTotales.next();
				TotalesTramitesCTITBean tramites = new TotalesTramitesCTITBean();
				tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
				if(obj[2].toString().equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())){
					Integer tipoTransferencia = new Integer(obj[1]!=null ? obj[1].toString() : BigDecimal.ZERO.toString());
					switch(tipoTransferencia){
						case 1: tramites.setTipoTransferencia(TipoTransferencia.tipo1.getNombreEnum()); break;
						case 2: tramites.setTipoTransferencia(TipoTransferencia.tipo2.getNombreEnum()); break;
						case 3: tramites.setTipoTransferencia(TipoTransferencia.tipo3.getNombreEnum()); break;
						case 4: tramites.setTipoTransferencia(TipoTransferencia.tipo4.getNombreEnum()); break;
						case 5: tramites.setTipoTransferencia(TipoTransferencia.tipo5.getNombreEnum()); break;
						default: tramites.setTipoTransferencia("No marcada"); break;
					}
				} else {
					tramites.setTipoTransferencia("Bajas por transferencia temporal");
				}
				sumarioTramitesTelematicos.add(tramites);
			}

			// Totales - tramites telematicos

			// Se recuperan los datos y se formatea en HTML la tabla
			for (TotalesTramitesCTITBean sumario:sumarioTramitesTelematicos){
				listado.append("<tr> <td>")
						.append(sumario.getNumTransferencias())
						.append("</td><td>")
						.append(sumario.getTipoTransferencia())
						.append("</td> </tr>");
				totalRegistros += sumario.getNumTransferencias().intValue();
			}

			cadenaResultado.append(totalRegistros);
			if (esTelematico) {
				totalTelematicas = totalRegistros;
			} else {
				totalPdf = totalRegistros;
			}
		}else{
			// Se obtiene el resultado de la consulta
			List<TramiteTrafico> listaTotalesCTIT = getModeloTrafico().obtenerTotalesTramitesCTITPorJefatura(esTelematico, provincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
			List<TotalesTramitesCTITBean> sumarioTramitesTelematicos = new ArrayList<TotalesTramitesCTITBean>();
			Iterator<?> iterTotales = listaTotalesCTIT.iterator();
			while(iterTotales.hasNext()){
				Object[] obj = (Object[]) iterTotales.next();
				TotalesTramitesCTITBean tramites = new TotalesTramitesCTITBean();
				tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
				if(obj[2].toString().equals(TipoTramiteTrafico.TransmisionElectronica.getValorEnum())){
					Integer tipoTransferencia = new Integer(obj[1]!=null ? obj[1].toString() : BigDecimal.ZERO.toString());
					switch(tipoTransferencia){
						case 1: tramites.setTipoTransferencia(TipoTransferencia.tipo1.getNombreEnum()); break;
						case 2: tramites.setTipoTransferencia(TipoTransferencia.tipo2.getNombreEnum()); break;
						case 3: tramites.setTipoTransferencia(TipoTransferencia.tipo3.getNombreEnum()); break;
						case 4: tramites.setTipoTransferencia(TipoTransferencia.tipo4.getNombreEnum()); break;
						case 5: tramites.setTipoTransferencia(TipoTransferencia.tipo5.getNombreEnum()); break;
						default: tramites.setTipoTransferencia("No marcada"); break;
					}
				} else {
					tramites.setTipoTransferencia("Bajas por transferencia temporal");
				}
				sumarioTramitesTelematicos.add(tramites);
			}

			// Totales - tramites telematicos

			// Se recuperan los datos y se formatea en HTML la tabla
			for (TotalesTramitesCTITBean sumario : sumarioTramitesTelematicos) {
				listado.append("<tr> <td>")
						.append(sumario.getNumTransferencias())
						.append("</td><td>")
						.append(sumario.getTipoTransferencia())
						.append("</td> </tr>");
				totalRegistros += sumario.getNumTransferencias().intValue();
			}

			cadenaResultado.append(totalRegistros);

			if (esTelematico){
				if ("28".equals(provincia)){
					totalTelMadrid=totalRegistros;
				}else if("40".equals(provincia)){
					totalTelSegovia=totalRegistros;
				}else if("05".equals(provincia)){
					totalTelAvila=totalRegistros;
				}else if("16".equals(provincia)){
					totalTelCuenca=totalRegistros;
				}else if("13".equals(provincia)){
					totalTelCiudadReal=totalRegistros;
				}else if("19".equals(provincia)){
					totalTelGuadalajara=totalRegistros;
				}
			}else{
				if ("28".equals(provincia)){
					totalPdfMadrid=totalRegistros;
				}else if("40".equals(provincia)){
					totalPdfSegovia=totalRegistros;
				}else if("05".equals(provincia)){
					totalPdfAvila=totalRegistros;
				}else if("16".equals(provincia)){
					totalPdfCuenca=totalRegistros;
				}else if("13".equals(provincia)){
					totalPdfCiudadReal=totalRegistros;
				}else if("19".equals(provincia)){
					totalPdfGuadalajara=totalRegistros;
				}
			}
		}

		if (totalRegistros>0){//Si hay datos se pintan las dos tablas, sumario y detalle

			// Sumario - tabla para presentacion de datos
			// Cabecera
			cadenaResultado.append("<table border='1'> <tr> <th>Numero de tramites</th> <th>Tipo de transferencia</th> </tr> ");
			// fin cabecera
			cadenaResultado.append(listado).append("</table><br></br>");
			//**************** Tabla con resultados generales *******************
			if ("TOTALES".equals(provincia) && esTelematico) {
				listado.delete(0, listado.length());
				listado.append(CADENA_A_SUSTITUIR);
				cadenaResultado.append("<b>Porcentajes de Telemáticas: </b><table border='1' width='100%'> <tr> <th> Tipo </th> <th> Madrid </th> <th> Segovia </th> <th> Avila </th> <th> Cuenca </th> <th> Ciudad Real </th> <th> Guadalajara </th> <th> Total </th> </tr> ");
				// fin cabecera

				cadenaResultado.append(listado).append("</table>");
				// Se reinician las variables para reutilizarlas
				listado.delete(0, listado.length());
			}
			//**************** FIN Tabla con resultados generales *******************

			// Detalle - telematicos
			// Se obtiene el resultado de la consulta
			List<TramiteTrafico> listaTramitesTelematicos = getModeloTrafico().obtenerDetalleTramitesCTIT(esTelematico, provincia, fechaInicio.getTimestamp(), fechaFin.getTimestamp());
			List<DetalleTramitesCTITBean> detalleTramitesTelematicos = new ArrayList<DetalleTramitesCTITBean>();
			Iterator<?> iterTramites = listaTramitesTelematicos.iterator();
			while(iterTramites.hasNext()){
				Object[] obj = (Object[]) iterTramites.next();
				DetalleTramitesCTITBean tramites = new DetalleTramitesCTITBean();
				tramites.setNumTransferencias(new BigDecimal(obj[0].toString()));
				tramites.setNumColegiado(new BigDecimal(obj[1].toString()));
				Integer estadoTramite = new Integer(obj[2]!=null ? obj[2].toString() : BigDecimal.ZERO.toString()); 
				switch(estadoTramite){
					case 12: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_Telematicamente.getNombreEnum()); break;
					case 13: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_PDF.getNombreEnum()); break;
					case 14: tramites.setEstadoTransferencia(EstadoTramiteTrafico.Finalizado_Telematicamente_Impreso.getNombreEnum()); break;
					default: tramites.setEstadoTransferencia("No definido"); break;
				}
				detalleTramitesTelematicos.add(tramites);
			}

			// Se reinician las variables para reutilizarlas
			listado.delete(0, listado.length());
			if (!"TOTALES".equals(provincia)){
				cadenaResultado.append("<br></br>Transferencias por colegiado:<br></br>");
				for (DetalleTramitesCTITBean detalle : detalleTramitesTelematicos) {
					listado.append("<tr> <td>")
							.append(detalle.getNumColegiado())
							.append("</td> <td>")
							.append(detalle.getNumTransferencias())
							.append("</td> <td>")
							.append(detalle.getEstadoTransferencia())
							.append("</td> </tr>");
				}

				// Tabla para presentacion de datos - detalle
				// Cabecera

				cadenaResultado.append("<table border='1' width='100%'> <tr> <th>Numero de colegiado</th> <th>Numero de tramites</th> <th>Estado del tramite</th> </tr> ");
				// fin cabecera

				cadenaResultado.append(listado).append("</table>");
				// fin tabla
			}
			// Obtengo los totales

			if(!esTelematico){

				List<Long> listaExpedientes = getModeloTrafico().obtenerListadoNumerosExpedienteCTIT(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate());

				/* ************************************ ANOTACIONES GEST ************************************ */
			if (!"TOTALES".equals(provincia)){
					try {
						// Sumario - tabla para presentacion de datos
						cadenaResultado.append("<br></br>Expedientes con Anotaciones en GEST:<br></br>");
						// Cabecera
						cadenaResultado.append("<table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
						// fin cabecera

						// Se obtiene el resultado de la consulta
						int detalleTramitesCTITAnotacionesGest = getModeloTrafico().obtenerDetalleTramitesCTITAnotacionesGest(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

						// Se reinician las variables para reutilizarlas
						listado.delete(0, listado.length());

						listado.append("<tr> <td>")
								.append(detalleTramitesCTITAnotacionesGest)
								.append("</td> <td>Expedientes con Anotaciones en GEST</td> </tr>");

						cadenaResultado.append(listado);
						cadenaResultado.append("</table>");
						// fin tabla
						cadenaResultado.append("<p><em>Total expedientes con Anotaciones en GEST</em>: <strong>")
								.append(detalleTramitesCTITAnotacionesGest)
								.append("</strong></p>");

						List<Long> listaExpedientesCTITAnotacionesGest = getModeloTrafico().obtenerListaExpedientesCTITAnotacionesGest(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
						listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITAnotacionesGest);
					} catch (Exception e) {
						log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de Anotaciones en GEST", e);
					}

				/* ************************************ CARPETA TIPO FUSIÓN ************************************ */
				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes de Tipo Fusion:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo de Vehiculo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITCarpetaFusion = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaFusion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					Iterator<?> it = detalleTramitesCTITCarpetaFusion.iterator();
					int sumaTipoFusion = 0;
					while(it.hasNext()){
						Object[] detalle = (Object[]) it.next();
						sumaTipoFusion += (Integer) detalle[0];
						listado.append("<tr> <td>")
								.append(detalle[0].toString())
								.append("</td> <td>")
								.append(detalle[1].toString())
								.append("</td> </tr>"
						);
					}

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes Tipo Fusion</em>: <strong>")
							.append(sumaTipoFusion).append("</strong></p>");

					List<Long> listaExpedientesCTITTipoFusion = getModeloTrafico().obtenerListaExpedientesCTITCarpetaFusion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoFusion);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes Tipo Fusion", e);
				}

				/* ************************************ CARPETA TIPO I ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes de Tipo I:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo de Vehiculo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITCarpetaI = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaI(esTelematico,provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					Iterator<?> it = detalleTramitesCTITCarpetaI.iterator();
					int sumaTipoI = 0;
					while (it.hasNext()) {
						Object[] detalle = (Object[]) it.next();
						sumaTipoI += (Integer) detalle[0];
						listado.append("<tr> <td>")
								.append(detalle[0].toString())
								.append("</td> <td>")
								.append(detalle[1].toString())
								.append("</td> </tr>");
					}

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes Tipo I</em>: <strong>")
							.append(sumaTipoI).append("</strong></p>");

					List<Long> listaExpedientesCTITTipoI = getModeloTrafico().obtenerListaExpedientesCTITCarpetaI(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoI);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes Tipo I", e);
				}

				/* ************************************ CARPETA TIPO B ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes de Tipo B:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					int detalleTramitesCTITCarpetaB = getModeloTrafico().obtenerDetalleTramitesCTITCarpetaB(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>")
							.append(detalleTramitesCTITCarpetaB)
							.append("</td> <td>Expedientes Tipo B</td> </tr>");

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes Tipo B</em>: <strong>")
							.append(detalleTramitesCTITCarpetaB)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITTipoB = getModeloTrafico().obtenerListaExpedientesCTITCarpetaB(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITTipoB);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes tipo B", e);
				}

				/* ************************************ VEHÍCULOS AGRÍCOLAS ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes de Vehiculos Agricolas:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo de Vehiculo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					List<TramiteTrafico> detalleTramitesCTITVehiculosAgricolas = getModeloTrafico().obtenerDetalleTramitesCTITVehiculosAgricolas(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					Iterator<?> iterAgricolas = detalleTramitesCTITVehiculosAgricolas.iterator();
					int sumaAgricolas = 0;
					while(iterAgricolas.hasNext()){
						Object[] detalle = (Object[]) iterAgricolas.next();
						sumaAgricolas += (Integer) detalle[0];
						listado.append("<tr> <td>")
								.append(detalle[0].toString())
								.append("</td> <td>")
								.append(detalle[1].toString())
								.append("</td> </tr>");
					}

					cadenaResultado.append(listado)
							.append("</table><p><em>Total expedientes de vehiculos agricolas</em>: <strong>")
							.append(sumaAgricolas)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITVehiculosAgricolas = getModeloTrafico().obtenerListaExpedientesCTITVehiculosAgricolas(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITVehiculosAgricolas);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de Vehiculos agricolas", e);
				}

				/* ************************************ ADJUDICACIÓN JUDICIAL ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes por Modo de Adjudicacion judicial o subasta:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					int detalleTramitesCTITJudicialSubasta = getModeloTrafico().obtenerDetalleTramitesCTITJudicialSubasta(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>").append(detalleTramitesCTITJudicialSubasta).append("</td> <td>Adjudicacion Judicial o Subasta</td> </tr>");

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total Modo de Adjudicacion judicial o subasta:</em> <strong>")
							.append(detalleTramitesCTITJudicialSubasta)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITJudicialSubasta = getModeloTrafico().obtenerListaExpedientesCTITJudicialSubasta(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITJudicialSubasta);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de Adjudicacion Judicial / Subasta", e);
				}

				/* ************************************ SIN CET, NI FACTURA ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes sin CET, ni factura:<br></br><table border='1' width='100%'><tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					int obtenerDetalleTramitesCTITSinCETNiFactura = getModeloTrafico().obtenerDetalleTramitesCTITSinCETNiFactura(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>")
							.append(obtenerDetalleTramitesCTITSinCETNiFactura)
							.append("</td> <td>Sin CET, ni factura</td> </tr>");

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes sin CET, ni factura:</em> <strong>")
							.append(obtenerDetalleTramitesCTITSinCETNiFactura)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITSinCETNiFactura = getModeloTrafico().obtenerListaExpedientesCTITSinCETNiFactura(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITSinCETNiFactura);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes sin CET, ni factura", e);
				}

				/* ************************************ TRAMITABLE EN JEFATURA ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes Tramitables en Jefatura:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					int obtenerDetalleTramitesCTITEmbargoPrecinto = getModeloTrafico().obtenerDetalleTramitesCTITEmbargoPrecinto(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>")
							.append(obtenerDetalleTramitesCTITEmbargoPrecinto)
							.append("</td> <td>Tramitables en Jefatura</td> </tr>");

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes Tramitables en Jefatura</em>: <strong>")
							.append(obtenerDetalleTramitesCTITEmbargoPrecinto)
							.append("</strong></p>");

					List<Long> listaExpedientesCTITEmbargoPrecinto = getModeloTrafico().obtenerListaExpedientesCTITEmbargoPrecinto(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITEmbargoPrecinto);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes Tramitables en Jefatura", e);
				}

				/* ************************************ ERROR EN JEFATURA ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes con Error en Jefatura:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					// Se obtiene el resultado de la consulta
					List<Object> detalleTramitesCTITErrorJefatura = getModeloTrafico().obtenerDetalleTramitesCTITErrorJefatura(fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					int sumaErrorJefatura = 0;
					Iterator<?> itErrorJefatura = detalleTramitesCTITErrorJefatura.iterator();
					while (itErrorJefatura.hasNext()) {
						Object[] detalle = (Object[]) itErrorJefatura.next();
						sumaErrorJefatura += new BigDecimal(detalle[0].toString()).intValue();
						listado.append("<tr> <td>")
								.append(detalle[0].toString())
								.append("</td> <td>")
								.append(detalle[1].toString())
								.append("</td> </tr>");
					}

					cadenaResultado
							.append(listado)
							.append("</table><p><em>Total expedientes con Error en Jefatura</em>: <strong>")
							.append(sumaErrorJefatura)
							.append("</strong></p>");

					List<Object> listaExpedientesCTITErrorJefatura = getModeloTrafico().obtenerListaExpedientesCTITErrorJefatura(fechaInicio.getDate(), fechaFin.getDate(), listaExpedientes);
					List<Long> listaCTITErrorJefaturaLong = new ArrayList<Long>();
					Iterator<?> itErrorJefatura2 = listaExpedientesCTITErrorJefatura.iterator();
					while(itErrorJefatura2.hasNext()){
						BigDecimal detalle = (BigDecimal) itErrorJefatura2.next();
						listaCTITErrorJefaturaLong.add(Long.valueOf(detalle.toString()));
					}
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaCTITErrorJefaturaLong);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes con Error en Jefatura", e);
				}

				/* ************************************ FINALIZADOS ERROR -> FINALIZADOS PDF ************************************ */

				// Se obtiene el resultado de la consulta
				List<EstadoTramiteTrafico> transicionEstados;
				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<br></br>Expedientes que han pasado de Validado Telematicamente a Finalizado PDF, pasando por Finalizado con Error:<br></br><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					transicionEstados = new ArrayList<EstadoTramiteTrafico>();
					transicionEstados.add(EstadoTramiteTrafico.Pendiente_Envio);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_Con_Error);
					transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);
					int detalleTramitesCTITValidadosErrorFinalizados = getModeloTrafico().obtenerDetalleTramitesCTITEvolucion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>")
							.append(detalleTramitesCTITValidadosErrorFinalizados)
							.append("</td> <td>Expedientes</td> </tr>");

					cadenaResultado.append(listado).append("</table><p><em>Total expedientes que han pasado de Validado Telematicamente a Finalizado PDF, pasando por Finalizado con Error</em>: <strong>").append(detalleTramitesCTITValidadosErrorFinalizados).append("</strong></p>");

					List<Long> listaExpedientesCTITValidadosErrorFinalizados = getModeloTrafico().obtenerListaExpedientesCTITEvolucion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosErrorFinalizados);
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes Finalizados PDF que pasan por Finalizados con Error", e);
				}

				/* ************************************ VALIDADOS TELEMATICAMENTE -> FINALIZADOS PDF ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					// Cabecera
					cadenaResultado.append("<p>Expedientes que pasan de Validado Telematicamente a Finalizado PDF:</p><table border='1' width='100%'> <tr> <th>Numero de tramites</th> <th>Tipo</th> </tr> ");
					// fin cabecera

					// Se obtiene el resultado de la consulta
					transicionEstados = new ArrayList<EstadoTramiteTrafico>();
					transicionEstados.add(EstadoTramiteTrafico.Validado_Telematicamente);
					transicionEstados.add(EstadoTramiteTrafico.Finalizado_PDF);
					int detalleTramitesCTITValidadosAFinalizados = getModeloTrafico().obtenerDetalleTramitesCTITEvolucion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					listado.append("<tr> <td>")
							.append(detalleTramitesCTITValidadosAFinalizados)
							.append("</td> <td>Expedientes</td> </tr>");

					cadenaResultado.append(listado);
					cadenaResultado.append("</table>");
					// fin tabla

					List<Long> listaExpedientesCTITValidadosAFinalizados = getModeloTrafico().obtenerListaExpedientesCTITEvolucion(esTelematico, provincia, fechaInicio.getDate(), fechaFin.getDate(), transicionEstados, listaExpedientes);				
					listaExpedientes = quitarDuplicadosLista(listaExpedientes, listaExpedientesCTITValidadosAFinalizados);

					// Loggeo la lista de expedientes de este tipo
					if (log.isInfoEnabled()){
						String expValFin = "";
						for(int i = 0; i < listaExpedientesCTITValidadosAFinalizados.size(); i++){
							expValFin += listaExpedientesCTITValidadosAFinalizados.get(i).toString() + " - ";
						}
						log.info(expValFin);
					}

					if(listaExpedientesCTITValidadosAFinalizados!=null && listaExpedientesCTITValidadosAFinalizados.size() > BigDecimal.ZERO.intValue()){
						List<Object> listaTramitesGestorCTITValidadosAFinalizados = getModeloTrafico().obtenerListaTramitesGestorSobreExp(listaExpedientesCTITValidadosAFinalizados);
						listado.append("<p>Listado de Gestorias con Tramites que pasan de Validado Telematicamente a Finalizado PDF</p><table border='1' width='100%'><tr> <td>Numero de tramites</td> <td>Gestoria</td> </tr>");
						Iterator<?> iterGestorias = listaTramitesGestorCTITValidadosAFinalizados.iterator();
						while (iterGestorias.hasNext()) {
							Object[] detalle = (Object[]) iterGestorias.next();
							listado.append("<tr> <td>")
									.append(detalle[0].toString())
									.append("</td> <td>")
									.append(detalle[1].toString())
									.append("</td> </tr>");
						}
						listado.append("</table>");

						cadenaResultado.append(listado);
					}
					cadenaResultado.append("<p><em>Total expedientes que pasan de Validado Telematicamente a Finalizado PDF</em>: <strong>").append(detalleTramitesCTITValidadosAFinalizados).append("</strong></p>");
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes que pasan directamente de Validado PDF a Finalizado PDF", e);
				}

				/* ************************************ EXPEDIENTES PDF POR MOTIVO INDETERMINADO ************************************ */

				try {
					// Sumario - tabla para presentacion de datos
					cadenaResultado.append("<p>Expedientes PDF por motivo indeterminado:</p>");

					// Se reinician las variables para reutilizarlas
					listado.delete(0, listado.length());

					if(listaExpedientes!=null && listaExpedientes.size() > BigDecimal.ZERO.intValue()){

						listado.append("<p>Listado de Gestorias con Tramites PDF por motivo indeterminado</p>");

						List<Object> listaTramitesGestorIndefinidos = getModeloTrafico().obtenerListaTramitesGestorSobreExp(listaExpedientes);
						listado.append("<table border='1' width='100%'><tr> <td>Numero de tramites</td> <td>Gestoria</td> </tr>");
						Iterator<?> iterIndefinidos = listaTramitesGestorIndefinidos.iterator();
						while (iterIndefinidos.hasNext()) {
							Object[] detalle = (Object[]) iterIndefinidos.next();
							listado.append("<tr> <td>")
									.append(detalle[0].toString())
									.append("</td> <td>")
									.append(detalle[1].toString())
									.append("</td> </tr>");
						}
						listado.append("</table>");

						// Loggeo la lista de expedientes de este tipo
						if (log.isInfoEnabled()) {
							String expIndefinidos = "";
							for(int i = 0; i < listaExpedientes.size(); i++){
								expIndefinidos += listaExpedientes.get(i).toString() + " - ";
							}
							log.info(expIndefinidos);
						}

						cadenaResultado.append(listado);
					}
					cadenaResultado.append("<p><em>Total expedientes indeterminados</em>: <strong>").append(listaExpedientes.size()).append("</strong></p>");
				} catch (Exception e) {
					log.error("ENVIO_ESTADISTICAS_CTIT: Error al generar la estadistica de expedientes en Finalizado PDF por causa indeterminada", e);
				}

			}
			}
		}

		return cadenaResultado;
	}

	/* *********************************************************************** */
	/* MODELOS *************************************************************** */
	/* *********************************************************************** */
	// FIXME Si se implementa inyeccion por Spring quitar los ifs de los getters
	/* *********************************************************************** */

	public ModeloTrafico getModeloTrafico() {
		if (modeloTrafico == null) {
			modeloTrafico = new ModeloTrafico();
		}
		return modeloTrafico;
	}

	public void setModeloTrafico(ModeloTrafico modeloTrafico) {
		this.modeloTrafico = modeloTrafico;
	}

	private void guardarPrimeraMatricula(String matricula){

		StringBuffer xml = new StringBuffer();
		xml.append("<?xml version='1.0'?><info><matricula><b>" + matricula +"</b></matricula></info>");
		try {
			gestorDocumentos.guardarFichero(ConstantesGestorFicheros.FICHERO_PRIMERA_MATRICULA, null, null, "primera_matricula", ConstantesGestorFicheros.EXTENSION_XML, String.valueOf(xml).getBytes());
		} catch (OegamExcepcion e) {
			e.printStackTrace();
		}

	}

}