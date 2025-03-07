package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.StringWriter;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.validation.Schema;
import javax.xml.validation.SchemaFactory;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.licencias.model.enumerados.TipoEdificacionEnum;
import org.gestoresmadrid.core.licencias.model.enumerados.TipoResumenEdificacionEnum;
import org.gestoresmadrid.core.licencias.model.vo.LcActuacionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaAltaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPlantaBajaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosPortalAltaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDatosSuministrosVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDireccionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcDocumentoLicenciaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcEdificacionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcEpigrafeVO;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioAltaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoEdificioBajaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcInfoLocalVO;
import org.gestoresmadrid.core.licencias.model.vo.LcIntervinienteVO;
import org.gestoresmadrid.core.licencias.model.vo.LcObraVO;
import org.gestoresmadrid.core.licencias.model.vo.LcParteAutonomaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcResumenEdificacionVO;
import org.gestoresmadrid.core.licencias.model.vo.LcSupNoComputablePlantaVO;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.core.model.enumerados.TipoInterviniente;
import org.gestoresmadrid.core.model.enumerados.TipoTramiteTrafico;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.licenciasCam.gestor.GestorDatosMaestrosLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.AguaCalienteSanitaria;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.AutoliquidacionesLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CaptacionEnergiaSolar;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ClimatizacionAireAcondicionado;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Codigo;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigosError;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosActuacionSlim;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosAparcamientoAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosComunicacionSolicitud;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosEdificacionAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosEpigrafeLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosObraLIC;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosPlantasAltaLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosPlantasBaja;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosPortalAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DatosSuperficiePortal;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DireccionCompleta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DireccionCorta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DireccionLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.DireccionNoNormalizada;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.EmplazamientoLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.EquiposInstRelevantesOrdinario;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.InfoEdificioAltaLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.InfoEdificioBajaLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.InformacionLocalLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.InstalacionesCalefaccion;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.LICEMCC;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Mensaje;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Notificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ObjectFactory;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.OtrosDatosActuacionLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ParametrosEdificacionAltaLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ParametrosEdificacionBajaLic;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ParteAutonoma;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Persona;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.Peticion;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ProyectoGarajeAparcamientoAlta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ResumenEdificacion;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ResumenEdificacionTipo;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.ResumenEdificacionValor;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.SolicitudConjuntaAutorizaciones;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.SuperficieNoComputablePlanta;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.TiposAparcamiento;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.VentilacionForzada;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcDocumentoLicencia;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicEnviarSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.request.PeticionEnvio;
import org.gestoresmadrid.oegam2comun.licenciasCam.view.bean.DatoMaestroLicBean;
import org.gestoresmadrid.oegam2comun.proceso.enumerados.ProcesosEnum;
import org.gestoresmadrid.oegam2comun.registradores.corpme.xml.XmlCORPMEFactory;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.xml.sax.SAXException;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;
import viafirma.utilidades.UtilesViafirma;

@Service
public class ServicioLicEnviarSolictudRestWSImpl implements ServicioLicEnviarSolicitudRestWS {

	private static final long serialVersionUID = 670688101064717720L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicEnviarSolictudRestWSImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioLcTramite servicioLcTramite;

	@Autowired
	ServicioLcDocumentoLicencia servicioLcDocumentoLicencia;

