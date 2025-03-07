package org.oegam.gestor.distintivos.service.impl;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.cryptacular.x509.dn.NameReader;
import org.cryptacular.x509.dn.RDNSequence;
import org.cryptacular.x509.dn.StandardAttributeType;
import org.oegam.gestor.distintivos.integracion.bean.ResultadoCertBean;
import org.oegam.gestor.distintivos.model.Emisor;
import org.oegam.gestor.distintivos.service.CertificateService;
import org.springframework.stereotype.Service;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


@Service
public class CertificateServiceImpl implements CertificateService {

	private static final ILoggerOegam log = LoggerOegam.getLogger(CertificateServiceImpl.class);

	private static final String ATTRIBUTECERTIFICATE = "javax.servlet.request.X509Certificate";

	@Override
	public Principal obtenerPrincipal(X509Certificate cert) {
		return cert.getSubjectDN();
	}

	@Override
	public String obtenerEmisor(X509Certificate cert) {
		RDNSequence dn = new NameReader(cert).readSubject();
		String emisor = dn.getValue(StandardAttributeType.LocalityName);
		log.info("Emisor ==> " + emisor);
		return emisor;
	}

	@Override
	public String obtenerJefatura(X509Certificate cert) {
		return Emisor.obtenerJefatura(obtenerEmisor(cert)).getValorEnum();
	}

	@Override
	public X509Certificate obtenerCertificado(HttpServletRequest httpRequest) {
		X509Certificate cert = null;
		X509Certificate[] certs = (X509Certificate[]) httpRequest.getAttribute(ATTRIBUTECERTIFICATE);
		if (null != certs && certs.length > 0) { 
			cert = certs[0];
		}
		return cert;
	}

	@Override
	public String obtenerJefatura(HttpServletRequest httpRequest) {
		// TODO Auto-generated method stub
		X509Certificate cert = obtenerCertificado(httpRequest);
		if (null != cert) {
			return obtenerJefatura(cert);
		}
		return null;
	}

	@Override
	public ResultadoCertBean obtenerDatosCertificado(HttpServletRequest request) {
		ResultadoCertBean resultado = new ResultadoCertBean(Boolean.FALSE);
		try {
			X509Certificate cert = obtenerCertificado(request);
			if (cert != null) {
				RDNSequence dn = new NameReader(cert).readSubject();
				if (dn.getValue(StandardAttributeType.LocalityName).contains(Emisor.Colegio.getNombreEnum())) {
					resultado.setJefatura(Emisor.Colegio);
					if (dn.getValue(StandardAttributeType.LocalityName).contains("PC")) {
						resultado.setTipoRassb("PC_DSTV");
					} else {
						resultado.setTipoRassb("FCT");
					}
				} else {
					resultado.setJefatura(Emisor.convertirLocalityName(dn.getValue(StandardAttributeType.LocalityName)));
				}
			} else {
				log.error("No se ha indicado un certificado para realizar la conexión.");
				resultado.setError(Boolean.TRUE);
			}
		} catch (Exception e) {
			log.error("Ha sucedido un error a la hora de obtener la jefatura y la rasberry, error: ", e);
			resultado.setError(Boolean.TRUE);
		}
		return resultado;
	}

}