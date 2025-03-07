package org.oegam.gestor.distintivos.service;

import java.security.Principal;
import java.security.cert.X509Certificate;

import javax.servlet.http.HttpServletRequest;

import org.oegam.gestor.distintivos.integracion.bean.ResultadoCertBean;

public interface CertificateService {
	X509Certificate obtenerCertificado(HttpServletRequest httpRequest);
	Principal       obtenerPrincipal(X509Certificate cert);
	String          obtenerEmisor(X509Certificate cert);
	String          obtenerJefatura(X509Certificate cert);
	String 			obtenerJefatura(HttpServletRequest httpRequest);
	ResultadoCertBean obtenerDatosCertificado(HttpServletRequest request);
}
