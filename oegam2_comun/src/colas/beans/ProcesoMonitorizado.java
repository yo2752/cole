package colas.beans;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.gestoresmadrid.utilidades.components.UtilesFecha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

public class ProcesoMonitorizado {
	
	private String nombre;
	private String ultimaOK;
	private String ultimaKO;
	private String ultimaExcepcion;
	private Integer solicitudesCola;
	
	private static final String OK = "<span size='5' style=\"background-color:green;color:white\" >OK</span>";
	private static final String ERROR = "<span size='5' style=\"background-color:red;color:white\" >ERROR</span>";
	private static final String INDETERMINADO = "<span size='5' style=\"background-color:yellow;color:black\" >INDETERMINADO</span>";
	
	@Autowired
	UtilesFecha utilesFecha;

	public ProcesoMonitorizado() {
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
		solicitudesCola = 0;
	}
	public String getNombre() {
		return nombre;
	}
	public void setNombre(String nombre) {
		this.nombre = nombre;
	}
	public String getUltimaOK() {
		return ultimaOK;
	}
	public void setUltimaOK(String ultimaOK) {
		this.ultimaOK = ultimaOK;
	}
	public String getUltimaKO() {
		return ultimaKO;
	}
	public void setUltimaKO(String ultimaKO) {
		this.ultimaKO = ultimaKO;
	}
	public String getUltimaExcepcion() {
		return ultimaExcepcion;
	}
	public void setUltimaExcepcion(String ultimaExcepcion) {
		this.ultimaExcepcion = ultimaExcepcion;
	}
	public Integer getSolicitudesCola() {
		return solicitudesCola;
	}
	public void setSolicitudesCola(Integer solicitudesCola) {
		this.solicitudesCola = solicitudesCola;
	}
	public String getEstado() {
		return getLogicaEstadoProcesos();
	}
	
	private String getLogicaEstadoProcesos(){
		String estado = INDETERMINADO;
		SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
		try {
			Date ultimaOK = formatter.parse(this.ultimaOK);
			Date ultimaExcepcion = formatter.parse(this.ultimaExcepcion);
			Date fechaActual = new Date();
				
			//Primer caso: +15 min desde la ultima ejecucion	
			if(ultimaOK.after(ultimaExcepcion) && utilesFecha.diferenciaMinutosEntreFechas(ultimaOK, fechaActual) > 15 ){
				estado = OK;
			}else if (ultimaExcepcion.after(ultimaOK) && utilesFecha.diferenciaMinutosEntreFechas(ultimaExcepcion, fechaActual) > 15 ){
				estado = INDETERMINADO;
			
			//Segundo caso: -15 min desde la ultima ejecucion y +5 min entre ejec. OK y erronea. 
			}else if(ultimaOK.after(ultimaExcepcion)  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaOK, fechaActual) < 15) 
													  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaExcepcion, ultimaOK) > 5)){
				estado = OK;
			}else if(ultimaExcepcion.after(ultimaOK)  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaExcepcion, fechaActual) < 15) 
					  								  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaOK, ultimaExcepcion) > 5)){
				estado = ERROR;
			
			//Tercer caso: -15 min desde la ultima ejecución y -5 min entre ejec. OK y erronea.
			}else if(ultimaOK.after(ultimaExcepcion)  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaOK, fechaActual) < 15) 
					  								  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaExcepcion, ultimaOK) < 5)){
				estado = INDETERMINADO;
			}else if(ultimaExcepcion.after(ultimaOK)  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaExcepcion, fechaActual) < 15) 
									  				  && (utilesFecha.diferenciaMinutosEntreFechas(ultimaOK, ultimaExcepcion) < 5)){
				estado = INDETERMINADO;
			
			// Otros	
			}else{
				estado = INDETERMINADO;
			}	
					
		} catch (Exception e) {
			//Cuarto caso: ha lanzado excepcion porque una de las dos es nulas
			if(this.ultimaOK == null && this.ultimaExcepcion == null)
				estado = INDETERMINADO;
			else if(this.ultimaExcepcion == null) //Solo evaluo la ultima  excepcion por evaluacion perezosa del if anterior.
				estado = OK;
			else
				estado = ERROR;
		}
		return estado;
	}
	
	
}
