package org.gestoresmadrid.procesos.model.jobs;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import javax.xml.bind.Unmarshaller;

import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.model.beans.ColaBean;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.oegam2comun.correo.model.service.ServicioCorreo;
import org.gestoresmadrid.oegam2comun.importacion.model.service.ServicioImportar;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGABajaDtoConversion;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGAMatwDtoConversion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.proceso.model.service.ServicioProcesos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFFNuevo;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.quartz.JobExecutionException;
import org.springframework.beans.factory.annotation.Autowired;

import colas.constantes.ConstantesProcesos;
import colas.daos.EjecucionesProcesosDAO;
import colas.procesos.utiles.UtilesProcesos;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import hibernate.entities.general.Colegiado;
import oegam.constantes.ConstantesPQ;
import oegam.constantes.ConstantesSession;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA;
import trafico.beans.utiles.FORMATOGAtransmisionPQConversion;
import trafico.modelo.ModeloImportacionDGT;
import trafico.utiles.XMLBajaFactory;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.XMLTransmisionFactory;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import utilidades.web.excepciones.CambiarEstadoTramiteTraficoExcepcion;
import utilidades.web.excepciones.SinSolicitudesExcepcion;
import utilidades.web.excepciones.TramiteNoRecuperadoExcepcion;
import utilidades.web.excepciones.XmlNoValidoExcepcion;

/**
 * Proceso para la importación masiva de trámites
 */
public class ProcesoImportacionesMasivas extends AbstractProcesoBase {

