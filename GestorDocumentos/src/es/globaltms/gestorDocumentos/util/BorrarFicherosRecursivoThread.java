package es.globaltms.gestorDocumentos.util;

import java.io.File;

import org.gestoresmadrid.core.springContext.ContextoSpring;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;

public class BorrarFicherosRecursivoThread extends Thread {

	private String nombreFichero;
	private long tiempo;
	private GestorDocumentos gestorDocumentos;
	
	public BorrarFicherosRecursivoThread(String nombreFichero, long tiempo){
		this.nombreFichero = nombreFichero;
		this.tiempo = tiempo;
	}
	
	public BorrarFicherosRecursivoThread(String nombreFichero){
		this.nombreFichero = nombreFichero;
		this.tiempo = 300000;
	}

	public String getNombreFichero() {
		return nombreFichero;
	}
	
	public void setNombreFichero(String nombreFichero) {
		this.nombreFichero = nombreFichero;
	}

	public long getTiempo() {
		return tiempo;
	}

	public void setTiempo(long tiempo) {
		this.tiempo = tiempo;
	}


	@Override
	public void run(){
		
		try {
			Thread.sleep(tiempo);
			File file = new File(nombreFichero);
			gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
			gestorDocumentos.borradoRecursivo(file);
			
			if(file.delete()){ //Se borra
				
			}
			else{ //No se borra
				
			}
						
		} catch (InterruptedException e) {
			
		}
		
		
	}
	
}
