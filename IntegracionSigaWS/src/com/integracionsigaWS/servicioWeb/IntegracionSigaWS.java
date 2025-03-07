package com.integracionsigaWS.servicioWeb;

import java.io.FileNotFoundException;
import java.io.StringReader;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;

import org.apache.commons.codec.binary.Base64;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.EstadosImportacionEstadistica;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.OrigenImportacion;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.ResultadoImportacionEnum;
import org.gestoresmadrid.core.importacionFichero.model.enumerados.TipoImportacion;
import org.gestoresmadrid.core.importacionFichero.model.vo.EstadisticaImportacionFicherosVO;
import org.gestoresmadrid.core.trafico.eeff.model.enumerados.ConstantesEEFF;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.importacion.model.service.ServicioImportar;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGABajaDtoConversion;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGADuplicadoDtoConversion;
import org.gestoresmadrid.oegam2comun.importacion.utiles.FORMATOGAMatwDtoConversion;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service.ServicioIvtmMatriculacion;
import org.gestoresmadrid.oegam2comun.model.service.ServicioPermisos;
import org.gestoresmadrid.oegam2comun.trafico.eeff.model.service.ServicioEEFF;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoDuplicado;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafBajaDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafDuplicadoDto;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.oegamImportacion.bean.ResultadoImportacionBean;
import org.gestoresmadrid.oegamImportacion.estadisticasImportacion.service.ServicioEstadisticaImportacion;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import escrituras.beans.ResultBean;
import general.utiles.UtilesXML;
import oegam.constantes.ConstantesPQ;
import trafico.beans.SolicitudDatosBean;
import trafico.beans.daos.BeanPQAltaMatriculacionImport;
import trafico.beans.daos.BeanPQTramiteTransmisionImport;
import trafico.beans.jaxb.duplicados.FORMATOOEGAM2DUPLICADO;
import trafico.beans.jaxb.solicitud.FORMATOOEGAM2SOLICITUD;
import trafico.beans.jaxb.transmision.FORMATOGA;
import trafico.beans.utiles.FORMATOGAtransmisionPQConversion;
import trafico.beans.utiles.FormatoGaMatriculacionMatwPqConversion;
import trafico.modelo.ModeloAccesos;
import trafico.modelo.ModeloImportacionDGT;
import trafico.modelo.ModeloSolicitudDatos;
import trafico.modelo.ivtm.IVTMModeloMatriculacionInterface;
import trafico.modelo.ivtm.impl.IVTMModeloMatriculacionImpl;
import trafico.utiles.XMLBajaFactory;
import trafico.utiles.XMLDuplicadoFactory;
import trafico.utiles.XMLGAFactory;
import trafico.utiles.XMLManejadorErrores;
import trafico.utiles.XMLSolicitudFactory;
import trafico.utiles.XMLTransmisionFactory;
import trafico.utiles.constantes.Constantes;
import trafico.utiles.enumerados.TipoCreacion;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.propiedades.PropertiesConstantes;
import utilidades.utiles.UtilesExcepciones;
import utilidades.web.OegamExcepcion;

public class IntegracionSigaWS {

	private static final ILoggerOegam log = LoggerOegam.getLogger(IntegracionSigaWS.class);

	@Autowired
	protected ServicioEEFF servicioEEFF;

	@Autowired
	protected ServicioPermisos servicioPermisos;

	@Autowired
	protected ServicioContrato servicioContrato;

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	protected ServicioImportar servicioImportacion;

	@Autowired
	protected ServicioIvtmMatriculacion servicioIvtmMatriculacion;

	@Autowired
	protected FORMATOGABajaDtoConversion formatoGABajaDtoConversion;

	@Autowired
	protected FORMATOGADuplicadoDtoConversion formatoGADuplicadoDtoConversion;

	@Autowired
	protected FORMATOGAMatwDtoConversion formatoGAMatwDtoConversion;

	@Autowired
	protected FormatoGaMatriculacionMatwPqConversion formatoGaMatriculacionMatwPqConversion;

	@Autowired
	protected GestorPropiedades gestorPropiedades;

	@Autowired
	protected ServicioEstadisticaImportacion servicioEstadisticaImportacion;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	private static final String MATRICULACION = "MA";
	private static final String MATRICULACION_MATW = "MW";
	private static final String TRANSMISIONES = "TA";
	private static final String TRANSMISIONESELECTRONICA = "TE";
	private static final String BAJAS = "BA";
	private static final String DUPLICADO = "DU";
	private static final String SOLICITUD = "SO";
	private static final String CODIGO_FUNCION_TRANSMISION = "OTIM03";

	private ModeloImportacionDGT modeloImportacionDGT;