	private static ILoggerOegam log = LoggerOegam.getLogger(ProcesoImportacionesMasivas.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioProcesos servicioProcesos;

	@Autowired
	private ServicioEEFFNuevo servicioEEFF;

	@Autowired
	private ServicioPermisos servicioPermisos;

	@Autowired
	private ServicioImportar servicioImportacion;

	@Autowired
	private ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	private FORMATOGAMatwDtoConversion formatoMatwDtoConversion;

	@Autowired
	private FORMATOGABajaDtoConversion formatoBajaDtoConversion;
	
	@Autowired
	private ServicioCorreo servicioCorreo;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	private EjecucionesProcesosDAO ejecucionesProcesosDAO;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private String numColegiado;
	private String tipoImportacion;
	private BigDecimal contratoImportacion;
	private BigDecimal idContrato;
	private ContratoVO contrato;
	private Fecha fechaFichero;

	private ModeloImportacionDGT modeloImportacionDGT;

	private UtilesProcesos utilesProcesos;
	
	private static final String MAIL_HABILITADO = "importacionesMasivas.correo.habilitado";
	private static final String RECIPIENT = "importacionesMasivas.correo.recipient";
	private static final String DIRECCIONES_OCULTAS = "importacionesMasivas.correo.direccionesOcultas";

	private StringBuffer resumen = new StringBuffer();

	@Override
	public void doExecute() throws JobExecutionException {
		// Hilo/cola en ejecución:
		log.error("INICIO PROCESO IMPORTACIONES MASIVAS");
		ColaBean colaBean = new ColaBean();
		Colegiado colegiado;
		boolean errorImportacion = false;
		boolean recuperable = false;

		try {
			log.error("Proceso " + ConstantesProcesos.IMPORTACION_MASIVA + " -- Buscando Solicitud");
			// Recupera la solicitud de la tabla cola:
			colaBean = tomarSolicitud();
			if (colaBean == null) {
				throw new SinSolicitudesExcepcion("No existe ninguna solicitud para la cola");
			}
			log.error("Proceso " + ConstantesProcesos.IMPORTACION_MASIVA + " -- Solicitud " + ConstantesProcesos.IMPORTACION_MASIVA + " encontrada");

			String[] separadorExtension = colaBean.getXmlEnviar().split("\\.");
			obtenerParametros(separadorExtension[0]);

			contrato = utilesColegiado.getContratoDelColegiado(idContrato);
			colegiado = utilesColegiado.getColegiado(numColegiado);

			File ficheroXml = gestorDocumentos.buscarFicheroPorNombreTipoYDia(ConstantesGestorFicheros.IMPORTACION_MASIVA, ConstantesGestorFicheros.PETICIONES, fechaFichero, separadorExtension[0],
					".xml");

			if (ConstantesSession.XML_TRANSMISION_ELECTRONICA.equals(tipoImportacion)) {
				errorImportacion = transmisionXML(ficheroXml, colaBean.getIdUsuario(), true);
			} else if (ConstantesSession.XML_MATRICULACION_MATW.equals(tipoImportacion)) {
				errorImportacion = matriculacionMatwXML(ficheroXml, colaBean.getIdUsuario());
			} else if (ConstantesSession.XML_BAJA.equals(tipoImportacion)) {
				errorImportacion = bajaXML(ficheroXml, colaBean.getIdUsuario());
			}

			String textoNotificacion = "";
			if (errorImportacion) {
				textoNotificacion = "IMPORTACIÓN MASIVA FINALIZADA CON ERRORES";
			} else {
				textoNotificacion = "IMPORTACIÓN MASIVA FINALIZADA";
			}

			colaBean.setRespuesta(textoNotificacion);
			String tipoEjecucion = ConstantesProcesos.EJECUCION_CORRECTA;

			getUtilesProcesos().creaNotificacion(colaBean, textoNotificacion);
			// Si el idUsuario es distinto al idUsuario del colegiado se crea
			// otra notificación para el colegiado
			if (!colaBean.getIdUsuario().equals(new BigDecimal(colegiado.getUsuario().getIdUsuario()))) {
				getUtilesProcesos().creaNotificacionColegiado(colaBean, textoNotificacion, colegiado.getUsuario());
			}
			peticionCorrecta();
			if (ficheroXml != null) {
				gestorDocumentos.borradoRecursivo(ficheroXml);
			}
			getModeloSolicitud().borrarSolicitud(colaBean.getIdEnvio(), textoNotificacion);

			enviarCorreo(errorImportacion);

			ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, tipoEjecucion ,textoNotificacion,ConstantesProcesos.IMPORTACION_MASIVA);
			//ejecucionesProcesosDAO.actualizarUltimaEjecucion(colaBean, tipoEjecucion, null);
		} catch (SinSolicitudesExcepcion sinSolicitudesExcepcion) {
			
			sinPeticiones();
		} catch (TramiteNoRecuperadoExcepcion tramiteNoRecuperadoExcepcion) {
			recuperable = servicioProcesos.tratarRecuperable(colaBean, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.NO_SE_PUEDE_RECUPERAR_LA_INFORMACIÓN_DEL_TRÁMITE_NECESARIA_PARA_INVOCAR_EL_SERVICIO);
		} catch (CambiarEstadoTramiteTraficoExcepcion cambiarEstadoTramiteTraficoExcepcion) {
			recuperable = servicioProcesos.tratarRecuperable(colaBean, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, ConstantesProcesos.ERROR_AL_CAMBIAR_ESTADO);
		} catch (XmlNoValidoExcepcion e) {
			String error = e.getMensajeError1();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			recuperable = servicioProcesos.tratarRecuperable(colaBean, "Proceso Importaciones Masivas, error al validar el XML" + " : " + error);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, "Proceso Importaciones Masivas, error al validar el XML" + " : " + error);
		} catch (OegamExcepcion oegamEx) {
			String error = oegamEx.getMensajeError1();
			if (error == null || error.equals("")) {
				error = oegamEx.toString();
			}
			recuperable = servicioProcesos.tratarRecuperable(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Exception e) {
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			recuperable = servicioProcesos.tratarRecuperable(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		} catch (Throwable e) {
			String error = e.getMessage();
			if (error == null || error.equals("")) {
				error = e.toString();
			}
			recuperable = servicioProcesos.tratarRecuperable(colaBean, ConstantesProcesos.ERROR_INTERNO_DEL_SERVIDOR + " : " + error);
			if (!recuperable) {
				errorNoRecuperable();
			} else {
				peticionRecuperable();
			}
			actualizarUltimaEjecucion(colaBean, ConstantesProcesos.EJECUCION_EXCEPCION, error);
		}
	}

	@Override
	protected String getProceso() {
		return ProcesosEnum.IMPORTACIONMASIVA.getNombreEnum();
	}

	private boolean transmisionXML(File ficheroXml, BigDecimal idUsuario, boolean electronica) throws Throwable {
		boolean errorImportacion = false;

		XMLTransmisionFactory xMLTransmisionFactory = new XMLTransmisionFactory();
		trafico.beans.jaxb.transmision.FORMATOGA ga = xMLTransmisionFactory.getFormatoOegam2Transmision(new FileInputStream(ficheroXml.getAbsoluteFile()));

		List<BeanPQTramiteTransmisionImport> beansAlta = FORMATOGAtransmisionPQConversion.convertirFORMATOGAtoPQ(ga, true, idUsuario, idContrato);
		// Realizar la importación
		// Permisos
		if (utilesColegiado.tienePermisoAdmin()) {
			for (int i = 0; i < beansAlta.size(); i++) {
				errorImportacion = procesarGATransmision((BeanPQTramiteTransmisionImport) beansAlta.get(i), idUsuario, i, null, electronica);
			}

		} else if (utilesColegiado.tienePermisoColegio()) {
			List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
			Boolean puede = false;
			for (int i = 0; i < beansAlta.size(); i++) {
				int j = 0;
				while ((!puede) && (listaColegiados.size() > j)) {
					puede = puede || beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO().equals(listaColegiados.get(j));
					j++;
				}
				if (puede) {
					errorImportacion = procesarGATransmision((BeanPQTramiteTransmisionImport) beansAlta.get(i), idUsuario, i, null, electronica);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO() + ")</font><br>");
					errorImportacion = true;
					break;
				}
				puede = false;
			}
		} else {
			for (int i = 0; i < beansAlta.size(); i++) {
				if (numColegiado.equals(beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO())) {
					errorImportacion = procesarGATransmision((BeanPQTramiteTransmisionImport) beansAlta.get(i), idUsuario, i, null, electronica);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getBeanGuardarTransmision().getP_NUM_COLEGIADO() + ")</font><br>");
					errorImportacion = true;
				}
			}
		}
		return errorImportacion;
	}

	private boolean matriculacionMatwXML(File ficheroXml, BigDecimal idUsuario) throws Throwable {
		boolean errorImportacion = false;
		boolean tienePermisoIVTM = false;
		boolean tienePermisoLiberarEEFF = false;

		Unmarshaller unmarshaller = new XMLGAFactory().getMatriculacionMatWContext().createUnmarshaller();
		trafico.beans.jaxb.matw.FORMATOGA ga = (trafico.beans.jaxb.matw.FORMATOGA) unmarshaller.unmarshal(new FileInputStream(ficheroXml.getAbsoluteFile()));

		tienePermisoLiberarEEFF = servicioPermisos.tienePermisoElContrato(idContrato.longValue(), ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF, UtilesColegiado.APLICACION_OEGAM_TRAF);
		
		List<TramiteTrafMatrDto> beansAlta = formatoMatwDtoConversion.convertirFORMATOGAtoPQ(ga, idContrato, contratoImportacion,tienePermisoLiberarEEFF);

		// Realizar la importación
		// Permisos
		tienePermisoIVTM = servicioPermisos.tienePermisoElContrato(idContrato.longValue(), UtilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM, UtilesColegiado.APLICACION_OEGAM_TRAF);

		if (utilesColegiado.tienePermisoAdmin()) {
			for (int i = 0; i < beansAlta.size(); i++) {
				if (beansAlta.get(i) != null && beansAlta.get(i).getVehiculoDto() != null && beansAlta.get(i).getVehiculoDto().getImportado() != null
						&& beansAlta.get(i).getVehiculoDto().getImportado()
						&& (beansAlta.get(i).getVehiculoDto().getVehiUsado() == null || beansAlta.get(i).getVehiculoDto().getVehiUsado() != null && !beansAlta.get(i).getVehiculoDto().getVehiUsado())) {
					resumen.append("<font color=\"red\">Cuando un vehículo se marca como importado obligatoriamente tiene que ser marcado como usado. </font><br>");
					errorImportacion = true;
				} else {
					ResultBean resultado = servicioIvtmMatriculacion.validarFORMATOIVTMGA(ga, tienePermisoIVTM);
					if (resultado != null && !resultado.getError()) {
						if (tienePermisoLiberarEEFF) {
							resultado = servicioEEFF.validarEeffLibMatwFORMATOGA(ga.getMATRICULACION().get(i));
						}
						if (resultado != null && !resultado.getError()) {
							beansAlta.get(i).setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
							ResultBean result = servicioImportacion.importarMatriculacion(beansAlta.get(i), idUsuario,tienePermisoLiberarEEFF,Boolean.FALSE);

							if (result.getError()) {
								resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> " + result.getMensaje() + "</font><br>");
								errorImportacion = true;
							} else {
								BigDecimal numExpediente = (BigDecimal) result.getAttachment("numExpediente");
								ResultBean resultadoEEFF = servicioEEFF.guardarDatosImportacion(ga.getMATRICULACION().get(i), numExpediente);
								resumen.append("<font color=\"green\"><b>- Trámite " + i + ":</b> " + result.getMensaje() + "</font><br>");
								if (null != resultadoEEFF) {
									resumen.append("<font color=\"green\">-EEFF Liberación ");
									for (String mensaje : resultadoEEFF.getListaMensajes()) {
										resumen.append("- " + mensaje);
									}
									resumen.append("</font><br>");
								}
							}
						} else {
							resumen.append("<font color=\"red\">-EEFF Liberación ");
							if (null != resultado) {
								for (String mensaje : resultado.getListaMensajes()) {
									resumen.append("- " + mensaje);
								}
							}
							resumen.append("</font><br>");
							errorImportacion = true;
						}
					}
				}
			}
		} else if (utilesColegiado.tienePermisoColegio()) {
			List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
			Boolean puede = false;
			for (int i = 0; i < beansAlta.size(); i++) {
				int j = 0;
				while ((!puede) && (listaColegiados.size() > j)) {
					puede = puede || beansAlta.get(i).getNumColegiado().equals(listaColegiados.get(j));
					j++;
				}
				if (puede) {
					procesarGAMatriculacion(beansAlta.get(i), idUsuario, i, true);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getNumColegiado() + ")</font><br>");
					errorImportacion = true;
					break;
				}
				puede = false;
			}
		} else {
			for (int i = 0; i < beansAlta.size(); i++) {
				if (beansAlta.get(i).getNumColegiado().equals(numColegiado)) {
					procesarGAMatriculacion(beansAlta.get(i), idUsuario, i, true);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getNumColegiado() + ")</font><br>");
					errorImportacion = true;
				}
			}
		}
		return errorImportacion;
	}

	private boolean bajaXML(File ficheroXml, BigDecimal idUsuario) throws Throwable {
		boolean errorImportacion = false;

		XMLBajaFactory xMLBajaFactory = new XMLBajaFactory();
		FORMATOOEGAM2BAJA ga = xMLBajaFactory.getFormatoOegam2Baja(ficheroXml.getAbsoluteFile());

		List<TramiteTrafBajaDto> beansAlta = formatoBajaDtoConversion.convertirFORMATOGAtoDto(ga, idContrato, null);

		// Realizar la importación
		// Permisos
		if (utilesColegiado.tienePermisoAdmin()) {
			for (int i = 0; i < beansAlta.size(); i++) {
				procesarGABaja(beansAlta.get(i), idUsuario, i);
			}
		} else if (utilesColegiado.tienePermisoColegio()) {
			List<String> listaColegiados = utilesColegiado.getNumColegiadosDelContrato();
			Boolean puede = false;
			for (int i = 0; i < beansAlta.size(); i++) {
				int j = 0;
				while ((!puede) && (listaColegiados.size() > j)) {
					puede = puede || beansAlta.get(i).getNumColegiado().equals(listaColegiados.get(j));
					j++;
				}
				if (puede) {
					procesarGABaja(beansAlta.get(i), idUsuario, i);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getNumColegiado() + ")</font><br>");
					errorImportacion = true;
					break;
				}
				puede = false;
			}
		} else {
			for (int i = 0; i < beansAlta.size(); i++) {
				if (numColegiado.equals(beansAlta.get(i).getNumColegiado())) {
					procesarGABaja(beansAlta.get(i), idUsuario, i);
				} else {
					resumen.append("<font color=\"red\"><b>- Trámite " + i + ":</b> El usuario (" + numColegiado + ") no tiene permisos para realizar la importación para el colegiado del fichero ("
							+ beansAlta.get(i).getNumColegiado() + ")</font><br>");
					errorImportacion = true;
				}
			}
		}
		return errorImportacion;
	}

	private void enviarCorreo(boolean errorImportacion) {
		String subject = null;
		String recipent = null;
		String direccionesOcultas = null;

		try {

			StringBuffer texto = new StringBuffer("<span style=\"font-size:15pt;font-family:Tunga;margin-left:20px;\">");

			if (errorImportacion) {
				subject = "Importación Masiva Finalizada (con errores)";
				texto.append("La importación masiva de " + getDescripcionTipoImportacion() + " contiene errores. <br><b><u>Resumen:</u></b><br>");
			} else {
				subject = "Importación Masiva Finalizada";
				texto.append("La importación masiva de " + getDescripcionTipoImportacion() + " se ha finalizado con éxito. <br><b><u>Resumen:</u></b><br>");
			}

			String entorno = gestorPropiedades.valorPropertie("Entorno");
			if (!"PRODUCCION".equals(entorno)) {
				subject = entorno + ": " + subject;
			}

			texto.append(resumen);

			texto.append("<br></span>");

			String correoHabilitado = gestorPropiedades.valorPropertie(MAIL_HABILITADO);
			if (correoHabilitado != null && !"".equals(correoHabilitado) && correoHabilitado.equals("SI")) {
				recipent = contrato.getCorreoElectronico();
				direccionesOcultas = gestorPropiedades.valorPropertie(DIRECCIONES_OCULTAS);
			} else {
				recipent = gestorPropiedades.valorPropertie(RECIPIENT);
			}

			ResultBean resultado;
			resultado = servicioCorreo.enviarCorreo(texto.toString(), null, null, subject, recipent, null, direccionesOcultas, null, null);

			if (resultado.getError()) {
				log.error("No se ha enviado el correo para las importaciones masivas. Error: " + resultado.getMensaje());
			}
		} catch (OegamExcepcion | IOException e) {
			log.error("No se enviaron correos de la importación masiva", e);
		}
	}

	private void obtenerParametros(String nombreFichero) throws Exception {
		String[] separador = nombreFichero.split("-");
		numColegiado = separador[0];
		idContrato = new BigDecimal(separador[1]);
		contratoImportacion = new BigDecimal(separador[2]);
		tipoImportacion = separador[3];
		String fechaHora = separador[4];
		Date fecha = utilesFecha.getFechaHoraDate(fechaHora);
		fechaFichero = utilesFecha.getFechaTimeStampConDate(fecha);
	}

	private boolean procesarGATransmision(BeanPQTramiteTransmisionImport beanAlta, BigDecimal idUsuario, int numTramite, String token, boolean electronica) throws OegamExcepcion {
		boolean errorImport = false;
		HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport(beanAlta, contratoImportacion, idUsuario, idContrato, TipoCreacion.XML);
		if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
			resumen.append("<font color=\"red\"><b>- Trámite " + numTramite + ":</b> " + ((ResultBean) resultado.get("ResultBean")).getMensaje() + "</font><br>");
			errorImport = true;
		} else {
			getModeloImportacionDGT().controlImportacion((BigDecimal) resultado.get(ConstantesPQ.NUM_EXPEDIENTE), token);
			resumen.append("<font color=\"green\"><b>- Trámite " + numTramite + ":</b> " + ((ResultBean) resultado.get("ResultBean")).getMensaje() + "</font><br>");
		}
		return errorImport;
	}

	private boolean procesarGAMatriculacion(TramiteTrafMatrDto tramiteTrafMatrDto, BigDecimal idUsuario, int numTramite, boolean matw) throws OegamExcepcion {
		boolean errorImport = false;
		ResultBean resultado = null;

		Boolean tienePermisoLiberarEEFF = servicioPermisos.tienePermisoElContrato(idContrato.longValue(), ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF, UtilesColegiado.APLICACION_OEGAM_TRAF);

		if (matw) {
			tramiteTrafMatrDto.setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
			resultado = servicioImportacion.importarMatriculacion(tramiteTrafMatrDto, idUsuario,tienePermisoLiberarEEFF,Boolean.FALSE);
		}

		if (resultado.getError()) {
			resumen.append("<font color=\"red\"><b>- Trámite " + numTramite + ":</b> " + resultado.getMensaje() + "</font><br>");
			errorImport = true;
		} else {
			resumen.append("<font color=\"green\"><b>- Trámite " + numTramite + ":</b> " + resultado.getMensaje() + "</font><br>");
		}
		return errorImport;
	}

	private boolean procesarGABaja(TramiteTrafBajaDto beanAlta, BigDecimal idUsuario, int numTramite) throws OegamExcepcion {
		boolean errorImport = false;

		beanAlta.setIdTipoCreacion(new BigDecimal(TipoCreacion.XML.getValorEnum()));
		Boolean tienePermisoBtv = servicioPermisos.tienePermisoElContrato(idContrato.longValue(), UtilesColegiado.PERMISO_BTV, UtilesColegiado.APLICACION_OEGAM_TRAF);
		ResultBean result = servicioImportacion.importarBaja(beanAlta, idUsuario,tienePermisoBtv, Boolean.FALSE);

		if (result.getError()) {
			String mensajes = "";
			for (String mensaje : result.getListaMensajes()) {
				mensajes = mensajes + mensaje + " ";
			}
			resumen.append("<font color=\"red\"><b>- Trámite " + numTramite + ":</b> " + mensajes + "</font><br>");
			errorImport = true;
		} else {
			resumen.append("<font color=\"green\"><b>- Trámite " + numTramite + ":</b> " + result.getMensaje() + "</font><br>");
		}
		return errorImport;
	}

	private String getDescripcionTipoImportacion() {
		String descripcion = "";

		if (ConstantesSession.XML_TRANSMISION.equals(tipoImportacion)) {
			return "Transmisiones";
		} else if (ConstantesSession.XML_TRANSMISION_ELECTRONICA.equals(tipoImportacion)) {
			return "Transmisión Eléctrocnica";
		} else if (ConstantesSession.XML_MATRICULACION_MATW.equals(tipoImportacion)) {
			return "Matriculaciones Matw";
		} else if (ConstantesSession.XML_MATRICULACION.equals(tipoImportacion)) {
			return "Matriculaciones";
		} else if (ConstantesSession.XML_MATRICULACION_ANTIGUO.equals(tipoImportacion)) {
			return "Matriculaciones Antiguas";
		} else if (ConstantesSession.XML_BAJA.equals(tipoImportacion)) {
			return "Bajas";
		}

		return descripcion;
	}

	/* ***************************************************************** */
	/* MODELOS ********************************************************* */
	/* ***************************************************************** */

	public UtilesProcesos getUtilesProcesos() {
		if (utilesProcesos == null) {
			utilesProcesos = new UtilesProcesos();
		}
		return utilesProcesos;
	}

	public void setUtilesProcesos(UtilesProcesos utilesProcesos) {
		this.utilesProcesos = utilesProcesos;
	}

	public ModeloImportacionDGT getModeloImportacionDGT() {
		if (modeloImportacionDGT == null) {
			modeloImportacionDGT = new ModeloImportacionDGT();
		}
		return modeloImportacionDGT;
	}

	public void setModeloImportacionDGT(ModeloImportacionDGT modeloImportacionDGT) {
		this.modeloImportacionDGT = modeloImportacionDGT;
	}

	public ServicioProcesos getServicioProcesos() {
		return servicioProcesos;
	}

	public void setServicioProcesos(ServicioProcesos servicioProcesos) {
		this.servicioProcesos = servicioProcesos;
	}

	public ServicioEEFFNuevo getServicioEEFF() {
		return servicioEEFF;
	}

	public void setServicioEEFF(ServicioEEFFNuevo servicioEEFF) {
		this.servicioEEFF = servicioEEFF;
	}

	public ServicioPermisos getServicioPermisos() {
		return servicioPermisos;
	}

	public void setServicioPermisos(ServicioPermisos servicioPermisos) {
		this.servicioPermisos = servicioPermisos;
	}

	public ServicioImportar getServicioImportacion() {
		return servicioImportacion;
	}

	public void setServicioImportacion(ServicioImportar servicioImportacion) {
		this.servicioImportacion = servicioImportacion;
	}
}