package org.gestoresmadrid.oegam2comun.model.service;

import java.util.List;

import org.gestoresmadrid.core.ficheroAEAT.bean.FicheroAEATBean;
import org.gestoresmadrid.oegam2comun.trafico.view.dto.TramiteTrafMatrDto;

import trafico.beans.ResumenErroresFicheroAEAT;

public interface ServicioFicheroAEAT {

	/**
	 * Metodo para generar el fichero de texto con los lotes en AEAT
	 * @param listaFicherosAEATBean
	 */	

	public byte[] generarFichero(List<FicheroAEATBean> listaFicherosAEATBean);
	
	/**
	 * Metodo para generar el String que contiene todos los datos para mostrar en el documento
	 * @param ficherosAEATBean
	 * @return String
	 */

	public String generarDatos(FicheroAEATBean ficherosAEATBean);

	public List<FicheroAEATBean> obtenerDatos(String[] codSeleccionados, List<ResumenErroresFicheroAEAT> resumenErroresFicheroAEAT);
	
	byte[] generarFicheroLiquidacionNRE06(TramiteTrafMatrDto tramite);
	
	String generarDatosLiquidacionNRE06(TramiteTrafMatrDto tramite);
	
	

}
