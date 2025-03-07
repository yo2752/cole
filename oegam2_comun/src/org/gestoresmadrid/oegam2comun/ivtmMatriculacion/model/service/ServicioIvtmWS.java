package org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.service;

import java.math.BigDecimal;

import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ResultBeanAltaIVTM;
import org.gestoresmadrid.oegam2comun.ivtmMatriculacion.model.view.dto.ResultBeanConsultaIVTM;

public interface ServicioIvtmWS {

	public final String TIMEOUT_PROP_CONSULTA_IVTM = "webservice.consultaBtv.timeOut";
	public final String WEBSERVICE_URL_IVTM = "https://test.munimadrid.es:444/INTOP_SBExterna/InteroperabilidadExterna";
	public final String WEBSERVICE_URL_IVTM_MOCK = "http://localhost:8085/prueba";
	public final String UTF_8 = "UTF-8";
	public final String NOMBRE_HOST_SOLICITUD = "nombreHostSolicitud";

	ResultBeanAltaIVTM procesarSolicitudAlta(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);
	ResultBeanConsultaIVTM procesarSolicitudModificacion(BigDecimal numExpediente, String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);
	ResultBeanConsultaIVTM procesarSolicitudConsulta(BigDecimal numExpediente,String xmlEnviar, BigDecimal idUsuario, BigDecimal idContrato);

}