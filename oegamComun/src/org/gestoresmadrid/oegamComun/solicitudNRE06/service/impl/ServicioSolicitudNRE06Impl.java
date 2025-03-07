package org.gestoresmadrid.oegamComun.solicitudNRE06.service.impl;

import java.io.InputStream;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import org.gestoresmadrid.core.intervinienteTrafico.model.vo.IntervinienteTraficoVO;
import org.gestoresmadrid.core.model.enumerados.EstadoTramiteTrafico;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegamComun.contrato.service.ServicioComunContrato;
import org.gestoresmadrid.oegamComun.correo.service.ServicioComunCorreo;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.ResultadoSolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.beans.SolicitudNRE06Bean;
import org.gestoresmadrid.oegamComun.solicitudNRE06.service.ServicioNRE06Web;
import org.gestoresmadrid.oegamComun.solicitudNRE06.service.ServicioSolicitudNRE06;
import org.gestoresmadrid.oegamComun.trafico.service.ServicioComunTramiteTrafico;
import org.gestoresmadrid.oegamComun.utiles.CampoPdfBean;
import org.gestoresmadrid.oegamComun.utiles.ConstantesPDF;
import org.gestoresmadrid.oegamComun.utiles.PdfMaker;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioSolicitudNRE06Impl implements ServicioSolicitudNRE06 {

	private static final long serialVersionUID = 4196172712551878075L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioSolicitudNRE06Impl.class);
	private static final String TRAMITE_VISADO = "006";
	private static final String FECHA_PRESENTACION = "fecha presentacion";
	private static final String HORA_PRESENTACION = "hora presentacion";
	private static final String EXPEDIENTE_REF = "expediente referencia";
	private static final String CODIGO_ELECTRONICO = "codigo electronico";
	private static final String NIF_PRESENTADOR = "nif presentador";
	private static final String APELL_NOMBRE_PRESENTADOR = "apellidos nombre presentador";
	private static final String NIF_REPRESENTANTE = "nif representante";
	private static final String APELL_NOMBRE_REPRESENTANTE = "apellidos nombre representante";
	private static final String RESPUESTA_NRE = "respuesta NRE";
	private static final String TRAM_VISADO = "tramite visado";
	private static final String NIF_SUJETO = "nif sujeto";
	private static final String APELL_SUJETO = "apellidos sujeto";
	private static final String NOMBRE_SUJETO = "nombre sujeto";
	private static final String NIF_COLEGIADO = "nif colegiado";
	private static final String NOMBRE_COLEGIADO = "nombre colegiado";
	private static final String FECHA_VISADO = "fecha visado";
	private static final String BASTIDOR = "bastidor";
	private static final String REF_COLEGIO = "ref colegio";
	private static final String EN_CALIDAD = "en calidad";

	private static final String RUTA = "/trafico/plantillasPDF/SolicitudNRE06.pdf";

	@Autowired
	ServicioComunTramiteTrafico servicioTramiteTrafico;

	@Autowired
	ServicioNRE06Web servicioNRE06Web;

	@Autowired
	ServicioComunCorreo servicioComunCorreo;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	ServicioComunContrato servicioComunContrato;

	@Autowired
	GestorPropiedades gestorPropiedades;

	private String fileName;

	private InputStream inputStream;

	@Override
	public ResultadoSolicitudNRE06Bean solicitarNRE06(BigDecimal numExpediente,BigDecimal idUsuario) {
		ResultadoSolicitudNRE06Bean resultado = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		String nombreFichero = "NRE06";
		try {
			TramiteTraficoVO tramiteTraficoBBDD = servicioTramiteTrafico.getTramite(numExpediente, Boolean.TRUE);
			if (tramiteTraficoBBDD != null) {
				SolicitudNRE06Bean solicitudNRE06Bean = new SolicitudNRE06Bean();
				rellenarDatosSolicitud(tramiteTraficoBBDD,solicitudNRE06Bean);
				resultado = servicioNRE06Web.navegacionWeb(solicitudNRE06Bean, resultado);
				if (!resultado.getError()) {
					ResultadoSolicitudNRE06Bean result = servicioTramiteTrafico.guardarNRE(tramiteTraficoBBDD, resultado.getNre(), new Date());
					if (!result.getError()) {
						ResultadoSolicitudNRE06Bean resultGenPdf= generarPDF(solicitudNRE06Bean, resultado);
						if (!resultGenPdf.getError()) {
							byte[] byte1 = (byte[]) resultGenPdf.getAttachment(ResultadoSolicitudNRE06Bean.TIPO_PDF);
							gestorDocumentos.guardarFichero(ConstantesGestorFicheros.AEAT, ConstantesGestorFicheros.SOLICITUDES_NRE06, utilesFecha.getFechaConDate(tramiteTraficoBBDD.getFechaPresentacion()), nombreFichero +"_"+tramiteTraficoBBDD.getNumExpediente(), ConstantesGestorFicheros.EXTENSION_PDF, byte1);
							resultado = enviarMail(tramiteTraficoBBDD);
						} else {
							resultado.setError(Boolean.TRUE);
							resultado.setMensaje("No se ha podido guardar el pdf generado.");
						}
						servicioTramiteTrafico.cambiarEstado(tramiteTraficoBBDD.getNumExpediente(),new BigDecimal(EstadoTramiteTrafico.TramitadoNRE06.getValorEnum()),idUsuario.longValue());
					} else {
						resultado.setError(Boolean.TRUE);
						resultado.setMensaje("No se ha podido guardar el NRE en la tabla Tramite.");
					}
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Hubo un problema al realizar la navegación web.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha podido recuperar el trámite con número expediente: " +numExpediente);
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Ha sucedido un error al solicitar NRE06, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al solicitar NRE06.");
		}
		return resultado;
	}

	private ResultadoSolicitudNRE06Bean enviarMail(TramiteTraficoVO tramiteTraficoBBDD) {
		ResultadoSolicitudNRE06Bean resultado = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		String direccion = tramiteTraficoBBDD.getContrato().getCorreoElectronico();
		String direccionCC = null;
		String dirOculta = gestorPropiedades.valorPropertie("nre06.direccionOcultaEnvioMail");
		String subject = "Solicitud Justificante NRE06";
		try {
			StringBuffer sb = new StringBuffer();
			sb.append("Justificante de Solicitud NRE06 para el tramite con numero de expediente: " +tramiteTraficoBBDD.getNumExpediente());
			sb.append("<br>");
			sb.append("Para descargar el documento debe ir a la Consulta de tramites e imprimir documentos.");
			ResultadoBean resultEnvio = servicioComunCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccion, direccionCC, dirOculta, null, null);
			if (resultEnvio.getError()) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje(resultEnvio.getMensaje());
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error al al enviar mail con el pdf NRE06, error: ",e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error al al enviar mail con el pdf NRE06.");
		}
		return resultado;
	}

	private void rellenarDatosSolicitud(TramiteTraficoVO tramiteTraficoBBDD, SolicitudNRE06Bean solicitudNRE06Bean) {
		solicitudNRE06Bean.setTramiteVisado(TRAMITE_VISADO);
		solicitudNRE06Bean.setNifTitular(getNifTitular(tramiteTraficoBBDD));
		solicitudNRE06Bean.setApellRazonSocialTitular(getApeRazonSocialTitular(tramiteTraficoBBDD));
		solicitudNRE06Bean.setNombreTitular(getNombreTitular(tramiteTraficoBBDD));
		solicitudNRE06Bean.setNifColegiado(getNifColegiado(tramiteTraficoBBDD));
		solicitudNRE06Bean.setApellRazonSocialColegiado(getApeRazonSocialColegiado(tramiteTraficoBBDD));
		solicitudNRE06Bean.setNombreColegiado(getNombreColegiado(tramiteTraficoBBDD));
		solicitudNRE06Bean.setFechaVisado(getFechaPresentacion(tramiteTraficoBBDD));
		solicitudNRE06Bean.setRefColegio(tramiteTraficoBBDD.getNumExpediente().toString());
		solicitudNRE06Bean.setNumBastidor(tramiteTraficoBBDD.getVehiculo().getBastidor());
	}

	private String getFechaPresentacion(TramiteTraficoVO tramiteTraficoBBDD) {
		SimpleDateFormat formatterFecha = new SimpleDateFormat("yyyyMMdd");
		String fechaPresentacion = "";
		if (tramiteTraficoBBDD.getFechaPresentacion() != null) {
			fechaPresentacion = formatterFecha.format(tramiteTraficoBBDD.getFechaPresentacion());
		}
		return fechaPresentacion;
	}

	private String getNombreColegiado(TramiteTraficoVO tramiteTraficoBBDD) {
		String nombreColegiado = " ";
		if (tramiteTraficoBBDD.getContrato() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado().getUsuario() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado().getUsuario().getApellidosNombre() != null) {
			String[] nombCol = tramiteTraficoBBDD.getContrato().getColegiado().getUsuario().getApellidosNombre().split(",");
			nombreColegiado = nombCol[1].trim();
		}
		if (!nombreColegiado.equals(" ") && nombreColegiado.length() > 30) {
			nombreColegiado = nombreColegiado.substring(0, 30);
		}

		return nombreColegiado;
	}

	private String getApeRazonSocialColegiado(TramiteTraficoVO tramiteTraficoBBDD) {
		String apeRazonSocialColegiado = " ";
		if (tramiteTraficoBBDD.getContrato() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado().getUsuario() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado().getUsuario().getApellidosNombre() != null) {
			String[] apellRSocial = tramiteTraficoBBDD.getContrato().getColegiado().getUsuario().getApellidosNombre().split(",");
			apeRazonSocialColegiado = apellRSocial[0];
		}
		if (!apeRazonSocialColegiado.equals(" ") && apeRazonSocialColegiado.length() > 30) {
			apeRazonSocialColegiado = apeRazonSocialColegiado.substring(0, 30);
		}

		return apeRazonSocialColegiado;
	}

	private String getNifColegiado(TramiteTraficoVO tramiteTraficoBBDD) {
		String nifColegiado = " ";
		if (tramiteTraficoBBDD.getContrato() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado() != null
				&& tramiteTraficoBBDD.getContrato().getColegiado().getUsuario() != null) {
			nifColegiado = tramiteTraficoBBDD.getContrato().getColegiado().getUsuario().getNif();
		}
		return nifColegiado;
	}

	private String getNombreTitular(TramiteTraficoVO tramiteTraficoBBDD) {
		String nombreTitular = " ";
		for (IntervinienteTraficoVO interviniente : tramiteTraficoBBDD.getIntervinienteTraficosAsList()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Titular.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente()) ||
					TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Adquiriente.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente())) {
				if (interviniente.getPersona().getNombre() != null) {
					nombreTitular = interviniente.getPersona().getNombre();
				}
			}
		}
		if (!nombreTitular.equals(" ") && nombreTitular.length() > 15) {
			nombreTitular = nombreTitular.substring(0, 15);
		}
		return nombreTitular;
	}

	private String getApeRazonSocialTitular(TramiteTraficoVO tramiteTraficoBBDD) {
		String apeRazonSocialTitular = " ";
		for (IntervinienteTraficoVO interviniente : tramiteTraficoBBDD.getIntervinienteTraficosAsList()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Titular.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente()) ||
					TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Adquiriente.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente())) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getApellido2() != null) {
					apeRazonSocialTitular = interviniente.getPersona().getApellido1RazonSocial() + " " + interviniente.getPersona().getApellido2();
				} else {
					apeRazonSocialTitular = interviniente.getPersona().getApellido1RazonSocial();
				}
			}
		}
		if (!apeRazonSocialTitular.equals(" ") && apeRazonSocialTitular.length() > 30) {
			apeRazonSocialTitular = apeRazonSocialTitular.substring(0, 30);
		}

		return apeRazonSocialTitular;
	}

	private String getNifTitular(TramiteTraficoVO tramiteTraficoBBDD) {
		String nifTitular = " ";
		for (IntervinienteTraficoVO interviniente : tramiteTraficoBBDD.getIntervinienteTraficosAsList()) {
			if (TipoTramiteTrafico.Matriculacion.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Titular.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente()) ||
					TipoTramiteTrafico.TransmisionElectronica.getValorEnum().equals(tramiteTraficoBBDD.getTipoTramite()) && TipoInterviniente.Adquiriente.getValorEnum().equals(interviniente.getTipoIntervinienteVO().getTipoInterviniente())) {
				if (interviniente.getPersona() != null && interviniente.getPersona().getId() != null) {
					nifTitular = interviniente.getPersona().getId().getNif();
				}
			}
		}
		return nifTitular;
	}

	private ResultadoSolicitudNRE06Bean generarPDF(SolicitudNRE06Bean solicitudNRE06Bean, ResultadoSolicitudNRE06Bean resultado) {
		ArrayList<CampoPdfBean> camposFormateados = new ArrayList<>();
		ResultadoSolicitudNRE06Bean resultadoMetodo = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		PdfMaker pdf = new PdfMaker();
		Utiles util = new Utiles();
		String file = util.getFilePath(RUTA);
		byte[] byte1 = pdf.abrirPdf(file);
		Set<String> camposPlantilla = pdf.getAllFields(byte1);

		if(camposPlantilla.contains(NIF_COLEGIADO)) {
			String campo = solicitudNRE06Bean.getNifColegiado();
			CampoPdfBean campoAux = new CampoPdfBean(NIF_COLEGIADO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NOMBRE_COLEGIADO)) {
			String campo = solicitudNRE06Bean.getNombreColegiado();
			CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_COLEGIADO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(FECHA_VISADO)) {
			String campo = solicitudNRE06Bean.getFechaVisado();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_VISADO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(BASTIDOR)) {
			String campo = solicitudNRE06Bean.getNumBastidor();
			CampoPdfBean campoAux = new CampoPdfBean(BASTIDOR, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(REF_COLEGIO)) {
			String campo = solicitudNRE06Bean.getRefColegio();
			CampoPdfBean campoAux = new CampoPdfBean(REF_COLEGIO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(FECHA_PRESENTACION)) {
			//cambiar formato para meter fecha y hora
			String campo = resultado.getFecha();
			CampoPdfBean campoAux = new CampoPdfBean(FECHA_PRESENTACION, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(HORA_PRESENTACION)) {
			String campo = resultado.getHora();
			CampoPdfBean campoAux = new CampoPdfBean(HORA_PRESENTACION, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NIF_PRESENTADOR)) {
			//datos del presentador son los del colegio Q2861007I
			
			String campo = "Q2861007I";
			CampoPdfBean campoAux = new CampoPdfBean(NIF_PRESENTADOR, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(APELL_NOMBRE_PRESENTADOR)) {
			// COLEGIO OFICIAL DE GESTORES ADMINISTRATIVOS DE MADRID
			
			String campo = "COLEGIO OFICIAL DE GESTORES ADMINISTRATIVOS DE MAD";
			CampoPdfBean campoAux = new CampoPdfBean(APELL_NOMBRE_PRESENTADOR, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NIF_REPRESENTANTE)) {
			//NIF REPRE 00821563A 
			String campo = "00821563A";
			CampoPdfBean campoAux = new CampoPdfBean(NIF_REPRESENTANTE, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(APELL_NOMBRE_REPRESENTANTE)) {
			//SANTIAGO OLLERO FERNANDO JESUS
			String campo = "SANTIAGO OLLERO FERNANDO JESUS";
			CampoPdfBean campoAux = new CampoPdfBean(APELL_NOMBRE_REPRESENTANTE, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(RESPUESTA_NRE)) {
			String campo = resultado.getNre();
			CampoPdfBean campoAux = new CampoPdfBean(RESPUESTA_NRE, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(TRAM_VISADO)) {
			String campo = solicitudNRE06Bean.getTramiteVisado();
			CampoPdfBean campoAux = new CampoPdfBean(TRAM_VISADO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NIF_SUJETO)) {
			String campo = solicitudNRE06Bean.getNifTitular();
			CampoPdfBean campoAux = new CampoPdfBean(NIF_SUJETO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(APELL_SUJETO)) {
			String campo = solicitudNRE06Bean.getApellRazonSocialTitular();
			CampoPdfBean campoAux = new CampoPdfBean(APELL_SUJETO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(NOMBRE_SUJETO)) {
			String campo = solicitudNRE06Bean.getNombreTitular();
			CampoPdfBean campoAux = new CampoPdfBean(NOMBRE_SUJETO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(EXPEDIENTE_REF)) {
			String campo = resultado.getReg();
			CampoPdfBean campoAux = new CampoPdfBean(EXPEDIENTE_REF, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(CODIGO_ELECTRONICO)) {
			String campo = resultado.getCodElect();
			CampoPdfBean campoAux = new CampoPdfBean(CODIGO_ELECTRONICO, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		if(camposPlantilla.contains(EN_CALIDAD)) {
			String campo = "COLABORADOR";
			CampoPdfBean campoAux = new CampoPdfBean(EN_CALIDAD, campo, true, false, ConstantesPDF._8);
			camposFormateados.add(campoAux);
		}
		byte1 = pdf.setCampos(byte1, camposFormateados);
		resultadoMetodo.setError(false);
		resultadoMetodo.addAttachment("pdf", byte1);
		return resultadoMetodo;
	}

	@Override
	public ResultadoSolicitudNRE06Bean generarResumen() throws ParseException {
		return enviarResumenPorMail();
	}

	private ResultadoSolicitudNRE06Bean enviarResumenPorMail() throws ParseException {
		ResultadoSolicitudNRE06Bean resultado = new ResultadoSolicitudNRE06Bean(Boolean.FALSE);
		StringBuffer sb = datosResumen();
		String direccion = gestorPropiedades.valorPropertie("nre06.direccionEnvioMailResumen");
		String dirOculta = gestorPropiedades.valorPropertie("nre06.direccionOcultaEnvioMail");
		String subject = "Resumen generación NRE06";
		ResultadoBean resultEnvio = servicioComunCorreo.enviarCorreo(sb.toString(), Boolean.FALSE, null, subject, direccion, null, dirOculta, null, null);
		if (resultEnvio.getError()) {
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(resultEnvio.getMensaje());
		}
		return resultado;
	}

	private StringBuffer datosResumen() throws ParseException {
		StringBuffer resultadoHtml = new StringBuffer();
		List<Object[]> listaTramites = servicioTramiteTrafico.getTramiteNRE();
		resultadoHtml.append("Resumen Generación NRE06")
		.append(".<br></br>");
		resultadoHtml.append("<table border='1'> <span style=\"font-size:12pt;font-family:Tunga;margin-left:3px;\"><tr> <th>Num. Colegiado</th> <th>TOTAL</th> <th>Fecha Registro</th> </tr> </span> ");
		for (Object[] object : listaTramites) {
			resultadoHtml.append("<tr>");
			resultadoHtml.append("<td>"+ ((String)object[1]) +"</td>");
			resultadoHtml.append("<td>"+ ((int)object[0]) +"</td>");
			resultadoHtml.append("<td>"+ utilesFecha.formatoFecha((Timestamp)object[2]) +"</td>");
			resultadoHtml.append("</tr>");
		}
		resultadoHtml.append("</table>");
		return resultadoHtml;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}
}