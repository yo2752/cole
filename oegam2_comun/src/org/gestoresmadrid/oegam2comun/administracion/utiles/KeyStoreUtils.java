package org.gestoresmadrid.oegam2comun.administracion.utiles;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.Key;
import java.security.KeyStore;
import java.security.cert.Certificate;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;

import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.io.IOUtils;
import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.administracion.view.dto.CertificateDto;
import org.gestoresmadrid.utilidades.components.UtilesFecha;

import administracion.beans.CertificateBean;

public class KeyStoreUtils {

	private final String JKS = "JKS";
	private final String X509_CERT_TYPE = "X.509";
	private final String PKCS_12 = "PKCS12";
	private final int HTTPS_PORT = 443;

	public KeyStore load(String ruta, String password) throws Exception {
		File fKeyStore;
		char[] cPassword = password.toCharArray();
		KeyStore keyStore = null;
		fKeyStore = new File(ruta);
		keyStore = KeyStore.getInstance(JKS);
		FileInputStream fis = new FileInputStream(fKeyStore);
		keyStore.load(fis, cPassword);
		return keyStore;
	}

	public void modificarAliasDeClavePublica(String alias, String nuevoAlias, KeyStore keyStore, String ruta, String password) throws Exception {
		Certificate cert = keyStore.getCertificate(alias);
		keyStore.deleteEntry(alias);
		keyStore.setCertificateEntry(nuevoAlias, cert);
		confirmarCambios(keyStore, new File(ruta), password);
	}

	public void modificarAliasDeClavePrivada(String alias, String nuevoAlias, KeyStore keyStore, String ruta, String password) throws Exception {
		Key key = keyStore.getKey(alias, password.toCharArray());
		Certificate[] certs = keyStore.getCertificateChain(alias);
		keyStore.deleteEntry(alias);
		keyStore.setKeyEntry(nuevoAlias, key, password.toCharArray(), certs);
		confirmarCambios(keyStore, new File(ruta), password);
	}

	public void borrarCertificado(String alias, KeyStore keyStore, String ruta, String password) throws Exception {
		keyStore.deleteEntry(alias);
		confirmarCambios(keyStore, new File(ruta), password);
	}

	public ByteArrayInputStream getStream(String alias, KeyStore keyStore) throws Exception {
		Certificate certificate = keyStore.getCertificate(alias);
		X509Certificate x509Certificate = generarCertificado(certificate.getEncoded());
		byte[] bytesCertificado = x509Certificate.getEncoded();
		ByteArrayInputStream stream = new ByteArrayInputStream(bytesCertificado);
		return stream;
	}

	public String importar(boolean sobrescribir, File file, String alias, KeyStore keyStore, String ruta, String password) throws Exception {
		FileInputStream fis = new FileInputStream(file);
		byte[] nuevoCertificadoBytes = IOUtils.toByteArray(fis);
		X509Certificate x509Certificate = generarCertificado(nuevoCertificadoBytes);
		for (Enumeration<String> enumeration = keyStore.aliases(); enumeration.hasMoreElements();) {
			String aliasEnum = enumeration.nextElement();
			if (keyStore.isCertificateEntry(aliasEnum)) {
				Certificate certificate = keyStore.getCertificate(aliasEnum);
				X509Certificate compCert = generarCertificado(certificate.getEncoded());
				if (x509Certificate.equals(compCert)) {
					if (sobrescribir) {
						keyStore.deleteEntry(aliasEnum);
					} else {
						return aliasEnum;
					}
				}
			}
		}
		keyStore.setCertificateEntry(alias, x509Certificate);
		confirmarCambios(keyStore, new File(ruta), password);
		return null;
	}

	public String importarClavePrivada(boolean sobrescribir, File fileClaves, String passwordClaves, String alias, KeyStore almacen, String ruta, String password) throws Exception {

		KeyStore keyStore = KeyStore.getInstance(PKCS_12);
		FileInputStream fis = new FileInputStream(fileClaves);
		keyStore.load(fis, passwordClaves.toCharArray());
		Key key = null;
		String aliasClave = null;
		Certificate[] certs = null;
		for (Enumeration<String> enumeration = keyStore.aliases(); enumeration.hasMoreElements();) {
			aliasClave = enumeration.nextElement();
			if (keyStore.isKeyEntry(aliasClave)) {
				key = keyStore.getKey(aliasClave, passwordClaves.toCharArray());
				certs = keyStore.getCertificateChain(aliasClave);
				break;
			}
		}
		//
		for (Enumeration<String> enumeration = almacen.aliases(); enumeration.hasMoreElements();) {
			String aliasAlmacen = enumeration.nextElement();
			if (almacen.isKeyEntry(aliasAlmacen)) {
				Key keyAlmacen = almacen.getKey(aliasAlmacen, password.toCharArray());
				if (key.equals(keyAlmacen) && !sobrescribir) {
					return aliasAlmacen;
				} else if (key.equals(keyAlmacen) && sobrescribir) {
					almacen.deleteEntry(aliasAlmacen);
				}
				break;
			}
		}
		//
		almacen.setKeyEntry(alias, key, password.toCharArray(), certs);
		confirmarCambios(almacen, new File(ruta), password);
		return null;

	}