	@Autowired
	GestorDatosMaestrosLic gestorDatosMaestrosLic;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Override
	public ResultBean enviarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, true);

			// TODO: ver si se ese es el código correcto
			if ("COK1".equals(tramite.getRespuestaDoc())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No ha realizado el envío de los documentos.  Envíe la documentación antes de enviar la solicitud.");
			} else if (!validarDocumentacion(tramite.getNumExpediente())) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Faltan documentos obligatorios por enviar. Envíe toda documentación antes de enviar la solicitud.");
			}

			Peticion peticion = obtenerPeticion(tramite, tramite.getIdSolicitud(), Boolean.FALSE);

			String xml = toXML(peticion);

			if (validar(xml)) {
				log.error("No se ha validado correctamente el XML contra el XSD");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha validado correctamente el XML contra el XSD");
				return resultado;
			}

			resultado = firmar(xml, aliasColegiado);
			if (resultado != null && !resultado.getError()) {
				String xmlFirmado = (String) resultado.getAttachment("xmlFirmado");
				String nombreDocumento = "EnviarSolicitud_" + numExpediente;
				resultado = guardarDocumentos(xmlFirmado, numExpediente, nombreDocumento, ConstantesGestorFicheros.ENVIO);
				if (resultado != null && !resultado.getError()) {
					resultado = servicioCola.crearSolicitud(ProcesosEnum.LIC_ENVIAR_SOLICITUD.getNombreEnum(), nombreDocumento, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Licencias_CAM
							.getValorEnum(), tramite.getNumExpediente().toString(), new BigDecimal(idUsuario), null, new BigDecimal(idContrato));
					if (resultado != null && !resultado.getError()) {
						tramite.setRespuestaEnv("Solicitud creada correctamente");
						servicioLcTramite.guardarOActualizar(tramite);
						servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Pendiente_Respuesta_Cam.getValorEnum()), new BigDecimal(idUsuario));
					}
				} else {
					resultado.setMensaje("Error al guardar el XML en disco");
				}
			} else {
				resultado.setMensaje("Error al firmar el XML");
			}
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de licencias CAM, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al enviar la solicitud de licencias CAM");
		} catch (OegamExcepcion oe) {
			log.error("El trámite ya se encuentra en la cola, por lo que no se duplicara, error:", oe);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite ya se encuentra en la cola, por lo que no se duplicara");
		}
		return resultado;
	}

	private boolean validarDocumentacion(BigDecimal numExpediente) {
		boolean validado = false;
		List<DatoMaestroLicBean> docObligatorios = gestorDatosMaestrosLic.obtenerTiposDocumentosObligatorios();
		if (docObligatorios != null && !docObligatorios.isEmpty()) {
			List<LcDocumentoLicenciaVO> documentos = servicioLcDocumentoLicencia.getLcDocumentoLicenciaPorNumExp(numExpediente);
			for (DatoMaestroLicBean doc : docObligatorios) {
				validado = false;
				for (LcDocumentoLicenciaVO docTramite : documentos) {
					if (docTramite.getTipoDocumento().equals(doc.getCodigo())) {
						validado = true;
					}
				}
				if (!validado) {
					return false;
				}
			}
		}
		return validado;
	}

	@Override
	public ResultBean validarSolicitud(BigDecimal numExpediente, Long idUsuario, Long idContrato, String aliasColegiado) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, true);

			if (tramite != null && StringUtils.isBlank(tramite.getIdSolicitud())) {
				String idSolicitud = servicioLcTramite.generarIdentificadorCam();
				if (idSolicitud == null) {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No se ha generado el identificador de solicitud");
					return resultado;
				} else {
					tramite.setIdSolicitud(idSolicitud.toString());
					servicioLcTramite.guardarOActualizar(tramite);
				}
			}

			Peticion peticion = obtenerPeticion(tramite, tramite.getIdSolicitud(), Boolean.TRUE);

			String xml = toXML(peticion);

			if (validar(xml)) {
				log.error("No se ha validado correctamente el XML contra el XSD");
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No se ha validado correctamente el XML contra el XSD");
				return resultado;
			}

			// TODO: no hay que firmar el xml
			// resultado = firmar(xml, aliasColegiado);
			// if (resultado != null && !resultado.getError()) {
			// String xmlFirmado = (String) resultado.getAttachment("xmlFirmado");
			String nombreDocumento = "ValidarSolicitud_" + numExpediente;
			resultado = guardarDocumentos(xml, numExpediente, nombreDocumento, ConstantesGestorFicheros.ENVIO);
			if (resultado != null && !resultado.getError()) {
				resultado = servicioCola.crearSolicitud(ProcesosEnum.LIC_VALIDAR_SOLICITUD.getNombreEnum(), nombreDocumento, gestorPropiedades.valorPropertie(NOMBRE_HOST), TipoTramiteTrafico.Licencias_CAM
						.getValorEnum(), tramite.getNumExpediente().toString(), new BigDecimal(idUsuario), null, new BigDecimal(idContrato));
				if (resultado != null && !resultado.getError()) {
					tramite.setRespuestaVal("Solicitud creada correctamente");
					servicioLcTramite.guardarOActualizar(tramite);
					servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Pendiente_Comprobar.getValorEnum()), new BigDecimal(idUsuario));
				}
			} else {
				resultado.setMensaje("Error al guardar el XML en disco");
			}
			// } else {
			// resultado.setMensaje("Error al firmar el XML");
			// }
		} catch (Exception e) {
			log.error("Error al enviar la solicitud de licencias CAM, error:", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al validar la solicitud de licencias CAM");
		} catch (OegamExcepcion oe) {
			log.error("El trámite ya se encuentra en la cola, por lo que no se duplicara, error:", oe);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("El trámite ya se encuentra en la cola, por lo que no se duplicara");
		}
		return resultado;
	}

	@Override
	public ResultBean enviarSolicitudRest(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			String xmlBase64 = obtenerXMLBase64(numExpediente, nombreXml);
			if (xmlBase64 != null && !xmlBase64.isEmpty()) {
				resultado = llamadaRest(xmlBase64, tramite, tramite.getIdSolicitud(), Boolean.FALSE, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe el xml de envio para poder realizar el envío de solicitud.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar el envío de solicitud para las Licencias CAM: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el envío de solicitud para las Licencias CAM");
		}
		return resultado;
	}

	@Override
	public ResultBean validarSolicitudRest(BigDecimal numExpediente, String nombreXml, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			String xmlBase64 = obtenerXMLBase64(numExpediente, nombreXml);
			if (xmlBase64 != null && !xmlBase64.isEmpty()) {
				resultado = llamadaRest(xmlBase64, tramite, tramite.getIdSolicitud(), Boolean.TRUE, idUsuario);
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe el xml de envio para poder realizar el validado de solicitud.");
			}
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar el validado de solicitud para las Licencias CAM: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar el validado de solicitud para las Licencias CAM");
		}
		return resultado;
	}

	@Override
	@Transactional
	public void finalizarValidacion(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario) {
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			tramite.setRespuestaVal(respuesta);
			servicioLcTramite.guardarOActualizar(tramite);
			servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), estado, idUsuario);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora finalizar el proceso de validación de Licencias CAM: " + numExpediente + ", error: ", e);
		}
	}

	@Override
	@Transactional
	public void finalizarEnvio(BigDecimal numExpediente, String respuesta, BigDecimal estado, BigDecimal idUsuario) {
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			tramite.setRespuestaEnv(respuesta);
			servicioLcTramite.guardarOActualizar(tramite);
			servicioLcTramite.cambiarEstado(true, numExpediente, tramite.getEstado(), estado, idUsuario);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora finalizar el proceso de envío de la solicitud de Licencias CAM: " + numExpediente + ", error: ", e);
		}
	}

	private ResultBean firmar(String xml, String aliasColegiado) {
		ResultBean resultado = new ResultBean(false);
		try {
			UtilesViafirma utilesViafirma = new UtilesViafirma();
			byte[] txtFirmado = utilesViafirma.firmarXMLLicenciasCam(xml.getBytes(), aliasColegiado);
			if (txtFirmado != null) {
				String xmlFirmado = new String(txtFirmado);
				resultado.addAttachment("xmlFirmado", xmlFirmado);
			} else {
				resultado.setError(true);
			}
		} catch (Exception e) {
			resultado.setError(true);
		}
		return resultado;
	}

	private Peticion obtenerPeticion(LcTramiteVO tramite, String idSolicitud, Boolean validar) {
		ObjectFactory objFactory = new ObjectFactory();
		LICEMCC licEmcc = objFactory.createLICEMCC();

		LcIntervinienteVO intervinienteInteresadoPrincipal = null;
		LcIntervinienteVO intervinienteRepresentanteInteresado = null;
		LcIntervinienteVO intervinienteNotificacion = null;
		Set<LcIntervinienteVO> otrosInteresados = new HashSet<>();

		// Previamente desglosamos los intervinientes del listado
		for (LcIntervinienteVO interviniente : tramite.getLcIntervinientes()) {
			if (TipoInterviniente.InteresadoPrincipal.getValorEnum().equalsIgnoreCase(interviniente.getTipoInterviniente())) {
				intervinienteInteresadoPrincipal = interviniente;
			} else if (TipoInterviniente.RepresentanteInteresado.getValorEnum().equalsIgnoreCase(interviniente.getTipoInterviniente())) {
				intervinienteRepresentanteInteresado = interviniente;
			} else if (TipoInterviniente.Notificacion.getValorEnum().equalsIgnoreCase(interviniente.getTipoInterviniente())) {
				intervinienteNotificacion = interviniente;
			} else if (TipoInterviniente.OtroInteresado.getValorEnum().equalsIgnoreCase(interviniente.getTipoInterviniente())) {
				otrosInteresados.add(interviniente);
			}
		}

		licEmcc.setDescripcion(tramite.getRefPropia());
		licEmcc.setIdSolicitud(idSolicitud);
		licEmcc.setTipoActuacion(tramite.getTipoActuacion());

		licEmcc.setDatosComunicacionSolicitud(rellenarDatosComunicacionSolicitud(objFactory, tramite));
		licEmcc.setInteresadoPrincipal(rellenarPersona(objFactory, intervinienteInteresadoPrincipal));

		// Lista de otros interesados
		if (otrosInteresados != null && !otrosInteresados.isEmpty()) {
			for (LcIntervinienteVO otroInteresado : otrosInteresados) {
				licEmcc.getOtrosInteresados().add(rellenarPersona(objFactory, otroInteresado));
			}
		}

		if (intervinienteRepresentanteInteresado != null) {
			licEmcc.setRepresentante(rellenarPersona(objFactory, intervinienteRepresentanteInteresado));
		}

		if (intervinienteNotificacion != null) {
			licEmcc.setNotificacion(rellenarNotificacion(objFactory, intervinienteNotificacion));
		}

		licEmcc.setEmplazamientoActuacion(rellenarEmplazamientoLic(objFactory, tramite.getLcIdDirEmplazamiento()));
		licEmcc.setDatosActuacion(rellenarActuacionSlim(objFactory, tramite.getLcActuacion()));
		licEmcc.setSolicitudConjuntaAutorizaciones(rellenarSolicitudConjuntaAutorizaciones(objFactory, tramite));
		licEmcc.setDatosObra(rellenarDatosObrasLic(objFactory, tramite.getLcObra()));
		licEmcc.setOtrosDatosActuacion(rellenarOtrosDatosActuacionLic(objFactory, tramite.getLcActuacion()));
		licEmcc.setParametrosEdificacionAlta(rellenarParametrosEdificacionAltaLic(objFactory, tramite.getLcEdificacionesAsList()));
		licEmcc.setParametrosEdificacionBaja(rellenarParametrosEdificacionBajaLic(objFactory, tramite.getLcEdificacionesAsList()));
		if (tramite.getLcDatosSuministros() != null) {
			licEmcc.setEquiposInstRelevantes(rellenarEquiposInstRelevantesOrdinario(objFactory, tramite.getLcDatosSuministros()));
			licEmcc.setVentilacionForzada(rellenarVentilacionForzada(objFactory, tramite.getLcDatosSuministros()));
			licEmcc.setAguaCalienteSanitaria(rellenarAguaCalienteSanitaria(objFactory, tramite.getLcDatosSuministros()));
			licEmcc.setInstalacionesCalefaccion(rellenarInstalacionesCalefaccion(objFactory, tramite.getLcDatosSuministros()));
			licEmcc.setClimatizacionAireAcondicionado(rellenarClimatizacionAireAcondicionado(objFactory, tramite.getLcDatosSuministros()));
			licEmcc.setCaptacionEnergiaSolar(rellenarCaptacionEnergiaSolar(objFactory, tramite.getLcDatosSuministros()));
		}
		if (tramite.getLcInfoLocal() != null) {
			licEmcc.setInformacionLocal(rellenarInformacionLocalLic(objFactory, tramite.getLcInfoLocal()));
		}
		licEmcc.setAutoliquidaciones(rellenarAutoliquidacionesLic(objFactory, tramite));

		Mensaje mensaje = objFactory.createMensaje();
		if (validar) {
			mensaje.setLICVALI(licEmcc);
		} else {
			mensaje.setLICEMCC(licEmcc);
		}

		Peticion peticion = objFactory.createPeticion();
		peticion.setMensaje(mensaje);

		return peticion;
	}

	private AutoliquidacionesLic rellenarAutoliquidacionesLic(ObjectFactory objFactory, LcTramiteVO tramite) {
		AutoliquidacionesLic autoliquidacionesLic = objFactory.createAutoliquidacionesLic();
		autoliquidacionesLic.setNumAutoliquidacionPagada(tramite.getNumAutoliquidacionPagada());
		return autoliquidacionesLic;
	}

	private InformacionLocalLic rellenarInformacionLocalLic(ObjectFactory objFactory, LcInfoLocalVO infoLocal) {
		InformacionLocalLic informacionLocalLic = objFactory.createInformacionLocalLic();
		informacionLocalLic.setAccesoPrincipalIgual(infoLocal.getAccesoPrincipalIgual());

		if ("N".equals(infoLocal.getAccesoPrincipalIgual())) {
			informacionLocalLic.setDireccionAct(rellenarDireccionCorta(objFactory, infoLocal.getLcDirInfoLocal()));
		}

		informacionLocalLic.setLocalizacion(infoLocal.getLocalizacion());
		informacionLocalLic.setCodLocal(infoLocal.getCodLocal());
		informacionLocalLic.setNombreAgrupacion(infoLocal.getNombreAgrupacion());
		informacionLocalLic.setPlanta(infoLocal.getPlanta());
		informacionLocalLic.setNumLocal(infoLocal.getNumLocal());
		informacionLocalLic.setEscalera(infoLocal.getEscalera());
		informacionLocalLic.setPuerta(infoLocal.getPuerta());
		informacionLocalLic.setReferenciaCatastral(infoLocal.getReferenciaCatastral());
		informacionLocalLic.setSuperficieUtilLocal(infoLocal.getSuperficieUtilLocal());
		informacionLocalLic.setSuperficieUtilUsoPublico(infoLocal.getSuperficieUtilUsoPublico());
		informacionLocalLic.setPresupuestoObraActividad(infoLocal.getPresupuestoObraActividad());
		informacionLocalLic.setPotenciaNominal(infoLocal.getPotenciaNominal());
		informacionLocalLic.setKwa(infoLocal.getKwa());
		informacionLocalLic.setDescripcionActividad(infoLocal.getDescripcionActividad());
		informacionLocalLic.setSolicitaRotulo(infoLocal.getSolicitaRotulo());
		informacionLocalLic.setRotuloSolicitado(infoLocal.getRotuloSolicitado());

		// Lista Epígrafes
		if (infoLocal.getLcEpigrafes() != null && !infoLocal.getLcEpigrafes().isEmpty()) {
			for (LcEpigrafeVO epigrafe : infoLocal.getLcEpigrafes()) {
				informacionLocalLic.getEpigrafes().add(rellenarDatosEpigrafeLic(objFactory, epigrafe));
			}
		}

		informacionLocalLic.setActividadAnterior(infoLocal.getActividadAnterior());
		informacionLocalLic.setExpedienteActividadAnterior(infoLocal.getExpedienteActividadAnterior());
		informacionLocalLic.setSujetaAOtros(infoLocal.getSujetaAOtros());
		informacionLocalLic.setDescOtros(infoLocal.getDescOtros());
		if (infoLocal.getIdLocal() != null) {
			informacionLocalLic.setIdLocal(infoLocal.getIdLocal().toBigInteger());
		}
		return informacionLocalLic;
	}

	private DatosEpigrafeLic rellenarDatosEpigrafeLic(ObjectFactory objFactory, LcEpigrafeVO epigrafe) {
		DatosEpigrafeLic datosEpigrafeLic = objFactory.createDatosEpigrafeLic();
		datosEpigrafeLic.setSeccion(epigrafe.getSeccion());
		datosEpigrafeLic.setEpigrafe(epigrafe.getEpigrafe());
		return datosEpigrafeLic;
	}

	private DireccionCorta rellenarDireccionCorta(ObjectFactory objFactory, LcDireccionVO direccion) {
		DireccionCorta direccionCorta = objFactory.createDireccionCorta();
		direccionCorta.setClaseVial(direccion.getTipoVia());
		direccionCorta.setNombreVial(direccion.getNombreVia());
		direccionCorta.setTipoNumeracion(direccion.getTipoNumeracion());
		if (direccion.getNumCalle() != null) {
			direccionCorta.setNumCalle(direccion.getNumCalle().toBigInteger());
		}
		direccionCorta.setCalificador(direccion.getCalificador());
		direccionCorta.setClaseDireccion(direccion.getClaseDireccion());
		if (direccion.getCodDireccion() != null) {
			direccionCorta.setCodDireccion(direccion.getCodDireccion().toBigInteger());
		}
		return direccionCorta;
	}

	private CaptacionEnergiaSolar rellenarCaptacionEnergiaSolar(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		CaptacionEnergiaSolar captacionEnergiaSolar = objFactory.createCaptacionEnergiaSolar();
		captacionEnergiaSolar.setCaptacionEnergiaSolar(suministros.getCaptEnergiaSolar());
		if ("S".equals(suministros.getCaptEnergiaSolar())) {
			captacionEnergiaSolar.setEquiposFotovoltaicos(suministros.getEquipoFotovoltaico());
			if (suministros.getNumEquiposUsoFotovoltaico() != null) {
				captacionEnergiaSolar.setNumEquiposFotovoltaicos(suministros.getNumEquiposUsoFotovoltaico().toBigInteger());
			}
		}
		captacionEnergiaSolar.setEquiposUsoTermico(suministros.getEquiposUsosTermico());
		if (suministros.getNumEquiposUsoTermico() != null) {
			captacionEnergiaSolar.setNumEquiposUsoTermico(suministros.getNumEquiposUsoTermico().toBigInteger());
		}
		captacionEnergiaSolar.setSuperficieCaptacionEquiposUsoTermico(suministros.getSuperfCaptEqUsoTermico());
		captacionEnergiaSolar.setSuperficieCaptacionEquiposFotovoltaicos(suministros.getSuperfCaptEqUsoFotovoltaico());
		return captacionEnergiaSolar;
	}

	private ClimatizacionAireAcondicionado rellenarClimatizacionAireAcondicionado(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		ClimatizacionAireAcondicionado climatizacionAireAcondicionado = objFactory.createClimatizacionAireAcondicionado();
		climatizacionAireAcondicionado.setClimatizacionAireAcondicionado(suministros.getClimatizacionAc());

		if ("N".equals(suministros.getClimatizacionAc())) {
			climatizacionAireAcondicionado.setEquiposAutonomos(BigInteger.ZERO);
			climatizacionAireAcondicionado.setEnfriadoresEvaporativos(BigInteger.ZERO);
			climatizacionAireAcondicionado.setNumTorresRefrigeracion(BigInteger.ZERO);
		} else {
			if (suministros.getEquiposAutonomos() != null) {
				climatizacionAireAcondicionado.setEquiposAutonomos(suministros.getEquiposAutonomos().toBigInteger());
			}

			if (suministros.getEnfriadoresEvaporativos() != null) {
				climatizacionAireAcondicionado.setEnfriadoresEvaporativos(suministros.getEnfriadoresEvaporativos().toBigInteger());
			}

			if (suministros.getNumTorresRefrigeracion() != null) {
				climatizacionAireAcondicionado.setNumTorresRefrigeracion(suministros.getNumTorresRefrigeracion().toBigInteger());
			}
			climatizacionAireAcondicionado.setInstalacionesGeneralEdificio(suministros.getInstalacionesGeneralEdificio());
			climatizacionAireAcondicionado.setEquipoCentralizadoLocal(suministros.getEquipoCentralizadoLocal());
			climatizacionAireAcondicionado.setCondensacionAireSalidaChimenea(suministros.getCondensacionSalidaChimenea());
			climatizacionAireAcondicionado.setCondensacionAireSalidaFachada(suministros.getCondensacionSalidaFachada());
			climatizacionAireAcondicionado.setCondensadorSituadoCubierta(suministros.getCondensadorCubierta());
		}

		climatizacionAireAcondicionado.setPotenciaCalorificaInstGen(suministros.getPotenciaCalorInstGen());
		climatizacionAireAcondicionado.setPotenciaFrigorificaInstGen(suministros.getPotenciaFrigoInstGen());
		climatizacionAireAcondicionado.setPotenciaCalorificaEqCentr(suministros.getPotenciaCalorEqCentr());
		climatizacionAireAcondicionado.setPotenciaFrigorificaEqCentr(suministros.getPotenciaFrigoEqCentr());
		climatizacionAireAcondicionado.setHayEquiposAutonomos(suministros.getHayEquiposAutonomos());
		climatizacionAireAcondicionado.setPotenciaCalorificaEqAutonomos(suministros.getPotenciaCalorEqAutonomos());
		climatizacionAireAcondicionado.setPotenciaFrigorificaEqAutonomos(suministros.getPotenciaFrigoEqAutonomos());
		climatizacionAireAcondicionado.setHayEnfriadoresEvaporativos(suministros.getHayEnfriadoresEvaporativos());
		climatizacionAireAcondicionado.setPotenciaCalorificaEnfriadores(suministros.getPotenciaCalorEnfriadores());
		climatizacionAireAcondicionado.setPotenciaFrigorificaEnfriadores(suministros.getPotenciaFrigoEnfriadores());
		climatizacionAireAcondicionado.setCaudalSalidaFachada(suministros.getCaudalSalidaFachada());
		climatizacionAireAcondicionado.setCaudalSalidaCubierta(suministros.getCaudalSalidaCubierta());
		climatizacionAireAcondicionado.setCaudalCondensadorCubierta(suministros.getCaudalCondensadorCubierta());
		climatizacionAireAcondicionado.setTorreRefrigeracion(suministros.getTorreRefirgeracion());
		return climatizacionAireAcondicionado;
	}

	private InstalacionesCalefaccion rellenarInstalacionesCalefaccion(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		InstalacionesCalefaccion instalacionesCalefaccion = objFactory.createInstalacionesCalefaccion();
		instalacionesCalefaccion.setInstalacionesCalefaccion(suministros.getInstalacionesCalefaccion());
		if ("N".equals(suministros.getInstalacionesCalefaccion())) {
			instalacionesCalefaccion.setPotenciaCalefaccion(BigDecimal.ZERO);
			instalacionesCalefaccion.setPotenciaCalorificaInstGen(BigDecimal.ZERO);
			instalacionesCalefaccion.setPotenciaCalorificaEqCentr(BigDecimal.ZERO);
		} else {
			instalacionesCalefaccion.setPotenciaCalefaccion(suministros.getPotenciaCalefaccion());
			instalacionesCalefaccion.setPotenciaCalorificaInstGen(suministros.getPotenciaCalorInstGen());
			instalacionesCalefaccion.setPotenciaCalorificaEqCentr(suministros.getPotenciaCalorEqCentr());
			instalacionesCalefaccion.setInstalacionesGeneralEdificio(suministros.getInstalacionesGeneralEdificio());
			instalacionesCalefaccion.setEquipoCentralizadoLocal(suministros.getEquipoCentralizadoLocal());
		}
		instalacionesCalefaccion.setTipoCombustible(suministros.getTipoCombustibleCalefcc());

		return instalacionesCalefaccion;
	}

	private AguaCalienteSanitaria rellenarAguaCalienteSanitaria(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		AguaCalienteSanitaria aguaCalienteSanitaria = objFactory.createAguaCalienteSanitaria();
		aguaCalienteSanitaria.setAguaCalienteSanitaria(suministros.getAguaCalienteSanitaria());
		if ("N".equals(suministros.getAguaCalienteSanitaria())) {
			aguaCalienteSanitaria.setPotenciaAguaCaliente(BigDecimal.ZERO);
		} else {
			aguaCalienteSanitaria.setPotenciaAguaCaliente(suministros.getPotenciaAguaCaliente());
		}
		aguaCalienteSanitaria.setTipoCombustible(suministros.getTipoCombustibleAguaC());
		aguaCalienteSanitaria.setAcumuladorCalor(suministros.getAcumuladorCalorAguaC());
		aguaCalienteSanitaria.setPotenciaAcumulador(suministros.getPotenciaAcumuladorAguaC());
		return aguaCalienteSanitaria;
	}

	private VentilacionForzada rellenarVentilacionForzada(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		VentilacionForzada ventilacionForzada = objFactory.createVentilacionForzada();
		ventilacionForzada.setVentilacionForzada(suministros.getVentilacionForzada());
		if ("N".equals(suministros.getVentilacionForzada())) {
			ventilacionForzada.setCaudal(BigDecimal.ZERO);
		} else {
			ventilacionForzada.setCaudal(suministros.getCaudal());
		}
		ventilacionForzada.setEvacuacionFachada(suministros.getEvacuacionFachada());
		ventilacionForzada.setEvacuacionCubierta(suministros.getEvacuacionCubierta());
		return ventilacionForzada;
	}

	private EquiposInstRelevantesOrdinario rellenarEquiposInstRelevantesOrdinario(ObjectFactory objFactory, LcDatosSuministrosVO suministros) {
		if (StringUtils.isNotBlank(suministros.getHayInstalacionesRelevantes())) {
			EquiposInstRelevantesOrdinario equiposInstRelevantesOrdinario = objFactory.createEquiposInstRelevantesOrdinario();
			equiposInstRelevantesOrdinario.setPotenciaTotal(suministros.getPotenciaTotal());
			equiposInstRelevantesOrdinario.setPotenciaCentroTransformador(suministros.getPotenciaCentroTransformador());
			equiposInstRelevantesOrdinario.setHayInstalacionesRelevantes(suministros.getHayInstalacionesRelevantes());
			equiposInstRelevantesOrdinario.setEquiposInstRadioactivas(suministros.getEquiposInstRadioactivas());
			equiposInstRelevantesOrdinario.setEquiposRayosUva(suministros.getEquiposRayosUva());
			equiposInstRelevantesOrdinario.setEquiposRayosLaser(suministros.getEquiposRayosLaser());
			equiposInstRelevantesOrdinario.setPotenciaEquiposRayosLaser(suministros.getPotenciaEquiposRayosLaser());
			equiposInstRelevantesOrdinario.setEquiposAudiovisuales(suministros.getEquiposAudiovisuales());
			return equiposInstRelevantesOrdinario;
		}
		return null;
	}

	private ParametrosEdificacionAltaLic rellenarParametrosEdificacionAltaLic(ObjectFactory objFactory, List<LcEdificacionVO> edificaciones) {
		if (edificaciones != null && !edificaciones.isEmpty()) {
			for (LcEdificacionVO edificacion : edificaciones) {
				if (TipoEdificacionEnum.Edificacion_Alta.getValorEnum().equals(edificacion.getTipoEdificacion())) {
					ParametrosEdificacionAltaLic parametrosEdificacionAltaLic = objFactory.createParametrosEdificacionAltaLic();
					parametrosEdificacionAltaLic.setDatosEdificacion(rellenarDatosEdificacionAlta(objFactory, edificacion));
					parametrosEdificacionAltaLic.setDatosAparcamiento(rellenarDatosAparcamientoAlta(objFactory, edificacion));
					parametrosEdificacionAltaLic.setProyectoGarajeAparcamiento(rellenarProyectoGarajeAparcamientoAlta(objFactory, edificacion));

					// Lista Info Edificios Alta
					if (edificacion.getLcInfoEdificiosAlta() != null && !edificacion.getLcInfoEdificiosAlta().isEmpty()) {
						for (LcInfoEdificioAltaVO infoEdificioAlta : edificacion.getLcInfoEdificiosAlta()) {
							parametrosEdificacionAltaLic.getInfoEdificio().add(rellenarInfoEdificioAltaLic(objFactory, infoEdificioAlta));
						}
					}

					parametrosEdificacionAltaLic.setResumenEdificacion(rellenarResumenEdificacion(objFactory, edificacion.getLcResumenEdificacionAsList()));
					return parametrosEdificacionAltaLic;
				}
			}
		}
		return null;
	}

	private ParametrosEdificacionBajaLic rellenarParametrosEdificacionBajaLic(ObjectFactory objFactory, List<LcEdificacionVO> edificaciones) {
		if (edificaciones != null && !edificaciones.isEmpty()) {
			for (LcEdificacionVO edificacion : edificaciones) {
				if (TipoEdificacionEnum.Edificacion_Baja.getValorEnum().equals(edificacion.getTipoEdificacion())) {
					ParametrosEdificacionBajaLic parametrosEdificacionBajaLic = objFactory.createParametrosEdificacionBajaLic();
					parametrosEdificacionBajaLic.setTipoDemolicion(edificacion.getTipoDemolicion());
					parametrosEdificacionBajaLic.setIndustrial(edificacion.getIndustrial());

					if (edificacion.getLcInfoEdificiosBaja() != null && !edificacion.getLcInfoEdificiosBaja().isEmpty()) {
						parametrosEdificacionBajaLic.setNumEdificios(BigInteger.valueOf(edificacion.getLcInfoEdificiosBaja().size()));

						// Lista de Info edificios
						for (LcInfoEdificioBajaVO infoEdificio : edificacion.getLcInfoEdificiosBaja()) {
							parametrosEdificacionBajaLic.getInfoEdificio().add(rellenarInfoEdificioBajaLic(objFactory, infoEdificio));
						}
					}
					return parametrosEdificacionBajaLic;
				}
			}
		}
		return null;
	}

	private ResumenEdificacion rellenarResumenEdificacion(ObjectFactory objFactory, List<LcResumenEdificacionVO> resumen) {
		if (resumen != null && !resumen.isEmpty()) {
			ResumenEdificacion resumenEdificacion = objFactory.createResumenEdificacion();
			for (LcResumenEdificacionVO tipoResumen : resumen) {
				ResumenEdificacionTipo resumenEdificacionTipo = rellenarResumenEdificacionTipo(objFactory, tipoResumen);
				if (TipoResumenEdificacionEnum.Vivienda.getValorEnum().equals(tipoResumen.getTipoResumenEdificacion())) {
					resumenEdificacion.setViviendas(resumenEdificacionTipo);
				} else if (TipoResumenEdificacionEnum.Local.getValorEnum().equals(tipoResumen.getTipoResumenEdificacion())) {
					resumenEdificacion.setLocales(resumenEdificacionTipo);
				} else if (TipoResumenEdificacionEnum.Garaje.getValorEnum().equals(tipoResumen.getTipoResumenEdificacion())) {
					resumenEdificacion.setGarajes(resumenEdificacionTipo);
				} else if (TipoResumenEdificacionEnum.Trastero.getValorEnum().equals(tipoResumen.getTipoResumenEdificacion())) {
					resumenEdificacion.setTrasteros(resumenEdificacionTipo);
				}
			}

			rellenarConCeros(objFactory, resumenEdificacion);

			resumenEdificacion.setTotal(rellenarResumenEdificacionTipoTotal(objFactory, resumenEdificacion));
			return resumenEdificacion;
		}
		return null;
	}

	private ResumenEdificacion rellenarConCeros(ObjectFactory objFactory, ResumenEdificacion resumenEdificacion) {
		if (resumenEdificacion.getViviendas() == null) {
			resumenEdificacion.setViviendas(objFactory.createResumenEdificacionTipo());
			resumenEdificacion.getViviendas().setBajoRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
			resumenEdificacion.getViviendas().setSobreRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
		}
		if (resumenEdificacion.getLocales() != null) {
			resumenEdificacion.setLocales(objFactory.createResumenEdificacionTipo());
			resumenEdificacion.getLocales().setBajoRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
			resumenEdificacion.getLocales().setSobreRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
		}
		if (resumenEdificacion.getGarajes() != null) {
			resumenEdificacion.setGarajes(objFactory.createResumenEdificacionTipo());
			resumenEdificacion.getGarajes().setBajoRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
			resumenEdificacion.getGarajes().setSobreRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
		}
		if (resumenEdificacion.getTrasteros() != null) {
			resumenEdificacion.setTrasteros(objFactory.createResumenEdificacionTipo());
			resumenEdificacion.getTrasteros().setBajoRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
			resumenEdificacion.getTrasteros().setSobreRasante(rellenarResumenEdificacionValor(objFactory, null, null, null));
		}
		return resumenEdificacion;
	}

	private ResumenEdificacionTipo rellenarResumenEdificacionTipoTotal(ObjectFactory objFactory, ResumenEdificacion resumenEdificacion) {
		ResumenEdificacionTipo resumenEdificacionTipo = objFactory.createResumenEdificacionTipo();

		long numUnidadesBajoRasante = resumenEdificacion.getViviendas().getBajoRasante().getNumUnidades().longValue() + resumenEdificacion.getLocales().getBajoRasante().getNumUnidades().longValue()
				+ resumenEdificacion.getGarajes().getBajoRasante().getNumUnidades().longValue() + resumenEdificacion.getTrasteros().getBajoRasante().getNumUnidades().longValue();
		long numUnidadesSobreRasante = resumenEdificacion.getViviendas().getSobreRasante().getNumUnidades().longValue() + resumenEdificacion.getLocales().getSobreRasante().getNumUnidades().longValue()
				+ resumenEdificacion.getGarajes().getSobreRasante().getNumUnidades().longValue() + resumenEdificacion.getTrasteros().getSobreRasante().getNumUnidades().longValue();
		long supConstruidaBajoRasante = resumenEdificacion.getViviendas().getBajoRasante().getSupConstruida().longValue() + resumenEdificacion.getLocales().getBajoRasante().getSupConstruida()
				.longValue() + resumenEdificacion.getGarajes().getBajoRasante().getSupConstruida().longValue() + resumenEdificacion.getTrasteros().getBajoRasante().getSupConstruida().longValue();
		long supConstruidaSobreRasante = resumenEdificacion.getViviendas().getSobreRasante().getSupConstruida().longValue() + resumenEdificacion.getLocales().getSobreRasante().getSupConstruida()
				.longValue() + resumenEdificacion.getGarajes().getSobreRasante().getSupConstruida().longValue() + resumenEdificacion.getTrasteros().getSobreRasante().getSupConstruida().longValue();
		long supComputableBajoRasante = resumenEdificacion.getViviendas().getBajoRasante().getSupComputable().longValue() + resumenEdificacion.getLocales().getBajoRasante().getSupComputable()
				.longValue() + resumenEdificacion.getGarajes().getBajoRasante().getSupComputable().longValue() + resumenEdificacion.getTrasteros().getBajoRasante().getSupComputable().longValue();
		long supComputableSobreRasante = resumenEdificacion.getViviendas().getSobreRasante().getNumUnidades().longValue() + resumenEdificacion.getLocales().getSobreRasante().getSupComputable()
				.longValue() + resumenEdificacion.getGarajes().getSobreRasante().getSupComputable().longValue() + resumenEdificacion.getTrasteros().getSobreRasante().getSupComputable().longValue();

		resumenEdificacionTipo.setBajoRasante(rellenarResumenEdificacionValor(objFactory, BigDecimal.valueOf(numUnidadesBajoRasante), BigDecimal.valueOf(supConstruidaBajoRasante), BigDecimal.valueOf(
				supComputableBajoRasante)));
		resumenEdificacionTipo.setSobreRasante(rellenarResumenEdificacionValor(objFactory, BigDecimal.valueOf(numUnidadesSobreRasante), BigDecimal.valueOf(supConstruidaSobreRasante), BigDecimal
				.valueOf(supComputableSobreRasante)));
		resumenEdificacionTipo.setTotal(rellenarResumenEdificacionValorTotal(objFactory, resumenEdificacionTipo));
		return resumenEdificacionTipo;
	}

	private ResumenEdificacionTipo rellenarResumenEdificacionTipo(ObjectFactory objFactory, LcResumenEdificacionVO resumenTipo) {
		ResumenEdificacionTipo resumenEdificacionTipo = objFactory.createResumenEdificacionTipo();
		resumenEdificacionTipo.setBajoRasante(rellenarResumenEdificacionValor(objFactory, resumenTipo.getNumUnidadesBajoRasante(), resumenTipo.getSupConstruidaBajoRasante(), resumenTipo
				.getSupComputableBajoRasante()));
		resumenEdificacionTipo.setSobreRasante(rellenarResumenEdificacionValor(objFactory, resumenTipo.getNumUnidadesSobreRasante(), resumenTipo.getSupConstruidaSobreRasante(), resumenTipo
				.getSupComputableSobreRasante()));
		resumenEdificacionTipo.setTotal(rellenarResumenEdificacionValorTotal(objFactory, resumenEdificacionTipo));
		return resumenEdificacionTipo;
	}

	private ResumenEdificacionValor rellenarResumenEdificacionValorTotal(ObjectFactory objFactory, ResumenEdificacionTipo resumenEdificacionTipo) {
		ResumenEdificacionValor resumenEdificacionValor = objFactory.createResumenEdificacionValor();

		long totalUnidades = resumenEdificacionTipo.getBajoRasante().getNumUnidades().longValue() + resumenEdificacionTipo.getSobreRasante().getNumUnidades().longValue();
		long totalSupConstruida = resumenEdificacionTipo.getBajoRasante().getSupConstruida().longValue() + resumenEdificacionTipo.getSobreRasante().getSupConstruida().longValue();
		long totalSupComputable = resumenEdificacionTipo.getBajoRasante().getSupComputable().longValue() + resumenEdificacionTipo.getSobreRasante().getSupComputable().longValue();

		resumenEdificacionValor.setNumUnidades(BigInteger.valueOf(totalUnidades));
		resumenEdificacionValor.setSupConstruida(BigDecimal.valueOf(totalSupConstruida));
		resumenEdificacionValor.setSupComputable(BigDecimal.valueOf(totalSupComputable));

		return resumenEdificacionValor;
	}

	private ResumenEdificacionValor rellenarResumenEdificacionValor(ObjectFactory objFactory, BigDecimal numUnidades, BigDecimal supConstruida, BigDecimal supComputable) {
		ResumenEdificacionValor resumenEdificacionValor = objFactory.createResumenEdificacionValor();

		if (numUnidades != null) {
			resumenEdificacionValor.setNumUnidades(numUnidades.toBigInteger());
		} else {
			resumenEdificacionValor.setNumUnidades(BigInteger.ZERO);
		}

		if (supConstruida != null) {
			resumenEdificacionValor.setSupConstruida(supConstruida);
		} else {
			resumenEdificacionValor.setSupConstruida(BigDecimal.ZERO);
		}

		if (supComputable != null) {
			resumenEdificacionValor.setSupComputable(supComputable);

		} else {
			resumenEdificacionValor.setSupComputable(BigDecimal.ZERO);
		}

		return resumenEdificacionValor;
	}

	private InfoEdificioAltaLic rellenarInfoEdificioAltaLic(ObjectFactory objFactory, LcInfoEdificioAltaVO infoEdificioAlta) {
		InfoEdificioAltaLic infoEdificioAltaLic = objFactory.createInfoEdificioAltaLic();
		infoEdificioAltaLic.setDireccionEdificio(rellenarDireccionLic(objFactory, infoEdificioAlta.getLcDirEdificacionAlta()));

		if (infoEdificioAlta.getLcDatosPortalesAlta() != null && !infoEdificioAlta.getLcDatosPortalesAlta().isEmpty()) {
			infoEdificioAltaLic.setNumPortales(BigInteger.valueOf(infoEdificioAlta.getLcDatosPortalesAlta().size()));

			// Lista de Portales Alta
			for (LcDatosPortalAltaVO portalAlta : infoEdificioAlta.getLcDatosPortalesAlta()) {
				infoEdificioAltaLic.getPortal().add(rellenarDatosPortalAlta(objFactory, portalAlta));
			}
		}

		return infoEdificioAltaLic;
	}

	private InfoEdificioBajaLic rellenarInfoEdificioBajaLic(ObjectFactory objFactory, LcInfoEdificioBajaVO infoEdificio) {
		InfoEdificioBajaLic infoEdificioBajaLic = objFactory.createInfoEdificioBajaLic();
		if (infoEdificio.getNumEdificio() != null) {
			infoEdificioBajaLic.setNumEdificio(infoEdificio.getNumEdificio().toBigInteger());
		}
		infoEdificioBajaLic.setDireccionEdificio(rellenarDireccionLic(objFactory, infoEdificio.getLcDirEdificacion()));

		if (infoEdificio.getLcDatosPlantasBaja() != null && !infoEdificio.getLcDatosPlantasBaja().isEmpty()) {
			infoEdificioBajaLic.setNumPlantas(BigInteger.valueOf(infoEdificio.getLcDatosPlantasBaja().size()));

			// Lista de Plantas Baja
			for (LcDatosPlantaBajaVO plantaBaja : infoEdificio.getLcDatosPlantasBaja()) {
				infoEdificioBajaLic.getDatosPlantas().add(rellenarDatosPlantasBaja(objFactory, plantaBaja));
			}
		}

		return infoEdificioBajaLic;
	}

	private DatosPlantasBaja rellenarDatosPlantasBaja(ObjectFactory objFactory, LcDatosPlantaBajaVO plantaBaja) {
		DatosPlantasBaja datosPlantasBaja = objFactory.createDatosPlantasBaja();
		datosPlantasBaja.setTipoPlanta(plantaBaja.getTipoPlanta());
		datosPlantasBaja.setSupConstruida(plantaBaja.getSupConstruida());
		return datosPlantasBaja;
	}

	private DatosPortalAlta rellenarDatosPortalAlta(ObjectFactory objFactory, LcDatosPortalAltaVO portalAlta) {
		DatosPortalAlta datosPortalAlta = objFactory.createDatosPortalAlta();
		datosPortalAlta.setNombrePortal(portalAlta.getNombrePortal());
		if (portalAlta.getUnidadesBjRasante() != null && portalAlta.getSuperficieConstruidaBjRasante() != null && portalAlta.getSuperficieComputableBjRasante() != null) {
			datosPortalAlta.setBajoRasante(rellenarDatosSuperficiePortal(objFactory, portalAlta.getUnidadesBjRasante(), portalAlta.getSuperficieConstruidaBjRasante(), portalAlta
					.getSuperficieComputableBjRasante()));
		}
		datosPortalAlta.setSobreRasante(rellenarDatosSuperficiePortal(objFactory, portalAlta.getUnidadesSbRasante(), portalAlta.getSuperficieConstruidaSbRasante(), portalAlta
				.getSuperficieComputableSbRasante()));

		// Lista Plantas Alta
		if (portalAlta.getLcDatosPlantasAlta() != null && !portalAlta.getLcDatosPlantasAlta().isEmpty()) {
			for (LcDatosPlantaAltaVO plantaAlta : portalAlta.getLcDatosPlantasAlta()) {
				datosPortalAlta.getPlanta().add(rellenarDatosPlantasAltaLic(objFactory, plantaAlta));
			}
		}

		return datosPortalAlta;
	}

	private DatosPlantasAltaLic rellenarDatosPlantasAltaLic(ObjectFactory objFactory, LcDatosPlantaAltaVO plantaAlta) {
		DatosPlantasAltaLic datosPlantasAltaLic = objFactory.createDatosPlantasAltaLic();
		datosPlantasAltaLic.setTipoPlanta(plantaAlta.getTipoPlanta());
		datosPlantasAltaLic.setNumPlanta(plantaAlta.getNumPlanta());
		datosPlantasAltaLic.setAlturaLibre(plantaAlta.getAlturaLibre());
		datosPlantasAltaLic.setAlturaPiso(plantaAlta.getAlturaPiso());
		datosPlantasAltaLic.setUsoPlanta(plantaAlta.getUsoPlanta());
		if (plantaAlta.getNumUnidades() != null) {
			datosPlantasAltaLic.setNumUnidades(plantaAlta.getNumUnidades().toBigInteger());
		}
		datosPlantasAltaLic.setSupConstruida(plantaAlta.getSupConstruida());
		datosPlantasAltaLic.setSupComputable(plantaAlta.getSupComputable());

		// Lista Superficies no computables
		if (plantaAlta.getLcSupNoComputablesPlanta() != null && !plantaAlta.getLcSupNoComputablesPlanta().isEmpty()) {
			for (LcSupNoComputablePlantaVO supNoComputable : plantaAlta.getLcSupNoComputablesPlanta()) {
				datosPlantasAltaLic.getSuperficieNoComputable().add(rellenarSuperficieNoComputablePlanta(objFactory, supNoComputable));
			}
		}

		return datosPlantasAltaLic;
	}

	private SuperficieNoComputablePlanta rellenarSuperficieNoComputablePlanta(ObjectFactory objFactory, LcSupNoComputablePlantaVO supNoComputable) {
		SuperficieNoComputablePlanta superficieNoComputablePlanta = objFactory.createSuperficieNoComputablePlanta();
		if (supNoComputable.getTipo() != null) {
			superficieNoComputablePlanta.setTipo(supNoComputable.getTipo().toBigInteger());
		}
		superficieNoComputablePlanta.setTamano(supNoComputable.getTamanio());
		return superficieNoComputablePlanta;
	}

	private DatosSuperficiePortal rellenarDatosSuperficiePortal(ObjectFactory objFactory, BigDecimal unidades, BigDecimal superConstruida, BigDecimal superComputable) {
		DatosSuperficiePortal datosSuperficiePortal = objFactory.createDatosSuperficiePortal();
		if (unidades != null) {
			datosSuperficiePortal.setUnidades(unidades.toBigInteger());
		}
		datosSuperficiePortal.setSuperficieConstruida(superConstruida);
		datosSuperficiePortal.setSuperficieComputable(superComputable);
		return datosSuperficiePortal;
	}

	private ProyectoGarajeAparcamientoAlta rellenarProyectoGarajeAparcamientoAlta(ObjectFactory objFactory, LcEdificacionVO edificacion) {
		ProyectoGarajeAparcamientoAlta proyectoGarajeAparcamientoAlta = objFactory.createProyectoGarajeAparcamientoAlta();
		proyectoGarajeAparcamientoAlta.setSuperaSeismil(edificacion.getSuperaSeisMil());
		proyectoGarajeAparcamientoAlta.setSuperaDocemil(edificacion.getSuperaDoceMil());
		proyectoGarajeAparcamientoAlta.setExpPlanEspecialUrbanistico(edificacion.getExpPlanEspecialUrbanistico());
		return proyectoGarajeAparcamientoAlta;
	}

	private DatosEdificacionAlta rellenarDatosEdificacionAlta(ObjectFactory objFactory, LcEdificacionVO edificacion) {
		DatosEdificacionAlta datosEdificacionAlta = objFactory.createDatosEdificacionAlta();
		if (edificacion.getNumEdificios() != null) {
			datosEdificacionAlta.setNumEdificios(edificacion.getNumEdificios().toBigInteger());
		}
		datosEdificacionAlta.setDescripcion(edificacion.getDescripcion());
		datosEdificacionAlta.setTipologia(edificacion.getTipologia());
		return datosEdificacionAlta;
	}

	private DatosAparcamientoAlta rellenarDatosAparcamientoAlta(ObjectFactory objFactory, LcEdificacionVO edificacion) {
		if (edificacion.getDotacionalEnEdificio() != null || edificacion.getLibreDisposicionEnEdificio() != null || edificacion.getDotacionalEnSuperficie() != null || edificacion
				.getLibreDisposicionEnSuperficie() != null || edificacion.getDotacionalDispacitados() != null || edificacion.getLibreDisposicionDiscapacitados() != null) {
			DatosAparcamientoAlta datosAparcamientoAlta = objFactory.createDatosAparcamientoAlta();

			if (edificacion.getDotacionalEnEdificio() != null || edificacion.getLibreDisposicionEnEdificio() != null) {
				datosAparcamientoAlta.setEnEdificio(rellenarTipoAparcamiento(objFactory, edificacion.getDotacionalEnEdificio(), edificacion.getLibreDisposicionEnEdificio()));
			}
			if (edificacion.getDotacionalEnSuperficie() != null || edificacion.getLibreDisposicionEnSuperficie() != null) {
				datosAparcamientoAlta.setEnSuperficie(rellenarTipoAparcamiento(objFactory, edificacion.getDotacionalEnSuperficie(), edificacion.getLibreDisposicionEnSuperficie()));
			}
			if (edificacion.getDotacionalDispacitados() != null || edificacion.getLibreDisposicionDiscapacitados() != null) {
				datosAparcamientoAlta.setDiscapacitados(rellenarTipoAparcamiento(objFactory, edificacion.getDotacionalDispacitados(), edificacion.getLibreDisposicionDiscapacitados()));
			}
			return datosAparcamientoAlta;
		}
		return null;

	}

	private TiposAparcamiento rellenarTipoAparcamiento(ObjectFactory objFactory, BigDecimal dotacional, BigDecimal libreDisposicion) {
		TiposAparcamiento tiposAparcamiento = objFactory.createTiposAparcamiento();
		long total = 0;
		if (dotacional != null) {
			total = total + dotacional.intValue();
			tiposAparcamiento.setDotacional(dotacional.toBigInteger());
		} else {
			tiposAparcamiento.setDotacional(BigInteger.ZERO);
		}

		if (libreDisposicion != null) {
			total = total + libreDisposicion.intValue();
			tiposAparcamiento.setLibreDisposicion(libreDisposicion.toBigInteger());
		} else {
			tiposAparcamiento.setLibreDisposicion(BigInteger.ZERO);
		}

		tiposAparcamiento.setTotal(BigInteger.valueOf(total));
		return tiposAparcamiento;
	}

	private OtrosDatosActuacionLic rellenarOtrosDatosActuacionLic(ObjectFactory objFactory, LcActuacionVO actuacion) {
		OtrosDatosActuacionLic otrosDatosActuacionLic = objFactory.createOtrosDatosActuacionLic();
		otrosDatosActuacionLic.setNormaZonalFiguraOrdenacion(actuacion.getNormaZonalFiguraOrdenacion());
		otrosDatosActuacionLic.setNivelProteccion(actuacion.getNivelProteccion());
		otrosDatosActuacionLic.setDotacionalTransporte(actuacion.getDotacionalTransporte());
		otrosDatosActuacionLic.setGestionadoAdminPublica(actuacion.getGestionadoAdminPublica());
		return otrosDatosActuacionLic;
	}

	private DatosObraLIC rellenarDatosObrasLic(ObjectFactory objFactory, LcObraVO obra) {
		DatosObraLIC datosObraLIC = objFactory.createDatosObraLIC();

		// Lista de tipos obra
		if (StringUtils.isNotBlank(obra.getTipoObra())) {
			String[] tipos = obra.getTipoObra().split(";");
			for (String tipo : tipos) {
				datosObraLIC.getTipoObra().add(tipo);
			}
		}

		datosObraLIC.setDescripcionObra(obra.getDescripcionObra());
		datosObraLIC.setPresupuestoTotal(obra.getPresupuestoTotal());

		if (obra.getPlazoInicio() != null) {
			datosObraLIC.setPlazoInicio(obra.getPlazoInicio().toBigInteger());
		}
		if (obra.getPlazoEjecucion() != null) {
			datosObraLIC.setPlazoEjecucion(obra.getPlazoEjecucion().toBigInteger());
		}
		datosObraLIC.setSuperficieAfectada(obra.getSuperficieAfectada());
		datosObraLIC.setOcupacionViaPublica(obra.getOcupacionViaPublica());
		datosObraLIC.setAndamios(obra.getAndamios());
		datosObraLIC.setVallas(obra.getVallas());
		datosObraLIC.setInstalacionGrua(obra.getInstalacionGrua());
		datosObraLIC.setContenedorViaPublica(obra.getContenedorViaPublica());
		if (obra.getDuracionAndamioMeses() != null) {
			datosObraLIC.setDuracionAndamiosMeses(obra.getDuracionAndamioMeses().toBigInteger());
		}
		if (obra.getDuracionVallaMeses() != null) {
			datosObraLIC.setDuracionVallasMeses(obra.getDuracionVallaMeses().toBigInteger());
		}
		if (obra.getDuracionGruaMeses() != null) {
			datosObraLIC.setDuracionGruaMeses(obra.getDuracionGruaMeses().toBigInteger());
		}
		if (obra.getDuracionContenedorMeses() != null) {
			datosObraLIC.setDuracionContenedorMeses(obra.getDuracionContenedorMeses().toBigInteger());
		}
		// Lista de partes autónomas
		if (obra.getPartesAutonomas() != null && !obra.getPartesAutonomas().isEmpty()) {
			for (LcParteAutonomaVO parteAutonoma : obra.getPartesAutonomas()) {
				datosObraLIC.getParte().add(rellenarParteAutonoma(objFactory, parteAutonoma));
			}
		}

		return datosObraLIC;
	}

	private ParteAutonoma rellenarParteAutonoma(ObjectFactory objFactory, LcParteAutonomaVO parte) {
		ParteAutonoma parteAutonoma = objFactory.createParteAutonoma();
		if (parte.getNumero() != null) {
			parteAutonoma.setNumero(parte.getNumero().toBigInteger());
		}
		parteAutonoma.setDescripcion(parte.getDescripcion());
		return parteAutonoma;
	}

	private SolicitudConjuntaAutorizaciones rellenarSolicitudConjuntaAutorizaciones(ObjectFactory objFactory, LcTramiteVO tramite) {
		SolicitudConjuntaAutorizaciones solicitudConjuntaAutorizaciones = objFactory.createSolicitudConjuntaAutorizaciones();
		solicitudConjuntaAutorizaciones.setSolicitaOtraAutorizacion(tramite.getSolicitaOtraAutorizacion());
		solicitudConjuntaAutorizaciones.setOcupacionViario(tramite.getOcupacionViario());
		solicitudConjuntaAutorizaciones.setOtra(tramite.getOtra());
		return solicitudConjuntaAutorizaciones;
	}

	private DatosActuacionSlim rellenarActuacionSlim(ObjectFactory objFactory, LcActuacionVO actuacion) {
		DatosActuacionSlim datosActuacionSlim = objFactory.createDatosActuacionSlim();
		datosActuacionSlim.setNumExpedienteCedula(actuacion.getNumExpedienteCedula());
		datosActuacionSlim.setNumExpedienteParcelacion(actuacion.getNumExpedienteParcelacion());
		datosActuacionSlim.setNumExpedienteModificar(actuacion.getNumExpedienteModificar());
		datosActuacionSlim.setSolicitaAyudaRehabilitacion(actuacion.getSolicitudAyudaRehabilitacion());
		datosActuacionSlim.setNumExpedienteConsulta(actuacion.getNumExpedienteConsulta());
		datosActuacionSlim.setNumExpedienteLicencia(actuacion.getNumExpedienteLicencia());
		datosActuacionSlim.setNumCcLicencia(actuacion.getNumCcLicencia());
		return datosActuacionSlim;
	}

	private EmplazamientoLic rellenarEmplazamientoLic(ObjectFactory objFactory, LcDireccionVO dirEmplazamiento) {
		EmplazamientoLic emplazamientoLic = objFactory.createEmplazamientoLic();

		if (StringUtils.isNotBlank(dirEmplazamiento.getTipoVia()) && StringUtils.isNotBlank(dirEmplazamiento.getNombreVia()) && StringUtils.isNotBlank(dirEmplazamiento.getTipoNumeracion())
				&& dirEmplazamiento.getNumCalle() != null) {
			emplazamientoLic.setDireccionEmplazamientoPrincipal(rellenarDireccionLic(objFactory, dirEmplazamiento));
		}

		if (StringUtils.isNotBlank(dirEmplazamiento.getEmplNoNormalizado())) {
			emplazamientoLic.setEmplazamientoNoNormalizado(rellenarDireccionNoNormalizada(objFactory, dirEmplazamiento));
		}

		return emplazamientoLic;
	}

	private DireccionNoNormalizada rellenarDireccionNoNormalizada(ObjectFactory objFactory, LcDireccionVO direccion) {
		DireccionNoNormalizada direccionNoNormalizada = objFactory.createDireccionNoNormalizada();
		direccionNoNormalizada.setEmplazamientoNoNormalizado(direccion.getEmplNoNormalizado());
		direccionNoNormalizada.setAmbito(direccion.getAmbito());
		direccionNoNormalizada.setDistrito(direccion.getDistrito());
		direccionNoNormalizada.setReferenciaCatastral(direccion.getReferenciaCatastral());
		direccionNoNormalizada.setCoordenadaX(direccion.getCoordenadaX());
		direccionNoNormalizada.setCoordenadaY(direccion.getCoordenadaY());
		return direccionNoNormalizada;
	}

	private DireccionLic rellenarDireccionLic(ObjectFactory objFactory, LcDireccionVO direccion) {
		DireccionLic direccionLic = objFactory.createDireccionLic();
		direccionLic.setClaseVial(direccion.getTipoVia());
		direccionLic.setNombreVial(direccion.getNombreVia());
		direccionLic.setTipoNumeracion(direccion.getTipoNumeracion());
		if (direccion.getNumCalle() != null) {
			direccionLic.setNumCalle(direccion.getNumCalle().toBigInteger());
		}
		if (direccion.getCodigoPostal() != null) {
			direccionLic.setCodPostal(new BigInteger(direccion.getCodigoPostal()));
		}
		direccionLic.setClaseDireccion(direccion.getClaseDireccion());
		if (direccion.getCodDireccion() != null) {
			direccionLic.setCodDireccion(direccion.getCodDireccion().toBigInteger());
		}
		direccionLic.setCalificador(direccion.getCalificador());
		direccionLic.setPlanta(direccion.getPlanta());
		direccionLic.setEscalera(direccion.getEscalera());
		direccionLic.setPuerta(direccion.getPuerta());
		return direccionLic;
	}

	private Notificacion rellenarNotificacion(ObjectFactory objFactory, LcIntervinienteVO intervinienteNotificacion) {
		Notificacion notificacion = objFactory.createNotificacion();
		notificacion.setTipoSujeto(intervinienteNotificacion.getLcPersona().getTipoSujeto());
		if ("F".equalsIgnoreCase(intervinienteNotificacion.getLcPersona().getTipoSujeto())) {
			notificacion.setApellido1(intervinienteNotificacion.getLcPersona().getApellido1RazonSocial());
			notificacion.setApellido2(intervinienteNotificacion.getLcPersona().getApellido2());
			notificacion.setNombre(intervinienteNotificacion.getLcPersona().getNombre());
		} else {
			notificacion.setRazonSocial(intervinienteNotificacion.getLcPersona().getApellido1RazonSocial());
		}
		notificacion.setTipoDocumento(intervinienteNotificacion.getLcPersona().getTipoDocumento());
		notificacion.setNumDocumento(intervinienteNotificacion.getLcPersona().getNif());
		notificacion.setNumTelefono1(intervinienteNotificacion.getLcPersona().getNumTelefono1());
		notificacion.setNumTelefono2(intervinienteNotificacion.getLcPersona().getNumTelefono2());
		notificacion.setNumFax(intervinienteNotificacion.getLcPersona().getNumFax());
		notificacion.setCorreoElectronico(intervinienteNotificacion.getLcPersona().getCorreoElectronico());
		notificacion.setDireccion(rellenarDireccionCompleta(objFactory, intervinienteNotificacion.getLcDireccion()));
		return notificacion;
	}

	private DatosComunicacionSolicitud rellenarDatosComunicacionSolicitud(ObjectFactory objFactory, LcTramiteVO tramite) {
		DatosComunicacionSolicitud datosComunicacionSolicitud = objFactory.createDatosComunicacionSolicitud();
		datosComunicacionSolicitud.setTelefono(tramite.getTelefono());
		datosComunicacionSolicitud.setCorreoElectronico(tramite.getCorreoElectronico());
		return datosComunicacionSolicitud;
	}

	private Persona rellenarPersona(ObjectFactory objFactory, LcIntervinienteVO interviniente) {
		Persona persona = objFactory.createPersona();
		persona.setTipoSujeto(interviniente.getLcPersona().getTipoSujeto());

		if ("F".equalsIgnoreCase(interviniente.getLcPersona().getTipoSujeto())) {
			persona.setApellido1(interviniente.getLcPersona().getApellido1RazonSocial());
			persona.setApellido2(interviniente.getLcPersona().getApellido2());
			persona.setNombre(interviniente.getLcPersona().getNombre());
		} else {
			persona.setRazonSocial(interviniente.getLcPersona().getApellido1RazonSocial());
		}

		persona.setTipoDocumento(interviniente.getLcPersona().getTipoDocumento());
		persona.setNumDocumento(interviniente.getLcPersona().getNif());
		persona.setNumTelefono1(interviniente.getLcPersona().getNumTelefono1());
		persona.setNumTelefono2(interviniente.getLcPersona().getNumTelefono2());
		persona.setNumFax(interviniente.getLcPersona().getNumFax());
		persona.setCorreoElectronico(interviniente.getLcPersona().getCorreoElectronico());
		persona.setDireccion(rellenarDireccionCompleta(objFactory, interviniente.getLcDireccion()));
		return persona;
	}

	private DireccionCompleta rellenarDireccionCompleta(ObjectFactory objFactory, LcDireccionVO direccion) {
		DireccionCompleta direccionCompleta = objFactory.createDireccionCompleta();
		if (StringUtils.isNotBlank(direccion.getCodLocal())) {
			direccionCompleta.setCodLocal(new BigInteger(direccion.getCodLocal()));
		}
		direccionCompleta.setEscalera(direccion.getEscalera());
		direccionCompleta.setPlanta(direccion.getPlanta());
		direccionCompleta.setPuerta(direccion.getPuerta());
		direccionCompleta.setLocal(direccion.getLocal());
		if (StringUtils.isNotBlank(direccion.getCodigoPostal())) {
			direccionCompleta.setCodigoPostal(new BigInteger(direccion.getCodigoPostal()));
		}
		direccionCompleta.setPoblacion(direccion.getPoblacion());
		direccionCompleta.setMunicipio(direccion.getMunicipio());
		direccionCompleta.setProvincia(direccion.getProvincia());
		direccionCompleta.setPais(direccion.getPais());
		direccionCompleta.setClaseVial(direccion.getTipoVia());
		direccionCompleta.setNombreVial(direccion.getNombreVia());
		direccionCompleta.setTipoNumeracion(direccion.getTipoNumeracion());
		if (direccion.getNumCalle() != null) {
			direccionCompleta.setNumCalle(direccion.getNumCalle().toBigInteger());
		}
		direccionCompleta.setCalificador(direccion.getCalificador());
		direccionCompleta.setClaseDireccion(direccion.getClaseDireccion());
		if (direccion.getCodDireccion() != null) {
			direccionCompleta.setCodDireccion(direccion.getCodDireccion().toBigInteger());
		}
		return direccionCompleta;
	}

	private ResultBean llamadaRest(String xmlBase64, LcTramiteVO tramite, String idSolicitud, Boolean validar, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			System.out.println(xmlBase64);
			PeticionEnvio request = crearRequest(xmlBase64, idSolicitud, validar);
			resultado = licenciasCamRest.enviarSolicitud(request);
			CodigosError response = (CodigosError) resultado.getAttachment("RESPONSE");
			resultado = gestionarRespuesta(response, tramite, validar, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada REST de Envío de Solicitud de Licencias Cam, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la llamada REST de Envío de Solicitud de Licencias Cam");
		}
		return resultado;
	}

	private ResultBean gestionarRespuesta(CodigosError response, LcTramiteVO tramite, Boolean validar, BigDecimal idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		String nombreDocumento = "";
		try {
			if (validar != null && validar) {
				nombreDocumento = "ValidarSolicitud_" + tramite.getNumExpediente();
				if (response != null && response.getCodigo() != null && !response.getCodigo().isEmpty()) {
					if (response.getCodigo().size() == 1 && "COK_001".equals(response.getCodigo().get(0).getCodigoRespuesta())) {
						servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Tramitable.getValorEnum()), idUsuario);
						tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Tramitable.getValorEnum()));
						tramite.setRespuestaVal("Validación correcta");
					} else {
						String mensaje = "";
						for (Codigo error : response.getCodigo()) {
							if (mensaje.isEmpty()) {
								mensaje = error.getDescripcion();
							} else {
								mensaje += ", " + error.getDescripcion();
							}
						}
						mensaje = mensaje.replaceAll("\\P{Print}", "");
						if (mensaje.length() > 500) {
							tramite.setRespuestaVal(mensaje.substring(0, 499));
						} else {
							tramite.setRespuestaVal(mensaje);
						}
						servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.No_Tramitable.getValorEnum()), idUsuario);
						tramite.setEstado(new BigDecimal(EstadoLicenciasCam.No_Tramitable.getValorEnum()));
					}
				}
			} else {
				nombreDocumento = "EnviarSolicitud_" + tramite.getNumExpediente();
				tramite.setFechaEnvio(new Date());
				if (response != null && response.getCodigo() != null && !response.getCodigo().isEmpty()) {
					if (response.getCodigo().size() == 1 && "COK_001".equals(response.getCodigo().get(0).getCodigoRespuesta())) {
						tramite.setRespuestaEnv(response.getCodigo().get(0).getDescripcion());
						servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Tramitable.getValorEnum()), idUsuario);
						tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Entrada_Cam.getValorEnum()));
					} else {
						String mensaje = "";
						for (Codigo error : response.getCodigo()) {
							if (mensaje.isEmpty()) {
								mensaje = error.getDescripcion();
							} else {
								mensaje += ", " + error.getDescripcion();
							}
						}
						servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.No_Tramitable.getValorEnum()), idUsuario);
						tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Finalizado_Con_Error.getValorEnum()));
						if (mensaje.length() > 500) {
							tramite.setRespuestaEnv(mensaje.substring(0, 499));
						} else {
							tramite.setRespuestaEnv(mensaje);
						}
					}
				}
			}
			servicioLcTramite.guardarOActualizar(tramite);

			String xml = toXMLCodigoError(response);
			guardarDocumentos(xml, tramite.getNumExpediente(), nombreDocumento, ConstantesGestorFicheros.RESPUESTA);
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar los datos de la response");
		}
		return resultado;
	}

	private PeticionEnvio crearRequest(String xmlBase64, String idSolicitud, Boolean validar) {
		PeticionEnvio request = new PeticionEnvio();
		String codigoAgente = gestorPropiedades.valorPropertie("licencias.cam.codigo.agente");
		request.setIdCanal(codigoAgente);
		request.setIdProceso("LIC");
		request.setIdSolicitud(idSolicitud);
		if (validar) {
			request.setIdEtapa("VALI");
		} else {
			request.setIdEtapa("EMCC");
		}
		request.setFicheroBase64(xmlBase64);
		request.setSoloValidar(validar);
		return request;
	}

	private String obtenerXMLBase64(BigDecimal numExpediente, String nombreXml) {
		String xmlBase64 = null;
		try {
			Fecha fechaBusqueda = Utilidades.transformExpedienteFecha(numExpediente);
			FileResultBean ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.LICENCIAS_CAM, ConstantesGestorFicheros.ENVIO, fechaBusqueda, nombreXml,
					ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				byte[] xml = readFileToByteArray(ficheroAenviar.getFile());
				xmlBase64 = new String(Base64.encodeBase64(xml));
			}
		} catch (Throwable e) {
			log.error("Error al crear el xml para licencias, error: ", e, numExpediente.toString());
		}
		return xmlBase64;
	}

	private ResultBean guardarDocumentos(String xmlFirmado, BigDecimal numExpediente, String nombreDocumento, String subTipo) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			FicheroBean ficheroBean = new FicheroBean();
			ficheroBean.setTipoDocumento(ConstantesGestorFicheros.LICENCIAS_CAM);
			ficheroBean.setSubTipo(subTipo);
			ficheroBean.setExtension(ConstantesGestorFicheros.EXTENSION_XML);
			ficheroBean.setNombreDocumento(nombreDocumento);
			ficheroBean.setFicheroByte(xmlFirmado.getBytes("ISO-8859-1"));
			ficheroBean.setSobreescribir(true);
			ficheroBean.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
			gestorDocumentos.guardarFichero(ficheroBean);
		} catch (Throwable e) {
			log.error("Error guardar el xml firmado de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

	private byte[] readFileToByteArray(File file) {
		FileInputStream fis = null;
		byte[] bArray = new byte[(int) file.length()];
		try {
			fis = new FileInputStream(file);
			fis.read(bArray);
			fis.close();
		} catch (IOException ioExp) {
			ioExp.printStackTrace();
		}
		return bArray;
	}

	private String toXMLCodigoError(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance(CodigosError.class);
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}

	private String toXML(Object xmlObject) throws JAXBException {
		StringWriter writer = new StringWriter();
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.licenciasCam.jaxb");
		Marshaller m = context.createMarshaller();
		m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, Boolean.TRUE);
		m.setProperty(Marshaller.JAXB_ENCODING, "ISO-8859-1");
		m.marshal(xmlObject, writer);
		writer.flush();
		return writer.toString();
	}

	private boolean validar(String stringXml) throws SAXException, JAXBException, URISyntaxException {
		URL resource = XmlCORPMEFactory.class.getResource("/org/gestoresmadrid/oegam2comun/licenciasCam/jaxb/GestionLicencias.xsd");
		JAXBContext context = JAXBContext.newInstance("org.gestoresmadrid.oegam2comun.licenciasCam.jaxb");
		try {
			SchemaFactory sf = SchemaFactory.newInstance(javax.xml.XMLConstants.W3C_XML_SCHEMA_NS_URI);
			Schema schema = sf.newSchema(resource);
			Unmarshaller unmarshaller = context.createUnmarshaller();
			unmarshaller.setSchema(schema);
			ByteArrayInputStream bais = new ByteArrayInputStream(stringXml.getBytes("ISO-8859-1"));
			unmarshaller.unmarshal(bais);
			return true;
		} catch (Exception ex) {
			log.error(ex);
			return false;
		}
	}
}
