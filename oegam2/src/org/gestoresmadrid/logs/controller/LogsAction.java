package org.gestoresmadrid.logs.controller;

import general.acciones.ActionBase;
import general.acciones.ScriptFeaturesBean;
import general.acciones.TipoPosicionScript;
import general.acciones.TipoScript;

import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.struts2.interceptor.ServletRequestAware;
import org.apache.struts2.interceptor.ServletResponseAware;
import org.gestoresmadrid.logs.service.LogsModeloInt;
import org.gestoresmadrid.logs.view.beans.LogsBeanPantalla;
import org.springframework.beans.factory.annotation.Autowired;

import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class LogsAction extends ActionBase implements ServletRequestAware, ServletResponseAware{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -131931659375902299L;

	private static final String GDOC_CSS = "css/gdoc.css";
	public LogsBeanPantalla logsBeanPantalla;
	private static final ILoggerOegam log = LoggerOegam.getLogger(LogsAction.class);
	private HttpServletRequest servletRequest; // para ajax
	private HttpServletResponse servletResponse; //para ajax
	@Autowired
	private LogsModeloInt logsModelo;
	
	private final String DESCARGAR_LOG = "descargarLog"; 
	
	private InputStream inputStream; 	//Flujo de bytes del fichero a imprimir en PDF del action
	private String fileName;			// Nombre del fichero a imprimir
	private String fileSize;			// Tamaño del fichero a imprimir
	
	
	public String inicio(){
		cargarScriptsYCSS();
		return SUCCESS;
	}

	protected void cargarScriptsYCSS(){
		/* Carga de css y javascript específico para GDoc */
		inicializarScripts();
		ScriptFeaturesBean css = new ScriptFeaturesBean();
		css.setName(GDOC_CSS);
		css.setPosicion(TipoPosicionScript.TOP);
		css.setTipo(TipoScript.CSS);
		addScripts.getScripts().add(css);
		/* Fin Carga de css y javascript específico para GDoc */
	}
	
	public void recuperar() throws Throwable{
		log.debug("recuperar tipos de log según máquina"); 

		String optionSelected = getServletRequest().getParameter("ipMaquina");
		log.debug("ipMaquina=" + optionSelected);
		String ficherosPorMaquina = "";
		ficherosPorMaquina = logsModelo.getFicherosPosMaquina(optionSelected);
		servletResponse.setContentType("text/html; charset=iso-8859-1");
		PrintWriter out = servletResponse.getWriter();
		out.print(ficherosPorMaquina);
	}
	
	public String descargar(){
		cargarScriptsYCSS();
		
		if(logsBeanPantalla.getFechaLog()==null || logsBeanPantalla.getFechaLog().getDiaInicio().isEmpty() ||
				logsBeanPantalla.getFechaLog().getMesInicio().isEmpty() || logsBeanPantalla.getFechaLog().getAnioInicio().isEmpty()) {
			addActionError("Debe rellenar todos los elementos del campo Fecha de log.");
			return SUCCESS;
		}
		
		if(logsBeanPantalla.getMaquinasEnum()==null || logsBeanPantalla.getMaquinasEnum().isEmpty()
				|| logsBeanPantalla.getFicheros()==null || logsBeanPantalla.getFicheros().isEmpty()) {
			addActionError("Debe seleccionar algún elemento en los dos combos.");
			return SUCCESS;
		}
		
		List<String> listaFicheros = logsModelo.existeFichero(logsBeanPantalla.getMaquinasEnum(),logsBeanPantalla.getFicheros(),logsBeanPantalla.getFechaLog());
		
		if(listaFicheros== null){
			//No existe el fichero de log a descargar
			addActionError("No existe el fichero a descargar. Por favor elija una fecha diferente.");
			return SUCCESS;
		}else{
			//Existe fichero de log a descargar
			inputStream = logsModelo.recuperaFichero(listaFicheros);
		}
		
		if (inputStream!=null){
			setFileName("Log");
			return DESCARGAR_LOG;
		}else{
			return SUCCESS;
		}
	}
	
	public LogsBeanPantalla getLogsBeanPantalla() {
		return logsBeanPantalla;
	}

	public HttpServletRequest getServletRequest() {
		return servletRequest;
	}

	public HttpServletResponse getServletResponse() {
		return servletResponse;
	}

	public void setLogsBeanPantalla(LogsBeanPantalla logsBeanPantalla) {
		this.logsBeanPantalla = logsBeanPantalla;
	}

	public void setServletResponse(HttpServletResponse servletResponse) {
		this.servletResponse = servletResponse;
	}
	
	public void setServletRequest(HttpServletRequest servletRequest) {
		this.servletRequest = servletRequest;
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

	public String getFileSize() {
		return fileSize;
	}

	public void setFileSize(String fileSize) {
		this.fileSize = fileSize;
	}

	public LogsModeloInt getLogsModelo() {
		return logsModelo;
	}

	public void setLogsModelo(LogsModeloInt logsModelo) {
		this.logsModelo = logsModelo;
	}

}
