package org.gestoresmadrid.oegam2comun.consultaEitv.model.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Date;
import java.util.List;

import javax.naming.NamingException;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.contrato.model.vo.ContratoVO;
import org.gestoresmadrid.core.general.model.vo.ColegiadoVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.core.trafico.prematriculados.model.vo.VehiculoPrematriculadoVO;
import org.gestoresmadrid.core.vehiculo.model.vo.MarcaDgtVO;
import org.gestoresmadrid.core.vehiculo.model.vo.VehiculoVO;
import org.gestoresmadrid.gescogroup.blackbox.EITVQueryServiceLocator;
import org.gestoresmadrid.gescogroup.blackbox.EITVQueryWS;
import org.gestoresmadrid.gescogroup.blackbox.EITVQueryWSBindingStub;
import org.gestoresmadrid.gescogroup.blackbox.EitvQueryError;
import org.gestoresmadrid.gescogroup.blackbox.EitvQueryWSRequest;
import org.gestoresmadrid.gescogroup.blackbox.EitvQueryWSResponse;
import org.gestoresmadrid.gescogroup.ws.SoapConsultaTarjetaEitvPreMatriculadosWSHandler;
import org.gestoresmadrid.gescogroup.ws.SoapConsultaTarjetaEitvWSHandler;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.dto.RespuestaConsultaEitv;
import org.gestoresmadrid.oegam2comun.consultaEitv.model.service.ServicioWebServiceConsultaEitv;
import org.gestoresmadrid.oegam2comun.contrato.model.service.ServicioContrato;
import org.gestoresmadrid.oegam2comun.trafico.model.service.ServicioTramiteTraficoMatriculacion;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.ServicioVehiculosPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.model.service.impl.ComunesServicioVehiculoPrematriculados;
import org.gestoresmadrid.oegam2comun.trafico.prematriculados.view.dto.VehiculoPrematriculadoDto;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioMarcaDgt;
import org.gestoresmadrid.oegam2comun.vehiculo.model.service.ServicioVehiculo;
import org.gestoresmadrid.oegamComun.colegiado.services.ServicioColegiado;
import org.gestoresmadrid.oegamComun.usuario.view.dto.UsuarioDto;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import trafico.beans.schemas.generated.eitv.generated.ConsultaTarjeta;
import trafico.beans.schemas.generated.eitv.generated.ObjectFactory;
import trafico.beans.schemas.generated.eitv.generated.xmlDataVehiculos.Vehiculo;
import trafico.utiles.constantes.ConstantesMensajes;
import trafico.utiles.enumerados.TipoImpreso;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.Claves;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioWebServiceConsultaEitvImpl implements ServicioWebServiceConsultaEitv {

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebServiceConsultaEitvImpl.class);

	@Autowired
	private GestorDocumentos gestorDocumentos;

	@Autowired
	private ServicioVehiculo servicioVehiculo;

	@Autowired
	private ServicioContrato servicioContrato;

	@Autowired
	private ServicioVehiculosPrematriculados servicioVehiculosPrematriculados;

	@Autowired
	private ServicioTramiteTraficoMatriculacion servicioTramiteTraficoMatriculacion;

	@Autowired
	private ServicioColegiado servicioColegiado;

	@Autowired
	private ServicioMarcaDgt servicioMarcaDgt;

	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	UtilesFecha utilesFecha;

	@Autowired
	Utiles utiles;

	private UtilesViafirma utilesViafirma;
	private ObjectFactory objectFactory;

	private ComunesServicioVehiculoPrematriculados serviciosComunes;

	@Override
	public RespuestaConsultaEitv llamadaWS(ConsultaTarjeta consultaTarjeta, String xml, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) {
		log.info("ENTRA EN BLACK BOX");
		RespuestaConsultaEitv respuestaConsultaEitv = null;
		String respuestaError = "";
		try {
			EitvQueryWSRequest req = completaRequest(consultaTarjeta, xml, tramiteTraficoVO);
			// Llamada WS
			EitvQueryWSResponse respuestaEitv = getStubEitv(false).getDataEITV(req);
			log.info("------ " + ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " dossierNumber Respuesta: " + respuestaEitv.getDossierNumber());

			EitvQueryError[] bbErrores = respuestaEitv.getErrorCodes();

			if (null == bbErrores || bbErrores.length == 0) {
				log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RESPUESTA SIN ERRORES");
				// Se gestiona la respuesta del WS
				respuestaConsultaEitv = gestionRespuestaWS(respuestaEitv, consultaTarjeta, idUsuario, tramiteTraficoVO, admin);
				respuestaError = ConstantesMensajes.CONSULTA_EITV_CORRECTA;

			} else {
				log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RESPUESTA CON ERRORES");
				if (bbErrores != null) {
					listarErroresMateITV(bbErrores);
					respuestaError += getMensajeError(bbErrores);
					log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " Errores: " + respuestaError);
				}
				respuestaConsultaEitv = new RespuestaConsultaEitv(true, respuestaError);

			}
			if (StringUtils.isNotBlank(respuestaError)) {
				respuestaError = utiles.quitarCaracteresExtranios(respuestaError);
				servicioTramiteTraficoMatriculacion.actualizarRespuestaMateEitv(respuestaError, tramiteTraficoVO.getNumExpediente());
			}
		} catch (UnsupportedEncodingException eU) {
			log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + eU);
			respuestaConsultaEitv = new RespuestaConsultaEitv();
			respuestaConsultaEitv.setException(new Exception(eU));
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + e);
			respuestaConsultaEitv = new RespuestaConsultaEitv();
			respuestaConsultaEitv.setException(e);
		}
		log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " SALE DE EITV");
		return respuestaConsultaEitv;
	}

	@Override
	@Transactional
	public RespuestaConsultaEitv llamadaWSPreMatriculados(ConsultaTarjeta consultaTarjetaEitv, String xmlFirmado, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idUsuario, BigDecimal idContrato) {
		log.info("ENTRA EN BLACK BOX");
		RespuestaConsultaEitv respuestaConsultaEitv = null;
		try {
			EitvQueryWSRequest req = completaRequestVehiculoP(consultaTarjetaEitv, xmlFirmado, vehiculoPreVO, idContrato);
			// Llamada WS
			EitvQueryWSResponse respuestaEitv = getStubEitv(true).getDataEITV(req);
			log.info(" ");
			log.info("------ " + ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RECIBIDA RESPUESTA DEL WEBSERVICE-----");
			log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " customDossierNumber Request: " + req.getCustomDossierNumber());
			log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " dossierNumber Respuesta: " + respuestaEitv.getDossierNumber());

			EitvQueryError[] bbErrores = respuestaEitv.getErrorCodes();
			if (null == bbErrores || bbErrores.length == 0) {
				log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RESPUESTA SIN ERRORES");
				// Se gestiona la respuesta del WS
				respuestaConsultaEitv = gestionRespuestaPreMatriculadosWS(respuestaEitv, consultaTarjetaEitv, idUsuario, vehiculoPreVO, idUsuario);
			} else {
				log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " RESPUESTA CON ERRORES");
				String respuestaError = "";
				if (bbErrores != null) {
					listarErroresMateITV(bbErrores);
					respuestaError += getMensajeError(bbErrores);
					log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " Errores: " + respuestaError);
				}
				servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(false, respuestaError, vehiculoPreVO.getId());
				respuestaConsultaEitv = new RespuestaConsultaEitv(true, respuestaError);
			}
		} catch (UnsupportedEncodingException eU) {
			log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + eU);
			respuestaConsultaEitv = new RespuestaConsultaEitv();
			respuestaConsultaEitv.setException(new Exception(eU));
		} catch (Exception e) {
			log.error(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " EXCEPCION: " + e);
			respuestaConsultaEitv = new RespuestaConsultaEitv();
			respuestaConsultaEitv.setException(e);
		}
		log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " SALE DE EITV");
		return respuestaConsultaEitv;
	}

	private RespuestaConsultaEitv gestionRespuestaPreMatriculadosWS(EitvQueryWSResponse respuestaWS, ConsultaTarjeta consultaTarjeta, BigDecimal idUsuario, VehiculoPrematriculadoVO vehiculoPreVO,
			BigDecimal idUsuario2) {
		RespuestaConsultaEitv respuesta = null;
		ResultBean result = null;
		try {
			servicioVehiculosPrematriculados.guardarRespuestaDatosTecnicos(true, ServicioWebServiceConsultaEitv.MENSAJE_RESPUESTA_OK, vehiculoPreVO.getId());
			result = servicioVehiculosPrematriculados.guardarXmlRespuestaDatosTecnicos(respuestaWS.getXmldata(), vehiculoPreVO.getId(), vehiculoPreVO.getFechaAlta());
			if (result != null) {
				result = servicioVehiculosPrematriculados.guardarPdfRespuestaDatosTecnicos(respuestaWS.getFicha(), vehiculoPreVO);
			}
			if (result != null && result.getError()) {
				respuesta = new RespuestaConsultaEitv();
				respuesta.setError(true);
				respuesta.setMensajesError(result.getListaMensajes());
				return respuesta;
			}
		} catch (Exception e) {
			respuesta = new RespuestaConsultaEitv();
			respuesta.setException(e);
		}
		return respuesta;
	}

	private EitvQueryWSRequest completaRequestVehiculoP(ConsultaTarjeta consultaTarjeta, String xmlFirmado, VehiculoPrematriculadoVO vehiculoPreVO, BigDecimal idContrato)
			throws UnsupportedEncodingException {
		EitvQueryWSRequest req = new EitvQueryWSRequest();
		req.setNive(consultaTarjeta.getDatosFirmados().getNIVE() == null ? "0" : consultaTarjeta.getDatosFirmados().getNIVE().toUpperCase());
		req.setVin(consultaTarjeta.getDatosFirmados().getVIN());
		req.setAgentFiscalId(consultaTarjeta.getDatosFirmados().getAGENTDOI());
		req.setAgencyFiscalId(consultaTarjeta.getDatosFirmados().getAGENCYDOI());
		req.setCustomDossierNumber(vehiculoPreVO.getId().toString());
		if (idContrato != null) {
			ContratoVO contratoVO = servicioContrato.getContrato(idContrato);
			if (contratoVO != null && contratoVO.getColegio() != null) {
				req.setExternalSystemFiscalId(contratoVO.getColegio().getCif());
			}
		}
		req.setXmlB64(utiles.doBase64Encode(xmlFirmado.getBytes(UTF_8)));
		return req;
	}

	@Override
	public String xmlFiletoString(File ficheroAenviar) throws JAXBException {
		byte[] by = null;
		FileInputStream fis;
		String peticionXML = null;
		try {
			fis = new FileInputStream(ficheroAenviar);
			by = IOUtils.toByteArray(fis);
			peticionXML = new String(by, ServicioWebServiceConsultaEitv.UTF_8);
		} catch (FileNotFoundException e) {
			log.error(e);
		} catch (IOException e) {
			log.error(e);
		}
		return peticionXML;
	}

	public RespuestaConsultaEitv gestionRespuestaWS(EitvQueryWSResponse respuestaWS, ConsultaTarjeta consultaTarjeta, BigDecimal idUsuario, TramiteTraficoVO tramiteTraficoVO, boolean admin) {
		RespuestaConsultaEitv respuesta = null;
		ResultBean result = null;
		try {
			// Guardar el xml de respuesta
			result = guardarXmlRespuesta(respuestaWS, tramiteTraficoVO.getNumExpediente().toString());
			if (result == null) {
				// Comprobar si ha sido correcta la respuesta del WS si es asi guardar los datos del vehiculo que vienen en el xml
				ResultBean resultado = guardarDatosVehiculoXml(respuestaWS.getXmldata(), tramiteTraficoVO, idUsuario, admin);
				if (resultado != null && resultado.getError()) {
					respuesta = new RespuestaConsultaEitv();
					respuesta.setError(true);
					respuesta.setMensajesError(resultado.getListaMensajes());
					return respuesta;
				}
				String nombreFichero = TipoImpreso.ConsultaEITV.toString() + "_" + tramiteTraficoVO.getNumExpediente();
				// Una vez recibida la respuesta del WS obtener el pdf y guardarlo
				respuesta = guardarPDFConsultaEitv(respuestaWS, nombreFichero);
			} else {
				respuesta = new RespuestaConsultaEitv();
				respuesta.setException(new Exception(result.getListaMensajes().get(0)));
			}
		} catch (Exception e) {
			respuesta = new RespuestaConsultaEitv();
			respuesta.setException(e);
		}
		return respuesta;
	}

	private ResultBean guardarXmlRespuesta(EitvQueryWSResponse respuestaWS, String numExpediente) {
		ResultBean resultado = null;
		FicheroBean fichero = new FicheroBean();
		fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
		fichero.setSubTipo(ConstantesGestorFicheros.RESPUESTAEITV);
		fichero.setNombreDocumento(ConstantesGestorFicheros.CONSULTA_EITV_NUEVO + numExpediente);
		fichero.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
		Fecha fecha = Utilidades.transformExpedienteFecha(numExpediente);
		fichero.setFecha(fecha);

		try {
			byte[] xmlData = utiles.doBase64Decode(respuestaWS.getXmldata(), UTF_8);
			fichero.setFicheroByte(xmlData);
			File file = gestorDocumentos.guardarFichero(fichero);
			if (file == null) {
				resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
			}
		} catch (OegamExcepcion e) {
			log.error("Error al guardar el documento", e);
			resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
		} catch (Exception e) {
			log.error("Error al guardar el documento,error al decodificar", e);
			resultado = new ResultBean(true, "No se ha podido guardar el xml con los datos de la consulta.");
		}
		return resultado;
	}

	public void listarErroresMateITV(EitvQueryError[] listadoErrores) {
		log.error("Listando errores MateITV");
		for (int i = 0; i < listadoErrores.length; i++) {
			log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.CODIGO + " " + listadoErrores[i].getKey());

			if (listadoErrores[i].getMessage() == null) {
				log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + "No devuelve descripcion del error");
			} else {
				log.error(ConstantesProcesos.PROCESO_CONSULTAEITV + ": " + ConstantesProcesos.ERROR_DE_WEBSERVICE + " " + ConstantesProcesos.DESCRIPCION + " " + listadoErrores[i].getMessage());
			}
		}
	}

	private EitvQueryWSRequest completaRequest(ConsultaTarjeta consultaTarjeta, String xmlFirmado, TramiteTraficoVO tramiteTraficoVO) throws UnsupportedEncodingException {
		EitvQueryWSRequest req = new EitvQueryWSRequest();
		String nive = consultaTarjeta.getDatosFirmados().getNIVE();
		if (nive != null && !nive.equals(" ")) {
			req.setNive(consultaTarjeta.getDatosFirmados().getNIVE().toUpperCase());
		} else {
			req.setNive("");
		}
		req.setVin(consultaTarjeta.getDatosFirmados().getVIN());
		req.setAgentFiscalId(consultaTarjeta.getDatosFirmados().getAGENTDOI());
		req.setAgencyFiscalId(consultaTarjeta.getDatosFirmados().getAGENCYDOI());
		req.setCustomDossierNumber(tramiteTraficoVO.getNumExpediente().toString());
		req.setExternalSystemFiscalId(tramiteTraficoVO.getContrato().getColegio().getCif());
		req.setXmlB64(utiles.doBase64Encode(xmlFirmado.getBytes()));
		return req;
	}

	public String getMensajeError(EitvQueryError[] listadoErrores) {
		StringBuffer mensajeError = new StringBuffer();
		for (int i = 0; i < listadoErrores.length; i++) {
			mensajeError.append(listadoErrores[i].getKey());
			mensajeError.append(" - ");
			if ("EITV07002".equals(listadoErrores[i].getKey()) && (listadoErrores[i].getMessage() == null) || "None".equals(listadoErrores[i].getMessage())) {
				listadoErrores[i].setMessage("No se ha encontrado la tarjeta EITV");
				mensajeError.append(listadoErrores[i].getMessage());
			} else if (listadoErrores[i].getMessage() == null) {
				listadoErrores[i].setMessage("Tipo de error: " + listadoErrores[i].getKey());
				mensajeError.append(listadoErrores[i].getMessage());
			} else {
				String error = listadoErrores[i].getMessage().replaceAll("'", "");
				error = error.replaceAll("\"", "");
				if (error.length() > 80) {
					String resAux = "";
					for (int tam = 0; tam <= Math.floor(error.length() / 80); tam++) {
						if (tam != Math.floor(error.length() / 80)) {
							resAux += error.substring(80 * tam, 80 * tam + 80) + " - ";
						} else {
							resAux += error.substring(80 * tam) + " - ";
						}
					}
					error = resAux;
				}
				String errorDecode = "";
				try {
					errorDecode = new String(error.getBytes(), "UTF-8");
				} catch (Throwable th) {
					errorDecode = error;
				}
				mensajeError.append(errorDecode);
			}
		}
		return mensajeError.toString();
	}

	/**
	 * El stub de conexion con el webservice de Eitv
	 * @throws OegamExcepcion
	 * @throws NamingException
	 */

	public EITVQueryWSBindingStub getStubEitv(boolean preMatriculado) throws MalformedURLException, ServiceException {
		EITVQueryWSBindingStub stubEitv = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceConsultaEitv.TIMEOUT_PROP_EITV);
		URL miURL = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceConsultaEitv.WEBSERVICE_CONSULTA_TARJETA_EITV));
		EITVQueryWS eitvQueryService = null;

		if (null != miURL) {
			EITVQueryServiceLocator eitvQueryServiceLocator = new EITVQueryServiceLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miURL.toString(), eitvQueryServiceLocator.getEITVQueryServiceWSDDServiceName());

			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = eitvQueryServiceLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			if (preMatriculado) {
				logHandlerInfo.setHandlerClass(SoapConsultaTarjetaEitvPreMatriculadosWSHandler.class);
			} else {
				logHandlerInfo.setHandlerClass(SoapConsultaTarjetaEitvWSHandler.class);
			}
			list.add(logHandlerInfo);

			eitvQueryService = eitvQueryServiceLocator.getEITVQueryService(miURL);
			log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " miURL: " + miURL.toString());
		}

		stubEitv = (EITVQueryWSBindingStub) eitvQueryService;
		log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " generado stubEitv.");

		stubEitv.setTimeout(Integer.parseInt(timeOut));
		log.info(ServicioWebServiceConsultaEitv.TEXTO_CONSULTAEITV_LOG + " timeout: " + ServicioWebServiceConsultaEitv.TIMEOUT_MATEGE);

		return stubEitv;
	}

	private ResultBean guardarDatosVehiculoXml(String xmlB64, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) throws Exception {
		String xmlData = new String(utiles.doBase64Decode(xmlB64, Claves.ENCODING_UTF8));
		Log.info("Datos del vehiculo convertidos");
		Vehiculo vehiculo = (Vehiculo) getBeanToXml(xmlData, ServicioWebServiceConsultaEitv.NAME_CONTEXT_VEHICULO);
		ResultBean result = null;
		if (vehiculo != null) {
			result = guardarDatosVehiculoConsultaEITV(vehiculo, tramiteTraficoVO, idUsuario, admin);
		} else {
			result = new ResultBean(true, "Ha surgido un error a la hora de crear el Vehículo a partir del xml");
		}
		return result;
	}

	@Override
	public Object getBeanToXml(String xml, String nameContext) throws JAXBException {
		Object obj = null;
		Unmarshaller um = getContext(nameContext).createUnmarshaller();
		ByteArrayInputStream bais = new ByteArrayInputStream(xml.getBytes());
		try {
			obj = um.unmarshal(bais);
		} catch (Exception e) {
			log.error("Error UNMARSHAL");
			log.error(e);
		}
		try {
			bais.close();
		} catch (IOException ioe) {
			// Error al cerrar el flujo de bytes
		}
		return obj;
	}

	private ResultBean guardarDatosVehiculoConsultaEITV(Vehiculo vehiculoEitv, TramiteTraficoVO tramiteTraficoVO, BigDecimal idUsuario, boolean admin) {
		ResultBean result = new ResultBean();
		VehiculoVO vehiculoVO = new VehiculoVO();

		vehiculoVO.setIdVehiculo(tramiteTraficoVO.getVehiculo().getIdVehiculo());
		vehiculoVO.setNumColegiado(tramiteTraficoVO.getNumColegiado());

		// BLOQUE DATOS DEL VEHÍCULO
		setDatosVehiculoConversion(vehiculoEitv, vehiculoVO);

		// BLOQUE DATOS TÉCNICOS
		setDatosTecnicosConversion(vehiculoEitv, vehiculoVO);

		// BLOQUE CARACTERÍSTICAS
		setCaracteristicasConversion(vehiculoEitv, vehiculoVO);

		// BLOQUE DATOS DE LA INSPECCIÓN TÉCNICA (ITV)
		setDatosItvConversion(vehiculoEitv, vehiculoVO);

		chequeaDirectivaCEE(vehiculoVO);

		Date fechaPresentacion = null;
		if (tramiteTraficoVO.getFechaPresentacion() != null) {
			fechaPresentacion = tramiteTraficoVO.getFechaPresentacion();
		}
		UsuarioDto usuarioDto = new UsuarioDto();
		usuarioDto.setIdUsuario(idUsuario);

		result = servicioVehiculo.guardarVehiculo(vehiculoVO, tramiteTraficoVO.getNumExpediente(), ServicioVehiculo.TIPO_TRAMITE_MATE_EITV, usuarioDto, fechaPresentacion,
				ServicioVehiculo.CONVERSION_VEHICULO_MATE_EITV, false, admin);

		return result;
	}

	/*
	 * Comprobación del valor de homologación UE antes de guardar Según la documentación recibida por DGT en Mayo de 2013 el campo categoría no es sensible a mayúsculas/minúsculas Debe seguir el siguiente patrón. Si no lo cumple se modifica para que así sea. M1, M2, M3, N1, N2, N3, O1, O2, O3, O4,
	 * L1e, L2e, L3e, L4e, L5e, L6e, L7e, T1, T2, T3, T4, quad, atv, M1G, M2G, M3G, N1G, N2G, N3G, OTRO
	 */
	private void chequeaDirectivaCEE(VehiculoVO vehiculoVO) {
		if (vehiculoVO != null && vehiculoVO.getIdDirectivaCee() != null && !vehiculoVO.getIdDirectivaCee().equals("")) {

			String directivaCEEaux = vehiculoVO.getIdDirectivaCee().toUpperCase();
			if ("QUAD".equals(directivaCEEaux) || "ATV".equals(directivaCEEaux)) {
				directivaCEEaux = directivaCEEaux.toLowerCase();
			}
			if (directivaCEEaux.startsWith("L")) {
				directivaCEEaux = directivaCEEaux.replace("E", "e");
			}
			vehiculoVO.setIdDirectivaCee(directivaCEEaux);
		}
	}

	private void setDatosItvConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		// Tipo ITV
		vehiculoVO.setTipoItv(vehiculoEitv.getCaracteristicas().getTipo() != null && !vehiculoEitv.getCaracteristicas().getTipo().equals("") ? vehiculoEitv.getCaracteristicas().getTipo() : null);

		// Tipo Tarjeta ITV
		vehiculoVO.setIdTipoTarjetaItv(vehiculoEitv.getGeneral().getTipotarjeta() != null && !vehiculoEitv.getGeneral().getTipotarjeta().equals("") ? vehiculoEitv.getGeneral().getTipotarjeta()
				: null);
	}

	private void setCaracteristicasConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {

		// Categoría / Homologación EU
		vehiculoVO.setIdDirectivaCee(vehiculoEitv.getCaracteristicas().getCategoria() != null && !vehiculoEitv.getCaracteristicas().getCategoria().equals("") ? vehiculoEitv.getCaracteristicas()
				.getCategoria() : null);

		// Carrocería
		vehiculoVO.setCarroceria(vehiculoEitv.getCaracteristicas().getTipocarroc() != null && !vehiculoEitv.getCaracteristicas().getTipocarroc().equals("") ? vehiculoEitv.getCaracteristicas()
				.getTipocarroc().substring(0, 2) : null);

		// Cilindrada
		vehiculoVO.setCilindrada(vehiculoEitv.getCaracteristicas().getCilindrada() != null && !vehiculoEitv.getCaracteristicas().getCilindrada().equals("") ? vehiculoEitv.getCaracteristicas()
				.getCilindrada() : null);

		// CO2
		vehiculoVO.setCo2(vehiculoEitv.getCaracteristicas().getEco2Cmixto() != null && !vehiculoEitv.getCaracteristicas().getEco2Cmixto().equals("") ? vehiculoEitv.getCaracteristicas().getEco2Cmixto()
				: null);

		// Código ECO

		// vehiculoBean.setCodigoEco(codigoEco);

		// Consumo

		// vehiculoBean.setConsumo(consumo);

		// Distancia Entre Ejes
		vehiculoVO.setDistanciaEjes(vehiculoEitv.getCaracteristicas().getDistanejes() != null && !vehiculoEitv.getCaracteristicas().getDistanejes().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getDistanejes()) : BigDecimal.ZERO);

		// ECO Innovacion
		// vehiculoBean.setEcoInnovacion(ecoInnovacion);

		// Potencia Fiscal
		vehiculoVO.setPotenciaFiscal(vehiculoEitv.getCaracteristicas().getPotencfiscal() != null && !vehiculoEitv.getCaracteristicas().getPotencfiscal().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getPotencfiscal()) : BigDecimal.ZERO);

		// MOM
		vehiculoVO.setMom(vehiculoEitv.getCaracteristicas().getMasamarcha() != null && !vehiculoEitv.getCaracteristicas().getMasamarcha().equals("") ? new BigDecimal(vehiculoEitv.getCaracteristicas()
				.getMasamarcha()) : null);

		// MMA
		vehiculoVO.setPesoMma(vehiculoEitv.getCaracteristicas().getMma() != null && !vehiculoEitv.getCaracteristicas().getMma().equals("") ? vehiculoEitv.getCaracteristicas().getMma() : null);

		// MTA / MMA
		vehiculoVO.setMtmaItv(vehiculoEitv.getCaracteristicas().getMmta() != null && !vehiculoEitv.getCaracteristicas().getMmta().equals("") ? vehiculoEitv.getCaracteristicas().getMmta() : null);

		// Nivel de emisiones
		if (vehiculoEitv.getCaracteristicas().getNemisioneseuro() != null && vehiculoEitv.getCaracteristicas().getNemisioneseuro().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getNemisioneseuro().length() > 15) {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro().substring(0, 14));
			} else {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNemisioneseuro());
			}
		} else if (vehiculoEitv.getCaracteristicas().getNivelemisiones() != null && vehiculoEitv.getCaracteristicas().getNivelemisiones().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getNivelemisiones().length() > 15) {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNivelemisiones().substring(0, 14));
			} else {
				vehiculoVO.setNivelEmisiones(vehiculoEitv.getCaracteristicas().getNivelemisiones());
			}
		} else {
			vehiculoVO.setNivelEmisiones(null);
		}

		// Número de ruedas
		vehiculoVO.setnRuedas(vehiculoEitv.getCaracteristicas().getNumruedas() != null && !vehiculoEitv.getCaracteristicas().getNumruedas().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getNumruedas()) : null);

		// Número máximo de plazas
		vehiculoVO.setPlazas(vehiculoEitv.getCaracteristicas().getNumasientos() != null && !vehiculoEitv.getCaracteristicas().getNumasientos().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getNumasientos()) : null);

		// Número de plazas pie
		vehiculoVO.setnPlazasPie(vehiculoEitv.getCaracteristicas().getNumplazaspie() != null && !vehiculoEitv.getCaracteristicas().getNumplazaspie().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getNumplazaspie()) : null);

		// Potencia Neta
		if (vehiculoEitv.getCaracteristicas().getPotenciamax() != null && !vehiculoEitv.getCaracteristicas().getPotenciamax().equals("")) {
			if (vehiculoEitv.getCaracteristicas().getPotenciamax().contains("/")) {
				String potenciaMax[] = vehiculoEitv.getCaracteristicas().getPotenciamax().split("/");
				vehiculoVO.setPotenciaNeta(new BigDecimal(potenciaMax[0]));
			} else {
				vehiculoVO.setPotenciaNeta(new BigDecimal(vehiculoEitv.getCaracteristicas().getPotenciamax()));
			}
		} else {
			vehiculoVO.setPotenciaNeta(null);
		}

		// Potencia / Peso
		BigDecimal potenciaPeso = vehiculoEitv.getCaracteristicas().getPotenciapeso() != null && !vehiculoEitv.getCaracteristicas().getPotenciapeso().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getPotenciapeso()) : BigDecimal.ZERO;
		vehiculoVO.setPotenciaPeso(potenciaPeso);

		// Reducción ECO
		// vehiculoBean.setReduccionEco(reduccionEco);

		// Tara
		if (vehiculoEitv.getCaracteristicas().getTara() != null && vehiculoEitv.getCaracteristicas().getTara().length() > 7) {
			String tara = vehiculoEitv.getCaracteristicas().getTara().substring(vehiculoEitv.getCaracteristicas().getTara().length() - 7);
			vehiculoVO.setTara(tara);
		} else {
			vehiculoVO.setTara(vehiculoEitv.getCaracteristicas().getTara() != null && !vehiculoEitv.getCaracteristicas().getTara().equals("") ? vehiculoEitv.getCaracteristicas().getTara() : null);
		}

		// Tipo Alimentación
		if (vehiculoEitv.getCaracteristicas().getMonobiflex() != null && !vehiculoEitv.getCaracteristicas().getMonobiflex().equals("")) {
			if ("MONOCOMBUSTIBLE".equalsIgnoreCase(vehiculoEitv.getCaracteristicas().getMonobiflex()) || "MONO COMBUSTIBLE".equalsIgnoreCase(vehiculoEitv.getCaracteristicas().getMonobiflex())
					|| "MONOCARBURANT".equalsIgnoreCase(vehiculoEitv.getCaracteristicas().getMonobiflex())) {
				vehiculoVO.setTipoAlimentacion("M");
			} else if ("BICOMBUSTIBLE".equalsIgnoreCase(vehiculoEitv.getCaracteristicas().getMonobiflex()) || "BI COMBUSTIBLE".equalsIgnoreCase(vehiculoEitv.getCaracteristicas().getMonobiflex())) {
				vehiculoVO.setTipoAlimentacion("B");
			} else {
				vehiculoVO.setTipoAlimentacion(vehiculoEitv.getCaracteristicas().getMonobiflex());
			}
		}

		// Tipo Carburante
		vehiculoVO.setIdCarburante(vehiculoEitv.getCaracteristicas().getCarburante() != null && !vehiculoEitv.getCaracteristicas().getCarburante().equals("") ? vehiculoEitv.getCaracteristicas()
				.getCarburante() : null);

		// Vía Anterior
		vehiculoVO.setViaAnterior(vehiculoEitv.getCaracteristicas().getViaeje1() != null && !vehiculoEitv.getCaracteristicas().getViaeje1().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getViaeje1()) : null);

		// Vía Posterior
		vehiculoVO.setViaPosterior(vehiculoEitv.getCaracteristicas().getViaeje2() != null && !vehiculoEitv.getCaracteristicas().getViaeje2().equals("") ? new BigDecimal(vehiculoEitv
				.getCaracteristicas().getViaeje2()) : null);

		// Categoria electrica
		vehiculoVO.setCategoriaElectrica(vehiculoEitv.getCaracteristicas().getCatelectrica() != null && !vehiculoEitv.getCaracteristicas().getCatelectrica().isEmpty() ? vehiculoEitv
				.getCaracteristicas().getCatelectrica() : null);

		// Autonomia Electrica
		vehiculoVO.setAutonomiaElectrica(vehiculoEitv.getCaracteristicas().getAutonoelectrica() != null && !vehiculoEitv.getCaracteristicas().getAutonoelectrica().equals("") ? new BigDecimal(
				vehiculoEitv.getCaracteristicas().getAutonoelectrica()) : null);

		// Velocidad Máxima

	}

	private void setDatosTecnicosConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		String clasificacionITV = vehiculoEitv.getFabricante().getClasificacion() != null && !vehiculoEitv.getFabricante().getClasificacion().equals("") ? vehiculoEitv.getFabricante()
				.getClasificacion() : null;
		String criterioConstruccion = clasificacionITV != null ? clasificacionITV.substring(0, 2) : null;
		String criterioUtilizacion = clasificacionITV != null ? clasificacionITV.substring(2) : null;

		// Criterio de Construcción
		vehiculoVO.setIdCriterioConstruccion(criterioConstruccion);

		// Criterio de Utilización
		vehiculoVO.setIdCriterioUtilizacion(criterioUtilizacion);

		// Clasificación ITV
		vehiculoVO.setClasificacionItv(clasificacionITV);

		// Color
		vehiculoVO.setIdColor(vehiculoEitv.getCaracteristicas().getColor() != null && !vehiculoEitv.getCaracteristicas().getColor().equals("") ? vehiculoEitv.getCaracteristicas().getColor() : null);

		// Fabricante
		vehiculoVO.setFabricante(vehiculoEitv.getGeneral().getNomfabricante() != null && !vehiculoEitv.getGeneral().getNomfabricante().equals("") ? vehiculoEitv.getGeneral().getNomfabricante()
				: null);

		// Número de serie
		vehiculoVO.setnSerie(vehiculoEitv.getGeneral().getSerieindustria() != null && !vehiculoEitv.getGeneral().getSerieindustria().equals("") ? vehiculoEitv.getGeneral().getSerieindustria() : null);

		// Número de homologación

		vehiculoVO.setnHomologacion(vehiculoEitv.getCertificacion().getContraseña() != null && !vehiculoEitv.getCertificacion().getContraseña().equals("") ? vehiculoEitv.getCertificacion()
				.getContraseña() : null);

		// Servicio
		// vehiculoVO.setIdServicio(vehiculoEitv.getComunidad().getServicio()!=null && !vehiculoEitv.getComunidad().getServicio().equals("") ? vehiculoEitv.getComunidad().getServicio() : null);

		// Variante
		vehiculoVO.setVariante(vehiculoEitv.getCaracteristicas().getVariante() != null && !vehiculoEitv.getCaracteristicas().getVariante().equals("") ? vehiculoEitv.getCaracteristicas().getVariante()
				: null);

		// Versión
		vehiculoVO.setVersion(vehiculoEitv.getCaracteristicas().getVersion() != null && !vehiculoEitv.getCaracteristicas().getVersion().equals("") ? vehiculoEitv.getCaracteristicas().getVersion()
				: null);
	}

	private void setDatosVehiculoConversion(Vehiculo vehiculoEitv, VehiculoVO vehiculoVO) {
		// Bastidor
		vehiculoVO.setBastidor(vehiculoEitv.getFabricante().getNumerovin());

		// NIVE
		if (vehiculoEitv.getGeneral().getNive() != null) {
			vehiculoVO.setNive(vehiculoEitv.getGeneral().getNive().toUpperCase());
		}

		// Código ITV
		vehiculoVO.setCoditv((vehiculoEitv.getFabricante().getCodigoitv() != null && !vehiculoEitv.getFabricante().getCodigoitv().equals("") ? vehiculoEitv.getFabricante().getCodigoitv() : null));

		// Marca
		// se busca cod marca
		if (vehiculoEitv.getCaracteristicas().getMarca() != null) {
			MarcaDgtVO marcaDgtVO = servicioMarcaDgt.getMarcaDgt(null, vehiculoEitv.getCaracteristicas().getMarca(), true);
			if (marcaDgtVO != null) {
				vehiculoVO.setCodigoMarca(marcaDgtVO.getCodigoMarca().toString());
			}
		}
		// Modelo
		vehiculoVO.setModelo(vehiculoEitv.getCaracteristicas().getDenominacion() != null && !vehiculoEitv.getCaracteristicas().getDenominacion().equals("") ? vehiculoEitv.getCaracteristicas()
				.getDenominacion() : null);

		// Matrícula (sólo si viene, porque el vehículo ya esté matriculado)
		vehiculoVO.setMatricula(vehiculoEitv.getTrafico().getMatricula());
	}

	private RespuestaConsultaEitv guardarPDFConsultaEitv(EitvQueryWSResponse respuestaWS, String nombreFichero) throws Exception {
		RespuestaConsultaEitv respuesta = null;
		try {
			String fichaEitv = respuestaWS.getFicha();
			byte[] ptcBytes = utiles.doBase64Decode(fichaEitv, UTF_8);
			FicheroBean fichero = new FicheroBean();
			fichero.setTipoDocumento(ConstantesGestorFicheros.MATE);
			fichero.setSubTipo(ConstantesGestorFicheros.PDF_CONSULTA_EITV);
			fichero.setSobreescribir(true);
			fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
			fichero.setFecha(utilesFecha.getFechaHoraActualLEG());
			fichero.setNombreDocumento(nombreFichero);
			fichero.setFicheroByte(ptcBytes);
			File ficheroResult = gestorDocumentos.guardarByte(fichero);
			if (ficheroResult == null) {
				respuesta = new RespuestaConsultaEitv();
				respuesta.setException(new Exception("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta eITV."));
				log.error("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta eITV.");
			}
		} catch (OegamExcepcion e) {
			respuesta = new RespuestaConsultaEitv();
			respuesta.setException(new Exception(e));
			log.error("Ha sucedido un error a la hora de guardar el fichero pdf de la consulta de la tarjeta eITV, error: ", e);
		}
		return respuesta;
	}

	@Override
	public ResultBean firmarConsultaTarjetaEitv(ConsultaTarjeta consultaTarjetaEitv, TramiteTraficoVO tramiteTraficoVO) {
		ResultBean resultado = new ResultBean(false);
		String xmlFirmado = null;
		try {
			String aliasColegiado = tramiteTraficoVO.getContrato().getColegiado().getAlias();
			String xmlConsultaTarjeta = getXmlConsultaTarjeta(consultaTarjetaEitv);
			String xml = generarXmlDatosFirmados(xmlConsultaTarjeta);
			String idFirma = getUtilesViafirma().firmarPruebaCertificadoCaducidad(xml, aliasColegiado);
			if (idFirma != null) {
				idFirma = getUtilesViafirma().firmarConsultaTarjetaEitv(xml.getBytes(), aliasColegiado);
				if (idFirma != null) {
					byte[] txtFirmado = getUtilesViafirma().getBytesDocumentoFirmado(idFirma);
					String firmaB64 = new String(txtFirmado);
					consultaTarjetaEitv.setFirmaGestor(firmaB64.getBytes(ServicioWebServiceConsultaEitv.UTF_8));
					xmlFirmado = getXmlConsultaTarjeta(consultaTarjetaEitv);
					resultado.addAttachment("xmlFirmado", xmlFirmado);
				} else {
					resultado.setError(true);
					resultado.addMensajeALista("Ha sucedido un error a la hora de firmar el xml.");
				}
			} else {
				resultado.setError(true);
				resultado.addMensajeALista("El certificado del colegiado está caducado.");
			}
		} catch (JAXBException | UnsupportedEncodingException e) {
			log.error("Error a la hora de crear el xml de los datos firmados, error: ", e);
			resultado.setError(true);
			resultado.addMensajeALista("Error a la hora de crear el xml de los datos firmados.");
		}
		return resultado;
	}

	public String firmarConsultaTarjetaEitvPreMatriculados(ConsultaTarjeta consultaTarjetaEitv, VehiculoPrematriculadoDto vehiculoPrematriculado, ColegiadoVO colegiadoVO) {
		String resultado = null;
		try {
			String xmlConsultaTarjeta = getXmlConsultaTarjeta(consultaTarjetaEitv);
			String xml = generarXmlDatosFirmados(xmlConsultaTarjeta);
			String idFirma = getUtilesViafirma().firmarPruebaCertificadoCaducidad(xml, colegiadoVO.getAlias());
			if (idFirma != null) {
				idFirma = getUtilesViafirma().firmarConsultaTarjetaEitv(xml.getBytes(), colegiadoVO.getAlias());
				if (idFirma != null) {
					byte[] txtFirmado = getUtilesViafirma().getBytesDocumentoFirmado(idFirma);
					String firmaB64 = new String(txtFirmado);
					consultaTarjetaEitv.setFirmaGestor(firmaB64.getBytes(ServicioWebServiceConsultaEitv.UTF_8));
					resultado = getXmlConsultaTarjeta(consultaTarjetaEitv);
				}
			}
		} catch (JAXBException | UnsupportedEncodingException e) {
			log.error("Error a la hora de crear el xml de los datos firmados, error: ", e);
		}
		return resultado;
	}

	@Override
	public String getXmlConsultaTarjeta(ConsultaTarjeta consultaTarjetaEitv) throws JAXBException, UnsupportedEncodingException {
		StringWriter writer = new StringWriter();
		Marshaller m = getContext(ServicioWebServiceConsultaEitv.NAME_CONTEXT).createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, ServicioWebServiceConsultaEitv.XML_ENCODING);
		m.marshal(consultaTarjetaEitv, writer);
		writer.flush();
		return writer.toString();
	}

	private String generarXmlDatosFirmados(String xmlConsulta) throws JAXBException, UnsupportedEncodingException {
		String nodoDatosFirmados = obtenerNodoDatosFirmados(xmlConsulta);
		nodoDatosFirmados = utiles.doBase64Encode(nodoDatosFirmados.getBytes(ServicioWebServiceConsultaEitv.UTF_8));
		nodoDatosFirmados = nodoDatosFirmados.replaceAll("\n", "");
		return encerrarAfirmaContent(nodoDatosFirmados);
	}

	private String obtenerNodoDatosFirmados(String xml) {
		xml = xml.replaceAll("<ns2:", "<");
		xml = xml.replaceAll("</ns2:", "</");
		int inicio = xml.indexOf("<Datos_Firmados>") + 16;
		int fin = xml.indexOf("</Datos_Firmados>");
		return xml.substring(inicio, fin);
	}

	private static String encerrarAfirmaContent(String encodedAFirmar) {
		String xmlAFirmar = "<AFIRMA><CONTENT Id=\"" + MATEGE_NODO_FIRMAR_ID + "\">" + encodedAFirmar + "</CONTENT></AFIRMA>";
		log.debug("XML a firmar:" + xmlAFirmar);
		return xmlAFirmar;
	}

	public ServicioVehiculo getServicioVehiculo() {
		return servicioVehiculo;
	}

	public void setServicioVehiculo(ServicioVehiculo servicioVehiculo) {
		this.servicioVehiculo = servicioVehiculo;
	}

	public JAXBContext getContext(String nameContext) throws JAXBException {
		return JAXBContext.newInstance(nameContext);
	}

	public UtilesViafirma getUtilesViafirma() {
		if (utilesViafirma == null) {
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}

	public void setUtilesViafirma(UtilesViafirma utilesViafirma) {
		this.utilesViafirma = utilesViafirma;
	}

	public ObjectFactory getObjectFactory() {
		if (objectFactory == null) {
			objectFactory = new ObjectFactory();
		}
		return objectFactory;
	}

	public void setObjectFactory(ObjectFactory objectFactory) {
		this.objectFactory = objectFactory;
	}

	public ServicioContrato getServicioContrato() {
		return servicioContrato;
	}

	public void setServicioContrato(ServicioContrato servicioContrato) {
		this.servicioContrato = servicioContrato;
	}

	public ServicioVehiculosPrematriculados getServicioVehiculosPrematriculados() {
		return servicioVehiculosPrematriculados;
	}

	public void setServicioVehiculosPrematriculados(ServicioVehiculosPrematriculados servicioVehiculosPrematriculados) {
		this.servicioVehiculosPrematriculados = servicioVehiculosPrematriculados;
	}

	public ComunesServicioVehiculoPrematriculados getServiciosComunes() {
		if (serviciosComunes == null) {
			serviciosComunes = new ComunesServicioVehiculoPrematriculados();
		}
		return serviciosComunes;
	}

	public void setServiciosComunes(ComunesServicioVehiculoPrematriculados serviciosComunes) {
		this.serviciosComunes = serviciosComunes;
	}

	public ServicioColegiado getServicioColegiado() {
		return servicioColegiado;
	}

	public void setServicioColegiado(ServicioColegiado servicioColegiado) {
		this.servicioColegiado = servicioColegiado;
	}

	public ServicioMarcaDgt getServicioMarcaDgt() {
		return servicioMarcaDgt;
	}

	public void setServicioMarcaDgt(ServicioMarcaDgt servicioMarcaDgt) {
		this.servicioMarcaDgt = servicioMarcaDgt;
	}

}