	public IntegracionSigaWS() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}

	public String importacionTramites(String usuarioAutenticado, String password, BigDecimal idUsuario, BigDecimal idContrato, String tipoTramite, byte[] Documento) throws Exception {
		log.info("SERVICIO WEB INTEGRACION SIGA: INICIO");

		boolean errorTramite = false;
		boolean errorTransElectronica = false;
		boolean errorCabecera = false;
		boolean errorXMLNoValido = false;
		boolean errorMATE = false;
		String respuesta = "";
		ModeloAccesos modelo = new ModeloAccesos();
		boolean permiso = false;

		List<String> resultados = new ArrayList<String>();
		List<String> identificadores = new ArrayList<String>();
		List<String> mensajes = new ArrayList<String>();

		ResultadoImportacionBean resultadoImportacion = new ResultadoImportacionBean(Boolean.FALSE);

		Properties ficheroPropiedades = new Properties();
		ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream(("integracionSigaWS.properties")));
		String user = ficheroPropiedades.getProperty("user");
		String pass = ficheroPropiedades.getProperty("password");

		if ((user.equals(usuarioAutenticado)) && (pass.equals(password))) {

			// Mantis 7670 (jose.rumbo): No permitir importaciones de ficheros para contratos deshabilitados
			ContratoVO contrato = servicioContrato.getContrato(idContrato);
			if (Constantes.ESTADO_CONTRATO_HABILITADO == contrato.getEstadoContrato().intValue()) {
				EstadisticaImportacionFicherosVO estadisticaImporFich = puedeHacerImportacion(idContrato.longValue(), idUsuario.longValue(), tipoTramite);
				if (estadisticaImporFich != null) {
					try {
						// Decodifico el XML que vendra en base 64
						String xmlData = new String(Base64.decodeBase64(Documento));
						// El unmarshaller trabaja con tipo StringReader
						StringReader stringReaderXML = new StringReader(xmlData);

						// Custodiar el XML en TDOC
						byte[] xmlDataByte = xmlData.getBytes();
						FicheroBean ficheroXMLCustodiar = new FicheroBean();

						log.info("SERVICIO WEB INTEGRACION SIGA: INICIO idUsuario " + idUsuario);
						log.info("SERVICIO WEB INTEGRACION SIGA: INICIO idContrato " + idContrato);
						log.info("SERVICIO WEB INTEGRACION SIGA: INICIO tipoTramite " + tipoTramite);

						// Compruebo si se solicita Matriculacion (MA), Transmision (TA), Transmision Electronica (TE) o Baja (BA)
						if (MATRICULACION.equals(tipoTramite)) { // MATE esta desactivado
							errorMATE = true;
						} else if (MATRICULACION_MATW.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN MATRICULACION MATW");
							String rutaXDS = gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_MATRICULACION_MATW);
							XMLGAFactory xmlGAFactory = new XMLGAFactory();
							Unmarshaller unmarshaller = xmlGAFactory.getMatriculacionMatWContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlGAFactory.getSchema(rutaXDS);

							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							trafico.beans.jaxb.matw.FORMATOGA ga = (trafico.beans.jaxb.matw.FORMATOGA) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							String habilitarImportacionMatriculacion = gestorPropiedades.valorPropertie("habilitar.importacion.matriculacion.nueva");

							if (habilitarImportacionMatriculacion != null && "SI".equals(habilitarImportacionMatriculacion)) {
								log.info("SERVICIO WEB INTEGRACION SIGA: ENTRA Importación Nueva");
								Boolean tienePermisoLiberarEEFF = servicioPermisos.tienePermisoElContrato(idContrato != null ? idContrato.longValue() : idContrato.longValue(),
										ConstantesEEFF.CODIGO_PERMISO_BBDD_LIBERAR_EEFF, utilesColegiado.APLICACION_OEGAM_TRAF);

								List<TramiteTrafMatrDto> beansMatriculacion = formatoGAMatwDtoConversion.convertirFORMATOGAtoPQ(ga, idContrato, idContrato, tienePermisoLiberarEEFF);
								if (ga.getCABECERA() == null || ga.getCABECERA().getDATOSGESTORIA() == null || ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(ga
										.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
									errorCabecera = true;
								} else if (beansMatriculacion == null || (beansMatriculacion != null && beansMatriculacion.size() == 0) || ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT() != null
										&& !ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT().equals("")) {
									errorXMLNoValido = true;
								} else {
									for (int i = 0; i < beansMatriculacion.size(); i++) {
										log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarAltaMatriculacionMatwImport");
										beansMatriculacion.get(i).setIdTipoCreacion(new BigDecimal(TipoCreacion.SIGA.getValorEnum()));
										ResultBean resultado = servicioImportacion.importarMatriculacion(beansMatriculacion.get(i), idUsuario, tienePermisoLiberarEEFF, false);
										log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarAltaMatriculacionMatwImport");
										if (resultado.getError()) {
											String error = "KO";
											String mensaje = resultado.getMensaje();
											resultados.add(error);
											identificadores.add(ga.getMATRICULACION().get(i).getREFERENCIAPROPIA());
											mensajes.add(mensaje);
										} else {
											BigDecimal numExpediente = (BigDecimal) resultado.getAttachment(ServicioTramiteTraficoMatriculacion.NUMEXPEDIENTE);
											// Se guardan los datos de EEFF
											servicioEEFF.guardarDatosImportadosLiberacion(ga.getMATRICULACION().get(i), numExpediente);
											// Se guardan los datos del IVTM.

											boolean tienePermisoIVTM = servicioPermisos.tienePermisoElContrato(idContrato.longValue(), utilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM,
													utilesColegiado.APLICACION_OEGAM_TRAF);

											if (tienePermisoIVTM) {
												servicioIvtmMatriculacion.guardarDatosImportados(ga.getMATRICULACION().get(i), numExpediente);
											}

											String error = "OK";
											String mensaje = resultado.getMensaje();
											resultados.add(error);
											identificadores.add(ga.getMATRICULACION().get(i).getREFERENCIAPROPIA());
											mensajes.add(mensaje);
										}
									}
								}
							} else {
								List<BeanPQAltaMatriculacionImport> beansMatriculacion = formatoGaMatriculacionMatwPqConversion.convertirFORMATOGAtoPQ(ga, idUsuario, idContrato);
								if (ga.getCABECERA() == null || ga.getCABECERA().getDATOSGESTORIA() == null || ga.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(ga
										.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
									errorCabecera = true;
								} else if (beansMatriculacion == null || (beansMatriculacion != null && beansMatriculacion.size() == 0) || ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT() != null
										&& !ga.getCABECERA().getDATOSGESTORIA().getOEGAMDGT().equals("")) {
									errorXMLNoValido = true;
								} else {
									for (int i = 0; i < beansMatriculacion.size(); i++) {
										log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarAltaMatriculacionMatwImport");
										ResultBean resultado = getModeloImportacionDGT().guardarAltaMatriculacionMatwImport((BeanPQAltaMatriculacionImport) beansMatriculacion.get(i), false, null,
												idUsuario, idContrato, TipoCreacion.SIGA);
										log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarAltaMatriculacionMatwImport");
										if (resultado.getError()) {
											String error = "KO";
											String mensaje = resultado.getMensaje();
											resultados.add(error);
											identificadores.add(ga.getMATRICULACION().get(i).getREFERENCIAPROPIA());
											mensajes.add(mensaje);
										} else {
											BigDecimal numExpediente = (BigDecimal) resultado.getAttachment("numExpediente");
											// Se guardan los datos de EEFF
											servicioEEFF.guardarDatosImportadosLiberacion(ga.getMATRICULACION().get(i), numExpediente);
											// Se guardan los datos del IVTM.
											IVTMModeloMatriculacionInterface modeloIVTM = new IVTMModeloMatriculacionImpl();
											boolean tienePermisoIVTM = false;
											if (gestorPropiedades.valorPropertie("ivtm.usaPermiso") != null && gestorPropiedades.valorPropertie("ivtm.usaPermiso").equals("1")) {
												tienePermisoIVTM = servicioPermisos.tienePermisoElUsuario(idUsuario.longValue(), idContrato.longValue(), utilesColegiado.PERMISO_AUTOLIQUIDAR_IVTM,
														utilesColegiado.APLICACION_OEGAM_TRAF);
											}

											modeloIVTM.guardarDatosImportados(ga.getMATRICULACION().get(i), numExpediente, tienePermisoIVTM);
											String error = "OK";
											String mensaje = resultado.getMensaje();
											resultados.add(error);
											identificadores.add(ga.getMATRICULACION().get(i).getREFERENCIAPROPIA());
											mensajes.add(mensaje);
										}
									}
								}
							}
							// Si llega una transmisión de tipo
						} else if (TRANSMISIONES.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN TRANSMISION");

							try {
								permiso = utilesColegiado.tienePermiso(CODIGO_FUNCION_TRANSMISION);
							} catch (Throwable e) {
								log.error("Se ha producido un error al obtener los trámites de SIGA: " + e.getMessage());
							}

							String rutaXDS = gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_TRANSMISION);
							XMLTransmisionFactory xmlTransmisionFactory = new XMLTransmisionFactory();
							Unmarshaller unmarshaller = xmlTransmisionFactory.getContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlTransmisionFactory.getSchema(rutaXDS);

							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							FORMATOGA transmision = (FORMATOGA) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							Boolean electronica = false;
							List<BeanPQTramiteTransmisionImport> beansTransmision = FORMATOGAtransmisionPQConversion.convertirFORMATOGAtoPQ(transmision, electronica, idUsuario, idContrato);

							if (transmision.getCABECERA() == null || transmision.getCABECERA().getDATOSGESTORIA() == null || transmision.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || ""
									.equals(transmision.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
								errorCabecera = true;
							} else {
								for (int i = 0; i < beansTransmision.size(); i++) {
									if (!permiso) {
										String error = "KO";
										String mensaje = "No tiene permiso para importar un trámite de este tipo (Transmisión actual) ";
										resultados.add(error);
										identificadores.add(transmision.getTRANSMISION().get(i).getNUMERODOCUMENTO());
										mensajes.add(mensaje);
									} else {
										log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarTransmisionImport");
										HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansTransmision.get(i), null,
												idUsuario, idContrato, TipoCreacion.SIGA);
										log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarTransmisionImport");
										if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
											String error = "KO";
											String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
											resultados.add(error);
											identificadores.add(transmision.getTRANSMISION().get(i).getNUMERODOCUMENTO());
											mensajes.add(mensaje);
										} else {
											String error = "OK";
											String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
											resultados.add(error);
											identificadores.add(transmision.getTRANSMISION().get(i).getNUMERODOCUMENTO());
											mensajes.add(mensaje);
										}
									}
								}
							}
						} else if (TRANSMISIONESELECTRONICA.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN TRANSMISION ELECTRONICA");

							String rutaXDS = gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_TRANSMISION);
							XMLTransmisionFactory xmlTransmisionFactory = new XMLTransmisionFactory();
							Unmarshaller unmarshaller = xmlTransmisionFactory.getContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlTransmisionFactory.getSchema(rutaXDS);

							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							FORMATOGA transmision = (FORMATOGA) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							Boolean electronica = true;

							List<BeanPQTramiteTransmisionImport> beansTransmision = FORMATOGAtransmisionPQConversion.convertirFORMATOGAtoPQ(transmision, electronica, idUsuario, idContrato);

							if (transmision.getCABECERA() == null || transmision.getCABECERA().getDATOSGESTORIA() == null || transmision.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || ""
									.equals(transmision.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
								errorCabecera = true;
							} else {
								String token = transmision.getCABECERA().getDATOSGESTORIA().getTIPODGT();
								if (token == null) {
									errorTransElectronica = true;
								} else {
									for (int i = 0; i < beansTransmision.size(); i++) {
										log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarTransmisionImport Elec");
										HashMap<String, Object> resultado = getModeloImportacionDGT().guardarTransmisionImport((BeanPQTramiteTransmisionImport) beansTransmision.get(i), null,
												idUsuario, idContrato, TipoCreacion.SIGA);
										log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarTransmisionImport Elec");
										if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
											String error = "KO";
											String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
											resultados.add(error);
											identificadores.add(transmision.getTRANSMISION().get(i).getNUMERODOCUMENTO());
											mensajes.add(mensaje);
										} else {
											String error = "OK";
											String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
											resultados.add(error);
											identificadores.add(transmision.getTRANSMISION().get(i).getNUMERODOCUMENTO());
											mensajes.add(mensaje);
										}
									}
								}
							}
						} else if (BAJAS.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN BAJAS");

							String rutaXDS = gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_BAJA);
							XMLBajaFactory xmlBajaFactory = new XMLBajaFactory();
							Unmarshaller unmarshaller = xmlBajaFactory.getContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlBajaFactory.getSchema(rutaXDS);
							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA baja = (trafico.beans.jaxb.baja.FORMATOOEGAM2BAJA) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							List<TramiteTrafBajaDto> beansBaja = formatoGABajaDtoConversion.convertirFORMATOGAtoDto(baja, idContrato, idContrato);

							if (baja.getCABECERA() == null || baja.getCABECERA().getDATOSGESTORIA() == null || baja.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || "".equals(baja
									.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
								errorCabecera = true;
							} else {
								for (TramiteTrafBajaDto tramiteTraficoBaja : beansBaja) {
									log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarBajaImport");
									tramiteTraficoBaja.setIdTipoCreacion(new BigDecimal(TipoCreacion.SIGA.getValorEnum()));
									ResultBean result = servicioImportacion.importarBaja(tramiteTraficoBaja, idUsuario, Boolean.FALSE, Boolean.TRUE);
									log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarBajaImport");
									if (result != null && result.getError()) {
										String error = "KO";
										String mensaje = "";
										for (String mnj : result.getListaMensajes()) {
											mensaje = mensaje + mnj + " ";
										}
										resultados.add(error);
										identificadores.add(tramiteTraficoBaja.getRefPropia());
										mensajes.add(mensaje);
									} else {
										String error = "OK";
										String mensaje = result.getMensaje();
										resultados.add(error);
										identificadores.add(tramiteTraficoBaja.getRefPropia());
										mensajes.add(mensaje);
									}
								}
							}
						} else if (DUPLICADO.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN DUPLICADO");

							String rutaXDS = gestorPropiedades.valorPropertie(Constantes.PROPERTIES_RUTA_ESQUEMA_DUPLICADO);
							XMLDuplicadoFactory xmlDuplicadoFactory = new XMLDuplicadoFactory();
							Unmarshaller unmarshaller = xmlDuplicadoFactory.getContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlDuplicadoFactory.getSchema(rutaXDS);

							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							FORMATOOEGAM2DUPLICADO duplicado = (FORMATOOEGAM2DUPLICADO) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							List<TramiteTrafDuplicadoDto> beansDuplicado = formatoGADuplicadoDtoConversion.convertirFORMATOGAtoDto(duplicado, idContrato, idContrato);

							if (duplicado.getCABECERA() == null || duplicado.getCABECERA().getDATOSGESTORIA() == null || duplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || ""
									.equals(duplicado.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
								errorCabecera = true;
							} else {
								for (TramiteTrafDuplicadoDto tramiteTraficoDuplicado : beansDuplicado) {
									log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarDuplicadoImport");
									tramiteTraficoDuplicado.setIdTipoCreacion(new BigDecimal(TipoCreacion.SIGA.getValorEnum()));
									ResultBean result = servicioImportacion.importarDuplicado(tramiteTraficoDuplicado, idUsuario);
									log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarDuplicadoImport");

									String numExpediente = "";
									if (result.getAttachment(ServicioTramiteTraficoDuplicado.NUMEXPEDIENTE) != null) {
										numExpediente = result.getAttachment(ServicioTramiteTraficoDuplicado.NUMEXPEDIENTE).toString();
									}

									if (result != null && result.getError()) {
										String error = "KO";
										String mensaje = "";
										for (String mnj : result.getListaMensajes()) {
											mensaje = mensaje + mnj + " ";
										}
										resultados.add(error);
										identificadores.add(numExpediente);
										mensajes.add(mensaje);
									} else {
										String error = "OK";
										String mensaje = result.getMensaje();
										resultados.add(error);
										identificadores.add(numExpediente);
										mensajes.add(mensaje);
									}
								}
							}

						} else if (SOLICITUD.equals(tipoTramite)) {
							log.info("SERVICIO WEB INTEGRACION SIGA: ENTRO EN SOLICITUD");

							String rutaXDS = gestorPropiedades.valorPropertie(PropertiesConstantes.RUTA_DIRECTORIO_DATOS) + gestorPropiedades.valorPropertie(
									Constantes.PROPERTIES_RUTA_ESQUEMA_SOLICITUD);
							XMLSolicitudFactory xmlSolicitudFactory = new XMLSolicitudFactory();
							Unmarshaller unmarshaller = xmlSolicitudFactory.getContext().createUnmarshaller();
							XMLManejadorErrores manejadorErrores = new XMLManejadorErrores();
							Schema schema = xmlSolicitudFactory.getSchema(rutaXDS);

							// Se valida y parsea el XML a la vez
							unmarshaller.setSchema(schema);
							unmarshaller.setEventHandler(manejadorErrores);
							FORMATOOEGAM2SOLICITUD solicitud = (FORMATOOEGAM2SOLICITUD) unmarshaller.unmarshal(stringReaderXML);

							UtilesXML.comprobarErroresValidacion(manejadorErrores, rutaXDS);

							String numColegiado = solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL();

							List<SolicitudDatosBean> listaTramiteTraficoSolicitud = null;

							if (solicitud.getCABECERA() == null || solicitud.getCABECERA().getDATOSGESTORIA() == null || solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL() == null || ""
									.equals(solicitud.getCABECERA().getDATOSGESTORIA().getPROFESIONAL())) {
								errorCabecera = true;
							} else {
								for (SolicitudDatosBean tramiteTraficoSolicitado : listaTramiteTraficoSolicitud) {
									log.info("SERVICIO WEB INTEGRACION SIGA: INICIO guardarSolicitudImport");

									ModeloSolicitudDatos modeloSolicitudDatos = new ModeloSolicitudDatos();
									HashMap<String, Object> resultado = modeloSolicitudDatos.guardarSolicitudDatos(tramiteTraficoSolicitado, idUsuario, idContrato, solicitud.getCABECERA()
											.getDATOSGESTORIA().getPROFESIONAL());

									log.info("SERVICIO WEB INTEGRACION SIGA: FIN guardarSolicitudImport");

									SolicitudDatosBean tramiteResultado = ((SolicitudDatosBean) resultado.get(ConstantesPQ.BEANPANTALLA));

									String numExpediente = "";
									if (tramiteResultado != null && tramiteResultado.getTramiteTrafico() != null && tramiteResultado.getTramiteTrafico().getNumExpediente() != null) {
										numExpediente = tramiteResultado.getTramiteTrafico().getNumExpediente().toString();
									}
									if (((ResultBean) resultado.get(ConstantesPQ.RESULTBEAN)).getError()) {
										String error = "KO";
										String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
										resultados.add(error);
										identificadores.add(numExpediente);
										mensajes.add(mensaje);
									} else {
										String error = "OK";
										String mensaje = ((ResultBean) resultado.get("ResultBean")).getMensaje();
										resultados.add(error);
										identificadores.add(numExpediente);
										mensajes.add(mensaje);
									}
								}
							}

						} else {
							errorTramite = true;
						}

						if (!errorTramite && !errorTransElectronica && !errorCabecera && !errorXMLNoValido && !errorMATE) {
							if ((TRANSMISIONES.equals(tipoTramite)) || (TRANSMISIONESELECTRONICA.equals(tipoTramite))) {
								for (int numeroTramite = 0; numeroTramite < resultados.size(); numeroTramite++) {
									// Mantis 13949. David Sierra
									if ("".equals(identificadores.get(numeroTramite))) {
										identificadores.set(numeroTramite, "-");
									} // Fin Mantis
									respuesta = respuesta + "El tramite numero " + (numeroTramite + 1) + " con Numero de Documento " + identificadores.get(numeroTramite) + " ha dado " + resultados
											.get(numeroTramite) + " con el mensaje: " + mensajes.get(numeroTramite) + "\n";

									if ("OK".equals(resultados.get(numeroTramite))) {
										resultadoImportacion.addResumenOK(resultados.get(numeroTramite));
									} else {
										resultadoImportacion.addResumenKO(resultados.get(numeroTramite));
									}
								}
								log.info(respuesta);
							} else { // es una Matriculacion o una Baja
								for (int numeroTramite = 0; numeroTramite < resultados.size(); numeroTramite++) {
									// Mantis 13949. David Sierra
									if ("".equals(identificadores.get(numeroTramite))) {
										identificadores.set(numeroTramite, "-");
									} // Fin Mantis
										// INI 0001690
									if (identificadores.get(numeroTramite) != null) {
										respuesta = respuesta + "El tramite numero " + (numeroTramite + 1) + " con Referencia Propia " + identificadores.get(numeroTramite) + " ha dado " + resultados
												.get(numeroTramite) + " con el mensaje: " + mensajes.get(numeroTramite) + "\n";
									} else {
										respuesta = respuesta + "El tramite numero " + (numeroTramite + 1) + " sin Referencia Propia ha dado " + resultados.get(numeroTramite) + " con el mensaje: "
												+ mensajes.get(numeroTramite) + "\n";
									}

									if ("OK".equals(resultados.get(numeroTramite))) {
										resultadoImportacion.addResumenOK(resultados.get(numeroTramite));
									} else {
										resultadoImportacion.addResumenKO(resultados.get(numeroTramite));
									}
								}
								log.info(respuesta);
							}
							log.info("SERVICIO WEB INTEGRACION SIGA: TERMINO CORRECTAMENTE");

							// Custodiar el XML en TDOC
							ficheroXMLCustodiar.setFicheroByte(xmlDataByte);
							ficheroXMLCustodiar.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
							ficheroXMLCustodiar.setTipoDocumento(ConstantesGestorFicheros.IMPORTACION);
							Fecha f = new Fecha(new Date());
							ficheroXMLCustodiar.setFecha(f);
							String fecha = f.getDia() + "_" + f.getHora() + f.getMinutos() + f.getSegundos();
							ficheroXMLCustodiar.setNombreDocumento(idUsuario + "_" + idContrato + "_" + fecha);
							ficheroXMLCustodiar.setSubTipo(ConstantesGestorFicheros.SUBTIPO_DOC_ISIGA + tipoTramite);
							gestorDocumentos.guardarByte(ficheroXMLCustodiar);

						} else if (errorTramite) {
							resultadoImportacion.setError(Boolean.TRUE);
							respuesta = respuesta + "El/Los tramite(s)  ha(n) dado KO  con el mensaje: No se ha especificado un tipo de tramite compatible" + "\n";
							log.error("SERVICIO WEB INTEGRACION SIGA : Error, No se ha especificado un tipo de tramite compatible");
						} else if (errorTransElectronica) {
							resultadoImportacion.setError(Boolean.TRUE);
							respuesta = respuesta
									+ "El/Los tramite(s)  ha(n) dado KO  con el mensaje: Para la importacion de tramites de transmision electronica es obligatorio informar el campo TIPO_DGT" + "\n";
							log.error("SERVICIO WEB INTEGRACION SIGA : Error, Para la importacion de tramites de transmision electronica es obligatorio informar el campo TIPO_DGT");
						} else if (errorCabecera) {
							resultadoImportacion.setError(Boolean.TRUE);
							respuesta = respuesta + "El fichero no tiene número de profesional, no se podrán importar sus trámites" + "\n";
							log.error("SERVICIO WEB INTEGRACION SIGA : Error, El fichero no tiene número de profesional");
						} else if (errorXMLNoValido) {
							resultadoImportacion.setError(Boolean.TRUE);
							respuesta = respuesta + "XML No Valido" + "\n";
							log.error("SERVICIO WEB INTEGRACION SIGA : Error, XML No Valido");
						} else if (errorMATE) {
							resultadoImportacion.setError(Boolean.TRUE);
							respuesta = respuesta + "ERROR: Actualmente solo se permite importación de tramites de MATW\n";
							log.error("SERVICIO WEB INTEGRACION SIGA : Error, se ha intentado importar un trámite de MATE");
						}
						log.info("SERVICIO WEB INTEGRACION SIGA: FIN idUsuario " + idUsuario);
						log.info("SERVICIO WEB INTEGRACION SIGA: FIN idContrato " + idContrato);
						log.info("SERVICIO WEB INTEGRACION SIGA: FIN tipoTramite " + tipoTramite);

						actualizarEstadistica(estadisticaImporFich, resultadoImportacion);

						return respuesta;
					} catch (FileNotFoundException e) {
						String error = "KO";
						resultadoImportacion.setError(Boolean.TRUE);
						String mensaje = "Error No existe el fichero para importar " + e.getMessage() + "\n";
						resultados.add(error);
						mensajes.add(mensaje);
						respuesta = respuesta + "Error " + error + " con el mensaje: " + mensaje;
						log.error("SERVICIO WEB INTEGRACION SIGA : Error FileNotFoundException. " + " wsOegam." + e.getMessage() + "\n" + e + " Pila de llamadas de la excepción:\n" + UtilesExcepciones
								.stackTraceAcadena(e, 10));
					} catch (Exception e) {
						String error = "KO";
						resultadoImportacion.setError(Boolean.TRUE);
						String mensaje = "Error interno " + e.getMessage() + "\n";
						resultados.add(error);
						mensajes.add(mensaje);
						respuesta = respuesta + "Error " + error + " con el mensaje: " + mensaje;
						log.error("SERVICIO WEB INTEGRACION SIGA : Error Exception. " + " wsOegam." + e.getMessage() + "\n" + e + " Pila de llamadas de la excepción:\n" + UtilesExcepciones
								.stackTraceAcadena(e, 10));
					} catch (OegamExcepcion e) {
						String error = "KO";
						resultadoImportacion.setError(Boolean.TRUE);
						String mensaje = "Error OegamExcepcion " + e.getMessage() + "\n";
						resultados.add(error);
						mensajes.add(mensaje);
						respuesta = respuesta + "Error " + error + " con el mensaje: " + mensaje;
						log.error("SERVICIO WEB INTEGRACION SIGA : Error OegamExcepcion. " + " wsOegam." + e.getMessage() + "\n" + e + " Pila de llamadas de la excepción:\n" + UtilesExcepciones
								.stackTraceAcadena(e, 10));
					}

					actualizarEstadistica(estadisticaImporFich, resultadoImportacion);
				} else {
					respuesta = respuesta + "El contrato no tiene permiso para importar o ya existen un numero grande de importaciones en SIGA" + "\n";
					log.error("El contrato no tiene permiso para importar o ya existen un numero grande de importaciones en SIG" + "\n");
				}
			} else {
				respuesta = respuesta + "El contrato esta deshabilitado, no se puede hacer importaciones de ficheros" + "\n";
				log.error("El contrato esta deshabilitado, no se puede hacer importaciones de ficheros" + "\n");
			}
		} else {
			respuesta = respuesta + "El/Los tramite(s) ha(n) dado KO con el mensaje: Usuario/Password incorrectos" + "\n";
			log.error("El/Los tramite(s) ha(n) dado KO con el mensaje: Usuario/Password incorrectos" + "\n");
		}
		log.info("SERVICIO WEB INTEGRACION SIGA: FIN LLAMADA WS");
		return respuesta;
	}

	private EstadisticaImportacionFicherosVO puedeHacerImportacion(Long idContrato, Long idUsuario, String tipoTramite) {
		EstadisticaImportacionFicherosVO estadisticaImporFich = null;
		if (!servicioEstadisticaImportacion.superaLimiteImportaciones(OrigenImportacion.SIGA.getValorEnum()) && servicioEstadisticaImportacion.contratoConPermiso(idContrato, OrigenImportacion.SIGA
				.getValorEnum())) {
			try {
				estadisticaImporFich = new EstadisticaImportacionFicherosVO();
				estadisticaImporFich.setIdContrato(idContrato);
				estadisticaImporFich.setIdUsuario(idUsuario);
				estadisticaImporFich.setFecha(new Date());
				estadisticaImporFich.setTipo(convertirTipoTramite(tipoTramite));
				estadisticaImporFich.setOrigen(OrigenImportacion.SIGA.getValorEnum());
				estadisticaImporFich.setEstado(EstadosImportacionEstadistica.Ejecutandose.getValorEnum());
				ResultadoImportacionBean resultado = servicioEstadisticaImportacion.guardarEstadistica(estadisticaImporFich);
				estadisticaImporFich.setIdImportacionFich(resultado.getIdImportacionFich());
			} catch (Exception e) {
				log.error("Error al guardar la estadística en SIGA. ", e);
				estadisticaImporFich = null;
			}
		}
		return estadisticaImporFich;
	}

	private String convertirTipoTramite(String tipoTramite) {
		if (MATRICULACION.equals(tipoTramite)) {
			return TipoImportacion.XML_MATW.getValorEnum();
		} else if (MATRICULACION_MATW.equals(tipoTramite)) {
			return TipoImportacion.XML_MATW.getValorEnum();
		} else if (TRANSMISIONES.equals(tipoTramite)) {
			return TipoImportacion.XML_CTIT.getValorEnum();
		} else if (TRANSMISIONESELECTRONICA.equals(tipoTramite)) {
			return TipoImportacion.XML_CTIT.getValorEnum();
		} else if (BAJAS.equals(tipoTramite)) {
			return TipoImportacion.XML_BAJA.getValorEnum();
		} else if (DUPLICADO.equals(tipoTramite)) {
			return TipoImportacion.XML_DUPLICADO.getValorEnum();
		} else if (SOLICITUD.equals(tipoTramite)) {
			return TipoImportacion.XML_SOLICITUDES.getValorEnum();
		}
		return tipoTramite;
	}

	private void actualizarEstadistica(EstadisticaImportacionFicherosVO estadisticaImporFich, ResultadoImportacionBean resultadoImportacion) {
		if (estadisticaImporFich != null) {
			if (resultadoImportacion.getError()) {
				estadisticaImporFich.setTipoError(ResultadoImportacionEnum.Error_Catastrofico.getCodigo());
				estadisticaImporFich.setNumError(new Long(0));
			} else if ((resultadoImportacion.getResumen().getListaErrores() != null && !resultadoImportacion.getResumen().getListaErrores().isEmpty()) && (resultadoImportacion.getResumen()
					.getListaOk() != null && !resultadoImportacion.getResumen().getListaOk().isEmpty())) {
				estadisticaImporFich.setTipoError(ResultadoImportacionEnum.OK_PARCIAL.getCodigo());
				estadisticaImporFich.setNumOk(resultadoImportacion.getResumen().getNumOk().longValue());
				estadisticaImporFich.setNumError(resultadoImportacion.getResumen().getNumError().longValue());
			} else if (resultadoImportacion.getResumen().getListaErrores() != null && !resultadoImportacion.getResumen().getListaErrores().isEmpty()) {
				estadisticaImporFich.setTipoError(ResultadoImportacionEnum.Error_Catastrofico.getCodigo());
				estadisticaImporFich.setNumError(resultadoImportacion.getResumen().getNumError().longValue());
			} else {
				estadisticaImporFich.setTipoError(ResultadoImportacionEnum.OK.getCodigo());
				estadisticaImporFich.setNumOk(resultadoImportacion.getResumen().getNumOk().longValue());
			}
			estadisticaImporFich.setEstado(EstadosImportacionEstadistica.Finalizado.getValorEnum());
			servicioEstadisticaImportacion.actualizarEstadistica(estadisticaImporFich);
		}
	}

	public String hashString(String password) throws Exception {
		String hashword = null;
		MessageDigest md5 = MessageDigest.getInstance("MD5");
		md5.update(password.getBytes());
		BigInteger hash = new BigInteger(1, md5.digest());
		hashword = hash.toString(16);
		return hashword;
	}

	private ModeloImportacionDGT getModeloImportacionDGT() {
		if (modeloImportacionDGT == null) {
			modeloImportacionDGT = new ModeloImportacionDGT();
		}
		return modeloImportacionDGT;
	}

}