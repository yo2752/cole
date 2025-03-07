package org.gestoresmadrid.oegamBajas.service.impl;

import java.io.FileInputStream;
import java.math.BigDecimal;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.rpc.ServiceException;
import javax.xml.rpc.handler.HandlerInfo;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.model.enumerados.TipoImpreso;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTrafBajaVO;
import org.gestoresmadrid.core.trafico.model.vo.TramiteTraficoVO;
import org.gestoresmadrid.oegam2comun.cola.view.dto.ColaDto;
import org.gestoresmadrid.oegam2comun.sega.model.service.ServicioWebServiceBtv;
import org.gestoresmadrid.oegamBajas.service.ServicioPersistenciaBaja;
import org.gestoresmadrid.oegamBajas.service.ServicioWebBtv;
import org.gestoresmadrid.oegamBajas.view.bean.ResultadoBajasBean;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import colas.constantes.ConstantesProcesos;
import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.bean.FileResultBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import net.gestores.sega.btvtramita.BTVTramitaLocator;
import net.gestores.sega.btvtramita.BTVWS;
import net.gestores.sega.btvtramita.BTVWSBindingStub;
import net.gestores.sega.btvtramita.BtvCodigoDescripcion;
import net.gestores.sega.btvtramita.BtvtramitaArgument;
import net.gestores.sega.btvtramita.BtvtramitaReturn;
import net.gestores.sega.btvtramita.ws.SoapTramitarBtvSegaWSHandler;
import utilidades.estructuras.Fecha;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

@Service
public class ServicioWebBtvImpl implements ServicioWebBtv {

