package utilidades.ficheros;

import java.io.File;

public class BorrarFicherosThread extends Thread {
	private String nombreFichero;
	private long tiempo;
	
	public BorrarFicherosThread(){
		this.tiempo = 300000;
		this.nombreFichero = "carpetaPorDefecto";
	}
	
	public BorrarFicherosThread(String nombreFichero, long tiempo){
		this.nombreFichero = nombreFichero;
		this.tiempo = tiempo;
	}
	
	public BorrarFicherosThread(String nombreFichero){
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
			if(file.delete()){ // Se borra
			}
			else{ // No se borra
			}
						
		} catch (InterruptedException e) {
			
		}
		
		
	}
	
}
