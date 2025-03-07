package administracion.acciones;

import java.io.File;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.cert.CertificateException;

import org.apache.struts2.ServletActionContext;
import org.gestoresmadrid.oegam2comun.administracion.utiles.KeyStoreUtils;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.Utiles;
import org.springframework.beans.factory.annotation.Autowired;

import administracion.beans.CertificateBean;
import general.acciones.ActionBase;
import trafico.utiles.UtilesWSMate;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.web.OegamExcepcion;

public class KeyStoreAction extends ActionBase {

	private static final long serialVersionUID = 1L;
	private static final ILoggerOegam log = LoggerOegam.getLogger(KeyStoreAction.class);

	private static KeyStore clavesPublicasJks;
	private static KeyStore clavesPrivadasJks;
	private static CertificateBean[] certificatesClavesPublicas;
	private static CertificateBean[] certificatesClavesPrivadas;

	private String keyStorePassword;
	private String keyStoreName;

	private final String JSP = "jspKeyStores";
	private final String EXPORTACION_CERTIFICADO = "exportacionCertificado";

	private final String REF_PROP_CLAVES_PUBLICAS_UBICACION = "keystore.claves.publicas.xDefecto.ubicacion";
	private final String REF_PROP_CLAVES_PUBLICAS_PASSWORD = "keystore.claves.publicas.xDefecto.password";
	private final String REF_PROP_CLAVES_PRIVADAS_UBICACION = "keystore.claves.privadas.xDefecto.ubicacion";
	private final String REF_PROP_CLAVES_PRIVADAS_PASSWORD = "keystore.claves.privadas.xDefecto.password";

	private static String CLAVES_PUBLICAS_UBICACION;
	private static String CLAVES_PUBLICAS_PASSWORD;
	private static String CLAVES_PRIVADAS_UBICACION;
	private static String CLAVES_PRIVADAS_PASSWORD;

	private final String CLAVES_PUBLICAS_JKS = "CLAVES_PUBLICAS";
	private final String CLAVES_PRIVADAS_JKS = "CLAVES_PRIVADAS";

	private final String POP_UP_DETALLE = "popUpDetalle";
	private String infoCertificado;
	private String aliasCertificado;
	private String nuevoAlias;
	private static String almacenAbierto;
	private boolean certificadoEspera;

	private InputStream inputStream;
	private String fileName;
	private final String EXTENSION_CERTIFICADO_EXPORTADO = ".cer";

	private String fileUploadContentType;
	private String fileUploadFileName;
	private File fileUpload;

	private String aliasCertificadoDuplicado;
	private boolean sobrescribir;
	private String passwordClavePrivada;
	private String comprobarUrl;

	private final String PFX = "pfx";
	private final String P12 = "p12";
	private final String PEM = "pem";
	
	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private Utiles utiles;

