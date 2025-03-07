package org.gestoresmadrid.oegam2comun.impresion.util;

import java.io.File;

import org.gestoresmadrid.core.springContext.ContextoSpring;
import org.gestoresmadrid.oegam2comun.impresion.masiva.model.service.ServicioImpresionMasiva;

import es.globaltms.gestorDocumentos.interfaz.GestorDocumentos;

public class BorrarFicherosImpresionesMasivasThread extends Thread {
	private String nombreFichero;
	private String rutaFichero;
	private long tiempo;

	public BorrarFicherosImpresionesMasivasThread() {
		this.tiempo = 300000;
		this.nombreFichero = "carpetaPorDefecto";
	}

	public BorrarFicherosImpresionesMasivasThread(String nombreFichero) {
		this.nombreFichero = nombreFichero;
		this.tiempo = 300000;
	}

	public BorrarFicherosImpresionesMasivasThread(String nombreFichero, String rutaFichero) {
		this.nombreFichero = nombreFichero;
		this.rutaFichero = rutaFichero;
		this.tiempo = 300000;
	}

	public BorrarFicherosImpresionesMasivasThread(String nombreFichero, String rutaFichero, long tiempo) {
		this.nombreFichero = nombreFichero;
		this.rutaFichero = rutaFichero;
		this.tiempo = tiempo;
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

	public String getRutaFichero() {
		return rutaFichero;
	}

	public void setRutaFichero(String rutaFichero) {
		this.rutaFichero = rutaFichero;
	}

	@Override
	public void run() {
		try {
			Thread.sleep(tiempo);
			File file = new File(rutaFichero);

			if (file.exists()) {
				GestorDocumentos gestorDocumentos = ContextoSpring.getInstance().getBean(GestorDocumentos.class);
				gestorDocumentos.borradoRecursivo(file);

				ServicioImpresionMasiva servicioImpresionMasiva = ContextoSpring.getInstance().getBean(ServicioImpresionMasiva.class);
				servicioImpresionMasiva.eliminar(nombreFichero);
			} else { // No se borra
			}

		} catch (InterruptedException e) {

		}
	}

}
