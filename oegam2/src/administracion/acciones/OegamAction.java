package administracion.acciones;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.oegamComun.acceso.service.impl.UtilesColegiado;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.springframework.beans.factory.annotation.Autowired;

import com.opensymphony.xwork2.Action;

import general.acciones.ActionBase;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;


@SuppressWarnings("serial")
public class OegamAction  extends ActionBase implements ServletRequestAware, ServletResponseAware {
	
	private static final ILoggerOegam log = LoggerOegam.getLogger(OegamAction.class);
	private HttpServletResponse servletResponse;
	private HttpServletRequest servletRequest;
	
	// Fichero
	private String fileUploadContentType;
	private String fileUploadFileName;
	private File fileUpload;
	private InputStream inputStream;
	
	@Autowired
	private GestorPropiedades gestorPropiedades;

	@Autowired
	private UtilesColegiado utilesColegiado;
	
	public String acceso() throws Throwable {
		log.info("Acceso Oegam : Inicio de la redireccion a oegam");
		try{			
			String nif= utilesColegiado.getNifUsuario();
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam = ficheroPropiedades.getProperty("oegam");	
			oegam+="?nif="+nif;
			servletResponse.sendRedirect(oegam);
			
		}catch(Exception e){
			//e.printStackTrace();
			log.error("Acceso Oegam : Se ha lanzado la siguiente excepcion : " + e);
		}
		return null;
		
		
	}
	
	
	public String accesoAdmin() throws Throwable {
		log.info("Acceso Oegam Admin : Inicio de la redireccion a oegam");
		try{			
			String nif= utilesColegiado.getNifUsuario();			
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam = ficheroPropiedades.getProperty("oegam_admin");	
			oegam+="?nif="+nif;
			servletResponse.sendRedirect(oegam);
			
		}catch(Exception e){
			log.error("Acceso Oegam Admin : Se ha lanzado la siguiente excepcion : " + e);
		}
		return null;
		
		
	}
	
	public String accesoAdminPre() throws Throwable {
		log.info("Acceso Oegam Admin_pre : Inicio de la redireccion a oegam");
		try{			
			String nif= utilesColegiado.getNifUsuario();			
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam = ficheroPropiedades.getProperty("oegam_admin_pre");	
			oegam+="?nif="+nif;
			servletResponse.sendRedirect(oegam);
			
		}catch(Exception e){
			log.error("Acceso Oegam Admin_pre : Se ha lanzado la siguiente excepcion : " + e);
		}
		return null;
		
		
	}
	public String accesoHist()throws Throwable {
		log.info("Acceso Oegam_hist : Inicio de la redireccion a oegam_hist");
		try{			
			String nif= utilesColegiado.getNifUsuario();			
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam_hist = ficheroPropiedades.getProperty("oegam_hist");	
			oegam_hist+="?nif="+nif;
			servletResponse.sendRedirect(oegam_hist);
			
		}catch(Exception e){
			log.error("Acceso Oegam : Se ha lanzado la siguiente excepcion : ", e);
		}
		return null;
		
		
	}
	
	public String accesoPre()throws Throwable {
		log.info("Acceso Oegam_pre : Inicio de la redireccion a oegam_hist");
		try{			
			String nif= utilesColegiado.getNifUsuario();			
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam_pre = ficheroPropiedades.getProperty("oegam_pre");	
			oegam_pre+="?nif="+nif;
			servletResponse.sendRedirect(oegam_pre);
			
		}catch(Exception e){
			log.error("Acceso Oegam_pre : Se ha lanzado la siguiente excepcion : " , e);
		}
		return null;
		
		
	}
	
	public String accesoPreHist()throws Throwable {
		log.info("Acceso Oegam_pre_hist : Inicio de la redireccion a oegam_pre_hist");
		try{			
			String nif= utilesColegiado.getNifUsuario();			
			Properties ficheroPropiedades = new Properties();
			ficheroPropiedades.load(Thread.currentThread().getContextClassLoader().getResourceAsStream("acceso.properties"));
			String oegam_pre_hist = ficheroPropiedades.getProperty("oegam_pre_hist");	
			oegam_pre_hist+="?nif="+nif;
			servletResponse.sendRedirect(oegam_pre_hist);
			
		}catch(Exception e){
			log.error("Acceso Oegam_pre_hist : Se ha lanzado la siguiente excepcion : ", e);
		}
		return null;
		
		
	}
	
	/**
	 * Método que se ejecuta cuando se pulsa el enlace 'Ayuda' y que permite la 
	 * descarga del manual de usuario de oegam
	 * @return cadena identificativa para result struts 2
	 */
	public String descargarManualUsuario(){
	
		try{
			File fichero = null;
			// Muestra el manual con la parte de administración dependiendo de si el usuario en sesión tiene
			// o no los permisos de un administrador:
			if(utilesColegiado.tienePermisoAdmin()){
				fichero = new File(gestorPropiedades.valorPropertie("ruta.manual.administrador.oegam"));
			}else{
				fichero = new File(gestorPropiedades.valorPropertie("ruta.manual.usuario.oegam"));
			}
			inputStream = new FileInputStream(fichero);
			return "descargarManual";
		}catch(FileNotFoundException ex){
			addActionError("No se ha podido recuperar el manual");
			log.error(ex);
			return Action.ERROR;
		}catch(Exception ex){
			addActionError("No se ha podido recuperar el manual");
			return Action.ERROR;
		}
	}
	
	
	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}
	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
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


	public File getFileUpload() {
		return fileUpload;
	}


	public void setFileUpload(File fileUpload) {
		this.fileUpload = fileUpload;
	}


	public InputStream getInputStream() {
		return inputStream;
	}


	public void setInputStream(InputStream inputStream) {
		this.inputStream = inputStream;
	}

}
