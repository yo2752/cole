package org.gestoresmadrid.oegam2comun.ficheros.temporales.util;

import org.gestoresmadrid.core.model.enumerados.EstadoImpresion;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.FicherosTemporalesDto;
import org.gestoresmadrid.oegam2comun.ficheros.temporales.view.dto.ResultadoFicherosTemp;
import org.gestoresmadrid.oegam2comun.trafico.ficheros.temporales.model.service.ServicioFicherosTemporales;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.context.support.SpringBeanAutowiringSupport;

import es.globaltms.gestorDocumentos.bean.FicheroBean;
import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;
import utilidades.logger.ILoggerOegam;
import utilidades.logger.LoggerOegam;

public class BorrarFicherosTemporalesThread extends Thread {
	private static final ILoggerOegam log = LoggerOegam.getLogger(BorrarFicherosTemporalesThread.class);
	
	private Long idFichero;
	private long tiempo;
	
	@Autowired
	private ServicioFicherosTemporales servicioFicherosTemporales;
	
	@Autowired
	private GestorDocumentos gestorDocumentos;
	
	public BorrarFicherosTemporalesThread() {
		this.tiempo = 300000;
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	public BorrarFicherosTemporalesThread(Long idFichero) {
		this.idFichero = idFichero;
		this.tiempo = 300000;
		SpringBeanAutowiringSupport.processInjectionBasedOnCurrentContext(this);
	}
	
	
	@Override
	public void run(){
		try{
			Thread.sleep(tiempo);
			if(idFichero != null){
				FicherosTemporalesDto ficheroTemporalDto = servicioFicherosTemporales.getFicheroTemporalDto(idFichero);
				if(EstadoImpresion.DESCARGADO.getValorEnum().equals(ficheroTemporalDto.getEstado())){
					ResultadoFicherosTemp resultado = servicioFicherosTemporales.getFicheroImprimir(ficheroTemporalDto);
					if(!resultado.isError() && resultado.getFichero() != null){
						FicheroBean fichero = resultado.getFichero();
						gestorDocumentos.borradoRecursivo(fichero.getFichero());
						resultado = servicioFicherosTemporales.borrarFicheroTemporal(ficheroTemporalDto);
						if(resultado != null && resultado.isError()){
							log.error(resultado.getMensaje());
						}else{
							log.info("El fichero se ha borrado correctamente");
						}
					}
				}
			}
		}catch(InterruptedException e){
			log.error(e);
		}
	}
	
	
	public Long getIdFichero() {
		return idFichero;
	}
	
	public void setIdFichero(Long idFichero) {
		this.idFichero = idFichero;
	}
	
	public long getTiempo() {
		return tiempo;
	}
	
	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}
	
	public ServicioFicherosTemporales getServicioFicherosTemporales() {
		return servicioFicherosTemporales;
	}

	public void setServicioFicherosTemporales(
			ServicioFicherosTemporales servicioFicherosTemporales) {
		this.servicioFicherosTemporales = servicioFicherosTemporales;
	}

}
