package org.gestoresmadrid.oegamComun.service.build;

import java.io.Serializable;

import org.apache.commons.lang3.StringUtils;
import org.gestoresmadrid.oegamComun.checkCertificados.jaxb.DatosFirmados;
import org.gestoresmadrid.oegamComun.checkCertificados.jaxb.ObjectFactory;
import org.gestoresmadrid.oegamComun.checkCertificados.jaxb.SolicitudPruebaCertificado;
import org.gestoresmadrid.oegamComun.checkCertificados.utiles.XMLCheckCertificado;
import org.gestoresmadrid.oegamComun.view.bean.ResultadoBean;
import org.springframework.stereotype.Component;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import viafirma.utilidades.UtilesViafirma;

@Component
public class BuildCheckCaducidadCert implements Serializable{

	private static final long serialVersionUID = 743084162429013704L;
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(BuildCheckCaducidadCert.class);

	UtilesViafirma utilesViafirma;
	
	public ResultadoBean comprobarCaducidadCertificado(String alias) {
		ResultadoBean resultado = new ResultadoBean(Boolean.FALSE);
		try {
			ObjectFactory objectFactory = new ObjectFactory();
			SolicitudPruebaCertificado solicitudPruebaCertificado = (SolicitudPruebaCertificado) objectFactory.createSolicitudPruebaCertificado();
			DatosFirmados datosFirmados = objectFactory.createDatosFirmados();
			datosFirmados.setAlias(alias);
			solicitudPruebaCertificado.setDatosFirmados(datosFirmados);
			XMLCheckCertificado xmlPruebaCertificado = new XMLCheckCertificado();
			String xml = xmlPruebaCertificado.toXMLSolicitudPruebaCert(solicitudPruebaCertificado);
			String idFirma = getUtilesViafirma().firmarPruebaCertificadoCaducidad(xml, alias);
			if (StringUtils.isBlank(idFirma)) {
				resultado.setError(Boolean.TRUE);
				resultado.setMensaje("Certificado caducado");
			}
		} catch (Exception e) {
			log.error("Certificado caducado para el alias: " + alias);
			resultado.setError(Boolean.TRUE);
			resultado.setMensaje("Certificado caducado");
		}
		return resultado;
	}
	
	public UtilesViafirma getUtilesViafirma() {
		if (utilesViafirma == null) {
			utilesViafirma = new UtilesViafirma();
		}
		return utilesViafirma;
	}
}