	public String abrirPopUpDetalles(){
		try{
			String aliasCertificado = ServletActionContext.getRequest().getParameter("aliasCertificado");
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			CertificateBean certificateBean = null;
			if(almacenAbierto.equals(CLAVES_PUBLICAS_JKS)){
				certificateBean = keyStoreUtils.getCertificateBean(aliasCertificado, clavesPublicasJks);
			}else if(almacenAbierto.equals(CLAVES_PRIVADAS_JKS)){
				certificateBean = keyStoreUtils.getCertificateBean(aliasCertificado, clavesPrivadasJks);
			}
			infoCertificado = certificateBean.getInfo();
			return POP_UP_DETALLE;
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return POP_UP_DETALLE;
	}

	public String inicio(){
		try{
			CLAVES_PRIVADAS_UBICACION = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_UBICACION);
			CLAVES_PRIVADAS_PASSWORD = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PRIVADAS_PASSWORD);
			CLAVES_PUBLICAS_UBICACION = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_UBICACION);
			CLAVES_PUBLICAS_PASSWORD = gestorPropiedades.valorPropertie(REF_PROP_CLAVES_PUBLICAS_PASSWORD);
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	private boolean cargarAlmacen(String nombreAlmacen) throws Exception{
		boolean passwordOk = validarPasswordKeyStore();
		if(!passwordOk){
			return false;
		}
		boolean cargado = false;
		KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
		if(nombreAlmacen.equals(CLAVES_PRIVADAS_JKS)){
			clavesPrivadasJks = keyStoreUtils.load(CLAVES_PRIVADAS_UBICACION, CLAVES_PRIVADAS_PASSWORD);
			almacenAbierto = CLAVES_PRIVADAS_JKS;
			cargado = true;
		}
		if(nombreAlmacen.equals(CLAVES_PUBLICAS_JKS)){
			clavesPublicasJks = keyStoreUtils.load(CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
			almacenAbierto = CLAVES_PUBLICAS_JKS;
			cargado = true;
		}
		return cargado;
	}

	public String load() throws Exception{
		boolean cargado = false;
		try{
			if(keyStoreName.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
				cargado = cargarAlmacen(CLAVES_PRIVADAS_JKS);
				if(cargado == true){
					actualizarListaBeansPantalla(CLAVES_PRIVADAS_JKS);
				}
			}
			if(keyStoreName.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
				cargado = cargarAlmacen(CLAVES_PUBLICAS_JKS);
				if(cargado == true){
					actualizarListaBeansPantalla(CLAVES_PUBLICAS_JKS);
				}
			}
			if(!cargado){
				addActionError("Password incorrecta");
			}
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	public String close() throws Exception{
		try{
			certificatesClavesPrivadas = null;
			certificatesClavesPublicas = null;
			almacenAbierto = null;
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	private void actualizarListaBeansPantalla(String keyStoreName) throws Exception{
		KeyStoreUtils keyStoreUtils = null;
		if(keyStoreName.equals(CLAVES_PUBLICAS_JKS)){
			keyStoreUtils = new KeyStoreUtils();
			certificatesClavesPublicas = keyStoreUtils.getCertificateBeanList(clavesPublicasJks);
			certificatesClavesPrivadas = null;
		}else if(keyStoreName.equals(CLAVES_PRIVADAS_JKS)){
			keyStoreUtils = new KeyStoreUtils();
			certificatesClavesPrivadas = keyStoreUtils.getCertificateBeanList(clavesPrivadasJks);
			certificatesClavesPublicas = null;
		}
	}

	private boolean validarPasswordKeyStore() throws Exception{
		if(keyStoreName == null || keyStoreName.equals("") || keyStorePassword == null || keyStorePassword.equals("")){
			return false;
		}
		if(keyStoreName.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
			if(CLAVES_PRIVADAS_PASSWORD.equals(keyStorePassword)){
				return true;
			}else{
				return false;
			}
		}else if(keyStoreName.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
			if(CLAVES_PUBLICAS_PASSWORD.equals(keyStorePassword)){
				return true;
			}else{
				return false;
			}
		}else{
			return false;
		}
	}

	public String importar() throws Exception{
		try{
			if(fileUpload == null){
				addActionError("No se ha seleccionado ningún fichero");
				return JSP;
			}
			if(!utiles.esNombreFicheroValido(fileUploadFileName)) {
				addActionError("El nombre del fichero es erroneo");
				return JSP;
			}
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			if(almacenAbierto.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
				int ultimoPunto = fileUploadFileName.lastIndexOf(".");
				if(ultimoPunto == -1){
					addActionError("No se ha seleccionado un fichero de tipo 'Key Pair Files' (*.p12, *.pfx, *.pem)");
					return JSP;
				}
				String extension = fileUploadFileName.substring(ultimoPunto + 1, fileUploadFileName.length()); 
				if(!extension.equalsIgnoreCase(P12) && !extension.equalsIgnoreCase(PFX) &&
						!extension.equalsIgnoreCase(PEM)){
					addActionError("No se ha seleccionado un fichero de tipo 'Key Pair Files' (*.p12, *.pfx, *.pem)");
					return JSP;
				}
				aliasCertificadoDuplicado = keyStoreUtils.importarClavePrivada(sobrescribir, fileUpload, passwordClavePrivada, fileUploadFileName, clavesPrivadasJks, CLAVES_PRIVADAS_UBICACION, CLAVES_PRIVADAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PRIVADAS_JKS);
				if(aliasCertificadoDuplicado != null){
					addActionError("No se ha completado la importación. La clave privada ya está en el keystore con el alias: \'" + 
							aliasCertificadoDuplicado + "\'. Si desea sobrescribirla, marque la casilla de \'Sobrescribir si fuera necesario\'");
					return JSP;
				}
			}else if(almacenAbierto.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
				aliasCertificadoDuplicado = keyStoreUtils.importar(sobrescribir, fileUpload, fileUploadFileName, clavesPublicasJks, CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PUBLICAS_JKS);
				if(aliasCertificadoDuplicado != null){
					addActionError("No se ha completado la importación. El certificado ya está en el keystore con el alias: \'" + 
							aliasCertificadoDuplicado + "\'. Si desea sobrescribirlo, marque la casilla de \'Sobrescribir si fuera necesario\'");
					return JSP;
				}
			}
			addActionMessage("Se ha importado el certificado");
			UtilesWSMate utilesWsMate = new UtilesWSMate();
			utilesWsMate.cargarAlmacenesTrafico();
			return JSP;
		}catch(Exception ex){
			if(ex instanceof CertificateException){
				addActionError("El fichero seleccionado no es un certificado válido para la importación");
			}else if(ex.getCause() != null && ex.getCause().getMessage() != null && 
					ex.getCause().getMessage().contains("Given final block not properly padded")){
				addActionError("No se ha podido extraer la clave privada. Password incorrecta");
			}else{
				addActionError(ex.toString());
			}
			log.error(ex);
		}catch(OegamExcepcion ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	public String exportar() throws Exception{
		try{
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			if(almacenAbierto.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
				setInputStream(keyStoreUtils.getStream(aliasCertificado, clavesPrivadasJks));
			}else if(almacenAbierto.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
				setInputStream(keyStoreUtils.getStream(aliasCertificado, clavesPublicasJks));
			}
			aliasCertificado = aliasCertificado.replaceAll(" ", "_");
			setFileName(aliasCertificado + EXTENSION_CERTIFICADO_EXPORTADO);
			return EXPORTACION_CERTIFICADO;
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
			return JSP;
		}

	}

	public String eliminar() throws Exception{
		try{
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			if(almacenAbierto.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
				keyStoreUtils.borrarCertificado(aliasCertificado, clavesPrivadasJks, CLAVES_PRIVADAS_UBICACION, CLAVES_PRIVADAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PRIVADAS_JKS);
			}else if(almacenAbierto.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
				keyStoreUtils.borrarCertificado(aliasCertificado, clavesPublicasJks, CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PUBLICAS_JKS);
			}
			addActionMessage("Eliminado correctamente");
			UtilesWSMate utilesWsMate = new UtilesWSMate();
			utilesWsMate.cargarAlmacenesTrafico();
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}catch(OegamExcepcion ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	public String renombrar() throws Exception{
		try{
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			if(almacenAbierto.equalsIgnoreCase(CLAVES_PRIVADAS_JKS)){
				keyStoreUtils.modificarAliasDeClavePrivada(aliasCertificado, nuevoAlias, clavesPrivadasJks, CLAVES_PRIVADAS_UBICACION, CLAVES_PRIVADAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PRIVADAS_JKS);
			}else if(almacenAbierto.equalsIgnoreCase(CLAVES_PUBLICAS_JKS)){
				keyStoreUtils.modificarAliasDeClavePublica(aliasCertificado, nuevoAlias, clavesPublicasJks, CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
				actualizarListaBeansPantalla(CLAVES_PUBLICAS_JKS);
			}
			addActionMessage("Renombrado correctamente");
			UtilesWSMate utilesWsMate = new UtilesWSMate();
			utilesWsMate.cargarAlmacenesTrafico();
		}catch(Exception ex){
			log.error(ex);
			addActionError(ex.toString());
		}catch(OegamExcepcion ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}
	
	public String comprobarCertificadosUrl() throws Exception {
		try{
			UtilesWSMate utilesWsMate = new UtilesWSMate();
			utilesWsMate.cargarAlmacenesTrafico();
			KeyStoreUtils keyStoreUtils = new KeyStoreUtils();
			String alias = null;
			clavesPublicasJks = keyStoreUtils.load(CLAVES_PUBLICAS_UBICACION, CLAVES_PUBLICAS_PASSWORD);
			alias = keyStoreUtils.comprobarCertificadosUrl(comprobarUrl, clavesPublicasJks);
			if(alias != null){
				addActionMessage("Comprobación correcta. El certificado asociado a la url \'" + comprobarUrl + 
						"\' está en el almacén de claves bajo el alias \'" + alias + "\'");
			}else{
				addActionMessage("Comprobación incorrecta. No existe ningún certificado en el almacén de claves públicas asociado a esa url");
			}
		}catch(Exception ex){
			log.error(ex);
			addActionError("No se han podido recuperar los certificados del servidor \'" + comprobarUrl + "\'. Debido al siguiente error:");
			addActionError(ex.toString());
		}catch(OegamExcepcion ex){
			log.error(ex);
			addActionError(ex.toString());
		}
		return JSP;
	}

	public static KeyStore getClavesPublicasJks() {
		return clavesPublicasJks;
	}

	public static void setClavesPublicasJks(KeyStore clavesPublicasJks) {
		KeyStoreAction.clavesPublicasJks = clavesPublicasJks;
	}

	public static KeyStore getClavesPrivadasJks() {
		return clavesPrivadasJks;
	}

	public static void setClavesPrivadasJks(KeyStore clavesPrivadasJks) {
		KeyStoreAction.clavesPrivadasJks = clavesPrivadasJks;
	}

	public static CertificateBean[] getCertificatesClavesPublicas() {
		return certificatesClavesPublicas;
	}

	public static void setCertificatesClavesPublicas(
			CertificateBean[] certificatesClavesPublicas) {
		KeyStoreAction.certificatesClavesPublicas = certificatesClavesPublicas;
	}

	public static CertificateBean[] getCertificatesClavesPrivadas() {
		return certificatesClavesPrivadas;
	}

	public static void setCertificatesClavesPrivadas(
			CertificateBean[] certificatesClavesPrivadas) {
		KeyStoreAction.certificatesClavesPrivadas = certificatesClavesPrivadas;
	}

	public String getKeyStorePassword() {
		return keyStorePassword;
	}

	public void setKeyStorePassword(String keyStorePassword) {
		this.keyStorePassword = keyStorePassword;
	}

	public String getKeyStoreName() {
		return keyStoreName;
	}

	public void setKeyStoreName(String keyStoreName) {
		this.keyStoreName = keyStoreName;
	}

	public String getInfoCertificado() {
		return infoCertificado;
	}

	public void setInfoCertificado(String infoCertificado) {
		this.infoCertificado = infoCertificado;
	}

	public static String getAlmacenAbierto() {
		return almacenAbierto;
	}

	public static void setAlmacenAbierto(String almacenAbierto) {
		KeyStoreAction.almacenAbierto = almacenAbierto;
	}

	public String getAliasCertificado() {
		return aliasCertificado;
	}

	public void setAliasCertificado(String aliasCertificado) {
		this.aliasCertificado = aliasCertificado;
	}

	public String getNuevoAlias() {
		return nuevoAlias;
	}

	public void setNuevoAlias(String nuevoAlias) {
		this.nuevoAlias = nuevoAlias;
	}

	public boolean isCertificadoEspera() {
		return certificadoEspera;
	}

	public void setCertificadoEspera(boolean certificadoEspera) {
		this.certificadoEspera = certificadoEspera;
	}

	public InputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public File getFileUpload() {
		return fileUpload;
	}

	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}

	public String getFileUploadContentType() {
		return fileUploadContentType;
	}

	public void setFileUploadContentType(String fileUploadContentType) {
		this.fileUploadContentType = fileUploadContentType;
	}

	public String getFileUploadFileName() {
		return fileUploadFileName;
	}

	public void setFileUploadFileName(String fileUploadFileName) {
		this.fileUploadFileName = fileUploadFileName;
	}

	public String getAliasCertificadoDuplicado() {
		return aliasCertificadoDuplicado;
	}

	public void setAliasCertificadoDuplicado(String aliasCertificadoDuplicado) {
		this.aliasCertificadoDuplicado = aliasCertificadoDuplicado;
	}

	public boolean isSobrescribir() {
		return sobrescribir;
	}

	public void setSobrescribir(boolean sobrescribir) {
		this.sobrescribir = sobrescribir;
	}

	public String getPasswordClavePrivada() {
		return passwordClavePrivada;
	}

	public void setPasswordClavePrivada(String passwordClavePrivada) {
		this.passwordClavePrivada = passwordClavePrivada;
	}

	public String getComprobarUrl() {
		return comprobarUrl;
	}

	public void setComprobarUrl(String comprobarUrl) {
		this.comprobarUrl = comprobarUrl;
	}
	

}
