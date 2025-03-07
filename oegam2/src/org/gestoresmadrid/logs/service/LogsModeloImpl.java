package org.gestoresmadrid.logs.service;

import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.util.List;

import org.gestoresmadrid.logs.view.beans.LogsBeanPantalla;
import org.gestoresmadrid.utilidades.components.GestorPropiedades;
import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.estructuras.Fecha;
import utilidades.estructuras.FechaFraccionada;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;
import utilidades.mensajes.GestorFicherosPropiedades;
import utilidades.web.OegamExcepcion;

@Component
public class LogsModeloImpl implements LogsModeloInt{

	private static final ILoggerOegam log = LoggerOegam.getLogger(LogsModeloImpl.class);
	
	private final StringBuffer LISTA_FICHEROS_FRONTAL = new StringBuffer();
	private final StringBuffer LISTA_FICHEROS_PROCESOS = new StringBuffer();
	private final String MAQUINA_PROCESOS="192.168.50.46";
	
	private final String MPROCESOS_URL_LOG_WINDOWS = "mprocesos.url.logs.windows"; //c:datos/logs
	private final String MPROCESOS_URL_LOG_PROCESOS_WINDOWS = "mprocesos.url.logs.procesos.windows"; //c:datos/logs/procesos
	//private final String MFRONTAL_URL_LOG_WINDOWS = "mfrontal.url.logs.windows"; //c:datos/logs/logs_ant
	
	private final String MPROCESOS_URL_LOG_UBUNTU = "mprocesos.url.logs.ubuntu"; //c:datos/logs
	private final String MPROCESOS_URL_LOG_PROCESOS_UBUNTU = "mprocesos.url.logs.procesos.ubuntu"; //c:datos/logs/procesos
	//private final String MFRONTAL_URL_LOG_UBUNTU = "mfrontal.url.logs.ubuntu"; //c:datos/logs/logs_ant
	
	private final String PRODUCCION_LOGS = "produccion.logs";
	private final String SI = "SI";
	
	private final String UBUNTU_FRONTAL_31 = "ubuntu.frontal.31";
	private final String UBUNTU_FRONTAL_32 = "ubuntu.frontal.32";
	private final String UBUNTU_FRONTAL_33 = "ubuntu.frontal.33";
	private final String UBUNTU_FRONTAL_34 = "ubuntu.frontal.34";
	private final String UBUNTU_FRONTAL_71 = "ubuntu.frontal.71";
	private final String UBUNTU_FRONTAL_72 = "ubuntu.frontal.72";
	private final String UBUNTU_FRONTAL_16 = "ubuntu.frontal.16";
	private final String UBUNTU_PROCESOS_46 = "ubuntu.procesos.46";
	
	private final String LOGING="loging";
	private final String TRAFICO="trafico";
	
	private final String FICHERO_PROPERTIES = "log4j.properties";
	private final String RUTA_LOGING = "log4j.appender.file.File";
	private final String RUTA_TRAFICO = "log4j.appender.TRAFICO.File";
	
	private final String RUTA_LOGING_INDICE = "loging.log";
	private final String RUTA_TRAFICO__INDICE = "trafico.log"; 

	@Autowired
	GestorDocumentos gestorDocumentos;

	@Autowired
	private GestorPropiedades gestorPropiedades;
	
	@Autowired
	UtilesFecha utilesFecha;

	public LogsModeloImpl(){
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		LISTA_FICHEROS_FRONTAL.append("loging;");
		LISTA_FICHEROS_FRONTAL.append("loging");
		LISTA_FICHEROS_FRONTAL.append("||");
		
		LISTA_FICHEROS_FRONTAL.append("trafico;");
		LISTA_FICHEROS_FRONTAL.append("trafico");
		
		LISTA_FICHEROS_PROCESOS.append("loging;");
		LISTA_FICHEROS_PROCESOS.append("loging");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("trafico;");
		LISTA_FICHEROS_PROCESOS.append("trafico");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("yerbabuena;");
		LISTA_FICHEROS_PROCESOS.append("proc_yerbabuena");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("matriculacion;");
		LISTA_FICHEROS_PROCESOS.append("proc_matriculacion");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("transmision;");
		LISTA_FICHEROS_PROCESOS.append("proc_transmision");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("solicitud de informacion;");
		LISTA_FICHEROS_PROCESOS.append("proc_sol_informacion");
		LISTA_FICHEROS_PROCESOS.append("||");
		
		LISTA_FICHEROS_PROCESOS.append("justificantes;");
		LISTA_FICHEROS_PROCESOS.append("proc_justificantes");
	}
	
	public String getFicherosPosMaquina(String maquinaSeleccionada){
		//Máquina frontal
		if (!maquinaSeleccionada.equals(MAQUINA_PROCESOS)){
			return LISTA_FICHEROS_FRONTAL.toString();
		}else{
		//Máquina Procesos	
			return LISTA_FICHEROS_PROCESOS.toString();
		}
	}
	
