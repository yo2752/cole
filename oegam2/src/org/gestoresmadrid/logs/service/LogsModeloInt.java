package org.gestoresmadrid.logs.service;

import java.io.InputStream;
import java.util.List;

import org.gestoresmadrid.logs.view.beans.LogsBeanPantalla;

import utilidades.estructuras.FechaFraccionada;

public interface LogsModeloInt {

	public String getFicherosPosMaquina(String maquinaSeleccionada);
	
	public void descargarFichero(LogsBeanPantalla logsBeanPantalla);
	
	public List<String> existeFichero(String maquina, String fichero, FechaFraccionada fechaLog);
	
	public InputStream recuperaFichero(List<String> listaFicherosADescargar);
}