	private static final long serialVersionUID = 8378034035289474498L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioWebBtvImpl.class);

	@Autowired
	GestorPropiedades gestorPropiedades;

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	ServicioPersistenciaBaja servicioPersistenciaBaja;

	@Autowired
	Utiles utiles;

	@Override
	public void finalizarSolicitudBtv(BigDecimal numExpediente, BigDecimal nuevoEstado, String respuesta, BigDecimal idUsuario) {
		try {
			servicioPersistenciaBaja.finalizarSolicitudBtv(numExpediente, nuevoEstado, respuesta, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de finalizar el tramite: " + numExpediente + " , error: ", e);
		}
	}

	@Override
	public ResultadoBajasBean procesarSolicitudBtv(ColaDto solicitud) {
		ResultadoBajasBean resultado = new ResultadoBajasBean(Boolean.FALSE);
		try {
			String xml = recogerXml(solicitud);
			if (xml != null && !xml.isEmpty()) {
				TramiteTrafBajaVO tramiteTrafBajaVO = servicioPersistenciaBaja.getTramiteBaja(solicitud.getIdTramite(), true);
				if (tramiteTrafBajaVO != null) {
					llamadaWS(tramiteTrafBajaVO,xml, resultado);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("No existe ningún tranite con el número de expediente: " + solicitud.getIdTramite());
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("No existe xml de envio para esta solicitud.");
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de procesar la solicitud de BTV, error: ", e, solicitud.getIdTramite().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
		return resultado;
	}

	private void llamadaWS(TramiteTrafBajaVO tramiteTrafBajaVO, String xml, ResultadoBajasBean resultado) {
		try {
			BtvtramitaArgument btvsoapPeticion = generarPeticionSoapTramitarBtv(xml,tramiteTrafBajaVO);
			if (btvsoapPeticion != null) {
				BtvtramitaReturn btvsoapTramitaRespuesta = getStubTramitarBtv(tramiteTrafBajaVO.getNumExpediente()).tramitarBTV(btvsoapPeticion);
				log.info("-------" + TEXTO_WS_TRAMITAR_BTV + " Recibida Respuesta WS ------" );
				if (btvsoapTramitaRespuesta != null) {
					gestionarResultadoWS(btvsoapTramitaRespuesta,tramiteTrafBajaVO.getNumExpediente(), resultado);
				} else {
					resultado.setError(Boolean.TRUE);
					resultado.setMensaje("Ha sucedido un error a la hora realizar la petición de tramitar de BTV.");
				}
			} else {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Ha sucedido un error a la hora realizar la petición de tramitar de BTV.");
			}
		} catch (Exception e) {
			log.error("Error en la llamada la WS de tramitarBTV, error: ",e, tramiteTrafBajaVO.getNumExpediente().toString());
			resultado.setError(Boolean.TRUE);
			resultado.setExcepcion(e);
		}
	}

	private void gestionarResultadoWS(BtvtramitaReturn btvsoapTramitaRespuesta, BigDecimal numExpediente, ResultadoBajasBean resultado) {
		String mensaje = "";
		if ("OK".equals(btvsoapTramitaRespuesta.getResultado_descripcion())) {
			guardarInformeBajas(btvsoapTramitaRespuesta, numExpediente, resultado);
			if (!resultado.getError() && btvsoapTramitaRespuesta.getAvisos() != null) {
				mensaje = resultado.getRespuesta() + "---Avisos:";
				for (BtvCodigoDescripcion avisos : btvsoapTramitaRespuesta.getAvisos()) {
					if (mensaje.isEmpty()) {
						mensaje = avisos.getCodigo() + ": " + avisos.getDescripcion();
					} else {
						mensaje += ", " + avisos.getCodigo() + ": " + avisos.getDescripcion();
					}
				}
				resultado.setRespuesta(mensaje);
			}
		} else if ("NO TRAMITABLE".equals(btvsoapTramitaRespuesta.getResultado_descripcion())) {
			if (btvsoapTramitaRespuesta.getImpedimentos() != null) {
				mensaje += "---Impedimentos:";
				for (BtvCodigoDescripcion impedimento : btvsoapTramitaRespuesta.getImpedimentos()) {
					if (mensaje.isEmpty()) {
						mensaje = impedimento.getCodigo() + ": " + impedimento.getDescripcion();
					} else {
						mensaje += ", " + impedimento.getCodigo() + ": " + impedimento.getDescripcion();
					}
				}
			}
			if (btvsoapTramitaRespuesta.getAvisos() != null) {
				mensaje += "---Avisos:";
				for (BtvCodigoDescripcion avisos : btvsoapTramitaRespuesta.getAvisos()) {
					if (mensaje.isEmpty()) {
						mensaje = avisos.getCodigo() + ": " + avisos.getDescripcion();
					} else {
						mensaje += ", " + avisos.getCodigo() + ": " + avisos.getDescripcion();
					}
				}
			}
			resultado.setIsNoTramitable(Boolean.TRUE);
			resultado.setRespuesta(mensaje);
		} else if (ConstantesProcesos.ERROR.equals(btvsoapTramitaRespuesta.getResultado_descripcion())) {
			for (BtvCodigoDescripcion error : btvsoapTramitaRespuesta.getErrores()) {
				if (mensaje.isEmpty()) {
					mensaje = error.getCodigo() + ": " + error.getDescripcion();
				} else {
					mensaje += ", " + error.getCodigo() + ": " + error.getDescripcion();
				}
			}
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje(mensaje);
			resultado.setRespuesta(mensaje);
		}
	}

	private void guardarInformeBajas(BtvtramitaReturn btvsoapTramitaRespuesta, BigDecimal numExpediente, ResultadoBajasBean resultado) {
		if (btvsoapTramitaRespuesta.getInforme() != null && !btvsoapTramitaRespuesta.getInforme().isEmpty()) {
			byte[] informe = null;
			try {
				informe = utiles.doBase64Decode(btvsoapTramitaRespuesta.getInforme(), ServicioWebServiceBtv.UTF_8);
				if (informe != null) {
					FicheroBean fichero = new FicheroBean();
					fichero.setTipoDocumento(ConstantesGestorFicheros.TRAMITAR_BTV);
					fichero.setSubTipo(ConstantesGestorFicheros.INFORMES);
					fichero.setFecha(Utilidades.transformExpedienteFecha(numExpediente));
					fichero.setExtension(ConstantesGestorFicheros.EXTENSION_PDF);
					fichero.setFicheroByte(informe);
					fichero.setNombreDocumento(TipoImpreso.InformeBajaTelematica.getNombreEnum() + "_" + numExpediente);
					gestorDocumentos.guardarByte(fichero);
					resultado.setRespuesta("Baja tramitada telemáticamente");
				} else {
					resultado.setMensaje("Ha sucedido un error a la hora de tratar el informe de baja y no se ha custodiado correctamente.");
					resultado.setRespuesta("Ha sucedido un error a la hora de tratar el informe de baja y no se ha custodiado correctamente.");
					resultado.setError(Boolean.TRUE);
				}
			} catch (Throwable e) {
				log.error("Ha sucedido un error a la hora de tratar el informe de baja, error: ",e, numExpediente.toString());
				resultado.setMensaje("Ha sucedido un error a la hora de recuperar el informe de baja y no se ha custodiado correctamente.");
				resultado.setRespuesta("No se ha podido recuperar el informe de baja");
				resultado.setError(Boolean.TRUE);
			}
		} else {
			log.error("Para el expediente: " + numExpediente + ", no se ha podido guardar el informe de baja porque no lo hemos recibido de DGT.");
		}
	}

	private BTVWSBindingStub getStubTramitarBtv(BigDecimal numExpediente) throws MalformedURLException, ServiceException {
		BTVWSBindingStub stubTramitarBtv = null;
		String timeOut = gestorPropiedades.valorPropertie(ServicioWebServiceBtv.TIMEOUT_PROP_TRAMITAR_BTV);
		URL miUrl = new URL(gestorPropiedades.valorPropertie(ServicioWebServiceBtv.WEBSERVICE_TRAMITAR_BTV));
		BTVWS tramitarBtvService = null;
		if (miUrl != null) {
			BTVTramitaLocator btvTramitarLocator = new BTVTramitaLocator();
			javax.xml.namespace.QName portName = new javax.xml.namespace.QName(miUrl.toString(),btvTramitarLocator.getBTVTramitaWSDDServiceName());

			@SuppressWarnings("unchecked")
			List<HandlerInfo> list = btvTramitarLocator.getHandlerRegistry().getHandlerChain(portName);
			HandlerInfo logHandlerInfo = new HandlerInfo();
			logHandlerInfo.setHandlerClass(SoapTramitarBtvSegaWSHandler.class);

			Map<String, Object> filesConfig = new HashMap<>();
			filesConfig.put(SoapTramitarBtvSegaWSHandler.PROPERTY_KEY_ID, numExpediente);
			logHandlerInfo.setHandlerConfig(filesConfig);

			list.add(logHandlerInfo);

			tramitarBtvService = btvTramitarLocator.getBTVTramita(miUrl);
			stubTramitarBtv = (BTVWSBindingStub) tramitarBtvService;
			stubTramitarBtv.setTimeout(Integer.parseInt(timeOut));
		}
		return stubTramitarBtv;
	}

	private BtvtramitaArgument generarPeticionSoapTramitarBtv(String xml, TramiteTraficoVO tramiteTrafVO) {
		BtvtramitaArgument btvsoapPeticion = null;
		if (tramiteTrafVO.getContrato().getColegiado() != null
				&& tramiteTrafVO.getContrato().getColegiado().getUsuario() != null
				&& tramiteTrafVO.getContrato().getColegio() != null) {
			btvsoapPeticion = new BtvtramitaArgument();
			btvsoapPeticion.setDoi_gestor(tramiteTrafVO.getContrato().getColegiado().getUsuario().getNif());
			btvsoapPeticion.setDoi_gestoria(tramiteTrafVO.getContrato().getColegiado().getUsuario().getNif());
			btvsoapPeticion.setDoi_plataforma(tramiteTrafVO.getContrato().getColegio().getCif());
			btvsoapPeticion.setNum_expediente_propio(tramiteTrafVO.getNumExpediente().toString());
			btvsoapPeticion.setHistorico(false);
			try {
				btvsoapPeticion.setXmlB64(utiles.doBase64Encode(xml.getBytes(StandardCharsets.UTF_8)));
			} catch (Exception e) {
				log.error("Ha sucedido un error a la hora de pasar a base64 el XML de envio, error: ", e, tramiteTrafVO.getNumExpediente().toString());
				return null;
			}
		}
		return btvsoapPeticion;
	}

	private String recogerXml(ColaDto solicitud) {
		FileResultBean ficheroAenviar = null;
		String xml = null;
		try {
			Fecha fecha = Utilidades.transformExpedienteFecha(solicitud.getIdTramite());
			ficheroAenviar = gestorDocumentos.buscarFicheroPorNombreTipo(ConstantesGestorFicheros.TRAMITAR_BTV,
					ConstantesGestorFicheros.XML_ENVIO, fecha, solicitud.getXmlEnviar(), ConstantesGestorFicheros.EXTENSION_XML);
			if (ficheroAenviar != null && ficheroAenviar.getFile() != null) {
				FileInputStream fis = new FileInputStream(ficheroAenviar.getFile());
				byte[] by = IOUtils.toByteArray(fis);
				xml = new String (by, StandardCharsets.UTF_8);
			}
		} catch (Exception | OegamExcepcion e) {
			log.error("Error al recuperar el documento, error: ", e, solicitud.getIdTramite().toString());
		}
		return xml;
	}
}