	public void descargarFichero(LogsBeanPantalla logsBeanPantalla){
		//Es la fecha actual, el fichero tiene extension .log
		if (isDiaActual(logsBeanPantalla.getFechaLog())){
			
		}else{
		//No es la fecha actual, el fichero tiene extension .txt
			
		}
	}
	
	private boolean isDiaActual(FechaFraccionada fechaLog){
		Fecha fecha = new Fecha();
		fecha.setDia(fechaLog.getDiaInicio());
		fecha.setMes(fechaLog.getMesInicio());
		fecha.setAnio(fechaLog.getAnioInicio());
		try{
			if (utilesFecha.compararFechas(utilesFecha.getFechaHoraActualLEG(), fecha)==0){
				return true;
			}else{
				return false;
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		return false;
	}
	
	public List<String> existeFichero(String maquina, String fichero, FechaFraccionada fechaLog){
		
		List<String> listaFicheros = null;
		String ip;
		String isUbuntu = null;
		
		//Esto se ha puesto para que funcione en entornos previos.
		if (SI.equals(gestorPropiedades.valorPropertie(PRODUCCION_LOGS))){
			ip="//" + maquina + "/";
		}else{
			ip="//localhost" + "/";
		}
		
		Integer ipMaquina = Integer.parseInt(maquina.substring(maquina.lastIndexOf(".")+1));
		
		//Obtiene la property que indica si es una máquina ubuntu o no para despues saber que url montar.
		switch(ipMaquina){
			case 31:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_31);
				break;
			case 32:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_32);
				break;
			case 33:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_33);
				break;
			case 34:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_34);
				break;
			case 71:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_71);
				break;
			case 72:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_72);
				break;
			case 16:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_FRONTAL_16);
				break;
			case 46:
				isUbuntu = gestorPropiedades.valorPropertie(UBUNTU_PROCESOS_46);
				break;
		}
		
		//Recoge el path que corresponde según máquina y tipo de log.
		//Como se va a ir implantando ubuntu de forma escalonada en los frontales es necesario hacer distincion para saber que path coger.
		try{
			if ( SI.equals(isUbuntu) ) {
				
				if (MAQUINA_PROCESOS.equals(maquina)){
					if (LOGING.equals(fichero)||TRAFICO.equals(fichero)){
						ip = ip + gestorPropiedades.valorPropertie(MPROCESOS_URL_LOG_UBUNTU);
					}else{
						ip = ip + gestorPropiedades.valorPropertie(MPROCESOS_URL_LOG_PROCESOS_UBUNTU);}
				}else{
					GestorFicherosPropiedades gestor = new GestorFicherosPropiedades("log4j.properties");
					if (LOGING.equals(fichero)){
						ip = ip + gestor.getMensaje("log4j.appender.file.File");
					}
					if (TRAFICO.equals(fichero)){
						ip = ip + gestor.getMensaje("log4j.appender.TRAFICO.File");
					}
				}
			}else{

				if (MAQUINA_PROCESOS.equals(maquina)){
					if (LOGING.equals(fichero)||TRAFICO.equals(fichero)){
						ip = ip + gestorPropiedades.valorPropertie(MPROCESOS_URL_LOG_WINDOWS);
					}else{
						ip = ip + gestorPropiedades.valorPropertie(MPROCESOS_URL_LOG_PROCESOS_WINDOWS);}
				}else{
					GestorFicherosPropiedades gestor = new GestorFicherosPropiedades(FICHERO_PROPERTIES);
					if (LOGING.equals(fichero)){
						ip = gestor.getMensaje(RUTA_LOGING);
						ip = ip.substring(0,(ip.indexOf(RUTA_LOGING_INDICE)-1));
					}
					if (TRAFICO.equals(fichero)){
						ip = gestor.getMensaje(RUTA_TRAFICO);
						ip = ip.substring(0,(ip.indexOf(RUTA_TRAFICO__INDICE)-1));
					}
				}
			}
			
			listaFicheros = gestorDocumentos.listarFicherosLogs(ip, fichero, fechaLog);
			
		}catch(OegamExcepcion e){
			log.error("Se ha producido un error al descargar el fichero de log", e);
		}
		return listaFicheros;
	}
	
	public InputStream recuperaFichero(List<String> listaFicherosADescargar){
		
		InputStream inputStream = null;
		try {
			inputStream = gestorDocumentos.getFicherosLogs(listaFicherosADescargar);
		} catch (IOException e) {
			log.error("Se ha producido un error al descargar el fichero de log", e);
		} catch (OegamExcepcion e) {
			log.error("Se ha producido un error al descargar el fichero de log", e);
		}
		
		return inputStream;
	}
}