	public CertificateDto[] getCertificateDtoList(KeyStore keyStore) throws Exception {
		ArrayList<CertificateDto> certificateDtoArray = new ArrayList<CertificateDto>();
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		CertificateDto certificateDto = null;
		Enumeration<String> aliases = keyStore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			X509Certificate x509certificate = (X509Certificate) keyStore.getCertificate(alias);
			Date desde = x509certificate.getNotBefore();
			Date hasta = x509certificate.getNotAfter();
			certificateDto = new CertificateDto();
			certificateDto.setAlias(alias);
			certificateDto.setValidoDesde(utilesFecha.formatoFecha(desde));
			certificateDto.setValidoHasta(utilesFecha.formatoFecha(hasta));
			long diasValidezRestantes = utilesFecha.diferenciaFechaEnDias(new Date(), hasta);
			certificateDto.setDiasValidezRestantes(diasValidezRestantes);
			certificateDto.setInfo(getCertificateInfo(x509certificate));
			certificateDtoArray.add(certificateDto);
		}
		CertificateDto[] array = new CertificateDto[certificateDtoArray.size()];
		certificateDtoArray.toArray(array);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i].getDiasValidezRestantes() < array[j].getDiasValidezRestantes()) {
					CertificateDto certificateDtoTemp = array[j];
					array[j] = array[i];
					array[i] = certificateDtoTemp;
				}
			}
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].getDiasValidezRestantes() < 0) {
				array[i].setDiasValidezRestantes(new Long(0));
			}
		}
		return array;
	}

	public CertificateDto getCertificate(String aliasBuscado, KeyStore keyStore) throws Exception {
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		CertificateDto certificateDto = null;
		Enumeration<String> aliases = keyStore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (alias.equals(aliasBuscado)) {
				X509Certificate x509certificate = (X509Certificate) keyStore.getCertificate(alias);
				Date desde = x509certificate.getNotBefore();
				Date hasta = x509certificate.getNotAfter();
				certificateDto = new CertificateDto();
				certificateDto.setAlias(alias);
				certificateDto.setValidoDesde(utilesFecha.formatoFecha(desde));
				certificateDto.setValidoHasta(utilesFecha.formatoFecha(hasta));
				long diasValidezRestantes = utilesFecha.diferenciaFechaEnDias(new Date(), hasta);
				if (diasValidezRestantes > 0) {
					certificateDto.setDiasValidezRestantes(diasValidezRestantes);
				} else {
					certificateDto.setDiasValidezRestantes(new Long(0));
				}
				certificateDto.setInfo(getCertificateInfo(x509certificate));
				return certificateDto;
			}
		}
		throw new Exception("No existe el alias: " + aliasBuscado + " en el almacén de certificados: " + keyStore);
	}

	public CertificateBean[] getCertificateBeanList(KeyStore keyStore) throws Exception {
		ArrayList<CertificateBean> certificateBeanArray = new ArrayList<CertificateBean>();
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		CertificateBean certificateBean = null;
		Enumeration<String> aliases = keyStore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			X509Certificate x509certificate = (X509Certificate) keyStore.getCertificate(alias);
			Date desde = x509certificate.getNotBefore();
			Date hasta = x509certificate.getNotAfter();
			certificateBean = new CertificateBean();
			certificateBean.setAlias(alias);
			certificateBean.setValidoDesde(utilesFecha.formatoFecha(desde));
			certificateBean.setValidoHasta(utilesFecha.formatoFecha(hasta));
			long diasValidezRestantes = utilesFecha.diferenciaFechaEnDias(new Date(), hasta);
			certificateBean.setDiasValidezRestantes(diasValidezRestantes);
			certificateBean.setInfo(getCertificateInfo(x509certificate));
			certificateBeanArray.add(certificateBean);
		}
		CertificateBean[] array = new CertificateBean[certificateBeanArray.size()];
		certificateBeanArray.toArray(array);
		for (int i = 0; i < array.length; i++) {
			for (int j = 0; j < array.length; j++) {
				if (array[i].getDiasValidezRestantes() < array[j].getDiasValidezRestantes()) {
					CertificateBean certificateBeanTemp = array[j];
					array[j] = array[i];
					array[i] = certificateBeanTemp;
				}
			}
		}
		for (int i = 0; i < array.length; i++) {
			if (array[i].getDiasValidezRestantes() < 0) {
				array[i].setDiasValidezRestantes(0);
			}
		}
		return array;
	}

	public CertificateBean getCertificateBean(String aliasBuscado, KeyStore keyStore) throws Exception {
		UtilesFecha utilesFecha = ContextoSpring.getInstance().getBean(UtilesFecha.class);
		CertificateBean certificateBean = null;
		Enumeration<String> aliases = keyStore.aliases();
		while (aliases.hasMoreElements()) {
			String alias = aliases.nextElement();
			if (alias.equals(aliasBuscado)) {
				X509Certificate x509certificate = (X509Certificate) keyStore.getCertificate(alias);
				Date desde = x509certificate.getNotBefore();
				Date hasta = x509certificate.getNotAfter();
				certificateBean = new CertificateBean();
				certificateBean.setAlias(alias);
				certificateBean.setValidoDesde(utilesFecha.formatoFecha(desde));
				certificateBean.setValidoHasta(utilesFecha.formatoFecha(hasta));
				long diasValidezRestantes = utilesFecha.diferenciaFechaEnDias(new Date(), hasta);
				if (diasValidezRestantes > 0) {
					certificateBean.setDiasValidezRestantes(diasValidezRestantes);
				} else {
					certificateBean.setDiasValidezRestantes(0);
				}
				certificateBean.setInfo(getCertificateInfo(x509certificate));
				return certificateBean;
			}
		}
		throw new Exception("No existe el alias: " + aliasBuscado + " en el almacén de certificados: " + keyStore);
	}

	public String comprobarCertificadosUrl(String url, KeyStore keyStore) throws Exception {
		SSLSocket socket = null;
		try {
			String hostName = getHostName(url);
			SSLSocketFactory factory = (SSLSocketFactory) SSLSocketFactory.getDefault();
			socket = (SSLSocket) factory.createSocket(hostName, HTTPS_PORT);
			socket.setSoTimeout(10000);
			SSLSession sslSession = socket.getSession();
			Certificate[] serverCerts = sslSession.getPeerCertificates();
			socket.close();
			for (Certificate certServer : serverCerts) {
				X509Certificate x509CertServer = generarCertificado(certServer.getEncoded());
				for (Enumeration<String> enumeration = keyStore.aliases(); enumeration.hasMoreElements();) {
					String aliasEnum = enumeration.nextElement();
					if (keyStore.isCertificateEntry(aliasEnum)) {
						Certificate certificate = keyStore.getCertificate(aliasEnum);
						X509Certificate x509CertAlmacen = generarCertificado(certificate.getEncoded());
						if (x509CertAlmacen.equals(x509CertServer)) {
							return aliasEnum;
						}
					}
				}
			}
		} catch (Exception ex) {
			if (socket != null) {
				socket.close();
			}
			throw new Exception(ex);
		}
		return null;
	}

	// MÉTODOS PRIVADOS

	private String getCertificateInfo(X509Certificate x509certificate) throws Exception {
		String nuevalinea = System.getProperty("line.separator");
		StringBuilder info = new StringBuilder();
		info.append("SUBJECT : " + x509certificate.getSubjectDN() + nuevalinea + nuevalinea);
		info.append("ISSUER : " + x509certificate.getIssuerDN() + nuevalinea + nuevalinea);
		info.append("SERIAL NUMBER : " + x509certificate.getSerialNumber() + nuevalinea + nuevalinea);
		info.append("NOT BEFORE : " + x509certificate.getNotBefore() + nuevalinea + nuevalinea);
		info.append("NOT AFTER : " + x509certificate.getNotAfter() + nuevalinea + nuevalinea);
		info.append("TYPE : " + x509certificate.getType() + nuevalinea + nuevalinea);
		info.append("KEY USAGE : " + x509certificate.getKeyUsage() + nuevalinea + nuevalinea);
		info.append("EXTENDED KEY USAGE : " + x509certificate.getExtendedKeyUsage() + nuevalinea + nuevalinea);
		info.append("PUBLIC KEY : " + x509certificate.getPublicKey() + nuevalinea + nuevalinea);
		info.append("SIGNATURE ALGORITHM NAME : " + x509certificate.getSigAlgName() + nuevalinea + nuevalinea);
		info.append("SIGNATURE ALGORITHM OID : " + x509certificate.getSigAlgOID() + nuevalinea + nuevalinea);
		info.append("SIGNATURE : " + x509certificate.getSignature() + nuevalinea + nuevalinea);
		return info.toString();
	}

	private X509Certificate generarCertificado(byte[] certificadoBytes) throws Exception {
		ByteArrayInputStream bais = new ByteArrayInputStream(certificadoBytes);
		CertificateFactory cf = CertificateFactory.getInstance(X509_CERT_TYPE);
		X509Certificate x509Certificate = (X509Certificate) cf.generateCertificate(bais);
		return x509Certificate;
	}

	private void confirmarCambios(KeyStore keyStore, File fKeyStoreFile, String password) throws Exception {
		char[] cPassword = password.toCharArray();
		FileOutputStream fos = new FileOutputStream(fKeyStoreFile);
		keyStore.store(fos, cPassword);
	}

	private String getHostName(String url) throws MalformedURLException {
		URL host = new URL(url);
		String hostName = host.getHost();
		return hostName;
	}
}
