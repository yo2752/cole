package org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.impl;

import java.io.StringWriter;
import java.math.BigDecimal;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import org.gestoresmadrid.core.licencias.model.enumerados.EstadoLicenciasCam;
import org.gestoresmadrid.core.licencias.model.enumerados.EstadoSolicitudLicenciasCam;
import org.gestoresmadrid.core.licencias.model.vo.LcTramiteVO;
import org.gestoresmadrid.oegam2comun.cola.model.service.ServicioCola;
import org.gestoresmadrid.oegam2comun.licenciasCam.jaxb.CodigoError;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.service.ServicioLcTramite;
import org.gestoresmadrid.oegam2comun.licenciasCam.model.ws.service.ServicioLicConsultarSolicitudRestWS;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.LicenciasCamRest;
import org.gestoresmadrid.oegam2comun.licenciasCam.restWS.response.EstadoSolicitud;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.constantes.ConstantesGestorFicheros;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import es.globaltms.gestorDocumentos.util.Utilidades;
import escrituras.beans.ResultBean;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

@Service
public class ServicioLicConsultarSolicitudRestWSImpl implements ServicioLicConsultarSolicitudRestWS {

	private static final long serialVersionUID = -1882206332845808525L;

	private static final ILoggerOegam log = LoggerOegam.getLogger(ServicioLicConsultarSolicitudRestWSImpl.class);

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	LicenciasCamRest licenciasCamRest;

	@Autowired
	ServicioCola servicioCola;

	@Autowired
	ServicioLcTramite servicioLcTramite;

	@Override
	public ResultBean consultarSolicitudRest(BigDecimal numExpediente, Long idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			LcTramiteVO tramite = servicioLcTramite.getTramiteLcVO(numExpediente, false);
			resultado = llamadaRest(tramite, tramite.getIdSolicitud(), idUsuario);
		} catch (Throwable e) {
			log.error("Ha sucedido un error a la hora de realizar la consulta de solicitud para las Licencias CAM: " + numExpediente + ", error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la consulta de solicitud para las Licencias CAM");
		}
		return resultado;
	}

	private ResultBean llamadaRest(LcTramiteVO tramite, String idSolicitud, Long idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			resultado = licenciasCamRest.consultarSolicitud(idSolicitud);
			EstadoSolicitud response = (EstadoSolicitud) resultado.getAttachment("RESPONSE");
			resultado = gestionarRespuesta(response, tramite, idUsuario);
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de realizar la llamada REST de Consulta de Solicitud de Licencias Cam, error: ", e);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Ha sucedido un error a la hora de realizar la llamada REST de Consulta de Solicitud de Licencias Cam");
		}
		return resultado;
	}

	private ResultBean gestionarRespuesta(EstadoSolicitud response, LcTramiteVO tramite, Long idUsuario) {
		ResultBean resultado = new ResultBean(Boolean.FALSE);
		try {
			tramite.setRespuestaCon(response.getDescripcionEstado());
			resultado.setMensaje(response.getDescripcionEstado());
			if (EstadoSolicitudLicenciasCam.Registrada.getValorEnum().equals(response.getCodigoEstado())) {
				servicioLcTramite.cambiarEstado(true, tramite.getNumExpediente(), tramite.getEstado(), new BigDecimal(EstadoLicenciasCam.Tramitable.getValorEnum()), new BigDecimal(idUsuario));
				tramite.setEstado(new BigDecimal(EstadoLicenciasCam.Registrada.getValorEnum()));
			} else if (EstadoSolicitudLicenciasCam.Solicitud_Con_Errores.getValorEnum().equals(response.getCodigoEstado())) {
				resultado.setError(Boolean.TRUE);
				if (response.getErrores() != null && !response.getErrores().isEmpty()) {
					for (CodigoError error : response.getErrores()) {
						resultado.addMensajeALista(error.getCodigoRespuesta() + " - " + error.getDescripcion());
					}
				}
			}
			servicioLcTramite.guardarOActualizar(tramite);

			String xml = toXML(response);
			String nombreDocumento = "ConsultarSolicitud_" + tramite.getNumExpediente();
			resultado = guardarDocumentos(xml, tramite.getNumExpediente(), nombreDocumento, ConstantesGestorFicheros.RESPUESTA);
		} catch (Exception e) {
			log.error("Error al guardar los datos de la response");
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Error al guardar los datos de la response");
		}
		return resultado;
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
			log.error("Error guardar el xml de licencias, error: ", e, numExpediente.toString());
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
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
}
